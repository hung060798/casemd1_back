package com.codegym.casemd4.controller;


import com.codegym.casemd4.model.Account;
import com.codegym.casemd4.model.Image;
import com.codegym.casemd4.model.Post;
import com.codegym.casemd4.service.account.IServiceAccount;
import com.codegym.casemd4.service.image.IServiceImage;
import com.codegym.casemd4.service.jwt.JwtService;
import com.codegym.casemd4.service.post.IServicePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class AccountController {
    @Autowired
    IServiceAccount serviceAccount;
    @Autowired
    IServiceImage serviceImage;
    @Autowired
    IServicePost servicePost;
    @Autowired
    JwtService jwtService;


    @PostMapping("/createPost")
    public  ResponseEntity<?> createPost(@RequestBody Image image){
        Post post = image.getPost();
        Post newPost = servicePost.add(post);
        image.setPost(newPost);
        serviceImage.save(image);
        return new ResponseEntity<>(servicePost.findAll(), HttpStatus.OK);
    }


}
