/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AccountingEJBClient;
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
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.Digits;

/**
 *
 * @author ri00642
 */
@ManagedBean(name = "raccount")
@RequestScoped
public class RepAccount implements Serializable {

    private String fcode;
    private String fname;
    private Date fdatefrom;
    private Date fdateto;
    private Date fdateReport;
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private String itemtype;
    private String itemgroup;
    private int ftype;
    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private double stock;
    private static AdminEJBClient AdminEJBService;
    private Integer reportLevel;

    @PostConstruct
    public void initForm() {
        this.setFtype(1);
        this.setReportLevel(3);

        Calendar c1 = GregorianCalendar.getInstance();
        Date sDate = c1.getTime();
        this.setFdateto(sDate);
        this.setFdatefrom(sDate);
        this.setFdateReport(sDate);
        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1);  //January 30th 2000
        sDate = c1.getTime();

        try {
            AccountingEJBClient accEJBService = new AccountingEJBClient();
            accEJBService.Update_endTotal();
        } catch (Exception e) {
        }

    }
    /*
     * despliega pdf a pantalla
     * Rutilio
     * Abril 2015
     */

    public void doPrint() throws Exception {
        this.print(0);
    }

    public void print(int _type) throws Exception {
        //AccountingEJBClient accEJBService = new AccountingEJBClient();
        //accEJBService.Update_endTotal();

        Map<String, Object> reportParameters = new HashMap<>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        String _reportFilters = "";
        Date datefrom = this.getFdatefrom();
        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(this.getFdateReport());
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        EnterpriseTO resp = null;
        try {
            //resp = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(repPurchases.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.ftype == 1) {
            _reportname = "/account/RepBalance";
            _reportTitle = "Balance General";

            _whereclausule = " 1=1";
            reportParameters.put("PWHEREACTIVOS", " groupmask in ('1')");
            reportParameters.put("PWHEREPASIVOS", " groupmask in ('2','3')");
            reportParameters.put("PMAXLEVEL", this.getReportLevel());

            reportParameters.put("PREPORTSIGN1", " JESÚS RIVERA HERNANDEZ");
            reportParameters.put("PREPORTSIGNTITLE1", " REPRESENTANTE LEGAL");

            reportParameters.put("PREPORTSIGN2", "NOÉ ANTONIO LÓPEZ GONZÁLEZ");
            reportParameters.put("PREPORTSIGNTITLE2", "TESORERO");

            reportParameters.put("PREPORTSIGN3", "IRINEO CORTÉZ HERNÁNDEZ");
            reportParameters.put("PREPORTSIGNTITLE3", "PRESIDENTE JUNTA DE VIGILANCIA");

            reportParameters.put("PREPORTSIGN4", "NOÉ SORIANO VILLALOBOS");
            reportParameters.put("PREPORTSIGNTITLE4", "CONTADOR GENERAL");

        }

        if (this.ftype == 2) {
            _reportname = "/account/RepBalance";
            _reportTitle = "Balance de Comprobación";

            _whereclausule = " 1=1";
            reportParameters.put("PWHEREACTIVOS", " groupmask in ('1','4')");
            reportParameters.put("PWHEREPASIVOS", " groupmask in ('2','3','5')");
            reportParameters.put("PMAXLEVEL", this.getReportLevel());

            reportParameters.put("PREPORTSIGN1", " JESÚS RIVERA HERNANDEZ");
            reportParameters.put("PREPORTSIGNTITLE1", " REPRESENTANTE LEGAL");

            reportParameters.put("PREPORTSIGN2", "NOÉ ANTONIO LÓPEZ GONZÁLEZ");
            reportParameters.put("PREPORTSIGNTITLE2", "TESORERO");

            reportParameters.put("PREPORTSIGN3", "IRINEO CORTÉZ HERNÁNDEZ");
            reportParameters.put("PREPORTSIGNTITLE3", "PRESIDENTE JUNTA DE VIGILANCIA");

            reportParameters.put("PREPORTSIGN4", "NOÉ SORIANO VILLALOBOS");
            reportParameters.put("PREPORTSIGNTITLE4", "CONTADOR GENERAL");
        }

        if (this.ftype == 3) {
            _reportname = "/account/RepIncomeStatement";
            _reportTitle = "Estado de Resultados";

            _whereclausule = " 1=1";

            reportParameters.put("PCUENTASINGRESOS", " groupmask in ('5')");
            reportParameters.put("PCUENTASGASTOS", " (groupmask in ('4') or acctcode='4') and acctcode<>'4101' and fathernum<>'4101'");
            reportParameters.put("PCUENTASCOSTOS", " groupmask in ('4') and (acctcode='4101' or fathernum='4101')");
            reportParameters.put("PCUENTASCOSTOS_TOTAL", " groupmask in ('4') and (acctcode='4101' or fathernum='4101') and levels=3");

            reportParameters.put("PLEVELS", this.getReportLevel());

        }

        if (this.ftype == 4) {
            _reportname = "/account/RepDailyAccount";
            _reportTitle = "Libro Diario";

            _whereclausule = " h.transid=d.transid and c.acctcode=d.account and h.refdate>=$P{pdocdate} and h.refdate<=$P{PDOCDATE2}";

        }
        if (this.ftype == 5) {
            _reportname = "/account/RepAuxDaily";
            _reportTitle = "Diario Auxiliar";

            _whereclausule = " levels <=$P{PLEVELS}";
            reportParameters.put("PLEVELS", this.getReportLevel());

        }
        reportParameters.put("corpName", "ACOETMISAB");
        reportParameters.put("pdocdate", this.getFdatefrom());
        reportParameters.put("PDOCDATE2", this.getFdateto());
        reportParameters.put("PWHERE", _whereclausule);
        //reportParameters.put("PWHERESR", _whereclausuleSR);
        reportParameters.put("PFECHAREPORTE", "Al " + cal1.get(Calendar.DAY_OF_MONTH) + " de " + repPurchases.getNameMonth(cal1) + " " + cal1.get(Calendar.YEAR));
        reportParameters.put("reportName", _reportTitle);
        if (_type == 0) {
            this.bean = new ReportsBean();
            getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "PDF"));
        }
        if (_type == 1) {
            this.bean = new ReportsBean();
            getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "EXCEL"));
            getBean().setFileName(_reportTitle);
        }
        getBean().setParameters(reportParameters);
        getBean().setReportName(_reportname);
        getBean().execute();

    }

    public void printFormat() throws Exception {
        this.print(1);
    }

    /**
     * Creates a new instance of repsales
     */
    public RepAccount() {
    }

    /**
     * @return the fcode
     */
    public String getFcode() {
        return fcode;
    }

    /**
     * @param fcode the fcode to set
     */
    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the bean
     */
    public ReportsBean getBean() {
        return bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setBean(ReportsBean bean) {
        this.bean = bean;
    }

    /**
     * @return the fdatefrom
     */
    public Date getFdatefrom() {
        return fdatefrom;
    }

    /**
     * @param fdatefrom the fdatefrom to set
     */
    public void setFdatefrom(Date fdatefrom) {
        this.fdatefrom = fdatefrom;
    }

    /**
     * @return the fdateto
     */
    public Date getFdateto() {
        return fdateto;
    }

    /**
     * @param fdateto the fdateto to set
     */
    public void setFdateto(Date fdateto) {
        this.fdateto = fdateto;
    }

    /**
     * @return the ftype
     */
    public int getFtype() {
        return ftype;
    }

    /**
     * @param ftype the ftype to set
     */
    public void setFtype(int ftype) {
        this.ftype = ftype;
    }

    /**
     * @return the itemtype
     */
    public String getItemtype() {
        return itemtype;
    }

    /**
     * @param itemtype the itemtype to set
     */
    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    /**
     * @return the itemgroup
     */
    public String getItemgroup() {
        return itemgroup;
    }

    /**
     * @param itemgroup the itemgroup to set
     */
    public void setItemgroup(String itemgroup) {
        this.itemgroup = itemgroup;
    }

    /**
     * @return the stock
     */
    public double getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(double stock) {
        this.stock = stock;
    }

    /**
     * @return the reportLevel
     */
    public Integer getReportLevel() {
        return reportLevel;
    }

    /**
     * @param reportLevel the reportLevel to set
     */
    public void setReportLevel(Integer reportLevel) {
        this.reportLevel = reportLevel;
    }

    /**
     * @return the fdateReport
     */
    public Date getFdateReport() {
        return fdateReport;
    }

    /**
     * @param fdateReport the fdateReport to set
     */
    public void setFdateReport(Date fdateReport) {
        this.fdateReport = fdateReport;
    }

}
