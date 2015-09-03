/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.inventory.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.InventoryEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesInTO;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.BranchArticlesTO;
import com.sifcoapp.objects.admin.to.BranchTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.inventory.to.TransfersDetailTO;
import com.sifcoapp.objects.inventory.to.TransfersInTO;
import com.sifcoapp.objects.inventory.to.TransfersTO;
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
import javax.faces.bean.ManagedBean;
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


@ManagedBean(name = "TransfersBean")
@SessionScoped
public class TransfersBean implements Serializable{

        HttpSession session = Util.getSession();

//<editor-fold defaultstate="collapsed" desc="Declaración de variables para formulario" >
    private static AdminEJBClient AdminEJBService;
    private static InventoryEJBClient inventoryEJB;

    private String completeCodigo; //autocompletado del campo codigo
    private String completeNomArt; //autocompletado del campo nombre articulo

    //--------------------------------------------------------------------------
    private TransfersTO newTransfer = new TransfersTO(); //cabecera de la pantalla
    private ArrayList<TransfersTO> listaBusquedaTable = new ArrayList<TransfersTO>();
    private List listaBusqueda = new Vector();
    private Object selectReceipt = new TransfersTO();
    private int docEntry;             //No.
    private int docNum;
    private String almDest;     //Almacen destino
    private String almOrigen;
    private Date fechaConta = new Date();    //Fecha Contabilizacion

    private Date fechaDoc = new Date();      //Fecha Documento
    private String refe;        //Referencia
    private String comentario;  //Comentario

    //--------------------------------------------------------------------------
    //nuevo detalle
    private TransfersDetailTO selectDetail = new TransfersDetailTO();

    private ArrayList<TransfersDetailTO> listaDetalles = new ArrayList<TransfersDetailTO>();  //lista para el datatable <TransfersDetailTO>
    private List listaPadre = new Vector();
    private String newCod;
    private String newNomArt;
    private Double newTotal;
    private String newUnidad;
    private Double newCostoPromedio;

    @Digits(integer = 14, fraction = 2, message = "Maximo 2 Decimales en Precio")
    private Double newPrecio;

    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private Double newCantidad;

    private Double newExistencia;

    //--------------------------------------------------------------------------
    private List<BranchTO> listaAlmacenes; //para llenar el combo-box
    private String nomAlmacen1;
    private String nomAlmacen2;
    private String codAlmacen;

    //--------------------------------------------------------------------------
    private int varEstados;     //0=guardar; 1=update; 2=buscar
    private String botonEstado;
    private boolean disable, disableComun, btn1, btn2;
    private boolean docNumState;

    //--------------------------------------------------------------------------
    //Confirmacion
    private String msjDialog;
    private boolean confirm;
    private int toolbarBoton;
    private boolean rendered;
    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S" >
    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {    
        this.rendered = rendered;
    }


    public String getNomAlmacen2() {
        return nomAlmacen2;
    }

    public void setNomAlmacen2(String nomAlmacen2) {
        this.nomAlmacen2 = nomAlmacen2;
    }
    
    

    public String getAlmOrigen() {
        return almOrigen;
    }

    public void setAlmOrigen(String almOrigen) {
        this.almOrigen = almOrigen;
    }
    
    

    public Double getNewCostoPromedio() {
        return newCostoPromedio;
    }

    public void setNewCostoPromedio(Double newCostoPromedio) {
        this.newCostoPromedio = newCostoPromedio;
    }
    
    public String getMsjDialog() {
        return msjDialog;
    }

