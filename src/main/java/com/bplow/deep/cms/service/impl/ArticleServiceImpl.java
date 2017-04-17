package com.bplow.deep.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.cms.domain.Article;
import com.bplow.deep.cms.mapper.ArticleMapper;
import com.bplow.deep.cms.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{
    
    @Autowired
    ArticleMapper  articleMapper;

    @Override
    public Article queryArticle(String id) {
        
        Article art = articleMapper.selectByPrimaryKey(Integer.parseInt(id));
        
        return art;
    }

    @Override
    public void createArticle(Article article) {
        
        articleMapper.insert(article);
        
    }

}
