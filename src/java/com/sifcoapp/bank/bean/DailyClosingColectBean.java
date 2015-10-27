/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.BankEJBClient;
import com.sifcoapp.objects.bank.to.ColecturiaDetailTO;
import com.sifcoapp.objects.bank.to.ColecturiaTO;
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
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "dailyClosingColectBean")
@SessionScoped
public class DailyClosingColectBean implements Serializable {

    public DailyClosingColectBean() {
    }

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    //servicios
    private static AccountingEJBClient AccountingEJBClient;
    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();

    //variables de pantalla
    private String comment;
    private int userSign = (int)session.getAttribute("usersign");
    private Date fecha= new Date();
    private ArrayList<ColecturiaDetailTO> listaDetalles = new ArrayList<>(); //DataTable
    private boolean btn = false;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="LOAD">
    @PostConstruct
    public void initForm() {
        
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
            List lista = new ArrayList();
            lista = AccountingEJBClient.devolver_saldo_colecturia(fecha, userSign);
            for (Object obj : lista) {
                listaDetalles.add((ColecturiaDetailTO) obj);
            }
            RequestContext.getCurrentInstance().update("frmDaily");
        } catch (Exception e) {
            faceMessage("Error get saldos: " +e.getMessage()+" "+e.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {
        try {
            ColecturiaTO newColect = new ColecturiaTO();
            newColect.setDocdate(fecha);
            newColect.setUsersign(userSign);
            newColect.setComments(comment);
            List lstPadre = new ArrayList();
            for (ColecturiaDetailTO obj : listaDetalles) {
                lstPadre.add(obj);
            }
            newColect.setColecturiaDetail(lstPadre);
            ResultOutTO _res = new ResultOutTO();
            
            _res = AccountingEJBClient.traslado_caja_colecturia(newColect);
            
            if (_res.getCodigoError()==0) {
                faceMessage(_res.getMensaje());
                this.comment="";
                updTabla(fecha);
            }else
                faceMessage(_res.getMensaje());
            
            
        } catch (Exception e) {
            faceMessage(e.getMessage() + " " + e.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BTN GUARDAR">
    public void btnPrincipal() {
        if (this.listaDetalles.size()>0) {
            showHideDialog("dlgC2", 1);
        }else{
            faceMessage("No se encuentran traslados");
        }
        
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funcions varias">
    public void confirmDialog() {
        showHideDialog("dlgC2", 2);
        //faceMessage("Guardar");
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

    public boolean isBtn() {
        return btn;
    }

    public void setBtn(boolean btn) {
        this.btn = btn;
    }
    
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //</editor-fold>
    
}//cierre de clas
