/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.ejb;

import com.sifcoapp.client.AdminEJBClient;
import com.sifcoapp.client.InventoryEJBClient;
import com.sifcoapp.objects.admin.to.PricesListTO;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.inventory.to.GoodsReceiptDetailTO;
import com.sifcoapp.objects.inventory.to.GoodsreceiptTO;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Date;
/**
 *
 * @author Estela
 */
public class Pricelist {

    /**
     * @param args the command line arguments
     */
    private static AdminEJBClient AdminEJBService;

    public static void main(String[] args) {
        if (AdminEJBService == null) {
            AdminEJBService = new AdminEJBClient();
        }
        articlesprices();
    }

    public static void articlesprices() {

        ResultOutTO _result = new ResultOutTO();
        PricesListTO para = new PricesListTO();

        try {
            System.out.println(new Date());            
            para = AdminEJBService.getPricesListByKey(2);
            System.out.println("Registros:" + para.getArticlesPrices().size() );
            System.out.println(new Date());

        } catch (Exception e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

    }
}
