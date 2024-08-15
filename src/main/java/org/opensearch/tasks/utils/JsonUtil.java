package org.opensearch.tasks.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.opensearch.tasks.model.Task;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String convertTasksToJson(List<Task> tasks) throws IOException {
        try {
            return mapper.writeValueAsString(tasks);
        } catch (IOException e) {
            throw new IOException("Failed to convert tasks to JSON: " + e.getMessage(), e);
        }
    }

    public static String convertTaskToJson(Task task) throws IOException {
        try {
            return mapper.writeValueAsString(task);
        } catch (IOException e) {
            throw new IOException("Failed to convert task to JSON: " + e.getMessage(), e);
        }
    }

    public static Task parseTaskFromJson(String json) throws IOException {
        try {
            return mapper.readValue(json, Task.class);
        } catch (IOException e) {
            throw new IOException("Failed to parse task from JSON: " + e.getMessage(), e);
        }
    }
}
