package com.fastcampus.snsproject.controller;

import com.fastcampus.snsproject.controller.reqeust.PostCommentRequest;
import com.fastcampus.snsproject.controller.reqeust.PostCreateRequest;
import com.fastcampus.snsproject.controller.reqeust.PostModifyRequest;
import com.fastcampus.snsproject.controller.response.CommentResponse;
import com.fastcampus.snsproject.controller.response.PostResponse;
import com.fastcampus.snsproject.controller.response.Response;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Integer postId,@RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> modify(@PathVariable Integer postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return Response.success();
    }

    @GetMapping
    public Response<Page<PostResponse>> getList(Pageable pageable) {
        return Response.success(postService.getList(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> getMyList(Pageable pageable, Authentication authentication) {
        return Response.success(postService.getMyList(authentication.getName(), pageable).map(PostResponse::fromPost));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable Integer postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Integer> likeCount(@PathVariable Integer postId, Authentication authentication) {
//        int likeCount = postService.getLikeCount(postId);
        return Response.success(postService.getLikeCount(postId));
    }

    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable Integer postId, @RequestBody PostCommentRequest request, Authentication authentication) {
        postService.comment(postId, request.getComment(), authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> comment(@PathVariable Integer postId, Pageable pageable, Authentication authentication) {
        return Response.success(postService.getComment(postId, pageable).map(CommentResponse::fromComment));
    }
}
