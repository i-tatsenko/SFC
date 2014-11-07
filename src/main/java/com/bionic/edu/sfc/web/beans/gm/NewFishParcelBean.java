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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by docent on 28.10.14.
 */
@Named
@Scope("session")
public class NewFishParcelBean {

    private static final Log LOG = LogFactory.getLog(NewFishParcelBean.class);

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IFishService fishService;

    @Autowired
    private IManufacturerService manufacturerService;

    private Set<FishShipSupply> fishShipSupplies;

    private Set<Fish> fishes;

    private Set<Manufacturer> manufacturers;

    private Set<FishParcel> fishParcels;

    private FishShipSupply fishShipSupply;

    private long fishShipSupplyId;

    private long nfpFishId;

    private long nfpManufId;

    private long nfpWeight;

    private double nfpWholeSale;

    @PostConstruct
    public void init() {
        try {
            LOG.info("FishShipSupplyId: " + fishShipSupplyId);
            update();
            if (fishShipSupply == null) {
//                invalidAccess();
            }
        } catch (NumberFormatException e) {
            LOG.error("Invalid access", e);
            invalidAccess();
        }
    }

    public void update() {
        String fss = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fss");
        LOG.info("fss: " + fss);
        if (fss != null) {
            try {
                fishShipSupplyId = Long.parseLong(fss);
            } catch (NumberFormatException e) {
                invalidAccess();
            }
        }
        fishShipSupply = fishShipSupplyService.findById(fishShipSupplyId);
        if (fishShipSupply == null) {
            invalidAccess();
        }
        fillFish();
        fillManuf();
        fillFishParcells();
    }

    public void deleteFishParcel(long fishParcelId) {
        FishParcel fishParcel = fishParcelService.findById(fishParcelId);
        fishParcelService.delete(fishParcel);
    }

    private void fillManuf() {
        manufacturers = new TreeSet<>(Util.getManufComparator());
        manufacturers.addAll(manufacturerService.getAll("name"));
    }

    private void fillFish() {
        fishes = new TreeSet<>(Util.getFishComparator());
        fishes.addAll(fishService.getAll("name"));
    }

    private void fillFishParcells() {
        fishParcels = new TreeSet<>(Util.getFishParcelComparator());
        fishParcels.addAll(fishParcelService.getAllForFishSupply(fishShipSupply));
    }

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public Set<Fish> getFishes() {
        return fishes;
    }

    private void invalidAccess() {
        try {
            LOG.info("Invalid access");
            FacesContext.getCurrentInstance().getExternalContext().redirect("reg_new_ship_supply.xhtml");
        } catch (IOException e) {
            LOG.error("Very very bad thing", e);
        }
    }

    public void createNewFishParcel() {
        try {
            LOG.info("Creating new fish parcel");
            fishShipSupply = fishShipSupplyService.findById(fishShipSupplyId);
            FishParcel newFishParcel = new FishParcel();
            Fish fish = fishService.findById(nfpFishId);
            Manufacturer manuf = manufacturerService.findById(nfpManufId);

            newFishParcel.setFishShipSupply(fishShipSupply);
            newFishParcel.setFish(fish);
            newFishParcel.setManufacturer(manuf);
            newFishParcel.setWeight(nfpWeight);
            newFishParcel.setWholeSale(nfpWholeSale);
            fishParcelService.create(newFishParcel);
            update();

            nfpWeight = 0;
            nfpWholeSale = 0;
            LOG.info("New fish parcel has been created");
        } catch (Exception e) {
            LOG.error("Can't create fish parcel", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create fish parcel.", e.getMessage()));
        }
    }

    public void onFishCreated(SelectEvent event) {
        LOG.info("Item created: " + event.getObject());
        fillFish();
        fillManuf();

    }

    public void showNewFishForm() {
        LOG.info("Now will show dialog");
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

    public void setFishes(Set<Fish> fishes) {
        this.fishes = fishes;
    }

    public long getFishShipSupplyId() {
        return fishShipSupplyId;
    }

    public long getNfpFishId() {
        return nfpFishId;
    }

    public Set<FishParcel> getFishParcels() {
        return fishParcels;
    }

    public void setNfpFishId(long nfpFishId) {
        this.nfpFishId = nfpFishId;
    }

    public long getNfpManufId() {
        return nfpManufId;
    }

    public void setNfpManufId(long nfpManufId) {
        this.nfpManufId = nfpManufId;
    }

    public long getNfpWeight() {
        return nfpWeight;
    }

    public void setNfpWeight(long nfpWeight) {
        this.nfpWeight = nfpWeight;
    }

    public double getNfpWholeSale() {
        return nfpWholeSale;
    }

    public void setNfpWholeSale(double nfpWholeSale) {
        this.nfpWholeSale = nfpWholeSale;
    }

    public void setFishShipSupplyId(long fishShipSupplyId) {
        this.fishShipSupplyId = fishShipSupplyId;
    }

    public Set<FishShipSupply> getFishShipSupplies() {
        return fishShipSupplies;
    }

    public void setFishShipSupplies(Set<FishShipSupply> fishShipSupplies) {
        this.fishShipSupplies = fishShipSupplies;
    }
}
