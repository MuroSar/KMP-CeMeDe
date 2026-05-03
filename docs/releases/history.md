# Historial de Versiones y Releases

Este documento registra la evolución del proyecto y los hitos alcanzados en cada lanzamiento.

## [1.2.0] - Seguridad, Automatización y UX - XX-XX-2026
* **Optimización de Scroll en Detalle de Staff**: Implementación de lógica de scroll inteligente y condicional en `StaffMemberDetailScreen`. Ahora la aplicación detecta automáticamente si el horario próximo es visible o si requiere un desplazamiento suave, mejorando la UX al abrir el perfil del profesional.
* **Eliminación de Splash Nativa**: Optimización de los puntos de entrada nativos para eliminar la sensación de "doble pantalla" y asegurar un arranque instantáneo con la identidad visual de la marca.
    * **Android**: Implementación de `androidx.core:core-splashscreen` configurada con el color de la marca (`#72775F`) y logo. Esto evita el fondo negro/logo por defecto de Android 12+ y proporciona feedback visual inmediato antes de entrar al Splash de Compose.
    * **iOS**: Configuración del `LaunchScreen` vía `Info.plist` con un Color Set sólido (`LaunchBackground`) coincidente con la marca, eliminando cualquier delay visual o pantalla en blanco al iniciar.
* **Configuración Automatizada del Keystore**: Implementación de lectura de credenciales de firmado desde `local.properties` para automatizar la generación de APKs y AABs firmados en Android, eliminando la necesidad de completar el asistente manual de Android Studio.
* **Actualización de Documentación**: Reestructuración de las guías de release y keystore para reflejar el nuevo flujo de trabajo simplificado.
* **Implementación de Unit Tests**: Cobertura exhaustiva de pruebas unitarias para asegurar la estabilidad y calidad de la lógica de negocio mediante el uso de Mockative y Turbine.
    * **ViewModels**: Validación de estados de UI y flujos de datos reactivos.
    * **UseCases**: Pruebas de lógica de negocio y orquestación de datos.
    * **Repositories**: Verificación de la integración y mapeo de datos de dominio.
    * **DataSources**: Validación de fuentes de datos externas (Ktor/CSV) y locales.
    * **DataBase**: Pruebas de persistencia, consultas y transacciones con Room.
    * **Mappers**: Verificación de transformaciones de modelos entre las diferentes capas de la arquitectura.
    * **Utils**: Validación de utilidades de ayuda, formateo y lógica auxiliar (ej. `DateTimeHandler`).

## [1.1.1] - Hotfix iOS - 02-05-2026
* **Ejecución en dispositivos físicos iOS**: Ajustes en la configuración del framework y scripts de build para permitir la instalación y ejecución en dispositivos Apple reales (no solo simuladores).

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
