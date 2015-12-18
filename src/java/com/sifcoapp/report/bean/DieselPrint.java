/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.bean;

import com.sifco.sales.bean.SalesBean;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.SalesEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.sales.to.SalesDetailTO;
import com.sifcoapp.objects.sales.to.SalesTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class DieselPrint extends HttpServlet {

    private static SalesEJBClient SalesEJBService;
    private static AdminEJBClient AdminEJBService;

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
        Double total = 0.0;
        String tipoImp = "";
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
            SalesEJBService = new SalesEJBClient();
            var = SalesEJBService.getSalesByKey(Integer.parseInt(request.getParameter("foo")));
            tipoImp = request.getParameter("tip");
        } catch (Exception ex) {
            Logger.getLogger(DieselPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        //fecha
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(var.getDocdate());

        int year = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String fechaImp = dia + "/" + mes + "/" + year;

        //detalle
        SalesDetailTO det = new SalesDetailTO();
        det = (SalesDetailTO) var.getSalesDetails().get(0);

        Double IVA, FOV, COT, PRICE;
        IVA = calcularIVA(det);
        FOV = calcularFovial(det);
        COT = calcularCotrans(det);
        PRICE = det.getLinetotal() + IVA;

        String nombre = "", codigo = "", des = "";

        if (var.getCardname().length() > 40) {
            nombre = var.getCardname().substring(0, 40).toUpperCase();
        } else {
            nombre = var.getCardname().toUpperCase();
        }

        if (var.getCardcode().length() > 11) {
            codigo = var.getCardcode().substring(0, 11);
        } else {
            codigo = var.getCardcode();
        }

        if (det.getDscription().length() > 19) {
            des = det.getDscription().substring(0, 19);
        } else {
            des = det.getDscription().toUpperCase();
        }

        response.setContentType("text/html;charset=UTF-8");

        if (tipoImp.equals("3")) {//factura
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "\n"
                        + "    <head>\n"
                        + "        <title>Factura Diesel</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "<script type=\"text/javascript\">\n"
                        + "window.print();\n"
                        + "window.open('', '_self', ''); window.close(); \n"
                        + "</script>"
                        + "    </head>\n"
                        + "\n"
                        + "    <body style=\"font-size: 15px; font-family: sans-serif; line-height: 0px\">\n"
                        + "        <!-- tamanio de factura 566x453px-->\n"
                        + "        <div style=\"width: 800px\">\n"
                        + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                        + "                <!-- primero -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\" width: 100px; height: 132px \"/>\n"
                        + "                    <td style=\" width: 386px;  \">\n"
                        + "                        <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 100px\">\n"
                        + "                                <td style=\"width: 50%; vertical-align: bottom; text-align: right\">\n"
                        + "                                    " + var.getNumatcard() + "\n"
                        + "                                </td>\n"
                        + "                                <td style=\"width: 45%\"></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 80px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td style=\" width: 7px;  \"/>\n"
                        + "                </tr>\n"
                        + "                <!-- segundo -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\" width: 7px; height: 113px \"/>\n"
                        + "                    <td style=\" width: 386px;  \">\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 22px\">\n"
                        + "                                <td style=\"width: 100%\">\n"
                        + "                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                        + "                                    &nbsp;&nbsp;&nbsp;&nbsp;\n"
                        + "                                    " + nombre + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 19px\">\n"
                        + "                                <td style=\"width: 100%\">\n"
                        + "                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                        + "                                    &nbsp;&nbsp;&nbsp;&nbsp;\n"
                        + "                                    " + codigo + "\n"
                        + "                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                        + "                                    &nbsp;&nbsp;&nbsp; \n"
                        + "                                    " + fechaImp + " " + horaImp + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 19px\">\n"
                        + "                                <td style=\"width: 100%\"></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 19px\">\n"
                        + "                                <td style=\"width: 100%\"></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 19px\">\n"
                        + "                                <td style=\"width: 100%\"></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 14px\">\n"
                        + "                                <td style=\"width: 100%\"></td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td style=\" width: 7px;  \"/>\n"
                        + "                </tr>\n"
                        + "                <!-- tercero -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\" width: 7px; height: 94px \"/>\n"
                        + "                    <td style=\" width: 386px;  \">\n"
                        + "                        <table style=\"width:100%\" border=\"0\">\n"
                        + "                            <tr style=\"height: 50px\">\n"
                        + "                                <td style=\"width: 17%\"></td>\n"
                        + "                                <td style=\"width: 68%\"></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 17px\">\n"
                        + "                                <td style=\"text-align: right\">\n"
                        + "                                    " + truncarDouble(det.getQuantity()) + "\n"
                        + "                                </td>\n"
                        + "                                <td>\n"
                        + "                                    &nbsp;&nbsp;&nbsp;&nbsp;\n"
                        + "                                    " + des + "\n"
                        + "                                </td>\n"
                        + "                                <td style=\"text-align: right\">\n"
                        + "                                    " + truncarDouble(PRICE) + "\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 17px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 17px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                            <tr style=\"height: 17px\">\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                                <td></td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td style=\" width: 7px;  \"/>\n"
                        + "                </tr>\n"
                        + "                <!-- cuarto -->\n"
                        + "                <tr>\n"
                        + "                    <td style=\" width: 7px; height: 125px \"/>\n"
                        + "                    <td style=\" width: 386px;  \">\n"
                        + "                        <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                        + "                            <tr>\n"
                        + "                                <td style=\"width: 45%\"></td>\n"
                        + "                                <td style=\"width: 40%\"></td>\n"
                        + "                                <td>\n"
                        + "                                    <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                        + "                                        <tr style=\"height: 13px\">\n"
                        + "                                            <td></td>\n"
                        + "                                        </tr>\n"
                        + "                                        <tr style=\"height: 17px\">\n"
                        + "                                            <td></td>\n"
                        + "                                        </tr >\n"
                        + "                                        <tr style=\"height: 17px\">\n"
                        + "                                            <td></td>\n"
                        + "                                        </tr>\n"
                        + "                                        <tr style=\"height: 43px\">\n"
                        + "                                            <td></td>\n"
                        + "                                        </tr>\n"
                        + "                                        <tr style=\"height: 20px\">\n"
                        + "                                            <td style=\"text-align: right\">\n"
                        + "                                                " + truncarDouble(COT) + "\n"
                        + "                                            </td>\n"
                        + "                                        </tr>\n"
                        + "                                        <tr style=\"height: 23px\">\n"
                        + "                                            <td></td>\n"
                        + "                                        </tr>\n"
                        + "                                        <tr style=\"height: 21px\">\n"
                        + "                                            <td style=\"text-align: right\">\n"
                        + "                                                " + truncarDouble(FOV) + "\n"
                        + "                                            </td>\n"
                        + "                                        </tr>\n"
                        + "                                        <tr style=\"height: 21px\">\n"
                        + "                                            <td style=\"text-align: right\">\n"
                        + "                                                " + truncarDouble(var.getDoctotal()) + "\n"
                        + "                                            </td>\n"
                        + "                                        </tr>\n"
                        + "                                    </table>\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                    </td>\n"
                        + "                    <td style=\" width: 7px;  \"/>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>");
            }
        } else {
            if (tipoImp.equals("4")) {//ticket
                try (PrintWriter out = response.getWriter()) {
                    String vendedor = request.getParameter("bar");
                    if (vendedor.length()>12) {
                        vendedor = vendedor.substring(0,12);
                    }
                    out.println("<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "    <head>\n"
                            + "        <title>Ticket</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "<script type=\"text/javascript\">\n"
                            + "window.print();\n"
                            + "window.open('', '_self', ''); window.close(); \n"
                            + "</script>"
                            + "    </head>\n"
                            + "\n"
                            + "    <body style=\"font-size: 12px; font-family: sans-serif; line-height: 0px\">\n"
                            + "        <!-- tamanio de factura 566x453px-->\n"
                            + "        <div style=\"width: 218px\">\n"
                            + "            <!-- ENCABEZADO -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        ACOETMISAB DE R.L.\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        ESTACION DE SERVICO PRIVADA\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        VENTRA DE REPUESTOS\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        CAJA NO. 00\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        CARRETERA DE ORO KM 6 1/2\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        100 MTS. AL PTE PUENTE TICSA\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        SAN BARTOLO - ILOPANGO\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        SAN SALVADOR - EL SALVADOR\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        TEL: 2295-8643\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        REG. IVA NO. 119947-7\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        NIT: 0607-050200-101-9\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        TRANSPORTE PUBLICO COLECTIVO\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        DE PASAJEROS A TRAVÉS DE\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        MICROBUSES RUTA 29MB\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        VENTA DE PARTES, PIEZAS Y\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        ACCESORIOS DE VEHICULOS AUT.\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- HR -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100%\">\n"
                            + "                        <hr>\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- ENCABEZADO -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        BOMBA NO. 00\n"
                            + "                    </td>\n"
                            + "                    <td>\n"
                            + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        GLS:" + truncarDouble(det.getQuantity()) + " @ PRECIO: " + truncarDouble(det.getPriceafvat()) + "\n"
                            + "                    </td>\n"
                            + "                    <td>\n"
                            + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        " + des + " (G)\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        " + truncarDouble(PRICE) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        FOVIAL $0.20\n"
                            + "                    </td>\n"
                            + "                    <td>\n"
                            + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        GLS:" + truncarDouble(det.getQuantity()) + " @ $0.20\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $" + truncarDouble(FOV) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        COTRANS $0.10\n"
                            + "                    </td>\n"
                            + "                    <td>\n"
                            + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        GLS:" + truncarDouble(det.getQuantity()) + " @ $0.10\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $" + truncarDouble(COT) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left \">\n"
                            + "                        SUBTOTAL\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        " + truncarDouble(var.getDoctotal()) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- HR -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100%\">\n"
                            + "                        <hr>\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- TOTALES -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: right\">\n"
                            + "                        SUBTTL GRAVADO (G)\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $" + truncarDouble(PRICE) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: right\">\n"
                            + "                        SUBTTL EXENTO\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $" + truncarDouble(FOV + COT) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: right\">\n"
                            + "                        TOTAL VENTA\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $" + truncarDouble(var.getDoctotal()) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: right\">\n"
                            + "                    </td>\n"
                            + "                    <td>\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: right\">\n"
                            + "                        CONTADO:\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $" + truncarDouble(var.getDoctotal()) + "\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: right\">\n"
                            + "                        VUELTO:\n"
                            + "                    </td>\n"
                            + "                    <td style=\"text-align: right\">\n"
                            + "                        $0.00\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 75%; height: 10px; text-align: left\">\n"
                            + "                        ARTíCULO:"+request.getParameter("art")+"\n"
                            + "                    </td>\n"
                            + "                    <td></td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- HR -->\n"
                            + "            <table style=\"width:100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100%\">\n"
                            + "                        <hr>\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- FECHA Y HORA -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 50%; height: 10px\">\n"
                            + "                        "+fechaImp+"\n"
                            + "                    </td>\n"
                            + "                    <td style=\" width: 50%; text-align: right; height: 10px\">\n"
                            + "                        "+horaImp+"\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- ENCABEZADO -->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        SERIE #001 - TICKET #0001\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        LE ATENDIÓ: SR@. "+vendedor.toUpperCase()+"\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        GRACIAS POR SU AMABLE COMPRA\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        VUELVA PRONTO!!\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        G=GRAVADO IVA / NADA=EXENTO\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        NO. RES. AUTORIZ. CORRELATIVO\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        123456\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        FECHA AUTORIZ. CORRELATIVO\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        15/ENE/2015\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100px; height: 10px; text-align: center \">\n"
                            + "                        RANGO AUTORIZ. 0001-9999\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "            <!-- MARGEN BOT-->\n"
                            + "            <table style=\"width:100%; height: 100%\" border=\"0\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\" width: 100%; height: 375px; vertical-align: bottom\">\n"
                            + "                        .\n"
                            + "                    </td>\n"
                            + "                </tr>\n"
                            + "            </table>\n"
                            + "        </div>\n"
                            + "    </body>\n"
                            + "</html>");
                }
            }
        }

    }//cierre

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

    //<editor-fold defaultstate="collapsed" desc="funciones para calculos de impuestos">
    public Double calcularGravadas(SalesDetailTO detail) {

        return detail.getLinetotal();
    }

    public Double calcularExentas(SalesDetailTO detail) {
        return 0.0;

    }

    public Double calcularIVA(SalesDetailTO detail) {
        AdminEJBService = new AdminEJBClient();
        Double sumIVA = 0.0;
        ArrayList<SalesDetailTO> listaArt = new ArrayList<>();
        listaArt.add(detail);
        //Double sumTotal = 0.0;
        for (SalesDetailTO det : listaArt) {
            ArticlesTO art = new ArticlesTO();
            try {
                art = AdminEJBService.getArticlesByKey(det.getItemcode());
                // CatalogTO imp = new CatalogTO();
                //imp = (CatalogTO) art.getVatgourpsaList().get(0);

                if (art.getVatgourpsa().equals("FOV")) {
                    String[] valorImp = det.getVatgroup().split("\\|");
                    Double impIVA = Double.parseDouble(valorImp[0]);//0: IVA 1: FOV 2:COT
                    //Double impIVA = (aux+0.0)/100;

                    sumIVA = sumIVA + ((impIVA * det.getPrice()) * det.getQuantity());
                } else {
                    sumIVA = sumIVA + det.getVatsum();
                }

            } catch (Exception e) {
            }

        }

        //sumIVA = sumTotal * 0.13;
        return sumIVA;
    }

    public Double calcularRetencion(SalesDetailTO detail) {
        return 0.0;
    }

    public Double calcularFovial(SalesDetailTO detail) {
        AdminEJBService = new AdminEJBClient();
        Double sumFovial = 0.0;
        ArrayList<SalesDetailTO> listaArt = new ArrayList<>();
        listaArt.add(detail);
        for (SalesDetailTO det : listaArt) {
            ArticlesTO art = new ArticlesTO();
            try {
                art = AdminEJBService.getArticlesByKey(det.getItemcode());
                CatalogTO imp = new CatalogTO();
                imp = (CatalogTO) art.getVatgourpsaList().get(0);

                if (art.getVatgourpsa().equals(imp.getCatcode())) {
                    String[] valorImp = det.getVatgroup().split("\\|");
                    Double impFov = Double.parseDouble(valorImp[1]);//0: IVA 1: FOV 2:COT
                    //Double valorImp = impFov;
                    sumFovial = sumFovial + (impFov * det.getQuantity());
                }

            } catch (Exception ex) {
                //faceMessage("Error de calculo Fovial");
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sumFovial;

    }

    public Double calcularCotrans(SalesDetailTO detail) {
        AdminEJBService = new AdminEJBClient();
        Double sumCotrans = 0.0;
        ArrayList<SalesDetailTO> listaArt = new ArrayList<>();
        listaArt.add(detail);
        for (SalesDetailTO det : listaArt) {
            ArticlesTO art = new ArticlesTO();
            try {
                art = AdminEJBService.getArticlesByKey(det.getItemcode());
                CatalogTO imp = new CatalogTO();
                imp = (CatalogTO) art.getVatgourpsaList().get(0);

                if (art.getVatgourpsa().equals(imp.getCatcode())) {
                    String[] valorImp = det.getVatgroup().split("\\|");
                    Double impCot = Double.parseDouble(valorImp[2]);//0: IVA 1: FOV 2:COT
                    //Double valorImp = Double.parseDouble(imp.getCatvalue3());
                    sumCotrans = sumCotrans + (impCot * det.getQuantity());
                }

            } catch (Exception ex) {
                //faceMessage("Error de calculo Cotrans");
                Logger.getLogger(SalesBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return sumCotrans;
    }

//</editor-fold>
    private String truncarDouble(Double doctotal) {
        DecimalFormat df;
        df = new DecimalFormat("#,###.00");
        return df.format(Math.floor(100 * doctotal) / 100);
    }

}
