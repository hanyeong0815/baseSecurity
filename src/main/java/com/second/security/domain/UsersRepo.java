package com.second.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Long> {
    @Query(
            "select u from Users u where u.username = ?1"
    )
    Users findUserNameByUsername(String username);

    Optional<Users> findByUsername(String username);

    UserInfoMapping findByNameAndEmail(String name, String email);
}
