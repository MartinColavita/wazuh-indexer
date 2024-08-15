package org.opensearch.tasks.services;


import org.junit.Before;
import org.junit.Test;
import org.opensearch.tasks.model.Task;
import org.opensearch.tasks.repository.TaskRepository;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class TaskServiceImplTests {
    private TaskRepository taskRepository;
    private TaskService taskService;

    @Before
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    public void createTask() throws IOException {
        Task task = new Task();
        task.setId("1");
        when(taskRepository.getTaskById("1")).thenReturn(null);
        doNothing().when(taskRepository).saveTask(task);

        Task createdTask = taskService.createTask(task);
        assertEquals(task, createdTask);
    }

    @Test
    public void getTask() throws IOException {
        Task task = new Task();
        task.setId("1");
        when(taskRepository.getTaskById("1")).thenReturn("{\"id\":\"1\"}");

        Task retrievedTask = taskService.getTask("1");
        assertEquals("1", retrievedTask.getId());
    }

    @Test
    public void getAllTasks() throws IOException {
        when(taskRepository.getAllTasks()).thenReturn(List.of(new Task()));

        List<Task> tasks = taskService.getAllTasks();
        assertFalse(tasks.isEmpty());
    }

    @Test
    public void updateTask() throws IOException {
        Task task = new Task();
        task.setId("1");
        when(taskRepository.getTaskById("1")).thenReturn("{\"id\":\"1\"}");
        doNothing().when(taskRepository).updateTask("1", task);

        Task updatedTask = taskService.updateTask("1", task);
        assertEquals(task, updatedTask);
    }

    @Test
    public void deleteTask() throws IOException {
        when(taskRepository.getTaskById("1")).thenReturn("{\"id\":\"1\"}");
        doNothing().when(taskRepository).deleteTask("1");

        assertDoesNotThrow(() -> taskService.deleteTask("1"));
    }
}