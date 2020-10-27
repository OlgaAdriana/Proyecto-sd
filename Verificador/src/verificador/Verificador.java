/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificador;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.agente;
import modelos.lecturas;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Verificador {
    /**
     * @Aplicación de sistemas 
     */
 public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
        
        JSONParser parser = new JSONParser();
        String lectura = "";
        String sensor = "";          
        DataOutputStream out;
        JOptionPane.showMessageDialog(null,"Sistema de aplicación distribuida");
        int confirmado = JOptionPane.showConfirmDialog(
                        null,
                        "¿Genara Reportes?");

                     if (JOptionPane.OK_OPTION == confirmado)
                        System.out.println("OK");                        
                     else{
                        System.out.println("Cancelado");
                     System.exit(0);}
        
        ArrayList<String> lst=new ArrayList<>();        
        try {         
            lst=Colector("C:/Users/adria/OneDrive/Documents/Proyecto/ftp");
            
            for (int k = 0; k < lst.size(); k++){         
            Object obj = parser.parse(new FileReader("C:/Users/adria/OneDrive/Documents/Proyecto/ftp/"+lst.get(k)));
            JSONObject jsonObject = (JSONObject) obj;                     
            String agente =jsonObject.get("agente").toString();
            String fecha =jsonObject.get("fechahoraUTC").toString();
                        
            //Cabecera Json
            agente ob=null;                                                        
            JSONArray array = (JSONArray) jsonObject.get("lecturas");                                   
            lecturas lect=null;
            List<modelos.lecturas> listLect=null;
            listLect= new ArrayList();
            
            for(int i = 0 ; i < array.size() ; i++) {
                lect=new lecturas();               
                
                JSONObject jsonObject1 = (JSONObject) array.get(i);                
                sensor=jsonObject1.get("sensor").toString();
                lectura =jsonObject1.get("lectura").toString();              
                lect.setSensor(sensor);  
                lect.setLectura(new Double(lectura));
                listLect.add(lect);                                              
            }
            if (lect!=null) {
              ob =new agente(agente,fecha,listLect);   
            }
             Gson gson = new Gson();
             String JSON = gson.toJson(ob);             
              Socket miSocket;                                              
              miSocket = new Socket("192.168.100.105",9999);
              
              out = new DataOutputStream(miSocket.getOutputStream());
              
            //Envio un mensaje al cliente
            
            out.writeUTF(JSON+"/"+lst.get(k));              
            out.close();        
            }                     
            System.out.println("");
       } catch(FileNotFoundException e) { }
        catch(IOException e) { }       
    }
 
 //Método que obtiene ls archivos este metodo simula al agente colector ftp
   public static ArrayList<String> Colector(String folder) {
        boolean resultado;
        String archivo="";    
        List<String> lista=new ArrayList<>();
        File folderFile = new File(folder);
        if ((resultado = folderFile.exists())) {
            File[] files = folderFile.listFiles();
            int i=files.length;               
            for (File file : files) {
                boolean isFolder = file.isDirectory();
               archivo= file.getName();
                lista.add(archivo);                
            }
        }
        return (ArrayList<String>) lista;
    }
      }            