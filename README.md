#microservicio Reglas Torneo

Este microservicio se encarga de registrar  todas las reglas de un torneo, ya sea un minimo de jugadores requerido o activar un AntiCheat,
tiene conexion com Juego, Torneo y Auditoria, y se encarga de validar de que dichas reglas se cumplan.  
puede hacer cambios como guardar, crear , actualizar o eliminar y se guardara en la base de datos para tener un historial de dichos cambios.


## Dependencias

* Spring Web
* Validation
* Spring Data JPA
* OpenFeign
* MySQL Driver
* Lombok

