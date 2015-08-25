/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifcoapp.assignment.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ps05393
 */
@FacesConverter("converterAcc")
public class ConverterAcc implements Converter{

    /**
     * Creates a new instance of ConverterAcc
     */
    public ConverterAcc() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        if(value != null && value.trim().length() > 0) {
//            try {
//                ThemeService service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
//                return service.getThemes().get(Integer.parseInt(value));
//            } catch(NumberFormatException e) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
//            }
//        }
//        else {
//            return null;
//        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//         if(object != null) {
//            return String.valueOf(((Theme) object).getId());
//        }
//        else {
//            return null;
//        }
//    }   
    }
    
}
