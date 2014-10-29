package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by docent on 28.10.14.
 */
@ManagedBean (eager = false)
@ViewScoped
public class NewFishParcelBean {

    private static final Logger LOGGER = LogManager.getLogger(NewFishParcelBean.class);

    private FishShipSupply fishShipSupply;

    @ManagedProperty("fishShipSupplyService")
    private IFishShipSupplyService fishShipSupplyService;

    @ManagedProperty("fishParcelService")
    private IFishParcelService fishParcelService;

    @ManagedProperty("fishService")
    private IFishService fishService;

    @ManagedProperty("manufacturerService")
    private IManufacturerService manufacturerService;

    private FishParcel newFishParcel = new FishParcel();

    @PostConstruct
    public void init() {
        try {
            long fssId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fss"));
            fishShipSupply = fishShipSupplyService.findById(fssId);
            if (fishShipSupply == null) {
//                invalidAccess();
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid access", e);
//            invalidAccess();
        }
    }

    public Collection<FishParcel> getFishParcels() {
        return fishParcelService.getAllForFishSupply(fishShipSupply);
    }

    public Collection<Manufacturer> getManufacturers() {
        return manufacturerService.getAll();
    }

    public Collection<Fish> getFishes() {
        return fishService.getAll();
    }

    private void invalidAccess() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("reg_new_ship_supply.xhtml");
        } catch (IOException e) {
            LOGGER.error("Very very bad thing", e);
        }
    }

    public void createNewFishParcel() {
        try {
            newFishParcel.setFishShipSupply(fishShipSupply);
            fishParcelService.create(newFishParcel);
            newFishParcel = new FishParcel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create fish parcel.", e.getMessage()));
        }
    }

    public void onFishCreated(SelectEvent event) {
        LOGGER.info("Item created: " + event.getObject());
    }

    public void showNewFishForm() {
        LOGGER.info("Now will show dialog");
        RequestContext.getCurrentInstance().openDialog("reg_new_fish");
    }

    public void showNewManufForm() {
        RequestContext.getCurrentInstance().openDialog("reg_new_manuf");
    }

    public FishShipSupply getFishShipSupply() {
        return fishShipSupply;
    }

    public void setFishShipSupply(FishShipSupply fishShipSupply) {
        this.fishShipSupply = fishShipSupply;
    }

    public IFishShipSupplyService getFishShipSupplyService() {
        return fishShipSupplyService;
    }

    public void setFishShipSupplyService(IFishShipSupplyService fishShipSupplyService) {
        this.fishShipSupplyService = fishShipSupplyService;
    }

    public IFishParcelService getFishParcelService() {
        return fishParcelService;
    }

    public void setFishParcelService(IFishParcelService fishParcelService) {
        this.fishParcelService = fishParcelService;
    }

    public FishParcel getNewFishParcel() {
        return newFishParcel;
    }

    public void setNewFishParcel(FishParcel newFishParcel) {
        this.newFishParcel = newFishParcel;
    }

    public IFishService getFishService() {
        return fishService;
    }

    public void setFishService(IFishService fishService) {
        this.fishService = fishService;
    }

    public IManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    public void setManufacturerService(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }
}
