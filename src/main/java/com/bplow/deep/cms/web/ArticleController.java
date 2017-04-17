package com.bplow.deep.cms.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.cms.domain.Article;
import com.bplow.deep.cms.service.ArticleService;

@Controller
@RequestMapping("/art")
public class ArticleController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    ArticleService articleService;
    
    
    @RequestMapping(value = "/createArticlePage")
    public String createArticlePage(HttpServletRequest httpRequest, Model view,Article article){
        logger.info("创建文章");
        
        
        return "cms/create-art";
    }
    
    @RequestMapping(value = "/createArticleFrame")
    public String createArticleFrame(HttpServletRequest httpRequest, Model view,Article article){
        logger.info("创建文章");
        
        
        return "{\"responseMsg\":\"success\"}";
    }

    
    @RequestMapping(value = "/createArticle")
    @ResponseBody
    public String createArticle(HttpServletRequest httpRequest, Model view,Article article){
        logger.info("创建文章");
        
        articleService.createArticle(article);
        
        return "{\"responseMsg\":\"success\"}";
    }
    
    @RequestMapping(value = "/preView")
    @ResponseBody
    public String preView(HttpServletRequest httpRequest, Model view,Article article){
        
        articleService.queryArticle(article.getId()+"");
        
        return "";
    }
    
    @RequestMapping(value = "/imageMng")
    public String imageMng(HttpServletRequest httpRequest, Model view,Article article){
        logger.info("创建文章");
        
        
        return "cms/image-mng";
    }
    
    @RequestMapping(value = "/image/ckfinderPop", method = RequestMethod.GET)
    public String toImageMngPage_(Model model){
        
        
        
        
        return "/cms/BrowersImagePop";
    }
}
