package co.edu.uptc.monolito.pagos.service;

public interface IServicioPago {

    boolean procesarPago(int idUsuario, int idPedido, double monto);

}
