/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifco.to.clases.Periodo;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.objects.accounting.to.AccPeriodInTO;
import com.sifcoapp.objects.accounting.to.AccPeriodOutTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
 
@ManagedBean(name="periodo")
@SessionScoped
public class PeriodoBean implements Serializable{
    private static AccountingEJBClient AccountingEJBService=null;
    private static final long serialVersionUID = 1L;
    
      
    private String nombrePeriodo;
    private String subPeriodo;
    private String cantidadPeriodo;
    private String indicadorPeriodo;
    private String statusPeriodo;
    private String fechaConta;
    private String fechaVencimiento;
    private String fechaDocumento;
    private String inicioEjercicio;
    private String ejercicio;
    private String codigoPeriodo;
    private boolean canEdit;

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public String getSubPeriodo() {
        return subPeriodo;
    }

    public void setSubPeriodo(String subPeriodo) {
        this.subPeriodo = subPeriodo;
    }

    public String getCantidadPeriodo() {
        return cantidadPeriodo;
    }

    public void setCantidadPeriodo(String cantidadPeriodo) {
        this.cantidadPeriodo = cantidadPeriodo;
    }

    public String getIndicadorPeriodo() {
        return indicadorPeriodo;
    }

    public void setIndicadorPeriodo(String indicadorPeriodo) {
        this.indicadorPeriodo = indicadorPeriodo;
    }

    public String getStatusPeriodo() {
        return statusPeriodo;
    }

    public void setStatusPeriodo(String statusPeriodo) {
        this.statusPeriodo = statusPeriodo;
    }

    public String getFechaConta() {
        return fechaConta;
    }

    public void setFechaConta(String fechaConta) {
        this.fechaConta = fechaConta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getInicioEjercicio() {
        return inicioEjercicio;
    }

    public void setInicioEjercicio(String inicioEjercicio) {
        this.inicioEjercicio = inicioEjercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getCodigoPeriodo() {
        return codigoPeriodo;
    }

    public void setCodigoPeriodo(String codigoPeriodo) {
        this.codigoPeriodo = codigoPeriodo;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    private static final ArrayList<Periodo> periodoList = 
		new ArrayList<Periodo>(Arrays.asList(
		new Periodo("A0001", "Intel CPU","700.00", "1","","","","","","","")
                ));
    
    public ArrayList<Periodo> getPeriodoList() {
 
		return periodoList;
 
	}
    public String addAction() {
	Periodo pe = new Periodo(this.nombrePeriodo,this.subPeriodo,this.cantidadPeriodo,this.indicadorPeriodo,this.statusPeriodo,this.fechaConta,this.fechaVencimiento,this.fechaDocumento,this.inicioEjercicio,this.ejercicio,this.codigoPeriodo);
        AccPeriodInTO parameters = new AccPeriodInTO();
	AccPeriodOutTO outputs = new AccPeriodOutTO();
//    	parameters.setCantidadPeriodo("cant1");
//	parameters.setCodigoPeriodo("CodigoPeriodo1");
//	parameters.setEjercicio("Ejercicio1");
//    	parameters.setFechaConta("20/11/2014");
//    	parameters.setFechaDocumento("20/11/2014");
//    	parameters.setFechaVencimiento("20/11/2014");
//    	parameters.setIndicadorPeriodo("indicadorPeriodo1");
//    	parameters.setInicioEjercicio("inicioEjercicio");
//    	parameters.setNombrePeriodo("nombrePeriodo");
//    	parameters.setStatusPeriodo("statusPeriodo");
//    	parameters.setSubPeriodo("subPeriodo");
        
        parameters.setNombrePeriodo(this.nombrePeriodo);
        parameters.setSubPeriodo(this.subPeriodo);
        parameters.setCantidadPeriodo(this.cantidadPeriodo);
        parameters.setIndicadorPeriodo(this.indicadorPeriodo);
        parameters.setStatusPeriodo(this.statusPeriodo);
        parameters.setFechaConta(this.fechaConta);
        parameters.setFechaVencimiento(this.fechaVencimiento);
        parameters.setInicioEjercicio(this.inicioEjercicio);
        parameters.setEjercicio(this.ejercicio);
        parameters.setCodigoPeriodo(this.codigoPeriodo);
        if     (AccountingEJBService==null){
            AccountingEJBService= new AccountingEJBClient();
        }
   //     outputs=AccountingEJBService.AccAddPeriod(parameters);
    	System.out.println(outputs.getCodResp());
        if (outputs.getCodResp()==0){
             periodoList.add(pe);
        }else{
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,"Invalid Insert!","Please Try Again!"));
        }
        //periodoList.add(pe);
	return null;
	}
    public String deleteAction(Periodo periodo) {
	    
		periodoList.remove(periodo);
		return null;
	}
    
}
