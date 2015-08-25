/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

/**
 *
 * @author ri00642
 */
import java.util.HashMap;
import java.util.Map;
import com.sifcoapp.report.common.AbstractReportBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean(name = "reportsBean", eager = true)
@SessionScoped
 
public class ReportsBean extends AbstractReportBean {
 
    private final String COMPILE_FILE_NAME = "report1";
 
    @Override
    protected String getCompileFileName() {
        //return COMPILE_FILE_NAME;
        return this.getReportName();
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
        
        return getParameters();
    }
 
    public String execute() {
        try {
            super.prepareReport();
        } catch (Exception e) {
            // make your own exception handling
            e.printStackTrace();
        }
 
        return null;
    }
    
       
    
}
