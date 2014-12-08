package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.Payment;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IPaymentService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by docent on 08.12.14.
 */
@Named
@Scope("request")
public class CustomersBean {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IPaymentService paymentService;

    private List<Customer> customers;

    private Collection<Payment> customerPayments = new TreeSet<>((p1, p2) -> {
        int result = p2.getCreationDate().compareTo(p1.getCreationDate());
        if (result != 0) {
            return result;
        }
        return (int) (p2.getId() - p1.getId());
    });

    @PostConstruct
    public void init() {
        customers = customerService.getAll("name");
    }

    public void updateCustomer(RowEditEvent rowEditEvent) {
        Customer customer = (Customer) rowEditEvent.getObject();
        customerService.update(customer);
    }

    public void onRowSelect(SelectEvent selectEvent) {
        customerPayments.clear();
        Customer selectedCustomer = (Customer) selectEvent.getObject();
        System.out.println(selectEvent.getObject());
        if (selectedCustomer == null) {
            return;
        }
        List<Payment> allForCustomer = paymentService.getAllForCustomer(selectedCustomer);
        System.out.println(allForCustomer);
        customerPayments.addAll(allForCustomer);
    }

    public List<Payment> getCustomerPayments() {
        return new LinkedList<>(customerPayments);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
