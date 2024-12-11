package com.example.biryanipot.controller;
import com.example.biryanipot.enums.CommentStatus;

import com.example.biryanipot.entity.Comments;
import com.example.biryanipot.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping
    public ResponseEntity<Comments> createComment(@RequestBody Comments comment) {
        Comments createdComment = commentsService.addComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<Comments>> getCommentsByBlogId(@PathVariable Long blogId) {
        List<Comments> comments = commentsService.getCommentsByBlogId(blogId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}/status")
    public ResponseEntity<Comments> updateCommentStatus(@PathVariable Long commentId,
                                                        @RequestParam CommentStatus status) {
        Comments updatedComment = commentsService.updateCommentStatus(commentId, status);
        return ResponseEntity.ok(updatedComment);

    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentsService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
