package org.opensearch.tasks.repository;


import org.opensearch.tasks.model.Task;

import java.io.IOException;
import java.util.List;

/**
 * La interfaz TaskRepository define los métodos que se pueden utilizar para interactuar con la base de datos o el índice de OpenSearch.
 * Estos métodos incluyen la creación, recuperación, actualización y eliminación de tareas, así como la obtención de todas las tareas.
 *
 * La implementación de esta interfaz se encargará de proporcionar la lógica para interactuar con la base de datos o el índice de OpenSearch.
 */
public interface TaskRepository {

    /**
     * Crea una nueva tarea en la base de datos o el índice de OpenSearch.
     */
    void saveTask(Task task) throws IOException;

    /**
     * Obtiene todas las tareas de la base de datos o el índice de OpenSearch.
     */
    List<Task> getAllTasks() throws IOException;

    /**
     * Recupera una tarea por su ID.
     */
    String getTaskById(String taskId) throws IOException;

    /**
     * Actualiza una tarea existente en la base de datos o el índice de OpenSearch.
     */
    void updateTask(String taskId, Task task) throws IOException;

    /**
     * Elimina una tarea de la base de datos o el índice de OpenSearch.
     */
    void deleteTask(String taskId) throws IOException;


}
