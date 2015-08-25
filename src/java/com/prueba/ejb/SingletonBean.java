/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.ejb;
import com.prueba.model.primefaces.Property;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
 
import javax.ejb.Singleton;
 



@Singleton

public class  SingletonBean   {
  
 private  List<Property> cache; 
 

 @PostConstruct
 public void initCache(){
   
    cache = new ArrayList<Property>();
 }

 public void delete(Property prop){
	 
	  this.cache.remove(prop);
 }
 public void put(String key,String value){
	 Property p = new Property();
	  p.setKey(key);
	  p.setValue(value);
 
	  
      this.cache.add(p);
 }
 public List<Property> getCache() {
	 return cache;
 }
 	      


}
