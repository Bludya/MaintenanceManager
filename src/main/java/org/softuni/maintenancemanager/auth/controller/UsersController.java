package org.softuni.maintenancemanager.auth.controller;

import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryCanNotBeCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Object register(@Valid @ModelAttribute UserFullModel userDto,
                           BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new EntryCanNotBeCreated(bindingResult.getSuppressedFields());
        }

        return this.userService.register(userDto);
    }

    @GetMapping("/profile")
    public UserViewModel profile(@RequestParam(name = "name", required = false) String name,
                                 Authentication auth) {
        if(name == null || !name.equals("")){
            return this.userService.getByUsername(auth.getName());
        }

        return this.userService.getByUsername(name);
    }

    @GetMapping("/all")
    public Object allUsers() {
        return this.userService.getAllBySearchWordOrderedByActive("");
    }

    @GetMapping("/managers")
    public Object allManagers(){
        return this.userService.getAllByRole("ROLE_MANAGER");
    }

    @GetMapping("/find/{searchWord}")
    public Object searchUsers(@PathVariable(required = false) String searchWord) {
        if (searchWord == null) {
            searchWord = "";
        }
        return this.userService.getAllBySearchWordOrderedByActive(searchWord);
    }

    @GetMapping("/activate/{username}")
    public Object activateUser(@PathVariable String username,
                               Authentication authentication) {
        return this.userService.activateUser(authentication.getName(), username);
    }

    @GetMapping("/deactivate/{username}")
    public Object deactivateUser(@PathVariable String username,
                                 Authentication authentication) {
        return this.userService.deactivateUser(authentication.getName(), username);
    }

    @GetMapping("/delete/{username}")
    public Object deleteUser(@PathVariable String username,
                           Authentication authentication) {
        this.userService.delete(authentication.getName(), username);
        return  "{\"success\": \"yes\"}";
    }

    @PostMapping("/change-role/{username}")
    public UserViewModel changeRole(@PathVariable String username,
                                    @RequestParam String role,
                                    Authentication auth){
        return this.userService.changeUserRole(auth.getName(), username, role);
    }
}
