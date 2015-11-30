/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.assignment.bean.AccassignmentBean;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.BankEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.bank.to.CheckForPaymentInTO;
import com.sifcoapp.objects.bank.to.CheckForPaymentTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.report.common.numerosAletras;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Digits;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "checkForPaymentBean")
@SessionScoped
public class CheckForPaymentBean implements Serializable {

    public CheckForPaymentBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    //Servicios EJB
    private static BankEJBClient BankEJBClient;
    private static CatalogEJBClient CatalogEJB;
    private static AccountingEJBClient AccountingEJBClient;
    private static AdminEJBClient AdminEJBService;

    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();
    //__________________________________________________________________________
    //Pantalla
    private String codSocio;        //codigo de socio
    private String nameSocio;       //nombre del socio
    private String newCodCuenta;       //codigo de cuenta
    private String newNomCuenta;      //nombre de cuenta

    private int idInterno;          //id interno del cheque
    private String referencia;      //referencia
    private Date fechaConta = new Date();        //fecha de contabilizacion

    private String comentario;      //comentarios
    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private Double impVencido;      //importe vencido

    private String firma;           //firma
    private Double total;           //total

    private String favorDe;         //En favor de 
    private String impLetra;        //importe en letra

    private Date fechaVen = new Date();        //fecha de vencimiento
    private String banco;           //Banco
    private String cuenta;          //cuenta aspciada al banco
    private String NoCheque;           //numero de cheque

    //__________________________________________________________________________
    //funciones de numeros a letras
    private numerosAletras convertNumber;

    //__________________________________________________________________________
    //ComboBox
    private static final String CATALOGOBANK = "Banks";     //banco seleccionado

    private List<CatalogTO> lstBanks;                       //llenar cmb bancos

    //__________________________________________________________________________
    //Manejo de estados
    private int varEstados;
    private String botonEstado;

    //__________________________________________________________________________
    //nuevo cheque
    private CheckForPaymentTO newCheck = new CheckForPaymentTO();

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private ArrayList<CheckForPaymentTO> listaBusquedaTable = new ArrayList<>();
    private CheckForPaymentTO selectCheck = new CheckForPaymentTO();

    //__________________________________________________________________________
    //habilitaciones
    private boolean idCheck;
    private boolean common;
    private int toolbarBoton;
    private boolean confirm;

