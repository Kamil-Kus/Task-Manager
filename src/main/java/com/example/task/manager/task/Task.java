package com.example.task.manager.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userId;
    private TypeOfTask type;
    @OneToMany(mappedBy = "id", cascade = {CascadeType.PERSIST})
    private List<Device> targets;
    private Status status;

    public TaskDTO toDto() {
        return TaskDTO.builder()
                .name(this.name)
                .userId(this.userId)
                .targets(convertToTaskDTO(this.getTargets()))
                .typeOfTask(this.getType())
                .build();
    }

    public TaskExecutorDTO toExecutorDto() {
        return TaskExecutorDTO.builder()
                .deviceList(convertToTaskDTO(this.targets))
                .typeOfTask(this.type)
                .build();
    }

    private List<DeviceDTO> convertToTaskDTO(List<Device> targets){
        return targets.stream().map(Device::toDeviceDTO).toList();
    }
}
