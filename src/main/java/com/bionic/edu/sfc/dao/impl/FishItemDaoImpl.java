package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishItemDao;
import com.bionic.edu.sfc.entity.FishItem;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class FishItemDaoImpl extends ADao<FishItem> implements IFishItemDao {

    public FishItemDaoImpl() {
        super(FishItem.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishItem> getReadyForWriteOff() {
        return (List<FishItem>)getSession()
                .createQuery("FROM FishItem " +
                             "WHERE forWriteOff=true " +
                             "AND removedFromColdStore=false " +
                             "AND visible=true ")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishItem> getAllReadyForShipment() {
        return (List<FishItem>)getSession()
                .createQuery("FROM FishItem " +
                             "WHERE readyForShipment=true " +
                             "AND removedFromColdStore=false " +
                             "AND visible=true")
                .list();
    }
}
