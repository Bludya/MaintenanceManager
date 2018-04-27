package org.softuni.maintenancemanager.auth.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.auth.model.repositories.UserRepository;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFoundException;
import org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions.UserAlreadyExists;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {
    private static UserFullModel basicUser;
    private static UserViewModel basicViewUser;
    private static User user;
    private static UserFullModel repeatingUser;
    private static UserFullModel mappingUser;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository userRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp(){
        user = new User();
        user.setUsername("Valio");
        user.setEmail("&lt;div&gt;abv@abv.bg");

        basicViewUser = new UserViewModel();
        basicViewUser.setUsername("Valio");
        basicViewUser.setEmail("&lt;div&gt;abv@abv.bg");

        basicUser = new UserFullModel(
                "Valio",
                "<div>abv@abv.bg",
                "123123",
                "123123"
        );

        repeatingUser = new UserFullModel(
                "Pesho",
                "abv@abv.bg",
                "123123",
                "123123"
        );

        when(this.userRepository.existsByEmailOrUsername(eq(repeatingUser.getEmail()), any()))
                .thenAnswer(a -> true);

        when(this.userRepository.existsByEmailOrUsername(any(), eq(repeatingUser.getUsername())))
                .thenAnswer(a -> true);

        when(this.userRepository.findById("1"))
                .thenAnswer(a ->Optional.ofNullable(user));

        when(this.userRepository.getByUsername("Valio"))
                .thenAnswer(a ->user);

        when(this.userRepository.existsByUsername("Valio"))
                .thenAnswer(a -> true);
    }

    @Test
    public void testRegisterSymbolEscapes(){
        UserViewModel userViewModel = userService.register(basicUser);

        String expectedWrongEmail = "<div>abv@abv.bg";

        Assert.assertNotEquals("Email is not escaped.", userViewModel.getEmail(), expectedWrongEmail);
    }

    @Test(expected = UserAlreadyExists.class)
    public void testRegisterSameNameUserExistsException(){
        repeatingUser.setEmail("notHisEmail");
        this.userService.register(repeatingUser);
    }

    @Test(expected = UserAlreadyExists.class)
    public void testRegisterSameEmailUserExistsException(){
        repeatingUser.setUsername("NotPesho");
        this.userService.register(repeatingUser);
    }

    @Test
    public void testRegisterMapping(){
        UserViewModel userViewModel = this.userService.register(basicUser);

        String expectedUsername = "Valio";
        String expecterEmail = "&lt;div&gt;abv@abv.bg";

        Assert.assertEquals("Username not mapped correctly", userViewModel.getUsername(), expectedUsername);
        Assert.assertEquals("Email not mapped correctly", userViewModel.getEmail(), expecterEmail);
    }

    @Test(expected = EntryNotFoundException.class)
    public void testGetByIdNotFoundException(){
        this.userService.getById("some_non_id");
    }

    @Test
    public void testGetByExistingId(){
        UserViewModel userViewModel = this.userService.getById("1");
        String expectedUsername = "Valio";

        Assert.assertEquals("Existing user not returned.", userViewModel.getUsername(), expectedUsername);
    }

    @Test(expected = EntryNotFoundException.class)
    public void testGetByUsernameNotFoundException(){
        this.userService.getByUsername("some_missing_username");
    }

    @Test
    public void testGetByUsernameExistingUser(){
        UserViewModel userViewModel = this.userService.getByUsername("Valio");

        String expectedUsername = "Valio";

        Assert.assertEquals("Existing user not found by username", userViewModel.getUsername(), expectedUsername);
    }

    @Test(expected = EntryNotFoundException.class)
    public void testEditEntryNotFoundException(){
        this.userService.edit("someEditor", basicUser, "some_Non_existing_id");
    }

    @Test(expected = UserAlreadyExists.class)
    public void testEditUserAlreadyExists(){
        this.userService.edit("someEditor", basicUser, "1");
    }

    @Test(expected = EntryNotFoundException.class)
    public void testActivateEntryNotFoundException(){
        this.userService.activateUser("someeditor", "nonexistingusername");
    }

    @Test
    public void testActivate(){
        UserViewModel userViewModel = this.userService.activateUser("someAuthor", "Valio");

        Assert.assertTrue("User not activated correctly", userViewModel.isEnabled());
    }

    @Test(expected = EntryNotFoundException.class)
    public void testDeactivateEntryNotFoundException(){
        this.userService.deactivateUser("someeditor", "nonexistingusername");
    }

    @Test
    public void testDeactivate(){
        UserViewModel userViewModel = this.userService.deactivateUser("someAuthor", "Valio");

        Assert.assertFalse("User not deactivated correctly", userViewModel.isEnabled());
    }

    @Test(expected = EntryNotFoundException.class)
    public void testDeleteEntryNotFoundException(){
        this.userService.delete("someeditor", "nonexistingusername");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameNotFoundException(){
        this.userService.loadUserByUsername("someNonExistingUsername");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testGetUserByUsernameNonExists(){
        this.userService.getUserByUsername("someNonExistingUsername");
    }

    @Test
    public void testGetUsersByUsernamesEmptySet(){
        Set<String> usernames = new HashSet<>();
        usernames.add("nonExisting1");
        usernames.add("nonExisting2");

        Set<User> users = this.userService.getUsersByUsernames(usernames);

        Assert.assertNotNull("Get users by username returns null when no users found.", users);
    }
}
