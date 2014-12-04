package com.bionic.edu.sfc.web.beans;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by docent on 26.11.14.
 */
@Named
@Scope("request")
public class OnSaleBean {

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IFishService fishService;

    private List<FishParcel> onSaleParcels = new LinkedList<>();

    @PostConstruct
    public void init() {
        onSaleParcels = fishParcelService.getAllAvailableForCustomers();
    }

    public String getFishLogo(long fishId) {
        Fish fish = fishService.findById(fishId);
        return fish.getLogo() == null ? "def_fish.png" : fish.getLogo();
    }

    public List<FishParcel> getOnSaleParcels() {
        return onSaleParcels;
    }

    public void setOnSaleParcels(List<FishParcel> onSaleParcels) {
        this.onSaleParcels = onSaleParcels;
    }
}
