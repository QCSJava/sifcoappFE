/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prueba.model.primefaces;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author ps05393
 */
public class GaugeBean implements Serializable {

    private MeterGaugeChartModel meterGaugeModel1;
    private MeterGaugeChartModel meterGaugeModel2;
 
    @PostConstruct
    public void init() {
        createMeterGaugeModels();
    }
 
    public MeterGaugeChartModel getMeterGaugeModel1() {
        return meterGaugeModel1;
    }
     
    public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
    }
 
    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){{
            add(20);
            add(50);
            add(120);
            add(220);
        }};
         
        return new MeterGaugeChartModel(140, intervals);
    }
 
    private void createMeterGaugeModels() {
//        meterGaugeModel1 = initMeterGaugeModel();
//        meterGaugeModel1.setTitle("MeterGauge Chart");
//        meterGaugeModel1.setGaugeLabel("km/h");
//         
//        meterGaugeModel2 = initMeterGaugeModel();
//        meterGaugeModel2.setTitle("Custom Options");
//        meterGaugeModel2.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
//        meterGaugeModel2.setGaugeLabel("km/h");
//        meterGaugeModel2.setGaugeLabelPosition("bottom");
//        meterGaugeModel2.setShowTickLabels(false);
//        meterGaugeModel2.setLabelHeightAdjust(110);
//        meterGaugeModel2.setIntervalOuterRadius(100);
    }
 
}