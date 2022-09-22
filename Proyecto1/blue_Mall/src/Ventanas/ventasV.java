`package Ventanas;

import static Ventanas.ControlarDatos.arregloGeneralVentas;
import static Ventanas.ControlarDatos.buscarClientePorCorreo;
import static Ventanas.ControlarDatos.buscarClientePorGeneroCantRep;
import static Ventanas.ControlarDatos.buscarClientePorGeneroCantRepPos;
import static Ventanas.ControlarDatos.buscarClientePorNit;
import static Ventanas.ControlarDatos.buscarClientePorNombre;
import static Ventanas.ControlarDatos.buscarClientePorNombreCantRep;
import static Ventanas.ControlarDatos.buscarClientePorNombreCantRepPos;
import static Ventanas.ControlarDatos.buscarCodigoProducto;
import static Ventanas.ControlarDatos.clientes;
import static Ventanas.ControlarDatos.compararDatosGeneroYCorreo;
import static Ventanas.ControlarDatos.compararDatosNombreYCorreo;
import static Ventanas.ControlarDatos.compararDatosNombreYNit;
import static Ventanas.ControlarDatos.contador_clientes;
import static Ventanas.ControlarDatos.contador_ventas_general;
import static Ventanas.ControlarDatos.crearPDFVenta;
import static Ventanas.ControlarDatos.escribirVendedores;
import static Ventanas.ControlarDatos.escribirVentas;
import static Ventanas.ControlarDatos.mayorCantidadProductos;
import static Ventanas.ControlarDatos.ordenarPorCodigoCliente;
import static Ventanas.ControlarDatos.ordenarPorCodigoProducto;
import static Ventanas.ControlarDatos.productos;
import static Ventanas.ControlarDatos.vendedores;
import static Ventanas.login.numFactura;
import clasesPrincipales.Cliente;
import clasesPrincipales.Producto;
import clasesPrincipales.Vendedor;
import clasesPrincipales.Venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static Ventanas.ControlarDatos.*;

public class ventasV extends JFrame implements ActionListener, MouseListener {

    //Hay unos datos a eliminar tomarlo en cuenta...
    Producto producto_a_agregar = null;
    Producto[] productos_a_agregar = new Producto[45];
    int[] cantidades = new int[45];
    int contador_cantidades = 0;
    Cliente cliente_compra = null;
    Venta venta_a_realizar;
    Double[] subTotales = new Double[45];
    int cont_productos_agregar = 0;
    int cont_sub_totales = 0;
    String nombre_cliente = "";
    int posicion_cliente = 0;
    int cantidad_a_descontar = 0;
    String fechaActual = LocalDate.now().toString();

    //Se crea el agregado de ventanas como pestañas
    public JTabbedPane pestañas;

    //Paneles
    JPanel nuevaVenta, VentasRealizadas;

    //Paneles de los paneles principales
    JPanel panel1, panel2, panel3;

    //Botones
    JButton agregarProducto, aplicarFiltro, nuevoCliente, vender, retornoLoginV1, retornoLoginV2, aplicarFiltroVentas;

    //Cajas de texto
    JTextField t1, t2, t3, t4, codigoProducto, cantidadProducto, t5, t6, t7, t8;

    //Etiquetas
    JLabel seleccionarCliente, filtradosPor, nombre, correo, NIT, genero, filtrados, cliente;
    JLabel agregarProductos, codigo, cantidad, fecha, noFactura, total, totalLabel, listadoGeneral;
    JLabel numeroFacturaJ, nitJ, nombreJ, fechaJ;

    //Combo de datos
    JComboBox nombreClientes;

    //Tabla de productos a vender
    JTable tablaproductos_a_vender, tabla_ventas_realizadas;
    DefaultTableModel mTS, mTVR;
    Object[][] datos, datos_ventas_realizadas;

    static Vendedor vendedor_a_enviar;

    Double subtotal = 0.0, totalVenta = 0.0;

    public void cargarNombresClientes() {
        nombreClientes.addItem("");
        ordenarPorCodigoCliente();
        for (int i = 0; i < contador_clientes; i++) {
            nombreClientes.addItem(clientes[i].getNombre());
        }
    }

    public ventasV(Vendedor vendedor_cargado) {
        vendedor_a_enviar = vendedor_cargado;
        String nombreVendedor = "¡BIENVENIDO " + vendedor_cargado.getNombre() + " !";
        pestañas = new JTabbedPane();

        nuevaVenta = new JPanel();
        nuevaVenta.setLayout(null);

        VentasRealizadas = new JPanel();
        VentasRealizadas.setLayout(null);

        pestañas.addTab("Nueva Venta", nuevaVenta);
        pestañas.addTab("Ventas", VentasRealizadas);

        PestañaNuevaVenta(nuevaVenta);
        PestañaVentas(VentasRealizadas);

        this.setTitle(nombreVendedor);
        this.setSize(950, 675);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.getContentPane().add(pestañas);

    }

    private void PestañaNuevaVenta(JPanel nuevaVenta) {
        Font Fontcajas = new Font("ARIAL", 0, 15);

        nuevaVenta.setBorder(new LineBorder(Color.blue));
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(15, 15, 915, 250);
        panel1.setBackground(new Color(124, 240, 240));
        panel1.setBorder(new MatteBorder(3, 3, 3, 3, Color.BLACK));
        nuevaVenta.add(panel1);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(15, 285, 915, 270);
        panel2.setBackground(new Color(124, 240, 240));
        panel2.setBorder(new MatteBorder(3, 3, 3, 3, Color.BLACK));
        nuevaVenta.add(panel2);

        seleccionarCliente = new JLabel("  Seleccionar Cliente");
        seleccionarCliente.setForeground(Color.BLUE);
        seleccionarCliente.setBounds(0, 0, 155, 35);
        seleccionarCliente.setFont(new Font("Arial", Font.BOLD, 15));
        seleccionarCliente.setBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK));
        panel1.add(seleccionarCliente);

        //Declarando los labels
        filtradosPor = new JLabel("Filtrar por:");
        filtradosPor.setBounds(35, 50, 100, 30);
        filtradosPor.setVisible(true);
        Font font = filtradosPor.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.SIZE, new Float(15));
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        filtradosPor.setFont(font.deriveFont(attributes));
        panel1.add(filtradosPor);

        nombre = new JLabel("Nombre:");
        nombre.setBounds(155, 50, 100, 30);
        nombre.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        nombre.setVisible(true);
        panel1.add(nombre);

        NIT = new JLabel("NIT:");
        NIT.setBounds(460, 50, 50, 30);
        NIT.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        NIT.setVisible(true);
        panel1.add(NIT);

        correo = new JLabel("Correo:");
        correo.setBounds(155, 100, 50, 30);
        correo.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        correo.setVisible(true);
        panel1.add(correo);

        genero = new JLabel("Genero:");
        genero.setBounds(460, 100, 75, 30);
        genero.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        genero.setVisible(true);
        panel1.add(genero);

        //Declarando el textfield, osea la cajita donde se escribe
        t1 = new JTextField("");
        t1.setBounds(215, 50, 200, 30);
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setVisible(true);
        t1.setFont(Fontcajas);
        panel1.add(t1);

        t2 = new JTextField("");
        t2.setBounds(535, 50, 200, 30);
        t2.setHorizontalAlignment(SwingConstants.CENTER);
        t2.setVisible(true);
        t2.setFont(Fontcajas);
        panel1.add(t2);

        t3 = new JTextField("");
        t3.setBounds(215, 100, 200, 30);
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setVisible(true);
        t3.setFont(Fontcajas);
        panel1.add(t3);

        t4 = new JTextField("");
        t4.setBounds(535, 100, 200, 30);
        t4.setHorizontalAlignment(SwingConstants.CENTER);
        t4.setVisible(true);
        t4.setFont(Fontcajas);
        panel1.add(t4);

        aplicarFiltro = new JButton("Aplicar Filtro");
        aplicarFiltro.setHorizontalTextPosition(SwingConstants.CENTER);
        aplicarFiltro.setBounds(215, 150, 525, 30);
        aplicarFiltro.setVisible(true);
        aplicarFiltro.addActionListener(this);
        panel1.add(aplicarFiltro);

        filtrados = new JLabel("Filtrados:");
        filtrados.setBounds(35, 200, 100, 30);
        filtrados.setFont(font.deriveFont(attributes));
        filtrados.setVisible(true);
        panel1.add(filtrados);

        cliente = new JLabel("Cliente:");
        cliente.setBounds(215, 200, 100, 30);
        cliente.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        cliente.setVisible(true);
        panel1.add(cliente);

        nombreClientes = new JComboBox();
        nombreClientes.setBounds(275, 200, 300, 30);
        cargarNombresClientes();
        //Casteo explicito, para poder centrar los datos
        ((JLabel) nombreClientes.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        nombreClientes.setFont(new Font("Arial", Font.BOLD, 15));
        nombreClientes.setVisible(true);
        panel1.add(nombreClientes);

        nuevoCliente = new JButton("Nuevo Cliente");
        nuevoCliente.setBounds(590, 200, 150, 30);
        nuevoCliente.setVisible(true);
        nuevoCliente.addActionListener(this);
        panel1.add(nuevoCliente);

        //todo lo que respecta al 2do panel
        agregarProductos = new JLabel("  Agregar Productos");
        agregarProductos.setForeground(Color.BLUE);
        agregarProductos.setBounds(0, 0, 155, 35);
        agregarProductos.setFont(new Font("Arial", Font.BOLD, 15));
        agregarProductos.setBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK));
        panel2.add(agregarProductos);

        fecha = new JLabel("Fecha: " + fechaActual);
        fecha.setBounds(475, 3, 150, 30);
        fecha.setFont(new Font("Arial", Font.BOLD, 15));;
        fecha.setVisible(true);
        panel2.add(fecha);

        noFactura = new JLabel("No.         " + numFactura);
        noFactura.setBounds(700, 3, 150, 30);
        noFactura.setVisible(true);
        noFactura.setFont(new Font("Arial", Font.BOLD, 15));;
        panel2.add(noFactura);

        codigo = new JLabel("Código:");
        codigo.setBounds(155, 45, 70, 30);
        codigo.setFont(new Font("Arial", Font.ITALIC, 15));
        codigo.setVisible(true);
        panel2.add(codigo);

        codigoProducto = new JTextField();
        codigoProducto.setBounds(220, 45, 180, 25);
        codigoProducto.setHorizontalAlignment(SwingConstants.CENTER);
        codigoProducto.setFont(Fontcajas);
        codigoProducto.setVisible(true);
        panel2.add(codigoProducto);

        cantidad = new JLabel("Cantidad:");
        cantidad.setBounds(430, 45, 75, 30);
        cantidad.setFont(new Font("Arial", Font.ITALIC, 15));
        cantidad.setVisible(true);
        panel2.add(cantidad);

        cantidadProducto = new JTextField();
        cantidadProducto.setBounds(510, 45, 180, 25);
        cantidadProducto.setHorizontalAlignment(SwingConstants.CENTER);
        cantidadProducto.setFont(Fontcajas);
        cantidadProducto.setVisible(true);
        panel2.add(cantidadProducto);

        agregarProducto = new JButton("Agregar");
        agregarProducto.setBounds(700, 45, 110, 25);
        agregarProducto.setVisible(true);
        agregarProducto.addActionListener(this);
        panel2.add(agregarProducto);

        datos = new Object[0][5];
        String[] columnas = {"Código", "Nombre", "Cantidad", "Precio", "Subtotal"};
        tablaproductos_a_vender = new JTable();

        //Se hace uso de la tabla model para validar que el admin no pueda realizar
        //ningun tipo de cambio por medio de las tablas!
        mTS = new DefaultTableModel(datos, columnas) {

            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tablaproductos_a_vender.setModel(mTS);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = tablaproductos_a_vender.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            tablaproductos_a_vender.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }

        //Se debe de crear un scroll para poder visualizar los datos
        JScrollPane spV = new JScrollPane(tablaproductos_a_vender);
        spV.setBounds(155, 85, 655, 100);
        spV.setVisible(true);
        // Por ultimo agregamos el ScrollPane que tiene dentro de la ventana.
        panel2.add(spV);

        vender = new JButton("Vender");
        vender.setBounds(155, 200, 340, 30);
        vender.setBackground(Color.GREEN);
        vender.setFont(new Font("Arial", Font.BOLD, 15));
        vender.setVisible(true);
        vender.addActionListener(this);
        panel2.add(vender);

        total = new JLabel("Total:");
        total.setBounds(600, 200, 75, 30);
        total.setFont(new Font("Arial", Font.BOLD, 15));
        total.setVisible(true);
        panel2.add(total);

        totalLabel = new JLabel();
        totalLabel.setBounds(670, 200, 120, 30);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setOpaque(true);
        totalLabel.setBackground(Color.yellow);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setForeground(Color.RED);
        totalLabel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        totalLabel.setVisible(true);
        panel2.add(totalLabel);

        retornoLoginV1 = new JButton("Cerrar Sesión");
        retornoLoginV1.setBounds(800, 560, 125, 50);
        retornoLoginV1.setVisible(true);
        retornoLoginV1.setBackground(new Color(255, 50, 50));
        retornoLoginV1.setFont(new Font("ARIAL", Font.BOLD, 15));
        retornoLoginV1.addActionListener(this);
        nuevaVenta.add(retornoLoginV1);

    }

    //trabajando...
    private void PestañaVentas(JPanel VentasRealizadas) {
        Font Fontcajas = new Font("ARIAL", 0, 15);
        VentasRealizadas.setBorder(new LineBorder(Color.blue));
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBounds(15, 15, 915, 530);
        panel3.setBackground(new Color(124, 240, 240));
        panel3.setBorder(new MatteBorder(3, 3, 3, 3, Color.BLACK));
        VentasRealizadas.add(panel3);

        //todo lo que respecta al 2do panel
        listadoGeneral = new JLabel("  Listado General");
        listadoGeneral.setForeground(Color.BLUE);
        listadoGeneral.setBounds(0, 0, 145, 35);
        listadoGeneral.setFont(new Font("Arial", Font.BOLD, 17));
        listadoGeneral.setBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK));
        panel3.add(listadoGeneral);

        //Declarando los labels
        filtradosPor = new JLabel("Filtrar por:");
        filtradosPor.setBounds(35, 50, 100, 30);
        filtradosPor.setVisible(true);
        Font font = filtradosPor.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.SIZE, new Float(15));
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        filtradosPor.setFont(font.deriveFont(attributes));
        panel3.add(filtradosPor);

        numeroFacturaJ = new JLabel("No. Factura:");
        numeroFacturaJ.setBounds(155, 50, 150, 30);
        numeroFacturaJ.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        numeroFacturaJ.setVisible(true);
        panel3.add(numeroFacturaJ);

        nitJ = new JLabel("NIT:");
        nitJ.setBounds(475, 50, 50, 30);
        nitJ.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        nitJ.setVisible(true);
        panel3.add(nitJ);

        nombreJ = new JLabel("Nombre:");
        nombreJ.setBounds(155, 100, 150, 30);
        nombreJ.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        nombreJ.setVisible(true);
        panel3.add(nombreJ);

        fechaJ = new JLabel("Fecha:");
        fechaJ.setBounds(475, 100, 75, 30);
        fechaJ.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        fechaJ.setVisible(true);
        panel3.add(fechaJ);

        //Declarando el textfield, osea la cajita donde se escribe
        //No. factura
        t5 = new JTextField("");
        t5.setBounds(240, 50, 200, 30);
        t5.setHorizontalAlignment(SwingConstants.CENTER);
        t5.setVisible(true);
        t5.setFont(Fontcajas);
        panel3.add(t5);

        //NIT
        t6 = new JTextField("");
        t6.setBounds(535, 50, 200, 30);
        t6.setHorizontalAlignment(SwingConstants.CENTER);
        t6.setVisible(true);
        t6.setFont(Fontcajas);
        panel3.add(t6);

        //NOMBRE
        t7 = new JTextField("");
        t7.setBounds(240, 100, 200, 30);
        t7.setHorizontalAlignment(SwingConstants.CENTER);
        t7.setVisible(true);
        t7.setFont(Fontcajas);
        panel3.add(t7);

        //Fecha
        t8 = new JTextField("");
        t8.setBounds(535, 100, 200, 30);
        t8.setHorizontalAlignment(SwingConstants.CENTER);
        t8.setVisible(true);
        t8.setFont(Fontcajas);
        panel3.add(t8);

        aplicarFiltroVentas = new JButton("Aplicar Filtro");
        aplicarFiltroVentas.setHorizontalTextPosition(SwingConstants.CENTER);
        aplicarFiltroVentas.setBounds(155, 150, 585, 30);
        aplicarFiltroVentas.setVisible(true);
        aplicarFiltroVentas.addActionListener(this);
        panel3.add(aplicarFiltroVentas);

        filtrados = new JLabel("Filtrados:");
        filtrados.setBounds(35, 200, 100, 30);
        filtrados.setFont(font.deriveFont(attributes));
        filtrados.setVisible(true);
        panel3.add(filtrados);

        datos_ventas_realizadas = convertirDatosVentas();

        String[] columnas = {"No. Factura", "NIT", "Nombre", "Fecha", "Total", "Acciones"};
        tabla_ventas_realizadas = new JTable();

        //Se hace uso de la tabla model para validar que el admin no pueda realizar
        //ningun tipo de cambio por medio de las tablas!
        mTVR = new DefaultTableModel(datos_ventas_realizadas, columnas) {
            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tabla_ventas_realizadas.setModel(mTVR);
        tabla_ventas_realizadas.addMouseListener(this);

        tabla_ventas_realizadas.setDefaultRenderer(Object.class, new Render());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = tabla_ventas_realizadas.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            if (columnIndex != 5) {
                tabla_ventas_realizadas.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
            }
        }

        //Se debe de crear un scroll para poder visualizar los datos
        JScrollPane spV = new JScrollPane(tabla_ventas_realizadas);
        spV.setBounds(135, 250, 655, 250);
        spV.setVisible(true);
        // Por ultimo agregamos el ScrollPane que tiene dentro de la ventana.
        panel3.add(spV);

        retornoLoginV2 = new JButton("Cerrar Sesión");
        retornoLoginV2.setBounds(795, 550, 125, 50);
        retornoLoginV2.setVisible(true);
        retornoLoginV2.setBackground(new Color(255, 50, 50));
        retornoLoginV2.setFont(new Font("ARIAL", Font.BOLD, 15));
        retornoLoginV2.addActionListener(this);
        VentasRealizadas.add(retornoLoginV2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String nombre_a_buscar = "", nit_a_buscar = "", correo_a_buscar = "", genero_a_buscar = "";
        int cant_nombre_encontrado = 0, cant_genero_encontrado = 0;
        int pos_nombre_encontrado = -1, pos_nit_encontrado = -1;
        int pos_correo_encontrado = -1;
        int[] posNombreRep, posGeneroRep;

        //----------------------------------------------------------------
        String coP = codigoProducto.getText(), caP = cantidadProducto.getText();
        int codigo_producto_a_buscar = -1;
        //----------------------------------------------------------------
        if (ae.getSource() == nuevoCliente) {
            nuevoClienteVentas cliente_nuevo = new nuevoClienteVentas(vendedor_a_enviar);
            cliente_nuevo.setVisible(true);
            dispose();
        }

        if (ae.getSource() == retornoLoginV1) {
            login regresar_a_login = new login();
            regresar_a_login.setVisible(true);
            dispose();
        }

        if (ae.getSource() == retornoLoginV2) {
            login regresar_a_login = new login();
            regresar_a_login.setVisible(true);
            dispose();
        }

        if (ae.getSource() == aplicarFiltro) {
            //Al momento de aplicar el filtro, la idea es que independientemente
            //(cabe agregar, que si el espacio de la caja es igual a "" osea
            // vacio, no se tomara encuenta para la busqueda.)
            //de que cajas se llenen el metodo tendra que buscar al cliente y
            //Colocarlo en el JComboBox, si ahora bien se repite, en el caso de 
            //que los nombres se repitan, se tendria que mostrar un mensaje diciendo
            //que debe ingresar en el campo de nit o en el campo de correo el dato
            //correspondiente, para poder encontrar al cliente deseado, mas facil
            nombre_a_buscar = t1.getText();
            nit_a_buscar = t2.getText();
            correo_a_buscar = t3.getText();
            genero_a_buscar = t4.getText();

            //valida cuando no se ha llenado ningun valor
            if (nombre_a_buscar.equals("") && nit_a_buscar.equals("") && correo_a_buscar.equals("") && genero_a_buscar.equals("")) {
                JOptionPane.showMessageDialog(this, "No se lleno ningun campo");
            } else {
                //Caso en el que a las 4 cajas se les lleno de informacion
                if (!nombre_a_buscar.equals("") && !nit_a_buscar.equals("") && !correo_a_buscar.equals("") && !genero_a_buscar.equals("")) {
                    pos_correo_encontrado = buscarClientePorCorreo(correo_a_buscar);
                    pos_nit_encontrado = buscarClientePorNit(nit_a_buscar);

                    cant_genero_encontrado = buscarClientePorGeneroCantRep(genero_a_buscar);
                    posGeneroRep = new int[cant_genero_encontrado];
                    posGeneroRep = buscarClientePorGeneroCantRepPos(cant_genero_encontrado, genero_a_buscar);
                    int posComprobadaGenero = compararDatosGeneroYCorreo(posGeneroRep, pos_correo_encontrado);

                    cant_nombre_encontrado = buscarClientePorNombreCantRep(nombre_a_buscar);
                    posNombreRep = new int[cant_nombre_encontrado];
                    posNombreRep = buscarClientePorNombreCantRepPos(cant_nombre_encontrado, nombre_a_buscar);
                    int posComprobadaNombre = compararDatosNombreYNit(posNombreRep, pos_nit_encontrado);

                    if (pos_nit_encontrado == pos_correo_encontrado) {

                        if (pos_correo_encontrado == posComprobadaGenero) {

                            if (posComprobadaGenero == posComprobadaNombre) {
                                //Llegados aqui, ya se puede seleccionar el cliente
                                nombreClientes.setSelectedIndex(posComprobadaNombre + 1);
                            } else {
                                JOptionPane.showMessageDialog(this, "Esta ingresando mal el campo de genero o nombre, revise porfavor!");
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Esta ingresando mal el campo de correo o genero, revise porfavor!");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Esta ingresando mal el campo de nit o correo, revise porfavor!");
                    }

                }

                //Se hace un cambio de switch, y lo que quiere decir es:
                //Si el nombre_a_buscar es diferente de vacio  == true
                if (!nombre_a_buscar.equals("")) {
                    cant_nombre_encontrado = buscarClientePorNombreCantRep(nombre_a_buscar);
                    //cantidad de nombres sea mayor a uno y las demas cajas esten vacias
                    if (cant_nombre_encontrado > 1 && nit_a_buscar.equals("") && correo_a_buscar.equals("") && genero_a_buscar.equals("")) {
                        JOptionPane.showMessageDialog(this, "Se encontraron " + cant_nombre_encontrado
                                + " coincidencias, por favor ingrese mas datos, para especificar al"
                                + " cliente a encontrar");
                        //cantidad de nombres sea solo uno y las demas cajas esten vacias
                    } else if (cant_nombre_encontrado <= 1 && nit_a_buscar.equals("") && correo_a_buscar.equals("") && genero_a_buscar.equals("")) {
                        pos_nombre_encontrado = buscarClientePorNombre(nombre_a_buscar);
                        if (pos_nombre_encontrado != -1) {
                            nombreClientes.setSelectedIndex(pos_nombre_encontrado + 1);
                        } else {
                            JOptionPane.showMessageDialog(this, "El nombre ingresado no fue encontrado, deberá crear un cliente nuevo");
                        }

                        nombreClientes.setSelectedIndex(pos_nombre_encontrado + 1);
                        //cantidad de nombres es igual o mayor a uno, la caja de nit no este vacia y las demas si
                    } else if (cant_nombre_encontrado >= 1 && !nit_a_buscar.equals("") && correo_a_buscar.equals("") && genero_a_buscar.equals("")) {
                        posNombreRep = new int[cant_nombre_encontrado];

                        posNombreRep = buscarClientePorNombreCantRepPos(cant_nombre_encontrado, nombre_a_buscar);
                        pos_nit_encontrado = buscarClientePorNit(nit_a_buscar);
                        int posicionComprobada = compararDatosNombreYNit(posNombreRep, pos_nit_encontrado);
                        if (posicionComprobada != -1) {
                            nombreClientes.setSelectedIndex(posicionComprobada + 1);
                        } else {
                            JOptionPane.showMessageDialog(this, "error!");
                        }
                        //cantidad de nombres es mayor o igual a 1, la caja de correo no este vacia y las demas si
                    } else if (cant_nombre_encontrado >= 1 && !correo_a_buscar.equals("") && nit_a_buscar.equals("") && genero_a_buscar.equals("")) {
                        posNombreRep = new int[cant_nombre_encontrado];

                        posNombreRep = buscarClientePorNombreCantRepPos(cant_nombre_encontrado, nombre_a_buscar);
                        pos_correo_encontrado = buscarClientePorCorreo(correo_a_buscar);
                        int posicionComprobada = compararDatosNombreYCorreo(posNombreRep, pos_correo_encontrado);

                        if (posicionComprobada != -1) {
                            nombreClientes.setSelectedIndex(posicionComprobada + 1);
                        }//tendria que un else 
                    } else if (!genero_a_buscar.equals("")) {
                        cant_genero_encontrado = buscarClientePorGeneroCantRep(genero_a_buscar);
                        if (cant_genero_encontrado > 1 && nombre_a_buscar.equals("") && correo_a_buscar.equals("") && nit_a_buscar.equals("")) {
                            JOptionPane.showMessageDialog(this, "Se encontraron " + cant_genero_encontrado + " coincidencias "
                                    + " de genero, ingrese un dato más datos!");
                            System.out.println("Validacion de solo ingreso de genero y nada del resto de campos");
                        } else if (cant_nombre_encontrado > 1 && !nombre_a_buscar.equals("") && cant_genero_encontrado > 1 && correo_a_buscar.equals("") && nit_a_buscar.equals("")) {
                            JOptionPane.showMessageDialog(this, "Se encontraron " + cant_nombre_encontrado
                                    + " coincidencias en nombres y " + cant_genero_encontrado + " coincidencias "
                                    + " de genero, ingrese un dato más, ya sea en NIT o correo!");
                        } else if (cant_nombre_encontrado >= 1 && !nombre_a_buscar.equals("") && cant_genero_encontrado > 1 && !correo_a_buscar.equals("") && nit_a_buscar.equals("")) {
                            posNombreRep = new int[cant_nombre_encontrado];

                            posNombreRep = buscarClientePorNombreCantRepPos(cant_nombre_encontrado, nombre_a_buscar);
                            pos_correo_encontrado = buscarClientePorCorreo(correo_a_buscar);
                            int posicionComprobada = compararDatosNombreYCorreo(posNombreRep, pos_correo_encontrado);

                            if (posicionComprobada != -1) {
                                nombreClientes.setSelectedIndex(posicionComprobada + 1);
                            } else {
                                JOptionPane.showMessageDialog(this, "El correo ingresado, no coincide con el nombre ingresado, verifique los datos del correo");
                            }
                        } else if (cant_nombre_encontrado >= 1 && cant_genero_encontrado > 1 && !nit_a_buscar.equals("") && correo_a_buscar.equals("")) {
                            posNombreRep = new int[cant_nombre_encontrado];

                            posNombreRep = buscarClientePorNombreCantRepPos(cant_nombre_encontrado, nombre_a_buscar);
                            pos_nit_encontrado = buscarClientePorNit(nit_a_buscar);
                            int posicionComprobada = compararDatosNombreYNit(posNombreRep, pos_nit_encontrado);
                            //System.out.println("posicionComprobada: " + posicionComprobada);

                            if (posicionComprobada != -1) {
                                nombreClientes.setSelectedIndex(posicionComprobada + 1);
                            } else {
                                JOptionPane.showMessageDialog(this, "El nit ingresado, no coincide con el nombre ingresado, verifique los datos del nit");
                            }
                        } else if (cant_nombre_encontrado <= 1 && !nombre_a_buscar.equals("") && cant_genero_encontrado > 1 && correo_a_buscar.equals("") && nit_a_buscar.equals("")) {
                            pos_nombre_encontrado = buscarClientePorNombre(nombre_a_buscar);
                            System.out.println("pos_nombre_encontrado = " + pos_nombre_encontrado);
                            if (pos_nombre_encontrado != -1) {
                                nombreClientes.setSelectedIndex(pos_nombre_encontrado + 1);

                            } else {
                                JOptionPane.showMessageDialog(this, "El nombre ingresado, no fue encontrado, debera crear un cliente nuevo!");
                            }
                        }
                    }

                }

                //Atributos independientes --> Correo y Genero
                if (!nit_a_buscar.equals("") && nombre_a_buscar.equals("") && correo_a_buscar.equals("") && genero_a_buscar.equals("")) {
                    //Hay problemas con el JComboBox por parte de valores repetidos, se tendra que decir
                    //en el manual, que hacer
                    pos_nit_encontrado = buscarClientePorNit(nit_a_buscar);
                    if (pos_nit_encontrado != -1) {
                        nombreClientes.setSelectedIndex(pos_nit_encontrado + 1);
                    } else {
                        JOptionPane.showMessageDialog(this, "El nit no fue encontrado, es necesario crear un cliente nuevo!");
                    }

                }

                if (!correo_a_buscar.equals("") && nombre_a_buscar.equals("") && nit_a_buscar.equals("") && genero_a_buscar.equals("")) {
                    pos_correo_encontrado = buscarClientePorCorreo(correo_a_buscar);
                    if (pos_correo_encontrado != -1) {
                        nombreClientes.setSelectedIndex(pos_correo_encontrado + 1);
                    } else {
                        JOptionPane.showMessageDialog(this, "El correo no fue encontrado, es necesario crear un cliente nuevo!");
                    }
                }

            }//Fin del else
        }//Fin del boton de filtro

        if (ae.getSource() == agregarProducto) {
            //Validamos que el JComboBox, no este en la posicion vacia
            if (nombreClientes.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(this, "Usted no puede agregar ningún producto, "
                        + "hasta seleccionar a algún cliente.");
            } else {

                if (coP.equals("") || caP.equals("")) {
                    JOptionPane.showMessageDialog(this, "Se deben de llenar ambos campos, codigo y cantidad");
                } else {
                    nombre_cliente = nombreClientes.getSelectedItem().toString();
                    posicion_cliente = (nombreClientes.getSelectedIndex() - 1);

                    //Empezamos la jugada de la tabla
                    codigo_producto_a_buscar = Integer.parseInt(codigoProducto.getText());
                    cantidad_a_descontar = Integer.parseInt(cantidadProducto.getText());
                    producto_a_agregar = buscarCodigoProducto(codigo_producto_a_buscar);//me retorna el producto
                    if (producto_a_agregar != null) {

                        subtotal = (double) (cantidad_a_descontar * producto_a_agregar.getPrecio());
                        producto_a_agregar.descontarProductos(cantidad_a_descontar);
                        ControlarDatos.escribirProductos(productos);

                        //preparando datos para agregarlos a la tabla
                        String[] datosProducto = {String.valueOf(producto_a_agregar.getCodigo()),
                            String.valueOf(producto_a_agregar.getNombre()), cantidadProducto.getText(),
                            String.valueOf(producto_a_agregar.getPrecio()), String.valueOf(subtotal)};
                        mTS.addRow(datosProducto); //Agregamos los datos del producto

                        if (cont_sub_totales < 11) {
                            productos_a_agregar[cont_productos_agregar++] = producto_a_agregar;
                            subTotales[cont_sub_totales++] = subtotal;
                            cantidades[contador_cantidades++] = cantidad_a_descontar;
                        }

                        totalVenta = totalVenta + (subtotal);

                        totalLabel.setText(String.valueOf(totalVenta));

                        codigoProducto.setText("");
                        cantidadProducto.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontro el codigo del producto!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        codigo_producto_a_buscar = 0;
                        cantidad_a_descontar = 0;
                        producto_a_agregar = null;
                    }

                }

            }

        }//Fin de agregar Producto

        if (ae.getSource() == vender) {
            int Seleccionada = tablaproductos_a_vender.getRowCount();

            if (Seleccionada != 0) {
                cliente_compra = clientes[posicion_cliente];

                /*
                System.out.println("\n----------------------------------------");
                System.out.println("El cliente a trabajar es: " + nombre_cliente);
                System.out.println("La posicion del cliente es: " + posicion_cliente);
                System.out.println("----------------------------------------\n");
                 */
                venta_a_realizar = new Venta(vendedor_a_enviar, cliente_compra, totalVenta,
                        cantidades, subTotales, fechaActual, numFactura);

                //Se usara un for para llenar la venta de sus productos
                for (int i = 0; i < cont_productos_agregar; i++) {
                    //Se debe agregar el producto a la venta
                    //debemos moverlo a donde esta la venta a realizar instanciada, sino no va a jalar
                    venta_a_realizar.agregarProducto(productos_a_agregar[i]);
                }

                arregloGeneralVentas[contador_ventas_general++] = venta_a_realizar;
                numFactura++;
                totalVenta = 0.0;
                mayorCantidadProductos();
                ordenarPorCodigoProducto();
                totalLabel.setText("");
                reset_mTS();
                //System.out.println("Venta en curso:");
                //System.out.println(venta_a_realizar.toString() + "\n\n");
                noFactura.setText("No.         " + numFactura);
                vendedor_a_enviar.agregarVenta(venta_a_realizar);
                vendedor_a_enviar.aumentarVentas(); //aumenta el numero de ventas que ha realizado
                //aqui mandamos a crear el archivo y a abrirlo automaticamente
                crearPDFVenta(venta_a_realizar);
                //Se escribenuevamente el arreglo de ventas y el arreglo de vendedores
                escribirVentas(arregloGeneralVentas);
                escribirVendedores(vendedores);
                venta_a_realizar = null;
                productos_a_agregar = new Producto[45];
                cont_productos_agregar = 0;
                subTotales = new Double[45];
                cont_sub_totales = 0;
                cantidades = new int[45];
                contador_cantidades = 0;
                //System.out.println("\n");
                //vendedor_a_enviar.MostrarDatos();
                login.escribirNoFactura(numFactura);

            } else {
                JOptionPane.showMessageDialog(null, "No Existen Datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }//Fin de vender

        if (ae.getSource() == aplicarFiltroVentas) {
            JButton nuevo = new JButton("Visualizar");
            nuevo.setForeground(Color.BLUE);

            Venta venta_a_mostrar;
            Venta[] ventas_match;
            int coincidencias = 0;

            String no_factura_a_buscar_a = t5.getText();
            int no_factura_a_buscar = 0;
            nit_a_buscar = t6.getText();
            nombre_a_buscar = t7.getText();
            String fecha_a_buscar = t8.getText();
            if (no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {

                JOptionPane.showMessageDialog(this, "Campos vacios!, llenelos", "Advertencia", JOptionPane.WARNING_MESSAGE);

            } else if (!no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos unicamente con el numero de factura
                venta_a_mostrar = venta_a_pdf_fac(vendedor_a_enviar.getVentas(), no_factura_a_buscar);

                if (venta_a_mostrar != null) {
                    reset_mTRV();
                    nuevo.setName(String.valueOf(venta_a_mostrar.getNoFactura()));
                    Object[] datosCoincidencia = {
                        venta_a_mostrar.getNoFactura(),
                        venta_a_mostrar.getCliente().getNit(),
                        venta_a_mostrar.getCliente().getNombre(),
                        venta_a_mostrar.getFecha(),
                        venta_a_mostrar.getTotal(),
                        nuevo
                    };
                    mTVR.addRow(datosCoincidencia); //Agregamos los datos del producto

                } else {
                    System.err.println("No se encontro la venta!");
                }
            } else if (no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                //Buscamos unicamente con el NIT
                coincidencias = buscar_nit_rep(vendedor_a_enviar.getVentas(), nit_a_buscar);

                if (coincidencias == 1) {

                    venta_a_mostrar = venta_a_pdf_nit(vendedor_a_enviar.getVentas(), nit_a_buscar);

                    reset_mTRV();
                    nuevo.setName(String.valueOf(venta_a_mostrar.getNoFactura()));
                    Object[] datosCoincidencia = {
                        venta_a_mostrar.getNoFactura(),
                        venta_a_mostrar.getCliente().getNit(),
                        venta_a_mostrar.getCliente().getNombre(),
                        venta_a_mostrar.getFecha(),
                        venta_a_mostrar.getTotal(),
                        nuevo
                    };
                    mTVR.addRow(datosCoincidencia); //Agregamos los datos del producto
                } else if (coincidencias > 1) {

                    ventas_match = ventasCoincidentesNit(vendedor_a_enviar.getVentas(), nit_a_buscar);
                    if (ventas_match != null) {
                        reset_mTRV();
                        int filas = ventas_match.length;

                        try {
                            for (int i = 0; i < filas; i++) {
                                nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                                Object[] datosCoincidencias = {
                                    ventas_match[i].getNoFactura(),
                                    ventas_match[i].getCliente().getNit(),
                                    ventas_match[i].getCliente().getNombre(),
                                    ventas_match[i].getFecha(),
                                    ventas_match[i].getTotal(),
                                    nuevo
                                };
                                mTVR.addRow(datosCoincidencias);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "No Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(this, "No se encontraron coincidencias!");
                }

            } else if (no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                //Buscamos unicamente con el nombre
                coincidencias = buscar_nombre_rep(vendedor_a_enviar.getVentas(), nombre_a_buscar);
                if (coincidencias == 1) {

                    venta_a_mostrar = venta_a_pdf_nombre(vendedor_a_enviar.getVentas(), nombre_a_buscar);
                    reset_mTRV();

                    nuevo.setName(String.valueOf(venta_a_mostrar.getNoFactura()));
                    Object[] datosCoincidencia = {
                        venta_a_mostrar.getNoFactura(),
                        venta_a_mostrar.getCliente().getNit(),
                        venta_a_mostrar.getCliente().getNombre(),
                        venta_a_mostrar.getFecha(),
                        venta_a_mostrar.getTotal(),
                        nuevo
                    };
                    mTVR.addRow(datosCoincidencia); //Agregamos los datos del producto

                } else if (coincidencias > 1) {
                    //se tiene que hacer algo diferente
                    ventas_match = ventasCoincidentesNombre(vendedor_a_enviar.getVentas(), nombre_a_buscar);
                    if (ventas_match != null) {
                        reset_mTRV();
                        int filas = ventas_match.length;

                        try {
                            for (int i = 0; i < filas; i++) {
                                nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                                Object[] datosCoincidencias = {
                                    ventas_match[i].getNoFactura(),
                                    ventas_match[i].getCliente().getNit(),
                                    ventas_match[i].getCliente().getNombre(),
                                    ventas_match[i].getFecha(),
                                    ventas_match[i].getTotal(),
                                    nuevo
                                };
                                mTVR.addRow(datosCoincidencias);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "No Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(this, "No se encontraron coincidencias!");
                }//Fin de coincidencias por por nombre

            } else if (no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {
                //Buscamos unicamente por fecha
                coincidencias = buscar_fecha_rep(vendedor_a_enviar.getVentas(), fecha_a_buscar);
                if (coincidencias == 1) {
                    venta_a_mostrar = venta_a_pdf_fecha(vendedor_a_enviar.getVentas(), fecha_a_buscar);
                    reset_mTRV();

                    nuevo.setName(String.valueOf(venta_a_mostrar.getNoFactura()));
                    Object[] datosCoincidencia = {
                        venta_a_mostrar.getNoFactura(),
                        venta_a_mostrar.getCliente().getNit(),
                        venta_a_mostrar.getCliente().getNombre(),
                        venta_a_mostrar.getFecha(),
                        venta_a_mostrar.getTotal(),
                        nuevo
                    };
                    mTVR.addRow(datosCoincidencia); //Agregamos los datos del producto
                } else if (coincidencias > 1) {

                    ventas_match = ventasCoincidentesFecha(vendedor_a_enviar.getVentas(), fecha_a_buscar);
                    if (ventas_match != null) {
                        reset_mTRV();
                        int filas = ventas_match.length;

                        try {
                            for (int i = 0; i < filas; i++) {
                                nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                                Object[] datosCoincidencias = {
                                    ventas_match[i].getNoFactura(),
                                    ventas_match[i].getCliente().getNit(),
                                    ventas_match[i].getCliente().getNombre(),
                                    ventas_match[i].getFecha(),
                                    ventas_match[i].getTotal(),
                                    nuevo
                                };
                                mTVR.addRow(datosCoincidencias);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (ventas_match == null) {
                        JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }

                //A partir de aqui empiezan los dobles
            } else if (!no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos por no. Factura y por el nit
                ventas_match = ventasCoincidentesFactNit(vendedor_a_enviar.getVentas(), no_factura_a_buscar, nit_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else if (!no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos por no. Factura y por el nombre
                ventas_match = ventasCoincidentesFactNombre(vendedor_a_enviar.getVentas(), no_factura_a_buscar, nombre_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else if (!no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos por no. Factura y por la fecha
                ventas_match = ventasCoincidentesFactFecha(vendedor_a_enviar.getVentas(), no_factura_a_buscar, fecha_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else if (no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                //Buscamos por nit y por el nombre
                ventas_match = ventasCoincidentesNitNombre(vendedor_a_enviar.getVentas(), nit_a_buscar, nombre_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else if (no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {
                //Buscamos por nit y por la fecha
                ventas_match = ventasCoincidentesNitFecha(vendedor_a_enviar.getVentas(), nit_a_buscar, fecha_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else if (no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {

                //Buscamos por el nombre y fecha
                ventas_match = ventasCoincidentesNombreFecha(vendedor_a_enviar.getVentas(), nombre_a_buscar, fecha_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }
                //A partir de aqui empiezan las triples
            } else if (!no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos por la factura, por nit y por fecha
                ventas_match = ventasCoincidentesNFNtFe(vendedor_a_enviar.getVentas(), no_factura_a_buscar, nit_a_buscar, fecha_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else if (!no_factura_a_buscar_a.equals("") && nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {

                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos por la factura, por nombre y por fecha
                ventas_match = ventasCoincidentesNFNomFe(vendedor_a_enviar.getVentas(), no_factura_a_buscar, nombre_a_buscar, fecha_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else if (!no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos por el nit, por factura y por nombre
                ventas_match = ventasCoincidentesNtNfNm(vendedor_a_enviar.getVentas(), nit_a_buscar, no_factura_a_buscar, nombre_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else if (no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {
                //Buscamos por el nombre, por la fecha y por el nit
                ventas_match = ventasCoincidentesNmFeNt(vendedor_a_enviar.getVentas(), nombre_a_buscar, fecha_a_buscar, nit_a_buscar);
                if (ventas_match != null) {

                    reset_mTRV();
                    int filas = ventas_match.length;

                    try {
                        for (int i = 0; i < filas; i++) {
                            nuevo.setName(String.valueOf(ventas_match[i].getNoFactura()));
                            Object[] datosCoincidencias = {
                                ventas_match[i].getNoFactura(),
                                ventas_match[i].getCliente().getNit(),
                                ventas_match[i].getCliente().getNombre(),
                                ventas_match[i].getFecha(),
                                ventas_match[i].getTotal(),
                                nuevo
                            };
                            mTVR.addRow(datosCoincidencias);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Se presento un error al cargar los datos a la tabla!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha ingresada, no se encuentra en el registro del vendedor!", "Error", JOptionPane.WARNING_MESSAGE);
                }

            } else if (!no_factura_a_buscar_a.equals("") && !nit_a_buscar.equals("") && !nombre_a_buscar.equals("") && !fecha_a_buscar.equals("")) {
                no_factura_a_buscar = Integer.parseInt(no_factura_a_buscar_a);
                //Buscamos con todos los datos 
                venta_a_mostrar = ventaCoincidente(vendedor_a_enviar.getVentas(), no_factura_a_buscar, nit_a_buscar, nombre_a_buscar, fecha_a_buscar);

                if (venta_a_mostrar != null) {
                    reset_mTRV();
                    nuevo.setName(String.valueOf(venta_a_mostrar.getNoFactura()));
                    Object[] datosCoincidencia = {
                        venta_a_mostrar.getNoFactura(),
                        venta_a_mostrar.getCliente().getNit(),
                        venta_a_mostrar.getCliente().getNombre(),
                        venta_a_mostrar.getFecha(),
                        venta_a_mostrar.getTotal(),
                        nuevo
                    };
                    mTVR.addRow(datosCoincidencia); //Agregamos los datos del producto

                } else {
                    System.err.println("No se encontro la venta!");
                }
            }

        }
    }

    //Para limpiar los valores de la tabla de la venta en curso
    void reset_mTS() {
        mTS.setRowCount(0);
    }

    //Para limpiar los valores de la tabla de las ventas
    void reset_mTRV() {
        mTVR.setRowCount(0);
    }

    public static Object[][] convertirDatosVentas() {
        Venta[] ventas_realizadas = vendedor_a_enviar.getVentas();
        int contador_ventas_realizadas = 0;
        for (int i = 0; i < ventas_realizadas.length; i++) {
            if (ventas_realizadas[i] != null) {
                contador_ventas_realizadas++;
            }
        }

        int filas = contador_ventas_realizadas;
        Object[][] arreglo = new Object[filas][6];
        //Factura", "NIT", "Nombre", "Fecha", "Total", "Acciones
        for (int i = 0; i < filas; i++) {
            arreglo[i][0] = ventas_realizadas[i].getNoFactura();
            arreglo[i][1] = ventas_realizadas[i].getCliente().getNit();
            arreglo[i][2] = ventas_realizadas[i].getCliente().getNombre();
            arreglo[i][3] = ventas_realizadas[i].getFecha();
            arreglo[i][4] = ventas_realizadas[i].getTotal();
            JButton nuevo = new JButton("Visualizar");
            nuevo.setForeground(Color.BLUE);
            nuevo.setName(String.valueOf(ventas_realizadas[i].getNoFactura()));
            arreglo[i][5] = nuevo;
        }
        return arreglo;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // Obtenemos la columna con el MouseEvent.getX
        int columna = tabla_ventas_realizadas.getColumnModel().getColumnIndexAtX(me.getX());
        // Obtenemos la fila con el MouseEvent.getY
        int fila = me.getY() / tabla_ventas_realizadas.getRowHeight();
        System.out.println("Click en la posicion: " + fila + "-" + columna);

        // Solo se valida que se haya dado click dentro de la tabla
        if (fila < tabla_ventas_realizadas.getRowCount() && fila >= 0 && columna < tabla_ventas_realizadas.getColumnCount() && columna >= 0) {
            // Obtenemos el objeto que esta dentro de la tabla, tomando en cuenta la fila y columna
            Object value = tabla_ventas_realizadas.getValueAt(fila, columna);
            if (value instanceof JButton) {//En caso de que el valor que se selecciono sea la instancia de un boton se valida 
                String numeroFac = tabla_ventas_realizadas.getValueAt(fila, 0).toString();
                //System.out.println("No. Factura= " + numeroFac);

                Venta venta_a_imprimir;
                venta_a_imprimir = venta_a_pdf_fac(vendedor_a_enviar.getVentas(), Integer.parseInt(numeroFac));
                if (venta_a_imprimir != null) {

                    crearPDFVenta(venta_a_imprimir);

                } else {
                    JOptionPane.showMessageDialog(this, "La venta no fue encontrada!");
                }

            }
        }

    }

    //Estos metodos no se tienen que borrar, igual no nos hacen daño
    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
