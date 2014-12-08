package com.bionic.edu.sfc.service;

import com.bionic.edu.sfc.entity.*;
import com.bionic.edu.sfc.entity.builder.BillBuilder;
import com.bionic.edu.sfc.entity.builder.UserBuilder;
import com.bionic.edu.sfc.exception.NotActualPriceException;
import com.bionic.edu.sfc.exception.NotActualWeightException;
import com.bionic.edu.sfc.exception.NotAvailableForCustomersException;
import com.bionic.edu.sfc.service.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by docent on 22.10.14.
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class TradeServiceImpl implements ITradeService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IFishItemService fishItemService;

    @Autowired
    private IBillService billService;

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IPaymentService paymentService;

    @PostConstruct
    public void init() {
        if (userService.findUserByLogin("docent") == null) {
            createDocentAccount();
        }
    }

    public void createDocentAccount() {
        User user = UserBuilder.anUser("docent")
                .withUserRole(UserRole.ROLE_SECURITY_OFFICER)
                .withName("Ivan")
                .build();
        user.setActive(true);
        userService.create(user, "docent");
         org.apache.logging.log4j.LogManager.getLogger().info("Security officer acc has been created");
    }

    @Override
    public void registerNewBill(Collection<FishItem> fishItems) throws NotActualPriceException, NotActualWeightException, NotAvailableForCustomersException {
        Collection<FishItem> notActualItems = getNotAvailableForCustomerItems(fishItems);
        if (notActualItems != null && notActualItems.size() > 0) {
            throw new NotAvailableForCustomersException(notActualItems);
        }

        notActualItems = getFishItemsWithoutActualPrice(fishItems);
        if (notActualItems != null && notActualItems.size() > 0) {
            throw new NotActualPriceException(notActualItems);
        }
        notActualItems = getFishItemsWithoutActualWeight(fishItems);
        if (notActualItems != null && notActualItems.size() > 0) {
            throw new NotActualWeightException(fishItems);
        }
        Customer customer = fishItems.stream().findAny().get().getCustomer();
        double sum = fishItems.stream().mapToDouble(fi -> fi.getPrice() * fi.getWeight()).sum();
        double deliveryCost = deliveryService.getDeliveryCost(fishItems.stream().mapToDouble(FishItem::getWeight).sum());
        Bill bill = BillBuilder.aBill(customer,
                sum + deliveryCost).build();

        billService.create(bill);
        fishItems.forEach(fi -> fi.setBill(bill));
        fishItems.forEach(fishItemService::create);
        fishItems.forEach(fi -> {
            double weight = fi.getWeight();
            FishParcel fishParcel = fishParcelService.findById(fi.getFishParcel().getId());
            fishParcel.setWeightSold(fishParcel.getWeightSold() + weight);
            fishParcelService.update(fishParcel);
        });
    }

    private Collection<FishItem> getNotAvailableForCustomerItems(Collection<FishItem> fishItems) {
        Collection<FishItem> result = new LinkedList<>();
        for (FishItem fishItem : fishItems) {
            FishParcel actualParcel = fishParcelService.findById(fishItem.getFishParcel().getId());
            if (!actualParcel.isAvailableForCustomers()) {
                result.add(fishItem);
            }
        }
        return result;
    }

    @Override
    public Payment registerNewPayment(long billId, double sum) {
        Bill bill = billService.findById(billId);
        if (bill == null) {
            throw new IllegalArgumentException("No bill with id " + billId);
        }
        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setCreationDate(new Date());
        payment.setTotalSum(sum);

        paymentService.create(payment);

        bill.setAlreadyPaid(bill.getAlreadyPaid() + sum);
        if (bill.getAlreadyPaid() >= bill.getTotalSum()) {
            bill.setCloseDate(new Date());
        }
        billService.update(bill);
        return payment;
    }

    private Collection<FishItem> getFishItemsWithoutActualPrice(Collection<FishItem> fishItems) {
        Collection<FishItem> notActualPriceItems = new LinkedList<>();
        for (FishItem fishItem : fishItems) {
            FishParcel actualParcel = fishParcelService.findById(fishItem.getFishParcel().getId());
            if (Double.compare(actualParcel.getActualPrice(), fishItem.getPrice()) != 0) {
                notActualPriceItems.add(fishItem);
            }
        }
        return notActualPriceItems;
    }

    private Collection<FishItem> getFishItemsWithoutActualWeight(Collection<FishItem> fishItems) {
        return fishItems.stream().filter(fi -> !fishParcelService.hasEnoughWeight(fi.getFishParcel().getId(), fi.getWeight())).collect(Collectors.toList());
    }
}
