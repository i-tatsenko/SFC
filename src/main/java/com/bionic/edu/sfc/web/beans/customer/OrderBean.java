package com.bionic.edu.sfc.web.beans.customer;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.builder.FishItemBuilder;
import com.bionic.edu.sfc.exception.NotActualPriceException;
import com.bionic.edu.sfc.exception.NotActualWeightException;
import com.bionic.edu.sfc.exception.NotAvailableForCustomersException;
import com.bionic.edu.sfc.service.IDeliveryService;
import com.bionic.edu.sfc.service.ITradeService;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IUserService;
import com.bionic.edu.sfc.web.JSFUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    private static final Log LOG = LogFactory.getLog(OrderBean.class);

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITradeService tradeService;

    @Autowired
    private IUserService userService;

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
        if (customerService.getCurrentCustomer() != null) {
            return "Buy!";
        }
        return "Login as Customer to buy items";
    }

    public boolean buyButtonEnabled() {
        return customerService.getCurrentCustomer() != null;
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
            orderItems.clear();
        } catch (NotActualPriceException e) {
            LOG.info("Not actual price");
            for (FishItem fishItem : e.getFishItems()) {
                FishParcel actualParcel = fishParcelService.findById(fishItem.getFishParcel().getId());
                String fishName = actualParcel.getFish().getName();
                double oldPrice = fishItem.getPrice();
                double actualPrice = actualParcel.getActualPrice();
                showMessage("Price for " + fishName + " was changed.",
                        "Old: " + oldPrice + "\nNew: " + actualPrice);
                fishItem.setFishParcel(actualParcel);
                fishItem.setPrice(actualPrice);
            }
        } catch (NotActualWeightException e) {
            for (FishItem fishItem : e.getFishItems()) {
                FishParcel actualParcel = fishParcelService.findById(fishItem.getFishParcel().getId());
                double availableWeight = actualParcel.getWeight() - actualParcel.getWeightSold();
                String fishName = fishItem.getFishParcel().getFish().getName();
                if (availableWeight <= 0) {
                    showMessage("There is no " + fishName + " on coldstore.", "It was removed from order");
                    orderItems.remove(fishItem);
                    return;
                }
                showMessage("There is not enough weight of " + fishName + " on coldstore.", "Weight was changed to max available");
                fishItem.setWeight(availableWeight);
            }
        } catch (NotAvailableForCustomersException e) {
            for (FishItem fishItem : e.getFishItems()) {
                showMessage(fishItem.getFishParcel().getFish().getName() + " is no longer available for customers.", "It was removed from order");
                orderItems.remove(fishItem);
            }
        }
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

    public void showMessage(String text) {
        showMessage(text, null);
    }

    public void showMessage(String text, String details) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text, details));
    }
}
