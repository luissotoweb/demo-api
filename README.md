# API de Registros - Demo con Spring Boot 3.5

Este proyecto es una API REST simple para gestionar registros de personas, desarrollado con Spring Boot 3.5, Java 21, y documentado con Swagger/OpenAPI 3.

----------------------------------------
FUNCIONALIDAD
----------------------------------------
- Crear personas
- Listar todas las personas
- Buscar persona por ID

----------------------------------------
TECNOLOGÍAS UTILIZADAS
----------------------------------------
- Java 21
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- H2 Database (en memoria)
- Springdoc OpenAPI 2.x (Swagger)
- Lombok

----------------------------------------
ARQUITECTURA DEL CÓDIGO
----------------------------------------

El código está organizado por capas:

- **Entidad**: `Persona.java` contiene los atributos de la persona (nombre, apellido, edad, email).
- **Repositorio**: Interfaz JPA para acceder a la base de datos.
- **Servicio**: Lógica de negocio separada en interfaz e implementación.
- **Controlador**: Exposición de la API REST.
- **Configuración**: Clase `SwaggerConfig.java` para exponer la documentación OpenAPI.

----------------------------------------
CÓMO EJECUTAR EL PROYECTO
----------------------------------------
1. Clonar el repositorio:
   git clone <url-del-repo>

2. Navegar al directorio del proyecto:
   cd demo

3. Compilar y ejecutar:
   ./mvnw spring-boot:run

4. Swagger UI (Documentación de la API):
   http://localhost:8080/swagger-ui.html

5. Consola H2 (base de datos en memoria):
   http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:testdb
   Usuario: sa
   Contraseña: (vacía)

----------------------------------------
ENDPOINTS PRINCIPALES
----------------------------------------
| Método | Endpoint       | Descripción              |
|--------|----------------|--------------------------|
| GET    | /personas      | Lista todas las personas |
| GET    | /personas/{id} | Obtiene persona por ID   |
| POST   | /personas      | Crea una nueva persona   |

----------------------------------------
MODELO DE PERSONA (JSON)
----------------------------------------
{
"nombre": "Luis",
"apellido": "Soto",
"edad": 35,
"email": "luis@example.com"
}

----------------------------------------
NOTAS
----------------------------------------
- La base de datos es en memoria (H2), se reinicia en cada ejecución.
- El proyecto está diseñado como base para una evaluación técnica o pruebas de API REST.
- Swagger UI se genera automáticamente usando Springdoc OpenAPI Starter.

----------------------------------------
AUTOR
----------------------------------------
Luis M. Soto S.