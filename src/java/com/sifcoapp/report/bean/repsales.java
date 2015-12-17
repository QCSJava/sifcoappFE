/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifco.inventory.bean.GoodsReceiptBean;
import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesInTO;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.report.common.AbstractReportBean;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "rsales")
@SessionScoped
public class repsales implements Serializable {

//<editor-fold defaultstate="collapsed" desc="VAR">
    private String fcode;
    private String fname;
    private String fname2;
    private Date fdatefrom;
    private Date fdateto;
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private static AdminEJBClient AdminEJBService = null;
    private static CatalogEJBClient CatalogEJB;
    private int ftype;
    ParameterEJBClient ParameterEJBClient;
    String newCod = "";
    String newNomArt = "";
    private String user="";
    HttpSession session = Util.getSession();

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="LOAD">
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
        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }
        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="PRINTERS">
    public void printFormatExcel() {
        this.print(2);
    }

    public repsales() {
    }

    public void printFormat() {
        this.print(1);
    }

    public void doPrint() {
        this.print(0);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="PRINT">

    public void print(int _type) {
        Map<String, Object> reportParameters = new HashMap<>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        EnterpriseTO resp = null;
        try {
            resp = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(repPurchases.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.ftype == 1 || this.ftype == 8) {
            if (this.ftype == 1) {
                _reportname = "/sales/dailySalesControl";
            } else {
                _reportname = "/sales/dailySalesControlCost";
            }

            _reportTitle = "Control Diario de Ventas";
            reportParameters.put("userName", "-1");
            
            _whereclausule = " h.docdate>=$P{pdocdate} and h.docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
            //d.itemcode = '001'
            if (this.getNewCod() != null && this.getNewCod().length() > 0) {
                _whereclausule += " and d.itemcode like '" + this.newCod + "'";
            }
            //t2.nickname = 'Hugo'
            if (this.getUser() != null && this.getUser().length()>0) {
                _whereclausule += " and t2.nickname = '"+this.user+"'";
                reportParameters.put("userName", "1");
            }
        } else if (this.ftype == 2) {
            _reportname = "/sales/dailySales";
            _reportTitle = "Detalle de Ventas Diarias";
            _whereclausule = "docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
            if (this.getNewCod() != null && this.getNewCod().length() > 0) {
                _whereclausule += " and d.itemcode = '" + this.newCod + "'";
            }
        } else if (this.ftype == 3) {
            _reportname = "/sales/dailySalesCust";
            _reportTitle = " Ventas por Cliente";
            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        } else if (this.ftype == 4) {
            _reportname = "/sales/dailySalesRevert";
            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2} and docstatus='A'";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }

        }
        if (this.ftype == 5) {
            _reportname = "/sales/SalesBySeller";
            _whereclausule = " h.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2} and h.usersign=u.usersign";
            _whereclausuleSR = " h.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Ventas por Vendedor - Detallado";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
            if (this.getNewCod() != null && this.getNewCod().length() > 0) {
                _whereclausule += " and d.itemcode = '" + this.newCod + "'";
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

        if (this.ftype == 7) {
            _reportname = "/sales/Deliverysales";
            _reportTitle = "Reporte Notas de Remisión";

            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
        }

        if (this.ftype == 9 || this.ftype == 10) {
            if (this.ftype == 9) {
                _reportname = "/sales/FuelIncome";
                _reportTitle = "INGRESO POR VENTA DE COMBUSTIBLE";
            } else {
                _reportname = "/sales/SalesRevenue";
                _reportTitle = "INGRESO POR VENTA DE REPUESTOS";
            }

            Calendar fecha = Calendar.getInstance();
            fecha.setTime(this.getFdatefrom());
            int year = fecha.get(Calendar.YEAR);
            //codigo diesel y year 
            reportParameters.put("codDiesel", ParameterEJBClient.getParameterbykey(3).getValue1());
            reportParameters.put("year", year);

        }

        if (this.ftype == 11 || this.ftype == 12) {
            if (this.ftype == 11) {
                _reportname = "/sales/ConsumerBook";
                _reportTitle = "LIBRO DE VENTAS AL CONSUMIDOR";
            } else {
                _reportname = "/sales/BookSales";
                _reportTitle = "LIBRO DE VENTAS A CONTRIBUYENTES";
            }

            reportParameters.put("nameF1", ParameterEJBClient.getParameterbykey(22).getValue1());
            reportParameters.put("titleF1", ParameterEJBClient.getParameterbykey(22).getValue2());

        }

        System.out.println(_whereclausule);
        System.out.println(_reportname);
        System.out.println(_reportTitle);
        System.out.println(resp.getCrintHeadr());
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

        getBean().setParameters(reportParameters);
        getBean().setReportName(_reportname);
        getBean().execute();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocomplete Socio" > 
    public List<String> compSocioCode(String query) {
        List _result = null;

        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(query);
        in.setCardtype("C");

        try {
            _result = CatalogEJB.get_businesspartner(in);

        } catch (Exception e) {
            //faceMessage("Error en autocompletado");
        }

        List<String> results = new ArrayList<>();

        Iterator<BusinesspartnerTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            BusinesspartnerTO articulo = (BusinesspartnerTO) iterator.next();
            results.add(articulo.getCardcode());
        }
        return results;
    }

    public List<String> compSocioName(String query) {
        List _result = null;

        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardname(query);
        in.setCardtype("C");

        try {
            _result = CatalogEJB.get_businesspartner(in);

        } catch (Exception e) {
            //faceMessage("Error en autocompletado");
        }

        List<String> results = new ArrayList<>();

        Iterator<BusinesspartnerTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            BusinesspartnerTO articulo = (BusinesspartnerTO) iterator.next();
            results.add(articulo.getCardcode() + "-" + articulo.getCardname());
        }
        return results;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Seleccionar de autocomplete de Socio, Name o Cod">
    public void selectSocioName() {
        String[] newName = fname2.split("-");
        selectSocioCod(newName[0]);
    }

    public void selectSocioCod(String code) {
        List socio = new Vector();
        List _result = null;
        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(fname == null ? code : fname);

        try {
            _result = CatalogEJB.get_businesspartner(in);

        } catch (Exception e) {
            //faceMessage(e.getMessage() + " -- " + e.getCause());
            fname = null;
            fname2 = null;
        }
        if (_result.isEmpty()) {
            fname = null;
            fname2 = null;
        } else {
            for (Object obj : _result) {
                BusinesspartnerTO articulo = (BusinesspartnerTO) obj;
                socio.add(articulo);
            }
            if (socio.size() == 1) {
                try {
                    System.out.println("articulo unico, llenar campos en pantalla");
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                    fname = art.getCardcode();
                    fname2 = art.getCardname();

                } catch (Exception ex) {
                    Logger.getLogger(repsales.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getFname2() {
        return fname2;
    }

    public void setFname2(String fname2) {
        this.fname2 = fname2;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        repsales.AdminEJBService = AdminEJBService;
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

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        repsales.CatalogEJB = CatalogEJB;
    }

    public ParameterEJBClient getParameterEJBClient() {
        return ParameterEJBClient;
    }

    public void setParameterEJBClient(ParameterEJBClient ParameterEJBClient) {
        this.ParameterEJBClient = ParameterEJBClient;
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

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Autocompletado" > 
    public List<String> completeText(String query) {
        List _result = null;
        String var = null;

        ArticlesInTO in = new ArticlesInTO();
        in.setItemCode(var);
        in.setItemName(query);

        try {
            _result = AdminEJBService.getArticles(in);

        } catch (Exception e) {
        }

        List<String> results = new ArrayList<>();

        Iterator<ArticlesTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            ArticlesTO articulo = (ArticlesTO) iterator.next();
            results.add(articulo.getItemName());
        }
        return results;
    }

    public List<String> completeCode(String query) {
        List _result = null;
        String var = null;

        ArticlesInTO in = new ArticlesInTO();
        in.setItemCode(query);
        in.setItemName(var);

        try {
            _result = AdminEJBService.getArticles(in);

        } catch (Exception e) {
        }

        List<String> results = new ArrayList<>();

        Iterator<ArticlesTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            ArticlesTO articulo = (ArticlesTO) iterator.next();
            results.add(articulo.getItemCode());
        }
        return results;
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

}//cierre de clase
