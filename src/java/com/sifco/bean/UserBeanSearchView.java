/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.security.to.UserTO;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rutilio
 */
@ManagedBean(name = "usersView")
public class UserBeanSearchView {
    private List<UserTO> usersLst;
    private static SecurityEJBClient SecurityEJBService=null;
    /**
     * Creates a new instance of UserBeanSearchView
     */
    public UserBeanSearchView() {
    }
    @PostConstruct
     public void initForm() {
        
        if (SecurityEJBService==null)
			SecurityEJBService=new SecurityEJBClient(); 
        
        this.setUsersLst(SecurityEJBService.getUser());
                
        
    }
    
    /**
     * @return the usersLst
     */
    public List<UserTO> getUsersLst() {
        return usersLst;
    }

    /**
     * @param usersLst the usersLst to set
     */
    public void setUsersLst(List<UserTO> usersLst) {
        this.usersLst = usersLst;
    }
    /*
    Devuelve el usuario seleccionado a la ventana padre
    */
    public void selectCarFromDialog(UserTO usr) {
        RequestContext.getCurrentInstance().closeDialog(usr);
    }
}
