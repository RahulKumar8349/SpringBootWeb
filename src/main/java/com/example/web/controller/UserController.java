package com.example.web.controller;

import com.example.web.entity.Post;
import com.example.web.entity.ResourceNotFoundException;
import com.example.web.entity.User;
import com.example.web.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    //@RequestMapping(method = RequestMethod.GET.path)
    @GetMapping("/first")
    public List<User> get()
    {
        return userRepository.findAll();
    }

    @PostMapping("/second")
    //public User create(@RequestBody User user)
    public User create(@RequestBody User user)
    {
       return userRepository.save(user);
    }

    @GetMapping("/third/{id}")
    public User getById(@PathVariable("id") Long id)
    {
        Optional<User> user= userRepository.findById(id);
        return user.get();
    }

    @DeleteMapping("/fourth/{id}")
    public String deleteById(@PathVariable("id") Long id)
    {
            userRepository.deleteById(id);
            return "deleted";
    }

    @GetMapping("/{userId}/posts")
    public List<Post> getUserPosts(@PathVariable Long userId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPosts();
        }
        throw new ResourceNotFoundException("User not found with ID: " + userId);
    }

}