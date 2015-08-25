/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.parameters;

import com.sifcoapp.client.ParameterEJBClient;
import com.sifcoapp.objects.admin.to.parameterTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "paramBean")
@SessionScoped
public class ParamBean implements Serializable{

    public ParamBean() {
    }
    
//<editor-fold defaultstate="collapsed" desc="Variables">
    ParameterEJBClient ParameterEJBClient;
    //__________________________________________________________________________
    private int parametercode;
    private String parametername;
    private String value1;
    private String value2;
    private String value3;
    private int usersign;
    
    private ArrayList<parameterTO> lstParamTable = new ArrayList<>(); //DataTable 
    private List lstParam = new Vector();
    private parameterTO selectParam = new parameterTO();
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">

    public parameterTO getSelectParam() {
        return selectParam;
    }

    public void setSelectParam(parameterTO selectParam) {
        this.selectParam = selectParam;
    }
    
    

    public ArrayList<parameterTO> getLstParamTable() {
        return lstParamTable;
    }

    public void setLstParamTable(ArrayList<parameterTO> lstParamTable) {
        this.lstParamTable = lstParamTable;
    }

    public List getLstParam() {
        return lstParam;
    }

    public void setLstParam(List lstParam) {
        this.lstParam = lstParam;
    }
    

    public int getParametercode() {
        return parametercode;
    }

    public void setParametercode(int parametercode) {
        this.parametercode = parametercode;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Load">
    @PostConstruct
    public void initForm() {
        if (ParameterEJBClient == null) {
            ParameterEJBClient = new ParameterEJBClient();
        }
        
        try {
            this.lstParam = ParameterEJBClient.getParameter();
            for (Object obj : lstParam) {
                parameterTO var = (parameterTO) obj;
                this.lstParamTable.add(var);
            }
        } catch (Exception e) {
            System.out.println("Error getParameter");
        }
        
        
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Btn upda">
    public void btnUpdate(){
        faceMessage("Actualizando parametros");
        
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Edit param">
    public void onRowEdit(RowEditEvent event) {
        ResultOutTO res = new ResultOutTO();
        parameterTO param = (parameterTO) event.getObject();
        
        try {
            res = ParameterEJBClient.parameter_mtto(param, Common.MTTOUPDATE);
            if (res.getCodigoError() == 0) {
                faceMessage(res.getMensaje());
            }else
                faceMessage(res.getMensaje());
        } catch (Exception e) {
            faceMessage(e.getMessage() + " - " + e.getCause() );
            System.out.println(e.getMessage() + " - " + e.getCause());
        }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Funciones varias">
    public void faceMessage(String var) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(var));
    }
//</editor-fold>
    
}//cierre de clase
