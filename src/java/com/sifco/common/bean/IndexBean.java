/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.common.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
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
    private String profileCode;
    private static AdminEJBClient AdminEJBService = null;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public static AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public static void setAdminEJBService(AdminEJBClient AdminEJBService) {
        IndexBean.AdminEJBService = AdminEJBService;
    }

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
    
//<editor-fold defaultstate="collapsed" desc="NAME CORP">
    public String nameCorp() {
        try {
            AdminEJBService = new AdminEJBClient();
            EnterpriseTO resp = AdminEJBService.getEnterpriseInfo();
            return resp.getCrintHeadr();
        } catch (Exception e) {
            return null;
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    public void dlg() {
        try {
            if (band == 0) {
                stop = true;
                band = 1;
                ParameterEJBClient ParameterEJBClient = new ParameterEJBClient();
                setProfileCode(ParameterEJBClient.getParameterbykey(8).getValue1());
                int cmpCode = (int) session.getAttribute("profilecode");
                if (getProfileCode().equals(cmpCode + "")) {
                    showHideDialog("dlg001", 1);
                } else {
                    System.out.println("NO Recurring...");
                }
            }
        } catch (Exception e) {
            System.out.println("Recurring...");
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
