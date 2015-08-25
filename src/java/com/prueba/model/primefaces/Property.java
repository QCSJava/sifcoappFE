package com.prueba.model.primefaces;



import java.util.Date;

public class Property {
	 

	public Property() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Property [key=" + key + ", value=" + value + "]";
	}

 
private String key;
private String value;
private String random;
private Date date;

 
public String getRandom() {
	return random;
}

public void setRandom(String random) {
	this.random = random;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}
  
 

}
