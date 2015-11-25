/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.BankEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.bank.to.ColecturiaConceptTO;
import com.sifcoapp.objects.bank.to.ColecturiaDetailTO;
import com.sifcoapp.objects.bank.to.ColecturiaInTO;
import com.sifcoapp.objects.bank.to.ColecturiaTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.sales.to.SalesTO;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "colecturiaBean")
@SessionScoped
public class ColecturiaBean implements Serializable {

    public ColecturiaBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    //Servicios EJB
    private static CatalogEJBClient CatalogEJB;
    private static BankEJBClient BankEJBClient;
    ParameterEJBClient ParameterEJBClient;
    private static AdminEJBClient AdminEJBService;
    //__________________________________________________________________________
    //Pantalla
    private int No;             //Numero de documento
    private Date fechaDoc = new Date();      //Fecha documento
    private Date fechaPago = new Date();     //Fecha de pago 
    private String codSocio;    //Codigo de socio
    private String nameSocio;   //Nombre del socio
    private int equipo, serie, NoRecibo;
    private String observaciones;
    private String group;

    private Double t1 = 0.0, t2 = 0.0, t3 = 0.0;  //Totales de detalles

    //__________________________________________________________________________
    //Detalles
    private List lstInicial = new Vector();
    private List lstPadre = new Vector();
    private ArrayList<ColecturiaDetailTO> lstTable = new ArrayList<>();
    private ColecturiaDetailTO selectDet;

    //__________________________________________________________________________
    //Estados
    private int varEstados;
    private String botonEstado;

    private boolean idCheck;
    private boolean common;

    private boolean ifCancelacion;

    //__________________________________________________________________________
    //new colect
    private ColecturiaTO newColect = new ColecturiaTO();

    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private ArrayList<ColecturiaTO> listaBusquedaTable = new ArrayList<>();
    private ColecturiaTO selectC = new ColecturiaTO();

    //__________________________________________________________________________
    //
    private boolean confirm;
    private int toolbarBoton;
    private String ctlAcc;
    private Double totalFac = 0.0;
    private Double totalFacValorAct = 0.0;
    private int lineNum;

    //__________________________________________________________________________
    //estado
    private static final String CATALOGOTYPEDOC = "ColecturiaType";  //tipo doc
    private List<CatalogTO> lstTipoDoc;
    private String documento;
    private String estado;
    private int idAnterior;
    private boolean rendered;

