# 🤖 Guía de Compilación y Distribución en Android

Este documento detalla el proceso para compilar, firmar y generar los archivos distribuibles (`.apk` y `.aab`) de la aplicación CeMeDe para dispositivos Android.

---

## 🏗️ Modos de Compilación

En Android existen dos formatos principales de salida:
1. **APK (Android Package)**: Formato estándar para instalación directa en dispositivos (ideal para pruebas rápidas).
2. **AAB (Android App Bundle)**: Formato requerido para publicar en Google Play Store.

---

## 🔐 Firmado de la Aplicación (Keystore)

Para que una aplicación pueda instalarse en un dispositivo en modo Release, debe estar firmada digitalmente.

*   **Configuración Detallada**: Consulta la [Guía de Configuración de Keystore](./keystore.md) para aprender a configurar tus llaves de firma de forma segura sin exponer contraseñas en el repositorio.

---

## 🚀 Proceso de Generación desde Android Studio

### 1. Generar un APK de Debug (Pruebas Rápidas)
Si solo necesitas probar la app rápidamente:
1. Ve a la pestaña **Gradle** (derecha de Android Studio).
2. Navega a: `:composeApp -> Tasks -> build -> assembleDebug`.
3. El archivo generado estará en: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`.

### 2. Generar un APK/Bundle de Release (Firmado)
Para una versión final:
1. En el menú superior: **Build -> Generate Signed Bundle / APK...**.
2. Selecciona **Android App Bundle** (para Play Store) o **APK** (para distribución directa).
3. Selecciona tu archivo **Keystore**, ingresa el **Alias** y las **Contraseñas** (ver [Keystore Guide](./keystore.md)).
4. Selecciona la variante de destino: `release`.
5. Haz clic en **Finish**.

---

## 💻 Generación desde Línea de Comandos (Terminal)

Puedes generar las versiones de release directamente desde la terminal situada en la raíz del proyecto:

### Para generar el APK de Release:
```bash
./gradlew :composeApp:assembleRelease
```

### Para generar el App Bundle (AAB):
```bash
./gradlew :composeApp:bundleRelease
```

> **Nota**: Los archivos resultantes se encontrarán en:
> * **APK**: `composeApp/build/outputs/apk/release/`
> * **AAB**: `composeApp/build/outputs/bundle/release/`

---

## 📦 Instalación en Dispositivo

1. **Vía ADB**: Si tienes el dispositivo conectado por USB:
   ```bash
   adb install composeApp/build/outputs/apk/release/composeApp-release.apk
   ```
2. **Instalación Directa**: Puedes enviar el archivo `.apk` por cualquier medio (WhatsApp, Drive, etc.) al dispositivo. Asegúrate de que el dispositivo tenga habilitada la opción "Instalar aplicaciones de fuentes desconocidas".

---
Regresar al [Historial de Releases](../history.md)
