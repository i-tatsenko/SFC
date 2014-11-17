package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.service.dao.IFishService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.util.List;

/**
 * Created by docent on 28.10.14.
 */
@Named
@Scope("request")
public class NewFishBean {

    private static final Log LOGGER = LogFactory.getLog(NewFishBean.class);

    private String name;

    private String description;

    @Autowired
    private IFishService fishService;

    private List<Fish> allFish;

    private Fish selectedFish;

    @PostConstruct
    public void init() {
        allFish = fishService.getAll("name");
    }

    public void addNewFish() {
        try {
            Fish newFish = new Fish(name, description);
            name = null;
            description = null;
            fishService.create(newFish);
            allFish.add(newFish);
        } catch (Exception e) {
            LOGGER.error("Some error while saving new fish", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create new Fish", e.getMessage()));
        }
    }

    public void deleteFish(long fishId) {
        Fish fish = fishService.findById(fishId);
        fishService.delete(fish);
        allFish.remove(fish);
        selectedFish = null;
    }

    public void updateFish(RowEditEvent editEvent) {
        Fish fish = (Fish) editEvent.getObject();
        fishService.update(fish);
        LOGGER.info("Updated fish : " + fish);
    }

    public void closeDialog(ActionEvent event) {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Fish> getAllFish() {
        return allFish;
    }

    public void setAllFish(List<Fish> allFish) {
        this.allFish = allFish;
    }

    public Fish getSelectedFish() {
        return selectedFish;
    }

    public void setSelectedFish(Fish selectedFish) {
        this.selectedFish = selectedFish;
    }
}
