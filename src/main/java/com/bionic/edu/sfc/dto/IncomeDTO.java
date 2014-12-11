package com.bionic.edu.sfc.dto;

import java.util.Date;

/**
 * Created by docent on 11.12.14.
 */
public class IncomeDTO {

    private long billId;

    private Date creationDate;

    private Date closeDate;

    private double totalSum;

    private double paidsum;

    private double storageCost;

    public IncomeDTO() { }

    public IncomeDTO(long billId, Date creationDate, Date closeDate, double totalSum, double paidsum, double storageCost) {
        this.billId = billId;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
        this.totalSum = totalSum;
        this.paidsum = paidsum;
        this.storageCost = storageCost;
    }

    public IncomeDTO(long billId, Date creationDate, Date closeDate, double totalSum, double paidsum) {
        this.billId = billId;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
        this.totalSum = totalSum;
        this.paidsum = paidsum;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
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

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public double getPaidsum() {
        return paidsum;
    }

    public void setPaidsum(double paidsum) {
        this.paidsum = paidsum;
    }

    public double getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(double storageCost) {
        this.storageCost = storageCost;
    }
}
