# Taller-Monolito-Modular

## Arquitectura Monolitica

Este proyecto presenta dos implementaciones de una arquitectura monolítica para un sistema de gestión de pedidos, productos, usuarios y pagos. A continuación, se explican las diferencias entre los dos proyectos: **monolito-acoplado** y **monolito-modular**.

### Monolito Acoplado (`monolito-acoplado/`)

- **Estructura**: Toda la lógica de negocio, persistencia y gestión de los datos la contiene una sola clase principal llamada `GestionSistema.java`. Las clases `Pedido`, `Producto`, `Usuario` y `Persistencia` están en el mismo paquete, pero sin tener una separación clara de responsabilidades.

- **Características**:
  - Acoplamiento alto: Las clases dependen directamente unas de otras sin tener interfaces o abstracciones entre ellas.
  - No muy factible de mantener: Si se realizan cambios en una funcionalidad estos pueden afectar otras partes del sistema.
  - Persistencia múltiple: La clase `Persistencia` maneja la carga y el guardado de datos de todos las clases directamente en los archivos de texto.
  - Ejecución en uns solo punto: Todo se gestiona desde `GestionSistema`, lo que facilita entenderlo al principio pero hace dificil la escalabilidad.

- **Ventajas**: Simplicidad comenzando, fácil de entender si se queda como un proyecto pequeño.

- **Desventajas**: Difícil de extender, probar y mantener a medida va creciendo el proyecto. No hay separación de responsabilidades.

### Monolito Modular (`monolito-modular/`)
- **Estructura**: El código se organiza en módulos separados por dominio, en este caso son: `pedidos/`, `productos/`, `usuarios/`, `pagos/`, cada uno con subpaquetes para `model/`, `persistence/` y `service/`. Se utilizan interfaces para desacoplar las dependencias.

- **Características**:
  - Acoplamiento bajo: Cada módulo tiene su propia lógica de negocio, persistencia y servicios, con y las dependencias se manejan a través de interfaces (como por ejemplo: `IPedidoService`, `IProductoService`).

  - Separación de responsabilidades: 
    - `model/`: Contiene las entidades de datos.
    - `persistence/`: Maneja la carga y guardado de datos.
    - `service/`: Implementa la lógica de negocio.
  - Manejo de dependencias: Los servicios se construyen con dependencias externas-
  - Persistencia modular: Cada módulo tiene su propia clase de persistencia, pero comparte archivos de datos.

- **Ventajas**: Más mantenible, escalable y testable. Mejora la legibilidad del código.

- **Desventajas**: Mayor complejidad inicial por la estructura de paquetes e interfaces.

### Comparación 
- **Escalabilidad**: El monolito modular es más escalable, ya que permite modificar o extender módulos individuales sin afectar otros.

- **Pruebas**: En el modular, es más fácil probar unidades individuales gracias al manejo de dependencias.

- **Mantenimiento**: El acoplado es adecuado para proyectos rápidos, mientras que el modular es mejor para proyectos que evolucionan.

- **Arquitectura**: Ambos son monolitos, pero el modular lo prepara para manejar arquitecturas más complejas como las distribuidas.

Para ejecutar cualquiera de los proyectos, navega al directorio correspondiente y usa `mvn compile exec:java -Dexec.mainClass="co.edu.uptc.monolito.App"`o hazlo desde App.java.