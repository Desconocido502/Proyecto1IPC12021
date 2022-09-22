package Ventanas;

import static Ventanas.ControlarDatos.agregarClientes;
import static Ventanas.ControlarDatos.agregarProductos;
import static Ventanas.ControlarDatos.agregarSucursales;
import static Ventanas.ControlarDatos.agregarVendedores;
import static Ventanas.ControlarDatos.clientes;
import static Ventanas.ControlarDatos.contador_clientes;
import static Ventanas.ControlarDatos.contador_productos;
import static Ventanas.ControlarDatos.contador_sucursales;
import static Ventanas.ControlarDatos.contador_vendedores;
import static Ventanas.ControlarDatos.convertirDatosClientes;
import static Ventanas.ControlarDatos.convertirDatosProductos;
import static Ventanas.ControlarDatos.convertirDatosSucursales;
import static Ventanas.ControlarDatos.convertirDatosVendedores;
import static Ventanas.ControlarDatos.creacionPDFCliente;
import static Ventanas.ControlarDatos.crearPDFProductos;
import static Ventanas.ControlarDatos.crearPDFSucursales;
import static Ventanas.ControlarDatos.crearPDFVendedor;
import static Ventanas.ControlarDatos.generos;
import static Ventanas.ControlarDatos.mayorCantidadProductos;
import static Ventanas.ControlarDatos.mayorCantidadVendedores;
import static Ventanas.ControlarDatos.ordenarPorCodigoProducto;
import static Ventanas.ControlarDatos.ordenarPorCodigoVendedor;
import static Ventanas.ControlarDatos.productos;
import static Ventanas.ControlarDatos.sucursales;
import static Ventanas.ControlarDatos.vendedores;
import clasesPrincipales.Cliente;
import clasesPrincipales.Producto;
import clasesPrincipales.Sucursal;
import clasesPrincipales.Vendedor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ventanaAdministradora extends JFrame implements ActionListener {

    //Se crea el agregado de ventanas como pestañas
    public JTabbedPane pestañas;

    //Paneles
    JPanel Sucursal, Producto, Cliente, Vendedor;

    //Para leer los archivos
    static String contenido = "";
    File archivo;
    FileReader fr;
    BufferedReader br;

    //Cajas de texto y botones para sucursal
    JLabel listadoOficial;
    JButton CrearSucursal, cMSucursal, actualizarSucursal;
    JButton eliminarSucursal, exportarSucursales;

    JButton retornoLoginS, retornoLoginP, retornoLoginC, retornoLoginV;

    //Cajas de texto y botones para Producto
    JButton crearProducto, CMProducto, actualizarProducto;
    JButton eliminarProducto, exportarProductos;

    //Cajas de texto y botones para Cliente
    JButton crearCliente, CMCliente, actualizarCliente;
    JButton eliminarCliente, exportarCliente;

    //Cajas de texto y botones para Vendedor
    JButton crearVendedor, cMVendedor, ActualizarVendedor;
    JButton eliminarVendedor, ExportarVendedor;

    //Tablas
    JTable tablaSucursales, tablaProductos, tablaClientes, tablaVendedores;

    Object[][] datosSucursal, datosProducto, datosCliente, datosVendedor;

    static Producto[] datosGrafica = new Producto[3];

    static String[] nombreProductos = new String[3];
    static int[] cantidadProductos = new int[3];

    static String[] nombres_productos_top = new String[3];
    static int[] cantidades_productos_top = new int[3];

    static String[] nombres_vendedores_top = new String[3];
    static int[] cantidades_vendedores_top = new int[3];

    //Para agregar el grafico 
    static JFreeChart chartProducto, chartCliente, chartVendedor;

    //JFrame para mostrar la grafica
    JPanel panelGraficadorProducto, panelGraficadorCliente, panelGraficadorVendedor;

    public static String GraficaBarProducto = "GraficaBarProducto.png";
    public static String GraficaPieCliente = "GraficaPieCliente.png";
    public static String GraficaBarVendedor = "GraficaBarVendedor.png";

    public static void recibirmayorCantidadProductos(String[] nombres, int[] cantidades) {
        nombres_productos_top = nombres;
        cantidades_productos_top = cantidades;
    }

    static int[] datosGenero = new int[2];

    public static void cantidadGenero(int[] generos) {
        for (int i = 0; i < 2; i++) {
            datosGenero[i] = generos[i];
        }
    }

    public static void recibirmayorCantidadVentas(String[] nombres, int[] cantidades) {
        nombres_vendedores_top = nombres;
        cantidades_vendedores_top = cantidades;
    }

    public ventanaAdministradora() {
        pestañas = new JTabbedPane();

        Sucursal = new JPanel();
        Sucursal.setLayout(null);

        Producto = new JPanel();
        Producto.setLayout(null);

        Cliente = new JPanel();
        Cliente.setLayout(null);

        Vendedor = new JPanel();
        Vendedor.setLayout(null);

        pestañas.addTab("Sucursales", Sucursal);
        pestañas.addTab("Productos", Producto);
        pestañas.addTab("Clientes", Cliente);
        pestañas.addTab("Vendedores", Vendedor);

        PestañaSucursal(Sucursal);
        PestañaProducto(Producto);
        PestañaCliente(Cliente);
        PestañaVendedor(Vendedor);

        this.setTitle("Administrador");
        this.setSize(950, 675);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.getContentPane().add(pestañas);

    }

    public void PestañaSucursal(JPanel Sucursales) {

        //Creación de 6 botones
        //crear una nueva sucursal
        CrearSucursal = new JButton("Crear");
        CrearSucursal.setBounds(600, 60, 150, 40);
        CrearSucursal.setVisible(true);
        CrearSucursal.setForeground(new Color(23, 71, 152));
        CrearSucursal.setFont(new Font("ARIAL", Font.BOLD, 15));
        CrearSucursal.addActionListener(this);
        Sucursales.add(CrearSucursal);

        //Se cargan un JSON para leer las sucursales
        cMSucursal = new JButton("Carga Masiva");
        cMSucursal.setBounds(775, 60, 150, 40);
        cMSucursal.setVisible(true);
        cMSucursal.setForeground(new Color(23, 71, 152));
        cMSucursal.setFont(new Font("ARIAL", Font.BOLD, 15));
        cMSucursal.addActionListener(this);
        Sucursales.add(cMSucursal);

        //Es para actualizar la informacion de la sucursal
        actualizarSucursal = new JButton("Actualizar");
        actualizarSucursal.setBounds(600, 120, 150, 40);
        actualizarSucursal.setVisible(true);
        actualizarSucursal.setForeground(new Color(23, 71, 152));
        actualizarSucursal.setFont(new Font("ARIAL", Font.BOLD, 15));
        actualizarSucursal.addActionListener(this);
        Sucursales.add(actualizarSucursal);

        //Para eliminar a una sucursal
        eliminarSucursal = new JButton("Eliminar");
        eliminarSucursal.setBounds(775, 120, 150, 40);
        eliminarSucursal.setVisible(true);
        eliminarSucursal.setForeground(new Color(23, 71, 152));
        eliminarSucursal.setFont(new Font("ARIAL", Font.BOLD, 15));
        eliminarSucursal.addActionListener(this);
        Sucursales.add(eliminarSucursal);

        //Pasamos la informacion obtenida a un pdf 
        exportarSucursales = new JButton("Exportar Listado a PDF");
        exportarSucursales.setBounds(600, 190, 320, 50);
        exportarSucursales.setVisible(true);
        exportarSucursales.setForeground(new Color(23, 71, 152));
        exportarSucursales.setFont(new Font("ARIAL", Font.BOLD, 15));
        exportarSucursales.addActionListener(this);
        Sucursales.add(exportarSucursales);

        //Retornamos a la ventana inicial del sistema
        retornoLoginS = new JButton("Cerrar Sesión");
        retornoLoginS.setBounds(775, 550, 150, 50);
        retornoLoginS.setVisible(true);
        retornoLoginS.setBackground(new Color(255, 50, 50));
        retornoLoginS.setFont(new Font("ARIAL", Font.BOLD, 15));
        retornoLoginS.addActionListener(this);
        Sucursales.add(retornoLoginS);

        datosSucursal = convertirDatosSucursales();
        String[] columnas = {"Codigo", "Nombre", "Direccion", "Correo", "Telefono"};
        tablaSucursales = new JTable();

        //Se hace uso de la tabla model para validar que el admin no pueda realizar
        //ningun tipo de cambio por medio de las tablas!
        DefaultTableModel mTS = new DefaultTableModel(datosSucursal, columnas) {

            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tablaSucursales.setModel(mTS);

        //setAutoResizeMode -->Sirve para establecer el mdo de ajuste automatico de la tabla
        tablaSucursales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //Otro estilo de tabla que me permite cambiar el tam de las columnas
        TableColumnModel columnModel = tablaSucursales.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(75);
        columnModel.getColumn(1).setPreferredWidth(93);
        columnModel.getColumn(2).setPreferredWidth(145);
        columnModel.getColumn(3).setPreferredWidth(180);
        columnModel.getColumn(4).setPreferredWidth(80);

        //Para centrar los datos de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = tablaSucursales.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            tablaSucursales.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }

        //Se debe de crear un scroll para poder visualizar los datos
        JScrollPane spS = new JScrollPane(tablaSucursales);
        spS.setBounds(10, 25, 575, 575);
        spS.setVisible(true);
        // Por ultimo agregamos el ScrollPane que tiene dentro de la ventana.
        Sucursales.add(spS);

    }

    public void PestañaProducto(JPanel Productos) {

        //Creación de 6 botones
        //crear un nuevo producto
        crearProducto = new JButton("Crear");
        crearProducto.setBounds(600, 60, 150, 40);
        crearProducto.setVisible(true);
        crearProducto.setForeground(new Color(23, 71, 152));
        crearProducto.setFont(new Font("ARIAL", Font.BOLD, 15));
        crearProducto.addActionListener(this);
        Productos.add(crearProducto);

        //Se cargan un JSON para leer los productos
        CMProducto = new JButton("Carga Masiva");
        CMProducto.setBounds(775, 60, 150, 40);
        CMProducto.setVisible(true);
        CMProducto.setForeground(new Color(23, 71, 152));
        CMProducto.setFont(new Font("ARIAL", Font.BOLD, 15));
        CMProducto.addActionListener(this);
        Productos.add(CMProducto);

        //Es para actualizar la informacion del producto
        actualizarProducto = new JButton("Actualizar");
        actualizarProducto.setBounds(600, 120, 150, 40);
        actualizarProducto.setVisible(true);
        actualizarProducto.setForeground(new Color(23, 71, 152));
        actualizarProducto.setFont(new Font("ARIAL", Font.BOLD, 15));
        actualizarProducto.addActionListener(this);
        Productos.add(actualizarProducto);

        //Para eliminar a un producto
        eliminarProducto = new JButton("Eliminar");
        eliminarProducto.setBounds(775, 120, 150, 40);
        eliminarProducto.setVisible(true);
        eliminarProducto.setForeground(new Color(23, 71, 152));
        eliminarProducto.setFont(new Font("ARIAL", Font.BOLD, 15));
        eliminarProducto.addActionListener(this);
        Productos.add(eliminarProducto);

        //Pasamos la informacion obtenida a un pdf 
        exportarProductos = new JButton("Exportar Listado a PDF");
        exportarProductos.setBounds(600, 190, 320, 50);
        exportarProductos.setVisible(true);
        exportarProductos.setForeground(new Color(23, 71, 152));
        exportarProductos.setFont(new Font("ARIAL", Font.BOLD, 15));
        exportarProductos.addActionListener(this);
        Productos.add(exportarProductos);

        //Retornamos a la ventana inicial del sistema
        retornoLoginP = new JButton("Cerrar Sesión");
        retornoLoginP.setBounds(775, 550, 150, 50);
        retornoLoginP.setVisible(true);
        retornoLoginP.setBackground(new Color(255, 50, 50));
        retornoLoginP.setFont(new Font("ARIAL", Font.BOLD, 15));
        retornoLoginP.addActionListener(this);
        Productos.add(retornoLoginP);

        datosProducto = convertirDatosProductos();
        String[] columnas = {"Código", "Nombre", "Descripción", "Cantidad", "Precio"};
        tablaProductos = new JTable();

        //Se hace uso de la tabla model para validar que el admin no pueda realizar
        //ningun tipo de cambio por medio de las tablas!
        DefaultTableModel mTS = new DefaultTableModel(datosProducto, columnas) {

            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tablaProductos.setModel(mTS);

        //setAutoResizeMode -->Sirve para establecer el mdo de ajuste automatico de la tabla
        tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //Otro estilo de tabla que me permite cambiar el tam de las columnas
        TableColumnModel columnModel = tablaProductos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(135);
        columnModel.getColumn(2).setPreferredWidth(223);
        columnModel.getColumn(3).setPreferredWidth(75);
        columnModel.getColumn(4).setPreferredWidth(70);

        //Para centrar los datos de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = tablaProductos.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            if (columnIndex != 2) {
                tablaProductos.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
            }
        }

        //Se debe de crear un scroll para poder visualizar los datos
        JScrollPane spP = new JScrollPane(tablaProductos);
        spP.setBounds(10, 25, 575, 575);
        spP.setVisible(true);
        // Por ultimo agregamos el ScrollPane que tiene dentro de la ventana.
        Productos.add(spP);

        DefaultCategoryDataset datasetP = new DefaultCategoryDataset();
        datasetP.addValue(cantidades_productos_top[0], String.valueOf(nombres_productos_top[0]), "Producto 1");
        datasetP.addValue(cantidades_productos_top[1], String.valueOf(nombres_productos_top[1]), "Producto 2");
        datasetP.addValue(cantidades_productos_top[2], String.valueOf(nombres_productos_top[2]), "Producto 3");

        chartProducto = ChartFactory.createBarChart3D("Top productos con más existencia", "Categoria", "Valores", datasetP, PlotOrientation.VERTICAL, true, true, false);
        chartProducto.setBackgroundPaint(new Color(236, 240, 241));
        chartProducto.getTitle().setPaint(new Color(125, 60, 152));
        chartProducto.getLegend().setItemPaint(Color.blue);

        //Interesante toma nota podemos cambiarle color
        CategoryPlot p = chartProducto.getCategoryPlot();
        ValueAxis axis = p.getRangeAxis();
        CategoryAxis axis2 = p.getDomainAxis();
        Font font = new Font("Dialog", Font.PLAIN, 10);
        axis.setTickLabelFont(font);

        axis.setLabelPaint(Color.BLUE);
        axis2.setLabelPaint(Color.BLUE);
        axis2.setTickLabelFont(font);
        p.setBackgroundPaint(new Color(236, 240, 241));
        p.setRangeCrosshairPaint(Color.ORANGE);
        p.setRangeGridlinePaint(Color.MAGENTA);
        BarRenderer renderer = (BarRenderer) p.getRenderer();
        renderer.setSeriesPaint(0, new Color(203, 67, 53));
        renderer.setSeriesPaint(1, new Color(40, 180, 99));
        renderer.setSeriesPaint(2, new Color(46, 134, 193));
        renderer.setDrawBarOutline(true);

        panelGraficadorProducto = new JPanel();
        panelGraficadorProducto.setLayout(null);
        panelGraficadorProducto.setBounds(600, 255, 330, 280);
        panelGraficadorProducto.setBackground(Color.LIGHT_GRAY);
        Productos.add(panelGraficadorProducto);
        ChartPanel chartPanelProducto = new ChartPanel(chartProducto);
        chartPanelProducto.setBounds(1, 1, 330, 280);
        chartPanelProducto.setVisible(true);
        panelGraficadorProducto.add(chartPanelProducto);

        //Esta parte del codigo lo que hace es que pasa mi chartP a formato png
        //Osea guarda mi chartP como una imagen :)
        try {
            final ChartRenderingInfo informacion = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(GraficaBarProducto);
            ChartUtilities.saveChartAsPNG(file1, chartProducto, 450, 380, informacion);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PestañaCliente(JPanel Clientes) {
        //crear un nuevo cliente
        crearCliente = new JButton("Crear");
        crearCliente.setBounds(600, 60, 150, 40);
        crearCliente.setVisible(true);
        crearCliente.setForeground(new Color(23, 71, 152));
        crearCliente.setFont(new Font("ARIAL", Font.BOLD, 15));
        crearCliente.addActionListener(this);
        Clientes.add(crearCliente);

        //Se cargan un JSON para leer los clientes
        CMCliente = new JButton("Carga Masiva");
        CMCliente.setBounds(775, 60, 150, 40);
        CMCliente.setVisible(true);
        CMCliente.setForeground(new Color(23, 71, 152));
        CMCliente.setFont(new Font("ARIAL", Font.BOLD, 15));
        CMCliente.addActionListener(this);
        Clientes.add(CMCliente);

        //Es para actualizar la informacion del cliente
        actualizarCliente = new JButton("Actualizar");
        actualizarCliente.setBounds(600, 120, 150, 40);
        actualizarCliente.setVisible(true);
        actualizarCliente.setForeground(new Color(23, 71, 152));
        actualizarCliente.setFont(new Font("ARIAL", Font.BOLD, 15));
        actualizarCliente.addActionListener(this);
        Clientes.add(actualizarCliente);

        //Para eliminar a un cliente
        eliminarCliente = new JButton("Eliminar");
        eliminarCliente.setBounds(775, 120, 150, 40);
        eliminarCliente.setVisible(true);
        eliminarCliente.setForeground(new Color(23, 71, 152));
        eliminarCliente.setFont(new Font("ARIAL", Font.BOLD, 15));
        eliminarCliente.addActionListener(this);
        Clientes.add(eliminarCliente);

        //Pasamos la informacion obtenida a un pdf 
        exportarCliente = new JButton("Exportar Listado a PDF");
        exportarCliente.setBounds(600, 190, 320, 50);
        exportarCliente.setVisible(true);
        exportarCliente.setForeground(new Color(23, 71, 152));
        exportarCliente.setFont(new Font("ARIAL", Font.BOLD, 15));
        exportarCliente.addActionListener(this);
        Clientes.add(exportarCliente);

        //Retornamos a la ventana inicial del sistema
        retornoLoginC = new JButton("Cerrar Sesión");
        retornoLoginC.setBounds(775, 550, 150, 50);
        retornoLoginC.setVisible(true);
        retornoLoginC.setBackground(new Color(255, 50, 50));
        retornoLoginC.setFont(new Font("ARIAL", Font.BOLD, 15));

        retornoLoginC.addActionListener(this);
        Clientes.add(retornoLoginC);

        datosCliente = convertirDatosClientes();
        String[] columnas = {"Codigo", "Nombre", "NIT", "Correo", "Genero"};
        tablaClientes = new JTable();

        //Se hace uso de la tabla model para validar que el admin no pueda realizar
        //ningun tipo de cambio por medio de las tablas!
        DefaultTableModel mTS = new DefaultTableModel(datosCliente, columnas) {

            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tablaClientes.setModel(mTS);

        //setAutoResizeMode -->Sirve para establecer el mdo de ajuste automatico de la tabla
        tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //Otro estilo de tabla que me permite cambiar el tam de las columnas
        TableColumnModel columnModel = tablaClientes.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(135);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(80);

        //Para centrar los datos de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = tablaClientes.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            tablaClientes.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }

        //Se debe de crear un scroll para poder visualizar los datos
        JScrollPane spC = new JScrollPane(tablaClientes);
        spC.setBounds(10, 25, 575, 575);
        spC.setVisible(true);
        // Por ultimo agregamos el ScrollPane que tiene dentro de la ventana.
        Clientes.add(spC);

        //Se le crea la fuente de datos
        DefaultPieDataset datasetA = new DefaultPieDataset();
        datasetA.setValue("Hombres", generos[0]);
        datasetA.setValue("Mujeres", generos[1]);

        //Se crea el chartP para el pie
        chartCliente = ChartFactory.createPieChart("Genero de los clientes", datasetA, true, true, false);
        //Para cambiarle el color a las secciones XD
        PiePlot plot = (PiePlot) chartCliente.getPlot();
        plot.setSectionPaint(0, Color.MAGENTA);
        plot.setSectionPaint(1, Color.CYAN);

        chartCliente.getTitle().setPaint(new Color(125, 60, 152));

        panelGraficadorCliente = new JPanel();
        panelGraficadorCliente.setLayout(null);
        panelGraficadorCliente.setBounds(600, 255, 330, 280);
        panelGraficadorCliente.setBackground(Color.DARK_GRAY);
        Clientes.add(panelGraficadorCliente);
        ChartPanel chartPanelCliente = new ChartPanel(chartCliente);
        chartPanelCliente.setBounds(1, 1, 327, 279);
        chartPanelCliente.setVisible(true);
        panelGraficadorCliente.add(chartPanelCliente);

        try {
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(GraficaPieCliente);
            ChartUtilities.saveChartAsPNG(file1, chartCliente, 280, 265, info);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PestañaVendedor(JPanel Vendedores) {
        //Creación de 6 botones
        //crear un nuevo vendedor
        crearVendedor = new JButton("Crear");
        crearVendedor.setBounds(600, 60, 150, 40);
        crearVendedor.setVisible(true);
        crearVendedor.setForeground(new Color(23, 71, 152));
        crearVendedor.setFont(new Font("ARIAL", Font.BOLD, 15));
        crearVendedor.addActionListener(this);
        Vendedores.add(crearVendedor);

        //Se cargan un JSON para leer los vendedor
        cMVendedor = new JButton("Carga Masiva");
        cMVendedor.setBounds(775, 60, 150, 40);
        cMVendedor.setVisible(true);
        cMVendedor.setForeground(new Color(23, 71, 152));
        cMVendedor.setFont(new Font("ARIAL", Font.BOLD, 15));
        cMVendedor.addActionListener(this);
        Vendedores.add(cMVendedor);

        //Es para actualizar la informacion del vendedor
        ActualizarVendedor = new JButton("Actualizar");
        ActualizarVendedor.setBounds(600, 120, 150, 40);
        ActualizarVendedor.setVisible(true);
        ActualizarVendedor.setForeground(new Color(23, 71, 152));
        ActualizarVendedor.setFont(new Font("ARIAL", Font.BOLD, 15));
        ActualizarVendedor.addActionListener(this);
        Vendedores.add(ActualizarVendedor);

        //Para eliminar a un vendedor(a)
        eliminarVendedor = new JButton("Eliminar");
        eliminarVendedor.setBounds(775, 120, 150, 40);
        eliminarVendedor.setVisible(true);
        eliminarVendedor.setForeground(new Color(23, 71, 152));
        eliminarVendedor.setFont(new Font("ARIAL", Font.BOLD, 15));
        eliminarVendedor.addActionListener(this);
        Vendedores.add(eliminarVendedor);

        //Pasamos la informacion obtenida a un pdf 
        ExportarVendedor = new JButton("Exportar Listado a PDF");
        ExportarVendedor.setBounds(600, 190, 320, 50);
        ExportarVendedor.setVisible(true);
        ExportarVendedor.setForeground(new Color(23, 71, 152));
        ExportarVendedor.setFont(new Font("ARIAL", Font.BOLD, 15));
        ExportarVendedor.addActionListener(this);
        Vendedores.add(ExportarVendedor);

        //Retornamos a la ventana inicial del sistema
        retornoLoginV = new JButton("Cerrar Sesión");
        retornoLoginV.setBounds(775, 550, 150, 50);
        retornoLoginV.setVisible(true);
        retornoLoginV.setBackground(new Color(255, 50, 50));
        retornoLoginV.setFont(new Font("ARIAL", Font.BOLD, 15));
        retornoLoginV.addActionListener(this);
        Vendedores.add(retornoLoginV);

        datosVendedor = convertirDatosVendedores();
        String[] columnas = {"Código", "Nombre", "Caja", "Ventas", "Genero"};
        tablaVendedores = new JTable();

        //Se hace uso de la tabla model para validar que el admin no pueda realizar
        //ningun tipo de cambio por medio de las tablas!
        DefaultTableModel mTS = new DefaultTableModel(datosVendedor, columnas) {

            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tablaVendedores.setModel(mTS);
        //Para centrar los datos de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableModel tableModel = tablaVendedores.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            tablaVendedores.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }

        //Se debe de crear un scroll para poder visualizar los datos
        JScrollPane spV = new JScrollPane(tablaVendedores);
        spV.setBounds(10, 25, 575, 575);
        spV.setVisible(true);
        // Por ultimo agregamos el ScrollPane que tiene dentro de la ventana.
        Vendedores.add(spV);

        DefaultCategoryDataset datasetV = new DefaultCategoryDataset();
        datasetV.addValue(cantidades_vendedores_top[0], String.valueOf(nombres_vendedores_top[0]), "Vendedor 1");
        datasetV.addValue(cantidades_vendedores_top[1], String.valueOf(nombres_vendedores_top[1]), "Vendedor 2");
        datasetV.addValue(cantidades_vendedores_top[2], String.valueOf(nombres_vendedores_top[2]), "Vendedor 3");

        chartVendedor = ChartFactory.createBarChart3D("Top vendedores con mas ventas", "Categoria", "Valores", datasetV, PlotOrientation.VERTICAL, true, true, false);
        chartVendedor.setBackgroundPaint(new Color(236, 240, 241));
        chartVendedor.getTitle().setPaint(new Color(125, 60, 152));
        chartVendedor.getLegend().setItemPaint(Color.blue);

        //Interesante toma nota podemos cambiarle color
        CategoryPlot p = chartVendedor.getCategoryPlot();
        ValueAxis axis = p.getRangeAxis();
        CategoryAxis axis2 = p.getDomainAxis();
        Font font = new Font("Dialog", Font.PLAIN, 10);
        axis.setTickLabelFont(font);

        axis.setLabelPaint(Color.BLUE);
        axis2.setLabelPaint(Color.BLUE);
        axis2.setTickLabelFont(font);
        p.setBackgroundPaint(new Color(236, 240, 241));
        p.setRangeCrosshairPaint(Color.ORANGE);
        p.setRangeGridlinePaint(Color.MAGENTA);
        BarRenderer renderer = (BarRenderer) p.getRenderer();
        renderer.setSeriesPaint(0, new Color(203, 67, 53));
        renderer.setSeriesPaint(1, new Color(40, 180, 99));
        renderer.setSeriesPaint(2, new Color(46, 134, 193));
        renderer.setDrawBarOutline(true);

        panelGraficadorVendedor = new JPanel();
        panelGraficadorVendedor.setLayout(null);
        panelGraficadorVendedor.setBounds(600, 255, 330, 280);
        panelGraficadorVendedor.setBackground(Color.LIGHT_GRAY);
        Vendedores.add(panelGraficadorVendedor);
        ChartPanel chartPanelProducto = new ChartPanel(chartVendedor);
        chartPanelProducto.setBounds(1, 1, 330, 280);
        chartPanelProducto.setVisible(true);
        panelGraficadorVendedor.add(chartPanelProducto);

        //Esta parte del codigo lo que hace es que pasa mi chartVendedor a formato png
        //Osea guarda mi chartVendedor como una imagen
        try {
            final ChartRenderingInfo informacion = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(GraficaBarVendedor);
            ChartUtilities.saveChartAsPNG(file1, chartVendedor, 450, 380, informacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == CrearSucursal) {
            nuevaSucursal sucursal_nueva = new nuevaSucursal();
            sucursal_nueva.setVisible(true);
            dispose();
        }

        if (ae.getSource() == cMSucursal) {
            leerArchivos();

            ventanaAdministradora manejoSucursal = new ventanaAdministradora();
            manejoSucursal.pestañas.setSelectedIndex(0);
            manejoSucursal.setVisible(true);
            dispose();
        }

        if (ae.getSource() == actualizarSucursal) {

            actualizarSucursalV actualizarSucursalv = new actualizarSucursalV();
            actualizarSucursalv.setVisible(true);
            dispose();
        }

        if (ae.getSource() == eliminarSucursal) {

            eliminarSucursalV eliminarSucursalv = new eliminarSucursalV();
            eliminarSucursalv.setVisible(true);
            dispose();
        }

        if (ae.getSource() == exportarSucursales) {
            JOptionPane.showMessageDialog(this, "Se esta exportando la información de las Sucursales a pdf");

            //Vamos a exportar la info de la tabla y la grafica a un pdf, para eso crearemos un metodo
            //y lo mandaremos a llamar
            crearPDFSucursales();
            JOptionPane.showMessageDialog(this, "Se ha creado archivo Sucursales en pdf");

        }

        if (ae.getSource() == crearProducto) {
            nuevoProducto producto_nuevo = new nuevoProducto();
            producto_nuevo.setVisible(true);
            dispose();
        }

        if (ae.getSource() == CMProducto) {
            leerArchivos();

            mayorCantidadProductos();
            ordenarPorCodigoProducto();
            ventanaAdministradora manejoSucursal = new ventanaAdministradora();
            manejoSucursal.pestañas.setSelectedIndex(1);
            manejoSucursal.setVisible(true);
            dispose();
        }

        if (ae.getSource() == actualizarProducto) {

            actualizarProductoV actualizarProductov = new actualizarProductoV();
            actualizarProductov.setVisible(true);
            dispose();
        }

        if (ae.getSource() == eliminarProducto) {

            eliminarProductoV eliminarProductov = new eliminarProductoV();
            eliminarProductov.setVisible(true);
            dispose();
        }

        if (ae.getSource() == exportarProductos) {
            JOptionPane.showMessageDialog(this, "Se esta exportando la información de los Productos a pdf");

            //Vamos a exportar la info de la tabla y la grafica a un pdf, para eso crearemos un metodo
            //y lo mandaremos a llamar
            crearPDFProductos();
            JOptionPane.showMessageDialog(this, "Se ha creado archivo Productos en pdf");
        }

        if (ae.getSource() == crearCliente) {
            nuevoClienteV cliente_nuevo = new nuevoClienteV();
            cliente_nuevo.setVisible(true);
            dispose();
        }

        if (ae.getSource() == CMCliente) {
            leerArchivos();

            ventanaAdministradora manejoSucursal = new ventanaAdministradora();
            manejoSucursal.pestañas.setSelectedIndex(2);
            manejoSucursal.setVisible(true);
            dispose();
        }

        if (ae.getSource() == actualizarCliente) {

            actualizarClienteV actualizarClientev = new actualizarClienteV();
            actualizarClientev.setVisible(true);
            dispose();
        }

        if (ae.getSource() == eliminarCliente) {

            eliminarClienteV eliminarClientev = new eliminarClienteV();
            eliminarClientev.setVisible(true);
            dispose();
        }

        if (ae.getSource() == exportarCliente) {
            JOptionPane.showMessageDialog(this, "Se esta exportando la información de los clientes a pdf");

            //Vamos a exportar la info de la tabla y la grafica a un pdf, para eso crearemos un metodo
            //y lo mandaremos a llamar
            creacionPDFCliente();
            JOptionPane.showMessageDialog(this, "Se ha creado archivo Clientes en pdf");
        }
        
        if (ae.getSource() == crearVendedor) {

            nuevoVendedorV vendedor_nuevo = new nuevoVendedorV();
            vendedor_nuevo.setVisible(true);
            dispose();
        }

        if (ae.getSource() == cMVendedor) {
            leerArchivos();

            mayorCantidadVendedores();
            ordenarPorCodigoVendedor();
            ventanaAdministradora manejoSucursal = new ventanaAdministradora();
            manejoSucursal.pestañas.setSelectedIndex(3);
            manejoSucursal.setVisible(true);
            dispose();
        }
        
        if (ae.getSource() == ActualizarVendedor) {

            actualizarVendedorV actualizarVendedorv = new actualizarVendedorV();
            actualizarVendedorv.setVisible(true);
            dispose();
        }

        if (ae.getSource() == eliminarVendedor) {

            eliminarVendedorV eliminarVendedorv = new eliminarVendedorV();
            eliminarVendedorv.setVisible(true);
            dispose();
        }

        if (ae.getSource() == ExportarVendedor) {
            JOptionPane.showMessageDialog(this, "Se esta exportando la información de los vendedores a pdf");

            //Vamos a exportar la info de la tabla y la grafica a un pdf, para eso crearemos un metodo
            //y lo mandaremos a llamar
            crearPDFVendedor();
            JOptionPane.showMessageDialog(this, "Se ha creado archivo vendedores en pdf");

        }
        
        if (ae.getSource() == retornoLoginS || ae.getSource() == retornoLoginP) {
            login regresar_a_login = new login();
            regresar_a_login.setVisible(true);
            dispose();
        }

        if (ae.getSource() == retornoLoginC || ae.getSource() == retornoLoginV) {
            login regresar_a_login = new login();
            regresar_a_login.setVisible(true);
            dispose();
        }

    }

    public void leerArchivos() {
        try {

            JFileChooser fc = new JFileChooser();

            //Creamos el filtro
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JSON", "json");

            //Le indicamos el filtro
            fc.setFileFilter(filtro);

            int op = fc.showOpenDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {

                archivo = fc.getSelectedFile();

                //Lo que hace es extraer la ruta absoluta del archivo fc.getSelectedFile()
                //Lo volvemos a string usando el metodo toString()
                //Pero lo que se necesita es solamente el name del archivo
                //para ello se usa un indice y veremos que mas, pero funciona
                String fullPath = fc.getSelectedFile().toString();
                int indice = fullPath.lastIndexOf("\\");
                String fileName = fullPath.substring(indice + 1);
                System.out.println(fileName);

                // se hace la lectura
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                // se lee linea a linea
                while ((linea = br.readLine()) != null) {
                    // Solo agregamos el contenido a un String
                    contenido += linea + "\n";
                }

                if (fileName.equalsIgnoreCase("Sucursales.json")) {
                    //se manda a llamar al metodo convertir a json Sucursales
                    contenido_json_a_sucursal();
                }

                if (fileName.equalsIgnoreCase("Productos.json")) {
                    //se manda a llamar al metodo convertir a json Productos
                    contenido_json_a_producto();
                }

                if (fileName.equalsIgnoreCase("Clientes.json")) {
                    //se manda a llamar al metodo convertir a json Clientes
                    contenido_json_a_cliente();
                }

                if (fileName.equalsIgnoreCase("Vendedores.json")) {
                    //se manda a llamar al metodo convertir a json Vendedores
                    contenido_json_a_vendedor();
                }

            } else if (op == JFileChooser.CANCEL_OPTION) {
                //se cierra y ya
                JOptionPane.showMessageDialog(this, "Esta cerrando....");
            }

        } catch (HeadlessException | IOException e) {
            e.getMessage();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (IOException e2) {
                e2.getMessage();
            }
        }
    }

    public static void contenido_json_a_sucursal() {

        // JsonParser parser -> Todos los metodos necesarios para interpretar un JSON.
        JsonParser parser = new JsonParser();
        // JsonArray arreglo de objetos JSON.
        JsonArray arreglo = parser.parse(contenido).getAsJsonArray();
        // PARA ESTE MOMENTO, ARREGLO YA TIENE LO QUE ES UN ARREGLO ALMACENADO
        System.out.println("Cantidad de Objetos: " + arreglo.size());

        //Iremos convirtiendo los datos de una 
        //vuelta en un objeto de tipo Sucursal
        for (int i = 0; i < arreglo.size(); i++) {

            // JsonObject -> Tomar el Objeto del Arreglo
            JsonObject objeto = arreglo.get(i).getAsJsonObject();

            // GUARDAMOS LOS DATOS EN VARIABLES
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String direccion = objeto.get("direccion").getAsString();
            String correo = objeto.get("correo").getAsString();
            String telefono = objeto.get("telefono").getAsString();

            if (contador_sucursales < sucursales.length) {
                //Se crea una nueva sucursal
                Sucursal nuevaSucursal = new Sucursal(codigo, nombre, direccion, correo, telefono);

                //Se manda a llamar al procedimiento de agregar una nueva sucursal 
                //al arreglo de objetos de tipo sucursal 
                agregarSucursales(nuevaSucursal);
            } else {
                JOptionPane.showMessageDialog(null, "Solo se pueden manejar 50 sucursales");
                break;
            }
        }
        contenido = "";
    }

    public static void contenido_json_a_producto() {
        //System.out.println(contenido);

        // JsonParser parser -> Todos los metodos necesarios para interpretar un JSON.
        JsonParser parser = new JsonParser();
        // JsonArray arreglo de objetos JSON.
        JsonArray arreglo = parser.parse(contenido).getAsJsonArray();
        // PARA ESTE MOMENTO, ARREGLO YA TIENE LO QUE ES UN ARREGLO ALMACENADO
        System.out.println("Cantidad de Objetos: " + arreglo.size());

        //Iremos convirtiendo los datos de una 
        //vuelta en un objeto de tipo Sucursal
        for (int i = 0; i < arreglo.size(); i++) {

            // JsonObject -> Tomar el Objeto del Arreglo
            JsonObject objeto = arreglo.get(i).getAsJsonObject();

            // GUARDAMOS LOS DATOS EN VARIABLES
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String descripcion = objeto.get("descripcion").getAsString();
            int cantidad = objeto.get("cantidad").getAsInt();
            double precio = objeto.get("precio").getAsDouble();

            if (contador_productos < productos.length) {
                //Se crea un nuevo producto
                Producto nuevoProducto = new Producto(codigo, nombre, descripcion, cantidad, precio);

                //Se manda a llamar al procedimiento de agregar un nuevo producto 
                //al arreglo de objetos de tipo producto 
                agregarProductos(nuevoProducto);
            } else {
                JOptionPane.showMessageDialog(null, "Solo se pueden manejar mas de 200 productos");
            }
        }
        ordenarPorCodigoProducto();
        contenido = "";
    }

    public static void contenido_json_a_cliente() {

        //System.out.println(contenido);
        // JsonParser parser -> Todos los metodos necesarios para interpretar un JSON.
        JsonParser parser = new JsonParser();
        // JsonArray arreglo de objetos JSON.
        JsonArray arreglo = parser.parse(contenido).getAsJsonArray();
        // PARA ESTE MOMENTO, ARREGLO YA TIENE LO QUE ES UN ARREGLO ALMACENADO
        System.out.println("Cantidad de Objetos: " + arreglo.size());

        //Iremos convirtiendo los datos de una 
        //vuelta en un objeto de tipo Sucursal
        for (int i = 0; i < arreglo.size(); i++) {
            // JsonObject -> Tomar el Objeto del Arreglo
            JsonObject objeto = arreglo.get(i).getAsJsonObject();

            // GUARDAMOS LOS DATOS EN VARIABLES
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String nit = objeto.get("nit").getAsString();
            String correo = objeto.get("correo").getAsString();
            String genero = objeto.get("genero").getAsString();

            if (contador_clientes < clientes.length) {
                //Se crea un nuevo cliente
                Cliente nuevoCliente = new Cliente(codigo, nombre, nit, correo, genero);

                //Se manda a llamar al procedimiento de agregar un nuevo cliente 
                //al arreglo de objetos de tipo cliente 
                agregarClientes(nuevoCliente);
            } else {
                JOptionPane.showMessageDialog(null, "Solo se pueden manejar mas de 100 clientes");
            }

        }
        contenido = "";
    }

    public static void contenido_json_a_vendedor() {
        //System.out.println(contenido);

        // JsonParser parser -> Todos los metodos necesarios para interpretar un JSON.
        JsonParser parser = new JsonParser();
        // JsonArray arreglo de objetos JSON.
        //JsonArray arreglo = parser.parse(contenidoVendedores).getAsJsonArray();
        JsonArray arreglo = parser.parse(contenido).getAsJsonArray();
        // PARA ESTE MOMENTO, ARREGLO YA TIENE LO QUE ES UN ARREGLO ALMACENADO
        System.out.println("Cantidad de Objetos: " + arreglo.size());

        //Iremos convirtiendo los datos de una 
        //vuelta en un objeto de tipo Sucursal
        for (int i = 0; i < arreglo.size(); i++) {
            // JsonObject -> Tomar el Objeto del Arreglo
            JsonObject objeto = arreglo.get(i).getAsJsonObject();

            // GUARDAMOS LOS DATOS EN VARIABLES
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            int caja = objeto.get("caja").getAsInt();
            int ventas = objeto.get("ventas").getAsInt();
            String genero = objeto.get("genero").getAsString();
            String password = objeto.get("password").getAsString();

            if (contador_vendedores < vendedores.length) {
                //Se crea un nuevo vendedor
                Vendedor nuevoVendedor = new Vendedor(codigo, nombre, caja, ventas, genero, password);
                //Se manda a llamar al procedimiento de agregar un nuevo vendedor 
                //al arreglo de objetos de tipo vendedor 
                agregarVendedores(nuevoVendedor);
            } else {
                JOptionPane.showMessageDialog(null, "Solo se pueden manejar 400 vendedores");
                //System.out.println("Solo se pueden manejar 400 vendedores");
            }

        }
        contenido = "";
    }
}
