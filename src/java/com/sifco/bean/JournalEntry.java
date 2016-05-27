/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.assignment.bean.AccassignmentBean;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.accounting.to.JournalEntryInTO;
import com.sifcoapp.objects.accounting.to.JournalEntryLinesTO;
import com.sifcoapp.objects.accounting.to.JournalEntryTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.report.bean.ReportsBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "JournalEntry")
@SessionScoped
public class JournalEntry implements Serializable {

    HttpSession session = Util.getSession();

//<editor-fold defaultstate="collapsed" desc="declaracion de variables">
    //beans
    private AccountingEJBClient AccountingEJBClient;
    private static AdminEJBClient AdminEJBService;
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    // tipo TO
    private JournalEntryLinesTO selectDetail = new JournalEntryLinesTO();
    private JournalEntryTO newJournal = new JournalEntryTO();
    // usadas como banderas para disables, etc
    private boolean disable;
    private boolean disable2;
    private int varEstados;
    private String botonEstado;
    private boolean confirm;
    private boolean disableComun;
    private boolean disableX;
    private boolean disableY;
    //listas
    List listaPadre = new Vector();
    ArrayList<JournalEntryLinesTO> listaDetalles = new ArrayList<>();
    private List listaBusqueda = new Vector();
    private ArrayList<JournalEntryTO> listaBusquedaTable = new ArrayList<>();
    private Object selectJournal = new JournalEntryTO();
    //para el encabezado
    private int number;//numero
    private Date refdate;//fecha contabilizacion
    private Date duedate;//fecha venc.
    private Date taxdate;//fecha docu
    private String transtype;//origen depende del tipo objeto base, convertir dependiendo de la transaccion realizada(crear lista en COMMON)
    private String baseref;//numero origen
    private int transid;//Nº trans
    private String memo;//comen
    private String transcode;//nº transaccion
    private Double loctotal = 0.0;//total padredebe
    private Double systotal = 0.0;//totalhaber
    private BigDecimal tempBig = new BigDecimal(0);

    //para el detalle
    private String account;//codigocuenta
    private String shortname;//codigocuenta usado para el nombre pero lleva el mismo codigo
    private Double debit;//debe y haber
    private Double credit;
    private String debcred;//indicador de credito/debito C/D
    
