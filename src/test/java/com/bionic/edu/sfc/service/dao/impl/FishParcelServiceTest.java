package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.Manufacturer;
import com.bionic.edu.sfc.entity.builder.FishBuilder;
import com.bionic.edu.sfc.entity.builder.FishParcelBuilder;
import com.bionic.edu.sfc.entity.builder.FishShipSupplyBuilder;
import com.bionic.edu.sfc.entity.builder.ManufacturerBuilder;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import com.bionic.edu.sfc.service.dao.IFishService;
import com.bionic.edu.sfc.service.dao.IFishShipSupplyService;
import com.bionic.edu.sfc.service.dao.IManufacturerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
public class FishParcelServiceTest {

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private IFishService fishService;

    @Autowired
    private IFishShipSupplyService fishShipSupplyService;

    @Autowired
    private IFishParcelService fishParcelService;

    @Test
    public void testGetAllAvailableForSale() throws Exception {
        Manufacturer manufacturer = ManufacturerBuilder.aManufacturer("some man").build();
        manufacturerService.create(manufacturer);

        Fish fish = FishBuilder.aFish("some fish").build();
        fishService.create(fish);

        FishShipSupply fishShipSupply = FishShipSupplyBuilder.aFishShipSupply("some code").build();
        fishShipSupplyService.create(fishShipSupply);

        FishParcel fishParcelOk = FishParcelBuilder.aFishParcel()
                                .withFish(fish)
                                .withManufacturer(manufacturer)
                                .withFishShipSupply(fishShipSupply)
                                .withAvailableForCustomers(true)
                                .withWeight(1)
                                .build();

        FishParcel fishParcelNotOk1 = FishParcelBuilder.aFishParcel()
                .withFish(fish)
                .withManufacturer(manufacturer)
                .withFishShipSupply(fishShipSupply)
                .withAvailableForCustomers(false)
                .withWeight(1)
                .build();

        FishParcel fishParcelNotOk2 = FishParcelBuilder.aFishParcel()
                .withFish(fish)
                .withManufacturer(manufacturer)
                .withFishShipSupply(fishShipSupply)
                .withAvailableForCustomers(true)
                .withWeight(1)
                .withWeightSold(1)
                .build();
        fishParcelService.create(fishParcelOk);
        fishParcelService.create(fishParcelNotOk1);
        fishParcelService.create(fishParcelNotOk2);

        List<FishParcel> availableForSale  = fishParcelService.getAllAvailableForCustomers();
        assertThat(availableForSale).hasSize(1);
        assertThat(availableForSale.get(0)).isEqualTo(fishParcelOk);

    }
}
