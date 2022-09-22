package Ventanas;

import static Ventanas.ventanaAdministradora.cantidadGenero;
import static Ventanas.ventanaAdministradora.recibirmayorCantidadProductos;
import static Ventanas.ventanaAdministradora.recibirmayorCantidadVentas;
import clasesPrincipales.Cliente;
import clasesPrincipales.Producto;
import clasesPrincipales.Sucursal;
import clasesPrincipales.Vendedor;
import clasesPrincipales.Venta;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import javax.swing.JOptionPane;

public class ControlarDatos {

    //Ventas
    static Venta arregloGeneralVentas[] = new Venta[1000];
    static Venta venta_a_reasignar = null;
    static int contador_ventas_general = 0;
    //Re-asignacion de las ventas a los vendedores
    static Vendedor vendedor_a_reasignar = null;
    static Cliente cliente_a_reasignar = null;
    static double totalVenta = 0;
    static int[] cantidades = new int[10];
    static Double[] subTotales = new Double[10];
    static int cont_sub_totales = 0;
    static int contador_cantidades = 0;
    static String fechaActual = "";
    static int numFactura = 0;
    static Producto[] productos_a_reasignar = new Producto[10];
    static int contador_productos_a_reasignar = 0;

    //Sucursales-------------------------------------------
    // Estatico global para poder usarlo en cualquier parte
    static Sucursal sucursales[] = new Sucursal[50];
    static int contador_sucursales = 0;

    //Productos-------------------------------------------
    static Producto productos[] = new Producto[200];
    static int contador_productos = 0;

    //Productos-------------------------------------------
    static Cliente clientes[] = new Cliente[100];
    static int contador_clientes = 0;
    static int[] generos = new int[2];

    //Vendedor-------------------------------------------
    static Vendedor vendedores[] = new Vendedor[400];
    static int contador_vendedores = 0;

    static ObjectInputStream ois;

    static ObjectOutputStream objSalida;

    public static String GraficaBarProducto = "GraficaBarProducto.png";
    public static String GraficaPieCliente = "GraficaPieCliente.png";
    public static String GraficaBarVendedor = "GraficaBarVendedor.png";

