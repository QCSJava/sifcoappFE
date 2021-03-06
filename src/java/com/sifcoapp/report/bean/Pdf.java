/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifco.login.bean.Util;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.SalesEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.sales.to.SalesDetailTO;
import com.sifcoapp.objects.sales.to.SalesTO;
import com.sifcoapp.report.common.numerosAletras;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

public class Pdf extends HttpServlet {

    private static AdminEJBClient AdminEJBService;
    private static SalesEJBClient SalesEJBService;
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
        HttpSession session = request.getSession(true);
        String var5 = (String) session.getAttribute("username");
        
        

        SalesTO var = new SalesTO();
        CatalogTO _R = new CatalogTO();
        String numberToletter = null;
        Double total = 0.0;
        String nombreVendedor = "";
		String nombreCliente = "";
        //hora de impresion
        Calendar calendario = new GregorianCalendar();
        int hora, minutos;
        hora = calendario.get(Calendar.HOUR);
        minutos = calendario.get(Calendar.MINUTE);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 

        try {
            convertNumber = new numerosAletras() {
            };
            SalesEJBService = new SalesEJBClient();
            AdminEJBService = new AdminEJBClient();

            var = SalesEJBService.getSalesByKey(Integer.parseInt(request.getParameter("foo")));//quemado
            nombreVendedor = request.getParameter("bar");
            _R = AdminEJBService.findCatalogByKey(var.getPeymethod(), 8);
            total = var.getDoctotal();//formatNumber(var.getDoctotal());
            numberToletter = convertNumber.convertNumberToLetter(total);

        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

		if (var.getNamenp() == null || var.getNamenp().equals("")) {
            nombreCliente = var.getCardcode() + "-" + var.getBplname().toUpperCase();
        } else {
            nombreCliente = var.getNamenp().toUpperCase();
        }
		

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <title>Sales Print</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "<script type=\"text/javascript\">\n"
                    + "window.print();\n"
                    + "window.open('', '_self', ''); window.close(); \n"
                    + "</script>"
                    + "\n"
                    + "<script type=\"text/javascript\">\n"
                    + "function currencyFormat (num) {\n"
                    + "    return  num.toFixed(2).replace(/(\\d)(?=(\\d{3})+(?!\\d))/g, \"$1,\")\n"
                    + "}\n"
                    + "</script>"
                    + "\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                    + "        <!-- tamanio de factura 566x453px-->\n"
                    + "        <div style=\"width: 690px; height: 453px\">\n"
                    + "\n"
                    + "            <table style=\"width:100%\" border=\"0\">\n"
                    + "                <!-- borde top y unica fila-->\n"
                    + "                <tr>\n"
                    + "                    <td style=\"height: 145px\">\n"
                    + "\n"
                    + "                    </td>\n"
                    + "                    <td style=\"height: 145px\">\n"
                    + "                        <table border=\"0\" style=\"width: 100%\">\n"
                    + "                            <tr>\n"
                    + "                                <td style=\"height: 25px; width: 60%\">\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                                <td style=\"height: 25px; width: 25%\">\n"
                    + "                                    " + var.getNumatcard() + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"height: 25px\">\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                    </td>"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <!-- borde izquierdo -->\n"
                    + "                    <td style=\"width: 5px; height: 313px\"></td>\n"
                    + "\n"
                    + "                    <!-- contenido -->\n"
                    + "                    <td style=\"width: 556px; height: 313px\">\n"
                    + "                        <!-- ENCABEZADO -->\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "\n"
                    + "                            <!-- Encabezado 56px -->\n"
                    + "\n"
                    + "                            <tr style=\"height: 18px\">\n"
                    + "                                <td style=\"width: 10%\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 65%\">\n"
                    + "                                     " + nombreCliente + "\n"
                    + "                                </td>\n"
                    + "                                <td>\n"
                    + "                                    " + sdf.format(var.getDocdate()) + " HORA: " + hora + ":" + minutos + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 18px\">\n"
                    + "                                <td>\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 50px\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td>\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 18px\">\n"
                    + "                                <td></td>\n"
                    + "                                <td>\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                                <td>\n"
                    + "                                    " + _R.getCatvalue().toUpperCase() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 2px\"/>\n"
                    + "\n"
                    + "                            <!-- header detalles 30px -->\n"
                    + "                            <tr style=\"height: 55px\">\n"

                    + "                                <td colspan=\"7\"></td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <!-- DETALLES -->\n"
                    + "                        <table border=\"0\">\n"
                    + "                            <!-- Detalles de factura 148px -->\n");

