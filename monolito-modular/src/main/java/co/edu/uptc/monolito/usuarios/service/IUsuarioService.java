package co.edu.uptc.monolito.usuarios.service;

import co.edu.uptc.monolito.usuarios.model.Usuario;
import java.util.List;

public interface IUsuarioService {
    void crearUsuario(int id, String nombre, double saldo);
    void eliminarUsuario(int id);
    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(int id);
    void actualizarSaldo(int id, double nuevoSaldo);
    boolean existeUsuario(int id);
}
