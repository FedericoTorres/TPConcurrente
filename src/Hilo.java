
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leo
 */
public class Hilo implements Runnable
{
    private ArrayList<String> transicionesADisparar;
    Monitor monitor;
    
    public Hilo (Monitor monitor, Set transiciones,
                 ArrayList<String> filtro) throws FileNotFoundException, IOException
    {
        transicionesADisparar = new ArrayList<>(transiciones);
        this.monitor = monitor;
        Collections.sort(transicionesADisparar);
        transicionesADisparar.retainAll(filtro);
        System.out.println(transicionesADisparar);

       

    }
    

    @Override
    public void run() 
    {   
        while(true)
        {
            for (String aux : transicionesADisparar)
            {
                monitor.dispararTransicion(aux);
            }
        }
        
    }
    
 
    
}
