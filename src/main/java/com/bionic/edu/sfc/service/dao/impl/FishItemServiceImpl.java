package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IFishItemDao;
import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.service.dao.IFishItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class FishItemServiceImpl implements IFishItemService {

    @Autowired
    private IFishItemDao fishItemDao;

    @Override
    public final IDao<FishItem> getDao() {
        return fishItemDao;
    }

    @Override
    public void create(FishItem fishItem) {
        fishItem.setUuid(UUID.randomUUID().toString());
        fishItemDao.create(fishItem);
    }

    @Override
    public List<FishItem> getReadyForWriteOff() {
        return fishItemDao.getReadyForWriteOff();
    }

    @Override
    public List<FishItem> getAllForBill(Bill bill) {
        return fishItemDao.getAllForBill(bill);
    }

    @Override
    public FishItem getForUuid(String uuid) {
        return fishItemDao.getForUuid(uuid);
    }

    @Override
    public void removeFromColdStore(FishItem fishItem) {
        fishItem.setRemovedFromColdStore(true);
        fishItem.setRemovedFromColdStoreDate(new Date());
        fishItemDao.update(fishItem);
    }
}
