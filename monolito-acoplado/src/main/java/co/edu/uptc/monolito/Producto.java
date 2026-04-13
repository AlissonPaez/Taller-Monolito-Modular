package co.edu.uptc.monolito;

public class Producto {
    public int id;
    public String nombre;
    public double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return id + "," + nombre + "," + precio;
    }

    public static Producto fromString(String line) {
        String[] parts = line.split(",");
        return new Producto(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
    }
}
