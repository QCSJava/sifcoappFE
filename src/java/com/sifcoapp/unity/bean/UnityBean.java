/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.unity.bean;

import com.sifco.sales.bean.SalesBean;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.client.UnityEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.UnityTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "unityBean")
@SessionScoped
public class UnityBean implements Serializable {

    public UnityBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    //variables de pantalla
    private String code;
    private String record;
    private String license;
    private String card;
    private String driver;
    private String cardcode;
    private String cardname;
    private String notes;
    private String type;
    private String status = "Y";
    private int year;
    private String brand;
    private Date duedate = new Date();

    private String dflaccount;
    private String relatedacc1;
    private String relatedacc2;
    private String relatedacc3;
    private String relatedacc4;

    private String dflaccountN;
    private String relatedacc1N;
    private String relatedacc2N;
    private String relatedacc3N;
    private String relatedacc4N;

    private Date purchasedate = new Date();
    private int usersign;

    //__________________________________________________________________________
    //servicios
    private static AccountingEJBClient AccountingEJBClient;
    private static CatalogEJBClient CatalogEJB;
    private static UnityEJBClient UnityEJBClient;
    //__________________________________________________________________________
    //nueva unidad
    UnityTO newUnity = new UnityTO();
    
    //__________________________________________________________________________
    //Estados
    private int varEstados;
    private String botonEstado;
    
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Load de pantalla">
    @PostConstruct
    public void initForm() {
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }
        if (UnityEJBClient == null) {
            UnityEJBClient = new UnityEJBClient();
        }
        
