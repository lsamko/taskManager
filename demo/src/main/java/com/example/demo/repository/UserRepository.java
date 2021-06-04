package com.example.demo.repository;

import com.example.demo.entity.TasksByUser;
import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findUserByUserId(String uuid);

    void deleteUserByUserId(String uuid);

    List<TasksByUser> findTasksByUserId(String userId);

    boolean existsUserByLastName(String lastName);
    boolean existsTaskByUserId(String uuid);
}
