/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Peter-PC
 */
@ManagedBean(name = "paymentBank")
public class PaymentBankBean {

    /**
     * Creates a new instance of PaymentBankBean
     */
    public PaymentBankBean() {
    }
    private String cod;
    private String name;
    private String contact;
    private String check;
    private String numer;
    private Date postdate;
    private Date postdoc;
    private Date postdocf;
    private String reference;
    private List<CatalogTO> ltsCatalogCl;
    private AccountingEJBClient AccountingEJBClient;
    private AdminEJBClient AdminEJBClient;
    private static final String OPTIONS = "cg_payoptions";
    private String comment;
    private String commentA;
    private String impotV;
    private String saldoP;
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentA() {
        return commentA;
    }

    public void setCommentA(String commentA) {
        this.commentA = commentA;
    }

    public String getImpotV() {
        return impotV;
    }

    public void setImpotV(String impotV) {
        this.impotV = impotV;
    }

    public String getSaldoP() {
        return saldoP;
    }

    public void setSaldoP(String saldoP) {
        this.saldoP = saldoP;
    }

    public List<CatalogTO> getLtsCatalogCl() {
        return ltsCatalogCl;
    }

    public void setLtsCatalogCl(List<CatalogTO> ltsCatalogCl) {
        this.ltsCatalogCl = ltsCatalogCl;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }

    public Date getPostdoc() {
        return postdoc;
    }

    public void setPostdoc(Date postdoc) {
        this.postdoc = postdoc;
    }

    public Date getPostdocf() {
        return postdocf;
    }

    public void setPostdocf(Date postdocf) {
        this.postdocf = postdocf;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    
    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="INIT">
    


    @PostConstruct
    public void init() {
        try {
            if (AdminEJBClient == null) {
                AdminEJBClient = new AdminEJBClient();
            }
            this.ltsCatalogCl = AdminEJBClient.findCatalog(OPTIONS);
            
            // this.arcmact = AccountingEJBClient.getAccAssignment().getArcmact();
            //this.LtsAccount=AccountingEJBClient.getAccount();
            //cars
//        SelectItemGroup g1 = new SelectItemGroup("German Cars");
//        g1.setSelectItems(new SelectItem[] {new SelectItem("BMW", "BMW"), new SelectItem("Mercedes", "Mercedes"), new SelectItem("Volkswagen", "Volkswagen")});
//         
//        SelectItemGroup g2 = new SelectItemGroup("American Cars");
//        g2.setSelectItems(new SelectItem[] {new SelectItem("Chrysler", "Chrysler"), new SelectItem("GM", "GM"), new SelectItem("Ford", "Ford")});
//         
//        cars = new ArrayList<SelectItem>();
//        cars.add(g1);
//        cars.add(g2);
            //cities
//        cities = new HashMap<String, String>();
//        cities.put("New York", "New York");
//        cities.put("London","London");
//        cities.put("Paris","Paris");
//        cities.put("Barcelona","Barcelona");
//        cities.put("Istanbul","Istanbul");
//        cities.put("Berlin","Berlin");
//         
//        //themes
//        themes = service.getThemes();
        } catch (Exception ex) {
            Logger.getLogger(PaymentBankBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//</editor-fold>

    public void doSave() {

//    int resp;
//    int valor=0;
//    AccPeriodTO parameters=new AccPeriodTO();
//    resp=AccountingEJBClient.cat_accPeriod_mtto(valor, usersign,MTTOINSERT );
//    
//        if (resp>0) {
//              FacesMessage msg = new FacesMessage("Registro Insertado","");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        PeriodLst=AccountingEJBClient.getAccPeriods();
//        
//        }else{
//        FacesMessage msg = new FacesMessage("No se Pudo Insertar","");
//        FacesContext.getCurrentInstance().addMessage(null, msg); 
//        }
    }
    public void doCancel() {}
    
    public void doSearch() {}
    
}//cierre de clase
