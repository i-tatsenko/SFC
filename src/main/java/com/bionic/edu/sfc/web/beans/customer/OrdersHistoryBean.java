package com.bionic.edu.sfc.web.beans.customer;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.service.dao.IBillService;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

/**
 * Created by docent on 05.12.14.
 */
@Named
@Scope("request")
public class OrdersHistoryBean {

    @Autowired
    private IBillService billService;

    @Autowired
    private ICustomerService customerService;

    private List<Bill> customerBills;

    private long selectedBillId;

    @PostConstruct
    public void init() {
        Customer customer = customerService.getCurrentCustomer();
        customerBills = customer.getBills();
    }

    public List<FishItem> getItemsForSelectedBill() {
        if (selectedBillId == 0) {
            return Collections.emptyList();
        }
        Bill bill = billService.findById(selectedBillId);
        return bill.getFishItems();
    }

    public void prepareShowBillDetails(long selectedBillId) {
        this.selectedBillId = selectedBillId;
    }

    public List<Bill> getCustomerBills() {
        return customerBills;
    }

    public void setCustomerBills(List<Bill> customerBills) {
        this.customerBills = customerBills;
    }

    public long getSelectedBillId() {
        return selectedBillId;
    }

    public void setSelectedBillId(long selectedBillId) {
        this.selectedBillId = selectedBillId;
    }
}
