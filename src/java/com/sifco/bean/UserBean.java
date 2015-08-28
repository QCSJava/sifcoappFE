/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.prueba.model.primefaces.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.security.to.ProfileTO;
import com.sifcoapp.objects.security.to.UserTO;
import com.sifcoapp.objects.utilities.PasswordService;
import com.sifcoapp.report.bean.ReportsBean;
import com.sifcoapp.report.common.AbstractReportBean.ExportOption;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "user")
@ApplicationScoped
public class UserBean implements Serializable {

    HttpSession session = Util.getSession();

//<editor-fold defaultstate="collapsed" desc="Declaracion Variables" >
    public UserBean() {
    }
    private Integer usersign;
    private String nickname;
    private String username;
    private String lastname;
    private String password = "-1";
    private int profilecode;
    private String locked;
    private Date userdate;
    private Integer cusersign;

    private List<ProfileTO> profilesLst;// = new ArrayList<ProfileTO>();
    private AdminEJBClient AdminEJBService;
    //private static final String CATALOGOPROFILES = "cg_profiles";
    private SecurityEJBClient SecurityEJBService;

    private int formAction;
    private final Logger log = Logger.getLogger(getClass().getName());

    //__________________________________________________________________________
    //__________________________________________________________________________
    private List listaBusqueda = new Vector();
    private ArrayList<UserTO> listaBusquedaTable = new ArrayList<>();

    private UserTO newUser = new UserTO();
    private UserTO selectU = new UserTO();

    private int varEstados;
    private String botonEstado;

    private boolean disable;

    private boolean reNick, roNick;

    private String exportOption;

    @ManagedProperty(value = "#{reportsBean}")
    private ReportsBean bean;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Botones Toolbar" >    
    public void doNew(ActionEvent actionEvent) {
        cleanBean();
        estateGuardar();
       // System.out.println("entra doNew");
        //this.setFormAction(Common.MTTOINSERT);
        //log.info("doNew log" + this.getFormAction());

        //return "";
    }

