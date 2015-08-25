/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.common.bean;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "commonBean")
@ApplicationScoped
public class CommonBean implements Serializable{

    /**
     * Creates a new instance of CommonBean
     */
    public CommonBean() {
    }
    
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private int minFractionDigits1 = 2;
    private int minFractionDigits2 = 4;
    private int minFractionDigits3 = 2;
    
    private String sizeFont1 = "13px";
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">

    public String getSizeFont1() {
        return sizeFont1;
    }

    public void setSizeFont1(String sizeFont1) {
        this.sizeFont1 = sizeFont1;
    }
    
    

    public int getMinFractionDigits1() {
        return minFractionDigits1;
    }

    public void setMinFractionDigits1(int minFractionDigits1) {
        this.minFractionDigits1 = minFractionDigits1;
    }

    public int getMinFractionDigits2() {
        return minFractionDigits2;
    }

    public void setMinFractionDigits2(int minFractionDigits2) {
        this.minFractionDigits2 = minFractionDigits2;
    }

    public int getMinFractionDigits3() {
        return minFractionDigits3;
    }

    public void setMinFractionDigits3(int minFractionDigits3) {
        this.minFractionDigits3 = minFractionDigits3;
    }
    
//</editor-fold>
    
}//CIERREDE CLASE
