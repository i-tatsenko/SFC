package com.bionic.edu.sfc.web.beans.subzero;

import com.bionic.edu.sfc.entity.*;
import com.bionic.edu.sfc.entity.builder.FishParcelBuilder;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by docent on 17.11.14.
 */
@Named
@Scope("request")
public class SuppliesBean {

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IFishService fishService;

    @Autowired
    private IManufacturerService manufacturerService;

    private long newParcelFishId;

    private long newParcelManufacturerId;

    private double newParcelWeight;

    private List<FishShipSupply> supplies;

    private FishShipSupply selectedSupply;

    private List<FishParcel> parcelsForSupply;
    public static final Log LOG = LogFactory.getLog(SuppliesBean.class);

    @PostConstruct
    public void init() {
        supplies = fishShipSupplyService.getAllReadyForTransport();
        if (selectedSupply == null && supplies != null && supplies.size() > 0) {
            selectedSupply = supplies.get(0);
            fillParcels();
        }
    }

    public void onSupplyEdit(RowEditEvent event) {
        FishParcel parcel = (FishParcel) event.getObject();
        try {
            fishParcelService.update(parcel);
        } catch (Exception e) {
            LOG.error("Can't update parcel", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There was error while was saving parcel", e.getMessage()));
        }
    }

    public void deleteParcel(long id) {
        FishParcel parcel = parcelsForSupply.stream().filter(p -> p.getId() == id).findAny().get();
        parcelsForSupply.remove(parcel);
        fishParcelService.delete(parcel);
    }

    public void deliverParcels() {
        if (selectedSupply == null) {
            LOG.info("Selected supply is null");
            return;
        }
        parcelsForSupply.forEach(fp -> fp.setColdStoreRegistrationDate(new Date()));
        parcelsForSupply.forEach(fishParcelService::update);
        selectedSupply.setStatus(FishShipSupplyStatus.READY_FOR_COLD_STORE_REGISTRATION);
        fishShipSupplyService.update(selectedSupply);
        selectedSupply = null;
        parcelsForSupply = Collections.emptyList();
        init();
    }

    public boolean deliverParcelsButtonEnabled() {
        return parcelsForSupply != null && parcelsForSupply.size() > 0;
    }

    public void onRowSelect(SelectEvent event) {
        fillParcels();
    }

    private void fillParcels() {
        parcelsForSupply = fishParcelService.getAllForFishSupply(selectedSupply);
    }

    public List<FishShipSupply> getSupplies() {
        return supplies;
    }

    public void newParcel() {
        LOG.info("New parcel");
        Fish fish = fishService.findById(newParcelFishId);
        Manufacturer manuf = manufacturerService.findById(newParcelManufacturerId);
        try {
            FishParcel newFishParcel = FishParcelBuilder.aFishParcel()
                    .withFish(fish)
                    .withFishShipSupply(selectedSupply)
                    .withManufacturer(manuf)
                    .withWeight(newParcelWeight)
                    .build();
            fishParcelService.create(newFishParcel);
            newParcelFishId = 0;
            newParcelManufacturerId = 0;
            newParcelWeight = 0;
            parcelsForSupply.add(newFishParcel);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create new fish parcel", e.getMessage()));
            LOG.error("Can't create new fish parcel", e);
        }
    }

    public double getTotalWeight() {
        if (parcelsForSupply == null || parcelsForSupply.size() == 0) {
            return 0;
        }
        return parcelsForSupply.stream().mapToDouble(FishParcel::getWeight).sum();
    }

    public void setSupplies(List<FishShipSupply> supplies) {
        this.supplies = supplies;
    }

    public FishShipSupply getSelectedSupply() {
        return selectedSupply;
    }

    public void setSelectedSupply(FishShipSupply selectedSupply) {
        this.selectedSupply = selectedSupply;
    }

    public List<FishParcel> getParcelsForSupply() {
        return parcelsForSupply;
    }

    public void setParcelsForSupply(List<FishParcel> parcelsForSupply) {
        this.parcelsForSupply = parcelsForSupply;
    }

    public double getNewParcelWeight() {
        return newParcelWeight;
    }

    public void setNewParcelWeight(double newParcelWeight) {
        this.newParcelWeight = newParcelWeight;
    }

    public long getNewParcelManufacturerId() {
        return newParcelManufacturerId;
    }

    public void setNewParcelManufacturerId(long newParcelManufacturerId) {
        this.newParcelManufacturerId = newParcelManufacturerId;
    }

    public long getNewParcelFishId() {
        return newParcelFishId;
    }

    public void setNewParcelFishId(long newParcelFishId) {
        this.newParcelFishId = newParcelFishId;
    }

    public IFishService getFishService() {
        return fishService;
    }

    public IManufacturerService getManufacturerService() {
        return manufacturerService;
    }
}
