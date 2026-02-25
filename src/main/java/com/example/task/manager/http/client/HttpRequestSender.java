package com.example.task.manager.http.client;

import com.example.task.manager.http.client.messages.DeviceModelDTO;
import com.example.task.manager.http.client.messages.ResponseDevicesMessages;
import com.example.task.manager.http.client.messages.ResponseJobId;
import com.example.task.manager.http.client.messages.ResponseUserMessage;
import com.example.task.manager.http.client.models.DeviceModel;
import com.example.task.manager.http.client.models.User;
import com.example.task.manager.task.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class HttpRequestSender {
    private final String urlOfUserManager = "http://localhost:8081";
    private final String urlOfDeviceManager = "http://localhost:8082";
    private final String urlOfExecutor = "http://localhost:8083";
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
        return transformDeviceLIstDTO(responseMessage.getDeviceList());
    }

    private List<DeviceModel> transformDeviceLIstDTO(List<DeviceModelDTO> deviceList) {
        ArrayList<DeviceModel> deviceModels = new ArrayList<>();
        deviceList.forEach(deviceModelDTO -> {
            deviceModels.add(DeviceModel.builder()
                    .name(deviceModelDTO.getName())
                    .type(deviceModelDTO.getType())
                    .status(deviceModelDTO.getStatus())
                    .build());
        });
        return deviceModels;
    }

    public void sendTaskToExecutor(Task task) throws IOException, InterruptedException, URISyntaxException {
        var request = HttpRequest.newBuilder()
                .uri(new URI(urlOfExecutor + "/execute"))
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .POST(HttpRequest.BodyPublishers.ofString(generateJsonFromTask(task)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Devices: {}", response.body());
        var responseMessage = objectMapper.readValue(response.body(), ResponseJobId.class);
        log.info("Response: {}", responseMessage);
    }

    private String generateJsonFromTask(Task task) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(task.toExecutorDto());
        log.info(requestBody);
        return requestBody;
    }
}
