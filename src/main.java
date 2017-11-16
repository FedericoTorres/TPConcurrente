
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String path7 = "Hilos.csv";
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
        
        Monitor monitor = new Monitor (path2
                , plazas.getKeys(), transiciones.getKeys()
                , path3
                , path4);

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
        
        Thread thread0 = hilos.get(0);
        Thread thread1 = hilos.get(1);
        Thread thread2 = hilos.get(2);
        Thread thread3 = hilos.get(3);
        Thread thread4 = hilos.get(4);
        Thread thread5 = hilos.get(5);
        Thread thread6 = hilos.get(6);
        Thread thread7 = hilos.get(7);
        Thread thread8 = hilos.get(8);

        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
       
        
        
        
        
        
        
        
        /*
        ArrayList<String> sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T10");
        red.disparo2("T10");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T21");
        red.disparo2("T21");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T11");
        red.disparo2("T11");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T10");
        red.disparo2("T10");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T22");
        red.disparo2("T22");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T23");
        red.disparo2("T23");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T24");
        red.disparo2("T24");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T13");
        red.disparo2("T13");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T12");
        red.disparo2("T12");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
        System.out.println("Voy a disparar T31");
        System.out.println(red.puedeDispararse2("T16"));
        red.disparo2("T31");
        sensibilizadas = red.estanSensibilizadas2();
        System.out.println(sensibilizadas);
*/


        
        

        


    }
    
}
