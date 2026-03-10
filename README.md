# 🌐 **FOROHUB API REST**

🚀 ForoHub es una API REST desarrollada con Spring Boot que replica el funcionamiento backend de un foro de discusión.
Permite a los usuarios crear, consultar, actualizar y eliminar tópicos mientras protege el acceso mediante autenticación con JWT (JSON Web Tokens).

Este proyecto fue desarrollado como parte del Challenge Back End de Alura, aplicando buenas prácticas de arquitectura, seguridad y diseño de APIs REST.

# 🎯 **OBJETIVO DEL PROYECTO**

Los foros son espacios donde estudiantes y profesionales pueden compartir preguntas, respuestas y conocimientos.

Este proyecto busca replicar el backend de un sistema de foro, permitiendo:

Crear tópicos

Consultar tópicos

Actualizar tópicos

Eliminar tópicos

Proteger los endpoints mediante autenticación segura

# 🛠 **TECNOLOGÍAS UTILIZADAS**

Este proyecto fue construido utilizando un stack moderno de backend:

Tecnología	Descripción
☕ Java 17	Lenguaje principal
🌱 Spring Boot	Framework principal
🔐 Spring Security	Seguridad y control de acceso
🗄 Spring Data JPA	Persistencia de datos
⚙ Hibernate	ORM
🔑 JWT	Autenticación basada en tokens
🐬 MySQL	Base de datos
🛫 Flyway	Migraciones de base de datos
📦 Maven	Gestión de dependencias
📄 Swagger / OpenAPI	Documentación automática de la API
✔ Bean Validation	Validación de datos

# 🧩 **ARQUITECTURA DEL PROYECTO**

El proyecto sigue una arquitectura limpia basada en capas para mantener el código organizado y escalable.

<img width="692" height="643" alt="image" src="https://github.com/user-attachments/assets/1f2a6666-f60f-4641-83cb-5bc5196df26c" />

   
📂 controller

Contiene los controladores REST que exponen los endpoints de la API.

📂 domain

Contiene entidades, DTOs y lógica del dominio.

📂 infra

Incluye configuraciones de seguridad y manejo global de errores.

📂 repository

Interfaces de acceso a datos utilizando Spring Data JPA.

# 🔐 **AUTENTICACIÓN Y SEGURIDAD**

La API utiliza JWT (JSON Web Tokens) para autenticar usuarios.

Flujo de autenticación

1️⃣ El usuario envía sus credenciales al endpoint /login.

2️⃣ El servidor valida las credenciales.

3️⃣ Si son correctas, se genera un token JWT.

4️⃣ El cliente debe enviar este token en cada request protegido.

Ejemplo de header:

Authorization: Bearer TOKEN

La aplicación utiliza Spring Security en modo Stateless, lo que significa que el servidor no mantiene sesiones.

# 📌 **ENDPOINTS PRINCIPALES**

🔑 Autenticación
Login
POST /login

Body:

{
  "login": "usuario",
  "clave": "password"
}

Respuesta:

{
  "token": "jwt_token_generado"
}

# 📚 **ENDPOINTS DE TÓPICOS**

📝 Crear tópico
POST /topicos

Body:

{
  "titulo": "Título del tópico",
  "mensaje": "Contenido del mensaje",
  "curso": "Spring Boot"
}
📖 Listar tópicos
GET /topicos

Soporta paginación:

?page=0&size=10
🔎 Ver detalle de un tópico
GET /topicos/{id}
✏ Actualizar tópico
PUT /topicos/{id}
🗑 Eliminar tópico
DELETE /topicos/{id}

El sistema utiliza borrado lógico, preservando la integridad de los datos.

# ✔ **VALIDACIONES IMPLEMENTADAS**

El sistema incluye múltiples validaciones de negocio:

✔ Campos obligatorios

✔ Validación de formato de datos

✔ Prevención de tópicos duplicados

✔ Control de permisos para modificar recursos

✔ Manejo de errores global

Las validaciones se implementan utilizando Bean Validation.

# 🗄 **BASE DE DATOS**

La aplicación utiliza MySQL para almacenar la información.

Entidades principales:

👤 Usuarios

Usuarios registrados que pueden autenticarse en el sistema.

💬 Tópicos

Publicaciones realizadas dentro del foro.

Las migraciones de la base de datos son gestionadas mediante Flyway, permitiendo versionar cambios estructurales.

# 📑 **DOCUMENTACIÓN DE LA API**


La documentación automática está disponible gracias a Swagger / SpringDoc OpenAPI.

Puedes acceder a la interfaz interactiva en:

http://localhost:8080/swagger-ui/index.html

Desde allí puedes:

Explorar endpoints

Enviar requests

Probar autenticación

# 🚀 **CÓMO EJECUTAR EL PROYECTO**

1️⃣ Clonar el repositorio
git clone https://github.com/Growltore/FinalproyectForoHub.git
2️⃣ Acceder al proyecto
cd FinalproyectForoHub
3️⃣ Configurar la base de datos

Editar el archivo:

application.properties

Ejemplo:

spring.datasource.url=jdbc:mysql://localhost/forohub
spring.datasource.username=root
spring.datasource.password=tu_password

api.security.secret=clave_secreta

4️⃣ Ejecutar la aplicación
mvn spring-boot:run

La aplicación se ejecutará en:

http://localhost:8080

# ⚠ **MANEJO DE ERRORES**

El sistema implementa un manejo global de excepciones mediante:

@RestControllerAdvice

Esto permite devolver respuestas claras ante errores como:

❌ Validaciones incorrectas

❌ Recursos inexistentes

❌ Peticiones inválidas


# 👨‍💻 **AUTOR**

Proyecto desarrollado por:

Cristian Figueroa

Como parte del Challenge Back End de Alura.

# 📜 **LICENCIA**

Este proyecto se distribuye bajo la licencia MIT.

⭐ Si te gustó este proyecto

Puedes apoyar el repositorio con una ⭐ en GitHub.
