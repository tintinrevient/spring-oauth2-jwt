package com.hncy.platform.resourceserver.repository;

import com.hncy.platform.resourceserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByFullnameLike(String fullname);
    List<User> findAll();
}
