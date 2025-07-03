Taller: Arquitectura Pub/Sub con APIs y RabbitMQ
Integrantes: Martín Vargas, Kevin Rosero

1. Descripción Técnica
Este repositorio contiene una implementación de la arquitectura Publicador/Suscriptor utilizando Spring Boot y RabbitMQ. El sistema simula el procesamiento asíncrono de tareas estudiantiles a través de tres microservicios independientes:

publisher-api: Un servicio REST que expone un endpoint POST /subir-tarea para recibir datos y publicarlos en un exchange de RabbitMQ.

subscriber-notifications: Un servicio de consola que se suscribe al exchange para recibir una copia de los mensajes y simular el envío de una notificación.

subscriber-plagiarism: Un segundo servicio de consola que también se suscribe al mismo exchange para recibir los mensajes y simular un análisis de plagio.

La comunicación entre los servicios es gestionada por RabbitMQ, asegurando el desacoplamiento total entre el publicador y los múltiples suscriptores. El formato de intercambio de mensajes es JSON.

2. Requisitos Previos
 Java 11 o superior

 Apache Maven 3.6+

 Docker Desktop

3. Estructura del Repositorio
El proyecto está organizado en tres carpetas principales, cada una conteniendo un microservicio de Maven independiente:

/publisher-api

Descripción: Proyecto del servicio publicador (API REST).

/subscriber-notifications

Descripción: Proyecto del servicio suscriptor de notificaciones.

/subscriber-plagiarism

Descripción: Proyecto del servicio suscriptor de análisis de plagio.

4. Instrucciones de Compilación y Ejecución
Siga estos pasos en orden para levantar y probar el sistema completo.

Paso 1: Levantar el Broker de Mensajería (RabbitMQ)
Asegúrese de que Docker Desktop esté en ejecución. Luego, abra una terminal y ejecute el siguiente comando para iniciar un contenedor de RabbitMQ con un usuario administrador (admin/admin):

docker run -d --name rabbitmq-taller-apis -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management

Una vez que el contenedor esté corriendo, acceda al panel de administración en http://localhost:15672. Inicie sesión y cree manualmente el siguiente componente:

Exchange Name: entregas.tareas

Type: fanout

Durability: Durable

Paso 2: Compilación de los Proyectos (Opcional pero Recomendado)
Este paso asegura que todas las dependencias se descarguen correctamente. Abra una terminal en la carpeta raíz del proyecto (Taller-PubSub-APIs) y ejecute los siguientes comandos:

cd publisher-api
mvn clean install

cd ../subscriber-notifications
mvn clean install

cd ../subscriber-plagiarism
mvn clean install

Paso 3: Ejecución de los Microservicios
Necesitará tres terminales separadas para ejecutar cada servicio de forma simultánea.

Terminal 1: Iniciar el Publicador (API)
# Desde la carpeta raíz Taller-PubSub-APIs/
cd publisher-api
mvn spring-boot:run

Terminal 2: Iniciar el Suscriptor de Notificaciones
# Desde la carpeta raíz Taller-PubSub-APIs/
cd subscriber-notifications
mvn spring-boot:run

Terminal 3: Iniciar el Suscriptor de Plagio
# Desde la carpeta raíz Taller-PubSub-APIs/
cd subscriber-plagiarism
mvn spring-boot:run

Espere a que los tres servicios muestren el banner de Spring Boot y los logs indiquen que han iniciado y se han conectado a RabbitMQ.

Paso 4: Verificación del Sistema
Con los tres servicios corriendo, abra una cuarta terminal para enviar una petición de prueba al endpoint del publicador usando curl.

curl -X POST http://localhost:8080/subir-tarea -H "Content-Type: application/json" -d "{\"estudiante\": \"Juan Perez\", \"curso\": \"Integracion de Sistemas\", \"archivo\": \"tarea1.docx\", \"fechaEnvio\": \"2025-07-02T19:00:00\"}"

5. Resultado Esperado
La terminal de curl debe devolver una respuesta exitosa: Tarea enviada para procesamiento!.

La terminal del publisher-api debe mostrar el log: Publicando mensaje: Tarea{...}.

La terminal del subscriber-notifications debe mostrar el log: [SERVICIO DE NOTIFICACIONES] Mensaje recibido: Tarea{...}.

La terminal del subscriber-plagiarism debe mostrar el log: [SERVICIO DE PLAGIO] Mensaje recibido: Tarea{...}.

Esto confirma que el mensaje fue publicado y recibido por ambos suscriptores de manera desacoplada.
