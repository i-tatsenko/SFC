package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by docent on 09.11.14.
 */
@Named("gmCStore")
@Scope("request")
public class GMColdStoreBean {

    private static final Log LOG = LogFactory.getLog(GMColdStoreBean.class);

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    private long selectedFSSId;

    private Set<FishParcel> readyForApproveParcels = new TreeSet<>((fp1, fp2) -> {
        int result = fp1.getManufacturer().getName().compareTo(fp2.getManufacturer().getName());
        if (result != 0) {
            return result;
        }
        return fp1.getFish().getName().compareTo(fp2.getFish().getName());
    });

    private Set<FishShipSupply> deliveredToCMSupplies = new TreeSet<>(Util.getFishShipSupplyComparator());

    @PostConstruct
    public void init() {
        LOG.info("init");
        deliveredToCMSupplies.addAll(fishShipSupplyService.getAllTransportedToCM());
        if (deliveredToCMSupplies.size() > 0) {
            selectedFSSId = deliveredToCMSupplies.iterator().next().getId();
        }
        if (selectedFSSId != 0) {
            FishShipSupply supply = fishShipSupplyService.findById(selectedFSSId);
            List<FishParcel> fishParcels = fishParcelService.getAllForFishSupply(supply);
            LOG.info("parcels: " + fishParcels);
            readyForApproveParcels.addAll(fishParcels);
        }
    }

    public boolean haveWaitingForApprove() {
        return readyForApproveParcels.size() > 0;
    }

    public double getTotalWeight() {
        if (readyForApproveParcels.size() > 0) {
            return readyForApproveParcels.stream().mapToDouble(FishParcel::getWeight).sum();
        }
        return -1;
    }

    public Set<FishShipSupply> getDeliveredToCMSupplies() {
        return deliveredToCMSupplies;
    }

    public long getSelectedFSSId() {
        return selectedFSSId;
    }

    public void setSelectedFSSId(long selectedFSSId) {
        this.selectedFSSId = selectedFSSId;
    }

    public List<FishParcel> getReadyForApproveParcels() {
        List<FishParcel> result = new LinkedList<>();
        result.addAll(readyForApproveParcels);
        return result;
    }

    public void setReadyForApproveParcels(Set<FishParcel> readyForApproveParcels) {
        this.readyForApproveParcels = readyForApproveParcels;
    }

    public void setDeliveredToCMSupplies(Set<FishShipSupply> deliveredToCMSupplies) {
        this.deliveredToCMSupplies = deliveredToCMSupplies;
    }
}
