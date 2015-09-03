/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.accounting.to.JournalEntryLinesInTO;
import com.sifcoapp.objects.accounting.to.JournalEntryLinesTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "chartAccounts")
@SessionScoped
/**
 *
 * @author Jcastro
 */
public class ChartAccounts implements Serializable {

    public ChartAccounts() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaración de variables para formulario" >  
    private static AccountingEJBClient accEJBService;

    private String acctcode;
    private String acctname;
    private Double currtotal;
    private Double endtotal;
    private String finanse;
    private String budget;
    private boolean postable;
    private int levels;
    private int grpline;
    private String fathernum;
    private int groupmask;
    private int intrmatch;
    private String acttype;
    private String protected1;
    private int usersign;
    private String objtype;
    private boolean validfor;
    private String formatcode;
    private List TreeAcc;
    private TreeNode root;
    //declaracion de variables para el evento de seleccion
    private TreeNode selectedNode;
    private AccountTO newAccount;
    //declaracion de variables de input en pantalla
    private Double cuuentaMayor;
    private String pk;

    //__________________________________________________________________________
    //dialog
    private ArrayList<JournalEntryLinesTO> lstAccTable = new ArrayList<>();
    private JournalEntryLinesTO select;
    private Date firstDate = FirstDate();
    private Date lastDate = LastDate();
    private int numDet = 50;
    private String nameCodAcc;
    private Double t1=0.0, t2=0.0, t3=0.0;

    //__________________________________________________________________________
    //btn
    private boolean btnEnable = true;
    
    
    
    

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Get and Set" >
    
    public JournalEntryLinesTO getSelect() {    
        return select;
    }
    
    public void setSelect(JournalEntryLinesTO select) {
        this.select = select;
    }

    public Double getT1() {
        return t1;
    }

    public void setT1(Double t1) {
        this.t1 = t1;
    }

    public Double getT2() {
        return t2;
    }

    public void setT2(Double t2) {
        this.t2 = t2;
    }

    public Double getT3() {
        return t3;
    }
    
    public void setT3(Double t3) {
        this.t3 = t3;
    }

    public boolean isBtnEnable() {
        return btnEnable;
    }
    
    public String getNameCodAcc() {
        return nameCodAcc;
    }
    
    public void setNameCodAcc(String nameCodAcc) {    
        this.nameCodAcc = nameCodAcc;
    }

    public int getNumDet() {
        return numDet;
    }

