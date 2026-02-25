package com.example.task.manager.http.client.messages;

import com.example.task.manager.http.client.models.StatusDevice;
import com.example.task.manager.task.TypeOfDevice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceModelDTO {
    private String name;
    private TypeOfDevice type;
    private StatusDevice status;
}
