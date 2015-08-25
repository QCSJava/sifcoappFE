/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.ejb;

import com.sifcoapp.client.InventoryEJBClient;
import com.sifcoapp.objects.common.to.ResultOutTO;
import com.sifcoapp.objects.inventory.to.GoodsReceiptDetailTO;
import com.sifcoapp.objects.inventory.to.GoodsreceiptTO;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Estela
 */
public class NewMain {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GoodReceipt_mtto_condetalle();
        
        

		String v_method = args[0];

		/*
		 * List lstPeriods=new Vector();
		 * 
		 * lstPeriods=AccountingEJBService.getAccPeriods();
		 * 
		 * System.out.println(lstPeriods);
		 */

		
                
                
	}

    /**
     *
     */
    public static void GoodReceipt_mtto_condetalle() {
         InventoryEJBClient Inventory = null;
        
        if (Inventory == null)
			Inventory = new InventoryEJBClient();
        
		int _result=0;
		GoodsReceiptDetailTO document = new GoodsReceiptDetailTO();

		GoodsreceiptTO parameters = new GoodsreceiptTO();
		
		List prueba = new Vector();
		GoodsReceiptDetailTO document1 = new GoodsReceiptDetailTO();
		
		//document.setDocentry(1);
		document.setLinenum(5);
		document.setItemcode("ART-001");
		document.setDscription("Articulo de prueba");
		document.setQuantity(10.25);
		document.setOpenqty(1.56);
		document.setPrice(11.25);
                document.setDocentry(10);
		//document.setLinetotal(5.6);
		//prueba.add(document);
		//document1.setDocentry(1);
		document1.setLinenum(6);
                document1.setDocentry(10);
		document1.setItemcode("ART-001");
		document1.setDscription("Articulo de prueba");
		document1.setQuantity(10.25);
		document1.setOpenqty(15.56);
		document1.setPrice(11.25);
		//document1.setLinetotal(5.6);
		//prueba.add(document1);
		parameters.setDocnum(485);
		parameters.setUsersign(1);
		//parameters.setDocentry(26);
		//parameters.setDoctotal(3.6);
		Date fecha= new Date();
		parameters.setDocdate(fecha);
		parameters.setGoodReceiptDetail(prueba);
		try {
                    ResultOutTO _res;
			_res = Inventory.inv_GoodsReceipt_mtto(parameters,1);
                        _result = _res.getDocentry();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error EJB " + e.getMessage());
		}

		System.out.println("luego de servicio");
		System.out.println(_result);

	}	
    
}
