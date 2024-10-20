package com.urbanvoyage.ecom.controllers;

import com.urbanvoyage.ecom.DTO.PasswordChangeRequest;
import com.urbanvoyage.ecom.controllers.auth.AuthCookieController;
import com.urbanvoyage.ecom.models.User;
import com.urbanvoyage.ecom.repositories.UserRepository;
import com.urbanvoyage.ecom.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        User foundUser = userService.findUserById(id).orElse(null);
        if(foundUser == null){
            return new ResponseEntity<User>(foundUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(foundUser, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable String id,
            @RequestParam String email,
            @RequestParam String shippingAddress
            ) {

        // Find the user by ID
        User foundUser = userService.findUserById(id).orElse(null);
        if (foundUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        foundUser.setEmail(email);
        foundUser.setShippingAddress(shippingAddress);

        // Save updated user
        userService.updateUser(id, foundUser);

        return new ResponseEntity<>("User successfully updated.", HttpStatus.OK);
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changeUserPassword(@RequestBody PasswordChangeRequest changePassword, HttpServletRequest request){
        String userId = AuthCookieController.getAuthDetail(request, "userId");
        String oldPassword = changePassword.getOldPassword();
        User foundUser = userService.findUserById(userId).orElse(null);
        if(foundUser == null){
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
        if(userService.verifyPassword(foundUser.getUsername() , oldPassword)){
        String newPassword = changePassword.getNewPassword();
        if (newPassword == null || newPassword.isEmpty()) {
            return new ResponseEntity<>("New password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(userId , newPassword);
        return new ResponseEntity<String>("Password successfully updated.", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Incorrect Credentials", HttpStatus.BAD_REQUEST);
    }


}



//no -> all users, delete
// yes -> access one user and update ,