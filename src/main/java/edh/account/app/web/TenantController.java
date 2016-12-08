package edh.account.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(value="/{id}/receipt/create", method=RequestMethod.POST)
    public ResponseEntity<RentReceipt> createReceipt(@PathVariable Long id, @RequestBody Double amount) {
        ResponseEntity<RentReceipt> returnValue = null;
        Tenant tenant = tenantService.find(id);
        if (tenant != null) {
            
        }
        return returnValue;
    }
}
