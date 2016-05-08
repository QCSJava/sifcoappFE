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
import com.sifcoapp.objects.purchase.to.SupplierDetailTO;
import com.sifcoapp.objects.purchase.to.SupplierTO;
import com.sifcoapp.objects.sales.to.ClientCrediDetailTO;
import com.sifcoapp.objects.sales.to.ClientCrediTO;
import com.sifcoapp.report.common.numerosAletras;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valentin
 */
public class PrintNotes extends HttpServlet {

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
        //variables
        ClientCrediTO credit = new ClientCrediTO();
        SupplierTO debit = new SupplierTO();

        CatalogTO _R = new CatalogTO();
        String numberToletter = null;
        Double total = 0.0;
        int tipoDoc = 0;

        //letras num
        String numberToletter1 = "", numberToletter2 = "", name1 = "", name2 = "", registro = "", tipoDocu = "", ref = "";

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

        try {
            convertNumber = new numerosAletras() {
            };
            AdminEJBService = new AdminEJBClient();
            tipoDoc = Integer.parseInt(request.getParameter("tip"));

            if (tipoDoc == 1) {//nota de credito - ventas(nota de credito)
                SalesEJBService = new SalesEJBClient();
                credit = SalesEJBService.getClientCrediByKey(Integer.parseInt(request.getParameter("foo")));
                _R = AdminEJBService.findCatalogByKey(credit.getPeymethod(), 8);
                total = credit.getDoctotal() - credit.getVatsum();
                numberToletter = convertNumber.convertNumberToLetter(credit.getDoctotal());
                int tam = numberToletter.length();
                if (tam > 40) {
                    numberToletter1 = numberToletter.substring(0, 40);
                    numberToletter2 = numberToletter.substring(40, tam);
                } else {
                    numberToletter1 = numberToletter;
                }

                String name = credit.getCardcode() + "-" + credit.getCardname().toUpperCase();
                int tam2 = name.length();
                if (tam2 > 30) {
                    name1 = name.substring(0, 35);
                    name2 = name.substring(35, tam2);
                } else {
                    name1 = name;
                }

                if (credit.getNumatcard() != null) {
                    ref = credit.getNumatcard();
                }

            } else {
                if (tipoDoc == 2) {//nota de debito - compras(nota de credito)
                    PurchaseEJBClient = new PurchaseEJBClient();
                    debit = PurchaseEJBClient.getSupplierByKey(Integer.parseInt(request.getParameter("foo")));
                    _R = AdminEJBService.findCatalogByKey(debit.getPeymethod(), 8);
                    total = debit.getDoctotal() - debit.getVatsum();
                    numberToletter = convertNumber.convertNumberToLetter(debit.getDoctotal());
                    int tam = numberToletter.length();
                    if (tam > 40) {
                        numberToletter1 = numberToletter.substring(0, 40);
                        numberToletter2 = numberToletter.substring(40, tam);
                    } else {
                        numberToletter1 = numberToletter;
                    }

                    String name = debit.getCardcode() + "-" + debit.getCardname().toUpperCase();
                    int tam2 = name.length();
                    if (tam2 > 30) {
                        name1 = name.substring(0, 35);
                        name2 = name.substring(35, tam2);
                    } else {
                        name1 = name;
                    }

                    if (debit.getNumatcard() != null) {
                        ref = debit.getNumatcard();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR: proceso by key fallo, o uno de sus componentes...");
        }

        //fecha
        Calendar fecha = Calendar.getInstance();
        if (tipoDoc == 1) {
            fecha.setTime(credit.getDocdate());
        } else {
            fecha.setTime(debit.getDocdate());
        }

        int year = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String fechaImp = dia + "/" + mes + "/" + year;

        response.setContentType("text/html;charset=UTF-8");

        if (tipoDoc == 1) {
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "\n"
                        + "    <head>\n"
                        + "        <title>Nota de Credito</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "<script type=\"text/javascript\">\n"
                        + "window.print();\n"
                        + "window.open('', '_self', ''); window.close(); \n"
                        + "</script>"
                        + "    </head>\n"
                        + "\n"
                        + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                        + "        <!-- tamanio de factura 566x453px-->\n"
                        + "        <div style=\"width: 550px; height: 650px\">\n"
                        + "            <table style=\"width:100%\" border=\"0\">\n"
                        + "                <!-- primero -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\" width: 19px; height: 120px \"/>\n"
                        + "                    <td style=\"\">\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 50px\">\n"
                        + "                                <td style=\"width: 60%\"/>\n"
                        + "                                <td style=\"width: 20%; vertical-align: bottom\">\n"
                        + "                                    " + ref + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 51px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 60px\">\n"
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
                        + "                                <td style=\"width: 70%\"></td>\n"
                        + "                                <td >\n"
                        + "                                    " + fechaImp + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    " + name1 + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>" + name2 + "</td>\n"
                        + "                                <td>\n"
                        + "                                    " + registro + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + _R.getCatvalue().toUpperCase() + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 18px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + tipoDocu + "\n"
                        + "                                </td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 25px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td></td>\n"
                        + "                </tr>\n"
                        + "                <!-- tercero -->\n"
                        + "                <tr>\n"
                        + "                    <td></td>\n"
                        + "                    <td>\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 35px\">\n"
                        + "                                <td style=\"width:15%\"/>\n"
                        + "                                <td style=\"width: 60%\"/>\n"
                        + "                                <td style=\"width: 15%\"/>\n"
                        + "                                <td style=\"width: 0%\"/>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <!--DETALLES-->\n");

                int tam = credit.getclientDetails().size();
                List det = credit.getclientDetails();
                for (int i = 0; i < 12; i++) {

                    if (i < tam) {
                        ClientCrediDetailTO var2 = (ClientCrediDetailTO) det.get(i);
                        String des = var2.getDscription();
                        int largo = des.length();
                        if (largo > 35) {
                            des = des.substring(0, 35);
                        }

                        out.println("                          <tr style=\"height: 19px\">\n"
                                + "                                <td>\n"
                                + "                                    " + var2.getQuantity() + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + des.toUpperCase() + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + truncarDouble(var2.getPrice()) + "\n"
                                + "                                </td>\n"
                                + "                                <td></td>\n"
                                + "                                <td>\n"
                                + "                                    " + truncarDouble(var2.getLinetotal()) + "\n"
                                + "                                </td>\n"
                                + "                            </tr>\n");

                    } else {
                        String vacio = "";
                        out.println("                          <tr style=\"height: 19px\">\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                                <td></td>\n"
                                + "                                <td>\n"
                                + "                                    " + vacio + "\n"
                                + "                                </td>\n"
                                + "                            </tr>\n");
                    }
                }

                out.println("                           <tr style=\"height: 25px\">\n"
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
                        + "                                    " + truncarDouble(total) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td>&nbsp; &nbsp; " + numberToletter1 + "</td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    " + truncarDouble(credit.getVatsum()) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td>&nbsp; &nbsp; " + numberToletter2 + "</td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td>\n"
                        + "                                    " + truncarDouble(credit.getDoctotal()) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 20px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
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
            }
        } else {
            if (tipoDoc == 2) {
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "\n"
                            + "    <head>\n"
                            + "        <title>Nota de Credito</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "<script type=\"text/javascript\">\n"
                            + "window.print();\n"
                            + "window.open('', '_self', ''); window.close(); \n"
                            + "</script>"
                            + "    </head>\n"
                            + "\n"
                            + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                            + "        <!-- tamanio de factura 566x453px-->\n"
                            + "        <div style=\"width: 550px; height: 650px\">\n"
                            + "            <table style=\"width:100%\" border=\"0\">\n"
                            + "                <!-- primero -->\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 19px; height: 120px \"/>\n"
                            + "                    <td style=\"\">\n"
                            + "                        <table style=\"width:100%\" border=\"0\">\n"
                            + "                            <tr style=\"height: 50px\">\n"
                            + "                                <td style=\"width: 60%\"/>\n"
                            + "                                <td style=\"width: 20%; vertical-align: bottom\">\n"
                            + "                                    " + ref + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 51px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 60px\">\n"
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
                            + "                                <td style=\"width: 70%\"></td>\n"
                            + "                                <td >\n"
                            + "                                    " + fechaImp + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    " + name1 + "\n"
                            + "                                </td>\n"
                            + "                                <td>" + registro + "</td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>" + name2 + "</td>\n"
                            + "                                <td>\n"
                            + "                                    \n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + _R.getCatvalue().toUpperCase() + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 18px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + tipoDocu + "\n"
                            + "                                </td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 25px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                        </table>\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "                <!-- tercero -->\n"
                            + "                <tr>\n"
                            + "                    <td></td>\n"
                            + "                    <td>\n"
                            + "                        <table style=\"width:100%\" border=\"0\">\n"
                            + "                            <tr style=\"height: 35px\">\n"
                            + "                                <td style=\"width:15%\"/>\n"
                            + "                                <td style=\"width: 60%\"/>\n"
                            + "                                <td style=\"width: 15%\"/>\n"
                            + "                                <td style=\"width: 0%\"/>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <!--DETALLES-->\n");

                    int tam = debit.getsupplierDetails().size();
                    List det = debit.getsupplierDetails();
                    for (int i = 0; i < 12; i++) {

                        if (i < tam) {
                            SupplierDetailTO var2 = (SupplierDetailTO) det.get(i);
                            String des = var2.getDscription();
                            int largo = des.length();
                            if (largo > 35) {
                                des = des.substring(0, 35);
                            }

                            out.println("                          <tr style=\"height: 19px\">\n"
                                    + "                                <td>\n"
                                    + "                                    " + var2.getQuantity() + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + des.toUpperCase() + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + truncarDouble(var2.getPrice()) + "\n"
                                    + "                                </td>\n"
                                    + "                                <td></td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + truncarDouble(var2.getLinetotal()) + "\n"
                                    + "                                </td>\n"
                                    + "                            </tr>\n");

                        } else {
                            String vacio = "";
                            out.println("                          <tr style=\"height: 19px\">\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                                <td></td>\n"
                                    + "                                <td>\n"
                                    + "                                    " + vacio + "\n"
                                    + "                                </td>\n"
                                    + "                            </tr>\n");
                        }
                    }

                    out.println("                           <tr style=\"height: 25px\">\n"
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
                            + "                                    " + truncarDouble(total) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td>&nbsp; &nbsp; " + numberToletter1 + "</td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    " + truncarDouble(debit.getVatsum()) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td>&nbsp; &nbsp; " + numberToletter2 + "</td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td>\n"
                            + "                                    " + truncarDouble(debit.getDoctotal()) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\"height: 20px\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
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
                }
            }
        }

    }

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
        df = new DecimalFormat("#,##0.00");
        return df.format(doctotal);
    }

}//cierre de clase
