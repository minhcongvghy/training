package com.vsii.training.service;

import com.vsii.training.model.User;

import java.util.Optional;

public interface IUserService {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    void save(User user);

    Iterable<User> findAll();

    void delete(Long id);
}
