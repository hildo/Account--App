package edh.account.app.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;

@Repository
public interface RentReceiptRepository extends CrudRepository<RentReceipt, Long> {
    
    List<RentReceipt> findByTenant(Tenant tenant);
    
}
