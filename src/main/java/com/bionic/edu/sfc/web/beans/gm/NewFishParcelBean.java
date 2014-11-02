package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import com.bionic.edu.sfc.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by docent on 28.10.14.
 */
@Named
@Scope("request")
public class NewFishParcelBean {

    private static final Logger LOGGER = LogManager.getLogger(NewFishParcelBean.class);

    private FishShipSupply fishShipSupply;

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IFishService fishService;

    @Autowired
    private IManufacturerService manufacturerService;

    private FishParcel newFishParcel = new FishParcel();
    private Set<Fish> fishes;
    private Set<Manufacturer> manufacturers;

    private Fish fish;

    private Manufacturer manufacturer;

    @PostConstruct
    public void init() {
        try {
            String fss = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fss");
            if (fss == null) {
                return;
            }
            long fssId = Long.parseLong(fss);
            fishShipSupply = fishShipSupplyService.findById(fssId);
            fillFish();
            fillManuf();
            if (fishShipSupply == null) {
//                invalidAccess();
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid access", e);
//            invalidAccess();
        }
    }

    private void fillManuf() {
        manufacturers = new TreeSet<>(Util.getManufComp());
        manufacturers.addAll(manufacturerService.getAll("name"));
    }

    private void fillFish() {
        fishes = new TreeSet<>(Util.getFishCompar());
        fishes.addAll(fishService.getAll("name"));
    }

    public List<FishParcel> getFishParcels() {
        return fishParcelService.getAllForFishSupply(fishShipSupply);
    }

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public Set<Fish> getFishes() {
        return fishes;
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
            LOGGER.info("Creating new fish parcel");
            newFishParcel.setFishShipSupply(fishShipSupply);
            fishParcelService.create(newFishParcel);
            newFishParcel = new FishParcel();
            LOGGER.info("New fish parcel has been created");
        } catch (Exception e) {
            LOGGER.error("Can't create fish parcel", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create fish parcel.", e.getMessage()));
        }
    }

    public void onFishCreated(SelectEvent event) {
        LOGGER.info("Item created: " + event.getObject());
        fillFish();
        fillManuf();

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

    public void setFishes(Set<Fish> fishes) {
        this.fishes = fishes;
    }

    public void setManufacturers(Set<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
