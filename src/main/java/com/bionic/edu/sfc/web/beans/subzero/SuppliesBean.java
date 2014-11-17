package com.bionic.edu.sfc.web.beans.subzero;

import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        supplies = fishShipSupplyService.getAllReadyForTransport();
    }

    public void onRowSelect(SelectEvent event) {
        parcelsForSupply = fishParcelService.getAllForFishSupply(selectedSupply);
    }

    public List<FishShipSupply> getSupplies() {
        return supplies;
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
