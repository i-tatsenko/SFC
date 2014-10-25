package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IFishDao;
import com.bionic.edu.sfc.entity.Fish;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.09
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class FishDaoImpl extends ADao<Fish> implements IFishDao {

    public FishDaoImpl() {
        super(Fish.class);
    }
}
