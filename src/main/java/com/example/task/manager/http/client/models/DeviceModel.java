package com.example.task.manager.http.client.models;

import com.example.task.manager.task.TypeOfDevice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModel {
    private Long id;
    private String name;
    private TypeOfDevice type;
    private StatusDevice status;
}
