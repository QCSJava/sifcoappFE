/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prueba.model.primefaces;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 *
 * @author Peter-PC
 */

public class Computer implements Serializable {

	private static final long serialVersionUID = 8601221421852213479L;

	private String name;

	private Station station;

	public Computer(String name, Station station) {
		this.name = name;
		this.station = station;
	}

	public String getName() {
		return name;
	}

	public Station getStation() {
		return station;
	}

	

	public void setName(String name) {
		this.name = name;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(super.hashCode());
		hcb.append(getName());
		hcb.append(getStation());
		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Computer)) {
			return false;
		}
		final Computer other = (Computer) obj;
		final EqualsBuilder eqb = new EqualsBuilder();
		eqb.append(this.getName(), other.getName());
		eqb.append(this.getStation(), other.getStation());
		return eqb.isEquals();
	}

	@Override
	public String toString() {
		return "Computer [name=" + name + ", station="
				+ station + "]";
	}

}
