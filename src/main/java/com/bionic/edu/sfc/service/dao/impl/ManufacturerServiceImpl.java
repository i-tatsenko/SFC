package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IManufacturerDao;
import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class ManufacturerServiceImpl implements IManufacturerService {

    @Autowired
    private IManufacturerDao manufacturerDao;

    @Override
    public IDao<Manufacturer> getDao() {
        return manufacturerDao;
    }
}
