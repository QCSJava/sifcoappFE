/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.sales.bean;

import com.ocpsoft.pretty.faces.util.StringUtils;
import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.SalesEJBClient;
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
import com.sifcoapp.objects.sales.to.ClientCrediTO;
import com.sifcoapp.objects.sales.to.DeliveryDetailTO;
import com.sifcoapp.objects.sales.to.DeliveryInTO;
import com.sifcoapp.objects.sales.to.DeliveryTO;
import com.sifcoapp.objects.sales.to.SalesDetailTO;
import com.sifcoapp.objects.sales.to.SalesInTO;
import com.sifcoapp.objects.sales.to.SalesTO;
import com.sifcoapp.report.bean.ReportsBean;
import com.sifcoapp.report.common.AbstractReportBean;
import com.sifcoapp.report.common.numerosAletras;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Digits;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "salesBean")
@SessionScoped
public class SalesBean implements Serializable {

    public SalesBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaración de variables para formulario" >
    //Servicios EJB
    private static CatalogEJBClient CatalogEJB;
    private static AdminEJBClient AdminEJBService;
    private static SalesEJBClient SalesEJBService;

    //__________________________________________________________________________
    //Espacio entre componentes depntalla
    private int spacer = 3; //<p:spacer>

    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();

    //__________________________________________________________________________
    //Nueva Factura
    private ClientCrediTO newCredit;

    private SalesTO newBill = new SalesTO();        //Nueva Factura
    private SalesTO selectBill = new SalesTO();        //Select de lista busqueda
    private BusinesspartnerTO selectSocio = new BusinesspartnerTO();
    private int precioArt;
    //__________________________________________________________________________
    //Encabezado
    private int docEntry;           //No visible
    private int docNum;             //No.
    private String estadoDoc;       //Estado de Documento
    private String socioNeg;        //Socio de Negocio
    private String codSocio;        //Codigo de Socio
    private String clientName;      //Nombre de cleitne adicional
    private int equipo;             //Equipo
    private int refe;            //Referencia

    private Date fechaConta = new Date();   //Fecha Contabilizacion
    private Date fechaDoc = new Date();     //Fecha Documento
    private int tipoDoc = 1;         //Tipo Documento
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

    private ArticlesTO newArticle = new ArticlesTO();
    private Double newPrecio;       //Precio
    private Double newPriceafvat;   //Precio con impuestos incluidos
    private Double newTotal;        //Total de detalle
    private Double newTotalafvat;  //Total despues de impuestos
    private String newUnidad;       //Unidad de Medida
    private Double newExistencia;   //Existencia

    private SalesDetailTO selectDetail = new SalesDetailTO(); //detalle seleccionado para eliminar
    private ArrayList<SalesDetailTO> listaDetalles = new ArrayList<>(); //DataTable 
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

    private List<CatalogTO> lstTipoDoc;     //llenar cmb tipo de documento
    private List<CatalogTO> lstTipoPago;    //llenar cmb tipo de pago
    private List<BranchTO> listaAlmacenes;  //llenar cmb de almacenes
    private List<DocStatusTO> lstEstados;   //llenar cmb de estados

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
    private boolean required1;

    private boolean creditNote;
    private boolean fromSales = false;
    private boolean rendered;

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private ArrayList<SalesTO> listaBusquedaTable = new ArrayList<>();

    //__________________________________________________________________________
    //
    private ExternalContext ec;// = FacesContext.getCurrentInstance().getExternalContext();
    private CreditNotesBean cn;// = (SalesBean) ec.getApplicationMap().get("salesBean");

    //__________________________________________________________________________
    //Reportes
    //@ManagedProperty(value = "#{reportSalesBean}")
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
    private numerosAletras convertNumber;

    private String exportOption;
    private boolean btnPrint;

