/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.catalogo.bean;

import com.sifcoapp.client.AdminEJBClient;

import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.TablesCatalogTO;
import java.io.Serializable;
import java.util.List;
import com.sifcoapp.objects.catalogos.Common;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author ps05393
 */
@ManagedBean(name = "catalog")
@ViewScoped
public class CatalogoBean implements Serializable {

public CatalogoBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    private CatalogTO catalogoClassModel;

//     private static final String CATALOGOCLASES = "cg_articlesclass";
//    private static final String CATALOGOGROUP = "cg_articlesGroup";
//    private static final String CATALOGDEFAULTPROV = "cg_defaultProvider";
//    private static final String CATALOGSHOPPMESUNIT = "cg_shoppMeasureUnit";
//    private static final String CATALOGSALESMESUNIT = "cg_salesMeasureUnit";
    private static final long serialVersionUID = 1L;
    private static final String CATALOGSTATUS = "cg_status";
    private String catcode;
    private int tablecode;
    private String catvalue;
    private String catstatus;
    private int usersign;
    private AdminEJBClient AdminEJBService;
    private String catalogoClass;
    private List<CatalogTO> catalogoClassLst;

    private String description;
    private String locked;
    private boolean isActive;
    private List<TablesCatalogTO> catlgLst;
    private String tableCatalog;
    private String catvalue2;
    private String catvalue3;
    private List catalogest;
    TablesCatalogTO var = new TablesCatalogTO();

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    public List getCatalogest() {
        return catalogest;
    }

    public void setCatalogest(List catalogest) {
        this.catalogest = catalogest;
    }

    public String getCatvalue2() {
        return catvalue2;
    }

    public void setCatvalue2(String catvalue2) {
        this.catvalue2 = catvalue2;
    }

    public String getCatvalue3() {
        return catvalue3;
    }

    public void setCatvalue3(String catvalue3) {
        this.catvalue3 = catvalue3;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public List<TablesCatalogTO> getCatlgLst() {
        return catlgLst;
    }

    public void setCatlgLst(List<TablesCatalogTO> catlgLst) {
        this.catlgLst = catlgLst;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CatalogTO> getCatalogoClassLst() {
        return catalogoClassLst;
    }

    public void setCatalogoClassLst(List<CatalogTO> catalogoClassLst) {
        this.catalogoClassLst = catalogoClassLst;
    }

    public String getCatalogoClass() {
        return catalogoClass;
    }

    public void setCatalogoClass(String catalogoClass) {
        this.catalogoClass = catalogoClass;
    }

    public CatalogTO getCatalogoClassModel() {
        return catalogoClassModel;
    }

    public void setCatalogoClassModel(CatalogTO catalogoClassModel) {
        this.catalogoClassModel = catalogoClassModel;
    }

    public String getCatcode() {
        return catcode;
    }

    public void setCatcode(String catcode) {
        this.catcode = catcode;
    }

    public int getTablecode() {
        return tablecode;
    }

    public void setTablecode(int tablecode) {
        this.tablecode = tablecode;
    }

    public String getCatvalue() {
        return catvalue;
    }

    public void setCatvalue(String catvalue) {
        this.catvalue = catvalue;
    }

    public String getCatstatus() {
        return catstatus;
    }

    public void setCatstatus(String catstatus) {
        this.catstatus = catstatus;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }

    public AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public void setAdminEJBService(AdminEJBClient AdminEJBService) {
        this.AdminEJBService = AdminEJBService;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Load de pantalla">
    @PostConstruct
    public void initForm() {

        try {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            this.tableCatalog = getTableCatalog();
            catlgLst = AdminEJBService.getTablesCatalog();
            catalogoClassLst = AdminEJBService.findCatalog(tableCatalog);
        } catch (Exception ex) {
            Logger.getLogger(CatalogoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Guardar en base">
    public void doSave() {
        try {
            //Nota: No se si el codigo tiene que generarse o traer el max?
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            CatalogTO parameters = new CatalogTO();

            int resp;
            parameters.setCatcode(this.catcode);
            parameters.setCatvalue(this.catvalue);
            parameters.setCatvalue2(this.catvalue2);
            parameters.setCatvalue3(this.catvalue3);
            parameters.setCatstatus(this.catstatus);
            parameters.setTablecode(Integer.parseInt(this.tableCatalog));
            parameters.setUsersign(this.usersign);

            resp = AdminEJBService.cat_tab1_catalogos_mtto(parameters, Common.MTTOINSERT);
            if (resp == 0) {
                FacesMessage msg = new FacesMessage("Registro Insertado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.catcode = "";
                this.catvalue = "";
                this.catvalue2 = "";
                this.catvalue3 = "";
                this.isActive = false;
                this.tableCatalog = getTableCatalog();

                List res;
                if (tableCatalog != null) {
                    try {
                        res = AdminEJBService.getTablesCatalog();
                        for (Object obj : res) {

                            TablesCatalogTO cat = (TablesCatalogTO) obj;
                            if (cat.getCode() == Integer.parseInt(this.tableCatalog)) {
                                catalogoClassLst = AdminEJBService.findCatalog(cat.getName());
                                RequestContext.getCurrentInstance().update("form");
                                break;
                            }
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(CatalogoBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                FacesMessage msg = new FacesMessage("No se Pudo Insertar", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            Logger.getLogger(CatalogoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="CatalogChange">
    public void onCatalogChange() {
        this.tableCatalog = getTableCatalog();
        List res;
        if (tableCatalog != null) {
            try {
                res = AdminEJBService.getTablesCatalog();
                for (Object obj : res) {

                    TablesCatalogTO cat = (TablesCatalogTO) obj;
                    if (cat.getCode() == Integer.parseInt(this.tableCatalog)) {
                        catalogoClassLst = AdminEJBService.findCatalog(cat.getName());
                        RequestContext.getCurrentInstance().update("catalog");
                        break;
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(CatalogoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="onRowEdit">
    public void onRowEdit(RowEditEvent event) {
        try {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            CatalogTO parameters = new CatalogTO();
            int resp;

            parameters.setCatcode(((CatalogTO) event.getObject()).getCatcode());
            parameters.setCatstatus(((CatalogTO) event.getObject()).getCatstatus());
            parameters.setCatvalue(((CatalogTO) event.getObject()).getCatvalue());
            parameters.setCatvalue2(((CatalogTO) event.getObject()).getCatvalue2());
            parameters.setCatvalue3(((CatalogTO) event.getObject()).getCatvalue3());
            parameters.setTablecode(((CatalogTO) event.getObject()).getTablecode());
            //parameters.setTablecode(this.tablecode);
            System.out.println("Imprime Valores a Actualizar!");
            System.out.println(((CatalogTO) event.getObject()).getCatcode());
            System.out.println(((CatalogTO) event.getObject()).getCatcode());
            System.out.println(((CatalogTO) event.getObject()).getCatvalue());
            System.out.println(((CatalogTO) event.getObject()).getCatvalue2());
            System.out.println(((CatalogTO) event.getObject()).getCatvalue3());
            System.out.println(((CatalogTO) event.getObject()).getTablecode());
            System.out.println("Fin Imprime Valores a Actualizar!");
            resp = AdminEJBService.cat_tab1_catalogos_mtto(parameters, Common.MTTOUPDATE);
            if (resp == 0) {
                FacesMessage msg = new FacesMessage("Registro Editado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("No se Pudo Editar", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            Logger.getLogger(CatalogoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="onRowCancel">
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        //FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="onCellEdit">
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

}//cierre de clase
