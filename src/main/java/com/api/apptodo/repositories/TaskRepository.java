package com.api.apptodo.repositories;

import com.api.apptodo.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {

    public List<TaskModel> findByStatus (String status);

}
