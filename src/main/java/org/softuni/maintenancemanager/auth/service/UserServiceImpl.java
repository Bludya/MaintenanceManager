package org.softuni.maintenancemanager.auth.service;

import org.modelmapper.ModelMapper;
import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.model.entity.Role;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.softuni.maintenancemanager.auth.model.repositories.UserRepository;
import org.softuni.maintenancemanager.auth.service.interfaces.UserService;
import org.softuni.maintenancemanager.utils.CharacterEscapes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_EXISTS_MESSAGE =
            "User with this email already exists.";
    private static final String SERVER_SIDE_ERROR_MESSAGE =
            "Users services are unavailable at the moment. Please try again later.";
    private static final String USER_NOT_EXISTS_MESSAGE =
            "The user you are trying to access does not exist.";

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    private UserRepository usersRepository;

    @Autowired
    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ModelMapper modelMapper,
            UserRepository usersRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
    }

    //Returns null if everything is fine or object error.
    @Override
    public FieldError register(UserFullModel userDto) {
        userDto = CharacterEscapes.escapeStringFields(userDto);

        if(this.userExists(userDto.getEmail())){
            System.out.println("qweqwqweqweqwe");
            return new FieldError("userDto", "email", USER_EXISTS_MESSAGE);
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));

        try {
            usersRepository.save(user);
            return null;
        }catch (Exception e){
            return new FieldError("userDto", "exception", SERVER_SIDE_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean userExists(String email) {
        return this.usersRepository.existsByEmail(email);
    }

    @Override
    public boolean userExists(String email, String id) {
       return this.usersRepository.existsByEmailAndIdNot(email, id);
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

        if(!optionalUser.isPresent()){
            return null;
        }

        return this.mapUserViewModel(optionalUser.get());
    }

    //Returns null if everything is fine or object error.
    @Override
    public FieldError edit(UserFullModel userDto, String id) {
        Optional<User> optionalUser = this.usersRepository.findById(id);

        if(!optionalUser.isPresent()){
            return new FieldError("userDto","exception", USER_NOT_EXISTS_MESSAGE);
        }

        User user = optionalUser.get();

        modelMapper.map(userDto, user);
        usersRepository.save(user);

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        username = CharacterEscapes.escapeString(username);
        return this.usersRepository.findByUsernameEquals(username);
    }

    private UserViewModel mapUserViewModel(User user){
        UserViewModel userViewModel = modelMapper.map(user, UserViewModel.class);
        userViewModel.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet())
        );

        return userViewModel;
    }

    public FieldError deactivateUser(String id){
        //TODO: option to deactivate user;
        return new FieldError("userDto", "exception","not implemented yet");
    }

    public FieldError delete(String id){
        //TODO: option to delete user;
        return new FieldError("userDto", "exception","not implemented yet");
    }
}
