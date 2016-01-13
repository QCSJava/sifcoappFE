/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.servlet.filter;

/**
 *
 * @author ri00642
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private ArrayList<String> urlList;
    private static String sn = null;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        ServletContext context = req.getServletContext();
        if (context.getAttribute("license") == null) {
            System.out.println("Licencia no verificada...");
            context.setAttribute("license", verifyLicense());//setea license en true o false
        }
        if (context.getAttribute("license").equals(true)) {
            System.out.println("Licencia valida");
        } else {
            System.out.println("Licencia no valida");
        }

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();

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
                //System.out.println(session);
                _username = (String) session.getAttribute("username");
            }

            System.out.println("url no permitida " + url);

            //if (null == session || _username==null) {
            if (null == session || _username == null) {
                System.out.println("sesion null " + url);
                response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                //System.out.println("sesion nula redirecciona sino es index, login ni jafax?faces-redirect=true");    
            } else {
                ArrayList<String> urlListUsr = (ArrayList<String>) session.getAttribute("urlsUser");
                //if (!urlListUsr.contains(url)) {
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
                    for (int x = 0; x < urlListUsr.size(); x++) {
                        System.out.println(urlListUsr.get(x));
                        if (url.contains(urlListUsr.get(x))) {
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
                //response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
                // }
            }
        }

        chain.doFilter(req, res);
    }

    public void init(FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");

        urlList = new ArrayList<String>();

        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
            System.out.println("agregando token" + token.nextToken());
        }
    }

    /*
     * Rutilio Iraheta
     * Julio 2015
     * Valida las urls por perfil
     */
    public void checkProfile(HttpSession session) {
        ArrayList<String> urlListUsr;
        urlListUsr = (ArrayList<String>) session.getAttribute("urlsUser");

    }

    private boolean verifyLicense() {
        if ("4B402343K".equals(getSerialNumber())) {
            System.out.println("Licencia verificada correctamente: "+getSerialNumber());
            return true;
        } else {
            System.out.println("Solicite una licencia, esta es invalida: "+getSerialNumber());
            return false;
        }
    }

    public String getSerialNumber() {

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
}