    //
    private String url;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load de pantalla">
    @PostConstruct
    public void initForm() {

        if (BankEJBClient == null) {
            BankEJBClient = new BankEJBClient();
        }

        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }

        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        try {
            lstTipoDoc = AdminEJBService.findCatalog(CATALOGOTYPEDOC);
        } catch (Exception ex) {
            Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        lineNum = Integer.parseInt(ParameterEJBClient.getParameterbykey(9).getValue1());
        estateGuardar();

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
        in.setCardtype("C");

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
    
//<editor-fold defaultstate="collapsed" desc="Seleccionar de autocomplete de Socio, Name o Cod">
    public void selectSocioName() {
        String[] newName = nameSocio.split("-");
        selectSocioCod(newName[0]);
    }

    public void selectSocioCod(String code) {
        List socio = new Vector();
        List _result = null;
        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(codSocio == null ? code : codSocio);

        try {
            _result = CatalogEJB.get_businesspartner(in);

        } catch (Exception e) {
            faceMessage(e.getMessage() + " -- " + e.getCause());
            codSocio = null;
            nameSocio = null;
        }
        if (_result.isEmpty()) {
            this.codSocio = null;
            this.nameSocio = null;
        } else {
            for (Object obj : _result) {
                BusinesspartnerTO articulo = (BusinesspartnerTO) obj;
                socio.add(articulo);
            }
            if (socio.size() == 1) {
                try {
                    System.out.println("articulo unico, llenar campos en pantalla");
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                  
                    ctlAcc = art.getDebpayacct();
                    codSocio = art.getCardcode();
                    nameSocio = art.getCardname();
                    group = art.getGroupcode();
                    if (varEstados == 1) {
                       llenarListaDetalles();
                        doTotal(); 
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

//<editor-fold defaultstate="collapsed" desc="llenar lista detalles desde concept">
    private void llenarListaDetalles() {
        this.totalFac = 0.0;
        this.totalFacValorAct = 0.0;
        this.lstInicial.clear();
        this.lstTable.clear();

        try {
            this.lstInicial = BankEJBClient.get_ges_colecturiaConcept1(codSocio);
        } catch (Exception ex) {
            Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Object lst : this.lstInicial) {
            ColecturiaDetailTO newCol = new ColecturiaDetailTO();
            ColecturiaConceptTO concept = (ColecturiaConceptTO) lst;

            if (concept.getLinenum() == this.lineNum) {
                //validar get facturas
                if (concept.getFacturas() != null) {
                    for (Object obj : concept.getFacturas()) {
                        SalesTO var = (SalesTO) obj;
                        totalFac = totalFac + var.getDoctotal();
                    }
                    totalFac = Double.parseDouble(formatNumber(totalFac));
                    totalFacValorAct = totalFac;
                    newCol.setCtlaccount(concept.getCtlaccount());
                    newCol.setLinenum(concept.getLinenum());
                    newCol.setDscription(concept.getDscription());
                    newCol.setAcctcode(concept.getAcctcode());
                    newCol.setAcctcode2(concept.getAcctcode2());
                    newCol.setAcctcode3(concept.getAcctcode3());
                    newCol.setConfirmed(concept.getConfirmed());
                    newCol.setDocsubtype(concept.getDocsubtype());
                    newCol.setLinestatus(concept.getLinestatus());
                    newCol.setObjtype(concept.getObjtype());
                    newCol.setOcrcode(concept.getOcrcode());
                    newCol.setPeymethod(concept.getPeymethod());
                    newCol.setPaidsum(totalFac);
                    newCol.setTaxstatus(concept.getTaxstatus());
                    newCol.setAditional_account(concept.getAditional_account());
                    newCol.setFacturas(concept.getFacturas());
                    newCol.setValue1("0.00");
                    newCol.setValue3("0.00");
                    this.lstTable.add(newCol);
                } //else {
                //faceMessage("Sin facturas de Diesel");
                //}

            } else {
                newCol.setCtlaccount(concept.getCtlaccount());
                newCol.setLinenum(concept.getLinenum());
                newCol.setDscription(concept.getDscription());
                newCol.setAcctcode(concept.getAcctcode());
                newCol.setAcctcode2(concept.getAcctcode2());
                newCol.setAcctcode3(concept.getAcctcode3());
                newCol.setConfirmed(concept.getConfirmed());
                newCol.setDocsubtype(concept.getDocsubtype());
                newCol.setLinestatus(concept.getLinestatus());
                newCol.setObjtype(concept.getObjtype());
                newCol.setOcrcode(concept.getOcrcode());
                newCol.setPeymethod(concept.getPeymethod());
                newCol.setPaidsum(concept.getPaidsum());
                newCol.setTaxstatus(concept.getTaxstatus());
                newCol.setAditional_account(concept.getAditional_account());
                try {
                    newCol.setValue1(formatNumber(Double.parseDouble(concept.getValue1())));
                    Double aux = null;
                    if (concept.getConfirmed().equals("Y")) {//SUMA
                        aux = Double.parseDouble(concept.getValue1()) + concept.getPaidsum();
                    } else //Resta
                    {
                        aux = Double.parseDouble(concept.getValue1()) - concept.getPaidsum();
                    }

                    newCol.setValue3(formatNumber(aux));
                } catch (Exception e) {
                    faceMessage(e.getMessage() + " " + e.getCause());
                }
                this.lstTable.add(newCol);
            }
        }

    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="upd contex">
    public void updTotal(){
        //faceMessage("entro");
        doTotal();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Total detalles">
    public void doTotal() {
        //faceMessage("entro upd total");
        this.t2 = 0.0;
        for (Object lst : lstTable) {
            ColecturiaDetailTO det = (ColecturiaDetailTO) lst;
            //faceMessage(det.getValue1());
            if (det.getPaidsum() != null ){//&& this.lineNum != det.getLinenum()) {
                try {
                    Double var = det.getPaidsum();
                    this.t2 = this.t2 + var;
                } catch (Exception e) {
                    faceMessage(e.getMessage() + " " + e.getCause());
                }

            }
        }
        t2 = formatNumber2(t2);
        doTotalAct();
    }

    public void doTotalAct() {
        for (Object lst : lstTable) {
            ColecturiaDetailTO det = (ColecturiaDetailTO) lst;
            if (lineNum != det.getLinenum()) {
                String val = det.getValue1();//.replace(",","");
                if (val != null && val.contains(",")) {
                    val = det.getValue1().replace(",", "");
                }
                if (val != null && det.getConfirmed().equals("Y")) {//SUMA
                    double aux = Double.parseDouble(val) + det.getPaidsum();
                    det.setValue3(formatNumber(aux));
                } else {//Rsta
                    if (val != null) {
                        double aux = Double.parseDouble(val) - det.getPaidsum();
                        det.setValue3(formatNumber(aux));
                    }
                }

            } else {
                det.setValue3("0.00");
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR" > 
    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";

        this.idCheck = true;
        this.common = false;
        this.rendered = false;
        this.documento = "1";
        this.estado = "1";
        this.ifCancelacion = false;
        RequestContext.getCurrentInstance().update("frmColect");
        try {
            reload();
        } catch (IOException ex) {
        }
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        if (varEstados != 3) {
            if (varEstados == 1) {
                this.varEstados = Common.MTTOUPDATE; //2
                this.botonEstado = "Actualizar";

                this.idCheck = true;
                this.common = true;
                this.rendered = true;
                doTotal();
                RequestContext.getCurrentInstance().update("frmprt");
                showHideDialog("dlgPtr", 1);

                RequestContext.getCurrentInstance().update("frmColect");
            } else {
                this.varEstados = Common.MTTOUPDATE; //2
                this.botonEstado = "Actualizar";

                this.idCheck = true;
                this.common = true;
                this.rendered = true;
                doTotal();
                try {
                    reload();
                } catch (IOException ex) {
                }
            }

        } else {
            this.varEstados = Common.MTTOUPDATE; //2
            this.botonEstado = "Actualizar";

            this.idCheck = true;
            this.common = true;
            this.rendered = true;
            try {
                reload();
            } catch (IOException ex) {
            }
        }
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";
        this.documento = null;
        this.estado = null;
        this.idCheck = false;
        this.common = false;
        this.rendered = false;
        RequestContext.getCurrentInstance().update("frmColect");
        try {
            reload();
        } catch (IOException ex) {
        }
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton PRINCIPAL" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                doTotal();
                if (validarColect()) {
                    doSave();
                    //showHideDialog("dlgC2", 1);
                }
                break;

            case 2:
                if (validarColect()) {
                    doUpdate();
                    //showHideDialog("dlgC2", 1);
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

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {
        //if (newColect == null) {
        newColect = new ColecturiaTO();
        //}
        if (BankEJBClient == null) {
            BankEJBClient = new BankEJBClient();
        }
        newColect.setUsersign((int) session.getAttribute("usersign"));
        newColect.setCardcode(codSocio);
        newColect.setCardname(nameSocio);
        newColect.setRef1(equipo + "");
        newColect.setRef2(NoRecibo + "");
        newColect.setDocdate(fechaDoc);
        newColect.setTaxdate(fechaPago);
        newColect.setComments(observaciones);
        newColect.setDoctotal(t2);
        newColect.setPrinted(group);
        newColect.setSeries(Integer.parseInt(documento)); //1 creacion, 2 reversion 
        newColect.setTranstype(Integer.parseInt(estado)); //1 abierto,  2 anulado

        if (ifCancelacion) {
            //newColect.setDocentry(0);
            newColect.setReceiptnum(idAnterior); //pk de donde se genera anulacion
            ArrayList<ColecturiaDetailTO> newPadreLst = new ArrayList<>();
            newPadreLst = newDetails();
            //String vacio = null;
            for (ColecturiaDetailTO obj : newPadreLst) {
                for (ColecturiaDetailTO obj2 : lstTable) {
                    if (obj.getLinenum() == obj2.getLinenum()) {
                        obj.setPaidsum(obj2.getPaidsum());
                        /*obj.setValue1(vacio);
                         obj.setValue2(vacio);
                         obj.setValue3(vacio);*/
                        break;
                    }
                }
            }
            newColect.setColecturiaDetail(lstTable);

            //update al colet de donde se genera anulacion: transtype = 2 (anulado)
        } else {
            String vacio = null;
            lstPadre.clear();
            for (Object obj : lstTable) {
                ColecturiaDetailTO det = (ColecturiaDetailTO) obj;
                try {
                    if (det.getPaidsum() == null || det.getPaidsum() < 0) {
                        det.setPaidsum(0.0);
                    }
                } catch (Exception e) {
                    det.setPaidsum(0.0);
                }
                /*det.setValue1(vacio);
                 det.setValue2(vacio);
                 det.setValue3(vacio);*/
                this.lstPadre.add(det);
            }
            newColect.setColecturiaDetail(lstPadre);
        }

        ResultOutTO _res = new ResultOutTO();

        try {
            _res = BankEJBClient.ges_ges_col0_colecturia_mtto(newColect, Common.MTTOINSERT); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                this.totalFacValorAct = 0.0;
                this.No = _res.getDocentry();
                this.newColect.setDocentry(_res.getDocentry());
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="UPDATE EN BASE">
    public void doUpdate() {
        if (newColect == null) {
            newColect = new ColecturiaTO();
        }
        newColect.setUsersign((int) session.getAttribute("usersign"));
        newColect.setDocentry(No);
        newColect.setCardcode(codSocio);
        newColect.setCardname(nameSocio);
        newColect.setSeries(1);//por defecto 1 al guardar de forma normal, se actualiza al ser cancelada a 2
        newColect.setRef1(equipo + "");
        newColect.setRef2(NoRecibo + "");
        newColect.setDocdate(fechaDoc);
        newColect.setTaxdate(fechaPago);
        newColect.setComments(observaciones);
        newColect.setDoctotal(t2);

        for (Object obj : lstTable) {
            //ColecturiaDetailTO det = (ColecturiaDetailTO) obj;
            this.lstPadre.add(obj);
        }
        newColect.setColecturiaDetail(lstPadre);

        ResultOutTO _res = null;

        try {
            _res = BankEJBClient.ges_ges_col0_colecturia_mtto(newColect, Common.MTTOUPDATE); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                this.No = _res.getDocentry();
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BUSCAR EN BASE">
    public void doSearch() {
        ColecturiaInTO search = new ColecturiaInTO();
        String vacio = null;

        search.setDocentry(No);
        search.setDocdate(fechaDoc);
        search.setTaxdate(fechaPago);

        search.setSeries(serie);

        if (equipo == 0) {
            search.setRef1(vacio);
        } else {
            search.setRef1(equipo + "");
        }

        if (NoRecibo == 0) {
            search.setRef2(vacio);
        } else {
            search.setRef2(NoRecibo + "");
        }

        if (codSocio == null) {
            search.setCardcode(vacio);
        } else {
            search.setCardcode(codSocio);
        }

        if (nameSocio == null) {
            search.setCardname(vacio);
        } else {
            search.setCardname(nameSocio);
        }

        if (observaciones.equals("")) {
            search.setComments(vacio);
        } else {
            search.setComments(observaciones);
        }

        try {
            listaBusqueda = BankEJBClient.get_ges_colecturia(search);
        } catch (Exception e) {
            faceMessage(" Error en la busqueda " + e.getMessage());
        }

        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newColect = (ColecturiaTO) listaBusqueda.get(0);
                try {
                    ColecturiaTO var = BankEJBClient.get_ges_colecturiaByKey(newColect.getDocentry());
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
                        ColecturiaTO var = (ColecturiaTO) receipt;
                        listaBusquedaTable.add(var);

                    } catch (Exception e) {
                        faceMessage(e.getMessage() + " - " + e.getCause());
                    }
                }
                RequestContext.getCurrentInstance().update("colect");
                showHideDialog("dlgListColect", 1);
            }
        } else {
            faceMessage("No se encontraron registros");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones Varias">
    public Double formatNumber2(Double doctotal) {
        DecimalFormat df;
        df = new DecimalFormat("#,###.##");
        return (Math.floor(100 * doctotal) / 100);
    }
    
    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public String formatNumber(Double num) {
        String st = null;
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            st = nf.format(num);
            if (!st.contains(".")) {
                st = st + ".00";
            }
        } catch (Exception e) {
            faceMessage("Error format Number");
        }

        return st;
    }

    public boolean validarClear() {
        try {
            if (codSocio != null || nameSocio != null || serie > 0 || NoRecibo > 0 || equipo > 0 || t2 > 0.0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

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

        RequestContext.getCurrentInstance().update("frmColect");
    }

    public boolean validarColect() {
        if (codSocio == null || nameSocio == null) {
            faceMessage("Seleccione un socio de negocio");
            return false;
        }
        if (fechaDoc == null) {
            faceMessage("Ingrese fecha de documento");
            return false;
        }
        if (fechaPago == null) {
            faceMessage("Ingrese fecha de pago");
            return false;
        }
        if (this.t2 <= 0) {
            faceMessage("Ingrese valores");
            return false;
        }
        return true;
    }

    public void selectDialogBill() {
        if (BankEJBClient == null) {
            BankEJBClient = new BankEJBClient();
        }

        if (newColect == null) {
            newColect = new ColecturiaTO();
        }

        showHideDialog("dlgListColect", 2);
        ColecturiaTO var = (ColecturiaTO) selectC;

        try {
            newColect = BankEJBClient.get_ges_colecturiaByKey(var.getDocentry());
            llenarPantalla(newColect);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(CheckForPaymentBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectC = new ColecturiaTO();
        RequestContext.getCurrentInstance().update("frmColect");

    }

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

    public void llenarPantalla(ColecturiaTO var) {
        this.No = var.getDocentry();
        this.codSocio = var.getCardcode();
        this.nameSocio = var.getCardname();
        this.equipo = Integer.parseInt(var.getRef1());
        this.NoRecibo = Integer.parseInt(var.getRef2());
        this.serie = var.getSeries();
        this.t1 = 0.0;
        this.t2 = var.getDoctotal();
        this.t3 = 0.0;
        this.observaciones = var.getComments();
        this.setFechaDoc(var.getDocdate());
        this.setFechaPago(var.getTaxdate());
        this.group = var.getPrinted();
        
        this.documento = var.getSeries() + "";
        this.estado = var.getTranstype() + "";

        if (varEstados != 1) {
            this.lstTable.clear();
            /*List lstAux = null;
             try {
             lstAux = BankEJBClient.get_ges_colecturiaConcept1(codSocio);
             } catch (Exception ex) {
             Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            for (Object obj : var.getColecturiaDetail()) {
                ColecturiaDetailTO col = (ColecturiaDetailTO) obj;
                this.lstTable.add(col);
                /*for (Object obj2 : lstAux) {
                 ColecturiaConceptTO col2 = (ColecturiaConceptTO) obj2;
                 //ColecturiaDetailTO col = (ColecturiaDetailTO) obj;
                 if (col.getLinenum() != this.lineNum && col.getLinenum() == col2.getLinenum()) {
                 col.setValue1(col2.getValue1());
                 this.lstTable.add(col);
                 break;
                 } else {
                 if (col.getLinenum() == this.lineNum) {
                 this.lstTable.add(col);
                 break;
                 }
                 }
                 }*/
            }
        } else {
            this.lstTable.clear();
            for (Object obj : var.getColecturiaDetail()) {
                ColecturiaDetailTO col = (ColecturiaDetailTO) obj;
                this.lstTable.add(col);
            }
        }

        /*if (varEstados == 3) {
         List lstAux = null;
         try {
         lstAux = BankEJBClient.get_ges_colecturiaConcept1(codSocio);
         } catch (Exception ex) {
         Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         for (Object obj : var.getColecturiaDetail()) {
         for (Object obj2 : lstAux) {
         ColecturiaConceptTO col2 = (ColecturiaConceptTO) obj2;
         ColecturiaDetailTO col = (ColecturiaDetailTO) obj;

         if (col.getLinenum() == col2.getLinenum()) {
         if (col.getLinenum() != this.lineNum) {//17
         col.setValue1(col2.getValue1());
         this.lstTable.add(col);
         break;
         } else {

         for (Object lstfac : col2.getFacturas()) {
         SalesTO var2 = (SalesTO) obj;
         totalFac = totalFac + var2.getDoctotal();
         }
         totalFac = Double.parseDouble(formatNumber(totalFac));
         col.setValue1(totalFac + "");
         //valor de facturas actuales
         this.totalFac = col.getPaidsum();
         this.lstTable.add(col);
         break;
         }
         }
         }
         }
         } else {
         for (Object obj : var.getColecturiaDetail()) {
         ColecturiaDetailTO col = (ColecturiaDetailTO) obj;
         this.lstTable.add(col);
         }
         }*/
    }

    public void cleanBean(int tipo) {

        this.No = 0;
        this.codSocio = null;
        this.nameSocio = null;
        this.equipo = 0;
        this.NoRecibo = 0;
        this.serie = 0;
        this.t1 = 0.0;
        this.t2 = 0.0;
        this.t3 = 0.0;
        this.observaciones = null;

        if (tipo == 1) {
            Date d = new Date();
            this.setFechaDoc(d);
            this.setFechaPago(d);
        } else {
            Date d = null;
            this.setFechaDoc(d);
            this.setFechaPago(d);
        }

        this.lstInicial.clear();
        this.lstPadre.clear();
        this.lstTable.clear();
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Contex anular">
    public void anularColet() {
        if (newColect.getSeries() == 2 || newColect.getTranstype() == 2) {
            faceMessage("No se puede anular");
        } else {
            faceMessage("anular colecturia");
            estateGuardar();
            this.documento = "2";
            this.estado = "1";
            this.ifCancelacion = true;
            this.idAnterior = No;
            this.No = 0;
            RequestContext.getCurrentInstance().update("frmColect");
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funcion auxiliar to save ">
    public ArrayList<ColecturiaDetailTO> newDetails() {
        List newList = null;
        ArrayList<ColecturiaDetailTO> auxList = new ArrayList<>();
        try {
            newList = BankEJBClient.get_ges_colecturiaConcept1(codSocio);
        } catch (Exception ex) {
            Logger.getLogger(ColecturiaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Object lst : newList) {
            ColecturiaDetailTO newCol = new ColecturiaDetailTO();
            ColecturiaConceptTO concept = (ColecturiaConceptTO) lst;

            if (concept.getLinenum() == this.lineNum) {
                //validar get facturas
                if (concept.getFacturas() != null) {
                    for (Object obj : concept.getFacturas()) {
                        SalesTO var = (SalesTO) obj;
                        totalFac = totalFac + var.getDoctotal();
                    }
                    totalFac = Double.parseDouble(formatNumber(totalFac));

                    newCol.setCtlaccount(concept.getCtlaccount());
                    newCol.setLinenum(concept.getLinenum());
                    newCol.setDscription(concept.getDscription());
                    newCol.setAcctcode(concept.getAcctcode());
                    newCol.setAcctcode2(concept.getAcctcode2());
                    newCol.setAcctcode3(concept.getAcctcode3());
                    newCol.setConfirmed(concept.getConfirmed());
                    newCol.setDocsubtype(concept.getDocsubtype());
                    newCol.setLinestatus(concept.getLinestatus());
                    newCol.setObjtype(concept.getObjtype());
                    newCol.setOcrcode(concept.getOcrcode());
                    newCol.setPeymethod(concept.getPeymethod());
                    newCol.setPaidsum(concept.getPaidsum());
                    newCol.setTaxstatus("Y");
                    newCol.setAditional_account("Y");
                    newCol.setFacturas(concept.getFacturas());

                    auxList.add(newCol);
                } else {
                    faceMessage("Sin facturas de Diesel");
                }

            } else {
                newCol.setCtlaccount(concept.getCtlaccount());
                newCol.setLinenum(concept.getLinenum());
                newCol.setDscription(concept.getDscription());
                newCol.setAcctcode(concept.getAcctcode());
                newCol.setAcctcode2(concept.getAcctcode2());
                newCol.setAcctcode3(concept.getAcctcode3());
                newCol.setConfirmed(concept.getConfirmed());
                newCol.setDocsubtype(concept.getDocsubtype());
                newCol.setLinestatus(concept.getLinestatus());
                newCol.setObjtype(concept.getObjtype());
                newCol.setOcrcode(concept.getOcrcode());
                newCol.setPeymethod(concept.getPeymethod());
                newCol.setPaidsum(concept.getPaidsum());
                newCol.setTaxstatus("Y");
                newCol.setAditional_account("Y");

                auxList.add(newCol);
            }
        }
        return auxList;
    }//final funcion

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getTotalFacValorAct() {
        return totalFacValorAct;
    }

    public void setTotalFacValorAct(Double totalFacValorAct) {
        this.totalFacValorAct = totalFacValorAct;
    }

    public int getIdAnterior() {
        return idAnterior;
    }

    public void setIdAnterior(int idAnterior) {
        this.idAnterior = idAnterior;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isIfCancelacion() {
        return ifCancelacion;
    }

    public void setIfCancelacion(boolean ifCancelacion) {
        this.ifCancelacion = ifCancelacion;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        ColecturiaBean.AdminEJBService = AdminEJBService;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<CatalogTO> getLstTipoDoc() {
        return lstTipoDoc;
    }

    public void setLstTipoDoc(List<CatalogTO> lstTipoDoc) {
        this.lstTipoDoc = lstTipoDoc;
    }

    public ParameterEJBClient getParameterEJBClient() {
        return ParameterEJBClient;
    }

    public void setParameterEJBClient(ParameterEJBClient ParameterEJBClient) {
        this.ParameterEJBClient = ParameterEJBClient;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public Double getTotalFac() {
        return totalFac;
    }

    public void setTotalFac(Double totalFac) {
        this.totalFac = totalFac;
    }

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        ColecturiaBean.CatalogEJB = CatalogEJB;
    }

    public static BankEJBClient getBankEJBClient() {
        return BankEJBClient;
    }

    public static void setBankEJBClient(BankEJBClient BankEJBClient) {
        ColecturiaBean.BankEJBClient = BankEJBClient;
    }

    public List getLstInicial() {
        return lstInicial;
    }

    public void setLstInicial(List lstInicial) {
        this.lstInicial = lstInicial;
    }

    public List getLstPadre() {
        return lstPadre;
    }

    public void setLstPadre(List lstPadre) {
        this.lstPadre = lstPadre;
    }

    public int getVarEstados() {
        return varEstados;
    }

    public void setVarEstados(int varEstados) {
        this.varEstados = varEstados;
    }

    public ColecturiaTO getNewColect() {
        return newColect;
    }

    public void setNewColect(ColecturiaTO newColect) {
        this.newColect = newColect;
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

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public int getToolbarBoton() {
        return toolbarBoton;
    }

    public void setToolbarBoton(int toolbarBoton) {
        this.toolbarBoton = toolbarBoton;
    }

    public String getCtlAcc() {
        return ctlAcc;
    }

    public void setCtlAcc(String ctlAcc) {
        this.ctlAcc = ctlAcc;
    }

    public ArrayList<ColecturiaTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<ColecturiaTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public ColecturiaTO getSelectC() {
        return selectC;
    }

    public void setSelectC(ColecturiaTO selectC) {
        this.selectC = selectC;
    }

    public String getBotonEstado() {
        return botonEstado;
    }

    public void setBotonEstado(String botonEstado) {
        this.botonEstado = botonEstado;
    }

    public boolean isIdCheck() {
        return idCheck;
    }

    public void setIdCheck(boolean idCheck) {
        this.idCheck = idCheck;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public Double getT1() {
        return t1;
    }

    public void setT1(Double t1) {
        this.t1 = t1;
    }

    public Double getT2() {
        return t2;
    }

    public void setT2(Double t2) {
        this.t2 = t2;
    }

    public Double getT3() {
        return t3;
    }

    public void setT3(Double t3) {
        this.t3 = t3;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int No) {
        this.No = No;
    }

    public Date getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(Date fechaDoc) {
        this.fechaDoc = fechaDoc;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getNoRecibo() {
        return NoRecibo;
    }

    public void setNoRecibo(int NoRecibo) {
        this.NoRecibo = NoRecibo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ArrayList<ColecturiaDetailTO> getLstTable() {
        return lstTable;
    }

    public void setLstTable(ArrayList<ColecturiaDetailTO> lstTable) {
        this.lstTable = lstTable;
    }

    public ColecturiaDetailTO getSelectDet() {
        return selectDet;
    }

    public void setSelectDet(ColecturiaDetailTO selectDet) {
        this.selectDet = selectDet;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="IMPRIMIR FORMA 2">
    public String printInvoice() throws UnsupportedEncodingException {
        //faceMessage(getApplicationUri());
        //System.out.println(getApplicationUri()+"||----------------------------------------------------------------");
        setUrl(getApplicationUri());
        String nombre = session.getAttribute("username").toString().toUpperCase();
        if (newColect.getDocentry() > 0) {
            String foo = newColect.getDocentry() + "";
            String bar = nombre;
            return "/PrintColectView?faces-redirect=true"
                    + "&foo=" + URLEncoder.encode(foo, "UTF-8")
                    + "&bar=" + URLEncoder.encode(bar, "UTF-8");
        } else {
            faceMessage("No se puede imprimir");
            return "/view/bank/Colecturia.xhtml";
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="redirect">
    public void redirect() throws IOException {
        String url2 = getUrl() + "/faces/view/bank/Colecturia.xhtml"; //url donde se redirige la pantalla
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
