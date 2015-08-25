/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.bank.bean;

import com.prueba.model.primefaces.Util;
import com.sifcoapp.client.BankEJBClient;
import com.sifcoapp.objects.bank.to.ColecturiaConceptTO;
import com.sifcoapp.objects.bank.to.ColecturiaTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Digits;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "conceptBean")
@ApplicationScoped
public class ConceptBean implements Serializable {

    public ConceptBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Variables">
    //Servicios EJB
    //private static CatalogEJBClient CatalogEJB;
    private static BankEJBClient BankEJBClient;
    //__________________________________________________________________________
    //spacer
    private int spacer1_w = 20;     //width
    private int spacer1_h = 10;     //height

    //__________________________________________________________________________
    //Session
    HttpSession session = Util.getSession();

    //__________________________________________________________________________
    //Variables de pantalla
    private int lineNum;            //pk numero de linea
    private String lineStatus = "Y";      //estado de linea
    private String objType;         //tipo de objeto
    private String description;     //descripcion

    private String acctCode1;       //codigo cuenta 1
    private String acctCode2;       //codigo cuenta 2
    private String acctCode3;       //codigo cuenta 3
    private String ocrCode;         //codigo de reparto

    private int transId;            //codigo transaccion
    private String SumRes = "Y";       //confirmado
    private String peyMethod;       //forma de pago
    private String ctlAccount;      //cuenta asociada

    @Digits(integer = 14, fraction = 2, message = "Total inadecuada")
    private Double paidSum;         //total de pago

    @Digits(integer = 14, fraction = 2, message = "Impuesto inadecuada")
    private Double vatSum;          //impuesto total

    private String docSubType;      //subtipo de documento
    private String valor1;          //valor 1
    private String valor2;          //valor 2

    private String valor3;          //valor 3
    private final int userSing = (int) session.getAttribute("usersign");  //firma
    private ArrayList<ColecturiaConceptTO> lstTable = new ArrayList<>();    //Data table
    
    private String IVA = "N";
    private String adicional = "N";

    //__________________________________________________________________________
    //selecciones
    private ColecturiaConceptTO selectConcept = new ColecturiaConceptTO();

    //__________________________________________________________________________
    //guardar
    private ColecturiaConceptTO newConcept = new ColecturiaConceptTO();

    //__________________________________________________________________________
    //estados
    private String valueBoton;
    private int varEstados;
    private boolean lineNumState;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">

    public static BankEJBClient getBankEJBClient() {
        return BankEJBClient;
    }

