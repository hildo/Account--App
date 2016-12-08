package edh.account.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private RentReceiptRepository receiptRepository;
    
    @Autowired
    private PaymentProcessor paymentProcessor;
    
    @Override
    public Tenant create(String name) {
        Tenant newTenant = new Tenant(name);
        return tenantRepository.save(newTenant);
    }

    @Override
    public Tenant find(Long tenantId) {
        return tenantRepository.findOne(tenantId);
    }
    
    @Override
    public RentReceipt applyPayment(Long tenantId, Double paymentAmount) {
        RentReceipt returnValue = null;
        Tenant tenant = find(tenantId);
        if (tenant != null) {
            RentReceipt receipt = paymentProcessor.process(tenant, paymentAmount);
            if (receipt != null) {
                returnValue = receiptRepository.save(receipt);
            }
        }
        return returnValue;
    }
}
