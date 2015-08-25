/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prueba.model.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.Validate;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Peter-PC
 */

@ManagedBean(name="tutoView")
@ViewScoped
public class TutoView implements Serializable{

	private static final long serialVersionUID = -8376453139595321470L;

	private List<Computer> computers = new ArrayList<Computer>();
	
	private Computer computerSelected;

	
	@PostConstruct
	public void init() {
		computers = fillComputer();	
	}
	

	public void rowEditListener(RowEditEvent event){
		final Computer computer=(Computer) event.getObject();
		Validate.notNull(computer);
		
		
	}
	
	public void removeComputerListener(ActionEvent event) {
		computers.remove(computerSelected);
	}
	
	private List<Computer> fillComputer() {
		final List<Computer> allComputers=new ArrayList<Computer>();
		int i=0;
		while (i<=10) {
			allComputers.add(new Computer("Dispositivo_" +i, Station.ALMACENADO));
			i++;
		}
		return allComputers;
	}
	
	public Station[] getStationValues() {
		return new Station[] { Station.ALMACENADO, Station.REPARADO};
	}
	
	
	public Computer getComputerSelected() {
		return computerSelected;
	}

	public void setComputerSelected(Computer computerSelected) {
		if(computerSelected!=null){
			this.computerSelected = computerSelected;
		}
	}

	public List<Computer> getComputers() {
		return computers;
	}
	
}
