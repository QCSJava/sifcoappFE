/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.model.primefaces;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.security.to.ProfileDetOutTO;
import com.sifcoapp.objects.security.to.ProfileInTO;
import com.sifcoapp.objects.security.to.ProfileOutTO;
import com.sifcoapp.objects.security.to.UserAppInTO;
import com.sifcoapp.objects.security.to.UserAppOutTO;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
/**
 *
 * @author ps05393
 */
@ManagedBean
public class MenuView {
    private MenuModel model;
    private static SecurityEJBClient SecurityEJBService=null;
    /**
     * Creates a new instance of MenuView
     */
    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
         
        //First submenu
        //DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
        DefaultSubMenu firstSubmenu = new DefaultSubMenu();
        DefaultMenuItem item = new DefaultMenuItem();
        
//        item.setUrl("http://www.primefaces.org");
//        item.setIcon("ui-icon-home");
//        firstSubmenu.addElement(item);
//         
//       // model.addElement(firstSubmenu);
//         
//        //Second submenu
//        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
// 
//        item = new DefaultMenuItem("Save");
//        item.setIcon("ui-icon-disk");
//        item.setCommand("#{menuView.save}");
//        item.setUpdate("messages");
//        secondSubmenu.addElement(item);
//         
//        item = new DefaultMenuItem("Delete");
//        item.setIcon("ui-icon-close");
//        item.setCommand("#{menuView.delete}");
//        item.setAjax(false);
//        secondSubmenu.addElement(item);
//         
//        item = new DefaultMenuItem("Redirect");
//        item.setIcon("ui-icon-search");
//        item.setCommand("#{menuView.redirect}");
//        secondSubmenu.addElement(item);
 
       // model.addElement(secondSubmenu);
        
        
        /*de aqui para abajo */
            if 		(SecurityEJBService==null)
			SecurityEJBService=new SecurityEJBClient();
		
		UserAppInTO usr = new UserAppInTO();
		UserAppOutTO usrRes = new UserAppOutTO();
		usr.setIdUserApp("admin");
		usr.setPasswordUserApp("adminadmin");
		//usrRes=SecurityEJBService.UserValidate(usr);
		
		// TODO Auto-generated method stub

		System.out.println(usrRes.getValidUser());
		
		ProfileInTO profileInTO=new ProfileInTO();
		ProfileOutTO profileOutTO=new ProfileOutTO();
		//profileInTO.setId_perfil(id_perfil);
		//en este 
               /* profileOutTO=SecurityEJBService.GetUserProfile(profileInTO);
		
                //nombre del perfil (admin, conta)
		System.out.println(profileOutTO.getDesc_perfil());
		while (true){
			ProfileDetOutTO profileDetOutTO= new ProfileDetOutTO();
			List profileDetOutTOLst= new Vector();
			profileDetOutTOLst=profileOutTO.getProfile_det();
			//lista de todos los objetos principales (menu)
                        System.out.println(profileDetOutTO.getDesc_perfil_det());
			
			Iterator<ProfileDetOutTO> iterator = profileDetOutTOLst.iterator();
			while (iterator.hasNext()) {
				//System.out.println(iterator.next());
				ProfileDetOutTO profileDetOutTO1=(ProfileDetOutTO)iterator.next();
				System.out.println("->"+profileDetOutTO1.getDesc_perfil_det());
                                  firstSubmenu = new DefaultSubMenu(profileDetOutTO1.getDesc_perfil_det());
                                  model.addElement(firstSubmenu);
				if (profileDetOutTO1.getNodeDetail()!=null){
					//ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
					List profileDetOutTOLst1= profileDetOutTO1.getNodeDetail();
					Iterator<ProfileDetOutTO> iterator1 = profileDetOutTOLst1.iterator();
					try{
						while (iterator1.hasNext()) {
							ProfileDetOutTO profileDetOutTO3=(ProfileDetOutTO)iterator1.next();
							System.out.println("-->"+profileDetOutTO3.getDesc_perfil_det());
                                                        item= new DefaultMenuItem(profileDetOutTO3.getDesc_perfil_det());
                                                        item.setIcon("ui-icon-disk");
                                                        item.setCommand("#{menuView.save}");
                                                        item.setUpdate("messages");
                                                         firstSubmenu.addElement(item);
							if (profileDetOutTO3.getNodeDetail()!=null){
								//ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
								List profileDetOutTOLst2= profileDetOutTO3.getNodeDetail();
								Iterator<ProfileDetOutTO> iterator2 = profileDetOutTOLst2.iterator();
								try{
									while (iterator2.hasNext()) {
										ProfileDetOutTO profileDetOutTO4=(ProfileDetOutTO)iterator2.next();
										System.out.println("--->"+profileDetOutTO4.getDesc_perfil_det());
                                                                                item= new DefaultMenuItem(profileDetOutTO4.getDesc_perfil_det());
                                                                                item.setIcon("ui-icon-disk");
                                                                                item.setCommand("#{menuView.save}");
                                                                                item.setUpdate("messages");
                                                                                firstSubmenu.addElement(item);
									}	
								}catch(Exception ex){
									
								}
								
							}
						}	
					}catch(Exception ex){
						
					}
					
				}
			}
			
			break;
		}*/
        
        /*de aqui para arriba */
               // model.addElement(firstSubmenu);
              //  model.addElement(secondSubmenu);
    }
 
    public MenuModel getModel() {
        return model;
    }  
     
    public void save() {
        addMessage("Success", "Data saved");
    }
     
    public void update() {
        addMessage("Success", "Data updated");
    }
     
    public void delete() {
        addMessage("Success", "Data deleted");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
