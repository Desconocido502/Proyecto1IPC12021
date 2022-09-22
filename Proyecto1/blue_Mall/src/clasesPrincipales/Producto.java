package clasesPrincipales;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class Producto implements Serializable {

    private int codigo;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precio;

    //Constructor vacio
    public Producto() {

    }

    public Producto(int codigo, String nombre, String descripcion, int cantidad, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void descontarProductos(int cantidad_a_descontar) {
        if (cantidad > 0) {
            cantidad = cantidad - cantidad_a_descontar;
        } else {
            JOptionPane.showMessageDialog(null, "No se pueden retirar "
                    + "mas productos, productos agotados!");
        }
    }

    @Override
    public String toString() {
        return "Producto {\n" + " codigo=" + codigo + ",\n nombre=" + nombre + ",\n descripcion=" + descripcion + ",\n cantidad=" + cantidad + ",\n precio=" + precio + "\n}";
    }

}
