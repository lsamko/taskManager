package com.example.demo.repository;

import com.example.demo.entity.TasksByUser;
import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findUserById(String uuid);

    void deleteUserById(String uuid);

    Optional<List<TasksByUser>> findTasksByUserId(String userId);
}
