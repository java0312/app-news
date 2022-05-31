package uz.pdp.appnewssiteindependent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssiteindependent.entity.Comment;
import uz.pdp.appnewssiteindependent.entity.Post;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.CommentDto;
import uz.pdp.appnewssiteindependent.repository.CommentRepository;
import uz.pdp.appnewssiteindependent.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentrepository;

    @Autowired
    PostRepository postRepository;

    public ApiResponse addComment(CommentDto commentDto) {
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (optionalPost.isEmpty())
            return new ApiResponse("post not found!", false);

        commentrepository.save(new Comment(
                commentDto.getText(),
                optionalPost.get()
        ));

        return new ApiResponse("Comment added!", true);
    }

    public List<Comment> getAllComments() {
        return commentrepository.findAll();
    }

    public Comment getComment(Long id) {
        Optional<Comment> optionalComment = commentrepository.findById(id);
        return optionalComment.orElse(null);
    }

    public ApiResponse editComment(Long id, CommentDto commentDto) {
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (optionalPost.isEmpty())
            return new ApiResponse("post not found!", false);

        Optional<Comment> optionalComment = commentrepository.findById(id);
        if (optionalComment.isEmpty())
            return new ApiResponse("Comment not found!", false);

        Comment comment = optionalComment.get();
        comment.setText(commentDto.getText());
        comment.setPost(optionalPost.get());
        commentrepository.save(comment);

        return new ApiResponse("Comment edited!", true);
    }

    public ApiResponse deleteComment(Long id) {
        if (!commentrepository.existsById(id))
            return new ApiResponse("Comment not found!", false);

        commentrepository.deleteById(id);
        return new ApiResponse("Comment deleted!", true);
    }
}
