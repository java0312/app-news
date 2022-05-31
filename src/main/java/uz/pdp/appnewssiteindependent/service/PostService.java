package uz.pdp.appnewssiteindependent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssiteindependent.entity.Post;
import uz.pdp.appnewssiteindependent.payload.ApiResponse;
import uz.pdp.appnewssiteindependent.payload.PostDto;
import uz.pdp.appnewssiteindependent.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public ApiResponse addPost(PostDto postDto) {
        boolean exists = postRepository.existsByTitleOrUrl(postDto.getTitle(), postDto.getUrl());
        if (exists)
            return new ApiResponse("This post exists!", false);

        postRepository.save(new Post(
                postDto.getTitle(),
                postDto.getText(),
                postDto.getUrl()
        ));

        return new ApiResponse("Post added!", true);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public ApiResponse editPost(Long id, PostDto postDto) {
        boolean exists = postRepository.existsByTitleOrUrlAndIdNot(postDto.getTitle(), postDto.getUrl(), id);
        if (exists)
            return new ApiResponse("Post exists!", false);

        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty())
            return new ApiResponse("Post not found!", false);

        Post post = optionalPost.get();
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);

        return new ApiResponse("Post edited!", true);
    }

    public ApiResponse deletePost(Long id) {
        if (!postRepository.existsById(id))
            return new ApiResponse("Post not found!", true);

        postRepository.deleteById(id);
        return new ApiResponse("Post deleted!", true);
    }
}
