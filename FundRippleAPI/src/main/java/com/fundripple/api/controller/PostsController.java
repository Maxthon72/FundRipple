package com.fundripple.api.controller;


import com.fundripple.api.model.dto.read.PostUnderProjectReadModel;
import com.fundripple.api.model.dto.read.PostUnderUserReadModel;
import com.fundripple.api.model.dto.write.PostUnderProjectWriteModel;
import com.fundripple.api.model.dto.write.PostUnderUserWriteModel;
import com.fundripple.api.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private final PostService postService;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/project/{projectName}")
    ResponseEntity<PostUnderProjectReadModel> addPostUnderProject(
            @PathVariable String projectName,
            @RequestBody PostUnderProjectWriteModel postUnderProjectWriteModel,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(postService.addPostUnderProject(projectName,postUnderProjectWriteModel,header));
    }

    @PostMapping("/user")
    ResponseEntity<PostUnderUserReadModel> addPostUnderUser(
            @RequestBody PostUnderUserWriteModel postUnderUserWriteModel,
            @RequestHeader("Authorization") String header
    ){
        return ResponseEntity.ok(postService.addPostUnderUser(postUnderUserWriteModel,header));
    }

    @PutMapping("/project/like/{postId}")
    ResponseEntity<String> likePostUnderProject(@PathVariable Long postId){
        return ResponseEntity.ok(postService.likePostUnderProject(postId));
    }

    @PutMapping("/user/like/{postId}")
    ResponseEntity<String> likePostUnderUser(@PathVariable Long postId){
        return ResponseEntity.ok(postService.likePostUnderUser(postId));
    }

    @PutMapping("/project/unlike/{postId}")
    ResponseEntity<String> unlikePostUnderProject(@PathVariable Long postId){
        return ResponseEntity.ok(postService.removeLikePostUnderProject(postId));
    }

    @PutMapping("/user/unlike/{postId}")
    ResponseEntity<String> unlikePostUnderUser(@PathVariable Long postId){
        return ResponseEntity.ok(postService.removeLikePostUnderUser(postId));
    }
}
