package clasesPrincipales;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class Venta implements Serializable {

    private Vendedor vendedor;
    private Cliente cliente;
    private Producto[] productos_venta;
    private Double total;
    private int[] cantidad;
    private Double[] subTotal;
    private String fecha;
    private int noFactura;
    private int contador_productos;
    private final int MAX_PRODUCTOS = 45;

    //Constructor vacio por buena practica
    public Venta() {

    }

    public Venta(Vendedor vendedor, Cliente cliente, Double total, int[] cantidad, Double[] subTotal, String fecha, int noFactura) {
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.total = total;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.fecha = fecha;
        this.noFactura = noFactura;

        this.contador_productos = 0;
        this.productos_venta = new Producto[MAX_PRODUCTOS];
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto[] getProductos_venta() {
        return productos_venta;
    }

    public void setProductos_venta(Producto[] productos_venta) {
        this.productos_venta = productos_venta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int[] getCantidad() {
        return cantidad;
    }

    public void setCantidad(int[] cantidad) {
        this.cantidad = cantidad;
    }

    public Double[] getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double[] subTotal) {
        this.subTotal = subTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(int noFactura) {
        this.noFactura = noFactura;
    }

    @Override
    public String toString() {
        String datos = "";
        datos = "Vendedor: " + this.vendedor.getNombre() + ", caja: " + this.vendedor.getCaja() + ",\n"
                + "Nombre Cliente: " + this.cliente.getNombre() + ", NIT Cliente: " + this.cliente.getNit()
                + ",\n Productos vendidos: \n" + mostrarProductosEnVentana()
                + "total a pagar: " + this.total + ", numero Factura: " + this.noFactura;
        return datos;
    }

    public String mostrarProductosEnVentana() {
        String datos = "";
        for (int i = 0; i < productos_venta.length; i++) {
            if (this.productos_venta[i] != null) {
                datos += "Producto " + (i + 1) + ":\n{" + "codigo=" + this.productos_venta[i].getCodigo()
                        + ", Nombre=" + this.productos_venta[i].getNombre()
                        + ", SubTotal=" + this.subTotal[i] + ", cantidad=" + this.cantidad[i] + "}\n";
            }
        }
        return datos;
    }

    public void agregarProducto(Producto producto) {
        if (contador_productos < MAX_PRODUCTOS) {
            this.productos_venta[contador_productos++] = producto;
        } else {
            JOptionPane.showMessageDialog(null, "La cantidad de productos ha sido superada");
        }

    }

    public void mostrarListaDeVentas() {
        System.out.println("Cliente: " + this.cliente.getNombre() + ", NIT: " + this.cliente.getNit() + "\n");
        System.out.println("Lista de ventas hechas por el vendedor: " + this.vendedor.getNombre()
                + ", caja:" + this.vendedor.getCaja() + "\n");
        System.out.println("Productos: \n");
        for (int i = 0; i < productos_venta.length; i++) {
            if (productos_venta[i] != null) {
                System.out.println("Producto " + (i + 1) + ":\n");
                System.out.println("Codigo: " + this.productos_venta[i].getCodigo()
                        + ", Nombre: " + this.productos_venta[i].getNombre()
                        + ", Cantidad: " + +this.cantidad[i]
                        + ", Subtotal: " + this.subTotal[i]);
            }
        }
        System.out.println("-----------------------------------------------");
        System.out.println("Total a pagar: " + this.total + ", no.Factura: " + this.noFactura);
    }

}
