package com.bionic.edu.sfc.service;

import java.util.Date;

/**
 * Created by docent on 11.12.14.
 */
public interface IStorageService {

    double getStorageCost(Date startDate, Date endDate, double weight);

    double getStorageCost(Date startDate, double weight);
}
