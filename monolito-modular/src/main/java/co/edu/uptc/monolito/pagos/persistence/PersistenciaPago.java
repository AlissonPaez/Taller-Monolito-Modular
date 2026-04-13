package co.edu.uptc.monolito.pagos.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.monolito.pagos.model.Pago;


public class PersistenciaPago {
 
   private String path = "src/main/java/co/edu/uptc/monolito/pagos/persistence/";
   private String paymentFileName = path + "pagos.txt";

   public PersistenciaPago() {
    try {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File pFile = new File(paymentFileName);
        if (!pFile.exists()) {
            pFile.createNewFile();
        }
    } catch (IOException e) {
        System.out.println("Error al crear archivo de pagos: " + e.getMessage());
    }
}

// CRUD para Pagos
public void guardarPago(Pago pago) {
    try (PrintWriter out = new PrintWriter(new FileWriter(paymentFileName, true))) {
        out.println(pago.toString());
    } catch (IOException e) {
        System.out.println("Error al guardar pago: " + e.getMessage());
    }
}

public List<Pago> cargarPagos() {
    List<Pago> pagos = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(paymentFileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                pagos.add(Pago.fromString(line));
            }
        }
    } catch (IOException e) {
        System.out.println("Error al cargar pagos: " + e.getMessage());
    }
    return pagos;
}

public void actualizarPago(Pago pagoActualizado) {
    List<Pago> pagos = cargarPagos();
    boolean encontrado = false;
    for (int i = 0; i < pagos.size(); i++) {
        if (pagos.get(i).getId() == pagoActualizado.getId()) {
            pagos.set(i, pagoActualizado);
            encontrado = true;
            break;
        }
    }
    if (encontrado) {
        reescribirPagos(pagos);
    }
}

private void reescribirPagos(List<Pago> pagos) {
    try (PrintWriter out = new PrintWriter(new FileWriter(paymentFileName, false))) {
        for (Pago p : pagos) {
            out.println(p.toString());
        }
    } catch (IOException e) {
        System.out.println("Error al reescribir pagos: " + e.getMessage());
    }
}
}
