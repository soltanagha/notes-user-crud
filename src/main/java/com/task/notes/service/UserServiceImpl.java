package com.task.notes.service;

import com.task.notes.dto.UserDTO;
import com.task.notes.entity.User;
import com.task.notes.entity.UserDetailsImpl;
import com.task.notes.entity.exception.EmptyInputException;
import com.task.notes.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userInfo = userRepository.findByUsername(username);
        return userInfo.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String id) {
        return userRepository.findById(id).stream().map(this::convertToUserDto).findFirst().
                orElseThrow(() -> new UsernameNotFoundException("User not found id: " + id));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        validateUserDTO(userDTO);

        User user = convertToUserEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(userDTO.getRoleList()));
        userRepository.save(user);
        userDTO = convertToUserDto(user);
        return userDTO;
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        validateUserDTO(userDTO);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found id: " + id));
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setRoles(new HashSet<>(userDTO.getRoleList()));
        userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return id;
    }

    private void validateUserDTO(UserDTO userDTO) {
        if (userDTO.getUsername().isEmpty())
            throw new EmptyInputException("601","Username is empty!");
        else if (userDTO.getPassword().isEmpty())
            throw new EmptyInputException("601","Password is empty!");
        else if (userDTO.getRoleList().size() == 0)
            throw new EmptyInputException("601","User has to have at least one role");
    }
    private UserDTO convertToUserDto(User user) {
        UserDTO userDT0 =  modelMapper.map(user, UserDTO.class);
        userDT0.setRoleList(user.getRoles().stream().toList());
        return userDT0;
    }

    private User convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
