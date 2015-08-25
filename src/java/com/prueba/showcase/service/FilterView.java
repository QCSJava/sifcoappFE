package com.prueba.showcase.service;

import static com.prueba.ejb.Pricelist.articlesprices;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.prueba.showcase.domain.Car;
import com.sifco.inventory.bean.PriceListBean;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.admin.to.ArticlesPriceTO;
import com.sifcoapp.objects.admin.to.PricesListTO;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "dtFilterView")
@ViewScoped
public class FilterView implements Serializable {

    private static AdminEJBClient AdminEJBService;
    public List<ArticlesPriceTO> prices;

    private List<Car> cars;

    private List<Car> filteredCars;

    private Boolean value1 = true;
    private Boolean value2 = false;

    @ManagedProperty("#{carService}")
    private CarService service;

    @PostConstruct
    public void init() {
       // cars = service.createCars(10000);

    }

    public void showList() {
        try {

            if (AdminEJBService == null) {
                AdminEJBService = new AdminEJBClient();
            }
            PricesListTO para = new PricesListTO();
            System.out.println("Antes de la consulta getPricesListByKey: " + new Date());
            para = AdminEJBService.getPricesListByKey(2);
            System.out.println("Registros:" + para.getArticlesPrices().size() + " :  " + new Date());
            prices = para.getArticlesPrices();

            //this.selectPriceList = null;
            System.out.println("Antes de : RequestContext.getCurrentInstance()." + new Date());
            RequestContext.getCurrentInstance().update("frmPriceList");
            System.out.println("despues de : RequestContext.getCurrentInstance()." + new Date());

            System.out.println("Antes del showHideDialog  " + new Date());
            showHideDialog("dlgDetalles", 1);
            System.out.println("Despues del showHideDialog  " + new Date());

        } catch (Exception ex) {
            Logger.getLogger(PriceListBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showHideDialog(String name, int openClose) {
        try {
            RequestContext rc = RequestContext.getCurrentInstance();
            if (openClose == 1) {
                rc.execute("PF('" + name + "').show();");
            }
            if (openClose == 2) {
                rc.execute("PF('" + name + "').hide();");
            }

        } catch (Exception e) {

        }
    }

    public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

    public List<String> getBrands() {
        return service.getBrands();
    }

    public List<String> getColors() {
        return service.getColors();
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Car> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(List<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }

    public void setService(CarService service) {
        this.service = service;
    }

    /**
     * @return the prices
     */
    public List<ArticlesPriceTO> getPrices() {
        return prices;
    }

    /**
     * @param prices the prices to set
     */
    public void setPrices(List<ArticlesPriceTO> prices) {
        this.prices = prices;
    }
}
