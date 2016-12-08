package edh.account.app.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edh.account.app.domain.RentReceipt;

@Repository
public interface RentReceiptRepository extends CrudRepository<RentReceipt, Long> {
    
}
