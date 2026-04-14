package co.edu.uptc.monolito;

import java.util.Scanner;

import co.edu.uptc.monolito.pagos.model.Pago;
import co.edu.uptc.monolito.pagos.service.IServicioPago;
import co.edu.uptc.monolito.pagos.service.PagoService;
import co.edu.uptc.monolito.pedidos.model.Pedido;
import co.edu.uptc.monolito.pedidos.service.IPedidoService;
import co.edu.uptc.monolito.pedidos.service.PedidoService;
import co.edu.uptc.monolito.productos.model.Producto;
import co.edu.uptc.monolito.productos.service.IProductoService;
import co.edu.uptc.monolito.productos.service.ProductoService;
import co.edu.uptc.monolito.usuarios.model.Usuario;
import co.edu.uptc.monolito.usuarios.service.IUsuarioService;
import co.edu.uptc.monolito.usuarios.service.UsuarioService;

public class App {
    public static void main(String[] args) {
       
        IUsuarioService usuarioService = new UsuarioService();
        IProductoService productoService = new ProductoService();
        
        IServicioPago pagoService = new PagoService(null); 
        
        IPedidoService pedidoService = new PedidoService(usuarioService, productoService, pagoService);
        
        ((PagoService)pagoService).setPedidoService(pedidoService);

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Registrar Producto");
            System.out.println("3. Crear Pedido");
            System.out.println("4. Pagar Pedido");
            System.out.println("5. Ver Reporte (Usuarios/Pedidos)");
            System.out.println("6. Ver Historial de comprobantes de pago"); 
            System.out.println("7. Listar Productos");
            System.out.println("8. Eliminar Usuario");
            System.out.println("9. Salir");
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
                    System.out.print("ID Producto: "); int idPr = sc.nextInt();
                    System.out.print("Nombre: "); String nPr = sc.next();
                    System.out.print("Precio: "); double pPr = sc.nextDouble();
                    productoService.crearProducto(idPr, nPr, pPr);
                    break;

                case 3:
                    System.out.print("ID Pedido: "); int idP = sc.nextInt();
                    System.out.print("ID Producto: "); int idPd = sc.nextInt();
                    System.out.print("Cantidad: "); int cant = sc.nextInt();
                    System.out.print("ID Usuario: "); int idUsu = sc.nextInt();
                    System.out.print("Estado (PENDIENTE/PAGADO): "); String est = sc.next();
                    pedidoService.crearPedido(idP, idPd, cant, idUsu, est);
                    break;
                
                case 4:
                    System.out.print("ID Pedido a pagar: ");
                    int idPay = sc.nextInt();
                    pedidoService.pagarPedido(idPay);
                    break;

                case 5:
                    System.out.println("\n=== REPORTE ===");
                    for (Usuario u : usuarioService.obtenerTodos()) {
                        System.out.println("Usuario: " + u.nombre + " (Saldo: " + u.saldo + ")");
                        for (Pedido p : pedidoService.obtenerPorUsuario(u.id)) {
                            System.out.println("   - Pedido " + p.id + ": " + p.descripcion + " [" + p.estado + "] $" + p.total);
                        }
                    }
                    break;

                case 6: 
                    System.out.println("\n=== HISTORIAL DE COMPROBANTES DE PAGO ===");
                    for (Pago p : pagoService.obtenerTodos()) {
                        System.out.println("ID Pago: " + p.getId() + " | Pedido: #" + p.getIdPedido() + 
                                           " | Monto: $" + p.getMonto() + " | Método de Pago: " + p.getMetodoPago());
                    }
                    break;

                case 7:
                    System.out.println("\n=== LISTA DE PRODUCTOS ===");
                    for (Producto p : productoService.obtenerTodos()) {
                        System.out.println("ID: " + p.id + " | " + p.nombre + " | $" + p.precio);
                    }
                    break;

                case 8:
                    System.out.print("ID Usuario a eliminar: ");
                    int idDel = sc.nextInt();
                    usuarioService.eliminarUsuario(idDel);
                    break;

                case 9:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 9);

        sc.close();
    }
}