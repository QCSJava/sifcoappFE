/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.prueba.model.primefaces.Util;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.accounting.to.JournalEntryTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.security.to.AdmProfileTO;
import com.sifcoapp.objects.security.to.ProfileDetOutTO;
import com.sifcoapp.objects.security.to.ProfileOutTO;
import com.sifcoapp.objects.security.to.ProfileTO;
import com.sifcoapp.objects.security.to.UserAppInTO;
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
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "profileBean")
@SessionScoped
public class ProfileBean implements Serializable{

//<editor-fold defaultstate="collapsed" desc="declaracion de variables">
    //variables varias
    private boolean disable;
    private boolean disable2;
    private int varEstados;
    private String botonEstado;
    private boolean confirm;
    //variables para el perfil creado para el datatable
    private int profilecode;
    private String profilename;
    private boolean active;
    //variables para la asignacion de permisos
    private String doccode;
    private String code;
    //TO y EJBS
    SecurityEJBClient SecurityEJBService;
    //listas
    List listaPadre = new Vector();
    ArrayList<ProfileTO> listaDetalles = new ArrayList<>();
    private List listaBusqueda = new Vector();
    private ArrayList<ProfileTO> listaBusquedaTable = new ArrayList<>();
    private Object selectJournal = new JournalEntryTO();
    private ArrayList<ProfileTO> selectDetail = new ArrayList<>();
    private List codes = new Vector();

    //para el arbol
    private TreeNode root;
    private TreeNode[] selectedNode;
    private List TreeAcc;
    TreeNode acc = null;
    HttpSession session = Util.getSession();
    UserAppInTO usr = new UserAppInTO();
    ProfileOutTO profileOutTO = new ProfileOutTO();
    int bandera = 0;

    //msj
    private String msj;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ProfileBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getCodes() {
        return codes;
    }

    public void setCodes(List codes) {
        this.codes = codes;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode[] getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode[] selectedNode) {
        this.selectedNode = selectedNode;
    }

    public ArrayList<ProfileTO> getSelectDetail() {
        return selectDetail;
    }

    public void setSelectDetail(ArrayList<ProfileTO> selectDetail) {
        this.selectDetail = selectDetail;
    }

