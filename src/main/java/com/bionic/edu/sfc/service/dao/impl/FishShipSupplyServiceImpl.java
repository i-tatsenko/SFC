package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IFishShipSupplyDao;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(Transactional.TxType.MANDATORY)
public class FishShipSupplyServiceImpl implements IFishShipSupplyService {

    @Autowired
    private IFishShipSupplyDao fishShipSupplyDao;

    @Override
    public final IDao<FishShipSupply> getDao() {
        return fishShipSupplyDao;
    }
}
