package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishShipSupplyDao;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.FishShipSupplyStatus;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.09
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class FishShipSupplyDaoImpl extends ADao<FishShipSupply> implements IFishShipSupplyDao {

    public FishShipSupplyDaoImpl() {
        super(FishShipSupply.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishShipSupply> getAllTransportedToCM() {
        return getSession().createQuery("FROM FishShipSupply " +
                "WHERE visible=true " +
                "AND status=:status")
                .setParameter("status", FishShipSupplyStatus.READY_FOR_COLD_STORE_REGISTRATION)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishShipSupply> getAllReadyForTransport() {
        return (List<FishShipSupply>) getSession().createQuery(
                "FROM FishShipSupply " +
                        "WHERE visible=true " +
                        "AND status=:status " +
                        "ORDER BY creationDate")
                .setParameter("status", FishShipSupplyStatus.READY_FOR_TRANSPORT)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishShipSupply> getAllToRefund() {
        return getSession().createQuery(
                        "FROM FishShipSupply " +
                        "WHERE visible=true " +
                        "AND status=:status"
        ).setParameter("status", FishShipSupplyStatus.REFUNDED).list();
    }
}
