# Remote Trainer

## Introduccion

Muchos entrenadores personales tienen la modalidad de enseñar a la distancia, dejando sus rutinas por escrito, las cuales después los clientes siguen. De acá surgen varios problemas:
- Las personas se olvidan de cómo son los ejercicios.
- Las rutinas pueden perderse si es por chat, escrito, etc.
- El profesor tiene que explicar muchas veces un mismo ejercicio, y a veces se puede volver difícil hacerlo vía chat.
- Tenemos el mismo problema para seguir el progreso.

Ante la necesidad de tener dichos puntos centralizados y organizados, se presenta la idea de desarrollar una aplicación mobile.

## Idea de la aplicacion

La aplicación tiene tres secciones principales:
- Rutina
- Seguimiento de peso
- Progreso

Adicionalmente, y como varios entrenadores personales recomiendan dietas o bien trabajan con profesionales de nutrición, se puede extender la funcionalidad de la aplicación agregando la misma funcionalidad para lo mencionado.
- Plan dietario

Por último, siendo profesor o nutricionista, uno podría querer hacer seguimiento del progreso de sus alumnos. Para ello se podría extender aún más la funcionalidad de la aplicación, agregando una sección de registro de alumnos o clientes.
- Seguimiento clientes


## Arquitectura de la aplicacion
La aplicacion esta hecha en Kotlin full stack con Spring.
Se pueden encontrar distintos paquetes que representan las distintas capas:
- Model
- Persistence
- Application
- - Service
- - Controller
