package com.api.apptodo.controllers;

import com.api.apptodo.dto.TaskDto;
import com.api.apptodo.models.TaskModel;
import com.api.apptodo.models.enums.StatusEnum;
import com.api.apptodo.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService= taskService;
    }

    @Operation(summary = "Inclusão de uma nova Tarefa")
    @RequestMapping(path = "/task", method = RequestMethod.POST)
    public ResponseEntity<Object> saveTask(@RequestBody @Valid TaskDto taskDto){
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setPriority(taskDto.getPriority().toString());
        taskModel.setStatus(StatusEnum.ABERTA.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));
    }

    @Operation(summary = "Exibe listagem de Tarefas a serem concluídas")
    @RequestMapping(path = "/task", method = RequestMethod.GET)
    public ResponseEntity<List<TaskModel>> getAllTasks(){
        String status = "ABERTA";
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findByStatus(status));
    }

    @Operation(summary = "Exibe uma Tarefa em específico")
    @RequestMapping(path = "/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id")Integer id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelOptional.get());
    }

    @Operation(summary = "Deleta uma nova Tarefa")
    @RequestMapping(path = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOneTask(@PathVariable(value = "id") Integer id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        taskService.delete(taskModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted sucessfully");
    }

    @Operation(summary = "Permite a edição de uma nova Tarefa")
    @RequestMapping(path = "/task/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") Integer id,
                                             @RequestBody @Valid TaskDto taskDto){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        var taskModel = taskModelOptional.get();
        taskModel.setId(taskModelOptional.get().getId());
        taskModel.setDescriptionTask(taskDto.getDescriptionTask());
        taskModel.setPriority(taskDto.getPriority().toString());
        taskModel.setUserEmail(taskDto.getUserEmail());
        taskModel.setStatus(StatusEnum.ABERTA.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));
    }

    @Operation(summary = "Finaliza uma Tarefa")
    @RequestMapping(path = "/task/{id}/finalizar", method = RequestMethod.PUT)
    public ResponseEntity<Object> endTask(@PathVariable(value = "id") Integer id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        var taskModel = taskModelOptional.get();
        taskModel.setId(taskModelOptional.get().getId());
        taskModel.setDescriptionTask(taskModelOptional.get().getDescriptionTask());
        taskModel.setPriority(taskModelOptional.get().getPriority().toString());
        taskModel.setUserEmail(taskModelOptional.get().getUserEmail());
        taskModel.setStatus(StatusEnum.FINALIZADA.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));
    }
}
