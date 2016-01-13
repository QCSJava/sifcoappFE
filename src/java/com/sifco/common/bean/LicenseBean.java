/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.common.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Scanner;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "licenseBean")
@ApplicationScoped
public class LicenseBean implements Serializable {

    public LicenseBean() {
    }

//<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private boolean executed = false;
    private boolean license = false;
    
    private static String sn = null;

//</editor-fold>
    
    public boolean metodo() {
        if (!executed) {
        //ejecutar

        }
        return license;
    }

    public static final String getSerialNumber() {

        if (sn != null) {
            return sn;
        }

        OutputStream os = null;
        InputStream is = null;

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(new String[]{"wmic", "bios", "get", "serialnumber"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        os = process.getOutputStream();
        is = process.getInputStream();

        try {
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(is);
        try {
            while (sc.hasNext()) {
                String next = sc.next();
                if ("SerialNumber".equals(next)) {
                    sn = sc.next().trim();
                    break;
                }
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (sn == null) {
            throw new RuntimeException("Cannot find computer SN");
        }

        return sn;
    }

//<editor-fold defaultstate="collapsed" desc="G & S">
    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

//</editor-fold>
}//cierre de clase
