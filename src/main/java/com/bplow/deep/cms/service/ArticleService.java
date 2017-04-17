package com.bplow.deep.cms.service;

import com.bplow.deep.cms.domain.Article;

public interface ArticleService {
    
    public Article queryArticle(String id);
    
    public void createArticle(Article article);

}
