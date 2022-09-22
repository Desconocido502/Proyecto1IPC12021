package clasesPrincipales;

import java.io.Serializable;

public class Sucursal implements Serializable {

    private int codigo;
    private String nombre;
    private String direccion;
    private String correo;
    private String telefono;

    //Constructor vacio 
    public Sucursal() {

    }

    //Constructor personalizado
    public Sucursal(int codigo, String nombre, String direccion, String correo, String telefono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Sucursal {\n" + " codigo=" + codigo + ",\n nombre=" + nombre + ",\n direccion=" + direccion + ",\n correo=" + correo + ",\n telefono=" + telefono + "\n}";
    }

}
