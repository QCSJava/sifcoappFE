/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.prueba.model.primefaces.Util;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "dailyClosingBean")
@SessionScoped
public class DailyClosingBean implements Serializable{

    public DailyClosingBean() {
    }
    
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    //servicios
    private static AdminEJBClient AdminEJBService;
    private static AccountingEJBClient AccountingEJBClient;
    
    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();
    
    //variables de pantalla
    private int tipoCierre;
    private Double saldoTrans = 0.0;
    private String comment;
    private String accBank;
    
    //__________________________________________________________________________
    private boolean enableBtn = true;
    //__________________________________________________________________________
    //CMB
    private static final String CATALOGBANK = "Banks";
    private List<CatalogTO> lstBanks;
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="LOAD">
    @PostConstruct
    public void initForm() {
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        
        try {
            lstBanks = AdminEJBService.findCatalog(CATALOGBANK);
        } catch (Exception e) {
        }
        
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Seleccion de tipo">
    public void tipeChange(){//ValueChangeEvent event) {
        //!event.getOldValue().equals("0")){//
        //faceMessage("entro");
        if (this.tipoCierre != 0 && !this.accBank.equals("-1")) {
            try {
                this.enableBtn = false;
                AccountTO in = new AccountTO();
                in.setUsersing((int)session.getAttribute("usersign"));
                in.setObjtype(tipoCierre+"");
                this.saldoTrans = AccountingEJBClient.devolver_saldo(in);
            } catch (Exception e) {
            }
        }else
            this.enableBtn = true;
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave(){
        if (!this.accBank.equals("-1") && this.saldoTrans>0.0 && this.tipoCierre != 0) {
            try {
                ResultOutTO res = new ResultOutTO();
                AccountTO in = new AccountTO();
                Date d = new Date();
                in.setUsersing((int)session.getAttribute("usersign"));
                in.setObjtype(tipoCierre+"");
                in.setCreatedate(d);
                in.setAcctcode(accBank);
                in.setFormatcode(comment);
                in.setCurrtotal(saldoTrans);
                res = AccountingEJBClient.traslado_caja(in);
                if (res.getCodigoError() == 0) {
                    faceMessage(res.getMensaje());
                    this.enableBtn = true;
                    RequestContext.getCurrentInstance().update("frmDaily");
                }else
                    faceMessage(res.getMensaje());
            } catch (Exception e) {
            }
        }else
            faceMessage("No se puede realizar la transferencia");
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BTN NUEVO">
    public void botonNuevo(ActionEvent actionEvent) {
        this.accBank = "-1";
        this.tipoCierre = 0;
        this.saldoTrans = 0.0;
        this.comment = "";
        this.enableBtn = true;
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BTN GUARDAR">
    public void btnPrincipal(){
        showHideDialog("dlgC2", 1);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funcions varias">
    
    public void confirmDialog() {
        showHideDialog("dlgC2", 2);
        doSave();
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
    
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        DailyClosingBean.AdminEJBService = AdminEJBService;
    }

    public List<CatalogTO> getLstBanks() {
        return lstBanks;
    }

    public void setLstBanks(List<CatalogTO> lstBanks) {
        this.lstBanks = lstBanks;
    }
    
    

    public boolean isEnableBtn() {
        return enableBtn;
    }

    public void setEnableBtn(boolean enableBtn) {
        this.enableBtn = enableBtn;
    }
    
    
    public int getTipoCierre() {
        return tipoCierre;
    }

    public void setTipoCierre(int tipoCierre) {
        this.tipoCierre = tipoCierre;
    }

    public Double getSaldoTrans() {
        return saldoTrans;
    }

    public void setSaldoTrans(Double saldoTrans) {
        this.saldoTrans = saldoTrans;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccBank() {
        return accBank;
    }

    public void setAccBank(String accBank) {
        this.accBank = accBank;
    }
    
    //</editor-fold>

}//cierre de clas
