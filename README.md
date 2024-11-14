# Sistema de Inventario (En la rama Master) 

 
Este proyecto es un sistema de inventario básico desarrollado con Spring Boot y Thymeleaf. Su propósito es permitir la gestión de productos, especialmente útil para pequeñas empresas que buscan llevar un control de su inventario. La aplicación incluye funcionalidades CRUD para los productos, control de stock bajo y generación de reportes en PDF.

## Características

CRUD de Productos: Crear, leer, actualizar y eliminar productos.
Control de Stock: Alerta de productos con stock bajo.
Generación de Reportes PDF: Genera un reporte en PDF de los productos en inventario.
Interfaz Administrativa: Interfaz de usuario con Thymeleaf para la administración de productos.

## Tecnologías Utilizadas

- Java 11 (o superior)
- Spring Boot 2.x
- Spring Data JPA para la interacción con la base de datos
- H2 Database para la base de datos en memoria (configurable a otra base de datos como MySQL o PostgreSQL)
- Thymeleaf para la generación de las vistas HTML
- Lombok para simplificar la escritura de código en las entidades (opcional)
- JasperReports o iText para la generación de reportes en PDF

## Requisitos Previos

- Java 11 (o superior)
- Maven para la gestión de dependencias


## 1. Configuración y Ejecución del Proyecto
Clona el repositorio: 
git clone https://github.com/tu_usuario/sistema-inventario.git
cd sistema-inventario

## 2. Configura la Base de Datos (Opcional):
   - spring.datasource.url=jdbc:h2:mem:testdb
   - spring.datasource.driverClassName=org.h2.Driver
   - spring.datasource.username=sa
   - spring.datasource.password=password
   - spring.jpa.hibernate.ddl-auto=update
## 3. Ejecuta la aplicación

## Endpoints Principales
Método HTTP	Endpoint	Descripción
GET	/api/productos	Listar todos los productos
POST	/api/productos	Crear un nuevo producto
GET	/api/productos/{id}	Obtener detalles de un producto
PUT	/api/productos/{id}	Actualizar un producto
DELETE	/api/productos/{id}	Eliminar un producto
GET	/api/productos/low-stock	Listar productos con stock bajo
GET	/api/productos/reportePDF	Generar reporte PDF de inventario

## Interfaz de Usuario
La interfaz administrativa está diseñada para la gestión de productos y permite realizar todas las operaciones CRUD de forma intuitiva. Las páginas principales son:
* Lista de Productos: Muestra todos los productos, con opciones para editar y eliminar.
* Formulario de Producto: Permite agregar y editar productos.
* Confirmación de Eliminación: Solicita confirmación para eliminar un producto.

## Generación de Reportes
La aplicación permite la generación de un reporte de inventario en formato PDF mediante el endpoint /api/productos/reportePDF. Puedes personalizar el formato de este reporte utilizando JasperReports o iText.
