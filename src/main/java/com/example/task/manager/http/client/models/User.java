package com.example.task.manager.http.client.models;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String nick;
    private String mail;
    private Role role;
    private Status status;
}
