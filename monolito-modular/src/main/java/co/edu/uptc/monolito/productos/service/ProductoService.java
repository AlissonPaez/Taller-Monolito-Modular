package co.edu.uptc.monolito.productos.service;

import co.edu.uptc.monolito.productos.model.Producto;
import co.edu.uptc.monolito.productos.persistence.ProductoPersistence;
import java.util.List;

public class ProductoService implements IProductoService {
    private List<Producto> productos;
    private ProductoPersistence persistence = new ProductoPersistence();

    public ProductoService() {
        this.productos = persistence.cargar();
    }

    @Override
    public void crearProducto(int id, String nombre, double precio) {
        if (existeProducto(id)) {
            System.out.println("Error: El ID de producto " + id + " ya existe.");
            return;
        }
        Producto nuevo = new Producto(id, nombre, precio);
        productos.add(nuevo);
        persistence.guardar(nuevo);
        System.out.println("Producto creado en módulo modular.");
    }

    @Override
    public List<Producto> obtenerTodos() {
        return productos;
    }

    @Override
    public Producto obtenerPorId(int id) {
        for (Producto p : productos) {
            if (p.id == id) return p;
        }
        return null;
    }

    @Override
    public boolean existeProducto(int id) {
        return obtenerPorId(id) != null;
    }
}
