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
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ri00642
 */
@ManagedBean(name = "rpurchase")
@RequestScoped
public class repPurchases implements Serializable {

    private String fcode;
    private String fname;
    private Date fdatefrom;
    private Date fdateto;
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private static AdminEJBClient AdminEJBService=null;
    private int ftype;

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

    /*
     * despliega pdf a pantalla
     * Rutilio
     * Abril 2015
     */
    public void doPrint() {
        this.print(0);
    }

    public void print(int _type) {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        Date datefrom = this.getFdatefrom();
        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(this.getFdatefrom());
        EnterpriseTO resp=null;
        try {
            resp=AdminEJBService.getEnterpriseInfo();
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
        } else if (this.ftype == 2) {
            _reportname = "/purchase/DailyPurchaseDet";
            _reportTitle = "Productos comprados por Proveedor";
            _whereclausule = " p.docentry=d.docentry and docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        } else if (this.ftype == 3) {
            _reportname = "/purchase/DailyPurchaseProv";
            _reportTitle = " Compras por proveedor - RESUMEN";
            _whereclausule = " docdate>=$P{pdocdate} and docdate<=$P{PDOCDATE2}";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
        } else if (this.ftype == 4) {
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
            

            _reportname = "/purchase/purchBook";

            _whereclausule = "  extract(month from docdate)= " + (cal1.get(Calendar.MONTH) + 1) + " and extract(year from docdate)=" + cal1.get(Calendar.YEAR);

            _reportTitle = "LIBRO DE COMPRAS (ART. 141 Y 86 R.C.T.)";
            if (this.getFcode() != null && this.getFcode().length() > 0) {
                _whereclausule += " and docnum=" + this.getFcode();
            }
            if (this.getFname() != null && this.getFname().length() > 0) {
                _whereclausule += " and cardcode='" + this.getFname() + "'";
            }
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
            getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "FILE"));
        }
        if (_type == 1) {
            getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "FILE"));
            getBean().setFileName(_reportTitle);
        }
        getBean().setParameters(reportParameters);
        getBean().setReportName(_reportname);
        getBean().execute();

    }

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

    public void printFormat() {
        this.print(1);
    }

    /**
     * Creates a new instance of repsales
     */
    public repPurchases() {
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

}
