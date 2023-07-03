package com.dyc.controller;

import com.dyc.constants.SystemConstants;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.dto.AddCommentDto;
import com.dyc.domain.entity.Comment;
import com.dyc.service.CommentService;
import com.dyc.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//增强swagger的可读性
@Api(tags = "评论",description = "评论相关接口")
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    //swagger接口描述配置
    @ApiOperation(value = "文章评论列表",notes = "获取文章评论信息")
    //文章评论列表
    @GetMapping("/commentList")
    //swagger接口参数配置
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id"),
            @ApiImplicitParam(name = "pageNum", value = "第几页")
    }
    )
    public ResponseResult commentList(String commentType,Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }


    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto addcomment){
        Comment comment = BeanCopyUtils.copyBean(addcomment, Comment.class);
        return commentService.addComment(comment);
    }

    //友联评论列表
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
