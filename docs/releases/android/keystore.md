# Configuración de Keystore para Android

Para generar una versión de lanzamiento (Release) firmada de la aplicación Android, el proyecto utiliza una configuración automatizada que lee las credenciales desde el archivo `local.properties`.

## Requisitos de Configuración
Para que el proceso de compilación de Gradle pueda firmar el APK/Bundle, debés definir las siguientes propiedades en tu archivo `local.properties` (ubicado en la raíz del proyecto):

```properties
# Ruta absoluta al archivo .jks o .keystore
release.keystore.path=/ruta/completa/a/tu/archivo.jks

# Contraseña del almacén de claves
release.keystore.password=tu_password_del_keystore

# Alias de la clave
release.keystore.alias=tu_alias

# Contraseña específica de la clave
release.keystore.key.password=tu_password_de_la_clave
```

## Funcionamiento Técnico
El archivo `composeApp/build.gradle.kts` contiene un bloque de lectura automática:
1. Busca el archivo `local.properties`.
2. Carga las propiedades con el prefijo `release.keystore.*`.
3. Configura el bloque `signingConfigs.release` dinámicamente.
4. Aplica esta configuración al `buildType` de `release`.

## Seguridad
⚠️ **Importante:**
*   **Nunca** subas el archivo `.jks` al repositorio.
*   El archivo `local.properties` está excluido de Git por seguridad.
*   Asegurate de tener una copia de respaldo (backup) de tu Keystore en un lugar seguro y privado.

---
Regresar al [Historial de Releases](../history.md) | Regresar al [Índice](../../Index.md)
