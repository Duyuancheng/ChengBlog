package com.dyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dyc.constants.SystemConstants;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.Article;
import com.dyc.domain.vo.ArticleDetailVo;
import com.dyc.domain.vo.ArticleListVo;

import com.dyc.domain.entity.Category;
import com.dyc.domain.vo.HotArticleVo;
import com.dyc.domain.vo.PageVo;
import com.dyc.mapper.ArticleMapper;
import com.dyc.service.ArticleService;

import com.dyc.service.CategoryService;
import com.dyc.utils.BeanCopyUtils;
import com.dyc.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，然后封装成ResponseResult返回
        //条件查询
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        条件
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
//      最多查询10条数据
        Page<Article> page=new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

//        Bean拷贝
//        List<HotArticleVo> hotArticleVos=new ArrayList<>();
//        for(Article article:articles){
//            HotArticleVo hotArticleVo=new HotArticleVo();
//            BeanUtils.copyProperties(article,hotArticleVo);
//            hotArticleVos.add(hotArticleVo);
//        }

//        自定义拷贝工具类
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Long categoryId, Integer pageNum, Integer pageSize) {

        //查询条件
        LambdaQueryWrapper<Article> lambdaqueryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId就要求查询时要和传入的相同
        lambdaqueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        lambdaqueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //置顶文章显示到最前面（对isTop字段进行排序）
        lambdaqueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page= new Page<>(pageNum,pageSize);
        //查询结果就在上面的page当中
        page(page, lambdaqueryWrapper);

        //查询categoryName
        List<Article> articles = page.getRecords();
        //articleId去查询articleName进行设置
//        方法一
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
//        方法二
        articles.stream()
                .map(new Function<Article, Article>() {

                    @Override
                    public Article apply(Article article) {
                        //获取分类id，查询分类信息，获取分类名称
                        Category category = categoryService.getById(article.getCategoryId());
                        String name = category.getName();
                        //把分类名称设置给article
                        article.setCategoryName(name);
                        return article;
                    }
                }).collect(Collectors.toList());

        //        方法三
//        article= articles.stream()
//                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
//                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null){
            articleDetailVo.setCategoryName(category.getName());
            return ResponseResult.okResult(articleDetailVo);
        }

        return ResponseResult.errorResult(500,"封装响应错误");
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新Redis中对应id的浏览量
        redisCache.incrementCacheMapValue("ArticleViewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}
