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

public class Queues 
{
    private HashMap<String, SemaforoC> colas;
    
    public Queues(ArrayList<String> transiciones)
    {
        colas = new HashMap<>();
        
        for(String transicion: transiciones)
            colas.put(transicion, new SemaforoC());
    }
    
    /**
     *  Método que permite realizar un acquire de un
     * semáforo asociado a la transición que se pasa como parámetro.
     * @param transicion
     */
    public void acquireTransition(String transicion)
    {
        try
        {
            colas.get(transicion).getSemaphore();
            colas.get(transicion).acquire();
            
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Función que realiza el release de el semáforo asociado
     * a la transición ingresada como parámetro
     * @param transicion 
     */
    public void releaseTransition(String transicion)
    {  
        colas.get(transicion).releaseSemaphore();
        colas.get(transicion).release();
    }
    
    /**
     * Método que se encarga de registrar los semáforos
     * de cada transición y registrar en una lista aquellas transiciones que 
     * posean a alguien esperando en la misma.
     * @return Lista de String Transiciones
     */
    public ArrayList<String> getEsperando()
    {
        ArrayList<String> listaEsperando = new ArrayList<String>();
        
        for(String transicion : colas.keySet())
        {    
            if(colas.get(transicion).getLength() > 0)
                listaEsperando.add(transicion);
        }
        
        return listaEsperando;
    }
}
