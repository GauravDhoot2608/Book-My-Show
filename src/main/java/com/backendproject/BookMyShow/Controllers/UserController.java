package com.backendproject.BookMyShow.Controllers;

import com.backendproject.BookMyShow.Entities.User;
import com.backendproject.BookMyShow.Requests.AddUserRequest;
import com.backendproject.BookMyShow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody AddUserRequest addUserRequest){
        String result = userService.addUser(addUserRequest);
        return new ResponseEntity(result , HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    public ResponseEntity getUser(@RequestParam("userId") Integer userId){
        try {
            User user = userService.getUser(userId);
            return new ResponseEntity(user ,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

}
