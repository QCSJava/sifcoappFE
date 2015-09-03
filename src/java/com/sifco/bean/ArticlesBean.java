/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesInTO;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.BranchArticlesTO;
import com.sifcoapp.objects.admin.to.BranchTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.report.bean.ReportsBean;
import com.sifcoapp.report.common.AbstractReportBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.*;
import org.primefaces.context.RequestContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "articles")
@SessionScoped
public class ArticlesBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    HttpSession session = Util.getSession();
    private CatalogTO articleClassModel;
    private static final String CATALOGOCLASES = "cg_articlesclass";
    private static final String CATALOGOGROUP = "cg_articlesGroup";
    private static final String CATALOGDEFAULTPROV = "cg_defaultProvider";
    private static final String IMPUESTOS = "Impuestos";
    private ArrayList<ArticlesTO> listaBusquedaTable = new ArrayList<ArticlesTO>();
    private ArticlesTO newArticle = new ArticlesTO();
    private Object selectArticle = new ArticlesTO();
    private List<CatalogTO> articleClassLst;
    private List<CatalogTO> articleGroupLst;
    private List<CatalogTO> shoppingDefaultProv;
    //private List<BranchTO> branchArticlesLst;
    private List<BranchArticlesTO> ListaGeneral = new ArrayList();
    private ArrayList<BranchArticlesTO> listaDetalles = new ArrayList<BranchArticlesTO>();
    private List listaBusqueda = new Vector();
    private String name;
    private ArticlesTO parameters = new ArticlesTO();
    //@Size(min=1,max=30)
    //@NotNull
    //@Size(min = 1, message = "Por favor ingrese el codigo")
    private String itemcode;
    //@NotNull
    //@Size(min = 1, message = "Por favor ingrese la descripcion")
    private String itemname;
    private Double price;
    //@NotNull(message="Por favor ingrese la Clase")        
    private String itemtype;
    private String itemgroup;
    private boolean invitem;
    private boolean sellitem;
    private boolean prchitem;
    private boolean writable;
    private boolean asset;
    private String manufactured;
    private String comments;
    private boolean validfor;
    private String cardcode;
    private String suppcatnum;
    @Digits(integer = 14, fraction = 2, message = "Cantidad inadecuada")
    private Double numinbuy;
    private String buyunitmsr;
    private String salesMeasureUnit;
    private String invtryuom;
    private Double salpackun;
    private Double PromCost;
    private AdminEJBClient AdminEJBService;
    private String sww;
    private String vatgourpsa;
    private List vatgourpsaList = new Vector();
    //--------------------------------------------------------------------------
    private int varEstados;     //0=guardar; 1=update; 2=buscar
    private String botonEstado;
    private boolean disable, disableComun, btn1, btn2;
    private boolean docNumState;
    private boolean confirm;
    private int toolbarBoton;
    private List resp = null;
    private String name2 = null;
    private String code = null;
    
    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getters and seeters" > 
    public List getVatgourpsaList() {
        return vatgourpsaList;
    }

    public void setVatgourpsaList(List vatgourpsaList) {
        this.vatgourpsaList = vatgourpsaList;
    }

    public String getVatgourpsa() {
        return vatgourpsa;
    }

    public void setVatgourpsa(String vatgourpsa) {
        this.vatgourpsa = vatgourpsa;
    }

    public List getResp() {
        return resp;
    }

    public void setResp(List resp) {
        this.resp = resp;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BranchArticlesTO> getListaGeneral() {
        return ListaGeneral;
    }

    public void setListaGeneral(List<BranchArticlesTO> ListaGeneral) {
        this.ListaGeneral = ListaGeneral;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public ArrayList<ArticlesTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<ArticlesTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public ArticlesTO getNewArticle() {
        return newArticle;
    }

    public void setNewArticle(ArticlesTO newArticle) {
        this.newArticle = newArticle;
    }

    public Object getSelectArticle() {
        return selectArticle;
    }

    public void setSelectArticle(Object selectArticle) {
        this.selectArticle = selectArticle;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArticlesTO getParameters() {
        return parameters;
    }

    public void setParameters(ArticlesTO parameters) {
        this.parameters = parameters;
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

    public ArrayList<BranchArticlesTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<BranchArticlesTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public boolean isAsset() {
        return asset;
    }

    /**
     * @param asset the asset to set
     */
    public void setAsset(boolean asset) {
        this.asset = asset;
    }

    /**
     * @return the sww
     */
    public String getSww() {
        return sww;
    }

    /**
     * @param sww the sww to set
     */
    public void setSww(String sww) {
        this.sww = sww;
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

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisableComun() {
        return disableComun;
    }

    public void setDisableComun(boolean disableComun) {
        this.disableComun = disableComun;
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

    public boolean isDocNumState() {
        return docNumState;
    }

    public void setDocNumState(boolean docNumState) {
        this.docNumState = docNumState;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the itemname
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname the itemname to set
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the itemtype
     */
    public String getItemtype() {
        return itemtype;
    }

    /**
     * @param itemtype the itemtype to set
     */
    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    /**
     * @return the itemgroup
     */
    public String getItemgroup() {
        return itemgroup;
    }

    /**
     * @param itemgroup the gropu to set
     */
    public void setItemgroup(String itemgroup) {
        this.itemgroup = itemgroup;
    }

    /**
     * @return the invitem
     */
    public boolean isInvitem() {
        return invitem;
    }

    /**
     * @param invitem the invitem to set
     */
    public void setInvitem(boolean invitem) {
        this.invitem = invitem;
    }

    /**
     * @return the sellitem
     */
    public boolean isSellitem() {
        return sellitem;
    }

    /**
     * @param sellitem the sellitem to set
     */
    public void setSellitem(boolean sellitem) {
        this.sellitem = sellitem;
    }

    /**
     * @return the prchitem
     */
    public boolean isPrchitem() {
        return prchitem;
    }

    /**
     * @param prchitem the prchitem to set
     */
    public void setPrchitem(boolean prchitem) {
        this.prchitem = prchitem;
    }

    /**
     * @return the writable
     */
    public boolean isWritable() {
        return writable;
    }

    /**
     * @param writable the writable to set
     */
    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    /**
     * @return the manufactured
     */
    public String getManufactured() {
        return manufactured;
    }

    /**
     * @param manufactured the manufactured to set
     */
    public void setManufactured(String manufactured) {
        this.manufactured = manufactured;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the validfor
     */
    public boolean isValidfor() {
        return validfor;
    }

    /**
     * @param validfor the validfor to set
     */
    public void setValidfor(boolean validfor) {
        this.validfor = validfor;
    }

    /**
     * @return the cardcode
     */
    public String getCardcode() {
        return cardcode;
    }

    /**
     * @param cardcode the cardcode to set
     */
    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    /**
     * @return the suppcatnum
     */
    public String getSuppcatnum() {
        return suppcatnum;
    }

    /**
     * @param suppcatnum the suppcatnum to set
     */
    public void setSuppcatnum(String suppcatnum) {
        this.suppcatnum = suppcatnum;
    }

    /**
     * @return the numinbuy
     */
    public Double getNuminbuy() {
        return numinbuy;
    }

    /**
     * @param numinbuy the numinbuy to set
     */
    public void setNuminbuy(Double numinbuy) {
        this.numinbuy = numinbuy;
    }

    /**
     * @return the buyunitmsr
     */
    public String getBuyunitmsr() {
        return buyunitmsr;
    }

    /**
     * @param buyunitmsr the buyunitmsr to set
     */
    public void setBuyunitmsr(String buyunitmsr) {
        this.buyunitmsr = buyunitmsr;
    }

    /**
     * @return the salesMeasureUnit
     */
    public String getSalesMeasureUnit() {
        return salesMeasureUnit;
    }

    /**
     * @param salesMeasureUnit the salesMeasureUnit to set
     */
    public void setSalesMeasureUnit(String salesMeasureUnit) {
        this.salesMeasureUnit = salesMeasureUnit;
    }

    /**
     * @return the salpackun
     */
    public Double getSalpackun() {
        return salpackun;
    }

    /**
     * @param salpackun the salpackun to set
     */
    public void setSalpackun(Double salpackun) {
        this.salpackun = salpackun;
    }

    /**
     * @return the itemcode
     */
    public String getItemcode() {
        return itemcode;
    }

    /**
     * @param itemcode the itemcode to set
     */
    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    /**
     * @return the articleClassModel
     */
    public CatalogTO getArticleClassModel() {
        return articleClassModel;
    }

    /**
     * @param articleClassModel the articleClassModel to set
     */
    public void setArticleClassModel(CatalogTO articleClassModel) {
        this.articleClassModel = articleClassModel;
    }

    /**
     * @return the articleClassLst
     */
    public List<CatalogTO> getArticleClassLst() {
        return articleClassLst;
    }

    /**
     * @param articleClassLst the articleClassLst to set
     */
    public void setArticleClassLst(List<CatalogTO> articleClassLst) {
        this.articleClassLst = articleClassLst;
    }

    /**
     * @return the articleGroupLst
     */
    public List<CatalogTO> getArticleGroupLst() {
        return articleGroupLst;
    }

    /**
     * @param articleGroupLst the articleGroupLst to set
     */
    public void setArticleGroupLst(List<CatalogTO> articleGroupLst) {
        this.articleGroupLst = articleGroupLst;
    }

    /**
     * @return the shoppingDefaultProv
     */
    public List<CatalogTO> getShoppingDefaultProv() {
        return shoppingDefaultProv;
    }

    /**
     * @param shoppingDefaultProv the shoppingDefaultProv to set
     */
    public void setShoppingDefaultProv(List<CatalogTO> shoppingDefaultProv) {
        this.shoppingDefaultProv = shoppingDefaultProv;
    }

    /**
     * @return the PromCost
     */
    public Double getPromCost() {
        return PromCost;
    }

    /**
     * @param PromCost the PromCost to set
     */
    public void setPromCost(Double PromCost) {
        this.PromCost = PromCost;
    }

    /**
     * @return the branchArticlesLst
     */
    /* public List<BranchArticlesTO> getBranchArticlesLst() {
     return branchArticlesLst;
     }*/
    /**
     * @param branchArticlesLst the branchArticlesLst to set
     */
    /*public void setBranchArticlesLst(List<BranchArticlesTO> branchArticlesLst) {
     this.branchArticlesLst = branchArticlesLst;
     }*/
    /**
     * @return the invtryuom
     */
    public String getInvtryuom() {
        return invtryuom;
    }

    /**
     * @param invtryuom the invtryuom to set
     */
    public void setInvtryuom(String invtryuom) {
        this.invtryuom = invtryuom;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Init de la ventana">
    @PostConstruct
    public void initForm() {

        try {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            this.articleClassLst = AdminEJBService.findCatalog(CATALOGOCLASES);
            this.articleGroupLst = AdminEJBService.findCatalog(CATALOGOGROUP);
            this.vatgourpsaList = AdminEJBService.findCatalog(IMPUESTOS);
            this.setShoppingDefaultProv((List<CatalogTO>) AdminEJBService.findCatalog(CATALOGDEFAULTPROV));
            //this.branchArticlesLst.add(new BranchArticlesTO(new BranchTO("01","Almacen-001","00","00"),"01","01",new Double(1),new Double(2),new Double(3),new Double(4),new Double(5),new Double(6),"false",false));
        } catch (Exception ex) {
            Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        estateGuardar();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Guardar en base">
    public void doSave() {

        ResultOutTO _result = new ResultOutTO();
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        if (parameters == null) {
            parameters = new ArticlesTO();
        }
        parameters.setItemCode(itemcode.replaceAll("\\s",""));
        parameters.setItemName(itemname);
        parameters.setItemType(itemtype);
        if (!itemgroup.equals("-1")) {
            parameters.setItmsIsGrpCod(itemgroup);
        }
        if (!vatgourpsa.equals("-1")) {
            parameters.setVatgourpsa(vatgourpsa);
        }
        parameters.setInvntItem(invitem ? "Y" : "N");
        parameters.setSellItem(sellitem ? "Y" : "N");
        parameters.setPrchseItem(prchitem ? "Y" : "N");
        parameters.setAssetItem(asset ? "Y" : "N");

        //general sheet
        parameters.setWtliable(writable ? "N" : "Y");
        parameters.setSww(sww);
        parameters.setValidFor(validfor ? "Y" : "N");

        //buy sheet
        parameters.setCardCode(cardcode);
        parameters.setBuyUnitMsr(buyunitmsr);
        if (numinbuy != null) {
            parameters.setNumInBuy(numinbuy);
        }
        parameters.setSuppCatNum(suppcatnum);
        //TODO: agregar catalogo fabricante
        parameters.setUserSign((int) session.getAttribute("usersign"));

        //sales sheet
        parameters.setSalUnitMsr(salesMeasureUnit);
        if (salpackun != null) {
            parameters.setSalPackUn(salpackun);
        }
        //inventory Sheet
        parameters.setInvntryUom(invtryuom);
        //parameters.setAvgPrice(this.getPromCost());
        if (!listaDetalles.isEmpty()) {
            listaDetalles.clear();
        }
        List branchArt = new Vector();

        Iterator<BranchArticlesTO> iterator = getListaGeneral().iterator();
        while (iterator.hasNext()) {
            BranchArticlesTO branchArticlesTO = (BranchArticlesTO) iterator.next();
            if (branchArticlesTO.isIsasociated()) {
                BranchArticlesTO Branchit = new BranchArticlesTO();
                Branchit.setItemcode(this.getItemcode());
                Branchit.setWhscode(branchArticlesTO.getWhscode());
                Branchit.setIsasociated(branchArticlesTO.isIsasociated());
                Branchit.setLocked(branchArticlesTO.getLocked().substring(0, 1));
                if (branchArticlesTO.getMinstock() != null) {
                    Branchit.setMinstock(branchArticlesTO.getMinstock());
                }
                if (branchArticlesTO.getMaxstock() != null) {
                    Branchit.setMaxstock(branchArticlesTO.getMaxstock());
                }
                branchArt.add(Branchit);
                listaDetalles.add(branchArticlesTO);
            }
        }
        parameters.setBranchArticles(branchArt);
        //parameters.setBranchArticles(this.getBranchArticlesLst());
        try {
            _result = AdminEJBService.cat_articles_mtto(parameters, Common.MTTOINSERT);
            if (_result.getCodigoError() == 0) {//se realizo correctamente

                faceMessage(_result.getMensaje());
                //ListaGeneral.clear();
                estateActualizar();

            } else {
                faceMessage(_result.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update en base">
    public void Update() {
        ResultOutTO _result = new ResultOutTO();
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        if (parameters == null) {
            parameters = new ArticlesTO();
        }
        parameters.setItemCode(itemcode);
        parameters.setItemName(itemname);
        parameters.setItemType(itemtype);
        if (!itemgroup.equals("-1")) {
            parameters.setItmsIsGrpCod(itemgroup);
        }
        if (!vatgourpsa.equals("-1")) {
            parameters.setVatgourpsa(vatgourpsa);
        }
        parameters.setInvntItem(invitem ? "Y" : "N");
        parameters.setSellItem(sellitem ? "Y" : "N");
        parameters.setPrchseItem(prchitem ? "Y" : "N");
        parameters.setAssetItem(asset ? "Y" : "N");

        //general sheet
        parameters.setWtliable(writable ? "N" : "Y");
        parameters.setSww(sww);
        parameters.setValidFor(validfor ? "Y" : "N");

        //buy sheet
        parameters.setCardCode(cardcode);
        parameters.setBuyUnitMsr(buyunitmsr);
        if (numinbuy != null) {
            parameters.setNumInBuy(numinbuy);
        }
        parameters.setSuppCatNum(suppcatnum);
        //TODO: agregar catalogo fabricante
        parameters.setUserSign((int) session.getAttribute("usersign"));

        //sales sheet
        parameters.setSalUnitMsr(salesMeasureUnit);
        if (this.getSalpackun() != null) {
            parameters.setSalPackUn(salpackun);
        }
        //inventory Sheet
        parameters.setInvntryUom(invtryuom);
            //parameters.setAvgPrice(this.getPromCost());
        // if(!listaDetalles.isEmpty()){
        //           listaDetalles.clear();
        //     }
        List branchArt = new Vector();

        Iterator<BranchArticlesTO> iterator = getListaGeneral().iterator();
        while (iterator.hasNext()) {

            BranchArticlesTO branchArticlesTO = (BranchArticlesTO) iterator.next();
            BranchArticlesTO Branchit = new BranchArticlesTO();
            Branchit.setItemcode(this.getItemcode());
            Branchit.setWhscode(branchArticlesTO.getWhscode());
            Branchit.setIsasociated(branchArticlesTO.isIsasociated());
            Branchit.setLocked(branchArticlesTO.getLocked().substring(0, 1));
            if (branchArticlesTO.getMinstock() != null) {
                Branchit.setMinstock(branchArticlesTO.getMinstock());
            }
            if (branchArticlesTO.getMaxstock() != null) {
                Branchit.setMaxstock(branchArticlesTO.getMaxstock());
            }
            //listaDetalles.add(Branchit);
            branchArt.add(Branchit);

        }

        parameters.setBranchArticles(branchArt);
        //parameters.setBranchArticles(this.getBranchArticlesLst());
        try {

            _result = AdminEJBService.cat_articles_mtto(parameters, Common.MTTOUPDATE);
            if (_result.getCodigoError() == 0) {//se realizo correctamente

                faceMessage(_result.getMensaje());
                estateActualizar();

            } else {
                faceMessage(_result.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar en base">
    public void buscarArticles() {
        ArticlesInTO art = new ArticlesInTO();
        art.setAssetItem(asset ? "Y" : null);
        art.setInvntItem(invitem ? "Y" : null);
        if (itemcode.equals("")) {
            art.setItemCode(null);
        } else {
            art.setItemCode(itemcode);
        }
        if (itemname.equals("")) {
            art.setItemName(null);
        } else {
            art.setItemName(itemname);
        }
        if (itemtype.equals("-1")) {
            art.setItemType(null);
        } else {
            art.setItemType(itemtype);
        }
        if (itemgroup.equals("-1")) {
            art.setItmsIsGrpCod(null);
        } else {
            art.setItmsIsGrpCod(itemgroup);
        }
        art.setPrchseItem(prchitem ? "Y" : null);
        art.setSellItem(sellitem ? "Y" : null);

        try {
            listaBusqueda = AdminEJBService.getArticles(art);
        } catch (Exception e) {
            faceMessage(e.getMessage() + "Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento unico encontrado");
                newArticle = (ArticlesTO) listaBusqueda.get(0);
                try {
                    ArticlesTO var2 = AdminEJBService.getArticlesByKey(newArticle.getItemCode());
                    //listaDetalles.clear();
                    llenarPantalla(var2);
                    estateActualizar();
                } catch (Exception ex) {
                    Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
                    faceMessage("Error en busqueda por PK");
                }

            } else {
                faceMessage("Seleccione un elemento");

                for (Object articles : listaBusqueda) {
                    ArticlesTO var = (ArticlesTO) articles;
                    listaBusquedaTable.add(var);
                }
                RequestContext.getCurrentInstance().update("frmTable");
                showHideDialog("dlg33", 1);

            }

        } else {
            faceMessage("No se obtuvieron resultados de la Busqueda");
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Validaciones">
    public boolean validarIn() {

        if (itemcode.isEmpty()) {
            faceMessage("Ingrese un Código de articulo");
            return false;
        }
        if (itemname.isEmpty()) {
            faceMessage("Ingrese una Descripción");
            return false;
        }
        if (itemtype.equals("-1")) {
            faceMessage("Seleccione una Clase");
            return false;
        }
        if (invtryuom == null || invtryuom.isEmpty()) {
            faceMessage("Ingrese una Unidad de Medida en Inventario");
            return false;
        } else {
            return true;
        }

    }

    public boolean validarClear() {

        return !(itemcode.isEmpty() && itemname.isEmpty() && invtryuom.isEmpty());

    }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        varEstados = 0;
        botonEstado = "Guardar";
        disable = false;
        btn1 = false;
        validfor = true;
        if (!ListaGeneral.isEmpty()) {
            ListaGeneral.clear();
        }
        try {
            resp = AdminEJBService.getBranch(code, name2);
        } catch (Exception ex) {
            Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator<BranchTO> iterator = resp.iterator();
        while (iterator.hasNext()) {
            BranchTO branch = (BranchTO) iterator.next();
            BranchArticlesTO branchArticlesTO = new BranchArticlesTO();
            branchArticlesTO.setBranch(branch);
            branchArticlesTO.setWhscode(branch.getWhscode());
            branchArticlesTO.setWhsname(branch.getWhsname());
            ListaGeneral.add(branchArticlesTO);
        }
        RequestContext.getCurrentInstance().update("frmArticles");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        if (varEstados == 2) {
            if (ListaGeneral != null) {
                ListaGeneral.clear();
            }
            Iterator<BranchArticlesTO> iterator = listaDetalles.iterator();
            while (iterator.hasNext()) {
                BranchArticlesTO branch = (BranchArticlesTO) iterator.next();
                BranchArticlesTO branchArticlesTO = new BranchArticlesTO();
                branchArticlesTO.setItemcode(branch.getItemcode());
                branchArticlesTO.setWhscode(branch.getWhscode());
                branchArticlesTO.setWhsname(branch.getWhsname());
                branchArticlesTO.setLocked(branch.getLocked());
                branchArticlesTO.setOnhand(branch.getOnhand());
                branchArticlesTO.setMaxstock(branch.getMaxstock());
                branchArticlesTO.setMinstock(branch.getMinstock());
                branchArticlesTO.setIsasociated(branch.isIsasociated());
                ListaGeneral.add(branchArticlesTO);
            }
        }
        varEstados = 1;
        botonEstado = "Actualizar";
        disable = true;
        btn1 = false;
        RequestContext.getCurrentInstance().update("frmArticles");

    }

    public void estateBuscar() {
        this.varEstados = 2;
        this.botonEstado = "Buscar";
        this.disable = false;
        this.btn1 = true;
        RequestContext.getCurrentInstance().update("frmArticles");
    }
    //</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="Boton Principal">
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 0:
                if (validarIn()) {
                    showHideDialog("dlg9", 1);
                }
                break;

            case 1:
                if (validarIn()) {
                    showHideDialog("dlg9", 1);
                }
                break;

            case 2:
                listaBusqueda.clear();
                listaBusquedaTable.clear();
                buscarArticles();
                break;

            default:
                break;

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Dialogos confirm cacel">
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public void showHideDialog(String name, int openClose) {
        System.out.println(itemcode);
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
        if (varEstados == 0) {
            doSave();
        } else {
            if (varEstados == 1) {
                Update();
            }
        }
        RequestContext.getCurrentInstance().update("frmArticles");
    }

    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlg10", 2);
        this.confirm = true;
        cleanBean();
        RequestContext.getCurrentInstance().update("frmArticles");
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg9').hide();");
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg10').hide();");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias (Çleans etc)">
    public void cleanBean() {
        itemcode = null;
        itemname = null;
        itemtype = null;
        itemgroup = null;
        invitem = false;
        sellitem = false;
        prchitem = false;
        asset = false;
        writable = false;
        sww = null;
        validfor = true;
        cardcode = null;
        suppcatnum = null;
        buyunitmsr = null;
        numinbuy = null;
        salesMeasureUnit = null;
        salpackun = null;
        invtryuom = null;
        PromCost = null;
        parameters = new ArticlesTO();
        listaDetalles.clear();
        listaBusqueda.clear();
        listaBusquedaTable.clear();
        //ListaGeneral.clear();
        this.newArticle = new ArticlesTO();

    }

    public void llenarPantalla(ArticlesTO var) {
        //llenado de cabecera
        setItemcode(var.getItemCode());
        setInvitem(var.getInvntItem() == null ? false : var.getInvntItem().equals("Y"));
        setItemname(var.getItemName());
        setSellitem(var.getSellItem() == null ? false : var.getSellItem().equals("Y"));
        setItemtype(var.getItemType());
        setPrchitem(var.getPrchseItem() == null ? false : var.getPrchseItem().equals("Y"));
        setItemgroup(var.getItmsIsGrpCod());
        setAsset(var.getAssetItem() == null ? false : var.getAssetItem().equals("Y"));

        //llenado de tab general
        setWritable(var.getWtliable() == null ? false : var.getWtliable().equals("Y"));
        setSww(sww);
        setValidfor(var.getValidFor() == null ? false : var.getValidFor().equals("Y") ? true : false);

        //llenado de tab compras
        setCardcode(var.getCardCode());
        setSuppcatnum(var.getSuppCatNum());
        setBuyunitmsr(var.getBuyUnitMsr());
        setNuminbuy(var.getNumInBuy());

        //llenado de tab ventas
        setSalesMeasureUnit(var.getSalUnitMsr());
        setSalpackun(var.getSalPackUn());
        setVatgourpsa(var.getVatgourpsa());

        //llenado de tab inventario
        setInvtryuom(var.getInvntryUom());
        setPromCost(var.getAvgPrice());

        for (Object detalle : var.getBranchArticles()) {
            BranchArticlesTO det = (BranchArticlesTO) detalle;
            this.listaDetalles.add(det);
        }

    }

    //evento al seleccionar un elemento del dialogo

    public void selectDialog() {
        showHideDialog("dlg33", 2);
        ArticlesTO var = (ArticlesTO) selectArticle;
        //llenarPantalla(var);

        try {
            newArticle = AdminEJBService.getArticlesByKey(var.getItemCode());
            //listaDetalles.clear();
            llenarPantalla(newArticle);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new ArrayList();
        //RequestContext.getCurrentInstance().update("formReceipt");

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Botones toolbar " > 
    public void botonNuevo(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 1) {//mas de un detalle

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
        if (validarClear() && varEstados != 1) {//mas de un detalle

            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                //ListaGeneral.clear();
                estateBuscar();
            }
        } else {

            cleanBean();
            estateBuscar();
        }
    }
    //</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="No usadas">
    /*
     public void chooseArt() {

     Map<String, Object> options = new HashMap<String, Object>();
     options.put("modal", true);
     options.put("draggable", false);
     options.put("resizable", false);
     options.put("contentHeight", 320);
     RequestContext.getCurrentInstance().openDialog("/faces/view/mtto/ArticlesSearch.xhtml", options, null);

     }

     public void onArtChosen(SelectEvent event) {
     ArticlesTO art = (ArticlesTO) event.getObject();
     FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car Selected", "Id:" + art.getItemCode());

     FacesContext.getCurrentInstance().addMessage(null, message);

     this.setItemcode(art.getItemCode());
     this.setItemname(art.getItemName());

     //FacesContext.getCurrentInstance()
     }*/
//</editor-fold>

    /**
     * @return the bean
     */
    public ReportsBean getBean() {
        return bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setBean(ReportsBean bean) {
        this.bean = bean;
    }
    //<editor-fold defaultstate="collapsed" desc="Imprime codigo de barras">
    /**
     * Rutilio Iraheta
     * Mayo 2015
     */
    public void PrintBarCode() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
        String _whereclausule = null;
        String _whereclausuleSR = null;
        String _reportname = null;
        String _reportTitle = null;
        String _reportFilters = "";
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        EnterpriseTO resp = null;
        try {
            resp = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(ArticlesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        _reportname = "/inventory/InvBarCode";
        _reportTitle = "Barcode";

        _whereclausule = "itemcode='"+this.getItemcode()+"'";
         reportParameters.put("corpName",resp.getCrintHeadr());

        reportParameters.put("PWHERE", _whereclausule);
        reportParameters.put("PWHERESR", _whereclausuleSR);
        reportParameters.put("reportName", _reportTitle);
        System.out.println(_whereclausule);
        getBean().setExportOption(AbstractReportBean.ExportOption.valueOf(AbstractReportBean.ExportOption.class, "PDF"));
                
        getBean().setParameters(reportParameters);
        getBean().setReportName(_reportname);
        getBean().execute();
        
        
    }
    //</editor-fold>
}
