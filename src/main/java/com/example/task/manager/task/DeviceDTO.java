package com.example.task.manager.task;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private String name;
    private TypeOfDevice typeOfDevice;
}