    public void setNumDet(int numDet) {
        this.numDet = numDet;
    }
    
    
    public void setBtnEnable(boolean btnEnable) {
        this.btnEnable = btnEnable;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public boolean isPostable() {
        return postable;
    }

    public boolean isValidfor() {
        return validfor;
    }

    public static AccountingEJBClient getAccEJBService() {
        return accEJBService;
    }

    public static void setAccEJBService(AccountingEJBClient accEJBService) {
        ChartAccounts.accEJBService = accEJBService;
    }

    public Double getCuuentaMayor() {
        return cuuentaMayor;
    }

    public void setCuuentaMayor(Double cuuentaMayor) {
        this.cuuentaMayor = cuuentaMayor;
    }

    public ArrayList<JournalEntryLinesTO> getLstAccTable() {
        return lstAccTable;
    }

    public void setLstAccTable(ArrayList<JournalEntryLinesTO> lstAccTable) {
        this.lstAccTable = lstAccTable;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public AccountTO getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(AccountTO newAccount) {
        this.newAccount = newAccount;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getAcctcode() {
        return acctcode;
    }

    /**
     * @param acctcode the acctcode to set
     */
    public void setAcctcode(String acctcode) {
        this.acctcode = acctcode;
    }

    /**
     * @return the acctname
     */
    public String getAcctname() {
        return acctname;
    }

    /**
     * @param acctname the acctname to set
     */
    public void setAcctname(String acctname) {
        this.acctname = acctname;
    }

    /**
     * @return the currtotal
     */
    public Double getCurrtotal() {
        return currtotal;
    }

    /**
     * @param currtotal the currtotal to set
     */
    public void setCurrtotal(Double currtotal) {
        this.currtotal = currtotal;
    }

    /**
     * @return the endtotal
     */
    public Double getEndtotal() {
        return endtotal;
    }

    /**
     * @param endtotal the endtotal to set
     */
    public void setEndtotal(Double endtotal) {
        this.endtotal = endtotal;
    }

    /**
     * @return the finanse
     */
    public String getFinanse() {
        return finanse;
    }

    /**
     * @param finanse the finanse to set
     */
    public void setFinanse(String finanse) {
        this.finanse = finanse;
    }

    /**
     * @return the budget
     */
    public String getBudget() {
        return budget;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    /**
     * @return the postable
     */
    public boolean getPostable() {
        return postable;
    }

    /**
     * @param postable the postable to set
     */
    public void setPostable(boolean postable) {
        this.postable = postable;
    }

    /**
     * @return the levels
     */
    public int getLevels() {
        return levels;
    }

    /**
     * @param levels the levels to set
     */
    public void setLevels(int levels) {
        this.levels = levels;
    }

    /**
     * @return the grpline
     */
    public int getGrpline() {
        return grpline;
    }

    /**
     * @param grpline the grpline to set
     */
    public void setGrpline(int grpline) {
        this.grpline = grpline;
    }

    /**
     * @return the fathernum
     */
    public String getFathernum() {
        return fathernum;
    }

    /**
     * @param fathernum the fathernum to set
     */
    public void setFathernum(String fathernum) {
        this.fathernum = fathernum;
    }

    /**
     * @return the groupmask
     */
    public int getGroupmask() {
        return groupmask;
    }

    /**
     * @param groupmask the groupmask to set
     */
    public void setGroupmask(int groupmask) {
        this.groupmask = groupmask;
    }

    /**
     * @return the intrmatch
     */
    public int getIntrmatch() {
        return intrmatch;
    }

    /**
     * @param intrmatch the intrmatch to set
     */
    public void setIntrmatch(int intrmatch) {
        this.intrmatch = intrmatch;
    }

    /**
     * @return the acttype
     */
    public String getActtype() {
        return acttype;
    }

    /**
     * @param acttype the acttype to set
     */
    public void setActtype(String acttype) {
        this.acttype = acttype;
    }

    /**
     * @return the protected1
     */
    public String getProtected1() {
        return protected1;
    }

    /**
     * @param protected1 the protected1 to set
     */
    public void setProtected1(String protected1) {
        this.protected1 = protected1;
    }

    /**
     * @return the usersign
     */
    public int getUsersign() {
        return usersign;
    }

    /**
     * @param usersign the usersign to set
     */
    public void setUsersign(int usersign) {
        this.usersign = usersign;
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
     * @return the validfor
     */
    public boolean getValidfor() {
        return validfor;
    }

    /**
     * @param validfor the validfor to set
     */
    public void setValidfor(boolean validfor) {
        this.validfor = validfor;
    }

    /**
     * @return the formatcode
     */
    public String getFormatcode() {
        return formatcode;
    }

    /**
     * @param formatcode the formatcode to set
     */
    public void setFormatcode(String formatcode) {
        this.formatcode = formatcode;
    }

    /**
     * @return the nodeDetail
     */
    public List getTreeAcc() {
        return TreeAcc;
    }

    public void setTreeAcc(List TreeAcc) {
        this.TreeAcc = TreeAcc;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

//</editor-fold> 
    
//<editor-fold defaultstate="collapsed" desc="Load de la pantalla">
    //load de la pantalla    
    @PostConstruct
    public void initForm() {
        if (accEJBService == null) {
            accEJBService = new AccountingEJBClient();
        }

        llenarRoot();
    }

//</editor-fold>
    
public void updTreeAcc(){
    try {
        facesMessage("Actualizando");
        llenarRoot();
        //seachAcc("11", "");
    } catch (Exception e) {
        facesMessage(e.getCause() + " " + e.getCause() );
    }
}
    
//<editor-fold defaultstate="collapsed" desc="Llenar arbol">
    public void llenarRoot() {
        try {
            this.setTreeAcc(accEJBService.getTreeAccount());

            //Crear arbol de cuentas contables
            this.root = createDocuments();
            root.setExpanded(true);
            //RequestContext.getCurrentInstance().update("treeAcc");
        } catch (Exception e) {
            facesMessage("error");
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="create documents">
    public TreeNode createDocuments() {
        AccountTO accNode = null;
        TreeNode acc = new DefaultTreeNode(accNode, null);

        List AccDetLst = new Vector();
        AccDetLst = this.TreeAcc;
        Iterator<AccountTO> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            AccountTO accdetail = (AccountTO) iterator.next();
            TreeNode documents = new DefaultTreeNode(accdetail, acc);
            //Asignar hijos
            if (accdetail.getChild() != null) {
                createDocumentsChild(documents, accdetail.getChild());
                documents.setExpanded(true);
            }
        }
        acc.getChildren().get(0).setExpanded(true);
        return acc;
    }

    public void createDocumentsChild(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
        Iterator<AccountTO> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            AccountTO accdetail = (AccountTO) iterator.next();
            TreeNode documents = new DefaultTreeNode(accdetail, padre);
            //Asignar hijos
            if (accdetail.getChild() != null) {
                createDocumentsChild(documents, accdetail.getChild());
                documents.setExpanded(true);
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evento al seleccionar del treeTable">
    public void onNodeSelect(NodeSelectEvent event) {
        //facesMessage("entro");
        AccountTO var = null;
        if (newAccount != null) {
            newAccount = new AccountTO();
        }
        this.setSelectedNode(event.getTreeNode());
        var = (AccountTO) selectedNode.getData();//event.getTreeNode().getData();

        try {
            newAccount = accEJBService.getAccountByKey(var.getAcctcode());
        } catch (Exception ex) {
            Logger.getLogger(ChartAccounts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setAcctcode(newAccount.getAcctcode());
        setAcctname(newAccount.getAcctname());
        postable = (newAccount.getPostable().equals("N"));
        setLevels(newAccount.getLevels());
        currtotal = newAccount.getCurrtotal();
        
        if (newAccount.getPostable().equals("Y")) {
            btnEnable = false;
        } else {
            btnEnable = true;
        }
        //RequestContext.getCurrentInstance().update("principal");
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Boton Principal">
    public void btnPrincipal() {
        showHideDialog("dlgC2", 1);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Guardar">
    public void UpdtAcc() {
        if (validateName()) {
            try {
                ResultOutTO Resp;
                AccountTO updAcc = new AccountTO();
                //updAcc = (AccountTO) this.selectedNode.getData();
                updAcc.setAcctcode(this.acctcode);
                updAcc = accEJBService.getAccountByKey(this.acctcode);
                updAcc.setAcctname(this.acctname);
                Resp = accEJBService.cat_acc0_ACCOUNT_mtto(updAcc, Common.MTTOUPDATE);
                llenarRoot();
                this.acctname = null;
                this.acctcode = null;
                RequestContext.getCurrentInstance().update("principal");
                facesMessage("Update Realizado");
            } catch (Exception ex) {
                Logger.getLogger(ChartAccounts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="funciones varias">
    
    public String formatNumber(Double num) {
        String st = null;
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            String var = num+"";
            
            st = nf.format(num);
            if (!st.contains(".")) {
                st = st + ".00";
            }
            if (st.contains(",")) {
                st = st.replace(",", "");
            }
        } catch (Exception e) {
            //faceMessage("Error format Number");
        }

        return st;
    }
    
    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlgC2", 2);
        UpdtAcc();
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC2').hide();");
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
            facesMessage(e.getMessage() + "---" + e.getCause());
        }
    }

    private boolean validateName() {
        if (this.acctname.equals("") || this.acctname == null || this.acctname.isEmpty()) {
            facesMessage("Ingrese un nombre para la cuenta");
            return false;
        }
        return true;
    }

    private void facesMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="btn ">
    public void btnSaldo() {
        t1 = 0.0;
        t2 = 0.0;
        t3 = 0.0;
        this.lstAccTable.clear();
        List _res = null;
        //facesMessage("saldo");
        JournalEntryLinesInTO entry = new JournalEntryLinesInTO();
        entry.setAccount(acctcode);
        entry.setRefdate(firstDate);
        entry.setTaxdate(lastDate);
        entry.setTransid(numDet);
        entry.setTotalvat(currtotal);

        try {
            _res = accEJBService.getEntryDetail(entry);
            for (Object obj : _res) {
                JournalEntryLinesTO jour = (JournalEntryLinesTO) obj;
                if (jour.getTotalvat()!= null) {
                    t1= t1 + jour.getTotalvat();
                }
                if (jour.getDebit() != null) {
                    t2= t2 + jour.getDebit();
                }
                if (jour.getCredit() != null) {
                    t3= t3 + jour.getCredit();
                }
                this.lstAccTable.add(jour);
            }
            t1 = t2 - t3;
            
            t1 = Double.parseDouble(formatNumber(t1));
            t2 = Double.parseDouble(formatNumber(t2));
            t3 = Double.parseDouble(formatNumber(t3));
            RequestContext.getCurrentInstance().update("detList");
            showHideDialog("dlgC3", 1);
        } catch (Exception e) {
            facesMessage(e.getMessage() +" "+e.getCause());
        }

    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="btn Upd dialog">
    public void updDialog(){
        t1 = 0.0;
        t2 = 0.0;
        t3 = 0.0;
        this.lstAccTable.clear();
        List _res = null;
       // facesMessage("saldo");
        JournalEntryLinesInTO entry = new JournalEntryLinesInTO();
        entry.setAccount(acctcode);
        entry.setRefdate(firstDate);
        entry.setTaxdate(lastDate);
        entry.setTransid(numDet);
        entry.setTotalvat(currtotal);
        this.nameCodAcc = "";
        nameCodAcc = this.acctcode +"-"+ this.acctname;
        
        try {
            _res = accEJBService.getEntryDetail(entry);
            for (Object obj : _res) {
                JournalEntryLinesTO jour = (JournalEntryLinesTO) obj;
                if (jour.getTotalvat()!= null) {
                    t1= t1 + jour.getTotalvat();
                }
                if (jour.getDebit() != null) {
                    t2= t2 + jour.getDebit();
                }
                if (jour.getCredit() != null) {
                    t3= t3 + jour.getCredit();
                }
                this.lstAccTable.add(jour);
            }
            t1 = t2 - t3;
            t1 = Double.parseDouble(formatNumber(t1));
            t2 = Double.parseDouble(formatNumber(t2));
            t3 = Double.parseDouble(formatNumber(t3));
            RequestContext.getCurrentInstance().update("dlgupd");
            //showHideDialog("dlgC3", 1);
        } catch (Exception e) {
            facesMessage(e.getMessage() +" "+e.getCause());
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Realizar busqueda">
    public void seachAcc(String accCode, String accName) {
        //Colapsar todo el arbol
        collapsingORexpanding(this.root, false);
        //Buscar codigo o nombre
        searchAcc2(this.root,accCode,accName);
    }

    public boolean searchAcc2(TreeNode nodoP, String accCode, String accName) {
        boolean result = false;
        if (nodoP.getChildren().isEmpty()) {
            AccountTO var = (AccountTO) nodoP.getData();
            if (var.getAcctcode().equals(accCode) ){//|| var.getAcctname().equals(accName)) {
                nodoP.setSelected(true);
                nodoP.setExpanded(true);
                return true;
            }
            return false;   

        } else {
            for (TreeNode s : nodoP.getChildren()) {
                if (result) {
                    break;
                }

                result = searchAcc2(s, accCode, accName);
                nodoP.setExpanded(result);
                /*if (nodoP.getChildren().isEmpty()) {
                    AccountTO var = (AccountTO) s.getData();
                    if (var.getAcctcode().equals(accCode) || var.getAcctname().contains(accName)) {
                        nodoP.setSelected(true);
                        nodoP.setExpanded(true);
                        return true;
                    }
                    
                }*/
                return result;
            }

        }
        return false;
    }

    public void collapsingORexpanding(TreeNode n, boolean option) {
        if (n.getChildren().isEmpty()) {
            n.setSelected(false);
        } else {
            for (TreeNode s : n.getChildren()) {
                collapsingORexpanding(s, option);
            }
            n.setExpanded(option);
            n.setSelected(false);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="primer dia y ultimo">
    public int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public Date FirstDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getYear());
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);

        return c.getTime();
    }

    public Date LastDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getYear());
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        c.set(Calendar.DAY_OF_MONTH, 31);

        return c.getTime();
    }

//</editor-fold>
    
}//cierre de clase
