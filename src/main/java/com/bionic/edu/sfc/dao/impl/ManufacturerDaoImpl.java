package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IManufacturerDao;
import com.bionic.edu.sfc.entity.Manufacturer;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.09
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class ManufacturerDaoImpl extends ADao<Manufacturer> implements IManufacturerDao {

    public ManufacturerDaoImpl() {
        super(Manufacturer.class);
    }
}
