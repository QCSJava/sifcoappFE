/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Digits;

@ManagedBean(name = "raccount")
@SessionScoped
public class RepAccount implements Serializable {

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
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
    private String rubro;
    public List<CatalogTO> lstRubros;
    private static final String CATALOGORUB = "Rubros_PC";

    //
    private AccountingEJBClient AccountingEJBClient;
    private String account = "";//codigocuenta
    private String shortname;//codigocuenta usado para el nombre pero lleva el mismo codigo

    //
    private static ParameterEJBClient ParameterEJBClient;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LOAD">
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

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }

        try {
            lstRubros = AdminEJBService.findCatalog(CATALOGORUB);
            //AccountingEJBClient accEJBService = new AccountingEJBClient();
            //accEJBService.Update_endTotal();
        } catch (Exception e) {
        }

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="PRINT">
    public void print(int _type) throws Exception {
        if ((this.ftype == 7) && (this.account == null || this.account.isEmpty() || this.account.equals(""))) {
            faceMessage("Seleccione una cuenta");
        } else {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }

            Map<String, Object> reportParameters = new HashMap<>();
            String _whereclausule = null;
            String _reportname = null;
            String _reportTitle = null;
            Date datefrom = this.getFdatefrom();
            Date dateTo = this.getFdateto();
            Calendar Al = GregorianCalendar.getInstance(), Del = GregorianCalendar.getInstance();
            Al.setTime(this.getFdateto());
            Del.setTime(this.getFdatefrom());
            String Rubro = this.getRubro();

            if (this.ftype == 1) {
                if (_type == 1) {
                    _reportname = "/account/RepBalanceEX";
                } else {
                    _reportname = "/account/RepBalance";
                }
                _reportTitle = "Balance Situacion Financiera";

                _whereclausule = " 1=1";
                reportParameters.put("PWHEREACTIVOS", " groupmask in ('1')");
                reportParameters.put("PWHEREPASIVOS", " groupmask in ('2','3')");
                reportParameters.put("PMAXLEVEL", this.getReportLevel());
                reportParameters.put("pdocdate", this.getFdateto());

                reportParameters.put("PREPORTSIGN1", ParameterEJBClient.getParameterbykey(19).getValue1());
                reportParameters.put("PREPORTSIGNTITLE1", ParameterEJBClient.getParameterbykey(19).getValue2());

                reportParameters.put("PREPORTSIGN2", ParameterEJBClient.getParameterbykey(20).getValue1());
                reportParameters.put("PREPORTSIGNTITLE2", ParameterEJBClient.getParameterbykey(20).getValue2());

                reportParameters.put("PREPORTSIGN3", ParameterEJBClient.getParameterbykey(21).getValue1());
                reportParameters.put("PREPORTSIGNTITLE3", ParameterEJBClient.getParameterbykey(21).getValue2());

                reportParameters.put("PREPORTSIGN4", ParameterEJBClient.getParameterbykey(22).getValue1());
                reportParameters.put("PREPORTSIGNTITLE4", ParameterEJBClient.getParameterbykey(22).getValue2());

            }

            if (this.ftype == 2) {
                if (_type == 1) {
                    _reportname = "/account/RepBalanceComEX";
                } else {
                    _reportname = "/account/RepBalanceCom";
                }
                _reportTitle = "Balance de Comprobación";

                _whereclausule = " 1=1";
                reportParameters.put("pdocdate", sumarFecha(this.getFdateto(), 1));
                reportParameters.put("PWHEREACTIVOS", " groupmask in ('1','4')");
                reportParameters.put("PWHEREPASIVOS", " groupmask in ('2','3','5')");
                reportParameters.put("PMAXLEVEL", this.getReportLevel());

                reportParameters.put("PREPORTSIGN1", ParameterEJBClient.getParameterbykey(19).getValue1());
                reportParameters.put("PREPORTSIGNTITLE1", ParameterEJBClient.getParameterbykey(19).getValue2());

                reportParameters.put("PREPORTSIGN2", ParameterEJBClient.getParameterbykey(20).getValue1());
                reportParameters.put("PREPORTSIGNTITLE2", ParameterEJBClient.getParameterbykey(20).getValue2());

                reportParameters.put("PREPORTSIGN3", ParameterEJBClient.getParameterbykey(21).getValue1());
                reportParameters.put("PREPORTSIGNTITLE3", ParameterEJBClient.getParameterbykey(21).getValue2());

                reportParameters.put("PREPORTSIGN4", ParameterEJBClient.getParameterbykey(22).getValue1());
                reportParameters.put("PREPORTSIGNTITLE4", ParameterEJBClient.getParameterbykey(22).getValue2());
            }

            if (this.ftype == 3) {
                _reportname = "/account/StatementCategory";
                _reportTitle = "ESTADO DE RESULTADOS POR RUBRO";

                reportParameters.put("category", RubroName(this.getRubro()));
                reportParameters.put("numCategory", this.getRubro());
                reportParameters.put("startdate", this.getFdatefrom());
                reportParameters.put("enddate", this.getFdateto());
                reportParameters.put("levels", this.getReportLevel());

            }

            if (this.ftype == 4) {
                if (_type == 1) {
                    _reportname = "/account/BDailyEX";
                } else {
                    _reportname = "/account/BDaily";
                }
                
                _reportTitle = "Libro Diario";

                //_whereclausule = " h.transid=d.transid and c.acctcode=d.account and h.refdate>=$P{pdocdate} and h.refdate<=$P{PDOCDATE2}";
                reportParameters.put("startdate", this.getFdatefrom());
                reportParameters.put("enddate", this.getFdateto());

                if (this.getAccount() == null || this.getAccount().equals("")) {
                    reportParameters.put("account", "");
                } else {
                    reportParameters.put("account", "^" + this.getAccount());
                }
            }

            /*
             if (this.ftype == 5) {
             _reportname = "/account/RepAuxDaily";
             _reportTitle = "Diario Auxiliar";

             _whereclausule = " levels <=$P{PLEVELS}";
             reportParameters.put("PLEVELS", this.getReportLevel());

             }*/
            if (this.ftype == 6) {
                if (_type == 1) {
                    _reportname = "/account/StatementReservaEX";
                } else {
                    _reportname = "/account/StatementReserva";
                }

                _reportTitle = "ESTADO DE RESULTADOS INTEGRAL";
                
                reportParameters.put("category", RubroName(this.getRubro()));
                reportParameters.put("numCategory", this.getRubro());
                reportParameters.put("startdate", this.getFdatefrom());
                reportParameters.put("enddate", this.getFdateto());
                reportParameters.put("levels", this.getReportLevel());

                //% de reservas
                reportParameters.put("R1", Integer.parseInt(ParameterEJBClient.getParameterbykey(17).getValue1()));
                reportParameters.put("R2", Integer.parseInt(ParameterEJBClient.getParameterbykey(17).getValue2()));
                reportParameters.put("R3", Integer.parseInt(ParameterEJBClient.getParameterbykey(17).getValue3()));

                //firma1
                reportParameters.put("F1_NAME", ParameterEJBClient.getParameterbykey(19).getValue1());
                reportParameters.put("F1_TITLE", ParameterEJBClient.getParameterbykey(19).getValue2());

                //firma2
                reportParameters.put("F2_NAME", ParameterEJBClient.getParameterbykey(20).getValue1());
                reportParameters.put("F2_TITLE", ParameterEJBClient.getParameterbykey(20).getValue2());

                //firma3
                reportParameters.put("F3_NAME", ParameterEJBClient.getParameterbykey(21).getValue1());
                reportParameters.put("F3_TITLE", ParameterEJBClient.getParameterbykey(21).getValue2());

                //firma4
                reportParameters.put("F4_NAME", ParameterEJBClient.getParameterbykey(22).getValue1());
                reportParameters.put("F4_TITLE", ParameterEJBClient.getParameterbykey(22).getValue2());

            }

            if (this.ftype == 7) {
                _reportname = "/account/RepAnexo";
                _reportTitle = "REPORTES ANEXOS";

                reportParameters.put("startdate", this.getFdatefrom());
                reportParameters.put("enddate", this.getFdateto());
                reportParameters.put("account", this.getAccount());

            }

            if (this.ftype == 8) {
                _reportname = "/account/CBDjournal";
                _reportTitle = "Libro Diario Consolidado";

                reportParameters.put("startdate", this.getFdatefrom());
                reportParameters.put("enddate", this.getFdateto());
                reportParameters.put("account", this.getAccount());
                reportParameters.put("level", this.getReportLevel());

            }

            if (this.ftype == 9) {
                _reportname = "/account/BDjournal";
                _reportTitle = "DIARIO MAYOR";

                reportParameters.put("startdate", this.getFdatefrom());
                reportParameters.put("enddate", this.getFdateto());
                reportParameters.put("account", this.getAccount());
            }

            reportParameters.put("reportName", _reportTitle);
            reportParameters.put("corpName", "ACOETMISAB DE R.L.");
            if (this.ftype == 1 || this.ftype == 2) {
                reportParameters.put("PWHERE", _whereclausule);
            }

            int dia1, mes1, anio1, dia2, mes2, anio2;
            String diaS1,Smes1,Sdia2,Smes2;
            
            dia1 = Del.get(Calendar.DAY_OF_MONTH);
            if (dia1<10) {
                diaS1 = "0"+dia1;
            }else
                diaS1 = dia1+"";
            mes1 = Del.get(Calendar.MONTH);
            mes1 = mes1 + 1;
            if (mes1<10) {
                Smes1 = "0"+mes1;
            }else
                Smes1 = mes1+"";
            anio1 = Del.get(Calendar.YEAR);

            dia2 = Al.get(Calendar.DAY_OF_MONTH);
             if (dia2<10) {
                Sdia2 = "0"+dia2;
            }else
                Sdia2 = dia2+"";
            mes2 = Al.get(Calendar.MONTH);
            mes2 = mes2 + 1;
            if (mes2<10) {
                Smes2 = "0"+mes2;
            }else
                Smes2 = mes2+"";
            anio2 = Al.get(Calendar.YEAR);
            
            

            if (this.ftype == 1 || this.ftype == 2) {
                try {
                    String[] mesesString = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
                    reportParameters.put("PFECHAREPORTE", "AL " + Sdia2 + " DE " + mesesString[mes2 - 1] + " DE " + anio2);
                } catch (Exception e) {
                }
            } else {
                reportParameters.put("PFECHAREPORTE", "Del " + diaS1 + "/" + Smes1 + "/" + anio1 + " Al " + Sdia2 + "/" + Smes2 + "/" + anio2);
            }

            if (_type == 0) {
                this.bean = new ReportsBean();
                getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "PDF"));
            } else {
                if (_type == 1) {
                    this.bean = new ReportsBean();
                    getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "EXCEL"));
                    getBean().setFileName(_reportTitle);
                } else {
                    if (_type == 2) {
                        getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "FILE"));
                        getBean().setFileName(_reportTitle);
                    }
                }
            }

            getBean().setParameters(reportParameters);
            getBean().setReportName(_reportname);
            getBean().execute();
        }

    }//cierre de funcion

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    
    public Date sumarFecha(Date fecha, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public void doPrint() throws Exception {
        this.print(0);
    }

    public void printFormat() throws Exception {
        this.print(1);
    }

    public void printFormatPDF() throws Exception {
        this.print(2);
    }

    public RepAccount() {
    }

    public String RubroName(String rubro){
        for (CatalogTO lstRubro : lstRubros) {
            if (lstRubro.getCatcode().equals(rubro)) {
                return lstRubro.getCatvalue();
            }
        }
        return "TODOS LOS RUBROS";
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete" > 
    public void findAccount() {
        List account2 = new Vector();
        List _result = null;
        String[] newName = null;
        String codigo = null, nombre = null;

        if (shortname != null && !shortname.equals("")) {
            newName = shortname.split("-");
            account = newName[0];
            shortname = newName[1];
        } else {
            if (account != null && !account.equals("")) {
                newName = account.split("-");
                account = newName[0];
                shortname = newName[1];
            } else {
                codigo = account;
                nombre = shortname;
            }
        }
        /*
         try {
         _result = AccountingEJBClient.getAccountByFilter(codigo, nombre);
         } catch (Exception e) {
         faceMessage(e.getMessage() + " -- " + e.getCause());
         account = null;
         shortname = null;
         }
         if (_result.isEmpty()) {
         this.account = null;
         this.shortname = null;
         } else {
         for (Object obj : _result) {
         AccountTO articulo = (AccountTO) obj;
         account2.add(articulo);
         }
         if (account2.size() == 1) {
         AccountTO art = (AccountTO) account2.get(0);
         account = art.getAcctcode();
         shortname = art.getAcctname();
         } else {
         faceMessage("Error: codigo repetido");
         }
         }*/
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocompletado">
    public List<String> completeText(String query) {
        List _result = null;
        account = null;
        shortname = null;

        String filterByCode = null;
        try {

            _result = AccountingEJBClient.getAccountByFilter(filterByCode, query, null);
        } catch (Exception ex) {
            Logger.getLogger(RepAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> results = new ArrayList<>();

        Iterator<AccountTO> iterator = _result.iterator();
        while (iterator.hasNext()) {
            AccountTO cuentas = (AccountTO) iterator.next();
            results.add(cuentas.getAcctcode() + "-" + cuentas.getAcctname());
        }
        return results;
    }

    public List<String> completeCode(String query) {
        List _result = null;
        account = null;
        shortname = null;

        String filterByName = null;
        try {
            _result = AccountingEJBClient.getAccountByFilter(query, filterByName, null);
        } catch (Exception ex) {
            Logger.getLogger(RepAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> results = new ArrayList<>();

        Iterator<AccountTO> iterator = _result.iterator();
        while (iterator.hasNext()) {
            AccountTO cuentas = (AccountTO) iterator.next();
            results.add(cuentas.getAcctcode() + "-" + cuentas.getAcctname());
        }
        return results;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public static ParameterEJBClient getParameterEJBClient() {
        return ParameterEJBClient;
    }

    public static void setParameterEJBClient(ParameterEJBClient ParameterEJBClient) {
        RepAccount.ParameterEJBClient = ParameterEJBClient;
    }

    public AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        this.AccountingEJBClient = AccountingEJBClient;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public List<CatalogTO> getLstRubros() {
        return lstRubros;
    }

    public void setLstRubros(List<CatalogTO> lstRubros) {
        this.lstRubros = lstRubros;
    }

    
    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        RepAccount.AdminEJBService = AdminEJBService;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
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
//</editor-fold>

}//cierre de clase
