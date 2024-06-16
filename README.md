```markdown
### Tecnologías Utilizadas

- HTML/CSS/BOOTSTRAP: Para la estructura y diseño de la interfaz.
- JavaScript: Para la funcionalidad de Stripe.
- Java: Para la lógica del backend.
- Spring Boot: Para la creación del backend en Java.

---

## Requisitos del Sistema

Para ejecutar este proyecto, asegúrate de tener instalados los siguientes componentes:

1. JDK 8 o superior
2. Visual Studio Code
3. Java Development Kit (JDK) para Visual Studio Code
4. WAMP o XAMPP

### Dependencias

Las dependencias necesarias están listadas en el archivo `pom.xml`.

---

## Instalación

Sigue estos pasos para instalar y ejecutar el proyecto en tu máquina local:

### Clonar el Repositorio

```bash
git clone https://github.com/Jordycamacho/Ecommerce-spring-boot-v1.git
```

### Configuración

1. Configura el archivo `application.properties`:

   Abre el archivo `application.properties` y modifica las siguientes propiedades si es necesario:

   ```properties
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```

2. Asegúrate de tener instalado Visual Studio Code con el Java Development Kit.

### Configuración de la Base de Datos

1. Configura tu base de datos SQL:
   - Si estás usando XAMPP, asegúrate de que el servicio MySQL está corriendo.
   - Si estás usando una base de datos remota, asegúrate de tener la URI de conexión correcta en el archivo `application.properties`.

### Ejecutar el Servidor

1. Inicia el servidor:

   Abre Visual Studio Code, navega hasta la carpeta del proyecto y ejecuta el proyecto.

2. Accede a la aplicación:

   Abre tu navegador y dirígete a la URL `http://localhost:8080`.

---
