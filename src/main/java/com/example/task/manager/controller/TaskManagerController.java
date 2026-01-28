package com.example.task.manager.controller;

import com.example.task.manager.task.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController(value = "/manager/task")
@AllArgsConstructor
public class TaskManagerController {

    private TaskManager taskManager;


    @PostMapping(value = "/add")
    public ResponseEntity<ResponseMessage> addTask(@RequestBody TaskDTO task) {
        var targetsList = task.getTargets();
        ArrayList<Device> devices = new ArrayList<>();
        for (DeviceDTO deviceDTO : targetsList) {
            devices.add(Device.builder()
                    .name(deviceDTO.getName())
                    .typeOfDevice(deviceDTO.getTypeOfDevice())
                    .build());
        }
        var build = Task.builder()
                .name(task.getName())
                .userId(task.getUserId())
                .type(task.getTypeOfTask())
                .targets(devices)
                .status(Status.PENDING)
                .build();
        taskManager.addTask(build);
        return new ResponseEntity<>(new ResponseMessage("Task added"), HttpStatus.CREATED);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseMessageList> getAll() {
        var result = taskManager.getAllTasks();
        return new ResponseEntity<>(new ResponseMessageList("Return all tasks", result), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseMessageList> getById(@PathVariable Long id) {
        var task1 = taskManager.getTaskById(id)
                .orElse(new Task());
        var list = new ArrayList<Task>();
        list.add(task1);
        ResponseMessageList dataOfOneTask = new ResponseMessageList("Data of one task", list);
        return new ResponseEntity<>(dataOfOneTask, HttpStatus.OK);
    }

    @PostMapping(value = "/launch")
    public ResponseEntity<ResponseMessage> startTasks(@RequestBody ListOfTaskToRun listOfTaskToRun) {
        taskManager.runListOfTasks(listOfTaskToRun);

        return new ResponseEntity<>(new ResponseMessage("Tasks are processing check status."), HttpStatus.ACCEPTED);
    }
}