        //estado
        estateGuardar();
        
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="select Cuentas" > 
    public void findAcc1(SelectEvent event) {

        String var = null;
        if (!event.getObject().toString().equals(var)) {
            List _result = null;
            //String accCode = event.getObject().toString();
            try {

                _result = AccountingEJBClient.getAccountByFilter(dflaccount, dflaccountN);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
            }
            if(_result == null || _result.isEmpty()) {
                this.dflaccount = null;
                this.dflaccountN = null;
            } else {

                if (_result.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) _result.get(0);

                        this.dflaccount = art.getAcctcode();
                        this.dflaccountN = art.getAcctname();
                    } catch (Exception ex) {
                    }
                } else {
                    for (Object ac : _result) {
                        AccountTO art = (AccountTO) ac;
                        if (dflaccountN.equals(art.getAcctname())) {
                            dflaccount = art.getAcctcode();
                            dflaccountN = art.getAcctname();
                            break;
                        }
                    }
                }
            }
        }//if principal
    }//cierre funcion

    public void findAcc2(SelectEvent event) {

        String var = null;
        if (!event.getObject().toString().equals(var)) {
            List _result = null;
            //String accCode = event.getObject().toString();
            try {

                _result = AccountingEJBClient.getAccountByFilter(relatedacc1, relatedacc1N);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
            }
            if (_result == null || _result.isEmpty()) {
                this.relatedacc1 = null;
                this.relatedacc1N = null;
            } else {

                if (_result.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) _result.get(0);

                        this.relatedacc1 = art.getAcctcode();
                        this.relatedacc1N = art.getAcctname();
                    } catch (Exception ex) {
                    }
                } else {
                    for (Object ac : _result) {
                        AccountTO art = (AccountTO) ac;
                        if (relatedacc1N.equals(art.getAcctname())) {
                            relatedacc1 = art.getAcctcode();
                            relatedacc1N = art.getAcctname();
                            break;
                        }
                    }
                }
            }
        }//if principal
    }//cierre funcion

    public void findAcc3(SelectEvent event) {

        String var = null;
        if (!event.getObject().toString().equals(var)) {
            List _result = null;
            //String accCode = event.getObject().toString();
            try {

                _result = AccountingEJBClient.getAccountByFilter(relatedacc2, relatedacc2N);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
            }
            if (_result == null || _result.isEmpty()) {
                this.relatedacc2 = null;
                this.relatedacc2N = null;
            } else {

                if (_result.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) _result.get(0);

                        this.relatedacc2 = art.getAcctcode();
                        this.relatedacc2N = art.getAcctname();
                    } catch (Exception ex) {
                    }
                } else {
                    for (Object ac : _result) {
                        AccountTO art = (AccountTO) ac;
                        if (relatedacc2N.equals(art.getAcctname())) {
                            relatedacc2 = art.getAcctcode();
                            relatedacc2N = art.getAcctname();
                            break;
                        }
                    }
                }
            }
        }//if principal
    }//cierre funcion

    public void findAcc4(SelectEvent event) {

        String var = null;
        if (!event.getObject().toString().equals(var)) {
            List _result = null;
            //String accCode = event.getObject().toString();
            try {

                _result = AccountingEJBClient.getAccountByFilter(relatedacc3, relatedacc3N);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
            }
            if (_result == null || _result.isEmpty()) {
                this.relatedacc3 = null;
                this.relatedacc3N = null;
            } else {

                if (_result.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) _result.get(0);

                        this.relatedacc3 = art.getAcctcode();
                        this.relatedacc3N = art.getAcctname();
                    } catch (Exception ex) {
                    }
                } else {
                    for (Object ac : _result) {
                        AccountTO art = (AccountTO) ac;
                        if (relatedacc3N.equals(art.getAcctname())) {
                            relatedacc3 = art.getAcctcode();
                            relatedacc3N = art.getAcctname();
                            break;
                        }
                    }
                }
            }
        }//if principal
    }//cierre funcion

    public void findAcc5(SelectEvent event) {

        String var = null;
        if (!event.getObject().toString().equals(var)) {
            List _result = null;
            //String accCode = event.getObject().toString();
            try {

                _result = AccountingEJBClient.getAccountByFilter(relatedacc4, relatedacc4N);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
            }
            if (_result == null || _result.isEmpty()) {
                this.relatedacc4 = null;
                this.relatedacc4N = null;
            } else {

                if (_result.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) _result.get(0);

                        this.relatedacc4 = art.getAcctcode();
                        this.relatedacc4N = art.getAcctname();
                    } catch (Exception ex) {
                    }
                } else {
                    for (Object ac : _result) {
                        AccountTO art = (AccountTO) ac;
                        if (relatedacc4N.equals(art.getAcctname())) {
                            relatedacc4 = art.getAcctcode();
                            relatedacc4N = art.getAcctname();
                            break;
                        }
                    }
                }
            }
        }//if principal
    }//cierre funcion

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Seleccionar de autocomplete de Socio, Name o Cod">
    public void selectSocio(SelectEvent event) {
        String var = null;
        if (!event.getObject().toString().equals(var)) {
            List _result = new Vector();

            BusinesspartnerInTO in = new BusinesspartnerInTO();
            in.setCardcode(this.cardcode);
            in.setCardname(this.cardname);

            try {
                _result = CatalogEJB.get_businesspartner(in);

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
                cardcode = null;
                cardname = null;
            }
            if (_result.size() == 0) {
                this.cardcode = null;
                this.cardname = null;

            } else {

                if (_result.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        BusinesspartnerTO art = (BusinesspartnerTO) _result.get(0);

                        cardcode = art.getCardcode();
                        cardname = art.getCardname();

                    } catch (Exception ex) {
                        Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    for (Object ac : _result) {
                        BusinesspartnerTO art = (BusinesspartnerTO) ac;
                        if (cardname.equals(art.getCardname())) {
                            cardcode = art.getCardcode();
                            cardname = art.getCardname();
                            break;
                        }
                    }
                }
            }
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Guardar en base">
    public void doSave() {
        faceMessage("entro");
        newUnity.setCode(code);
        newUnity.setRecord(record);
        newUnity.setLicense(license);
        newUnity.setCard(card);
        newUnity.setDriver(driver);
        newUnity.setCardcode(cardcode);
        newUnity.setCardname(cardname);
        newUnity.setNotes(notes);
        newUnity.setType(type);
        newUnity.setStatus(status);
        newUnity.setYear(year);
        newUnity.setBrand(brand);
        newUnity.setDuedate(duedate);
        newUnity.setPurchasedate(purchasedate);

        newUnity.setDflaccount(dflaccount);
        newUnity.setRelatedacc1(relatedacc1);
        newUnity.setRelatedacc2(relatedacc2);
        newUnity.setRelatedacc3(relatedacc3);
        newUnity.setRelatedacc4(relatedacc4);

        newUnity.setUsersign(usersign);

        ResultOutTO _res = null;
        try {
            _res = UnityEJBClient.Unity_mtto(newUnity, 1);
            faceMessage(_res.getMensaje());
            if (_res.getCodigoError() == 0) {
                faceMessage(_res.getMensaje());
            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception e) {
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";

        
        RequestContext.getCurrentInstance().update("frmSales");

    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";

        //RequestContext.getCurrentInstance().update("frmSales");
        try {
            reload();
        } catch (IOException ex) {
        }
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";
        
        RequestContext.getCurrentInstance().update("frmSales");
    }
    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Botones Toolbar">
    public void doNew(ActionEvent actionEvent) {
        cleanBean(1);

    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    
    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    private void cleanBean(int tipo) {
            this.code = null;
            this.record = null;
            this.license = null;
            this.card = null;
            this.driver = null;
            this.cardcode = null;
            this.cardname = null;
            this.notes = null;
            this.type = null;
            this.status = "Y";
            this.year = 0;
            this.brand = null;

            if (tipo == 1) {
                this.duedate = new Date();
                this.purchasedate = new Date();
            } else {
                Date var = null;
                this.duedate = var;
                this.purchasedate = var;
            }

            this.dflaccount = null;
            this.relatedacc1 = null;
            this.relatedacc2 = null;
            this.relatedacc3 = null;
            this.relatedacc4 = null;

            this.dflaccountN = null;
            this.relatedacc1N = null;
            this.relatedacc2N = null;
            this.relatedacc3N = null;
            this.relatedacc4N = null;
    }
    
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G &  S">

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        UnityBean.CatalogEJB = CatalogEJB;
    }

    public static UnityEJBClient getUnityEJBClient() {
        return UnityEJBClient;
    }

    public static void setUnityEJBClient(UnityEJBClient UnityEJBClient) {
        UnityBean.UnityEJBClient = UnityEJBClient;
    }

    public UnityTO getNewUnity() {
        return newUnity;
    }

    public void setNewUnity(UnityTO newUnity) {
        this.newUnity = newUnity;
    }

    public int getVarEstados() {
        return varEstados;
    }

    public void setVarEstados(int varEstados) {
        this.varEstados = varEstados;
    }

    public String getBotonEstado() {
        return botonEstado;
    }

    public void setBotonEstado(String botonEstado) {
        this.botonEstado = botonEstado;
    }
    
    
    
    public static AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public static void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        UnityBean.AccountingEJBClient = AccountingEJBClient;
    }

    public String getDflaccountN() {
        return dflaccountN;
    }

    public void setDflaccountN(String dflaccountN) {
        this.dflaccountN = dflaccountN;
    }

    public String getRelatedacc1N() {
        return relatedacc1N;
    }

    public void setRelatedacc1N(String relatedacc1N) {
        this.relatedacc1N = relatedacc1N;
    }

    public String getRelatedacc2N() {
        return relatedacc2N;
    }

    public void setRelatedacc2N(String relatedacc2N) {
        this.relatedacc2N = relatedacc2N;
    }

    public String getRelatedacc3N() {
        return relatedacc3N;
    }

    public void setRelatedacc3N(String relatedacc3N) {
        this.relatedacc3N = relatedacc3N;
    }

    public String getRelatedacc4N() {
        return relatedacc4N;
    }

    public void setRelatedacc4N(String relatedacc4N) {
        this.relatedacc4N = relatedacc4N;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public String getDflaccount() {
        return dflaccount;
    }

    public void setDflaccount(String dflaccount) {
        this.dflaccount = dflaccount;
    }

    public String getRelatedacc1() {
        return relatedacc1;
    }

    public void setRelatedacc1(String relatedacc1) {
        this.relatedacc1 = relatedacc1;
    }

    public String getRelatedacc2() {
        return relatedacc2;
    }

    public void setRelatedacc2(String relatedacc2) {
        this.relatedacc2 = relatedacc2;
    }

    public String getRelatedacc3() {
        return relatedacc3;
    }

    public void setRelatedacc3(String relatedacc3) {
        this.relatedacc3 = relatedacc3;
    }

    public String getRelatedacc4() {
        return relatedacc4;
    }

    public void setRelatedacc4(String relatedacc4) {
        this.relatedacc4 = relatedacc4;
    }

    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }

//</editor-fold>

}//cierre de clase
