/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.test;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.objects.accounting.to.AccPeriodOutTO;
import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.BranchArticlesTO;
import com.sifcoapp.objects.admin.to.CatalogTO;
import com.sifcoapp.objects.admin.to.TablesCatalogTO;
import com.sifcoapp.objects.catalogos.Common;
import com.sifcoapp.objects.common.to.ResultOutTO;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ps05393
 */
public class Test2 {

    private static AccountingEJBClient AccountingEJBService = null;
    private static AdminEJBClient AdminEJBService;

    public static void main(String[] args) {
        try {
            if (AccountingEJBService == null) {
                AccountingEJBService = new AccountingEJBClient();
            }
            if 		(AdminEJBService==null)
                AdminEJBService=new AdminEJBClient();
            List lstPeriods = new Vector();
            lstPeriods = AccountingEJBService.getAccPeriods();
            System.out.println(lstPeriods);
//        Iterator<AccPeriodOutTO> iterator = lstPeriods.iterator();
//        while (iterator.hasNext()) {
//            //System.out.println(iterator.next());
//            AccPeriodOutTO accPeriodOutTO = (AccPeriodOutTO) iterator.next();
//            System.out.println("->" + accPeriodOutTO.getNombrePeriodo());
//        }
            
            List catlgLst=null;
            
            //catlgLst=AdminEJBService.findCatalog("cg_paises");
            //System.out.println("luego de servicio");
            
            //Iterator<CatalogTO> iterator = catlgLst.iterator();
            //while (iterator.hasNext()) {
            //System.out.println(iterator.next());
            //CatalogTO catalogTO=(CatalogTO)iterator.next();
//			System.out.println("->"+catalogTO.getValueCatlg());
//                        System.out.println("->"+catalogTO.getCodeCatlg());
            
            //}
            
            
            //List catlgLst=null;
            
            //catlgLst=AdminEJBService.getTablesCatalog();
//		System.out.println("luego de servicio");
//		Iterator<TablesCatalogTO> iterator = catlgLst.iterator();
//		while (iterator.hasNext()) {
//			//System.out.println(iterator.next());
//			TablesCatalogTO _returnTO=(TablesCatalogTO)iterator.next();
//			System.out.println("Code: ->"+_returnTO.getCode());
//			System.out.println("Name: ->"+_returnTO.getName());
            // }
            
            
            
            //   List catlgLst=null;
            
            catlgLst=AdminEJBService.findCatalog("paises");
            System.out.println("luego de servicio mod   ");
            Iterator<CatalogTO> iterator = catlgLst.iterator();
            while (iterator.hasNext()) {
                //System.out.println(iterator.next());
                CatalogTO catalogTO=(CatalogTO)iterator.next();
                System.out.println("--->"+ catalogTO.getCatcode() + "-"+ catalogTO.getCatvalue()+ "-");
            }
            
            ResultOutTO _result= new ResultOutTO();
            ArticlesTO parameters = new ArticlesTO();
            parameters.setItemCode("art-7");
            parameters.setItemName("Nombre Prueba 6");
            parameters.setUserSign(2);
            parameters.setItemType("S");
            parameters.setNumInBuy(54.2);
            parameters.setNumInSale(12.23);
            parameters.setOnHand(2.5);
            parameters.setPurPackUn(21.2);
            parameters.setSalPackUn(12.2);
            parameters.setAvgPrice(4.0);
            
            List branch = new Vector();
            
            BranchArticlesTO branch1 = new BranchArticlesTO();
            branch1.setIsasociated(true);
            branch1.setIscommited(100.2);
            branch1.setItemcode("art-007");
            branch1.setLocked("Y");
            branch1.setWhscode("suc-001");
            branch1.setMinstock(1.0);
            branch1.setMaxstock(10.2);
            branch1.setMinstock(1.2);
            branch1.setOnhand(10.2);
            branch1.setOnhand1(10.2);
            branch1.setOnorder(2.5);
            branch1.setMinorder(20.2);
            branch.add(branch1);
            
            parameters.setBranchArticles(branch);
            
            // parameters.setValidFrom((Date)"23/23/23" );
            
            // Agregar
            
            _result = AdminEJBService.cat_articles_mtto(parameters,
                    Common.MTTOINSERT);
            
            
            System.out.println("luego de servicio");
            System.out.println(_result);   
        } catch (Exception ex) {
            Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                
    }
}