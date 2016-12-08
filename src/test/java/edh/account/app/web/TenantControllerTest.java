package edh.account.app.web;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import edh.account.app.domain.Tenant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TenantControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testCreateAndFind() {
        // Create a new Tenant
        String tenantName = "Tenant-" + Instant.now().toEpochMilli();
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
}
