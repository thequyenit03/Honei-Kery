package com.example.service.service;

import com.example.service.entity.User;
import com.example.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User save(User user);
    User getById(Integer id);
    User findByUsername(String username);
    List<User> getAll();
    User update(Integer id, User user);
    void delete(Integer id);
}

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(Integer id, User newUser) {
        User user = getById(id);

        user.setFullName(newUser.getFullName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setAddress(newUser.getAddress());

        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}



