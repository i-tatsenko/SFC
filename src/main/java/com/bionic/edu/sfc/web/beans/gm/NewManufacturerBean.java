package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import com.bionic.edu.sfc.util.Util;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by docent on 28.10.14.
 */
@Named("newManufBean")
@Scope("request")
public class NewManufacturerBean {

    private String name;

    private String description;

    @Autowired
    private IManufacturerService manufacturerService;

    private Set<Manufacturer> manufacturers;

    @PostConstruct
    public void init() {
        manufacturers = new TreeSet<>(Util.getManufComp());
        manufacturers.addAll(manufacturerService.getAll("name"));
    }

    public void regNewManuf() {
        Manufacturer newManuf = new Manufacturer(name, description);
        name = null;
        description = null;
        manufacturerService.create(newManuf);
    }

    public void closeDialog() {
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

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Set<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }
}
