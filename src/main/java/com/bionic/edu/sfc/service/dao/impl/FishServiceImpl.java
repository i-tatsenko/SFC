package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IFishDao;
import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.service.dao.IFishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class FishServiceImpl implements IFishService {

    @Autowired
    private IFishDao fishDao;

    @Override
    public final IDao<Fish> getDao() {
        return fishDao;
    }
}
