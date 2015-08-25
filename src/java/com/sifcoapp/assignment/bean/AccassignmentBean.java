/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.assignment.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccassignmentTO;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


@ManagedBean(name = "Accassignment")
@ViewScoped
public class AccassignmentBean implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    public AccassignmentBean() {
    }
    private static final long serialVersionUID = 1L;
    private int absentry;
    private String periodcat;
    private Date financyear;
    private int year;
    private String periodname;
    private String subtype;
    private int periodnum;
    private Date f_refdate;
    private Date t_refdate;
    private Date f_duedate;
    private Date t_duedate;
    private Date f_taxdate;
    private Date t_taxdate;
    private String linkact_1;
    private String linkact_2;
    private String linkact_3;
    private String linkact_4;
    private String linkact_5;
    private String linkact_6;
    private String linkact_8;
    private String linkact_9;
    private String linkact_10;
    private String linkact_11;
    private String linkact_12;
    private String linkact_13;
    private String linkact_14;
    private String linkact_15;
    private String linkact_16;
    private String linkact_17;
    private String linkact_18;
    private String dfltincom;
    private String exmptincom;
    private String dfltexpn;
    private String forgnincm;
    private String ecincome;
    private String forgnexpn;
    private String dfltratedi;
    private String decresglac;
    private String linkact_27;
    private String dftstockob;
    private String linkact_19;
    private String linkact_20;
    private String linkact_21;
    private String linkact_22;
    private String linkact_23;
    private String linkact_24;
    private String linkact_25;
    private String linkact_26;
    private String incresglac;
    private String rturnngact;
    private String cogm_act;
    private String aloccstact;
    private String variancact;
    private String pricdifact;
    private String cdownpymnt;
    private String vdownpymnt;
    private String cboercvble;
    private String cboeonclct;
    private String cboepresnt;
    private String cboediscnt;
    private String cunpaidboe;
    private String vboepayble;
    private String vasstboepy;
    private String copendebts;
    private String vopendebts;
    private String purchseact;
    private String pareturnac;
    private String paoffsetac;
    private String linkact_28;
    private String exdiffact;
    private String balanceact;
    private String bnkchgact;
    private String linkact_29;
    private String linkact_30;
    private String incmacct;
    private String expnacct;
    private String vatrevact;
    private String expclract;
    private String expofstact;
    private String costrevact;
    private String repomoact;
    private String wipvaracct;
    private String salevatoff;
    private String purcvatoff;
    private String dpmsalact;
    private String dpmpuract;
    private String expvaract;
    private String costoffact;
    private String ecexepnses;
    private String stockact;
    private String dflinprcss;
    private String dfltincstm;
    private String dfltprofit;
    private String dfltloss;
    private String vassets;
    private String stockrvact;
    private String stkrvofact;
    private String wipacct;
    private String dfltcard;
    private String shpdgdsact;
    private String glrvoffact;
    private String overpayap;
    private String undrpayap;
    private String overpayar;
    private String undrpayar;
    private String arcmact;
    private String arcmexpact;
    private String arcmfrnact;
    private String arcmeuact;
    private String apcmact;
    private String apcmfrnact;
    private String apcmeuact;
    private String negstckact;
    private String stkintnact;
    private String glgainxdif;
    private String gllossxdif;
    private String amountdiff;
    private String slfinvincm;
    private String slfinvexpn;
    private String onholdact;
    private String plaact;
    private String icclract;
    private String occlract;
    private String purbalact;
    private String whicenact;
    private String whocenact;
    private String saldpmint;
    private String purdpmint;
    private String exrateondt;
    private String eurecvact;
    private String eupayact;
    private String wipoffset;
    private String stockoffst;
    private String dunintrst;
    private String dunfee;
    private String tdsinterst;
    private String tdscharges;
    private String sdfltwt;
    private String pdfltwt;
    private boolean shandlewt;
    private boolean phandlewt;
    private int usersign;
    private List<CatalogTO> LtsAcca;
    private AccountingEJBClient AccountingEJBClient;
    private AdminEJBClient AdminEJBClient;
    private List<AccountTO> LtsAccount;
    private AccountTO Account;
    private List<CatalogTO> LtsCatalog;
    private String compenAcc;
    private String ctaProvAn;
    private String crimp; //codigo de retencion de impuesto
    private String cctCredit;//Comision de cobro de tarjeta de credito
    private String purMerca;//Cuenta de costos de mercancías compradas
    private static final String CATALOGIVA = "cg_ivaclass";

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    public String getPurMerca() {
        return purMerca;
    }

    public void setPurMerca(String purMerca) {
        this.purMerca = purMerca;
    }

    public String getCctCredit() {
        return cctCredit;
    }

    public void setCctCredit(String cctCredit) {
        this.cctCredit = cctCredit;
    }

    public String getCrimp() {
        return crimp;
    }

    public void setCrimp(String crimp) {
        this.crimp = crimp;
    }

    public String getCtaProvAn() {
        return ctaProvAn;
    }

    public void setCtaProvAn(String ctaProvAn) {
        this.ctaProvAn = ctaProvAn;
    }

    public String getCompenAcc() {
        return compenAcc;
    }

    public void setCompenAcc(String compenAcc) {
        this.compenAcc = compenAcc;
    }

    public List<CatalogTO> getLtsCatalog() {
        return LtsCatalog;
    }

    public void setLtsCatalog(List<CatalogTO> LtsCatalog) {
        this.LtsCatalog = LtsCatalog;
    }

    public AccountTO getAccount() {
        return Account;
    }

    public void setAccount(AccountTO Account) {
        this.Account = Account;
    }

    public List<AccountTO> getLtsAccount() {
        return LtsAccount;
    }

    public void setLtsAccount(List<AccountTO> LtsAccount) {
        this.LtsAccount = LtsAccount;
    }

    public List<CatalogTO> getLtsAcca() {
        return LtsAcca;
    }

    public void setLtsAcca(List<CatalogTO> LtsAcca) {
        this.LtsAcca = LtsAcca;
    }

    public int getAbsentry() {
        return absentry;
    }

    public void setAbsentry(int absentry) {
        this.absentry = absentry;
    }

    public String getPeriodcat() {
        return periodcat;
    }

    public void setPeriodcat(String periodcat) {
        this.periodcat = periodcat;
    }

    public Date getFinancyear() {
        return financyear;
    }

    public void setFinancyear(Date financyear) {
        this.financyear = financyear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPeriodname() {
        return periodname;
    }

    public void setPeriodname(String periodname) {
        this.periodname = periodname;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getPeriodnum() {
        return periodnum;
    }

    public void setPeriodnum(int periodnum) {
        this.periodnum = periodnum;
    }

    public Date getF_refdate() {
        return f_refdate;
    }

    public void setF_refdate(Date f_refdate) {
        this.f_refdate = f_refdate;
    }

    public Date getT_refdate() {
        return t_refdate;
    }

    public void setT_refdate(Date t_refdate) {
        this.t_refdate = t_refdate;
    }

    public Date getF_duedate() {
        return f_duedate;
    }

    public void setF_duedate(Date f_duedate) {
        this.f_duedate = f_duedate;
    }

    public Date getT_duedate() {
        return t_duedate;
    }

    public void setT_duedate(Date t_duedate) {
        this.t_duedate = t_duedate;
    }

    public Date getF_taxdate() {
        return f_taxdate;
    }

    public void setF_taxdate(Date f_taxdate) {
        this.f_taxdate = f_taxdate;
    }

    public Date getT_taxdate() {
        return t_taxdate;
    }

    public void setT_taxdate(Date t_taxdate) {
        this.t_taxdate = t_taxdate;
    }

    public String getLinkact_1() {
        return linkact_1;
    }

    public void setLinkact_1(String linkact_1) {
        this.linkact_1 = linkact_1;
    }

    public String getLinkact_2() {
        return linkact_2;
    }

    public void setLinkact_2(String linkact_2) {
        this.linkact_2 = linkact_2;
    }

    public String getLinkact_3() {
        return linkact_3;
    }

    public void setLinkact_3(String linkact_3) {
        this.linkact_3 = linkact_3;
    }

    public String getLinkact_4() {
        return linkact_4;
    }

    public void setLinkact_4(String linkact_4) {
        this.linkact_4 = linkact_4;
    }

    public String getLinkact_5() {
        return linkact_5;
    }

    public void setLinkact_5(String linkact_5) {
        this.linkact_5 = linkact_5;
    }

    public String getLinkact_6() {
        return linkact_6;
    }

    public void setLinkact_6(String linkact_6) {
        this.linkact_6 = linkact_6;
    }

    public String getLinkact_8() {
        return linkact_8;
    }

    public void setLinkact_8(String linkact_8) {
        this.linkact_8 = linkact_8;
    }

    public String getLinkact_9() {
        return linkact_9;
    }

    public void setLinkact_9(String linkact_9) {
        this.linkact_9 = linkact_9;
    }

    public String getLinkact_10() {
        return linkact_10;
    }

    public void setLinkact_10(String linkact_10) {
        this.linkact_10 = linkact_10;
    }

    public String getLinkact_11() {
        return linkact_11;
    }

    public void setLinkact_11(String linkact_11) {
        this.linkact_11 = linkact_11;
    }

    public String getLinkact_12() {
        return linkact_12;
    }

    public void setLinkact_12(String linkact_12) {
        this.linkact_12 = linkact_12;
    }

    public String getLinkact_13() {
        return linkact_13;
    }

    public void setLinkact_13(String linkact_13) {
        this.linkact_13 = linkact_13;
    }

    public String getLinkact_14() {
        return linkact_14;
    }

    public void setLinkact_14(String linkact_14) {
        this.linkact_14 = linkact_14;
    }

    public String getLinkact_15() {
        return linkact_15;
    }

    public void setLinkact_15(String linkact_15) {
        this.linkact_15 = linkact_15;
    }

    public String getLinkact_16() {
        return linkact_16;
    }

    public void setLinkact_16(String linkact_16) {
        this.linkact_16 = linkact_16;
    }

    public String getLinkact_17() {
        return linkact_17;
    }

    public void setLinkact_17(String linkact_17) {
        this.linkact_17 = linkact_17;
    }

    public String getLinkact_18() {
        return linkact_18;
    }

    public void setLinkact_18(String linkact_18) {
        this.linkact_18 = linkact_18;
    }

    public String getDfltincom() {
        return dfltincom;
    }

    public void setDfltincom(String dfltincom) {
        this.dfltincom = dfltincom;
    }

    public String getExmptincom() {
        return exmptincom;
    }

    public void setExmptincom(String exmptincom) {
        this.exmptincom = exmptincom;
    }

    public String getDfltexpn() {
        return dfltexpn;
    }

    public void setDfltexpn(String dfltexpn) {
        this.dfltexpn = dfltexpn;
    }

    public String getForgnincm() {
        return forgnincm;
    }

    public void setForgnincm(String forgnincm) {
        this.forgnincm = forgnincm;
    }

    public String getEcincome() {
        return ecincome;
    }

    public void setEcincome(String ecincome) {
        this.ecincome = ecincome;
    }

    public String getForgnexpn() {
        return forgnexpn;
    }

    public void setForgnexpn(String forgnexpn) {
        this.forgnexpn = forgnexpn;
    }

    public String getDfltratedi() {
        return dfltratedi;
    }

    public void setDfltratedi(String dfltratedi) {
        this.dfltratedi = dfltratedi;
    }

    public String getDecresglac() {
        return decresglac;
    }

    public void setDecresglac(String decresglac) {
        this.decresglac = decresglac;
    }

    public String getLinkact_27() {
        return linkact_27;
    }

    public void setLinkact_27(String linkact_27) {
        this.linkact_27 = linkact_27;
    }

    public String getDftstockob() {
        return dftstockob;
    }

    public void setDftstockob(String dftstockob) {
        this.dftstockob = dftstockob;
    }

    public String getLinkact_19() {
        return linkact_19;
    }

    public void setLinkact_19(String linkact_19) {
        this.linkact_19 = linkact_19;
    }

    public String getLinkact_20() {
        return linkact_20;
    }

    public void setLinkact_20(String linkact_20) {
        this.linkact_20 = linkact_20;
    }

    public String getLinkact_21() {
        return linkact_21;
    }

    public void setLinkact_21(String linkact_21) {
        this.linkact_21 = linkact_21;
    }

    public String getLinkact_22() {
        return linkact_22;
    }

    public void setLinkact_22(String linkact_22) {
        this.linkact_22 = linkact_22;
    }

    public String getLinkact_23() {
        return linkact_23;
    }

    public void setLinkact_23(String linkact_23) {
        this.linkact_23 = linkact_23;
    }

    public String getLinkact_24() {
        return linkact_24;
    }

    public void setLinkact_24(String linkact_24) {
        this.linkact_24 = linkact_24;
    }

    public String getLinkact_25() {
        return linkact_25;
    }

    public void setLinkact_25(String linkact_25) {
        this.linkact_25 = linkact_25;
    }

    public String getLinkact_26() {
        return linkact_26;
    }

    public void setLinkact_26(String linkact_26) {
        this.linkact_26 = linkact_26;
    }

    public String getIncresglac() {
        return incresglac;
    }

    public void setIncresglac(String incresglac) {
        this.incresglac = incresglac;
    }

    public String getRturnngact() {
        return rturnngact;
    }

    public void setRturnngact(String rturnngact) {
        this.rturnngact = rturnngact;
    }

    public String getCogm_act() {
        return cogm_act;
    }

    public void setCogm_act(String cogm_act) {
        this.cogm_act = cogm_act;
    }

    public String getAloccstact() {
        return aloccstact;
    }

    public void setAloccstact(String aloccstact) {
        this.aloccstact = aloccstact;
    }

    public String getVariancact() {
        return variancact;
    }

    public void setVariancact(String variancact) {
        this.variancact = variancact;
    }

    public String getPricdifact() {
        return pricdifact;
    }

    public void setPricdifact(String pricdifact) {
        this.pricdifact = pricdifact;
    }

    public String getCdownpymnt() {
        return cdownpymnt;
    }

    public void setCdownpymnt(String cdownpymnt) {
        this.cdownpymnt = cdownpymnt;
    }

    public String getVdownpymnt() {
        return vdownpymnt;
    }

    public void setVdownpymnt(String vdownpymnt) {
        this.vdownpymnt = vdownpymnt;
    }

    public String getCboercvble() {
        return cboercvble;
    }

    public void setCboercvble(String cboercvble) {
        this.cboercvble = cboercvble;
    }

    public String getCboeonclct() {
        return cboeonclct;
    }

    public void setCboeonclct(String cboeonclct) {
        this.cboeonclct = cboeonclct;
    }

    public String getCboepresnt() {
        return cboepresnt;
    }

    public void setCboepresnt(String cboepresnt) {
        this.cboepresnt = cboepresnt;
    }

    public String getCboediscnt() {
        return cboediscnt;
    }

    public void setCboediscnt(String cboediscnt) {
        this.cboediscnt = cboediscnt;
    }

    public String getCunpaidboe() {
        return cunpaidboe;
    }

    public void setCunpaidboe(String cunpaidboe) {
        this.cunpaidboe = cunpaidboe;
    }

    public String getVboepayble() {
        return vboepayble;
    }

    public void setVboepayble(String vboepayble) {
        this.vboepayble = vboepayble;
    }

    public String getVasstboepy() {
        return vasstboepy;
    }

    public void setVasstboepy(String vasstboepy) {
        this.vasstboepy = vasstboepy;
    }

    public String getCopendebts() {
        return copendebts;
    }

    public void setCopendebts(String copendebts) {
        this.copendebts = copendebts;
    }

    public String getVopendebts() {
        return vopendebts;
    }

    public void setVopendebts(String vopendebts) {
        this.vopendebts = vopendebts;
    }

    public String getPurchseact() {
        return purchseact;
    }

    public void setPurchseact(String purchseact) {
        this.purchseact = purchseact;
    }

    public String getPareturnac() {
        return pareturnac;
    }

    public void setPareturnac(String pareturnac) {
        this.pareturnac = pareturnac;
    }

    public String getPaoffsetac() {
        return paoffsetac;
    }

    public void setPaoffsetac(String paoffsetac) {
        this.paoffsetac = paoffsetac;
    }

    public String getLinkact_28() {
        return linkact_28;
    }

    public void setLinkact_28(String linkact_28) {
        this.linkact_28 = linkact_28;
    }

    public String getExdiffact() {
        return exdiffact;
    }

    public void setExdiffact(String exdiffact) {
        this.exdiffact = exdiffact;
    }

    public String getBalanceact() {
        return balanceact;
    }

    public void setBalanceact(String balanceact) {
        this.balanceact = balanceact;
    }

    public String getBnkchgact() {
        return bnkchgact;
    }

    public void setBnkchgact(String bnkchgact) {
        this.bnkchgact = bnkchgact;
    }

    public String getLinkact_29() {
        return linkact_29;
    }

    public void setLinkact_29(String linkact_29) {
        this.linkact_29 = linkact_29;
    }

    public String getLinkact_30() {
        return linkact_30;
    }

    public void setLinkact_30(String linkact_30) {
        this.linkact_30 = linkact_30;
    }

    public String getIncmacct() {
        return incmacct;
    }

    public void setIncmacct(String incmacct) {
        this.incmacct = incmacct;
    }

    public String getExpnacct() {
        return expnacct;
    }

    public void setExpnacct(String expnacct) {
        this.expnacct = expnacct;
    }

    public String getVatrevact() {
        return vatrevact;
    }

    public void setVatrevact(String vatrevact) {
        this.vatrevact = vatrevact;
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

    public String getCostrevact() {
        return costrevact;
    }

    public void setCostrevact(String costrevact) {
        this.costrevact = costrevact;
    }

    public String getRepomoact() {
        return repomoact;
    }

    public void setRepomoact(String repomoact) {
        this.repomoact = repomoact;
    }

    public String getWipvaracct() {
        return wipvaracct;
    }

    public void setWipvaracct(String wipvaracct) {
        this.wipvaracct = wipvaracct;
    }

    public String getSalevatoff() {
        return salevatoff;
    }

    public void setSalevatoff(String salevatoff) {
        this.salevatoff = salevatoff;
    }

    public String getPurcvatoff() {
        return purcvatoff;
    }

    public void setPurcvatoff(String purcvatoff) {
        this.purcvatoff = purcvatoff;
    }

    public String getDpmsalact() {
        return dpmsalact;
    }

    public void setDpmsalact(String dpmsalact) {
        this.dpmsalact = dpmsalact;
    }

    public String getDpmpuract() {
        return dpmpuract;
    }

    public void setDpmpuract(String dpmpuract) {
        this.dpmpuract = dpmpuract;
    }

    public String getExpvaract() {
        return expvaract;
    }

    public void setExpvaract(String expvaract) {
        this.expvaract = expvaract;
    }

    public String getCostoffact() {
        return costoffact;
    }

    public void setCostoffact(String costoffact) {
        this.costoffact = costoffact;
    }

    public String getEcexepnses() {
        return ecexepnses;
    }

    public void setEcexepnses(String ecexepnses) {
        this.ecexepnses = ecexepnses;
    }

    public String getStockact() {
        return stockact;
    }

    public void setStockact(String stockact) {
        this.stockact = stockact;
    }

    public String getDflinprcss() {
        return dflinprcss;
    }

    public void setDflinprcss(String dflinprcss) {
        this.dflinprcss = dflinprcss;
    }

    public String getDfltincstm() {
        return dfltincstm;
    }

    public void setDfltincstm(String dfltincstm) {
        this.dfltincstm = dfltincstm;
    }

    public String getDfltprofit() {
        return dfltprofit;
    }

    public void setDfltprofit(String dfltprofit) {
        this.dfltprofit = dfltprofit;
    }

    public String getDfltloss() {
        return dfltloss;
    }

    public void setDfltloss(String dfltloss) {
        this.dfltloss = dfltloss;
    }

    public String getVassets() {
        return vassets;
    }

    public void setVassets(String vassets) {
        this.vassets = vassets;
    }

    public String getStockrvact() {
        return stockrvact;
    }

    public void setStockrvact(String stockrvact) {
        this.stockrvact = stockrvact;
    }

    public String getStkrvofact() {
        return stkrvofact;
    }

    public void setStkrvofact(String stkrvofact) {
        this.stkrvofact = stkrvofact;
    }

    public String getWipacct() {
        return wipacct;
    }

    public void setWipacct(String wipacct) {
        this.wipacct = wipacct;
    }

    public String getDfltcard() {
        return dfltcard;
    }

    public void setDfltcard(String dfltcard) {
        this.dfltcard = dfltcard;
    }

    public String getShpdgdsact() {
        return shpdgdsact;
    }

    public void setShpdgdsact(String shpdgdsact) {
        this.shpdgdsact = shpdgdsact;
    }

    public String getGlrvoffact() {
        return glrvoffact;
    }

    public void setGlrvoffact(String glrvoffact) {
        this.glrvoffact = glrvoffact;
    }

    public String getOverpayap() {
        return overpayap;
    }

    public void setOverpayap(String overpayap) {
        this.overpayap = overpayap;
    }

    public String getUndrpayap() {
        return undrpayap;
    }

    public void setUndrpayap(String undrpayap) {
        this.undrpayap = undrpayap;
    }

    public String getOverpayar() {
        return overpayar;
    }

    public void setOverpayar(String overpayar) {
        this.overpayar = overpayar;
    }

    public String getUndrpayar() {
        return undrpayar;
    }

    public void setUndrpayar(String undrpayar) {
        this.undrpayar = undrpayar;
    }

    public String getArcmact() {
        return arcmact;
    }

    public void setArcmact(String arcmact) {
        this.arcmact = arcmact;
    }

    public String getArcmexpact() {
        return arcmexpact;
    }

    public void setArcmexpact(String arcmexpact) {
        this.arcmexpact = arcmexpact;
    }

    public String getArcmfrnact() {
        return arcmfrnact;
    }

    public void setArcmfrnact(String arcmfrnact) {
        this.arcmfrnact = arcmfrnact;
    }

    public String getArcmeuact() {
        return arcmeuact;
    }

    public void setArcmeuact(String arcmeuact) {
        this.arcmeuact = arcmeuact;
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

    public String getApcmeuact() {
        return apcmeuact;
    }

    public void setApcmeuact(String apcmeuact) {
        this.apcmeuact = apcmeuact;
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

    public String getGlgainxdif() {
        return glgainxdif;
    }

    public void setGlgainxdif(String glgainxdif) {
        this.glgainxdif = glgainxdif;
    }

    public String getGllossxdif() {
        return gllossxdif;
    }

    public void setGllossxdif(String gllossxdif) {
        this.gllossxdif = gllossxdif;
    }

    public String getAmountdiff() {
        return amountdiff;
    }

    public void setAmountdiff(String amountdiff) {
        this.amountdiff = amountdiff;
    }

    public String getSlfinvincm() {
        return slfinvincm;
    }

    public void setSlfinvincm(String slfinvincm) {
        this.slfinvincm = slfinvincm;
    }

    public String getSlfinvexpn() {
        return slfinvexpn;
    }

    public void setSlfinvexpn(String slfinvexpn) {
        this.slfinvexpn = slfinvexpn;
    }

    public String getOnholdact() {
        return onholdact;
    }

    public void setOnholdact(String onholdact) {
        this.onholdact = onholdact;
    }

    public String getPlaact() {
        return plaact;
    }

    public void setPlaact(String plaact) {
        this.plaact = plaact;
    }

    public String getIcclract() {
        return icclract;
    }

    public void setIcclract(String icclract) {
        this.icclract = icclract;
    }

    public String getOcclract() {
        return occlract;
    }

    public void setOcclract(String occlract) {
        this.occlract = occlract;
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

    public String getSaldpmint() {
        return saldpmint;
    }

    public void setSaldpmint(String saldpmint) {
        this.saldpmint = saldpmint;
    }

    public String getPurdpmint() {
        return purdpmint;
    }

    public void setPurdpmint(String purdpmint) {
        this.purdpmint = purdpmint;
    }

    public String getExrateondt() {
        return exrateondt;
    }

    public void setExrateondt(String exrateondt) {
        this.exrateondt = exrateondt;
    }

    public String getEurecvact() {
        return eurecvact;
    }

    public void setEurecvact(String eurecvact) {
        this.eurecvact = eurecvact;
    }

    public String getEupayact() {
        return eupayact;
    }

    public void setEupayact(String eupayact) {
        this.eupayact = eupayact;
    }

    public String getWipoffset() {
        return wipoffset;
    }

    public void setWipoffset(String wipoffset) {
        this.wipoffset = wipoffset;
    }

    public String getStockoffst() {
        return stockoffst;
    }

    public void setStockoffst(String stockoffst) {
        this.stockoffst = stockoffst;
    }

    public String getDunintrst() {
        return dunintrst;
    }

    public void setDunintrst(String dunintrst) {
        this.dunintrst = dunintrst;
    }

    public String getDunfee() {
        return dunfee;
    }

    public void setDunfee(String dunfee) {
        this.dunfee = dunfee;
    }

    public String getTdsinterst() {
        return tdsinterst;
    }

    public void setTdsinterst(String tdsinterst) {
        this.tdsinterst = tdsinterst;
    }

    public String getTdscharges() {
        return tdscharges;
    }

    public void setTdscharges(String tdscharges) {
        this.tdscharges = tdscharges;
    }

    public String getSdfltwt() {
        return sdfltwt;
    }

    public void setSdfltwt(String sdfltwt) {
        this.sdfltwt = sdfltwt;
    }

    public String getPdfltwt() {
        return pdfltwt;
    }

    public void setPdfltwt(String pdfltwt) {
        this.pdfltwt = pdfltwt;
    }

    public boolean isShandlewt() {
        return shandlewt;
    }

    public void setShandlewt(boolean shandlewt) {
        this.shandlewt = shandlewt;
    }

    public boolean isPhandlewt() {
        return phandlewt;
    }

    public void setPhandlewt(boolean phandlewt) {
        this.phandlewt = phandlewt;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load de pantalla">
    @PostConstruct
    public void init() {
        try {
            if (AccountingEJBClient == null) {
                AccountingEJBClient = new AccountingEJBClient();
            }
            if (AdminEJBClient == null) {
                AdminEJBClient = new AdminEJBClient();
            }

            this.LtsAcca = AdminEJBClient.findCatalog(CATALOGIVA);

            //Pestaña de Ventas
            //1. General.
            /**
             * ***************************************************************************************************
             */
            AccassignmentTO accAss = new AccassignmentTO();
            accAss = AccountingEJBClient.getAccAssignment();
            
            this.linkact_1 = doCodeName(accAss.getLinkact_1()); //Clientes Locales
            this.linkact_9 = doCodeName(accAss.getLinkact_9()); //Clientes Extranjeros
            this.linkact_2 = doCodeName(accAss.getLinkact_2()); //Cheques Recibidos
            this.linkact_3 = doCodeName(accAss.getLinkact_3()); //Saldo Caja
            this.overpayar = doCodeName(accAss.getOverpayar());//Pago en exceso, cuenta de clientes
            this.undrpayar = doCodeName(accAss.getUndrpayar());//Pago en defecto, cuenta de clientes
            //Cuenta de compensacion de anticipos No esta.
            this.glgainxdif = doCodeName(accAss.getGlgainxdif()); //Beneficios por diferencia de tipo de cambio
            this.gllossxdif = doCodeName(accAss.getGllossxdif());//Pérdida por diferencia de tipo de cambio
            this.linkact_22 = doCodeName(accAss.getLinkact_22());//Descuento por pronto pago
            this.dfltincom = doCodeName(accAss.getDfltincom());//Cuenta de ingresos
            //ForgnIncm	Cuenta de ingresos - Extranjero No Existe
            this.ecincome = doCodeName(accAss.getEcincome());//Ingresos por ventas UE
            this.arcmact = doCodeName(accAss.getArcmact());//Cta.crédito ventas
            this.arcmfrnact = doCodeName(accAss.getArcmfrnact());//Cta.crédito ventas: Extranjero
            this.arcmeuact = doCodeName(accAss.getArcmeuact());//Cuenta crédito ventas - UE
            //Cuenta Provicional de Anticipo
            this.dunintrst = doCodeName(accAss.getDunintrst());//Interés de reclamación
            this.dunfee = doCodeName(accAss.getDunfee());//Tasa de reclamación
            /**
             * ***************************************************************************************************
             */

            /**
             * ***************************************************************************************************
             */
            //1.2 Impuesto
            //Grupo de impuesto sobre ventas(Articulos) IVA Repercutido
            //Grupo de impuesto sobre ventas(Servicios) IVA Repercutido
            this.salevatoff = doCodeName(accAss.getSalevatoff());//Cuenta de contrapartida para impuesto de anticipo
            /**
             * ***************************************************************************************************
             */

            /**
             * ***************************************************************************************************
             */
            //2. Compras
            //2.1General
            this.linkact_10 = doCodeName(accAss.getLinkact_10());//Proveedores Locales
            this.linkact_11 = doCodeName(accAss.getLinkact_11());//Proveedores Extranjeros
            this.linkact_25 = doCodeName(accAss.getLinkact_25());//Beneficio por diferencia de cambios
            this.linkact_21 = doCodeName(accAss.getLinkact_21());//LinkAct_21	Pérdida por diferencia de tipo de cambio
            this.linkact_12 = doCodeName(accAss.getLinkact_12());//Transferencia bancaria
            this.linkact_19 = doCodeName(accAss.getLinkact_19());//Descuento por Pronto Pago
            this.linkact_20 = doCodeName(accAss.getLinkact_20());//Compensacion de descuento
            this.dfltexpn = doCodeName(accAss.getDfltexpn());//Cuenta de gastos
            this.forgnexpn = doCodeName(accAss.getForgnexpn());//ForgnExpn	Cuenta de costos - Extranjero
            this.ecexepnses = doCodeName(accAss.getEcexepnses());// ECExepnses	Cuenta de costos - UE
            this.apcmact = doCodeName(accAss.getApcmact());// APCMAct	Cta.crédito compras
            this.apcmfrnact = doCodeName(accAss.getApcmfrnact());//Cuenta de crédito de compras - Extranjero
            this.overpayap = doCodeName(accAss.getOverpayap());
            this.undrpayap = doCodeName(accAss.getUndrpayap());//Pago en defecto, cuenta de proveedores
            //cuenta de compensacion de anticipos no esta
            this.expvaract = doCodeName(accAss.getExpvaract());//ExpVarAct	Cuenta de gastos y de stocks
            //cuenta provisional de anticipos
            /**
             * ***************************************************************************************************
             */

            /**
             * ***************************************************************************************************
             */
            //2.2Impuesto
            //Grupo de impuesto sobre ventas(Articulos) IVA Repercutido no esta
            //Grupo de impuesto sobre ventas(Servicios) IVA Repercutido no esta
            this.linkact_16 = doCodeName(accAss.getLinkact_16());// LinkAct_16	Retención de impuestos
            this.purcvatoff = doCodeName(accAss.getPurcvatoff());// PurcVatOff	Cuenta de contrapartida para impuesto de anticipo
            //Codigo de retencion de impuesto por defecto
            /**
             * ***************************************************************************************************
             */

            /**
             * ***************************************************************************************************
             */
            //3.General
            //Comision de cobro de tarjeta de credito no esta
            this.linkact_24 = doCodeName(accAss.getLinkact_24());//cuenta de redondeo
            this.linkact_27 = doCodeName(accAss.getLinkact_27());//LinkAct_27	Diferencia de reconciliación automática
            this.linkact_28 = doCodeName(accAss.getLinkact_28()); //LinkAct_28	Cuenta de cierre del período
            this.linkact_26 = doCodeName(accAss.getLinkact_26()); //LinkAct_26	Beneficios por diferencia de tipo de cambio
            this.linkact_23 = doCodeName(accAss.getLinkact_23()); // LinkAct_23	Pérdida por diferencia de tipo de cambio
            this.linkact_18 = doCodeName(accAss.getLinkact_18()); //LinkAct_18	Cuenta de saldo inicial
            this.bnkchgact = doCodeName(accAss.getBnkchgact());// BnkChgAct	Cuenta de gastos bancarios
            this.exrateondt = doCodeName(accAss.getExrateondt());//ExrateOnDt	Tipo de cambio de la cuenta de impuestos diferidos

            /**
             * ***************************************************************************************************
             */
            /**
             * *****************************************************************************************************
             */
            //4 Inventario
            this.stockact = doCodeName(accAss.getStockact());// StockAct	Cuenta de existencias
            this.cogm_act = doCodeName(accAss.getCogm_act());// COGM_Act	Cuenta de costo de bienes vendidos
            this.aloccstact = doCodeName(accAss.getAloccstact());// AlocCstAct	Cuenta de dotación
            this.variancact = doCodeName(accAss.getVariancact());// VariancAct	Cuenta de desviación
            this.pricdifact = doCodeName(accAss.getPricdifact());// PricDifAct	Cuenta de diferencias de precio
            this.negstckact = doCodeName(accAss.getNegstckact());// NegStckAct	Cuenta de compensación de inventario negativo

            this.dfltprofit = doCodeName(accAss.getDfltprofit());  // DfltProfit	Compensación de stocks: Cuenta de aumento
            this.dfltloss = doCodeName(accAss.getDfltloss());// DfltLoss	Compensación de stocks: Cuenta de reducción
            this.rturnngact = doCodeName(accAss.getRturnngact());// RturnngAct	Cuenta de devoluciones por ventas
            this.purchseact = doCodeName(accAss.getPurchseact());// PurchseAct	Cuenta de compras
            this.pareturnac = doCodeName(accAss.getPareturnac());// PaReturnAc	Cuenta de compras de devolución
            //cuenta de costos de mercancias compradas No esta
            this.exdiffact = doCodeName(accAss.getExdiffact()); // ExDiffAct	Cuenta de diferencias de tipo de cambio
            this.balanceact = doCodeName(accAss.getBalanceact());// BalanceAct	Cuenta compensación mercancías
            this.decresglac = doCodeName(accAss.getDecresglac()); // DecresGlAc	Cuenta de reducción del libro mayor
            this.incresglac = doCodeName(accAss.getIncresglac());// IncresGlAc	Cuenta de aumento del libro mayor
            this.wipacct = doCodeName(accAss.getWipacct());// WipAcct	Cuenta de stocks de trabajo en curso
            this.wipvaracct = doCodeName(accAss.getWipvaracct());//WipVarAcct	Cuenta de desviación de stock WIP
            this.wipoffset = doCodeName(accAss.getWipoffset());// WipOffset	Cuenta PyG de compensación WIP
            this.stockoffst = doCodeName(accAss.getStockoffst());// StockOffst	Cuenta PyG de compensación de stocks
            this.expclract = doCodeName(accAss.getExpclract());// ExpClrAct	Cuenta compensación gastos
            this.stkintnact = doCodeName(accAss.getStkintnact()); // StkInTnAct	Stock en la cuenta de tránsito
            /**
             * *******************************************************************************************************************
             */

//        this.absentry = doCodeName(accAss.getAbsentry();
//        this.periodcat = doCodeName(accAss.getPeriodcat();
//        this.financyear = doCodeName(accAss.getFinancyear();
//        this.year = doCodeName(accAss.getYear();
//        this.periodname = doCodeName(accAss.getPeriodname();
//        this.subtype = doCodeName(accAss.getSubtype();
//        this.periodnum = doCodeName(accAss.getPeriodnum();
//        this.f_refdate = doCodeName(accAss.getF_refdate();
//        this.t_refdate = doCodeName(accAss.getT_refdate();
//        this.f_duedate = doCodeName(accAss.getF_duedate();
//        this.f_taxdate = doCodeName(accAss.getF_taxdate();
//        this.t_duedate = doCodeName(accAss.getT_taxdate();
//
//        this.linkact_4 = doCodeName(accAss.getLinkact_4();
//        this.linkact_5 = doCodeName(accAss.getLinkact_5();
//        this.linkact_6 = doCodeName(accAss.getLinkact_6();
//        this.linkact_8 = doCodeName(accAss.getLinkact_8();
//
//        this.linkact_13 = doCodeName(accAss.getLinkact_13();
//        this.linkact_14 = doCodeName(accAss.getLinkact_14();
//        this.linkact_15 = doCodeName(accAss.getLinkact_15();
//
//        this.linkact_17 = doCodeName(accAss.getLinkact_17();
//
//        this.linkact_29 = doCodeName(accAss.getLinkact_29();
//        this.linkact_30 = doCodeName(accAss.getLinkact_30();
//
//        this.exmptincom = doCodeName(accAss.getExmptincom();
//
//        this.forgnincm = doCodeName(accAss.getForgnincm();
//
//        this.dfltratedi = doCodeName(accAss.getDfltratedi();
//
//        this.dftstockob = doCodeName(accAss.getDftstockob();
//
//        this.cdownpymnt = doCodeName(accAss.getCdownpymnt();
//        this.vdownpymnt = doCodeName(accAss.getVdownpymnt();
//        this.cboercvble = doCodeName(accAss.getCboercvble();
//        this.cboeonclct = doCodeName(accAss.getCboeonclct();
//        this.cboepresnt = doCodeName(accAss.getCboepresnt();
//        this.cboediscnt = doCodeName(accAss.getCboediscnt();
//        this.cunpaidboe = doCodeName(accAss.getCunpaidboe();
//        this.vboepayble = doCodeName(accAss.getVboepayble();
//        this.vasstboepy = doCodeName(accAss.getVasstboepy();
//        this.copendebts = doCodeName(accAss.getCopendebts();
//        this.vopendebts = doCodeName(accAss.getVopendebts();
//
//        this.paoffsetac = doCodeName(accAss.getPaoffsetac();
//
//        this.incmacct = doCodeName(accAss.getIncmacct();
//        this.expnacct = doCodeName(accAss.getExpnacct();
//        this.vatrevact = doCodeName(accAss.getVatrevact();
//
//        this.expofstact = doCodeName(accAss.getExpofstact();
//        this.costrevact = doCodeName(accAss.getCostrevact();
//        this.repomoact = doCodeName(accAss.getRepomoact();
//
//        this.dpmsalact = doCodeName(accAss.getDpmsalact();
//        this.dpmpuract = doCodeName(accAss.getDpmpuract();
//
//        this.costoffact = doCodeName(accAss.getCostoffact();
//
//        this.dflinprcss = doCodeName(accAss.getDflinprcss();
//        this.dfltincstm = doCodeName(accAss.getDfltincstm();
//
//        this.vassets = doCodeName(accAss.getVassets();
//
//        this.stockrvact = doCodeName(accAss.getStockrvact();
//        this.stkrvofact = doCodeName(accAss.getStkrvofact();
//
//        this.dfltcard = doCodeName(accAss.getDfltcard();
//        this.shpdgdsact = doCodeName(accAss.getShpdgdsact();
//
//        this.glrvoffact = doCodeName(accAss.getGlrvoffact();
//
//        this.arcmexpact = doCodeName(accAss.getArcmexpact();
//
//        this.apcmeuact = doCodeName(accAss.getApcmeuact();
//
//        this.amountdiff = doCodeName(accAss.getAmountdiff();
//        this.slfinvincm = doCodeName(accAss.getSlfinvincm();
//        this.slfinvexpn = doCodeName(accAss.getSlfinvexpn();
//
//        this.onholdact = doCodeName(accAss.getOnholdact();
//        this.plaact = doCodeName(accAss.getPlaact();
//        this.icclract = doCodeName(accAss.getIcclract();
//        this.occlract = doCodeName(accAss.getOcclract();
//        this.purbalact = doCodeName(accAss.getPurbalact();
//        this.whicenact = doCodeName(accAss.getWhicenact();
//        this.whocenact = doCodeName(accAss.getWhocenact();
//        this.saldpmint = doCodeName(accAss.getSaldpmint();
//        this.purdpmint = doCodeName(accAss.getPurdpmint();
//
//        this.eurecvact = doCodeName(accAss.getEurecvact();
//        this.eupayact = doCodeName(accAss.getEupayact();
//
//        this.tdsinterst = doCodeName(accAss.getTdsinterst();
//        this.tdscharges = doCodeName(accAss.getTdscharges();
//
//        this.usersign = doCodeName(accAss.getUsersign();
//
//        //this.shandlewt=doCodeName(accAss.gets no se encuentra en el metodo
//        //this.phandlewt=doCodeName(accAss.get no se encuentra en el metodo
//        this.sdfltwt = doCodeName(accAss.getSdfltwt();
//        this.pdfltwt = doCodeName(accAss.getPdfltwt();
        //getAccAssignment
            // this.arcmact = doCodeName(accAss.getArcmact();
            this.LtsAccount = AccountingEJBClient.getAccountByFilter(null,null);
 
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="code - name">
    public String doCodeName(String code){
        try {
            if (code != null) {
                AccountTO acc = new AccountTO();
                acc = AccountingEJBClient.getAccountByKey(code);
                String res;
                res = code + " - " + acc.getAcctname();
                return res;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Guardar en base">
    public void doSave() {
        ResultOutTO _return = null;
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        AccassignmentTO params = new AccassignmentTO();
        //Pestaña de Ventas
        //1. General.
        /**
         * ***************************************************************************************************
         */
        //String wlinkact_1=linkact_1.substring(0,linkact_1.indexOf("-")-1);
        //System.out.println(wlinkact_1);
        params.setLinkact_1(extractAccNumber(linkact_1));//Clientes Locales
        params.setLinkact_9(extractAccNumber(linkact_9)); //Clientes Extranjeros
        params.setLinkact_2(extractAccNumber(linkact_2)); //Cheques Recibidos
        params.setLinkact_3(extractAccNumber(linkact_3));//Saldo Caja
        params.setOverpayar(extractAccNumber(overpayar));//Pago en exceso, cuenta de clientes
        params.setUndrpayar(extractAccNumber(undrpayar));//Pago en defecto, cuenta de clientes
        //Cuenta de compensacion de anticipos No esta.
        params.setGlgainxdif(extractAccNumber(glgainxdif));//Beneficios por diferencia de tipo de cambio
        params.setGllossxdif(extractAccNumber(gllossxdif));//Pérdida por diferencia de tipo de cambio
        params.setLinkact_22(extractAccNumber(linkact_22));//Descuento por pronto pago
        params.setDfltincom(extractAccNumber(dfltincom));//Cuenta de ingresos
        //ForgnIncm	Cuenta de ingresos - Extranjero No Existe
        params.setEcincome(extractAccNumber(ecincome));//Ingresos por ventas UE
        params.setArcmact(extractAccNumber(arcmact));//Cta.crédito ventas
        params.setArcmfrnact(extractAccNumber(arcmfrnact));//Cta.crédito ventas: Extranjero
        params.setArcmeuact(extractAccNumber(arcmeuact));//Cuenta crédito ventas - UE
        //Cuenta Provicional de Anticipo
        params.setDunintrst(extractAccNumber(dunintrst));//Interés de reclamación
        params.setDunfee(extractAccNumber(dunfee));//Tasa de reclamación
        /**
         * ***************************************************************************************************
         */
        /**
         * ***************************************************************************************************
         */
        //1.2 Impuesto
        //Grupo de impuesto sobre ventas(Articulos) IVA Repercutido
        //Grupo de impuesto sobre ventas(Servicios) IVA Repercutido
        params.setSalevatoff(extractAccNumber(salevatoff));//Cuenta de contrapartida para impuesto de anticipo
        /**
         * ***************************************************************************************************
         */

        //2. Compras
        //2.1General
        params.setLinkact_10(extractAccNumber(linkact_10));//Proveedores Locales
        params.setLinkact_11(extractAccNumber(linkact_11));//Proveedores Extranjeros
        params.setLinkact_25(extractAccNumber(linkact_25));//Beneficio por diferencia de cambios
        params.setLinkact_21(extractAccNumber(linkact_21));//LinkAct_21	Pérdida por diferencia de tipo de cambio
        params.setLinkact_12(extractAccNumber(linkact_12));//Transferencia bancaria
        params.setLinkact_19(extractAccNumber(linkact_19));//Descuento por Pronto Pago
        params.setLinkact_20(extractAccNumber(linkact_20));//Compensacion de descuento
        params.setDfltexpn(extractAccNumber(dfltexpn));//Cuenta de gastos
        params.setForgnexpn(extractAccNumber(forgnexpn));//ForgnExpn	Cuenta de costos - Extranjero
        params.setEcexepnses(extractAccNumber(ecexepnses));// ECExepnses	Cuenta de costos - UE
        params.setApcmact(extractAccNumber(apcmact));// APCMAct	Cta.crédito compras
        params.setApcmfrnact(extractAccNumber(apcmfrnact));//Cuenta de crédito de compras - Extranjero
        params.setOverpayap(extractAccNumber(overpayap));
        params.setUndrpayap(extractAccNumber(undrpayap));//Pago en defecto, cuenta de proveedores
        //cuenta de compensacion de anticipos no esta
        params.setExpvaract(extractAccNumber(expvaract));//ExpVarAct	Cuenta de gastos y de stocks
        //cuenta provisional de anticipos
        /**
         * ***************************************************************************************************
         */
        //2.2Impuesto
        //Grupo de impuesto sobre ventas(Articulos) IVA Repercutido no esta
        //Grupo de impuesto sobre ventas(Servicios) IVA Repercutido no esta
        params.setLinkact_16(extractAccNumber(linkact_16));// LinkAct_16	Retención de impuestos
        params.setPurcvatoff(extractAccNumber(purcvatoff));// PurcVatOff	Cuenta de contrapartida para impuesto de anticipo
        //Codigo de retencion de impuesto por defecto no esta
        /**
         * ***************************************************************************************************
         */
        //3.General
        //Comision de cobro de tarjeta de credito no esta
        params.setLinkact_24(extractAccNumber(linkact_24));//cuenta de redondeo
        params.setLinkact_27(extractAccNumber(linkact_27));//LinkAct_27	Diferencia de reconciliación automática
        params.setLinkact_28(extractAccNumber(linkact_28));//LinkAct_28	Cuenta de cierre del período
        params.setLinkact_26(extractAccNumber(linkact_26));//LinkAct_26	Beneficios por diferencia de tipo de cambio
        params.setLinkact_23(extractAccNumber(linkact_23));// LinkAct_23	Pérdida por diferencia de tipo de cambio
        params.setLinkact_18(extractAccNumber(linkact_18));//LinkAct_18	Cuenta de saldo inicial
        params.setBnkchgact(extractAccNumber(bnkchgact));// BnkChgAct	Cuenta de gastos bancarios
        params.setExrateondt(extractAccNumber(exrateondt));//ExrateOnDt	Tipo de cambio de la cuenta de impuestos diferidos

        /**
         * ***************************************************************************************************
         */
        //4 Inventario
        params.setStockact(extractAccNumber(this.stockact));// StockAct	Cuenta de existencias
        params.setCogm_act(extractAccNumber(this.cogm_act));// COGM_Act	Cuenta de costo de bienes vendidos
        params.setAloccstact(extractAccNumber(this.aloccstact));// AlocCstAct	Cuenta de dotación
        params.setVariancact(extractAccNumber(this.variancact));// VariancAct	Cuenta de desviación
        params.setPricdifact(extractAccNumber(this.pricdifact));// PricDifAct	Cuenta de diferencias de precio
        params.setNegstckact(extractAccNumber(this.negstckact));// NegStckAct	Cuenta de compensación de inventario negativo

        params.setDfltprofit(extractAccNumber(this.dfltprofit));  // DfltProfit	Compensación de stocks: Cuenta de aumento
        params.setDfltloss(extractAccNumber(this.dfltloss));// DfltLoss	Compensación de stocks: Cuenta de reducción
        params.setRturnngact(extractAccNumber(this.rturnngact));// RturnngAct	Cuenta de devoluciones por ventas
        params.setPurchseact(extractAccNumber(this.purchseact));// PurchseAct	Cuenta de compras
        params.setPareturnac(extractAccNumber(this.pareturnac));// PaReturnAc	Cuenta de compras de devolución
        //cuenta de costosde mercancias compradas No esta
        params.setExdiffact(extractAccNumber(this.exdiffact)); // ExDiffAct	Cuenta de diferencias de tipo de cambio
        params.setBalanceact(extractAccNumber(this.balanceact));// BalanceAct	Cuenta compensación mercancías
        params.setDecresglac(extractAccNumber(this.decresglac)); // DecresGlAc	Cuenta de reducción del libro mayor
        params.setIncresglac(extractAccNumber(this.incresglac));// IncresGlAc	Cuenta de aumento del libro mayor
        params.setWipacct(extractAccNumber(this.wipacct));// WipAcct	Cuenta de stocks de trabajo en curso
        params.setWipvaracct(extractAccNumber(this.wipvaracct));//WipVarAcct	Cuenta de desviación de stock WIP
        params.setWipoffset(extractAccNumber(this.wipoffset));// WipOffset	Cuenta PyG de compensación WIP
        params.setStockoffst(extractAccNumber(this.stockoffst));// StockOffst	Cuenta PyG de compensación de stocks
        params.setExpclract(extractAccNumber(this.expclract));// ExpClrAct	Cuenta compensación gastos
        params.setStkintnact(extractAccNumber(this.stkintnact)); // StkInTnAct	Stock en la cuenta de tránsito
        try {
            _return = AccountingEJBClient.cat_accAssignment_mtto(params, Common.MTTOUPDATE);
            if (_return.getCodigoError() == 0) {
                faceMessage(_return.getMensaje());
            } else {
                faceMessage(_return.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="BOTON PRINCIPAL">
    public void btnPrincipal(){
        try {
            showHideDialog("dlgC2", 1);
        } catch (Exception e) {
        }
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="extractAccNumber">
   
    public String extractAccNumber(String _input) {
        String _result = null;
        if (_input != null && _input.length() > 0) {
            if (_input.indexOf("-") > 0) {
                _result = _input.substring(0, _input.indexOf("-") - 1);
            } else {
                _result = null;
            }
        } else {
            _result = _input;
        }
        //this.stkintnact.substring(0,stkintnact.indexOf("-")-1)   
        return _result;

    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="completeText">

    public List<String> completeText(String query) {
        List _result = null;
        String var = null;
        String filterByCode = null;
        String filterByName = null;
        try {
            if (query.matches(".*\\d+.*")) {
                //es un numero
                filterByCode = query;
            } else {
                filterByName = query;
            }

            _result = AccountingEJBClient.getAccountByFilter(filterByCode, filterByName);
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> results = new ArrayList<>();

        Iterator<AccountTO> iterator = _result.iterator();
        while (iterator.hasNext()) {
            AccountTO cuentas = (AccountTO) iterator.next();
            System.out.println(cuentas.getAcctcode() + " - " + cuentas.getAcctname());
            results.add(cuentas.getAcctcode() + " - " + cuentas.getAcctname());
        }
        return results;
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="SIN USO">
    

//public List<String> completeCode(String query) {
//        List _result = null;
//        String var = null;
//      
//      //  _result = AdminEJBService.getArticles(query, var);
//        _result=AccountingEJBClient.getAccount(2);
//        
//        List<String> results = new ArrayList<String>();
//        
//        Iterator<AccountTO> iterator=_result.iterator();
//        //Iterator<ArticlesTO> iterator = _result.iterator();
//        
//        while (iterator.hasNext()) {
//            AccountTO cuenta= (AccountTO) iterator.next();
//            //ArticlesTO articulo = (ArticlesTO) iterator.next();
//            results.add(cuenta.getAcctcode());
//        }
//        return results;
//    }
//public void findArticle(SelectEvent event){
//        List cuentas = new Vector();
//        String var = null;
//        
//        if(event.getObject().toString() != var){
//            List _result=null;
//            try{
//                _result=AccountingEJBClient.getAccount(2);
//            }catch(Exception e){
//                System.out.println("Error");
//                this.linkact_1=null;
//            }
//            if(_result.isEmpty()){
//                this.linkact_1=null;
//                
//            }else{
//                Iterator<AccountTO> iterator=_result.iterator();
//                while(iterator.hasNext()){
//                    AccountTO cuenta=(AccountTO) iterator.next();
//                    cuentas.add(cuenta);
//                }
//                if(cuentas.size()==1){
//                    System.out.println("Cuenta unica, llenar campos en pantalla");
//                    AccountTO cte= (AccountTO) cuentas.get(0);
//                    //this.linkact_1=
//                }
//            }
//        }
////            List _result = null;
//            
////            try {
////             //   _result = AdminEJBService.getArticles(newCod, newNomArt);
////                
////            } catch (Exception e) {
////                System.out.println("Error");
////           //     newCod = null;
////           //     newNomArt = null;
////            }
////            if (_result.isEmpty()) {
////             //   this.newCod = null;
////            //    this.newNomArt = null;
////                
////            }else{
////                Iterator<ArticlesTO> iterator = _result.iterator();
////                while (iterator.hasNext()) {
////                    ArticlesTO articulo = (ArticlesTO) iterator.next();
////                    articulos.add(articulo);
////                }
////                if(articulos.size() == 1){
////                        System.out.println("articulo unico, llenar campos en pantalla");
////                        ArticlesTO art = (ArticlesTO) articulos.get(0);
//////                        newNomArt = art.getItemName();
//////                        newCod = art.getItemCode();
//////                        newUnidad = "m";//varq.getBuyUnitMsr(); //provisional______________
//////                        newExistencia = art.getOnHand();
//////                        art = AdminEJBService.getArticlesByKey(newCod);
////                        if (almDest != null) {
////                            List alm = art.getBranchArticles();
////                            Iterator<BranchArticlesTO> iter = alm.iterator();
////                            while(iter.hasNext()){
////                                BranchArticlesTO branch = (BranchArticlesTO) iter.next();
////                                if (branch.getWhscode().equals(almDest)){
////                                    newExistencia = branch.getOnhand();
////                                    break;
////                                }
////                            }   
////                        }        
////                }else{
////                    ArticlesTO art = (ArticlesTO) articulos.get(0);
////                    newNomArt = art.getItemName();
////                    newCod = art.getItemCode();
////                    newUnidad ="Mas de un elemento";// varq.getBuyUnitMsr();     //provisional______________
////                }
////            }
////        }
//        
//    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="findAccount">
    public void findAccount(SelectEvent event) {
        /*try {
            //_result = AdminEJBService.getArticles(newCod, newNomArt);
            AccountTO _result = AccountingEJBClient.getAccountByKey(event.toString());
        } catch (Exception e) {
            faceMessage(e.getMessage() + " -- " + e.getCause());
        }*/
        faceMessage(event.toString()+" Agregado");

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    //Mostrar u ocultar un dialogo; 1 muestra, 2 oculta
    public void showHideDialog(String name, int openClose) {
        try {
            RequestContext rc = RequestContext.getCurrentInstance();
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
    
    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC2').hide();");
    }
    
    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlgC2", 2);
        doSave();
    }
    
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

}//cierre de clase
