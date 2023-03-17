package com.task.notes.service;

import com.task.notes.dto.UserDTO;
import com.task.notes.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    List<UserDTO> getUsers();

    UserDTO getUserById(String id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(String id, UserDTO user);

    String deleteUser(String id);
}
