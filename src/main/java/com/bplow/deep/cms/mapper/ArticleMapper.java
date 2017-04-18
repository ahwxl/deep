package com.bplow.deep.cms.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.cms.domain.Article;

public interface ArticleMapper {
    
    Page<Article> queryForPage(Article record);

    Article selectByPrimaryKey(Integer id);

    int update(Article record);

    int insert(Article record);

    int delete(Integer id);
    
}