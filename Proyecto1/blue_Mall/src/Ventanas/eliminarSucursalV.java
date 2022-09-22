package Ventanas;

import static Ventanas.ControlarDatos.eliminarSucursalCD;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class eliminarSucursalV extends JFrame implements ActionListener {

    //Se crean los labels
    JLabel l1, l2;

    //Se crean los botones
    JButton eliminarSucursal;

    //Se crea la caja de texto
    JTextField t1;

    public eliminarSucursalV() {
        //Colocandole fuente a los labels
        Font fuenteNueva = new Font("ARIAL", Font.BOLD, 30);
        Font fuentecodigo = new Font("ARIAL", Font.BOLD, 15);

        l1 = new JLabel("Eliminar Sucursal");
        l1.setBounds(25, 30, 350, 30);
        l1.setVisible(true);
        l1.setFont(fuenteNueva);
        this.add(l1);

        l2 = new JLabel("CÓDIGO:");
        l2.setBounds(25, 70, 150, 30);
        l2.setVisible(true);
        l2.setFont(fuentecodigo);
        this.add(l2);

        //Cajita de texto
        t1 = new JTextField("");
        t1.setBounds(100, 70, 225, 30);
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setVisible(true);
        this.add(t1);

        //eliminar un profesor
        eliminarSucursal = new JButton("Eliminar");
        eliminarSucursal.setBounds(105, 130, 150, 30);
        eliminarSucursal.setVisible(true);
        eliminarSucursal.addActionListener(this);
        this.add(eliminarSucursal);

        this.setTitle("Administrador");
        this.setBounds(100, 100, 350, 200);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int codigo_a_buscar = Integer.parseInt(t1.getText());
        if (ae.getSource() == eliminarSucursal) {

            if (eliminarSucursalCD(codigo_a_buscar)) {
                JOptionPane.showMessageDialog(this, "Se elimino la sucursal con el codigo:" + codigo_a_buscar);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la sucursal a eliminar con el codigo:" + codigo_a_buscar);
            }
            ventanaAdministradora manejoSucursal = new ventanaAdministradora();
            manejoSucursal.pestañas.setSelectedIndex(0);
            manejoSucursal.setVisible(true);
            dispose();
        }
    }

}
