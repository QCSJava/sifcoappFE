/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.sales.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.client.SalesEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.sales.to.SalesDetailTO;
import com.sifcoapp.objects.sales.to.SalesInTO;
import com.sifcoapp.objects.sales.to.SalesTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Digits;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "salesDieselBean")
@ApplicationScoped
public class SalesDieselBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaración de variables para formulario" >
    //Servicios EJB
    private static CatalogEJBClient CatalogEJB;
    private static AdminEJBClient AdminEJBService;
    private static SalesEJBClient SalesEJBService;
    ParameterEJBClient ParameterEJBClient;

    //__________________________________________________________________________
    //Espacio entre componentes depntalla
    private int spacer = 3; //<p:spacer>

    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();

    //__________________________________________________________________________
    //Nueva Factura
    private SalesTO newBill = new SalesTO();        //Nueva Factura
    private SalesDetailTO newDetalle = new SalesDetailTO();
    private SalesTO selectBill = new SalesTO();        //Select de lista busqueda
    private BusinesspartnerTO selectSocio = new BusinesspartnerTO();

    //__________________________________________________________________________
    //Encabezado
    private int docEntry;           //No visible
    private int docNum;             //No.
    private String estadoDoc;       //Estado de Documento
    private String socioNeg;        //Socio de Negocio
    private String codSocio;        //Codigo de Socio
    private int equipo;             //Equipo
    private String refe;            //Referencia

    private Date fechaConta = new Date();   //Fecha Contabilizacion

    private int tipoDoc;         //Tipo Documento
    private int formaPago;       //Forma de Pago
    private String alm;             //Almacen

    private String nomVendedor;     //Nombre del Vendedor
    private String ctlAcc;
    //__________________________________________________________________________
    //Detalles
    private String newCod;          //Codigo
    private String newNomArt;       //Nombre Articulo

    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private Double newCantidad;     //Cantidad

    private Double newPrecio;       //Precio
    private Double newTotal;        //Total de detalle
    private String newUnidad;       //Unidad de Medida
    private Double newExistencia;   //Existencia

    //__________________________________________________________________________
    //Impuestos y Total
    private Double gravadas;        //Gravadas
    private Double exentas;         //Exentas
    private Double IVA;             //IVA
    private Double retencion;       //Retencion
    private Double fovial;          //Fovial
    private Double cotrans;         //COTRANS
    private Double total;           //TOTAL

    //__________________________________________________________________________
    //ComboBox
    /*private static final String CATALOGOTYPEDOC = "TypesInvoices";  //tipo doc
     private static final String CATALOGOTYPEPAY = "TypesPayment";  //tipo de pago

     private List<CatalogTO> lstTipoDoc;     //llenar cmb tipo de documento
     private List<CatalogTO> lstTipoPago;    //llenar cmb tipo de pago
     private List<BranchTO> listaAlmacenes;  //llenar cmb de almacenes
     private List<DocStatusTO> lstEstados;   //llenar cmb de estados
     */
    //__________________________________________________________________________
    //manejo de estados
    //Estado principal Guardar, Update, Buscar
    private int varEstados;
    private String botonEstado;
    private boolean confirm;
    private int toolbarBoton;

    //habilitacion forma de pago y almacen
    private boolean disabledDocNum, disabledSearch, disabledComun;

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private ArrayList<SalesTO> listaBusquedaTable = new ArrayList<>();

    public boolean isDisabledDocNum() {
        return disabledDocNum;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        SalesDieselBean.CatalogEJB = CatalogEJB;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        SalesDieselBean.AdminEJBService = AdminEJBService;
    }

    public static SalesEJBClient getSalesEJBService() {
        return SalesEJBService;
    }

    public static void setSalesEJBService(SalesEJBClient SalesEJBService) {
        SalesDieselBean.SalesEJBService = SalesEJBService;
    }

    public ParameterEJBClient getParameterEJBClient() {
        return ParameterEJBClient;
    }

    public void setParameterEJBClient(ParameterEJBClient ParameterEJBClient) {
        this.ParameterEJBClient = ParameterEJBClient;
    }

    public SalesDetailTO getNewDetalle() {
        return newDetalle;
    }

    public void setNewDetalle(SalesDetailTO newDetalle) {
        this.newDetalle = newDetalle;
    }

    public String getCtlAcc() {
        return ctlAcc;
    }

    public void setCtlAcc(String ctlAcc) {
        this.ctlAcc = ctlAcc;
    }

    public boolean isDisabledComun() {
        return disabledComun;
    }

    public void setDisabledComun(boolean disabledComun) {
        this.disabledComun = disabledComun;
    }

    public boolean isDisabledSearch() {
        return disabledSearch;
    }

    public void setDisabledSearch(boolean disabledSearch) {
        this.disabledSearch = disabledSearch;
    }

    public void setDisabledDocNum(boolean disabledDocNum) {
        this.disabledDocNum = disabledDocNum;
    }

    public int getSpacer() {
        return spacer;
    }

    public void setSpacer(int spacer) {
        this.spacer = spacer;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public SalesTO getNewBill() {
        return newBill;
    }

    public void setNewBill(SalesTO newBill) {
        this.newBill = newBill;
    }

    public SalesTO getSelectBill() {
        return selectBill;
    }

    public void setSelectBill(SalesTO selectBill) {
        this.selectBill = selectBill;
    }

    public BusinesspartnerTO getSelectSocio() {
        return selectSocio;
    }

    public void setSelectSocio(BusinesspartnerTO selectSocio) {
        this.selectSocio = selectSocio;
    }

    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public String getEstadoDoc() {
        return estadoDoc;
    }

    public void setEstadoDoc(String estadoDoc) {
        this.estadoDoc = estadoDoc;
    }

    public String getSocioNeg() {
        return socioNeg;
    }

    public void setSocioNeg(String socioNeg) {
        this.socioNeg = socioNeg;
    }

    public String getCodSocio() {
        return codSocio;
    }

    public void setCodSocio(String codSocio) {
        this.codSocio = codSocio;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public String getRefe() {
        return refe;
    }

    public void setRefe(String refe) {
        this.refe = refe;
    }

    public Date getFechaConta() {
        return fechaConta;
    }

    public void setFechaConta(Date fechaConta) {
        this.fechaConta = fechaConta;
    }

    public int getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public int getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(int formaPago) {
        this.formaPago = formaPago;
    }

    public String getAlm() {
        return alm;
    }

    public void setAlm(String alm) {
        this.alm = alm;
    }

    public String getNomVendedor() {
        return nomVendedor;
    }

    public void setNomVendedor(String nomVendedor) {
        this.nomVendedor = nomVendedor;
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

    public Double getNewCantidad() {
        return newCantidad;
    }

    public void setNewCantidad(Double newCantidad) {
        this.newCantidad = newCantidad;
    }

    public Double getNewPrecio() {
        return newPrecio;
    }

    public void setNewPrecio(Double newPrecio) {
        this.newPrecio = newPrecio;
    }

    public Double getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(Double newTotal) {
        this.newTotal = newTotal;
    }

    public String getNewUnidad() {
        return newUnidad;
    }

    public void setNewUnidad(String newUnidad) {
        this.newUnidad = newUnidad;
    }

    public Double getNewExistencia() {
        return newExistencia;
    }

    public void setNewExistencia(Double newExistencia) {
        this.newExistencia = newExistencia;
    }

    public Double getGravadas() {
        return gravadas;
    }

    public void setGravadas(Double gravadas) {
        this.gravadas = gravadas;
    }

    public Double getExentas() {
        return exentas;
    }

    public void setExentas(Double exentas) {
        this.exentas = exentas;
    }

    public Double getIVA() {
        return IVA;
    }

    public void setIVA(Double IVA) {
        this.IVA = IVA;
    }

    public Double getRetencion() {
        return retencion;
    }

    public void setRetencion(Double retencion) {
        this.retencion = retencion;
    }

    public Double getFovial() {
        return fovial;
    }

    public void setFovial(Double fovial) {
        this.fovial = fovial;
    }

    public Double getCotrans() {
        return cotrans;
    }

    public void setCotrans(Double cotrans) {
        this.cotrans = cotrans;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public int getToolbarBoton() {
        return toolbarBoton;
    }

    public void setToolbarBoton(int toolbarBoton) {
        this.toolbarBoton = toolbarBoton;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<SalesTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<SalesTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Load de pantalla">
    @PostConstruct
    public void initForm() {
        //variables de estados habilitados/desabilitados

        this.disabledDocNum = true;
        this.disabledSearch = false;
        this.disabledComun = true;

        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }

        if (SalesEJBService == null) {
            SalesEJBService = new SalesEJBClient();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }

        //llenar nombre de vendedor
        this.nomVendedor = session.getAttribute("userfullname").toString();

        estateGuardar();

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Seleccionar de autocomplete de Socio, Name o Cod">
    public void selectSocio(SelectEvent event) {
        List socio = new Vector();
        String var = null;

        if (selectSocio == null) {
            selectSocio = new BusinesspartnerTO();
        }

        if (event.getObject().toString() != var) {
            List _result = null;

            BusinesspartnerInTO in = new BusinesspartnerInTO();
            //in.setCardcode(codSocio);
            in.setCardname(socioNeg);

            try {
                _result = CatalogEJB.get_businesspartner(in);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
                codSocio = null;
                socioNeg = null;
            }
            if (_result.isEmpty()) {
                this.codSocio = null;
                this.socioNeg = null;

            } else {
                Iterator<BusinesspartnerTO> iterator = _result.iterator();
                while (iterator.hasNext()) {
                    BusinesspartnerTO articulo = (BusinesspartnerTO) iterator.next();
                    socio.add(articulo);
                    this.setSelectSocio(articulo);//----------------------------
                }
                if (socio.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                        ctlAcc = art.getDebpayacct();
                        codSocio = art.getCardcode();
                        socioNeg = art.getCardname();

                    } catch (Exception ex) {
                        Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                    ctlAcc = art.getDebpayacct();
                    codSocio = art.getCardcode();
                    socioNeg = art.getCardname();
                    faceMessage("Error: Mas de un elemento encontrado, nombre de articulo repetido");
                }
            }
        }

    }

    public void selectSocioCod(SelectEvent event) {
        List socio = new Vector();
        String var = null;

        if (selectSocio == null) {
            selectSocio = new BusinesspartnerTO();
        }

        if (event.getObject().toString() != var) {
            List _result = null;

            BusinesspartnerInTO in = new BusinesspartnerInTO();
            in.setCardcode(codSocio);
            //in.setCardname(socioNeg);

            try {
                _result = CatalogEJB.get_businesspartner(in);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
                codSocio = null;
                socioNeg = null;
            }
            if (_result.isEmpty()) {
                this.codSocio = null;
                this.socioNeg = null;

            } else {
                Iterator<BusinesspartnerTO> iterator = _result.iterator();
                while (iterator.hasNext()) {
                    BusinesspartnerTO articulo = (BusinesspartnerTO) iterator.next();
                    socio.add(articulo);
                    this.setSelectSocio(articulo);//----------------------------
                }
                if (socio.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                        ctlAcc = art.getDebpayacct();
                        codSocio = art.getCardcode();
                        socioNeg = art.getCardname();

                    } catch (Exception ex) {
                        Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                    ctlAcc = art.getDebpayacct();
                    codSocio = art.getCardcode();
                    socioNeg = art.getCardname();
                    faceMessage("Error: Mas de un elemento encontrado, nombre de articulo repetido");
                }
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.estadoDoc = Common.DocStatusOpen;
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";

        this.disabledDocNum = true;
        this.disabledSearch = false;
        this.disabledComun = false;

        RequestContext.getCurrentInstance().update("frmSalesDiesel");

    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";

        this.disabledDocNum = true;//false
        this.disabledSearch = true;
        this.disabledComun = true;

        RequestContext.getCurrentInstance().update("frmSalesDiesel");
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";

        this.estadoDoc = null;

        this.disabledDocNum = false;
        this.disabledSearch = false;
        this.disabledComun = true;

        RequestContext.getCurrentInstance().update("frmSalesDiesel");
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Botones toolbar " > 
    public void botonNuevo(ActionEvent actionEvent) {
        if (validarClear()) {
            toolbarBoton = 1;
            showHideDialog("dlgC1", 1);
        } else {
            cleanBean(1);  //1:deja fecha atual, 2:borra fecha
            estateGuardar();
        }

    }

    public void botonBuscar(ActionEvent actionEvent) {

        if (validarClear() && varEstados != 2) {
            toolbarBoton = 2;
            showHideDialog("dlgC1", 1);
        } else {
            cleanBean(2);
            estateBuscar();
        }
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton PRINCIPAL" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                if (validarNewBill()) {
                    showHideDialog("dlgC2", 1);
                    //doSave();
                }
                break;

            case 2:
                if (validarNewBill()) {
                    showHideDialog("dlgC2", 1);
                    //doUpdate();
                }
                break;

            case 3:
                doSearch();
                break;

            default:
                break;

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {

        if (newBill == null) {
            newBill = new SalesTO();
        }

        //newBill.setDocstatus(estadoDoc);
        newBill.setUsersign((int) session.getAttribute("usersign"));
        newBill.setCardname(socioNeg);
        newBill.setCardcode(codSocio);
        newBill.setRef2("" + equipo);
        newBill.setRef1(refe);
        newBill.setDocdate(fechaConta);
        newBill.setTaxdate(fechaConta);
        newBill.setSeries(Integer.parseInt(ParameterEJBClient.getParameterbykey(2).getValue1()));
        newBill.setPeymethod(ParameterEJBClient.getParameterbykey(5).getValue1());
        newBill.setTowhscode(ParameterEJBClient.getParameterbykey(4).getValue1());
        //newBill.setComments(coment);
        newBill.setCtlaccount(ctlAcc);
        Double gTotalPadre = newDetalle.getGtotal(); //getgtotal
        Double sumImp = newDetalle.getVatsum();      //getvatsum

        newBill.setVatsum(sumImp);
        newBill.setDoctotal(gTotalPadre);
        List listaPadre = new Vector();
        listaPadre.add(newDetalle);
        newBill.setSalesDetails(listaPadre);

        try {
            ResultOutTO _res;
            _res = SalesEJBService.inv_Sales_mtto(newBill, Common.MTTOINSERT); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                docEntry = _res.getDocentry();
                docNum = docEntry; //
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="UPDATE EN BASE">
    public void doUpdate() {
        if (newBill == null) {
            newBill = new SalesTO();
        }
        String vacio = null;

        if (varEstados == 2) {
            try {
                newBill = SalesEJBService.getSalesByKey(docEntry);
            } catch (Exception ex) {
                Logger.getLogger(SalesDieselBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        newBill.setDocentry(docEntry);
        newBill.setDocnum(docNum);
        newBill.setCtlaccount(ctlAcc);
        newBill.setUsersign((int) session.getAttribute("usersign"));
        newBill.setCardname(socioNeg);
        newBill.setCardcode(codSocio);
        newBill.setRef2("" + equipo);

        if (refe.equals("")) {
            newBill.setRef1(vacio);
        } else {
            newBill.setRef1(refe);
        }

        try {
            ResultOutTO _res;
            _res = SalesEJBService.inv_Sales_mtto(newBill, Common.MTTOUPDATE); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                // docEntry = _res.getDocentry();
                // docNum = docEntry; //
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje() + " Error al guardar");
            }
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BUSCAR EN BASE">
    public void doSearch() {
        listaBusqueda.clear();
        listaBusquedaTable.clear();
        SalesInTO searchBill = new SalesInTO();
        searchBill.setSeries(3);
        searchBill.setDocnum(docNum);
        searchBill.setCardname(socioNeg);
        searchBill.setCardcode(codSocio);

        String var1 = "" + equipo;
        String vacio = null;

        if (var1.equals("0")) {
            searchBill.setRef2(vacio);
        } else {
            searchBill.setRef2("" + equipo);
        }

        if (refe.equals("")) {
            searchBill.setRef1(vacio);
        } else {
            searchBill.setRef1(refe);
        }

        searchBill.setDocdate(fechaConta);

        try {
            listaBusqueda = SalesEJBService.getSales(searchBill);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newBill = (SalesTO) listaBusqueda.get(0);
                try {

                    SalesTO var = SalesEJBService.getSalesByKey(newBill.getDocentry());
                    llenarPantalla(var);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }
            } else {
                faceMessage("Seleccione un elemento");

                for (Object receipt : listaBusqueda) {
                    try {
                        SalesTO var = (SalesTO) receipt;

                        listaBusquedaTable.add(var);

                    } catch (Exception e) {
                        faceMessage(e.getMessage() + " - " + e.getCause());
                    }
                }
                showHideDialog("dlgListBill", 1);

            }
        } else {
            faceMessage("No se encontraron Registros");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Calculo de impuestos">
    public void calcularImp() {
        try {

            if (this.newCantidad > 0) {

                //SalesDetailTO newDetalle = new SalesDetailTO();
                ArticlesTO thisArt = new ArticlesTO();
                CatalogTO thisCat = new CatalogTO();
                String codDiesel, formaPago2;

                //temporal
                try {
                    codDiesel = ParameterEJBClient.getParameterbykey(3).getValue1();
                    setNewCod(codDiesel);

                    thisArt = AdminEJBService.getArticlesByKey(newCod);
                    this.selectSocio = new BusinesspartnerTO();
                    this.selectSocio = CatalogEJB.get_businesspartnerBykey(codSocio);

                    if (selectSocio.getGroupcode().equals("1")) {
                        formaPago2 = ParameterEJBClient.getParameterbykey(5).getValue1();
                    } else {
                        formaPago2 = ParameterEJBClient.getParameterbykey(5).getValue2();
                        //setNewPrecio(thisArt.getPrice(Integer.parseInt(ParameterEJBClient.getParameterbykey(5).getValue2())));
                    }

                    CatalogTO cat = AdminEJBService.findCatalogByKey(formaPago2, 8);
                    int listP = Integer.parseInt(cat.getCatvalue2());
                    setNewPrecio(thisArt.getPrice(listP));

                    setNewUnidad(thisArt.getBuyUnitMsr());
                    setNewNomArt(thisArt.getItemName());

                    newDetalle.setItemcode(newCod);
                    newDetalle.setDscription(newNomArt);
                    newDetalle.setQuantity(newCantidad);

                    newDetalle.setPrice(newPrecio);
                    newDetalle.setPricebefdi(newPrecio);

                    newDetalle.setLinetotal(newPrecio * newCantidad);
                    newDetalle.setUnitmsr(newUnidad);
                    newDetalle.setLinenum(1);//UUID.randomUUID().hashCode());

                    if (thisArt.getWtliable().equals("Y")) {
                        newDetalle.setTaxstatus("Y");
                        faceMessage("se calcularan impuestos");

                        if (thisArt.getVatgourpsa().equals("FOV")) {
                            //faceMessage("articulo aplica: iva, fov, cotrans");
                            thisCat = (CatalogTO) thisArt.getVatgourpsaList().get(0);
                            Double impIVA = (Integer.parseInt(thisCat.getCatvalue()) + 0.0) / 100; //%de IVA
                            Double impFOV = Double.parseDouble(thisCat.getCatvalue2());
                            Double impCOT = Double.parseDouble(thisCat.getCatvalue3());

                            newDetalle.setVatgroup(impIVA + "|" + impFOV + "|" + impCOT); //valor de los ipuestos

                            Double impArt = ((newPrecio * newCantidad) * impIVA) + (newCantidad * impFOV) + (newCantidad * impCOT);
                            //Double descuentos = 0.0;

                            newDetalle.setPriceafvat((newPrecio) + (newPrecio * impIVA) + (impFOV) + (impCOT)); //precio bruto  
                            //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                            newDetalle.setVatsum(impArt);//suma total de impuestos aplicados
                            newDetalle.setGrssprofit(newDetalle.getLinetotal() - (thisArt.getAvgPrice() * newDetalle.getQuantity()));//precio venta - costo
                            newDetalle.setTaxcode(thisArt.getVatgourpsa());//FOV
                            newDetalle.setVatappld(newDetalle.getVatsum());//igual a vatsum
                            newDetalle.setStockpricestockprice(thisArt.getAvgPrice());//
                            newDetalle.setGtotal(newDetalle.getPriceafvat() * newDetalle.getQuantity());
                            newDetalle.setAcctcode("cuenta");//pendiente---------------------------------------------------------

                        } else {
                            //faceMessage("articulo aplica a X impuesto de descripcion1");
                            thisCat = (CatalogTO) thisArt.getVatgourpsaList().get(0);
                            Double impIVA = (Integer.parseInt(thisCat.getCatvalue()) + 0.0) / 100; //%de IVA
                            Double impArt = (newPrecio * newCantidad) * impIVA; //(precio unitario * cantidad) * 0.13%
                            Double impCOT = 0.0;
                            Double impFOV = 0.0;
                            //Double descuentos = 0.0;
                            newDetalle.setVatgroup(impIVA + "|" + impFOV + "|" + impCOT);
                            newDetalle.setPriceafvat((newPrecio) + (newPrecio * impIVA)); //total + impuesto de iva
                            //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                            newDetalle.setVatsum(impArt);//suma total de impuestos aplicados
                            newDetalle.setGrssprofit(newDetalle.getLinetotal() - (thisArt.getAvgPrice() * newDetalle.getQuantity()));//precio venta - costo
                            newDetalle.setTaxcode(thisArt.getVatgourpsa());//IVA u otro tipo
                            newDetalle.setVatappld(newDetalle.getVatsum());//igual a vatsum
                            newDetalle.setStockpricestockprice(thisArt.getAvgPrice());//
                            newDetalle.setGtotal(newDetalle.getPriceafvat() * newDetalle.getQuantity());
                            newDetalle.setAcctcode("cuenta");//pendiente---------------------------------------------------------

                        }
                    } else {
                        newDetalle.setTaxstatus("N");
                        //faceMessage("Articulo exento de impuestos");
                        Double impIVA = 0.0; //%de IVA
                        Double impArt = 0.0; //(precio unitario * cantidad) * 0.13%
                        Double impCOT = 0.0;
                        Double impFOV = 0.0;
                        //Double descuentos = 0.0;
                        newDetalle.setVatgroup(impIVA + "|" + impFOV + "|" + impCOT);
                        newDetalle.setPriceafvat((newPrecio) + (newPrecio * impIVA)); //total + impuesto de iva
                        //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                        newDetalle.setVatsum(impArt);//suma total de impuestos aplicados
                        newDetalle.setGrssprofit(newDetalle.getLinetotal() - (thisArt.getAvgPrice() * newDetalle.getQuantity()));//precio venta - costo
                        newDetalle.setTaxcode(thisArt.getVatgourpsa());//IVA u otro tipo
                        newDetalle.setVatappld(newDetalle.getVatsum());//igual a vatsum
                        newDetalle.setStockpricestockprice(thisArt.getAvgPrice());//
                        newDetalle.setGtotal(newDetalle.getPriceafvat() * newDetalle.getQuantity());
                        newDetalle.setAcctcode("cuenta");//pendiente---------------------------------------------------------

                    }
                } catch (Exception e) {
                }
                calcularTotalBill(newDetalle); //calculos por cada articulo agregado
            }
        } catch (Exception e) {
        }
        //cleanDetalle();
        //this.newCantidad = null;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Calcular Impuestos y TOTAL">
    public void calcularTotalBill(SalesDetailTO detail) {
        Double totalAux = 0.0;

        this.gravadas = calcularGravadas(detail);
        this.exentas = calcularExentas(detail);
        this.IVA = calcularIVA(detail);
        this.retencion = calcularRetencion(detail);
        this.fovial = calcularFovial(detail);
        this.cotrans = calcularCotrans(detail);

        totalAux = (this.gravadas + this.exentas + this.IVA + this.retencion + this.fovial + this.cotrans);

        this.total = totalAux;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="funciones para calculos de impuestos">
    public Double calcularGravadas(SalesDetailTO detail) {

        return detail.getLinetotal();
    }

    public Double calcularExentas(SalesDetailTO detail) {
        return 0.0;

    }

    public Double calcularIVA(SalesDetailTO detail) {
        Double sumIVA = 0.0;
        ArrayList<SalesDetailTO> listaArt = new ArrayList<>();
        listaArt.add(detail);
        //Double sumTotal = 0.0;
        for (SalesDetailTO det : listaArt) {
            ArticlesTO art = new ArticlesTO();
            try {
                art = AdminEJBService.getArticlesByKey(det.getItemcode());
                // CatalogTO imp = new CatalogTO();
                //imp = (CatalogTO) art.getVatgourpsaList().get(0);

                if (art.getVatgourpsa().equals("FOV")) {
                    String[] valorImp = det.getVatgroup().split("\\|");
                    Double impIVA = Double.parseDouble(valorImp[0]);//0: IVA 1: FOV 2:COT
                    //Double impIVA = (aux+0.0)/100;

                    sumIVA = sumIVA + ((impIVA * det.getPrice()) * det.getQuantity());
                } else {
                    sumIVA = sumIVA + det.getVatsum();
                }

            } catch (Exception e) {
            }

        }

        //sumIVA = sumTotal * 0.13;
        return sumIVA;
    }

    public Double calcularRetencion(SalesDetailTO detail) {
        return 0.0;
    }

    public Double calcularFovial(SalesDetailTO detail) {
        Double sumFovial = 0.0;
        ArrayList<SalesDetailTO> listaArt = new ArrayList<>();
        listaArt.add(detail);
        for (SalesDetailTO det : listaArt) {
            ArticlesTO art = new ArticlesTO();
            try {
                art = AdminEJBService.getArticlesByKey(det.getItemcode());
                CatalogTO imp = new CatalogTO();
                imp = (CatalogTO) art.getVatgourpsaList().get(0);

                if (art.getVatgourpsa().equals(imp.getCatcode())) {
                    String[] valorImp = det.getVatgroup().split("\\|");
                    Double impFov = Double.parseDouble(valorImp[1]);//0: IVA 1: FOV 2:COT
                    //Double valorImp = impFov;
                    sumFovial = sumFovial + (impFov * det.getQuantity());
                }

            } catch (Exception ex) {
                faceMessage("Error de calculo Fovial");
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sumFovial;

    }

    public Double calcularCotrans(SalesDetailTO detail) {
        Double sumCotrans = 0.0;
        ArrayList<SalesDetailTO> listaArt = new ArrayList<>();
        listaArt.add(detail);
        for (SalesDetailTO det : listaArt) {
            ArticlesTO art = new ArticlesTO();
            try {
                art = AdminEJBService.getArticlesByKey(det.getItemcode());
                CatalogTO imp = new CatalogTO();
                imp = (CatalogTO) art.getVatgourpsaList().get(0);

                if (art.getVatgourpsa().equals(imp.getCatcode())) {
                    String[] valorImp = det.getVatgroup().split("\\|");
                    Double impCot = Double.parseDouble(valorImp[2]);//0: IVA 1: FOV 2:COT
                    //Double valorImp = Double.parseDouble(imp.getCatvalue3());
                    sumCotrans = sumCotrans + (impCot * det.getQuantity());
                }

            } catch (Exception ex) {
                faceMessage("Error de calculo Cotrans");
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return sumCotrans;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    //evento al seleccionar un elemento del dialogo facturas
    public void selectDialogBill() {
        if (SalesEJBService == null) {
            SalesEJBService = new SalesEJBClient();
        }

        if (newBill == null) {
            newBill = new SalesTO();
        }

        showHideDialog("dlgListBill", 2);
        SalesTO var = (SalesTO) selectBill;

        try {
            newBill = SalesEJBService.getSalesByKey(var.getDocentry());
            llenarPantalla(newBill);
            //llenarNewCredit();
            //setCreditNote();
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        try {
            //doSetCreditNote();
        } catch (Exception e) {
            faceMessage(e.getMessage() + " - " + e.getCause());
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectBill = new SalesTO();
        RequestContext.getCurrentInstance().update("frmSales");

    }

    public void llenarPantalla(SalesTO var) {
        setEstadoDoc(var.getDocstatus());
        setDocEntry(var.getDocentry());
        setDocNum(var.getDocnum());
        setSocioNeg(var.getCardname());
        setCodSocio(var.getCardcode());
        setEquipo(Integer.parseInt(var.getRef2()));
        setRefe(var.getRef1());

        setFechaConta(var.getDocdate());

        SalesDetailTO detail;
        detail = (SalesDetailTO) var.getSalesDetails().get(0);
        setNewCantidad(detail.getQuantity());
        calcularTotalBill(detail);
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
        RequestContext.getCurrentInstance().update("frmSales");
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC1').hide();");
    }

    public void cleanBean(int tipo) {
        //cleanDetalle();

        //variables desabilitadas
        this.disabledComun = false;

        //encabezado
        this.docNum = 0;
        this.socioNeg = null;
        this.codSocio = null;
        this.equipo = 0;
        this.refe = null;
        if (tipo == 1) {
            Date d = new Date();
            this.setFechaConta(d);
        } else {
            Date d = null;
            this.setFechaConta(d);
        }

        this.newCantidad = null;
        //valorescalculados
        this.gravadas = 0.0;
        this.exentas = 0.0;
        this.IVA = 0.0;
        this.retencion = 0.0;
        this.fovial = 0.0;
        this.cotrans = 0.0;
        this.total = 0.0;

        this.selectSocio = null;
    }

    public boolean validarClear() {
        return doClean() && this.varEstados != 2;// && this.listaBusquedaTable.size() >=1;     
    }

    public boolean doClean() {
        if (equipo > 0) {
            return true;
        }
        if (newCantidad != null) {
            return true;
        }
        try {
            if (socioNeg != null || codSocio != null || !refe.equals("")) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " -- " + e.getCause());
            return false;
        }
        return false;
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC2').hide();");
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

    //validar nueva factura
    public boolean validarNewBill() {
        if (socioNeg == null && codSocio == null || socioNeg == null || codSocio == null) {
            faceMessage("Seleccione un Socio de Negocio ó Codigo de Socio");
            return false;
        }
        if (fechaConta == null) {
            faceMessage("Seleccione una fecha");
            return false;
        }
        if (this.newCantidad == null || this.newCantidad <= 0) {
            faceMessage("Ingrese una cantidad de diesel correcta");
            return false;
        }

        return true;
    }

    //mostrar un mensaje en pantalla dado un string
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

}//cierre de clse
