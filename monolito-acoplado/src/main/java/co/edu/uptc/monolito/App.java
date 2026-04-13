package co.edu.uptc.monolito;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        GestionSistema sistema = new GestionSistema();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú de Opciones---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Crear Pedido ");
            System.out.println("3. Ver Reporte General");
            System.out.println("4. Eliminar Usuario y sus Pedidos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el ID del usuario: "); int idU = sc.nextInt();
                    System.out.print("Ingrese el nombre del usuario: "); String nom = sc.next();
                    System.out.print("Ingrese el saldo inicial: "); double sal = sc.nextDouble();
                    sistema.crearUsuario(idU, nom, sal);
                    break;

                case 2:
                    System.out.print("Ingrese el ID del pedido: "); int idP = sc.nextInt();
                    System.out.print("Ingrese la descripción: "); sc.nextLine(); // Limpiar buffer
                    String desc = sc.nextLine();
                    System.out.print("Ingrese el ID del usuario: "); int idUsu = sc.nextInt();
                    System.out.print("Ingrese el total del pedido: "); double total = sc.nextDouble();
                    System.out.print("Ingrese el estado (1:Pendiente, 2:Pagado): "); int est = sc.nextInt();

                    sistema.realizarPedido(idP, desc, idUsu, total, est);
                    break;

                case 3:
                    sistema.reporteGeneral();
                    break;

                case 4:
                    System.out.print("Ingrese el ID del usuario a eliminar: ");
                    int idEliminar = sc.nextInt();
                    sistema.eliminarUsuario(idEliminar);
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }
}