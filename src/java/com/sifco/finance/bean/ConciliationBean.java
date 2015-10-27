/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.finance.bean;

import com.sifco.businesspartner.bean.BusinessPartner;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.BankreconciliationEJBClient;
import com.sifcoapp.objects.ExternalReconciliation.to.ExternalReconciliationTO;
import com.sifcoapp.objects.ExternalReconciliation.to.bankreconciliationauxTO;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.accounting.to.EntryTO;
import com.sifcoapp.objects.accounting.to.JournalEntryLinesTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.report.bean.ReportsBean;
import com.sifcoapp.report.bean.repPurchases;
import com.sifcoapp.report.common.AbstractReportBean;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@ManagedBean(name = "conciliationBean")
@SessionScoped
public class ConciliationBean implements Serializable {

    public ConciliationBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    //servicios
    private AccountingEJBClient AccountingEJBClient;
    private BankreconciliationEJBClient BankreconciliationEJBClient;

    //encabezado
    private Date fechaCuentas = new Date();
    private String newCodCuenta = "";
    private String newNomCuenta;
    private Double saldoFinal;
    private String saldoActual;
    private Double saldoActualR;

    //Notas de credito y debito
    private String identificador;
    private String concepto;
    private String cargoAbono;

    @Digits(integer = 14, fraction = 2, message = "Valor Inadecuado, Ingrese valores positivos y maximo 2 decimales")
    private Double valor;

    //pie pagina1
    private Double cargo1;
    private Double abono1;
    private String diferencia1;

    //pie de pagina2
    private Double cargo;
    private Double abono;
    private Double diferencia;

    private Double res;

    //listas tabla 1
    private ArrayList<JournalEntryLinesTO> lstAccTable1 = new ArrayList<>();
    private List<JournalEntryLinesTO> select1;
    private List lstAccPadre1 = new Vector();
    //listas tabla 2
    private ArrayList<bankreconciliationauxTO> lstAccTable2 = new ArrayList<>();
    private List<bankreconciliationauxTO> select2;
    private List lstAccPadre2 = new Vector();

    //__________________________________________________________________________
    //reporte
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private static AdminEJBClient AdminEJBService = null;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    public String getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(String saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Double getCargo1() {
        return cargo1;
    }

    public void setCargo1(Double cargo1) {
        this.cargo1 = cargo1;
    }

    public Double getAbono1() {
        return abono1;
    }

    public void setAbono1(Double abono1) {
        this.abono1 = abono1;
    }

    public String getDiferencia1() {
        return diferencia1;
    }

    public void setDiferencia1(String diferencia1) {
        this.diferencia1 = diferencia1;
    }

    public List getLstAccPadre1() {
        return lstAccPadre1;
    }

    public void setLstAccPadre1(List lstAccPadre1) {
        this.lstAccPadre1 = lstAccPadre1;
    }

    public List getLstAccPadre2() {
        return lstAccPadre2;
    }

    public void setLstAccPadre2(List lstAccPadre2) {
        this.lstAccPadre2 = lstAccPadre2;
    }

    public List<JournalEntryLinesTO> getSelect1() {
        return select1;
    }

    public void setSelect1(List<JournalEntryLinesTO> select1) {
        this.select1 = select1;
    }

    public List<bankreconciliationauxTO> getSelect2() {
        return select2;
    }

    public void setSelect2(List<bankreconciliationauxTO> select2) {
        this.select2 = select2;
    }

    public AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        this.AccountingEJBClient = AccountingEJBClient;
    }

    public BankreconciliationEJBClient getBankreconciliationEJBClient() {
        return BankreconciliationEJBClient;
    }

    public void setBankreconciliationEJBClient(BankreconciliationEJBClient BankreconciliationEJBClient) {
        this.BankreconciliationEJBClient = BankreconciliationEJBClient;
    }

    public ArrayList<JournalEntryLinesTO> getLstAccTable1() {
        return lstAccTable1;
    }

    public void setLstAccTable1(ArrayList<JournalEntryLinesTO> lstAccTable1) {
        this.lstAccTable1 = lstAccTable1;
    }

    public ArrayList<bankreconciliationauxTO> getLstAccTable2() {
        return lstAccTable2;
    }

    public void setLstAccTable2(ArrayList<bankreconciliationauxTO> lstAccTable2) {
        this.lstAccTable2 = lstAccTable2;
    }

    public Date getFechaCuentas() {
        return fechaCuentas;
    }

    public void setFechaCuentas(Date fechaCuentas) {
        this.fechaCuentas = fechaCuentas;
    }

    public String getNewCodCuenta() {
        return newCodCuenta;
    }

    public void setNewCodCuenta(String newCodCuenta) {
        this.newCodCuenta = newCodCuenta;
    }

