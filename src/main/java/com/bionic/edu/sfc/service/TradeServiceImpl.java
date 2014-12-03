package com.bionic.edu.sfc.service;

import com.bionic.edu.sfc.entity.*;
import com.bionic.edu.sfc.entity.builder.BillBuilder;
import com.bionic.edu.sfc.entity.builder.UserBuilder;
import com.bionic.edu.sfc.exception.NotActualPriceException;
import com.bionic.edu.sfc.exception.NotActualWeightException;
import com.bionic.edu.sfc.service.dao.IBillService;
import com.bionic.edu.sfc.service.dao.IFishItemService;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
    public void registerNewBill(Collection<FishItem> fishItems) throws NotActualPriceException, NotActualWeightException {
        Collection<FishItem> notActual = getFishItemsWithoutActualPrice(fishItems);
        if (notActual != null && notActual.size() > 0) {
            throw new NotActualPriceException(notActual);
        }
        notActual = getFishItemsWithoutActualWeight(fishItems);
        if (notActual != null && notActual.size() > 0) {
            throw new NotActualWeightException(fishItems);
        }
        Customer customer = fishItems.stream().findAny().get().getCustomer();
        double sum = fishItems.stream().mapToDouble(FishItem::getPrice).sum();
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
