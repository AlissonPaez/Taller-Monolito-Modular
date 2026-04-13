package co.edu.uptc.monolito;

import co.edu.uptc.monolito.usuarios.service.IUsuarioService;
import co.edu.uptc.monolito.usuarios.service.UsuarioService;
import co.edu.uptc.monolito.pedidos.service.IPedidoService;
import co.edu.uptc.monolito.pedidos.service.PedidoService;
import co.edu.uptc.monolito.usuarios.model.Usuario;
import co.edu.uptc.monolito.pedidos.model.Pedido;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        IUsuarioService usuarioService = new UsuarioService();
        IPedidoService pedidoService = new PedidoService(usuarioService);
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Monolito Modular ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Crear Pedido");
            System.out.println("3. Pagar Pedido");
            System.out.println("4. Ver Reporte de Pedidos");
            System.out.println("5. Eliminar Usuario");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("ID Usuario: "); int idU = sc.nextInt();
                    System.out.print("Nombre: "); String nom = sc.next();
                    System.out.print("Saldo Inicial: "); double sal = sc.nextDouble();
                    usuarioService.crearUsuario(idU, nom, sal);
                    break;

                case 2:
                    System.out.print("ID Pedido: "); int idP = sc.nextInt();
                    System.out.print("Descripción: "); String desc = sc.next();
                    System.out.print("ID Usuario: "); int idUsu = sc.nextInt();
                    System.out.print("Total: "); double tot = sc.nextDouble();
                    System.out.print("Estado (PENDIENTE/PAGADO): "); String est = sc.next();
                    pedidoService.crearPedido(idP, desc, idUsu, tot, est);
                    break;
                
                case 3:
                    System.out.print("ID Pedido a pagar: ");
                    int idPay = sc.nextInt();
                    pedidoService.pagarPedido(idPay);
                    break;

                case 4:
                    System.out.println("\n=== REPORTE MODULAR ===");
                    for (Usuario u : usuarioService.obtenerTodos()) {
                        System.out.println("Usuario: " + u.nombre + " (Saldo: " + u.saldo + ")");
                        for (Pedido p : pedidoService.obtenerPorUsuario(u.id)) {
                            System.out.println("  - Pedido " + p.id + ": " + p.descripcion + " [" + p.estado + "] $" + p.total);
                        }
                    }
                    break;

                case 5:
                    System.out.print("ID Usuario a eliminar: ");
                    int idDel = sc.nextInt();
                    usuarioService.eliminarUsuario(idDel);
                    break;

                case 6:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);

        sc.close();
    }
}
