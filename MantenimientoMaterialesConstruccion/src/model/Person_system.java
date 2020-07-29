/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Jose Pablo Sandoval
 */
public class Person_system {
    public String user;
    public float cambio;
    public String rol;
    public String Nombre;
    
    public Person_system(){
        this.Nombre = "";
        this.rol = "";
        this.user = "";
        this.cambio = 0;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
        System.out.println(Nombre);
    }        

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        System.out.println(user);
    }

    public float getCambio() {
        return cambio;
    }

    public void setCambio(float cambio) {
        this.cambio = cambio;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
        System.out.println(rol);
    }            
}
