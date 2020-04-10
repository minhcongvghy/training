package com.vsii.training.repository;

import com.vsii.training.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
