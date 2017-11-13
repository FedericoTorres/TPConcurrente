
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
        PlazaAEstado plazas = new PlazaAEstado();
        System.out.println("---------------------");
        TransicionAEvento transiciones = new TransicionAEvento();
        PetriNet red = new PetriNet("/home/leo/NetBeansProjects/TPFinal_Concurrente/src/datos/MatrizIncidencia.csv"
                , plazas.getKeys(), transiciones.getKeys()
                , "/home/leo/NetBeansProjects/TPFinal_Concurrente/src/datos/MarcadoInicial.csv"
                , "/home/leo/NetBeansProjects/TPFinal_Concurrente/src/datos/MatrizSensibilizadas.csv");
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
