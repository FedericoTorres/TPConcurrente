
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author leonardo
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // TODO code application logic here
        String path1 = "/src/datos/";
        String path2 = "MatrizIncidencia.csv";
        String path3 = "MarcadoInicial.csv";
        String path4 = "MatrizSensibilizadas.csv";
        String path5 = "DetallesDeTransiciones.csv";
        String path6 = "DetallesDePlazas.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        buff.append(path2);
        path2 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path3);
        path3 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path4);
        path4 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path5);
        path5 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path6);
        path6 = buff.toString();

        
        PlazaAEstado plazas = new PlazaAEstado(path6);
        System.out.println("---------------------");
        TransicionAEvento transiciones = new TransicionAEvento(path5);
        PetriNet red = new PetriNet(path2
                , plazas.getKeys(), transiciones.getKeys()
                , path3
                , path4);
        ArrayList<String> sensibilizadas = red.estanSensibilizadas();
        System.out.println(sensibilizadas);
        red.disparo("T10");
        sensibilizadas = red.estanSensibilizadas();
        System.out.println(sensibilizadas);
        red.disparo("T21");
        sensibilizadas = red.estanSensibilizadas();
        System.out.println(sensibilizadas);
        red.disparo("T11");
        sensibilizadas = red.estanSensibilizadas();
        System.out.println(sensibilizadas);
        


    }
    
}
