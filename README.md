# wazuh-indexer

# Tasks Plugin for OpenSearch

## Descripción General del Proyecto

El proyecto es un complemento para OpenSearch que permite gestionar tareas. Está desarrollado en Java y utiliza Gradle como sistema de construcción. El complemento incluye varias clases para manejar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de las tareas, así como tests unitarios para asegurar la funcionalidad.

## Arquitectura del Proyecto

### Clases Principales

#### `Task`
**Descripción**: Clase modelo que representa una tarea con un `id` y una `description`.

#### `TaskRepository`
**Descripción**: Interfaz que define los métodos para interactuar con el almacenamiento de tareas.

#### `TaskServiceImpl`
**Descripción**: Implementación del servicio que maneja la lógica de negocio para las tareas.

#### `TasksPlugin`
**Descripción**: Clase principal del complemento que extiende `Plugin` de OpenSearch.

### Tests

#### `TaskServiceImplTests`
**Descripción**: Clase de prueba que verifica la funcionalidad de `TaskServiceImpl`.

### Dockerfile
**Descripción**: El `Dockerfile` crea una imagen de Docker que contiene el complemento y lo ejecuta en un entorno de Java.

## Instrucciones de Construcción y Ejecución

1. **Construir el proyecto**:
    ```sh
    ./gradlew clean build
    ```
2. **Ejecutar los tests**:
    ```sh
    ./gradlew test
    ```
3. **Construir la imagen de Docker**:
    ```sh
    docker build -t tasks-plugin .
    ```
4. **Ejecutar la imagen de Docker**:
    ```sh
    docker run -p 9200:9200 tasks-plugin
    ```

## Endpoints para Pruebas

### Crear una Tarea
**Endpoint**: `POST /tasks`  
**Descripción**: Crea una nueva tarea.  
**Ejemplo de cURL**:
```sh
curl -X POST "http://localhost:9200/tasks" -H "Content-Type: application/json" -d '{"id":"1", "description":"Nueva tarea"}'
```

### Obtener una Tarea
**Endpoint**: `GET /tasks/{id}`  
**Descripción**: Obtiene una tarea por su ID.  
**Ejemplo de cURL**:
```sh
curl -X GET "http://localhost:9200/tasks/1"
```

### Obtener Todas las Tareas
**Endpoint**: `GET /tasks`  
**Descripción**: Obtiene todas las tareas.  
**Ejemplo de cURL**:
```sh
curl -X GET "http://localhost:9200/tasks"
```

### Actualizar una Tarea
**Endpoint**: `PUT /tasks/{id}`  
**Descripción**: Actualiza una tarea existente.  
**Ejemplo de cURL**:
```sh
curl -X PUT "http://localhost:9200/tasks/1" -H "Content-Type: application/json" -d '{"description":"Tarea actualizada"}'
```

### Eliminar una Tarea
**Endpoint**: `DELETE /tasks/{id}`  
**Descripción**: Elimina una tarea por su ID.  
**Ejemplo de cURL**:
```sh
curl -X DELETE "http://localhost:9200/tasks/1"
```