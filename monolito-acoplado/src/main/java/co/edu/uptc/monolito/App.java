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
            System.out.println("3. Realizar pago de pedido");
            System.out.println("4. Ver Reporte General");
            System.out.println("5. Eliminar Usuario y sus Pedidos");
            System.out.println("6. Gestión de Productos");
            System.out.println("7. Salir");
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
                    System.out.print("Ingrese el ID del producto: "); int idProdOrder = sc.nextInt();
                    System.out.print("Ingrese el ID del usuario: "); int idUsu = sc.nextInt();
                    System.out.print("Ingrese el estado (1:Pendiente, 2:Pagado): "); int est = sc.nextInt();

                    sistema.realizarPedido(idP, idProdOrder, idUsu, est);
                    break;
                
                case 3:
                    System.out.print("Ingrese el ID del pedido a pagar: ");
                    int idPedidoActualizar = sc.nextInt();
                    sistema.pagarPedido(idPedidoActualizar);
                    break;

                case 4:
                    sistema.reporteGeneral();
                    break;

                case 5:
                    System.out.print("Ingrese el ID del usuario a eliminar: ");
                    int idEliminar = sc.nextInt();
                    sistema.eliminarUsuario(idEliminar);
                    break;

                case 6:
                    System.out.println("\n--- Gestión de Productos ---");
                    System.out.println("1. Registrar Producto");
                    System.out.println("2. Actualizar Producto");
                    System.out.println("3. Eliminar Producto");
                    System.out.println("4. Listar Productos");
                    System.out.print("Seleccione: ");
                    int subOpcion = sc.nextInt();
                    switch (subOpcion) {
                        case 1:
                            System.out.print("ID: "); int idProd = sc.nextInt();
                            System.out.print("Nombre: "); String nomProd = sc.next();
                            System.out.print("Precio: "); double preProd = sc.nextDouble();
                            sistema.registrarProducto(idProd, nomProd, preProd);
                            break;
                        case 2:
                            System.out.print("ID del producto a actualizar: "); int idUp = sc.nextInt();
                            System.out.print("Nuevo Nombre: "); String nomUp = sc.next();
                            System.out.print("Nuevo Precio: "); double preUp = sc.nextDouble();
                            sistema.actualizarProducto(idUp, nomUp, preUp);
                            break;
                        case 3:
                            System.out.print("ID del producto a eliminar: "); int idDel = sc.nextInt();
                            sistema.eliminarProducto(idDel);
                            break;
                        case 4:
                            sistema.listarProductos();
                            break;
                    }
                    break;

                case 7:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 7);

        sc.close();
    }
}