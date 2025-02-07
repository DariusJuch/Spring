package lt.techin.Movies_studio_2.service;

import lt.techin.Movies_studio_2.model.User;
import lt.techin.Movies_studio_2.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> findById(long id) {
    return userRepository.findById(id);
  }

  public boolean existUserById(long id) {
    return userRepository.existsById(id);
  }

  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }


}
