package uz.pdp.appnewssiteindependent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.ResourceEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssiteindependent.aop.CheckPermission;
import uz.pdp.appnewssiteindependent.entity.Comment;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.CommentDto;
import uz.pdp.appnewssiteindependent.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @CheckPermission(value = "ADD_COMMENT")
    @PostMapping
    public HttpEntity<?> addComment(@Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse = commentService.addComment(commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(value = "VIEW_COMMENTS")
    @GetMapping
    public HttpEntity<?> getAllComments(){
        List<Comment> commentList = commentService.getAllComments();
        return ResponseEntity.ok(commentList);
    }

    @CheckPermission(value = "VIEW_COMMENTS")
    @GetMapping("/{id}")
    public HttpEntity<?> getComment(@PathVariable Long id){
        Comment comment = commentService.getComment(id);
        return ResponseEntity.status(comment != null ? 200 : 409).body(comment);
    }

    @CheckPermission(value = "EDIT_COMMENT")
    @PutMapping("/{id}")
    public HttpEntity<?> editComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto){
        ApiResponse apiResponse = commentService.editComment(id, commentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(value = "DELETE_COMMNET")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteComment(@PathVariable Long id){
        ApiResponse apiResponse = commentService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
