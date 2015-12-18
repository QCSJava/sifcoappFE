/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.common.bean;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "licenseBean")
@ApplicationScoped
public class LicenseBean implements Serializable{
    public LicenseBean() {
    }
    
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private boolean executed = false ;
    private boolean license=false;
    
//</editor-fold>
    
public boolean metodo(){
    if (!executed) {
        //ejecutar
        
        
    }
    return license;
}
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    
    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
    
//</editor-fold>
    
}//cierre de clase
