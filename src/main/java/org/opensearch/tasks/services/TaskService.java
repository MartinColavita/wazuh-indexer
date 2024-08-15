package org.opensearch.tasks.services;

import org.opensearch.tasks.model.Task;
import java.util.List;

/**
 * La interfaz TaskService define los métodos que se pueden utilizar para realizar operaciones CRUD en las tareas.
 * Estos métodos incluyen la creación, recuperación, actualización y eliminación de tareas, así como la obtención de todas las tareas.
 *
 * La implementación de esta interfaz se encargará de interactuar con la capa de datos para realizar estas operaciones.
 *
 * Ejemplo de uso:
 * <pre>
 * {@code
 * TaskService taskService = new TaskServiceImpl(new TaskRepositoryImpl(client));
 * Task newTask = new Task("1", "Title", "Status", LocalDate.now(), null, "Assignee");
 * taskService.createTask(newTask);
 * Task retrievedTask = taskService.getTask("1");
 * List<Task> allTasks = taskService.getAllTasks();
 * Task updatedTask = new Task("1", "Updated Title", "Updated Status", LocalDate.now(), null, "Updated Assignee");
 * taskService.updateTask("1", updatedTask);
 * taskService.deleteTask("1");
 * }
 * </pre>
 */
public interface TaskService {
    Task createTask(Task task);

    Task getTask(String id);

    List<Task> getAllTasks();

    Task updateTask(String id, Task task);

    void deleteTask(String id);

}