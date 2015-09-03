/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.sociedad.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.EnterpriseOutTO;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ps05393
 */
@ManagedBean(name = "sociedad")
@RequestScoped
public class SociedadBean implements Serializable {

    private static AdminEJBClient AdminEJBService = null;
    private static final long serialVersionUID = 1L;

    private int code;
    private String compnyName;
    private String compnyAddr;
    private String country_catalog;
    private String crintHeadr;
    private String phone1;
    private String phone2;
    private String fax;
    private String e_Mail;
    private String manager;
    private String taxIdNum;
    private CatalogTO catalogClassModel;
    private static final String CATALOGOCOUNTRY = "paises";
    private List<CatalogTO> catalogClassLst;
    private String name;
    private String catalogCode;
    private String description;

    public CatalogTO getCatalogClassModel() {
        return catalogClassModel;
    }

    public void setCatalogClassModel(CatalogTO catalogClassModel) {
        this.catalogClassModel = catalogClassModel;
    }

    public List<CatalogTO> getCatalogClassLst() {
        return catalogClassLst;
    }

    public void setCatalogClassLst(List<CatalogTO> catalogClassLst) {
        this.catalogClassLst = catalogClassLst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PostConstruct
    public void initForm() {

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        try {
            System.out.println("se ejecuto");
            EnterpriseTO resp = null;
            resp = AdminEJBService.getEnterpriseInfo();
		 // Aqui tengo el resultado de la informacion de la cuenta

            System.out.println(resp.getCompnyName());
            System.out.println(resp.getCompnyAddr());
            System.out.println(resp.getCountry_catalog());
            this.compnyName = resp.getCompnyName();
            this.compnyAddr = resp.getCompnyAddr();
            this.crintHeadr = resp.getCrintHeadr();
            this.e_Mail = resp.getE_Mail();
            this.fax = resp.getFax();
            this.manager = resp.getManager();
            this.phone1 = resp.getPhone1();
           // this.phone1 = "50322222222";
            this.phone2 = resp.getPhone2();
            this.taxIdNum = resp.getTaxIdNum();
            this.catalogClassLst = AdminEJBService.findCatalog(CATALOGOCOUNTRY);

                //aqui obtengo lo que es el pais en su respuesta del backend recibo "01"
            //es el codigo del pais.
            this.country_catalog = resp.getCountry_catalog();

                //necesitaria un metodo en el cual yo pudiera buscar por el nombre de la tabla
            //un codigo, para que me devuelva la descripcion
            //por ejemplo asi.
            // this.country_catalog = AdminEJBService.findCatalog(CATALOGOCOUNTRY,country_catalog);
//        this.articleClassLst = AdminEJBService.findCatalog(CATALOGOCLASES);
//        this.articleGroupLst = AdminEJBService.findCatalog(CATALOGOGROUP);
//        this.setShoppingDefaultProv((List<CatalogTO>) AdminEJBService.findCatalog(CATALOGDEFAULTPROV));
//        this.shoppMeasUnitLst=AdminEJBService.findCatalog(CATALOGSHOPPMESUNIT);
//        this.setSalesMeasUnitLst((List<CatalogTO>) AdminEJBService.findCatalog(CATALOGSALESMESUNIT));
//        this.branchArticlesLst=new Vector();
//        this.branchArticlesLst.add(new BranchArticlesTO(new BranchTO("01","Almacen-001","00","00"),"01","01",new Double(1),new Double(2),new Double(3),new Double(4),new Double(5),new Double(6),"false",false));
        } catch (Exception e) {
        }

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCompnyName() {
        return compnyName;
    }

    public void setCompnyName(String compnyName) {
        this.compnyName = compnyName;
    }

    public String getCompnyAddr() {
        return compnyAddr;
    }

    public void setCompnyAddr(String compnyAddr) {
        this.compnyAddr = compnyAddr;
    }

    public String getCountry_catalog() {
        return country_catalog;
    }

    public void setCountry_catalog(String country_catalog) {
        this.country_catalog = country_catalog;
    }

    public String getCrintHeadr() {
        return crintHeadr;
    }

    public void setCrintHeadr(String crintHeadr) {
        this.crintHeadr = crintHeadr;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getE_Mail() {
        return e_Mail;
    }

    public void setE_Mail(String e_Mail) {
        this.e_Mail = e_Mail;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTaxIdNum() {
        return taxIdNum;
    }

    public void setTaxIdNum(String taxIdNum) {
        this.taxIdNum = taxIdNum;
    }

    /**
     * Creates a new instance of sociedadBean
     */
    public SociedadBean() {
//        EnterpriseTO resp=null;
//		
//		resp=AdminEJBService.getEnterpriseInfo();
//		
//		System.out.println(resp.getCompnyName());
//		System.out.println(resp.getCompnyAddr());
//      
    }

    public String addAction() throws Exception {
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        //Sociedad so = new Sociedad(this.code, this.compnyAddr, this.compnyName, this.country_catalog, this.crintHeadr, this.e_Mail, this.fax, this.phone1, this.phone2, this.taxIdNum, this.manager);
        EnterpriseTO parameters = new EnterpriseTO();// Parametros de BE
        EnterpriseOutTO resp = null; //Respuesta de BE
        
        parameters.setCode(0);
        parameters.setCompnyAddr(this.compnyAddr);
        parameters.setCompnyName(this.compnyName);
        parameters.setCountry_catalog(this.country_catalog);
        parameters.setCrintHeadr(this.crintHeadr);
        parameters.setE_Mail(this.e_Mail);
        parameters.setFax(this.fax);
        parameters.setManager(this.manager);
        parameters.setPhone1(this.phone1);
        parameters.setPhone2(this.phone2);
        parameters.setTaxIdNum(this.taxIdNum);

        resp = AdminEJBService.saveEnterprise(parameters);
        System.out.println(resp.getRespCode());
        if (resp.getRespCode() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado!", "Correctamente!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Insert!", "Please Try Again!"));
        }

        return null;
    }
//    public String deleteAction(Periodo periodo) {
//	    
//		periodoList.remove(periodo);
//		return null;
//	}

}
