/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ri00642
 */
@ManagedBean (name="sales")
@RequestScoped
public class salesBean {

    /**
     * Creates a new instance of salesBean
     */
    public salesBean() {
    }
    private Integer docentry;
    private Integer docnum;
    private String canceled;
    private String docstatus;
    private String objtype;
    private String docdate;
    private Date docduedate;
    private String cardcode;
    private String numatcard;
    private Double doctotal;
    private String comments;
    private String jrnlmemo;
    private Integer series;
    private Date taxdate;
    private String doctype;
    private String whscode;
    private Date createddatel;
    private String createdbyl;
    private String modifiedbyl;
    private Date modifieddatel;
    private List<CatalogTO> objTypeLst;
    
    @PostConstruct
     public void initForm() {
        
         this.objTypeLst=new Vector();
        CatalogTO cat= new CatalogTO();
        cat.setCatcode("1");
        cat.setCatvalue("Factura");
        this.objTypeLst.add(cat);
        CatalogTO cat1= new CatalogTO();
        cat1.setCatcode("2");
        cat1.setCatvalue("CCF");
        this.objTypeLst.add(cat1); 
        Date date=new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.setDocdate(format.format(date));
    }
    
    
    
    /**
     * @return the docentry
     */
    public Integer getDocentry() {
        return docentry;
    }

    /**
     * @param docentry the docentry to set
     */
    public void setDocentry(Integer docentry) {
        this.docentry = docentry;
    }

    /**
     * @return the docnum
     */
    public Integer getDocnum() {
        return docnum;
    }

    /**
     * @param docnum the docnum to set
     */
    public void setDocnum(Integer docnum) {
        this.docnum = docnum;
    }

    /**
     * @return the canceled
     */
    public String getCanceled() {
        return canceled;
    }

    /**
     * @param canceled the canceled to set
     */
    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }

    /**
     * @return the docstatus
     */
    public String getDocstatus() {
        return docstatus;
    }

    /**
     * @param docstatus the docstatus to set
     */
    public void setDocstatus(String docstatus) {
        this.docstatus = docstatus;
    }

    /**
     * @return the objtype
     */
    public String getObjtype() {
        return objtype;
    }

    /**
     * @param objtype the objtype to set
     */
    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    /**
     * @return the docdate
     */
    public String getDocdate() {
        return docdate;
    }

    /**
     * @param docdate the docdate to set
     */
    public void setDocdate(String docdate) {
        this.docdate = docdate;
    }

    /**
     * @return the docduedate
     */
    public Date getDocduedate() {
        return docduedate;
    }

    /**
     * @param docduedate the docduedate to set
     */
    public void setDocduedate(Date docduedate) {
        this.docduedate = docduedate;
    }

    /**
     * @return the cardcode
     */
    public String getCardcode() {
        return cardcode;
    }

    /**
     * @param cardcode the cardcode to set
     */
    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    /**
     * @return the numatcard
     */
    public String getNumatcard() {
        return numatcard;
    }

    /**
     * @param numatcard the numatcard to set
     */
    public void setNumatcard(String numatcard) {
        this.numatcard = numatcard;
    }

    /**
     * @return the doctotal
     */
    public Double getDoctotal() {
        return doctotal;
    }

    /**
     * @param doctotal the doctotal to set
     */
    public void setDoctotal(Double doctotal) {
        this.doctotal = doctotal;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the jrnlmemo
     */
    public String getJrnlmemo() {
        return jrnlmemo;
    }

    /**
     * @param jrnlmemo the jrnlmemo to set
     */
    public void setJrnlmemo(String jrnlmemo) {
        this.jrnlmemo = jrnlmemo;
    }

    /**
     * @return the series
     */
    public Integer getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(Integer series) {
        this.series = series;
    }

    /**
     * @return the taxdate
     */
    public Date getTaxdate() {
        return taxdate;
    }

    /**
     * @param taxdate the taxdate to set
     */
    public void setTaxdate(Date taxdate) {
        this.taxdate = taxdate;
    }

    /**
     * @return the doctype
     */
    public String getDoctype() {
        return doctype;
    }

    /**
     * @param doctype the doctype to set
     */
    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    /**
     * @return the whscode
     */
    public String getWhscode() {
        return whscode;
    }

    /**
     * @param whscode the whscode to set
     */
    public void setWhscode(String whscode) {
        this.whscode = whscode;
    }

    /**
     * @return the createddatel
     */
    public Date getCreateddatel() {
        return createddatel;
    }

    /**
     * @param createddatel the createddatel to set
     */
    public void setCreateddatel(Date createddatel) {
        this.createddatel = createddatel;
    }

    /**
     * @return the createdbyl
     */
    public String getCreatedbyl() {
        return createdbyl;
    }

    /**
     * @param createdbyl the createdbyl to set
     */
    public void setCreatedbyl(String createdbyl) {
        this.createdbyl = createdbyl;
    }

    /**
     * @return the modifiedbyl
     */
    public String getModifiedbyl() {
        return modifiedbyl;
    }

    /**
     * @param modifiedbyl the modifiedbyl to set
     */
    public void setModifiedbyl(String modifiedbyl) {
        this.modifiedbyl = modifiedbyl;
    }

    /**
     * @return the modifieddatel
     */
    public Date getModifieddatel() {
        return modifieddatel;
    }

    /**
     * @param modifieddatel the modifieddatel to set
     */
    public void setModifieddatel(Date modifieddatel) {
        this.modifieddatel = modifieddatel;
    }

    /**
     * @return the objTypeLst
     */
    public List<CatalogTO> getObjTypeLst() {
        return objTypeLst;
    }

    /**
     * @param objTypeLst the objTypeLst to set
     */
    public void setObjTypeLst(List<CatalogTO> objTypeLst) {
        this.objTypeLst = objTypeLst;
    }
}
