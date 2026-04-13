package co.edu.uptc.monolito;

import java.util.ArrayList;
import java.util.List;

public class GestionSistema {

    public List<Usuario> usuarios = new ArrayList<>();
    public List<Pedido> pedidos = new ArrayList<>();
    public List<Producto> productos = new ArrayList<>();
    private Persistencia persistencia = new Persistencia();

    public GestionSistema() {
        this.productos = persistencia.cargar();
        this.usuarios = persistencia.cargarUsuarios();
    }
    
    public void crearUsuario(int id, String nombre, double saldo) {
        Usuario nuevo = new Usuario(id, nombre, saldo);
        usuarios.add(nuevo);
        persistencia.guardarUsuario(nuevo);
        System.out.println("Usuario creado y persistido" );
    }
    

    public void eliminarUsuario(int id) { // aquí mismo borramos sus pedidos.
        usuarios.removeIf(usuario -> usuario.id == id);
        pedidos.removeIf(pedido -> pedido.idUsuario == id);
        System.out.println("Usuario e historial de pedidos eliminados.");
    }

    public void realizarPedido(int idPedido, int idProducto, int idUsuario, int tipoEstado) {
        Producto productoEncontrado = null;
        for (Producto p : productos) {
            if (p.id == idProducto) {
                productoEncontrado = p;
                break;
            }
        }

        if (productoEncontrado == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        double total = productoEncontrado.precio;
        String descripcion = productoEncontrado.nombre;

        for (Usuario u : usuarios) {
            if (u.id == idUsuario) {
                if (u.saldo >= total) {
                    String estado;
                    
                    // Estado del pedido acoplado.
                    if (tipoEstado == 1) {
                        estado = "PENDIENTE";
                    } else if (tipoEstado == 2) {
                        estado = pagarPedido(idPedido, idUsuario, "PAGADO"); 
                    } else {
                        estado = "DESCONOCIDO";
                    }

                    Pedido p = new Pedido(idPedido, descripcion, idUsuario, total, estado);
                    
                    u.saldo -= total; 
                    persistencia.actualizarUsuario(u); // Persistir el cambio de saldo
                    pedidos.add(p);
                    System.out.println("Pedido creado con estado: " + estado + " para el producto: " + descripcion);
                } else {
                    System.out.println("Señor(a) " + u.nombre + ", su saldo es insuficiente.");
                }
                return;
            }
        }
        System.out.println("Usuario no encontrado.");
    }

    public void actualizarEstadoPedido(int idPedido, String nuevoEstado) {
        for (Pedido p : pedidos) {
            if (p.id == idPedido) {
                p.estado = nuevoEstado;
                System.out.println("El estado del pedido " + idPedido + " fue actualizado a " + nuevoEstado);
            }
        }
    }

    public String pagarPedido(int idPedido, int idUsuario, String nuevoEstado) {
        if ("PAGADO".equals(nuevoEstado)) {
            for (Usuario u : usuarios) {
                if (u.id == idUsuario) {
                    for (Pedido p : pedidos) {
                        if (p.id == idPedido && p.estado.equals("PENDIENTE")) {
                            if (u.saldo >= p.total) {
                                u.saldo -= p.total;
                                persistencia.actualizarUsuario(u); // Persistir el cambio de saldo
                                p.estado = "PAGADO";
                                System.out.println("El pedido " + idPedido + " ha sido pagado.");
                                nuevoEstado = "PAGADO";
                            } else {
                                System.out.println("Saldo insuficiente para pagar el pedido.");
                                nuevoEstado = "PENDIENTE";
                            }
                            return nuevoEstado;
                        }
                    }
                    
                    return nuevoEstado;
                }
            }
        } else {
            System.out.println("El estado del pedido no es válido para pago.");
        }
        return nuevoEstado;
    }

    public void reporteGeneral() {
        System.out.println("=== REPORTE DE PEDIDOS ===");
        for (Usuario u : usuarios) {
            System.out.print("Usuario: " + u.nombre + " | Pedidos: ");
            for (Pedido p : pedidos) {
                if (p.idUsuario == u.id) {
                    System.out.print("[" + p.id + ": $" + p.total + "] ");
                }
            }
            System.out.println();
        }
    }

    public void registrarProducto(int id, String nombre, double precio) {
        Producto p = new Producto(id, nombre, precio);
        productos.add(p);
        persistencia.guardar(p);
        System.out.println("Producto registrado y guardado.");
    }

    public void actualizarProducto(int id, String nombre, double precio) {
        for (Producto p : productos) {
            if (p.id == id) {
                p.nombre = nombre;
                p.precio = precio;
                persistencia.actualizar(p);
                System.out.println("Producto actualizado.");
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    public void eliminarProducto(int id) {
        if (productos.removeIf(p -> p.id == id)) {
            persistencia.eliminar(id);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void listarProductos() {
        System.out.println("=== LISTA DE PRODUCTOS ===");
        for (Producto p : productos) {
            System.out.println("ID: " + p.id + " | Nombre: " + p.nombre + " | Precio: $" + p.precio);
        }
    }

    public void actualizarSaldoUsuario(int idUsuario, double nuevoSaldo) {
        for (Usuario u : usuarios) {
            if (u.id == idUsuario) {
                u.saldo = nuevoSaldo;
                persistencia.actualizarUsuario(u);
                System.out.println("Saldo del usuario " + u.nombre + " actualizado a " + nuevoSaldo);
                return;
            }
        }
        System.out.println("Usuario no encontrado.");
    }
}
