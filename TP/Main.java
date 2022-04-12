package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import models.Articulo;
import models.Cliente;
import models.CuentaBancaria;
import models.Empleado;
import models.Usuario;

//Consigna:
// Alumno Roberto Sanabria - Comision 1 - Programacion de Computadores FIUNLZ NOV 2021-
//Realizar un programa en Java, el cual cuente con las siguientes funcionalidades:

//1. ABM artículos: Cargar artículos, editarlos y eliminarlos.

//Los artículos deberán contener cuanto mínimo los siguientes datos: 
//	Código de Articulo, Nombre/descripción y precio.

//2. Usuarios: Para utilizar el sistema
//se deberá en primer lugar ingresar con un usuario el cual, 
//cuanto mínimo, posea un nombre de usuario y una contraseña.

//Los mismos deberán ser catalogados en Cliente y Empleado.
//(Solo los empleados podrán acceder al ABM)

//3. Carrito de compra: Se deberá poder ver el listado de artículos en el sistema,
//agregarlos a un carrito, calcular su precio total y generar una factura.
//Una vez terminada la venta, 
//el usuario deberá poder completar la compra.
//La cantidad comprada, deberá verse reflejada automáticamente en el stock.
//(Se puede considerar una compra por parte del usuario, pero se trataría de una venta para la empresa que utiliza el sistema, el stock debería reducirse)

//4. Sección de Crédito: Contar con un gestor de dinero por cuenta, el cual permita 
//agregar dinero, transferir y remover.
//A su vez, si se aplica este módulo, en caso de que el usuario realice una compra
//, el sistema deberá descontar el valor total de la misma al dinero que posee.

//Errores:
//Cuidado con los números negativos, especialmente en precios y stock.

public class Main {
	public static Scanner sc = new Scanner(System.in);
	public static HashMap<String, Articulo> hashArticulos = new HashMap<String, Articulo>();
	public static HashMap<String, Usuario> diccionarioUsuarios = new HashMap<String, Usuario>();
	public static ArrayList<Articulo> carritoDeCompras = new ArrayList<>();
	public static Usuario usuarioActual;

