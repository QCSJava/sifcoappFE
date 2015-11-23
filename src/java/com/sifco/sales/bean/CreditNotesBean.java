/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.sales.bean;

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
import com.sifcoapp.objects.sales.to.ClientCrediDetailTO;
import com.sifcoapp.objects.sales.to.ClientCrediInTO;
import com.sifcoapp.objects.sales.to.ClientCrediTO;
import com.sifcoapp.objects.sales.to.SalesDetailTO;
import com.sifcoapp.objects.sales.to.SalesInTO;
import com.sifcoapp.objects.sales.to.SalesTO;
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

@ManagedBean(name = "creditNotesBean")
@ApplicationScoped
public class CreditNotesBean implements Serializable {

    public CreditNotesBean() {
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
    private int newCreditFromBill;
    private ClientCrediTO newCredit = new ClientCrediTO();        //Nueva Factura
    private ClientCrediTO selectCredit = new ClientCrediTO();        //Select de lista busqueda
    private BusinesspartnerTO selectSocio = new BusinesspartnerTO();
    private int precioArt;
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

    private Double newPrecio;       //Precio
    private Double newTotal;        //Total de detalle
    private String newUnidad;       //Unidad de Medida
    private Double newExistencia;   //Existencia

    private ClientCrediDetailTO selectDetail = new ClientCrediDetailTO(); //detalle seleccionado para eliminar
    private ArrayList<ClientCrediDetailTO> listaDetalles = new ArrayList<>(); //DataTable 
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
    private boolean required1, rendered;
    

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private List listaBusquedaBill = new Vector();
    private ArrayList<ClientCrediTO> listaBusquedaTable = new ArrayList<>();
    private ArrayList<SalesTO> listaBusquedaTableBill = new ArrayList<>();

    private SalesTO selectBill = new SalesTO();

    private String productId;

    //__________________________________________________________________________
    //from bill
    private ExternalContext ec;// = FacesContext.getCurrentInstance().getExternalContext();
    private SalesBean cn;// = (SalesBean) ec.getApplicationMap().get("salesBean");
    private boolean accion;

    //__________________________________________________________________________
    //btn copiar
    private boolean fromCopy;
    private boolean actBtn, isBill;
    private int docEntryBill;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >  
    @PostConstruct
    public void initForm() {
        ec = FacesContext.getCurrentInstance().getExternalContext();
        cn = (SalesBean) ec.getApplicationMap().get("salesBean");

        //variables de estados habilitados/desabilitados
        this.required1 = true;
        this.disabledDocNum = true;
        this.disabledSearch = false;
        this.disabledComun = true;
        this.selected = false;
        this.actBtn = true;

        //this.estadoDoc = Common.DocStatusOpen;
        //Inicializacion de servicios
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

        //Estado por defecto
        estateGuardar();

        try {
            if (cn.getNewCredit().getDocentry() > 0) {
                doCreditFromBill();
                llenarPantalla(newCredit, 2);
            }
        } catch (Exception e) {
            faceMessage(e.getMessage() + " - " + e.getCause());
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
            results.add(articulo.getCardname());
        }
        return results;
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
                                newPrecio = art.getPrice(precioArt);
                            } else {
                                if (factor > 0) {
                                    newPrecio = art.getPrice(precioArt) / factor;
                                } else {
                                    faceMessage("Factor articulo incorrecto " + factor);
                                }
                            }

                        } else {
                            faceMessage("no hay factor");
                            newPrecio = art.getPrice(precioArt);
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
                        Logger.getLogger(CreditNotesBean.class.getName()).log(Level.SEVERE, null, ex);
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
                                Logger.getLogger(CreditNotesBean.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            newPrecio = art.getPrice(selectSocio.getListnum());
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

                ClientCrediDetailTO newDetalle = new ClientCrediDetailTO();
                ArticlesTO thisArt = new ArticlesTO();
                CatalogTO thisCat = new CatalogTO();
                newDetalle.setObjtype(""+11);
                newDetalle.setItemcode(newCod);
                newDetalle.setDscription(newNomArt);
                newDetalle.setQuantity(newCantidad);

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
                            //Double descuentos = 0.0;

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
                calcularTotalBill(listaDetalles); //calculos por cada articulo agregado
            }
        } catch (Exception e) {
        }
        cleanDetalle();
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Calcular Impuestos y TOTAL">
    public void calcularTotalBill(ArrayList<ClientCrediDetailTO> listaArt) {
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
    public Double calcularGravadas(ArrayList<ClientCrediDetailTO> listaArt) {
        Double sumTotal = 0.0;
        for (ClientCrediDetailTO art : listaArt) {
            sumTotal = sumTotal + art.getLinetotal();
        }
        return sumTotal;
    }

    public Double calcularExentas(ArrayList<ClientCrediDetailTO> listaArt) {
        return 0.0;

    }

    public Double calcularIVA(ArrayList<ClientCrediDetailTO> listaArt) {
        Double sumIVA = 0.0;
        //Double sumTotal = 0.0;
        for (ClientCrediDetailTO det : listaArt) {
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

    public Double calcularRetencion(ArrayList<ClientCrediDetailTO> listaArt) {
        return 0.0;
    }

    public Double calcularFovial(ArrayList<ClientCrediDetailTO> listaArt) {
        Double sumFovial = 0.0;
        for (ClientCrediDetailTO det : listaArt) {
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

    public Double calcularCotrans(ArrayList<ClientCrediDetailTO> listaArt) {
        Double sumCotrans = 0.0;
        for (ClientCrediDetailTO det : listaArt) {
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
        this.actBtn = true;
        this.required1 = true;
        this.rendered = true;
        RequestContext.getCurrentInstance().update("frmCreditNotes");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";

        this.disabledDocNum = true;//false
        this.disabledSearch = true;
        this.disabledComun = true;
        this.selected = true;
        this.required1 = true;
        this.rendered = false;
        this.actBtn = true;
        try {
            //RequestContext.getCurrentInstance().update("frmCreditNotes");
            reload();
        } catch (IOException ex) {
            Logger.getLogger(CreditNotesBean.class.getName()).log(Level.SEVERE, null, ex);
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
        this.actBtn = true;
        this.required1 = false; //forma de pago y almacen no requeridos en busqueda
        this.rendered = false;
        RequestContext.getCurrentInstance().update("frmCreditNotes");
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
        String vacio = null;

        if (newCredit == null) {
            newCredit = new ClientCrediTO();
        }
        newCredit.setObjtype(""+11);
        newCredit.setDocstatus(estadoDoc);
        newCredit.setUsersign((int) session.getAttribute("usersign"));
        newCredit.setCardname(socioNeg);
        newCredit.setCardcode(codSocio);
        newCredit.setRef2("" + equipo);

        newCredit.setDocdate(fechaConta);
        newCredit.setTaxdate(fechaDoc);
        
        newCredit.setSeries(tipoDoc);
        newCredit.setPeymethod("" + formaPago);
        newCredit.setTowhscode(alm);
        newCredit.setCtlaccount(ctlaccount);

        if (refe.equals("")) {
            newCredit.setRef1(vacio);
        } else {
            newCredit.setNumatcard(refe);
        }

        if (coment.equals("")) {
            newCredit.setComments(vacio);
        } else {
            newCredit.setComments(coment);
        }

        Double gTotalPadre = 0.0;
        Iterator<ClientCrediDetailTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            ClientCrediDetailTO articleDetalle = (ClientCrediDetailTO) iterator2.next();
            articleDetalle.setLinenum(line + 1);
            gTotalPadre = gTotalPadre + articleDetalle.getGtotal();
            line = line + 1;
        }

        newCredit.setDoctotal(gTotalPadre);
        newCredit.setclientDetails(listaPadre);
        if (isBill) {
            newCredit.setReceiptnum(docEntryBill);
        }
        try {
            ResultOutTO _res;
            _res = SalesEJBService.inv_ClientCredi_mtto(newCredit, Common.MTTOINSERT); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                if (isBill) {
                    this.isBill = false;
                }
                docEntry = _res.getDocentry();
                docNum = docEntry; //
                newCredit = SalesEJBService.getClientCrediByKey(docEntry);
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

        if (newCredit == null) {
            newCredit = new ClientCrediTO();
        }
        String vacio = null;
        newCredit.setObjtype(""+11);
        newCredit.setDocentry(docEntry);
        newCredit.setDocnum(docNum);
        newCredit.setDocstatus(estadoDoc);
 
        newCredit.setUsersign((int) session.getAttribute("usersign"));
        newCredit.setCardname(socioNeg);
        newCredit.setCardcode(codSocio);
        newCredit.setRef2("" + equipo);

        if (refe.equals("")) {
            newCredit.setNumatcard(vacio);
        } else {
            newCredit.setNumatcard(refe);
        }

        if (coment.equals("")) {
            newCredit.setComments(vacio);
        } else {
            newCredit.setComments(coment);
        }

        newCredit.setDocdate(fechaConta);
        newCredit.setTaxdate(fechaDoc);
        newCredit.setSeries(tipoDoc);
        newCredit.setPeymethod("" + formaPago);
        newCredit.setTowhscode(alm);

        newCredit.setclientDetails(listaPadre);

        try {
            ResultOutTO _res;
            _res = SalesEJBService.inv_ClientCredi_mtto(newCredit, Common.MTTOUPDATE); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                //docEntry = _res.getDocentry();
                //docNum = docEntry; //
                faceMessage(_res.getMensaje() + " Guardado");

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
        ClientCrediInTO searchBill = new ClientCrediInTO();

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
            searchBill.setNumatcard(vacio);
        } else {
            searchBill.setNumatcard(refe);
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
            searchBill.setDocstatus(estadoDoc);
        }

        if (coment.equals("")) {
            searchBill.setComments(vacio);
        } else {
            searchBill.setComments(coment);
        }

        try {
            listaBusqueda = SalesEJBService.getClientCredi(searchBill);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newCredit = (ClientCrediTO) listaBusqueda.get(0);
                try {

                    ClientCrediTO var = SalesEJBService.getClientCrediByKey(newCredit.getDocentry());
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
                        ClientCrediTO var = (ClientCrediTO) receipt;
                        /*String formaPagoS, tipoDocS;      //que se muestra en DT

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
                showHideDialog("dlgListCredit", 1);

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
            String pago =  this.getFormaPago()+"";
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

    public void stateChange2(ValueChangeEvent event) {

        this.setAlm(event.getNewValue().toString());

        if (validarRequeridos()) {
            String pago =  this.getFormaPago()+"";
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
        try {
            this.setSocioNeg(event.getNewValue().toString());
            
            if (!socioNeg.isEmpty()) 
                this.actBtn = false;
        
            if (validarRequeridos()) {
                String pago =  this.getFormaPago()+"";
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
            
        } catch (Exception e) {
            faceMessage(e.getMessage()+"socioneg");
        }
        
    }

    public void stateChange4(ValueChangeEvent event) {
        
        try {
            this.setCodSocio(event.getNewValue().toString());
        
            if (!codSocio.isEmpty()) 
                this.actBtn = false;

            if (validarRequeridos()) {
                String pago =  this.getFormaPago()+"";
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
            
        } catch (Exception e) {
            faceMessage((e.getMessage()+" " + e.getCause() + " codsocio"));
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
    
//<editor-fold defaultstate="collapsed" desc="CREAR CREDITNOTE FROM BILL">
    public void doCreditFromBill() {
        try {
            newCredit = new ClientCrediTO();

            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //SalesBean cn = (SalesBean) ec.getApplicationMap().get("salesBean");
            ClientCrediTO newBill = cn.getNewCredit();

            //newCredit.setDocentry(newBill.getDocentry());
            //newCredit.setDocnum(newBill.getDocnum());
            newCredit.setDocstatus(newBill.getDocstatus());
            newCredit.setCardname(newBill.getCardname());
            newCredit.setCardcode(newBill.getCardcode());
            newCredit.setRef2(newBill.getRef2());
            newCredit.setNumatcard(newBill.getRef1());

            newCredit.setDocdate(newBill.getDocdate());
            newCredit.setDocduedate(newBill.getDocduedate());
            newCredit.setSeries(newBill.getSeries());
            newCredit.setPeymethod(newBill.getPeymethod());
            newCredit.setTowhscode(newBill.getTowhscode());

            newCredit.setComments(newBill.getComments());

            List bill = newBill.getclientDetails();
            List listaux = new Vector();
            for (Object var : bill) {
                listaux.add(var);
            }

            newCredit.setclientDetails(listaux);
            accion = false;
            ClientCrediTO var = new ClientCrediTO();
            cn.setNewCredit(var);
            //llenarPantalla(newCredit);
        } catch (Exception ex) {
            Logger.getLogger(CreditNotesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Boton COPIAR DESDE BILL">
    public void btnCopyFromBill(ActionEvent actionEvent) {
        this.listaBusquedaBill = new Vector();
        this.listaBusquedaTableBill = new ArrayList<>();
        this.selectBill = new SalesTO();
        this.selectSocio = new BusinesspartnerTO();
        fromCopy = true;
        faceMessage("Copiar desde: Facturas Cliente");

        
        doSearchBill();
        RequestContext.getCurrentInstance().update("dlgCfromB");
        if (listaBusquedaTableBill.size() > 0) {
            showHideDialog("dlgListBill2", 1);
        }else{
            faceMessage("No se encontraron registros para el socio: " + this.codSocio + " - " + this.socioNeg);
        }
        
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="doSearchBill">
    public void doSearchBill() {
        SalesInTO searchBill = new SalesInTO();
        searchBill.setCardcode(codSocio);
        searchBill.setCardname(socioNeg);
        //searchBill.setDocstatus("O");
        searchBill.setCanceled("N");
        searchBill.setNumatcard(refe);
        searchBill.setDocdate(fechaConta);
        searchBill.setTaxdate(fechaDoc);

        try {
            listaBusquedaBill = SalesEJBService.getSales(searchBill);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }

        for (Object receipt : listaBusquedaBill) {
            try {
                SalesTO var = (SalesTO) receipt;

                listaBusquedaTableBill.add(var);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " - " + e.getCause());
            }
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="selec dialog bill">
    public void selectDialogCredit() {
        if (SalesEJBService == null) {
            SalesEJBService = new SalesEJBClient();
        }
        SalesTO newBill = new SalesTO();

        showHideDialog("dlgListBill2", 2);
        this.fromCopy = false;
        SalesTO var = (SalesTO) selectBill;
        this.isBill = true;
        this.docEntryBill = selectBill.getDocentry();

        try {
            newBill = SalesEJBService.getSalesByKey(var.getDocentry());

            BusinesspartnerInTO in = new BusinesspartnerInTO();
            in.setCardcode(newBill.getCardcode());
            in.setCardname(newBill.getCardname());
            List _result = CatalogEJB.get_businesspartner(in);
            BusinesspartnerTO art = (BusinesspartnerTO) _result.get(0);
            this.setSelectSocio(art);

            newCredit.setDocstatus(newBill.getDocstatus());
            newCredit.setCardname(newBill.getCardname());
            newCredit.setCardcode(newBill.getCardcode());
            newCredit.setRef2(newBill.getRef2());
            newCredit.setRef1(newBill.getRef1());

            newCredit.setDocdate(newBill.getDocdate());
            newCredit.setTaxdate(newBill.getTaxdate());
            newCredit.setSeries(newBill.getSeries());
            newCredit.setPeymethod(newBill.getPeymethod());
            newCredit.setTowhscode(newBill.getTowhscode());

            newCredit.setComments(newBill.getComments());

            List bill = newBill.getSalesDetails();
            List listaux = new Vector();
            for (Object var2 : bill) {
                listaux.add(var2);
            }

            newCredit.setclientDetails(listaux);

            estateGuardar();
            llenarPantalla(newCredit, 2);
            disabledComun = false;
            selected = true;
        } catch (Exception ex) {
            faceMessage("Error en busqueda por PK");
        }

        listaBusquedaBill = new Vector();
        listaBusquedaTableBill = new ArrayList<>();
        selectBill = new SalesTO();
        RequestContext.getCurrentInstance().update("frmCreditNotes");

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

        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ex.redirect(((HttpServletRequest) ex.getRequest()).getRequestURI());
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
            if (fromCopy) {
                fromCopy = false;
                cleanBean(1);
                faceMessage("Seleccione una Factura");
                doSearchBill();
                RequestContext.getCurrentInstance().update("dlgCfromB");
                showHideDialog("dlgListBill2", 1);
            } else {
                cleanBean(1);
                estateGuardar();
            }
        } else {
            cleanBean(2);
            estateBuscar();
        }

        RequestContext.getCurrentInstance().update("frmCreditNotes");
    }

    //evento al seleccionar un elemento del dialogo facturas
    public void selectDialogBill() {
        if (SalesEJBService == null) {
            SalesEJBService = new SalesEJBClient();
        }

        if (newCredit == null) {
            newCredit = new ClientCrediTO();
        }

        showHideDialog("dlgListCredit", 2);
        ClientCrediTO var = (ClientCrediTO) selectCredit;

        try {
            newCredit = SalesEJBService.getClientCrediByKey(var.getDocentry());
            llenarPantalla(newCredit, 1);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectCredit = new ClientCrediTO();
        RequestContext.getCurrentInstance().update("frmCreditNotes");

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
    public void llenarPantalla(ClientCrediTO var, int tipo) {
        setEstadoDoc(var.getDocstatus());
        setDocNum(var.getDocnum());
        setDocEntry(var.getDocentry());
        setSocioNeg(var.getCardname());
        setCodSocio(var.getCardcode());
        setEquipo(Integer.parseInt(var.getRef2()));
        setRefe(var.getNumatcard());

        setFechaConta(var.getDocdate());
        setFechaDoc(var.getTaxdate());
        setTipoDoc(var.getSeries());
        setFormaPago(Integer.parseInt(var.getPeymethod()));
        setAlm(var.getTowhscode());

        setComent(var.getComments());

        if (tipo == 2) {
            for (Object detBill : var.getclientDetails()) {
                SalesDetailTO bill = (SalesDetailTO) detBill;
                ClientCrediDetailTO newDetalle = new ClientCrediDetailTO();

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
            for (Object detalle : var.getclientDetails()) {
                ClientCrediDetailTO det = (ClientCrediDetailTO) detalle;
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
        
        this.precioArt = 0;
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
        } else {
            Date d = null;
            this.setFechaConta(d);
            this.setFechaDoc(d);
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

        //this.listaDetalles.clear();
    }

    //mostrar un mensaje en pantalla dado un string
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public boolean validarClear() {
        return this.listaPadre.size() >= 1 && this.varEstados != 2 || doClean() && this.varEstados != 2;// && this.listaBusquedaTable.size() >=1;     
    }
    
    public boolean doClean(){
        if (equipo>0 || tipoDoc>0 || formaPago>0 ) {
            return true;
        }
        try {
            if (socioNeg != null || codSocio!= null ||  !refe.equals("") ||  !alm.equals("-1")) {
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
    
//<editor-fold defaultstate="collapsed" desc="Update bill">
    public void updBillCopy(int id) {
        SalesTO updBill = new SalesTO();
        try {
            updBill = SalesEJBService.getSalesByKey(id);
            updBill.setDocstatus("C");
            SalesEJBService.inv_Sales_mtto(updBill, Common.MTTOUPDATE);
        } catch (Exception ex) {
            Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        CreditNotesBean.CatalogEJB = CatalogEJB;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        CreditNotesBean.AdminEJBService = AdminEJBService;
    }

    public static SalesEJBClient getSalesEJBService() {
        return SalesEJBService;
    }

    public static void setSalesEJBService(SalesEJBClient SalesEJBService) {
        CreditNotesBean.SalesEJBService = SalesEJBService;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
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

    public SalesBean getCn() {
        return cn;
    }

    public void setCn(SalesBean cn) {
        this.cn = cn;
    }
    
    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }
    
    
    public int getPrecioArt() {
        return precioArt;
    }

    public void setPrecioArt(int precioArt) {
        this.precioArt = precioArt;
    }
    
    
    public boolean isActBtn() {
        return actBtn;
    }

    public void setActBtn(boolean actBtn) {
        this.actBtn = actBtn;
    }

    public SalesTO getSelectBill() {
        return selectBill;
    }

    public void setSelectBill(SalesTO selectBill) {
        this.selectBill = selectBill;
    }

    public ArrayList<SalesTO> getListaBusquedaTableBill() {
        return listaBusquedaTableBill;
    }

    public void setListaBusquedaTableBill(ArrayList<SalesTO> listaBusquedaTableBill) {
        this.listaBusquedaTableBill = listaBusquedaTableBill;
    }

    public List getListaBusquedaBill() {
        return listaBusquedaBill;
    }

    public void setListaBusquedaBill(List listaBusquedaBill) {
        this.listaBusquedaBill = listaBusquedaBill;
    }

    public boolean isFromCopy() {
        return fromCopy;
    }

    public void setFromCopy(boolean fromCopy) {
        this.fromCopy = fromCopy;
    }

    public boolean isAccion() {
        return accion;
    }

    public void setAccion(boolean accion) {
        this.accion = accion;
    }

    public int getNewCreditFromBill() {
        return newCreditFromBill;
    }

    public void setNewCreditFromBill(int newCreditFromBill) {
        this.newCreditFromBill = newCreditFromBill;
    }

    public ClientCrediTO getNewCredit() {
        return newCredit;
    }

    public void setNewCredit(ClientCrediTO newCredit) {
        this.newCredit = newCredit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getSpacer() {
        return spacer;
    }

    public void setSpacer(int spacer) {
        this.spacer = spacer;
    }

    public ClientCrediTO getSelectCredit() {
        return selectCredit;
    }

    public void setSelectCredit(ClientCrediTO selectCredit) {
        this.selectCredit = selectCredit;
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

    public ClientCrediDetailTO getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(ClientCrediDetailTO selectDetail) {
        this.selectDetail = selectDetail;
    }

    public ArrayList<ClientCrediDetailTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<ClientCrediDetailTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public List getListaPadre() {
        return listaPadre;
    }

    public void setListaPadre(List listaPadre) {
        this.listaPadre = listaPadre;
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

    public List<BranchTO> getListaAlmacenes() {
        return listaAlmacenes;
    }

    public void setListaAlmacenes(List<BranchTO> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public List<DocStatusTO> getLstEstados() {
        return lstEstados;
    }

    public void setLstEstados(List<DocStatusTO> lstEstados) {
        this.lstEstados = lstEstados;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public boolean isDisabledComun() {
        return disabledComun;
    }

    public void setDisabledComun(boolean disabledComun) {
        this.disabledComun = disabledComun;
    }

    public boolean isRequired1() {
        return required1;
    }

    public void setRequired1(boolean required1) {
        this.required1 = required1;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<ClientCrediTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<ClientCrediTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

//</editor-fold>
    
}//CIERRE CLASE
