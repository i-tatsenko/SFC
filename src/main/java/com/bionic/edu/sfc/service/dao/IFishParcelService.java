package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IFishParcelService extends IService<FishParcel> {

    List<FishParcel> getAllAvailableForCustomers();

    List<FishParcel> getAllUnsaled();

    List<FishParcel> getAllForFishSupply(FishShipSupply fishShipSupply);

    List<FishParcel> getAllForSupplyCode(String suppluCode);

    void writeOff(FishParcel parcel, double weight);

}
