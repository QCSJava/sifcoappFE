/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.model.primefaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author ps05393
 */
@ManagedBean
@ApplicationScoped
public class CounterBean {

    /**
     * Creates a new instance of CounterBean
     */
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
//getter setter
public void increment() {
count++;   
}
    
}
