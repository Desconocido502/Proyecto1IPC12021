package test;

import static Ventanas.ControlarDatos.*;

import Ventanas.login;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class principal {

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(new McWinLookAndFeel());
            login inicio = new login();
            inicio.setVisible(true);

            serializarSucursal();
            serializarProductos();
            serializarClientes();
            serializarVendedores();
            serializarVentas();

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }

}
