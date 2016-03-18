/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.servlet.filter;

import com.sifcoapp.client.ParameterEJBClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

//<editor-fold defaultstate="collapsed" desc="Variables">
    private ArrayList<String> urlList;
    private static String sn = null;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Destroy">
    @Override
    public void destroy() {
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="doFilter">
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();

        /*ServletContext context = req.getServletContext();
        if (context.getAttribute("license") == null) {
            System.out.println("Procesador no verificado...");
            context.setAttribute("license", verifyLicense());//setea license en true o false
        }
        if (context.getAttribute("license").equals(false)) {
            System.out.println("Procesador invalido");
            response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
            chain.doFilter(req, res);
        }*/

        url = request.getRequestURI().toString();
        boolean allowedRequest = false;
        //System.out.println("url "+url);
        if (urlList.contains(url)) {
            allowedRequest = true;
        }

        if (url.contains("/sifcoappFE/faces/javax.faces.resource/")
                || url.contains("login")
                || url.contains("/sifcoappFE/servlets/report/PDF")
                || url.contains("Pdf")
                || url.contains("testPrintInvoice")
                || url.contains("testPrintView")
                || url.contains("testSalesView")
                || url.contains("SalesPrint")
                || url.contains("PrintDeliveryView")
                || url.contains("PrintColectView")
                || url.contains("ColectPrint")
                || url.contains("PrintCheckView")//
                || url.contains("CheckPrint")
                || url.contains("quoteView")
                || url.contains("testPurchaseView")
                || url.contains("PrintCreditView")
                || url.contains("PrintDebitView")
                || url.contains("DieselPrintView")
                || url.contains("RemisionPrint")) {
            allowedRequest = true;
            //System.out.println("url permitida: " + url);
        }

        if (!allowedRequest) {
            HttpSession session = request.getSession(false);
            System.out.println("session");
            String _username = null;
            if (null != session) {
                _username = (String) session.getAttribute("username");
            }

            System.out.println("url no permitida " + url);

            if (null == session || _username == null) {
                System.out.println("sesion null " + url);
                response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
            } else {
                ArrayList<String> urlListUsr = (ArrayList<String>) session.getAttribute("urlsUser");
                if (url.contains("/sifcoappFE/faces/javax.faces.resource/")
                        || url.contains("login")
                        || url.contains("/sifcoappFE/servlets/report/PDF")
                        || url.contains("Pdf")
                        || url.contains("index")
                        || url.contains("ColectPrint")
                        || url.contains("CheckPrint")
                        || url.contains("SalesPrint")
                        || url.contains("QuotePrint")
                        || url.contains("PrintNotes")
                        || url.contains("DieselPrint")
                        || url.contains("RemisionPrint")) {
                    allowedRequest = true;
                    System.out.println("url permitida: " + url);
                } else {
                    System.out.println("perfil invalido " + url);
                    int _validurl = 0;
                    for (String urlListUsr1 : urlListUsr) {
                        //System.out.println(urlListUsr1);
                        if (url.contains(urlListUsr1)) {
                            _validurl = 1;
                        }
                    }
                    if (_validurl == 1) {
                        System.out.println("perfil valido " + url);
                    } else {
                        System.out.println("perfil invalido " + url);
                        response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                    }
                }
            }
        }
        try {
            chain.doFilter(req, res);
        } catch (IOException | ServletException e) {
            System.out.println(e.getMessage() + " - " + e.getCause());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="init">
    @Override
    public void init(FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");

        urlList = new ArrayList<>();

        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
            System.out.println("agregando token" + token.nextToken());
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="checkProfile">
    public void checkProfile(HttpSession session) {
        ArrayList<String> urlListUsr;
        urlListUsr = (ArrayList<String>) session.getAttribute("urlsUser");

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="verifyLicense">
    private boolean verifyLicense() {
        if (readSerial().equals(getSerialNumber())) {
            System.out.println("Procesador valido...");
            return true;
        } else {
            System.out.println("Error en procesador, este no es valido");
            return false;
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getSerialNumber">
    public String getSerialNumber() {
        /*if (sn != null) {
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
         }*/
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("sn" + ": " + result.trim());
        String add = "generationqualityconsulting";
        return encryptSerial(result.trim() + add);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="encryptSerial">
    private String encryptSerial(String numSerial) {
        byte[] digest = null;
        byte[] buffer = numSerial.getBytes();

        try {
            String algorithm = "MD5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest: " + ex.getMessage());
        }
        return toHexadecimal(digest);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="toHexadecimal">
    private String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        //System.out.println("read md5 ini--------------------------------------------");
        //System.out.println(hash);
        //System.out.println("read md5 fin--------------------------------------------");
        return hash;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="readSerial">
    private String readSerial() {
        String serialNum = "", dir = "";

        try {
            ParameterEJBClient ParameterEJBClient = new ParameterEJBClient();
            dir = ParameterEJBClient.getParameterbykey(28).getValue1();

            FileReader fr = new FileReader(dir);
            int valor = fr.read();
            while (valor != -1) {
                serialNum = serialNum + ((char) valor);
                //System.out.print((char) valor);
                valor = fr.read();
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("Error E/S: " + e.getMessage());
        }
        //System.out.println("read text-----------------------------------------------");
        //System.out.println(serialNum);
        //System.out.println("read text fin-------------------------------------------");
        return serialNum;
    }
//</editor-fold>

}//Cierre de claase
