/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sifco.bean;

import com.sifcoapp.objects.admin.to.ArticlesTO;
import com.sifcoapp.objects.admin.to.BranchArticlesTO;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ri00642
 */
@ManagedBean(name = "articlesView")
public class ArticlesSearchView implements Serializable{

    /**
     * Creates a new instance of ArticlesSearchView
     */
    public ArticlesSearchView() {
    }
  
    @PostConstruct
    public void initForm() {
        
        this.setArticlesLst(new Vector());
        
        ArticlesTO art=new ArticlesTO();
        art.setItemCode("art1");
        art.setItemName("articulo 1");
        this.getArticlesLst().add(art);
        
        ArticlesTO art1=new ArticlesTO();
        art1.setItemCode("art2");
        art1.setItemName("articulo 2");
        this.getArticlesLst().add(art1);
        
    }
    
    
    private List<ArticlesTO> articlesLst;

    /**
     * @return the articlesLst
     */
    public List<ArticlesTO> getArticlesLst() {
        return articlesLst;
    }

    /**
     * @param articlesLst the articlesLst to set
     */
    public void setArticlesLst(List<ArticlesTO> articlesLst) {
        this.articlesLst = articlesLst;
    }
    
     public void selectCarFromDialog(ArticlesTO art) {
        RequestContext.getCurrentInstance().closeDialog(art);
    }
}
