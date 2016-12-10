package edh.account.app.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;

@Component
public class PaymentProcessor {
    
    RentReceipt process(Tenant tenant, Double amount) {
        RentReceipt returnValue = null;
        
        if ((tenant != null) && (amount != null)) {
            // Get total available, which is amount + credit
            Double weeklyRent = tenant.getWeeklyRentAmount();
            Double totalAvailable = amount + tenant.getCurrentRentCreditAmount();
            // See how many weeks of rent this will pay
            double weeksPaid = Math.floor(totalAvailable / weeklyRent);
            int daysToAdvance = Double.valueOf(weeksPaid * 7).intValue();
            
            // Set the paid-to-date ahead the number of weeks
            Instant currentPaidToInstant = Instant.ofEpochMilli(tenant.getCurrentRentPaidToDate().getTime());
            Duration toAdvanceDuration = Duration.ofDays(daysToAdvance);
            Instant newPaidToInstant = currentPaidToInstant.plus(toAdvanceDuration);
            tenant.setCurrentRentPaidToDate(Date.from(newPaidToInstant));
            // Set the rent credit
            tenant.setCurrentRentCreditAmount(totalAvailable - (weeklyRent * weeksPaid));
            
            returnValue = new RentReceipt(tenant, amount, new Date());
        }
        
        return returnValue;
    }
    
}
