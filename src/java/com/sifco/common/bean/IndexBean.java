/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.common.bean;

import com.sifco.login.bean.Util;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "IndexBean")
@SessionScoped
public class IndexBean implements Serializable {

    public IndexBean() {
    }
//<editor-fold defaultstate="collapsed" desc="Declaraciones">
    HttpSession session = Util.getSession();
    private int band = 0;
    private boolean stop = false;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    public void dlg() {
        if (band == 0) {
            stop = true;
            band = 1;
            showHideDialog("dlg001", 1);
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

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

//</editor-fold>

}//cierre de clase
