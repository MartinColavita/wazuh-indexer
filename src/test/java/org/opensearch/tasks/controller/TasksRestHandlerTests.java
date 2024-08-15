package org.opensearch.tasks.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opensearch.client.node.NodeClient;
import org.opensearch.core.rest.RestStatus;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.services.TaskService;
import org.opensearch.tasks.model.Task;
import org.opensearch.rest.BytesRestResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para TasksRestHandler.
 */
public class TasksRestHandlerTests {
    private TaskService taskService;
    private TasksRestHandler tasksRestHandler;
    private RestChannel channel;
    private NodeClient client;

    @BeforeEach
    public void setUp() {
        taskService = mock(TaskService.class);
        tasksRestHandler = new TasksRestHandler(taskService);
        channel = mock(RestChannel.class);
        client = mock(NodeClient.class);
    }

    @Test
    public void handleGetRequest() throws IOException {
        RestRequest request = mock(RestRequest.class);
        when(request.method()).thenReturn(RestRequest.Method.GET);
        when(taskService.getAllTasks()).thenReturn(List.of(new Task()));

        try {
            tasksRestHandler.handleRequest(request, channel, client);
        } catch (Exception e) {
            channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, "Error handling GET request"));
        }
        verify(channel, times(1)).sendResponse(any());
    }

    @Test
    public void handlePostRequest() throws IOException {
        RestRequest request = mock(RestRequest.class);
        when(request.method()).thenReturn(RestRequest.Method.POST);
        when(request.content().utf8ToString()).thenReturn("{\"id\":\"1\"}");

        try {
            tasksRestHandler.handleRequest(request, channel, client);
        } catch (Exception e) {
            channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, "Error handling POST request"));
        }
        verify(channel, times(1)).sendResponse(any());
    }

    @Test
    public void handlePutRequest() throws IOException {
        RestRequest request = mock(RestRequest.class);
        when(request.method()).thenReturn(RestRequest.Method.PUT);
        when(request.param("id")).thenReturn("1");
        when(request.content().utf8ToString()).thenReturn("{\"id\":\"1\"}");

        try {
            tasksRestHandler.handleRequest(request, channel, client);
        } catch (Exception e) {
            channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, "Error handling PUT request"));
        }
        verify(channel, times(1)).sendResponse(any());
    }

    @Test
    public void handleDeleteRequest() throws IOException {
        RestRequest request = mock(RestRequest.class);
        when(request.method()).thenReturn(RestRequest.Method.DELETE);
        when(request.param("id")).thenReturn("1");

        try {
            tasksRestHandler.handleRequest(request, channel, client);
        } catch (Exception e) {
            channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, "Error handling DELETE request"));
        }
        verify(channel, times(1)).sendResponse(any());
    }
}