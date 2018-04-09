package org.softuni.maintenancemanager.auth.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.model.entity.Role;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.auth.model.repositories.UserRepository;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFound;
import org.softuni.maintenancemanager.errorHandling.exceptions.UserAlreadyExists;
import org.softuni.maintenancemanager.logger.service.interfaces.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    private UserRepository usersRepository;

    private LogService logService;

    @Autowired
    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ModelMapper modelMapper,
            UserRepository usersRepository,
            LogService logService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
        this.logService = logService;
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
    public Object register(UserFullModel userDto) {
        if (this.userExists(userDto.getEmail(), userDto.getUsername())) {
            throw new UserAlreadyExists();
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));

        usersRepository.save(user);
        logService.addLog(user.getUsername(), "User created");
        return userDto;
    }

    @Override
    public List<UserViewModel> getAll() {
        return StreamSupport
                .stream(this.usersRepository.findAll().spliterator(), false)
                .map(this::mapUserViewModel)
                .collect(Collectors.toList());

    }

    @Override
    public UserViewModel getById(String id) {
        Optional<User> optionalUser = this.usersRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new EntryNotFound();
        }

        return this.mapUserViewModel(optionalUser.get());
    }

    @Override
    public UserViewModel getByUsername(String username) {
        if (!this.usersRepository.existsByUsername(username)) {
            throw new EntryNotFound();
        }

        return this.mapUserViewModel(this.usersRepository.getByUsername(username));
    }

    @Override
    public UserViewModel edit(String editor, UserFullModel userDto, String id) {
        Optional<User> userOptional = this.usersRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new EntryNotFound();
        }

        if (!this.usersRepository.existsByEmailOrUsernameAndIdNot(userDto.getEmail(), userDto.getUsername(), id)) {
            throw new UserAlreadyExists();
        }

        User user = userOptional.get();

        modelMapper.map(userDto, user);
        usersRepository.save(user);

        logService.addLog(editor, "Editted user " + id);

        return this.mapUserViewModel(user);
    }

    public UserViewModel deactivateUser(String editor, String id) {
        Optional<User> optionalUser = this.usersRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new EntryNotFound();
        }

        User user = optionalUser.get();
        user.setEnabled(false);
        usersRepository.save(user);
        logService.addLog(editor, "Deactivated user with id: " + id);

        return this.mapUserViewModel(user);
    }

    public UserViewModel activateUser(String editor, String id) {
        Optional<User> optionalUser = this.usersRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new EntryNotFound();
        }

        User user = optionalUser.get();
        user.setEnabled(true);
        usersRepository.save(user);
        logService.addLog(editor, "Activated user with id: " + id);
        return this.mapUserViewModel(user);
    }

    public void delete(String editor, String id) {
        if (!usersRepository.existsById(id)) {
            throw new EntryNotFound();
        }

        usersRepository.deleteById(id);
        logService.addLog(editor, "Deleted user with id: " + id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        email = CharacterEscapes.escapeString(email);
        User user = this.usersRepository.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }

        return user;
    }

    private UserViewModel mapUserViewModel(User user) {
        UserViewModel userViewModel = modelMapper.map(user, UserViewModel.class);
        userViewModel.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet())
        );

        return userViewModel;
    }

    @Override
    public List<UserViewModel> getAllBySearchWordOrderedByActive(String searchWord) {
        return this.usersRepository.findAllByUsernameContainsOrEmailContains(searchWord, searchWord).stream()
                .map(this::mapUserViewModel)
                .collect(Collectors.toList());
    }

}
