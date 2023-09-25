package com.example.web.controller;

import com.example.web.entity.Post;
import com.example.web.entity.ResourceNotFoundException;
import com.example.web.entity.User;
import com.example.web.repository.PostRepository;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> get()
    {
        return postRepository.findAll();
    }


    @GetMapping("/{userId}/posts")
    public ResponseEntity<Object> getUserPosts(@PathVariable Long userId) throws ResourceNotFoundException
    {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent())
        {
            User user = userOptional.get();
            return ResponseEntity.ok().body(user.getPosts());
        }
        return ResponseEntity.ok().body("There is no User id : "+userId+" exist , which you given to find its Posts");
    }

    @GetMapping("/{userId}/posts/{postId}")
    public ResponseEntity<Object> createPost(@PathVariable("userId") Long userId,@PathVariable("postId") Long postId)
    {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent())
        {
            Optional<Post> post=postRepository.findById(postId);
            if(post.isPresent())
                return ResponseEntity.ok(post.get());
            else
                return ResponseEntity.ok().body("There is no Posts of id : "+postId+" exist , which you given to find its Posts in userId : "+userId);
        }
        return ResponseEntity.ok().body("There is no User id : "+userId+" exist , which you given to find its Posts");
    }

    @PostMapping("/{userId}/posts")
    public ResponseEntity<String> createPost(@PathVariable("userId") Long userId, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if(post.getContent()==null )
            {
                return ResponseEntity.ok().body("Post in not saved in database because content cannot be null ");
            }
            post.setUser(user);
            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully.");
        } else {
            return ResponseEntity.ok("user not exist of id "+userId);
        }
    }

    // Other post-related endpoints (update, delete, etc.)
}
