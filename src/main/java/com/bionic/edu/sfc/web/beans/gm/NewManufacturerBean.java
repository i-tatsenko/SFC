package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import com.bionic.edu.sfc.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Set;
import java.util.TreeSet;

@Named("newManufBean")
@Scope("request")
public class NewManufacturerBean {

    private static final Log LOG = LogFactory.getLog(NewManufacturerBean.class);

    private String name;

    private String description;

    @Autowired
    private IManufacturerService manufacturerService;

    private Set<Manufacturer> manufacturers;

    private Manufacturer selectedManufacturer;

    @PostConstruct
    public void init() {
        manufacturers = new TreeSet<>(Util.getManufComparator());
        manufacturers.addAll(manufacturerService.getAll("name"));
    }

    public void regNewManuf() {
        Manufacturer newManuf = new Manufacturer(name, description);
        name = null;
        description = null;
        try {
            manufacturerService.create(newManuf);
            manufacturers.add(newManuf);
        } catch (Exception e) {
            LOG.error("Can't create new manufacturer");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't create new manufacturer", e.getMessage()));
        }
    }

    public void updateManuf(RowEditEvent editEvent) {
        Manufacturer manuf= (Manufacturer) editEvent.getObject();
        manufacturerService.update(manuf);
    }

    public void deleteManuf(long id) {
        Manufacturer manuf = manufacturerService.findById(id);
        manufacturers.remove(manuf);
        manufacturerService.delete(manuf);
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

    public Manufacturer getSelectedManufacturer() {
        return selectedManufacturer;
    }

    public void setSelectedManufacturer(Manufacturer selectedManufacturer) {
        this.selectedManufacturer = selectedManufacturer;
    }
}
