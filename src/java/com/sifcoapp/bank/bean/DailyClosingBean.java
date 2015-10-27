/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "dailyClosingBean")
@SessionScoped
public class DailyClosingBean implements Serializable{

    public DailyClosingBean() {
    }
    
//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    //servicios
    private static AdminEJBClient AdminEJBService;
    private static AccountingEJBClient AccountingEJBClient;
    ParameterEJBClient ParameterEJBClient;
    
    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();
    
    //variables de pantalla
    
    private Double saldoTrans = 0.0;
    private String comment;
    private boolean enableBtn = true;
    private String newCodCuenta, newNomCuenta;
    private Date fecha = new Date();
    private int userSign = (int) session.getAttribute("usersign");
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="LOAD">
    @PostConstruct
    public void initForm() {
        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        updAcc();
        onDateSelect(fecha);
    }
//</editor-fold>
  
//<editor-fold defaultstate="collapsed" desc="selct fecha upd saldo">
    public void onDateSelect(Date fecha){
        try {
            faceMessage("Actualizando saldo...");
            this.saldoTrans = AccountingEJBClient.getSaldoSales(fecha, userSign);
            RequestContext.getCurrentInstance().update("frmDaily1");
        } catch (Exception e) {
            faceMessage("Error get saldos: " +e.getMessage()+" "+e.getCause());
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="upd cuenta">
    public void updAcc(){
        try {
            String code = ParameterEJBClient.getParameterbykey(26).getValue1();
            AccountTO var = AccountingEJBClient.getAccountByKey(code);
            this.newCodCuenta = var.getAcctcode();
            this.newNomCuenta = var.getAcctname();
        } catch (Exception ex) {
            Logger.getLogger(DailyClosingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave(){
        try {
            ResultOutTO var = AccountingEJBClient.traslado_caja_venta(saldoTrans, fecha, userSign);
            if (var.getCodigoError() == 0) {
                faceMessage(var.getMensaje());
                botonNuevo();
            }else
                faceMessage(var.getMensaje());
        } catch (Exception e) {
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BTN NUEVO">
    public void botonNuevo() {
        this.saldoTrans = 0.0;
        this.comment = "";
        this.newCodCuenta = "";
        this.newNomCuenta = "";
        this.fecha = new Date();
        updAcc();
        onDateSelect(fecha);
        
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BTN GUARDAR">
    public void btnPrincipal(){
        if (this.saldoTrans>0) {
            showHideDialog("dlgC2", 1);
        }else{
            faceMessage("No hay saldo a transferir");
        }
        
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getUserSign() {
        return userSign;
    }

    public void setUserSign(int userSign) {
        this.userSign = userSign;
    }
    public static AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public static void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        DailyClosingBean.AccountingEJBClient = AccountingEJBClient;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
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
    
    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        DailyClosingBean.AdminEJBService = AdminEJBService;
    }
public boolean isEnableBtn() {
        return enableBtn;
    }

    public void setEnableBtn(boolean enableBtn) {
        this.enableBtn = enableBtn;
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

    //</editor-fold>

}//cierre de clas
