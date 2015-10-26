/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.bank.to.ColecturiaDetailTO;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.ArrayList;
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

@ManagedBean(name = "dailyClosingColectBean")
@SessionScoped
public class DailyClosingColectBean implements Serializable {

    public DailyClosingColectBean() {
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
    private int userSign = (int)session.getAttribute("usersign");
    private Date fecha= new Date();
    private ArrayList<ColecturiaDetailTO> listaDetalles = new ArrayList<>(); //DataTable

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
        
        updTabla(fecha);

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SELECT FECHA">
    public void onDateSelect() {
        faceMessage("Actualizando saldos...");
        updTabla(fecha);
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="UPD TABLA">
    public void updTabla(Date fecha){
        try {
            listaDetalles.clear();
            List lista = AccountingEJBClient.devolver_saldo_colecturia(fecha, userSign);
            for (Object obj : lista) {
                listaDetalles.add((ColecturiaDetailTO) obj);
            }
            RequestContext.getCurrentInstance().update("tableDetailBill");
        } catch (Exception e) {
            faceMessage("Error get saldos: " +e.getMessage()+" "+e.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {
        
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BTN GUARDAR">
    public void btnPrincipal() {
        showHideDialog("dlgC2", 1);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funcions varias">
    public void confirmDialog() {
        showHideDialog("dlgC2", 2);
        faceMessage("Guardar");
        //doSave();
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

    public int getUserSign() {
        return userSign;
    }

    public void setUserSign(int userSign) {
        this.userSign = userSign;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public ArrayList<ColecturiaDetailTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<ColecturiaDetailTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public static AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public static void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        DailyClosingColectBean.AccountingEJBClient = AccountingEJBClient;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        DailyClosingColectBean.AdminEJBService = AdminEJBService;
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

    //</editor-fold>
    
}//cierre de clas
