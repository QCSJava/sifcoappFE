/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.objects.accounting.to.JournalEntryTO;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.report.bean.RepAccount;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "periodEndBean")
@SessionScoped
public class PeriodEndBean implements Serializable {

    public PeriodEndBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Variables">
    private final RepAccount rep = new RepAccount();
    private Date fecha = new Date();
    private AccountingEJBClient accounting;
    HttpSession session = Util.getSession();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="btn guardar">
    public void doSave() {
        if (accounting == null) {
            accounting = new AccountingEJBClient();
        }

        JournalEntryTO journal = new JournalEntryTO();
        ResultOutTO _res;
        journal.setDuedate(fecha);
        journal.setUsersign((int) session.getAttribute("usersign"));
        try {
            _res = accounting.fill_Journal_close(journal);
            if (_res.getCodigoError() == 0) {
                faceMessage("Cierre contable generado con exito");
            }else
                faceMessage("Error al generar cierre contable");

        } catch (Exception ex) {
            faceMessage(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(PeriodEndBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Descargar reporte">
    public void DescRep() {
        try {
            /*rep.initForm();
             rep.setFtype(3);
             rep.setFdateReport(fecha);
             rep.setReportLevel(3);
             rep.print(1);*/
            doSave();
            //return true;
        } catch (Exception e) {
            // return false;
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funcions varias">
    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void confirmDialog() {
        faceMessage("Entro");
        doSave();
    }

    //Mostrar u ocultar un dialogo; 1 muestra, 2 oculta
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

}//cierre de clase
