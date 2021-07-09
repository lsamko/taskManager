package com.example.demo.repository;

import com.example.demo.entity.Task;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskByTaskId(String uuid);

    Optional<Task> deleteTaskByTaskId(String uuid);

    List<Task> findTasksByTaskIdIn(List<String> ids);

    //@Query("select t from Task t where t.userId=:userId")
    @Query(value = "select * from task_manager.task "
        + "join task_manager.user_tasks as ut on task.id = ut.tasks_id "
        + "join task_manager.user as u on ut.user_id = u.id "
        + "where u.user_id =:userId", nativeQuery = true)
    List<Task> findTasksByUserId(String userId);

    boolean existsTaskByName(String name);

   List<Task> findTaskByDueToDateLessThan(LocalDateTime dueToDate);

    List<Task> findTaskByDueToDateBetweenAndDoneNot(LocalDateTime startDay, LocalDateTime endDay, boolean done,
        Sort sort);
}
