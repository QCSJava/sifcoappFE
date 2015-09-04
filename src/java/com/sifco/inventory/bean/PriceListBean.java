/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.inventory.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesPriceTO;
import com.sifcoapp.objects.admin.to.PricesListInTO;
import com.sifcoapp.objects.admin.to.PricesListTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.validation.constraints.Digits;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "priceListBean")
@ApplicationScoped
public class PriceListBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    //Servicios EJB
    private static AdminEJBClient AdminEJBService;

    //__________________________________________________________________________
    //Encabezado
    private List<String> nomListBase = new ArrayList<>();

    private String newList;
    private int newBase;

    @Digits(integer = 14, fraction = 2, message = "Factor Inadecuado")
    private Double newFactor = 1.0;

    private ArrayList<PricesListTO> listaDetalles = new ArrayList<>(); //DataTable
    private List listaObjetos = new Vector();
    private PricesListTO selectPriceList = new PricesListTO();
    private ArrayList<ArticlesPriceTO> selectHijo = new ArrayList<>();
    //__________________________________________________________________________
    //Llenar cmb box
    private List<PricesListTO> listPriceList;

    //__________________________________________________________________________
    //Detalles
    private ArrayList<ArticlesPriceTO> listaHijos = new ArrayList<>();

    public boolean vTrue = true;
    public boolean vFalse = false;

    //__________________________________________________________________________
    //Estados
    private int varEstados; //0: estado padre 1:estado hijo

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="LOAD DE PANTALLA">
    @PostConstruct
    public void initForm() {

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        //llenar datatable
        PricesListInTO var = new PricesListInTO();
        try {
            listaObjetos = AdminEJBService.getPricesList(var);
            listPriceList = AdminEJBService.getPricesList(var);
        } catch (Exception ex) {
            Logger.getLogger(PriceListBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error al llenar DataTable");
        }

        for (Object obj : listaObjetos) {
            PricesListTO list = (PricesListTO) obj;
            this.listaDetalles.add(list);
        }

        //Estado por defecto
        estatePadre();

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BotonesTOOLBAR">
    public void botonNuevo(ActionEvent actionEvent) {
        cleanDetalle();
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Agregar al DATATABLE">
    public void accionAgreagar(ActionEvent actionEvent) {
        if (validarNewList()) {
            PricesListTO newPriceList = new PricesListTO();

            newPriceList.setListname(newList);
            newPriceList.setBase_num(newBase);
            newPriceList.setFactor(newFactor);

            try {
                ResultOutTO _res = new ResultOutTO();
                _res = AdminEJBService.cat_prl0_priceslist_mtto(newPriceList, Common.MTTOINSERT, true);
                if (_res.getCodigoError() == 0) {
                    faceMessage(_res.getMensaje());
                } else {
                    faceMessage(_res.getMensaje());
                }
            } catch (Exception e) {
                faceMessage(e.getMessage() + "-" + e.getCause()+" aca erroor");
            }

            PricesListInTO var = null;
            try {
                listaObjetos = AdminEJBService.getPricesList(var);
            } catch (Exception ex) {
                Logger.getLogger(PriceListBean.class.getName()).log(Level.SEVERE, null, ex);
                faceMessage("Error al llenar DataTable");
            }

            this.listaDetalles.clear();
            for (Object obj : listaObjetos) {
                PricesListTO list = (PricesListTO) obj;
                this.listaDetalles.add(list);
            }

            refreshCmb();
            cleanDetalle();
            
            RequestContext.getCurrentInstance().update("frmPriceList");

        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Eliminar del dataTable" > 
    public void deleteDetalle() {
        //faceMessage("Lista Eliminada");
        try {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }

            if (this.selectPriceList.getListnum() > 2) {
                ResultOutTO var3 = new ResultOutTO();
                boolean var1;

                var1 = getListaDetalles().remove(this.selectPriceList);
                var3 = AdminEJBService.cat_prl0_priceslist_mtto(this.selectPriceList, Common.MTTODELETE, true);

                //actualizar cmb
                this.listPriceList.clear();
                refreshCmb();

                this.selectPriceList = null;

                if (var1 && var3.getCodigoError() == 0) {
                    faceMessage("Articulo Eliminado.");
                }
            } else {
                faceMessage("Listas bases no pueden ser eliminadas");
                this.selectPriceList = null;
            }
            RequestContext.getCurrentInstance().update("frmPriceList");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "---" + e.getCause());
            faceMessage("Seleccione una lista para Eliminar");
        }
        estatePadre();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Confirmar delete">
    public void confirmDelete(ActionEvent actionEvent) {
        varEstados = 2; //eliminar
        showHideDialog("dlgC2", 1);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="boton mostrar hijos">
    public void showList(ActionEvent actionEvent) {
        
        resetFiltrosTabla("fdlgDetail:tableDet");
        
        try {
            estateHijo();

            if (this.selectPriceList == null) {
                this.selectPriceList = new PricesListTO();
            }
            
            if (this.selectPriceList.getListnum()>0) {
                faceMessage("Precios de articulos " + this.selectPriceList.getListname());

                PricesListTO pri = new PricesListTO();
                
                System.out.println("Antes de la consulta getPricesListByKey: " + new Date());  
                pri = AdminEJBService.getPricesListByKey(this.selectPriceList.getListnum());
                System.out.println("Registros:" + pri.getArticlesPrices().size() + " :  "+ new Date() );
                
                List var = pri.getArticlesPrices();

                listaHijos.clear();
                
                for (Object art : var) {
                    ArticlesPriceTO aux = (ArticlesPriceTO) art;
                    listaHijos.add(aux);
                }
                //this.selectPriceList = null;
                System.out.println("Antes de : RequestContext.getCurrentInstance()."  +new Date());  
                RequestContext.getCurrentInstance().update("fdlgDetail");
                System.out.println("despues de : RequestContext.getCurrentInstance()." + new Date() );
                
                System.out.println("Antes del showHideDialog  " + new Date());  
                showHideDialog("dlgDetalles", 1);
                System.out.println("Despues del showHideDialog  " + new Date());  
                
            }else
                faceMessage("Seleccione una lista");
            

        } catch (Exception ex) {
            Logger.getLogger(PriceListBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Principal">
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 0:
                showHideDialog("dlg9", 1);
                break;

            case 1:
                showHideDialog("dlg9", 1);
                break;

            default:
                break;

        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="doUpdate">
    /**
     * Actualizar listas en la base
     */
    @SuppressWarnings("UnnecessaryContinue")
    public void doUpdate() {
        
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        ResultOutTO _res = new ResultOutTO();
        for (PricesListTO lista : listaDetalles) {
            if (lista.getListnum() == 1 || lista.getListnum() == 2) {
                //faceMessage(lista.getListname());
                continue;
            } else {
                try {
                    PricesListTO t = AdminEJBService.getPricesListByKey(lista.getListnum());
                    _res = AdminEJBService.cat_prl0_priceslist_mtto(t, Common.MTTOUPDATE, true);
                } catch (Exception e) {
                    System.out.println("Error al actulizar listas: " + e.getMessage() + e.getCause()); 
                    faceMessage("Error al actulizar listas: " + e.getMessage() + e.getCause());
                }
            }
        }
        if (_res.getCodigoError() == 0) {
            faceMessage(_res.getMensaje());
        } else {
            faceMessage(_res.getMensaje());
        }

        refreshCmb();
        RequestContext.getCurrentInstance().update("frmPriceList");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Actualizar hijos">
    public void btnActualizarH(ActionEvent actionEvent) {
        showHideDialog("dlg9", 1);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="doUpdateHijos">
    public void doUpdateHijos() {
        try {
            ResultOutTO _res = new ResultOutTO();

            this.selectPriceList.setArticlesPrices(listaHijos);
            _res = AdminEJBService.cat_prl0_priceslist_mtto(selectPriceList, Common.MTTOUPDATE, true);

            if (_res.getCodigoError() == 0) {
                faceMessage(_res.getMensaje());
            } else {
                faceMessage(_res.getMensaje());
            }

            this.selectPriceList = new PricesListTO();
            this.listaHijos.clear();

            showHideDialog("dlgDetalles", 2);
            estatePadre();

        } catch (Exception e) {
            faceMessage(e.getMessage() + " " + e.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="changelistener precio y checkbox">
    public void checkChange() {
        //faceMessage("check Cambio");
        for (ArticlesPriceTO hijo : selectHijo) {
            if (hijo.getOvrwritten()) {
                hijo.setOvrwritten(false);
            }
        }
    }

    public void priceChange() {
        //faceMessage("precio cambio  - " + selectHijo.size()); //+ this.selectHijo.getItemcode() +" - " +this.selectHijo.getOvrwritten());
        for (ArticlesPriceTO hijo : selectHijo) {
            //faceMessage(hijo.getItemcode() + "  " + listaHijos.contains(hijo));
            
            
            
            hijo.setOvrwritten(true);
            
            RequestContext.getCurrentInstance().update("tableDet");
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="ESTADOS">
    public void estatePadre() {
        this.varEstados = 0;
    }

    public void estateHijo() {
        this.varEstados = 1;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="FUNCIONES VARIAS">
    
    public void resetFiltrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }
    
    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlg9", 2);
        switch (varEstados) {
            case 0:
                doUpdate();
                break;
            case 1:
                doUpdateHijos();
                break;
            case 2:
                showHideDialog("dlgC2", 2);
                deleteDetalle();
                break;
            default:
                break;
        }
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

    //actualizar cmb
    public void refreshCmb() {
        PricesListInTO var = null;
        try {
            listPriceList = AdminEJBService.getPricesList(var);
        } catch (Exception e) {
            faceMessage("Error " + e.getMessage());
        }
    }

    //limpiar campos
    public void cleanDetalle() {
        this.newBase = 0;
        this.newFactor = null;
        this.newList = null;
    }

    //validar que los campos no esten vacios
    public boolean validarNewList() {
        if (this.newList.isEmpty()) {
            faceMessage("Ingrese el nombre de la lista nueva");
            return false;
        }
        if (this.newBase < 1) {
            faceMessage("Seleccione lista base");
            return false;
        }
        if (this.newFactor < 1) {
            faceMessage("Ingrese un factor correcto");
            return false;
        }
        return true;
    }

    //mostrar un mensaje en pantalla dado un string
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    public ArrayList<ArticlesPriceTO> getSelectHijo() {
        return selectHijo;
    }

    public void setSelectHijo(ArrayList<ArticlesPriceTO> selectHijo) {
        this.selectHijo = selectHijo;
    }

    public ArrayList<ArticlesPriceTO> getListaHijos() {
        return listaHijos;
    }

    public void setListaHijos(ArrayList<ArticlesPriceTO> listaHijos) {
        this.listaHijos = listaHijos;
    }

    public List<PricesListTO> getListPriceList() {
        return listPriceList;
    }

    public void setListPriceList(List<PricesListTO> listPriceList) {
        this.listPriceList = listPriceList;
    }

    public List<String> getNomListBase() {
        return nomListBase;
    }

    public void setNomListBase(List<String> nomListBase) {
        this.nomListBase = nomListBase;
    }

    public PricesListTO getSelectPriceList() {
        return selectPriceList;
    }

    public void setSelectPriceList(PricesListTO selectPriceList) {
        this.selectPriceList = selectPriceList;
    }

    public ArrayList<PricesListTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<PricesListTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public String getNewList() {
        return newList;
    }

    public void setNewList(String newList) {
        this.newList = newList;
    }

    public int getNewBase() {
        return newBase;
    }

    public void setNewBase(int newBase) {
        this.newBase = newBase;
    }

    public Double getNewFactor() {
        return newFactor;
    }

    public void setNewFactor(Double newFactor) {
        this.newFactor = newFactor;
    }

    public boolean isvTrue() {
        return true;
    }

    public void setvTrue(boolean m) {
        this.vTrue = m;
    }

    public boolean isvFalse() {
        return false;
    }

    public void setvFalse(boolean m) {
        this.vFalse = m;
    }


 //</editor-fold>
    
}//CIERRE PRINCIPAL
