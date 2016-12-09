package edh.account.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * A receipt of rent, receives as payment for a <code>Tenant</code>
 */
@Entity
public class RentReceipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    private Double amount;
    
    @Column
    private Date createdDate;
    
    @ManyToOne(optional = false)
    @JsonIgnore
    private Tenant tenant;

    protected RentReceipt() {        
    }
    
    public RentReceipt(Tenant tenant, Double amount, Date createdDate) {
        this.tenant = tenant;
        this.amount = amount;
        this.createdDate =  createdDate;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    
    /**
     * @return the tenant
     */
    public Tenant getTenant() {
        return tenant;
    }
    
}
