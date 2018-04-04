package org.softuni.maintenancemanager.auth.controller;

import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/users")
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Object register(@Valid @ModelAttribute UserFullModel userDto,
                                      BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            Object result = this.userService.register(userDto);
            if (result instanceof FieldError) {
                bindingResult.addError((FieldError) result);
            }else {
                return result;
            }
        }


        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return errors;
        }

        return "Successful registration.";
    }

    @PostMapping("/profile")
    public UserViewModel profile(@RequestParam String id){
        return this.userService.getById(id);
    }

    @GetMapping("/all")
    public Object allUsers(){
        return this.userService.getAll();
    }

}