    //__________________________________________________________________________
    //copiar desde remision
    private List listaBusquedaRemision = new Vector();
    private ArrayList<DeliveryTO> listaBusquedaTableRemision = new ArrayList<>();
    private DeliveryTO selectRemision = new DeliveryTO();
    private boolean fromCopy;
    private boolean actBtn, isRemision;
    private int docEntryRemision;
    //
    private String url;

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >    
    @PostConstruct
    public void initForm() {
        //variables de estados habilitados/desabilitados
        /*this.required1 = true;
         this.disabledDocNum = true;
         this.disabledSearch = false;
         this.disabledComun = true;
         this.selected = false;
         this.btnPrint = true;
         this.actBtn = true;*/
        //this.estadoDoc = Common.DocStatusOpen;
        this.setExportOption("FILE");
        //Inicializacion de servicios
        if (bean == null) {
            bean = new ReportsBean();
        }

        if (convertNumber == null) {
            convertNumber = new numerosAletras() {
            };
        }

        if (SalesEJBService == null) {
            SalesEJBService = new SalesEJBClient();
        }

        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        //llenar cmb tipo doc, formapago, almacenes
        try {
            String var = null;
            lstTipoDoc = AdminEJBService.findCatalog(CATALOGOTYPEDOC);
            lstTipoPago = AdminEJBService.findCatalog(CATALOGOTYPEPAY);
            listaAlmacenes = AdminEJBService.getBranch(var, var);
            lstEstados = Common.getDocStatusList();
        } catch (Exception e) {
            faceMessage("Error: Fallo al llenar combo box " + e.getMessage());
        }
        //llenar nombre de vendedor
        this.nomVendedor = session.getAttribute("userfullname").toString();

        setRef();

        //Estado por defecto
        estateGuardar();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SET REF">
    public void setRef() {
        String num;
        try {
            num = SalesEJBService.last_INPUT(tipoDoc, "10");
            if (isNum(num)) {
                int ref = 0;
                ref = Integer.parseInt(num);
                ref = ref + 1;
                this.refe = ref;
            } else {
                this.refe = 0;
            }
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocmplete" > 
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

    public List<String> completeText(String query) {
        List _result = null;
        String var = null;

        ArticlesInTO in = new ArticlesInTO();
        in.setItemCode(var);
        in.setItemName(query);

        try {
            _result = AdminEJBService.getArticles(in);

        } catch (Exception e) {
        }

        List<String> results = new ArrayList<String>();

        Iterator<ArticlesTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            ArticlesTO articulo = (ArticlesTO) iterator.next();
            results.add(articulo.getItemName() + " » " + articulo.getSww());
        }
        return results;
    }

    public List<String> completeCode(String query) {
        List _result = null;
        String var = null;

        ArticlesInTO in = new ArticlesInTO();
        in.setItemCode(query);
        in.setItemName(var);

        try {
            _result = AdminEJBService.getArticles(in);

        } catch (Exception e) {
        }

        List<String> results = new ArrayList<String>();

        Iterator<ArticlesTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            ArticlesTO articulo = (ArticlesTO) iterator.next();
            results.add(articulo.getItemCode());
        }
        return results;
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Seleccionar de autocomplete de Socio, Name o Cod">
    public void selectSocio(SelectEvent event) {
        String[] newName = socioNeg.split("-");
        this.codSocio = null;
        selectSocioCod(newName[0]);
    }

    public void selectSocioCod(String code) {
        List socio = new Vector();
        String var = null;

        if (selectSocio == null) {
            selectSocio = new BusinesspartnerTO();
        }

        List _result = null;

        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(codSocio == null ? code : codSocio);
        in.setCardtype("C");

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
                    Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                faceMessage("Error: Mas de un elemento encontrado, nombre de socio repetido");
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete Articulo" > 
    public void findArticle(SelectEvent event) {
        List articulos = new Vector();

        if (event.getObject().toString() != null) {

            //ArticlesTO var = (ArticlesTO) event.getObject();
            //faceMessage(event.getObject().toString());
            List _result = null;

            //Partir codigo, y quitar el codigo viejo            
            newNomArt = StringUtils.isBlank(newNomArt) ? newNomArt : newNomArt.substring(0, newNomArt.lastIndexOf("»") - 1);

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
                faceMessage("Articulo no encontrado");
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
                        //System.out.println("articulo unico, llenar campos en pantalla");
                        ArticlesTO art = (ArticlesTO) articulos.get(0);
                        newNomArt = art.getItemName();
                        newCod = art.getItemCode();
                        newUnidad = art.getSalUnitMsr();
                        newExistencia = art.getOnHand();

                        art = AdminEJBService.getArticlesByKey(newCod);
                        newArticle = art;
                        Double factor = art.getSalPackUn();
                        Double price = art.getPrice(precioArt);
                        if (price == -1 || price < 0) {
                            faceMessage("Articulo sin precio de venta");
                        }
                        if (factor != null) {
                            if (factor == 0.0) {
                                newPrecio = price;
                            } else {
                                if (factor > 0) {
                                    newPrecio = price / factor;
                                } else {
                                    faceMessage("Factor articulo incorrecto " + factor);
                                }
                            }

                        } else {
                            //faceMessage("no hay factor");
                            newPrecio = price;
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
                        Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //Mas de un articulo, si esta repetido el nombre
                    for (Object ac : articulos) {
                        ArticlesTO art = (ArticlesTO) ac;
                        if (newNomArt.equals(art.getItemName())) {

                            newNomArt = art.getItemName();
                            newCod = art.getItemCode();
                            newUnidad = art.getSalUnitMsr();
                            newExistencia = art.getOnHand();
                            try {
                                art = AdminEJBService.getArticlesByKey(newCod);
                                newArticle = art;
                            } catch (Exception ex) {
                                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Double price = art.getPrice(precioArt);
                            Double factor = art.getSalPackUn();
                            if (price == -1 || price < 0) {
                                faceMessage("Articulo sin precio de venta");
                            }
                            if (factor != null) {
                                if (factor == 0.0) {
                                    newPrecio = price;
                                } else {
                                    if (factor > 0) {
                                        newPrecio = price / factor;
                                    } else {
                                        faceMessage("Factor articulo incorrecto " + factor);
                                    }
                                }

                            } else {
                                //faceMessage("no hay factor");
                                newPrecio = price;
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

                // Calcular total con impuestos
                ArticlesTO thisArt = new ArticlesTO();
                CatalogTO thisCat = new CatalogTO();

                thisArt = newArticle;
                if (thisArt.getWtliable().equals("Y")) {

                    if (thisArt.getVatgourpsa().equals("FOV")) {
                        thisCat = (CatalogTO) thisArt.getVatgourpsaList().get(0);
                        Double impIVA = (Integer.parseInt(thisCat.getCatvalue()) + 0.0) / 100; //%de IVA
                        Double impFOV = Double.parseDouble(thisCat.getCatvalue2());
                        Double impCOT = Double.parseDouble(thisCat.getCatvalue3());

                        setNewPriceafvat((newPrecio) + (newPrecio * impIVA) + (impFOV) + (impCOT));
                        //Double descuentos = 0.0;

                    } else {
                        //faceMessage("articulo aplica a X impuesto de descripcion1");
                        thisCat = (CatalogTO) thisArt.getVatgourpsaList().get(0);
                        Double impIVA = (Integer.parseInt(thisCat.getCatvalue()) + 0.0) / 100; //%de IVA
                        setNewPriceafvat((newPrecio) + (newPrecio * impIVA)); //(precio unitario * cantidad) * 0.13%

                    }
                } else {
                    Double impIVA = 0.0; //%de IVA
                    setNewPriceafvat((newPrecio) + (newPrecio * impIVA)); //total + impuesto de iva
                }

                setNewTotalafvat(getNewPriceafvat() * newCantidad);

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

                SalesDetailTO newDetalle = new SalesDetailTO();
                ArticlesTO thisArt = new ArticlesTO();
                CatalogTO thisCat = new CatalogTO();
                newDetalle.setObjtype("" + 10);
                newDetalle.setItemcode(newCod);
                newDetalle.setDscription(newNomArt);
                newDetalle.setQuantity(newCantidad);

                newDetalle.setPrice(newPrecio);
                newDetalle.setPricebefdi(newPrecio);

                newDetalle.setLinetotal(newPrecio * newCantidad);
                newDetalle.setUnitmsr(newUnidad);
                newDetalle.setLinenum(UUID.randomUUID().hashCode());

                try {
                    thisArt = newArticle;//AdminEJBService.getArticlesByKey(newCod);

                    if (thisArt.getWtliable().equals("Y")) {
                        newDetalle.setTaxstatus("Y");
                        //faceMessage("se calcularan impuestos");

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

                listaPadre.add(newDetalle);
                this.getListaDetalles().add(newDetalle);
                calcularTotalBill(listaDetalles);
                /*if (newDetalle.getTaxstatus().equals("Y")) {
                 calcularTotalBill(listaDetalles); //calculos por cada articulo agregado
                 } else {
                 Double totalAux = 0.0;
                 this.gravadas = calcularGravadas(listaDetalles);
                 this.exentas = calcularExentas(listaDetalles);
                 this.retencion = calcularRetencion(listaDetalles);

                 if (this.IVA == null) {
                 this.IVA = 0.0;
                 }
                 if (this.fovial == null) {
                 this.fovial = 0.0;
                 }
                 if (this.cotrans == null) {
                 this.cotrans = 0.0;
                 }

                 totalAux = (this.gravadas + this.exentas + this.IVA + this.retencion + this.fovial + this.cotrans);

                 this.total = totalAux;
                 }*/

                // RequestContext.getCurrentInstance().execute("focusField(newCod);");
            }
        } catch (Exception e) {
        }
        cleanDetalle();
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Calcular Impuestos y TOTAL">
    public void calcularTotalBill(ArrayList<SalesDetailTO> listaArt) {
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
    public Double calcularGravadas(ArrayList<SalesDetailTO> listaArt) {
        Double sumTotal = 0.0;
        for (SalesDetailTO art : listaArt) {
            sumTotal = sumTotal + art.getLinetotal();
        }
        return sumTotal;
    }

    public Double calcularExentas(ArrayList<SalesDetailTO> listaArt) {
        return 0.0;

    }

    public Double calcularIVA(ArrayList<SalesDetailTO> listaArt) {
        Double sumIVA = 0.0;
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
                //faceMessage("Error calculo del IVA");
            }

        }

        //sumIVA = sumTotal * 0.13;
        return sumIVA;
    }

    public Double calcularRetencion(ArrayList<SalesDetailTO> listaArt) {
        return 0.0;
    }

    public Double calcularFovial(ArrayList<SalesDetailTO> listaArt) {
        Double sumFovial = 0.0;
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
                //faceMessage("Error de calculo Fovial");
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sumFovial;

    }

    public Double calcularCotrans(ArrayList<SalesDetailTO> listaArt) {
        Double sumCotrans = 0.0;
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
                //faceMessage("Error de calculo Cotrans");
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public void prueba() {
        faceMessage(this.selectDetail.getDscription());
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Contex Listener Nota de Credito----------------------">
    public String fromSales() {

        doSetCreditNote();
        this.fromSales = true;

        //cleanBean(1);
        //estateGuardar();
        return "ClientCreditNotes";
    }

    public void onComplete() {
        cleanBean(1);
        estateGuardar();
        try {
            reload();
        } catch (IOException ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
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
        this.btnPrint = true;
        this.creditNote = false;
        this.required1 = true;
        this.rendered = true;
        this.actBtn = true;
        setRef();
        RequestContext.getCurrentInstance().update("frmSales");

    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        if (varEstados == 1) {
            this.varEstados = Common.MTTOUPDATE; //2
            this.botonEstado = "Actualizar";

            this.disabledDocNum = true;//false
            this.disabledSearch = true;
            this.disabledComun = true;
            this.selected = true;
            this.btnPrint = false;
            this.creditNote = true;
            this.required1 = true;
            this.rendered = false;
            this.actBtn = true;

            RequestContext.getCurrentInstance().update("frmprt");
            showHideDialog("dlgPtr", 1);

            RequestContext.getCurrentInstance().update("frmSales");

            /*try {
             reload();
             } catch (IOException ex) {
             }*/
        } else {
            this.varEstados = Common.MTTOUPDATE; //2
            this.botonEstado = "Actualizar";

            this.disabledDocNum = true;//false
            this.disabledSearch = true;
            this.disabledComun = true;
            this.selected = true;
            this.btnPrint = false;
            this.creditNote = true;
            this.required1 = true;
            this.rendered = false;
            this.actBtn = true;
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
        this.btnPrint = true;
        this.creditNote = false;
        this.required1 = false; //forma de pago y almacen no requeridos en busqueda
        this.rendered = false;
        this.actBtn = true;
        this.tipoDoc = 0;
        RequestContext.getCurrentInstance().update("frmSales");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton PRINCIPAL" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                if (validarNewBill()) {
                    showHideDialog("dlgC2", 1);
                    //doSave();
                    //printReport();
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

        if (newBill == null) {
            newBill = new SalesTO();
        }
        newBill.setObjtype("" + 10);
        newBill.setDocstatus(estadoDoc);
        newBill.setUsersign((int) session.getAttribute("usersign"));
        newBill.setCardname(socioNeg);
        newBill.setCardcode(codSocio);
        newBill.setNamenp(clientName);
        newBill.setRef2("" + equipo);
        newBill.setNumatcard(getRefe() + "");
        newBill.setDocdate(fechaConta);
        newBill.setTaxdate(fechaDoc);
        newBill.setSeries(tipoDoc);
        newBill.setPeymethod("" + formaPago);
        newBill.setTowhscode(alm);
        newBill.setComments(coment);
        newBill.setCtlaccount(ctlaccount);

        Double gTotalPadre = 0.0;
        Double sumImp = 0.0;
        Iterator<SalesDetailTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            try {
                SalesDetailTO articleDetalle = (SalesDetailTO) iterator2.next();
                articleDetalle.setLinenum(line + 1);
                gTotalPadre = gTotalPadre + articleDetalle.getGtotal();
                sumImp = sumImp + articleDetalle.getVatsum();
                line = line + 1;
            } catch (Exception e) {
            }

        }

        newBill.setVatsum(sumImp);
        newBill.setDoctotal(gTotalPadre);
        newBill.setSalesDetails(listaPadre);
        if (isRemision) {
            newBill.setReceiptnum(docEntryRemision);
        }

        try {
            ResultOutTO _res;
            _res = SalesEJBService.inv_Sales_mtto(newBill, Common.MTTOINSERT); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                if (isRemision) {

                    this.isRemision = false;
                    //faceMessage("actualizar remison " + this.docEntryRemision);
                    //updRemision(this.docEntryRemision);
                }

                docEntry = _res.getDocentry();
                newBill = SalesEJBService.getSalesByKey(docEntry);
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
        newBill.setObjtype("" + 10);
        newBill.setDocentry(docEntry);
        newBill.setDocnum(docNum);
        newBill.setDocstatus(estadoDoc);

        newBill.setUsersign((int) session.getAttribute("usersign"));
        newBill.setCardname(socioNeg);
        newBill.setCardcode(codSocio);
        newBill.setNamenp(clientName);
        newBill.setRef2("" + equipo);

        newBill.setNumatcard(refe + "");

        if (coment.equals("")) {
            newBill.setComments(vacio);
        } else {
            newBill.setComments(coment);
        }

        newBill.setDocdate(fechaConta);
        newBill.setTaxdate(fechaDoc);
        newBill.setSeries(tipoDoc);
        newBill.setPeymethod("" + formaPago);
        newBill.setTowhscode(alm);

        newBill.setSalesDetails(listaPadre);

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
        SalesInTO searchBill = new SalesInTO();

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
        if (refe == 0) {
            searchBill.setRef1(vacio);
        } else {
            searchBill.setNumatcard(refe + "");
        }

        searchBill.setDocdate(fechaConta);
        searchBill.setTaxdate(fechaDoc);

        searchBill.setSeries(tipoDoc);

        String var3 = "" + formaPago;
        if (var3.equals("0")) {
            searchBill.setPeymethod(vacio);
        } else {
            searchBill.setPeymethod("" + formaPago);
        }

        if (alm.equals("-1")) {
            searchBill.setTowhscode(vacio);
        } else {
            searchBill.setTowhscode(alm);
        }

        if (estadoDoc.equals("-1")) {
            searchBill.setDocstatus(vacio);
        } else {
            if (estadoDoc.equals("S")) {
                searchBill.setDocstatus(vacio);
                searchBill.setCanceled("Y");
            } else {
                searchBill.setDocstatus(estadoDoc);
                searchBill.setCanceled("N");
            }
        }

        if (coment.equals("")) {
            searchBill.setComments(vacio);
        } else {
            searchBill.setComments(coment);
        }

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
                    llenarPantalla(var, 1);
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
                showHideDialog("dlgListBill", 1);

            }
        } else {
            faceMessage("No se encontraron Registros");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SET NEW CREDIT">
    public void doSetCreditNote() {
        newCredit = new ClientCrediTO();

        //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        //CreditNotesBean cn = (CreditNotesBean) ec.getApplicationMap().get("creditNotesBean");
        //newCredit.setDocentry(newBill.getDocentry());
        //newCredit.setDocnum(newBill.getDocnum());
        newCredit.setDocentry(docEntry);
        newCredit.setCardname(newBill.getCardname());
        newCredit.setCardcode(newBill.getCardcode());
        newCredit.setRef2(newBill.getRef2());
        newCredit.setRef1(newBill.getNumatcard());

        newCredit.setDocdate(newBill.getDocdate());
        newCredit.setDocduedate(newBill.getDocduedate());
        newCredit.setSeries(newBill.getSeries());
        newCredit.setPeymethod(newBill.getPeymethod());
        newCredit.setTowhscode(newBill.getTowhscode());

        newCredit.setComments(newBill.getComments());

        newCredit.setclientDetails(newBill.getSalesDetails());

        //cn.getnew
        //cn.setNewCredit(newCredit);
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="COPY FROM REMISION">
    public void copyFromRemision() {
        //faceMessage("Copiar desde remision");
        this.listaBusquedaRemision = new Vector();
        this.listaBusquedaTableRemision = new ArrayList<>();
        this.selectBill = new SalesTO();
        this.selectSocio = new BusinesspartnerTO();
        fromCopy = true;

        doSearchRemision();
        RequestContext.getCurrentInstance().update("lstRemision");
        if (listaBusquedaTableRemision.size() > 0) {
            showHideDialog("dlgRemision", 1);
        } else {
            faceMessage("No se encontraron registros para el socio: " + this.codSocio + " - " + this.socioNeg);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update remision">
    public void updRemision(int id) {
        DeliveryTO deli = new DeliveryTO();
        try {
            deli = SalesEJBService.getDeliveryByKey(id);
            deli.setDocstatus("C");
            SalesEJBService.inv_Delivery_mtto(deli, Common.MTTOUPDATE);
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="doSearchRemision">
    private void doSearchRemision() {
        String vacio = null;
        DeliveryInTO searchDeli = new DeliveryInTO();

        searchDeli.setCardcode(codSocio);
        searchDeli.setCardname(socioNeg);
        searchDeli.setDocstatus("O");

        //searchDeli.setDocdate(fechaConta);
        //searchDeli.setTaxdate(fechaDoc);
        //if (refe == 0) {
        //    searchDeli.setNumatcard(vacio);
        //} else {
        //    searchDeli.setNumatcard(refe + "");
        //}
        try {
            listaBusquedaRemision = SalesEJBService.getDelivery(searchDeli);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }

        for (Object receipt : listaBusquedaRemision) {
            try {
                DeliveryTO var = (DeliveryTO) receipt;
                listaBusquedaTableRemision.add(var);
            } catch (Exception e) {
                faceMessage(e.getMessage() + " - " + e.getCause());
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Select from remision">
    //evento al seleccionar un elemento del dialogo facturas
    public void selectDialogRemision() {
        if (SalesEJBService == null) {
            SalesEJBService = new SalesEJBClient();
        }
        DeliveryTO newDelivery = new DeliveryTO();

        showHideDialog("dlgRemision", 2);
        DeliveryTO var = (DeliveryTO) selectRemision;
        this.isRemision = true;
        this.docEntryRemision = selectRemision.getDocentry();
        try {
            newDelivery = SalesEJBService.getDeliveryByKey(var.getDocentry());
            newBill.setObjtype("" + 10);
            newBill.setDocstatus("O");
            newBill.setUsersign((int) session.getAttribute("usersign"));
            newBill.setCardname(newDelivery.getCardname());
            newBill.setCardcode(newDelivery.getCardcode());
            newBill.setRef2(newDelivery.getRef2());
            newBill.setNumatcard(newDelivery.getNumatcard());
            newBill.setDocdate(newDelivery.getDocdate());
            newBill.setTaxdate(newDelivery.getTaxdate());
            newBill.setSeries(newDelivery.getSeries());
            newBill.setPeymethod(newDelivery.getPeymethod());
            newBill.setTowhscode(newDelivery.getTowhscode());
            newBill.setComments(newDelivery.getComments());
            // newBill.setVatsum(newDelivery.getVatsum());
            newBill.setDoctotal(newDelivery.getDoctotal());

            List bill = newDelivery.getDeliveryDetails();
            List listaux = new Vector();
            for (Object var2 : bill) {
                listaux.add(var2);
            }

            newBill.setSalesDetails(listaux);

            llenarPantalla(newBill, 2);
            estateGuardar();
            disabledComun = false;
            selected = true;
        } catch (Exception ex) {
            faceMessage("Error en busqueda por PK");
        }

        listaBusquedaRemision = new Vector();
        listaBusquedaTableRemision = new ArrayList<>();
        selectRemision = new DeliveryTO();
        RequestContext.getCurrentInstance().update("frmSales");

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Select Tipo Documento">
    public void changeTipoDoc() {
        String num;
        try {
            num = SalesEJBService.last_INPUT(tipoDoc, 10 + "");
            if (isNum(num)) {
                int ref = 0;
                ref = Integer.parseInt(num);
                ref = ref + 1;
                this.refe = ref;
            } else {
                this.refe = 0;
            }
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isNum(String num) {
        if (num == null || num.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Seleccionar un almacen y Forma de pago">
    public void stateChange1(ValueChangeEvent event) {

        this.setFormaPago((int) event.getNewValue());

        if (validarRequeridos()) {
            String pago = this.getFormaPago() + "";
            try {
                CatalogTO cat = AdminEJBService.findCatalogByKey(pago, 8);
                this.precioArt = Integer.parseInt(cat.getCatvalue2());
            } catch (Exception ex) {
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (required1) {
                disabledComun = false;
                selected = true;
            }

            //RequestContext.getCurrentInstance().update("frmSales");
        }

    }

    public void stateChange2(ValueChangeEvent event) {

        this.setAlm(event.getNewValue().toString());

        if (validarRequeridos()) {
            String pago = this.getFormaPago() + "";
            try {
                CatalogTO cat = AdminEJBService.findCatalogByKey(pago, 8);
                this.precioArt = Integer.parseInt(cat.getCatvalue2());
            } catch (Exception ex) {
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
            //RequestContext.getCurrentInstance().update("frmSales");
        }

    }

    public void stateChange3(ValueChangeEvent event) {

        this.setSocioNeg(event.getNewValue().toString());

        if (!socioNeg.isEmpty()) {
            this.actBtn = false;
        }

        if (validarRequeridos()) {
            String pago = this.getFormaPago() + "";
            try {
                CatalogTO cat = AdminEJBService.findCatalogByKey(pago, 8);
                this.precioArt = Integer.parseInt(cat.getCatvalue2());
            } catch (Exception ex) {
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
            //RequestContext.getCurrentInstance().update("frmSales");
        }
    }

    public void stateChange4(ValueChangeEvent event) {

        this.setCodSocio(event.getNewValue().toString());

        if (!codSocio.isEmpty()) {
            this.actBtn = false;
        }

        if (validarRequeridos()) {
            String pago = this.getFormaPago() + "";
            try {
                CatalogTO cat = AdminEJBService.findCatalogByKey(pago, 8);
                this.precioArt = Integer.parseInt(cat.getCatvalue2());
            } catch (Exception ex) {
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.setAlm(event.getNewValue().toString());
            if (required1) {
                disabledComun = false;
                selected = true;
            }
            //RequestContext.getCurrentInstance().update("frmSales");
        }
    }

    public boolean validarRequeridos() {
        try {
            return formaPago > 0 && !alm.equals("-1") && !socioNeg.isEmpty() && !codSocio.isEmpty();
        } catch (Exception e) {
            return false;
        }

    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones Varias">
    private boolean validatePrice() {
        if (this.newPrecio < 0) {
            faceMessage("Articulo sin precio de venta");
            return false;
        }
        return true;
    }

    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void confirmDialog() {
        //RequestContext.getCurrentInstance().update("frmCalculos");

        showHideDialog("dlgC2", 2);

        if (varEstados == 1) {
            doSave();
            RequestContext.getCurrentInstance().update("frmCalculos");
            showHideDialog("dlgCambio", 1);

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

    public void cancelDialogp(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgPtr').hide();");
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
            llenarPantalla(newBill, 1);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectBill = new SalesTO();
        RequestContext.getCurrentInstance().update("frmSales");

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
    public void llenarPantalla(SalesTO var, int tipo) {
        setEstadoDoc(var.getDocstatus());

        setSocioNeg(var.getCardname());
        setClientName(var.getNamenp());
        setCodSocio(var.getCardcode());
        setEquipo(Integer.parseInt(var.getRef2()));
        //setRefe(var.getRef1());

        setFechaConta(var.getDocdate());
        setFechaDoc(var.getTaxdate());
        setTipoDoc(var.getSeries());
        setFormaPago(Integer.parseInt(var.getPeymethod()));
        setAlm(var.getTowhscode());

        setComent(var.getComments());

        try {
            setRefe(Integer.parseInt(var.getNumatcard()));
        } catch (Exception e) {
            setRefe(0);
        }

        if (tipo == 2) { //Se utiliza cuando la factura es generada a partir de una Nota de Remisión    
            for (Object detBill : var.getSalesDetails()) {
                DeliveryDetailTO bill = (DeliveryDetailTO) detBill;
                SalesDetailTO newDetalle = new SalesDetailTO();

                newDetalle.setBaseentry(bill.getDocentry());
                newDetalle.setBaseline(bill.getLinenum());

                newDetalle.setItemcode(bill.getItemcode());
                newDetalle.setDscription(bill.getDscription());
                newDetalle.setQuantity(bill.getQuantity());

                newDetalle.setPrice(bill.getPrice());
                newDetalle.setPricebefdi(bill.getPrice());

                newDetalle.setLinetotal(bill.getLinetotal());
                newDetalle.setUnitmsr(bill.getUnitmsr());
                newDetalle.setLinenum(bill.getLinenum());
                newDetalle.setTaxstatus(bill.getTaxstatus());
                newDetalle.setVatgroup(bill.getVatgroup());

                newDetalle.setPriceafvat(bill.getPriceafvat()); //precio bruto  
                //newDetalle.setLinetotal(newDetalle.getPriceafvat() - descuentos);//total - descuentos
                newDetalle.setVatsum(bill.getVatsum());//suma total de impuestos aplicados
                newDetalle.setGrssprofit(bill.getGrssprofit());//precio venta - costo
                newDetalle.setTaxcode(bill.getTaxcode());//FOV
                newDetalle.setVatappld(bill.getVatappld());//igual a vatsum
                newDetalle.setStockpricestockprice(bill.getStockpricestockprice());//
                newDetalle.setGtotal(bill.getGtotal());
                newDetalle.setAcctcode("cuenta");

                this.listaDetalles.add(newDetalle);
                this.listaPadre.add(newDetalle);

            }//cierre for
            disabledComun = false;
            selected = true;

        } else {
            setDocEntry(var.getDocentry());
            setDocNum(var.getDocnum());

            for (Object detalle : var.getSalesDetails()) {
                SalesDetailTO det = (SalesDetailTO) detalle;
                this.listaDetalles.add(det);
            }
        }

        calcularTotalBill(listaDetalles);
    }

    //validar nueva factura
    public boolean validarNewBill() {
        if (socioNeg == null && codSocio == null) {
            faceMessage("Seleccione un Socio de Negocio ó Codigo de Socio");
            return false;
        }
        if (fechaConta == null && fechaDoc == null || fechaConta == null || fechaDoc == null) {
            faceMessage("Seleccione ambas fechas");
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
        return !newCod.isEmpty() && !newNomArt.isEmpty() && newCantidad > 0;
    }

    //limpiar el bean
    public void cleanBean(int tipo) {
        cleanDetalle();
        this.newBill = new SalesTO();
        this.precioArt = 0;
        this.newCredit = new ClientCrediTO();

        this.listaDetalles.clear();
        this.listaPadre.clear();
        //variables desabilitadas
        this.disabledComun = true;
        this.selected = false;

        //encabezado
        this.docNum = 0;
        this.docEntry = 0;
        this.socioNeg = null;
        this.codSocio = null;
        this.clientName = null;
        this.equipo = 0;
        this.refe = 0;
        this.alm = null;
        if (tipo == 1) {
            Date d = new Date();
            this.setFechaConta(d);
            this.setFechaDoc(d);
        } else {
            Date d = null;
            this.setFechaConta(d);
            this.setFechaDoc(d);
        }

        this.tipoDoc = 1;
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
        this.isRemision = false;
    }

    //limpiar variables
    public void cleanDetalle() {
        newCod = null;
        newNomArt = null;
        newPrecio = null;
        newPriceafvat = null;
        newCantidad = null;
        newTotal = null;
        newTotalafvat = null;
        newUnidad = null;
        newExistencia = null;

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
        if (equipo > 0 || formaPago > 0) {
            return true;
        }
        try {
            if (socioNeg != null || codSocio != null) {
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

//<editor-fold defaultstate="collapsed" desc="Imprimir" > 
    public void printReport() {

        try {
            if (bean == null) {
                bean = new ReportsBean();
            }
            CatalogTO _R = AdminEJBService.findCatalogByKey(this.formaPago + "", 8);

            String numberToletter;
            numberToletter = convertNumber.convertNumberToLetter(this.total);

            Map<String, Object> reportParameters = new HashMap<>();

            reportParameters.put("number", numberToletter);
            reportParameters.put("docEntry", this.docEntry);
            reportParameters.put("formaPago", _R.getCatvalue());

            this.bean.setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "HTML"));
            this.bean.setFileName("##-FAC");
            this.bean.setParameters(reportParameters);
            this.bean.setReportName("/sales/SalesReport");
            this.bean.execute();

        } catch (Exception e) {
            faceMessage("Error al generar reporte: " + e.getMessage() + " " + e.getCause());
        }

    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="IMPRIMIR FORMA 2">
    public String printInvoice() throws UnsupportedEncodingException {
        //faceMessage(getApplicationUri());
        //System.out.println(getApplicationUri()+"||----------------------------------------------------------------");
        setUrl(getApplicationUri());
        if (newBill.getDocentry() > 0) {
            if (newBill.getSeries() == 1 || newBill.getSeries() == 2) {
                if (newBill.getSeries() == 1) {//consumidor final
                    String foo = newBill.getDocentry() + "";
                    String bar = (String) session.getAttribute("userfullname");
                    String tip = "1";//venta-sales
                    return "/testPrintView?faces-redirect=true"
                            + "&foo=" + URLEncoder.encode(foo, "UTF-8")
                            + "&bar=" + URLEncoder.encode(bar, "UTF-8")
                            + "&tip=" + URLEncoder.encode(tip, "UTF-8");
                }
                if (newBill.getSeries() == 2) {//credito fiscal
                    String foo = newBill.getDocentry() + "";
                    String bar = (String) session.getAttribute("userfullname");
                    String tip = "1";//venta-sales
                    return "/testSalesView?faces-redirect=true"
                            + "&foo=" + URLEncoder.encode(foo, "UTF-8")
                            + "&bar=" + URLEncoder.encode(bar, "UTF-8")
                            + "&tip=" + URLEncoder.encode(tip, "UTF-8");
                }
            } else {
                faceMessage("No se puede imprimir este tipo de documento");
                return "/view/sales/Sales.xhtml";
            }
        } else {
            faceMessage("No se puede imprimir");
            return "/view/sales/Sales.xhtml";
        }
        return null;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="PRINT COTIZACION">
    public String printCoti() throws UnsupportedEncodingException {
        if (this.varEstados == 1) {
            setUrl(getApplicationUri());

            if (this.listaDetalles.size() >= 1) {
                setSessionObj();
                String foo = "COTIZACION";
                String bar = (String) session.getAttribute("userfullname");
                return "/quoteView?faces-redirect=true"
                        + "&foo=" + URLEncoder.encode(foo, "UTF-8")
                        + "&bar=" + URLEncoder.encode(bar, "UTF-8");
            } else {
                faceMessage("Ingrese al menos un articulo para imprimir cotización");
                return "/view/sales/Sales.xhtml";
            }
        }
        return null;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="llenar objeto en sesion ">
    public void setSessionObj() {
        //setear valores
        SalesTO newCot = new SalesTO();
        int line = 0;
        newCot.setObjtype("" + 10);
        newCot.setDocstatus(estadoDoc);
        newCot.setUsersign((int) session.getAttribute("usersign"));
        newCot.setCardname(socioNeg);
        newCot.setCardcode(codSocio);
        newCot.setRef2("" + equipo);
        newCot.setNumatcard(getRefe() + "");
        newCot.setDocdate(fechaConta);
        newCot.setTaxdate(fechaDoc);
        newCot.setSeries(tipoDoc);
        newCot.setPeymethod("" + formaPago);
        newCot.setTowhscode(alm);
        newCot.setComments(coment);
        newCot.setCtlaccount(ctlaccount);

        Double gTotalPadre = 0.0;
        Double sumImp = 0.0;
        Iterator<SalesDetailTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            try {
                SalesDetailTO articleDetalle = (SalesDetailTO) iterator2.next();
                articleDetalle.setLinenum(line + 1);
                gTotalPadre = gTotalPadre + articleDetalle.getGtotal();
                sumImp = sumImp + articleDetalle.getVatsum();
                line = line + 1;
            } catch (Exception e) {
            }

        }

        newCot.setVatsum(sumImp);
        newCot.setDoctotal(gTotalPadre);
        newCot.setSalesDetails(listaPadre);

        session.setAttribute("objSales", newCot);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="redirect">
    public void redirect() throws IOException {
        String url2 = getUrl() + "/faces/view/sales/Sales.xhtml"; //url donde se redirige la pantalla
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

//<editor-fold defaultstate="collapsed" desc="G & S" >
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        SalesBean.CatalogEJB = CatalogEJB;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        SalesBean.AdminEJBService = AdminEJBService;
    }

    public static SalesEJBClient getSalesEJBService() {
        return SalesEJBService;
    }

    public static void setSalesEJBService(SalesEJBClient SalesEJBService) {
        SalesBean.SalesEJBService = SalesEJBService;
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

    public ExternalContext getEc() {
        return ec;
    }

    public void setEc(ExternalContext ec) {
        this.ec = ec;
    }

    public CreditNotesBean getCn() {
        return cn;
    }

    public void setCn(CreditNotesBean cn) {
        this.cn = cn;
    }

    public numerosAletras getConvertNumber() {
        return convertNumber;
    }

    public void setConvertNumber(numerosAletras convertNumber) {
        this.convertNumber = convertNumber;
    }

    public boolean isFromCopy() {
        return fromCopy;
    }

    public void setFromCopy(boolean fromCopy) {
        this.fromCopy = fromCopy;
    }

    public boolean isActBtn() {
        return actBtn;
    }

    public void setActBtn(boolean actBtn) {
        this.actBtn = actBtn;
    }

    public boolean isIsRemision() {
        return isRemision;
    }

    public void setIsRemision(boolean isRemision) {
        this.isRemision = isRemision;
    }

    public int getDocEntryRemision() {
        return docEntryRemision;
    }

    public void setDocEntryRemision(int docEntryRemision) {
        this.docEntryRemision = docEntryRemision;
    }

    public List getListaBusquedaRemision() {
        return listaBusquedaRemision;
    }

    public void setListaBusquedaRemision(List listaBusquedaRemision) {
        this.listaBusquedaRemision = listaBusquedaRemision;
    }

    public ArrayList<DeliveryTO> getListaBusquedaTableRemision() {
        return listaBusquedaTableRemision;
    }

    public void setListaBusquedaTableRemision(ArrayList<DeliveryTO> listaBusquedaTableRemision) {
        this.listaBusquedaTableRemision = listaBusquedaTableRemision;
    }

    public DeliveryTO getSelectRemision() {
        return selectRemision;
    }

    public void setSelectRemision(DeliveryTO selectRemision) {
        this.selectRemision = selectRemision;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isBtnPrint() {
        return btnPrint;
    }

    public void setBtnPrint(boolean btnPrint) {
        this.btnPrint = btnPrint;
    }

    public String getExportOption() {
        return exportOption;
    }

    public void setExportOption(String exportOption) {
        this.exportOption = exportOption;
    }

    public ReportsBean getBean() {
        return bean;
    }

    public void setBean(ReportsBean bean) {
        this.bean = bean;
    }

    public int getPrecioArt() {
        return precioArt;
    }

    public void setPrecioArt(int precioArt) {
        this.precioArt = precioArt;
    }

    public boolean isFromSales() {
        return fromSales;
    }

    public void setFromSales(boolean fromSales) {
        this.fromSales = fromSales;
    }

    public ClientCrediTO getNewCredit() {
        return newCredit;
    }

    public void setNewCredit(ClientCrediTO newCredit) {
        this.newCredit = newCredit;
    }

    public boolean isCreditNote() {
        return creditNote;
    }

    public void setCreditNote(boolean creditNote) {
        this.creditNote = creditNote;
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

    public SalesTO getSelectBill() {
        return selectBill;
    }

    public void setSelectBill(SalesTO selectBill) {
        this.selectBill = selectBill;
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

    public SalesDetailTO getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(SalesDetailTO selectDetail) {
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

    public ArrayList<SalesTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<SalesTO> listaBusquedaTable) {
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

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public int getRefe() {
        return refe;
    }

    public void setRefe(int refe) {
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

    /**
     * @return the newPriceafvat
     */
    public Double getNewPriceafvat() {
        return newPriceafvat;
    }

    /**
     * @param newPriceafvat the newPriceafvat to set
     */
    public void setNewPriceafvat(Double newPriceafvat) {
        this.newPriceafvat = newPriceafvat;
    }

    public Double getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(Double newTotal) {
        this.newTotal = newTotal;
    }

    /**
     * @return the newTotalafvat
     */
    public Double getNewTotalafvat() {
        return newTotalafvat;
    }

    /**
     * @param newTotalafvat the newTotalafvat to set
     */
    public void setNewTotalafvat(Double newTotalafvat) {
        this.newTotalafvat = newTotalafvat;
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

    public ArrayList<SalesDetailTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<SalesDetailTO> listaDetalles) {
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
}//Cierre de clase
