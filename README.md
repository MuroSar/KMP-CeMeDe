# Proyecto CeMeDe (Centro Médico Deportivo del Este)

Este es un proyecto **Kotlin Multiplatform** multiplataforma diseñado para la gestión interna del centro médico **CeMeDe**. 

El proyecto apunta a las siguientes plataformas:
*   **Android** (SDK 24+)
*   **iOS** (14.1+)
*   **Desktop (JVM)** (Java 17+)

---

## 📚 Documentación Técnica

Para una comprensión profunda de la arquitectura, tecnologías y guías de desarrollo, por favor consulta el índice de documentación:

👉 **[Índice de Documentación](./docs/Index.md)**

Dentro de la carpeta `docs/` encontrarás información detallada sobre:

### 📂 General
1. [Visión General](./docs/general/overview.md)
2. [Guía de Compilación](./docs/general/compilation_guide.md)
3. [Roadmap y Pendientes](./docs/Roadmap.md)

### 🏗️ Arquitectura
4. [Diseño de Arquitectura](./docs/architecture/architecture_design.md)
5. [Stack Tecnológico](./docs/architecture/technology_stack.md)

### 🛠️ Técnico
6. [Soporte de Plataformas](./docs/technical/platforms.md)
7. [Notas de Desarrollo](./docs/technical/development_notes.md)

## 🚀 Releases
8. [Guía de Compilación y Credenciales](./docs/releases/compilation.md)
9. [Historial de Releases y Keys](./docs/releases/history.md)

---

## 🚀 Inicio Rápido (Compilación)

### Android
```shell
./gradlew :composeApp:assembleDebug
```

### Desktop (JVM)
```shell
./gradlew :composeApp:run
```

### iOS
Abre el directorio `./iosApp` en **Xcode** y ejecútalo desde allí.

---

Para más información, consulta la [Guía de Compilación detallada](./docs/general/compilation_guide.md).
