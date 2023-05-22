package com.api.apptodo.controllers;

import com.api.apptodo.dto.UserDto;
import com.api.apptodo.models.UserModel;
import com.api.apptodo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    final UserService userService;
    final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder){
        this.userService = userService;
        this.encoder = encoder;
    }

    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto)  {
        Optional<UserModel> userModelOptional = userService.findByUserEmail(userDto.getUserEmail());
        if(!userModelOptional.isPresent()){
            var userModel = new UserModel();
            BeanUtils.copyProperties(userDto, userModel);
            userModel.setUserPassword(encoder.encode(userModel.getUserPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist");
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") Integer id){
        Optional<UserModel> userModelOptional = userService.findById(id);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOneUser(@PathVariable(value = "id") Integer id){
        Optional<UserModel> userModelOptional = userService.findById(id);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted sucessfully");
    }

    @RequestMapping(path = "/user/{id}/validausuario", method = RequestMethod.GET)
    public ResponseEntity<Boolean> validationUser(@PathVariable(value = "id") Integer id,
                                                  @RequestParam String userEmail,
                                                  @RequestParam String password){
        Optional<UserModel> userModelOptional = userService.findByUserEmail(userEmail);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UserModel userModel = userModelOptional.get();
        boolean valid = encoder.matches(password, userModel.getUserPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

}
