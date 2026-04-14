package co.edu.uptc.monolito.pedidos.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.monolito.pagos.service.IServicioPago;
import co.edu.uptc.monolito.pedidos.model.Pedido;
import co.edu.uptc.monolito.pedidos.persistence.PedidoPersistence;
import co.edu.uptc.monolito.productos.model.Producto;
import co.edu.uptc.monolito.productos.service.IProductoService;
import co.edu.uptc.monolito.usuarios.model.Usuario;
import co.edu.uptc.monolito.usuarios.service.IUsuarioService;

public class PedidoService implements IPedidoService {
    private List<Pedido> pedidos;
    private PedidoPersistence persistence = new PedidoPersistence();
    private IUsuarioService usuarioService;
    private IProductoService productoService;
    private IServicioPago pagoService;

    public PedidoService(IUsuarioService usuarioService, IProductoService productoService, IServicioPago pagoService) {
        this.pedidos = persistence.cargar();
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.pagoService = pagoService;
    }

    @Override
    public void crearPedido(int id, int idProducto, int cantidad, int idUsuario, String estado) {
        if (existePedido(id)) {
            System.out.println("Error: El ID de pedido " + id + " ya existe.");
            return;
        }

        Usuario u = usuarioService.obtenerPorId(idUsuario);
        if (u == null) {
            System.out.println("Error: El usuario " + idUsuario + " no existe.");
            return;
        }

        Producto pObj = productoService.obtenerPorId(idProducto);
        if (pObj == null) {
            System.out.println("Error: El producto " + idProducto + " no existe.");
            return;
        }

        double total = pObj.precio * cantidad;
        String descripcion = pObj.nombre + " x" + cantidad;

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
        System.out.println("Pedido creado con producto ID " + idProducto);
    }

    
    @Override
    public void pagarPedido(int idPedido) {
        Pedido p = obtenerPorId(idPedido);
        if (p == null || "PAGADO".equals(p.estado)) {
            System.out.println("Pedido no encontrado o ya pagado.");
            return;
        }

        Usuario u = usuarioService.obtenerPorId(p.idUsuario);
        if (u != null && u.saldo >= p.total) {
            // Restar saldo.
            usuarioService.actualizarSaldo(u.id, u.saldo - p.total);
            
            // cambiar el estado pues ya se pago.
            p.estado = "PAGADO";
            persistence.reescribir(pedidos);

            // se registra el pago en el modulo de pagos con un id aleatorio y con el id del pedido.
            int idNuevoPago = (int)(Math.random() * 10000); 
            pagoService.registrarPago(idNuevoPago, p.id, p.total, "Saldo_Usuario");

        } else {
            System.out.println("Saldo insuficiente.");
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

    @Override
    public Pedido obtenerPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.id == id) return p;
        }
        return null;
    }

    private boolean existePedido(int id) {
        return obtenerPorId(id) != null;
    }
}
