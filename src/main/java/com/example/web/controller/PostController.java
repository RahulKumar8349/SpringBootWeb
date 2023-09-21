package com.example.web.controller;

import com.example.web.entity.Post;
import com.example.web.entity.User;
import com.example.web.repository.PostRepository;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Post> get()
    {
        return postRepository.findAll();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createPost(@PathVariable("userId") Long userId, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);
            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Other post-related endpoints (update, delete, etc.)
}
