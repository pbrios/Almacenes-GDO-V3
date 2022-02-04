package com.example.almacenesgdov3.objetos;

import java.io.Serializable;

public class Productos implements Serializable {
    private String id;
    private String codigo;
    private String descripcion;
    private String unidad;
    private String cantidad;
    private String costo;
    private  String precio;

    public Productos(){}

    public Productos(String id, String codigo, String descripcion, String unidad, String cantidad) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    public Productos(String id, String codigo, String descripcion, String unidad, String costo, String precio) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.costo = costo;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidades() {
        return unidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getCosto(){
        return costo;
    }

    public String getPrecio(){
        return  precio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUnidades(String unidades) {
        this.unidad = unidades;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setCosto(String costo){
        this.costo = costo;
    }

    public void setPrecio(String precio){
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Productos{" +
                "id='" + id + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", unidad='" + unidad + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", costo='" + costo + '\'' +
                ", precio='" + precio + '\'' +
                '}';
    }
}
