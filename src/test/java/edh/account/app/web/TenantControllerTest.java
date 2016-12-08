package edh.account.app.web;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;
import edh.account.app.service.TenantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TenantControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TenantRepository tenantRepository;
    
    private String dummyName() {
        return "Tenant-" + Instant.now().toEpochMilli();
    }
    
    @Test
    public void testCreateAndFind() {
        // Create a new Tenant
        String tenantName = dummyName();
        Tenant tenant = restTemplate.postForObject("/tenant/create", tenantName, Tenant.class);
        Assert.assertNotNull(tenant);
        Assert.assertEquals(tenantName, tenant.getName());
        Assert.assertNotNull(tenant.getId());
        Assert.assertEquals((Double) 0.0, tenant.getCurrentRentCreditAmount());
        Assert.assertNotNull(tenant.getCurrentRentPaidToDate());
        Assert.assertNull(tenant.getWeeklyRentAmount());
        
        // Find the newly created tenant
        Tenant tenant2 = restTemplate.getForObject("/tenant/" + tenant.getId(), Tenant.class);
        Assert.assertNotNull(tenant2);
        Assert.assertTrue(tenant != tenant2);
        Assert.assertEquals(tenant.getId(), tenant2.getId());
        Assert.assertEquals(tenant.getName(), tenant2.getName());
    }
    
    @Test
    public void testFindReturnsNothing() {
        Tenant tenant = restTemplate.getForObject("/tenant/12345678", Tenant.class);
        Assert.assertNull(tenant);
    }
    
    @Test
    public void createReceipt() {
        Tenant tenant = new Tenant(dummyName());
        tenant.setCurrentRentCreditAmount(25.00);
        Calendar c = Calendar.getInstance();
        c.set(2016, 10, 14, 10, 0, 0);
        tenant.setCurrentRentPaidToDate(c.getTime());
        tenant.setWeeklyRentAmount(250.00);
        tenant = tenantRepository.save(tenant);
        
        RentReceipt receipt = restTemplate.postForObject("/tenant/" + tenant.getId() + "/receipt/create", 
                225.00, RentReceipt.class);
        
        Assert.assertNotNull(receipt);
        Assert.assertEquals((Double) 225.00, receipt.getAmount());
        Assert.assertNotNull(receipt.getId());
        Assert.assertEquals(tenant.getId(), receipt.getTenant().getId());
        
        tenant = restTemplate.getForObject("/tenant/" + tenant.getId(), Tenant.class);
        Assert.assertEquals((Double) 0.0, tenant.getCurrentRentCreditAmount());
        c.setTime(tenant.getCurrentRentPaidToDate());
        Assert.assertEquals(2016, c.get(Calendar.YEAR));
        Assert.assertEquals(10, c.get(Calendar.MONTH));
        Assert.assertEquals(21, c.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void testReceiptsForTenant() {
        Tenant tenant = new Tenant(dummyName());
        tenant.setCurrentRentCreditAmount(0.00);
        Calendar c = Calendar.getInstance();
        c.set(2016, 10, 14, 10, 0, 0);
        tenant.setCurrentRentPaidToDate(c.getTime());
        tenant.setWeeklyRentAmount(50.00);
        tenant = tenantRepository.save(tenant);
        
        String urlBase = "/tenant/" + tenant.getId();

        List<RentReceipt> receipts = restTemplate.getForObject(urlBase + "/receipt" , List.class);
        Assert.assertNotNull(receipts);
        Assert.assertTrue(receipts.isEmpty());
        
        RentReceipt receipt = restTemplate.postForObject(urlBase + "/receipt/create", 
                50.00, RentReceipt.class);
        
        receipts = restTemplate.getForObject(urlBase + "/receipt" , List.class);
        Assert.assertNotNull(receipts);
        Assert.assertFalse(receipts.isEmpty());
        Assert.assertEquals(1, receipts.size());
        
        receipt = restTemplate.postForObject(urlBase + "/receipt/create", 
                50.00, RentReceipt.class);
        
        receipts = restTemplate.getForObject(urlBase + "/receipt" , List.class);
        Assert.assertNotNull(receipts);
        Assert.assertFalse(receipts.isEmpty());
        Assert.assertEquals(2, receipts.size());
        
    }
}
