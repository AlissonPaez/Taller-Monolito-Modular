package co.edu.uptc.monolito.productos.service;

import co.edu.uptc.monolito.productos.model.Producto;
import java.util.List;

public interface IProductoService {
    void crearProducto(int id, String nombre, double precio);
    List<Producto> obtenerTodos();
    Producto obtenerPorId(int id);
    boolean existeProducto(int id);
}
