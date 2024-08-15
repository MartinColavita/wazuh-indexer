package org.opensearch.tasks.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * La clase Task representa una tarea en el sistema.
 * Cada tarea tiene un identificador único, un título, un estado, una fecha de creación, una fecha de finalización y un asignado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String id;
    private String title;
    private String status;
    private LocalDate creationDate;
    private LocalDate completionDate;
    private String assignee;
}
