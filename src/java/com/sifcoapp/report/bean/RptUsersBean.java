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
import com.sifco.bean.UserBean;
import java.util.HashMap;
import java.util.Map;
import com.sifcoapp.report.common.AbstractReportBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
 
@ManagedBean(name = "rptUsersBean")
@SessionScoped
 
public class RptUsersBean extends AbstractReportBean {
 
    private final String COMPILE_FILE_NAME = "profileuser";
    private Integer profileFilter;
    
    
    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
 
        ExternalContext ec=
        FacesContext.getCurrentInstance().getExternalContext();
        
        UserBean mb = (UserBean)ec.getSessionMap().get("user");
        
        //reportParameters.put("rtitle", "Hello JasperReports");
        reportParameters.put("PERFIL", new Integer(mb.getProfilecode()));
 
        return reportParameters;
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

    /**
     * @return the profileFilter
     */
    public Integer getProfileFilter() {
        return profileFilter;
    }

    /**
     * @param profileFilter the profileFilter to set
     */
    public void setProfileFilter(Integer profileFilter) {
        this.profileFilter = profileFilter;
    }
}
