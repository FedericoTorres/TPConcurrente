/*
 * Copyright (C) 2017 Leonardo Corrado & Federico Torres
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

/**
 *
 * @author Federico Torres & Corrado Leonardo
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
