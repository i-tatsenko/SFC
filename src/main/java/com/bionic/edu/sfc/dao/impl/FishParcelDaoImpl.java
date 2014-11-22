package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishParcelDao;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.FishShipSupplyStatus;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class FishParcelDaoImpl extends ADao<FishParcel> implements IFishParcelDao{

    public FishParcelDaoImpl() {
        super(FishParcel.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishParcel> getAllAvailableForCustomers() {
        return getSession()
                .createQuery("FROM FishParcel " +
                                "WHERE availableForCustomers=true " +
                                "AND weightSold < weight " +
                                "AND visible=true").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishParcel> getAllUnsaled() {
        Session session = getSession();
        Query query = session.createQuery("SELECT parcel FROM FishParcel parcel " +
                "INNER JOIN parcel.fishShipSupply supply " +
                "WHERE parcel.weightSold < parcel.weight " +
                "AND parcel.visible=true " +
                "AND supply.visible=true " +
                "AND supply.status=:status");
        Query status = query
                .setParameter("status", FishShipSupplyStatus.READY_FOR_SALE);
        return (List<FishParcel>) status
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishParcel> getAllForFishSupply(FishShipSupply fishShipSupply) {
        return getSession().createQuery("FROM FishParcel " +
                                        "WHERE fishShipSupply=:fishSupply " +
                                        "AND visible=true")
                .setParameter("fishSupply", fishShipSupply)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishParcel> getAllForSupplyCode(String supplyCode) {
        return getSession().createQuery(
                "SELECT parcel " +
                "FROM FishParcel parcel " +
                "INNER JOIN FishShipSupply supply " +
                "WHERE supply.supplyCode=:code"
        ).setParameter("code", supplyCode)
                .list();
    }


}
