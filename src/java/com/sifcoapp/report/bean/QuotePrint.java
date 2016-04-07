/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.EnterpriseTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Valentin
 */
public class QuotePrint extends HttpServlet {

    private numerosAletras convertNumber;
    private AdminEJBClient AdminEJBService;

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
        //servicios inicializacion
        AdminEJBService = new AdminEJBClient();

        //declaraciones
        HttpSession session = request.getSession(true);
        SalesTO newCot = new SalesTO();
        EnterpriseTO _res = new EnterpriseTO();
        String formaPago = "", tipoDoc = "";
        Double total = 0.0;

        //asignaciones
        try {
            newCot = (SalesTO) session.getAttribute("objSales");
            _res = AdminEJBService.getEnterpriseInfo();
        } catch (Exception ex) {
            Logger.getLogger(QuotePrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        //sets
        total = newCot.getDoctotal() - newCot.getVatsum();
        String numberToletter1 = "", numberToletter2 = "", numberToletter = "", vendedor = "";
        numberToletter = convertNumber.convertNumberToLetter(newCot.getDoctotal());
        int tam = numberToletter.length();
        if (tam > 30) {
            numberToletter1 = numberToletter.substring(0, 30);
            numberToletter2 = numberToletter.substring(30, tam);
        } else {
            numberToletter1 = numberToletter;
        }

        vendedor = request.getParameter("bar");

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

        //fecha
        Calendar fecha = Calendar.getInstance();
        //fecha.setTime(newCot.getDocdate());
        int year = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String fechaImp = dia + "/" + mes + "/" + year;

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. 
            
             + "<script type=\"text/javascript\">\n"
             + "window.print();\n"
             + "window.open('', '_self', ''); window.close(); \n"
             + "</script>"
            
             */
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <title>Cotización de Factura</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "<script type=\"text/javascript\">\n"
                    + "window.print();\n"
                    + "window.open('', '_self', ''); window.close(); \n"
                    + "</script>"
                    + "    </head>\n"
                    + "\n"
                    + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                    + "        <!-- tamanio de factura -->\n"
                    + "        <div style=\"width: 600px; height: 650px\">\n"
                    + "            <table style=\"width:100%\" border=\"0\">\n"
                    + "                <!-- primero -->\n"
                    + "                <tr>\n"
                    + "                    <td style=\" width: 19px\">\n"
                    + "                    </td>\n"
                    + "                    <td>\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <hr>\n"
                    + "                            <tr style=\"height: 12px; width: 100%\">\n"
                    + "                                <td style=\"font-size: 16px\">\n"
                    + "                                    " + _res.getCrintHeadr().toUpperCase() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 12px\">\n"
                    + "                                <td>\n"
                    + "                                    COTIZACIÓN\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 12px\">\n"
                    + "                                <td>\n"
                    + "                                    Dirección: " + _res.getCompnyAddr() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 12px\">\n"
                    + "                                <td>\n"
                    + "                                    Telefonos: " + _res.getPhone1() + " / " + _res.getPhone2() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 12px\">\n"
                    + "                                <td>\n"
                    + "                                    Email: " + _res.getE_Mail() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 12px\">\n"
                    + "                                <td>\n"
                    + "                                    <hr>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                    <td style=\" width: 19px\"></td>\n"
                    + "                </tr>\n"
                    + "                <!-- segundo -->\n"
                    + "                <tr>\n"
                    + "                    <td></td>\n"
                    + "                    <td>\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr style=\"height: 18px\">\n"
                    + "                                <td style=\"width: 10%\"/>\n"
                    + "                                <td style=\"width: 60%\"/>\n"
                    + "                                <td style=\"width: 15%\">\n"
                    + "                                    " + horaImp + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 18px\">\n"
                    + "                                <td style=\"width: 10%\"></td>\n"
                    + "                                <td style=\"width: 60%\">\n"
                    + "                                    " + newCot.getCardcode().toUpperCase() + " - " + newCot.getCardname().toUpperCase() + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 15%\">\n"
                    + "                                    " + fechaImp + "\n"
                    + "                                </td>\n"
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
                    + "                            <tr style=\"height: 19px\">\n"
                    + "                                <td style=\"width: 15%\">\n"
                    + "                                    Cantidad\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 50%\">\n"
                    + "                                    Descripcion\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 15%; text-align: right\">\n"
                    + "                                    Precio unitario\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 20%; text-align: right\">\n"
                    + "                                    Total\n"
                    + "                                </td>\n"
                    + "                            </tr>\n");

            int tam2 = newCot.getSalesDetails().size();
            List det = newCot.getSalesDetails();
            for (int i = 0; i < 11; i++) {

                if (i < tam2) {
                    SalesDetailTO var2 = (SalesDetailTO) det.get(i);
                    String des = var2.getDscription();
                    int largo = des.length();
                    if (largo > 35) {
                        des = des.substring(0, 35);
                    }

                    out.println("                           <tr style=\"height: 19px\">\n"
                            + "                                <td>\n"
                            + "                                    " + var2.getQuantity() + "\n"
                            + "                                </td>\n"
                            + "                                <td>\n"
                            + "                                    " + des.toUpperCase() + "\n"
                            + "                                </td>\n"
                            + "                                <td style=\"width: 20%; text-align: right\">\n"
                            + "                                    $ " + truncarDouble(var2.getPrice()) + "\n"
                            + "                                </td>\n"
                            + "                                <td style=\"width: 20%; text-align: right\">\n"
                            + "                                    $ " + truncarDouble(var2.getLinetotal()) + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n");

                } else {
                    String vacio = "";
                    out.println("                           <tr style=\"height: 19px\">\n"
                            + "                                <td>\n"
                            + "                                    " + vacio + "\n"
                            + "                                </td>\n"
                            + "                                <td>\n"
                            + "                                    " + vacio + "\n"
                            + "                                </td>\n"
                            + "                                <td style=\"width: 20%; text-align: right\">\n"
                            + "                                    " + vacio + "\n"
                            + "                                </td>\n"
                            + "                                <td style=\"width: 20%; text-align: right\">\n"
                            + "                                    " + vacio + "\n"
                            + "                                </td>\n"
                            + "                            </tr>\n");
                }
            }

            out.println("                            <tr style=\"height: 25px\">\n"
                    + "                            <hr>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                    <td></td>\n"
                    + "                </tr>\n"
                    + "                <!-- cuarto -->\n"
                    + "                <tr>\n"
                    + "                    <td style=\"height: 144px\"/>\n"
                    + "                    <td>\n"
                    + "                        <hr>\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr style=\"height: 20px\">\n"
                    + "                                <td style=\"width: 62%\">\n"
                    + "                                    &nbsp; &nbsp; "+ numberToletter1 +"\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 28%;text-align: right\">\n"
                    + "                                    SUMA\n"
                    + "                                </td>\n"
                    + "                                <td style=\"text-align: right\">\n"
                    + "                                    $ "+ truncarDouble(total) +"\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 20px\">\n"
                    + "                                <td>"
                    + "                                    &nbsp; &nbsp; "+ numberToletter2 +"\n"
                    + "                                </td>\n"
                    + "                                <td style=\"text-align: right\">\n"
                    + "                                    IVA\n"
                    + "                                </td>\n"
                    + "                                <td style=\"text-align: right\">\n"
                    + "                                    $ "+truncarDouble(newCot.getVatsum())+"\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 20px\">\n"
                    + "                                <td>\n"
                    + "                                    &nbsp; &nbsp; &nbsp; &nbsp; "+ vendedor.toUpperCase() +"\n"
                    + "                                </td>\n"
                    + "                                <td style=\"text-align: right\">\n"
                    + "                                    TOTAL\n"
                    + "                                </td>\n"
                    + "                                <td style=\"text-align: right\">\n"
                    + "                                    $ "+ truncarDouble(newCot.getDoctotal()) + "\n"
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
        df = new DecimalFormat("#,###0.00");
        return df.format(doctotal);
    }

}
