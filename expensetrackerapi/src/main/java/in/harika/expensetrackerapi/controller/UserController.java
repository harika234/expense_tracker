package in.harika.expensetrackerapi.controller;

import in.harika.expensetrackerapi.entity.User;
import in.harika.expensetrackerapi.entity.UserModel;
import in.harika.expensetrackerapi.exceptions.ResourceNotFoundException;
import in.harika.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.module.ResolutionException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<User> get() {
        return new ResponseEntity<User>(userService.read(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public  ResponseEntity<User> update(@RequestBody UserModel user) {
//        User mUser = userService.update(user);
        return  new ResponseEntity<User>(userService.update(user),HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public  ResponseEntity<HttpStatus> delete() throws ResourceNotFoundException {
        userService.delete();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
