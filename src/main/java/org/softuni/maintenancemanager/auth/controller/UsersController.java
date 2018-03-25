package org.softuni.maintenancemanager.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users/")
public class UsersController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav){
        return mav;
    }
}
