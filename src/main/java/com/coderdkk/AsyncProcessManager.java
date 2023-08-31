package com.coderdkk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Component
@Slf4j
public class AsyncProcessManager {
  private final Map<String, Future> runningTasks = new ConcurrentHashMap<>();

  public String getNewTaskId() {
    return UUID.randomUUID().toString();
  }

  public void registerNewTask(String taskId, Future task) {
    runningTasks.put(taskId, task);
  }

  public Map<String, Future> getRunningTasks() {
    return runningTasks;
  }

}
