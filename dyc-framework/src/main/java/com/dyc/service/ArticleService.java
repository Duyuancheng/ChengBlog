package com.dyc.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.Article;

public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Long categoryId, Integer pageNum, Integer pageSize);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}