    //
    private String url;

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >    
    @PostConstruct
    public void initForm() {
        //
        idCheck = true;
        common = false;

        //Inicializacion de servicios
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        if (BankEJBClient == null) {
            BankEJBClient = new BankEJBClient();
        }

        //llenar cmb bancos
        try {
            String var = null;
            lstBanks = AdminEJBService.findCatalog(CATALOGOBANK);
        } catch (Exception e) {
            faceMessage("Error: Fallo al llenar combo box " + e.getMessage());
        }

        //estado por defecto
        estateGuardar();

    }
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
            faceMessage("Error en autocompletado");
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
            faceMessage("Error en autocompletado");
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
//<editor-fold defaultstate="collapsed" desc="Autocompletado de CUENTA ">
    public List<String> completeName(String query) {
        List _result = null;

        String filterByCode = null;
        try {

            _result = AccountingEJBClient.getAccountByFilter(filterByCode, query, "Y");
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
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

        String filterByName = null;
        try {
            _result = AccountingEJBClient.getAccountByFilter(query, filterByName, "Y");
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
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

//<editor-fold defaultstate="collapsed" desc="Seleccionar de autocomplete de Socio, Name o Cod">
    public void selectSocioName(SelectEvent event) {
        String[] newName = nameSocio.split("-");
        this.codSocio = null;
        selectSocioCod(newName[0]);

    }

    public void selectSocioCod(String code) {
        List socio = new Vector();
        String var = null;
        List _result = null;
        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(codSocio == null ? code : codSocio);

        try {
            _result = CatalogEJB.get_businesspartner(in);

        } catch (Exception e) {
            faceMessage(e.getMessage() + " -- " + e.getCause());
            codSocio = null;
            nameSocio = null;
            favorDe = null;
        }
        if (_result.isEmpty()) {
            this.codSocio = null;
            this.nameSocio = null;
            this.favorDe = null;

        } else {
            for (Object obj : _result) {
                BusinesspartnerTO articulo = (BusinesspartnerTO) obj;
                socio.add(articulo);
            }
            if (socio.size() == 1) {
                try {
                    System.out.println("articulo unico, llenar campos en pantalla");
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);

                    codSocio = art.getCardcode();
                    nameSocio = art.getCardname();
                    favorDe = art.getCardname();

                } catch (Exception ex) {
                    Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                faceMessage("Error: Mas de un elemento encontrado, nombre de articulo repetido");
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete CUENTA" > 
    public void findAccount(SelectEvent event) {
        List account = new Vector();
        List _result = null;

        String[] newName = null;
        String codigo = null, nombre = null;

        if (newNomCuenta != null && !newNomCuenta.equals("")) {
            newName = newNomCuenta.split("-");
            codigo = newName[0];
            nombre = newName[1];
        } else {
            if (newCodCuenta != null && !newCodCuenta.equals("")) {
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
                try {
                    AccountTO art = (AccountTO) account.get(0);
                    if (newCodCuenta != null || newNomCuenta != null) {
                        newCodCuenta = art.getAcctcode();
                        newNomCuenta = art.getAcctname();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                faceMessage("Error codigo repetido");
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";

        this.idCheck = true;
        this.common = false;

        RequestContext.getCurrentInstance().update("frmCheck");

    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        if (varEstados == 1) {
            this.varEstados = Common.MTTOUPDATE; //2
            this.botonEstado = "Actualizar";

            this.idCheck = true;
            this.common = true;

            RequestContext.getCurrentInstance().update("frmprt");
            showHideDialog("dlgPtr", 1);
            RequestContext.getCurrentInstance().update("frmCheck");
        } else {
            this.varEstados = Common.MTTOUPDATE; //2
            this.botonEstado = "Actualizar";

            this.idCheck = true;
            this.common = true;
            RequestContext.getCurrentInstance().update("frmCheck");
        }
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";

        this.idCheck = false;
        this.common = false;

        RequestContext.getCurrentInstance().update("frmCheck");
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton PRINCIPAL" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                if (validarNewCheck()) {
                    showHideDialog("dlgC2", 1);
                }
                break;

            case 2:
                if (validarNewCheck()) {
                    showHideDialog("dlgC2", 1);
                }
                break;

            case 3:
                listaBusqueda.clear();
                listaBusquedaTable.clear();
                doSearch();
                break;

            default:
                break;

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {

        if (newCheck == null) {
            newCheck = new CheckForPaymentTO();
        }

        try {
            CatalogTO cat = AdminEJBService.findCatalogByKey(banco, 23);
            newCheck.setBankname(cat.getCatvalue());
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        newCheck.setBanknum(banco);
        newCheck.setUsersign((int) session.getAttribute("usersign"));
        newCheck.setDpstacct(NoCheque);
        newCheck.setAcctnum(cuenta);
        newCheck.setCheckdate(fechaVen);
        newCheck.setSignature(firma);
        newCheck.setChecksum(total);
        newCheck.setTotalwords(impLetra);
        newCheck.setVendorname(favorDe);
        newCheck.setTransref(referencia);
        newCheck.setPmntdate(fechaConta);
        newCheck.setDetails(comentario);
        newCheck.setVendorcode(codSocio);
        newCheck.setCheckacct(newCodCuenta);

        try {
            ResultOutTO _res = null;

            _res = BankEJBClient.ges_cfp0_checkforpayment_mtto(newCheck, Common.MTTOINSERT); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                this.newCheck.setCheckkey(_res.getDocentry());
                idInterno = _res.getDocentry();
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="UPDATE EN BASE">
    public void doUpdate() {

        if (newCheck == null) {
            newCheck = new CheckForPaymentTO();
        }

        try {
            CatalogTO cat = AdminEJBService.findCatalogByKey(banco, 23);
            newCheck.setBankname(cat.getCatvalue());
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        newCheck.setBanknum(banco);
        newCheck.setUsersign((int) session.getAttribute("usersign"));
        newCheck.setDpstacct(NoCheque);
        newCheck.setAcctnum(cuenta);
        newCheck.setCheckdate(fechaVen);
        newCheck.setSignature(firma);
        newCheck.setChecksum(total);
        newCheck.setTotalwords(impLetra);
        newCheck.setVendorname(favorDe);
        newCheck.setTransref(referencia);
        newCheck.setPmntdate(fechaConta);
        newCheck.setDetails(comentario);
        newCheck.setVendorcode(codSocio);
        newCheck.setCheckacct(newCodCuenta);
        newCheck.setCheckkey(idInterno);

        try {
            ResultOutTO _res = null;

            _res = BankEJBClient.ges_cfp0_checkforpayment_mtto(newCheck, Common.MTTOUPDATE);

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                idInterno = _res.getDocentry();
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BUSCAR EN BASE">
    public void doSearch() {
        CheckForPaymentInTO searchCheck = new CheckForPaymentInTO();
        String vacio = null;
        searchCheck.setCheckkey(idInterno);

        if (NoCheque == null || NoCheque.equals("")) {
            searchCheck.setDpstacct(vacio);
        } else {
            searchCheck.setDpstacct(NoCheque);
        }

        if (banco.equals("-1")) {
            searchCheck.setBanknum(vacio);
        } else {
            searchCheck.setBanknum(banco);
        }

        if (cuenta == null) {
            searchCheck.setAcctnum(vacio);
        } else {
            searchCheck.setAcctnum(cuenta);
        }

        searchCheck.setCheckdate(fechaVen);
        if (firma.equals("")) {
            searchCheck.setSignature(vacio);
        } else {
            searchCheck.setSignature(firma);
        }

        searchCheck.setChecksum(total);

        if (favorDe.equals("")) {
            searchCheck.setVendorname(vacio);
        } else {
            searchCheck.setVendorname(favorDe);
        }

        /*if (referencia.equals("")) {
         searchCheck.setTransref(vacio);
         } else {
         searchCheck.setTransref(referencia);
         }*/
        searchCheck.setPmntdate(fechaConta);

        if (comentario.equals("")) {
            searchCheck.setDetails(vacio);
        } else {
            searchCheck.setDetails(comentario);
        }

        if (codSocio == null) {
            searchCheck.setVendorcode(vacio);
        } else {
            searchCheck.setVendorcode(codSocio);
        }

        if (newCodCuenta == null) {
            searchCheck.setCheckacct(vacio);
        } else {
            searchCheck.setCheckacct(newCodCuenta);
        }

        try {
            listaBusqueda = BankEJBClient.get_cfp0_checkforpayment(searchCheck);
        } catch (Exception e) {
            faceMessage(" Error en la busqueda " + e.getMessage());
        }

        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newCheck = (CheckForPaymentTO) listaBusqueda.get(0);
                try {

                    CheckForPaymentTO var = BankEJBClient.get_cfp0_checkforpaymentByKey(newCheck.getCheckkey());
                    llenarPantalla(var);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }
            } else {
                faceMessage("Seleccione un elemento");

                for (Object receipt : listaBusqueda) {
                    try {
                        CheckForPaymentTO var = (CheckForPaymentTO) receipt;
                        listaBusquedaTable.add(var);

                    } catch (Exception e) {
                        faceMessage(e.getMessage() + " - " + e.getCause());
                    }
                }
                RequestContext.getCurrentInstance().update("checkdlg");
                showHideDialog("dlgListCheck", 1);
            }
        } else {
            faceMessage("No se encontraron registros");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Botones toolbar " > 
    public void botonNuevo(ActionEvent actionEvent) {
        if (varEstados != 2 && validarClear()) {
            toolbarBoton = 1;
            showHideDialog("dlgC1", 1);
        } else {
            cleanBean(1);  //1:deja fecha atual, 2:borra fecha
            estateGuardar();
        }
    }

    public void botonBuscar(ActionEvent actionEvent) {
        if (varEstados != 2 && validarClear()) {
            toolbarBoton = 2;
            showHideDialog("dlgC1", 1);
        } else {
            cleanBean(2);
            estateBuscar();
        }
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="bank slect">
    public void bankChange(ValueChangeEvent event) {

        String bank = event.getNewValue().toString();

        try {
            CatalogTO cat = AdminEJBService.findCatalogByKey(bank, 23);
            this.cuenta = cat.getCatvalue2();
            this.banco = cat.getCatvalue();
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="numeros a letras">
    public void numToLetras() {
        String res;
        res = numerosAletras.convertNumberToLetter(this.impVencido);
        this.impLetra = res;
        this.total = this.impVencido;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Funciones Varias">
    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlgC2", 2);
        if (varEstados == 1) {
            doSave();
        } else {
            if (varEstados == 2) {
                doUpdate();
            }
        }
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC2').hide();");
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC1').hide();");
    }

    public boolean validarClear() {
        try {
            if (!banco.equals("-1") || impVencido != null || codSocio != null || newCodCuenta != null || !referencia.equals("") || !comentario.equals("") || !firma.equals("")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlgC1", 2);
        this.confirm = true;
        if (toolbarBoton == 1) {
            cleanBean(1);
            estateGuardar();
        } else {
            cleanBean(2);
            estateBuscar();
        }

        RequestContext.getCurrentInstance().update("frmCheck");
    }

    public void selectDialogBill() {
        if (BankEJBClient == null) {
            BankEJBClient = new BankEJBClient();
        }

        if (newCheck == null) {
            newCheck = new CheckForPaymentTO();
        }

        showHideDialog("dlgListCheck", 2);
        CheckForPaymentTO var = (CheckForPaymentTO) selectCheck;

        try {
            newCheck = BankEJBClient.get_cfp0_checkforpaymentByKey(var.getCheckkey());
            llenarPantalla(newCheck);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectCheck = new CheckForPaymentTO();
        RequestContext.getCurrentInstance().update("frmCheck");

    }

    //Mostrar u ocultar un dialogo; 1 muestra, 2 oculta
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

    //llenar pantalla
    public void llenarPantalla(CheckForPaymentTO var) {
        List _result;
        BusinesspartnerTO _result2 = new BusinesspartnerTO();
        AccountTO art = new AccountTO();

        this.setIdInterno(var.getCheckkey());
        this.setNoCheque(var.getDpstacct());
        this.setBanco(var.getBanknum());
        this.setCuenta(var.getAcctnum());
        this.setFechaVen(var.getCheckdate());
        this.setFirma(var.getSignature());
        this.setTotal(var.getChecksum());
        this.setImpVencido(var.getChecksum());
        this.setImpLetra(var.getTotalwords());
        this.setFavorDe(var.getVendorname());
        this.setReferencia(var.getTransref());
        this.setFechaConta(var.getPmntdate());
        this.setComentario(var.getDetails());

        try {

            _result = AccountingEJBClient.getAccountByFilter(var.getCheckacct(), null);
            art = (AccountTO) _result.get(0);

            _result2 = CatalogEJB.get_businesspartnerBykey(var.getVendorcode());

        } catch (Exception e) {
            faceMessage(e.getMessage() + " -- " + e.getCause());
            newCodCuenta = null;
            newNomCuenta = null;
        }

        this.setCodSocio(var.getVendorcode());
        this.setNameSocio(_result2.getCardname());
        this.setNewNomCuenta(art.getAcctname());
        this.setNewCodCuenta(var.getCheckacct());

    }

    public boolean validarNewCheck() {
        if (codSocio == null || nameSocio == null) {
            faceMessage("Seleccione un socio de negocio");
            return false;
        }
        if (newCodCuenta == null || newNomCuenta == null) {
            faceMessage("Seleccione una Cuenta");
            return false;
        }
        if (fechaConta == null) {
            faceMessage("Seleccione fecha de contabilizacion");
            return false;
        }
        if (total == null || total <= 0) {
            faceMessage("Importe debe ser una cantidad mayor a cero");
            return false;
        }
        if (favorDe == null) {
            faceMessage("Debe ingresar 'En favor de'");
            return false;
        }
        if (fechaVen == null) {
            faceMessage("Seleccione fecha de vencimiento");
            return false;
        }
        if ("-1".equals(banco)) {
            faceMessage("Seleccione un Banco");
            return false;
        }
        if (NoCheque == null || NoCheque.equals("")) {
            faceMessage("Ingrese un numero de cheque");
            return false;
        }
        return true;
    }

    public void cleanBean(int tipo) {
        codSocio = null;
        nameSocio = null;
        newCodCuenta = null;
        newNomCuenta = null;
        idInterno = 0;
        referencia = null;
        comentario = null;
        impVencido = 0.0;
        firma = null;
        total = 0.0;
        favorDe = null;
        impLetra = null;
        banco = null;
        cuenta = null;
        NoCheque = null;

        if (tipo == 1) {
            Date d = new Date();
            this.setFechaConta(d);
            this.setFechaVen(d);
        } else {
            Date d = null;
            this.setFechaConta(d);
            this.setFechaVen(d);
        }

    }

    //mostrar un mensaje en pantalla dado un string
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public static BankEJBClient getBankEJBClient() {
        return BankEJBClient;
    }

    public static void setBankEJBClient(BankEJBClient BankEJBClient) {
        CheckForPaymentBean.BankEJBClient = BankEJBClient;
    }

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        CheckForPaymentBean.CatalogEJB = CatalogEJB;
    }

    public static AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public static void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        CheckForPaymentBean.AccountingEJBClient = AccountingEJBClient;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        CheckForPaymentBean.AdminEJBService = AdminEJBService;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public numerosAletras getConvertNumber() {
        return convertNumber;
    }

    public void setConvertNumber(numerosAletras convertNumber) {
        this.convertNumber = convertNumber;
    }

    public CheckForPaymentTO getNewCheck() {
        return newCheck;
    }

    public void setNewCheck(CheckForPaymentTO newCheck) {
        this.newCheck = newCheck;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public int getToolbarBoton() {
        return toolbarBoton;
    }

    public void setToolbarBoton(int toolbarBoton) {
        this.toolbarBoton = toolbarBoton;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CheckForPaymentTO getSelectCheck() {
        return selectCheck;
    }

    public void setSelectCheck(CheckForPaymentTO selectCheck) {
        this.selectCheck = selectCheck;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public ArrayList<CheckForPaymentTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<CheckForPaymentTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public boolean isIdCheck() {
        return idCheck;
    }

    public void setIdCheck(boolean idCheck) {
        this.idCheck = idCheck;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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

    public List<CatalogTO> getLstBanks() {
        return lstBanks;
    }

    public void setLstBanks(List<CatalogTO> lstBanks) {
        this.lstBanks = lstBanks;
    }

    public String getCodSocio() {
        return codSocio;
    }

    public void setCodSocio(String codSocio) {
        this.codSocio = codSocio;
    }

    public String getNameSocio() {
        return nameSocio;
    }

    public void setNameSocio(String nameSocio) {
        this.nameSocio = nameSocio;
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

    public int getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(int idInterno) {
        this.idInterno = idInterno;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFechaConta() {
        return fechaConta;
    }

    public void setFechaConta(Date fechaConta) {
        this.fechaConta = fechaConta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getImpVencido() {
        return impVencido;
    }

    public void setImpVencido(Double impVencido) {
        this.impVencido = impVencido;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFavorDe() {
        return favorDe;
    }

    public void setFavorDe(String favorDe) {
        this.favorDe = favorDe;
    }

    public String getImpLetra() {
        return impLetra;
    }

    public void setImpLetra(String impLetra) {
        this.impLetra = impLetra;
    }

    public Date getFechaVen() {
        return fechaVen;
    }

    public void setFechaVen(Date fechaVen) {
        this.fechaVen = fechaVen;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNoCheque() {
        return NoCheque;
    }

    public void setNoCheque(String NoCheque) {
        this.NoCheque = NoCheque;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="IMPRIMIR FORMA 2">
    public String printInvoice() throws UnsupportedEncodingException {
        //faceMessage(getApplicationUri());
        //System.out.println(getApplicationUri()+"||----------------------------------------------------------------");
        setUrl(getApplicationUri());
        //String nombre = session.getAttribute("username").toString().toUpperCase();
        if (newCheck.getCheckkey() > 0) {
            String foo = newCheck.getCheckkey() + "";
            String bar = (String) session.getAttribute("userfullname");
            return "/PrintCheckView?faces-redirect=true"
                    + "&foo=" + URLEncoder.encode(foo, "UTF-8")
                    + "&bar=" + URLEncoder.encode(bar, "UTF-8");
        } else {
            faceMessage("No se puede imprimir");
            return "/view/bank/CheckForPayment.xhtml";
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="redirect">
    public void redirect() throws IOException {
        String url2 = getUrl() + "/faces/view/bank/CheckForPayment.xhtml"; //url donde se redirige la pantalla
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getExternalContext().redirect(url2);
    }

    public String getApplicationUri() {
        try {
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null, ext.getRequestServerName(), ext.getRequestServerPort(),
                    ext.getRequestContextPath(), null, null);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            throw new FacesException(e);
        }
    }
//</editor-fold>

}//cierre de clase
