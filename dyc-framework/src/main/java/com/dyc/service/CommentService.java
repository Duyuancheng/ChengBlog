package com.dyc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-31 15:37:28
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

