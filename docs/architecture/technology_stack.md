# Stack Tecnológico

El proyecto **CeMeDe** aprovecha el ecosistema de **Kotlin Multiplatform (KMP)** para compartir la mayor cantidad de código posible entre plataformas.

## Tecnologías Core
*   **Lenguaje**: [Kotlin 2.0.21](https://kotlinlang.org/) (usando el plugin de Compose 2.3.10).
*   **UI Framework**: [Compose Multiplatform 1.10.2](https://www.jetbrains.com/lp/compose-multiplatform/).
*   **Dependency Injection**: [Koin 4.1.1](https://insert-koin.io/) con soporte para Compose Multiplatform y ViewModels.

## Librerías y Herramientas

### Capa de Datos (Data)
*   **Persistencia Local**: [Room 2.8.4](https://developer.android.com/kotlin/multiplatform/room).
*   **Networking**: [Ktor 3.4.1](https://ktor.io/) con cliente Darwin (iOS), OkHttp (Android) y Java (JVM).
*   **Serialización**: [Kotlinx Serialization 1.10.0](https://github.com/Kotlin/kotlinx.serialization).

### Capa de Presentación (Presentation)
*   **ViewModel & Lifecycle**: [AndroidX Lifecycle 2.9.6](https://developer.android.com/jetpack/androidx/releases/lifecycle) (Versión multiplataforma).
*   **Navegación**: [Navigation Compose Multiplatform 2.9.2](https://www.jetbrains.com/lp/compose-multiplatform/).
*   **Imágenes**: [Coil 3.0.0-rc01](https://coil-kt.github.io/coil/compose/).
*   **Animaciones Lottie**: [Compottie 2.1.0](https://github.com/alexzhirkevich/compottie).

### Utilidades (Utilities)
*   **Corrutinas**: [Kotlinx Coroutines 1.10.2](https://github.com/Kotlin/kotlinx.coroutines).
*   **Fechas y Tiempo**: [Kotlinx Datetime 0.7.1](https://github.com/Kotlin/kotlinx-datetime).

---
Regresar al [Índice](../Index.md)
