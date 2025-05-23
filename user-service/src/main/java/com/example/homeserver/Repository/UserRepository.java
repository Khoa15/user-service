package com.example.homeserver.Repository;

import com.example.homeserver.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    boolean existsByUsername(String username);
}
