/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
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
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ri00642
 */
@ManagedBean(name = "rpurchase")
@RequestScoped
public class repPurchases implements Serializable {

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private String fcode;
    private String fname;
    private String fname2;
    private Date fdatefrom;
    private Date fdateto;
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private static AdminEJBClient AdminEJBService = null;
    private int ftype;
    private static CatalogEJBClient CatalogEJB;
    private static ParameterEJBClient ParameterEJBClient;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Autocomplete Socio" > 
    public List<String> compSocioCode(String query) {
        List _result = null;

        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(query);
        in.setCardtype("P");

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
        in.setCardtype("P");

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
        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }
        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="DOPRINT">
    /*
     * despliega pdf a pantalla
     * Rutilio
     * Abril 2015
     */
    public void doPrint() {
        this.print(0);
    }

    public void printFormat() {
        this.print(1);
    }

    public void printFormatExcel() {
        this.print(2);
    }

    public repPurchases() {
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Print">
    public void print(int _type) {

        Map<String, Object> reportParameters = new HashMap<>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        Date datefrom = this.getFdatefrom();
        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(this.getFdatefrom());
        EnterpriseTO resp = null;
        
        try {
            resp = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(repPurchases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (this.ftype == 1) {
            _reportname = "/purchase/DailyPurchase";
            _reportTitle = "Control Diario de Compras";

            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        } 
        
        if (this.ftype == 2) {
            _reportname = "/purchase/DailyPurchaseDet";
            _reportTitle = "Productos comprados por Proveedor";
            _whereclausule = " p.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        } 
        
        if (this.ftype == 3) {
            _reportname = "/purchase/DailyPurchaseProv";
            _reportTitle = " Compras por proveedor - RESUMEN";
            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        } 
        
        if (this.ftype == 4) {
            _reportname = "/purchase/DailyPurchaseProvVou";
            _reportTitle = "Facturas por proveedores";
            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }
        
        if (this.ftype == 5) {
            _reportname = "/purchase/suggestPurch";
            _whereclausule = " h.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2} and h.usersign=u.usersign";
            _whereclausuleSR = "h.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Compras Sugeridas";

            datefrom = addDays(this.getFdatefrom(), -90);
            reportParameters.put("pdocdate3m", datefrom);
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        if (this.ftype == 6) {
            _reportname = "/purchase/DaiPurchItmGrp";
            _whereclausule = " p.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _whereclausuleSR = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Productos comprados por Proveedor - Agrupados";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        if (this.ftype == 7) {
            _reportname = "/purchase/DailyPurBillDet";
            _whereclausule = " p.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _whereclausuleSR = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Informe detallado de facturas de compras";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        if (this.ftype == 8) {
            _reportname = "/purchase/PurchaseCost";
            _whereclausule = " p.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _whereclausuleSR = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Costo de Compras";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        if (this.ftype == 9) {
            _reportname = "/purchase/PurchLocForg";
            _whereclausule = "  docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _whereclausuleSR = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Compras Locales y Exteriores";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        if (this.ftype == 10) {
            _reportname = "/purchase/purchDebtCentr";
            _whereclausule = "  p.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _whereclausuleSR = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            _reportTitle = "Concentración de Deudas";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        }

        if (this.ftype == 11) {

            _reportname = "/purchase/PurchaseLedger";

            
            _reportTitle = "LIBRO DE COMPRAS"; //(ART. 141 Y 86 R.C.T.)";
            reportParameters.put("nameF1", ParameterEJBClient.getParameterbykey(22).getValue1());
            reportParameters.put("titleF1", ParameterEJBClient.getParameterbykey(22).getValue2());
        }
        
        if (this.ftype == 12) {
            _reportname = "/purchase/PurchasePending";
            _reportTitle = "Deuda por Proveedor";
            reportParameters.put("codigo",fname);
        }

        System.out.println(_whereclausule);
        System.out.println(_reportname);
        System.out.println(_reportTitle);
        System.out.println(resp.getCrintHeadr());

        reportParameters.put("corpName", resp.getCrintHeadr());
        reportParameters.put("PNRC", resp.getTaxIdNum());
        reportParameters.put("pdocdate", this.getFdatefrom());
        reportParameters.put("PDOCDATE2", this.getFdateto());
        reportParameters.put("PWHERE", _whereclausule);
        reportParameters.put("PMONTH", getNameMonth(cal1));
        reportParameters.put("PYEAR", cal1.get(Calendar.YEAR));
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

//<editor-fold defaultstate="collapsed" desc="funciones varias">
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    /*
     * Retorna el nombre del mes a partir del mes de un calendario
     * Rutilio Iraheta
     * Abril 2015
     */
    public static String getNameMonth(Calendar cal) {
        String _return = "";
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Agosto", "Octubre", "Noviembre", "Diciembre"};

        _return = months[cal.get(Calendar.MONTH)];

        return _return;
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

//<editor-fold defaultstate="collapsed" desc="G&S">
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
        repPurchases.AdminEJBService = AdminEJBService;
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

//</editor-fold>
}//cierre de clase
