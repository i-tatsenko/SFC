package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.service.dao.IFishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Created by docent on 28.10.14.
 */
@ManagedBean
@RequestScoped
public class NewFishBean {

    private static final Logger LOGGER = LogManager.getLogger(NewFishBean.class);

    private String name;

    private String description;

    private IFishService fishService;

    public void addNewFish() {
        try {
            Fish newFish = new Fish(name, description);
            fishService.create(newFish);
            RequestContext.getCurrentInstance().closeDialog(newFish);
        } catch (Exception e) {
            LOGGER.error("Some error while wanted to save new fish", e);
        }
    }

    public IFishService getFishService() {
        return fishService;
    }

    public void setFishService(IFishService fishService) {
        this.fishService = fishService;
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
}
