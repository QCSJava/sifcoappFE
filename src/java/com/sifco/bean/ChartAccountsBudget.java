/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.accounting.to.BudgetTO;
import com.sifcoapp.objects.catalogos.Common;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "ChartAccountsBudget")
@ApplicationScoped
/**
 *
 * @author Jcastro
 */
public class ChartAccountsBudget implements Serializable {
     public ChartAccountsBudget() {
    }

//<editor-fold defaultstate="collapsed" desc="Declaración de variables para formulario" >  
    private static AccountingEJBClient accEJBService;
    private AdminEJBClient AdminEJBService;
    private String CATALOGANIO="cg_year";
    private List Anios= new Vector();
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
    private Double monto = 0.0;
    //declaracion de variables para entrada de datos
    private Documentnew selectedTable;
    TreeNode acc = null;
    TreeNode acc2 = null;
    String anio;
    Date fechaSave=null;
    int anio2;

  
    
//<editor-fold defaultstate="collapsed" desc="Get and Set" >
    /**
     * @return the acctcode
     */
      public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public List getAnios() {
        return Anios;
    }

    public void setAnios(List Anios) {
        this.Anios = Anios;
    }

    public TreeNode getAcc() {
        return acc;
    }

    public void setAcc(TreeNode acc) {
        this.acc = acc;
    }

    public Double getCuuentaMayor() {
        return cuuentaMayor;
    }

    public void setCuuentaMayor(Double cuuentaMayor) {
        this.cuuentaMayor = cuuentaMayor;
    }

