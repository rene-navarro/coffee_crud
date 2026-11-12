# coffee_crud

Aplicación de escritorio didáctica para explorar operaciones de **consulta, inserción, modificación y eliminación (CRUD)** sobre una base de datos PostgreSQL utilizando **Java Swing, JDBC y Gradle**. Este documento describe el código entregado; algunas funciones de la interfaz están incompletas y no se debe interpretar el proyecto como una implementación CRUD integral de todas las tablas.

> **Seguridad:** el repositorio contiene un archivo `coffee_crud.properties` con credenciales en texto plano. No publique esas credenciales. Cámbielas si son reales y utilice un archivo local ignorado por Git o variables de entorno. No se reproducen aquí.

## Tecnologías

- Java (se inspeccionó el código con un entorno OpenJDK 21 disponible, sin afirmar compatibilidad certificada).
- Swing: `JFrame`, `JDialog`, `JDesktopPane`, `JInternalFrame`, `JTable` y modelos de tabla.
- JDBC y PostgreSQL; dependencia `org.postgresql:postgresql:42.7.14` declarada en `build.gradle`.
- Gradle con wrapper; JUnit Jupiter 5.8.1 declarado para pruebas, sin pruebas fuente en el ZIP.

## Estructura relevante

```text
coffee_crud/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── gradle/wrapper/
├── coffee_crud.properties       # configuración local: contiene secretos en el ZIP
└── src/main/java/mx/unison/coffees/
    ├── MainApp.java
    ├── TableBrowser.java
    ├── CoffeesTableListener.java
    ├── InsertCoffeesDialog.java
    ├── InsertHouses.java
    ├── DeleteCoffeeDialog.java
    ├── DeleteCoffeeHousesDialog.java
    ├── DeleteProveedorDialog.java
    ├── DeleteSuppliersDialog.java
    └── db/
        ├── Database.java
        └── JDBCTableAdapter.java
```

## Clases y responsabilidades

| Clase | Función observada |
|---|---|
| `MainApp` | Ventana principal JFrame, menús, inicialización de conexión y apertura de consultas y diálogos. |
| `TableBrowser` | JInternalFrame que presenta un TableModel mediante JTable. |
| `CoffeesTableListener` | Escucha cambios de celdas y emite UPDATE sobre coffees. |
| `InsertCoffeesDialog` | Formulario de inserción de cafés; consulta proveedores y construye INSERT. |
| `InsertHouses` | Formulario Swing para cafeterías; la lógica de persistencia no está implementada en el manejador observado. |
| `DeleteCoffeeDialog` | Selecciona y elimina cafés por nombre. |
| `DeleteCoffeeHousesDialog` | Selecciona cafeterías y elimina por STORE_ID. |
| `DeleteProveedorDialog` | Elimina proveedores mediante identificador introducido. |
| `DeleteSuppliersDialog` | Selecciona y elimina proveedores por nombre. |
| `Database` | Singleton JDBC: lee propiedades, abre Connection y ejecuta SQL mediante Statement. |
| `JDBCTableAdapter` | Adapta ResultSet a DefaultTableModel; obtiene metadatos y permite editar columnas distintas de la primera. |

## Flujo de funcionamiento

1. `MainApp.main()` inicia la interfaz en el hilo de eventos de Swing.
2. `MainApp` obtiene `Database.getDatabase()`, que lee `coffee_crud.properties` desde el directorio de trabajo y abre una conexión JDBC.
3. Las opciones del menú ejecutan consultas sobre `coffees`, `suppliers`, `coffee_houses` y `cof_inventory` y presentan los resultados en `TableBrowser`.
4. `JDBCTableAdapter` carga los datos y metadatos del `ResultSet` a un `DefaultTableModel`. En la vista de cafés se registra `CoffeesTableListener`, que intenta persistir ediciones.
5. Los formularios de inserción/eliminación ejecutan SQL mediante `Database.update()`; no existe una capa DAO o servicio independiente.

### Tablas y columnas mencionadas en el código

- `coffees`: `COF_NAME`, `SUP_ID`, `PRICE`, `SALES`, `TOTAL`.
- `suppliers`: `SUP_ID`, `SUP_NAME`, `STREET`, `CITY`, `STATE`, `ZIP`.
- `coffee_houses`: `STORE_ID`, `CITY`, `COFFEE`, `MERCH`.
- `cof_inventory`: `WAREHOUSE_ID`, `SUP_ID`, `QUAN`, `DATE_VAL`.

**Nota:** el ZIP no incluye un script SQL de creación o carga de datos. Los nombres anteriores se infieren de las consultas y no constituyen una definición completa del esquema, claves o restricciones.

## Configuración y ejecución

1. Instale un JDK compatible y PostgreSQL; cree una base de datos y las tablas esperadas mediante un esquema que coincida con las consultas (el esquema no se incluye).
2. Configure `coffee_crud.properties` en el directorio desde el que se ejecute el programa. Ejemplo **sin credenciales reales**:

```properties
DRIVER=org.postgresql.Driver
DB_URL=jdbc:postgresql://localhost:5432/coffeesdb
DB_USER=USUARIO_LOCAL
DB_PASSWORD=CLAVE_LOCAL
```

3. Desde la raíz del proyecto, compile:

```bash
./gradlew classes       # macOS/Linux
# gradlew.bat classes   # Windows
```

4. Ejecute la clase principal. El proyecto no declara el plugin Gradle `application`, por lo que **`./gradlew run` no está configurado**. Puede ejecutar `mx.unison.coffees.MainApp` desde IntelliJ IDEA, seleccionando el módulo y directorio de trabajo raíz. Alternativamente, agregue el plugin `application` y configure `mainClass = 'mx.unison.coffees.MainApp'` para habilitar `run`.

5. Para pruebas, cuando se incorporen clases de prueba:

```bash
./gradlew test
```

## Comportamiento y limitaciones

- La edición de celdas de `coffees` construye SQL dinámico y requiere correcciones antes de utilizarse con entradas no confiables.
- El adaptador JDBC presupone `ResultSet` desplazables (`last()`/`beforeFirst()`) y transforma todas las columnas a cadenas.
- La carga de datos y consultas SQL se realizan en manejadores de Swing, por lo que pueden bloquear la interfaz.
- `InsertHouses` presenta formulario, pero no se identificó una operación JDBC de inserción implementada en esa clase.
- La aplicación depende de una conexión y de tablas existentes; no hay migraciones ni datos de ejemplo incluidos.
- Las operaciones no cuentan con pruebas automatizadas en el código fuente proporcionado.

