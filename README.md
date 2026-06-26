# Microservicio Agenda - VetNova

Microservicio encargado de administrar las citas médicas veterinarias del sistema VetNova.

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* MySQL
* Maven
* Lombok
* JUnit 5
* Mockito

## Funcionalidades

* Listar citas
* Buscar cita por ID
* Registrar citas
* Actualizar citas
* Eliminar citas

## Puerto utilizado

8082

## Base de datos

agenda_db

## Endpoints principales

### Listar citas

GET /api/v1/citas

### Buscar cita por ID

GET /api/v1/citas/{id}

### Registrar cita

POST /api/v1/citas

### Actualizar cita

PUT /api/v1/citas/{id}

### Eliminar cita

DELETE /api/v1/citas/{id}

## Validaciones implementadas

* Nombre del veterinario obligatorio.
* Fecha de la cita obligatoria.
* Hora de la cita obligatoria.
* Estado de la cita obligatorio.
* Validaciones mediante Bean Validation.

## Manejo de excepciones

* Cita no encontrada.
* Errores de validación.
* Error interno del servidor.

## Logs implementados

El microservicio registra:

* Listado de citas.
* Búsquedas por ID.
* Registro de citas.
* Actualización de citas.
* Eliminación de citas.
* Errores del sistema.

## Pruebas unitarias

Se implementaron pruebas unitarias para:

* Controlador (Controller)
* Servicio (Service)

Resultado:

* 10 pruebas ejecutadas.
* 0 fallos.

## Ejecución del proyecto

```bash
mvn spring-boot:run
```

## Autor

ad
