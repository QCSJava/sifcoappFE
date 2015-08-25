package com.prueba.model.primefaces;



public enum Station {
	ALMACENADO, REPARADO;
	
	public String getLabel(){
		return this.getClass().getSimpleName() + "_" + this.name();
	}


}
