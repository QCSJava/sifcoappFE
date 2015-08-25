/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.test;

import com.sifcoapp.client.AccountingEJBClient;
import com.sifcoapp.client.SecurityEJBClient;
import com.sifcoapp.objects.accounting.to.AccPeriodInTO;
import com.sifcoapp.objects.accounting.to.AccPeriodOutTO;
import com.sifcoapp.objects.security.to.ProfileDetOutTO;
import com.sifcoapp.objects.security.to.ProfileInTO;
import com.sifcoapp.objects.security.to.ProfileOutTO;
import com.sifcoapp.objects.security.to.UserAppInTO;
import com.sifcoapp.objects.security.to.UserAppOutTO;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ps05393
 */
public class Test {
	private static SecurityEJBClient SecurityEJBService=null;
        private static AccountingEJBClient AccountingEJBService=null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

		if 		(SecurityEJBService==null)
			SecurityEJBService=new SecurityEJBClient();
//		
		UserAppInTO usr = new UserAppInTO();
		UserAppOutTO usrRes = new UserAppOutTO();
		usr.setIdUserApp("pedro");
		usr.setPasswordUserApp("pedropw");
                usrRes=SecurityEJBService.UserValidate(usr);
//		
//		// TODO Auto-generated method stub
//
        	System.out.println(usrRes.getValidUser());
//		
//		ProfileInTO profileInTO=new ProfileInTO();
//		ProfileOutTO profileOutTO=new ProfileOutTO();
//		
//		//en este 
//                profileOutTO=SecurityEJBService.GetUserProfile(profileInTO);
//		
//                //nombre del perfil (admin, conta)
//		System.out.println(profileOutTO.getDesc_perfil());
//		while (true){
//			ProfileDetOutTO profileDetOutTO= new ProfileDetOutTO();
//			List profileDetOutTOLst= new Vector();
//			profileDetOutTOLst=profileOutTO.getProfile_det();
//			//lista de todos los objetos principales (menu)
//                        System.out.println(profileDetOutTO.getDesc_perfil_det());
//			
//			Iterator<ProfileDetOutTO> iterator = profileDetOutTOLst.iterator();
//			while (iterator.hasNext()) {
//				//System.out.println(iterator.next());
//				ProfileDetOutTO profileDetOutTO1=(ProfileDetOutTO)iterator.next();
//				System.out.println("->"+profileDetOutTO1.getDesc_perfil_det());
//				if (profileDetOutTO1.getNodeDetail()!=null){
//					//ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
//					List profileDetOutTOLst1= profileDetOutTO1.getNodeDetail();
//					Iterator<ProfileDetOutTO> iterator1 = profileDetOutTOLst1.iterator();
//					try{
//						while (iterator1.hasNext()) {
//							ProfileDetOutTO profileDetOutTO3=(ProfileDetOutTO)iterator1.next();
//							System.out.println("-->"+profileDetOutTO3.getDesc_perfil_det());
//							if (profileDetOutTO3.getNodeDetail()!=null){
//								//ProfileDetOutTO profileDetOutTO2=(ProfileDetOutTO)iterator.next();
//								List profileDetOutTOLst2= profileDetOutTO3.getNodeDetail();
//								Iterator<ProfileDetOutTO> iterator2 = profileDetOutTOLst2.iterator();
//								try{
//									while (iterator2.hasNext()) {
//										ProfileDetOutTO profileDetOutTO4=(ProfileDetOutTO)iterator2.next();
//										System.out.println("--->"+profileDetOutTO4.getDesc_perfil_det());
//									}	
//								}catch(Exception ex){
//									
//								}
//								
//							}
//						}	
//					}catch(Exception ex){
//						
//					}
//					
//				}
//			}
//			
//			break;
//		}
             /*   
         AccPeriodInTO parameters = new AccPeriodInTO();
		AccPeriodOutTO outputs = new AccPeriodOutTO();
    	 
    	
		parameters.setCantidadPeriodo("cant1");
		parameters.setCodigoPeriodo("CodigoPeriodo1");
		
		parameters.setEjercicio("Ejercicio1");
    	parameters.setFechaConta("20/11/2014");
    	parameters.setFechaDocumento("20/11/2014");
    	parameters.setFechaVencimiento("20/11/2014");
    	parameters.setIndicadorPeriodo("indicadorPeriodo1");
    	parameters.setInicioEjercicio("inicioEjercicio");
    	parameters.setNombrePeriodo("nombrePeriodo");
    	parameters.setStatusPeriodo("statusPeriodo");
    	parameters.setSubPeriodo("subPeriodo");
    	outputs=AccountingEJBService.AccAddPeriod(parameters);
    	System.out.println(outputs.getCodResp());       
		*/
	}
}

