# Visión General del Proyecto

El proyecto **CeMeDe** es una herramienta de manejo interno para el **Centro Médico Deportivo del Este**. Su objetivo principal es facilitar la gestión de información tanto para el personal (Staff) como para los socios que asisten al centro.

## Propósito de la Aplicación
La aplicación permite la sincronización y consulta de datos críticos, asegurando que el personal tenga acceso a la información más actualizada sobre horarios, socios y planes de trabajo.

## Funcionalidades Principales

*   **Sincronización de Datos**: Al iniciar (SplashScreen), la aplicación sincroniza la información de los socios y el cronograma de trabajo del staff desde fuentes remotas.
*   **Gestión de Staff**: Listado detallado de profesionales, sus horarios diarios y socios asignados.
*   **Gestión de Socios**: Información clínica detallada, incluyendo diagnósticos, síndromes relacionados y planes de trabajo individuales.
*   **Monitoreo de Conexión**: Detección en tiempo real de la conectividad a internet para informar al usuario sobre la frescura de los datos.

## Estructura del Negocio
El sistema se centra en dos entidades principales:
1.  **StaffMember**: Profesionales del centro.
2.  **Partner**: Socios/Pacientes que reciben tratamiento o entrenamiento.

## Fuente de datos
Actualmente, la fuente de datos principal es un **Google Sheet (Excel)** que se exporta en formato CSV para su consumo:
*   [Enlace al Excel de Gestión](https://docs.google.com/spreadsheets/d/1BU6fuRBEZR08c2sXLyi_9Sp60uphxlGobgfkn0nbrFU/edit)

**Nota a futuro**: Se planea migrar la fuente de datos a una base de datos en **Firebase** para mejorar la escalabilidad, la seguridad y permitir actualizaciones en tiempo real de forma más eficiente.

---
Regresar al [Índice](../Index.md)
