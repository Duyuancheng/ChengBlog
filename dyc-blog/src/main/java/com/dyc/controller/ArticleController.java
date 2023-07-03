package com.dyc.controller;


import com.dyc.domain.ResponseResult;
import com.dyc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
    //查询热门文章，然后封装成ResponseResult返回
        ResponseResult result=articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Long categoryId,Integer pageNum,Integer pageSize){
        return articleService.articleList(categoryId,pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateviewCount(@PathVariable("id") Long id){
            return articleService.updateViewCount(id);
    }
}
