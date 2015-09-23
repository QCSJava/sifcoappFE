/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.purchase.bean;

import com.sifco.login.bean.Util;
import com.sifco.businesspartner.bean.BusinessPartner;
import com.sifcoapp.assignment.bean.AccassignmentBean;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.PurchaseEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.ArticlesInTO;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.BranchArticlesTO;
import com.sifcoapp.objects.admin.to.BranchTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.DocStatusTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.purchase.to.PurchaseDetailTO;
import com.sifcoapp.objects.purchase.to.PurchaseInTO;
import com.sifcoapp.objects.purchase.to.PurchaseTO;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Digits;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "purchaseBean")
@ApplicationScoped
public class PurchaseBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaración de variables para formulario" >
    //Servicios EJB
    private static CatalogEJBClient CatalogEJB;
    private static AdminEJBClient AdminEJBService;
    private static PurchaseEJBClient PurchaseEJBClient;
    private static AccountingEJBClient AccountingEJBClient;

    //__________________________________________________________________________
    //Espacio entre componentes depntalla
    private int spacer = 3; //<p:spacer>

    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();

    //__________________________________________________________________________
    //Nueva Factura
    private PurchaseTO newPurchase = new PurchaseTO();        //Nueva Factura
    private PurchaseTO selectPurchase = new PurchaseTO();        //Select de lista busqueda
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
    private Date fechaDoc = new Date();     //Fecha Documento
    private Date fechaVen = new Date();     //Fecha vencimiento
    private int tipoDoc;         //Tipo Documento
    private int formaPago;       //Forma de Pago
    private String alm;             //Almacen

    private String nomVendedor;     //Nombre del Vendedor
    private String coment;          //Comentario
    private String ctlaccount;

    //__________________________________________________________________________
    //Detalles
    private String newCod;          //Codigo
    private String newNomArt;       //Nombre Articulo

    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private Double newCantidad;     //Cantidad

    @Digits(integer = 14, fraction = 2, message = "Precio inadecuado")
    private Double newPrecio;       //Precio

    private Double newTotal;        //Total de detalle
    private String newUnidad;       //Unidad de Medida
    private Double newExistencia;   //Existencia

    private String newCodCuenta;
    private String newNomCuenta;
    private String newImpuesto;

    private PurchaseDetailTO selectDetail = new PurchaseDetailTO(); //detalle seleccionado para eliminar
    private ArrayList<PurchaseDetailTO> listaDetalles = new ArrayList<>(); //DataTable 
    private List listaPadre = new Vector(); //al guardar el bill
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
    private static final String CATALOGOTYPEDOC = "TypesInvoices";  //tipo doc
    private static final String CATALOGOTYPEPAY = "TypesPayment";  //tipo de pago
    private static final String CATALOGOTAXPURCHASE = "ImpuestosCompra";

    private List<CatalogTO> lstTipoDoc;     //llenar cmb tipo de documento
    private List<CatalogTO> lstTipoPago;    //llenar cmb tipo de pago
    private List<BranchTO> listaAlmacenes;  //llenar cmb de almacenes
    private List<DocStatusTO> lstEstados;   //llenar cmb de estados
    private List<CatalogTO> lstImpuestos;   //llenar cmb de impuestos de compra

    //__________________________________________________________________________
    //manejo de estados
    //Estado principal Guardar, Update, Buscar
    private int varEstados;
    private String botonEstado;
    private boolean confirm;
    private int toolbarBoton;

    //habilitacion forma de pago y almacen
    private boolean selected;       //para forma de pago y almacen
    private boolean disabledDocNum;
    private boolean disabledSearch;
    private boolean disabledComun; //para campos y boton de detalles
    private boolean required1, rendered;

    boolean termino;

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private ArrayList<PurchaseTO> listaBusquedaTable = new ArrayList<>();

    //__________________________________________________________________________
    //Lista de precio
    private final int ultPrecio = 1;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >    
    @PostConstruct
    public void initForm() {
        //variables de estados habilitados/desabilitados
        this.required1 = true;
        this.disabledDocNum = true;
        this.disabledSearch = false;
        this.disabledComun = true;
        this.selected = false;

        //this.estadoDoc = Common.DocStatusOpen;
        //Inicializacion de servicios
        if (PurchaseEJBClient == null) {
            PurchaseEJBClient = new PurchaseEJBClient();
        }

        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        //llenar cmb tipo doc, formapago, almacenes
        try {
            String var = null;
            lstTipoDoc = AdminEJBService.findCatalog(CATALOGOTYPEDOC);
            lstTipoPago = AdminEJBService.findCatalog(CATALOGOTYPEPAY);
            listaAlmacenes = AdminEJBService.getBranch(var, var);
            lstEstados = Common.getDocStatusList();
            lstImpuestos = AdminEJBService.findCatalog(CATALOGOTAXPURCHASE);
        } catch (Exception e) {
            faceMessage("Error: Fallo al llenar combo box " + e.getMessage());
        }
        //llenar nombre de vendedor
        this.nomVendedor = session.getAttribute("userfullname").toString();

        //Estado por defecto
        estateGuardar();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocomplete socio name y cod" > 
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
            results.add(articulo.getCardname());
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
            results.add(cuentas.getAcctname());
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
            results.add(cuentas.getAcctcode());
        }
        return results;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete CUENTA" > 
    public void findAccount(SelectEvent event) {
        List account = new Vector();
        String var = null;
        if (event.getObject().toString() != var) {
            List _result = null;

            try {
                if (newCodCuenta != null || newNomCuenta != null) {
                    _result = AccountingEJBClient.getAccountByFilter(newCodCuenta, newNomCuenta);
                }

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
                newCodCuenta = null;
                newNomCuenta = null;
            }
            if (_result.isEmpty()) {
                this.newCodCuenta = null;
                this.newNomCuenta = null;

            } else {
                Iterator<AccountTO> iterator = _result.iterator();
                while (iterator.hasNext()) {
                    AccountTO articulo = (AccountTO) iterator.next();
                    account.add(articulo);
                }
                if (account.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) account.get(0);
                        if (newCodCuenta != null || newNomCuenta != null) {
                            newCodCuenta = art.getAcctcode();
                            newNomCuenta = art.getAcctname();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (Object ac : account) {
                        AccountTO art = (AccountTO) ac;
                        if (newNomCuenta.equals(art.getAcctname())) {
                            newCodCuenta = art.getAcctcode();
                            newNomCuenta = art.getAcctname();
                            break;
                        }
                    }

                    /* AccountTO art = (AccountTO) account.get(0);
                     if (newCodCuenta != null || newNomCuenta != null) {
                     newCodCuenta = art.getAcctcode();
                     newNomCuenta = art.getAcctname();
                     }*/
                }
            }
        }
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
                        ctlaccount = art.getDebpayacct();
                        codSocio = art.getCardcode();
                        socioNeg = art.getCardname();
                        
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                    ctlaccount = art.getDebpayacct();
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
                        ctlaccount = art.getDebpayacct();
                        codSocio = art.getCardcode();
                        socioNeg = art.getCardname();

                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    BusinesspartnerTO art = (BusinesspartnerTO) socio.get(0);
                    ctlaccount = art.getDebpayacct();
                    codSocio = art.getCardcode();
                    socioNeg = art.getCardname();
                    faceMessage("Error: Mas de un elemento encontrado, nombre de articulo repetido");
                }
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete Articulo" > 
    public void findArticle(SelectEvent event) {
        List articulos = new Vector();

        if (event.getObject().toString() != null) {
            List _result = null;

            ArticlesInTO in = new ArticlesInTO();
            in.setItemCode(newCod);
            in.setItemName(newNomArt);

            try {
                _result = AdminEJBService.getArticles(in);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
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
                        newUnidad = art.getSalUnitMsr();
                        newExistencia = art.getOnHand();

                        art = AdminEJBService.getArticlesByKey(newCod);
                        Double factor = art.getSalPackUn();
                        if (factor != null) {
                            if (factor == 0.0) {
                                newPrecio = art.getPrice(ultPrecio);
                            } else {
                                if (factor > 0) {
                                    newPrecio = art.getPrice(ultPrecio) / factor;
                                } else {
                                    faceMessage("Factor articulo incorrecto " + factor);
                                }
                            }

                        } else {
                            faceMessage("no hay factor");
                            newPrecio = art.getPrice(ultPrecio);
                        }

                        if (!alm.equals("-1")) {//revisar-----------------------
                            List almac = art.getBranchArticles();
                            Iterator<BranchArticlesTO> iter = almac.iterator();
                            while (iter.hasNext()) {
                                BranchArticlesTO branch = (BranchArticlesTO) iter.next();
                                if (branch.getWhscode().equals(alm)) {
                                    newExistencia = branch.getOnhand();
                                    break;
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (Object ac : articulos) {
                        ArticlesTO art = (ArticlesTO) ac;
                        if (newNomArt.equals(art.getItemName())) {

                            newNomArt = art.getItemName();
                            newCod = art.getItemCode();
                            newUnidad = art.getSalUnitMsr();
                            newExistencia = art.getOnHand();
                            try {
                                art = AdminEJBService.getArticlesByKey(newCod);
                            } catch (Exception ex) {
                                Logger.getLogger(SupplierCreditNotesBean.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            newPrecio = art.getPrice(ultPrecio);
                            if (!alm.equals("-1")) {//revisar-----------------------
                                List almac = art.getBranchArticles();
                                Iterator<BranchArticlesTO> iter = almac.iterator();
                                while (iter.hasNext()) {
                                    BranchArticlesTO branch = (BranchArticlesTO) iter.next();
                                    if (branch.getWhscode().equals(alm)) {
                                        newExistencia = branch.getOnhand();
                                        break;
                                    }
                                }
                            }
                            break;
                        }//cierre if
                    }//cierre for 
                }
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Calcular total">
    //calcula el total visual en pantalla
    public void calcularTotal() {
        try {
            if (newPrecio > 0 && newCantidad > 0 && newPrecio != null && newCantidad != null) {
                Double aux = (newPrecio) * (newCantidad);
                /*NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                String st = nf.format(aux);
                Double dou = Double.valueOf(st);
                //newTotal = dou;*/
                newTotal = aux;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " - " + e.getCause());
            //faceMessage("Error al calcular total: " + e.getMessage() + "-" + e.getCause());
            //faceMessage("Precio: " + newPrecio + "  " + "Cantidad: " + newCantidad);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Formato Numeros NO USADA">
    public Double formatNumber(Double num) {

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String st = nf.format(num);
        Double dou = Double.valueOf(st);
        return dou;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Boton Agregar al DATATABLE">
    public void accionAgregar(ActionEvent actionEvent) {
        try {
            if (validarNull() && validatePrice()) {
                if (listaDetalles == null) {
                    listaDetalles = new ArrayList<>();
                }
                if (listaPadre == null) {
                    listaPadre = new Vector();
                }

                PurchaseDetailTO newDetalle = new PurchaseDetailTO();
                ArticlesTO thisArt = new ArticlesTO();
                CatalogTO thisCat = new CatalogTO();
                newDetalle.setObjtype("" + 20);
                newDetalle.setItemcode(newCod);
                newDetalle.setDscription(newNomArt);
                newDetalle.setQuantity(newCantidad);

                newDetalle.setAcctcode(newCodCuenta);
                Double impFOV = 0.0;
                Double impCOT = 0.0;
                Double aux;

                CatalogTO cat1 = new CatalogTO();
                cat1 = AdminEJBService.findCatalogByKey(newImpuesto, 12);

                aux = Integer.parseInt(cat1.getCatvalue()) + 0.0;//impuesto debe ser un valor entero
                Double imp = aux / 100;

                newDetalle.setTaxcode(cat1.getCatcode());
                newDetalle.setVatgroup(imp + "|" + impFOV + "|" + impCOT);

                newDetalle.setPrice(newPrecio);
                newDetalle.setPricebefdi(newPrecio);

                newDetalle.setLinetotal(newPrecio * newCantidad);
                newDetalle.setUnitmsr(newUnidad);
                newDetalle.setLinenum(UUID.randomUUID().hashCode());

                try {
                    thisArt = AdminEJBService.getArticlesByKey(newCod);
                    if (thisArt.getWtliable().equals("Y")) {
                        newDetalle.setTaxstatus("Y");
                        //faceMessage("se calcularan impuestos");

                        if (thisArt.getVatgourpsa().equals("FOV")) {
                            //faceMessage("articulo aplica: iva, fov, cotrans");
                            thisCat = (CatalogTO) thisArt.getVatgourpsaList().get(0);
                            Double impIVA = (Integer.parseInt(thisCat.getCatvalue()) + 0.0) / 100; //%de IVA
                            //Double impFOV = Double.parseDouble(thisCat.getCatvalue2());
                            //Double impCOT = Double.parseDouble(thisCat.getCatvalue3());

                            //newDetalle.setVatgroup(impIVA + "|" + impFOV + "|" + impCOT); //valor de los ipuestos
                            Double impArt = ((newPrecio * newCantidad) * impIVA) + (newCantidad * impFOV) + (newCantidad * impCOT);
                            //Double descuentos = 0.0;

                            newDetalle.setPriceafvat((newPrecio) + (newPrecio * impIVA) + (impFOV) + (impCOT)); //precio bruto  
                            //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                            newDetalle.setVatsum(impArt);//suma total de impuestos aplicados
                            newDetalle.setGrssprofit(newDetalle.getLinetotal() - (thisArt.getAvgPrice() * newDetalle.getQuantity()));//precio venta - costo
                            //newDetalle.setTaxcode(thisArt.getVatgourpsa());//FOV
                            newDetalle.setVatappld(newDetalle.getVatsum());//igual a vatsum
                            newDetalle.setStockpricestockprice(thisArt.getAvgPrice());//
                            newDetalle.setGtotal(newDetalle.getPriceafvat() * newDetalle.getQuantity());

                        } else {
                            //faceMessage("articulo aplica a X impuesto de descripcion1");
                            thisCat = (CatalogTO) thisArt.getVatgourpsaList().get(0);
                            Double impIVA = (Integer.parseInt(thisCat.getCatvalue()) + 0.0) / 100; //%de IVA
                            Double impArt = (newPrecio * newCantidad) * impIVA; //(precio unitario * cantidad) * 0.13%
                            //Double descuentos = 0.0;

                            newDetalle.setPriceafvat((newPrecio) + (newPrecio * impIVA)); //total + impuesto de iva
                            //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                            newDetalle.setVatsum(impArt);//suma total de impuestos aplicados
                            newDetalle.setGrssprofit(newDetalle.getLinetotal() - (thisArt.getAvgPrice() * newDetalle.getQuantity()));//precio venta - costo
                            //newDetalle.setTaxcode(thisArt.getVatgourpsa());//IVA u otro tipo
                            newDetalle.setVatappld(newDetalle.getVatsum());//igual a vatsum
                            newDetalle.setStockpricestockprice(thisArt.getAvgPrice());//
                            newDetalle.setGtotal(newDetalle.getPriceafvat() * newDetalle.getQuantity());

                        }
                    } else {
                        newDetalle.setTaxstatus("N");
                        //faceMessage("Articulo exento de impuestos");
                        Double impIVA = 0.0; //%de IVA
                        Double impArt = (newPrecio * newCantidad) * impIVA; //(precio unitario * cantidad) * 0.13%
                        //Double descuentos = 0.0;

                        newDetalle.setPriceafvat((newPrecio) + (newPrecio * impIVA)); //total + impuesto de iva
                        //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                        newDetalle.setVatsum(impArt);//suma total de impuestos aplicados
                        newDetalle.setGrssprofit(newDetalle.getLinetotal() - (thisArt.getAvgPrice() * newDetalle.getQuantity()));//precio venta - costo
                        //newDetalle.setTaxcode(thisArt.getVatgourpsa());//IVA u otro tipo
                        newDetalle.setVatappld(newDetalle.getVatsum());//igual a vatsum
                        newDetalle.setStockpricestockprice(thisArt.getAvgPrice());//
                        newDetalle.setGtotal(newDetalle.getPriceafvat() * newDetalle.getQuantity());
                    }
                } catch (Exception e) {
                }

                listaPadre.add(newDetalle);
                this.getListaDetalles().add(newDetalle);
                calcularTotalBill(listaDetalles); //calculos por cada articulo agregado
                termino = true;
            } else {
                termino = false;
            }
        } catch (Exception e) {
        }
        if (termino) {
            cleanDetalle();
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Calcular Impuestos y TOTAL">
    public void calcularTotalBill(ArrayList<PurchaseDetailTO> listaArt) {
        Double totalAux = 0.0;

        this.gravadas = calcularGravadas(listaArt);
        this.exentas = calcularExentas(listaArt);
        this.IVA = calcularIVA(listaArt);
        this.retencion = calcularRetencion(listaArt);
        this.fovial = calcularFovial(listaArt);
        this.cotrans = calcularCotrans(listaArt);

        totalAux = (this.gravadas + this.exentas + this.IVA + this.retencion + this.fovial + this.cotrans);

        this.total = totalAux;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="funciones para calculos de impuestos">
    public Double calcularGravadas(ArrayList<PurchaseDetailTO> listaArt) {
        Double sumTotal = 0.0;
        for (PurchaseDetailTO art : listaArt) {
            sumTotal = sumTotal + art.getLinetotal();
        }
        return sumTotal;
    }

    public Double calcularExentas(ArrayList<PurchaseDetailTO> listaArt) {
        return 0.0;

    }

    public Double calcularIVA(ArrayList<PurchaseDetailTO> listaArt) {
        Double sumIVA = 0.0;
        //Double sumTotal = 0.0;
        for (PurchaseDetailTO det : listaArt) {
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

    public Double calcularRetencion(ArrayList<PurchaseDetailTO> listaArt) {
        return 0.0;
    }

    public Double calcularFovial(ArrayList<PurchaseDetailTO> listaArt) {
        Double sumFovial = 0.0;
        for (PurchaseDetailTO det : listaArt) {
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
                //faceMessage("Error de calculo Fovial");
                Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sumFovial;

    }

    public Double calcularCotrans(ArrayList<PurchaseDetailTO> listaArt) {
        Double sumCotrans = 0.0;
        for (PurchaseDetailTO det : listaArt) {
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
                //faceMessage("Error de calculo Cotrans");
                Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return sumCotrans;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Eliminar del dataTable" > 
    public void deleteDetalle() {
        try {
            boolean var1, var2;
            var1 = getListaDetalles().remove(this.selectDetail);
            var2 = listaPadre.remove(this.selectDetail);
            this.selectDetail = null;
            if (var1 && var2) {
                calcularTotalBill(listaDetalles);
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

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.estadoDoc = Common.DocStatusOpen;
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";

        this.disabledDocNum = true;
        this.disabledSearch = false;
        this.disabledComun = true;
        this.selected = false;
        this.required1 = true;
        this.rendered = true;
        RequestContext.getCurrentInstance().update("frmPurchase");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";

        this.disabledDocNum = true;//false
        this.disabledSearch = true;
        this.disabledComun = true;
        this.selected = true;
        this.rendered = false;
        this.required1 = true;
        //RequestContext.getCurrentInstance().update("frmPurchase");
        try {
            reload();
        } catch (IOException ex) {
        }
        
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";

        this.estadoDoc = null;

        this.disabledDocNum = false;
        this.disabledSearch = false;
        this.disabledComun = true;
        this.selected = false;
        this.rendered = false;
        this.required1 = false; //forma de pago y almacen no requeridos en busqueda

        RequestContext.getCurrentInstance().update("frmPurchase");
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
        int line = 0;

        if (newPurchase == null) {
            newPurchase = new PurchaseTO();
        }
        newPurchase.setObjtype("" + 20);
        newPurchase.setDocstatus(estadoDoc);
        newPurchase.setUsersign((int) session.getAttribute("usersign"));
        newPurchase.setCardname(socioNeg);
        newPurchase.setCardcode(codSocio);
        newPurchase.setRef2("" + equipo);
        newPurchase.setNumatcard(refe);

        newPurchase.setDocdate(fechaConta);
        newPurchase.setTaxdate(fechaDoc);
        newPurchase.setDocduedate(fechaVen);

        newPurchase.setSeries(tipoDoc);
        newPurchase.setPeymethod("" + formaPago);
        newPurchase.setTowhscode(alm);
        newPurchase.setComments(coment);
        
        newPurchase.setCtlaccount(ctlaccount);

        Double gTotalPadre = 0.0;
        Iterator<PurchaseDetailTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            PurchaseDetailTO articleDetalle = (PurchaseDetailTO) iterator2.next();
            articleDetalle.setLinenum(line + 1);
            gTotalPadre = gTotalPadre + articleDetalle.getGtotal();
            line = line + 1;
        }

        newPurchase.setDoctotal(gTotalPadre);
        newPurchase.setpurchaseDetails(listaPadre);

        try {
            ResultOutTO _res;
            _res = PurchaseEJBClient.inv_Purchase_mtto(newPurchase, Common.MTTOINSERT); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                docEntry = _res.getDocentry();
                docNum = docEntry; //
                newPurchase = PurchaseEJBClient.getPurchaseByKey(docEntry);
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="UPDATE EN BASE">
    public void doUpdate() {

        if (newPurchase == null) {
            newPurchase = new PurchaseTO();
        }
        String vacio = null;
        newPurchase.setObjtype("" + 20);
        newPurchase.setDocentry(docEntry);
        newPurchase.setDocnum(docNum);
        newPurchase.setDocstatus(estadoDoc);

        newPurchase.setUsersign((int) session.getAttribute("usersign"));
        newPurchase.setCardname(socioNeg);
        newPurchase.setCardcode(codSocio);
        newPurchase.setRef2("" + equipo);
        newPurchase.setCtlaccount(ctlaccount);

        if (refe.equals("")) {
            newPurchase.setNumatcard(vacio);
        } else {
            newPurchase.setNumatcard(refe);
        }

        if (coment.equals("")) {
            newPurchase.setComments(vacio);
        } else {
            newPurchase.setComments(coment);
        }

        newPurchase.setDocdate(fechaConta);
        newPurchase.setTaxdate(fechaDoc);
        newPurchase.setDocduedate(fechaVen);

        newPurchase.setSeries(tipoDoc);
        newPurchase.setPeymethod("" + formaPago);
        newPurchase.setTowhscode(alm);

        newPurchase.setpurchaseDetails(listaPadre);

        try {
            ResultOutTO _res;
            _res = PurchaseEJBClient.inv_Purchase_mtto(newPurchase, Common.MTTOUPDATE); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                //docEntry = _res.getDocentry();
                //docNum = docEntry; //
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje() + " Error al guardar");
            }
        } catch (Exception ex) {
            Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage("ERROR " + ex.getMessage() + "-" + ex.getCause());
            System.out.println("ËRROR " + ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BUSCAR EN BASE">
    public void doSearch() {
        PurchaseInTO searchPurchase = new PurchaseInTO();

        searchPurchase.setDocnum(docNum);
        searchPurchase.setCardname(socioNeg);
        searchPurchase.setCardcode(codSocio);

        String var1 = "" + equipo;
        String vacio = null;

        if (var1.equals("0")) {
            searchPurchase.setRef2(vacio);
        } else {
            searchPurchase.setRef2("" + equipo);
        }

        if (refe.equals("")) {
            searchPurchase.setNumatcard(vacio);
        } else {
            searchPurchase.setNumatcard(refe);
        }

        searchPurchase.setDocdate(fechaConta);
        searchPurchase.setTaxdate(fechaDoc);
        searchPurchase.setDocduedate(fechaVen);
        
        searchPurchase.setSeries(tipoDoc);

        String var3 = "" + formaPago;
        if (var3.equals("0")) {
            searchPurchase.setPeymethod(vacio);
        } else {
            searchPurchase.setPeymethod("" + formaPago);
        }

        if (alm.equals("-1")) {
            searchPurchase.setTowhscode(vacio);
        } else {
            searchPurchase.setTowhscode(alm);
        }

        if (estadoDoc.equals("-1")) {
            searchPurchase.setDocstatus(vacio);
        } else {
            if (estadoDoc.equals("S")) {
                searchPurchase.setDocstatus(vacio);
                searchPurchase.setCanceled("Y");
            } else {
                searchPurchase.setDocstatus(estadoDoc);
                searchPurchase.setCanceled("N");
            }
        }

        if (coment.equals("")) {
            searchPurchase.setComments(vacio);
        } else {
            searchPurchase.setComments(coment);
        }

        try {
            listaBusqueda = PurchaseEJBClient.getPurchase(searchPurchase);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newPurchase = (PurchaseTO) listaBusqueda.get(0);
                try {

                    PurchaseTO var = PurchaseEJBClient.getPurchaseByKey(newPurchase.getDocentry());
                    llenarPantalla(var);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }
            } else {
                faceMessage("Seleccione un elemento");

                for (Object purchase : listaBusqueda) {
                    try {
                        PurchaseTO var = (PurchaseTO) purchase;
                        /* String formaPagoS, tipoDocS;      //que se muestra en DT

                         CatalogTO cat1, cat2 = new CatalogTO();
                         cat1 = AdminEJBService.findCatalogByKey(var.getPeymethod(), 8);
                         formaPagoS = cat1.getCatvalue();

                         cat2 = AdminEJBService.findCatalogByKey(var.getDoctype(), 9);
                         tipoDocS = cat2.getCatvalue();

                         var.setPeymethod(formaPagoS);
                         var.setDoctype(tipoDocS);*/
                        listaBusquedaTable.add(var);

                    } catch (Exception e) {
                        faceMessage(e.getMessage() + " - " + e.getCause());
                    }
                }
                showHideDialog("dlgListPurchase", 1);

            }
        } else {
            faceMessage("No se encontraron Registros");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Seleccionar un almacen y Forma de pago">
    public void stateChange1(ValueChangeEvent event) {

        this.setFormaPago((int) event.getNewValue());

        if (validarRequeridos()) {
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
        }

    }

    public void stateChange2(ValueChangeEvent event) {

        this.setAlm(event.getNewValue().toString());

        if (validarRequeridos()) {
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
        }

    }

    public void stateChange3(ValueChangeEvent event) {

        this.setSocioNeg(event.getNewValue().toString());

        if (validarRequeridos()) {
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
        }
    }

    public void stateChange4(ValueChangeEvent event) {

        this.setCodSocio(event.getNewValue().toString());

        if (validarRequeridos()) {
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
        }
    }

    public boolean validarRequeridos() {
        return formaPago > 0 && !alm.equals("-1") && !socioNeg.isEmpty() && !codSocio.isEmpty();
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones Varias">
    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    private boolean validatePrice() {
        if (this.newPrecio < 0) {
            faceMessage("Articulo con precio incorrecto");
            return false;
        }
        return true;
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

        RequestContext.getCurrentInstance().update("frmPurchase");
    }

    //evento al seleccionar un elemento del dialogo facturas
    public void selectDialogBill() {
        if (PurchaseEJBClient == null) {
            PurchaseEJBClient = new PurchaseEJBClient();
        }

        if (newPurchase == null) {
            newPurchase = new PurchaseTO();
        }

        showHideDialog("dlgListPurchase", 2);
        PurchaseTO var = (PurchaseTO) selectPurchase;

        try {
            newPurchase = PurchaseEJBClient.getPurchaseByKey(var.getDocentry());
            llenarPantalla(newPurchase);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectPurchase = new PurchaseTO();
        RequestContext.getCurrentInstance().update("frmPurchase");

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
    public void llenarPantalla(PurchaseTO var) {
        setEstadoDoc(var.getDocstatus());
        setDocEntry(var.getDocentry());
        setDocNum(var.getDocnum());
        setSocioNeg(var.getCardname());
        setCodSocio(var.getCardcode());
        try {
            setEquipo(Integer.parseInt(var.getRef2()));
        } catch (Exception e) {
        }
        
        setRefe(var.getNumatcard());

        setFechaConta(var.getDocdate());
        setFechaDoc(var.getTaxdate());
        setFechaVen(var.getDocduedate());
        setTipoDoc(var.getSeries());
        try {
            setFormaPago(Integer.parseInt(var.getPeymethod()));
        } catch (Exception e) {
        }
        
        setAlm(var.getTowhscode());

        setComent(var.getComments());

        for (Object detalle : var.getpurchaseDetails()) {
            PurchaseDetailTO det = (PurchaseDetailTO) detalle;
            this.listaDetalles.add(det);
        }

        calcularTotalBill(listaDetalles);
    }

    //validar nueva factura
    public boolean validarNewBill() {
        if (socioNeg == null && codSocio == null) {
            faceMessage("Seleccione un Socio de Negocio ó Codigo de Socio");
            return false;
        }
        if (fechaConta == null && fechaDoc == null && fechaVen == null || fechaConta == null || fechaDoc == null || fechaVen == null) {
            faceMessage("Seleccione las fechas");
            return false;
        }

        if (tipoDoc == 0) {
            faceMessage("Seleccione el tipo de Documento");
            return false;
        }

        if (getListaDetalles().size() < 1) {
            faceMessage("Ingrese al menos un Articulo");
            return false;
        }

        return true;
    }

    //validar campos
    public boolean validarNull() {

        if (!newCod.isEmpty() && !newNomArt.isEmpty() && newCantidad > 0 && !newImpuesto.equals("-1")) {
            return true;
        }

        if (newImpuesto.equals("-1")) {
            faceMessage("Seleccione un impuesto");
            return false;
        }
        return false;
    }

    //limpiar el bean
    public void cleanBean(int tipo) {
        cleanDetalle();

        this.listaDetalles.clear();
        this.listaPadre.clear();
        //variables desabilitadas
        this.disabledComun = true;
        this.selected = false;

        //encabezado
        this.docNum = 0;
        this.socioNeg = null;
        this.codSocio = null;
        this.equipo = 0;
        this.refe = null;
        this.alm = null;
        if (tipo == 1) {
            Date d = new Date();
            this.setFechaConta(d);
            this.setFechaDoc(d);
            this.setFechaVen(d);
        } else {
            Date d = null;
            this.setFechaConta(d);
            this.setFechaDoc(d);
            this.setFechaVen(d);
        }

        this.tipoDoc = 0;
        this.formaPago = 0;
        this.coment = null;

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

    //limpiar variables
    public void cleanDetalle() {
        newCod = null;
        newNomArt = null;
        newPrecio = null;
        newCantidad = null;
        newTotal = null;
        newUnidad = null;
        newExistencia = null;

        newCodCuenta = null;
        newNomCuenta = null;
        newImpuesto = null;

        //this.listaDetalles.clear();
    }

    //mostrar un mensaje en pantalla dado un string
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public boolean validarClear() {
        return this.listaPadre.size() >= 1 && this.varEstados != 2 || doClean() && this.varEstados != 2;// && this.listaBusquedaTable.size() >=1;     
    }

    public boolean doClean() {
        if (equipo > 0 || tipoDoc > 0 || formaPago > 0) {
            return true;
        }
        try {
            if (socioNeg != null || codSocio != null || !refe.equals("") || !alm.equals("-1")) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " -- " + e.getCause());
            return false;
        }
        return false;
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC1').hide();");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S" >

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        PurchaseBean.CatalogEJB = CatalogEJB;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        PurchaseBean.AdminEJBService = AdminEJBService;
    }

    public static PurchaseEJBClient getPurchaseEJBClient() {
        return PurchaseEJBClient;
    }

    public static void setPurchaseEJBClient(PurchaseEJBClient PurchaseEJBClient) {
        PurchaseBean.PurchaseEJBClient = PurchaseEJBClient;
    }

    public static AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public static void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        PurchaseBean.AccountingEJBClient = AccountingEJBClient;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public PurchaseTO getNewPurchase() {
        return newPurchase;
    }

    public void setNewPurchase(PurchaseTO newPurchase) {
        this.newPurchase = newPurchase;
    }

    public String getCtlaccount() {
        return ctlaccount;
    }

    public void setCtlaccount(String ctlaccount) {
        this.ctlaccount = ctlaccount;
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

    public boolean isTermino() {
        return termino;
    }

    public void setTermino(boolean termino) {
        this.termino = termino;
    }
    
    
    
    
    
    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }
    
    

    public Date getFechaVen() {
        return fechaVen;
    }

    public void setFechaVen(Date fechaVen) {
        this.fechaVen = fechaVen;
    }
    
    
    
    public List<CatalogTO> getLstImpuestos() {
        return lstImpuestos;
    }

    public void setLstImpuestos(List<CatalogTO> lstImpuestos) {
        this.lstImpuestos = lstImpuestos;
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

    public String getNewImpuesto() {
        return newImpuesto;
    }

    public void setNewImpuesto(String newImpuesto) {
        this.newImpuesto = newImpuesto;
    }

    public List<DocStatusTO> getLstEstados() {
        return lstEstados;
    }

    public void setLstEstados(List<DocStatusTO> lstEstados) {
        this.lstEstados = lstEstados;
    }

    public String getEstadoDoc() {
        return estadoDoc;
    }

    public void setEstadoDoc(String estadoDoc) {
        this.estadoDoc = estadoDoc;
    }

    public BusinesspartnerTO getSelectSocio() {
        return selectSocio;
    }

    public void setSelectSocio(BusinesspartnerTO selectSocio) {
        this.selectSocio = selectSocio;
    }

    public PurchaseTO getSelectPurchase() {
        return selectPurchase;
    }

    public void setSelectPurchase(PurchaseTO selectPurchase) {
        this.selectPurchase = selectPurchase;
    }

    public boolean isRequired1() {
        return required1;
    }

    public void setRequired1(boolean required1) {
        this.required1 = required1;
    }

    public boolean isDisabledDocNum() {
        return disabledDocNum;
    }

    public void setDisabledDocNum(boolean disabledDocNum) {
        this.disabledDocNum = disabledDocNum;
    }

    public boolean isDisabledSearch() {
        return disabledSearch;
    }

    public void setDisabledSearch(boolean disabledSearch) {
        this.disabledSearch = disabledSearch;
    }

    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    public PurchaseDetailTO getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(PurchaseDetailTO selectDetail) {
        this.selectDetail = selectDetail;
    }

    public boolean isDisabledComun() {
        return disabledComun;
    }

    public void setDisabledComun(boolean disabledComun) {
        this.disabledComun = disabledComun;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List getListaPadre() {
        return listaPadre;
    }

    public void setListaPadre(List listaPadre) {
        this.listaPadre = listaPadre;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<PurchaseTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<PurchaseTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
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

    public List<BranchTO> getListaAlmacenes() {
        return listaAlmacenes;
    }

    public void setListaAlmacenes(List<BranchTO> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public String getAlm() {
        return alm;
    }

    public void setAlm(String alm) {
        this.alm = alm;
    }

    public List<CatalogTO> getLstTipoDoc() {
        return lstTipoDoc;
    }

    public void setLstTipoDoc(List<CatalogTO> lstTipoDoc) {
        this.lstTipoDoc = lstTipoDoc;
    }

    public List<CatalogTO> getLstTipoPago() {
        return lstTipoPago;
    }

    public void setLstTipoPago(List<CatalogTO> lstTipoPago) {
        this.lstTipoPago = lstTipoPago;
    }

    public int getSpacer() {
        return spacer;
    }

    public void setSpacer(int spacer) {
        this.spacer = spacer;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
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

    public Date getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(Date fechaDoc) {
        this.fechaDoc = fechaDoc;
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

    public String getNomVendedor() {
        return nomVendedor;
    }

    public void setNomVendedor(String nomVendedor) {
        this.nomVendedor = nomVendedor;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
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

    public ArrayList<PurchaseDetailTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<PurchaseDetailTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
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

//</editor-fold> 
    
}//CIERRE PRINCIPAL
