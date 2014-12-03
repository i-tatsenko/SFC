package com.bionic.edu.sfc.web.beans.customer;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.builder.FishItemBuilder;
import com.bionic.edu.sfc.exception.NotActualPriceException;
import com.bionic.edu.sfc.exception.NotActualWeightException;
import com.bionic.edu.sfc.service.IDeliveryService;
import com.bionic.edu.sfc.service.ITradeService;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.web.JSFUtil;
import com.bionic.edu.sfc.web.beans.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by docent on 01.12.14.
 */
@Named
@Scope("session")
public class OrderBean {

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private LoginBean loginBean;

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITradeService tradeService;

    private List<FishItem> orderItems = new LinkedList<>();

    private long selectedParcelId;

    private double selectedWeight;

    public void addToOrder() {
        if (!fishParcelService.hasEnoughWeight(selectedParcelId, selectedWeight)) {
            JSFUtil.showMessage("There is no enough weight");
            return;
        }
        Customer customer = customerService.getCurrentCustomer();
        if (customer == null) {
            JSFUtil.showMessage("You must be logged in to order items");
            return;
        }

        FishParcel selectedParcel = fishParcelService.findById(selectedParcelId);

        for (FishItem fi : orderItems) {
            if (fi.getFishParcel().equals(selectedParcel)) {
                double totalWeight = selectedWeight + fi.getWeight();
                if (!fishParcelService.hasEnoughWeight(selectedParcel.getId(), totalWeight)) {
                    JSFUtil.showMessage("There is no enough weight", "You already have " + fi.getWeight() + " tonnes of " + selectedParcel.getFish().getName() + " in your order");
                    return;
                }
                fi.setWeight(fi.getWeight() + selectedWeight);
                return;
            }
        }

        FishItem fishItem = FishItemBuilder.aFishItem()
                .withFishParcel(selectedParcel)
                .withWeight(selectedWeight)
                .withPrice(selectedParcel.getActualPrice())
                .withCustomer(customer)
                .build();
        orderItems.add(fishItem);
    }

    public void prepareAddItemToOrder(long selectedParcelId) {
        this.selectedParcelId = selectedParcelId;
    }

    public double getTotalPrice() {
        if (orderItems == null || orderItems.size() == 0) {
            return 0;
        }
        return orderItems.stream().mapToDouble(fi -> fi.getWeight() * fi.getPrice()).sum();
    }

    public double getTotalWeight() {
        if (orderItems == null || orderItems.size() == 0) {
            return 0;
        }
        return orderItems.stream().mapToDouble(FishItem::getWeight).sum();
    }

    public String buyButtonText() {
        if (loginBean.isAuthenticated()) {
            return "Buy!";
        }
        return "Login to buy items";
    }

    public void removeItem(String itemUuid) {
        Optional<FishItem> fishItem = orderItems.stream().filter(fi -> fi.getUuid().equals(itemUuid)).findAny();
        if (!fishItem.isPresent()) {
            return;
        }
        orderItems.remove(fishItem.get());
    }

    public void buyItems() {
        try {
            tradeService.registerNewBill(orderItems);
        } catch (NotActualPriceException e) {
            e.printStackTrace();
        } catch (NotActualWeightException e) {
            e.printStackTrace();
        }
        orderItems.clear();
    }

    public double getDeliveryCost() {
        return deliveryService.getDeliveryCost(getTotalWeight());
    }

    public List<FishItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<FishItem> orderItems) {
        this.orderItems = orderItems;
    }

    public long getSelectedParcelId() {
        return selectedParcelId;
    }

    public void setSelectedParcelId(long selectedParcelId) {
        this.selectedParcelId = selectedParcelId;
    }

    public double getSelectedWeight() {
        return selectedWeight;
    }

    public void setSelectedWeight(double selectedWeight) {
        this.selectedWeight = selectedWeight;
    }
}
