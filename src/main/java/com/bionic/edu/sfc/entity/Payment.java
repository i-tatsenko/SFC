package com.bionic.edu.sfc.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Ivan
 * 2014.10
 */
@Entity
public class Payment extends Hideable {

    @ManyToOne
    @NotNull
    private Bill bill;

    @Column(nullable = false)
    @NotNull
    private Date creationDate;

    @Column(nullable = false)
    @Min(0)
    private Double totalSum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return getId() == payment.getId();
    }



    @Override
    public String toString() {
        return "Payment{" +
                "id=" + getId() +
                ", bill=" + bill +
                ", creationDate=" + creationDate +
                ", totalSum=" + totalSum +
                '}';
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }


}
