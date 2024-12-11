package com.example.biryanipot.impl;

import com.example.biryanipot.entity.Comments;
import com.example.biryanipot.enums.CommentStatus;
import com.example.biryanipot.repository.CommentsRepository;
import com.example.biryanipot.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public Comments addComment(Comments comment) {
        comment.setCreatedDate(LocalDateTime.now());
        comment.setStatus(CommentStatus.PENDING); // Correctly use the CommentStatus enum
        return commentsRepository.save(comment);
    }

    @Override
    public List<Comments> getCommentsByBlogId(Long blogId) {
        return commentsRepository.findByBlogId(blogId);
    }

    @Override
    public Comments updateCommentStatus(Long commentId, CommentStatus status) {
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comments comment = optionalComment.get();
            comment.setStatus(status);
            return commentsRepository.save(comment);
        }
        throw new RuntimeException("Comment not found with id: " + commentId);
    }

    @Override
    public void deleteComment(Long commentId) {
        if (commentsRepository.existsById(commentId)) {
            commentsRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
    }
}
