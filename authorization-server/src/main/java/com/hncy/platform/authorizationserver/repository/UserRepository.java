package com.hncy.platform.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hncy.platform.authorizationserver.domain.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByFullnameLike(String fullname);
    List<User> findAll();
}