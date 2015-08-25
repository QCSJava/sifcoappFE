/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.to.clases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author ps05393
 */
public class Periodo implements Serializable {
    private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private String country;
    private String city; 
    private Map<String,String> countries;
    private Map<String,String> cities;
    
    
    private String nombrePeriodo;
    private String subPeriodo;
    private String cantidadPeriodo;
    private String indicadorPeriodo;
    private String statusPeriodo;
    private String fechaConta;
    private String fechaVencimiento;
    private String fechaDocumento;
    private String inicioEjercicio;
    private String ejercicio;
    private String codigoPeriodo;
    private boolean canEdit;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public  Periodo(String nombrePeriodo, String subPeriodo, String cantidadPeriodo,String indicadorPeriodo, String statusPeriodo,String fechaConta,String fechaVencimiento,String fechaDocumento,String inicioEjercicio,String ejercicio, String codigoPeriodo){
       this.nombrePeriodo=nombrePeriodo;
       this.subPeriodo=subPeriodo;
       this.cantidadPeriodo=cantidadPeriodo;
       this.indicadorPeriodo=indicadorPeriodo;
       this.statusPeriodo=statusPeriodo;
       this.fechaConta=fechaConta;
       this.fechaVencimiento=fechaVencimiento;
       this.fechaDocumento=fechaDocumento;
       this.inicioEjercicio=inicioEjercicio;
       this.ejercicio=ejercicio;
       this.codigoPeriodo=codigoPeriodo;
       canEdit = false;
    }

//    public Periodo(String string) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    
//    
//    
//    @PostConstruct
//    public void init() {
//        countries  = new HashMap<String, String>();
//        countries.put("USA", "USA");
//        countries.put("Germany", "Germany");
//        countries.put("Brazil", "Brazil");
//         
//        Map<String,String> map = new HashMap<String, String>();
//        map.put("New York", "New York");
//        map.put("San Francisco", "San Francisco");
//        map.put("Denver", "Denver");
//        data.put("USA", map);
//         
//        map = new HashMap<String, String>();
//        map.put("Berlin", "Berlin");
//        map.put("Munich", "Munich");
//        map.put("Frankfurt", "Frankfurt");
//        data.put("Germany", map);
//         
//        map = new HashMap<String, String>();
//        map.put("Sao Paolo", "Sao Paolo");
//        map.put("Rio de Janerio", "Rio de Janerio");
//        map.put("Salvador", "Salvador");
//        data.put("Brazil", map);
//    }
    
    
    public String getCodigoPeriodo() {
        return codigoPeriodo;
    }

    public void setCodigoPeriodo(String codigoPeriodo) {
        this.codigoPeriodo = codigoPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public String getSubPeriodo() {
        return subPeriodo;
    }

    public void setSubPeriodo(String subPeriodo) {
        this.subPeriodo = subPeriodo;
    }

    public String getCantidadPeriodo() {
        return cantidadPeriodo;
    }

    public void setCantidadPeriodo(String cantidadPeriodo) {
        this.cantidadPeriodo = cantidadPeriodo;
    }

    public String getIndicadorPeriodo() {
        return indicadorPeriodo;
    }

    public void setIndicadorPeriodo(String indicadorPeriodo) {
        this.indicadorPeriodo = indicadorPeriodo;
    }

    public String getStatusPeriodo() {
        return statusPeriodo;
    }

    public void setStatusPeriodo(String statusPeriodo) {
        this.statusPeriodo = statusPeriodo;
    }

    public String getFechaConta() {
        return fechaConta;
    }

    public void setFechaConta(String fechaConta) {
        this.fechaConta = fechaConta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getInicioEjercicio() {
        return inicioEjercicio;
    }

    public void setInicioEjercicio(String inicioEjercicio) {
        this.inicioEjercicio = inicioEjercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }
    
    public Map<String, Map<String, String>> getData() {
        return data;
    }
 
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
 
    public String getCity() {
        return city;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
 
    public Map<String, String> getCountries() {
        return countries;
    }
 
    public Map<String, String> getCities() {
        return cities;
    }
 
    public void onCountryChange() {
        if(country !=null && !country.equals(""))
            cities = data.get(country);
        else
            cities = new HashMap<String, String>();
    }
     
    public void displayLocation() {
        FacesMessage msg;
        if(city != null && country != null)
            msg = new FacesMessage("Selected", city + " of " + country);
        else
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");
             
        FacesContext.getCurrentInstance().addMessage(null, msg); 
    }
    
    
            
            
}
