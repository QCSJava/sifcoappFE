/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.report.common;

/**
 *
 * @author ri00642
 */
import com.sifcoapp.clientutility.ClientUtility;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import com.sifcoapp.report.util.ReportConfigUtil;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import dao.Database;

public abstract class AbstractReportBean implements Serializable {

    //@Resource(name = "jdbc/sifcoDBJDBC")
    private DataSource miPool;

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public enum ExportOption {

        PDF, HTML, EXCEL, RTF, FILE
    }

    //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    //String realPath  = (String) servletContext.getRealPath("/"); // Sustituye "/" por el directorio ej: "/upload"
    private ExportOption exportOption;
    private final String COMPILE_DIR = "/reports/design/";
    //private final String COMPILE_DIR = "/reports/compile/";
    //private String compileFileName = "productlist";//name of your compiled report file
    private String message;
    private Map<String, Object> parameters;
    private String reportName;
    private String fileName;

    public AbstractReportBean() {
        super();
        setExportOption(ExportOption.PDF);
        try {
            Context ctx = ClientUtility.getInitialContext();
            miPool = (DataSource) ctx.lookup("jdbc/sifcoDBJDBC");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void prepareReport() throws JRException, IOException {
        //conn=adminDAO.getConn();
        /////////////////////

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        //ReportConfigUtil.compileReport(context, getCompileDir(), getCompileFileName());
        //File reportFile = new File(ReportConfigUtil.getJasperFilePath(request, getCompileDir(), getCompileFileName() + ".jasper"));
        File reportFile = new File("C:\\reports\\", getCompileFileName() + ".jasper");

        //AdminDAO adminDAO = new AdminDAO();
        //System.err.println("Ip cliente: " + response.getRemoteAddr());
        Connection conn = null;
        try {
            conn = miPool.getConnection();

            JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, getReportParameters(), conn);
            conn.close();
            if (getExportOption().equals(ExportOption.HTML)) {
                ReportConfigUtil.exportReportAsHtml(jasperPrint, response.getWriter());
            } else if (getExportOption().equals(ExportOption.EXCEL)) {
                ReportConfigUtil.exportReportAsExcel(jasperPrint, response.getWriter(), context, externalContext, this.getFileName());
            } else if (getExportOption().equals(ExportOption.FILE)) {
                ReportConfigUtil.exportToPDFFile(context, externalContext, jasperPrint, this.getFileName());
            } else {
                //String filename = "archivo.pdf";
                //response.setHeader("Content-Disposition", "inline;filename=\"" + filename + "\"");
                request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);

                externalContext.redirect(request.getContextPath() + "/servlets/report/" + getExportOption() + "?faces-redirect=true");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public ExportOption getExportOption() {
        return exportOption;
    }

    public void setExportOption(ExportOption exportOption) {
        this.exportOption = exportOption;
    }

    protected Map<String, Object> getReportParameters() {
        return new HashMap<String, Object>();
    }

    protected String getCompileDir() {
        return COMPILE_DIR;
    }

    protected abstract String getCompileFileName();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the parameters
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
