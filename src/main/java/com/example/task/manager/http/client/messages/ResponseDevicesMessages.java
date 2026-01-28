package com.example.task.manager.http.client.messages;

import com.example.task.manager.http.client.models.DeviceModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseDevicesMessages {
    private String message;
    private List<DeviceModel> deviceList;
}
