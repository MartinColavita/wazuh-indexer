package org.opensearch.tasks.services;


import org.opensearch.tasks.model.Task;
import org.opensearch.tasks.repository.TaskRepository;
import org.opensearch.tasks.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

/**
 * TaskServiceImpl es la implementación de la interfaz TaskService.
 * Utiliza TaskRepository para interactuar con la base de datos o el índice de OpenSearch.
 */
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    /**
     * Crea una nueva instancia de TaskServiceImpl.
     *
     * @param taskRepository El repositorio de tareas para interactuar con la base de datos o el índice de OpenSearch.
     */
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        try {
            if (taskRepository.getTaskById(task.getId()) != null) {
                throw new IllegalArgumentException("Task with ID " + task.getId() + " already exists.");
            }
            taskRepository.saveTask(task);
            return task;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create task: " + e.getMessage(), e);
        }
    }

    @Override
    public Task getTask(String id) {
        try {
            String taskJson = taskRepository.getTaskById(id);
            return JsonUtil.parseTaskFromJson(taskJson);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            return taskRepository.getAllTasks();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all tasks: " + e.getMessage(), e);
        }
    }

    @Override
    public Task updateTask(String id, Task task) {
        try {
            if (taskRepository.getTaskById(id) == null) {
                throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
            }
            taskRepository.updateTask(id, task);
            return task;
        } catch (IOException e) {
            throw new RuntimeException("Failed to update task: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTask(String id) {
        try {
            if (taskRepository.getTaskById(id) == null) {
                throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
            }
            taskRepository.deleteTask(id);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete task: " + e.getMessage(), e);
        }
    }
}