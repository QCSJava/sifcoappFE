/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import java.io.Serializable;
 
public class Documentnew implements Serializable, Comparable<Documentnew> {
 
    private String name;
     
    private String size;
     
    private String type;
    
    private String post;
    
    private Double monto;
            
    public Documentnew(String name, String size, String type,String post,Double monto) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.post = post;
        this.monto= monto;
    }
//<editor-fold defaultstate="collapsed" desc="getters setters">
   public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getSize() {
        return size;
    }
 
    public void setSize(String size) {
        this.size = size;
    }
 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }  
//</editor-fold>
   
    
    //Eclipse Generated hashCode and equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((post == null) ? 0 : post.hashCode());
        result = prime * result + ((monto == null) ? 0 : monto.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Documentnew other = (Documentnew) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (post == null) {
            if (other.post != null)
                return false;
        } else if (!post.equals(other.post))
            return false;
         if (monto == null) {
            if (other.monto != null)
                return false;
        } else if (!monto.equals(other.monto))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return name;
    }
 
    public int compareTo(Documentnew document) {
        return this.getName().compareTo(document.getName());
    }
}  