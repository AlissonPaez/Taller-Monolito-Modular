package co.edu.uptc.monolito.pagos.service;

import java.util.List;

import co.edu.uptc.monolito.pagos.model.Pago;

public interface IServicioPago {
    void registrarPago(int id, int idPedido, double monto, String metodo);
    List<Pago> obtenerTodos();
    List<Pago> obtenerPorPedido(int idPedido);
}