    public void setDoccode(String doccode) {
        this.doccode = doccode;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisable2() {
        return disable2;
    }

    public void setDisable2(boolean disable2) {
        this.disable2 = disable2;
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

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public List getListaPadre() {
        return listaPadre;
    }

    public void setListaPadre(List listaPadre) {
        this.listaPadre = listaPadre;
    }

    public ArrayList<ProfileTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(ArrayList<ProfileTO> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<ProfileTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<ProfileTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    public Object getSelectJournal() {
        return selectJournal;
    }

    public void setSelectJournal(Object selectJournal) {
        this.selectJournal = selectJournal;
    }

    public int getProfilecode() {
        return profilecode;

    }

    public void setProfilecode(int profilecode) {
        this.profilecode = profilecode;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDoccode() {
        return doccode;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="init de la ventana">
    @PostConstruct
    public void initForm() {
        if (SecurityEJBService == null) {
            SecurityEJBService = new SecurityEJBClient();
        }
        try {
            listaPadre = SecurityEJBService.getProfile(null);// para obtener todos los perfiles
            Iterator<ProfileTO> iterator = listaPadre.iterator();
            while (iterator.hasNext()) {
                ProfileTO nuevo = (ProfileTO) iterator.next();
                if (nuevo.getActive().equals("Y")) {
                    nuevo.setActive("true");
                } else {
                    nuevo.setActive("false");
                }
                nuevo.setProfilecode(nuevo.getProfilecode());
                nuevo.setProfilename(nuevo.getProfilename());
                codes.add(nuevo.getProfilename());
                listaDetalles.add(nuevo);
            }
            TreeAcc = SecurityEJBService.GetUserProfile_Mtto(0);
            this.root = createDocumentnews(TreeAcc);

        } catch (Exception ex) {
            Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Agregar detalles al dataTable (tab perfiles de usuario)" > 
    public void accionAgregar(ActionEvent actionEvent) {
        try {
            if (validarNull()) {
                showHideDialog("dlg9", 1);
            }
        } catch (Exception e) {
        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Eliminar del dataTable (tab perfiles de usuario)" > 
    public void deleteDetalle() {
        try {
            boolean var1, var2;
            var1 = getListaDetalles().remove(this.selectDetail.get(0));
            var2 = listaPadre.remove(this.selectDetail.get(0));

            if (var1 && var2) {
                SecurityEJBService.Usr1_profile_mtto(selectDetail.get(0), Common.MTTODELETE);
                faceMessage("Perfil Eliminado");

            } else {
                faceMessage("Seleccione un perfil para eliminar");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "---" + e.getCause());
            faceMessage("No pudo eliminarse la entrada: " + e.getMessage());
        }
        this.selectDetail = null;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Dialogs Confirm">
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
        saveProfile();
        RequestContext.getCurrentInstance().update("formPerfil");
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg9').hide();");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="validaciones">
    public boolean validarNull() {
        if (profilecode == 0) {
            faceMessage("Digite un código de perfil");
            return false;
        } else {
            if (profilename.isEmpty()) {
                faceMessage("Digite un nombre de perfil");
                return false;
            } else {
                return true;
            }
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="guardar en base (tab perfiles de usuario)">
    public void saveProfile() {
        if (listaDetalles == null) {
            listaDetalles = new ArrayList<ProfileTO>();
        }
        if (listaPadre == null) {
            listaPadre = new Vector();
        }
        ResultOutTO _return = new ResultOutTO();
        ProfileTO nuevoDetalle = new ProfileTO();
        try {
            // por defecto al ser insertado es true
            nuevoDetalle.setActive(active ? "Y" : "N");
            nuevoDetalle.setProfilecode(profilecode);
            nuevoDetalle.setProfilename(profilename);
            _return = SecurityEJBService.Usr1_profile_mtto(nuevoDetalle, Common.MTTOINSERT);

        } catch (Exception e) {
            faceMessage("Error no se pudo completar la insercion verifique que el código no exista");
        }
        if (_return.getCodigoError() == 0) {
            nuevoDetalle.setActive("true");
            listaPadre.add(nuevoDetalle);
            listaDetalles.add(nuevoDetalle);
            faceMessage(_return.getMensaje());
            cleanDetalle();
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="update en base a los estados de los perfiles (tab perfiles de usuario)">
    public void updateProfile() {
        ResultOutTO _return = new ResultOutTO();
        Iterator<ProfileTO> iterator = listaDetalles.iterator();
        while (iterator.hasNext()) {
            ProfileTO nuevo = (ProfileTO) iterator.next();
            if (nuevo.getActive().equals("true")) {
                nuevo.setActive("Y");
            } else {
                nuevo.setActive("N");
            }
            _return = SecurityEJBService.Usr1_profile_mtto(nuevo, Common.MTTOUPDATE);
            if (nuevo.getActive().equals("Y")) {
                nuevo.setActive("true");
            } else {
                nuevo.setActive("false");
            }
        }
        if (_return.getCodigoError() == 0) {
            faceMessage(_return.getMensaje());
        } else {
            faceMessage("Error no se pudo realizar la actualización");
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="funciones varias">
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }

    private void cleanDetalle() {
        profilecode = 0;
        profilename = null;
        active = true;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar en base (tab asignaciones)">
    public void SearchBudget() {
        List ProfileList = new Vector();
        if (!code.equals("-1")) {
            ProfileList = SecurityEJBService.GetUserProfile_Mtto(Integer.parseInt(code));
            if (!ProfileList.isEmpty()) {
                LlenarPantalla(ProfileList);
            } else {
                this.root = createDocumentnews(ProfileList);
                root.setExpanded(true);
                //RequestContext.getCurrentInstance().update("formPerfil");
                faceMessage("No hay datos para ese perfil");
            }
        }
    }

    public void LlenarPantalla(List ProfileList) {
        //DeleteDocument();
        root = null;
        root = createDocumentnews(ProfileList);
        root.setExpanded(true);
        //RequestContext.getCurrentInstance().update("padre");
        faceMessage("Datos encontrados para el Perfil seleccionado");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="create document (tab asignaciones)">
    public TreeNode createDocumentnews(List ProfileList) {
        ProfileDetOutTO padre = new ProfileDetOutTO();

        acc = new CheckboxTreeNode(padre, null);
        for (Object node : ProfileList) {
            ProfileDetOutTO accdetail = (ProfileDetOutTO) node;
            TreeNode newNode = new CheckboxTreeNode(accdetail, acc);

            if (accdetail.getNodeDetail().size() > 0) {
                createDocumentnewsChild(newNode, accdetail.getNodeDetail());
            }
        }

        return acc;
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="create documentChild (tab asignaciones)">
    
    public void createDocumentnewsChild(TreeNode padre, List hijos) {

        for (Object node : hijos) {
            ProfileDetOutTO accdetail = (ProfileDetOutTO) node;
            TreeNode newNode = new CheckboxTreeNode(accdetail, padre);

            if (accdetail.getNodeDetail().size() > 0) {
                createDocumentnewsChild(newNode, accdetail.getNodeDetail());
            } else {
                if (accdetail.getStatus().equals("Y") && !(accdetail.getNodeDetail().size() > 0) && accdetail.getParent_id() != 0) {
                    newNode.setSelected(true);
                }
            }
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Actualizar en base (tab asignaciones)">
    
//<editor-fold defaultstate="collapsed" desc="create document (tab asignaciones)">
    public void updateDocumentnews() {
        ResultOutTO _return = new ResultOutTO();
        List AccDetLst = new Vector();
        AccDetLst = root.getChildren();

        for (Object node : AccDetLst) {
            TreeNode accdetail = (TreeNode) node;

            //Asignar hijos
            if (accdetail.getChildCount() > 0) {
                updateDocumentnewsChild(accdetail.getChildren());
            }

            ProfileDetOutTO DocHijo = (ProfileDetOutTO) accdetail.getData();
            AdmProfileTO nuevo = new AdmProfileTO();
            if (DocHijo != null) {
                if (DocHijo.getStatus().equals("N") && (accdetail.isPartialSelected() || accdetail.isSelected())) {
                    nuevo.setDoccode(DocHijo.getId_perfil_det() + "");
                    nuevo.setProfilecode(Integer.parseInt(this.code));
                    //System.out.println(DocHijo.getType() + " " + Integer.parseInt(this.code) + "insert");
                    try {
                        List adm = SecurityEJBService.getAdmProfile_by_key(DocHijo.getId_perfil_det());
                        if (!adm.contains(nuevo)) {
                            _return = SecurityEJBService.adm_profile_mtto(nuevo, Common.MTTOINSERT);
                            this.msj = _return.getMensaje();
                        }else
                            faceMessage("Perfil ya asignado");
                    } catch (Exception e) {
                        //faceMessage(e.getMessage() + "" + e.getCause());

                    }
                }//DocHijo.getSize().equals("Y") && 
                if (!accdetail.isPartialSelected() && !accdetail.isSelected()) {
                    nuevo.setDoccode(DocHijo.getId_perfil_det() + "");
                    nuevo.setProfilecode(Integer.parseInt(this.code));
                    try {
                        _return = SecurityEJBService.adm_profile_mtto(nuevo, Common.MTTODELETE);
                        //faceMessage(_return.getMensaje());
                        //System.out.println(_return.getMensaje());
                    } catch (Exception e) {
                        faceMessage(e.getMessage() + "" + e.getCause());
                    }

                }
            }
        }
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="create documentChild (tab asignaciones)">
    public void updateDocumentnewsChild(List hijos) {
        ResultOutTO _return = new ResultOutTO();

        for (Object node : hijos) {
            TreeNode accdetail = (TreeNode) node;

            //Asignar hijos
            if (accdetail.getChildCount() > 0) {
                updateDocumentnewsChild(accdetail.getChildren());
            }

            AdmProfileTO nuevo = new AdmProfileTO();
            ProfileDetOutTO DocHijo = (ProfileDetOutTO) accdetail.getData();
            if (DocHijo != null) {
                if (DocHijo.getStatus().equals("N") && (accdetail.isPartialSelected() || accdetail.isSelected())) {
                    nuevo.setDoccode(DocHijo.getId_perfil_det() + "");
                    nuevo.setProfilecode(Integer.parseInt(this.code));
                    //System.out.println(DocHijo.getType() + " " + Integer.parseInt(this.code) + "insert");
                    try {
                        List adm = SecurityEJBService.getAdmProfile_by_key(DocHijo.getId_perfil_det());
                        if (!adm.contains(nuevo)) {
                            _return = SecurityEJBService.adm_profile_mtto(nuevo, Common.MTTOINSERT);
                            this.msj = _return.getMensaje();
                        }else
                            faceMessage("Perfil ya asignado");
                    } catch (Exception e) {
                       // faceMessage(e.getMessage() + "" + e.getCause());

                    }
                }//DocHijo.getSize().equals("Y") && 
                if (!accdetail.isPartialSelected() && !accdetail.isSelected()) {
                    nuevo.setDoccode(DocHijo.getId_perfil_det() + "");
                    nuevo.setProfilecode(Integer.parseInt(this.code));
                    try {
                        _return = SecurityEJBService.adm_profile_mtto(nuevo, Common.MTTODELETE);
                        //faceMessage(_return.getMensaje());
                        //System.out.println(_return.getMensaje());
                    } catch (Exception e) {
                        faceMessage(e.getMessage() + "" + e.getCause());
                    }

                }
            }

        }

    }//cierre funcion

//</editor-fold>
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones dialogos">
    public void OpenDlgUpdate(ActionEvent actionEvent) {
        try {
            showHideDialog("dlgUp", 1);
        } catch (Exception e) {
            faceMessage("ERROR " + e.getCause());
        }

    }

    public void confirmUpdate(ActionEvent actionEvent) {
        showHideDialog("dlgUp", 2);
        updateDocumentnews();
        faceMessage(msj);
    }

    public void cancelUpdate(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgUp').hide();");
    }

//</editor-fold>
}//cierre de clase
