package co.edu.uptc.monolito;

import java.util.ArrayList;
import java.util.List;

public class GestionSistema {

    public List<Usuario> usuarios = new ArrayList<>();
    public List<Pedido> pedidos = new ArrayList<>();
    
    public void crearUsuario(int id, String nombre, double saldo) {
        usuarios.add(new Usuario(id, nombre, saldo));
        System.out.println("Usuario creado" ); // syso aquí parque hay acoplamiento.
    }
    

    public void eliminarUsuario(int id) { // aquí mismo borramos sus pedidos.
        usuarios.removeIf(usuario -> usuario.id == id);
        pedidos.removeIf(pedido -> pedido.idUsuario == id);
        System.out.println("Usuario e historial de pedidos eliminados.");
    }

    public void realizarPedido(int idPedido, String descripcion, int idUsuario, double total, int tipoEstado) {
        for (Usuario u : usuarios) {
            if (u.id == idUsuario) {
                if (u.saldo >= total) {
                    String estado;
                    
                    // Estado del pedido acoplado.
                    if (tipoEstado == 1) {
                        estado = "PENDIENTE";
                    } else if (tipoEstado == 2) {
                        estado = "PAGADO";
                    } else {
                        estado = "DESCONOCIDO";
                    }

                    Pedido p = new Pedido(idPedido, descripcion, idUsuario, total, estado);
                    
                    u.saldo -= total; 
                    pedidos.add(p);
                    System.out.println("Pedido creado con estado: " + estado);
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
}
