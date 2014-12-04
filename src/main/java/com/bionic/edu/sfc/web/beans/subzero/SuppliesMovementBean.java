package com.bionic.edu.sfc.web.beans.subzero;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.service.dao.IBillService;
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

    @Autowired
    private IBillService billService;

    private Set<FishShipSupply> toRefundSupplies = new TreeSet<>(Util.getFishShipSupplyComparator());

    private Set<FishItem> writeOffItems = new TreeSet<>(Util.getFishItemComparator());

    private Set<FishItem> shipmentItems = new TreeSet<>(Util.getFishItemComparator());

    private Set<Bill> shipmentBills = new TreeSet<>((b1, b2) -> (int) (b1.getId() - b2.getId()));

    private long selectedBillId;

    @PostConstruct
    public void init() {
        toRefundSupplies.addAll(fishShipSupplyService.getAllToRefund());
        writeOffItems.addAll(fishItemService.getReadyForWriteOff());
        shipmentBills.addAll(billService.getAllReadyForShipment());
    }

    public void writeOff(String uuid) {
        FishItem fishItem = fishItemService.getForUuid(uuid);
        fishItemService.removeFromColdStore(fishItem);
        writeOffItems.remove(fishItem);
    }

    public void refundParcel(long fssId) {
        FishShipSupply supply = fishShipSupplyService.findById(fssId);
        fishShipSupplyService.refund(supply);
        toRefundSupplies.remove(supply);
    }

    public void ship(long billId) {
        Bill bill = billService.findById(billId);
        billService.ship(bill);
    }

    public void prepareShowBillDetails(long selectedBillId) {
        this.selectedBillId = selectedBillId;
    }

    public List<FishItem> getItemsForSelectedBill() {
        Bill bill = billService.findById(selectedBillId);
        if (bill == null) {
            return Collections.emptyList();
        }
        return bill.getFishItems();
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

    public void setToRefundSupplies(Set<FishShipSupply> toRefundSupplies) {
        this.toRefundSupplies = toRefundSupplies;
    }

    public void setWriteOffItems(Set<FishItem> writeOffItems) {
        this.writeOffItems = writeOffItems;
    }

    public void setShipmentItems(Set<FishItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

    public Set<Bill> getShipmentBills() {
        return shipmentBills;
    }

    public void setShipmentBills(Set<Bill> shipmentBills) {
        this.shipmentBills = shipmentBills;
    }



    public long getSelectedBillId() {
        return selectedBillId;
    }

    public void setSelectedBillId(long selectedBillId) {
        this.selectedBillId = selectedBillId;
    }
}
