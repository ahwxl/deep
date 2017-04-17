package com.bplow.deep.cms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.cms.domain.Article;
import com.bplow.deep.cms.service.ArticleService;

@ContextConfiguration(locations = { "/applicationContext.xml", "/spring/cxt-dao.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class ArticleServiceTest {
    
    @Autowired
    ArticleService articleService;
    
    @Test
    public void testQuery() throws IOException{
        
       Article art = articleService.queryArticle("1");
       
       System.out.println(IOUtils.toString(art.getContent(),"UTF-8"));
        
    }
    
    @Test
    public void testInsert() throws UnsupportedEncodingException{
        
        Article article = new Article();
        String cnt = "送你一朵小花！";
        article.setTitle("1233");
        article.setType(1);
        article.setContent(cnt.getBytes("UTF-8"));
        
        articleService.createArticle(article);
    }
    

}
