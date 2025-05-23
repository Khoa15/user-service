package com.example.homeserver.Service;

import com.example.homeserver.Model.User;
import com.example.homeserver.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
}
