package com.bplow.deep.cms.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.cms.domain.Article;

public interface ArticleService {
	
	public Page<Article> queryArticlePage(Article article);
    
    public Article queryArticle(Integer id);
    
    public void createArticle(Article article);
    
    public void deleteArticle(Integer id);
    
    public void editorArticle(Article article);

}
