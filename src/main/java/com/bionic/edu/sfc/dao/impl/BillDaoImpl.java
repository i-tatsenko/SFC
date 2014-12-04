package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IBillDao;
import com.bionic.edu.sfc.entity.Bill;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    @SuppressWarnings("unchecked")
    public List<Bill> getAllOpenBills() {
        return getSession().createQuery("" +
                "FROM Bill " +
                "WHERE visible=true " +
                "AND isShipmentAllowed=false " +
                "OR closeDate IS NULL " +
                "ORDER BY creationDate DESC ")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Bill> getAllReadyForShipment() {
        return getSession().createQuery("" +
                "SELECT b " +
                "FROM FishItem f " +
                "INNER JOIN f.bill b " +
                "WHERE f.removedFromColdStore=false " +
                "AND b.isShipmentAllowed=true " +
                "GROUP BY b")
                .list();
    }
}
