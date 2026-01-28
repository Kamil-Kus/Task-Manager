package com.example.task.manager.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String name;
    private String userId;
    private List<DeviceDTO> targets;
    private TypeOfTask typeOfTask;
}
