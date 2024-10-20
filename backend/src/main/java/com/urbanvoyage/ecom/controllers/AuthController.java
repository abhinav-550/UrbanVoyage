package com.urbanvoyage.ecom.controllers;


import com.urbanvoyage.ecom.controllers.auth.AuthCookieController;
import com.urbanvoyage.ecom.models.User;
import com.urbanvoyage.ecom.services.UserService;
import com.urbanvoyage.ecom.utils.Utility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    Utility utility;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse res) {

        if (userService.verifyPassword(username, password)) {
            User foundUser = userService.findUserByUsername(username);
            if (foundUser != null) {
                AuthCookieController.setAuthCookie(res, foundUser.getId(), foundUser.getRole());
                return new ResponseEntity<>("Successfully Logged In", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/register/user")
    public ResponseEntity<String> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String shippingAddress,
            @RequestPart(value = "PFP", required = false) MultipartFile pfpFile,
            HttpServletResponse res) throws IOException {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setShippingAddress(shippingAddress);
        user.setRole("User");

        User createdUser = userService.createUser(user);
        utility.createUserFolder(createdUser.getId());

        if (createdUser == null) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (pfpFile != null && !pfpFile.isEmpty()) {
            String pfpPath = utility.uploadFile(pfpFile, createdUser.getId());
            createdUser.setPFP(pfpPath);
            userService.saveUser(createdUser);
        }
        AuthCookieController.setAuthCookie(res, createdUser.getId(), createdUser.getRole());

        return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
    }

    @PostMapping(value = "/register/seller")
    public ResponseEntity<String> registerShop(
            @RequestParam String username,
            @RequestParam String shopName,
            @RequestParam String shopAddress,
            @RequestParam String shopWebsite,
            @RequestParam String shopDescription,
            @RequestParam String email,
            @RequestParam String password,
            @RequestPart(value="shopImages" , required = false)ArrayList<MultipartFile> shopImages,
            HttpServletResponse res
            ) throws IOException {
            User seller = new User();

            seller.setShopName(shopName);
            seller.setShopDescription(shopDescription);
            seller.setShopWebsite(shopWebsite);
            seller.setShopAddress(shopAddress);
            seller.setRole("Seller");
            seller.setPassword(password);
            seller.setEmail(email);
            seller.setUsername(username);

            User createdSeller = userService.createUser(seller);
            utility.createUserFolder(seller.getId());

            if (createdSeller == null) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            ArrayList<String> paths = new ArrayList<String>();
            if(shopImages != null){
                for(MultipartFile img : shopImages){
                    String shopImagePath = utility.uploadFile(img, createdSeller.getId());
                    paths.add(shopImagePath);
                }
            }
            createdSeller.setShopImages(paths);
            userService.saveUser(createdSeller);

            AuthCookieController.setAuthCookie(res, createdSeller.getId(), createdSeller.getRole());

            return new ResponseEntity<String>("Successfully registered as seller." , HttpStatus.OK);
    }


    @PostMapping("/logout-protocol")
    public ResponseEntity<String> logoutUser(HttpServletResponse res) {
        AuthCookieController.destroyAuthCookie(res);
        return new ResponseEntity<>("User successfully logged out.", HttpStatus.OK);
    }
}
