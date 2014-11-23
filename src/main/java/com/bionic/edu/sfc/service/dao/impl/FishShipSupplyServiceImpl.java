package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IFishShipSupplyDao;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.FishShipSupplyStatus;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class FishShipSupplyServiceImpl implements IFishShipSupplyService {

    @Autowired
    private IFishShipSupplyDao fishShipSupplyDao;

    @Override
    public final IDao<FishShipSupply> getDao() {
        return fishShipSupplyDao;
    }

    @Override
    public List<FishShipSupply> getAllTransportedToCM() {
        return fishShipSupplyDao.getAllTransportedToCM();
    }

    @Override
    public List<FishShipSupply> getAllReadyForTransport() {
        return fishShipSupplyDao.getAllReadyForTransport();
    }

    @Override
    public List<FishShipSupply> getAllToRefund() {
        return fishShipSupplyDao.getAllToRefund();
    }

    @Override
    public void refund(FishShipSupply fishShipSupply) {
        fishShipSupply.setStatus(FishShipSupplyStatus.REFUNDED);
        fishShipSupplyDao.delete(fishShipSupply);
    }
}
