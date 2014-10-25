package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.FishParcel;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IFishParcelService extends IService<FishParcel> {

    public List<FishParcel> getAllAvailableForCustomers();

    public List<FishParcel> getAllUnsaled();
}
