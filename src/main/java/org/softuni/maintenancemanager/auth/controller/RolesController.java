package org.softuni.maintenancemanager.auth.controller;

import org.softuni.maintenancemanager.auth.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/roles")
public class RolesController {
    private RoleService roleService;

    @Autowired
    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public Set<String> getAllRoles(){
        return this.roleService.getAllRoles();
    }
}
