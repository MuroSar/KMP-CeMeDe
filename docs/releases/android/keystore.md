# Configuración de Keystore para Android

Para generar una versión de lanzamiento (Release) firmada de la aplicación Android, es necesario configurar el almacén de claves (keystore).

## Requisitos
Para que el proceso de compilación de Gradle pueda firmar el APK/Bundle, se deben definir las siguientes propiedades:

*   **storeFile**: Ruta al archivo `.jks` o `.keystore`.
*   **storePassword**: Contraseña del almacén de claves.
*   **keyAlias**: Alias de la clave.
*   **keyPassword**: Contraseña de la clave específica.

## Configuración Recomendada
Se recomienda no incluir estas credenciales directamente en los archivos de Gradle del proyecto. En su lugar, utilice una de las siguientes opciones:

### 1. Archivo `local.properties`
Agregue las siguientes líneas a su archivo `local.properties` (que está excluido de Git):

```properties
release.keystore.path=/ruta/a/tu/archivo.jks
release.keystore.password=tu_password_del_almacen
release.keystore.alias=tu_alias
release.keystore.key.password=tu_password_de_la_clave
```

### 2. Variables de Entorno (CI/CD)
Si utiliza un sistema de integración continua (como GitHub Actions), configure estas propiedades como *Secrets*:

*   `ANDROID_KEYSTORE_BASE64`: El archivo keystore codificado en Base64.
*   `ANDROID_KEYSTORE_PASSWORD`
*   `ANDROID_KEY_ALIAS`
*   `ANDROID_KEY_PASSWORD`

## Seguridad
⚠️ **Nunca suba el archivo `.jks` ni las contraseñas al repositorio de Git.** Asegúrese de que el archivo esté debidamente respaldado en un lugar seguro.

---
Regresar al [Historial de Releases](../history.md) | Regresar al [Índice](../../Index.md)
