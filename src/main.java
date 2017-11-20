
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String path7 = "Hilos2.csv";
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
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path7);
        path7 = buff.toString();

        
        PlazaAEstado plazas = new PlazaAEstado(path6);
        System.out.println("---------------------");
        TransicionAEvento transiciones = new TransicionAEvento(path5);
        
        /*
        PetriNet red = new PetriNet(path2
                                    , plazas.getKeys(), transiciones.getKeys()
                                    , path3
                                    , path4);
        */
        
        Monitor monitor = new Monitor (path2, plazas.getKeys(),
                                        transiciones.getKeys(), path3, path4);
        ArrayList<Thread> hilos = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(path7));
        for (int i = 0; i < 9; i++)
        {
            String line  = br.readLine();
            String[] items = line.split(",");
            List list = Arrays.asList(items);
            Hilo hilo1 = new Hilo (monitor, transiciones.getKeys(), new ArrayList<>(list));
            hilos.add(new Thread(hilo1));
        }
        br.close();

        System.out.println("AQUI COMIENZA LA EJECUCION REAL");
        System.out.println("------------------------------");
        
        for (Thread aux : hilos)
        {
            aux.start();
        }
        //ola k ase

    }
    
}
