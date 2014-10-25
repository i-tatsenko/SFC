package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.entity.*;
import com.bionic.edu.sfc.entity.builder.*;
import com.bionic.edu.sfc.service.dao.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


/**
 * Ivan
 * 2014.10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class FishItemServiceTest {

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private IFishService fishService;

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    @Autowired
    private IFishItemService fishItemService;

    @Autowired
    private ICustomerService customerService;

    @Test
    public void testGetReadyForWriteOff() throws Exception {
        Manufacturer manufacturer = ManufacturerBuilder.aManufacturer("some man").build();
        manufacturerService.create(manufacturer);

        Fish fish = FishBuilder.aFish("some fish").build();
        fishService.create(fish);

        FishShipSupply fishShipSupply = FishShipSupplyBuilder.aFishShipSupply("some code").build();
        fishShipSupplyService.create(fishShipSupply);

        FishParcel fishParcel = FishParcelBuilder.aFishParcel()
                .withFish(fish)
                .withManufacturer(manufacturer)
                .withFishShipSupply(fishShipSupply)
                .withAvailableForCustomers(true)
                .withWeight(1)
                .build();
        fishParcelService.create(fishParcel);

        Customer customer = new Customer();
        customer.setLogin("some login");
        customer.setName("some name");
        customerService.create(customer, "some password");



        FishItem fishItemOk = FishItemBuilder.aFishItem()
                                .withFishParcel(fishParcel)
                                .withWeight(12d)
                                .withForWriteOff(true)
                                .build();

        FishItem fishItemNotOk = FishItemBuilder.aFishItem()
                .withFishParcel(fishParcel)
                .withCustomer(customer)
                .withWeight(12d)
                .withCreationDate(new Date(123213232L))
                .build();

        fishItemService.create(fishItemOk);
        fishItemService.create(fishItemNotOk);

        List<FishItem> customerBucket = fishItemService.getReadyForWriteOff();
        assertThat(customerBucket).hasSize(1);
        assertThat(customerBucket.get(0)).isEqualTo(fishItemOk);
    }
}
