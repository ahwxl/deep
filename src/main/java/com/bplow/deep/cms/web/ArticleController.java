package com.bplow.deep.cms.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.cms.domain.Article;
import com.bplow.deep.cms.service.ArticleService;

@Controller
@RequestMapping("/art")
public class ArticleController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    ArticleService articleService;
    
    @RequestMapping(value = "/article")
    public String index(HttpServletRequest httpRequest, Model view,Article article){
    	
    	return "cms/article";
    }
    
    
    @RequestMapping(value = "/articleList")
    @ResponseBody
    public Page<Article> queryArticleList(HttpServletRequest httpRequest, Model view,Article article){
    	
        Page<Article> page = articleService.queryArticlePage(article);
    	
    	return page;
    }
    
    @RequestMapping(value = "/createArticlePage")
    public String createArticlePage(HttpServletRequest httpRequest, Model view,Article article){
        logger.info("创建文章");
        
        
        return "cms/art-create";
    }
    
    @RequestMapping(value = "/delArticle")
    @ResponseBody
    public String createArticleFrame(HttpServletRequest httpRequest, Model view,Article article){
        logger.info("删除文章");
        
        articleService.deleteArticle(article.getId());
        
        return "{\"responseMsg\":\"success\"}";
    }
    
    @RequestMapping(value = "/queryArticleById")
    @ResponseBody
    public Article queryArticle(HttpServletRequest httpRequest, Model view,Article article){
    	
    	Article art = articleService.queryArticle(article.getId());
    	try {
			art.setCnt(IOUtils.toString(art.getContent(), "GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return art;
    }
    
    @RequestMapping(value = "/modifyArticlePage")
    public String mofifyArticlePage(HttpServletRequest httpRequest, Model view,Article article){
    	
    	view.addAttribute("id", article.getId());
    	
    	return "cms/art-modify";
    }
    
    @RequestMapping(value = "/modifyArticleById")
    @ResponseBody
    public String mofifyArticle(HttpServletRequest httpRequest, Model view,Article article){
    	
    	articleService.editorArticle(article);
    	
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
        
        articleService.queryArticle(article.getId());
        
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
