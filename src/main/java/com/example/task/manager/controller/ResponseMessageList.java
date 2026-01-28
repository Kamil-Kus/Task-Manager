package com.example.task.manager.controller;

import com.example.task.manager.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ResponseMessageList {
    private String message;
    private List<Task> taskList;
}
