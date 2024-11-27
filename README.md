# Inmobiliaria API

Esta API está orientada a empresas inmobiliarias para gestionar clientes y propiedades. Permite a los usuarios realizar operaciones sobre clientes, propiedades y realizar búsquedas filtradas por diferentes criterios, como la dirección de las propiedades y su estado (disponible, en reparación, etc.). Además, tiene un sistema de autenticación con JWT para garantizar la seguridad de las operaciones.

## Estado del Proyecto

> **Aviso**: Esta API aún está en desarrollo y algunas funcionalidades pueden no estar completamente implementadas o pueden estar sujetas a cambios. Si estás utilizando la API, ten en cuenta que podrían ocurrir actualizaciones importantes que afecten a los endpoints o a la estructura de los datos.


## Funcionalidades

- **Gestión de Clientes**: Crear, actualizar, eliminar y consultar clientes.
- **Gestión de Propiedades**: Crear, actualizar, eliminar y consultar propiedades.
- **Búsqueda de Propiedades**: Filtrar propiedades por dirección, estado y disponibilidad.
- **Autenticación**: Registro de usuarios y acceso mediante JWT.


## Variables de Entorno

Antes de ejecutar la API, asegúrate de configurar las siguientes variables de entorno para que la aplicación funcione correctamente:

- **`DB_URL`**: La URL de conexión a la base de datos (por ejemplo, `jdbc:mysql://localhost:3306/inmobiliaria`).
- **`DB_USERNAME`**: El nombre de usuario de la base de datos
- **`DB_PASSWORD`**: La contraseña para la base de datos.
- **`SERVER_PORT`**: El puerto en el que se ejecutará el servidor (por defecto, `8080`).

### Ejemplo de archivo `.env`:

```dotenv
DB_URL=jdbc:mysql://localhost:3306/inmobiliaria
DB_USERNAME=root
DB_PASSWORD=yourpassword
SERVER_PORT=8080
```

## Requisitos
Java 17+: La API está desarrollada en Java utilizando Spring Boot.
MySQL: Se utiliza MySQL como sistema de base de datos.
Maven: Para la gestión de dependencias y construcción del proyecto.

## Instalación
Sigue los siguientes pasos para configurar y ejecutar la API en tu máquina local:

### Clona el repositorio:
```dotenv
git clone https://github.com/jmlucer0/inmobiliariaApi
cd inmobiliaria-api
```
Crea tus propias variables de entorno o simplente remplazalas con los datos correspondientes.

### Construye y ejecuta la aplicación:
```dotenv
mvn spring-boot:run
La aplicación debería iniciarse en http://localhost:8080 por defecto.
```
## Pruebas
**Puedes interactuar con la API a través de Swagger UI:**

### Acceso a la API

Para probar la API puedes usar Swagger UI, que proporciona una interfaz fácil de usar para interactuar con los endpoints. Accede a la siguiente URL después de iniciar la aplicación:

[Swagger UI - Inmobiliaria API](http://localhost:8080/swagger-ui/index.html)

Desde la interfaz de Swagger, podrás realizar peticiones GET, POST, PUT, DELETE para interactuar con los diferentes endpoints de la API. Es importante que primero te registres y obtengas un token JWT para poder hacer pruebas autenticadas.