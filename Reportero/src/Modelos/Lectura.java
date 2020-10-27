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
public class Lectura {
    String FechaUtc;
    double lectura;
    
    
    public Lectura(){}
    public Lectura(String FechaUtc, double lectura) {
        this.FechaUtc = FechaUtc;
        this.lectura = lectura;
    }

    public String getFechaUtc() {
        return FechaUtc;
    }

    public void setFechaUtc(String FechaUtc) {
        this.FechaUtc = FechaUtc;
    }

    public double getLectura() {
        return lectura;
    }

    public void setLectura(double lectura) {
        this.lectura = lectura;
    }
    
}
