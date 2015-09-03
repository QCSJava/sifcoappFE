/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifco.login.bean.Util;
import com.sifco.businesspartner.bean.BusinessPartner;
import com.sifcoapp.assignment.bean.AccassignmentBean;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.accounting.to.JournalEntryTO;
import com.sifcoapp.objects.accounting.to.JournalEntryLinesTO;
import com.sifcoapp.objects.accounting.to.RecurringPostingsDetailTO;
import com.sifcoapp.objects.accounting.to.RecurringPostingsInTO;
import com.sifcoapp.objects.accounting.to.RecurringPostingsTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "RecurringPosting")
@ApplicationScoped
public class RecurringPosting implements Serializable {

    HttpSession session = Util.getSession();

//<editor-fold defaultstate="collapsed" desc="declaracion de variables">
    //beans
    private AccountingEJBClient AccountingEJBClient;
    // tipo TO
    private RecurringPostingsDetailTO selectDetail = new RecurringPostingsDetailTO();
    private RecurringPostingsTO newRecurring = new RecurringPostingsTO();
    // usadas como banderas para disables, etc
    private boolean disable;
    private boolean disable2;
    private boolean disable3;
    private int varEstados;
    private String botonEstado;
    private boolean confirm;
    private boolean disableComun;
    private boolean disableX;
    private boolean disableY;
    private static final String CATALOGOClASS = "Recurring_Principal";
    private static final String CATALOGODIARY = "Recurring_Diario";
    private static final String CATALOGOWEEK = "Recurring_semanal";
    private static final String CATALOGOMOUNTH = "Recurring_mensual";
    private List<CatalogTO> ClassLst;
    private List<CatalogTO> GeneralLst;
    private List<RecurringPostingsTO> LstExecute;
    private List<RecurringPostingsTO> RecurringSelected;
    private AdminEJBClient AdminEJBService;
    //listas
    List listaPadre = new Vector();
    ArrayList<RecurringPostingsDetailTO> listaDetalles = new ArrayList<>();
    private List listaBusqueda = new Vector();
    private ArrayList<RecurringPostingsTO> listaBusquedaTable = new ArrayList<>();
    private Object selectJournal = new RecurringPostingsTO();
    //para el encabezado
    private String rcurcode;//codigo
    private int instance;//instancia
    private String rcurdesc;//descripcion
    private String ref1, ref2, ref3;//referencias
    private String memo;//comen
    private String frequency;//frecuancia combo1
    private int remind;//frecuencia parcial combo 2
    private Date nextdeu, limitdate;//proxima y ultima ejecu
    private String limitrtrns;//checkbox

    private Double loctotal = 0.0;//total padredebe
    private Double systotal = 0.0;//totalhaber

    private Double totalVolumen;

    //para el detalle
    private String account;//codigocuenta
    private String shortname;//codigocuenta usado para el nombre pero lleva el mismo codigo
    private Double debit;//debe y haber
    private Double credit;
    private String grossvalue;//indicador de credito/debito C/D

