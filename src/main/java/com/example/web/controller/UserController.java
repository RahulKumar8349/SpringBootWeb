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
    public ResponseEntity<Object> create(@RequestBody User user)
    {
        if(user.getName()==null || user.getEmail()==null || user.getLocalDate()==null)
        {
            return ResponseEntity.ok().body("User in not saved in database because , name ,email,localdate must be provided these cannot be null and id is auto generated ");
        }
        else
        {
            return ResponseEntity.ok().body(userRepository.save(user));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id)
    {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent())
        {
            return ResponseEntity.ok().body(userOptional.get());
        }

        return ResponseEntity.ok().body("There is no User of id exist , which you given");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id)
    {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent())
        {
            userRepository.deleteById(id);
            return ResponseEntity.ok().body("User is deleted of id : "+id);
        }
        return ResponseEntity.ok().body("There is no User of id exist , which you given");
    }




    @PutMapping("/{id}")
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

        return ResponseEntity.ok().body("There is no id exist , which you given : "+id);

    }

}