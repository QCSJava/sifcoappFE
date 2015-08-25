/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.model.primefaces;
import com.prueba.ejb.SingletonBean;
import java.util.ArrayList;
import java.util.List;
 
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author ps05393
 */
@ManagedBean(name="manager")
public class PropertyManager {

    /**
     * Creates a new instance of PropertyManager
     */
    @EJB
    SingletonBean ejb;
   ArrayList  cacheList;
    private String key;
    private String value;
 
    public String getKey() {
        return key;
    }
 
    public void setKey(String key) {
        this.key = key;
    }
 
    public String getValue() {
        return value;
    }
 
    public void setValue(String value) {
        this.value = value;
    }
 
    public void save() {
        ejb.put(key, value);
         
    }
 
    public void clear() {
          
        cacheList.clear();
     
    }
    public List getCacheList() {
        return ejb.getCache();
    }
 
 
}