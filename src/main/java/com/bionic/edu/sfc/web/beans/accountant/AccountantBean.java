package com.bionic.edu.sfc.web.beans.accountant;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.Payment;
import com.bionic.edu.sfc.service.ITradeService;
import com.bionic.edu.sfc.service.dao.IBillService;
import com.bionic.edu.sfc.service.dao.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by docent on 04.12.14.
 */
@Named("accBean")
@Scope("request")
public class AccountantBean {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IBillService billService;

    @Autowired
    private ITradeService tradeService;

    private List<Payment> allPayments = new LinkedList<>();

    private List<Bill> allOpenBills = new LinkedList<>();

    private List<Bill> allBillsReadyForShipment = new LinkedList<>();

    private long billIdForNewPayment;

    private double sumForNewPayment;

    @PostConstruct
    public void init() {
        allPayments = paymentService.getAll("creationDate")
                .stream()
                .sorted((p1, p2) ->
                        p2.getCreationDate().compareTo(p1.getCreationDate()))
                .collect(Collectors.toList());
        allOpenBills = billService.getAllOpenBills();
        allBillsReadyForShipment = billService.getAllReadyForShipment();
    }

    public void registerPayment() {
        Payment newPayment = tradeService.registerNewPayment(billIdForNewPayment, sumForNewPayment);
        allPayments.add(0, newPayment);
//        Bill bill = allOpenBills.stream().filter(b -> b.getId() == newPayment.getBill().getId()).findAny().get();
//        bill.setAlreadyPaid(bill.getAlreadyPaid() + newPayment.getTotalSum());
    }

    public boolean canAllowShipment(Bill bill) {
        return billService.canAllowShipment(bill);
    }

    public void prepareNewPayment(long billIdForNewPayment) {
        this.billIdForNewPayment = billIdForNewPayment;
    }

    public void shipmentAllowed(long billId) {
        Bill bill = billService.findById(billId);
        bill.setShipmentAllowed(true);
        billService.update(bill);
        int pos = allOpenBills.indexOf(bill);
        allOpenBills.remove(bill);
        allOpenBills.add(pos, bill);
    }

    public String shipmentBtnText(Bill bill) {
        if (!bill.isShipmentAllowed()) {
            return "Ship";
        }
        return "ShipmentAllowed";
    }

    public List<Payment> getAllPayments() {
        return allPayments;
    }

    public void setAllPayments(List<Payment> allPayments) {
        this.allPayments = allPayments;
    }

    public List<Bill> getAllOpenBills() {
        return allOpenBills;
    }

    public void setAllOpenBills(List<Bill> allOpenBills) {
        this.allOpenBills = allOpenBills;
    }

    public List<Bill> getAllBillsReadyForShipment() {
        return allBillsReadyForShipment;
    }

    public void setAllBillsReadyForShipment(List<Bill> allBillsReadyForShipment) {
        this.allBillsReadyForShipment = allBillsReadyForShipment;
    }

    public long getBillIdForNewPayment() {
        return billIdForNewPayment;
    }

    public void setBillIdForNewPayment(long billIdForNewPayment) {
        this.billIdForNewPayment = billIdForNewPayment;
    }

    public double getSumForNewPayment() {
        return sumForNewPayment;
    }

    public void setSumForNewPayment(double sumForNewPayment) {
        this.sumForNewPayment = sumForNewPayment;
    }
}