    public void botonBuscar(ActionEvent actionEvent) {
        cleanBean();
        estateBuscar();
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Boton Principal" >
    public void botonPrincipal(ActionEvent actionEvent) {
        switch (varEstados) {
            case 1:
                showHideDialog("dlgC", 1);
                //doSave();
                break;

            case 2:
                showHideDialog("dlgC", 1);
                //doUpdate();
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
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buscar Usuario en base" >    
    private void searchUser() {
        if (nickname.isEmpty()) {
            try {
                this.setListaBusqueda(SecurityEJBService.getUser());
            } catch (Exception e) {
                faceMessage("Error al realizar busqueda");
            }

            faceMessage("Seleccione un elemento");

            for (Object user : listaBusqueda) {
                UserTO var = (UserTO) user;
                listaBusquedaTable.add(var);
            }
            RequestContext.getCurrentInstance().update("dlgListUser");
            showHideDialog("dlgU", 1);
        } else {
            try {
                UserTO var2 = SecurityEJBService.getUserByNickname(nickname);
                if (var2.getNickname().isEmpty()) {
                    faceMessage("No se encontro usuario");
                } else {
                    llenarPantallaUser(var2);
                    estateActualizar();
                }
            } catch (Exception ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                faceMessage("No se encontro usuario");
            }
        }
    }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Guardar Usuario en base" > 
    public void doSave() {
        //String _return = "KO";
        if (SecurityEJBService == null) {
            SecurityEJBService = new SecurityEJBClient();
        }
        int _resultsp;
        int _action = 0;
        //UserTO parameters = new UserTO();

        //newUser.setUsersign((int) session.getAttribute("usersign"));
        //System.out.println("Nickname" + this.getNickname());
        newUser.setNickname(this.getNickname());
        newUser.setUsername(this.getUsername());
        newUser.setLastname(this.getLastname());
        try {
            newUser.setPassword(PasswordService.getInstance().encrypt(this.getPassword()));
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        newUser.setProfilecode(this.getProfilecode());
        newUser.setLocked(this.getLocked() == "true" ? "S" : "N");

        log.info("FormAction log" + this.getFormAction());
        /* if (this.varEstados == Common.MTTOINSERT) {
         _action = Common.MTTOINSERT;

         } else {
         if (this.varEstados == Common.MTTOUPDATE) {
         _action = Common.MTTOUPDATE;
         newUser.setUsersign((int) session.getAttribute("usersign"));
         }
         }*/
        //System.out.println("FormAction" + this.getFormAction());
        //parameters.setCusersign(0);

        // Agregar
        try {
            _resultsp = SecurityEJBService.cat_users_mtto(newUser, 1);// _action);
            faceMessage("Usuario Guardado Satisfactoriamente");
            estateActualizar();
        } catch (Exception e) {

            faceMessage("Identificador ya existe");
        }

        // return _return;
    }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Update Usuario en base" >
    public void doUpdate() {
        //String _return = "KO";
        if (SecurityEJBService == null) {
            SecurityEJBService = new SecurityEJBClient();
        }
        int _resultsp;
        int _action = 0;
        //UserTO parameters = new UserTO();

        newUser.setUsersign(this.usersign);
        //System.out.println("Nickname" + this.getNickname());
        newUser.setNickname(this.getNickname());
        newUser.setUsername(this.getUsername());
        newUser.setLastname(this.getLastname());
        if (!this.password.equals("")) {
            try {
                newUser.setPassword(PasswordService.getInstance().encrypt(this.getPassword()));
            } catch (Exception ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        newUser.setProfilecode(this.getProfilecode());
        newUser.setLocked(this.getLocked() == "true" ? "S" : "N");

        log.info("FormAction log" + this.getFormAction());
        /* if (this.varEstados == Common.MTTOINSERT) {
         _action = Common.MTTOINSERT;

         } else {
         if (this.varEstados == Common.MTTOUPDATE) {
         _action = Common.MTTOUPDATE;
         newUser.setUsersign((int) session.getAttribute("usersign"));
         }
         }*/
        //System.out.println("FormAction" + this.getFormAction());
        //parameters.setCusersign(0);

        // Agregar
        try {
            _resultsp = SecurityEJBService.cat_users_mtto(newUser, 2);// _action);
            faceMessage("Usuario Guardado Satisfactoriamente");
            estateActualizar();
        } catch (Exception e) {

            faceMessage("Error al guardar Usuario");
        }

        // return _return;
    }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Manejo de estados de la pantalla GUARDAR; ACTUALIZAR; BUSCAR; NUEVO" > 
    public void estateGuardar() {//Estado por defecto
        this.varEstados = Common.MTTOINSERT; //1
        this.botonEstado = "Guardar";
        disable = false;
        reNick = true;
        roNick = false;
        RequestContext.getCurrentInstance().update("form2");

    }

    public void estateActualizar() {//se activa automaticamente despues de Guardar o buscar
        this.varEstados = Common.MTTOUPDATE; //2
        this.botonEstado = "Actualizar";
        disable = false;
        reNick = false;
        roNick = true;
        RequestContext.getCurrentInstance().update("form2");

    }

    public void estateBuscar() {
        this.varEstados = 3;
        this.botonEstado = "Buscar";
        this.disable = true;
        reNick = false;
        roNick = false;
        RequestContext.getCurrentInstance().update("form2");

    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Load de Pantalla" >    
    @PostConstruct
    private void InitForm() {
        this.setPassword("-1");
        estateGuardar();

        disable = false;
        reNick = true;
        roNick = false;

        try {
            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            if (SecurityEJBService == null) {
                SecurityEJBService = new SecurityEJBClient();
            }

            this.setProfilesLst((List<ProfileTO>) SecurityEJBService.getProfile());

            this.setExportOption("PDF");

            //System.out.println("antes InitForm " + this.getFormAction());
            //this.setFormAction(Common.MTTOINSERT);
            //System.out.println("ejecutando InitForm " + this.getFormAction());
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SIN USO" > 

    /*
     * Rutilio Iraheta
     * Establece la opcion de insertar cuando se limpia formulario para ingreso de datos
     //* /

     public void chooseArt() {
     Map<String, Object> options = new HashMap<String, Object>();
     options.put("modal", true);
     options.put("draggable", false);
     options.put("resizable", false);
     options.put("contentHeight", 320);
     RequestContext.getCurrentInstance().openDialog("/faces/view/mtto/mttoUsersView.xhtml", options, null);
     }

     public void onArtChosen(SelectEvent event) {
     UserTO usr = (UserTO) event.getObject();

     //if (usr != null) {
     //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User Selected", "Id:" + usr.getNickname());
     //FacesContext.getCurrentInstance().addMessage(null, message);
     //System.out.println(usr.getNickname());     
     this.setNickname(usr.getNickname());
     this.setLastname(usr.getLastname());
     this.setUsername(usr.getUsername());
     this.setProfilecode(usr.getProfilecode());
     this.setUsersign(usr.getUsersign());
     this.setLocked(usr.getLocked().equals("S") ? "true" : "false");
     this.setFormAction(Common.MTTOUPDATE);
     //System.out.println("locked: " + usr.getLocked());
     //System.out.println(usr.getLocked()=="S");   
     //System.out.println(usr.getLocked().equals("S")); 
     // this.setArticleCode(art.getItemCode());
     //this.setDescription(art.getItemName());
     //FacesContext.getCurrentInstance()
     //System.out.println("ejecutando onArtChosen " + this.getFormAction());
     // }
     }*/
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones varias" > 
    public void confirmDialog(ActionEvent actionEvent) {
        showHideDialog("dlgC", 2);
        if (varEstados == 1) {
            doSave();
        } else {
            if (varEstados == 2) {
                doUpdate();
            }
        }
    }

    public void cancelDialog(ActionEvent actionEvent) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlgC').hide();");
    }

    public void cleanBean() {
        this.nickname = null;
        this.username = null;
        this.lastname = null;
        this.profilecode = 0;
        this.locked = null;
        this.password = "-1";

        this.newUser = new UserTO();

        this.listaBusquedaTable.clear();
        this.listaBusqueda.clear();
    }

    public void llenarPantallaUser(UserTO var) {
        setNickname(var.getNickname());
        setUsername(var.getUsername());
        setLastname(var.getLastname());
        setProfilecode(var.getProfilecode());
        setLocked(var.getLocked());
        setPassword("-1");
        setUsersign(var.getUsersign());
    }

    public void selectDialog() {
        showHideDialog("dlgU", 2);
        UserTO var = (UserTO) selectU;

        try {
            newUser = SecurityEJBService.getUserByNickname(var.getNickname());
            llenarPantallaUser(newUser);
            estateActualizar();
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            faceMessage("Error en busqueda por Nickname");
        }

        listaBusqueda = new Vector();
        RequestContext.getCurrentInstance().update("form2");

    }

    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
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

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Imprimir" > 
    public void printReport() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();

        reportParameters.put("rtitle", "Hello JasperReports");

        getBean().setParameters(reportParameters);
        getBean().setReportName("report1");
        getBean().setExportOption(ExportOption.valueOf(ExportOption.class, this.getExportOption()));
        getBean().execute();

    }
    //</editor-fold>

public void downloadFile() {
        System.out.println("here");
        File file = new File("C:\\workspace\\debitotarjeta.txt");
        InputStream fis;
        try {
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;
            while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
                offset += numRead;
            }
            fis.close();
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=instructions.txt");
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//<editor-fold defaultstate="collapsed" desc="Imprimir factura" > 
    public void printInvoicePreformat() {
        /*Map<String, Object> reportParameters = new HashMap<String, Object>();

         reportParameters.put("rtitle", "Hello JasperReports");

         getBean().setParameters(reportParameters);
         getBean().setReportName("report1");
         getBean().setExportOption(ExportOption.valueOf(ExportOption.class, this.getExportOption()));
         getBean().execute();*/
        //PrintServiceApp.printInvoice();

    }

    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S" >
    
    

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

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
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

    public UserTO getSelectU() {
        return selectU;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public AdminEJBClient getAdminEJBService() {
        return AdminEJBService;
    }

    public void setAdminEJBService(AdminEJBClient AdminEJBService) {
        this.AdminEJBService = AdminEJBService;
    }

    public SecurityEJBClient getSecurityEJBService() {
        return SecurityEJBService;
    }

    public void setSecurityEJBService(SecurityEJBClient SecurityEJBService) {
        this.SecurityEJBService = SecurityEJBService;
    }
    
    public void setSelectU(UserTO selectU) {
        this.selectU = selectU;
    }

    public UserTO getNewUser() {
        return newUser;
    }

    public void setNewUser(UserTO newUser) {
        this.newUser = newUser;
    }

    public List getListaBusqueda() {
        return listaBusqueda;
    }

    public void setListaBusqueda(List listaBusqueda) {
        this.listaBusqueda = listaBusqueda;
    }

    public ArrayList<UserTO> getListaBusquedaTable() {
        return listaBusquedaTable;
    }

    public void setListaBusquedaTable(ArrayList<UserTO> listaBusquedaTable) {
        this.listaBusquedaTable = listaBusquedaTable;
    }

    /**
     * @return the usersign
     */
    public Integer getUsersign() {
        return usersign;
    }

    /**
     * @param usersign the usersign to set
     */
    public void setUsersign(Integer usersign) {
        this.usersign = usersign;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the profilecode
     */
    public int getProfilecode() {
        return profilecode;
    }

    /**
     * @param profilecode the profilecode to set
     */
    public void setProfilecode(int profilecode) {
        this.profilecode = profilecode;
    }

    /**
     * @return the locked
     */
    public String getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(String locked) {
        this.locked = locked;
    }

    /**
     * @return the userdate
     */
    public Date getUserdate() {
        return userdate;
    }

    /**
     * @param userdate the userdate to set
     */
    public void setUserdate(Date userdate) {
        this.userdate = userdate;
    }

    /**
     * @return the cusersign
     */
    public Integer getCusersign() {
        return cusersign;
    }

    /**
     * @param cusersign the cusersign to set
     */
    public void setCusersign(Integer cusersign) {
        this.cusersign = cusersign;
    }

    /**
     * @return the profilesLst
     */
    public List<ProfileTO> getProfilesLst() {
        return profilesLst;
    }

    /**
     * @param profilesLst the profilesLst to set
     */
    public void setProfilesLst(List<ProfileTO> profilesLst) {
        this.profilesLst = profilesLst;
    }

    /**
     * @return the formAction
     */
    public int getFormAction() {
        return formAction;
    }

    /**
     * @param formAction the formAction to set
     */
    public void setFormAction(int formAction) {
        this.formAction = formAction;
    }

    /**
     * @return the exportOption
     */
    public String getExportOption() {
        return exportOption;
    }

    /**
     * @param exportOption the exportOption to set
     */
    public void setExportOption(String exportOption) {
        this.exportOption = exportOption;
    }

//</editor-fold>

    public ReportsBean getBean() {
        return bean;
    }

    public void setBean(ReportsBean bean) {
        this.bean = bean;
    }
    
}//cierre de clase
