package co.edu.uptc.monolito.usuarios.persistence;

import co.edu.uptc.monolito.usuarios.model.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPersistence {
    private String path = "src/main/java/co/edu/uptc/monolito/usuarios/persistence/";
    private String fileName = path + "usuarios.txt";

    public UsuarioPersistence() {
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
            System.out.println("Error al crear el archivo de usuarios: " + e.getMessage());
        }
    }

    public void guardar(Usuario usuario) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(usuario.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    public List<Usuario> cargar() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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

    public void reescribir(List<Usuario> usuarios) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
            for (Usuario u : usuarios) {
                out.println(u.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir usuarios: " + e.getMessage());
        }
    }
}
