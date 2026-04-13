package co.edu.uptc.monolito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private String fileName = "productos.txt";
    private String userFileName = "usuarios.txt";

    public Persistencia() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            File uFile = new File(userFileName);
            if (!uFile.exists()) {
                uFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error al crear los archivos: " + e.getMessage());
        }
    }

    public void guardar(Producto producto) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(producto.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
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
            System.out.println("Error al cargar: " + e.getMessage());
        }
        return productos;
    }

    public void actualizar(Producto productoActualizado) {
        List<Producto> productos = cargar();
        boolean encontrado = false;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).id == productoActualizado.id) {
                productos.set(i, productoActualizado);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            reescribirArchivo(productos);
        }
    }

    public void eliminar(int id) {
        List<Producto> productos = cargar();
        boolean removido = productos.removeIf(p -> p.id == id);
        if (removido) {
            reescribirArchivo(productos);
        }
    }

    private void reescribirArchivo(List<Producto> productos) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
            for (Producto p : productos) {
                out.println(p.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir: " + e.getMessage());
        }
    }

    // Métodos para Usuarios
    public void guardarUsuario(Usuario usuario) {
        try (PrintWriter out = new PrintWriter(new FileWriter(userFileName, true))) {
            out.println(usuario.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    usuarios.add(Usuario.fromString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuarioActualizado) {
        List<Usuario> usuarios = cargarUsuarios();
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).id == usuarioActualizado.id) {
                usuarios.set(i, usuarioActualizado);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            reescribirUsuarios(usuarios);
        }
    }

    private void reescribirUsuarios(List<Usuario> usuarios) {
        try (PrintWriter out = new PrintWriter(new FileWriter(userFileName, false))) {
            for (Usuario u : usuarios) {
                out.println(u.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir usuarios: " + e.getMessage());
        }
    }
}
