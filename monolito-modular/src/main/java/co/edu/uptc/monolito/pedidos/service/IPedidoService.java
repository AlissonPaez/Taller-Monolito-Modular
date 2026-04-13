package co.edu.uptc.monolito.pedidos.service;

import co.edu.uptc.monolito.pedidos.model.Pedido;
import java.util.List;

public interface IPedidoService {
    void crearPedido(int id, int idProducto, int cantidad, int idUsuario, String estado);
    void pagarPedido(int idPedido);
    List<Pedido> obtenerTodos();
    List<Pedido> obtenerPorUsuario(int idUsuario);
}
