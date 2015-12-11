/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.PurchaseEJBClient;
import com.sifcoapp.client.SalesEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.purchase.to.PurchaseDetailTO;
import com.sifcoapp.objects.purchase.to.PurchaseTO;
import com.sifcoapp.objects.sales.to.SalesDetailTO;
import com.sifcoapp.objects.sales.to.SalesTO;
import com.sifcoapp.report.common.numerosAletras;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valentin
 */
public class SalesPrint extends HttpServlet {

    private static AdminEJBClient AdminEJBService;
    private static SalesEJBClient SalesEJBService;
    private static PurchaseEJBClient PurchaseEJBClient;
    private numerosAletras convertNumber;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalesTO var = new SalesTO();
        PurchaseTO pur = new PurchaseTO();
        CatalogTO _R = new CatalogTO();
        String numberToletter = null;
        Double total = 0.0;
        String vendedor = "";
        int tipoDoc = 0;

        //letras num
        String numberToletter1 = "", numberToletter2 = "", name1 = "", name2 = "";

        //hora
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, ampm;
        hora = calendario.get(Calendar.HOUR);
        minutos = calendario.get(Calendar.MINUTE);
        String minuteS;
        if (minutos < 10) {
            minuteS = "0" + minutos;
        } else {
            minuteS = minutos + "";
        }
        ampm = calendario.get(Calendar.AM_PM);
        String horaImp = hora + ":" + minuteS + (ampm == Calendar.AM ? " am" : " pm");

        //tipo doc
        String tipoDocu = "";//Credito Fiscal CCF

