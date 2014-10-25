package com.bionic.edu.sfc.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Ivan
 * 2014.10
 */
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToMany (mappedBy = "bill")
    private Set<FishItem> fishItems;

    @Column(nullable = false)
    private Date creationDate;

    private Date closeDate;

    @Column(nullable = false)
    private Double totalSum;

    private double alreadyPaid;

    private boolean isShipmentAllowed;

    public Bill() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        return id.equals(bill.id);
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", customer=" + customer +
                ", fishItems=" + fishItems +
                ", creationDate=" + creationDate +
                ", closeDate=" + closeDate +
                ", totalSum=" + totalSum +
                ", alreadyPaid=" + alreadyPaid +
                ", isShipmentAllowed=" + isShipmentAllowed +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<FishItem> getFishItems() {
        return fishItems;
    }

    public void setFishItems(Set<FishItem> fishItems) {
        this.fishItems = fishItems;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public double getAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(double alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
    }

    public boolean isShipmentAllowed() {
        return isShipmentAllowed;
    }

    public void setShipmentAllowed(boolean isShipmentAllowed) {
        this.isShipmentAllowed = isShipmentAllowed;
    }
}
