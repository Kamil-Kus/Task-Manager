package com.example.task.manager.http.client;

import com.example.task.manager.http.client.messages.ResponseDevicesMessages;
import com.example.task.manager.http.client.messages.ResponseUserMessage;
import com.example.task.manager.http.client.models.DeviceModel;
import com.example.task.manager.http.client.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Slf4j
@Service
public class HttpRequestSender {
    private final String urlOfUserManager = "http://localhost:8081";
    private final String urlOfDeviceManager = "http://localhost:8082";
    private final HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    Gson gson = new GsonBuilder().create();


    public List<User> askForUser(String id) throws URISyntaxException, IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(new URI(urlOfUserManager + "/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var responseMessage = objectMapper.readValue(response.body(), ResponseUserMessage.class);
        log.info("Response: {}", responseMessage.getMessage());
        log.info("Response: {}", responseMessage.getUserDataList());
        return responseMessage.getUserDataList();
    }

    public List<DeviceModel> askForDevices() throws IOException, InterruptedException, URISyntaxException {
        var request = HttpRequest.newBuilder()
                .uri(new URI(urlOfDeviceManager + "/list"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Devices: {}", response.body());
        var responseMessage = objectMapper.readValue(response.body(), ResponseDevicesMessages.class);
        return responseMessage.getDeviceList();
    }
}
