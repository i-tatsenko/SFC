package com.bionic.edu.sfc.web.beans.gm;

import com.bionic.edu.sfc.dto.IncomeDTO;
import com.bionic.edu.sfc.service.ITradeService;
import com.bionic.edu.sfc.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.chart.DonutChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by docent on 11.12.14.
 */
@Named
@Scope("request")
public class ReportBean {

    private static final Log LOG = LogFactory.getLog(ReportBean.class);

    @Autowired
    private ITradeService tradeService;

    private Date startDate = Util.localDateToDate(LocalDate.now().minus(7, ChronoUnit.DAYS));
    private Date endDate = new Date();
    private boolean forAllTime;
    private List<IncomeDTO> incomeDTOs = new LinkedList<>();
    private double totalPlus;
    private double totalMinus;
    private DonutChartModel chart;


    @PostConstruct
    public void init() {
        if (startDate == null || endDate == null || endDate.before(startDate)) {
            LOG.error("StartDate: " + startDate + " endDate: " + endDate);
            return;
        }
        if (forAllTime) {
            incomeDTOs = tradeService.getIncomeTotal();
        } else {
            incomeDTOs = tradeService.getIncomeForPeriod(startDate, endDate);
        }
        totalMinus = incomeDTOs.stream().mapToDouble(id -> id.getTotalSum() + id.getStorageCost()).sum();
        totalPlus = incomeDTOs.stream().mapToDouble(IncomeDTO::getPaidsum).sum();
        initGraphs();
    }

    private void initGraphs() {
        chart = new DonutChartModel();
        List<Map<String, Number>> fullData = new LinkedList<>();
        for (IncomeDTO incomeDTO : incomeDTOs) {
            Map<String, Number> data = new HashMap<>();
            data.put("Total sum", incomeDTO.getTotalSum());
            data.put("Paid", incomeDTO.getPaidsum());
            data.put("Storage cost", incomeDTO.getStorageCost());
            fullData.add(data);
        }
        fullData.stream().forEach(chart::addCircle);
        chart.setTitle("Money distributions");
        chart.setLegendPosition("e");
        chart.setSliceMargin(5);
        chart.setShowDataLabels(true);
        chart.setDataFormat("value");
        chart.setShadow(false);
    }

    public void updateIncome() {
        init();
    }

    public boolean isForAllTime() {
        return forAllTime;
    }

    public void setForAllTime(boolean forAllTime) {
        this.forAllTime = forAllTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<IncomeDTO> getIncomeDTOs() {
        return incomeDTOs;
    }

    public void setIncomeDTOs(List<IncomeDTO> incomeDTOs) {
        this.incomeDTOs = incomeDTOs;
    }

    public double getTotalPlus() {
        return totalPlus;
    }

    public void setTotalPlus(double totalPlus) {
        this.totalPlus = totalPlus;
    }

    public double getTotalMinus() {
        return totalMinus;
    }

    public void setTotalMinus(double totalMinus) {
        this.totalMinus = totalMinus;
    }

    public DonutChartModel getChart() {
        return chart;
    }

    public void setChart(DonutChartModel chart) {
        this.chart = chart;
    }
}
