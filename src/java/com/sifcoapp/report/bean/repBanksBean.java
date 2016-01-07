/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.report.common.AbstractReportBean;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "repBanksBean")
@SessionScoped
public class repBanksBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private static AdminEJBClient AdminEJBService = null;

    private Date fdatefrom;
    private Date fdateto;
    private String user;
    private int ftype;
    
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="INIT">
    @PostConstruct
    public void initForm() {
        this.setFtype(1);
        Calendar c1 = GregorianCalendar.getInstance();

        Date sDate = c1.getTime();
        this.setFdateto(sDate);
        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1);  //January 30th 2000
        sDate = c1.getTime();
        this.setFdatefrom(sDate);

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="funciones varias">
    public void printFormat() {
        this.print(1);
    }

    public void doPrint() {
        this.print(0);
    }

    public void printFormatExcel() {
        this.print(2);
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="PRINT">
    public void print(int _type) {
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        Map<String, Object> reportParameters = new HashMap<>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        EnterpriseTO resp = null;
        this.bean = new ReportsBean();
        
        try {
            resp = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(repPurchases.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        if (this.ftype == 1) {
            _reportname = "/bank/RevenueControl";
            _reportTitle = "CONTROL DE INGRESOS";
            
            reportParameters.put("user", this.user);
        }
        
        if (this.ftype == 2) {
            _reportname = "/bank/Colecturia";
            _reportTitle = "Reporte consolidado de colecturia";
            
            //and  t3.nickname = $P{user} userNick
            
            reportParameters.put("user", "");
            if (this.user != null && this.user.length()>0) {
                reportParameters.put("user", " and t3.nickname = "+this.user);
                reportParameters.put("userNick", this.user);
            }
        }
        
        if (this.ftype == 3) {
            _reportname = "/bank/DetailColecturia";
            _reportTitle = "Reporte detallado de colecturia";
            
            reportParameters.put("user", this.user);
        }

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        reportParameters.put("corpName", resp.getCrintHeadr());
        reportParameters.put("pdocdate", this.getFdatefrom());
        reportParameters.put("PDOCDATE2", this.getFdateto());
        reportParameters.put("reportName", _reportTitle);

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        if (_type == 0) {
            getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "PDF"));
        } else {
            if (_type == 1) {
                getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "FILE"));
                getBean().setFileName(_reportTitle);
            } else {
                if (_type == 2) {
                    getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "EXCEL"));
                    getBean().setFileName(_reportTitle);
                }
            }
        }

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        getBean().setParameters(reportParameters);
        getBean().setReportName(_reportname);
        getBean().execute();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public int getFtype() {
        return ftype;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getFdatefrom() {
        return fdatefrom;
    }

    public void setFdatefrom(Date fdatefrom) {
        this.fdatefrom = fdatefrom;
    }

    public Date getFdateto() {
        return fdateto;
    }

    public void setFdateto(Date fdateto) {
        this.fdateto = fdateto;
    }

    public ReportsBean getBean() {
        return bean;
    }

    public void setBean(ReportsBean bean) {
        this.bean = bean;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="funciones varias">
    public repBanksBean() {
    }
//</editor-fold>

}//cierre de clase
