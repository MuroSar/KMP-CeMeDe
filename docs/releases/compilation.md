# 🏗️ Guía de Compilación y Credenciales

Este documento centraliza la información necesaria para generar versiones finales de la aplicación en las distintas plataformas, así como la gestión de llaves y certificados.

## 🔑 Gestión de Credenciales y Keys
Para la compilación y firma de releases, se deben tener en cuenta las siguientes consideraciones:

*   **Android**: Para detalles sobre la generación de APKs, Bundles y firmado en Android, consulta la [Guía de Compilación de Android](android/android_release_guide.md).
*   **iOS**: Para detalles sobre la compilación, firmado y distribución de la aplicación en iOS, consulta la [Guía de Compilación de iOS](ios/ios_release_guide.md).
*   **API Keys**: Actualmente las URLs de Google Sheets son públicas (solo lectura), por lo que no requieren llaves de API adicionales por el momento.

## 🤖 Automatización del Build
El proyecto utiliza tareas de Gradle para asegurar que la numeración de versiones sea consistente entre todas las plataformas:

*   **Sincronización de Versión**: No es necesario editar manualmente la versión en Xcode o en el código de Desktop. Las tareas `generateIosVersionConfig` y `generateJvmVersionConfig` se encargan de propagar la versión definida en el `build.gradle.kts` principal.
*   Para más detalles técnicos sobre cómo funciona esta automatización, consulta las [Notas de Desarrollo](../technical/development_notes.md#7-gestión-de-versiones-multiplataforma).

---
Regresar al [Historial de Releases](./history.md) | Regresar al [Índice](../Index.md)
