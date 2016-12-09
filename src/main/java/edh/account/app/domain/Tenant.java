package edh.account.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 * An account (or ledger) used for tracking the amount of rent paid against a
 * lease.
 */
@Entity
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Double weeklyRentAmount;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date currentRentPaidToDate = new Date();
    
    @Column
    private Double currentRentCreditAmount = 0d;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenant")
    private Set<RentReceipt> receipts;

    protected Tenant() {
    }
    
    public Tenant(String name) {
        this.name = name;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the weeklyRentAmount
     */
    public Double getWeeklyRentAmount() {
        return weeklyRentAmount;
    }

    public void setWeeklyRentAmount(Double weeklyRentAmount) {
        this.weeklyRentAmount = weeklyRentAmount;
    }
    
    /**
     * @return the currentRentPaidToDate
     */
    public Date getCurrentRentPaidToDate() {
        return currentRentPaidToDate;
    }

    public void setCurrentRentPaidToDate(Date currentRentPaidToDate) {
        this.currentRentPaidToDate = currentRentPaidToDate;
    }
    
    /**
     * @return the currentRentCreditAmount
     */
    public Double getCurrentRentCreditAmount() {
        return currentRentCreditAmount;
    }
    
    public void setCurrentRentCreditAmount(Double currentRentCreditAmount) {
        this.currentRentCreditAmount = currentRentCreditAmount;
    }
    
    public Set<RentReceipt> getReceipts() {
        return receipts;
    }
}