    public String getNewNomCuenta() {
        return newNomCuenta;
    }

    public void setNewNomCuenta(String newNomCuenta) {
        this.newNomCuenta = newNomCuenta;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCargoAbono() {
        return cargoAbono;
    }

    public void setCargoAbono(String cargoAbono) {
        this.cargoAbono = cargoAbono;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getCargo() {
        return cargo;
    }

    public void setCargo(Double cargo) {
        this.cargo = cargo;
    }

    public Double getAbono() {
        return abono;
    }

    public void setAbono(Double abono) {
        this.abono = abono;
    }

    public Double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Double diferencia) {
        this.diferencia = diferencia;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="LOAD">
    @PostConstruct
    public void initForm() {

        if (bean == null) {
            bean = new ReportsBean();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        if (BankreconciliationEJBClient == null) {
            BankreconciliationEJBClient = new BankreconciliationEJBClient();
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Btn Agregar">
    public void btnAdd() {
        //faceMessage("Agregar");
        if ((!newCodCuenta.equals("") || !newCodCuenta.isEmpty()) && fechaCuentas != null) {

            bankreconciliationauxTO newBank = new bankreconciliationauxTO();
            ResultOutTO _res = null;

            newBank.setRef1(this.newCodCuenta);
            newBank.setRefdate(this.fechaCuentas);

            newBank.setIndicator(this.cargoAbono);
            newBank.setMemo(this.concepto);
            newBank.setTranscode(this.identificador);

            if (cargoAbono.equals("C")) {
                newBank.setLoctotal(this.valor);
            } else {
                newBank.setSystotal(this.valor);
            }

            try {
                _res = BankreconciliationEJBClient.bankreconciliationaux_mtto(newBank, Common.MTTOINSERT);
                if (_res.getCodigoError() == 0) {
                    this.lstAccTable2.add(newBank);
                    cargosAbonosUpd();
                    cleanDet();
                    faceMessage(_res.getMensaje());
                } else {
                    faceMessage(_res.getMensaje());
                }

            } catch (Exception e) {
                faceMessage(e.getMessage() + " " + e.getCause());
            }
        } else {
            faceMessage("Seleccione Fecha y Cuenta para agregar");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Btn Principal">
    public void btnPrincipal() {
        if ((cargo1 - abono1) - (cargo - abono) == 0) {

            //faceMessage("Boton Principal");
            //faceMessage(this.select1.size()+" lista 1");
            //faceMessage(this.select2.size()+" lista 2");
            ExternalReconciliationTO newExter = new ExternalReconciliationTO();

            newExter.setAccount(this.newCodCuenta);
            newExter.setRefDate(fechaCuentas);

            for (JournalEntryLinesTO obj1 : select1) {
                for (JournalEntryLinesTO obj2 : lstAccTable1) {
                    if (obj1.getTransid() == obj2.getTransid() && obj1.getLine_id() == obj2.getLine_id()) {
                        obj2.setExtrmatch(1);
                    }
                }
            }
            for (bankreconciliationauxTO obj1 : select2) {
                for (bankreconciliationauxTO obj2 : lstAccTable2) {
                    if (obj1.getTransid() == obj2.getTransid()) {
                        obj2.setExtrmatch(1);
                    }
                }
            }
            for (JournalEntryLinesTO obj1 : lstAccTable1) {
                obj1.setMthdate(fechaCuentas);
                obj1.setTomthsum(saldoFinal);
                lstAccPadre1.add(obj1);
            }
            for (bankreconciliationauxTO obj2 : lstAccTable2) {
                obj2.setStornodate(fechaCuentas);
                obj2.setBaseamnt(saldoFinal);
                lstAccPadre2.add(obj2);
            }

            newExter.setAccToConciliate(lstAccPadre1);
            newExter.setAuxiliaryDoc(lstAccPadre2);

            ResultOutTO _res = new ResultOutTO();
            try {
                _res = BankreconciliationEJBClient.UpdateExternalReconciliation(newExter);
                if (_res.getCodigoError() == 0) {
                    faceMessage(_res.getMensaje());
                } else {
                    faceMessage(_res.getMensaje());
                }
            } catch (Exception e) {
                faceMessage(e.getMessage() + " " + e.getCause());
            }
        } else {
            faceMessage("Diferencias deben ser iguales para Guardar");
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BTN nuevo">
    public void btnNuevo() {
        this.newCodCuenta = "";
        this.newNomCuenta = "";
        this.saldoActual = "";
        this.fechaCuentas = new Date();
        cleanDet();
        clearList();
        RequestContext.getCurrentInstance().update("frmConci");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete CUENTA" > 
    public void findAccount(SelectEvent event) {
        List account = new Vector();
        List _result = null;

        String[] newName = null;
        String codigo = null, nombre = null;

        if (newNomCuenta != null) {
            newName = newNomCuenta.split("-");
            codigo = newName[0];
            nombre = newName[1];
        } else {
            if (newCodCuenta != null) {
                newName = newCodCuenta.split("-");
                codigo = newName[0];
                nombre = newName[1];
            } else {
                codigo = newCodCuenta;
                nombre = newNomCuenta;
            }
        }

        try {
            _result = AccountingEJBClient.getAccountByFilter(codigo, nombre);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " -- " + e.getCause());
            newCodCuenta = null;
            newNomCuenta = null;
        }
        if (_result.isEmpty()) {
            this.newCodCuenta = null;
            this.newNomCuenta = null;

        } else {
            for (Object obj : _result) {
                AccountTO articulo = (AccountTO) obj;
                account.add(articulo);
            }
            if (account.size() == 1) {
                System.out.println("articulo unico, llenar campos en pantalla");
                AccountTO art = (AccountTO) account.get(0);
                if (newCodCuenta != null || newNomCuenta != null) {
                    newCodCuenta = art.getAcctcode();
                    newNomCuenta = art.getAcctname();
                    //saldoActual = "$ " + formatNum(art.getCurrtotal());
                    EntryTO in = new EntryTO();
                    JournalEntryLinesTO _res = new JournalEntryLinesTO();
                    in.setAcctcode(this.newCodCuenta);
                    in.setRefdate(fechaCuentas);
                    //_res = AccountingEJBClient.getsaldo(in);
                    //saldoActual = "$ " + formatNum(_res.getTotalvat());
                    //saldoActualR = formatNum(_res.getTotalvat());
                    clearList();
                    dataAcc(fechaCuentas, newCodCuenta);
                }
            } else {
                faceMessage("Error: codigo duplicado");
            }
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Datos de cuenta seleccionada">
    public void dataAcc(Date f1, String accCode) {
        this.cargo1 = 0.0;
        this.abono1 = 0.0;

        this.cargo = 0.0;
        this.abono = 0.0;
        this.diferencia = 0.0;

        faceMessage("Datos de cuenta seleccionada");
        ExternalReconciliationTO _res = new ExternalReconciliationTO();

        _res.setAccount(accCode);
        _res.setRefDate(f1);

        _res = BankreconciliationEJBClient.get_newExternalReconciliation(_res);

        for (Object obj : _res.getAccToConciliate()) {
            JournalEntryLinesTO line = (JournalEntryLinesTO) obj;
            cargo1 = cargo1 + line.getDebit();
            abono1 = abono1 + line.getCredit();
            lstAccTable1.add(line);
        }

        for (Object obj2 : _res.getAuxiliaryDoc()) {
            bankreconciliationauxTO bank = (bankreconciliationauxTO) obj2;
            if (bank.getIndicator().equals("C")) {
                cargo = cargo + bank.getLoctotal();
            } else {
                abono = abono + bank.getSystotal();
            }
            lstAccTable2.add(bank);
        }

        cargo1 = formatNum(cargo1);
        abono1 = formatNum(abono1);
        diferencia1 = "$ " + formatNum(cargo1 - abono1);
        diferencia = cargo - abono;
        RequestContext.getCurrentInstance().update("frmConci");

    }

    public Double formatNum(Double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(num));
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="recalcular cargos/abonos">
    public void cargosAbonosUpd() {
        this.cargo = 0.0;
        this.abono = 0.0;
        this.diferencia = 0.0;

        for (bankreconciliationauxTO bank : lstAccTable2) {
            if (bank.getIndicator().equals("C")) {
                cargo = cargo + bank.getLoctotal();
            } else {
                abono = abono + bank.getSystotal();
            }
        }

        diferencia = cargo - abono;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Select & Unselect">
    public void onRowSelect1(SelectEvent event) {
        //faceMessage("entro");
        Double t1 = 0.0, t2 = 0.0, s1 = 0.0, s2 = 0.0;

        for (JournalEntryLinesTO bank : lstAccTable1) {
            t1 = t1 + bank.getDebit();
            t2 = t2 + bank.getCredit();
        }

        for (JournalEntryLinesTO obj2 : select1) {
            s1 = s1 + obj2.getDebit();
            s2 = s2 + obj2.getCredit();
        }

        this.cargo1 = t1 - s1;
        this.abono1 = t2 - s2;
        diferencia1 = "$ " + formatNum(cargo1 - abono1);

        //RequestContext.getCurrentInstance().update("det");
    }

    public void onRowUnselect1(UnselectEvent event) {
        //faceMessage("entro");
        Double t1 = 0.0, t2 = 0.0, s1 = 0.0, s2 = 0.0;
        for (JournalEntryLinesTO bank : lstAccTable1) {
            t1 = t1 + bank.getDebit();
            t2 = t2 + bank.getCredit();
        }

        for (JournalEntryLinesTO obj2 : select1) {
            s1 = s1 + obj2.getDebit();
            s2 = s2 + obj2.getCredit();
        }

        this.cargo1 = t1;// - s1;
        this.abono1 = t2;// - s2;
        diferencia1 = "$ " + formatNum(cargo1 - abono1);

        //RequestContext.getCurrentInstance().update("det");
    }

    public void onRowSelect2(SelectEvent event) {
        //faceMessage("entro");
        Double t1 = 0.0, t2 = 0.0, s1 = 0.0, s2 = 0.0;
        for (bankreconciliationauxTO bank : lstAccTable2) {
            if (bank.getIndicator().equals("C")) {
                t1 = t1 + bank.getLoctotal();
            } else {
                t2 = t2 + bank.getSystotal();
            }
        }

        for (bankreconciliationauxTO obj2 : select2) {
            if (obj2.getIndicator().equals("C")) {
                s1 = s1 + obj2.getLoctotal();
            } else {
                s2 = s2 + obj2.getSystotal();
            }
        }

        this.cargo = t1 - s1;
        this.abono = t2 - s2;
        diferencia = cargo - abono;

        //RequestContext.getCurrentInstance().update("det");
    }

    public void onRowUnselect2(UnselectEvent event) {
        //faceMessage("entro");
        Double t1 = 0.0, t2 = 0.0, s1 = 0.0, s2 = 0.0;
        for (bankreconciliationauxTO bank : lstAccTable2) {
            if (bank.getIndicator().equals("C")) {
                t1 = t1 + bank.getLoctotal();
            } else {
                t2 = t2 + bank.getSystotal();
            }
        }

        for (bankreconciliationauxTO obj2 : select2) {
            if (obj2.getIndicator().equals("C")) {
                s1 = s1 + obj2.getLoctotal();
            } else {
                s2 = s2 + obj2.getSystotal();
            }
        }

        this.cargo = t1;// + s1;
        this.abono = t2;// + s2;
        diferencia = cargo - abono;

        //RequestContext.getCurrentInstance().update("det");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones Varias">
    public void cleanDet() {
        this.identificador = null;
        this.concepto = null;
        this.cargoAbono = null;
        this.valor = null;
    }

    private void clearList() {
        this.saldoFinal = null;

        this.lstAccPadre1.clear();
        this.lstAccPadre2.clear();
        this.lstAccTable1.clear();
        this.lstAccTable2.clear();
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Imprimir" > 
    public void printReport() {
        if (this.saldoFinal != null) {

            try {
                if (bean == null) {
                    bean = new ReportsBean();
                }
                EnterpriseTO resp = null;
                try {
                    resp = AdminEJBService.getEnterpriseInfo();
                } catch (Exception ex) {
                    Logger.getLogger(repPurchases.class.getName()).log(Level.SEVERE, null, ex);
                }

                Map<String, Object> reportParameters = new HashMap<>();

                //info empresa
                reportParameters.put("corpName", resp.getCrintHeadr());
                reportParameters.put("reportName", "Conciliaciones Bancarias");

                //fecha, saldo escrito y saldo consultado
                //java.sql.Date sqlDate = new java.sql.Date(getFechaCuentas().getTime());
                reportParameters.put("fechaHasta", getFechaCuentas());
                reportParameters.put("saldoEsrito", this.saldoFinal);
                reportParameters.put("saldoMetodo", this.saldoActualR);

                //where t1.extrmatch isnull and t1.account != $P{codAcc} and t1.refdate > $P{fechaHasta}
                // t1.extrmatch isnull and t1.account = $P{codAcc} and t1.refdate <= $P{fechaHasta}
                String where = " t1.extrmatch isnull ";//and t1.account = $P{codAcc} and t1.refdate <= $P{fechaHasta}";
                reportParameters.put("where1", where);

                //cod name acc
                reportParameters.put("codAcc", this.newCodCuenta);
                reportParameters.put("nameAcc", this.newNomCuenta);

                //totales 
                reportParameters.put("conciliar1", 105.00);
                reportParameters.put("conciliar2", 500.00);

                this.bean.setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "FILE"));
                this.bean.setFileName("##-CON");
                this.bean.setParameters(reportParameters);
                this.bean.setReportName("/bank/ConciliationReport1");
                this.bean.execute();

            } catch (Exception e) {
                faceMessage("Error al generar reporte: " + e.getMessage() + " " + e.getCause());
            }
        } else {
            faceMessage("Ingrese saldo final bancario");
        }

    }
    //</editor-fold>

    public void eventSaldo() {
        faceMessage("entro " + this.saldoActual + " " + saldoActualR + " " + saldoFinal + " " + fechaCuentas);
    }

}//cierre de clase
