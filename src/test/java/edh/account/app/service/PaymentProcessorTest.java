package edh.account.app.service;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;

public class PaymentProcessorTest {

    @Test
    public void testScenarioOne() {
        Tenant tenant = new Tenant("tenantName");
        tenant.setWeeklyRentAmount(300.00);
        tenant.setCurrentRentCreditAmount(50.00);
        Calendar c = Calendar.getInstance();
        c.set(2016, 10, 14);
        tenant.setCurrentRentPaidToDate(c.getTime());
        
        PaymentProcessor processor = new PaymentProcessor();
        RentReceipt receipt = processor.process(tenant, 275.00);
        
        Assert.assertNotNull(receipt);
        Assert.assertEquals((Double) 275.00, receipt.getAmount());
        Assert.assertNotNull(receipt.getTenant());
        Assert.assertEquals(tenant.getId(), receipt.getTenant().getId());
        Assert.assertEquals((Double) 25.00, receipt.getTenant().getCurrentRentCreditAmount());
        c.add(Calendar.DAY_OF_MONTH, 7);
        Assert.assertEquals(c.getTime(), receipt.getTenant().getCurrentRentPaidToDate());
    }
    
    @Test
    public void testScenarioTwo() {
        Tenant tenant = new Tenant("tenantName");
        tenant.setWeeklyRentAmount(300.00);
        tenant.setCurrentRentCreditAmount(50.00);
        Calendar c = Calendar.getInstance();
        c.set(2016, 10, 14);
        tenant.setCurrentRentPaidToDate(c.getTime());
        
        PaymentProcessor processor = new PaymentProcessor();
        RentReceipt receipt = processor.process(tenant, 200.00);
        
        Assert.assertNotNull(receipt);
        Assert.assertEquals((Double) 200.00, receipt.getAmount());
        Assert.assertNotNull(receipt.getTenant());
        Assert.assertEquals(tenant.getId(), receipt.getTenant().getId());
        Assert.assertEquals((Double) 250.00, receipt.getTenant().getCurrentRentCreditAmount());
        Assert.assertEquals(c.getTime(), receipt.getTenant().getCurrentRentPaidToDate());
    }

    @Test
    public void testScenarioThree() {
        Tenant tenant = new Tenant("tenantName");
        tenant.setWeeklyRentAmount(100.00);
        tenant.setCurrentRentCreditAmount(50.00);
        Calendar c = Calendar.getInstance();
        c.set(2016, 10, 14);
        tenant.setCurrentRentPaidToDate(c.getTime());
        
        PaymentProcessor processor = new PaymentProcessor();
        RentReceipt receipt = processor.process(tenant, 250.00);
        
        Assert.assertNotNull(receipt);
        Assert.assertEquals((Double) 250.00, receipt.getAmount());
        Assert.assertNotNull(receipt.getTenant());
        Assert.assertEquals(tenant.getId(), receipt.getTenant().getId());
        Assert.assertEquals((Double) 0.00, receipt.getTenant().getCurrentRentCreditAmount());
        c.add(Calendar.DAY_OF_MONTH, 21);
        Assert.assertEquals(c.getTime(), receipt.getTenant().getCurrentRentPaidToDate());
    }

    @Test
    public void testScenarioFour() {
        Tenant tenant = new Tenant("tenantName");
        tenant.setWeeklyRentAmount(250.00);
        tenant.setCurrentRentCreditAmount(0.00);
        Calendar c = Calendar.getInstance();
        c.set(2016, 10, 14);
        tenant.setCurrentRentPaidToDate(c.getTime());
        
        PaymentProcessor processor = new PaymentProcessor();
        RentReceipt receipt = processor.process(tenant, 1000.00);
        
        Assert.assertNotNull(receipt);
        Assert.assertEquals((Double) 1000.00, receipt.getAmount());
        Assert.assertNotNull(receipt.getTenant());
        Assert.assertEquals(tenant.getId(), receipt.getTenant().getId());
        Assert.assertEquals((Double) 0.00, receipt.getTenant().getCurrentRentCreditAmount());
        c.add(Calendar.DAY_OF_MONTH, 28);
        Assert.assertEquals(c.getTime(), receipt.getTenant().getCurrentRentPaidToDate());
    }
    
}
