package com.dyc.job;

import com.dyc.domain.entity.Article;
import com.dyc.service.ArticleService;
import com.dyc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCount {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    //每隔；两个小时更新redis的浏览量数据到数据库
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("ArticleViewCount");

        //entrySet()的键封装成article的id值封装成article的viewCount
        List<Article>articles= viewCountMap.entrySet()
                .stream()
                .map(entry->new Article(Long.valueOf(entry.getKey()),entry.getValue().longValue())).collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
