package clasesPrincipales;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class Vendedor implements Serializable {

    private int codigo;
    private String nombre;
    private int caja;
    private int venta;
    private String genero;
    private String password;
    private Venta ventas[];
    private static final int MAX_VENTAS = 50;
    private int contador_ventas;

    //Constructor vacio
    public Vendedor() {

    }

    public Vendedor(int codigo, String nombre, int caja, int venta, String genero, String password) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.caja = caja;
        this.venta = venta;
        this.genero = genero;
        this.password = password;

        this.ventas = new Venta[MAX_VENTAS];
        this.contador_ventas = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Venta[] getVentas() {
        return ventas;
    }

    public void setVentas(Venta[] ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "Vendedor {\n" + " codigo=" + codigo + ",\n nombre=" + nombre + ",\n caja=" + caja + ",\n venta=" + venta + ",\n genero=" + genero + "\n  }";
    }

    public void MostrarDatos() {
        System.out.println("\n\nLista de ventas realizadas por el vendedor: " + this.nombre);
        for (int i = 0; i < ventas.length; i++) {
            if (ventas[i] != null) {
                System.out.println(ventas[i].toString());
                System.out.println("-----------------------------------\n");
            }//sino esta en nulo
        }
    }

    public void agregarVenta(Venta venta) {
        if (contador_ventas < MAX_VENTAS) {
            this.ventas[contador_ventas++] = venta;
        } else {
            JOptionPane.showMessageDialog(null, "La cantidad de ventas ha sido superada!");
        }
    }

    public void aumentarVentas() {
        this.venta = this.venta + 1; //Este se tiene que colocar al vendedor para poder 
                                     //mostrar que si se hizo la venta.
    }

}