    private boolean renderedContex;

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        this.AccountingEJBClient = AccountingEJBClient;
    }
    
    public boolean isRenderedContex() {
        return renderedContex;
    }

    public void setRenderedContex(boolean renderedContex) {
        this.renderedContex = renderedContex;
    }

    public ReportsBean getBean() {
        return bean;
    }

    public void setBean(ReportsBean bean) {
        this.bean = bean;
    }

    public JournalEntry() {
    }

    public boolean isDisableY() {
        return disableY;
    }

    public void setDisableY(boolean disableY) {
        this.disableY = disableY;
    }

    public boolean isDisableX() {
        return disableX;
    }

    public void setDisableX(boolean disableX) {
        this.disableX = disableX;
    }

    public JournalEntryTO getNewJournal() {
        return newJournal;
    }

    public void setNewJournal(JournalEntryTO newJournal) {
        this.newJournal = newJournal;
    }

    public int getVarEstados() {
        return varEstados;
    }

    public void setVarEstados(int varEstados) {
        this.varEstados = varEstados;
    }

    public String getBotonEstado() {
        return botonEstado;
    }

    public void setBotonEstado(String botonEstado) {
        this.botonEstado = botonEstado;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isDisableComun() {
        return disableComun;
    }

    public void setDisableComun(boolean disableComun) {
        this.disableComun = disableComun;
    }

    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean getDisable2() {
        return disable2;
    }

    public void setDisable2(boolean disable2) {
        this.disable2 = disable2;
    }

    public int getTransid() {
        return transid;
    }

    public void setTransid(int transid) {
        this.transid = transid;
    }

    public Date getRefdate() {
        return refdate;
    }

    public void setRefdate(Date refdate) {
        this.refdate = refdate;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Date getTaxdate() {
        return taxdate;
    }

    public void setTaxdate(Date taxdate) {
        this.taxdate = taxdate;
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
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

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBaseref() {
        return baseref;
    }

    public void setBaseref(String baseref) {
        this.baseref = baseref;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getLoctotal() {
        return loctotal;
    }

    public void setLoctotal(Double loctotal) {
        this.loctotal = loctotal;
    }

    public Double getSystotal() {
        return systotal;
    }

    public void setSystotal(Double systotal) {
        this.systotal = systotal;
    }

    public String getDebcred() {
        return debcred;
    }

    public void setDebcred(String debcred) {
        this.debcred = debcred;
    }

    public List getListaPadre() {
        return listaPadre;
    }

    public void setListaPadre(List listaPadre) {
        this.listaPadre = listaPadre;
    }

    public ArrayList<JournalEntryLinesTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<JournalEntryLinesTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public JournalEntryLinesTO getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(JournalEntryLinesTO selectDetail) {
        this.selectDetail = selectDetail;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<JournalEntryTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<JournalEntryTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public Object getSelectJournal() {
        return selectJournal;
    }

    public void setSelectJournal(Object selectJournal) {
        this.selectJournal = selectJournal;
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="init de la ventana">
    @PostConstruct
    public void initForm() {
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        estateGuardar();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocompletado">
    public List<String> completeText(String query) {
        List _result = null;
        account = null;
        shortname = null;

        String filterByCode = null;
        try {

            _result = AccountingEJBClient.getAccountByFilter(filterByCode, query, "Y");
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> results = new ArrayList<String>();

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
            _result = AccountingEJBClient.getAccountByFilter(query, filterByName, "Y");
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> results = new ArrayList<String>();

        Iterator<AccountTO> iterator = _result.iterator();
        while (iterator.hasNext()) {
            AccountTO cuentas = (AccountTO) iterator.next();
            results.add(cuentas.getAcctcode() + "-" + cuentas.getAcctname());
        }
        return results;
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
            codigo = newName[0];
            nombre = newName[1];
        } else {
            if (account != null && !account.equals("")) {
                newName = account.split("-");
                codigo = newName[0];
                nombre = newName[1];
            } else {
                codigo = account;
                nombre = shortname;
            }
        }

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
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Agregar detalles al dataTable" > 
    public void accionAgregar(ActionEvent actionEvent) {
        try {
            if (validarNull()) {
                JournalEntryLinesTO nuevoDetalle = new JournalEntryLinesTO();
                if (debit != null) {
                    tempBig = new BigDecimal(loctotal).add(new BigDecimal(debit));
                    loctotal = tempBig.doubleValue();
                }
                if (credit != null) {
                    tempBig = new BigDecimal(systotal).add(new BigDecimal(credit));
                    systotal = tempBig.doubleValue();
                }
                nuevoDetalle.setAccount(account);
                nuevoDetalle.setAcctname(shortname);
                nuevoDetalle.setDebit(debit);
                nuevoDetalle.setCredit(credit);
                nuevoDetalle.setLine_id(UUID.randomUUID().hashCode());
                nuevoDetalle.setObjtype("" + 5);
                // nuevoDetalle.setLinenum(getListaDetalles().size() + 1);

                if (listaDetalles == null) {
                    listaDetalles = new ArrayList<>();
                }
                if (listaPadre == null) {
                    listaPadre = new Vector();
                }
                try {
                    if (this.debit != null && this.debit > 0) {
                        nuevoDetalle.setDebcred("D");
                    }
                } catch (Exception e) {
                }
                try {
                    if (this.credit != null && this.credit > 0) {
                        nuevoDetalle.setDebcred("C");
                    }
                } catch (Exception e) {
                }

                listaPadre.add(nuevoDetalle);
                getListaDetalles().add(nuevoDetalle);
                //cleanDetalle();
            }
        } catch (Exception e) {
        }
        cleanDetalle();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Eliminar del dataTable" > 
    public void deleteDetalle() {
        try {
            boolean var1, var2;
            var1 = getListaDetalles().remove(this.selectDetail);
            var2 = listaPadre.remove(this.selectDetail);
            if (selectDetail.getDebit() != null) {
                tempBig = new BigDecimal(this.loctotal).subtract(new BigDecimal(this.selectDetail.getDebit()));
                this.loctotal = tempBig.doubleValue();
            }
            if (selectDetail.getCredit() != null) {
                tempBig = new BigDecimal(this.systotal).subtract(new BigDecimal(this.selectDetail.getCredit()));
                this.systotal = tempBig.doubleValue();
            }
            this.selectDetail = null;
            if (var1 && var2) {
                faceMessage("Articulo Eliminado.");

            } else {
                faceMessage("Seleccione un articulo para eliminar");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "---" + e.getCause());
            //faceMessage("No pudo eliminarse la entrada: " + e.getMessage());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";
        this.disableComun = false;
        this.disableX = true;
        this.disableY = false;
        Date d = new Date();
        this.setRefdate(d);
        this.setTaxdate(d);
        this.setDuedate(d);
        this.renderedContex = false;
        RequestContext.getCurrentInstance().update("formJournal");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2;
        this.botonEstado = "Actualizar";
        this.disableComun = true;
        this.disableX = true;
        this.disableY = true;
        this.renderedContex = true;
        RequestContext.getCurrentInstance().update("formJournal");
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";
        this.disableComun = true;
        this.disableX = false;
        this.disableY = false;
        this.setRefdate(null);
        this.setTaxdate(null);
        this.setDuedate(null);
        this.renderedContex = false;
        RequestContext.getCurrentInstance().update("formJournal");
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Dialogs Confirm">
    public void showHideDialog(String name, int openClose) {
        try {
            RequestContext rc = RequestContext.getCurrentInstance();
            if (openClose == 1) {
                rc.execute("PF('" + name + "').show();");
            }
            if (openClose == 2) {
                rc.execute("PF('" + name + "').hide();");
            }

        } catch (Exception e) {
            faceMessage(e.getMessage() + "---" + e.getCause());
        }

    }

    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlg9", 2);
        if (varEstados == 1) {
            saveJournal();
        } else {
            if (varEstados == 2) {
                updateJournal();
            }
        }
    }

    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlg10", 2);
        this.confirm = true;
        cleanBean();
        RequestContext.getCurrentInstance().update("formJournal");
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg9').hide();");
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg10').hide();");
    }

    //evento al seleccionar un elemento del dialogo
    public void selectDialog() {
        showHideDialog("dlg2", 2);
        JournalEntryTO var = (JournalEntryTO) selectJournal;
        //llenarPantalla(var);

        try {
            newJournal = AccountingEJBClient.getJournalEntryByKey(var.getTransid());
            llenarPantalla(newJournal);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(JournalEntry.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        RequestContext.getCurrentInstance().update("formReceipt");

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Principal" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                if (validarDatosJournal()) {
                    showHideDialog("dlg9", 1);
                }
                break;

            case 2:
                if (validarDatosJournal()) {
                    showHideDialog("dlg9", 1);
                }
                break;

            case 3:
                listaBusqueda.clear();
                listaBusquedaTable.clear();
                searchJournal();
                break;

            default:
                break;

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Botones toolbar " > 
    public void botonNuevo(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 2) {//mas de un detalle
            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                estateGuardar();
            }
        } else {
            cleanBean();
            estateGuardar();
        }
    }

    public void botonBuscar(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 2) {//mas de un detalle
            //faceMessage(varEstados+" estado");
            //          toolbarBoton = 2;
            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                estateBuscar();
            }
        } else {
            cleanBean();
            estateBuscar();
        }
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="validaciones">
    public boolean validarNull() {
        if (account.isEmpty()) {
            faceMessage("Digite un código de cuenta");
            return false;
        } else {
            if (shortname.isEmpty()) {
                faceMessage("Digite un nombre de cuenta");
                return false;
            } else {
                if (debit == null && credit == null) {
                    faceMessage("Digite una cantidad");
                    return false;
                } else {
                    return true;
                }
            }
        }

    }

    public boolean validarClear() {
        return this.listaPadre.size() >= 1;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Guardar en base">
    public void saveJournal() {
        int line = 0;
        if (newJournal == null) {
            newJournal = new JournalEntryTO();
        }
        newJournal.setUsersign((int) session.getAttribute("usersign"));
        newJournal.setNumber(number);
        newJournal.setRefdate(refdate);
        newJournal.setDuedate(duedate);
        newJournal.setTaxdate(taxdate);
        newJournal.setTranstype(transtype);////###################pendiente logica
        newJournal.setBaseref(baseref);
        newJournal.setMemo(memo);
        newJournal.setObjtype("" + 5);
        if (!memo.isEmpty()) {
            newJournal.setMemo(memo);
        }
        Iterator<JournalEntryLinesTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            JournalEntryLinesTO jourLine = (JournalEntryLinesTO) iterator2.next();
            jourLine.setShortname(jourLine.getAccount());
            jourLine.setTaxdate(taxdate);
            jourLine.setRefdate(refdate);
            jourLine.setDuedate(duedate);
            jourLine.setLinememo(memo);
            jourLine.setLine_id(line + 1);
            line = line + 1;
        }

        newJournal.setJournalentryList(listaPadre);
        newJournal.setLoctotal(loctotal);
        newJournal.setSystotal(systotal);
        try {
            ResultOutTO _res;
            _res = AccountingEJBClient.journalEntry_mtto(newJournal, Common.MTTOINSERT);

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                transid = _res.getDocentry();
                newJournal = AccountingEJBClient.getJournalEntryByKey(transid);
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(JournalEntry.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update en base">
    public void updateJournal() {

        newJournal.setMemo(null);
        if (!memo.isEmpty()) {
            newJournal.setMemo(memo);
        }
        try {
            ResultOutTO _res;
            _res = AccountingEJBClient.journalEntry_mtto(newJournal, Common.MTTOUPDATE);

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                //transid = _res.getDocentry();
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(JournalEntry.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar en base" >
    public void searchJournal() {

        JournalEntryInTO searchJournal = new JournalEntryInTO();

        if (refdate != null) {
            searchJournal.setRefdate(refdate);
        }
        searchJournal.setNumber(number);
        if (duedate != null) {
            searchJournal.setDuedate(duedate);
        }
        if (taxdate != null) {
            searchJournal.setTaxdate(taxdate);
        }

        if (transtype.equals("")) {
            searchJournal.setTranstype(null);
        } else {
            searchJournal.setTranstype(transtype);
        }
        if (baseref.equals("")) {
            searchJournal.setBaseref(null);
        } else {
            searchJournal.setBaseref(baseref);
        }

        if (credit != null && credit > 0) {
            searchJournal.setLoctotal(credit);
        }

        if (memo != null && !memo.equals("")) {
            searchJournal.setMemo(memo);
        } else {
            searchJournal.setMemo(null);
        }

        searchJournal.setTransid(transid);

        try {

            listaBusqueda = AccountingEJBClient.getJournalEntry(searchJournal);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newJournal = (JournalEntryTO) listaBusqueda.get(0);
                try {
                    JournalEntryTO var2 = AccountingEJBClient.getJournalEntryByKey(newJournal.getTransid());
                    llenarPantalla(var2);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(JournalEntry.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }

            } else {
                faceMessage("Seleccione un elemento");

                for (Object receipt : listaBusqueda) {
                    JournalEntryTO var = (JournalEntryTO) receipt;
                    listaBusquedaTable.add(var);
                }
                showHideDialog("dlg2", 1);

            }

        } else {
            faceMessage("No se obtuvieron resultados de la Busqueda");
        }
        //listaBusqueda = new Vector();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    public void DisableInput() {
        if (debit != null) {
            disable = true;
        } else {
            disable = false;
        }
        if (credit != null) {
            disable2 = true;
        } else {
            disable2 = false;
        }
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    private void cleanDetalle() {
        account = null;
        shortname = null;
        debit = null;
        credit = null;
        disable = false;
        disable2 = false;
    }

    public void cleanBean() {
        this.transid = 0;
        this.number = 0;
        this.baseref = null;
        this.transtype = null;
        this.memo = null;
        Date d = new Date();
        this.setRefdate(d);
        this.setTaxdate(d);
        this.setDuedate(d);
        cleanDetalle();
        this.newJournal = new JournalEntryTO();
        this.listaDetalles.clear();
        this.listaPadre.clear();
        this.loctotal = 0.0;
        this.systotal = 0.0;

        //this.listaBusquedaTable.clear();
        //this.listaBusqueda.clear();
    }

    //llenar pantalla dado un Receipt
    public void llenarPantalla(JournalEntryTO var) {
        setNumber(var.getNumber());
        setRefdate(var.getRefdate());
        setTaxdate(var.getTaxdate());
        setDuedate(var.getDuedate());
        setBaseref(var.getBaseref());
        setTranstype(var.getTranstype());
        setTransid(var.getTransid());
        setMemo(var.getMemo());
        setLoctotal(var.getLoctotal());
        setSystotal(var.getLoctotal());
        for (Object detalle : var.getJournalentryList()) {
            JournalEntryLinesTO det = (JournalEntryLinesTO) detalle;
            this.listaDetalles.add(det);
        }
    }

    //validaciones antes de guardar a la base
    public boolean validarDatosJournal() {
        if (refdate == null || taxdate == null || duedate == null) {
            faceMessage("Fechas vacias");
            return false;
        }
        if (getListaDetalles().size() < 1) {
            faceMessage("Ingrese al menos una Transacción");
            return false;
        }
        if (!Objects.equals(systotal, loctotal)) {
            faceMessage("Error Debe es distinto al Haber");
            return false;
        }
        return true;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="print">
    public void print() {
        if (this.newJournal.getTransid() > 0 && this.varEstados == 2) {
            faceMessage("Imprimir " + this.transid);
            String _reportname = null;
            Map<String, Object> reportParameters = new HashMap<>();
            EnterpriseTO resp = new EnterpriseTO();

            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            try {
                resp = AdminEJBService.getEnterpriseInfo();
            } catch (Exception ex) {
                //faceMessage("Informacion de empresa ERROR");
            }

            _reportname = "/bank/JournalEntryPrint";
            reportParameters.put("pdocnum", this.newJournal.getTransid());
            reportParameters.put("reportName", " ");
            reportParameters.put("corpName", resp.getCrintHeadr());

            getBean().setParameters(reportParameters);
            getBean().setReportName(_reportname);
            getBean().execute();

        } else {
            faceMessage("No se puede imprimir");
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Reversión">
   public void revertJournal (){
        faceMessage("Revertir registro en diario");
        
        //Intercambiar el debe con el haber        
        listaPadre = new Vector();
        for (Object receipt : listaDetalles) {
            Double temp = 0.0;
            JournalEntryLinesTO var = (JournalEntryLinesTO) receipt;       
            
            temp = var.getCredit();
            var.setCredit(var.getDebit());
            var.setDebit(temp); 
            listaPadre.add(receipt);
        }
        this.memo = ("Reversión - " + memo);
       
        estateGuardar();
   }
    
//</editor-fold>
}//cierre de clase
