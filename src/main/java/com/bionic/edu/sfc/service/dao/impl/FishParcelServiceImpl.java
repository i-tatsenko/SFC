package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IFishParcelDao;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class FishParcelServiceImpl implements IFishParcelService {

    @Autowired
    private IFishParcelDao fishParcelDao;

    @Override
    public final IDao<FishParcel> getDao() {
        return fishParcelDao;
    }

    @Override
    public List<FishParcel> getAllAvailableForCustomers() {
        return fishParcelDao.getAllAvailableForCustomers();
    }

    @Override
    public List<FishParcel> getAllUnsaled() {
        return fishParcelDao.getAllUnsaled();
    }

    @Override
    public List<FishParcel> getAllForFishSupply(FishShipSupply fishShipSupply) {
        return fishParcelDao.getAllForFishSupply(fishShipSupply);
    }

    @Override
    public List<FishParcel> getAllForSupplyCode(String supplyCode) {
        return fishParcelDao.getAllForSupplyCode(supplyCode);
    }

}
