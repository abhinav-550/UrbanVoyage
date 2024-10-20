package com.urbanvoyage.ecom.controllers;

import com.urbanvoyage.ecom.controllers.auth.AuthCookieController;
import com.urbanvoyage.ecom.models.Item;
import com.urbanvoyage.ecom.models.User;
import com.urbanvoyage.ecom.services.ItemService;
import com.urbanvoyage.ecom.services.UserService;
import com.urbanvoyage.ecom.utils.ContentGenerator;
import com.urbanvoyage.ecom.utils.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    Utility utility;

    @PostMapping("/items/create")
    public ResponseEntity<String> createItem(
            @RequestParam String name,
            @RequestParam long price,
            @RequestParam int quantity,
            @RequestParam String description,
            @RequestParam ArrayList<String> tags,
            @RequestPart ArrayList<MultipartFile> images,
            HttpServletRequest req
            ) throws IOException {
        String role = AuthCookieController.getAuthDetail(req , "role");
        if(!role.equals("Seller")){
            return new ResponseEntity<String>("You cannot create a item as a User.", HttpStatus.FORBIDDEN);
        }
        String sellerId = AuthCookieController.getAuthDetail(req , "userId");

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setDescription(description);
        item.setTags(tags);
        item.setSellerId(sellerId);

        Item createdItem = itemService.createItem(item);

        ArrayList<String> paths = new ArrayList<String>();
        if(images != null){
            for(MultipartFile img : images){
                String imgPath = utility.uploadFile(img, sellerId);
                paths.add(imgPath);
            }
        }

        createdItem.setImages(paths);
        itemService.saveItem(createdItem);

        return new ResponseEntity<String>("Successfully created an Item, ID: "+createdItem.getId() , HttpStatus.CREATED);
    }



    @GetMapping("/items/search")
    public ResponseEntity<ArrayList<Item>> searchFilter(@RequestParam String q){
        ArrayList<Item> items = itemService.searchItems(q);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/all")
    public ResponseEntity<ArrayList<Item>> viewAllItems(){
        ArrayList<Item> allItems = itemService.getAllItems();
         return new ResponseEntity<ArrayList<Item>>(allItems , HttpStatus.OK);
    }

    @PostMapping("/items/generate-content")
    public ResponseEntity<String> generateItemDetails(@RequestPart MultipartFile image, HttpServletRequest req) throws Exception {
        String sellerId = AuthCookieController.getAuthDetail(req, "userId");
        String imgPath = utility.uploadFile(image , sellerId);

        if(imgPath == null){
            return  new ResponseEntity<String>("Internal Server Error" ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String content = ContentGenerator.fetchGeneratedContent(sellerId , image.getOriginalFilename());
        return new ResponseEntity<String>(content , HttpStatus.OK );
    }

    @PostMapping("items/{itemId}/add-to-cart")
    public ResponseEntity<String> addToCart(@PathVariable String itemId, HttpServletRequest req){
        String userId = AuthCookieController.getAuthDetail(req, "userId");
        userService.newCartItem(userId, itemId);
        return new ResponseEntity<String>("Item added to cart.", HttpStatus.OK);
    }

    @PostMapping("items/{itemId}/delete-from-cart")
    public ResponseEntity<String> deleteFromCart(@PathVariable String itemId, HttpServletRequest req){
        String userId = AuthCookieController.getAuthDetail(req, "userId");
        userService.deleteCartItem(userId, itemId);
        return new ResponseEntity<String>("Item removed from cart.", HttpStatus.OK);
    }



    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getAItem(@PathVariable String id){
        Item foundItem = itemService.getASingleItem(id).orElse(null);
        if(foundItem == null){
            return new ResponseEntity<Item>(foundItem, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Item>(foundItem, HttpStatus.OK);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<String> updateItemDetail(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam long price,
            @RequestParam int quantity,
            @RequestParam ArrayList<String> tags,
            @RequestParam String description
    ){
        Item foundItem = itemService.getASingleItem(id).orElse(null);
        if(foundItem == null){
            return new ResponseEntity<String>("Item not found.", HttpStatus.NOT_FOUND);
        }
        foundItem.setName(name);
        foundItem.setPrice(price);
        foundItem.setDescription(description);
        foundItem.setQuantity(quantity);
        foundItem.setTags(tags);
        itemService.saveItem(foundItem);
        return new ResponseEntity<String>("Item details updated. ID: "+foundItem.getId() , HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}/delete")
    public ResponseEntity<String> deleteItem(@PathVariable String id){
        Item foundItem = itemService.getASingleItem(id).orElse(null);
        if(foundItem == null){
            return new ResponseEntity<String>("Item not found.", HttpStatus.NOT_FOUND);
        }
        itemService.deleteItem(foundItem.getSellerId() , id);
        return new ResponseEntity<String>("Item deleted successfully.", HttpStatus.OK);
    }



}
