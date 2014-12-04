package com.bionic.edu.sfc.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Entity
public class Bill extends Hideable {

    @ManyToOne
    private Customer customer;

    @OneToMany (mappedBy = "bill")
    private List<FishItem> fishItems;

    @OneToMany (mappedBy = "bill")
    private List<Payment> payments;

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

        return getId() == ((Bill) o).getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + getId() +
                ", customer=" + customer +
                ", fishItems=" + fishItems +
                ", creationDate=" + creationDate +
                ", closeDate=" + closeDate +
                ", totalSum=" + totalSum +
                ", alreadyPaid=" + alreadyPaid +
                ", isShipmentAllowed=" + isShipmentAllowed +
                '}';
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<FishItem> getFishItems() {
        return fishItems;
    }

    public void setFishItems(List<FishItem> fishItems) {
        this.fishItems = fishItems;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public java.util.Date getCloseDate() {
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