    public static void escribirSucursales(Object sucursales) {
        try {
            objSalida = new ObjectOutputStream(new FileOutputStream("Sucursales.bin"));

            objSalida.writeObject(sucursales);

            objSalida.close();

            //System.out.println("Objeto Serializado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object cargarDatosSucursales() {
        Object object;
        try {
            ois = new ObjectInputStream(new FileInputStream("Sucursales.bin"));
            object = ois.readObject();
            return object;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    /*
    *Se agregan las sucursales de uno en uno
     */
    public static void agregarSucursales(Sucursal sucursal) {
        sucursales[contador_sucursales++] = sucursal;
        escribirSucursales(sucursales);
        ordenarPorCodigoSucursal();
    }

    public static void mostrarSucursales() {
        System.out.println("\nSucursales Activas:");
        for (int i = 0; i < contador_sucursales; i++) {
            System.out.println("Sucursal " + (i + 1) + ":");
            System.out.println(sucursales[i].toString());
            System.out.println("--------------------------------------");
        }
    }

    public static boolean verificarSucursalNoRep(int codigo_a_buscar) {
        boolean bandera = false;
        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no se ha agregado a las sucursales
        for (int i = 0; i < contador_sucursales; i++) {
            if (sucursales[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                break;
            }
        }

        return bandera;
    }

    public static void actualizarSucursalCD(String[] sucursal) {
        int codigo_a_actualizar = Integer.parseInt(sucursal[0]);
        for (int i = 0; i < contador_sucursales; i++) {
            if (sucursales[i].getCodigo() == codigo_a_actualizar) {
                sucursales[i].setCodigo(codigo_a_actualizar);
                sucursales[i].setNombre(sucursal[1]);
                sucursales[i].setDireccion(sucursal[2]);
                sucursales[i].setCorreo(sucursal[3]);
                sucursales[i].setTelefono(sucursal[4]);
                break;
            }
        }
        escribirSucursales(sucursales);
    }

    public static boolean eliminarSucursalCD(int codigo_a_buscar) {

        boolean bandera = false;

        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no existen las sucursales
        for (int i = 0; i < contador_sucursales; i++) {
            if (sucursales[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                //correrIzquierdaSucursales(i);
                //Se corren las posiciones hacia la izquierda en el caso de que 
                //se encuentre la sucursal a eliminar.
                for (int j = i; j < contador_sucursales; j++) {
                    sucursales[j] = sucursales[j + 1];
                }
                contador_sucursales--;

                break;
            }
        }
        escribirSucursales(sucursales);
        return bandera;
    }

    /*
    Ordena todo tipo de Objetos de menor a mayor 
    usando el codigo como guia
     */
    public static void ordenarPorCodigoSucursal() {
        //System.out.println("Se ordenan las sucursales por medio de su codigo");
        int cont = contador_sucursales;
        for (int i = 0; i < cont - 1; i++) {
            for (int j = 0; j < cont - i - 1; j++) {
                if (sucursales[j].getCodigo() > sucursales[j + 1].getCodigo()) {
                    Sucursal aux = sucursales[j + 1];
                    sucursales[j + 1] = sucursales[j];
                    sucursales[j] = aux;
                }
            }
        }
    }

    public static void serializarSucursal() {
        sucursales = (Sucursal[]) cargarDatosSucursales();
        /*
        Al momento de que se cargan los datos en el arreglo de sucursales, se tiene que 
        hacer nuevamente un conteo de cuantas sucursales hay, esto es para que se le
        pueda asignar al contador_sucursales, y  seguir llevando la cuenta actualizada, 
        luego se tiene que cargar estos datos a la tabla.
         */
        if (sucursales == null) {
            System.out.println("No se ha creado el archivo");
            sucursales = new Sucursal[50];
        } else if (sucursales != null) {
            //se va a buscar cuantos datos del arreglo de sucursales es diferente de nulo
            int contador_sucursales_cargadas = 0;
            for (int i = 0; i < sucursales.length; i++) {
                if (sucursales[i] != null) {
                    contador_sucursales_cargadas++;
                } else if (sucursales[i] == null) {
                    //En el momento en que la sucursal tenga datos vacios,
                    //es mejor paralo con un break, para no gastar mas recursos
                    break;
                }
            }
            contador_sucursales = contador_sucursales_cargadas;
            //System.out.println("El contador de sucursales tiene: " + contador_sucursales+" sucursales");
            //mostrarSucursales();
        }
    }

    public static Object[][] convertirDatosSucursales() {
        //se trae el contador de sucursales ya que en base a eso se pasaran las filas de datos
        //de los sucursales que se hayan encontrado
        int filas = contador_sucursales;

        Object[][] arregloSucursal = new Object[filas][5];

        for (int i = 0; i < filas; i++) {
            arregloSucursal[i][0] = sucursales[i].getCodigo();
            arregloSucursal[i][1] = sucursales[i].getNombre();
            arregloSucursal[i][2] = sucursales[i].getDireccion();
            arregloSucursal[i][3] = sucursales[i].getCorreo();
            arregloSucursal[i][4] = sucursales[i].getTelefono();
        }
        return arregloSucursal;
    }

    //Todos los procesos para productos

    /*
    *Se agregan los productos de uno en uno
     */
    public static void agregarProductos(Producto producto) {
        productos[contador_productos++] = producto;
        
        ordenarPorCodigoProducto();
        escribirProductos(productos);
    }

    public static void mostrarProductos() {
        System.out.println("\nProductos en inventario:");
        for (int i = 0; i < contador_productos; i++) {
            System.out.println("Producto " + (i + 1) + ":");
            System.out.println(productos[i].toString());
            System.out.println("--------------------------------------");
        }
    }

    public static void actualizarProductoCD(String[] producto) {
        int codigo_a_actualizar = Integer.parseInt(producto[0]);
        int cantidad_a_actualizar = Integer.parseInt(producto[3]);
        double precio_a_actualizar = Double.parseDouble(producto[4]);

        for (int i = 0; i < contador_productos; i++) {
            if (productos[i].getCodigo() == codigo_a_actualizar) {
                productos[i].setCodigo(codigo_a_actualizar);
                productos[i].setNombre(producto[1]);
                productos[i].setDescripcion(producto[2]);
                productos[i].setCantidad(cantidad_a_actualizar);
                productos[i].setPrecio(precio_a_actualizar);
                break;
            }
        }
        mayorCantidadProductos();
        ordenarPorCodigoProducto();
        escribirProductos(productos);
    }

    public static boolean eliminarProductoCD(int codigo_a_buscar) {
        boolean bandera = false;
        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no existe el producto
        for (int i = 0; i < contador_productos; i++) {
            if (productos[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                //correrIzquierdaSucursales(i);
                //Se corren las posiciones hacia la izquierda en el caso de que 
                //se encuentre la sucursal a eliminar.
                for (int j = i; j < contador_productos; j++) {
                    productos[j] = productos[j + 1];
                }
                contador_productos--;

                break;
            }
        }
        mayorCantidadProductos();
        ordenarPorCodigoProducto();
        escribirProductos(productos);
        return bandera;

    }

    /*
    Ordena de menor a mayor a sus objetos segun el codigo del producto
     */
    public static void ordenarPorCodigoProducto() {
        //System.out.println("Se ordenan los productos por medio de su codigo");
        int cont = contador_productos;
        for (int i = 0; i < cont - 1; i++) {
            for (int j = 0; j < cont - i - 1; j++) {
                if (productos[j].getCodigo() > productos[j + 1].getCodigo()) {
                    Producto aux = productos[j + 1];
                    productos[j + 1] = productos[j];
                    productos[j] = aux;
                }
            }
        }
    }

    public static boolean verificarProductoNoRep(int codigo_a_buscar) {

        boolean bandera = false;

        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no se ha agregado a los productos
        for (int i = 0; i < contador_productos; i++) {
            if (productos[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                break;
            }
        }

        return bandera;
    }

    public static void mayorCantidadProductos() {
        String[] mCP_nombres = new String[3];
        int[] mCP_cantidades = new int[3];
        int cont = contador_productos;
        for (int i = 0; i < cont - 1; i++) {
            for (int j = 0; j < cont - i - 1; j++) {
                if (productos[j].getCantidad() < productos[j + 1].getCantidad()) {
                    Producto aux = productos[j + 1];
                    productos[j + 1] = productos[j];
                    productos[j] = aux;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            mCP_nombres[i] = productos[i].getNombre();
            mCP_cantidades[i] = productos[i].getCantidad();
        }
        recibirmayorCantidadProductos(mCP_nombres, mCP_cantidades);
    }

    public static void escribirProductos(Object productos) {
        try {
            objSalida = new ObjectOutputStream(new FileOutputStream("Productos.bin"));

            objSalida.writeObject(productos);

            objSalida.close();

            //ystem.out.println("Objeto (Productos) Serializado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object cargarDatosProductos() {

        Object object;

        try {

            ois = new ObjectInputStream(new FileInputStream("Productos.bin"));

            object = ois.readObject();

            return object;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static void serializarProductos() {
        productos = (Producto[]) cargarDatosProductos();

        /*
        Al momento de que se cargan los datos en el arreglo de productos,
        se tiene que hacer nuevamente un conteo de cuantos productos hay,
        esto es para que se le pueda asignar al contador_productos, y  seguir
        llevando la cuenta actualizada, luego se tiene que cargar estos datos
        a la tabla.
         */
        if (productos == null) {
            System.out.println("No se ha creado el archivo");
            productos = new Producto[200];
        } else if (productos != null) {
            //se va a buscar cuantos datos del arreglo de productos es diferente de nulo
            int contador_productos_cargadas = 0;
            for (int i = 0; i < productos.length; i++) {
                if (productos[i] != null) {
                    contador_productos_cargadas++;
                } else if (productos[i] == null) {
                    //En el momento en que el producto tenga datos vacios,
                    //es mejor pararlo con un break, para no gastar mas recursos
                    break;
                }
            }
            contador_productos = contador_productos_cargadas;
            //System.out.println("El contador de productos tiene: " + contador_productos + " sucursales");
            //mostrarProductos();
            mayorCantidadProductos();
            ordenarPorCodigoProducto();
        }

    }

    public static Object[][] convertirDatosProductos() {

        //se trae el contador de productos ya que en base a eso se pasaran las filas de datos
        //de los productos que se hayan encontrado
        int filas = contador_productos;

        Object[][] arregloProducto = new Object[filas][5];

        for (int i = 0; i < filas; i++) {
            arregloProducto[i][0] = productos[i].getCodigo();
            arregloProducto[i][1] = productos[i].getNombre();
            arregloProducto[i][2] = productos[i].getDescripcion();
            arregloProducto[i][3] = productos[i].getCantidad();
            arregloProducto[i][4] = productos[i].getPrecio();
        }
        return arregloProducto;
    }

    //Todos los procesos para clientes
    /*
    *Se agregan los productos de uno en uno
     */
    public static void agregarClientes(Cliente cliente) {
        clientes[contador_clientes++] = cliente;
        escribirClientes(clientes);
        ordenarPorCodigoCliente();
        contadorGenero();
    }

    public static void mostrarClientes() {
        System.out.println("\nClientes en activos:");
        for (int i = 0; i < contador_clientes; i++) {
            System.out.println("Producto " + (i + 1) + ":");
            System.out.println(clientes[i].toString());
            System.out.println("--------------------------------------");
        }
    }

    public static void contadorGenero() {
        //Vamos a contar los generos del arreglo de clientes, luego la volveremos funcion
        int conM = 0, conF = 0;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getGenero().equalsIgnoreCase("m")) {
                conM++;
            } else {
                conF++;
            }
        }
        generos[0] = conM;
        generos[1] = conF;

        cantidadGenero(generos);
    }

    public static void actualizarClienteCD(String[] cliente) {
        int codigo_a_actualizar = Integer.parseInt(cliente[0]);

        for (int i = 0; i < contador_productos; i++) {
            if (clientes[i].getCodigo() == codigo_a_actualizar) {
                clientes[i].setCodigo(codigo_a_actualizar);
                clientes[i].setNombre(cliente[1]);
                clientes[i].setNit(cliente[2]);
                clientes[i].setCorreo(cliente[3]);
                clientes[i].setGenero(cliente[4]);
                break;
            }
        }
        escribirClientes(clientes);
        contadorGenero();
    }

    public static boolean eliminarClienteCD(int codigo_a_buscar) {

        boolean bandera = false;

        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no existen las sucursales
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                //correrIzquierdaSucursales(i);
                //Se corren las posiciones hacia la izquierda en el caso de que 
                //se encuentre la sucursal a eliminar.
                for (int j = i; j < contador_clientes; j++) {
                    clientes[j] = clientes[j + 1];
                }
                contador_clientes--;
                break;
            }
        }
        escribirClientes(clientes);
        contadorGenero();
        return bandera;
    }

    public static boolean verificarClienteNoRep(int codigo_a_buscar) {

        boolean bandera = false;

        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no se ha agregado a los productos
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                break;
            }
        }

        return bandera;
    }


    /*
    Ordena de menor a mayor a sus objetos segun el codigo del producto
     */
    public static void ordenarPorCodigoCliente() {
        System.out.println("Se ordenan los productos por medio de su codigo");
        int cont = contador_clientes;
        for (int i = 0; i < cont - 1; i++) {
            for (int j = 0; j < cont - i - 1; j++) {
                if (clientes[j].getCodigo() > clientes[j + 1].getCodigo()) {
                    Cliente aux = clientes[j + 1];
                    clientes[j + 1] = clientes[j];
                    clientes[j] = aux;
                }
            }
        }
    }

    public static void escribirClientes(Object clientes) {
        try {
            objSalida = new ObjectOutputStream(new FileOutputStream("Clientes.bin"));

            objSalida.writeObject(clientes);

            objSalida.close();

            //System.out.println("Objeto (Cliente) Serializado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object cargarDatosClientes() {

        Object object;

        try {

            ois = new ObjectInputStream(new FileInputStream("Clientes.bin"));

            object = ois.readObject();

            return object;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static void serializarClientes() {
        clientes = (Cliente[]) cargarDatosClientes();

        /*
        Al momento de que se cargan los datos en el arreglo de productos,
        se tiene que hacer nuevamente un conteo de cuantos productos hay,
        esto es para que se le pueda asignar al contador_productos, y  seguir
        llevando la cuenta actualizada, luego se tiene que cargar estos datos
        a la tabla.
         */
        if (clientes == null) {
            System.out.println("No se ha creado el archivo");
            clientes = new Cliente[100];
        } else if (clientes != null) {
            //se va a buscar cuantos datos del arreglo de clientes es diferente de nulo
            int contador_clientes_cargados = 0;
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] != null) {
                    contador_clientes_cargados++;
                } else if (clientes[i] == null) {
                    //En el momento en que el cliente(a) tenga datos vacios,
                    //es mejor pararlo con un break, para no gastar mas recursos
                    break;
                }
            }
            contador_clientes = contador_clientes_cargados;
        }
        ordenarPorCodigoCliente();
        contadorGenero();
    }

    public static Object[][] convertirDatosClientes() {

        //se trae el contador de clientes ya que en base a eso se pasaran las filas de datos
        //de los clientes que se hayan encontrado
        int filas = contador_clientes;

        Object[][] arregloCliente = new Object[filas][5];

        for (int i = 0; i < filas; i++) {
            arregloCliente[i][0] = clientes[i].getCodigo();
            arregloCliente[i][1] = clientes[i].getNombre();
            arregloCliente[i][2] = clientes[i].getNit();
            arregloCliente[i][3] = clientes[i].getCorreo();
            arregloCliente[i][4] = clientes[i].getGenero();
        }
        return arregloCliente;
    }

    //Todos los procesos para vendedores
    /*
    *Se agregan los productos de uno en uno
     */
    public static void agregarVendedores(Vendedor vendedor) {
        vendedores[contador_vendedores++] = vendedor;
        ordenarPorCodigoVendedor();
        escribirVendedores(vendedores);
    }

    public static void mostrarVendedores() {
        System.out.println("\nVendedores activos:");
        for (int i = 0; i < contador_vendedores; i++) {
            System.out.println("Vendedor " + (i + 1) + ":");
            System.out.println(vendedores[i].toString());
            System.out.println("--------------------------------------");
        }
    }

    public static void actualizarVendedorCD(String[] vendedor) {
        int codigo_a_actualizar = Integer.parseInt(vendedor[0]);
        int caja_a_actualizar = Integer.parseInt(vendedor[2]);
        int venta_a_actualizar = Integer.parseInt(vendedor[3]);

        for (int i = 0; i < contador_vendedores; i++) {
            if (vendedores[i].getCodigo() == codigo_a_actualizar) {
                vendedores[i].setCodigo(codigo_a_actualizar);
                vendedores[i].setNombre(vendedor[1]);
                vendedores[i].setCaja(caja_a_actualizar);
                vendedores[i].setVenta(venta_a_actualizar);
                vendedores[i].setGenero(vendedor[4]);
                break;
            }
        }
        mayorCantidadVendedores();
        ordenarPorCodigoVendedor();
        escribirVendedores(vendedores);
    }

    public static boolean eliminarVendedorCD(int codigo_a_buscar) {

        boolean bandera = false;
        System.out.println("codigo_a_buscar = " + codigo_a_buscar);
        System.out.println("contador_clientes = " + contador_clientes);
        
        
        
        
        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no existen entre los vendedores
        for (int i = 0; i < contador_vendedores; i++) {
            if (vendedores[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                //Se corren las posiciones hacia la izquierda en el caso de que 
                //se encuentre el vendedor a eliminar.
                for (int j = i; j < contador_vendedores; j++) {
                    vendedores[j] = vendedores[j + 1];
                }
                contador_vendedores--;
                break;
            }
        }
        mayorCantidadVendedores();
        ordenarPorCodigoVendedor();
        escribirVendedores(vendedores);
        return bandera;
    }

    /*
    Ordena de menor a mayor a sus objetos segun el codigo del venddedor
     */
    public static void ordenarPorCodigoVendedor() {
        //System.out.println("Se ordenan los vendedores por medio de su codigo");
        int cont = contador_vendedores;
        for (int i = 0; i < cont - 1; i++) {
            for (int j = 0; j < cont - i - 1; j++) {
                if (vendedores[j].getCodigo() > vendedores[j + 1].getCodigo()) {
                    Vendedor aux = vendedores[j + 1];
                    vendedores[j + 1] = vendedores[j];
                    vendedores[j] = aux;
                }
            }
        }
    }

    public static boolean verificarVendedorNoRep(int codigo_a_buscar) {

        boolean bandera = false;

        //Se busca el codigo, si el codigo no lo encontro debe decir que
        //no se ha agregado a los vendedores
        for (int i = 0; i < contador_vendedores; i++) {
            if (vendedores[i].getCodigo() == codigo_a_buscar) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public static void mayorCantidadVendedores() {
        String[] mCV_nombres = new String[3];
        int[] mCV_cantidades = new int[3];
        int cont = contador_vendedores;
        for (int i = 0; i < cont - 1; i++) {
            for (int j = 0; j < cont - i - 1; j++) {
                if (vendedores[j].getVenta() < vendedores[j + 1].getVenta()) {
                    Vendedor aux = vendedores[j + 1];
                    vendedores[j + 1] = vendedores[j];
                    vendedores[j] = aux;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            mCV_nombres[i] = vendedores[i].getNombre();
            mCV_cantidades[i] = vendedores[i].getVenta();
        }
        recibirmayorCantidadVentas(mCV_nombres, mCV_cantidades);
    }

    public static void escribirVendedores(Object vendedores) {
        try {
            objSalida = new ObjectOutputStream(new FileOutputStream("Vendedores.bin"));

            objSalida.writeObject(vendedores);

            objSalida.close();

            //System.out.println("Objeto (Vendedores) Serializado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object cargarDatosVendedores() {

        Object object;

        try {

            ois = new ObjectInputStream(new FileInputStream("Vendedores.bin"));

            object = ois.readObject();

            return object;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static void serializarVendedores() {
        vendedores = (Vendedor[]) cargarDatosVendedores();

        /*
        Al momento de que se cargan los datos en el arreglo de vendedores,
        se tiene que hacer nuevamente un conteo de cuantos vendedores hay,
        esto es para que se le pueda asignar al contador_vendedores, y  seguir
        llevando la cuenta actualizada, luego se tiene que cargar estos datos
        a la tabla.
         */
        if (vendedores == null) {
            System.out.println("No se ha creado el archivo");
            vendedores = new Vendedor[400];
        } else if (vendedores != null) {
            //se va a buscar cuantos datos del arreglo de vendedores es diferente de nulo
            int contador_vendedores_cargados = 0;
            for (int i = 0; i < vendedores.length; i++) {
                if (vendedores[i] != null) {
                    contador_vendedores_cargados++;
                } else if (vendedores[i] == null) {
                    //En el momento en que el vendedores tenga datos vacios,
                    //es mejor pararlo con un break, para no gastar mas recursos
                    break;
                }
            }
            contador_vendedores = contador_vendedores_cargados;
            mayorCantidadVendedores();
            ordenarPorCodigoVendedor();
        }

    }

    public static Object[][] convertirDatosVendedores() {

        //se trae el contador de vendedores ya que en base a eso se pasaran las filas de datos
        //de los vendedores que se hayan encontrado
        int filas = contador_vendedores;

        Object[][] arregloVendedores = new Object[filas][5];

        for (int i = 0; i < filas; i++) {
            arregloVendedores[i][0] = vendedores[i].getCodigo();
            arregloVendedores[i][1] = vendedores[i].getNombre();
            arregloVendedores[i][2] = vendedores[i].getCaja();
            arregloVendedores[i][3] = vendedores[i].getVenta();
            arregloVendedores[i][4] = vendedores[i].getGenero();
        }
        return arregloVendedores;
    }

    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    //Metodo para crear el pdf de crearPDFSucursales
    public static void crearPDFSucursales() {
        String nombrePDF = "Listado de Sucursales.pdf";
        try {

            Document documentoSucursales = new Document();
            PdfWriter.getInstance(documentoSucursales, new FileOutputStream(nombrePDF));
            //Se abre el documento para ingresar la tabla
            documentoSucursales.open();

            //Se agrega el titulo, el dato del reporte y la tabla con los respectivos datos
            Paragraph parrafo = new Paragraph();
            // agregamos una linea en blanco
            addEmptyLine(parrafo, 1);
            // Le agregamos un encabezado 
            parrafo.add(new Paragraph("Listado de Sucursales", subFont));

            addEmptyLine(parrafo, 1);
            // Crearemos: Reporte generado por: _nombre, _fecha
            parrafo.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold));

            addEmptyLine(parrafo, 1);

            documentoSucursales.add(parrafo);

            //Dicho codigo genera una tabla de 3 columnas 
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(new float[]{15, 25, 40, 42, 18});
            //addCell()  agrega una celda a la tabla, el cambio de fila es automatico
            PdfPCell c1 = new PdfPCell(new Phrase("Código"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Nombre"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Direccion"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Correo"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Telefono"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);
            tabla.setWidthPercentage(100);

            for (int i = 0; i < contador_sucursales; i++) {

                //salto automatico en la tabla
                tabla.addCell(Integer.toString(sucursales[i].getCodigo()));
                tabla.addCell(sucursales[i].getNombre());
                tabla.addCell(sucursales[i].getDireccion());
                tabla.addCell(sucursales[i].getCorreo());
                tabla.addCell(sucursales[i].getTelefono());

            }

            documentoSucursales.add(tabla);

            //Salto de linea :)
            addEmptyLine(parrafo, 2);

            // Iniciar una nueva pagina
            documentoSucursales.newPage();
            //Cerramos el documento para evitar errores
            documentoSucursales.close();

            abrirArchivo(nombrePDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo para crear el pdf de crearPDFProductos
    public static void crearPDFProductos() {
        String nombrePDFProductos = "Listado de Productos.pdf";
        try {

            Document documentoProductos = new Document();
            PdfWriter.getInstance(documentoProductos, new FileOutputStream(nombrePDFProductos));
            //Se abre el documento para ingresar la tabla
            documentoProductos.open();

            //Se agrega el titulo, el dato del reporte y la tabla con los respectivos datos
            Paragraph parrafo = new Paragraph();
            // agregamos una linea en blanco
            addEmptyLine(parrafo, 1);
            // Le agregamos un encabezado 
            parrafo.add(new Paragraph("Listado de Productos", subFont));

            addEmptyLine(parrafo, 1);
            // Crearemos: Reporte generado por: _nombre, _fecha
            parrafo.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold));

            addEmptyLine(parrafo, 1);

            documentoProductos.add(parrafo);

            //Dicho codigo genera una tabla de 5 columnas 
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(new float[]{15, 25, 50, 12, 9});
            //addCell()  agrega una celda a la tabla, el cambio de fila es automatico
            PdfPCell c1 = new PdfPCell(new Phrase("Código"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Nombre"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Descripción"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Cantidad"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Precio"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);
            tabla.setWidthPercentage(100);

            for (int i = 0; i < contador_productos; i++) {

                //salto automatico en la tabla
                tabla.addCell(Integer.toString(productos[i].getCodigo()));
                tabla.addCell(productos[i].getNombre());
                tabla.addCell(productos[i].getDescripcion());
                tabla.addCell(Integer.toString(productos[i].getCantidad()));
                tabla.addCell(Double.toString(productos[i].getPrecio()));

            }

            documentoProductos.add(tabla);

            //Salto de linea
            addEmptyLine(parrafo, 2);
            //Se va a agregar una imagen al pdf
            Image GraficaDeBarCurso = Image.getInstance(GraficaBarProducto);
            GraficaDeBarCurso.scaleToFit(180, 160);
            GraficaDeBarCurso.setAlignment(Chunk.ALIGN_CENTER);
            documentoProductos.add(GraficaDeBarCurso);

            // Iniciar una nueva pagina
            documentoProductos.newPage();
            //Cerramos el documento para evitar errores
            documentoProductos.close();
            abrirArchivo(nombrePDFProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo para crear el pdf del alumno
    public static void creacionPDFCliente() {
        String nombrePDFCliente = "Listado de clientes.pdf";
        try {

            Document documentoalumno = new Document();
            PdfWriter.getInstance(documentoalumno, new FileOutputStream(nombrePDFCliente));
            //Se abre el documento para ingresar la tabla
            documentoalumno.open();

            //Se agrega el titulo, el dato del reporte y la tabla con los respectivos datos
            Paragraph parrafo = new Paragraph();
            // We add one empty line
            addEmptyLine(parrafo, 1);
            // Lets write a big header
            parrafo.add(new Paragraph("Listado de Clientes", subFont));

            addEmptyLine(parrafo, 1);
            // Crearemos: Reporte generado por: _nombre, _fecha
            parrafo.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold));

            addEmptyLine(parrafo, 1);

            documentoalumno.add(parrafo);

            //Dicho codigo genera una tabla de 3 columnas 
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(new float[]{15, 20, 20, 50, 15});
            //addCell()  agrega una celda a la tabla, el cambio de fila es automatico
            PdfPCell c1 = new PdfPCell(new Phrase("Código"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Nombre"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("NIT"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Correo"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Genero"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);

            PdfPCell c2 = new PdfPCell(new Phrase("prueba"));
            for (int i = 0; i < contador_clientes; i++) {

                //salto automatico en la tabla
                tabla.addCell(Integer.toString(clientes[i].getCodigo()));
                tabla.addCell(clientes[i].getNombre());
                tabla.addCell(clientes[i].getNit());
                tabla.addCell(clientes[i].getCorreo());
                tabla.addCell(clientes[i].getGenero());

            }

            documentoalumno.add(tabla);

            //Salto de linea :)
            addEmptyLine(parrafo, 2);

            //Vamo a agregar una imagen al pdf :v
            Image GraficaDePieCliente = Image.getInstance(GraficaPieCliente);
            GraficaDePieCliente.scaleToFit(200, 180);
            GraficaDePieCliente.setAlignment(Chunk.ALIGN_CENTER);
            documentoalumno.add(GraficaDePieCliente);

            // Iniciar una nueva pagina
            documentoalumno.newPage();
            //Cerramos el documento para evitar errores
            documentoalumno.close();
            abrirArchivo(nombrePDFCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo para crear el pdf de crearPDFVendedor
    public static void crearPDFVendedor() {
        String nombrePDF = "Listado de Vendedores.pdf";
        try {

            Document documentoVendedores = new Document();
            PdfWriter.getInstance(documentoVendedores, new FileOutputStream(nombrePDF));
            //Se abre el documento para ingresar la tabla
            documentoVendedores.open();

            //Se agrega el titulo, el dato del reporte y la tabla con los respectivos datos
            Paragraph parrafo = new Paragraph();
            // agregamos una linea en blanco
            addEmptyLine(parrafo, 1);
            // Le agregamos un encabezado 
            parrafo.add(new Paragraph("Listado de Vendedores", subFont));

            addEmptyLine(parrafo, 1);
            // Crearemos: Reporte generado por: _nombre, _fecha
            parrafo.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold));

            addEmptyLine(parrafo, 1);

            documentoVendedores.add(parrafo);

            //Dicho codigo genera una tabla de 5 columnas 
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(new float[]{15, 30, 40, 12, 10});
            //addCell()  agrega una celda a la tabla, el cambio de fila es automatico
            PdfPCell c1 = new PdfPCell(new Phrase("Código"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Nombre"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Caja"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Ventas"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Genero"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);
            tabla.setWidthPercentage(100);

            for (int i = 0; i < contador_vendedores; i++) {

                //salto automatico en la tabla
                tabla.addCell(Integer.toString(vendedores[i].getCodigo()));
                tabla.addCell(vendedores[i].getNombre());
                tabla.addCell(Integer.toString(vendedores[i].getCaja()));
                tabla.addCell(Integer.toString(vendedores[i].getVenta()));
                tabla.addCell(vendedores[i].getGenero());
            }

            documentoVendedores.add(tabla);

            //Salto de linea
            addEmptyLine(parrafo, 2);
            //Se va a agregar una imagen al pdf
            Image GraficaDeBarCurso = Image.getInstance(GraficaBarVendedor);
            GraficaDeBarCurso.scaleToFit(180, 160);
            GraficaDeBarCurso.setAlignment(Chunk.ALIGN_CENTER);
            documentoVendedores.add(GraficaDeBarCurso);

            // Iniciar una nueva pagina
            documentoVendedores.newPage();
            //Cerramos el documento para evitar errores
            documentoVendedores.close();
            abrirArchivo(nombrePDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void abrirArchivo(String archivo) {
        //abre cualquier tipo de archivo
        try {

            File objetofile = new File(archivo);
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }

    }

    //Metodo para dejar lineas en blanco, por cada parrafo que encontremos, le damos un valor, ese valor
    //Seran los saltos de linea que dará, es mas rapido que estar usando  el Chuck new line :V
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    //Para la parte de las ventas(todo lo referente a las acciones del vendedor)
    public static int buscarClientePorNombre(String nombre) {
        int posicion = -1;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getNombre().equals(nombre)) {
                posicion = i;
                return posicion;
            }
        }
        return posicion;
    }

    public static int buscarClientePorNombreCantRep(String nombre) {
        int cont = 0;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getNombre().equals(nombre)) {
                cont++;
            }
        }
        return cont;
    }

    //Me interesa saber las posiciones donde se encuentra el nombre
    //para compararlas con el nit
    public static int[] buscarClientePorNombreCantRepPos(int tam, String nombre) {
        int[] posNombreRep = new int[tam];
        //System.out.println("Largo de posNombreRep 1= " + posNombreRep.length);
        int cont = 0;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getNombre().equals(nombre)) {
                posNombreRep[cont++] = i;
                //System.out.println("i:" + i);
            }
        }

        return posNombreRep;
    }

    public static int compararDatosNombreYNit(int[] posEvaluar, int posNit) {
        int posicion = -1;
        for (int i = 0; i < posEvaluar.length; i++) {
            if (posEvaluar[i] == posNit) {
                posicion = posEvaluar[i];
                return posicion;
            }
        }
        return posicion;
    }

    public static int buscarClientePorNit(String NIT) {
        int posicion = -1;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getNit().equals(NIT)) {
                posicion = i;
                return posicion;
            }
        }
        return posicion;
    }

    public static int buscarClientePorCorreo(String correo) {
        int posicion = -1;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getCorreo().equals(correo)) {
                posicion = i;
                return posicion;
            }
        }
        return posicion;
    }

    public static int compararDatosNombreYCorreo(int[] posEvaluar, int posCorreo) {
        int posicion = -1;
        for (int i = 0; i < posEvaluar.length; i++) {
            if (posEvaluar[i] == posCorreo) {
                posicion = posEvaluar[i];
                return posicion;
            }
        }
        return posicion;
    }

    public static int buscarClientePorGeneroCantRep(String genero) {
        int cont = 0;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getGenero().equals(genero)) {
                cont++;
            }
        }
        return cont;
    }

    public static int[] buscarClientePorGeneroCantRepPos(int tam, String genero) {
        int[] posGeneroRep = new int[tam];
        //System.out.println("Largo de posGeneroRep 1= " + posGeneroRep.length);
        int cont = 0;
        for (int i = 0; i < contador_clientes; i++) {
            if (clientes[i].getGenero().equals(genero)) {
                posGeneroRep[cont++] = i;
                //System.out.println("i:" + i);
            }
        }

        return posGeneroRep;
    }

    public static int compararDatosGeneroYCorreo(int[] posEvaluar, int posCorreo) {
        int posicion = -1;
        for (int i = 0; i < posEvaluar.length; i++) {
            if (posEvaluar[i] == posCorreo) {
                posicion = posEvaluar[i];
                return posicion;
            }
        }
        return posicion;
    }

    //Empieza la parte de agregar productos a la tabla
    public static Producto buscarCodigoProducto(int codigo_a_buscar) {
        Producto producto_encontrado = null;
        //Buscamos en productos
        for (int i = 0; i < contador_productos; i++) {
            if (productos[i].getCodigo() == codigo_a_buscar) {
                producto_encontrado = productos[i];
                return producto_encontrado;
            }
        }
        return producto_encontrado;
    }

    //Parte donde se escriben los datos y se recuperan
    public static void escribirVentas(Object ventas) {
        try {
            objSalida = new ObjectOutputStream(new FileOutputStream("Ventas.bin"));

            objSalida.writeObject(ventas);

            objSalida.close();

            System.out.println("Objeto (Ventas) Serializado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object cargarDatosVentas() {

        Object object;

        try {

            ois = new ObjectInputStream(new FileInputStream("Ventas.bin"));

            object = ois.readObject();

            return object;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static void serializarVentas() {

        arregloGeneralVentas = (Venta[]) cargarDatosVentas();

        /*
        Al momento de que se cargan los datos en el arreglo de vendedores,
        se tiene que hacer nuevamente un conteo de cuantos vendedores hay,
        esto es para que se le pueda asignar al contador_vendedores, y  seguir
        llevando la cuenta actualizada, luego se tiene que cargar estos datos
        a la tabla.
         */
        if (arregloGeneralVentas == null) {
            System.out.println("No se ha creado el archivo");
            arregloGeneralVentas = new Venta[1000];
        } else if (arregloGeneralVentas != null) {
            //se va a buscar cuantos datos del arreglo de General de Ventas es diferente de nulo
            int contador_ventas_cargadas = 0;
            for (int i = 0; i < arregloGeneralVentas.length; i++) {
                if (arregloGeneralVentas[i] != null) {
                    contador_ventas_cargadas++;
                } else if (arregloGeneralVentas[i] == null) {
                    //En el momento en que el arreglo General de Ventas tenga datos vacios,
                    //es mejor pararlo con un break, para no gastar mas recursos
                    break;
                }
            }
            contador_ventas_general = contador_ventas_cargadas;

            System.out.println("Total de ventas registradas: " + contador_ventas_general);

        }
    }

    //Metodo para crear el pdf de crearPDFVenta *Terminado*
    public static void crearPDFVenta(Venta venta) {
        String nombrePDF = "";
        nombrePDF = "Factura No" + venta.getNoFactura() + ".pdf";
        Producto[] productos_a_imprimir = venta.getProductos_venta();
        int[] cantidades_a_imprimir = venta.getCantidad();
        Double[] subtotales_a_imprimir = venta.getSubTotal();

        int contador_general = 0;
        for (int i = 0; i < productos_a_imprimir.length; i++) {
            if (productos_a_imprimir[i] != null) {
                contador_general++;
            }
        }

        Font fontLugar = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
        try {
            Paragraph parrafo = new Paragraph();
            Paragraph fecha = new Paragraph();

            Document documentoVenta = new Document();
            PdfWriter.getInstance(documentoVenta, new FileOutputStream(nombrePDF));
            //Se abre el documento para ingresar la tabla
            documentoVenta.open();

            // agregamos una linea en blanco
            //Se agrega el titulo, el dato del reporte y la tabla con los respectivos datos
            addEmptyLine(parrafo, 1);

            fecha.add(venta.getFecha());
            fecha.setAlignment(Element.ALIGN_RIGHT);

            documentoVenta.add(fecha);
            // Le agregamos un encabezado 
            try {
                Image logoEmpresa = Image.getInstance("Blue Mall.png");
                logoEmpresa.scaleToFit(160, 120);
                logoEmpresa.setAlignment(Chunk.ALIGN_CENTER);
                documentoVenta.add(logoEmpresa);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" + ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " + ex);
            }
            addEmptyLine(parrafo, 1);

            //Datos del vendedor
            parrafo.add("Vendedor: " + venta.getVendedor().getNombre());
            parrafo.add(new Paragraph("No. Caja: " + venta.getVendedor().getCaja()));

            addEmptyLine(parrafo, 1);
            parrafo.add("No.Factura:" + venta.getNoFactura());

            addEmptyLine(parrafo, 1);

            //            parrafo.add(new Paragraph("Blue Mall", fuenteTitulo));
            //"Nos ubicamos en: " + sucursales[0].getNombre()
            parrafo.add(new Paragraph("Nos ubicamos en: " + sucursales[0].getNombre(), fontLugar));
            parrafo.add("Direccion: " + sucursales[0].getDireccion());
            parrafo.add(new Paragraph("Contáctecnos: " + sucursales[0].getCorreo()));
            parrafo.add(new Paragraph("Telefono: " + sucursales[0].getTelefono()));

            addEmptyLine(parrafo, 2);
            parrafo.add("Cliente: " + venta.getCliente().getNombre() + "\n");
            parrafo.add(new Paragraph("NIT: " + venta.getCliente().getNit()));

            addEmptyLine(parrafo, 2);

            parrafo.add(new Paragraph("Productos: ", fontLugar));
            addEmptyLine(parrafo, 1);

            documentoVenta.add(parrafo);

            //Dicho codigo genera una tabla de 5 columnas 
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(new float[]{15, 40, 25, 25, 25});
            //addCell()  agrega una celda a la tabla, el cambio de fila es automatico
            PdfPCell c1 = new PdfPCell(new Phrase("Código"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Nombre"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Cantidad"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Precio"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);

            c1 = new PdfPCell(new Phrase("Total"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);
            tabla.setWidthPercentage(100);

            for (int i = 0; i < contador_general; i++) {

                //salto automatico en la tabla
                tabla.addCell(String.valueOf(productos_a_imprimir[i].getCodigo()));
                tabla.addCell(String.valueOf(productos_a_imprimir[i].getNombre()));
                tabla.addCell(String.valueOf(cantidades_a_imprimir[i]));
                tabla.addCell(String.valueOf(productos_a_imprimir[i].getPrecio()));
                tabla.addCell(String.valueOf(subtotales_a_imprimir[i]));
            }
            documentoVenta.add(tabla);

            Paragraph parrafo2 = new Paragraph();
            parrafo2.add("total a pagar:                    Q" + venta.getTotal());
            parrafo2.setAlignment(Element.ALIGN_RIGHT);
            documentoVenta.add(parrafo2);

            //Salto de linea
            addEmptyLine(parrafo, 2);

            // Iniciar una nueva pagina
            documentoVenta.newPage();
            //Cerramos el documento para evitar errores
            documentoVenta.close();
            abrirArchivo(nombrePDF);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " o esta abierto el archivo, revise!", "Error", JOptionPane.WARNING_MESSAGE);

            //e.printStackTrace();
        }
    }

    public static Venta[] ventasCoincidentesNit(Venta[] ventasi_i, String nit) {
        Venta[] ventas_coincidentes = null;
        int tam = 0;

        for (int i = 0; i < ventasi_i.length; i++) {
            if (ventasi_i[i] != null) {
                if (ventasi_i[i].getCliente().getNit().equals(nit)) {
                    tam++;
                }
            }

        }
        ventas_coincidentes = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventasi_i.length; i++) {
            if (ventasi_i[i] != null) {
                if (ventasi_i[i].getCliente().getNit().equals(nit)) {
                    ventas_coincidentes[tam++] = ventasi_i[i];
                }
            }
        }
        return ventas_coincidentes;
    }

    public static Venta[] ventasCoincidentesNombre(Venta[] ventasi_i, String nombre) {
        Venta[] ventas_coincidentes = null;
        int tam = 0;

        for (int i = 0; i < ventasi_i.length; i++) {
            if (ventasi_i[i] != null) {
                if (ventasi_i[i].getCliente().getNombre().equals(nombre)) {
                    tam++;
                }
            }

        }
        ventas_coincidentes = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventasi_i.length; i++) {
            if (ventasi_i[i] != null) {
                if (ventasi_i[i].getCliente().getNombre().equals(nombre)) {
                    ventas_coincidentes[tam++] = ventasi_i[i];
                }
            }
        }
        return ventas_coincidentes;
    }

    public static Venta[] ventasCoincidentesFecha(Venta[] ventasi_i, String fecha) {
        Venta[] ventas_coincidentes = null;
        int tam = 0;

        for (int i = 0; i < ventasi_i.length; i++) {
            if (ventasi_i[i] != null) {
                if (ventasi_i[i].getFecha().equals(fecha)) {
                    tam++;
                }
            }

        }
        ventas_coincidentes = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventasi_i.length; i++) {
            if (ventasi_i[i] != null) {
                if (ventasi_i[i].getFecha().equals(fecha)) {
                    ventas_coincidentes[tam++] = ventasi_i[i];
                }
            }
        }
        return ventas_coincidentes;
    }

    public static Venta venta_a_pdf_fac(Venta[] ventas_i, int noFact) {
        Venta venta_encontrada = null;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getNoFactura() == noFact) {
                    venta_encontrada = ventas_i[i];
                    return venta_encontrada;
                }
            }
        }
        return venta_encontrada;
    }

    public static Venta venta_a_pdf_nit(Venta[] ventas_i, String nit) {
        Venta venta_encontrada = null;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getCliente().getNit().equals(nit)) {
                    venta_encontrada = ventas_i[i];
                    return venta_encontrada;
                }
            }
        }
        return venta_encontrada;
    }

    public static int venta_posicion_nit(Venta[] ventas_i, String nit) {
        int posicion_encontrada = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getCliente().getNit().equals(nit)) {
                    posicion_encontrada = i;
                    return posicion_encontrada;
                }
            }
        }
        return posicion_encontrada;
    }

    public static int venta_posicion_nombre(Venta[] ventas_i, String nombre) {
        int posicion_encontrada = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getCliente().getNombre().equals(nombre)) {
                    posicion_encontrada = i;
                    return posicion_encontrada;
                }
            }
        }
        return posicion_encontrada;
    }

    public static Venta venta_a_pdf_nombre(Venta[] ventas_i, String nombre) {
        Venta venta_encontrada = null;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getCliente().getNombre().equals(nombre)) {
                    venta_encontrada = ventas_i[i];
                    return venta_encontrada;
                }
            }
        }
        return venta_encontrada;
    }

    public static int buscar_nit_rep(Venta[] ventas_i, String nit) {
        int contador_coincidencias = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getCliente().getNit().equals(nit)) {
                    contador_coincidencias++;
                }
            }
        }
        return contador_coincidencias;
    }

    public static int buscar_nombre_rep(Venta[] ventas_i, String nombre) {
        int contador_coincidencias = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getCliente().getNombre().equals(nombre)) {
                    contador_coincidencias++;
                }
            }
        }
        return contador_coincidencias;
    }

    public static Venta venta_a_pdf_fecha(Venta[] ventas_i, String fecha) {
        Venta venta_encontrada = null;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getFecha().equals(fecha)) {
                    venta_encontrada = ventas_i[i];
                    return venta_encontrada;
                }
            }
        }
        return venta_encontrada;
    }

    public static int buscar_fecha_rep(Venta[] ventas_i, String fecha) {
        int contador_coincidencias = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                if (ventas_i[i].getFecha().equals(fecha)) {
                    contador_coincidencias++;
                }
            }
        }
        return contador_coincidencias;
    }

    public static Venta venta_a_pdf_fecha(Venta[] ventas_i, int posfecha, String fecha) {
        Venta venta_encontrada = null;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {

                if (ventas_i[i].getFecha().equals(fecha) && i == posfecha) {
                    venta_encontrada = ventas_i[i];
                    return venta_encontrada;
                }

            }
        }
        return venta_encontrada;
    }

    public static Venta busqueda_nit_fac(Venta[] ventas_i, String nit, int factura) {
        Venta venta_nit = null, venta_factura = null, venta_a_enviar = null;
        String venta_nit1 = "", venta_nit2 = "";
        int venta_factura1 = 0, venta_factura2 = 0;

        venta_nit = venta_a_pdf_nit(ventas_i, nit);
        venta_factura = venta_a_pdf_fac(ventas_i, factura);

        if (venta_nit != null && venta_factura != null) {
            venta_nit1 = venta_nit.getCliente().getNit();
            venta_nit2 = venta_factura.getCliente().getNit();

            venta_factura1 = venta_nit.getNoFactura();
            venta_factura2 = venta_factura.getNoFactura();

            if (venta_nit1.equals(venta_nit2) && venta_factura1 == venta_factura2) {
                venta_a_enviar = venta_nit;
                return venta_a_enviar;
            }
        }
        return venta_a_enviar;
    }

    public static Venta[] ventasCoincidentesFactNit(Venta[] ventas_i, int factura, String nit) {
        Venta[] coincidencias = null;

        boolean factura_a_buscar = false, buscar_a_nit = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //factura y nit
                factura_a_buscar = ventas_i[i].getNoFactura() == factura;
                buscar_a_nit = ventas_i[i].getCliente().getNit().equals(nit);
                if (factura_a_buscar && buscar_a_nit) {
                    tam++;
                }
            }
        }

        coincidencias = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //factura y nit
                factura_a_buscar = ventas_i[i].getNoFactura() == factura;
                buscar_a_nit = ventas_i[i].getCliente().getNit().equals(nit);
                if (factura_a_buscar && buscar_a_nit) {
                    coincidencias[tam++] = ventas_i[i];
                }
            }
        }

        return coincidencias;
    }

    public static Venta[] ventasCoincidentesFactNombre(Venta[] ventas_i, int factura, String nombre) {
        Venta[] coincidencias = null;

        boolean factura_a_buscar = false, buscar_a_nombre = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //factura y nit
                factura_a_buscar = ventas_i[i].getNoFactura() == factura;
                buscar_a_nombre = ventas_i[i].getCliente().getNombre().equals(nombre);
                if (factura_a_buscar && buscar_a_nombre) {
                    tam++;
                }
            }
        }

        coincidencias = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //factura y nit
                factura_a_buscar = ventas_i[i].getNoFactura() == factura;
                buscar_a_nombre = ventas_i[i].getCliente().getNombre().equals(nombre);
                if (factura_a_buscar && buscar_a_nombre) {
                    coincidencias[tam++] = ventas_i[i];
                }
            }
        }

        return coincidencias;
    }

    public static Venta[] ventasCoincidentesFactFecha(Venta[] ventas_i, int factura, String fecha) {
        Venta[] coincidencias = null;

        boolean factura_a_buscar = false, buscar_a_fecha = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //factura y nit
                factura_a_buscar = ventas_i[i].getNoFactura() == factura;
                buscar_a_fecha = ventas_i[i].getFecha().equals(fecha);
                if (factura_a_buscar && buscar_a_fecha) {
                    tam++;
                }
            }
        }

        coincidencias = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //factura y nit
                factura_a_buscar = ventas_i[i].getNoFactura() == factura;
                buscar_a_fecha = ventas_i[i].getFecha().equals(fecha);
                if (factura_a_buscar && buscar_a_fecha) {
                    coincidencias[tam++] = ventas_i[i];
                }
            }
        }

        return coincidencias;
    }

    public static Venta[] ventasCoincidentesNitNombre(Venta[] ventas_i, String nit, String nombre) {
        Venta[] coincidencias = null;

        boolean nit_a_buscar = false, nombre_a_buscar = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nit y nombre
                nit_a_buscar = ventas_i[i].getCliente().getNit().equalsIgnoreCase(nit);
                nombre_a_buscar = ventas_i[i].getCliente().getNombre().equals(nombre);
                if (nit_a_buscar && nombre_a_buscar) {
                    tam++;
                }
            }
        }

        coincidencias = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nit y nombre
                nit_a_buscar = ventas_i[i].getCliente().getNit().equalsIgnoreCase(nit);
                nombre_a_buscar = ventas_i[i].getCliente().getNombre().equals(nombre);
                if (nit_a_buscar && nombre_a_buscar) {
                    coincidencias[tam++] = ventas_i[i];
                }
            }
        }

        return coincidencias;
    }

    public static Venta[] ventasCoincidentesNitFecha(Venta[] ventas_i, String nit, String fecha) {
        Venta[] coincidencias = null;

        boolean nit_a_buscar = false, fecha_a_buscar = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nit y fecha
                nit_a_buscar = ventas_i[i].getCliente().getNit().equalsIgnoreCase(nit);
                fecha_a_buscar = ventas_i[i].getFecha().equals(fecha);
                if (nit_a_buscar && fecha_a_buscar) {
                    tam++;
                }
            }
        }

        coincidencias = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nit y fecha
                nit_a_buscar = ventas_i[i].getCliente().getNit().equalsIgnoreCase(nit);
                fecha_a_buscar = ventas_i[i].getFecha().equals(fecha);
                if (nit_a_buscar && fecha_a_buscar) {
                    coincidencias[tam++] = ventas_i[i];
                }
            }
        }

        return coincidencias;
    }

    public static Venta[] ventasCoincidentesNombreFecha(Venta[] ventas_i, String nombre, String fecha) {
        Venta[] coincidencias = null;

        boolean nombre_a_buscar = false, fecha_a_buscar = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nombre y fecha
                nombre_a_buscar = ventas_i[i].getCliente().getNombre().equalsIgnoreCase(nombre);
                fecha_a_buscar = ventas_i[i].getFecha().equals(fecha);
                if (nombre_a_buscar && fecha_a_buscar) {
                    tam++;
                }
            }
        }

        coincidencias = new Venta[tam];
        tam = 0;
        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nombre y fecha
                nombre_a_buscar = ventas_i[i].getCliente().getNombre().equalsIgnoreCase(nombre);
                fecha_a_buscar = ventas_i[i].getFecha().equals(fecha);
                if (nombre_a_buscar && fecha_a_buscar) {
                    coincidencias[tam++] = ventas_i[i];
                }
            }
        }

        return coincidencias;
    }

    public static Venta busqueda_nit_nombre(Venta[] ventas_i, String nit, String nombre) {
        Venta venta_nit = null, venta_nombre = null, venta_a_enviar = null;
        String venta_nit1 = "", venta_nit2 = "";
        String venta_nombre1 = "", venta_nombre2 = "";

        venta_nit = venta_a_pdf_nit(ventas_i, nit);
        venta_nombre = venta_a_pdf_nombre(ventas_i, nombre);

        if (venta_nit != null && venta_nombre != null) {
            venta_nit1 = venta_nit.getCliente().getNit();
            venta_nit2 = venta_nombre.getCliente().getNit();

            venta_nombre1 = venta_nit.getCliente().getNombre();
            venta_nombre2 = venta_nombre.getCliente().getNombre();

            if (venta_nit1.equals(venta_nit2) && venta_nombre1.equals(venta_nombre2)) {
                venta_a_enviar = venta_nit;
                return venta_a_enviar;
            }
        }
        return venta_a_enviar;
    }

    public static Venta busqueda_nit_fecha(Venta[] ventas_i, String nit, String fecha) {
        Venta venta_nit = null, venta_fecha = null, venta_a_enviar = null;
        String venta_nit1 = "", venta_nit2 = "";
        String venta_fecha1 = "", venta_fecha2 = "";
        int indice_fecha = -1;

        venta_nit = venta_a_pdf_nit(ventas_i, nit);

        if (venta_nit != null) {
            indice_fecha = venta_posicion_nit(ventas_i, nit);
            if (indice_fecha != -1) {
                venta_fecha = venta_a_pdf_fecha(ventas_i, indice_fecha, fecha);

                if (venta_fecha != null) {
                    venta_nit1 = venta_nit.getCliente().getNit();
                    venta_nit2 = venta_fecha.getCliente().getNit();

                    venta_fecha1 = venta_nit.getFecha();
                    venta_fecha2 = venta_fecha.getFecha();

                    if (venta_nit1.equals(venta_nit2) && venta_fecha1.equals(venta_fecha2)) {
                        venta_a_enviar = venta_nit;
                        return venta_a_enviar;
                    }
                }
            }
        }
        return venta_a_enviar;
    }

    public static Venta busqueda_nombre_fecha(Venta[] ventas_i, String nombre, String fecha) {
        Venta venta_nombre = null, venta_fecha = null, venta_a_enviar = null;
        String venta_nombre1 = "", venta_nombre2 = "";
        String venta_fecha1 = "", venta_fecha2 = "";
        int indice_fecha = -1;

        venta_nombre = venta_a_pdf_nombre(ventas_i, nombre);
        if (venta_nombre != null) {
            indice_fecha = venta_posicion_nombre(ventas_i, nombre);
            if (indice_fecha != -1) {
                venta_fecha = venta_a_pdf_fecha(ventas_i, indice_fecha, fecha);
                if (venta_fecha != null) {
                    venta_nombre1 = venta_nombre.getCliente().getNombre();
                    venta_nombre2 = venta_fecha.getCliente().getNombre();

                    venta_fecha1 = venta_nombre.getFecha();
                    venta_fecha2 = venta_fecha.getFecha();

                    if (venta_nombre1.equals(venta_nombre2) && venta_fecha1.equals(venta_fecha2)) {
                        venta_a_enviar = venta_nombre;
                        return venta_a_enviar;
                    }

                }

            }

        }
        return venta_a_enviar;
    }

    public static Venta[] ventasCoincidentesNFNtFe(Venta[] ventas_i, int factura, String nit, String fecha) {
        Venta[] coincidentes = null;
        boolean nfab = false, fab = false, nab = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nombre y fecha
                nfab = ventas_i[i].getNoFactura() == factura;
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                fab = ventas_i[i].getFecha().equals(fecha);
                if (nfab && fab && nab) {
                    tam++;
                }
            }
        }
        //System.out.println("tam: " + tam);
        coincidentes = new Venta[tam];
        tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                nfab = ventas_i[i].getNoFactura() == factura;
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                fab = ventas_i[i].getFecha().equals(fecha);
                if (nfab && fab && fab) {
                    coincidentes[tam++] = ventas_i[i];
                }
            }
        }

        return coincidentes;
    }

    public static Venta[] ventasCoincidentesNFNomFe(Venta[] ventas_i, int factura, String nombre, String fecha) {
        Venta[] coincidentes = null;
        boolean nfab = false, fab = false, noab = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                // factura, nombre y fecha
                nfab = ventas_i[i].getNoFactura() == factura;
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                fab = ventas_i[i].getFecha().equals(fecha);
                if (nfab && fab && noab) {
                    tam++;
                }
            }
        }
        //System.out.println("tam: " + tam);
        coincidentes = new Venta[tam];
        tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                // factura, nombre y fecha
                nfab = ventas_i[i].getNoFactura() == factura;
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                fab = ventas_i[i].getFecha().equals(fecha);
                if (nfab && noab && fab) {
                    coincidentes[tam++] = ventas_i[i];
                }
            }
        }

        return coincidentes;
    }

    public static Venta[] ventasCoincidentesNtNfNm(Venta[] ventas_i, String nit, int factura, String nombre) {
        Venta[] coincidentes = null;
        boolean nab = false, fab = false, noab = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                // nit, factura y nombre
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                fab = ventas_i[i].getNoFactura() == factura;
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                if (nab && fab && noab) {
                    tam++;
                }
            }
        }
        //System.out.println("tam: " + tam);
        coincidentes = new Venta[tam];
        tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                // nit, factura y nombre
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                fab = ventas_i[i].getNoFactura() == factura;
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                if (nab && fab && noab) {
                    coincidentes[tam++] = ventas_i[i];
                }
            }
        }

        return coincidentes;
    }

    public static Venta[] ventasCoincidentesNmFeNt(Venta[] ventas_i, String nombre, String fecha, String nit) {
        Venta[] coincidentes = null;
        boolean noab = false, fab = false, nab = false;
        int tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nombre, fecha y nit
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                fab = ventas_i[i].getFecha().equals(fecha);
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                if (noab && fab && nab) {
                    tam++;
                }
            }
        }
        //System.out.println("tam: " + tam);
        coincidentes = new Venta[tam];
        tam = 0;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nombre, fecha y nit
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                fab = ventas_i[i].getFecha().equals(fecha);
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                if (noab && fab && nab) {
                    coincidentes[tam++] = ventas_i[i];
                }
            }
        }
        return coincidentes;
    }

    public static Venta ventaCoincidente(Venta[] ventas_i, int factura, String nit, String nombre, String fecha) {
        Venta venta_coincidente = null;
        boolean fab = false, nab = false, noab = false, feab;

        for (int i = 0; i < ventas_i.length; i++) {
            if (ventas_i[i] != null) {
                //nombre, fecha y nit
                fab = ventas_i[i].getNoFactura() == factura;
                nab = ventas_i[i].getCliente().getNit().equals(nit);
                noab = ventas_i[i].getCliente().getNombre().equals(nombre);
                feab = ventas_i[i].getFecha().equals(fecha);
                if (noab && fab && nab && feab) {
                    venta_coincidente = ventas_i[i];
                    return venta_coincidente;
                }
            }
        }

        return venta_coincidente;
    }

}
