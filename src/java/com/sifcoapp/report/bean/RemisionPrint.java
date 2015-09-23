/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.SalesEJBClient;
import com.sifcoapp.objects.sales.to.DeliveryDetailTO;
import com.sifcoapp.objects.sales.to.DeliveryTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemisionPrint extends HttpServlet {

    //private static AdminEJBClient AdminEJBService;
    private static SalesEJBClient SalesEJBService;
    //private numerosAletras convertNumber;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request2 servlet request2
     * @param response2 servlet response2
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request2, HttpServletResponse response2) throws ServletException, IOException {
        DeliveryTO var = new DeliveryTO();
        Double total = 0.0;
        try {
            SalesEJBService = new SalesEJBClient();
            //AdminEJBService = new AdminEJBClient();

            var = SalesEJBService.getDeliveryByKey(Integer.parseInt(request2.getParameter("foo")));
            total = formatNumber(var.getDoctotal());

        } catch (Exception ex) {
            Logger.getLogger(RemisionPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
        response2.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response2.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <title>Remision Print</title>\n"
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
                    + "        <!-- tamanio de remision 590X794px-->\n"
                    + "        <div style=\"width: 535px; height: 700px\">\n"
                    + "\n"
                    + "            <table style=\"width:100%\" border=\"0\">\n"
                    + "                <!-- borde top y unica fila-->\n"
                    + "                <tr>\n"
                    + "                    <td style=\"height: 140px\">\n"
                    + "\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <!-- borde izquierdo -->\n"
                    + "                    <td style=\"width: 28px; height: 100%\"></td>\n"
                    + "\n"
                    + "                    <!-- contenido -->\n"
                    + "                    <td style=\" height: 100%\">\n"
                    + "                        <!-- ENCABEZADO -->\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "\n"
                    + "                            <!-- Encabezado 56px -->\n"
                    + "\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 16%\">\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 60%\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td >\n"
                    + "                                    " + var.getDocdate() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 60px\">\n"
                    + "                                </td>\n"
                    + "                                <td >\n"
                    + "                                    " + var.getCardname() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 60px\">\n"
                    + "                                </td>\n"
                    + "                                <td >\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 19px\">\n"
                    + "                                <td style=\"width: 60px\">\n"
                    + "                                </td>\n"
                    + "                                <td >\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 20%\">\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 30%\" valign=\"bot\">\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 28%\" valign=\"bot\">\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 22%\" valign=\"bot\">\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 60px\">\n"
                    + "                                </td>\n"
                    + "                                <td valign=\"bot\">\n"
                    + "                                    " + "" + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 30%\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 20%\" valign=\"bot\">\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 30%\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 20%\" valign=\"bot\">\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 17px\">\n"
                    + "                                <td style=\"width: 30%\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 20%\" valign=\"bot\">\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 30%\">\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 20%\" valign=\"bot\">\n"
                    + "                                    \n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "\n"
                    + "\n"
                    + "                            <!-- header detalles 30px -->\n"
                    + "                            <tr style=\"height: 55px\">\n"
                    + "                                <td colspan=\"7\"></td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <!-- DETALLES -->\n"
                    + "                        <table border=\"0\" style=\"table-layout: fixed; overflow-y: scroll\">\n"
                    + "                            <!-- Detalles de factura 148px -->\n");

            int tam = var.getDeliveryDetails().size();
            List det = var.getDeliveryDetails();
            for (int i = 0; i < 15; i++) {

                if (i < tam) {
                    DeliveryDetailTO var2 = (DeliveryDetailTO) det.get(i);
                    String detalle = null;
                    if (var2.getDscription().length()>31) {
                        detalle = var2.getDscription().substring(0, 31);
                    }else
                        detalle = var2.getDscription().toUpperCase();
                    out.println(
                            "                            <tr style=\"height: 18px; width: 100%\" >\n"
                            + "\n"
                            + "                                <td style=\"width: 65px\">" + var2.getQuantity() + "</td>\n"
                            + "                                <td style=\"width: 250px\">" + detalle + "</td>\n"
                            + "                                <td style=\"width: 70px\">$" + formatNumber(var2.getPrice()) + "</td>\n"
                            + "                                <td >$" + formatNumber(var2.getLinetotal()) + "</td>\n"
                            + "\n"
                            + "                            </tr>\n");

                } else {
                    String vacio = "";
                    out.println(
                            "<tr style=\"height: 18px; width: 100%\" >\n"
                            + "\n"
                            + "                                <td style=\"width: 65px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 250px\">" + vacio + "</td>\n"
                            + "                                <td style=\"width: 70px\">" + vacio + "</td>\n"
                            + "                                <td >" + vacio + "</td>\n"
                            + "\n"
                            + "                            </tr>\n");
                }
            }

            out.println("                            <!-- TOTAL -->\n"
                    + "                            <tr style=\"height: 60px\">\n"
                    + "                                <td ></td>\n"
                    + "                                <td ></td>\n"
                    + "                                <td ></td>\n"
                    + "                                <td >$" + formatNumber(var.getDoctotal()) + "</td>\n"
                    + "                            </tr>\n"
                    + "                </tr>\n"
                    + "            </table>\n"
                    + "        </td> \n"
                    + "\n"
                    + "        <!-- borde derecho -->\n"
                    + "        <td style=\"width: 28px; height: 100%\"></td>\n"
                    + "    </tr>\n"
                    + "    <tr>\n"
                    + "        <td style=\"height: 104px\">\n"
                    + "\n"
                    + "        </td>\n"
                    + "    </tr>\n"
                    + "</table>\n"
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
     * @param request2 servlet request2
     * @param response2 servlet response2
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request2, HttpServletResponse response2)
            throws ServletException, IOException {
        processRequest(request2, response2);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request2 servlet request2
     * @param response2 servlet response2
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request2, HttpServletResponse response2)
            throws ServletException, IOException {
        processRequest(request2, response2);
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

    //<editor-fold defaultstate="collapsed" desc="Formato Numeros NO USADA">
    public Double formatNumber(Double doctotal) {
        /*
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String st = nf.format(num);
        Double dou = Double.valueOf(st);
        return dou;*/
        return Math.floor(100 * doctotal) / 100;
    }

//</editor-fold>
}//cierre de clase

