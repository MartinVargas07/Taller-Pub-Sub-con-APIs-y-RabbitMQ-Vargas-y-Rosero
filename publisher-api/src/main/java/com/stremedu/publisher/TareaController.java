package com.stremedu.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TareaController {

    private static final String EXCHANGE_NAME = "entregas.tareas";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/subir-tarea")
    public ResponseEntity<String> subirTarea(@RequestBody Tarea tarea) {
        System.out.println("Publicando mensaje: " + tarea);
        // Enviamos el objeto Tarea. Spring lo convertirá a JSON y lo publicará en el exchange.
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", tarea); // La routing key es vacía para fanout
        return ResponseEntity.ok("Tarea enviada para procesamiento!");
    }
}