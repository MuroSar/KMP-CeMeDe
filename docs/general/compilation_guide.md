# Guía de Compilación y Ejecución

Este proyecto es una aplicación **Kotlin Multiplatform** que apunta a Android, iOS y Desktop (JVM). A continuación se detallan los pasos para compilar y ejecutar el proyecto en cada plataforma.

---

## 1. Android
Para compilar y ejecutar la versión de desarrollo de la aplicación Android, puedes usar el widget de ejecución en tu IDE o hacerlo desde la terminal:

**Terminal (macOS/Linux):**
```shell
./gradlew :composeApp:assembleDebug
```

**Terminal (Windows):**
```shell
.\gradlew.bat :composeApp:assembleDebug
```

---

## 2. Desktop (JVM)
Para compilar y ejecutar la versión de escritorio, usa la configuración de ejecución del IDE o la terminal:

**Terminal (macOS/Linux):**
```shell
./gradlew :composeApp:run
```

**Terminal (Windows):**
```shell
.\gradlew.bat :composeApp:run
```

---

## 3. iOS
Para compilar y ejecutar la versión de iOS:
1.  Usa la configuración de ejecución en tu IDE (si tienes configurado el plugin de KMP).
2.  O abre el directorio `iosApp/` en **Xcode** y ejecútalo directamente desde allí.

---

## Estructura de Módulos
*   **[/composeApp](../composeApp/src)**: Contiene el código compartido y específico de las plataformas para la UI de Compose.
    *   `commonMain`: Código común para todos los targets.
    *   `androidMain`, `iosMain`, `jvmMain`: Código específico por plataforma.
*   **[/iosApp](../iosApp)**: Contiene la aplicación iOS nativa (SwiftUI) que sirve como punto de entrada.

---
Regresar al [Índice](../Index.md)
