package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import com.bionic.edu.sfc.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

/**
 * Created by docent on 28.10.14.
 */
@Named("newFishShipSupBean")
@Scope("request")
public class NewFishShipSupplyBean {

    private static final Logger LOGGER = LogManager.getLogger(NewFishShipSupplyBean.class);

    @Autowired
    private IFishService fishService;

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    private FishShipSupply newFishShipSupply = new FishShipSupply();
    private TreeSet<FishShipSupply> fishShipSupplies;

    @PostConstruct
    public void init() {
        newFishShipSupply.setCreationDate(new Date());
        fishShipSupplies = new TreeSet<>(Util.getFishShipSupplyComparator());
        fishShipSupplies.addAll(fishShipSupplyService.getAll("name"));
    }

    public void onSuplpyChosen(SelectEvent event) {
        FishShipSupply fishShipSupplyChosen = (FishShipSupply) event.getObject();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("reg_new_fish_parcel.xhtml?fss=" + fishShipSupplyChosen.getId());
        } catch (IOException e) {
            LOGGER.error("Can't redirect.", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't redirect you for new fish parcel creation"));
        }
    }

    public void createNewFishShipSupply() {
        try {
            fishShipSupplyService.create(newFishShipSupply);
            newFishShipSupply = new FishShipSupply();
            newFishShipSupply.setCreationDate(new Date());
        } catch (ConstraintViolationException cve) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create new Fish ship supply",
                    String.format("Fish ship supply with supply code %s is already registered", newFishShipSupply.getSupplyCode())));
        } catch (Exception e) {
            LOGGER.error("Can't create new Supply", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create new Fish ship supply", e.getMessage()));
        }
    }

    public Collection<FishShipSupply> getFishShipSupply() {
        return fishShipSupplies;
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

    public FishShipSupply getNewFishShipSupply() {
        return newFishShipSupply;
    }

    public void setNewFishShipSupply(FishShipSupply newFishShipSupply) {
        this.newFishShipSupply = newFishShipSupply;
    }
}
