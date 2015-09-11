/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.warehouse.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.BranchTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.catalogos.Common;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

public class WarehouseBean implements Serializable {

    public WarehouseBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Variables">
    private static final long serialVersionUID = 1L;
    private String whscode;
    private boolean whscodereq;
    private String whsname;
    private String grp_code;
    private boolean locked;
    private String street;
    private String city;
    private String country;
    private String location;
    private String usetax;
    private String balinvntac;
    private String salecostac;
    private String transferac;
    private String revenuesac;
    private String varianceac;
    private String decreasac;
    private String increasac;
    private String returnac;
    private String expensesac;
    private String frrevenuac;
    private String frexpensac;
    private String pricedifac;
    private String exchangeac;
    private String balanceacc;
    private String purchaseac;
    private String pareturnac;
    private String purchofsac;
    private String shpdgdsact;
    private String vatrevact;
    private String decresglac;
    private String incresglac;
    private String stokrvlact;
    private String stkoffsact;
    private String wipacct;
    private String wipvaracct;
    private String costrvlact;
    private String cstoffsact;
    private String expclract;
    private String expofstact;
    private String arcmact;
    private String arcmfrnact;
    private String arcmexpact;
    private String apcmact;
    private String apcmfrnact;
    private String revretact;
    private String negstckact;
    private String stkintnact;
    private String purbalact;
    private String whicenact;
    private String whocenact;
    private String excisable;
    private int usersign;
    private List LtsBranch = new Vector();

    private List<CatalogTO> LtsCatalog;
    private List<CatalogTO> LtsLocation;
    private AdminEJBClient AdminEJBService;
    private AccountingEJBClient AccountingEJBClient;
    private static final String CATALOGOCOUNTRY = "paises";
    private static final String CATALOGOLOCATION = "cg_location";
    private String catcode;
    private int tablecode;
    private String catvalue;
    private String catstatus;
    private List<AccountTO> LtsAccount;
    private List<BranchTO> LstBranch;
    private BranchTO selectUser = new BranchTO();
    private BranchTO newAlm = new BranchTO();
    private int activeTabIndex;

    /**
     * ************************************************************************
     */
    private static final String BalInvntAc = "BalInvntAc";
    private static final String SaleCostAc = "SaleCostAc";
    private static final String TransferAc = "TransferAc";
    private static final String RevenuesAc = "RevenuesAc";
    private static final String VarianceAc = "VarianceAc";
    private static final String DecreasAc = "DecreasAc ";
    private static final String IncreasAc = "IncreasAc ";
    private static final String ReturnAc = "ReturnAc  ";
    private static final String ExpensesAc = "ExpensesAc";
    private static final String FrRevenuAc = "FrRevenuAc";
    private static final String FrExpensAc = "FrExpensAc";
    private static final String PriceDifAc = "PriceDifAc";
    private static final String ExchangeAc = "ExchangeAc";
    private static final String BalanceAcc = "BalanceAcc";
    private static final String PurchaseAc = "PurchaseAc";
    private static final String PAReturnAc = "PAReturnAc";
    private static final String PurchOfsAc = "PurchOfsAc";
    private static final String ShpdGdsAct = "ShpdGdsAct";
    private static final String VatRevAct = "VatRevAct ";
    private static final String DecresGlAc = "DecresGlAc";
    private static final String IncresGlAc = "IncresGlAc";
    private static final String StokRvlAct = "StokRvlAct";
    private static final String StkOffsAct = "StkOffsAct";
    private static final String WipAcct = "WipAcct";
    private static final String WipVarAcct = "WipVarAcct";
    private static final String CostRvlAct = "CostRvlAct";
    private static final String CstOffsAct = "CstOffsAct";
    private static final String ExpClrAct = "ExpClrAct ";
    private static final String ExpOfstAct = "ExpOfstAct";
    private static final String ARCMAct = "ARCMAct   ";
    private static final String ARCMFrnAct = "ARCMFrnAct";
    private static final String ARCMExpAct = "ARCMExpAct";
    private static final String APCMAct = "APCMAct   ";
    private static final String APCMFrnAct = "APCMFrnAct";
    private static final String RevRetAct = "RevRetAct ";
    private static final String NegStckAct = "NegStckAct";
    private static final String StkInTnAct = "StkInTnAct";
    private static final String PurBalAct = "PurBalAct ";
    private static final String WhICenAct = "WhICenAct ";
    private static final String WhOCenAct = "WhOCenAct ";

