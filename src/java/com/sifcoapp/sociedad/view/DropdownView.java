/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.sociedad.view;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author ps05393
 */
@ManagedBean
@ViewScoped
public class DropdownView implements Serializable {
 private static AdminEJBClient AdminEJBService;
    private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private String country;
    private Map<String,String> countries;

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<String, String> countries) {
        this.countries = countries;
    }
    
    /**
     * Creates a new instance of DropdownView
     */
    public DropdownView() {
//        
//            List catlgLst=null;
//        if (AdminEJBService==null)
//		AdminEJBService=new AdminEJBClient();
//            
//                countries  = new HashMap<String, String>();
//                catlgLst=AdminEJBService.findCatalog("cg_paises");
//                    System.out.println("luego de servicio");
//		Iterator<CatalogTO> iterator = catlgLst.iterator();
//		while (iterator.hasNext()) {
//			//System.out.println(iterator.next());
//			CatalogTO catalogTO=(CatalogTO)iterator.next();
//                        System.out.println("->"+catalogTO.getValueCatlg());
//                        System.out.println("->"+catalogTO.getCodeCatlg());
//                        countries.put(catalogTO.getValueCatlg(), catalogTO.getCodeCatlg());
//                           }
    }
    
}
