package com.stremedu.notifications;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "notificaciones.queue", durable = "true"),
            exchange = @Exchange(name = "entregas.tareas", type = ExchangeTypes.FANOUT)
    ))
    public void recibirMensaje(Tarea tarea) {
        System.out.println("[SERVICIO DE NOTIFICACIONES] Mensaje recibido: " + tarea);
        System.out.println("[SERVICIO DE NOTIFICACIONES] Notificando al profesor del curso '" + tarea.getCurso() + "' sobre la entrega de '" + tarea.getEstudiante() + "'.");
        System.out.println("----------------------------------------------------");
    }
}