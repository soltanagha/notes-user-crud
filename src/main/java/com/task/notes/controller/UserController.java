package com.task.notes.controller;

import com.task.notes.dto.UserDTO;
import com.task.notes.entity.Response;
import com.task.notes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("user",userService.createUser(userDTO)))
                        .message("User created!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("users",userService.getUsers()))
                        .message("Fetched all users!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("user",userService.getUserById(id)))
                        .message("User fetched by id!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("user",userService.updateUser(id, user)))
                        .message("User updated!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("userID",userService.deleteUser(id)))
                        .message("User deleted!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
}
