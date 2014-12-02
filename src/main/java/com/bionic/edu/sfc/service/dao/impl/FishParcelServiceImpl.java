package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IFishParcelDao;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.builder.FishItemBuilder;
import com.bionic.edu.sfc.exception.NoEnoughFishException;
import com.bionic.edu.sfc.service.dao.IFishItemService;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class FishParcelServiceImpl implements IFishParcelService {

    @Autowired
    private IFishParcelDao fishParcelDao;

    @Autowired
    private IFishItemService fishItemService;

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

    @Override
    public void writeOff(FishParcel parcel, double weight) {
        if (parcel.getWeight() < parcel.getWeightSold() + weight) {
            throw new NoEnoughFishException("There is no enough fish to generate write off");
        }
        parcel.setWeightSold(parcel.getWeightSold() + weight);
        fishParcelDao.update(parcel);

        FishItem fishItem = FishItemBuilder.aFishItem()
                .withFishParcel(parcel)
                .withCreationDate(new Date())
                .withForWriteOff(true)
                .withWeight(weight)
                .build();

        fishItemService.create(fishItem);
    }

    @Override
    public boolean haveEnoughWeight(long fishParcelId, double weight) {
        FishParcel fishParcel = fishParcelDao.findById(fishParcelId);
        return fishParcel.getWeight() > weight;
    }

}
