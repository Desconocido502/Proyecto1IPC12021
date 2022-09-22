package Ventanas;

import static Ventanas.ControlarDatos.agregarClientes;
import static Ventanas.ControlarDatos.clientes;
import static Ventanas.ControlarDatos.contador_clientes;
import static Ventanas.ControlarDatos.verificarClienteNoRep;
import clasesPrincipales.Cliente;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class nuevoClienteV extends JFrame implements ActionListener {

    //Se crean los labels
    JLabel l1, l2, l3, l4, l5, l6, l7;

    //Se crean las cajitas de informacion
    JTextField t1, t2, t3, t4, t5;

    //Se crean los botones
    JButton crearCliente;

    static int codigo_a_buscar = 0;

    public nuevoClienteV() {
        Font fuenteNueva = new Font("ARIAL", Font.BOLD, 35);
        Font fuenteNueva2 = new Font("ARIAL", 0, 17);

        l1 = new JLabel("Agregar nuevo cliente");
        l1.setBounds(25, 30, 450, 40);
        l1.setVisible(true);
        l1.setFont(fuenteNueva);
        this.add(l1);

        l2 = new JLabel("CÓDIGO:");
        l2.setBounds(25, 120, 150, 40);
        l2.setVisible(true);
        l2.setFont(fuenteNueva2);
        this.add(l2);

        l3 = new JLabel("NOMBRE:");
        l3.setBounds(25, 170, 150, 40);
        l3.setVisible(true);
        l3.setFont(fuenteNueva2);
        this.add(l3);

        l4 = new JLabel("NIT:");
        l4.setBounds(25, 230, 150, 40);
        l4.setVisible(true);
        l4.setFont(fuenteNueva2);
        this.add(l4);

        l5 = new JLabel("Correo:");
        l5.setBounds(25, 290, 150, 40);
        l5.setVisible(true);
        l5.setFont(fuenteNueva2);
        this.add(l5);

        l6 = new JLabel("Genero:");
        l6.setBounds(25, 360, 150, 40);
        l6.setVisible(true);
        l6.setFont(fuenteNueva2);
        this.add(l6);

        //Declarando el textfield, osea la cajita donde se escribe texto
        //Codigo
        t1 = new JTextField("");
        t1.setBounds(145, 120, 410, 30);
        t1.setFont(new Font("ARIAL", 0, 17));
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setVisible(true);
        this.add(t1);

        //Nombre
        t2 = new JTextField("");
        t2.setBounds(145, 170, 410, 30);
        t2.setFont(new Font("ARIAL", 0, 17));
        t2.setHorizontalAlignment(SwingConstants.CENTER);
        t2.setVisible(true);
        this.add(t2);

        //nit
        t3 = new JTextField("");
        t3.setBounds(145, 230, 410, 30);
        t3.setFont(new Font("ARIAL", 0, 17));
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setVisible(true);
        this.add(t3);

        //correo
        t4 = new JTextField("");
        t4.setBounds(145, 290, 410, 30);
        t4.setFont(new Font("ARIAL", 0, 17));
        t4.setHorizontalAlignment(SwingConstants.CENTER);
        t4.setVisible(true);
        this.add(t4);

        //genero
        t5 = new JTextField("");
        t5.setBounds(145, 360, 410, 30);
        t5.setFont(new Font("ARIAL", 0, 17));
        t5.setHorizontalAlignment(SwingConstants.CENTER);
        t5.setVisible(true);
        this.add(t5);

        //crear una nueva cliente
        crearCliente = new JButton("Agregar");
        crearCliente.setBounds(430, 460, 150, 40);
        crearCliente.setVisible(true);
        crearCliente.addActionListener(this);
        this.add(crearCliente);

        //Se agregan los atributos para la ventana de crear cliente
        this.setTitle("Administrador");
        this.setBounds(100, 100, 600, 550);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        codigo_a_buscar = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el codigo del cliente a crear:"));
        while (verificarClienteNoRep(codigo_a_buscar)) {
            codigo_a_buscar = Integer.parseInt(JOptionPane.showInputDialog(this, "El codigo ingresado ya existe!,Ingrese el codigo del cliente a crear:"));
        }
        if (codigo_a_buscar != 0) {
            t1.setText(String.valueOf(codigo_a_buscar));
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String codigo = "", nombre = "", nit = "", correo = "", genero = "";
        codigo = t1.getText();
        nombre = t2.getText();
        nit = t3.getText();
        correo = t4.getText();
        genero = t5.getText();
        if (ae.getSource() == crearCliente) {

            if (codigo.equals("0") || nombre.equals("") || nit.equals("") || correo.equals("") || genero.equals("")) {
                JOptionPane.showMessageDialog(this, "No se han ingresado todos los valores o ninguno de ellos!, ingrese los valores");
            } else {
                int codigo_valido = Integer.parseInt(codigo);

                if (contador_clientes < clientes.length) {

                    Cliente nuevo_cliente = new Cliente(codigo_valido, nombre, nit, correo, genero);
                    agregarClientes(nuevo_cliente);
                } else {
                    JOptionPane.showMessageDialog(this, "Solo se pueden manejar más de 100 clientes");
                }
                ventanaAdministradora manejoSucursal = new ventanaAdministradora();
                manejoSucursal.pestañas.setSelectedIndex(2);
                manejoSucursal.setVisible(true);
                dispose();
            }
        }
    }

}
