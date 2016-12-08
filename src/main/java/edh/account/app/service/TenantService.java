package edh.account.app.service;

import java.util.List;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;

public interface TenantService {

    public Tenant create(String name);
    
    public Tenant find(Long tenantId);
    
    public List<RentReceipt> findReceiptsForTenant(Long tenantId);
    
    public RentReceipt applyPayment(Long tenantId, Double paymentAmout);
    
}
