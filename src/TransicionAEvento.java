
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leonardo
 */
public class TransicionAEvento 
{
     private HashMap<String,String> mapaTransiciones;
    
    
    public TransicionAEvento() throws IOException
    {
        mapaTransiciones = new HashMap<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader("/home/leo/NetBeansProjects/TPFinal_Concurrente/src/datos/DetallesDeTransiciones.csv"));
        String line  = br.readLine();
        while (line != null)
        {
            String[] items = line.split(",");
            mapaTransiciones.put(items[1], items[0]);
            line = br.readLine();
        }
        br.close();
        for (String clave : mapaTransiciones.keySet())
        {
            String cl = clave;
            String valor = mapaTransiciones.get(cl);
            System.out.println(cl + " " + valor);
        }
      
    }
    
    /**
     * Método que devuelve la descripción de la
     * transición indicada como parámetro
     * @param transicion
     * @return 
    */
    public String getEvento(String transicion)
    {
        return mapaTransiciones.get(transicion);
    }
    
    
    public Set getKeys()
    {
        return mapaTransiciones.keySet();
    }
}
