package com.bionic.edu.sfc.web.beans.customer;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.builder.FishItemBuilder;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.web.beans.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;

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
    private ICustomerService customerService;

    private List<FishItem> orderItems = new LinkedList<>();

    private long selectedParcelId;

    private double selectedWeight;

    public void addToOrder() {
        if (!fishParcelService.haveEnoughWeight(selectedParcelId, selectedWeight)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is no enough weight"));
            return;
        }
        Customer customer = customerService.
        FishParcel selectedParcel = fishParcelService.findById(selectedParcelId);
        FishItem fishItem = FishItemBuilder.aFishItem()
                .withFishParcel(selectedParcel)
                .withWeight(selectedWeight)
                .withPrice(selectedParcel.getActualPrice())
                .build();
        orderItems.add(fishItem);
    }

    public void prepareAddItemToOrder(long selectedParcelId) {
        this.selectedParcelId = selectedParcelId;
    }

    public String buyButtonText() {
        if (loginBean.isAuthenticated()) {
            return "Buy!";
        }
        return "Login to buy items";
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
