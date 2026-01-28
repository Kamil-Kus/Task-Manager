package com.example.task.manager.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDB extends JpaRepository<Task, Long> {
}
