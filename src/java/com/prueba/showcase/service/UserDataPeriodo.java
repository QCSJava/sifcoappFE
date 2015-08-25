/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.showcase.service;
import com.sifco.to.clases.Periodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author ps05393
 */
@ManagedBean(name = "userDataPeriodo", eager = true)
@SessionScoped
public class UserDataPeriodo implements Serializable {
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

   
    
//    private static final ArrayList<Employee> employees
//      = new ArrayList<Employee>(Arrays.asList(
//      new Employee("John", "Marketing", 30,2000.00),
//      new Employee("Robert", "Marketing", 35,3000.00),
//      new Employee("Mark", "Sales", 25,2500.00),
//      new Employee("Chris", "Marketing", 33,2500.00),
//      new Employee("Peter", "Customer Care", 20,1500.00)
//   ));
    
    private static final ArrayList<Periodo> periodos
            =new ArrayList<Periodo>(Arrays.asList(
            new Periodo("Periodo1","Periodo1","3","indicador","status","fecha","fecha","fecha","inicio","ejericio","codigo"),
            new Periodo("Periodo1","Periodo1","3","indicador","status","fecha","fecha","fecha","inicio","ejericio","codigo"),
            new Periodo("Periodo1","Periodo1","3","indicador","status","fecha","fecha","fecha","inicio","ejericio","codigo"),
            new Periodo("Periodo1","Periodo1","3","indicador","status","fecha","fecha","fecha","inicio","ejericio","codigo"),
            new Periodo("Periodo1","Periodo1","3","indicador","status","fecha","fecha","fecha","inicio","ejericio","codigo")
    ));
    
     public ArrayList<Periodo> getPeriodos() {
      return periodos;
   }
     
      public String addPeriodo() {		 
      //Periodo periodos = new Periodo(name,dept,age,salary);
       //employees.add(employee);
      Periodo periodos = new Periodo(nombrePeriodo,subPeriodo,cantidadPeriodo,indicadorPeriodo,statusPeriodo,fechaConta,fechaVencimiento,fechaDocumento,inicioEjercicio,ejercicio,codigoPeriodo);
     //periodos.add(periodos);

      return null;
   }
      public String deletePeriodo(Periodo periodo) {
      periodos.remove(periodos);		
      return null;
   }

   public String editPeriodo(Periodo periodo){
      periodo.setCanEdit(true);
      return null;
   }

   public String savePeriodos(){
      //set "canEdit" of all employees to false 
      for (Periodo periodo : periodos){
         periodo.setCanEdit(false);
      }		
      return null;
   }
   
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
	
   
   
    /**
     * Creates a new instance of UserDataPeriodo
     */
    public UserDataPeriodo() {
        //llamar tu metodo y asignar el listado lo que Rutilio me devuelva! y quitar el otro listado
    }
    
}
