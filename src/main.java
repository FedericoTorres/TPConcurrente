
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
 * @author Leonardo Corrado & Federico Torres
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        // TODO code application logic here
        String path1 = "/src/datos/Hilos2.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        path1 = buff.toString();


        
        
        PlazaAEstado plazas;
        plazas = instanciarPlazaAEstado();
        System.out.println("---------------------");
        TransicionAEvento transiciones = instanciarTransicionAEvento();
        Monitor monitor = instanciarMonitor(plazas, transiciones);
        ArrayList<Thread> hilos = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(path1));
        for (int i = 0; i < 18; i++)
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
    }
    
    
    public static Monitor instanciarMonitor(PlazaAEstado plazas, 
                                    TransicionAEvento transiciones) throws IOException
    {
        String path1 = "/src/datos/";
        String path2 = "MatrizIncidencia.csv";
        String path3 = "MarcadoInicial.csv";
        String path4 = "MatrizSensibilizadas.csv";
        String path8 = "tiempos.csv";
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
        buff.append(path8);
        path8 = buff.toString();
        
        return new Monitor(path2, plazas.getKeys(),
                transiciones.getKeys(),
                path3, path4, path8);
    }
    
    public static PlazaAEstado instanciarPlazaAEstado() throws IOException
    {
        String path1 = "/src/datos/DetallesDePlazas.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        path1 = buff.toString();
        return new PlazaAEstado(path1);
    }
    
    public static TransicionAEvento instanciarTransicionAEvento () throws IOException
    {
        String path1 = "/src/datos/DetallesDeTransiciones.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        path1 = buff.toString();
        return new TransicionAEvento(path1);
    }
    
}
