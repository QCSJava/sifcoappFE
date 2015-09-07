/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifco.login.bean.LoginBean2;
import com.sifco.login.bean.Util;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.security.to.ProfileDetOutTO;
import com.sifcoapp.objects.security.to.ProfileInTO;
import com.sifcoapp.objects.security.to.ProfileOutTO;
import com.sifcoapp.objects.security.to.UserAppInTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name = "MenuModel")
@SessionScoped
public class MenuModelBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Variables">
    private MenuModel simpleMenuModel = new DefaultMenuModel();
    private static SecurityEJBClient SecurityEJBService = null;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Init ventana">
    @PostConstruct
    public void initForm() {
        ArrayList urlList = new ArrayList<String>();    
        /*DefaultSubMenu sumenu=new DefaultSubMenu();
         sumenu.setLabel("submen");
         sumenu.setId("1");
        
         DefaultMenuItem menuItem = new DefaultMenuItem();
        
         menuItem.setValue("Computers");
         menuItem.setUrl("#");
         //simpleMenuModel.addElement(menuItem);
        
         sumenu.addElement(menuItem);
        
         simpleMenuModel.addElement(sumenu)*/
        if (SecurityEJBService == null) {
            SecurityEJBService = new SecurityEJBClient();
        }

        HttpSession session = Util.getSession();
        UserAppInTO usr = new UserAppInTO();
        ProfileInTO profileInTO = new ProfileInTO();
        ProfileOutTO profileOutTO = new ProfileOutTO();
        String _username = (String) session.getAttribute("username");
        usr.setIdUserApp((String) session.getAttribute("username"));
        //en este 
        System.out.println("_username");
        System.out.println(_username);
        if (_username != null) {
            profileOutTO = SecurityEJBService.GetUserProfile(usr);

            //nombre del perfil (admin, conta)
            System.out.println("PERFIL= " + profileOutTO.getDesc_perfil());
            //while (true){
            //ProfileDetOutTO profileDetOutTO = new ProfileDetOutTO();
            List profileDetOutTOLst = new Vector();
            profileDetOutTOLst = profileOutTO.getProfile_det();
            //lista de todos los objetos principales (menu)
            //System.out.println("MENU == " + profileDetOutTO.getDesc_perfil_det());
            //this.processMenu(profileDetOutTO);
            Iterator<ProfileDetOutTO> iterator = profileDetOutTOLst.iterator();
            while (iterator.hasNext()) {
                //System.out.println(iterator.next());
                ProfileDetOutTO profileDetOutTO1 = (ProfileDetOutTO) iterator.next();
                //System.out.println("Menu=="+profileDetOutTO1.getDesc_perfil_det());
                DefaultSubMenu subMenu = new DefaultSubMenu();
                //subMenu.setLabel(profileDetOutTO1.getDesc_perfil_det());
                //this.processMenu(profileDetOutTO1,subMenu);
                subMenu.setLabel(profileDetOutTO1.getDesc_perfil_det());

                //System.out.println("MENU->"+profileDetOutTO1.getDesc_perfil_det());
                if (profileDetOutTO1.getNodeDetail() != null) {
                    //ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
                    List profileDetOutTOLst1 = profileDetOutTO1.getNodeDetail();
                    Iterator<ProfileDetOutTO> iterator1 = profileDetOutTOLst1.iterator();
                    try {
                        while (iterator1.hasNext()) {
                            ProfileDetOutTO profileDetOutTO3 = (ProfileDetOutTO) iterator1.next();
                            DefaultSubMenu subMenu1 = new DefaultSubMenu();
                            if (profileDetOutTO3.getNodeDetail().size() > 0) {
                                //System.out.println("SUBMENU-->"+profileDetOutTO3.getDesc_perfil_det());                                                        
                                subMenu1.setLabel(profileDetOutTO3.getDesc_perfil_det());
                                //ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
                                List profileDetOutTOLst2 = profileDetOutTO3.getNodeDetail();
                                Iterator<ProfileDetOutTO> iterator2 = profileDetOutTOLst2.iterator();
                                try {
                                    while (iterator2.hasNext()) {
                                        ProfileDetOutTO profileDetOutTO4 = (ProfileDetOutTO) iterator2.next();

                                        DefaultSubMenu subMenu2 = new DefaultSubMenu();
                                        if (profileDetOutTO4.getNodeDetail().size() > 0) {
                                            //                 System.out.println("submenu--->"+profileDetOutTO4.getDesc_perfil_det());
                                            subMenu2.setLabel(profileDetOutTO4.getDesc_perfil_det());

                                            //ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
                                            List profileDetOutTOLst3 = profileDetOutTO4.getNodeDetail();
                                            Iterator<ProfileDetOutTO> iterator3 = profileDetOutTOLst3.iterator();
                                            try {
                                                while (iterator3.hasNext()) {
                                                    ProfileDetOutTO profileDetOutTO5 = (ProfileDetOutTO) iterator3.next();
                                                    //                               System.out.println("item--->"+profileDetOutTO5.getDesc_perfil_det());
                                                    DefaultMenuItem menuItem = new DefaultMenuItem();
                                                    menuItem.setValue(profileDetOutTO5.getDesc_perfil_det());
                                                    menuItem.setUrl(profileDetOutTO5.getUrl_perfil_det());
                                                    urlList.add(profileDetOutTO5.getUrl_perfil_det());
                                                    subMenu2.addElement(menuItem);

                                                }
                                            } catch (Exception ex) {

                                            }
                                            subMenu1.addElement(subMenu2);

                                        } else {
                                            //         System.out.println("item--->"+profileDetOutTO4.getDesc_perfil_det());
                                            DefaultMenuItem menuItem = new DefaultMenuItem();
                                            menuItem.setValue(profileDetOutTO4.getDesc_perfil_det());
                                            menuItem.setUrl(profileDetOutTO4.getUrl_perfil_det());
                                            urlList.add(profileDetOutTO4.getUrl_perfil_det());
                                            //subMenu2.addElement(menuItem);
                                            subMenu1.addElement(menuItem);
                                        }

                                    }
                                } catch (Exception ex) {

                                }
                                subMenu.addElement(subMenu1);
                            } else {
                                //System.out.println("ITEM-->"+profileDetOutTO3.getDesc_perfil_det());                                                        
                                DefaultMenuItem menuItem = new DefaultMenuItem();
                                menuItem.setValue(profileDetOutTO3.getDesc_perfil_det());
                                menuItem.setUrl(profileDetOutTO3.getUrl_perfil_det());
                                urlList.add(profileDetOutTO3.getUrl_perfil_det());
                                subMenu.addElement(menuItem);
                            }
                            //subMenu.addElement(subMenu1);
                        }
                    } catch (Exception ex) {

                    }

                } else {

                }
                simpleMenuModel.addElement(subMenu);

            }
            session.setAttribute("urlsUser", urlList);
            System.out.println("fin");
            
        } else {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //	
        //	break;
        //}*/
        /*DefaultMenuItem menuItem = new DefaultMenuItem();
        
         menuItem.setValue("Computers");
         menuItem.setUrl("#");
         simpleMenuModel.addElement(menuItem);
         */
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ProcessMenu">
    private void processMenu(ProfileDetOutTO menu, DefaultSubMenu parentMenu) {

        //System.out.println("Menu=="+menu.getDesc_perfil_det());
        ProfileDetOutTO subMenu;

        if (menu.getNodeDetail() != null) {

            List profileDetOutTOLst1 = menu.getNodeDetail();
            Iterator<ProfileDetOutTO> iterator1 = profileDetOutTOLst1.iterator();
            try {
                while (iterator1.hasNext()) {

                    DefaultSubMenu subMenuIt = new DefaultSubMenu();
                    DefaultMenuItem menuItem = new DefaultMenuItem();

                    subMenu = (ProfileDetOutTO) iterator1.next();

                    if (subMenu.getNodeDetail() != null) {
                        subMenuIt.setLabel(subMenu.getDesc_perfil_det());
                        parentMenu.addElement(subMenuIt);
                        this.processMenu(subMenu, subMenuIt);
                    } else {
                        menuItem.setValue(subMenu.getDesc_perfil_det());
                        menuItem.setUrl("#");
                        parentMenu.addElement(menuItem);
                    }

                    //parentMenu.addElement(subMenuIt);
                    //this.processMenu(subMenu, subMenuIt);
                }
            } catch (Exception ex) {

            }

            //this.processMenu(menu);
        } else {

            /*DefaultSubMenu subMenuIt=new DefaultSubMenu();
             subMenuIt.setLabel(menu.getDesc_perfil_det());
             parentMenu.addElement(subMenuIt);*/
            DefaultMenuItem menuItem = new DefaultMenuItem();
            menuItem.setValue(menu.getDesc_perfil_det());
            menuItem.setUrl("#");
            parentMenu.addElement(menuItem);

        }

    }

    public MenuModelBean() {
        /* DefaultMenuItem menuItem = new DefaultMenuItem();
         menuItem.setValue("Computers");
         menuItem.setUrl("#");
         simpleMenuModel.addElement(menuItem);*/
        /* menuItem = new MenuItem();
         menuItem.setValue("Clothes");
         menuItem.setUrl("#");
         simpleMenuModel.addMenuItem(menuItem);
         menuItem = new MenuItem();
         menuItem.setValue("Gaming");
         menuItem.setUrl("#");
         simpleMenuModel.addMenuItem(menuItem);
         menuItem = new MenuItem();
         menuItem.setValue("Books");
         menuItem.setUrl("#");
         simpleMenuModel.addMenuItem(menuItem);
         menuItem = new MenuItem();
         menuItem.setValue("Jewellery and Watches");
         menuItem.setUrl("#");
         simpleMenuModel.addMenuItem(menuItem); */

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">


    public void setSimpleMenuModel(MenuModel simpleMenuModel) {
        this.simpleMenuModel = simpleMenuModel;
    }

    public MenuModel getSimpleMenuModel() {
        return simpleMenuModel;
    }
//</editor-fold>

}//cierre de clase
