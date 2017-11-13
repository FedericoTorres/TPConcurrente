
import java.io.IOException;

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
        PetriNet red = new PetriNet("/home/leonardo/NetBeansProjects/TPFinal_Concurrente/src/datos/MatrizIncidencia.csv"
                , plazas.getKeys(), transiciones.getKeys()
                , "/home/leonardo/NetBeansProjects/TPFinal_Concurrente/src/Datos/MarcadoInicial.csv");
    }
    
}
