package edh.account.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edh.account.app.domain.Tenant;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, Long> {
    
    @Query("SELECT T FROM Tenant T WHERE (SELECT COUNT(R) FROM T.receipts R WHERE R.createdDate > :limit) > 0")
    public List<Tenant> findTenantsWithRecentReceipts(@Param("limit") Date limit);
}
