package lt.techin.Movies_studio_2.dto;

import lt.techin.Movies_studio_2.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserMapper {

  public static List<UserRequestDTO> toUserRequestDTOList(List<User> users) {
    List<UserRequestDTO> result = users.stream()
            .map(user -> new UserRequestDTO(user.getUsername(), user.getPassword()))
            .toList();

    return result;
  }

  public static List<UserResponseDTO> toUserResponseDTOList(List<User> users) {
    List<UserResponseDTO> result = users.stream()
            .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles()))
            .toList();

    return result;
  }

  public static UserRequestDTO toUserRequestDTO(User user) {
    return new UserRequestDTO(user.getUsername(), user.getPassword());
  }

  public static UserResponseDTO toUserResponseDTO(User user) {
    return new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
  }

  public static void updateUserFromDTO(User user, UserResponseDTO userResponseDTO) {
    user.setRoles(userResponseDTO.roles());
  }

  public static User toUser(UserRequestDTO userRequestDTO) {
    User user = new User();
    user.setPassword(userRequestDTO.password());
    user.setUsername(userRequestDTO.userName());

    return user;
  }

  public static User toUserResponse(UserResponseDTO userResponseDTO) {
    User user = new User();
    user.setRoles(userResponseDTO.roles());

    return user;
  }


}
