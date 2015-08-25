/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.warehouse.view;

import com.sifcoapp.assignment.bean.AccassignmentBean;
import com.sifcoapp.client.AdminEJBClient;
import java.io.Serializable;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import com.sifcoapp.objects.admin.to.BranchTO;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ps05393
 */
@ManagedBean(name = "warehouseView")
public class WareHouseView implements Serializable {

    private List<BranchTO> LstBranch;
    private AdminEJBClient AdminEJBService;

    public List<BranchTO> getLstBranch() {
        return LstBranch;
    }

    public void setLstBranch(List<BranchTO> LstBranch) {
        this.LstBranch = LstBranch;
    }

    /**
     * Creates a new instance of WareHouseView
     */
    public WareHouseView() {
    }

    @PostConstruct
    public void initForm() {
        this.setLstBranch(new Vector());
        BranchTO parameters = new BranchTO();
        parameters.setWhscode("SUC-001");
        parameters.setWhsname("Sucursal de pruebas");
        this.getLstBranch().add(parameters);
        String name = null;
        String code = null;
        List resp = null;
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }

        try {
            this.setLstBranch(AdminEJBService.getBranch(code, name));

        } catch (Exception ex) {
            Logger.getLogger(WareHouseView.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        

//        BranchTO parameters2=new BranchTO();
//        parameters2.setWhscode("SUC-001");
//        parameters2.setWhsname("Sucursal de pruebas");
//        this.getLstBranch().add(parameters2);
//        this.setArticlesLst(new Vector());
//        
//        ArticlesTO art=new ArticlesTO();
//        art.setItemCode("art1");
//        art.setItemName("articulo 1");
//        this.getArticlesLst().add(art);
//        
//        ArticlesTO art1=new ArticlesTO();
//        art1.setItemCode("art2");
//        art1.setItemName("articulo 2");
//        this.getArticlesLst().add(art1);
    }

    public void selectFromDialog(BranchTO bra) {
        RequestContext.getCurrentInstance().closeDialog(bra);
    }
}
