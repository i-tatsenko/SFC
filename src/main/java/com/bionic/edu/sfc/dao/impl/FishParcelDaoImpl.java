package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishParcelDao;
import com.bionic.edu.sfc.entity.FishParcel;
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
                                "AND weightSold < weight").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FishParcel> getAllUnsaled() {
        return (List<FishParcel>)getSession()
                .createQuery("FROM FishParcel " +
                             "WHERE weightSold < weight").list();
    }


}
