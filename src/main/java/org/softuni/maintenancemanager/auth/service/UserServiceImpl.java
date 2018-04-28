package org.softuni.maintenancemanager.auth.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.model.entity.Role;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.auth.model.repositories.UserRepository;
import org.softuni.maintenancemanager.auth.service.interfaces.RoleService;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFoundException;
import org.softuni.maintenancemanager.errorHandling.exceptions.entryExistsExceptions.UserAlreadyExists;
import org.softuni.maintenancemanager.logger.service.interfaces.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    private RoleService roleService;

    private UserRepository usersRepository;

    private Logger logger;

    @Autowired
    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ModelMapper modelMapper,
            RoleService roleService,
            UserRepository usersRepository,
            Logger logger) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.usersRepository = usersRepository;
        this.logger = logger;
    }

    @Override
    public boolean userExists(String email, String username) {
        return this.usersRepository.existsByEmailOrUsername(email, username);
    }

    @Override
    public boolean userExists(String email, String username, String id) {
        return this.usersRepository.existsByEmailOrUsernameAndIdNot(email, username, id);
    }

    @Override
    public UserViewModel register(UserFullModel userDto) {

        try {
            userDto = CharacterEscapes.escapeStringFields(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (this.userExists(userDto.getEmail(), userDto.getUsername())) {
            throw new UserAlreadyExists();
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));

        usersRepository.save(user);
        logger.addLog(user.getUsername(), "User created");
        return this.modelMapper.map(user, UserViewModel.class);
    }

    @Override
    public List<UserViewModel> getAll() {
        return StreamSupport
                .stream(this.usersRepository.findAll().spliterator(), false)
                .map(this::mapUserViewModel)
                .collect(Collectors.toList());

    }

    @Override
    public UserViewModel changeUserRole(String editor, String userName, String roleName){
        userName = CharacterEscapes.escapeString(userName);
        roleName = CharacterEscapes.escapeString(roleName);

        User user = this.usersRepository.getByUsername(userName);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        Role role = this.roleService.getRoleByRoleName(roleName);

        if(role == null){
            throw new EntryNotFoundException();
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        this.usersRepository.save(user);

        return this.mapUserViewModel(user);
    }

    @Override
    public Set<UserViewModel> getAllByRole(String role) {

        role = CharacterEscapes.escapeString(role);

        return this.usersRepository.findAllByRole(role).stream()
                .map(this::mapUserViewModel)
                .collect(Collectors.toSet());
    }

    @Override
    public UserViewModel getById(String id) {

        id = CharacterEscapes.escapeString(id);

        Optional<User> optionalUser = this.usersRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new EntryNotFoundException();
        }

        return this.mapUserViewModel(optionalUser.get());
    }

    @Override
    public UserViewModel getByUsername(String username) {
        username = CharacterEscapes.escapeString(username);

        if (!this.usersRepository.existsByUsername(username)) {
            throw new EntryNotFoundException();
        }

        return this.mapUserViewModel(this.usersRepository.getByUsername(username));
    }

    @Override
    public UserViewModel edit(String editor, UserFullModel userDto, String id) {

        id = CharacterEscapes.escapeString(id);

        try {
            userDto = CharacterEscapes.escapeStringFields(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Optional<User> userOptional = this.usersRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new EntryNotFoundException();
        }

        if (!this.usersRepository.existsByEmailOrUsernameAndIdNot(userDto.getEmail(), userDto.getUsername(), id)) {
            throw new UserAlreadyExists();
        }

        User user = userOptional.get();

        modelMapper.map(userDto, user);
        usersRepository.save(user);

        logger.addLog(editor, "Editted user " + id);

        return this.mapUserViewModel(user);
    }

    public UserViewModel deactivateUser(String editor, String username) {

        username = CharacterEscapes.escapeString(username);

        User user = this.usersRepository.getByUsername(username);

        if (user == null) {
            throw new EntryNotFoundException();
        }

        user.setEnabled(false);
        usersRepository.save(user);
        logger.addLog(editor, "Deactivated user with id: " + user.getId());

        return this.mapUserViewModel(user);
    }

    public UserViewModel activateUser(String editor, String username) {

        username = CharacterEscapes.escapeString(username);

        User user = this.usersRepository.getByUsername(username);

        if (user == null) {
            throw new EntryNotFoundException();
        }

        user.setEnabled(true);
        usersRepository.save(user);
        logger.addLog(editor, "Activated user with id: " + user.getId());
        return this.mapUserViewModel(user);
    }

    public void delete(String editor, String username) {
        username = CharacterEscapes.escapeString(username);

        if (!usersRepository.existsByUsername(username)) {
            throw new EntryNotFoundException();
        }

        usersRepository.deleteByUsername(username);
        logger.addLog(editor, "Deleted user with username: " + username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        username = CharacterEscapes.escapeString(username);

        User user = this.usersRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }

        return user;
    }

    @Override
    public List<UserViewModel> getAllBySearchWordOrderedByActive(String searchWord) {
        searchWord = CharacterEscapes.escapeString(searchWord);

        return this.usersRepository.findAllByUsernameContainsOrEmailContainsOrderByIsEnabledAsc(searchWord, searchWord).stream()
                .map(this::mapUserViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByUsername(String username) {
        username = CharacterEscapes.escapeString(username);

        User user = this.usersRepository.getByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User does not exist.");
        }

        return user;
    }

    @Override
    public Set<User> getUsersByUsernames(Set<String> usernames) {
        usernames = usernames.stream().map(CharacterEscapes::escapeString).collect(Collectors.toSet());

        if(usernames == null){
            usernames = new HashSet<>();
        }

        return this.usersRepository.getAllByUsernameIn(usernames);
    }

    private UserViewModel mapUserViewModel(User user) {
        UserViewModel userViewModel = modelMapper.map(user, UserViewModel.class);
        try {
            userViewModel.setRoles(
                    user.getRoles()
                            .stream()
                            .map(Role::getRoleName)
                            .collect(Collectors.toSet())
            );
        }catch (NullPointerException npe){
            userViewModel.setRoles(new HashSet<>());
        }
        return userViewModel;
    }

}
