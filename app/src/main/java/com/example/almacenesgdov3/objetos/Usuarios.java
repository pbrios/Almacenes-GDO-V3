package com.example.almacenesgdov3.objetos;

import java.io.Serializable;

public class Usuarios implements Serializable {
    private String name;
    private String id;
    private String idAlmacen;
    private String idSucursal;
    private String idPuesto;

    public Usuarios(){

    }

    public Usuarios(String name, String id, String idAlmacen, String idSucursal, String idPuesto) {
        this.name = name;
        this.id = id;
        this.idAlmacen = idAlmacen;
        this.idSucursal = idSucursal;
        this.idPuesto = idPuesto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(String idPuesto) {
        this.idPuesto = idPuesto;
    }


    @Override
    public String toString() {
        return "Usuarios{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", idAlmacen='" + idAlmacen + '\'' +
                ", idSucursal='" + idSucursal + '\'' +
                ", idPuesto='" + idPuesto + '\'' +
                '}';
    }
}
