/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.sociedad.model;

import java.io.Serializable;
/**
 *
 * @author ps05393
 */
public class Sociedad implements Serializable{
        private int code;
	private String compnyName;
	private String compnyAddr;
	private String   country_catalog;
	private String   crintHeadr;
	private String   phone1;
	private String   phone2;
	private String   fax;
	private String   e_Mail;
	private String   manager;
	private String   taxIdNum;

    public Sociedad(int code, String compnyAddr, String compnyName, String country_catalog, String crintHeadr, String e_Mail, String fax, String phone1, String phone2, String taxIdNum,String manager) {
        this.code=code;
        this.compnyAddr=compnyAddr;
        this.compnyName=compnyName;
        this.country_catalog=country_catalog;
        this.crintHeadr=crintHeadr;
        this.phone1=phone1;
        this.phone2=phone2;
        this.fax=fax;
        this.e_Mail=e_Mail;
        this.manager=manager;
        this.taxIdNum=taxIdNum;
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
        
    
}
