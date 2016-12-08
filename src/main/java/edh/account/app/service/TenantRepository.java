package edh.account.app.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edh.account.app.domain.Tenant;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, Long> {
    
}
