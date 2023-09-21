package com.example.web.controller;

import com.example.web.entity.Post;
import com.example.web.entity.ResourceNotFoundException;
import com.example.web.entity.User;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private UserService userService;

//    @Autowired
//    private PostRepository postRepository;

    //@RequestMapping(method = RequestMethod.GET.path)
    @GetMapping
    public List<User> get()
    {
        return userRepository.findAll();
    }

    @PostMapping
    //public User create(@RequestBody User user)
    public User create(@RequestBody User user)
    {
       return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id)
    {
        Optional<User> user= userRepository.findById(id);
        return user.get();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id)
    {
            userRepository.deleteById(id);
            return "deleted";
    }

    @GetMapping("/posts/{userId}")
    public List<Post> getUserPosts(@PathVariable Long userId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPosts();
        }
        throw new ResourceNotFoundException("User not found with ID: " + userId);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody User userInfo, @PathVariable("id") long id)
    {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent())
        {
            User user = userOptional.get();
            try {
                String ans=this.userService.update(user,userInfo,id);
                return ResponseEntity.ok().body(ans);
            } catch (Exception e) {
                System.out.println(" no id found for this id please check your id");
            }
        }

        return ResponseEntity.ok().body("There is no id exist , which you given");

    }

}
