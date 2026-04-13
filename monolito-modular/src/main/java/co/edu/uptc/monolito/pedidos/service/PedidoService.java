package co.edu.uptc.monolito.pedidos.service;

import co.edu.uptc.monolito.pedidos.model.Pedido;
import co.edu.uptc.monolito.pedidos.persistence.PedidoPersistence;
import co.edu.uptc.monolito.usuarios.service.IUsuarioService;
import co.edu.uptc.monolito.usuarios.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class PedidoService implements IPedidoService {
    private List<Pedido> pedidos;
    private PedidoPersistence persistence = new PedidoPersistence();
    private IUsuarioService usuarioService;

    public PedidoService(IUsuarioService usuarioService) {
        this.pedidos = persistence.cargar();
        this.usuarioService = usuarioService;
    }

    @Override
    public void crearPedido(int id, String descripcion, int idUsuario, double total, String estado) {
        if (existePedido(id)) {
            System.out.println("Error: El ID de pedido " + id + " ya existe.");
            return;
        }

        Usuario u = usuarioService.obtenerPorId(idUsuario);
        if (u == null) {
            System.out.println("Error: El usuario " + idUsuario + " no existe.");
            return;
        }

        if ("PAGADO".equals(estado)) {
            if (u.saldo >= total) {
                usuarioService.actualizarSaldo(idUsuario, u.saldo - total);
            } else {
                System.out.println("Saldo insuficiente. El pedido se creará como PENDIENTE.");
                estado = "PENDIENTE";
            }
        }

        Pedido p = new Pedido(id, descripcion, idUsuario, total, estado);
        pedidos.add(p);
        persistence.guardar(p);
        System.out.println("Pedido creado en módulo modular.");
    }

    @Override
    public void pagarPedido(int idPedido) {
        Pedido p = obtenerPorId(idPedido);
        if (p == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }
        if ("PAGADO".equals(p.estado)) {
            System.out.println("El pedido ya está pagado.");
            return;
        }

        Usuario u = usuarioService.obtenerPorId(p.idUsuario);
        if (u != null && u.saldo >= p.total) {
            usuarioService.actualizarSaldo(u.id, u.saldo - p.total);
            p.estado = "PAGADO";
            persistence.reescribir(pedidos);
            System.out.println("Pedido pagado exitosamente.");
        } else {
            System.out.println("No se pudo realizar el pago (saldo insuficiente o usuario no encontrado).");
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return pedidos;
    }

    @Override
    public List<Pedido> obtenerPorUsuario(int idUsuario) {
        List<Pedido> result = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.idUsuario == idUsuario) result.add(p);
        }
        return result;
    }

    private Pedido obtenerPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.id == id) return p;
        }
        return null;
    }

    private boolean existePedido(int id) {
        return obtenerPorId(id) != null;
    }
}
