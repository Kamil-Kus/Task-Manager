package com.example.task.manager.http.client.models;

import com.example.task.manager.task.TypeOfDevice;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceModel {
    private Long id;
    private String name;
    private TypeOfDevice type;
    private StatusDevice status;
}
