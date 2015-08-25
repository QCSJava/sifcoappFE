/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.tablas.model;

/**
 *
 * @author ps05393
 */
public class Tablas {
       
    private String name;
	private String description;
	private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Tablas(){
		this.setDescription(null);
		this.setName(null);
	}
        
    public Tablas(String _code, String _name, String _description){
		this.setDescription(_description);
		this.setName(_name);
		this.setCode(_code);
	}
    
}
