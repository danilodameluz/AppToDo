package com.api.apptodo.services;

import com.api.apptodo.models.TaskModel;
import com.api.apptodo.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskModel save(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }


    public List<TaskModel> findAll() {
        return taskRepository.findAll();
    }

    public Optional<TaskModel> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public void delete(TaskModel taskModel) {
        taskRepository.delete(taskModel);
    }

    public List<TaskModel> findByStatus(String status){ return taskRepository.findByStatus(status);}
}
