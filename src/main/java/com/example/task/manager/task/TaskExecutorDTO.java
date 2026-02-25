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
public class TaskExecutorDTO {
    private TypeOfTask typeOfTask;
    private List<DeviceDTO> deviceList;
}
