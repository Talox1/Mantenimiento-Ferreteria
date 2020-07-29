/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jose Pablo Sandoval
 */
public class Venta_productos {    
    private String nombreProducto;
    private String descripcion;
    private int precioUni;
    private int cantidad;
    private double total;
       
//    public Venta_productos(int id, String nombreProducto, String descripcion,int precioUni, int cantidad, int total){
//        this.id = id;
//        this.nombreProducto = nombreProducto;
//        this.descripcion = descripcion;
//        this.precioUni = precioUni;
//        this.cantidad = cantidad;
//        this.total = total;
//    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecioUni() {
        return precioUni;
    }

    public void setPrecioUni(int precioUni) {
        this.precioUni = precioUni;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
}