	public static void main(String[] args) {
		boolean repetir = true;
		char opcion = 'x';
		Usuario cliente = new Cliente("cliente", "1234");
		Usuario empleado = new Empleado("empleado", "1234");
		Usuario cliente2 = new Cliente("cliente2", "1234");
		Usuario cliente3 = new Cliente("cliente3", "1234");

		diccionarioUsuarios.put(cliente.getNombre(), cliente);
		diccionarioUsuarios.put(empleado.getNombre(), empleado);
		diccionarioUsuarios.put(cliente2.getNombre(), cliente2);
		diccionarioUsuarios.put(cliente3.getNombre(), cliente3);

		try {
			if (new File("C:\\Users\\54113\\OneDrive\\Escritorio\\usuarios.txt").exists()) {
				ObjectInputStream lecturaUsuarios = new ObjectInputStream(
						new FileInputStream("C:\\Users\\54113\\OneDrive\\Escritorio\\usuarios.txt"));
				diccionarioUsuarios = (HashMap<String, Usuario>) lecturaUsuarios.readObject();
			} else {
				ObjectOutputStream escribirArchivoUsuarios = new ObjectOutputStream(
						new FileOutputStream("C:\\Users\\54113\\OneDrive\\Escritorio\\usuarios.txt"));
				escribirArchivoUsuarios.writeObject(diccionarioUsuarios);
				escribirArchivoUsuarios.close();
			}

			if (new File("C:\\Users\\54113\\OneDrive\\Escritorio\\articulos.txt").exists()) {
				ObjectInputStream lecturaArticulos = new ObjectInputStream(
						new FileInputStream("C:\\Users\\54113\\OneDrive\\Escritorio\\articulos.txt"));
				hashArticulos = (HashMap<String, Articulo>) lecturaArticulos.readObject();
			} else {
				ObjectOutputStream escribirArchivoArticulos = new ObjectOutputStream(
						new FileOutputStream("C:\\Users\\54113\\OneDrive\\Escritorio\\articulos.txt"));
				escribirArchivoArticulos.writeObject(hashArticulos);
				escribirArchivoArticulos.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (repetir) {
			System.out.println("************** Menu principal **************");
			System.out.println("");
			System.out.println("************** Elija un opcion **************");
			System.out.println("1- Iniciar Sesion");
			System.out.println("0- Salir");
			opcion = sc.next().charAt(0);
			switch (opcion) {
			case '1': {
				Login();
				repetir = false;
				break;
			}
			case '0': {
				System.out.println("***** Programa Finalizado *****");
				System.exit(0);
				break;
			}
			default:
				System.out.println("La opcion que ingreso no es valida, por favor intente nuevamente");
				repetir = true;
				break;
			}
		}
	}

	public static void Login() {
		// String nombreEmpleado = "empleado";
		// String passEmpleado = "1234";
		// String nombreCliente = "cliente";
		// String passCliente = "1234";

		// System.out.println("************** Inicion de sesion **************");
		// System.out.println("");
		// System.out.println("Ingrese su nombre de Usuario : ");
		// String UsuarioLogin = sc.next();
		// System.out.println("Ingrese su contraseña: \n");
		// String PassLogin = sc.next();

		// if (UsuarioLogin.equals(nombreEmpleado) && PassLogin.equals(passEmpleado)) {
		// MenuEmpleado();
		// } else if (UsuarioLogin.equals(nombreCliente) &&
		// PassLogin.equals(passCliente)) {
		// MenuCliente();
		// } else {
		// System.out.println("Datos incorrectos intentelo de nuevo");
		// System.out.println("");
		// System.out.println("");
		// Login();
		// }
		// }
		System.out.println("************** Inicion de sesion **************");
		System.out.println("");
		System.out.println("Ingrese su nombre de usuario: ");
		String nombre = sc.next();

		if (diccionarioUsuarios.containsKey(nombre)) {

			Usuario usuario = diccionarioUsuarios.get(nombre);

			System.out.println("Ingrese la contraseña: ");
			String contrasenia = sc.next();

			if (usuario.getContrasenia().equals(contrasenia)) {

				System.out.println("Inicio de sesion exitoso!");
				usuarioActual = usuario;
				if (usuario instanceof Cliente) {
					MenuCliente();
				}

				if (usuario instanceof Empleado) {
					MenuEmpleado();
				}

			} else {
				System.out.println("Inicion de sesion fallido, intente nuevamente");
				Login();
			}
		}
	}

	public static void MenuEmpleado() {
		System.out.println("************** Menu empleado **************");
		System.out.println("1) ABM Articulos");
		System.out.println("2) Volver al menu principal");
		System.out.println("");
		System.out.println("Por favor, ingrese el numero de opcion que desea");
		boolean repetir = true;
		char opcion = 'x';
		opcion = sc.next().charAt(0);
		switch (opcion) {
		case '1':
			MenuABM();
			repetir = false;
		case '2':
			Login();
			repetir = false;
		}
	}

	public static void MenuABM() {
		System.out.println("************** Menu ABM de articulos **************");
		System.out.println("1) Cargar nuevo articulo");
		System.out.println("2) Editar articulo");
		System.out.println("3) Eliminar articulo");
		System.out.println("4) Listar articulos cargados");
		System.out.println("5) Volver al menu principal");
		System.out.println("");
		System.out.println("Ingrese una opcion");
		char opcion = 'x';
		opcion = sc.next().charAt(0);
		switch (opcion) {
		case '1':
			CargarArticulo();

		case '2':
			EditarArticulo();

		case '3':
			EliminarArticulo();

		case '4':
			ListarArticulos();

		case '5':
			MenuEmpleado();

		}

	}

	public static void CargarArticulo() {
		System.out.println("Ingrese el precio del articulo: ");
		double precio = sc.nextDouble();
		if (precio <= 0) {
			System.out.println("El precio ingresado no es valido, intente de nuevo");
			CargarArticulo();
		} else {
			System.out.println("Ingrese el codigo del articulo: ");
			String codigo = sc.next();
			System.out.println("Ingrese el nombre del articulo: ");
			String nombre = sc.next();
			System.out.println("Ingrese la cantidad a ingresar de articulos: ");
			int cantidad = sc.nextInt();
			if (cantidad <= 0) {
				System.out.println("La cantidad ingresada no es valida, intente de nuevo: ");
				CargarArticulo(); //Evitar cargas negativas de Stock y Precios 
			} else {
				if (hashArticulos.containsKey(codigo)) {
					System.out.println("El articulo ya existe-");
					System.out.println("***************************");
					;// Para evitar cargas repetidas
				} else {
					Articulo articulo = new Articulo(codigo, nombre, precio, cantidad);
					hashArticulos.put(codigo, articulo);
					System.out.println("El articulo se guardo con exito -> volviendo a menu anterior");
					System.out.println("***************************");
					MenuABM();
				}
			}
		}
	}

	public static void EditarArticulo() {
		System.out.println("************** Menu de Edicion de Articulos **************");
		for (HashMap.Entry<String, Articulo> articulo : hashArticulos.entrySet()) {
			System.out.println("codigo: " + articulo.getValue().getCodigoArticulo() + " , nombre de articulo: "
					+ articulo.getValue().getNombreArticulo() + " , Precio: " + articulo.getValue().getPrecioArticulo()
					+ " , Cantidad: " + articulo.getValue().getCantidadArticulo());
		}
		System.out.println("\nIngrese el codigo del articulo que desea editar ");
		String cod = sc.next();
		if (hashArticulos.containsKey(cod)) {
			System.out.println("1-Nombre\n2-Precio\n3-Cantidad\n4-Codigo de articulo");
			System.out.println("\nIngresar la opcion que quiere modificar :");
			String opc = sc.next();
			switch (opc) {
			case "1":
				System.out.println("Ingrese el nombre : ");
				hashArticulos.get(cod).setNombreArticulo(sc.next());
				System.out.println("El articulo fue modificado con exito <---  ");
				MenuABM();
				break;

			case "2":
				System.out.print("Ingrese el precio : ");
				hashArticulos.get(cod).setPrecioArticulo(sc.nextDouble());
				System.out.println("El articulo fue modificado con exito <---  ");
				MenuABM();

				break;
			case "3":
				System.out.print("ingrese la cantidad : ");
				hashArticulos.get(cod).setCantidadArticulo(sc.nextInt());
				System.out.println("El articulo fue modificado con exito <---  ");
				MenuABM();

				break;
			case "4":
				System.out.print("Ingrese el codigo de articulo");
				hashArticulos.get(cod).setCodigoArticulo(sc.next());
				System.out.println("El articulo fue modificado con exito <---  ");
				MenuABM();

				break;
			default:
				System.out.println("Opcion incorrecta");
				break;
			}

		} else {
			System.out.println("El codigo es incorrecto\n Seleccione nuevamente ");
			EditarArticulo();
		}
	}

	public static void EliminarArticulo() {
		System.out.println("Indique el codigo del articulo a eliminar: ");
		String codigoEliminar = sc.next();
		if (hashArticulos.containsKey(codigoEliminar)) {
			hashArticulos.remove(codigoEliminar);
			System.out.println("El articulo se elimino con exito -> volviendo a menu anterior");
			System.out.println("***************************");
			MenuABM();
		} else {
			System.out.println("Codigo no existe.Vuleva a intentrlo");
			MenuABM();
		}
	}

	public static void ListarArticulos() {
		System.out.println("************** Menu de Stock de articulos **************");
		for (HashMap.Entry<String, Articulo> articulo : hashArticulos.entrySet()) {
			System.out.println("codigo: " + articulo.getValue().getCodigoArticulo() + " , nombre de articulo: "
					+ articulo.getValue().getNombreArticulo() + " , Precio: " + articulo.getValue().getPrecioArticulo()
					+ " , Cantidad: " + articulo.getValue().getCantidadArticulo());
		}
		MenuABM();
	}

	public static void MenuCliente() {
		System.out.println("************** - Menu cliente - **************");
		System.out.println("1) Carrito de compra");
		System.out.println("2) Modulo bancario");
		System.out.println("3) Cerrar sesion");
		System.out.println("");
		System.out.println("Por favor, ingrese la opcion que desea");

		String opcion = sc.next();

		switch (opcion) {
		case "1":
			CarritoDeCompra();
			break;
		case "2":
			ModuloBancario();
			break;
		case "3":
			System.out.println("Se ha cerrado sesion exitosamente.");
			System.out.println("");
			Login();
			break;
		default:
			System.out.println("No se ingreso una opcion valida, por favor seleccione una opciï¿½n valida");
			System.out.println("");
			MenuCliente();
			break;
		}
	}

	public static void CarritoDeCompra() {
		System.out.println("************** Carrito de compra **************");
		System.out.println("************** Carrito de compra - Listado de Articulos - **************");
		for (HashMap.Entry<String, Articulo> articulo : hashArticulos.entrySet()) {
			System.out.println("codigo: " + articulo.getValue().getCodigoArticulo() + " , nombre de articulo: "
					+ articulo.getValue().getNombreArticulo() + " , Precio: " + articulo.getValue().getPrecioArticulo()
					+ " , Cantidad: " + articulo.getValue().getCantidadArticulo());
		}
		System.out.println("Para agregar un articulo al carrito, ingrese el codigo del mismo: ");
		String opcion = sc.next();
		if (hashArticulos.containsKey(opcion)) {

			Articulo articulo = hashArticulos.get(opcion);

			System.out.println("Producto encontrado -> codigo: " + opcion + ", nombre: " + articulo.getNombreArticulo()
					+ " ,precio: " + articulo.getPrecioArticulo());
			System.out.println("Ingrese la cantidad que desea llevar : ");
			boolean repetir = true;
			while (repetir) {
				int cant = sc.nextInt();

				if (cant <= articulo.getCantidadArticulo()) {
					Articulo articuloComprado = new Articulo(articulo.getCodigoArticulo(), articulo.getNombreArticulo(),
							articulo.getPrecioArticulo(), cant);
					articulo.setCantidadArticulo(articulo.getCantidadArticulo() - cant);
					carritoDeCompras.add(articuloComprado);
					System.out.println("Se ha agregado el articulo al carrito de compras");
					repetir = false;
				} else {
					System.out.println("Por el momento no hay stock para esa cantidad, intente con otra cantidad");
					MenuCliente();
				}
				repetir = true;

				while (repetir) {

					System.out.println("");
					System.out.println("¿Desea seguir agregando articulos?");
					System.out.println("1) Si, quiero agregar mas productos al carrito");
					System.out.println("2) No, quiero finalizar la compra");

					opcion = sc.next();

					switch (opcion) {
					case "1":
						CarritoDeCompra();
						break;
					case "2":
						GenerarFactura();
						break;
					default:
						System.out.println("Se ingreso una opcion incorrecta, intente nuevamente");
						CarritoDeCompra();
					}
				}
			}
		} else {
			System.out.println("El producto no existe, reintente de nuevamente");
			CarritoDeCompra();
		}

	}

	public static void GenerarFactura() {
		// double prec = 0;
		// for (Articulo articulo : carritoDeCompras) {
		// prec = prec + (articulo.getPrecioArticulo() *
		// articulo.getCantidadArticulo());
		// }
		// System.out.println("****Generando Factura****");
		// System.out.println("Su carrito de compras contienen los siguientes articulos:
		// ");
		// carritoDeCompras.forEach(System.out::println);
		// System.out.println("****************************");
		// System.out.println("************** Factura **************");
		// System.out.println("Articulos comprados:");
		// for (Articulo articulo : carritoDeCompras) {
		// System.out.println("Nombre: " + articulo.getNombreArticulo() + " | Precio
		// p/u: "
		// + articulo.getPrecioArticulo() + " | Cant. : " +
		// articulo.getCantidadArticulo());
		// }
		// System.out.println("El total a pagar es de : " + prec);
		// MenuCliente();
		double prec = 0;
		for (Articulo articulo : carritoDeCompras) {
			prec = prec + (articulo.getPrecioArticulo() * articulo.getCantidadArticulo());
		}
		Cliente cliente = (Cliente) usuarioActual;
		System.out.println("Con que cuenta desea pagar ?");
		for (HashMap.Entry<String, CuentaBancaria> cuenta : cliente.getCuentasBancaria().entrySet()) {
			System.out.println("Identificacion : " + cuenta.getValue().getIdentificacionDeCuenta() + " saldo : "
					+ cuenta.getValue().getDinero());
		}
		System.out.println("Ingrese con el nombre de la identificacion ");
		String id = sc.next();
		if (cliente.getCuentasBancaria().containsKey(id)) {
			if (cliente.realizarPago(id, prec)) {
				System.out.println("Se realizo el pago");
			} else {
				System.out.println("No se puedo realizar el pago saldo insuficiente -> volviendo al menu anterior");
				MenuCliente();
			}
		} else {
			System.out.println("La cuenta no existe, por favor intente nuevamente");

			MenuCliente();
		}
		System.out.println("************** Factura **************");
		System.out.println("Factura a nombre de: " + cliente.getNombre());
		System.out.println("Articulos comprados:");
		double precioTotal = 0;
		for (Articulo articulo : carritoDeCompras) {
			System.out.println("Nombre: " + articulo.getNombreArticulo() + " | Precio p/u: "
					+ articulo.getPrecioArticulo() + " | Cant. : " + articulo.getCantidadArticulo()
					+ " | precio cant. total: " + articulo.getPrecioArticulo() * articulo.getCantidadArticulo());
			precioTotal = precioTotal + (articulo.getCantidadArticulo() * articulo.getPrecioArticulo());
		}

		System.out.println("Precio total: " + precioTotal);
		System.out.println("***********************************************");
		System.out.println("Volviendo al menu principal ->");
		MenuCliente();

	}

	public static void ModuloBancario() {

		System.out.println("************** Modulo bancario **************");
		System.out.println("1) Dar de alta nueva cuenta");
		System.out.println("2) Agregar dinero a cuenta");
		System.out.println("3) Transferir dinero entre cuentas");
		System.out.println("4) Remover cuenta");
		System.out.println("");
		System.out.println("5) Volver al menu principal");

		String opcion = sc.next();

		switch (opcion) {
		case "1":
			DarDeAltaNuevaCuenta();
			break;
		case "2":
			AgregarDineroACuenta();
			break;
		case "3":
			TransferirDineroEntreCuentas();
			break;
		case "4":
			EliminarCuenta();
			break;
		case "5":
			MenuCliente();
			break;
		default:
			System.out.println("La opcion que ingreso no es valida, por favor inente nuevamente");
			ModuloBancario();
			break;
		}

	}

	public static void DarDeAltaNuevaCuenta() {
		System.out.println("************** Alta de cuenta **************");
		System.out.println("Ingrese la identificacion de cuenta: ");
		String idCuenta = sc.next();
		System.out.println("ingrese el dinero con el cual desea iniciar esta cuenta: ");
		double dinero = sc.nextDouble();

		CuentaBancaria cuentaBancaria = new CuentaBancaria(idCuenta, dinero);

		HashMap<String, CuentaBancaria> cuentasBancaria = Cliente.getCuentasBancaria();
		cuentasBancaria.put(idCuenta, cuentaBancaria);

		System.out.println("Cuenta creada exitosamente! volviendo al menu anterior");

		ModuloBancario();
	}

	public static void AgregarDineroACuenta() {
		System.out.println("************** Agregar dinero  *************");
		System.out.println("");
		for (HashMap.Entry<String, CuentaBancaria> map : Cliente.getCuentasBancaria().entrySet()) {
			System.out.println("Identificacion de cuenta: " + map.getValue().getIdentificacionDeCuenta() + ", dinero: "
					+ map.getValue().getDinero());
		}
		System.out.println("");
		System.out.println("Ingrese la identifiacion de cuenta a la cual desea agregar dinero:");
		String idCuen = sc.next();

		CuentaBancaria cuentaSeleccionada = Cliente.getCuentasBancaria().get(idCuen);

		if (cuentaSeleccionada != null) {
			System.out.println("************ Cuenta Seleccionada ***************");
			System.out.println("Identificacion de cuenta: " + cuentaSeleccionada.getIdentificacionDeCuenta()
					+ ", dinero: " + cuentaSeleccionada.getDinero());
			System.out.println("");
			System.out.println("Ingrese el dinero que desea agregar a la cuenta:");
			double dinero = sc.nextDouble();
			cuentaSeleccionada.agregarDinero(dinero);
			;
			System.out.println("El nuevo dinero de la cuenta es: " + cuentaSeleccionada.getDinero());
			ModuloBancario();
		} else {
			System.out.println("La cuenta no se encuentra, por favor intente con otra identificacion");
			MenuCliente();
		}

	}

	public static void TransferirDineroEntreCuentas() {
		System.out.println("************** Transferencia de dinero **************");
		System.out.println("");

		for (HashMap.Entry<String, CuentaBancaria> hashMap : Cliente.getCuentasBancaria().entrySet()) {
			System.out.println("Identificacion de cuenta: " + hashMap.getValue().getIdentificacionDeCuenta()
					+ ", dinero: " + hashMap.getValue().getDinero());
		}

		System.out.println("");
		System.out.println("Ingrese la identificacion de cuenta origen para la transferencia:");
		String idOrigen = sc.next();
		System.out.println("Ingrese la identificacion de cuenta destino para recibir la transferencia");
		String idDestino = sc.next();
		System.out.println("Ingrese el monto de transferencia");
		double monto = sc.nextDouble();

		if (Cliente.transferirDineroEntreCuentas(idOrigen, idDestino, monto)) {
			System.out.println("Transferencia exitosa! -> volviendo menu principal");
			MenuCliente();
		} else {
			System.out.println(
					"Ocurrio un error, verifique que exitan las cuentas y que la cuenta origen tenga saldo suficiente");
			TransferirDineroEntreCuentas();
		}
	}

	public static void EliminarCuenta() {
		System.out.println("************** Eliminar cuenta - **************");
		System.out.println("");

		for (HashMap.Entry<String, CuentaBancaria> hashMap : (Cliente.getCuentasBancaria().entrySet())) {
			System.out.println("Identificacion de cuenta: " + hashMap.getValue().getIdentificacionDeCuenta()
					+ ", dinero: " + hashMap.getValue().getDinero());
		}

		System.out.println("Ingrese la identificacion de cuenta que desea eliminar");
		String id = sc.next();

		try {
			if ((Cliente.eliminarCuenta(id))) {
				System.out.println("Se elimino correctamente la cuenta");
				System.out.println("Volviendo al menu principal");
				;
				MenuCliente();
			} else {
				System.out.println("El codigo ingresado no pertenece a ninguna cuenta, por favor intente de nuevo");
				EliminarCuenta();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void guardarDatosUsuario() {
		try {
			ObjectOutputStream escribirArchivoUsuarios = new ObjectOutputStream(
					new FileOutputStream("C:\\Users\\54113\\OneDrive\\Escritorio\\usuarios.txt"));
			;
			escribirArchivoUsuarios.writeObject(diccionarioUsuarios);
			escribirArchivoUsuarios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guardarDatosArticulos() {
		try {
			ObjectOutputStream escribirArchivoUsuarios = new ObjectOutputStream(
					new FileOutputStream("C:\\Users\\54113\\OneDrive\\Escritorio\\articulos.txt"));
			;
			escribirArchivoUsuarios.writeObject(hashArticulos);
			escribirArchivoUsuarios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}