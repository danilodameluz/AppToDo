package com.api.apptodo.controllers;

import com.api.apptodo.dto.TaskDto;
import com.api.apptodo.models.TaskModel;
import com.api.apptodo.models.enums.Status;
import com.api.apptodo.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService= taskService;
    }

    @PostMapping
    public ResponseEntity<Object> saveTask(@RequestBody @Valid TaskDto taskDto){
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setPriority(taskDto.getPriority().toString());
        taskModel.setStatus(Status.ABERTA.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));
    }

    @GetMapping
    public ResponseEntity<List<TaskModel>> getAllTasks(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id")Integer id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneTask(@PathVariable(value = "id") Integer id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        taskService.delete(taskModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted sucessfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") Integer id,
                                             @RequestBody @Valid TaskDto taskDto){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setId(taskModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));

    }
}
