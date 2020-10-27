/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportero;
import Modelos.Agente;
import Modelos.Lectura;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author windows
 */


public class Reportero {
    
    /**
     * @param args
     * @SERVIDOR Linux
     */

    public static void main(String[] args) {
        
        ServerSocket servidor;
        servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;
        JSONParser parser = new JSONParser();
        String JSON ="";
        Date fechaSistema = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");      
        int contador=0;
        //puerto de nuestro servidor
        final int PUERTO = 9999;
        try {
             int confirmado = JOptionPane.showConfirmDialog(
                        null,
                        "¿Iniciar Servidor Linux?");

                     if (JOptionPane.OK_OPTION == confirmado){
                           JOptionPane.showMessageDialog(null,"Servicio Iniciado...");
                        System.out.println("OK");  
                        }
                     else{
                        System.out.println("Cancelado");
                     System.exit(0);}
            //Creamos el socket del servidor         
            servidor = new ServerSocket(PUERTO);
            System.out.println("Esperando Respuesta..");
            //Siempre estara escuchando peticiones
            while (true) {

                //Espero a que un cliente se conecte
                sc = servidor.accept();

                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());           

                //Recupero el id agente leido y armo el json                 
                 String [] data= new String[1];
                 String obj= in.readUTF();  
                 data=obj.split("/");
                 String json=data[0];
                 String nombre=data[1];
                 Agente agente;
                 JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);                
                  String fecha =jsonObject.get("fecha").toString();  
                  String agent =jsonObject.get("agente").toString();
                  
                  // muestra por pantalla el id del agente leido                 
                 JOptionPane.showMessageDialog(null,"Lectura del FTP correctamente\n\n Id del Agente: "+agent+"\n\n Fecha de lectura >>  "+hourFormat.format(fechaSistema));            
                 JSONArray array = (JSONArray) jsonObject.get("lectura");
                 int cont=(int)array.size();//Obtengo Tamaño de la lista
                 
                 String [] spl=new String[cont]; // defino arreglo para obtener nuevos valores                          
                 String [] splSensor=new String[cont]; // defino arreglo para obtener nuevos valores      
                 Lectura lect=null;
                 List<Lectura> listLect=null;
                 listLect= new ArrayList();
            
             // Obtenemos valores de la la lectura y fecha 
               for(int i = 0 ; i < array.size() ; i++)
               {                                                
                JSONObject jsonObject1 = (JSONObject) array.get(i);   
                 splSensor[i]=jsonObject1.get("sensor").toString();
                 spl[i] =jsonObject1.get("lectura").toString();                                                                                    
               }                     
               
              for (int i = 0; i < array.size(); i++) {
                 lect=new Lectura();                                                     
                 lect.setFechaUtc(fecha);                 
                 lect.setLectura(new Double(spl[i]));   
                 listLect.add(lect);
               }
             
              // se generan los reportes por cada idSensor
                String codSensor0=splSensor[0];
                
                Gson gson=null;
                if (codSensor0 == null ? splSensor[0] == null : codSensor0.equals(splSensor[0])) {                                       
                    contador=0;
                    for (int m = 0; m <splSensor.length ; m++) {                    
                        agente= new Agente(agent, splSensor[m], new Double(spl[0]), new Double(spl[1]), new Double(spl[2]), listLect);
                        gson = new Gson();
                        JSON = gson.toJson(agente);
                    
                       
                        String ruta = "C:/ReportesGenerado/"+m+"-Reporte-"+nombre.trim();
                        File archivo = new File(ruta);
                        BufferedWriter bw;
                        if(archivo.exists())
                        {                            
                        bw = new BufferedWriter(new FileWriter(archivo));
                        bw.write("El archivo Ya existia");
                       } else {
                        bw = new BufferedWriter(new FileWriter(archivo));
                        bw.write(JSON);
                     
                    }
                     contador++;
                    bw.close();
                }
              }
            JTextArea textArea = new JTextArea("Reporte Generado\n\n Fecha: "+hourFormat.format(fechaSistema)+"\n\n Lectuara mínima;  "
                                     +spl[0]+"\n Lectura máxima:  "+spl[1]+"\n Lectura promedio"+spl[1]+"\n\n"+JSON+
                                     "\n\n\n Cantidad de reportes generados: "+contador);
            textArea.setColumns(50);
            textArea.setRows(20);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setSize(textArea.getPreferredSize().width, textArea.getPreferredSize().height);
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Reporte generado", JOptionPane.OK_OPTION);
                //Cierro el socket            
                sc.close();
                System.out.println("Se genero el reporte para el agente "+agent);
            }

        } catch (IOException ex) {
            
        } catch (ParseException ex) {
            Logger.getLogger(Reportero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