    private String fecha = "true";
    private int RowKey;
    //para el footer de la pagina

//</editor-fold>
   
//<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public RecurringPosting() {
    }

    public int getRowKey() {
        return this.hashCode();
    }

    public void setRowKey(int RowKey) {
        this.RowKey = RowKey;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<RecurringPostingsTO> getRecurringSelected() {
        return RecurringSelected;
    }

    public void setRecurringSelected(List<RecurringPostingsTO> RecurringSelected) {
        this.RecurringSelected = RecurringSelected;
    }

    public Double getTotalVolumen() {
        return totalVolumen;
    }

    public void setTotalVolumen(Double totalVolumen) {
        this.totalVolumen = totalVolumen;
    }

    public List<RecurringPostingsTO> getLstExecute() {
        return LstExecute;
    }

    public void setLstExecute(List<RecurringPostingsTO> LstExecute) {
        this.LstExecute = LstExecute;
    }

    public boolean isDisable3() {
        return disable3;
    }

    public void setDisable3(boolean disable3) {
        this.disable3 = disable3;
    }

    public List<CatalogTO> getGeneralLst() {
        return GeneralLst;
    }

    public void setGeneralLst(List<CatalogTO> GeneralLst) {
        this.GeneralLst = GeneralLst;
    }

    public List<CatalogTO> getClassLst() {
        return ClassLst;
    }

    public void setClassLst(List<CatalogTO> ClassLst) {
        this.ClassLst = ClassLst;
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

    public RecurringPostingsTO getNewRecurring() {
        return newRecurring;
    }

    public void setNewRecurring(RecurringPostingsTO newRecurring) {
        this.newRecurring = newRecurring;
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
        return grossvalue;
    }

    public void setDebcred(String grossvalue) {
        this.grossvalue = grossvalue;
    }

    public List getListaPadre() {
        return listaPadre;
    }

    public void setListaPadre(List listaPadre) {
        this.listaPadre = listaPadre;
    }

    public ArrayList<RecurringPostingsDetailTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<RecurringPostingsDetailTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public RecurringPostingsDetailTO getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(RecurringPostingsDetailTO selectDetail) {
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

    public ArrayList<RecurringPostingsTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<RecurringPostingsTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public Object getSelectJournal() {
        return selectJournal;
    }

    public void setSelectJournal(Object selectJournal) {
        this.selectJournal = selectJournal;
    }

    public String getRcurcode() {
        return rcurcode;
    }

    public void setRcurcode(String rcurcode) {
        this.rcurcode = rcurcode;
    }

    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public String getRcurdesc() {
        return rcurdesc;
    }

    public void setRcurdesc(String rcurdesc) {
        this.rcurdesc = rcurdesc;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public String getRef3() {
        return ref3;
    }

    public void setRef3(String ref3) {
        this.ref3 = ref3;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public Date getNextdeu() {
        return nextdeu;
    }

    public void setNextdeu(Date nextdeu) {
        this.nextdeu = nextdeu;
    }

    public Date getLimitdate() {
        return limitdate;
    }

    public void setLimitdate(Date limitdate) {
        this.limitdate = limitdate;
    }

    public String getLimitrtrns() {
        return limitrtrns;
    }

    public void setLimitrtrns(String limitrtrns) {
        this.limitrtrns = limitrtrns;
    }

    public String getGrossvalue() {
        return grossvalue;
    }

    public void setGrossvalue(String grossvalue) {
        this.grossvalue = grossvalue;
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
        try {
            this.ClassLst = AdminEJBService.findCatalog(CATALOGOClASS);
            this.GeneralLst = AdminEJBService.findCatalog(CATALOGOMOUNTH);
            //RecurringPostingsInTO in = new RecurringPostingsInTO();
            //in.setUsersign((int) session.getAttribute("usersign"));//g r p exe
            this.LstExecute = AccountingEJBClient.getrecurringPostingExecute((int) session.getAttribute("usersign"));
            totalVolumen = 0.00;
            Iterator<RecurringPostingsTO> iterator = LstExecute.iterator();
            while (iterator.hasNext()) {
                RecurringPostingsTO nuevo = new RecurringPostingsTO();
                nuevo = iterator.next();
                totalVolumen = totalVolumen + nuevo.getVolume();
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
        }
        estateGuardar();
        //faceMessage("load");

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
            results.add(cuentas.getAcctname());
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
            results.add(cuentas.getAcctcode());
        }
        return results;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete" > 
    public void findAccount(SelectEvent event) {
        List account2 = new Vector();
        String var = null;
        if (event.getObject().toString() != var) {
            List _result = null;

            try {
                _result = AccountingEJBClient.getAccountByFilter(account, shortname);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
                account = null;
                shortname = null;
            }
            if (_result.isEmpty()) {
                this.account = null;
                this.shortname = null;
            } else {
                Iterator<AccountTO> iterator = _result.iterator();
                while (iterator.hasNext()) {
                    AccountTO articulo = (AccountTO) iterator.next();
                    account2.add(articulo);
                }
                if (account2.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) account2.get(0);
                        account = art.getAcctcode();
                        shortname = art.getAcctname();
                    } catch (Exception ex) {
                        Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    AccountTO art = (AccountTO) account2.get(0);
                    account = art.getAcctcode();
                    shortname = art.getAcctname();
                    faceMessage("Error: Mas de un elemento encontrado, nombre de articulo repetido");
                }
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Agregar detalles al dataTable" > 
    public void accionAgregar(ActionEvent actionEvent) {
        try {
            if (validarNull()) {
                RecurringPostingsDetailTO nuevoDetalle = new RecurringPostingsDetailTO();
                if (debit != null) {
                    loctotal = loctotal + debit;
                }
                if (credit != null) {
                    systotal = systotal + credit;
                }
                nuevoDetalle.setAcctcode(account);
                nuevoDetalle.setAcctdesc(shortname);
                nuevoDetalle.setDebit(debit);
                nuevoDetalle.setCredit(credit);
                nuevoDetalle.setLineid(UUID.randomUUID().hashCode());
                // nuevoDetalle.setLinenum(getListaDetalles().size() + 1);

                if (listaDetalles == null) {
                    listaDetalles = new ArrayList<RecurringPostingsDetailTO>();
                }
                if (listaPadre == null) {
                    listaPadre = new Vector();
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
                this.loctotal = this.loctotal - this.selectDetail.getDebit();
            }
            if (selectDetail.getCredit() != null) {
                this.systotal = this.systotal - this.selectDetail.getCredit();
            }
            this.selectDetail = null;
            if (var1 && var2) {
                faceMessage("Transacción Eliminada");

            } else {
                faceMessage("Seleccione una transacción para eliminar");
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
        this.disableX = false;
        this.disableY = true;
        frequency = "M";
        limitrtrns = "false";
        Date d = new Date();
        this.setLimitdate(d);
        this.setNextdeu(d);
        GeneralLst.clear();
        this.fecha = "false";
        try {
            GeneralLst = AdminEJBService.findCatalog(CATALOGOMOUNTH);
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().update("formRecurring");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2;
        this.botonEstado = "Actualizar";
        this.disableComun = false;
        this.disableX = true;
        this.fecha = "false";
        RequestContext.getCurrentInstance().update("formRecurring");
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";
        this.disableComun = true;
        this.disableX = false;
        this.rcurcode = null;
        this.setLimitdate(null);
        this.setNextdeu(null);
        this.instance = 0;
        this.fecha = "false";
        RequestContext.getCurrentInstance().update("formRecurring");
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Llenar Dialogs y evento seleccionar del dialogo y msj Confirm">
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
            saveRecurring();
        } else {
            if (varEstados == 2) {
                updateRecurring();
            }
        }
    }

    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlg10", 2);
        this.confirm = true;
        cleanBean();
        RequestContext.getCurrentInstance().update("formRecurring");
    }

    public void confirmDialog3(ActionEvent actionEvent) {
        showHideDialog("dlg11", 2);
        GenerateJournal();
    }

    public void confirmDialog4(ActionEvent actionEvent) {
        showHideDialog("dlg12", 2);
        DeteleRecurring();
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg9').hide();");
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg10').hide();");
    }

    public void cancelDialog3(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg11').hide();");
    }

    public void cancelDialog4(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg12').hide();");
    }

    //evento al seleccionar un elemento del dialogo
    public void selectDialog() {
        showHideDialog("dlg2", 2);
        RecurringPostingsTO var = (RecurringPostingsTO) selectJournal;
        //llenarPantalla(var);

        try {
            newRecurring = AccountingEJBClient.getrecurringPosting_by_key(var.getRcurcode(), var.getInstance());
            llenarPantalla(newRecurring);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        RequestContext.getCurrentInstance().update("formRecurring");

    }

    public void ShowRecurring(ActionEvent actionEvent) {
        this.LstExecute.clear();
        RecurringPostingsInTO in = new RecurringPostingsInTO();
        in.setUsersign((int) session.getAttribute("usersign"));
        this.LstExecute = AccountingEJBClient.getrecurringPosting_user(in);
        totalVolumen = 0.00;
        Iterator<RecurringPostingsTO> iterator = LstExecute.iterator();
        while (iterator.hasNext()) {
            RecurringPostingsTO nuevo = new RecurringPostingsTO();
            nuevo = iterator.next();
            nuevo.setEntrycount(UUID.randomUUID().hashCode());
            //totalVolumen=totalVolumen+nuevo.getVolume();
        }
        showHideDialog("dlg001", 1);
        //RequestContext.getCurrentInstance().update("formRecurring"); 
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Principal y botones del Dialog" >
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

    public void Execute(ActionEvent event) {
        if (fecha == null) {
            faceMessage("Seleccione una fecha");
        } else {
            if (RecurringSelected.size() > 0) {
                showHideDialog("dlg11", 1);
            } else {
                faceMessage("No seleccionó ningún elemento a ejecutar");
            }
        }

    }

    public void Delete(ActionEvent event) {
        if (RecurringSelected.size() > 0) {
            showHideDialog("dlg12", 1);
        } else {
            faceMessage("No seleccionó ningún elemento a eliminar");
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
    public void saveRecurring() {
        int line = 0;
        if (newRecurring == null) {
            newRecurring = new RecurringPostingsTO();
        }
        newRecurring.setUsersign((int) session.getAttribute("usersign"));
        newRecurring.setRcurcode(rcurcode);
        newRecurring.setInstance(instance);//valor por defecto para las plantillas
        newRecurring.setEntrycount(0);
        if (!rcurdesc.equals("")) {
            newRecurring.setRcurdesc(rcurdesc);
        }
        if (!ref1.equals("")) {
            newRecurring.setRef1(ref1);
        }
        if (!ref2.equals("")) {
            newRecurring.setRef2(ref2);
        }
        if (!ref3.equals("")) {
            newRecurring.setRef3(ref3);
        }
        newRecurring.setMemo(null);
        newRecurring.setNextdeu(nextdeu);
        if (!memo.isEmpty()) {
            newRecurring.setMemo(memo);
        }
        newRecurring.setFrequency(frequency);
        if (disable3 != true) {
            newRecurring.setRemind(remind);
        }
        if (limitrtrns.equals("false")) {
            newRecurring.setLimitrtrns("Y");
            newRecurring.setLimitdate(limitdate);
        } else {
            newRecurring.setLimitrtrns("N");
        }
        Iterator<RecurringPostingsDetailTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            RecurringPostingsDetailTO articleDetalle = (RecurringPostingsDetailTO) iterator2.next();
            articleDetalle.setRcurcode(rcurcode);
            articleDetalle.setLineid(line + 1);
            line = line + 1;
        }

        newRecurring.setRecurringPostingsDetail(listaPadre);
        newRecurring.setVolume(loctotal);
        try {
            ResultOutTO _res;
            _res = AccountingEJBClient.fin_recurringPosting_mtto(newRecurring, Common.MTTOINSERT);

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update en base">
    public void updateRecurring() {
        int line = 0;
        if (newRecurring == null) {
            newRecurring = new RecurringPostingsTO();
        }
        newRecurring.setUsersign((int) session.getAttribute("usersign"));
        newRecurring.setRcurcode(rcurcode);
        newRecurring.setInstance(instance);
        newRecurring.setRcurdesc(rcurdesc);
        newRecurring.setEntrycount(0);
        if (!ref1.equals("")) {
            newRecurring.setRef1(ref1);
        }
        if (!ref2.equals("")) {
            newRecurring.setRef2(ref2);
        }
        if (!ref3.equals("")) {
            newRecurring.setRef3(ref3);
        }
        newRecurring.setMemo(null);
        newRecurring.setNextdeu(nextdeu);
        if (!memo.isEmpty()) {
            newRecurring.setMemo(memo);
        }
        newRecurring.setFrequency(frequency);
        if (disable3 != true) {
            newRecurring.setRemind(remind);
        }
        if (limitrtrns.equals("false")) {
            newRecurring.setLimitrtrns("Y");
            newRecurring.setLimitdate(limitdate);
        } else {
            newRecurring.setLimitrtrns("N");
        }
        listaPadre.clear();
        Iterator<RecurringPostingsDetailTO> iterator2 = listaDetalles.iterator();
        while (iterator2.hasNext()) {
            RecurringPostingsDetailTO articleDetalle = (RecurringPostingsDetailTO) iterator2.next();
            articleDetalle.setRcurcode(rcurcode);
            articleDetalle.setLineid(line + 1);
            line = line + 1;
            listaPadre.add(articleDetalle);
        }
        newRecurring.setRecurringPostingsDetail(null);
        newRecurring.setRecurringPostingsDetail(listaPadre);
        newRecurring.setVolume(loctotal);
        try {
            ResultOutTO _res;
            _res = AccountingEJBClient.fin_recurringPosting_mtto(newRecurring, Common.MTTOUPDATE);

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                //transid = _res.getDocentry();
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar en base" >
    public void searchJournal() {

        RecurringPostingsInTO searchJournal = new RecurringPostingsInTO();
        if (rcurcode.equals("")) {
            searchJournal.setRcurcode(null);
        } else {
            searchJournal.setRcurcode(rcurcode);
        }
        if (rcurdesc.equals("")) {
            searchJournal.setRcurdesc(null);
        } else {
            searchJournal.setRcurdesc(rcurdesc);
        }
        if (ref1.equals("")) {
            searchJournal.setRef1(null);
        } else {
            searchJournal.setRef1(ref1);
        }
        if (ref2.equals("")) {
            searchJournal.setRef2(null);
        } else {
            searchJournal.setRef2(ref2);
        }
        if (ref3.equals("")) {
            searchJournal.setRef3(null);
        } else {
            searchJournal.setRef3(ref3);
        }
        if (memo.equals("")) {
            searchJournal.setMemo(null);
        } else {
            searchJournal.setMemo(memo);
        }
        try {

            listaBusqueda = AccountingEJBClient.getrecurringPosting(searchJournal);
        } catch (Exception e) {
            faceMessage(e.getMessage() + "Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newRecurring = (RecurringPostingsTO) listaBusqueda.get(0);
                try {
                    RecurringPostingsTO var2 = AccountingEJBClient.getrecurringPosting_by_key(newRecurring.getRcurcode(), newRecurring.getInstance());
                    llenarPantalla(var2);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }

            } else {
                faceMessage("Seleccione un elemento");

                for (Object receipt : listaBusqueda) {
                    RecurringPostingsTO var = (RecurringPostingsTO) receipt;
                    var.setEntrycount(UUID.randomUUID().hashCode());
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

        this.memo = null;
        Date d = new Date();
        this.setLimitdate(d);
        this.setNextdeu(d);
        cleanDetalle();
        this.newRecurring = new RecurringPostingsTO();
        this.listaDetalles.clear();
        this.listaPadre.clear();
        this.loctotal = 0.0;
        this.systotal = 0.0;
        this.ref1 = null;
        this.ref2 = null;
        this.ref3 = null;
        this.rcurcode = null;
        this.rcurdesc = null;
        //this.listaBusquedaTable.clear();
        //this.listaBusqueda.clear();
    }

    //llenar pantalla dado un Receipt
    public void llenarPantalla(RecurringPostingsTO var) {
        setRcurcode(var.getRcurcode());
        //setInstance(var.getInstance());
        setRcurdesc(var.getRcurdesc());
        setRef1(var.getRef1());
        setRef2(var.getRef2());
        setRef3(var.getRef3());
        setMemo(var.getMemo());
        setLoctotal(var.getVolume());
        setSystotal(var.getVolume());
        setFrequency(var.getFrequency());
        stateChangeListenerFill(var);
        setNextdeu(var.getNextdeu());
        setInstance(var.getInstance());
        if (var.getLimitrtrns().equals("Y")) {
            setDisableY(true);
            setLimitrtrns("false");
            setLimitdate(var.getLimitdate());
        } else {
            setLimitrtrns("true");
            setDisableY(false);
            setLimitdate(null);
        }

        for (Object detalle : var.getRecurringPostingsDetail()) {
            RecurringPostingsDetailTO det = (RecurringPostingsDetailTO) detalle;
            this.listaDetalles.add(det);
        }
    }

    //validaciones antes de guardar a la base
    public boolean validarDatosJournal() {
        if (rcurcode.equals("")) {
            faceMessage("Ingrese un Código");
            return false;
        }
        if (rcurdesc.equals("")) {
            faceMessage("Ingrese una Descripción");
            return false;
        }
        if ((limitdate == null && disableY == false) || nextdeu == null) {
            faceMessage("Fechas vacias");
            return false;
        }
        if (getListaDetalles().size() < 1) {
            faceMessage("Ingrese al menos una Transacción");
            return false;
        }
        if (!Objects.equals(systotal, loctotal)) {
            faceMessage("Error el Debe es distinto del Haber");
            return false;
        }
        return true;
    }

    public void selectBooleanView() {
        limitrtrns = disableY ? "false" : "true";
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del ComboBox Frecuencia" > 
    public void stateChangeListener(ValueChangeEvent event) {
        System.out.print(event.getNewValue());
        try {
            if (event.getNewValue().equals("D")) {
                disable3 = false;
                GeneralLst.clear();
                GeneralLst = AdminEJBService.findCatalog(CATALOGODIARY);
            }
            if (event.getNewValue().equals("W")) {
                disable3 = false;
                GeneralLst.clear();
                GeneralLst = AdminEJBService.findCatalog(CATALOGOWEEK);
            }
            if (event.getNewValue().equals("M")) {
                disable3 = false;
                GeneralLst.clear();
                GeneralLst = AdminEJBService.findCatalog(CATALOGOMOUNTH);
            }
            if (event.getNewValue().equals("T")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (event.getNewValue().equals("S")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (event.getNewValue().equals("A")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (event.getNewValue().equals("O")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (event.getNewValue().equals("F")) {
                GeneralLst.clear();
                disable3 = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stateChangeListenerFill(RecurringPostingsTO var) {
        try {
            if (var.getFrequency().equals("D")) {
                disable3 = false;
                GeneralLst.clear();
                GeneralLst = AdminEJBService.findCatalog(CATALOGODIARY);
                remind = var.getRemind();
            }
            if (var.getFrequency().equals("W")) {
                disable3 = false;
                GeneralLst.clear();
                GeneralLst = AdminEJBService.findCatalog(CATALOGOWEEK);
                remind = var.getRemind();
            }
            if (var.getFrequency().equals("M")) {
                disable3 = false;
                GeneralLst.clear();
                GeneralLst = AdminEJBService.findCatalog(CATALOGOMOUNTH);
                remind = var.getRemind();
            }
            if (var.getFrequency().equals("T")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (var.getFrequency().equals("S")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (var.getFrequency().equals("A")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (var.getFrequency().equals("O")) {
                GeneralLst.clear();
                disable3 = true;
            }
            if (var.getFrequency().equals("F")) {
                GeneralLst.clear();
                disable3 = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Eliminar Contabilizaciones">
    public void DeteleRecurring() {

        Iterator<RecurringPostingsTO> iterator = RecurringSelected.iterator();
        ResultOutTO _res = null;
        try {
            while (iterator.hasNext()) {
                RecurringPostingsTO delete = (RecurringPostingsTO) iterator.next();
                _res = AccountingEJBClient.fin_recurringPosting_mtto(delete, Common.MTTODELETE);
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
        if (_res.getCodigoError() == 0) {//se realizo correctamente
            faceMessage(_res.getMensaje());
            showHideDialog("dlg001", 2);
        } else {
            showHideDialog("dlg001", 2);
            faceMessage(_res.getMensaje());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Generar asientos automaticos">
    public void GenerateJournal() {
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        Date _date = new Date();
        List HijosRecurring = new Vector();

        //obtener todos los padres que fueron seleccionados en el dialogo los que se guardaran en journalEntry
        Iterator<RecurringPostingsTO> iterator = RecurringSelected.iterator();
        ResultOutTO _res = null;
        try {
            //se recorre cada padre obteniendo sus correspondientes hijos (se setean los valores a guardar)
            while (iterator.hasNext()) {
                RecurringPostingsTO nuevo = (RecurringPostingsTO) iterator.next();
                RecurringPostingsTO PadreRecurring = new RecurringPostingsTO();
                JournalEntryTO padreJournal = new JournalEntryTO();
                if (fecha.equals("true")) {
                    padreJournal.setRefdate(_date);
                } else {
                    padreJournal.setRefdate(nuevo.getNextdeu());
                }
                padreJournal.setMemo(nuevo.getMemo());
                padreJournal.setRef1(nuevo.getRef1());
                padreJournal.setRef2(nuevo.getRef2());
                padreJournal.setRef3(nuevo.getRef3());
                padreJournal.setLoctotal(nuevo.getVolume());
                padreJournal.setSystotal(nuevo.getVolume());
                padreJournal.setUsersign((int) session.getAttribute("usersign"));
                //obtener todos los hijos del padre que se insertaran en journalentryLines
                PadreRecurring = AccountingEJBClient.getrecurringPosting_by_key(nuevo.getRcurcode(), nuevo.getInstance());
                Iterator<RecurringPostingsDetailTO> iterator2 = PadreRecurring.getRecurringPostingsDetail().iterator();
                // se recorre cada uno de los hijos para setearlos a un tipo JournalEntryDetail y luego asignarlos al padre
                while (iterator2.hasNext()) {
                    RecurringPostingsDetailTO son = (RecurringPostingsDetailTO) iterator2.next();
                    JournalEntryLinesTO HijoJournal = new JournalEntryLinesTO();
                    HijoJournal.setLine_id(son.getLineid());
                    HijoJournal.setAccount(son.getAcctcode());
                    HijoJournal.setDebit(son.getDebit());
                    HijoJournal.setCredit(son.getCredit());
                    HijoJournal.setShortname(son.getAcctcode());
                    HijosRecurring.add(HijoJournal);
                }
                padreJournal.setJournalentryList(HijosRecurring);
                _res = AccountingEJBClient.journalEntry_mtto(padreJournal, Common.MTTOINSERT);
            }
        } catch (Exception ex) {
            Logger.getLogger(RecurringPosting.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
        if (_res.getCodigoError() == 0) {//se realizo correctamente
            //si se hace correcta la insercion del asiento contable se elimina el registro de Contab peri
            DeteleRecurring();
            faceMessage(_res.getMensaje());

        } else {
            faceMessage(_res.getMensaje());
        }
    }

//</editor-fold>
    
    public void Add(SelectEvent event) {
        Iterator<RecurringPostingsTO> iterator = RecurringSelected.iterator();
        totalVolumen = 0.0;
        while (iterator.hasNext()) {
            RecurringPostingsTO nuevo = (RecurringPostingsTO) iterator.next();
            totalVolumen = totalVolumen + nuevo.getVolume();
        }
        RequestContext.getCurrentInstance().update("dlgT:tableDialog2");
        faceMessage("Seleccion" + totalVolumen);
    }

    public void Rest(SelectEvent event) {
        faceMessage("nooo");
    }
    
}//cierre de clase
