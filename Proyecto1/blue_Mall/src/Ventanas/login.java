package Ventanas;

import static Ventanas.ControlarDatos.objSalida;
import static Ventanas.ControlarDatos.ois;
import static Ventanas.ControlarDatos.ordenarPorCodigoCliente;
import static Ventanas.ControlarDatos.vendedores;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class login extends JFrame implements ActionListener {

    static String nombre = "", password = "";
    static int numFactura = 1;

    //Objetos de la ventana
    //Estos son los label o etiquetas a usar
    JLabel l1, l2, titulo;
    //Esta es una caja de info normal
    JTextField t1;
    //Esta es una caja de info que no deja ver lo que se escribe dentro de ella
    JPasswordField contra;
    //Boton para inicio de sesion y boton para retornar al modulo general
    JButton iniciarSesion;

    public login() {
        Font fuenteNueva = new Font("ARIAL", Font.BOLD, 35);
        //FUente para los labels normales
        Font FLN = new Font("IMPACT", 0, 20);
        //Fuente para las cajas
        Font Fontcajas = new Font("ARIAL", 0, 15);

        //Declarando los labels
        titulo = new JLabel("POS");
        titulo.setBounds(220, 15, 150, 40);
        titulo.setVisible(true);
        titulo.setFont(fuenteNueva);
        this.add(titulo);

        l1 = new JLabel("Código:");
        l1.setBounds(25, 60, 150, 40);
        l1.setVisible(true);
        l1.setFont(FLN);
        this.add(l1);

        l2 = new JLabel("Contraseña:");
        l2.setBounds(25, 100, 150, 40);
        l2.setVisible(true);
        l2.setFont(FLN);
        this.add(l2);

        //Declarando el textfield, osea la cajita donde se escribe
        t1 = new JTextField("");
        t1.setBounds(135, 65, 340, 30);
        t1.setVisible(true);
        t1.setFont(Fontcajas);
        this.add(t1);

        //Declarando el textfield, osea la cajita donde se escribe
        contra = new JPasswordField("");
        contra.setBounds(135, 105, 340, 30);
        contra.setVisible(true);
        contra.setFont(Fontcajas);
        this.add(contra);

        //Declarando el boton de inicio de sesion
        iniciarSesion = new JButton("Iniciar Sesión");
        iniciarSesion.setBounds(150, 160, 200, 30);
        iniciarSesion.setVisible(true);
        iniciarSesion.setFont(Fontcajas);
        iniciarSesion.addActionListener(this);
        this.add(iniciarSesion);

        //Se agregan los atributos para la ventana del Login
        this.setTitle("LOGIN");
        this.setBounds(100, 100, 500, 250);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }

    private static boolean isVendedor(String nombre) {
        boolean bandera = false;

        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i] != null) {
                if (nombre.equals(vendedores[i].getNombre())) {
                    return true;
                }
            }
        }
        return bandera;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        boolean banderaEntrada = false;
        nombre = t1.getText();
        password = contra.getText();
        if (ae.getSource() == iniciarSesion) {

            if (nombre.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(this, "Se dejaron campos vacios!");
            } else if (nombre.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Bienvenido al sistema " + nombre);
                ordenarPorCodigoCliente(); //agregalo que no se te olvide
                ventanaAdministradora mediadora = new ventanaAdministradora();
                mediadora.setVisible(true);
                dispose();
            } else if (nombre.equals("admin") && !password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Error!, contraseña erronea!");
            } else if (isVendedor(nombre)) {
                for (int i = 0; i < vendedores.length; i++) {
                    if (vendedores[i] != null) {
                        if (nombre.equals(vendedores[i].getNombre()) && password.equals(vendedores[i].getPassword())) {
                            banderaEntrada = true;
                            serializarnoFactura();
                            ventasV nuevasVentas = new ventasV(vendedores[i]);
                            nuevasVentas.setVisible(true);
                            dispose();
                            break;
                        } else if (nombre.equals(vendedores[i].getNombre()) && !password.equals(vendedores[i].getPassword())) {
                            banderaEntrada = true;
                            JOptionPane.showMessageDialog(this, "Error!, contraseña erronea!");
                            break;
                        }
                    }
                }
            } else if (!banderaEntrada) {
                JOptionPane.showMessageDialog(this, "El usuario no fue encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "Entrada al sistema denegado!");
            }

        }
    }

    public static void serializarnoFactura() {
        try {
            if (cargarNoFactura() != null) {
                numFactura = (int) cargarNoFactura();
                System.out.println("el valor del numero de factura es: " + numFactura);
            }
        } catch (Exception e) {
            System.out.println("El numero de factura es 0");
            //System.out.println(e.getMessage());
        }
    }

    public static Object cargarNoFactura() {
        Object object;
        try {
            ois = new ObjectInputStream(new FileInputStream("noFactura.bin"));
            object = ois.readObject();
            return object;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void escribirNoFactura(Object noFactura) {
        try {
            objSalida = new ObjectOutputStream(new FileOutputStream("noFactura.bin"));
            objSalida.writeObject(noFactura);
            objSalida.close();
            System.out.println("nF s");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
