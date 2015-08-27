/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valentin
 */
public class CheckPrint extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <title>Cheques Print</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                    + "        <!-- tamanio de factura 566x453px-->\n"
                    + "        <div style=\"width: 710px\">\n"
                    + "\n"
                    + "            <table style=\"width:100%\" border=\"0\">\n"
                    + "\n"
                    + "                <!-- espacio superior-->\n"
                    + "                <tr style=\"height: 60px\"></tr>\n"
                    + "\n"
                    + "                <tr>\n"
                    + "                    <!-- margen izquierdo-->\n"
                    + "                    <td/>\n"
                    + "\n"
                    + "                    <!-- CONTENIDO-->\n"
                    + "                    <td>\n"
                    + "                        <table border=\"0\" style=\"width: 100%\">\n"
                    + "\n"
                    + "                            <tr style=\"height: 350px\">\n"
                    + "                                <td>\n"
                    + "                                    <table border=\"0\" style=\"width: 100%\">\n"
                    + "                                        <tr style=\"height: 30px\">\n"
                    + "                                            <td style=\"width: 57%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                0123456789\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 40px\">\n"
                    + "                                            <td style=\"width: 58%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                325\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                    <table border=\"0\" style=\"width: 100%\">\n"
                    + "                                        <tr style=\"height: 25px\">\n"
                    + "                                            <td style=\"width: 15%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 30%; text-align: center\">\n"
                    + "                                                27 agosto\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 6%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 9%\">\n"
                    + "                                                15\n"
                    + "                                            </td>\n"
                    + "                                            <td >\n"
                    + "                                                250.00\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                    <table border=\"0\" style=\"width: 85%; margin-left: 100px\">\n"
                    + "                                        <tr style=\"height: 25px\">\n"
                    + "                                            <td style=\"width: 10%\"></td>\n"
                    + "                                            <td >\n"
                    + "                                                NELSON EDGARDO GAMEZ CRUZ.\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 25px\">\n"
                    + "                                            <td ></td>\n"
                    + "                                            <td>\n"
                    + "                                                DOSCIENTOS CINCUENTA DOLARES 00/100\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 25px\">\n"
                    + "                                            <td>\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 80px\">\n"
                    + "                                            <td>\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 40px; vertical-align: bottom\">\n"
                    + "                                            <td>\n"
                    + "                                                AGRICOLA\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 20px\">\n"
                    + "                                            <td>\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "\n"
                    + "                            <tr style=\"height: 30px\">\n"
                    + "                                <td>\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 500px\">\n"
                    + "                                <td>\n"
                    + "                                    <table style=\"width: 98%; margin-left: 15px\" border=\"0\">\n"
                    + "                                        <tr style=\"height: 10px\"/>\n"
                    + "                                        <tr style=\"height: 20px\">\n"
                    + "                                            <td style=\"width: 14%\">\n"
                    + "                                                CODIGO\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 55%\">\n"
                    + "                                                CONCEPTO\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                200.00\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                200.00\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 20px\">\n"
                    + "                                            <td style=\"width: 12%\">\n"
                    + "                                                CODIGO\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 55%\">\n"
                    + "                                                CONCEPTO\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                100.00\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                100.00\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 435px\"/>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 30px\">\n"
                    + "                                <td>\n"
                    + "                                    <table style=\"width: 100%\" border=\"0\">\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"width: 12%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 55%\">\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                100.00\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                100.00\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "\n"
                    + "                    <!-- Margen Derecho-->\n"
                    + "                    <td/>\n"
                    + "                </tr>\n"
                    + "            </table>\n"
                    + "\n"
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

}
