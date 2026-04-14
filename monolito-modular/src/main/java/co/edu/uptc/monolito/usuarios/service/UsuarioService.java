package co.edu.uptc.monolito.usuarios.service;

import java.util.List;

import co.edu.uptc.monolito.usuarios.model.Usuario;
import co.edu.uptc.monolito.usuarios.persistence.UsuarioPersistence;

public class UsuarioService implements IUsuarioService {
    private List<Usuario> usuarios;
    private UsuarioPersistence persistence = new UsuarioPersistence();

    public UsuarioService() {
        this.usuarios = persistence.cargar();
    }

    @Override
    public void crearUsuario(int id, String nombre, double saldo) {
        if (existeUsuario(id)) {
            System.out.println("Error: El ID de usuario " + id + " ya existe.");
            return;
        }
        Usuario nuevo = new Usuario(id, nombre, saldo);
        usuarios.add(nuevo);
        persistence.guardar(nuevo);
        System.out.println("Usuario creado.");
    }

    @Override
    public void eliminarUsuario(int id) {
        usuarios.removeIf(u -> u.id == id);
        persistence.reescribir(usuarios);
        System.out.println("Usuario eliminado.");
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarios;
    }

    @Override
    public Usuario obtenerPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.id == id) return u;
        }
        return null;
    }

    @Override
    public void actualizarSaldo(int id, double nuevoSaldo) {
        Usuario u = obtenerPorId(id);
        if (u != null) {
            u.saldo = nuevoSaldo;
            persistence.reescribir(usuarios);
        }
    }

    @Override
    public boolean existeUsuario(int id) {
        return obtenerPorId(id) != null;
    }
}
