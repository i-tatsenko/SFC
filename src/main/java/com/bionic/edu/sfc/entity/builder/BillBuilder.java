package com.bionic.edu.sfc.entity.builder;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.Customer;

import java.sql.Date;
import java.util.Calendar;

/**
 * Ivan
 * 2014.10
 */
public class BillBuilder {
    private Date creationDate = new Date(Calendar.getInstance().getTimeInMillis());
    private Date closeDate;
    private Double totalSum;
    private double alreadyPaid;
    private Customer customer;

    private BillBuilder(Customer customer, double totalSum) {
        this.customer = customer;
        this.totalSum = totalSum;
    }

    public static BillBuilder aBill(Customer customer, double totalSum) {
        return new BillBuilder(customer, totalSum);
    }

    public BillBuilder withCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public BillBuilder withCloseDate(Date closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public BillBuilder withTotalSum(Double totalSum) {
        this.totalSum = totalSum;
        return this;
    }

    public BillBuilder withAlreadyPaid(double alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
        return this;
    }

    public BillBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Bill build() {
        Bill bill = new Bill();
        bill.setCreationDate(creationDate);
        bill.setCloseDate(closeDate);
        bill.setTotalSum(totalSum);
        bill.setAlreadyPaid(alreadyPaid);
        bill.setCustomer(customer);
        return bill;
    }
}
