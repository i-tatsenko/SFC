package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishShipSupplyDao;
import com.bionic.edu.sfc.entity.FishShipSupply;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
}
