package com.bionic.edu.sfc.service;

import com.bionic.edu.sfc.dto.IncomeDTO;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.Payment;
import com.bionic.edu.sfc.exception.NotActualPriceException;
import com.bionic.edu.sfc.exception.NotActualWeightException;
import com.bionic.edu.sfc.exception.NotAvailableForCustomersException;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by docent on 04.12.14.
 */
public interface ITradeService {

    void registerNewBill(Collection<FishItem> fishItems) throws NotActualPriceException, NotActualWeightException, NotAvailableForCustomersException;

    Payment registerNewPayment(long billId, double sum);

    List<IncomeDTO> getIncomeForPeriod(Date startDate, Date endDate);

    List<IncomeDTO> getIncomeTotal();
}
