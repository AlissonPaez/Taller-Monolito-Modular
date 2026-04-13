package co.edu.uptc.monolito.productos.persistence;

import co.edu.uptc.monolito.productos.model.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoPersistence {
    private String path = "src/main/java/co/edu/uptc/monolito/productos/persistence/";
    private String fileName = path + "productos.txt";

    public ProductoPersistence() {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de productos: " + e.getMessage());
        }
    }

    public void guardar(Producto producto) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(producto.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }
    }

    public List<Producto> cargar() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    productos.add(Producto.fromString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
        return productos;
    }

    public void reescribir(List<Producto> productos) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
            for (Producto p : productos) {
                out.println(p.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir productos: " + e.getMessage());
        }
    }
}
