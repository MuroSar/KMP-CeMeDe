# 🍏 Guía de Compilación y Distribución en iOS

Este documento detalla el proceso para compilar, firmar y distribuir la aplicación CeMeDe para dispositivos iOS desde Xcode.

---

## 🛠️ Requisitos Previos
1. **macOS**: Es indispensable contar con una Mac para el proceso de compilación de iOS.
2. **Xcode**: Asegúrate de tener instalada la versión más reciente (se recomienda 16.0 o superior).
3. **Certificados**: Debes tener configurada tu cuenta de Apple ID en Xcode (**Settings -> Accounts**).

---

## 🏗️ Proceso de Compilación (Archive)

Para generar un paquete distribuible, no basta con ejecutar la app en el simulador; se debe realizar un "Archive".

1. **Configuración de la Firma**:
   - Abre el proyecto en Xcode: `iosApp/iosApp.xcodeproj`.
   - Selecciona el proyecto **iosApp** en el navegador de archivos (raíz).
   - Ve a la pestaña **Signing & Capabilities**.
   - Asegúrate de que el **Team** esté seleccionado (ej: "Mauro Sarti").
   - Verifica que el **Bundle Identifier** coincida (`com.cemede.cemede.CeMeDe`).

2. **Limpiar el Proyecto**:
   - En el menú superior de Xcode: **Product -> Clean Build Folder** (o `Shift + Cmd + K`).

3. **Ejecutar el Archive**:
   - Selecciona como destino **Any iOS Device (arm64)** en el selector de dispositivos (barra superior).
   - Ve al menú: **Product -> Archive**.
   - Espera a que el proceso finalice. Si hay errores de `CodeSign`, asegúrate de que el Llavero (Keychain) de tu Mac esté desbloqueado.

---

## 📦 Generación del Archivo .ipa

Una vez finalizado el Archive, se abrirá automáticamente el **Organizer** de Xcode.

1. Selecciona el último Archive generado.
2. Haz clic en el botón azul **Distribute App**.
3. Selecciona el método de distribución:
   - **Development**: Para pruebas internas en dispositivos registrados. (Opción recomendada para desarrollo).
   - **App Store Connect**: Para subirlo a TestFlight o la App Store (Requiere cuenta de desarrollador paga).
   - **Ad Hoc**: Para enviar el .ipa directamente (Requiere cuenta de desarrollador paga y UDIDs registrados).
4. Sigue el asistente de exportación y elige una carpeta de destino.
5. El archivo final se encontrará en una carpeta llamada `Apps` con la extensión `.ipa`.

---

## ⚠️ Consideraciones de Cuentas Gratuitas (Personal Team)

Si utilizas una cuenta gratuita de Apple ID:
* **No puedes generar un .ipa** para enviar por medios externos (WhatsApp, correo, etc).
* La única forma de probar la app en un dispositivo físico es conectándolo por cable a la Mac y ejecutando la app directamente desde Xcode (**Product -> Run**).
* La firma de la aplicación en el dispositivo expira a los **7 días**, tras los cuales deberás volver a instalarla desde Xcode.

---

## 🐞 Solución de Errores Comunes

### Error `errSecInternalComponent` (CodeSign failed)
Este error ocurre cuando Xcode no tiene permiso para acceder a tu clave privada en el Llavero.
* **Solución**: Abre la Terminal y ejecuta:
  ```bash
  security unlock-keychain ~/Library/Keychains/login.keychain
  ```
* O abre **Keychain Access**, busca tu clave privada y en **Control de Acceso**, permite que todas las aplicaciones accedan o añade explícitamente a `/usr/bin/codesign`.

---
Regresar al [Historial de Releases](../history.md)
