# 🤖 Guía de Compilación y Distribución en Android

Este documento detalla el proceso para compilar, firmar y generar los archivos distribuibles (`.apk` y `.aab`) de la aplicación CeMeDe para dispositivos Android.

---

## 🔐 Firmado de la Aplicación (Automatizado)

El proyecto está configurado para firmar las versiones de **Release** automáticamente utilizando las credenciales definidas en su entorno local.

*   **Configuración Obligatoria**: Antes de compilar, debés configurar tu archivo `local.properties`. Consulta la [Guía de Configuración de Keystore](./keystore.md) para conocer las variables necesarias.

---

## 🏗️ Modos de Compilación y Comandos

Existen dos formatos principales de salida según el destino de la aplicación:

### 1. APK (Android Package)
Ideal para instalación directa en dispositivos o pruebas rápidas.
*   **Debug**: `./gradlew :composeApp:assembleDebug`
*   **Release (Firmado)**: `./gradlew :composeApp:assembleRelease`
*   **Ubicación**: `composeApp/build/outputs/apk/[debug|release]/`

### 2. AAB (Android App Bundle)
Formato requerido para publicar en **Google Play Store**.
*   **Comando**: `./gradlew :composeApp:bundleRelease`
*   **Ubicación**: `composeApp/build/outputs/bundle/release/`

---

## 🚀 Proceso desde Android Studio

Si preferís usar la interfaz visual en lugar de la terminal:
1. Abrí la pestaña lateral de **Gradle** (extremo derecho).
2. Navegá a `:composeApp -> Tasks -> build`.
3. Ejecutá la tarea correspondiente (`assembleRelease` o `bundleRelease`).

> **Nota**: Gracias a la automatización, ya no es necesario utilizar el asistente manual `Build -> Generate Signed Bundle / APK...` siempre que el archivo `local.properties` esté configurado.

---

## 📦 Instalación en Dispositivo

Si tenés el dispositivo conectado por USB y el modo depuración activado:
```bash
adb install composeApp/build/outputs/apk/release/composeApp-release.apk
```

---
Regresar al [Historial de Releases](../history.md) | Regresar al [Índice](../../Index.md)
