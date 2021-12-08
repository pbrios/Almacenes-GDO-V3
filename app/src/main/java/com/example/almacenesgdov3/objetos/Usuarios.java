package com.example.almacenesgdov3.objetos;

import java.io.Serializable;

public class Usuarios implements Serializable {
    private String name;
    private String id;
    private String idAlmacen;

    public Usuarios(){

    }

    public Usuarios(String name, String id, String idAlmacen) {
        this.name = name;
        this.id = id;
        this.idAlmacen = idAlmacen;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getNombre() {
        return name;
    }

    public String getIdUsuario() {
        return id;
    }

    public String getIdAlmacen() {
        return idAlmacen;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", idAlmacen='" + idAlmacen + '\'' +
                '}';
    }
}
