package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IBillDao;
import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.service.dao.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class BillServiceImpl implements IBillService {

    @Autowired
    private IBillDao billDao;

    @Override
    public final IDao<Bill> getDao() {
        return billDao;
    }
}
