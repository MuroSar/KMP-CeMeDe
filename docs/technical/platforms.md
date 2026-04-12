# Soporte de Plataformas

La arquitectura **KMP** de **CeMeDe** permite la ejecución en los siguientes entornos con configuraciones específicas.

## Plataformas Disponibles

| Plataforma | Configuración / Versión Mínima | Herramientas |
| :--- | :--- | :--- |
| **Android** | SDK 24 (Android 7.0) | Android Studio, Gradle |
| **iOS** | iOS 14.1+ | Xcode, Compose UI |
| **Desktop (JVM)** | Java 17+ | Kotlin, Java AWT (para Desktop) |

---

## Particularidades de las Plataformas

### 1. Android
*   Configurado con el archivo `CeMeDeApplication.kt` para inicializar Koin con el contexto de Android.
*   Utiliza `ConnectivityManager` nativo en `NetworkHelper.android.kt`.

### 2. iOS
*   Generación de Framework nativo para ser consumido desde Xcode (`iosApp`).
*   Configuración del `MainViewController` para renderizar la UI de Compose.
*   Implementación de `NetworkHelper.ios.kt` usando `nw_path_monitor`.

### 3. Desktop (JVM)
*   Soporte para abrir URLs externas mediante `java.awt.Desktop`.
*   Implementación de red basada en `InetAddress` y polling para simular la observación del estado de conexión.

---
Regresar al [Índice](../Index.md)
