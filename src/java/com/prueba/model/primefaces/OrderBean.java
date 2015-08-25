/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prueba.model.primefaces;
import java.io.Serializable;

/**
 *
 * @author Peter-PC
 */
public class OrderBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String item;
    private Integer qty;
    private Double price;
 
    public OrderBean(String item, Integer qty, Double price) {
        this.item = item;
        this.qty = qty;
        this.price = price;
    }   
 
    public String getItem() {
        return item;
    }
 
    public void setItem(String item) {
        this.item = item;
    }
 
    public Double getPrice() {
        return price;
    }
 
    public void setPrice(Double price) {
        this.price = price;
    }
 
    public Integer getQty() {
        return qty;
    }
 
    public void setQty(Integer qty) {
        this.qty = qty;
    }   
}