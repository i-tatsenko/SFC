package com.bionic.edu.sfc.service;

import com.bionic.edu.sfc.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by docent on 11.12.14.
 */
@Service
public class StorageServiceImpl implements IStorageService {

    @Value("${storage.cost}")
    private double oneDayOneTonStorageCost;

    @Override
    public double getStorageCost(Date startDate, Date endDate, double weight) {
        LocalDate start = Util.dateToLocalDate(startDate);
        LocalDate end = Util.dateToLocalDate(endDate);
        long storageDays = Math.abs(end.until(start).getDays());
        return storageDays * weight * oneDayOneTonStorageCost;
    }

    @Override
    public double getStorageCost(Date startDate, double weight) {
        return getStorageCost(startDate, new Date(), weight);
    }
}
