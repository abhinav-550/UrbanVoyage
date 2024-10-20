package com.urbanvoyage.ecom.controllers;


import com.urbanvoyage.ecom.controllers.auth.AuthCookieController;
import com.urbanvoyage.ecom.models.User;
import com.urbanvoyage.ecom.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletResponse res){
        String username = user.getUsername();
        String rawPassword = user.getPassword();

        if(userService.verifyPassword(username , rawPassword)){
            User foundUser = userService.findUserByUsername(username);
            if(foundUser != null){
                AuthCookieController.setAuthCookie(res, foundUser.getId() , foundUser.getRole().name());
                return new ResponseEntity<String>("Successfully Logged In", HttpStatus.OK);
            }
            }
                return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
        }

     @PostMapping("/register")
     public ResponseEntity<String> registerUser(@RequestBody User user, @RequestPart MultipartFile file , HttpServletResponse res){
        User createdUser = userService.createUser(user);
        if(createdUser == null){
                return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
            AuthCookieController.setAuthCookie(res, createdUser.getId() , createdUser.getRole().name());
            return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);
     }

     @PostMapping("/logout-protocol")
     public ResponseEntity<String> logoutUser(HttpServletResponse res){
        AuthCookieController.destroyAuthCookie(res);
        return new ResponseEntity<String>("User successfully logged out." , HttpStatus.OK);
     }
 }
