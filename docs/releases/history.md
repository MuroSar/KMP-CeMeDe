# Historial de Versiones y Releases

Este documento registra la evolución del proyecto y los hitos alcanzados en cada lanzamiento.

## [1.1.0] - Incremental con funcionalidades - 30-04-2026
* **Disponibilidad en Main**: Agregar indicador/filtro en la pantalla principal para identificar profesionales con lugares libres en tiempo real.
* **Actualización de estado en Splash**: Agregar mensaje de estado en la splash para visibilidad del usuario
* **Calendario completo:** Implementar vista de calendario extendido dentro del detalle de cada StaffMember.
* **Dinamicidad de la versión:** Configurar la Splash Screen para que obtenga y muestre el `versionCode` (o `versionName`) real definido en el build script, eliminando la dependencia de un string manual.
* **Totalización de listas:** En los listados de staff y de socios, se totaliza la cantidad de elementos listados.

## [1.0.0] - Lanzamiento Inicial - 13-04-2026
* **Soporte Multiplataforma**: Android, iOS y Desktop (JVM).
* **Arquitectura**: Implementación de Clean Architecture con KMP.
* **Sincronización**: Integración con Google Sheets a través de `UrlProvider`.
* **Persistencia**: Base de datos local con Room.
* **UI/UX**: Diseño moderno basado en la identidad visual de CeMeDe, incluyendo banners de estado de red y pantallas de error personalizadas.
* **Monitoreo**: Implementación de `NetworkHelper` para estados offline.
* **Features**:
  * Listado y detalle de Staff
  * Listado y detalle de Socio
  * Comunicación entre detalles de Socio y Staff
  * Documentación técnica del proyecto
  * Seteo de íconos por plataforma (Android Manifest & Xcode Assets)

---

## 🛠️ Guía de Compilación
Para detalles sobre cómo generar versiones firmadas para Android e iOS, consulta la [Guía de Compilación y Credenciales](./compilation.md).

---
Regresar al [Índice](../Index.md)
