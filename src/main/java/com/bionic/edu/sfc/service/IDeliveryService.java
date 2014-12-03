package com.bionic.edu.sfc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeSet;

/**
 * Created by docent on 03.12.14.
 */
@Service
public class IDeliveryService {

    static class WeightAndCost {

        final double weight;
        final double cost;

        public WeightAndCost(String weightAndCost) {
            String[] weightAndCostArray = weightAndCost.split(":");
            weight = Double.parseDouble(weightAndCostArray[0]);
            cost = Double.parseDouble(weightAndCostArray[1]);
        }
    }

    @Value("${delivery.weight.and.cost}")
    private String weightsAndCostsLine;

    private WeightAndCost cheapestWac;

    private TreeSet<WeightAndCost> weightAndCosts = new TreeSet<>((w1, w2) -> (int)(w2.weight - w1.weight));

    @PostConstruct
    public void init() {
        String weightAndCostArray[] = weightsAndCostsLine.split(";");
        for (String wac : weightAndCostArray) {
            WeightAndCost e = new WeightAndCost(wac);
            if (cheapestWac == null || e.cost < cheapestWac.cost) {
                cheapestWac = e;
            }
            weightAndCosts.add(e);
        }
    }

    public double getDeliveryCost(double weight) {
        int totalSum = 0;
        while (weight > 0){
            WeightAndCost properWac = null;
            for (WeightAndCost wac : weightAndCosts) {
                if (wac.weight * 0.5 <= weight) {
                    properWac = wac;
                    break;
                }
            }
            if (properWac == null) {
                properWac = cheapestWac;
            }
            weight -= properWac.weight;
            totalSum += properWac.cost;
        }
        return totalSum;
    }
}
