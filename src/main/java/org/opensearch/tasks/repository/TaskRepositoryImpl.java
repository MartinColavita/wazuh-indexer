package org.opensearch.tasks.repository;


import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.update.UpdateRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHit;
import org.opensearch.search.SearchHits;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.tasks.model.Task;
import org.opensearch.tasks.utils.JsonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * TaskRepositoryImpl es la implementación de la interfaz TaskRepository.
 * Proporciona la lógica para interactuar con la base de datos o el índice de OpenSearch.
 */
public class TaskRepositoryImpl implements TaskRepository {
    private final RestHighLevelClient client;

    public TaskRepositoryImpl(RestHighLevelClient client) {
        this.client = client;
    }


    /**
     * Crea una nueva tarea en la base de datos o el índice de OpenSearch.
     */
    @Override
    public void saveTask(Task task) throws IOException {
        try {
            IndexRequest request = new IndexRequest("tasks")
                    .id(task.getId())
                    .source(JsonUtil.convertTaskToJson(task), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new IOException("Failed to save task: " + e.getMessage(), e);
        }
    }


    /**
     * Obtiene una tarea por su ID.
     */
    @Override
    public String getTaskById(String taskId) throws IOException {
        try {
            GetRequest getRequest = new GetRequest("tasks", taskId);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return getResponse.getSourceAsString();
            } else {
                throw new IOException("Task with ID " + taskId + " does not exist.");
            }
        } catch (IOException e) {
            throw new IOException("Failed to get task by ID: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las tareas de la base de datos o el índice de OpenSearch.
     */
    @Override
    public List<Task> getAllTasks() throws IOException {
        try {
            SearchRequest searchRequest = new SearchRequest("tasks");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(sourceBuilder);

            SearchHits hits = client.search(searchRequest, RequestOptions.DEFAULT).getHits();
            List<Task> tasks = new ArrayList<>();
            for (SearchHit hit : hits) {
                tasks.add(JsonUtil.parseTaskFromJson(hit.getSourceAsString()));
            }
            return tasks;
        } catch (IOException e) {
            throw new IOException("Failed to get all tasks: " + e.getMessage(), e);
        }
    }


    /**
     * Recupera una tarea por su ID.
     */
    @Override
    public void updateTask(String taskId, Task task) throws IOException {
        try {
            UpdateRequest request = new UpdateRequest("tasks", taskId)
                    .doc(JsonUtil.convertTaskToJson(task), XContentType.JSON);
            client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new IOException("Failed to update task: " + e.getMessage(), e);
        }
    }


    /**
     * Elimina una tarea de la base de datos o el índice de OpenSearch.
     */
    @Override
    public void deleteTask(String taskId) throws IOException {
        try {
            GetRequest getRequest = new GetRequest("tasks", taskId);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                DeleteRequest request = new DeleteRequest("tasks", taskId);
                client.delete(request, RequestOptions.DEFAULT);
            } else {
                throw new IOException("Task with ID " + taskId + " does not exist.");
            }
        } catch (IOException e) {
            throw new IOException("Failed to delete task: " + e.getMessage(), e);
        }
    }

}
