package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.Bill;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IBillService extends IService<Bill> {

    void ship(Bill bill);

    List<Bill> getAllOpenBills();

    List<Bill> getAllReadyForShipment();

    boolean canAllowShipment(Bill bill);
}
