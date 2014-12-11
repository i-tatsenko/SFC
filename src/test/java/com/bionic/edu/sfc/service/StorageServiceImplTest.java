package com.bionic.edu.sfc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
public class StorageServiceImplTest {

    @Autowired
    private IStorageService storageService;

    @Test
    public void testGetStorageCost() throws Exception {
        Date now = new Date();
        Date yesterday = new Date(now.getTime() - 24 * 60 * 60 * 1000);
        double storageCost = storageService.getStorageCost(yesterday, now, 10);

        assertThat(storageCost).isEqualTo(100);

        storageCost = storageService.getStorageCost(now, yesterday, 10);

        assertThat(storageCost).isEqualTo(100);

    }
}