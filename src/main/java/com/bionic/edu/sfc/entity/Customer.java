package com.bionic.edu.sfc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Ivan
 * 2014.09
 */
@Entity
public class Customer extends User {

    @Column(precision = 3,
            scale = 2)
    private float prepaymentRate;

    public Customer() { }

    public float getPrepaymentRate() {
        return prepaymentRate;
    }

    public void setPrepaymentRate(float prepaymentRate) {
        this.prepaymentRate = prepaymentRate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                " prepaymentRate=" + prepaymentRate +
                '}';
    }
}
