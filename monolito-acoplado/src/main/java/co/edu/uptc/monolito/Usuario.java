package co.edu.uptc.monolito;

public class Usuario {
    public int id;
    public String nombre;
    public double saldo; // para saber cuanto dinero tiene el usuario para pagar.

    public Usuario(int id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
    }

}