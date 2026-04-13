package co.edu.uptc.monolito.pedidos.persistence;

import co.edu.uptc.monolito.pedidos.model.Pedido;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoPersistence {
    private String path = "src/main/java/co/edu/uptc/monolito/pedidos/persistence/";
    private String fileName = path + "pedidos.txt";

    public PedidoPersistence() {
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
            System.out.println("Error al crear el archivo de pedidos: " + e.getMessage());
        }
    }

    public void guardar(Pedido pedido) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(pedido.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    public List<Pedido> cargar() {
        List<Pedido> pedidos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    pedidos.add(Pedido.fromString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    public void reescribir(List<Pedido> pedidos) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
            for (Pedido p : pedidos) {
                out.println(p.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir pedidos: " + e.getMessage());
        }
    }
}
