Taller: Arquitectura Pub/Sub con APIs y RabbitMQ
Integrantes: Martín Vargas, Kevin Rosero

1. Descripción General del Proyecto
Este repositorio contiene una implementación de la arquitectura Publicador/Suscriptor utilizando Spring Boot y RabbitMQ. El sistema simula el procesamiento asíncrono de tareas estudiantiles a través de tres microservicios independientes, demostrando un caso de uso real para sistemas distribuidos y desacoplados.

El objetivo principal es mostrar cómo los eventos (en este caso, la entrega de una tarea) pueden ser procesados por múltiples servicios en paralelo sin que estos tengan conocimiento directo entre sí, aumentando la escalabilidad y resiliencia del sistema.

2. Arquitectura y Componentes
La solución está compuesta por los siguientes microservicios:

publisher-api

Rol: Publicador.

Descripción: Es un servicio REST construido con Spring Boot que expone un endpoint POST /subir-tarea. Su única responsabilidad es recibir los datos de una nueva tarea, convertirlos a formato JSON y publicarlos en un exchange de RabbitMQ. Actúa como el punto de entrada al flujo de procesamiento asíncrono.

subscriber-notifications

Rol: Suscriptor.

Descripción: Es un servicio de consola que se suscribe al exchange de RabbitMQ. Al recibir una copia del mensaje de la tarea, simula el envío de una notificación por correo electrónico al profesor correspondiente, demostrando una acción de negocio que se dispara a raíz del evento.

subscriber-plagiarism

Rol: Suscriptor.

Descripción: Es un segundo servicio de consola que también se suscribe al mismo exchange. Su función es recibir el mismo mensaje de la tarea y simular un proceso de análisis de plagio sobre el documento entregado. Opera de forma completamente independiente al servicio de notificaciones.

3. Requisitos de Software
Java 11 o superior

Apache Maven 3.6+

Docker Desktop

4. Estructura del Repositorio
El proyecto está organizado en tres carpetas principales, cada una conteniendo un microservicio de Maven independiente:

Taller-PubSub-APIs/
├── publisher-api/              # Proyecto del servicio publicador (API REST)
├── subscriber-notifications/   # Proyecto del suscriptor de notificaciones
└── subscriber-plagiarism/      # Proyecto del suscriptor de análisis de plagio

5. Guía de Despliegue y Ejecución (Paso a Paso)
Siga estas instrucciones en orden para levantar y probar el sistema completo.

Paso 1: Levantar el Broker de Mensajería (RabbitMQ)
Asegúrese de que Docker Desktop esté en ejecución. Luego, abra una terminal y ejecute el siguiente comando para iniciar un contenedor de RabbitMQ. Este comando configura un usuario admin con contraseña admin para facilitar el acceso.

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

6. Resultado Esperado
La terminal de curl debe devolver una respuesta exitosa: Tarea enviada para procesamiento!.

La terminal del publisher-api debe mostrar el log: Publicando mensaje: Tarea{...}.

La terminal del subscriber-notifications debe mostrar el log: [SERVICIO DE NOTIFICACIONES] Mensaje recibido: Tarea{...}.

La terminal del subscriber-plagiarism debe mostrar el log: [SERVICIO DE PLAGIO] Mensaje recibido: Tarea{...}.

Esto confirma que el mensaje fue publicado y recibido por ambos suscriptores de manera desacoplada.
