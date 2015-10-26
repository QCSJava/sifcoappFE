/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifco.inventory.bean.GoodsReceiptBean;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesInTO;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.report.common.AbstractReportBean;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.Digits;
import org.primefaces.event.SelectEvent;

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
    private Date fdatefrom = new Date();
    private Date fdateto = new Date();
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private String itemtype;
    private String itemgroup;
    private int ftype;
    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private double stock;
    private static AdminEJBClient AdminEJBService;

    //
    private String newCod;
    private String newNomArt;
    private boolean check;
    private String almacen;

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="INIT">
    @PostConstruct
    public void initForm() {
        this.setFtype(1);
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

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

        Calendar Al = GregorianCalendar.getInstance(), Del = GregorianCalendar.getInstance();
        Al.setTime(this.getFdateto());
        Del.setTime(this.getFdatefrom());

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

            if (this.getStock() > 0.0) {
                _whereclausule += " and onhand>=" + this.getStock();

                _reportFilters += "\nPor existencia >=" + this.getStock();
                reportParameters.put("PFILTERS", _reportFilters);

            }

        }

        if (this.ftype == 2) {
            _reportname = "/inventory/InvKardex";
            _reportTitle = "KARDEX";
            
            /*if (check) {
                reportParameters.put("movimiento"," join ");
            }else
                reportParameters.put("movimiento"," left join ");
            */
            
            if (!almacen.equals("-1")) {
                reportParameters.put("WithAlm"," and t0.loccode = '"+this.almacen+"'");
                reportParameters.put("WithAlm2"," and t4.whscode = '"+this.almacen+"'");
            }else{
                reportParameters.put("WithAlm",""); //and t0.loccode = 'REP-001'");
                reportParameters.put("WithAlm2",""); //and t4.whscode = 'REM-001'");
            }
            
            if (newCod!= null && newCod.length() > 0 && check == false) {
                reportParameters.put("WithArt"," and t0.itemcode = '"+this.newCod+"'");
                reportParameters.put("WithArt2"," and t3.itemcode = '"+this.newCod+"'");
            }else{
                reportParameters.put("WithArt",""); //and t0.itemcode = 'INV0000295'");
                reportParameters.put("WithArt2",""); //and t3.itemcode = 'INV0000295'");
            }
 
            int dia1, mes1, anio1, dia2, mes2, anio2;
            String diaS1, Smes1, Sdia2, Smes2;

            dia1 = Del.get(Calendar.DAY_OF_MONTH);
            if (dia1 < 10) {
                diaS1 = "0" + dia1;
            } else {
                diaS1 = dia1 + "";
            }
            mes1 = Del.get(Calendar.MONTH);
            mes1 = mes1 + 1;
            if (mes1 < 10) {
                Smes1 = "0" + mes1;
            } else {
                Smes1 = mes1 + "";
            }
            anio1 = Del.get(Calendar.YEAR);

            dia2 = Al.get(Calendar.DAY_OF_MONTH);
            if (dia2 < 10) {
                Sdia2 = "0" + dia2;
            } else {
                Sdia2 = dia2 + "";
            }
            mes2 = Al.get(Calendar.MONTH);
            mes2 = mes2 + 1;
            if (mes2 < 10) {
                Smes2 = "0" + mes2;
            } else {
                Smes2 = mes2 + "";
            }
            anio2 = Al.get(Calendar.YEAR);

            reportParameters.put("PFECHAREPORTE", "Del " + diaS1 + "/" + Smes1 + "/" + anio1 + " Al " + Sdia2 + "/" + Smes2 + "/" + anio2);

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

        reportParameters.put("corpName", resp.getCrintHeadr());
        if (ftype == 2) {
            reportParameters.put("pdocdate", this.getFdateto());
            reportParameters.put("PDOCDATE2", this.getFdatefrom());
        } else {
            reportParameters.put("pdocdate", this.getFdatefrom());
            reportParameters.put("PDOCDATE2", this.getFdateto());
        }
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

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete" > 
    public void findArticle(SelectEvent event) {
        List articulos = new Vector();
        String var = null;

        if (event.getObject().toString() != var) {
            List _result = null;

            ArticlesInTO in = new ArticlesInTO();
            in.setItemCode(newCod);
            in.setItemName(newNomArt);

            try {
                _result = AdminEJBService.getArticles(in);

            } catch (Exception e) {
                //faceMessage(e.getMessage() + " -- " + e.getCause());
                newCod = null;
                newNomArt = null;
            }
            if (_result.isEmpty()) {
                this.newCod = null;
                this.newNomArt = null;

            } else {
                Iterator<ArticlesTO> iterator = _result.iterator();
                while (iterator.hasNext()) {
                    ArticlesTO articulo = (ArticlesTO) iterator.next();
                    articulos.add(articulo);
                }
                if (articulos.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        ArticlesTO art = (ArticlesTO) articulos.get(0);
                        newNomArt = art.getItemName();
                        newCod = art.getItemCode();
                    } catch (Exception ex) {
                        Logger.getLogger(GoodsReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (Object artt : articulos) {
                        ArticlesTO art = (ArticlesTO) artt;
                        if (newNomArt.equals(art.getItemName())) {
                            newNomArt = art.getItemName();
                            newCod = art.getItemCode();
                        }
                    }//cierre for
                }
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="btn nuevo">
    public void botonNuevo(ActionEvent actionEvent) {
        this.almacen = null;
        this.check = false;
        this.fcode = null;
        this.fname = null;
        this.itemgroup = null;
        this.itemtype = null;
        this.newCod = null;
        this.newNomArt = null;
        this.stock = 0.0;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        RepInventory.AdminEJBService = AdminEJBService;
    }

    public String getNewCod() {
        return newCod;
    }

    public void setNewCod(String newCod) {
        this.newCod = newCod;
    }

    public String getNewNomArt() {
        return newNomArt;
    }

    public void setNewNomArt(String newNomArt) {
        this.newNomArt = newNomArt;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

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
