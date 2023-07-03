package com.dyc.runner;

import com.dyc.domain.entity.Article;
import com.dyc.mapper.ArticleMapper;
import com.dyc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//继承CommandLineRunner实现run方法可以实现程序启动时的预处理
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String,Integer> viewCountMap =articles.stream()
                .collect(Collectors.toMap(new Function<Article, String>() {
                    @Override
                    public String apply(Article article) {
                        return article.getId().toString();
                    }
                }, new Function<Article, Integer>() {
                    @Override
                    public Integer apply(Article article) {
                        return article.getViewCount().intValue();//将Long类型转换成Integer类型
                    }
                }));
        //存储到redis中
        redisCache.setCacheMap("ArticleViewCount",viewCountMap);
    }
}
