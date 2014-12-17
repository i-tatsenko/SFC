package com.bionic.edu.sfc.controller;

import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.service.dao.IFishParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by docent on 17.12.14.
 */
@Controller
@RequestMapping("/")
public class RestController {

    @Autowired
    private IFishParcelService fishParcelService;

    @RequestMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAvailableForCusotomersParcels() {
        List<FishParcel> allAvailableForCustomers = fishParcelService.getAllAvailableForCustomers();

        List<Map<String, Object>> result = new LinkedList<>();
        for (FishParcel fishParcel : allAvailableForCustomers) {
            Map<String, Object> data = new HashMap<>();
            data.put("Fish", fishParcel.getFish().getName());
            data.put("Manufacturer", fishParcel.getManufacturer().getName());
            data.put("Description", fishParcel.getDescription());
            data.put("Tonns available", fishParcel.getWeight() - fishParcel.getWeightSold());
            data.put("Price", fishParcel.getActualPrice());
            result.add(data);
        }
        return ResponseEntity.ok(result);
    }

}
