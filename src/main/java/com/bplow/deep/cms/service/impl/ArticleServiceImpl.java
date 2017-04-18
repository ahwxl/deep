package com.bplow.deep.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.cms.domain.Article;
import com.bplow.deep.cms.mapper.ArticleMapper;
import com.bplow.deep.cms.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{
    
    @Autowired
    ArticleMapper  articleMapper;

    @Override
    public Article queryArticle(Integer id) {
        
        Article art = articleMapper.selectByPrimaryKey(id);
        
        return art;
    }

    @Override
    public void createArticle(Article article) {
        
        articleMapper.insert(article);
        
    }

	@Override
	public Page<Article> queryArticlePage(Article article) {
		Page<Article> page = articleMapper.queryForPage(article);
		return page;
	}

	@Override
	public void deleteArticle(Integer id) {
		articleMapper.delete(id);
	}

	@Override
	public void editorArticle(Article article) {
		articleMapper.update(article);
	}

}
