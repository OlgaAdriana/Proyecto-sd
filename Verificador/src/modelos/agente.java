package modelos;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class agente {
    String agente;
    String fecha;
    List<lecturas> lectura;

    public agente(String agente, String fecha, List<lecturas> types) {
        this.agente = agente;
        this.fecha = fecha;
        this.lectura = types;
    }

    
    
    
    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<lecturas> getTypes() {
        return lectura;
    }

    public void setTypes(List<lecturas> types) {
        this.lectura = types;
    }

    public agente() {
    }
            
}
