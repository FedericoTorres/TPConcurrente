
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leonardo
 */
public class PlazaAEstado 
{
    private HashMap<String,String> mapaEstados;
    
    
    public PlazaAEstado() throws IOException
    {
        mapaEstados = new HashMap<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader("/home/leonardo/NetBeansProjects/TPFinal_Concurrente/src/datos/DetallesDePlazas.csv"));
        String line  = br.readLine();
        while (line != null)
        {
            String[] items = line.split(",");
            mapaEstados.put(items[1], items[0]);
            line = br.readLine();
        }
        br.close();
        for (String clave : mapaEstados.keySet())
        {
            String cl = clave;
            String valor = mapaEstados.get(cl);
            System.out.println(cl + " " + valor);
        }
      
    }
    
     /**
     *  Método que devuelve la descripción de la plaza 
     *  indicada como parámetro.
     * 
     * @param plazita
     * @return String
     */
    public String getEstado(String plazita)
    {
        return mapaEstados.get(plazita);
    }
    
    public Set getKeys()
    {
        return mapaEstados.keySet();
    }







}








