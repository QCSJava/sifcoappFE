/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.report.common.AbstractReportBean;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
@ManagedBean(name = "rinventory")
@RequestScoped
public class RepInventory implements Serializable {

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private String fcode;
    private String fname;
    private Date fdatefrom;
    private Date fdateto;
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private String itemtype;
    private String itemgroup;
    private int ftype;
    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private double stock;
    private static AdminEJBClient AdminEJBService;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="INIT">
    @PostConstruct
    public void initForm() {
        this.setFtype(1);

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="PRINT">
    public void print(int _type) throws Exception {
        
        Map<String, Object> reportParameters = new HashMap<>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        String _reportFilters = "";
        
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        EnterpriseTO resp = null;
        try {
            resp = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(repPurchases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (this.ftype == 1) {
            _reportname = "/inventory/InvCostStock";
            _reportTitle = "Existencias y Costos";

            _whereclausule = " 1=1";
            if (this.getItemgroup() != null && !this.getItemgroup().equals("-1") && this.getItemgroup().length() > 0) {
                _whereclausule += " and itmsgrpcod='" + this.getItemgroup() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemgroup(), 3);

                _reportFilters = "Grupo: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
            if (this.getItemtype() != null && !this.getItemtype().equals("-1") && this.getItemtype().length() > 0) {
                _whereclausule += " and itemtype='" + this.getItemtype() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemtype(), 2);

                _reportFilters += "\nClase: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }

            if (this.getStock() > 0) {
                _whereclausule += " and onhand>=" + this.getStock();

                _reportFilters += "\nPor existencia >=" + this.getStock();
                reportParameters.put("PFILTERS", _reportFilters);

            }

        }
        
        if (this.ftype == 2) {
            _reportname = "/inventory/InvPhysical";
            _reportTitle = "Inventario Físico";

            _whereclausule = " art.itemcode=psl.itemcode and psl.pricelist=1";
            if (this.getItemgroup() != null && !this.getItemgroup().equals("-1") && this.getItemgroup().length() > 0) {
                _whereclausule += " and itmsgrpcod='" + this.getItemgroup() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemgroup(), 3);

                _reportFilters = "Grupo: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
            if (this.getItemtype() != null && !this.getItemtype().equals("-1") && this.getItemtype().length() > 0) {
                _whereclausule += " and itemtype='" + this.getItemtype() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemtype(), 2);

                _reportFilters += "\nClase: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
        }
        
        if (this.ftype == 3) {
            _reportname = "/inventory/InvPhysical";
            _reportTitle = "Inventario Físico";

            _whereclausule = " art.itemcode=psl.itemcode and psl.pricelist=1";
            if (this.getItemgroup() != null && !this.getItemgroup().equals("-1") && this.getItemgroup().length() > 0) {
                _whereclausule += " and itmsgrpcod='" + this.getItemgroup() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemgroup(), 3);

                _reportFilters = "Grupo: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
            if (this.getItemtype() != null && !this.getItemtype().equals("-1") && this.getItemtype().length() > 0) {
                _whereclausule += " and itemtype='" + this.getItemtype() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemtype(), 2);

                _reportFilters += "\nClase: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
        } 
        
        if (this.ftype == 4) {
            _reportname = "/inventory/InvBarCode";
            _reportTitle = "Barcode";

            _whereclausule = " art.itemcode=psl.itemcode and psl.pricelist=1";
            if (this.getItemgroup() != null && !this.getItemgroup().equals("-1") && this.getItemgroup().length() > 0) {
                _whereclausule += " and itmsgrpcod='" + this.getItemgroup() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemgroup(), 3);

                _reportFilters = "Grupo: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
            if (this.getItemtype() != null && !this.getItemtype().equals("-1") && this.getItemtype().length() > 0) {
                _whereclausule += " and itemtype='" + this.getItemtype() + "'";

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(this.getItemtype(), 2);

                _reportFilters += "\nClase: " + cat1.getCatvalue();
                reportParameters.put("PFILTERS", _reportFilters);

            }
        }
        
        if (this.ftype == 5) {
            _reportname = "/sales/SalesBySeller";
            _whereclausule = " h.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2} and h.usersign=u.usersign";
            _whereclausuleSR = "h.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Ventas por Vendedor - Detallado";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }
        
        if (this.ftype == 6) {
            _reportname = "/sales/SalesBySellRes";
            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2} and h.usersign=u.usersign";
            _whereclausuleSR = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Ventas por Vendedor - Resumido";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        reportParameters.put("corpName", resp.getCrintHeadr());
        reportParameters.put("pdocdate", this.getFdatefrom());
        reportParameters.put("PDOCDATE2", this.getFdateto());
        reportParameters.put("PWHERE", _whereclausule);
        reportParameters.put("PWHERESR", _whereclausuleSR);
        reportParameters.put("reportName", _reportTitle);
        
        if (_type == 0) {
            getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "PDF"));
        } else {
            if (_type == 1) {
                getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "FILE"));
                getBean().setFileName(_reportTitle);
            } else {
                if (_type == 2) {
                    this.bean = new ReportsBean();
                    getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "EXCEL"));
                    getBean().setFileName(_reportTitle);
                }
            }
        }
        
        System.out.println(_whereclausule);
        System.out.println(_reportname);
        System.out.println(_reportTitle);
        System.out.println(resp.getCrintHeadr());

        getBean().setParameters(reportParameters);
        getBean().setReportName(_reportname);
        getBean().execute();

    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="funciones varias">
    public void doPrint() throws Exception {
        this.print(0);
    }
    
    public void printFormat() throws Exception {
        this.print(1);
    }
    
    public void printFormatExcel() throws Exception {
        this.print(2);
    }

    public RepInventory() {
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    

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
//</editor-fold>

}//cierre de clase