    public static void setBankEJBClient(BankEJBClient BankEJBClient) {
        ConceptBean.BankEJBClient = BankEJBClient;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getIVA() {
        return IVA;
    }

    public void setIVA(String IVA) {
        this.IVA = IVA;
    }

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public int getVarEstados() {
        return varEstados;
    }

    public void setVarEstados(int varEstados) {
        this.varEstados = varEstados;
    }
    
    public boolean isLineNumState() {
        return lineNumState;
    }

    public void setLineNumState(boolean lineNumState) {
        this.lineNumState = lineNumState;
    }

    public String getValueBoton() {
        return valueBoton;
    }

    public void setValueBoton(String valueBoton) {
        this.valueBoton = valueBoton;
    }

    public ColecturiaConceptTO getNewConcept() {
        return newConcept;
    }

    public void setNewConcept(ColecturiaConceptTO newConcept) {
        this.newConcept = newConcept;
    }

    public ColecturiaConceptTO getSelectConcept() {
        return selectConcept;
    }

    public void setSelectConcept(ColecturiaConceptTO selectConcept) {
        this.selectConcept = selectConcept;
    }

    public String getCtlAccount() {
        return ctlAccount;
    }

    public void setCtlAccount(String ctlAccount) {
        this.ctlAccount = ctlAccount;
    }

    public ArrayList<ColecturiaConceptTO> getLstTable() {
        return lstTable;
    }

    public void setLstTable(ArrayList<ColecturiaConceptTO> lstTable) {
        this.lstTable = lstTable;
    }

    public int getSpacer1_h() {
        return spacer1_h;
    }

    public void setSpacer1_h(int spacer1_h) {
        this.spacer1_h = spacer1_h;
    }

    public int getSpacer1_w() {
        return spacer1_w;
    }

    public void setSpacer1_w(int spacer1_w) {
        this.spacer1_w = spacer1_w;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcctCode1() {
        return acctCode1;
    }

    public void setAcctCode1(String acctCode1) {
        this.acctCode1 = acctCode1;
    }

    public String getAcctCode2() {
        return acctCode2;
    }

    public void setAcctCode2(String acctCode2) {
        this.acctCode2 = acctCode2;
    }

    public String getAcctCode3() {
        return acctCode3;
    }

    public void setAcctCode3(String acctCode3) {
        this.acctCode3 = acctCode3;
    }

    public String getOcrCode() {
        return ocrCode;
    }

    public void setOcrCode(String ocrCode) {
        this.ocrCode = ocrCode;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public String getSumRes() {
        return SumRes;
    }

    public void setSumRes(String SumRes) {
        this.SumRes = SumRes;
    }

    public String getPeyMethod() {
        return peyMethod;
    }

    public void setPeyMethod(String peyMethod) {
        this.peyMethod = peyMethod;
    }

    public Double getPaidSum() {
        return paidSum;
    }

    public void setPaidSum(Double paidSum) {
        this.paidSum = paidSum;
    }

    public Double getVatSum() {
        return vatSum;
    }

    public void setVatSum(Double vatSum) {
        this.vatSum = vatSum;
    }

    public String getDocSubType() {
        return docSubType;
    }

    public void setDocSubType(String docSubType) {
        this.docSubType = docSubType;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public String getValor3() {
        return valor3;
    }

    public void setValor3(String valor3) {
        this.valor3 = valor3;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load de pantalla">
    @PostConstruct
    public void initForm() {
        if (BankEJBClient == null) {
            BankEJBClient = new BankEJBClient();
        }

        llenarDataTable();
        estateGuardar();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Llenar data table">
    public void llenarDataTable() {
        try {
            List concept = new Vector();
            concept = BankEJBClient.get_ges_colecturiaConcept();
            this.lstTable.clear();
            for (Object obj : concept) {
                ColecturiaConceptTO con = (ColecturiaConceptTO) obj;
                this.lstTable.add(con);
            }
            RequestContext.getCurrentInstance().update("frmConcept");

        } catch (Exception ex) {
            Logger.getLogger(ConceptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton AGREGAR">
    public void btnAgregar() {

        switch (varEstados) {
            case 1:
                if (validarAdd()) {
                    showHideDialog("dlgC2", 1);
                }
                break;

            case 2:
                if (validarAdd()) {
                    showHideDialog("dlgC2", 1);
                }
                break;

            default:
                break;

        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {
        if (newConcept == null) {
            newConcept = new ColecturiaConceptTO();
        }
        Double vacio = 0.0;

        newConcept.setLinenum(lineNum);
        newConcept.setLinestatus(lineStatus);
        newConcept.setObjtype(objType);
        newConcept.setDscription(description);
        newConcept.setAcctcode(acctCode1);
        newConcept.setAcctcode2(acctCode2);
        newConcept.setAcctcode3(acctCode3);
        newConcept.setCtlaccount(ctlAccount);
        newConcept.setOcrcode(ocrCode);
        newConcept.setTransid(transId);
        newConcept.setConfirmed(SumRes);
        newConcept.setPeymethod(peyMethod);
        newConcept.setTaxstatus(IVA);
        newConcept.setAditional_account(adicional);

        if (paidSum == null) {
            newConcept.setPaidsum(vacio);
        } else {
            newConcept.setPaidsum(paidSum);
        }

        if (vatSum == null) {
            newConcept.setVatsum(vacio);
        } else {
            newConcept.setVatsum(vatSum);
        }

        newConcept.setDocsubtype(docSubType);
        newConcept.setValue1(valor1);
        newConcept.setValue2(valor2);
        newConcept.setValue3(valor3);

        ResultOutTO _res = null;
        try {
            _res = BankEJBClient.ges_ges_col2_colecturiaConcepts_mtto(newConcept, Common.MTTOINSERT);
            if (_res.getCodigoError() == 0) {//se realizo correctamente
                this.lineNum = _res.getDocentry();
                faceMessage(_res.getMensaje());
            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(ConceptBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="UPDATE EN BASE">
    public void doUpdate() {
        Double vacio = 0.0;
        
        newConcept.setLinenum(lineNum);
        newConcept.setLinestatus(lineStatus);
        newConcept.setObjtype(objType);
        newConcept.setDscription(description);
        newConcept.setAcctcode(acctCode1);
        newConcept.setAcctcode2(acctCode2);
        newConcept.setAcctcode3(acctCode3);
        newConcept.setCtlaccount(ctlAccount);
        newConcept.setOcrcode(ocrCode);
        newConcept.setTransid(transId);
        newConcept.setConfirmed(SumRes);
        newConcept.setPeymethod(peyMethod);
        
        if (paidSum == null) {
            newConcept.setPaidsum(vacio);
        } else {
            newConcept.setPaidsum(paidSum);
        }

        if (vatSum == null) {
            newConcept.setVatsum(vacio);
        } else {
            newConcept.setVatsum(vatSum);
        }
        
        newConcept.setDocsubtype(docSubType);
        newConcept.setValue1(valor1);
        newConcept.setValue2(valor2);
        newConcept.setValue3(valor3);
        newConcept.setTaxstatus(IVA);
        newConcept.setAditional_account(adicional);

        ResultOutTO _res = null;
        try {
            _res = BankEJBClient.ges_ges_col2_colecturiaConcepts_mtto(newConcept, Common.MTTOUPDATE);
            if (_res.getCodigoError() == 0) {//se realizo correctamente
                this.lineNum = _res.getDocentry();
                ColecturiaConceptTO var = new ColecturiaConceptTO();
                faceMessage(_res.getMensaje());
                llenarPantalla(var);
                estateGuardar();
            } else {
                faceMessage(_res.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(ConceptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Contex eliminar">
    public void contexDelete() {
        showHideDialog("dlgC1", 1);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Contex Actualizar">
    public void contexUpdate() {
        faceMessage("Actualizar concepto " + this.selectConcept.getLinenum());
        estateActualizar();
        llenarPantalla(selectConcept);

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ESTADOS GUARDAR ACTUALIZAR">
    public void estateGuardar() {
        varEstados = Common.MTTOINSERT;
        valueBoton = "Agregar";
        lineNumState = false;
        RequestContext.getCurrentInstance().update("frmConcept");
    }

    public void estateActualizar() {
        varEstados = Common.MTTOUPDATE;
        valueBoton = "Actualizar";
        lineNumState = true;
        RequestContext.getCurrentInstance().update("frmConcept");
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    public void confirmDialog2(ActionEvent actionEvent) {
        showHideDialog("dlgC1", 2);
        
        try {
            BankEJBClient.ges_ges_col2_colecturiaConcepts_mtto(selectConcept, Common.MTTODELETE);
            faceMessage("Concepto: " + this.selectConcept.getLinenum() + " Eliminado");
            llenarDataTable();
            
        } catch (Exception ex) {
            Logger.getLogger(ConceptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cancelDialog2(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC1').hide();");
    }
    
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

    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlgC2", 2);
        if (varEstados == 1) {
            doSave();
            llenarDataTable();
            ColecturiaConceptTO var = new ColecturiaConceptTO();
            llenarPantalla(var);
        } else {
            if (varEstados == 2) {
                doUpdate();
                llenarDataTable();
            }
        }
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC2').hide();");
    }

    public boolean validarAdd() {
        if (validarLineNum(lineNum) && varEstados != 2) {
            faceMessage("Numero de linea repetido, ingrese otro");
            return false;
        }

        if (lineNum < 1) {
            faceMessage("Ingrese unNumero de linea correcto");
            return false;
        }
        if (description == null || description.equals("") || description.isEmpty()) {
            faceMessage("Ingrese una descripcion para la linea");
            return false;
        }
        return true;
    }

    public boolean validarLineNum(int line) {
        //if existe linenum return true, else false
        List res;
        try {
            res = BankEJBClient.get_ges_colecturiaConcept();
            for (Object re : res) {
                ColecturiaConceptTO colect = (ColecturiaConceptTO) re;
                if (colect.getLinenum() == line) {
                    return true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ConceptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void llenarPantalla(ColecturiaConceptTO var) {
        setAcctCode1(var.getAcctcode());
        setAcctCode2(var.getAcctcode2());
        setAcctCode3(var.getAcctcode3());
        setSumRes(var.getConfirmed());
        setCtlAccount(var.getCtlaccount());
        setDescription(var.getDscription());
        setDocSubType(var.getDocsubtype());
        setLineNum(var.getLinenum());
        setLineStatus(var.getLinestatus());
        setObjType(var.getObjtype());
        setOcrCode(var.getOcrcode());
        setPaidSum(var.getPaidsum());
        setPeyMethod(var.getPeymethod());
        setTransId(var.getTransid());
        setValor1(var.getValue1());
        setValor2(var.getValue2());
        setValor3(var.getValue3());
        setVatSum(var.getVatsum());
        setIVA(var.getTaxstatus());
        setAdicional(var.getAditional_account());

        RequestContext.getCurrentInstance().update("frmConcept");
    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

}//cierre de clase
