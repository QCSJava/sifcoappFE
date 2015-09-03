/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.util;

/**
 *
 * @author ri00642
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.sql.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.apache.commons.io.IOUtils;

public class ReportConfigUtil {

    /**
     * PRIVATE METHODS
     */
    private static void setCompileTempDir(ServletContext context, String uri) {
        System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
    }

    private static void setCompileTempDir(HttpServletRequest request, String uri) {
        System.setProperty("jasper.reports.compile.temp", request.getSession().getServletContext().getRealPath(uri));
    }

    /**
     * PUBLIC METHODS
     */
    public static boolean compileReport(ServletContext context, String compileDir, String filename) throws JRException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String jasperFileName = request.getSession().getServletContext().getRealPath("/");

        jasperFileName = request.getSession().getServletContext().getRealPath("/reports/compile") + "/" + filename + ".jasper";
        //String jasperFileName = context.getRealPath(compileDir + filename + ".jasper");
        File jasperFile = new File(jasperFileName);

        if (jasperFile.exists()) {
            return true; // jasper file already exists, do not compile again
        }
        try {
            // jasper file has not been constructed yet, so compile the xml file
            setCompileTempDir(request, compileDir);

            String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
            JasperCompileManager.compileReportToFile(xmlFileName);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, Connection conn) throws JRException {
        parameters.put("BaseDir", reportFile.getParentFile());

        /*parametro localidades para monedas estatico*/
        parameters.put(JRParameter.REPORT_LOCALE, Locale.US);

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);

        return jasperPrint;
    }

    public static String getJasperFilePath(ServletContext context, String compileDir, String jasperFile) {
        return context.getRealPath(compileDir + jasperFile);
    }

    public static String getJasperFilePath(HttpServletRequest request, String compileDir, String jasperFile) {
        return request.getSession().getServletContext().getRealPath(compileDir + jasperFile);
    }

    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out) throws JRException {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);

        exporter.exportReport();
    }

    public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/SampleReportJSF/servlets/image?image=");//SampleReportJSF is the name of the project

        exportReport(exporter, jasperPrint, out);
    }

    public static void exportReportAsExcel(JasperPrint jasperPrint, PrintWriter out, ServletContext context, ExternalContext ec, String fileName) throws JRException, FileNotFoundException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String ruta = context.getRealPath("WEB-INF") + "\\balance.xls";
        //OutputStream outputfile = new FileOutputStream(new File("d:/output/JasperReport1.xls"));//make sure to have the directory. excel file will export here
        OutputStream outputfile = new FileOutputStream(new File(ruta));//make sure to have the directory. excel file will export here

        // coding For Excel:
        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporterXLS.exportReport();
        outputfile.write(output.toByteArray());

        ec.responseReset();
        ec.setResponseContentType(ec.getMimeType(ruta));

        //ec.setResponseContentLength(contentLength); 
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls\"");

        InputStream input = new FileInputStream(ruta);
        OutputStream output2 = ec.getResponseOutputStream();

        IOUtils.copy(input, output2);

    }

    public static void exportToPDFFile(ServletContext context, ExternalContext ec, JasperPrint jasperPrint, String fileName) throws FileNotFoundException, IOException {
        String ruta = context.getRealPath("WEB-INF") + "\\" + fileName + ".pdf";
        System.out.println(ruta);
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, ruta);
            //enviandolo al browser
            ec.responseReset();
            ec.setResponseContentType(ec.getMimeType(ruta));
            //ec.setResponseContentLength(contentLength); 
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"");

            InputStream input = new FileInputStream(ruta);
            OutputStream output = ec.getResponseOutputStream();
            IOUtils.copy(input, output);

            System.out.println("Sending to browser...");
        } catch (JRException ex) {
            Logger.getLogger(ReportConfigUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //public static void exportReportASPDF(JasperPrint jasperPrint, String pdfPath, ExternalContext ec) {
    public static void exportReportASPDF(JasperPrint jasperPrint, HttpServletResponse response) {
        //JasperExportManager exporter = null;
        JRExporter exporter = null;
        String pdfName = "/reports/testReport.pdf";
        try {
            /* exporter.exportReportToPdfFile(jasperPrint,pdfPath);
            
            
             ec.responseReset(); 
             ec.setResponseContentType(ec.getMimeType(pdfPath)); 
             //ec.setResponseContentLength(contentLength); 
             ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + pdfName + "\""); 

             InputStream input = new FileInputStream(pdfPath);
             OutputStream output = ec.getResponseOutputStream();
             IOUtils.copy(input, output);

             System.out.println("Sending to browser...");
             */
            response.setContentType("application/pdf");

            //if ("application/pdf".equals(type)) {
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                    response.getOutputStream());

            exporter.exportReport();

        } catch (IOException ex) {
            Logger.getLogger(ReportConfigUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ReportConfigUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
