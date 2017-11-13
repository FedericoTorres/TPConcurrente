/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federico
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Queues 
{
    private HashMap<String, Semaphore> colas;
    
    public Queues(ArrayList<String> transiciones)
    {
        colas = new HashMap<String, Semaphore>();
        
        for(String transicion: transiciones)
            colas.put(transicion, new Semaphore(0));
    }
    
    public void acquireTransition(String transicion)
    {
        try
        {
            colas.get(transicion).acquire();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public void releaseTransition(String transicion)
    {  
        colas.get(transicion).release();
    }
    
    public ArrayList<String> getEsperando()
    {
        ArrayList<String> listaEsperando = new ArrayList<String>();
        
        for(String transicion : colas.keySet())
        {    
            if(colas.get(transicion).getQueueLength() > 0)
                listaEsperando.add(transicion);
        }
        
        return listaEsperando;
    }
}
