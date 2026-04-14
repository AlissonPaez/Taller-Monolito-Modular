package co.edu.uptc.monolito.pedidos.service;

import java.util.List;

import co.edu.uptc.monolito.pedidos.model.Pedido;

public interface IPedidoService {
    void crearPedido(int id, int idProducto, int cantidad, int idUsuario, String estado);
    void pagarPedido(int idPedido);
    List<Pedido> obtenerTodos();
    List<Pedido> obtenerPorUsuario(int idUsuario);
    Pedido obtenerPorId(int id);
}

