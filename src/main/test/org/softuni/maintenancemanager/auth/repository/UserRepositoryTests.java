package org.softuni.maintenancemanager.auth.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.maintenancemanager.auth.model.entity.Authority;
import org.softuni.maintenancemanager.auth.model.entity.Role;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.auth.model.repositories.AuthorityRepository;
import org.softuni.maintenancemanager.auth.model.repositories.RolesRepository;
import org.softuni.maintenancemanager.auth.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    private static boolean setup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @Before
    public void setUp(){

        if(this.setup){
            return;
        }

        Authority adminAuthority = new Authority();
        adminAuthority.setAuthority("ADMIN");

        Authority managerAuthority = new Authority();
        managerAuthority.setAuthority("MANAGER");

        this.authorityRepository.save(adminAuthority);
        this.authorityRepository.save(managerAuthority);

        Set<Authority> adminAuthorities = new HashSet<>();
        adminAuthorities.add(adminAuthority);
        adminAuthorities.add(managerAuthority);

        Set<Authority> managerAuthorities = new HashSet<>();
        managerAuthorities.add(managerAuthority);

        Role roleManager = new Role();
        roleManager.setAuthorities(managerAuthorities);

        Role roleAdmin = new Role();
        roleAdmin.setAuthorities(adminAuthorities);

        this.rolesRepository.save(roleAdmin);
        this.rolesRepository.save(roleManager);

        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleAdmin);

        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleManager);

        User user1 = new User();
        user1.setUsername("valio");
        user1.setRoles(roles1);

        User user2 = new User();
        user2.setUsername("pesho");
        user2.setRoles(roles2);

        this.userRepository.save(user1);
        this.userRepository.save(user2);

        this.setup = true;
    }

    @Test
    public void testFindAllByRoleAdmin(){
        Set<User> users = this.userRepository.findAllByRole("ADMIN");

        int expectedLength = 1;
        String expectedUsername = "valio";

        Assert.assertEquals("The number of retrieved users doesn't match the existing with role ADMIN.", users.size(), expectedLength);

        User user = new User();

        for (User user1 : users) {
            user = user1;
            break;
        }

        Assert.assertEquals("Retrieved user does not match with expected.", user.getUsername(), expectedUsername);
    }


    @Test
    public void testFindAllByRoleManager(){
        Set<User> users = this.userRepository.findAllByRole("MANAGER");

        int expectedLength = 2;

        Assert.assertEquals("The number of retrieved users doesn't match the existing with role MANAGER.", users.size(), expectedLength);
    }

    @Test
    public void testFindAllByRoleNotExists(){
        Set<User> users = this.userRepository.findAllByRole("NQMA_TAKAVA_ROLQ");

        int expectedLength = 0;

        Assert.assertEquals("There were users retrieved even though the role is non existant.", users.size(), expectedLength);
    }
}
