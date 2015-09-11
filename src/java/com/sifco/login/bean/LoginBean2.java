/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.login.bean;

import com.sifcoapp.client.AdminEJBClient;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.security.to.UserAppInTO;
import com.sifcoapp.objects.security.to.UserAppOutTO;
import com.sifcoapp.objects.utilities.PasswordService;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sifcoapp.objects.security.to.UserTO;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "loginBean")
@SessionScoped
/**
 *
 * @author ps05393
 */
public class LoginBean2 implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombreEmpresa;
    private String password;
    private String password1;
    private String password2;
    private String password3;
    private String message, uname;
    private static SecurityEJBClient SecurityEJBService = null;
    private static AdminEJBClient AdminEJBService = null;
    private String userName;

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public static SecurityEJBClient getSecurityEJBService() {
        return SecurityEJBService;
    }

    public static void setSecurityEJBService(SecurityEJBClient SecurityEJBService) {
        LoginBean2.SecurityEJBService = SecurityEJBService;
    }

    public String getPassword3() {
        return password3;
    }

    public void setPassword3(String password3) {
        this.password3 = password3;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String loginProject() throws Exception {
        //  boolean result = UserDAO.login(uname, password);
        String res = null;
        UserTO param = new UserTO();
        if (uname != null && password != null) {
            //if (uname != null && uname.equals("admin") && password != null && password.equals("admin")) {
            // get Http Session and store username

            if (SecurityEJBService == null) {
                SecurityEJBService = new SecurityEJBClient();
            }
            UserAppInTO usr = new UserAppInTO();
            UserAppOutTO usrRes = new UserAppOutTO();
            usr.setIdUserApp(uname);
            try {
                usr.setPasswordUserApp(PasswordService.getInstance().encrypt(password));
            } catch (Exception ex) {
                Logger.getLogger(LoginBean2.class.getName()).log(Level.SEVERE, null, ex);
            }
            usrRes = SecurityEJBService.UserValidate(usr);
            System.out.println(usrRes.getValidUser());

            if (usrRes.getValidUser() == 0) {
                param = SecurityEJBService.getUserByNickname(uname);
                HttpSession session = Util.getSession();
                session.setAttribute("username", uname);
                session.setAttribute("profilename", usrRes.getUsrprofile().getDesc_perfil());
                session.setAttribute("profilecode", usrRes.getUsrprofile().getId_perfil());
                session.setAttribute("usersign", param.getUsersign());
                session.setAttribute("userfullname", param.getUsername() + " " + param.getLastname());
                userName = (String) session.getAttribute("username");
                res = "index";
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
            }
            /* else {
             FacesContext.getCurrentInstance().addMessage(
             null,
             new FacesMessage(FacesMessage.SEVERITY_WARN,"Invalid Login!","Please Try Again!"));
             // invalidate session, and redirect to other pages
             //message = "Invalid Login. Please Try Again!";
             res= "login";
             }*/
        }
        return res;
    }

    public String logout() {
        /* ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()).invalidate();
         FacesContext.getCurrentInstance().getExternalContext().invalidateSession();*/

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        httpSession.invalidate();
        System.out.println("invalidando sesion");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        //ec.invalidateSession();
        try {
            ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "login";
    }

    public void ValidarPass() throws Exception {
        UserTO parameters = new UserTO();
        String password0;
        int result = 0;
        parameters = SecurityEJBService.getUserByNickname(uname);
        password0 = parameters.getPassword();
        if (password0.equals(PasswordService.getInstance().encrypt(password1))) {
            if (password2.equals(password3)) {
                parameters.setPassword(PasswordService.getInstance().encrypt(password3));
                result = SecurityEJBService.cat_users_mtto(parameters, 2);
                RequestContext.getCurrentInstance().update("change");
                faceMessage("Actualizacion Correcta");
                closeDialog();
            } else {
                faceMessage("Error las contraseñas no coinciden");
            }

        } else {
            faceMessage("Error La Contraseña Actual no es correcta");
        }
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public void closeDialog() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg').hide();");
        this.password1 = null;
        this.password2 = null;
        this.password3 = null;
    }

}//cierre clase
