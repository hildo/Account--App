package edh.account.app.service;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;

public interface TenantService {

    public Tenant create(String name);
    
    public Tenant find(Long tenantId);
    
    public RentReceipt applyPayment(Long tenantId, Double paymentAmout);
    
}
