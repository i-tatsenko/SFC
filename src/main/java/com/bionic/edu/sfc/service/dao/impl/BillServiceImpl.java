package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IBillDao;
import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.Payment;
import com.bionic.edu.sfc.service.dao.IBillService;
import com.bionic.edu.sfc.service.dao.IFishItemService;
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
public class BillServiceImpl implements IBillService {

    @Autowired
    private IBillDao billDao;

    @Autowired
    private IFishItemService fishItemService;

    @Override
    public final IDao<Bill> getDao() {
        return billDao;
    }

    @Override
    public void ship(Bill bill) {
        bill.getFishItems().forEach(fishItemService::removeFromColdStore);
    }

    @Override
    public List<Bill> getAllOpenBills() {
        return billDao.getAllOpenBills();
    }

    @Override
    public List<Bill> getAllReadyForShipment() {
        return billDao.getAllReadyForShipment();
    }

    @Override
    public boolean canAllowShipment(Bill bill) {
        return bill.getTotalSum() * bill.getCustomer().getPrepaymentRate() <= bill.getPayments().stream().mapToDouble(Payment::getTotalSum).sum();
    }

    @Override
    public List<Bill> getAllOpenOrClosedAtPeriod(Date startDate, Date endDate) {
        return billDao.getAllOpenOrClosedAtPeriod(startDate, endDate);
    }
}
