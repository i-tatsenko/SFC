package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by docent on 09.11.14.
 */
@Named
@Scope("request")
public class GMColdStoreBean {

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    private Set<FishShipSupply> deliveredToCMSupplies = new TreeSet<>(Util.getFishShipSupplyComparator());

    @PostConstruct
    public void init() {
        deliveredToCMSupplies.addAll(fishShipSupplyService.getAllTransportedToCM());
    }

    public Set<FishShipSupply> getDeliveredToCMSupplies() {
        return deliveredToCMSupplies;
    }
}
