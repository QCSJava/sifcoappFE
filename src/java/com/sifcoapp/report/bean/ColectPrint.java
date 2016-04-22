/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.BankEJBClient;
import com.sifcoapp.objects.bank.to.ColecturiaDetailTO;
import com.sifcoapp.objects.bank.to.ColecturiaTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ColectPrint extends HttpServlet {

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
        //HttpSession session = Util.getSession();
        BankEJBClient BankEJBClient = new BankEJBClient();
        ColecturiaTO newColect = new ColecturiaTO();
        String nombre = request.getParameter("bar");

        BigDecimal antes = new BigDecimal(0);
        BigDecimal despues = new BigDecimal(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  

        try {
            //nombre = session.getAttribute("username").toString().toUpperCase();
            newColect = BankEJBClient.get_ges_colecturiaByKey_print(Integer.parseInt(request.getParameter("foo")));
        } catch (Exception e) {
        }

        String doc = "";
        if (newColect.getSeries() == 1) {
            doc = "CREACION";
        } else {
            doc = "REVERSION";
        }

        for (Object lines : newColect.getColecturiaDetail()) {
            ColecturiaDetailTO line = (ColecturiaDetailTO) lines;

            if (!line.getDocsubtype().equals("N"))//Incluir solo los elementos que se visualice el saldo
            {
                //Sumar elementos conceptos que resten y restar los que sumen
                System.out.println(line.getValue1() + " - " + line.getValue3() + " - confirmer " +line.getConfirmed() );
                if (!line.getConfirmed().equals("N")) {
                    antes = antes.subtract(new BigDecimal(line.getValue1().replace(",", "")));
                    despues = despues.subtract(new BigDecimal(line.getValue3().replace(",", "")));
                } else {
                    antes = antes.add(new BigDecimal(line.getValue1().replace(",", "")));
                    despues = despues.add(new BigDecimal(line.getValue3().replace(",", "")));
                }

            }
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "\n"
                    + "    <head>\n"
                    + "        <title>Colect Print</title>\n"
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
                    + "        <div style=\"width: 1100px\">\n"
                    + "\n"
                    + "            <table style=\"width:100%\" border=\"0\">\n"
                    + "                <!-- Encabezado-->\n"
                    + "                <tr style=\"height: 10px\">\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <table style=\"height: 20px\">\n"
                    + "                            <tr >\n"
                    + "                                <td style=\"width: 20%\">\n"
                    + "                                    FECHA EMITIDO: " + sdf.format(newColect.getDocdate()) + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 25%\"> <strong>\n"
                    + "                                    ACOETMISAB DE R.L.\n"
                    + "                                </strong></td>\n"
                    + "                                <td style=\"width: 10%\">\n"
                    + "                                    RECIBO: " + newColect.getDocentry() + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <table style=\"height: 20px; width: 100%\">\n"
                    + "                            <tr >\n"
                    + "                                <td style=\"width: 75%\">\n"
                    + "                                    SOCIO: " + newColect.getCardcode() + " - " + newColect.getJrnlmemo().toUpperCase() + "\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 25%\">\n"
                    + "                                   DOCUMENTO: " + doc + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "\n"
                    + "                        <table style=\"height: 20px\">\n"
                    + "                            <tr >\n"
                    + "                                <td style=\"width: 47%\">\n"
                    + "                                    FACTURO: " + nombre + "\n"
                    + "                                </td>\n"
                    + "\n"
                    + "                                <td style=\"width: 35%\">\n"
                    + "                                    FECHA DE PAGADO: " + sdf.format(newColect.getTaxdate()) + "\n"
                    + "                                </td>\n"
                    + "                                <td >\n"
                    + "                                    APLICADO A: SOCIO\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <!-- Detalles -->\n"
                    + "                    <td style=\"width: 100%\">\n"
                    + "                        <!-- ENCABEZADO -->\n"
                    + "                        <hr/>\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n"
                    + "                            <tr >\n"
                    + "                                <td style=\"width: 46%\">\n"
                    + "                                    CONCEPTO DE PAGO\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 25%; text-align: right\">\n"
                    + "                                    SALDO ANTERIOR\n"
                    + "                                </td>\n"
                    + "                                <td style=\"width: 15%; text-align: right\">\n"
                    + "                                    VALOR CARGOS\n"
                    + "                                </td>\n"
                    + "                                <td style=\"text-align: right\">\n"
                    + "                                    SALDO ACTUAL\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                        <hr/>\n"
                    + "\n"
                    + "                        <!-- CONTENIDO -->\n"
                    + "                        <table style=\"width:100%\" border=\"0\">\n");

            int tam = newColect.getColecturiaDetail().size();
            List det = newColect.getColecturiaDetail();

            for (int i = 0; i < tam; i++) {
                ColecturiaDetailTO var2 = (ColecturiaDetailTO) det.get(i);
                String val1, val3;
                if (var2.getDocsubtype().equals("N")) {
                    val1 = "";
                    val3 = "";
                } else {
                    if (var2.getValue1() == null && var2.getValue3() == null) {
                        val1 = "0.00";
                        val3 = "0.00";
                    } else {
                        val1 = var2.getValue1();
                        val3 = var2.getValue3();
                    }

                }
                out.println(
                        "                            <tr style=\"height: 18px\">\n"
                        + "                                <td style=\"width: 36%\">\n"
                        + "                                    " + var2.getDscription().toUpperCase() + "\n"
                        + "                                </td>\n"
                        + "                                <td style=\"width: 10%; text-align: right\">\n"
                        + "                                    " + val1 + "\n"
                        + "                                </td>\n"
                        + "                                <td style=\"width: 10%; text-align: right\">\n"
                        + "                                    " + truncarDouble(var2.getPaidsum()) + "\n"
                        + "                                </td>\n"
                        + "                                <td style=\"width: 10%; text-align: right\">\n"
                        + "                                    " + val3 + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n");
            }

            Calendar calendario = new GregorianCalendar();
            int hora, minutos, segundos;
            hora = calendario.get(Calendar.HOUR);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            out.println("                        </table>\n"
                    + "\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "\n"
                    + "                <tr>\n"
                    + "                    <td style=\"width: 100%\">\n"
                    + "                        <table style=\"width: 100%\" border=\"0\">\n"
                    + "                            <tr>\n"
                    + "                                <!-- Izq -->\n"
                    + "                                <td style=\"width: 50%\">\n"
                    + "                                    <table>\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"height: 18px\">\n"
                    + "                                                OBSERV.:\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"height: 18px\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"height: 18px\">\n"
                    + "                                                RECIBI CONFORME:_________________________\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "\n"
                    + "                                <!-- Der -->\n"
                    + "                                <td style=\"width: 50%\">\n"
                    + "                                    <hr/>\n"
                    + "                                    <table style=\"width: 100%\" border=\"0\">\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"text-align: right; width: 39%\">\n"
                    + "                                                " + truncarDouble(antes.doubleValue()) + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: right; width: 32%\">\n"
                    + "                                                " + truncarDouble(newColect.getDoctotal()) + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: right\">\n"
                    + "                                                " + truncarDouble(despues.doubleValue()) + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                    <hr size=\"2\"/>\n"
                    + "                                    <table style=\"width: 100%\" border=\"0\">\n"
                    + "                                        <tr>\n"
                    + "                                            <td>\n"
                    + "                                                Viajes: 0\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 56%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                HORA: " + hora + ":" + minutos + ":" + segundos + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 300px\">	</tr>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "\n"
                    + "            </table>\n"
                    + "\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "</html> ");
        }
    }

    private String truncarDouble(Double doctotal) {
        NumberFormat df;
        df = new DecimalFormat("###,###0.00");
        return df.format(doctotal);
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