    public void setMsjDialog(String msjDialog) {
        this.msjDialog = msjDialog;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public ArrayList<TransfersTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<TransfersTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public Object getSelectReceipt() {
        return selectReceipt;
    }

    public void setSelectReceipt(Object selectReceipt) {
        this.selectReceipt = selectReceipt;
    }

    public boolean isBtn1() {
        return btn1;
    }

    public void setBtn1(boolean btn1) {
        this.btn1 = btn1;
    }

    public boolean isBtn2() {
        return btn2;
    }

    public void setBtn2(boolean btn2) {
        this.btn2 = btn2;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public boolean isDisableComun() {
        return disableComun;
    }

    public void setDisableComun(boolean disableComun) {
        this.disableComun = disableComun;
    }

    public boolean isDocEntryState() {
        return docNumState;
    }

    public void setDocEntryState(boolean docEntryState) {
        this.docNumState = docEntryState;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getBotonEstado() {
        return botonEstado;
    }

    public void setBotonEstado(String botonEstado) {
        this.botonEstado = botonEstado;
    }

    public int getVarEstados() {
        return varEstados;
    }

    public void setVarEstados(int varEstados) {
        this.varEstados = varEstados;
    }

    public TransfersDetailTO getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(TransfersDetailTO selectDetail) {
        this.selectDetail = selectDetail;
    }

    public Double getNewExistencia() {
        return newExistencia;
    }

    public void setNewExistencia(Double newExistencia) {
        this.newExistencia = newExistencia;
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

    public TransfersTO getReceipt() {
        return newTransfer;
    }

    public void setReceipt(TransfersTO receipt) {
        this.newTransfer = receipt;
    }

    public List<TransfersDetailTO> getListaDetalles() {
        return listaDetalles;
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

    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    public String getAlmDest() {
        return almDest;
    }

    public void setAlmDest(String almDest) {
        this.almDest = almDest;
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

    public String getRefe() {
        return refe;
    }

    public void setRefe(String refe) {
        this.refe = refe;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setListaDetalles(ArrayList<TransfersDetailTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public String getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        this.codAlmacen = codAlmacen;
    }

    public String getNomAlmacen1() {
        return nomAlmacen1;
    }

    public void setNomAlmacen1(String nomAlmacen1) {
        this.nomAlmacen1 = nomAlmacen1;
    }

    public List<BranchTO> getListaAlmacenes() {
        return listaAlmacenes;
    }

    public void setListaAlmacenes(List<BranchTO> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public String getCompleteCodigo() {
        return completeCodigo;
    }

    public void setCompleteCodigo(String completeCodigo) {
        this.completeCodigo = completeCodigo;
    }

    public String getCompleteNomArt() {
        return completeNomArt;
    }

    public void setCompleteNomArt(String completeNomArt) {
        this.completeNomArt = completeNomArt;
    }

    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >    
    @PostConstruct
    public void initForm() {

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        if (inventoryEJB == null) {
            inventoryEJB = new InventoryEJBClient();
        }

        //llenar los almacenes en combobox
        String var = null;
        try {
            this.setListaAlmacenes((List<BranchTO>) AdminEJBService.getBranch(var, var));

        } catch (Exception e) {
            faceMessage(e.getMessage() + " -" + e.getCause());
            System.out.println(e.getMessage() + " -" + e.getCause());
        }

        //estado por defecto
        estateGuardar();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocompletado" > 
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
            results.add(articulo.getItemName());
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

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del ComboBox de almacenes" > 
    
    public void stateChangeListener(ValueChangeEvent event) {
        if (event.getNewValue() != almDest) {
            try {
                List _result = null;
                String var = null;
                _result = AdminEJBService.getBranch(event.getNewValue().toString(), var);                        
                Iterator<BranchTO> iterator = _result.iterator();
                BranchTO articulo = (BranchTO) iterator.next();
                nomAlmacen1 = articulo.getWhsname().toString();
            } catch (Exception ex) {
                Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stateChangeListener2(ValueChangeEvent event) {
        if (event.getNewValue() != almDest) {
            try {
                List _result = null;
                String var = null;
                _result = AdminEJBService.getBranch(event.getNewValue().toString(), var);                        
                Iterator<BranchTO> iterator = _result.iterator();
                BranchTO articulo = (BranchTO) iterator.next();
                nomAlmacen2 = articulo.getWhsname().toString();
            } catch (Exception ex) {
                Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete" > 
    public void findArticle(SelectEvent event) {
        List articulos = new Vector();
        String var = null;

        if (event.getObject().toString() != var) {
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
                        newUnidad = art.getBuyUnitMsr();
                        newExistencia = art.getOnHand();
                        newCostoPromedio = art.getAvgPrice();
                        art = AdminEJBService.getArticlesByKey(newCod);
                        if (almOrigen != "-1") {
                            List alm = art.getBranchArticles();
                            Iterator<BranchArticlesTO> iter = alm.iterator();
                            while (iter.hasNext()) {
                                BranchArticlesTO branch = (BranchArticlesTO) iter.next();
                                if (branch.getWhscode().equals(almOrigen)) {
                                    newExistencia = branch.getOnhand();
                                    break;
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (Object artt : articulos) {
                        ArticlesTO art = (ArticlesTO) artt;
                        if (newNomArt.equals(art.getItemName())) {
                            newNomArt = art.getItemName();
                            newCod = art.getItemCode();
                            newUnidad = art.getBuyUnitMsr();
                        }
                    }//cierre for
                }
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Agregar detalles al dataTable" > 
    public void accionAgregar(ActionEvent actionEvent) {
        try {
            if (validarNull()) {
                calcularTotal();
                TransfersDetailTO nuevoDetalle = new TransfersDetailTO();
                nuevoDetalle.setObjtype(32+"");
                nuevoDetalle.setItemcode(newCod);
                nuevoDetalle.setDscription(newNomArt);
                nuevoDetalle.setQuantity(newCantidad);
                nuevoDetalle.setPrice(newCostoPromedio);
                nuevoDetalle.setLinetotal(newTotal);
                nuevoDetalle.setUnitmsr(newUnidad);
                nuevoDetalle.setLinenum(UUID.randomUUID().hashCode());
               // nuevoDetalle.setLinenum(getListaDetalles().size() + 1);

                if (listaDetalles == null) {
                    listaDetalles = new ArrayList<>();
                }
                if (listaPadre == null) {
                    listaPadre = new Vector();
                }

                listaPadre.add(nuevoDetalle);
                getListaDetalles().add(nuevoDetalle);
                //cleanDetalle();
            }
        } catch (Exception e) {
            faceMessage("Valores Incorrectos: " + e.getMessage() + e.getCause());
        }
        cleanDetalle();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias" > 
    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    //valida que los campos esten llenos para agregar un detalle al datatable
    public boolean validarNull() {
        return !newCod.isEmpty() && !newNomArt.isEmpty() && newCantidad > 0; //&& !newUnidad.isEmpty();
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
        newCostoPromedio = null;
    }

    //calcula el total visual en pantalla
    public void calcularTotal() {
        try {
            if (newCantidad > 0  && newCantidad != null) {
                Double aux = (newCostoPromedio) * (newCantidad);
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                String st = nf.format(aux);
                Double dou = Double.valueOf(st);
                newTotal = dou;
            }
        } catch (Exception e) {
            //faceMessage("Error al calcular total: " + e.getMessage() + "-" + e.getCause());
            //faceMessage("Precio: " + newPrecio + "  " + "Cantidad: " + newCantidad);
        }

    }

    //validaciones antes de guardar a la base
    public boolean validarDatosReceipt() {
        if (fechaConta == null && fechaDoc == null) {
            faceMessage("Fechas vacias");
            return false;
        }
        if (almDest.contains("-1")) {
            faceMessage("Seleccione un Almacen Destino");
            return false;
        }
        if (almOrigen.contains("-1")) {
            faceMessage("Seleccione un Almacen Origen");
            return false;
        }
        if (getListaDetalles().size() < 1) {
            faceMessage("Ingrese al menos, 1 articulo");
            return false;
        }
        if (almDest.equals(almOrigen)) {
        faceMessage("Almacen Destino debe ser diferente de Almacen Origen");
        return false;
        }
        return true;
    }

    //ventana emergente

    public void addArt() {
        RequestContext.getCurrentInstance().openDialog("/faces/view/inventory/GoodsReceiptProducto.xhtml");
    }

    /**
     * Mandar un String para mostrar en pantalla
     *
     * @param var
     */
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    //limpiar variables
    public void cleanBean() {
        this.docEntry = 0;
        this.docNum = 0;
        this.almDest = null;
        this.almOrigen = null;
        this.nomAlmacen1 = null;
        this.refe = null;
        this.comentario = null;
        Date d = new Date();
        this.setFechaConta(d);
        this.setFechaDoc(d);
        cleanDetalle();
        this.newTransfer = new TransfersTO();
        this.listaDetalles.clear();
        this.listaPadre.clear();

        this.listaBusquedaTable.clear();
        this.listaBusqueda.clear();
    }

    //limpiar variables 2
    public void cleanBean2() {
        cleanBean();
        Date d = null;
        this.setFechaConta(d);
        this.setFechaDoc(d);
    }

    //llenar pantalla dado un Receipt
    public void llenarPantalla(TransfersTO var) {
        setDocNum(var.getDocnum());
        setDocEntry(var.getDocentry());
        setAlmDest(var.getTowhscode());
        setAlmOrigen(var.getFromwhscode());
        setFechaDoc(var.getDocduedate());
        setFechaConta(var.getDocdate());
        setRefe(var.getRef1());
        setComentario(var.getComments());

        for (Object detalle : var.getTransfersDetail()) {
            TransfersDetailTO det = (TransfersDetailTO) detalle;
            this.listaDetalles.add(det);
        }
    }

    //evento al seleccionar un elemento del dialogo
    public void selectDialog() {
        showHideDialog("dlg2", 2);
        TransfersTO var = (TransfersTO) selectReceipt;
        //llenarPantalla(var);

        try {
            newTransfer = inventoryEJB.getTransfersByKey(var.getDocentry());
            llenarPantalla(newTransfer);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        RequestContext.getCurrentInstance().update("formTransfer");

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
    
    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlg9", 2);
        if (varEstados == 1) {
            saveTransfer();
        }else{
            if (varEstados == 2) {
            updateTransfer();
            }
        }        
    }
    
    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlg10", 2);
        this.confirm = true;
        if (toolbarBoton == 1) {
            cleanBean();
        }else
            cleanBean2();
        
        RequestContext.getCurrentInstance().update("formTransfer");   
    }
    
    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg9').hide();");
    }
    
    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg10').hide();");
    }
    
    public boolean validarClear(){
        return this.listaPadre.size() >= 1;       
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Guardar en base" > 
    public void saveTransfer() {
        int line = 0;
        String vacio = null;
        if (newTransfer == null) {
            newTransfer = new TransfersTO();
        }
        newTransfer.setObjtype(32+"");
        newTransfer.setUsersign((int) session.getAttribute("usersign"));
        newTransfer.setTowhscode(almDest);
        newTransfer.setFromwhscode(almOrigen);
        newTransfer.setDocdate(fechaConta);
        newTransfer.setDocduedate(fechaDoc);
        //newTransfer.setRef1(refe);
        //newTransfer.setComments(comentario);
        
        if (refe.equals("")) {
            newTransfer.setRef1(vacio);
        } else {
            newTransfer.setRef1(refe);
        }
        
        if (comentario.equals("")) {
            newTransfer.setComments(vacio);
        } else {
            newTransfer.setComments(comentario);
        }
        
        Iterator<TransfersDetailTO> iterator2 = listaPadre.iterator();
        while (iterator2.hasNext()) {
            TransfersDetailTO articleDetalle = (TransfersDetailTO) iterator2.next();
            articleDetalle.setLinenum(line+1);
            line=line+1;
        }
        
        newTransfer.setTransfersDetail(listaPadre);

        try {
            ResultOutTO _res;
            _res = inventoryEJB.inv_transfers_mtto(newTransfer, 1); //1 insert

            if (_res.getCodigoError() == 0) {//se realizo correctamente
                docEntry = _res.getDocentry();
                docNum = docEntry; //
                faceMessage(_res.getMensaje());

                estateActualizar();

            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            faceMessage(ex.getMessage() + "-" + ex.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update en base" > 
    public void updateTransfer() {
            newTransfer.setObjtype(32+"");
            newTransfer.setUsersign((int) session.getAttribute("usersign"));
            newTransfer.setDocentry(docEntry);
            newTransfer.setDocnum(docNum);
            newTransfer.setTowhscode(almDest);
            newTransfer.setFromwhscode(almOrigen);
            newTransfer.setDocdate(fechaConta);
            newTransfer.setDocduedate(fechaDoc);
            newTransfer.setRef1(refe);
            newTransfer.setComments(comentario);
            newTransfer.setTransfersDetail(listaPadre);

            try {
                ResultOutTO _res;
                _res = inventoryEJB.inv_transfers_mtto(newTransfer, 2); //2 Update

                if (_res.getCodigoError() == 0) {//se realizo correctamente
                    docEntry = _res.getDocentry();
                    faceMessage(_res.getMensaje());

                } else {
                    faceMessage(_res.getMensaje());
                }
            } catch (Exception ex) {
                Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
                faceMessage(ex.getMessage() + "-" + ex.getCause());
            }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar en base" >
    public void searchTransfer() {

        TransfersInTO searchTrans = new TransfersInTO();

        searchTrans.setDocnum(docNum);
        searchTrans.setDocdate(fechaConta);
        searchTrans.setDocduedate(fechaDoc);
        if (refe.equals("")) {
            searchTrans.setRef1(null);
        } else {
            searchTrans.setRef1(refe);
        }
        if (comentario.equals("")) {
            searchTrans.setComments(null);
        } else {
            searchTrans.setComments(comentario);
        }
        if (almDest.equals("-1")) {
            searchTrans.setTowhscode(null);
        } else {
            searchTrans.setTowhscode(almDest);
        }
        if (almOrigen.equals("-1")) {
            searchTrans.setFromwhscode(null);
        } else {
            searchTrans.setFromwhscode(almOrigen);
        }
        searchTrans.setSeries(0);

        try {
            listaBusqueda = inventoryEJB.getTransfers(searchTrans);
        } catch (Exception e) {
            faceMessage(e.getMessage() + "Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newTransfer = (TransfersTO) listaBusqueda.get(0);
                try {
                    TransfersTO var2 = inventoryEJB.getTransfersByKey(newTransfer.getDocentry());
                    llenarPantalla(var2);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(TransfersBean.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }

            } else {
                faceMessage("Seleccione un elemento");

                for (Object receipt : listaBusqueda) {
                    TransfersTO var = (TransfersTO) receipt;
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

//<editor-fold defaultstate="collapsed" desc="Eliminar del dataTable" > 
    public void deleteDetalle() {
        try {
            boolean var1, var2;
            var1 = getListaDetalles().remove(this.selectDetail);
            var2 = listaPadre.remove(this.selectDetail);
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
        this.varEstados = Common.MTTOINSERT;
        this.botonEstado = "Guardar";
        this.disable = false;
        this.disableComun = false;
        this.docNumState = true;
        this.rendered = true;
        RequestContext.getCurrentInstance().update("formTransfer");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE;
        this.botonEstado = "Actualizar";
        this.disable = true;
        this.disableComun = true;
        this.docNumState = true;
        this.rendered = false;
        //RequestContext.getCurrentInstance().update("formReceipt");
        try {
            reload();
        } catch (IOException ex) {
            Logger.getLogger(GoodsReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";
        this.disable = false;
        this.disableComun = true;
        docNumState = false;
        this.rendered = false;
        RequestContext.getCurrentInstance().update("formTransfer");
    }
    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Botones toolbar " > 
    
    public void botonNuevo(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 2) {//mas de un detalle
            toolbarBoton = 1;
            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                estateGuardar();
            }
        }else{
            cleanBean();
            estateGuardar();
        }    
    }

    public void botonBuscar(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 2) {//mas de un detalle
            toolbarBoton = 2;
            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                estateBuscar();
            }
        }else{
            cleanBean2();
            estateBuscar();
        }   
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Principal" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                if (validarDatosReceipt()) {
                    showHideDialog("dlg9", 1);
                }
                break;

            case 2:
                if (validarDatosReceipt()) {
                    showHideDialog("dlg9", 1);
                }
                break;

            case 3:
                listaBusqueda.clear();
                listaBusquedaTable.clear();
                searchTransfer();
                break;

            default:
                break;

        }

    }
//</editor-fold>
    
}//cierre de clase
