/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks;

import org.apache.http.HttpHost;
import org.opensearch.client.Client;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.cluster.metadata.IndexNameExpressionResolver;
import org.opensearch.cluster.node.DiscoveryNodes;
import org.opensearch.common.settings.ClusterSettings;
import org.opensearch.common.settings.IndexScopedSettings;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.settings.SettingsFilter;
import org.opensearch.core.common.transport.TransportAddress;
import org.opensearch.plugins.ActionPlugin;
import org.opensearch.plugins.Plugin;
import org.opensearch.rest.RestController;
import org.opensearch.rest.RestHandler;
import org.opensearch.tasks.controller.TasksRestHandler;
import org.opensearch.tasks.repository.TaskRepository;
import org.opensearch.tasks.repository.TaskRepositoryImpl;
import org.opensearch.tasks.services.TaskService;
import org.opensearch.tasks.services.TaskServiceImpl;

import java.net.InetAddress;
import java.util.List;
import java.util.function.Supplier;

/** TasksPlugin actúa como el punto de entrada del plugin, donde se definen las configuraciones iniciales
 * y se registran los componentes (como los RestHandler) que OpenSearch utilizará cuando ejecute el plugin. */
public class TasksPlugin extends Plugin implements ActionPlugin {
    private final TaskService taskService;

    public TasksPlugin() {
        // Obtener una instancia de RestHighLevelClient
        RestHighLevelClient client = getClient();

        // Crear la instancia de TaskRepository
        TaskRepository taskRepository = new TaskRepositoryImpl(client);

        // Crear la instancia de TaskService con la instancia de TaskRepository
        this.taskService = new TaskServiceImpl(taskRepository);
    }



    /** Método para obtener una instancia de RestHighLevelClient
     * Es responsable de obtener una instancia de RestHighLevelClient, que es necesaria para interactuar con OpenSearch.
     */
    private RestHighLevelClient getClient() {
        // Crear una instancia de RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        return client;
    }


    /** Este metodo retorna una lista de objetos RestHandler que el plugin va a manejar
     *Este método ha sobrescrito el método getRestHandlers() para devolver una lista de RestHandler, donde se han registrado las rutas de la API REST que se exponen.
     */
    @Override
    public List<RestHandler> getRestHandlers(Settings settings, RestController restController, ClusterSettings clusterSettings, IndexScopedSettings indexScopedSettings, SettingsFilter settingsFilter, IndexNameExpressionResolver indexNameExpressionResolver, Supplier<DiscoveryNodes> nodesInCluste) {
        return List.of(new TasksRestHandler(taskService));
    }



}