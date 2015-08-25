/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Rutilio
 */
@ManagedBean(name = "tprint")
@RequestScoped
public class testPrint {

    /**
     * Creates a new instance of testPrint
     */
    public testPrint() {
    }

    public String printInvoice() throws UnsupportedEncodingException {
        String foo = "abc";
        String bar = "xyz";
        return "/testPrintView?faces-redirect=true"
                + "&foo=" + URLEncoder.encode(foo, "UTF-8")
                + "&bar=" + URLEncoder.encode(bar, "UTF-8");
    }
}
