package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user, List<Long> roleIds);

    void deleteUserById(Long id);

    void updateUser(User user, List<Long> roleIds);

    User getUser(Long id);

    List<User> getAllUsers();
}
