package com.bionic.edu.sfc.web.beans.subzero;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IFishItemService;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.*;

/**
 * Created by docent on 20.11.14.
 */
@Named("supMoveBean")
@Scope("request")
public class SuppliesMovementBean {

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishItemService fishItemService;

    @Autowired
    private IFishParcelService fishParcelService;

    private Set<FishShipSupply> toRefundSupplies = new TreeSet<>(Util.getFishShipSupplyComparator());

    private List<FishParcel> toRefundParcels;

    private Set<FishItem> writeOffItems = new TreeSet<>();

    private Set<FishItem> shipmentItems = new TreeSet<>();

    private Bill selectedBill;

    private FishShipSupply selectedSupply;

    @PostConstruct
    public void init() {
        toRefundSupplies.addAll(fishShipSupplyService.getAllToRefund());
        writeOffItems.addAll(fishItemService.getReadyForWriteOff());
    }

    public List<FishItem> getAllForBill() {
        if (selectedBill != null) {
            return fishItemService.getAllForBill(selectedBill);
        }
        return Collections.emptyList();
    }

    public void writeOff(String uuid) {

    }

    public void fillRefundParcels(String supplyCode) {
        toRefundParcels = new LinkedList<>(toRefundSupplies.stream().filter(sup -> sup.getSupplyCode().equals(supplyCode)).findAny().get().getFishParcels());
    }

    public List<FishParcel> getAllToRefund() {
        return toRefundParcels;
    }

    public Set<FishShipSupply> getToRefundSupplies() {
        return toRefundSupplies;
    }

    public Set<FishItem> getWriteOffItems() {
        return writeOffItems;
    }

    public Set<FishItem> getShipmentItems() {
        return shipmentItems;
    }

    public FishShipSupply getSelectedSupply() {
        return selectedSupply;
    }

    public void setSelectedSupply(FishShipSupply selectedSupply) {
        this.selectedSupply = selectedSupply;
    }

    public Bill getSelectedBill() {
        return selectedBill;
    }

    public void setSelectedBill(Bill selectedBill) {
        this.selectedBill = selectedBill;
    }

    public void setToRefundSupplies(Set<FishShipSupply> toRefundSupplies) {
        this.toRefundSupplies = toRefundSupplies;
    }

    public List<FishParcel> getToRefundParcels() {
        return toRefundParcels;
    }

    public void setToRefundParcels(List<FishParcel> toRefundParcels) {
        this.toRefundParcels = toRefundParcels;
    }

    public void setWriteOffItems(Set<FishItem> writeOffItems) {
        this.writeOffItems = writeOffItems;
    }

    public void setShipmentItems(Set<FishItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }
}
