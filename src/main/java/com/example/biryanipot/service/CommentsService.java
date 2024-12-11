package com.example.biryanipot.service;

import com.example.biryanipot.entity.Comments;
import com.example.biryanipot.enums.CommentStatus;

import java.util.List;

public interface CommentsService {

    Comments addComment(Comments comment);

    List<Comments> getCommentsByBlogId(Long blogId);


Comments updateCommentStatus(Long commentId, CommentStatus status);

    void deleteComment(Long commentId);
}
