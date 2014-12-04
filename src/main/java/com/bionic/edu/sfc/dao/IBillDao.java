package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.Bill;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IBillDao extends IDao<Bill> {

    List<Bill> getAllOpenBills();

    List<Bill> getAllReadyForShipment();
}
