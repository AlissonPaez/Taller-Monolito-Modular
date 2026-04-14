package co.edu.uptc.monolito.pagos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.monolito.pagos.model.Pago;
import co.edu.uptc.monolito.pagos.persistence.PersistenciaPago;
import co.edu.uptc.monolito.pedidos.model.Pedido;
import co.edu.uptc.monolito.pedidos.service.IPedidoService;

public class PagoService implements IServicioPago {
    
    private List<Pago> pagos;
    private PersistenciaPago persistence = new PersistenciaPago();
    private IPedidoService pedidoService;
    

    public PagoService(IPedidoService pedidoService) {
        this.pagos = persistence.cargarPagos();
        this.pedidoService = pedidoService;
    }


    @Override
    public void registrarPago(int id, int idPedido, double monto, String metodo) {
        if (existePago(id)) {
            System.out.println("Error: El ID de pago " + id + " ya existe.");
            return;
        }

        Pedido pedido = pedidoService.obtenerPorId(idPedido);

        if (pedido == null) {
            System.out.println("Error: El pedido " + idPedido + " no existe. No se puede pagar.");
            return;
        }

        // Crear y guardar el pago
        Pago p = new Pago(id, idPedido, monto, LocalDateTime.now(), metodo);
        pagos.add(p);
        persistence.guardarPago(p);
        
        System.out.println("El pago con ID " + id + " fue registrado para el pedido con ID " + idPedido + " exitosamente.");
    }

    @Override
    public List<Pago> obtenerTodos() {
        return pagos;
    }

    @Override
    public List<Pago> obtenerPorPedido(int idPedido) {
        List<Pago> result = new ArrayList<>();
        for (Pago p : pagos) {
            if (p.getIdPedido() == idPedido) {
                result.add(p);
            }
        }
        return result;
    }

    private boolean existePago(int id) {
        for (Pago p : pagos) {
            if (p.getId() == id) return true;
        }
        return false;
    }

    public void setPedidoService(IPedidoService pedidoService) {
    this.pedidoService = pedidoService;
}
}
