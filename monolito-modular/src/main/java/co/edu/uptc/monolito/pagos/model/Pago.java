package co.edu.uptc.monolito.pagos.model;

import java.time.LocalDateTime;

public class Pago {
    
    private int id;
    private int idPedido;
    private double monto;
    private LocalDateTime fecha;
    private String metodoPago;

    public Pago(int id, int idPedido, double monto, LocalDateTime fecha, String metodoPago) {
        this.id = id;
        this.idPedido = idPedido;
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public static Pago fromString(String line) {
    String[] partes = line.split(";");
    
    int id = Integer.parseInt(partes[0]);
    int idPedido = Integer.parseInt(partes[1]);
    double monto = Double.parseDouble(partes[2]);
    LocalDateTime fecha = LocalDateTime.parse(partes[3]);
    String metodoPago = partes[4];
    
    return new Pago(id, idPedido, monto, fecha, metodoPago);
}

@Override
public String toString() {
    return id + ";" + 
           idPedido + ";" + 
           monto + ";" + 
           fecha + ";" + 
           metodoPago;
}


}
