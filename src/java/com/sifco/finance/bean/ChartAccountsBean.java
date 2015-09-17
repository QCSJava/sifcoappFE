/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.finance.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.objects.accounting.to.AccountTO;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "chartAccountsBean")
@SessionScoped
public class ChartAccountsBean implements Serializable {

    public ChartAccountsBean() {
    }

//<editor-fold defaultstate="collapsed" desc="Variables">
    //servicios
    private static AccountingEJBClient accEJBService;

    //__________________________________________________________________________
    //variables de pantalla
    private String AccCode;     //codigo nuevo de cuenta    
    private String AccName;     //nombre nuevo de cuenta
    private TreeNode root1;     //arbol 1
    private TreeNode root2;     //arbol 2
    private TreeNode rootAux;
    private String opcion;      //Seleccion del radio buton

    //__________________________________________________________________________
    //prime
    private TreeNode selectedNode1;     //nodo arbol 1
    private TreeNode selectedNode2;     //nodo arbol 2
    private List TreeAcc;               //lista para llenar arbol 2

    //__________________________________________________________________________
    //dragg and drop
    private TreeNode dr, dp;
    private int index;
    private boolean validCode = true;
    //__________________________________________________________________________

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="G & S">
    public TreeNode getRootAux() {
        return rootAux;
    }