    private int varEstados;
    private List listaBusqueda = new Vector();
    private ArrayList<BranchTO> listaBusquedaTable = new ArrayList<BranchTO>();
    private List<BranchTO> listaTable;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="G & S">
    /**
     * @return the whscodereq
     */
    public boolean isWhscodereq() {
        return whscodereq;
    }

    /**
     * @param whscodereq the whscodereq to set
     */
    public void setWhscodereq(boolean whscodereq) {
        this.whscodereq = whscodereq;
    }

    /**
     * @return the activeTabIndex
     */
    public int getActiveTabIndex() {
        return activeTabIndex;
    }

    /**
     * @param activeTabIndex the activeTabIndex to set
     */
    public void setActiveTabIndex(int activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public BranchTO getNewAlm() {
        return newAlm;
    }

    public void setNewAlm(BranchTO newAlm) {
        this.newAlm = newAlm;
    }

    public BranchTO getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(BranchTO selectUser) {
        this.selectUser = selectUser;
    }

    public List<BranchTO> getLstBranch() {
        return LstBranch;
    }

    public void setLstBranch(List<BranchTO> LstBranch) {
        this.LstBranch = LstBranch;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public List<BranchTO> getListaTable() {
        return listaTable;
    }

    public void setListaTable(List<BranchTO> listaTable) {
        this.listaTable = listaTable;
    }

    public ArrayList<BranchTO> getListaBusquedaTable() {
        //si lista lista esta igual a null o no inicializada
        //entonces que mande a llamar el searchuser
        if (this.listaBusquedaTable == null) {
            this.searchUser();
        }
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<BranchTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public int getVarEstados() {
        return varEstados;
    }

    public void setVarEstados(int varEstados) {
        this.varEstados = varEstados;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isReNick() {
        return reNick;
    }

    public void setReNick(boolean reNick) {
        this.reNick = reNick;
    }

    public boolean isRoNick() {
        return roNick;
    }

    public void setRoNick(boolean roNick) {
        this.roNick = roNick;
    }

    public String getExportOption() {
        return exportOption;
    }

    public void setExportOption(String exportOption) {
        this.exportOption = exportOption;
    }
    private String botonEstado;

    public String getBotonEstado() {
        return botonEstado;
    }

    public void setBotonEstado(String botonEstado) {
        this.botonEstado = botonEstado;
    }

    private boolean disable;

    private boolean reNick, roNick;

    private String exportOption;

    /**
     * ************************************************************************
     */
    public List<AccountTO> getLtsAccount() {
        return LtsAccount;
    }

    public void setLtsAccount(List<AccountTO> LtsAccount) {
        this.LtsAccount = LtsAccount;
    }

    public List<CatalogTO> getLtsLocation() {
        return LtsLocation;
    }

    public void setLtsLocation(List<CatalogTO> LtsLocation) {
        this.LtsLocation = LtsLocation;
    }

    public List<CatalogTO> getLtsCatalog() {
        return LtsCatalog;
    }

    public void setLtsCatalog(List<CatalogTO> LtsCatalog) {
        this.LtsCatalog = LtsCatalog;
    }

    public String getCatcode() {
        return catcode;
    }

    public void setCatcode(String catcode) {
        this.catcode = catcode;
    }

    public int getTablecode() {
        return tablecode;
    }

    public void setTablecode(int tablecode) {
        this.tablecode = tablecode;
    }

    public String getCatvalue() {
        return catvalue;
    }

    public void setCatvalue(String catvalue) {
        this.catvalue = catvalue;
    }

    public String getCatstatus() {
        return catstatus;
    }

    public void setCatstatus(String catstatus) {
        this.catstatus = catstatus;
    }

    public List<BranchTO> getLtsBranch() {
        return LtsBranch;
    }

    public void setLtsBranch(List<BranchTO> LtsBranch) {
        this.LtsBranch = LtsBranch;
    }

    public String getWhscode() {
        return whscode;
    }

    public void setWhscode(String whscode) {
        this.whscode = whscode;
    }

    public String getWhsname() {
        return whsname;
    }

    public void setWhsname(String whsname) {
        this.whsname = whsname;
    }

    public String getGrp_code() {
        return grp_code;
    }

    public void setGrp_code(String grp_code) {
        this.grp_code = grp_code;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsetax() {
        return usetax;
    }

    public void setUsetax(String usetax) {
        this.usetax = usetax;
    }

    public String getBalinvntac() {
        return balinvntac;
    }

    public void setBalinvntac(String balinvntac) {
        this.balinvntac = balinvntac;
    }

    public String getSalecostac() {
        return salecostac;
    }

    public void setSalecostac(String salecostac) {
        this.salecostac = salecostac;
    }

    public String getTransferac() {
        return transferac;
    }

    public void setTransferac(String transferac) {
        this.transferac = transferac;
    }

    public String getRevenuesac() {
        return revenuesac;
    }

    public void setRevenuesac(String revenuesac) {
        this.revenuesac = revenuesac;
    }

    public String getVarianceac() {
        return varianceac;
    }

    public void setVarianceac(String varianceac) {
        this.varianceac = varianceac;
    }

    public String getDecreasac() {
        return decreasac;
    }

    public void setDecreasac(String decreasac) {
        this.decreasac = decreasac;
    }

    public String getIncreasac() {
        return increasac;
    }

    public void setIncreasac(String increasac) {
        this.increasac = increasac;
    }

    public String getReturnac() {
        return returnac;
    }

    public void setReturnac(String returnac) {
        this.returnac = returnac;
    }

    public String getExpensesac() {
        return expensesac;
    }

    public void setExpensesac(String expensesac) {
        this.expensesac = expensesac;
    }

    public String getFrrevenuac() {
        return frrevenuac;
    }

    public void setFrrevenuac(String frrevenuac) {
        this.frrevenuac = frrevenuac;
    }

    public String getFrexpensac() {
        return frexpensac;
    }

    public void setFrexpensac(String frexpensac) {
        this.frexpensac = frexpensac;
    }

    public String getPricedifac() {
        return pricedifac;
    }

    public void setPricedifac(String pricedifac) {
        this.pricedifac = pricedifac;
    }

    public String getExchangeac() {
        return exchangeac;
    }

    public void setExchangeac(String exchangeac) {
        this.exchangeac = exchangeac;
    }

    public String getBalanceacc() {
        return balanceacc;
    }

    public void setBalanceacc(String balanceacc) {
        this.balanceacc = balanceacc;
    }

    public String getPurchaseac() {
        return purchaseac;
    }

    public void setPurchaseac(String purchaseac) {
        this.purchaseac = purchaseac;
    }

    public String getPareturnac() {
        return pareturnac;
    }

    public void setPareturnac(String pareturnac) {
        this.pareturnac = pareturnac;
    }

    public String getPurchofsac() {
        return purchofsac;
    }

    public void setPurchofsac(String purchofsac) {
        this.purchofsac = purchofsac;
    }

    public String getShpdgdsact() {
        return shpdgdsact;
    }

    public void setShpdgdsact(String shpdgdsact) {
        this.shpdgdsact = shpdgdsact;
    }

    public String getVatrevact() {
        return vatrevact;
    }

    public void setVatrevact(String vatrevact) {
        this.vatrevact = vatrevact;
    }

    public String getDecresglac() {
        return decresglac;
    }

    public void setDecresglac(String decresglac) {
        this.decresglac = decresglac;
    }

    public String getIncresglac() {
        return incresglac;
    }

    public void setIncresglac(String incresglac) {
        this.incresglac = incresglac;
    }

    public String getStokrvlact() {
        return stokrvlact;
    }

    public void setStokrvlact(String stokrvlact) {
        this.stokrvlact = stokrvlact;
    }

    public String getStkoffsact() {
        return stkoffsact;
    }

    public void setStkoffsact(String stkoffsact) {
        this.stkoffsact = stkoffsact;
    }

    public String getWipacct() {
        return wipacct;
    }

    public void setWipacct(String wipacct) {
        this.wipacct = wipacct;
    }

    public String getWipvaracct() {
        return wipvaracct;
    }

    public void setWipvaracct(String wipvaracct) {
        this.wipvaracct = wipvaracct;
    }

    public String getCostrvlact() {
        return costrvlact;
    }

    public void setCostrvlact(String costrvlact) {
        this.costrvlact = costrvlact;
    }

    public String getCstoffsact() {
        return cstoffsact;
    }

    public void setCstoffsact(String cstoffsact) {
        this.cstoffsact = cstoffsact;
    }

    public String getExpclract() {
        return expclract;
    }

    public void setExpclract(String expclract) {
        this.expclract = expclract;
    }

    public String getExpofstact() {
        return expofstact;
    }

    public void setExpofstact(String expofstact) {
        this.expofstact = expofstact;
    }

    public String getArcmact() {
        return arcmact;
    }

    public void setArcmact(String arcmact) {
        this.arcmact = arcmact;
    }

    public String getArcmfrnact() {
        return arcmfrnact;
    }

    public void setArcmfrnact(String arcmfrnact) {
        this.arcmfrnact = arcmfrnact;
    }

    public String getArcmexpact() {
        return arcmexpact;
    }

    public void setArcmexpact(String arcmexpact) {
        this.arcmexpact = arcmexpact;
    }

    public String getApcmact() {
        return apcmact;
    }

    public void setApcmact(String apcmact) {
        this.apcmact = apcmact;
    }

    public String getApcmfrnact() {
        return apcmfrnact;
    }

    public void setApcmfrnact(String apcmfrnact) {
        this.apcmfrnact = apcmfrnact;
    }

    public String getRevretact() {
        return revretact;
    }

    public void setRevretact(String revretact) {
        this.revretact = revretact;
    }

    public String getNegstckact() {
        return negstckact;
    }

    public void setNegstckact(String negstckact) {
        this.negstckact = negstckact;
    }

    public String getStkintnact() {
        return stkintnact;
    }

    public void setStkintnact(String stkintnact) {
        this.stkintnact = stkintnact;
    }

    public String getPurbalact() {
        return purbalact;
    }

    public void setPurbalact(String purbalact) {
        this.purbalact = purbalact;
    }

    public String getWhicenact() {
        return whicenact;
    }

    public void setWhicenact(String whicenact) {
        this.whicenact = whicenact;
    }

    public String getWhocenact() {
        return whocenact;
    }

    public void setWhocenact(String whocenact) {
        this.whocenact = whocenact;
    }

    public String getExcisable() {
        return excisable;
    }

    public void setExcisable(String excisable) {
        this.excisable = excisable;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="doNew, doUpdate, doSave">
    public void doNew(ActionEvent actionEvent) {
        cleanBean();
        estateGuardar();
        this.setActiveTabIndex(0);
    }

    public void doUpdate() {
        try {
            int resp;
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }

            BranchTO parameters = new BranchTO();

            parameters.setWhscode(whscode);
            parameters.setWhsname(whsname);
            parameters.setCity(city);
            parameters.setLocation(location);
            parameters.setLocked(locked);
            parameters.setCountry(country);
            parameters.setStreet(street);
            /*Pestaña de Finanzas*/

            parameters.setApcmact(extractAccNumber(apcmact));
            parameters.setApcmfrnact(extractAccNumber(apcmfrnact));
            parameters.setArcmact(extractAccNumber(arcmact));
            parameters.setArcmexpact(extractAccNumber(arcmexpact));
            parameters.setArcmfrnact(extractAccNumber(arcmfrnact));

            parameters.setBalanceacc(extractAccNumber(balanceacc));
            parameters.setBalinvntac(extractAccNumber(balinvntac));

            parameters.setCostrvlact(extractAccNumber(costrvlact));

            parameters.setDecreasac(extractAccNumber(decreasac));
            parameters.setDecresglac(extractAccNumber(decresglac));

            parameters.setExchangeac(extractAccNumber(exchangeac));
            parameters.setExcisable(extractAccNumber(excisable));
            parameters.setExpclract(extractAccNumber(expclract));
            parameters.setExpensesac(extractAccNumber(expensesac));
            parameters.setExpofstact(extractAccNumber(expofstact));

            parameters.setFrexpensac(extractAccNumber(frexpensac));
            parameters.setFrrevenuac(extractAccNumber(frrevenuac));

            parameters.setGrp_code(extractAccNumber(grp_code));

            parameters.setIncreasac(extractAccNumber(increasac));
            parameters.setIncresglac(extractAccNumber(incresglac));

            parameters.setNegstckact(extractAccNumber(negstckact));

            parameters.setPareturnac(extractAccNumber(pareturnac));
            parameters.setPricedifac(extractAccNumber(pricedifac));
            parameters.setPurbalact(extractAccNumber(purbalact));
            parameters.setPurchaseac(extractAccNumber(purchaseac));
            parameters.setPurchofsac(extractAccNumber(purchofsac));

            parameters.setReturnac(extractAccNumber(returnac));
            parameters.setRevenuesac(extractAccNumber(revenuesac));
            parameters.setRevretact(extractAccNumber(revretact));

            parameters.setSalecostac(extractAccNumber(salecostac));
            parameters.setShpdgdsact(extractAccNumber(shpdgdsact));
            parameters.setStkintnact(extractAccNumber(stkintnact));
            parameters.setStkoffsact(extractAccNumber(stkoffsact));
            parameters.setStokrvlact(extractAccNumber(stokrvlact));

            parameters.setTransferac(extractAccNumber(transferac));
            parameters.setUsersign(usersign);
            parameters.setUsetax(extractAccNumber(usetax));
            parameters.setWipacct(extractAccNumber(wipacct));
            parameters.setCstoffsact(extractAccNumber(cstoffsact));
            parameters.setWhicenact(extractAccNumber(whicenact));
            parameters.setWhocenact(extractAccNumber(whocenact));

            parameters.setVarianceac(extractAccNumber(varianceac));
            parameters.setVatrevact(extractAccNumber(vatrevact));
            resp = AdminEJBService.cat_branch_mtto(parameters, Common.MTTOUPDATE);
            System.out.println("Respuesta del Backend " + resp);
            if (resp == 0) {
                FacesMessage msg = new FacesMessage("Registro Actualizado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("No se Pudo Insertar", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doSave() {
        try {
            int resp;
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }

            BranchTO parameters = new BranchTO();

            parameters.setWhscode(whscode.replaceAll("\\s", ""));
            parameters.setWhsname(whsname);
            parameters.setCity(city);
            parameters.setLocation(location);
            parameters.setLocked(locked);
            parameters.setCountry(country);
            parameters.setStreet(street);
            /*Pestaña de Finanzas*/

            parameters.setApcmact(extractAccNumber(apcmact));
            parameters.setApcmfrnact(extractAccNumber(apcmfrnact));
            parameters.setArcmact(extractAccNumber(arcmact));
            parameters.setArcmexpact(extractAccNumber(arcmexpact));
            parameters.setArcmfrnact(extractAccNumber(arcmfrnact));

            parameters.setBalanceacc(extractAccNumber(balanceacc));
            parameters.setBalinvntac(extractAccNumber(balinvntac));

            parameters.setCostrvlact(extractAccNumber(costrvlact));

            parameters.setDecreasac(extractAccNumber(decreasac));
            parameters.setDecresglac(extractAccNumber(decresglac));

            parameters.setExchangeac(extractAccNumber(exchangeac));
            parameters.setExcisable(extractAccNumber(excisable));
            parameters.setExpclract(extractAccNumber(expclract));
            parameters.setExpensesac(extractAccNumber(expensesac));
            parameters.setExpofstact(extractAccNumber(expofstact));

            parameters.setFrexpensac(extractAccNumber(frexpensac));
            parameters.setFrrevenuac(extractAccNumber(frrevenuac));

            parameters.setGrp_code(extractAccNumber(grp_code));

            parameters.setIncreasac(extractAccNumber(increasac));
            parameters.setIncresglac(extractAccNumber(incresglac));

            parameters.setNegstckact(extractAccNumber(negstckact));

            parameters.setPareturnac(extractAccNumber(pareturnac));
            parameters.setPricedifac(extractAccNumber(pricedifac));
            parameters.setPurbalact(extractAccNumber(purbalact));
            parameters.setPurchaseac(extractAccNumber(purchaseac));
            parameters.setPurchofsac(extractAccNumber(purchofsac));

            parameters.setReturnac(extractAccNumber(returnac));
            parameters.setRevenuesac(extractAccNumber(revenuesac));
            parameters.setRevretact(extractAccNumber(revretact));

            parameters.setSalecostac(extractAccNumber(salecostac));
            parameters.setShpdgdsact(extractAccNumber(shpdgdsact));
            parameters.setStkintnact(extractAccNumber(stkintnact));
            parameters.setStkoffsact(extractAccNumber(stkoffsact));
            parameters.setStokrvlact(extractAccNumber(stokrvlact));

            parameters.setTransferac(extractAccNumber(transferac));
            parameters.setUsersign(usersign);
            parameters.setUsetax(extractAccNumber(usetax));
            parameters.setWipacct(extractAccNumber(wipacct));
            parameters.setCstoffsact(extractAccNumber(cstoffsact));
            parameters.setWhicenact(extractAccNumber(whicenact));
            parameters.setWhocenact(extractAccNumber(whocenact));

            parameters.setVarianceac(extractAccNumber(varianceac));
            parameters.setVatrevact(extractAccNumber(vatrevact));
            resp = AdminEJBService.cat_branch_mtto(parameters, Common.MTTOINSERT);
            if (resp == 0) {
                FacesMessage msg = new FacesMessage("Registro Insertado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("No se Pudo Insertar", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Init de pantalla">
    @PostConstruct
    public void initForm() {

        try {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            System.out.println("Invoca el Servicio de Paises" + CATALOGOCOUNTRY);
            LtsCatalog = AdminEJBService.findCatalog(CATALOGOCOUNTRY);
            System.out.println("Invoca el Servicio de Paises");
            LtsLocation = AdminEJBService.findCatalog(CATALOGOLOCATION);
            estateGuardar();

            //  this.tableCatalog = getTableCatalog();
//        this.articleClassLst = AdminEJBService.findCatalog(CATALOGOCLASES);
//        this.articleGroupLst = AdminEJBService.findCatalog(CATALOGOGROUP);
//        this.setShoppingDefaultProv((List<CatalogTO>) AdminEJBService.findCatalog(CATALOGDEFAULTPROV));
//        this.shoppMeasUnitLst=AdminEJBService.findCatalog(CATALOGSHOPPMESUNIT);
//        this.setSalesMeasUnitLst((List<CatalogTO>) AdminEJBService.findCatalog(CATALOGSALESMESUNIT));
//        this.branchArticlesLst=new Vector();
//        this.branchArticlesLst.add(new BranchArticlesTO(new BranchTO("01","Almacen-001","00","00"),"01","01",new Double(1),new Double(2),new Double(3),new Double(4),new Double(5),new Double(6),"false",false));
            //  catlgLst = AdminEJBService.getTablesCatalog();
//        catalogoClassLst = AdminEJBService.findCatalog(tableCatalog);
            System.out.println("luego de sevicio");
//		Iterator<TablesCatalogTO> iterator = catlgLst.iterator();
//		while (iterator.hasNext()) {
//			//System.out.println(iterator.next());
//			TablesCatalogTO _returnTO=(TablesCatalogTO)iterator.next();
//			System.out.println("Code: ->"+_returnTO.getCode());
//			System.out.println("Name: ->"+_returnTO.getName());
        } catch (Exception ex) {
            Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>

    public void chooseArt() {
        //   RequestContext.getCurrentInstance().openDialog("/faces/view/inventory/WareHouseSearch.xhtml");
    }

    public void onArtChosen(SelectEvent event) {
        BranchTO bra = (BranchTO) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Seleccionado", "Id:" + bra.getWhscode());

        FacesContext.getCurrentInstance().addMessage(null, message);
        this.setWhscode(bra.getWhscode());
        this.setWhsname(bra.getWhsname());
        this.setLocation(bra.getLocation());
        this.setStreet(bra.getStreet());
        this.setCountry(bra.getCountry());

        this.setApcmact(bra.getApcmact());
        this.setApcmfrnact(bra.getApcmfrnact());
        this.setArcmact(bra.getArcmact());
        this.setArcmexpact(bra.getArcmexpact());
        this.setArcmfrnact(bra.getArcmfrnact());
        //this.setSalecostac(bra.getSalecostac());
        this.setBalanceacc(bra.getBalanceacc());
        this.setBalinvntac(bra.getBalinvntac());

//        this.setArticleCode(art.getItemCode());
//        this.setDescription(art.getItemName());
        //FacesContext.getCurrentInstance()
    }

    public List<String> completeText(String query) {
        List _result = null;
        String var = null;
        List<String> results = new ArrayList<String>();
        String filterByCode = null;
        String filterByName = null;
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        try {

            if (query.matches(".*\\d+.*")) {
                //es un numero
                filterByCode = query;
            } else {
                filterByName = query;
            }
            _result = AccountingEJBClient.getAccountByFilter(filterByCode, filterByName);

            Iterator<AccountTO> iterator = _result.iterator();
            while (iterator.hasNext()) {
                AccountTO cuentas = (AccountTO) iterator.next();
                //ArticlesTO articulo = (ArticlesTO) iterator.next();
                System.out.println(cuentas.getAcctcode() + " - " + cuentas.getAcctname());
                results.add(cuentas.getAcctcode() + " - " + cuentas.getAcctname());
                //results.add(articulo.getItemName());
            }
        } catch (Exception ex) {
            Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    public void botonBuscar(ActionEvent actionEvent) {
        cleanBean();
        estateBuscar();
        this.listaBusquedaTable.clear();
        this.searchUser();
    }

    public void setToSearch(ActionEvent actionEvent) {
        cleanBean();
        estateBuscar();
        this.listaBusquedaTable.clear();
        //this.searchUser();
    }

    public void estateBuscar() {
        this.varEstados = 3;
        this.botonEstado = "Buscar";
        this.disable = true;
        this.setWhscodereq(false);
        RequestContext.getCurrentInstance().update("formAlm");

    }

    public void cleanBean() {
        //pestanna  general

        this.setLocked(false);
        this.setWhscode(null);
        this.setWhsname(null);
        this.setLocation(null);
        this.setStreet(null);
        this.setCountry(null);
        this.setCity(null);

        //pestanna finanzas
        this.setApcmact(null);
        this.setApcmfrnact(null);
        this.setArcmact(null);
        this.setArcmexpact(null);
        this.setArcmfrnact(null);

        this.setBalanceacc(null);
        this.setBalinvntac(null);

        this.setCostrvlact(null);

        this.setDecreasac(null);
        this.setDecresglac(null);

        this.setExchangeac(null);
        this.setExcisable(null);
        this.setExpclract(null);
        this.setExpensesac(null);
        this.setExpofstact(null);

        this.setFrexpensac(null);
        this.setFrrevenuac(null);

        this.setGrp_code(null);

        this.setIncreasac(null);
        this.setIncresglac(null);

        this.setNegstckact(null);

        this.setPareturnac(null);
        this.setPricedifac(null);
        this.setPurbalact(null);
        this.setPurchaseac(null);
        this.setPurchofsac(null);

        this.setReturnac(null);
        this.setRevenuesac(null);
        this.setRevretact(null);

        this.setSalecostac(null);
        this.setShpdgdsact(null);
        this.setStkintnact(null);
        this.setStkoffsact(null);
        this.setStokrvlact(null);

        this.setTransferac(null);

        this.setUsetax(null);

        this.setVarianceac(null);
        this.setVatrevact(null);
    }

    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                //showHideDialog("dlgC", 1);
                doSave();
                break;

            case 2:
                //showHideDialog("dlgC", 1);
                doUpdate();
                break;

            case 3:
                listaBusqueda.clear();
                listaBusquedaTable.clear();
                searchUser();
                break;

            default:
                break;
        }

    }

    public void showHideDialog(String name, int openClose) {

        try {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.update("dlgUser");
            if (openClose == 1) {
                rc.execute("PF('" + name + "').show();");
            }
            if (openClose == 2) {
                rc.execute("PF('" + name + "').hide();");
            }

        } catch (Exception e) {
            faceMessage(e.getMessage() + "---" + e.getCause());
        }
    }

    private void searchUser() {
        //this.LstBranch(new Vector());
        String name = null;
        String code = null;
        List resp = null;
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        if (whscode == null || whscode.isEmpty()) {
            try {

                this.setLtsBranch(AdminEJBService.getBranch(code, name));

                //  LstBranch = AdminEJBService.getBranch(code, name);
            } catch (Exception ex) {
                Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
                faceMessage("Error al realizar busqueda");
            }
            faceMessage("Seleccione un elemento");
            for (Object alma : LtsBranch) {
                if (alma != null) {
                    BranchTO var = (BranchTO) alma;
                    this.listaBusquedaTable.add(var);
                }
                //this.listaTable.add(var);
            }
            RequestContext.getCurrentInstance().update("frmSearch");
            showHideDialog("dlgU", 1);
        } else {
            try {
                BranchTO var2 = AdminEJBService.getBranchByKey(whscode);
                if (var2.getWhscode().isEmpty()) {
                    faceMessage("No se Encontro Almacen");
                } else {
                    llenarPantallaAlmacen(var2);
                    estateActualizar();
                }
            } catch (Exception ex) {
                Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
                faceMessage("No se Encontro Almacen");
            }
        }
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public void selectDialog() {
        showHideDialog("dlgU", 2);
        BranchTO var = (BranchTO) selectUser;
        try {
            newAlm = AdminEJBService.getBranchByKey(var.getWhscode());
            llenarPantallaAlmacen(newAlm);
            estateActualizar();
        } catch (Exception ex) {

        }
        listaBusqueda.clear();
        RequestContext.getCurrentInstance().update("formAlm");

    }

    public String construyeCuenta(String _accountCode) throws Exception {
        String _return = null;
        AccountTO _accountTO;
        if (_accountCode != null) {
            _accountTO = AccountingEJBClient.getAccountByKey(_accountCode);
            _return = _accountTO.getAcctcode()
                    + " - " + _accountTO.getAcctname();

        } else {
            _return = null;
        }
        return _return;
    }

    public void llenarPantallaAlmacen(BranchTO bra) throws Exception {
        String _account = null;
        AccountTO _accountTO = null;
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        //pestanna  general
        this.setLocked(bra.isLocked());
        this.setWhscode(bra.getWhscode());
        this.setWhsname(bra.getWhsname());
        this.setLocation(bra.getLocation());
        this.setStreet(bra.getStreet());
        this.setCountry(bra.getCountry());
        this.setCity(bra.getCity());

        //pestanna finanzas
        this.setApcmact(construyeCuenta(bra.getApcmact()));
        this.setBalinvntac(construyeCuenta(bra.getBalinvntac()));
        this.setApcmfrnact(construyeCuenta(bra.getApcmfrnact()));
        this.setArcmact(construyeCuenta(bra.getArcmact()));
        this.setArcmexpact(construyeCuenta(bra.getArcmexpact()));
        this.setArcmfrnact(construyeCuenta(bra.getArcmfrnact()));
        this.setBalanceacc(construyeCuenta(bra.getBalanceacc()));
        this.setCostrvlact(construyeCuenta(bra.getCostrvlact()));
        this.setDecreasac(construyeCuenta(bra.getDecreasac()));
        this.setDecresglac(construyeCuenta(bra.getDecresglac()));
        this.setExchangeac(construyeCuenta(bra.getExchangeac()));
        this.setExcisable(construyeCuenta(bra.getExcisable()));
        this.setExpclract(construyeCuenta(bra.getExpclract()));
        this.setExpensesac(construyeCuenta(bra.getExpensesac()));
        this.setExpofstact(construyeCuenta(bra.getExpofstact()));
        this.setFrexpensac(construyeCuenta(bra.getFrexpensac()));
        this.setFrrevenuac(construyeCuenta(bra.getFrrevenuac()));
        this.setGrp_code(construyeCuenta(bra.getGrp_code()));
        this.setIncreasac(construyeCuenta(bra.getIncreasac()));
        this.setIncresglac(construyeCuenta(bra.getIncresglac()));
        this.setNegstckact(construyeCuenta(bra.getNegstckact()));
        this.setPareturnac(construyeCuenta(bra.getPareturnac()));
        this.setPricedifac(construyeCuenta(bra.getPricedifac()));
        this.setPurbalact(construyeCuenta(bra.getPurbalact()));
        this.setPurchaseac(construyeCuenta(bra.getPurchaseac()));
        this.setPurchofsac(construyeCuenta(bra.getPurchofsac()));
        this.setReturnac(construyeCuenta(bra.getReturnac()));
        this.setRevenuesac(construyeCuenta(bra.getRevenuesac()));
        this.setRevretact(construyeCuenta(bra.getRevretact()));
        this.setSalecostac(construyeCuenta(bra.getSalecostac()));
        this.setShpdgdsact(construyeCuenta(bra.getShpdgdsact()));
        this.setStkintnact(construyeCuenta(bra.getStkintnact()));
        this.setStkoffsact(construyeCuenta(bra.getStkoffsact()));
        this.setStokrvlact(construyeCuenta(bra.getStokrvlact()));
        this.setTransferac(construyeCuenta(bra.getTransferac()));
        this.setUsetax(construyeCuenta(bra.getUsetax()));
        this.setVarianceac(construyeCuenta(bra.getVarianceac()));
        this.setVatrevact(construyeCuenta(bra.getVatrevact()));

    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";
        disable = false;
//        reNick = false;
//        roNick = true;
        RequestContext.getCurrentInstance().update("formAlm");

    }

    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1
        this.botonEstado = "Guardar";
        disable = false;
        reNick = true;
        roNick = false;
        this.setWhscodereq(true);
        this.setActiveTabIndex(0);
        RequestContext.getCurrentInstance().update("formAlm");

    }

    public String extractAccNumber(String _input) {
        String _result = null;
        if (_input != null && _input.length() > 0) {
            if (_input.indexOf("-") > 0) {
                _result = _input.substring(0, _input.indexOf("-") - 1);
            } else {
                if (_input == null || _input.length() == 0) {
                    _result = " ";
                } else {
                    _result = _input;
                }

            }
        } else {
            _result = _input;
        }
        //this.stkintnact.substring(0,stkintnact.indexOf("-")-1)   
        return _result;

    }

}//cierre de clase
