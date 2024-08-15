package org.opensearch.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.opensearch.client.node.NodeClient;
import org.opensearch.core.rest.RestStatus;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestRequest;
import org.opensearch.tasks.services.TaskService;
import org.opensearch.tasks.model.Task;
import org.opensearch.tasks.utils.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * La clase TasksRestHandler extiende BaseRestHandler y se encarga de manejar las solicitudes HTTP
 * para las operaciones CRUD en las tareas. Define las rutas HTTP que este plugin manejará y
 * proporciona la lógica para procesar estas solicitudes.
 *
 * Las rutas definidas incluyen:
 * - GET /tasks: para obtener todas las tareas
 * - POST /tasks: para crear una nueva tarea
 * - PUT /tasks/{id}: para actualizar una tarea existente
 * - DELETE /tasks/{id}: para eliminar una tarea existente
 *
 * Cada una de estas rutas se asociará con un método que implementará la lógica correspondiente.
 * Por ejemplo, la ruta GET /tasks se asociará con un método que recuperará todas las tareas del repositorio.
 *
 * Esta clase interactuará con la capa de servicio para realizar las operaciones necesarias y devolverá
 * la respuesta al cliente.
 */
public class TasksRestHandler extends BaseRestHandler{
    private final TaskService taskService;

    public TasksRestHandler(TaskService taskService) {
        this.taskService = taskService;
    }


    /** Este metodo retorna una lista de rutas que el plugin va a manejar
     */
    @Override
    public List<Route> routes() {
        return List.of(
                new Route(RestRequest.Method.GET, "/tasks"),
                new Route(RestRequest.Method.GET, "/tasks/{id}"),
                new Route(RestRequest.Method.POST, "/tasks"),
                new Route(RestRequest.Method.PUT, "/tasks/{id}"),
                new Route(RestRequest.Method.DELETE, "/tasks/{id}")
        );
    }


    /** Este metodo retorna el nombre del plugin */
    @Override
    public String getName() {
        return "tasks_rest_handler";
    }


    /** Este metodo prepara la respuesta del request
     * maneja la solicitud según el método HTTP (GET, POST, PUT, DELETE) y envía la respuesta utilizando channel.sendResponse()

     *  channel.sendResponse(): Es la forma adecuada de responder a las solicitudes en OpenSearch.
     En lugar de retornar un objeto desde el método, le pasas la respuesta al RestChannel, que se encarga de enviarla al cliente.

     *Uso de RestChannel: Esto es importante porque OpenSearch espera que las respuestas sean enviadas de manera asíncrona utilizando
     el canal.*/
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        return channel -> {
            try {
                switch (request.method()) {
                    case GET:
                        handleGetRequest(request, client, channel);
                        break;
                    case POST:
                        handlePostRequest(request, client, channel);
                        break;
                    case PUT:
                        handlePutRequest(request, client, channel);
                        break;
                    case DELETE:
                        handleDeleteRequest(request, client, channel);
                        break;
                    default:
                        channel.sendResponse(new BytesRestResponse(RestStatus.METHOD_NOT_ALLOWED, "Method not allowed"));
                }
            } catch (IOException e) {
                channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            }
        };
    }


    /** Este metodo maneja las solicitudes GET */
    private void handleGetRequest(RestRequest request, NodeClient client, RestChannel channel) throws IOException {
        String taskId = request.param("id");  // Obtiene el parámetro 'id' de la solicitud

        if (taskId != null) {
            // Si se proporciona un ID, obtener la tarea específica
            Task task = taskService.getTask(taskId);
            if (task != null) {
                String jsonResponse = JsonUtil.convertTaskToJson(task);
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, jsonResponse));
            } else {
                channel.sendResponse(new BytesRestResponse(RestStatus.NOT_FOUND, "Task not found"));
            }
        } else {
            // Si no se proporciona un ID, obtener todas las tareas
            List<Task> tasks = taskService.getAllTasks();
            String jsonResponse = JsonUtil.convertTasksToJson(tasks);
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, jsonResponse));
        }
    }


    private void handlePostRequest(RestRequest request, NodeClient client, RestChannel channel) throws IOException {
        String requestBody = request.content().utf8ToString();
        Task newTask = JsonUtil.parseTaskFromJson(requestBody);
        taskService.createTask(newTask);
        String jsonResponse = "{\"message\": \"Task created\"}";
        channel.sendResponse(new BytesRestResponse(RestStatus.CREATED, jsonResponse));
    }

    /** Este metodo maneja las solicitudes PUT */
    private void handlePutRequest(RestRequest request, NodeClient client, RestChannel channel) throws IOException {
        String taskId = request.param("id");
        String requestBody = request.content().utf8ToString();
        Task updatedTask = JsonUtil.parseTaskFromJson(requestBody);
        taskService.updateTask(taskId, updatedTask);
        String jsonResponse = "{\"message\": \"Task updated\"}";
        channel.sendResponse(new BytesRestResponse(RestStatus.OK, jsonResponse));
    }

    /** Este metodo maneja las solicitudes DELETE */
    private void handleDeleteRequest(RestRequest request, NodeClient client, RestChannel channel) throws IOException {
        String taskId = request.param("id");
        taskService.deleteTask(taskId);
        String jsonResponse = "{\"message\": \"Task deleted\"}";
        channel.sendResponse(new BytesRestResponse(RestStatus.OK, jsonResponse));
    }


}