    public void setRootAux(TreeNode rootAux) {
        this.rootAux = rootAux;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public List getTreeAcc() {
        return TreeAcc;
    }

    public void setTreeAcc(List TreeAcc) {
        this.TreeAcc = TreeAcc;
    }

    public String getAccCode() {
        return AccCode;
    }

    public void setAccCode(String AccCode) {
        this.AccCode = AccCode;
    }

    public String getAccName() {
        return AccName;
    }

    public void setAccName(String AccName) {
        this.AccName = AccName;
    }

    public TreeNode getRoot1() {
        return root1;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot1(TreeNode root1) {
        this.root1 = root1;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public TreeNode getSelectedNode1() {
        return selectedNode1;
    }

    public void setSelectedNode1(TreeNode selectedNode1) {
        this.selectedNode1 = selectedNode1;
    }

    public TreeNode getSelectedNode2() {
        return selectedNode2;
    }

    public void setSelectedNode2(TreeNode selectedNode2) {
        this.selectedNode2 = selectedNode2;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Init de la ventana">
    @PostConstruct
    public void init() {
        if (accEJBService == null) {
            accEJBService = new AccountingEJBClient();
        }

        AccountTO accNode = new AccountTO();
        accNode.setAcctname("-1");
        //llenar arbol 1
        root1 = new DefaultTreeNode(accNode, null);

        //llenar arbol 2
        root2 = new DefaultTreeNode(accNode, null);
        rootAux = new DefaultTreeNode(accNode, null);

        try {
            LLenarRoot2();
            LLenarRootAux1();
        } catch (Exception e) {
            facesMessage("Error");
        }

        //cloneRoot2_to_aux();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Clonar de root2 a rootAux">
    public void cloneRoot2_to_aux() {
        AccountTO accNode = null;
        rootAux = new DefaultTreeNode(accNode, null);
        List AccDetLst = new Vector();
        AccDetLst = this.root2.getChildren();

        for (Object obj : AccDetLst) {
            try {
                TreeNode node = (TreeNode) BeanUtils.cloneBean(obj);
                AccountTO accdetail = (AccountTO) node;
                TreeNode documents = new DefaultTreeNode(accdetail, rootAux);

                if (accdetail.getChild() != null) {
                    cloneRoot2(documents, accdetail.getChild());
                    documents.setExpanded(true);
                }
            } catch (Exception e) {
            }
        }
    }

    public void cloneRoot2(TreeNode padre, List hijos) {
        for (Object obj : hijos) {
            try {
                TreeNode node = (TreeNode) BeanUtils.cloneBean(obj);
                AccountTO accdetail = (AccountTO) node;
                TreeNode documents = new DefaultTreeNode(accdetail, padre);

                if (accdetail.getChild() != null) {
                    cloneRoot2(documents, accdetail.getChild());
                    documents.setExpanded(true);
                }
            } catch (Exception e) {
            }
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Clonar de root2 a rootAux">
    public void cloneAux_to_Root2() {
        AccountTO accNode = null;
        rootAux = new DefaultTreeNode(accNode, null);
        List AccDetLst = new Vector();
        AccDetLst = this.root2.getChildren();

        for (Object obj : AccDetLst) {
            try {
                TreeNode node = (TreeNode) BeanUtils.cloneBean(obj);
                AccountTO accdetail = (AccountTO) node;
                TreeNode documents = new DefaultTreeNode(accdetail, rootAux);

                if (accdetail.getChild() != null) {
                    cloneRoot2(documents, accdetail.getChild());
                    documents.setExpanded(true);
                }
            } catch (Exception e) {
            }
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Root 2">
    public void LLenarRoot2() {
        try {
            this.setTreeAcc(accEJBService.getTreeAccount());
        } catch (Exception ex) {
            Logger.getLogger(ChartAccountsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Crear arbol de cuentas contables
        this.root2 = createDocuments();
        root2.setExpanded(true);
    }

    private void LLenarRootAux1() {
        try {
            this.setTreeAcc(accEJBService.getTreeAccount());
        } catch (Exception ex) {
            Logger.getLogger(ChartAccountsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Crear arbol de cuentas contables
        this.rootAux = createDocuments();
        rootAux.setExpanded(true);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Crear Arbol Cuentas">
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
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="create documentChild">
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

//<editor-fold defaultstate="collapsed" desc="Boton Agregar Cuenta">
    public void addAcc() {
        this.validCode = true;
        validateAccCode();
        if (validateAdd() && this.validCode) {
            this.validCode = false;
            this.root1.getChildren().clear();
            AccountTO newNode = new AccountTO();
            newNode.setAcctname(this.AccName);
            newNode.setAcctcode(this.AccCode);
            newNode.setPostable(this.opcion);
            newNode.setFinanse("Y");
            newNode.setObjtype(4 + "");

            TreeNode treeNode = new DefaultTreeNode(newNode, root1);
            treeNode.setExpanded(true);

            RequestContext.getCurrentInstance().update("tree1");
        }
    }

    public void addAcc2() {
        this.root1.getChildren().clear();
        AccountTO newNode = new AccountTO();
        newNode.setAcctname(this.AccName);
        newNode.setAcctcode(this.AccCode);
        newNode.setPostable(this.opcion);
        newNode.setFinanse("Y");

        TreeNode treeNode = new DefaultTreeNode(newNode, root1);
        treeNode.setExpanded(true);

        RequestContext.getCurrentInstance().update("tree1");
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="deleteNode">
    public void deleteNode() {
        selectedNode2.getChildren().clear();
        selectedNode2.getParent().getChildren().remove(selectedNode2);
        selectedNode2.setParent(null);

        selectedNode2 = null;

    }

    public void deleteNodeFromRoot(TreeNode node) {
        node.getChildren().clear();
        node.getParent().getChildren().remove(node);
        node.setParent(null);

        node = null;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="onDragDrop">
    public void onDragDrop(TreeDragDropEvent event) {

        this.dr = event.getDragNode();  //origen
        this.dp = event.getDropNode();  //destino
        this.index = event.getDropIndex();

        AccountTO ant, des = new AccountTO();
        ant = (AccountTO) dr.getData();
        des = (AccountTO) dp.getData();

        if (!dp.getRowKey().equals("root") && validateOrigen(ant) && validateDestino(des)) {
            facesMessage("Arrastrado " + ant.getAcctname() + " Agregado en " + des.getAcctname() + " posicion " + (index + 1) + " " + ant.getPostable());
            this.AccCode = null;
            this.AccName = null;
            this.opcion = null;

            cloneRoot2_to_aux();
            //try {
            //    rootAux = (TreeNode) BeanUtils.cloneBean(root2);
            //} catch (Exception e) {
            //}
            //rootAux = root2;
            //setRootAux(root2);
            RequestContext.getCurrentInstance().update("frmAccounts");

        } else {
            facesMessage("Esta cuenta no puede ser agregada en esta Posicion");

            if (true) {//ant.getFinanse() != null && ant.getFinanse().equals("Y")) {
                setAccCode(ant.getAcctcode());
                setAccName(ant.getAcctname());
                setOpcion(ant.getPostable());
                addAcc2();
            }
            if (ant.getLevels() == 1) {
                LLenarRoot2();
                this.AccCode = null;
                this.AccName = null;
                this.opcion = null;
                this.root1.getChildren().clear();
            }

            cloneAux_to_Root2();
            //root2 = rootAux;
            deleteNodeFromRoot(dr);
            //setRoot2(getRootAux());
            //try {
            //    root2 = null;
            //    root2 = (TreeNode) BeanUtils.cloneBean(rootAux);
            //} catch (Exception e) {
            //}

            this.dr = null;
            this.dp = null;
            RequestContext.getCurrentInstance().update("frmAccounts");
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Boton Prinipal">
    public void btnPrincipal() {
        //facesMessage("Entro al SAVE");
        doSave();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {
        //accEJBService.cat_acc0_ACCOUNT_mtto(null, index);
        List AccDetLst = new Vector();
        List lstAcc = new Vector();
        AccDetLst = root2.getChildren();

        for (Object node : AccDetLst) {
            int cont = 1;
            int nivel = 1;
            TreeNode acc = (TreeNode) node;
            AccountTO padre = (AccountTO) acc.getData();
            if (acc.getChildCount() > 0) {
                doSaveAux(padre, acc.getChildren(), lstAcc, cont, nivel + 1);
            }

            AccountTO newAcc = (AccountTO) acc.getData();
            newAcc.setFinanse(null);
            newAcc.setFathernum(null);
            newAcc.setGrpline(cont);
            lstAcc.add(newAcc);
        }

        //save
        try {
            ResultOutTO res;
            if (accEJBService == null) {
                accEJBService = new AccountingEJBClient();
            }
            res = accEJBService.saveTreeAccount(lstAcc);
            if (res.getCodigoError() == 0) {
                facesMessage(res.getMensaje());
            } else {
                facesMessage(res.getMensaje());
            }
        } catch (Exception e) {
            facesMessage(e.getMessage() + " " + e.getCause());
        }
    }

    public void doSaveAux(AccountTO padre, List hijos, List lstAcc, int cont, int nivel) {
        for (Object node : hijos) {
            cont++;
            TreeNode acc = (TreeNode) node;

            AccountTO newAcc = (AccountTO) acc.getData();
            newAcc.setFinanse(null);
            newAcc.setFathernum(padre.getAcctcode());
            newAcc.setGroupmask(padre.getGroupmask());
            newAcc.setGrpline(cont);
            newAcc.setLevels(nivel);
            newAcc.setCurrtotal(0.0);
            newAcc.setEndtotal(0.0);

            if (acc.getChildCount() > 0) {
                AccountTO padreH = (AccountTO) acc.getData();
                doSaveAux(padreH, acc.getChildren(), lstAcc, cont, nivel + 1);
            }

            lstAcc.add(newAcc);
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Funciones de Validaciones">
    public boolean validateDestino(AccountTO des) {
        if (!des.getPostable().equals("N")) {
            return false;
        }

        return true;
    }

    public boolean validateOrigen(AccountTO org) {
        if (org.getLevels() == 1) {
            return false;
        }
        if (org.getAcctcode() == null || org.getAcctcode().isEmpty() || org.getAcctcode().equals("")) {
            return false;
        }
        if (org.getAcctname() == null || org.getAcctname().isEmpty() || org.getAcctname().equals("")) {
            return false;
        }

        return true;
    }

    public void validateAccCode() {
        List nodes = root2.getChildren();
        for (Object node : nodes) {
            TreeNode accdetail = (TreeNode) node;

            if (accdetail.getChildCount() > 0) {
                validateAccCode2(accdetail.getChildren());
            }

            AccountTO hijo = new AccountTO();
            hijo = (AccountTO) accdetail.getData();
            if (this.AccCode.equals(hijo.getAcctcode())) {
                this.validCode = false;
                facesMessage("Codigo invalido, esta repetido");
            }
        }
    }

    public void validateAccCode2(List nodes) {

        for (Object node : nodes) {
            TreeNode accdetail = (TreeNode) node;

            if (accdetail.getChildCount() > 0) {
                validateAccCode2(accdetail.getChildren());
            }

            AccountTO hijo = new AccountTO();
            hijo = (AccountTO) accdetail.getData();
            if (this.AccCode.equals(hijo.getAcctcode())) {
                this.validCode = false;
                facesMessage("Codigo invalido, esta repetido");
            }
        }
    }

    public boolean validateDragg(String post) {
        return post.equals("N");
    }

    public boolean validateAdd() {
        if (this.AccName.equals("") || this.AccName == null || this.AccName.isEmpty()) {
            facesMessage("Ingrese el Nombre de la Cuenta");
            return false;
        }
        if (this.AccCode.equals("") || this.AccCode == null || this.AccCode.isEmpty()) {
            facesMessage("Ingrese el Codigo de la Cuenta");
            return false;
        }
        if (this.opcion == null || this.opcion.isEmpty() || this.opcion.equals("")) {
            facesMessage("Seleccione el tipo de cuenta");
            return false;
        }
        return true;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Funciones Varias">
    public void reload() throws IOException {
        // ...

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    private void facesMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var)); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>

}//Cierre de Clase