    public Documentnew getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(Documentnew selectedTable) {
        this.selectedTable = selectedTable;
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

    /**
     * @param nodeDetail the nodeDetail to set
     */
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
//</editor-fold> 
   
//<editor-fold defaultstate="collapsed" desc="Load de la pantalla">
    //load de la pantalla    

    @PostConstruct
    public void initForm() {
         try {
        if (accEJBService == null) {
            accEJBService = new AccountingEJBClient();
        }
        if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            this.Anios= AdminEJBService.findCatalog(CATALOGANIO);
       
            this.setTreeAcc(accEJBService.getTreeAccount());

            //Crear arbol de cuentas contables
            this.root = createDocumentnews();
            root.setExpanded(true);

        } catch (Exception e) {
        }
    }

    public TreeNode createDocumentnews() {
        Double monto = 0.0;
        acc = new DefaultTreeNode(new Documentnew("Cuenta", "Saldo", "Comentario", "post", monto), null);

        List AccDetLst = new Vector();
        AccDetLst = this.TreeAcc;
        Iterator<AccountTO> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            AccountTO accdetail = (AccountTO) iterator.next();
            TreeNode documents = new DefaultTreeNode(new Documentnew(accdetail.getAcctcode() + " - " + accdetail.getAcctname(), accdetail.getCurrtotal().toString(), accdetail.getAcctcode(), accdetail.getPostable(), null), acc);
            //Asignar hijos
            if (accdetail.getChild()!= null) {
                createDocumentnewsChild(documents, accdetail.getChild());
                documents.setExpanded(true);
            }
        }
        acc.getChildren().get(0).setExpanded(true);
        return acc;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="create documentChild">
    public void createDocumentnewsChild(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
         
        Iterator<AccountTO> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            AccountTO accdetail = (AccountTO) iterator.next();
            TreeNode documents = new DefaultTreeNode(new Documentnew(accdetail.getAcctcode() + " - " + accdetail.getAcctname(), "$ " + accdetail.getCurrtotal().toString(), accdetail.getAcctcode(), accdetail.getPostable(), null), padre);
            //Asignar hijos
            if (accdetail.getChild() != null) {
                createDocumentnewsChild(documents, accdetail.getChild());
                documents.setExpanded(true);
            }
            Documentnew DocPadre = null;
            Documentnew DocHijo = null;
            DocPadre = (Documentnew) padre.getData();
            DocPadre.setMonto(monto);
            DocHijo = (Documentnew) documents.getData();
            DocHijo.setMonto(monto);
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="calculo de mayores">
     public void CalculoDocument(){
       DeleteDocument();
       UpdateDocument();
       facesMessage("Datos actualizados correctamente");
   }
     
    public void DeleteDocument() {
        List AccDetLst = new Vector();
        AccDetLst = acc.getChildren();
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                DeleteDocumentChild(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
        }
    }

    public void DeleteDocumentChild(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
               DeleteDocumentChild(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
            Documentnew DocPadre = null;
            DocPadre = (Documentnew) padre.getData();
            DocPadre.setMonto(monto);
            
        }
    }
    
    
    public void UpdateDocument() {
        List AccDetLst = new Vector();
        AccDetLst = acc.getChildren();
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                UpdateDocumentChild(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
        }
    }

    public void UpdateDocumentChild(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                UpdateDocumentChild(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
            Documentnew DocPadre = null;
            Documentnew DocHijo = null;
            DocPadre = (Documentnew) padre.getData();
            if(DocPadre==null){
                DocPadre.setMonto(monto);
            }
            DocHijo = (Documentnew) accdetail.getData();
            if(DocHijo==null){
                DocHijo.setMonto(monto);
            }
            DocPadre.setMonto(DocPadre.getMonto() + DocHijo.getMonto());
        }
    }
//</editor-fold>
   
//<editor-fold defaultstate="collapsed" desc="guardar en base">
        public void Save(){
            if(Validar()){
               List BudgetList= new Vector();
               String fechaSa=anio+"-1-1";
                DateFormat forma;
                forma= new SimpleDateFormat("yyyy-MM-dd");
                 try {
                     fechaSave=forma.parse(fechaSa);
                 } catch (ParseException ex) {
                     Logger.getLogger(ChartAccountsBudget.class.getName()).log(Level.SEVERE, null, ex);
                 }
               String formato="yyyy";
               SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
               anio2= Integer.parseInt(dateFormat.format(fechaSave));
                    
               BudgetList=accEJBService.getBudget(anio2);
               if(BudgetList.size()!=0){
                   CalculoDocument();
                   UpdateDocumentX();
               }
               else{
                   CalculoDocument();
                    SaveDocument();
               }
                 
                facesMessage("Datos guardados con Éxito");
            }else{
                facesMessage("Seleccione un año");
            }
           
        }
  
       public void SaveDocument() {
        
        List AccDetLst = new Vector();
        AccDetLst = acc.getChildren();
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                SaveDocumentChild(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
                    BudgetTO newBudget = new BudgetTO();
                    Documentnew padre2=null;
                    padre2=(Documentnew) accdetail.getData();
                    newBudget.setDebltotal(padre2.getMonto());
                    newBudget.setFathercode(null);
                    newBudget.setAcctcode(padre2.getType());
                    newBudget.setFinancyear(fechaSave);
                    newBudget.setBgdcode(anio2);
            try {
                accEJBService.cat_budget_mtto(newBudget,Common.MTTOINSERT);
            } catch (Exception ex) {
                Logger.getLogger(ChartAccountsBudget.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void SaveDocumentChild(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                SaveDocumentChild(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
                BudgetTO newBudget = new BudgetTO();
                Documentnew hijo=null;
                Documentnew padre2=null;
                hijo=(Documentnew) accdetail.getData();
                if(hijo==null){
                     newBudget.setDebltotal(monto);
                }else{
                    newBudget.setDebltotal(hijo.getMonto());
                }
                    padre2=(Documentnew) padre.getData();
                    newBudget.setFathercode(padre2.getType());
                    newBudget.setAcctcode(hijo.getType());
                    newBudget.setFinancyear(fechaSave);
                    newBudget.setBgdcode(anio2);
            try {
                accEJBService.cat_budget_mtto(newBudget,Common.MTTOINSERT);
            } catch (Exception ex) {
                Logger.getLogger(ChartAccountsBudget.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Update en base">
    public void UpdateDocumentX() {
        List AccDetLst = new Vector();
        AccDetLst = acc.getChildren();
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                UpdateDocumentChildX(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
                    BudgetTO newBudget = new BudgetTO();
                    Documentnew padre2=null;
                    padre2=(Documentnew) accdetail.getData();
                    newBudget.setDebltotal(padre2.getMonto());
                    newBudget.setFathercode(null);
                    newBudget.setAcctcode(padre2.getType());
                    newBudget.setFinancyear(fechaSave);
                    newBudget.setBgdcode(anio2);
            try {
                accEJBService.cat_budget_mtto(newBudget,Common.MTTOUPDATE);
            } catch (Exception ex) {
                Logger.getLogger(ChartAccountsBudget.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void UpdateDocumentChildX(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
        Iterator<TreeNode> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            TreeNode accdetail = (TreeNode) iterator.next();
            //Asignar hijos
            if (accdetail.getChildCount() != 0) {
                UpdateDocumentChildX(accdetail, accdetail.getChildren());
                //accdetail.setExpanded(true);
            }
                BudgetTO newBudget = new BudgetTO();
                Documentnew hijo=null;
                Documentnew padre2=null;
                hijo=(Documentnew) accdetail.getData();
                if(hijo==null){
                     newBudget.setDebltotal(monto);
                }else{
                    newBudget.setDebltotal(hijo.getMonto());
                }
                    padre2=(Documentnew) padre.getData();
                    newBudget.setFathercode(padre2.getType());
                    newBudget.setAcctcode(hijo.getType());
                    newBudget.setFinancyear(fechaSave);
                    newBudget.setBgdcode(anio2);
            try {
                accEJBService.cat_budget_mtto(newBudget,Common.MTTOUPDATE);
            } catch (Exception ex) {
                Logger.getLogger(ChartAccountsBudget.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar en base">
    public void SearchBudget(){
        List BudgetList= new Vector();
        if(!anio.equals("-1")){
            BudgetList=accEJBService.getBudget(Integer.parseInt(anio));
            if(!BudgetList.isEmpty()){
                LlenarPantalla(BudgetList);
            }
            else{
                this.root = createDocumentnews();
                root.setExpanded(true);
                RequestContext.getCurrentInstance().update("principal");
                facesMessage("No hay datos para ese año, ingrese el presupuesto");
            }
        }
    }
    public void LlenarPantalla(List BudgetList){
        DeleteDocument();
        root=null;
        root=searchDocumentnews(BudgetList);
        root.setExpanded(true);
        RequestContext.getCurrentInstance().update("principal");
        facesMessage("Datos encontrados para el año seleccionado");
    }
    public TreeNode searchDocumentnews(List budget) {
        List AccDetLst = new Vector();
        acc = new DefaultTreeNode(new Documentnew("Cuenta", "Saldo", "Comentario", "post", 5.5), null);
        AccDetLst = budget;
        Iterator<BudgetTO> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            BudgetTO accdetail = (BudgetTO) iterator.next();
            TreeNode documents = new DefaultTreeNode(new Documentnew(accdetail.getAcctcode() + " - " + accdetail.getAcctname(), accdetail.getCurrtotal().toString(), accdetail.getAcctcode(), accdetail.getPostable(),accdetail.getDebltotal()), acc);
            //Asignar hijos
            if (accdetail.getNodeDetail() != null) {
                searchDocumentnewsChild(documents, accdetail.getNodeDetail());
                documents.setExpanded(true);
            }
        }
        acc.getChildren().get(0).setExpanded(true);
        return acc;
    }
 public void searchDocumentnewsChild(TreeNode padre, List hijos) {
        List AccDetLst = new Vector();
        AccDetLst = hijos;
         
        Iterator<BudgetTO> iterator = AccDetLst.iterator();
        while (iterator.hasNext()) {
            BudgetTO accdetail = (BudgetTO) iterator.next();
            TreeNode documents = new DefaultTreeNode(new Documentnew(accdetail.getAcctcode() + " - " + accdetail.getAcctname(), "$ " + accdetail.getCurrtotal().toString(), accdetail.getAcctcode(), accdetail.getPostable(), accdetail.getDebltotal()), padre);
            //Asignar hijos
            if (accdetail.getNodeDetail() != null) {
                searchDocumentnewsChild(documents, accdetail.getNodeDetail());
                documents.setExpanded(true);
            }
           
        }
    }
//</editor-fold>
      
//<editor-fold defaultstate="collapsed" desc="funciones varias">
    public boolean Validar(){
        if(anio.equals("-1"))
            return false;
        else
            return true;
    }
   private void facesMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var)); //To change body of generated methods, choose Tools | Templates.
    } 
   public void keyTyped( ValueChangeEvent e ) {
    String c = e.getNewValue().toString();
    if(isNumeric(c))
        facesMessage("bien");
    else 
        facesMessage("mal");
}
   
//</editor-fold>
   
private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}


}
