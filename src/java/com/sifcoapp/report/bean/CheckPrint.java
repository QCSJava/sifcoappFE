/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.BankEJBClient;
import com.sifcoapp.objects.accounting.to.JournalEntryLinesTO;
import com.sifcoapp.objects.accounting.to.JournalEntryTO;
import com.sifcoapp.objects.bank.to.CheckForPaymentTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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

        BankEJBClient BankEJBClient = new BankEJBClient();
        AccountingEJBClient AccountingEJBClient = new AccountingEJBClient();
        String pkCheck  = request.getParameter("foo");
        String user     = request.getParameter("bar");
        JournalEntryTO det = new JournalEntryTO();
        ArrayList<JournalEntryLinesTO> listaDetalles = new ArrayList<>();
        CheckForPaymentTO cheque = new CheckForPaymentTO();
        try {
            cheque = BankEJBClient.get_cfp0_checkforpaymentByKey(Integer.parseInt(pkCheck));
            det = AccountingEJBClient.getJournalEntryByKey(Integer.parseInt(cheque.getTransref()));
        } catch (Exception ex) {
            Logger.getLogger(CheckPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Object obj : det.getJournalentryList()) {
            JournalEntryLinesTO line = (JournalEntryLinesTO) obj;
            listaDetalles.add(line);
        }

        //FECHA DEL CHEQUE FORMATEADA
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cheque.getPmntdate());

        int anio_ = calendar.get(Calendar.YEAR);
        int mes_ = calendar.get(Calendar.MONTH) + 1;
        int dia_ = calendar.get(Calendar.DAY_OF_MONTH);

        String nameMes = getMes(mes_);
        String var = anio_ + "";
        var = var.substring(2);
        String espacio = " ";
        String numCheque = "";
        if (cheque.getAcctnum() == null || cheque.getAcctnum().equals("")) {
            numCheque = "";
        }else
            numCheque = cheque.getAcctnum();
        

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
                    + "<script type=\"text/javascript\">\n"
                    + "window.print();\n"
                    + "window.open('', '_self', ''); window.close(); \n"
                    + "</script>"
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
                    + "                <tr style=\"height: 79px\"></tr>\n"
                    + "\n"
                    + "                <tr>\n"
                    + "                    <!-- margen izquierdo-->\n"
                    + "                    <td/>\n"
                    + "\n"
                    + "                    <!-- CONTENIDO-->\n"
                    + "                    <td>\n"
                    + "                        <table border=\"0\" style=\"width: 100%\">\n"
                    + "\n"
                    + "                            <tr style=\"height: 303px\">\n"
                    + "                                <td>\n"
                    + "                                    <table border=\"0\" style=\"width: 100%\">\n"
                    + "                                        <tr style=\"height: 30px\">\n"
                    + "                                            <td style=\"width: 60%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                " + numCheque + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 35px\">\n"
                    + "                                            <td style=\"width: 60%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                " + cheque.getDpstacct()+ "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                    <table border=\"0\" style=\"width: 100%\">\n"
                    + "                                        <tr style=\"height: 22px\">\n"
                    + "                                            <td style=\"width: 15%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 29%; text-align: center\">\n"
                    + "                                                " + dia_ + "" + espacio + "" + nameMes + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 6%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 12%\">\n"
                    + "                                                " + var + "\n"
                    + "                                            </td>\n"
                    + "                                            <td >\n"
                    + "                                                " + cheque.getChecksum() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                    <table border=\"0\" style=\"width: 85%; margin-left: 80px\">\n"
                    + "                                        <tr style=\"height: 18px\">\n"
                    + "                                            <td style=\"width: 10%\"></td>\n"
                    + "                                            <td >\n"
                    + "                                                " + cheque.getVendorname().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 18px\">\n"
                    + "                                            <td ></td>\n"
                    + "                                            <td>\n"
                    + "                                                " + cheque.getTotalwords().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 25px\">\n"
                    + "                                            <td>\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 60px\">\n"
                    + "                                            <td>\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table >\n"
                    + "                                    <table border=\"0\" style=\"width: 85%; margin-left: 80px\">\n"
                    + "                                        <tr style=\"height: 70px; vertical-align: bottom\">\n"
                    + "                                            <td style=\"width: 50%\">\n"
                    + "                                                " + cheque.getBankname().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 10%\" />\n"
                    + "                                            <td>\n"
                    + "                                                " + cheque.getSignature().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "\n"
                    + "                            <tr style=\"height: 70px\">\n"
                    + "                                <td>\n"
                    + "\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 400px\">\n"
                    + "                                <td>\n"
                    + "                                    <table style=\"width: 98%; margin-left: 15px\" border=\"0\">\n"
                    + "                                        <tr style=\"height: 20px\">\n"
                    + "                                            <td style=\"width: 14%\">\n"
                    + "                                                " + listaDetalles.get(0).getAccount().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 48%\">\n"
                    + "                                                " + listaDetalles.get(0).getAcctname().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                $ " + listaDetalles.get(0).getDebit() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                $ " + listaDetalles.get(0).getCredit() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 20px\">\n"
                    + "                                            <td style=\"width: 12%\">\n"
                    + "                                                " + listaDetalles.get(1).getAccount().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td >\n"
                    + "                                                " + listaDetalles.get(1).getAcctname().toUpperCase() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                $ " + listaDetalles.get(1).getDebit() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                $ " + listaDetalles.get(1).getCredit() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                        <tr style=\"height: 400px\"/>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 75px\">\n"
                    + "                                <td>\n"
                    + "                                    <table style=\"width: 100%\" border=\"0\">\n"
                    + "                                        <tr style=\"height: 55px\">\n"
                    + "\n"
                    + "                                        </tr>\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"width: 14%\">\n"
                    + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 48%\">\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                $ " + listaDetalles.get(0).getDebit() + "\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"text-align: center\">\n"
                    + "                                                $ " + listaDetalles.get(0).getDebit() + "\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </table>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"height: 35px\">\n"
                    + "                                <td>\n"
                    + "                                    <table border=\"0\" style=\"width: 100%\">\n"
                    + "                                        <tr>\n"
                    + "                                            <td style=\"width: 25%\"></td>\n"
                    + "                                            <td style=\"width: 25%\">\n"
                    + "                                               "+user.toUpperCase()+"\n"
                    + "                                            </td>\n"
                    + "                                            <td style=\"width: 25%\"></td>\n"
                    + "                                            <td></td>\n"
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

    private String getMes(int month) {
        String monthString;
        switch (month) {
            case 1:
                monthString = "ENERO";
                break;
            case 2:
                monthString = "FEBRERO";
                break;
            case 3:
                monthString = "MARZO";
                break;
            case 4:
                monthString = "ABRIL";
                break;
            case 5:
                monthString = "MAYO";
                break;
            case 6:
                monthString = "JUNIO";
                break;
            case 7:
                monthString = "JULIO";
                break;
            case 8:
                monthString = "AGOSTO";
                break;
            case 9:
                monthString = "SEPTIEMBRE";
                break;
            case 10:
                monthString = "OCTUBRE";
                break;
            case 11:
                monthString = "NOVIEMBRE";
                break;
            case 12:
                monthString = "DICIEMBRE";
                break;
            default:
                monthString = "MES INVALIDO";
                break;
        }
        return monthString;

    }

}
