
package lt.techin.Movies_studio_2.controller;


import jakarta.validation.Valid;
import lt.techin.Movies_studio_2.dto.UserMapper;
import lt.techin.Movies_studio_2.dto.UserRequestDTO;
import lt.techin.Movies_studio_2.dto.UserResponseDTO;
import lt.techin.Movies_studio_2.model.Role;
import lt.techin.Movies_studio_2.model.User;
import lt.techin.Movies_studio_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getUser() {
    return ResponseEntity.ok(UserMapper.toUserResponseDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {
    Optional<User> foundUser = userService.findById(id);

    if (foundUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(UserMapper.toUserResponseDTO(foundUser.get()));
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
    User user = UserMapper.toUser(userRequestDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
//    user.setRoles(new ArrayList<Role>(List.of(new Role("ROLE_USER"))));
    User savedUser = userService.saveUser(user);


    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(UserMapper.toUserResponseDTO(savedUser));
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserResponseDTO userResponseDTO) {

    if (userService.existUserById(id)) {
      User userDb = userService.findById(id).get();

      UserMapper.updateUserFromDTO(userDb, userResponseDTO);

      userService.saveUser(userDb);

      return ResponseEntity.ok(userResponseDTO);
    }
    User savedUser = userService.saveUser(UserMapper.toUserResponse(userResponseDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/users/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(userResponseDTO);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable long id) {
    if (!userService.existUserById(id)) {
      return ResponseEntity.notFound().build();
    }
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}