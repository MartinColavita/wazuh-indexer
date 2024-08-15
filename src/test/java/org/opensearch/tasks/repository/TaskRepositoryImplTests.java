package org.opensearch.tasks.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.tasks.model.Task;
import org.opensearch.tasks.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskRepositoryImplTests {
    private RestHighLevelClient client;
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        client = mock(RestHighLevelClient.class);
        taskRepository = new TaskRepositoryImpl(client);
    }

    @Test
    public void saveTask() throws IOException {
        Task task = new Task();
        task.setId("1");
        doNothing().when(client).index(any(), any());

        taskRepository.saveTask(task);
        verify(client, times(1)).index(any(), any());
    }

    @Test
    public void getTaskById() throws IOException {
        String taskId = "1";
        when(client.get(any(), any())).thenReturn(mock(GetResponse.class));

        String taskJson = taskRepository.getTaskById(taskId);
        assertNotNull(taskJson);
    }

    @Test
    public void getAllTasks() throws IOException {
        when(client.search(any(), any())).thenReturn(mock(SearchResponse.class));

        List<Task> tasks = taskRepository.getAllTasks();
        assertNotNull(tasks);
    }

    @Test
    public void updateTask() throws IOException {
        Task task = new Task();
        task.setId("1");
        doNothing().when(client).update(any(), any());

        taskRepository.updateTask("1", task);
        verify(client, times(1)).update(any(), any());
    }

    @Test
    public void deleteTask() throws IOException {
        String taskId = "1";
        doNothing().when(client).delete(any(), any());

        taskRepository.deleteTask(taskId);
        verify(client, times(1)).delete(any(), any());
    }
}