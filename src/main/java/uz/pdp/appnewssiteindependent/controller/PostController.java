package uz.pdp.appnewssiteindependent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssiteindependent.aop.CheckPermission;
import uz.pdp.appnewssiteindependent.entity.Post;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.PostDto;
import uz.pdp.appnewssiteindependent.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @CheckPermission(value = "ADD_POST")
    @PostMapping
    public HttpEntity<?> addPost(@Valid @RequestBody PostDto postDto){
        ApiResponse apiResponse = postService.addPost(postDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(value = "VIEW_POSTS")
    @GetMapping
    public HttpEntity<?> getAllPosts(){
        List<Post> postList = postService.getAllPosts();
        return ResponseEntity.ok(postList);
    }

    @CheckPermission(value = "VIEW_POSTS")
    @GetMapping("/{id}")
    public HttpEntity<?> getPost(@PathVariable Long id){
        Post post = postService.getPost(id);
        return ResponseEntity.status(post != null ? 200 : 409).body(post);
    }

    @CheckPermission(value = "EDIT_POST")
    @PutMapping("/{id}")
    public HttpEntity<?> editPost(@PathVariable Long id, @Valid @RequestBody PostDto postDto){
        ApiResponse apiResponse = postService.editPost(id, postDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(value = "DELETE_POST")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePost(@PathVariable Long id){
        ApiResponse apiResponse = postService.deletePost(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}




