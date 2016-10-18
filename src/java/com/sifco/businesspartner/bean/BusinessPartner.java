/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.businesspartner.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.assignment.bean.AccassignmentBean;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.CatalogEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.PricesListInTO;
import com.sifcoapp.objects.admin.to.PricesListTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerAcountTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerInTO;
import com.sifcoapp.objects.catalog.to.BusinesspartnerTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "businessPartner")
@SessionScoped
public class BusinessPartner implements Serializable {

    public BusinessPartner() {
    }

//<editor-fold defaultstate="collapsed" desc="declaracion de variables">
    //variables de cabercera y del TO
    private String fax;
    private String cardcode;
    private String cardname;
    private String cardtype;
    private String groupcode;
    private String cmpprivate;
    private String address;
    private String zipcode;
    private String mailaddres;
    private String mailzipcod;
    private String phone1;
    private String phone2;
    private String cntctprsn;
    private String notes;
    private Double balance;
    private Double checksbal;
    private Double ordersbal;
    private Double creditline;
    private Double debtline;
    private Double discount;
    private String vatstatus;
    private int listnum;
    private String transfered;
    private String baltrnsfrd;
    private String cellular;
    private String e_mail;
    private String picture;
    private String dflaccount;
    private String dflbranch;
    private String bankcode;
    private String addid;
    private String qrygroup1;
    private String qrygroup2;
    private String qrygroup3;
    private String qrygroup4;
    private String qrygroup5;
    private String qrygroup6;
    private String qrygroup7;
    private String qrygroup8;
    private String qrygroup9;
    private String qrygroup10;
    private boolean validfor;
    private String vatgroup;
    private String objtype;
    private String debpayacct = "";
    private int docentry;
    private String pymcode;
    private String partdelivr;
    private String paymblock;
    private boolean wtliable;
    private String ninum;

    private String wtcode;
    private String vatregnum;
    private String industry;
    private String business;
    private String isdomestic;
    private String isresident;
    private String profession;
    private int industryc;
    private String intracc;
    private String feeacc;
    private int series;
    private String taxidident;
    private String typecont;
    private String typesn;
    private String nit;
    private int usersign;

    //otras variables para la pantalla principal
    private int varEstados;
    private boolean disable;
    private String botonEstado;
    private AdminEJBClient AdminEJBService;
    private static CatalogEJBClient CatalogEJB;
    private static final String GRUPO = "GroupBusinesspartner";
    private static final String RAMO = "RamoBusinesspartner";
    private static final String CONDICIONPAGO = "CondicionPagoBusiness";
    private static final String INDICADORIMP = "IndicadorImpuestoBusines";
    private List<CatalogTO> BusinessGroupLst;
    private List<CatalogTO> RamoLst;
    private List<CatalogTO> CondicionPagoLst;
    private List<CatalogTO> IndicadorImpLst;
    private List<PricesListTO> listPriceList;
    private boolean confirm;
    private BusinesspartnerTO newBusiness;
    private ResultOutTO _result;
    HttpSession session = Util.getSession();
    // variables para el dialogo de cuantas asociadas
    private int inNumero;
    private String fathercard;
    private String nombreClase;

    private String codigoCuenta;
    private String codigoCuenta2;
    private String codigoCuenta3;
    private String codigoCuenta4;

    private AccountingEJBClient AccountingEJBClient;
    private AccountTO detalle = new AccountTO();
    private ArrayList Detail = new ArrayList();

    //__________________________________________________________________________
    //Listas para busqueda
    private List listaBusqueda = new Vector();
    private ArrayList<BusinesspartnerTO> listaBusquedaTable = new ArrayList<>();
    private BusinesspartnerTO selectBusiness = new BusinesspartnerTO();

    //__________________________________________________________________________
    //cuentas asociadas
    private ArrayList<BusinesspartnerAcountTO> listaAccAddTable = new ArrayList<>();
    private List listaAccAdd = new Vector();
    private BusinesspartnerAcountTO selectAcc = new BusinesspartnerAcountTO();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getters and setters">
    public String getCodigoCuenta4() {
        return codigoCuenta4;
    }

    public void setCodigoCuenta4(String codigoCuenta4) {
        this.codigoCuenta4 = codigoCuenta4;
    }

    public BusinesspartnerAcountTO getSelectAcc() {
        return selectAcc;
    }

    public void setSelectAcc(BusinesspartnerAcountTO selectAcc) {
        this.selectAcc = selectAcc;
    }

    public ArrayList<BusinesspartnerAcountTO> getListaAccAddTable() {
        return listaAccAddTable;
    }

    public void setListaAccAddTable(ArrayList<BusinesspartnerAcountTO> listaAccAddTable) {
        this.listaAccAddTable = listaAccAddTable;
    }

    public List getListaAccAdd() {
        return listaAccAdd;
    }

    public void setListaAccAdd(List listaAccAdd) {
        this.listaAccAdd = listaAccAdd;
    }

    public int getInNumero() {
        return inNumero;
    }

    public void setInNumero(int inNumero) {
        this.inNumero = inNumero;
    }

