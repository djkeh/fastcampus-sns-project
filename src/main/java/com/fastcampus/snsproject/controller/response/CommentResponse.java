package com.fastcampus.snsproject.controller.response;

import com.fastcampus.snsproject.model.Comment;
import com.fastcampus.snsproject.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Data
public class CommentResponse {
    private Integer id;
    private String comment;

    private String userName;

    private Integer postId;

    private Timestamp registerAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static CommentResponse fromComment(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUserName(),
                comment.getPostId(),
                comment.getRegisterAt(),
                comment.getUpdatedAt(),
                comment.getDeletedAt()
        );
    }
}
