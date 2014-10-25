package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IBillDao;
import com.bionic.edu.sfc.entity.Bill;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.10
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class BillDaoImpl extends ADao<Bill> implements IBillDao {

    public BillDaoImpl() {
        super(Bill.class);
    }
}
