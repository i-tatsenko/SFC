package com.bionic.edu.sfc.web.beans.subzero;

import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.FishShipSupplyStatus;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.service.dao.impl.FishParcelServiceImpl;
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
            return;
        }
        parcelsForSupply.forEach(fishParcelService::update);
        selectedSupply.setStatus(FishShipSupplyStatus.READY_FOR_COLD_STORE_REGISTRATION);
        fishShipSupplyService.update(selectedSupply);
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
}
