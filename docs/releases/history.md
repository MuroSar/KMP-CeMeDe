# Historial de Versiones y Releases

Este documento registra la evolución del proyecto y los hitos alcanzados en cada lanzamiento.

## [1.0.0] - Lanzamiento Inicial (Versión Actual)
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

## 🔑 Gestión de Credenciales y Keys
Para la compilación y firma de releases, se deben tener en cuenta las siguientes consideraciones:

*   **Android**: Los archivos `.jks` y las propiedades de firma deben configurarse en el `local.properties` o mediante variables de entorno en CI/CD. No subir nunca el archivo de almacén de claves al repositorio.
*   **iOS**: El aprovisionamiento se gestiona a través de Xcode (`iosApp.xcodeproj`) mediante perfiles de desarrollo y distribución de Apple.
*   **API Keys**: Actualmente las URLs de Google Sheets son públicas (solo lectura), por lo que no requieren llaves de API adicionales por el momento.

---
Regresar al [Índice](../Index.md)
