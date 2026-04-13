package co.edu.uptc.monolito;

public class Pedido {

    public int id;
    public String descripcion;
    public int idUsuario;
    public double total;
    public String estado;

    public Pedido(int id, String descripcion, int idUsuario, double total, String estado) {
        this.id = id;
        this.descripcion = descripcion; // para saber de que es el pedido ya que no tendremos productos.
        this.idUsuario = idUsuario;
        this.total = total; // para los pagos.
        this.estado = estado; // para saber si está pendiente o pagado.
    }
    
}
