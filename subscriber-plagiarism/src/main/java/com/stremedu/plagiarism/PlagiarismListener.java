package com.stremedu.plagiarism;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PlagiarismListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "plagio.queue", durable = "true"), // <-- COLA DIFERENTE
            exchange = @Exchange(name = "entregas.tareas", type = ExchangeTypes.FANOUT)
    ))
    public void recibirMensaje(Tarea tarea) {
        System.out.println("[SERVICIO DE PLAGIO] Mensaje recibido: " + tarea.toString());
        System.out.println("[SERVICIO DE PLAGIO] Analizando archivo '" + tarea.getArchivo() + "' en busca de plagio...");
        System.out.println("----------------------------------------------------");
    }
}