        try {
            convertNumber = new numerosAletras() {
            };
            AdminEJBService = new AdminEJBClient();

            tipoDoc = Integer.parseInt(request.getParameter("tip"));
            if (tipoDoc == 1) {
                //venta
                SalesEJBService = new SalesEJBClient();
                var = SalesEJBService.getSalesByKey(Integer.parseInt(request.getParameter("foo")));
                vendedor = request.getParameter("bar");
                _R = AdminEJBService.findCatalogByKey(var.getPeymethod(), 8);
                total = var.getDoctotal() - var.getVatsum();//formatNumber(var.getDoctotal());
                numberToletter = convertNumber.convertNumberToLetter(var.getDoctotal());
                int tam = numberToletter.length();
                if (tam > 30) {
                    numberToletter1 = numberToletter.substring(0, 30);
                    numberToletter2 = numberToletter.substring(30, tam);
                } else {
                    numberToletter1 = numberToletter;
                }

                String name = var.getCardcode() + "-" + var.getCardname().toUpperCase();
                int tam2 = name.length();
                if (tam2 > 30) {
                    name1 = name.substring(0, 35);
                    name2 = name.substring(35, tam2);
                } else {
                    name1 = name;
                }

                tipoDocu = "Credito Fiscal CCF";
            } else {
                if (tipoDoc == 2) {
                    //compra
                    PurchaseEJBClient = new PurchaseEJBClient();
                    pur = PurchaseEJBClient.getPurchaseByKey(Integer.parseInt(request.getParameter("foo")));//quemado
                    vendedor = request.getParameter("bar");
                    _R = AdminEJBService.findCatalogByKey(pur.getPeymethod(), 8);
                    total = pur.getDoctotal() - pur.getVatsum();//formatNumber(var.getDoctotal());
                    numberToletter = convertNumber.convertNumberToLetter(pur.getDoctotal());
                    int tam = numberToletter.length();
                    if (tam > 30) {
                        numberToletter1 = numberToletter.substring(0, 30);
                        numberToletter2 = numberToletter.substring(30, tam);
                    } else {
                        numberToletter1 = numberToletter;
                    }

                    String name = pur.getCardcode() + "-" + pur.getCardname().toUpperCase();
                    int tam2 = name.length();
                    if (tam2 > 30) {
                        name1 = name.substring(0, 35);
                        name2 = name.substring(35, tam2);
                    } else {
                        name1 = name;
                    }

                    tipoDocu = request.getParameter("doc");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        //fecha
        Calendar fecha = Calendar.getInstance();
        if (tipoDoc == 1) {
            fecha.setTime(var.getDocdate());
        } else {
            fecha.setTime(pur.getDocdate());
        }

        int year = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String fechaImp = dia + "/" + mes + "/" + year;

        response.setContentType("text/html;charset=UTF-8");

        if (tipoDoc == 1) {
            //venta
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "\n"
                        + "    <head>\n"
                        + "        <title>Sales Print - Credito Fiscal</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "<script type=\"text/javascript\">\n"
                        + "window.print();\n"
                        + "window.open('', '_self', ''); window.close(); \n"
                        + "</script>"
                        + "\n"
                        + "    </head>\n"
                        + "\n"
                        + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                        + "        <!-- tamanio de factura 566x453px-->\n"
                        + "        <div style=\"width: 550px; height: 650px\">\n"
                        + "            <table style=\"width:100%\" border=\"0\">\n"
                        + "                <!-- primero -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\" width: 19px; height: 120px \">\n"
                        + "                    </td>\n"
                        + "                    <td style=\"\">\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 60px\">\n"
                        + "                                <td style=\"width: 60%\">\n"
                        + "                                </td>\n"
                        + "                                <td style=\"width: 20%; vertical-align: bottom\">\n"
                        + "                                    " + var.getNumatcard() + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 51px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 19px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td style=\"vertical-align: bottom\">\n"
                        + "                                    " + horaImp + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td style=\" width: 19px\"></td>\n"
                        + "                </tr>\n"
                        + "                <!-- segundo -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\"height: 126px\"></td>\n"
                        + "                    <td style=\"\">\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td style=\"width: 10%\"></td>\n"
                        + "                                <td style=\"width: 60%\">\n"
                        + "                                    " + name1 + "\n"
                        + "                                </td>\n"
                        + "                                <td style=\"width: 10%\"></td>\n"
                        + "                                <td style=\"width: 20%\">\n"
                        + "                                    " + fechaImp + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>" + name2 + "</td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + _R.getCatvalue().toUpperCase() + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 25px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + tipoDocu + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td></td>\n"
                        + "                </tr>\n"
                        + "                <!-- tercero -->\n"
                        + "                <tr>\n"
                        + "                    <td > </td>\n"
                        + "                    <td>\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 25px\">\n"
                        + "                                <td style=\"width: 8%\"/>\n"
                        + "                                <td style=\"width: 54%\"/>\n"
                        + "                                <td style=\"width: 10%\"/>\n"
                        + "                                <td style=\"width: 9%\"/>\n"
                        + "                                <td style=\"width: 9%\"/>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "\n");

                int tam = var.getSalesDetails().size();
                List det = var.getSalesDetails();
                for (int i = 0; i < 12; i++) {

                    if (i < tam) {
                        SalesDetailTO var2 = (SalesDetailTO) det.get(i);
                        String des = var2.getDscription();
                        int largo = des.length();
                        if (largo > 35) {
                            des = des.substring(0, 35);
                        }

                        out.println("                            <tr style=\"height: 19px\">\n"
                                + "                                <td>\n"
                                + "                                    " + var2.getQuantity() + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + des.toUpperCase() + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                   $" + truncarDouble(var2.getPrice()) + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + "$0.00" + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + "$0.00" + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    $" + truncarDouble(var2.getLinetotal()) + "\n"
                                + "                                </td>\n"
                                + "                            </tr>\n");

                    } else {
                        String vacio = "";
                        out.println("                            <tr style=\"height: 19px\">\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                   " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                   " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                            </tr>\n");
                    }
                }

                out.println("                            <tr style=\"height: 25px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td></td>\n"
                        + "                </tr>\n"
                        + "                <!-- cuarto -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\"height: 144px\"/>\n"
                        + "                    <td>\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td style=\"width: 62%\"></td>\n"
                        + "                                <td style=\"width: 28%\"/>\n"
                        + "                                <td >\n"
                        + "                                    $" + truncarDouble(total) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td>\n"
                        + "                                    &nbsp; &nbsp; " + numberToletter + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $" + truncarDouble(var.getVatsum()) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $0.00\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $0.00\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $0.00 \n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $0.00\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td>\n"
                        + "                                    &nbsp; &nbsp; &nbsp; &nbsp; " + vendedor.toUpperCase() + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $0.00\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    $" + truncarDouble(var.getDoctotal()) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td></td>\n"
                        + "                </tr>\n"
                        + "                <!-- quinto -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\"height: 50px\"/>\n"
                        + "                    <td></td>\n"
                        + "                    <td></td>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>");
            }//cierre html
        } else {
            if (tipoDoc == 2) {
                //compra
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "\n"
                            + "    <head>\n"
                            + "        <title>Impresion de Compra</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "<script type=\"text/javascript\">\n"
                            + "window.print();\n"
                            + "window.open('', '_self', ''); window.close(); \n"
                            + "</script>"
                            + "\n"
                            + "    </head>\n"
                            + "\n"
                            + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                            + "        <!-- tamanio de factura 566x453px-->\n"
                            + "        <div style=\"width: 550px; height: 650px\">\n"
                            + "            <table style=\"width:100%\" border=\"0\">\n"
                            + "                <!-- primero -->\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 19px; height: 120px \">\n"
                            + "                    </td>\n"
                            + "                    <td style=\"\">\n"
                            + "                        <table style=\"width:100%\" border=\"0\">\n"
                            + "                            <tr style=\"height: 60px\">\n"
                            + "                                <td style=\"width: 60%\">\n"
                            + "                                </td>\n"
                            + "                                <td style=\"width: 20%; vertical-align: bottom\">\n"
                            + "                                    " + pur.getNumatcard() + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 51px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 19px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td style=\"vertical-align: bottom\">\n"
                            + "                                    " + horaImp + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                        </table>\n"
                            + "                    </td>\n"
                            + "                    <td style=\" width: 19px\"></td>\n"
                            + "                </tr>\n"
                            + "                <!-- segundo -->\n"
                            + "                <tr>\n"
                            + "                    <td style=\"height: 126px\"></td>\n"
                            + "                    <td style=\"\">\n"
                            + "                        <table style=\"width:100%\" border=\"0\">\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td style=\"width: 10%\"></td>\n"
                            + "                                <td style=\"width: 60%\">\n"
                            + "                                    " + name1 + "\n"
                            + "                                </td>\n"
                            + "                                <td style=\"width: 10%\"></td>\n"
                            + "                                <td style=\"width: 20%\">\n"
                            + "                                    " + fechaImp + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>" + name2 + "</td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + _R.getCatvalue().toUpperCase() + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 25px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + tipoDocu + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                        </table>\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "                <!-- tercero -->\n"
                            + "                <tr>\n"
                            + "                    <td > </td>\n"
                            + "                    <td>\n"
                            + "                        <table style=\"width:100%\" border=\"0\">\n"
                            + "                            <tr style=\"height: 25px\">\n"
                            + "                                <td style=\"width: 8%\"/>\n"
                            + "                                <td style=\"width: 54%\"/>\n"
                            + "                                <td style=\"width: 10%\"/>\n"
                            + "                                <td style=\"width: 9%\"/>\n"
                            + "                                <td style=\"width: 9%\"/>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "\n");

                    int tam = pur.getpurchaseDetails().size();
                    List det = pur.getpurchaseDetails();
                    for (int i = 0; i < 12; i++) {

                        if (i < tam) {
                            PurchaseDetailTO var2 = (PurchaseDetailTO) det.get(i);

                            String des = var2.getDscription();
                            int largo = des.length();
                            if (largo > 30) {
                                des = des.substring(0, 30);
                            }

                            out.println("                            <tr style=\"height: 19px\">\n"
                                    + "                                <td>\n"
                                    + "                                    " + var2.getQuantity() + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + des.toUpperCase() + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                   $" + truncarDouble(var2.getPrice()) + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + "$0.00" + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + "$0.00" + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    $" + truncarDouble(var2.getLinetotal()) + "\n"
                                    + "                                </td>\n"
                                    + "                            </tr>\n");

                        } else {
                            String vacio = "";
                            out.println("                            <tr style=\"height: 19px\">\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                   " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                   " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                            </tr>\n");
                        }
                    }

                    out.println("                            <tr style=\"height: 25px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                        </table>\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "                <!-- cuarto -->\n"
                            + "                <tr>\n"
                            + "                    <td style=\"height: 144px\"/>\n"
                            + "                    <td>\n"
                            + "                        <table style=\"width:100%\" border=\"0\">\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td style=\"width: 62%\"></td>\n"
                            + "                                <td style=\"width: 28%\"/>\n"
                            + "                                <td >\n"
                            + "                                    $" + truncarDouble(total) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td>\n"
                            + "                                    &nbsp; &nbsp; " + numberToletter1 + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $" + truncarDouble(pur.getVatsum()) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td>"
                            + "                                     &nbsp; &nbsp; " + numberToletter2 + "</td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $0.00\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $0.00\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $0.00 \n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $0.00\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td>\n"
                            + "                                    &nbsp; &nbsp; &nbsp; &nbsp; " + vendedor.toUpperCase() + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $0.00\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    $" + truncarDouble(pur.getDoctotal()) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                        </table>\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "                <!-- quinto -->\n"
                            + "                <tr>\n"
                            + "                    <td style=\"height: 50px\"/>\n"
                            + "                    <td></td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "        </div>\n"
                            + "    </body>\n"
                            + "</html>");
                }//cierre html
            }
        }

    }//cierre de clase

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String truncarDouble(Double doctotal) {
        DecimalFormat df;
        df = new DecimalFormat("#,###.##");
        return df.format(Math.floor(100 * doctotal) / 100);
    }

}
