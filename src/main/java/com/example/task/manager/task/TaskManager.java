package com.example.task.manager.task;

import com.example.task.manager.controller.ListOfTaskToRun;
import com.example.task.manager.http.client.HttpRequestSender;
import com.example.task.manager.http.client.models.Role;
import com.example.task.manager.http.client.models.Status;
import com.example.task.manager.http.client.models.StatusDevice;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TaskManager {
    private TaskDB taskDB;
    private HttpRequestSender httpRequestSender;

    public void addTask(Task task) {
        taskDB.save(task);
    }

    public List<Task> getAllTasks() {
        return taskDB.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskDB.findById(id);
    }

    private boolean validateTask(Task task) {
        try {
            validateUser(task.getUserId(), task.getType());
            List<Device> deviceList = validateDevices(task.getTargets());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private List<Device> validateDevices(List<Device> targets) throws IOException, URISyntaxException, InterruptedException {
        var deviceList = httpRequestSender.askForDevices();
        var result = new ArrayList<Device>();
        targets.forEach(device -> {
            log.info(device.toString());
            deviceList.forEach(deviceFromManager -> {
                if (device.getName().equals(deviceFromManager.getName()) &&
                        device.getTypeOfDevice().equals(deviceFromManager.getType()) && deviceFromManager.getStatus() == StatusDevice.READY) {
                    result.add(device);
                }
            });
        });
        return result;
    }

    private boolean validateUser(String userId, TypeOfTask type) throws URISyntaxException, IOException, InterruptedException {
        var userList = httpRequestSender.askForUser(userId);

        log.info("User : {}", userList.getFirst());
        var user = userList.getFirst();
        if (user.getStatus() != Status.ACTIVE) {
            return false;
        }
        if (user.getRole() == Role.ADMIN) {
            return true;
        }
        if (user.getRole() == Role.READER && (type == TypeOfTask.DISCOVERY || type == TypeOfTask.GET_INFO)) {
            return true;
        }
        return user.getRole() == Role.EXECUTOR && (type == TypeOfTask.BACKUP || type == TypeOfTask.UPDATE);
    }

    private void startTask(Task task) {
        try {
            httpRequestSender.sendTaskToExecutor(task);
        } catch (Exception exception) {
            log.error("Catch error {}", exception.getMessage());
        }
    }

    public void runListOfTasks(ListOfTaskToRun listOfTaskToRun) {
        List<Task> taskList = new ArrayList<>();
        for (Long id : listOfTaskToRun.getListOfIds()) {
            taskDB.findById(id).ifPresent(taskList::add);
        }
        for (Task task : taskList) {
            validateTask(task);
            log.info("Task {} validated", task);
        }
        taskList.forEach(this::startTask);
    }
}