    public AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public void setAdminEJBService(AdminEJBClient AdminEJBService) {
        this.AdminEJBService = AdminEJBService;
    }

    public static CatalogEJBClient getCatalogEJB() {
        return CatalogEJB;
    }

    public static void setCatalogEJB(CatalogEJBClient CatalogEJB) {
        BusinessPartner.CatalogEJB = CatalogEJB;
    }

    public ResultOutTO getResult() {
        return _result;
    }

    public void setResult(ResultOutTO _result) {
        this._result = _result;
    }

    public String getFathercard() {
        return fathercard;
    }

    public void setFathercard(String fathercard) {
        this.fathercard = fathercard;
    }

    public AccountingEJBClient getAccountingEJBClient() {
        return AccountingEJBClient;
    }

    public void setAccountingEJBClient(AccountingEJBClient AccountingEJBClient) {
        this.AccountingEJBClient = AccountingEJBClient;
    }

    public ArrayList getDetail() {
        return Detail;
    }

    public void setDetail(ArrayList Detail) {
        this.Detail = Detail;
    }

    public BusinesspartnerTO getSelectBusiness() {
        return selectBusiness;
    }

    public void setSelectBusiness(BusinesspartnerTO selectBusiness) {
        this.selectBusiness = selectBusiness;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<BusinesspartnerTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<BusinesspartnerTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public String getCodigoCuenta2() {
        return codigoCuenta2;
    }

    public List<PricesListTO> getListPriceList() {
        return listPriceList;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setListPriceList(List<PricesListTO> listPriceList) {
        this.listPriceList = listPriceList;
    }

    public void setCodigoCuenta2(String codigoCuenta2) {
        this.codigoCuenta2 = codigoCuenta2;
    }

    public String getCodigoCuenta3() {
        return codigoCuenta3;
    }

    public void setCodigoCuenta3(String codigoCuenta3) {
        this.codigoCuenta3 = codigoCuenta3;
    }

    public AccountTO getDetalle() {
        return detalle;
    }

    public void setDetalle(AccountTO detalle) {
        this.detalle = detalle;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public List<CatalogTO> getBusinessGroupLst() {
        return BusinessGroupLst;
    }

    public void setBusinessGroupLst(List<CatalogTO> BusinessGroupLst) {
        this.BusinessGroupLst = BusinessGroupLst;
    }

    public List<CatalogTO> getRamoLst() {
        return RamoLst;
    }

    public void setRamoLst(List<CatalogTO> RamoLst) {
        this.RamoLst = RamoLst;
    }

    public List<CatalogTO> getCondicionPagoLst() {
        return CondicionPagoLst;
    }

    public void setCondicionPagoLst(List<CatalogTO> CondicionPagoLst) {
        this.CondicionPagoLst = CondicionPagoLst;
    }

    public List<CatalogTO> getIndicadorImpLst() {
        return IndicadorImpLst;
    }

    public void setIndicadorImpLst(List<CatalogTO> IndicadorImpLst) {
        this.IndicadorImpLst = IndicadorImpLst;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public BusinesspartnerTO getNewBusiness() {
        return newBusiness;
    }

    public void setNewBusiness(BusinesspartnerTO newBusiness) {
        this.newBusiness = newBusiness;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public void setBotonEstado(String botonEstado) {
        this.botonEstado = botonEstado;
    }

    public String getBotonEstado() {
        return botonEstado;
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

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getCmpprivate() {
        return cmpprivate;
    }

    public void setCmpprivate(String cmpprivate) {
        this.cmpprivate = cmpprivate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMailaddres() {
        return mailaddres;
    }

    public void setMailaddres(String mailaddres) {
        this.mailaddres = mailaddres;
    }

    public String getMailzipcod() {
        return mailzipcod;
    }

    public void setMailzipcod(String mailzipcod) {
        this.mailzipcod = mailzipcod;
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

    public String getCntctprsn() {
        return cntctprsn;
    }

    public void setCntctprsn(String cntctprsn) {
        this.cntctprsn = cntctprsn;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getChecksbal() {
        return checksbal;
    }

    public void setChecksbal(Double checksbal) {
        this.checksbal = checksbal;
    }

    public Double getOrdersbal() {
        return ordersbal;
    }

    public void setOrdersbal(Double ordersbal) {
        this.ordersbal = ordersbal;
    }

    public Double getCreditline() {
        return creditline;
    }

    public void setCreditline(Double creditline) {
        this.creditline = creditline;
    }

    public Double getDebtline() {
        return debtline;
    }

    public void setDebtline(Double debtline) {
        this.debtline = debtline;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getVatstatus() {
        return vatstatus;
    }

    public void setVatstatus(String vatstatus) {
        this.vatstatus = vatstatus;
    }

    public int getListnum() {
        return listnum;
    }

    public void setListnum(int listnum) {
        this.listnum = listnum;
    }

    public String getTransfered() {
        return transfered;
    }

    public void setTransfered(String transfered) {
        this.transfered = transfered;
    }

    public String getBaltrnsfrd() {
        return baltrnsfrd;
    }

    public void setBaltrnsfrd(String baltrnsfrd) {
        this.baltrnsfrd = baltrnsfrd;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDflaccount() {
        return dflaccount;
    }

    public void setDflaccount(String dflaccount) {
        this.dflaccount = dflaccount;
    }

    public String getDflbranch() {
        return dflbranch;
    }

    public void setDflbranch(String dflbranch) {
        this.dflbranch = dflbranch;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getAddid() {
        return addid;
    }

    public void setAddid(String addid) {
        this.addid = addid;
    }

    public String getQrygroup1() {
        return qrygroup1;
    }

    public void setQrygroup1(String qrygroup1) {
        this.qrygroup1 = qrygroup1;
    }

    public String getQrygroup2() {
        return qrygroup2;
    }

    public void setQrygroup2(String qrygroup2) {
        this.qrygroup2 = qrygroup2;
    }

    public String getQrygroup3() {
        return qrygroup3;
    }

    public void setQrygroup3(String qrygroup3) {
        this.qrygroup3 = qrygroup3;
    }

    public String getQrygroup4() {
        return qrygroup4;
    }

    public void setQrygroup4(String qrygroup4) {
        this.qrygroup4 = qrygroup4;
    }

    public String getQrygroup5() {
        return qrygroup5;
    }

    public void setQrygroup5(String qrygroup5) {
        this.qrygroup5 = qrygroup5;
    }

    public String getQrygroup6() {
        return qrygroup6;
    }

    public void setQrygroup6(String qrygroup6) {
        this.qrygroup6 = qrygroup6;
    }

    public String getQrygroup7() {
        return qrygroup7;
    }

    public void setQrygroup7(String qrygroup7) {
        this.qrygroup7 = qrygroup7;
    }

    public String getQrygroup8() {
        return qrygroup8;
    }

    public void setQrygroup8(String qrygroup8) {
        this.qrygroup8 = qrygroup8;
    }

    public String getQrygroup9() {
        return qrygroup9;
    }

    public void setQrygroup9(String qrygroup9) {
        this.qrygroup9 = qrygroup9;
    }

    public String getQrygroup10() {
        return qrygroup10;
    }

    public void setQrygroup10(String qrygroup10) {
        this.qrygroup10 = qrygroup10;
    }

    public boolean getValidfor() {
        return validfor;
    }

    public void setValidfor(boolean validfor) {
        this.validfor = validfor;
    }

    public String getVatgroup() {
        return vatgroup;
    }

    public void setVatgroup(String vatgroup) {
        this.vatgroup = vatgroup;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getDebpayacct() {
        return debpayacct;
    }

    public void setDebpayacct(String debpayacct) {
        this.debpayacct = debpayacct;
    }

    public int getDocentry() {
        return docentry;
    }

    public void setDocentry(int docentry) {
        this.docentry = docentry;
    }

    public String getPymcode() {
        return pymcode;
    }

    public void setPymcode(String pymcode) {
        this.pymcode = pymcode;
    }

    public String getPartdelivr() {
        return partdelivr;
    }

    public void setPartdelivr(String partdelivr) {
        this.partdelivr = partdelivr;
    }

    public String getPaymblock() {
        return paymblock;
    }

    public void setPaymblock(String paymblock) {
        this.paymblock = paymblock;
    }

    public boolean getWtliable() {
        return wtliable;
    }

    public void setWtliable(boolean wtliable) {
        this.wtliable = wtliable;
    }

    public String getNinum() {
        return ninum;
    }

    public void setNinum(String ninum) {
        this.ninum = ninum;
    }

    public String getWtcode() {
        return wtcode;
    }

    public void setWtcode(String wtcode) {
        this.wtcode = wtcode;
    }

    public String getVatregnum() {
        return vatregnum;
    }

    public void setVatregnum(String vatregnum) {
        this.vatregnum = vatregnum;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getIsdomestic() {
        return isdomestic;
    }

    public void setIsdomestic(String isdomestic) {
        this.isdomestic = isdomestic;
    }

    public String getIsresident() {
        return isresident;
    }

    public void setIsresident(String isresident) {
        this.isresident = isresident;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getIndustryc() {
        return industryc;
    }

    public void setIndustryc(int industryc) {
        this.industryc = industryc;
    }

    public String getIntracc() {
        return intracc;
    }

    public void setIntracc(String intracc) {
        this.intracc = intracc;
    }

    public String getFeeacc() {
        return feeacc;
    }

    public void setFeeacc(String feeacc) {
        this.feeacc = feeacc;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getTaxidident() {
        return taxidident;
    }

    public void setTaxidident(String taxidident) {
        this.taxidident = taxidident;
    }

    public String getTypecont() {
        return typecont;
    }

    public void setTypecont(String typecont) {
        this.typecont = typecont;
    }

    public String getTypesn() {
        return typesn;
    }

    public void setTypesn(String typesn) {
        this.typesn = typesn;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >    
    @PostConstruct
    public void initForm() {

        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        //llenar todos los combobox
        //String var = null;
        PricesListInTO var2 = new PricesListInTO();
        try {
            BusinessGroupLst = AdminEJBService.findCatalog(GRUPO);
            RamoLst = AdminEJBService.findCatalog(RAMO);
            CondicionPagoLst = AdminEJBService.findCatalog(CONDICIONPAGO);
            IndicadorImpLst = AdminEJBService.findCatalog(INDICADORIMP);
            listPriceList = AdminEJBService.getPricesList(var2);
            //this.setListaAlmacenes((List<BranchTO>) AdminEJBService.getBranch(var, var));

        } catch (Exception e) {
            //faceMessage(e.getMessage() + " -" + e.getCause());
            System.out.println(e.getMessage() + " -" + e.getCause());
        }

        //estado por defecto
        estateGuardar();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Principal" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                if (validarIn()) {
                    showHideDialog("dlg9", 1);
                }
                break;
            case 2:
                if (validarIn()) {
                    showHideDialog("dlg9", 1);
                }
                break;
            case 3:
                listaBusqueda.clear();
                listaBusquedaTable.clear();
                doSearch();
                break;

            default:
                break;

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1;
        this.botonEstado = "Guardar";
        this.disable = false;
        validfor = true;
        //this.disableComun = false;
        //this.docNumState = true;
        RequestContext.getCurrentInstance().update("frmPartner");
    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";
        this.disable = true;
        //this.disableComun = true;
        //this.docNumState = true;
        RequestContext.getCurrentInstance().update("frmPartner");
    }

    public void estateBuscar() {
        this.varEstados = 3; //buscar
        this.botonEstado = "Buscar";
        this.disable = false;
        //this.disableComun = true;
        //docNumState = false;
        RequestContext.getCurrentInstance().update("frmPartner");

    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Botones toolbar " > 
    public void botonNuevo(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 2) {//mas de un detalle

            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                estateGuardar();
            }
        } else {
            cleanBean();
            estateGuardar();
            RequestContext.getCurrentInstance().update("frmPartner");
        }
    }

    public void botonBuscar(ActionEvent actionEvent) {
        if (validarClear() && varEstados != 2) {//mas de un detalle

            showHideDialog("dlg10", 1);
            if (confirm) {
                confirm = false;
                estateBuscar();
            }
        } else {
            cleanBean();
            estateBuscar();
            RequestContext.getCurrentInstance().update("frmPartner");
        }
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Validaciones">
    public boolean validarIn() {

        if (cardcode.isEmpty()) {
            faceMessage("Ingrese un Código");
            return false;
        }
        if (cardname.isEmpty()) {
            faceMessage("Ingrese un Nombre");
            return false;
        }
        if (debpayacct == null || debpayacct.equals("")) {
            faceMessage("Ingrese: Cuenta de deudor/acreedor");
            return false;
        }
        return true;
    }

    public boolean validarClear() {
        return !(cardcode.isEmpty() && cardname.isEmpty());

    }

    public boolean validarNull() {
        return !nombreClase.isEmpty() && !codigoCuenta.isEmpty();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Guardar en base">
    public void SavePartener() {

        if (newBusiness == null) {
            newBusiness = new BusinesspartnerTO();
        }
        //CABECERA

        /*detalle = new AccountTO();
         detalle.setAcctcode(codigoCuenta);
         detalle.setAcctname(nombreCuenta);
         Detail.add(detalle);
         detalle = new AccountTO();
         detalle.setAcctcode(codigoCuenta2);
         detalle.setAcctname(nombreCuenta2);
         Detail.add(detalle);
         detalle = new AccountTO();
         detalle.setAcctcode(codigoCuenta3);
         detalle.setAcctname(nombreCuenta3);
         Detail.add(detalle);*/
        newBusiness.setObjtype(1 + "");
        newBusiness.setCardcode(cardcode.replaceAll("\\s", ""));
        newBusiness.setCardtype(cardtype);
        newBusiness.setCardname(cardname);
        if (!groupcode.equals("-1")) {
            newBusiness.setGroupcode(groupcode);
        }
        newBusiness.setBalance(balance);

        //GENERAL
        if (!phone1.equals("")) {
            newBusiness.setPhone1(phone1);
        }
        if (!phone2.equals("")) {
            newBusiness.setPhone2(phone2);
        }
        if (!cellular.equals("")) {
            newBusiness.setCellular(cellular);
        }
        if (!fax.equals("")) {
            newBusiness.setFax(fax);
        }
        if (!addid.equals("")) {
            newBusiness.setAddid(addid);
        }
        if (!e_mail.equals("")) {
            newBusiness.setE_mail(e_mail);
        }
        if (!mailaddres.equals("")) {
            newBusiness.setMailaddres(mailaddres);
        }
        if (industryc != 0) {
            newBusiness.setIndustryc(industryc);
        }
        newBusiness.setValidfor(validfor ? "Y" : "N");
        if (!cntctprsn.equals("")) {
            newBusiness.setCntctprsn(cntctprsn);
        }
        if (!ninum.equals("")) {
            newBusiness.setNinum(ninum);
        }
        if (!notes.equals("")) {
            newBusiness.setNotes(notes);
        }
        //-------------FALTA EL DUI
        //CONDICIONES DE PAGO
        if (pymcode != null) {
            newBusiness.setPymcode(pymcode);
        }
        if (listnum != 0) {
            newBusiness.setListnum(listnum);
        }
        newBusiness.setCreditline(creditline);
        //FINANZAS
        //-------------- FALTA LISTA DE CUENTAS ASOCIADAS E INDICADORES DE RETENCION
        for (BusinesspartnerAcountTO obj : listaAccAddTable) {
            obj.setCardcode(cardcode);
            /*obj.setAcctcode(codigoCuenta);
             obj.setAcctcode2(codigoCuenta2);
             obj.setAcctcode3(codigoCuenta3);
             obj.setAcctcode4(codigoCuenta4);*/
            obj.setBalance(0.0);
            listaAccAdd.add(obj);
        }
        newBusiness.setBusinesspartnerAcount(listaAccAdd);

        newBusiness.setFathercard(fathercard);

        newBusiness.setWtliable(wtliable ? "Y" : "N");
        if (!debpayacct.equals("")) {
            newBusiness.setDebpayacct(debpayacct);
        }
        if (vatgroup != null) {
            newBusiness.setVatgroup(vatgroup);
        }
        newBusiness.setUsersign((int) session.getAttribute("usersign"));
        try {
            _result = CatalogEJB.cat_bpa_businesspartner_mtto(newBusiness, Common.MTTOINSERT);
            if (_result.getCodigoError() == 0) {//se realizo correctamente

                faceMessage(_result.getMensaje());
                //ListaGeneral.clear();
                estateActualizar();

            } else {
                faceMessage(_result.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update en base">
    public void UpdatePartner() {
        if (newBusiness == null) {
            newBusiness = new BusinesspartnerTO();
        }
        //CABERCERA
        newBusiness.setObjtype(1 + "");
        newBusiness.setCardcode(cardcode);
        newBusiness.setCardtype(cardtype);
        newBusiness.setCardname(cardname);
        if (!groupcode.equals("-1")) {
            newBusiness.setGroupcode(groupcode);
        }
        newBusiness.setBalance(balance);
        //GENERAL
        if (!nit.equals("")) {
            newBusiness.setNit(nit);
        }
        if (!addid.equals("")) {
            newBusiness.setAddid(addid);
        }
        if (!phone1.equals("")) {
            newBusiness.setPhone1(phone1);
        }
        if (!phone2.equals("")) {
            newBusiness.setPhone2(phone2);
        }
        if (!cellular.equals("")) {
            newBusiness.setCellular(cellular);
        }
        if (!fax.equals("")) {
            newBusiness.setCellular(fax);
        }
        //------------FALTA EL FAX
        if (!e_mail.equals("")) {
            newBusiness.setE_mail(e_mail);
        }
        if (!mailaddres.equals("")) {
            newBusiness.setMailaddres(mailaddres);
        }
        if (industryc != 0) {
            newBusiness.setIndustryc(industryc);
        }
        newBusiness.setValidfor(validfor ? "Y" : "N");
        if (!cntctprsn.equals("")) {
            newBusiness.setCntctprsn(cntctprsn);
        }
        if (!ninum.equals("")) {
            newBusiness.setNinum(ninum);
        }
        if (!notes.equals("")) {
            newBusiness.setNotes(notes);
        }
        //-------------FALTA EL DUI
        //CONDICIONES DE PAGO
        if (pymcode != null) {
            newBusiness.setPymcode(pymcode);
        }
        if (listnum != 0) {
            newBusiness.setListnum(listnum);
        }
        newBusiness.setCreditline(creditline);
        //FINANZAS
        //-------------- FALTA LISTA DE CUENTAS ASOCIADAS E INDICADORES DE RETENCION

        for (BusinesspartnerAcountTO obj : listaAccAddTable) {
            obj.setCardcode(cardcode);
            obj.setBalance(0.0);
            listaAccAdd.add(obj);
        }
        newBusiness.setBusinesspartnerAcount(listaAccAdd);
        newBusiness.setFathercard(fathercard);

        newBusiness.setWtliable(wtliable ? "Y" : "N");
        if (!debpayacct.equals("")) {
            newBusiness.setDebpayacct(debpayacct);
        }
        if (vatgroup != null) {
            newBusiness.setVatgroup(vatgroup);
        }
        newBusiness.setUsersign((int) session.getAttribute("usersign"));
        try {
            _result = CatalogEJB.cat_bpa_businesspartner_mtto(newBusiness, Common.MTTOUPDATE);
            if (_result.getCodigoError() == 0) {//se realizo correctamente

                faceMessage(_result.getMensaje());
                //ListaGeneral.clear();
                estateActualizar();

            } else {
                faceMessage(_result.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="BUSCAR EN BASE">
    public void doSearch() {
        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }
        BusinesspartnerInTO search = new BusinesspartnerInTO();
        String vacio = null;

        if (cardcode.equals("")) {
            search.setCardcode(vacio);
        } else {
            search.setCardcode(cardcode);
        }

        if (cardname.equals("")) {
            search.setCardname(vacio);
        } else {
            search.setCardname(cardname);
        }

        search.setCardtype(cardtype);

        if (!groupcode.equals("-1")) {
            search.setGroupcode(Integer.parseInt(groupcode));
        }

        if (nit.equals("")) {
            search.setNit(vacio);
        } else {
            search.setNit(nit);
        }

        try {
            listaBusqueda = CatalogEJB.get_businesspartner(search);
        } catch (Exception e) {
            faceMessage(e.getMessage() + " Error en la busqueda");
        }
        if (!listaBusqueda.isEmpty()) {
            if (listaBusqueda.size() == 1) {
                faceMessage("Elemento Unico");
                newBusiness = (BusinesspartnerTO) listaBusqueda.get(0);
                try {
                    BusinesspartnerTO var = CatalogEJB.get_businesspartnerBykey(newBusiness.getCardcode());
                    llenarPantalla(var);
                    estateActualizar();
                } catch (Exception e) {
                    faceMessage("Error en busqueda por PK");
                }
            } else {
                faceMessage("Seleccione un elemento");
                for (Object obj : listaBusqueda) {
                    BusinesspartnerTO par = (BusinesspartnerTO) obj;
                    listaBusquedaTable.add(par);
                }
                RequestContext.getCurrentInstance().update("frmsoc");
                showHideDialog("dlgListBusiness", 1);
            }
        } else {
            faceMessage("No se encontraron registros");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Autocompletado">
    public List<String> completeText(String query) {
        List _result = null;
        codigoCuenta = null;
        codigoCuenta2 = null;
        codigoCuenta3 = null;
        codigoCuenta4 = null;
        String filterByCode = null;
        try {

            _result = AccountingEJBClient.getAccountByFilter(filterByCode, query, "Y");
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> results = new ArrayList<String>();

        Iterator<AccountTO> iterator = _result.iterator();
        while (iterator.hasNext()) {
            AccountTO cuentas = (AccountTO) iterator.next();
            results.add(cuentas.getAcctname());
        }
        return results;
    }

    public List<String> completeCode(String query) {
        List _result = null;
        codigoCuenta = null;
        codigoCuenta2 = null;
        codigoCuenta3 = null;
        codigoCuenta4 = null;
        String filterByName = null;
        try {
            _result = AccountingEJBClient.getAccountByFilter(query, filterByName, "Y");
        } catch (Exception ex) {
            Logger.getLogger(AccassignmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> results = new ArrayList<String>();

        Iterator<AccountTO> iterator = _result.iterator();
        while (iterator.hasNext()) {
            AccountTO cuentas = (AccountTO) iterator.next();
            results.add(cuentas.getAcctcode());
        }
        return results;
    }

    public List<String> compSocioCode(String query) {
        List _result = null;

        BusinesspartnerInTO in = new BusinesspartnerInTO();
        in.setCardcode(query);
        //in.setCardtype("C");

        try {
            _result = CatalogEJB.get_businesspartner(in);

        } catch (Exception e) {
            faceMessage("Error en autocompletado");
        }

        List<String> results = new ArrayList<>();

        Iterator<BusinesspartnerTO> iterator = _result.iterator();

        while (iterator.hasNext()) {
            BusinesspartnerTO articulo = (BusinesspartnerTO) iterator.next();
            results.add(articulo.getCardcode());
        }
        return results;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del autocomplete" > 
    public void findAccount(SelectEvent event) {
        List account = new Vector();
        String var = null;
        if (event.getObject().toString() != var) {
            List _result = null;

            try {
                if (codigoCuenta != null) {
                    _result = AccountingEJBClient.getAccountByFilter(codigoCuenta, null);
                }
                if (codigoCuenta2 != null) {
                    _result = AccountingEJBClient.getAccountByFilter(codigoCuenta2, null);
                }
                if (codigoCuenta3 != null) {
                    _result = AccountingEJBClient.getAccountByFilter(codigoCuenta3, null);
                }
                if (codigoCuenta4 != null) {
                    _result = AccountingEJBClient.getAccountByFilter(codigoCuenta4, null);
                }

            } catch (Exception e) {
                faceMessage(e.getMessage() + " -- " + e.getCause());
                codigoCuenta = null;
                codigoCuenta2 = null;
                codigoCuenta3 = null;
                codigoCuenta4 = null;
            }
            if (_result.isEmpty()) {
                codigoCuenta = null;
                codigoCuenta2 = null;
                codigoCuenta3 = null;
                codigoCuenta4 = null;

            } else {
                Iterator<AccountTO> iterator = _result.iterator();
                while (iterator.hasNext()) {
                    AccountTO articulo = (AccountTO) iterator.next();
                    account.add(articulo);
                }
                if (account.size() == 1) {
                    try {
                        System.out.println("articulo unico, llenar campos en pantalla");
                        AccountTO art = (AccountTO) account.get(0);
                        if (codigoCuenta != null) {
                            codigoCuenta = art.getAcctcode();
                        }
                        if (codigoCuenta2 != null) {
                            codigoCuenta2 = art.getAcctcode();
                        }
                        if (codigoCuenta3 != null) {
                            codigoCuenta3 = art.getAcctcode();
                        }
                        if (codigoCuenta4 != null) {
                            codigoCuenta4 = art.getAcctcode();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    AccountTO art = (AccountTO) account.get(0);
                    if (codigoCuenta != null) {
                        codigoCuenta = art.getAcctcode();
                    }
                    if (codigoCuenta2 != null) {
                        codigoCuenta2 = art.getAcctcode();
                    }
                    if (codigoCuenta3 != null) {
                        codigoCuenta3 = art.getAcctcode();
                    }
                    if (codigoCuenta4 != null) {
                        codigoCuenta4 = art.getAcctcode();
                    }
                    faceMessage("Error: Mas de un elemento encontrado, nombre de articulo repetido");
                }
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Dialogos confirm cacel">
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

    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlg9", 2);
        if (varEstados == Common.MTTOINSERT) {
            SavePartener();
        } else {
            if (varEstados == Common.MTTOUPDATE) {
                UpdatePartner();
            }
        }
    }

    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlg10", 2);
        this.confirm = true;
        cleanBean();
        RequestContext.getCurrentInstance().update("frmPartner");
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg9').hide();");
    }

    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg10').hide();");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="BTN Agregar cuentas">
    public void addAcc() {
        if (validAddAcc() && validCod()) {
            BusinesspartnerAcountTO newDet = new BusinesspartnerAcountTO();
            newDet.setCardcode(cardcode);
            newDet.setAcctcode(codigoCuenta);
            newDet.setAcctype(inNumero);
            newDet.setAcctcode2(codigoCuenta2);
            newDet.setAcctcode3(codigoCuenta3);
            newDet.setAcctcode4(codigoCuenta4);
           
            try {
                _result = CatalogEJB.inv_cat_bpa_businesspartnerAcount_mtto(newDet, Common.MTTOINSERT);
                if (_result.getCodigoError() == 0) {//se realizo correctamente
                    
                    this.listaAccAddTable.add(newDet);
                    faceMessage(_result.getMensaje());
                    //ListaGeneral.clear();
                } else {
                    faceMessage(_result.getMensaje());
                }
            } catch (Exception ex) {
                Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
                faceMessage("Ha ocurrido un error, informe al administrador del sistema");
            }

            clearAdd();
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="btn ...">
    public void businessAcc() {
        RequestContext.getCurrentInstance().update("frmDialog");
        showHideDialog("dlg01", 1);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Funciones Varias frmDialog">
    private boolean validCod() {
        for (BusinesspartnerAcountTO obj : listaAccAddTable) {
            if (inNumero == obj.getAcctype()) {
                faceMessage("Numero repetido");
                inNumero = 0;
                return false;
            }
        }
        return true;
    }

    public void deleteAcc() {
        try {
            _result = CatalogEJB.inv_cat_bpa_businesspartnerAcount_mtto(selectAcc, Common.MTTODELETE);
            if (_result.getCodigoError() == 0) {//se realizo correctamente
                listaAccAddTable.remove(selectAcc);
                faceMessage(_result.getMensaje());
                //ListaGeneral.clear();
            } else {
                faceMessage(_result.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(BusinessPartner.class.getName()).log(Level.SEVERE, null, ex);
        }

        selectAcc = null;
    }

    public void clearAdd() {
        this.inNumero = 0;
        this.codigoCuenta = null;
        this.codigoCuenta2 = null;
        this.codigoCuenta3 = null;
        this.codigoCuenta4 = null;
    }

    public boolean validAddAcc() {
        if (this.inNumero <= 0) {
            faceMessage("Numero incorrecto");
            return false;
        }
        if (this.codigoCuenta == null || this.codigoCuenta.isEmpty() || this.codigoCuenta.equals("")) {
            faceMessage("Codigo de cuenta incorrecto");
            return false;
        }
        return true;
    }

    public void selectDialogBill() {
        if (CatalogEJB == null) {
            CatalogEJB = new CatalogEJBClient();
        }

        if (newBusiness == null) {
            newBusiness = new BusinesspartnerTO();
        }

        showHideDialog("dlgListBusiness", 2);
        BusinesspartnerTO var = (BusinesspartnerTO) selectBusiness;

        try {
            newBusiness = CatalogEJB.get_businesspartnerBykey(var.getCardcode());
            llenarPantalla(newBusiness);
            estateActualizar();
        } catch (Exception ex) {
            faceMessage("Error en busqueda por PK");
        }

        listaBusqueda = new Vector();
        listaBusquedaTable = new ArrayList<>();
        selectBusiness = new BusinesspartnerTO();
        RequestContext.getCurrentInstance().update("frmPartner");

    }

    private void llenarPantalla(BusinesspartnerTO var) {
        cardcode = var.getCardcode();
        cardname = var.getCardname();
        cardtype = var.getCardtype();
        groupcode = var.getGroupcode();
        cmpprivate = var.getCmpprivate();
        address = var.getAddress();
        zipcode = var.getZipcode();
        mailaddres = var.getMailaddres();
        mailzipcod = var.getMailzipcod();
        phone1 = var.getPhone1();
        phone2 = var.getPhone2();
        fax = var.getFax();
        cntctprsn = var.getCntctprsn();
        notes = var.getNotes();
        balance = var.getBalance();
        checksbal = var.getChecksbal();
        ordersbal = var.getOrdersbal();
        creditline = var.getCreditline();
        debtline = var.getDebtline();
        discount = var.getDiscount();
        vatstatus = var.getVatstatus();
        listnum = var.getListnum();
        transfered = var.getTransfered();
        baltrnsfrd = var.getBaltrnsfrd();
        cellular = var.getCellular();
        e_mail = var.getE_mail();
        picture = var.getPicture();
        dflaccount = var.getDflaccount();
        dflbranch = var.getDflbranch();
        bankcode = var.getBankcode();
        addid = var.getAddid();
        qrygroup1 = var.getQrygroup1();
        qrygroup2 = var.getQrygroup2();
        qrygroup3 = var.getQrygroup3();
        qrygroup4 = var.getQrygroup4();
        qrygroup5 = var.getQrygroup5();
        qrygroup6 = var.getQrygroup6();
        qrygroup7 = var.getQrygroup7();
        qrygroup8 = var.getQrygroup8();
        qrygroup9 = var.getQrygroup9();
        qrygroup10 = var.getQrygroup10();
        if (var.getValidfor() != null) {
            validfor = var.getValidfor().equals("Y");
        }

        vatgroup = var.getVatgroup();
        objtype = var.getObjtype();
        debpayacct = var.getDebpayacct();
        docentry = var.getDocentry();
        pymcode = var.getPymcode();
        partdelivr = var.getPartdelivr();
        paymblock = var.getPaymblock();
        if (var.getWtliable() != null) {
            wtliable = var.getWtliable().equals("Y");
        }

        ninum = var.getNinum();
        wtcode = var.getWtcode();
        vatregnum = var.getVatregnum();
        industry = var.getIndustry();
        business = var.getBusiness();
        isdomestic = var.getIsdomestic();
        isresident = var.getIsresident();
        profession = var.getProfession();
        industryc = var.getIndustryc();
        intracc = var.getIntracc();
        feeacc = var.getFeeacc();
        series = var.getSeries();
        taxidident = var.getTaxidident();
        typecont = var.getTypecont();
        typesn = var.getTypesn();
        nit = var.getNit();
        usersign = var.getUsersign();
        /*codigoCuenta = var.getRelatedacc1();
         codigoCuenta2 = var.getRelatedacc2();
         codigoCuenta3 = var.getRelatedacc3();*/

        fathercard = var.getFathercard();
        if (var.getBusinesspartnerAcount() != null) {
            for (Object obj : var.getBusinesspartnerAcount()) {
                BusinesspartnerAcountTO bu = (BusinesspartnerAcountTO) obj;
                this.listaAccAddTable.add(bu);
            }
        }
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    public void cleanBean() {
        newBusiness = new BusinesspartnerTO();
        cardcode = null;
        cardname = null;
        cardtype = null;
        groupcode = null;
        cmpprivate = null;
        address = null;
        zipcode = null;
        mailaddres = null;
        mailzipcod = null;
        phone1 = null;
        phone2 = null;
        fax = null;
        cntctprsn = null;
        notes = null;
        balance = null;
        checksbal = null;
        ordersbal = null;
        creditline = null;
        debtline = null;
        discount = null;
        vatstatus = null;
        listnum = 0;
        transfered = null;
        baltrnsfrd = null;
        cellular = null;
        e_mail = null;
        picture = null;
        dflaccount = null;
        dflbranch = null;
        bankcode = null;
        addid = null;
        qrygroup1 = null;
        qrygroup2 = null;
        qrygroup3 = null;
        qrygroup4 = null;
        qrygroup5 = null;
        qrygroup6 = null;
        qrygroup7 = null;
        qrygroup8 = null;
        qrygroup9 = null;
        qrygroup10 = null;
        validfor = true;
        vatgroup = null;
        objtype = null;
        debpayacct = null;
        docentry = 0;
        pymcode = null;
        partdelivr = null;
        paymblock = null;
        wtliable = false;
        ninum = null;
        wtcode = null;
        vatregnum = null;
        industry = null;
        business = null;
        isdomestic = null;
        isresident = null;
        profession = null;
        industryc = 0;
        intracc = null;
        feeacc = null;
        series = 0;
        taxidident = null;
        typecont = null;
        typesn = null;
        nit = null;
        usersign = 0;
        codigoCuenta = null;
        codigoCuenta2 = null;
        codigoCuenta3 = null;
        codigoCuenta4 = null;

        fathercard = null;
        this.listaAccAddTable.clear();
        this.listaAccAdd.clear();

    }

//</editor-fold>
}//cierre de clase
