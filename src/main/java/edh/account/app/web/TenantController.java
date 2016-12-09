package edh.account.app.web;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edh.account.app.domain.RentReceipt;
import edh.account.app.domain.Tenant;
import edh.account.app.service.TenantService;

@RestController
@RequestMapping(value="/tenant")
public class TenantController {
    
    @Autowired
    private TenantService tenantService;
    
    @RequestMapping(value="/{id}")
    public Tenant find(@PathVariable Long id) {
        return tenantService.find(id);
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)    
    public Tenant create(@RequestBody String name) {
        return tenantService.create(name);
    }
    
    @RequestMapping("/{id}/receipt")
    public List<RentReceipt> findReceiptsForTenant(@PathVariable Long id) {
        return tenantService.findReceiptsForTenant(id);
    }
    
    @RequestMapping(value="/{id}/receipt/create", method=RequestMethod.POST)
    public RentReceipt createReceipt(@PathVariable Long id, @RequestBody Double amount) {
        return tenantService.applyPayment(id, amount);
    }
    
    @RequestMapping(value="/withrecentpayments")
    public List<Tenant> findTenantsWithRecentPayments(@RequestParam(value="limit", required = false) Integer limit) {
        return tenantService.findTenantsWithRecentPayments((limit != null ? limit : 1));
    }
}
