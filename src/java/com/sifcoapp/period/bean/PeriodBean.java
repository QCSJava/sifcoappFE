/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.period.bean;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccPeriodTO;
import java.sql.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.objects.admin.to.CatalogTO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;


@ManagedBean(name = "periodBean")
public class PeriodBean implements Serializable {

public PeriodBean() {}

//<editor-fold defaultstate="collapsed" desc="Variables">
    private int absentry;
    private String acccode;
    private String accname;
    private Date f_refdate;
    private Date t_refdate;
    private Date f_duedate;
    private Date t_duedate;
    private Date f_taxdate;
    private Date t_taxdate;
    private int periodstat;
    private int usersign;
    private List<AccPeriodTO> PeriodLst;
    private AccountingEJBClient AccountingEJBClient;
    private static AdminEJBClient AdminEJBService = null;
    public static final int MTTOINSERT = 1;
    public static final int MTTOUPDATE = 2;
    public static final int MTTODELETE = 3;
    private List<CatalogTO> catalogClassLst;
    private List<CatalogTO> catalogest;
    private String year;
    private String anio;
    private List<CatalogTO> catalogyear;
    private static final String CATALOGOYEAR = "cg_year";

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="G & S">
    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public List<CatalogTO> getCatalogyear() {
        return catalogyear;
    }

    public void setCatalogyear(List<CatalogTO> catalogyear) {
        this.catalogyear = catalogyear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<CatalogTO> getCatalogest() {
        return catalogest;
    }

    public void setCatalogest(List<CatalogTO> catalogest) {
        this.catalogest = catalogest;
    }

    public List<CatalogTO> getCatalogClassLst() {
        return catalogClassLst;
    }

    public void setCatalogClassLst(List<CatalogTO> catalogClassLst) {
        this.catalogClassLst = catalogClassLst;
    }

    public List<AccPeriodTO> getPeriodLst() {
        return PeriodLst;
    }

    public void setPeriodLst(List<AccPeriodTO> PeriodLst) {
        this.PeriodLst = PeriodLst;
    }

    public int getAbsentry() {
        return absentry;
    }

    public void setAbsentry(int absentry) {
        this.absentry = absentry;
    }

    public String getAcccode() {
        return acccode;
    }

    public void setAcccode(String acccode) {
        this.acccode = acccode;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public Date getF_refdate() {
        return f_refdate;
    }

    public void setF_refdate(Date f_refdate) {
        this.f_refdate = f_refdate;
    }

    public Date getT_refdate() {
        return t_refdate;
    }

    public void setT_refdate(Date t_refdate) {
        this.t_refdate = t_refdate;
    }

    public Date getF_duedate() {
        return f_duedate;
    }

    public void setF_duedate(Date f_duedate) {
        this.f_duedate = f_duedate;
    }

    public Date getT_duedate() {
        return t_duedate;
    }

    public void setT_duedate(Date t_duedate) {
        this.t_duedate = t_duedate;
    }

    public Date getF_taxdate() {
        return f_taxdate;
    }

    public void setF_taxdate(Date f_taxdate) {
        this.f_taxdate = f_taxdate;
    }

    public Date getT_taxdate() {
        return t_taxdate;
    }

    public void setT_taxdate(Date t_taxdate) {
        this.t_taxdate = t_taxdate;
    }

    public int getPeriodstat() {
        return periodstat;
    }

    public void setPeriodstat(int periodstat) {
        this.periodstat = periodstat;
    }

    public int getUsersign() {
        return usersign;
    }

    public void setUsersign(int usersign) {
        this.usersign = usersign;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Edicion en el data table">
    public void onRowEdit(RowEditEvent event) {

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        //FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUARDAR EN BASE">
    public void doSave() {

        System.out.println("Insertando");
        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        int resp;

//        Calendar cal = Calendar.getInstance();
//        System.out.println(dateFormat.format(cal.getTime().getYear()));
//        year=dateFormat.format(cal.getTime().getYear());
        int yearsave;
        yearsave = Integer.parseInt(this.year);
        try {
            resp = AccountingEJBClient.cat_accPeriod_mtto(yearsave, usersign, MTTOINSERT);
            //resp = AccountingEJBClient.cat_accPeriod_mtto(Integer.parseInt(this.year), usersign, MTTOINSERT);
        } catch (Exception ex) {
            Logger.getLogger(PeriodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doSavePeriod() {
        try {
            if (AccountingEJBClient == null) {
                AccountingEJBClient = new AccountingEJBClient();
            }

            int resp;
            int valor = 0;
            valor = Integer.parseInt(this.anio);
            AccPeriodTO parameters = new AccPeriodTO();
            resp = AccountingEJBClient.cat_accPeriod_mtto(valor, usersign, MTTOINSERT);

            if (resp == 0) {
                FacesMessage msg = new FacesMessage("Registro Insertado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                PeriodLst = AccountingEJBClient.getAccPeriods();

            } else {
                FacesMessage msg = new FacesMessage("No se Pudo Insertar", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            Logger.getLogger(PeriodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LOAD">
    @PostConstruct
    public void initForm() {

        if (AccountingEJBClient == null) {
            AccountingEJBClient = new AccountingEJBClient();
        }

        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        try {
            //    this.PeriodLst = new Vector();
            PeriodLst = AccountingEJBClient.getAccPeriods();
            catalogyear = AdminEJBService.findCatalog(CATALOGOYEAR);

        } catch (Exception ex) {
            //   Logger.getLogger(PeriodBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

}//cierre de clase