            int tam = var.getSalesDetails().size();
            List det = var.getSalesDetails();
            for (int i = 0; i < 10; i++) {

                if (i < tam) {
                    SalesDetailTO var2 = (SalesDetailTO) det.get(i);
                    String des = var2.getDscription();
                    int largo = des.length();
                    if (largo > 35) {
                        des = des.substring(0, 35);
                    }

                    out.println(
                            "                            <tr style=\"height: 18px; width: 100%\" >\n"
                            + "\n"
                            + "                                <td style=\"width: 40px\">" + var2.getQuantity() + "</td>\n"


                            + "                                <td style=\"width: 100px\">" + var2.getItemcode() + "</td>\n"
                            + "                                <td style=\"width: 270px\">" + des.toUpperCase() + "</td>\n"
                            + "                                <td style=\"width: 60px\">$" + truncarDouble(var2.getPriceafvat()) + "</td>\n"
                            + "                                <td style=\"width: 50px\">" + "$0.00" + "</td>\n"
                            + "                                <td style=\"width: 50px\">" + "$0.00" + "</td>\n"


                            + "                                <td style=\"width: 10px\">$" + truncarDouble(var2.getGtotal()) + "</td>\n"
                            + "\n"
                            + "                            </tr>\n");

                } else {
                    String vacio = "";
                    out.println(
                            "<tr style=\"height: 18px; width: 100%\" >\n"
                            + "\n"
                            + "                                <td style=\"width: 40px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 100px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 270px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 60px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 50px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 50px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 10px\">" + vacio + "</td>\n"








                            + "\n"
                            + "                            </tr>\n");
                }
            }

            out.println("                            <tr style=\"height: 30px\">\n"
                    + "                            <br/>\n"
                    + "                </tr>\n"
                    + "            </table>\n"
                    + "\n"
                    + "            <!-- TOTALES -->\n"
                    + "            <table border=\"0\">\n"
                    + "                <!-- Parte de totales de factura -->\n"
                    + "                <tr style=\"height: 18px\">\n"
                    + "                    <td style=\"width: 50px\"/>\n"

                    + "                    <td valign=\"bot\" style=\"width: 545px\">\n"
                    + "                        " + numberToletter + "\n"
                    + "                    </td>\n"
                    + "                    <td valign=\"bot\">\n"
                    + "                        $" + truncarDouble(total) + "\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\"height: 15.8px\">\n"
                    + "                    <td/>\n"
                    + "                    <td>\n"


                    + "                        vendedor: " + nombreVendedor + "\n"
                    + "                    </td>\n"
                    + "                    <td>\n"
                    + "                        $0.00\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\"height: 15.8px\">\n"
                    + "                    <td style=\"width: 50px\"/>\n"
                    + "                    <td   style=\"width: 100px\">\n"


                    + "                    </td>\n"
                    + "                    <td>\n"
                    + "                        $0.00\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\"height: 15.8px\">\n"
                    + "                    <td style=\"width: 50px\"/>\n"
                    + "                    <td   style=\"width: 100px\">\n"


                    + "                    </td>\n"
                    + "                    <td>\n"
                    + "                        $0.00\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\"height: 15.8px\">\n"
                    + "                    <td style=\"width: 50px\"/>\n"
                    + "                    <td  style=\"width: 100px\">\n"


                    + "                    </td>\n"
                    + "                    <td>\n"
                    + "                        $" + truncarDouble(total) + "\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "            </table>\n"
                    + "        </td> \n"
                    + "\n"
                    + "        <!-- borde derecho -->\n"
                    + "        <td style=\"width: 5px; height: 313px\"></td>\n"
                    + "    </tr>\n"
                    + "    <tr>\n"
                    + "        <td style=\"height: 36px\">\n"
                    + "\n"
                    + "        </td>\n"
                    + "    </tr>\n"
                    + "</table>\n"
                    + "\n"
                    + "</div>\n"
                    + "</body>\n"
                    + "</html>"
            );
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
