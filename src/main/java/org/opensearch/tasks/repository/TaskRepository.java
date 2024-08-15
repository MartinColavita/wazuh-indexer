package org.opensearch.tasks.repository;


import org.opensearch.tasks.model.Task;
import java.io.IOException;
import java.util.List;


/**
 * Repository interface for tasks.
 * The implementation of this interface will provide the logic to interact with the OpenSearch database or index.
 */
public interface TaskRepository {

    /**
     * Saves a task.
     *
     * @param task the task to save
     * @throws IOException if an I/O error occurs
     */
    void saveTask(Task task) throws IOException;

    /**
     * Retrieves all tasks.
     *
     * @return the list of tasks
     * @throws IOException if an I/O error occurs
     */
    List<Task> getAllTasks() throws IOException;

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the task ID
     * @return the task as a JSON string
     * @throws IOException if an I/O error occurs
     */
    String getTaskById(String taskId) throws IOException;

    /**
     * Updates a task.
     *
     * @param taskId the task ID
     * @param task the task to update
     * @throws IOException if an I/O error occurs
     */
    void updateTask(String taskId, Task task) throws IOException;

    /**
     * Deletes a task.
     *
     * @param taskId the task ID
     * @throws IOException if an I/O error occurs
     */
    void deleteTask(String taskId) throws IOException;


}
