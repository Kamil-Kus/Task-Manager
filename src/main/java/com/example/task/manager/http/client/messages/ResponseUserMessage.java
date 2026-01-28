package com.example.task.manager.http.client.messages;

import com.example.task.manager.http.client.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseUserMessage {
    private String message;
    private List<User> userDataList;
}
