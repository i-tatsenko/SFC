package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishItemDao;
import com.bionic.edu.sfc.entity.Bill;
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
    public List<FishItem> getAllForBill(Bill bill) {
        return getSession().createQuery(
                "FROM FishItem " +
                "WHERE bill=:bill "
        ).list();
    }

    @Override
    public FishItem getForUuid(String uuid) {
        return (FishItem) getSession().createQuery(
                "FROM FishItem " +
                "WHERE uuid=:uuid"
        ).setParameter("uuid", uuid).uniqueResult();
    }


}
