package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.service.IDeliveryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by docent on 03.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
public class DeliveryServiceImplTest {

    @Autowired
    private IDeliveryService deliveryService;

    @Test
    public void testGetDeliveryCost() throws Exception {
        double deliveryCost1 = deliveryService.getDeliveryCost(50);
        assertThat(deliveryCost1).isEqualTo(1200);

        double deliverCost2 = deliveryService.getDeliveryCost(85);
        assertThat(deliverCost2).isEqualTo(1600);

        double deliverCost3 = deliveryService.getDeliveryCost(10);
        assertThat(deliverCost3).isEqualTo(400);

        double deliverCost4 = deliveryService.getDeliveryCost(170);
        assertThat(deliverCost4).isEqualTo(2000);

    }
}
