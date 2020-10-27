/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.List;

/**
 *
 * @author windows
 */
public class Agente {

     private String agente;
     private String sensor;
     private double lectMinima;
     private double lectMaxima;
     private double lectMed;  
    List<Lectura> lectura;

    public Agente(){}
    public Agente(String agente, String sensor, double lectMinima, double lectMaxima, double lectMed, List<Lectura> lectura) {
        this.agente = agente;
        this.sensor = sensor;
        this.lectMinima = lectMinima;
        this.lectMaxima = lectMaxima;
        this.lectMed = lectMed;
        this.lectura = lectura;
    }
    
    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public double getLectMinima() {
        return lectMinima;
    }

    public void setLectMinima(double lectMinima) {
        this.lectMinima = lectMinima;
    }

    public double getLectMaxima() {
        return lectMaxima;
    }

    public void setLectMaxima(double lectMaxima) {
        this.lectMaxima = lectMaxima;
    }

    public double getLectMed() {
        return lectMed;
    }

    public void setLectMed(double lectMed) {
        this.lectMed = lectMed;
    }

    public List<Lectura> getLectura() {
        return lectura;
    }

    public void setLectura(List<Lectura> lectura) {
        this.lectura = lectura;
    }
    
    
}
