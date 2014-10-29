package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Created by docent on 28.10.14.
 */
@ManagedBean
@RequestScoped
public class NewManufacturerBean {

    private String name;

    private String description;

    private IManufacturerService manufacturerService;

    public void regNewManuf() {
        Manufacturer newManuf = new Manufacturer(name, description);
        manufacturerService.create(newManuf);
        RequestContext.getCurrentInstance().closeDialog(newManuf);
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

    public IManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    public void setManufacturerService(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }
}
