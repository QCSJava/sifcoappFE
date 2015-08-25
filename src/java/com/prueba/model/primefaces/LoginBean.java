/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.model.primefaces;


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
/*
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.security.to.UserAppInTO;
import com.sifcoapp.objects.security.to.UserAppOutTO;
*/

public class LoginBean implements Serializable {
  private static final long serialVersionUID = -2152389656664659476L;
  private String nombre;
  private String clave;
  private boolean logeado = false;
  //private static SecurityEJBClient SecurityEJBService=null;

  public boolean estaLogeado() {
    return logeado;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public void login(ActionEvent actionEvent) {
    RequestContext context = RequestContext.getCurrentInstance();
    FacesMessage msg = null;
    if (nombre != null  && clave != null) {
    /*	if (SecurityEJBService==null)
  		  SecurityEJBService=new SecurityEJBClient();
  		  UserAppInTO usr = new UserAppInTO();
  		  UserAppOutTO usrRes = new UserAppOutTO();
  		  usr.setIdUserApp(nombre);
  		  usr.setPasswordUserApp(clave);
  		  usrRes=SecurityEJBService.UserValidate(usr);
  		  System.out.println(usrRes.getValidUser());
  		  if (usrRes.getValidUser()==1)
  			  {logeado = true;
  		  		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", nombre);
  			  }
  		  else{
  			 logeado = false;
  		      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
  		                             "Credenciales no válidas");
  		  }
    } else {
      logeado = false;
      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                             "Credenciales no válidas");
    }*/

    
    if (nombre != null && nombre.equals("admin") && clave != null
            && clave.equals("admin")) {
          logeado = true;
          msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", nombre);
        } else {
          logeado = false;
          msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                                 "Credenciales no válidas");
        }
    FacesContext.getCurrentInstance().addMessage(null, msg);
    context.addCallbackParam("estaLogeado", logeado);
    if (logeado)
      context.addCallbackParam("view", "gauge.xhtml");
	
	  
  }
 }

  public void logout() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance() 
                                        .getExternalContext().getSession(false);
    session.invalidate();
    logeado = false;
  }
}