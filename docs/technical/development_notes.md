# Particularidades del Desarrollo y Notas Técnicas

El desarrollo de **CeMeDe** presenta varias particularidades que son cruciales para el mantenimiento y escalabilidad del proyecto.

## 1. Manejo de Conectividad (NetworkHelper)
Dada la naturaleza crítica de los datos (Información Clínica), la aplicación implementa un sistema de monitoreo de red multiplataforma:
*   **Android**: Basado en `ConnectivityManager`.
*   **iOS**: Basado en `NWPathMonitor`.
*   **Desktop**: Basado en polling síncrono para mayor robustez en entornos de escritorio.
*   **Comportamiento en UI**: El banner `NoInternetConnectionBanner` se muestra automáticamente en las pantallas principales si el estado de red cambia a `false`.

## 2. Sincronización Paralela (SplashScreen)
Para optimizar el tiempo de carga inicial, la sincronización de socios y cronogramas se realiza en paralelo utilizando `async` y `await` de Corrutinas en `SplashViewModel`.

## 3. Manejo de Errores y Estados Vacíos
Se han diseñado componentes específicos para mejorar la experiencia de usuario (UX):
*   `CemedeErrorState`: Pantalla de error con diseño moderno y botón de reintento.
*   `CemedeEmptyState`: Para listas vacías o resultados de búsqueda no encontrados.
*   `CemedeBanner`: Notificaciones en la parte inferior de la pantalla (Construcción, Sin Internet).

## 4. Persistencia Local (Room)
Toda la información sincronizada se almacena localmente. Esto permite que la aplicación funcione en modo lectura incluso si no hay conexión a internet tras la primera sincronización.

## 5. Proveedor de URLs (UrlProvider)
La clase `UrlProvider` centraliza todas las direcciones de las fuentes de datos externas (actualmente basadas en Google Sheets exportados como CSV). 
*   **Centralización**: Permite cambiar rápidamente las fuentes de datos sin modificar la lógica de los repositorios.
*   **Mapeo de Profesionales**: Contiene diccionarios para vincular a cada profesional con su respectiva hoja de cálculo de horarios y planes de trabajo.
*   **Escalabilidad**: Facilita la adición de nuevos profesionales o nuevas secciones de datos simplemente añadiendo una nueva entrada a los mapas correspondientes.

Nota: El Google Sheet está configurado para que con cada ajuste se publiqué automaticamente una nueva versión a las URLs dadas.  

## 6. Diseño Visual
El sistema visual utiliza una paleta de colores centrada en el **verde oliva** y **dorado**, evocando una sensación de salud, deporte y profesionalismo. Se utilizan fuentes modernas (`Public Sans`) y componentes de `Material 3`.

## 7. Gestión de Versiones Multiplataforma
Para mantener la consistencia entre las versiones de Gradle y los binarios finales de cada plataforma, el proyecto utiliza tareas automáticas de generación de código/configuración:

*   **`generateIosVersionConfig`**: Crea el archivo `Version.xcconfig` en la carpeta `iosApp`. Este archivo mapea la versión definida en Gradle (`MARKETING_VERSION_GRADLE`) para que Xcode la utilice automáticamente al compilar, evitando discrepancias entre plataformas.
*   **`generateJvmVersionConfig`**: Genera un objeto Kotlin llamado `BuildInfo` (en el sourceSet de Desktop). Esto permite que la versión de la aplicación sea accesible programáticamente en la versión de escritorio, facilitando su visualización en menús de "Acerca de" o logs.
*   **Automatización**: Estas tareas están integradas en el ciclo de vida de Gradle mediante `dependsOn`, por lo que se ejecutan automáticamente durante el proceso de build.

---
Regresar al [Índice](../Index.md)
