
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leo
 */
public class Politica 
{
    HashMap <String, Integer> politicaActual;
    HashMap <String, Integer> contador;
    
    public Politica (int eleccion)
    {
        politicaActual = new HashMap<>();
        contador = new HashMap<>();
        if  (eleccion == 1)
        {
            politicaActual.put ("T19", 1);
            politicaActual.put ("T24", 2);
            politicaActual.put ("T36", 1);
        }
        else
        {
            politicaActual.put ("T19", 3);
            politicaActual.put ("T24", 2);
            politicaActual.put ("T36", 1);
        }
        contador.put ("T19", 0);
        contador.put ("T24", 0);
        contador.put ("T36", 0);
        contador.put ("TOTAL", 0);
    }
    
    
    public void registrarDisparo (String transicion)
    {
        if (politicaActual.containsKey(transicion)) //testear
        {
            contador.replace (transicion, this.getNumero(transicion, contador) + 1);
        }       
                
    }
    
    public String cualDisparar (ArrayList<String> obj)
    {
        ArrayList <String> aux = new ArrayList<>();
        //Meter en la lista aux las transiciones que ya estan en su limite
        //y ya no se pueden disparar
        for (String clave : politicaActual.keySet())
        {
           if (getNumero(clave, contador) == getNumero(clave,politicaActual))
           {
               aux.add(clave);
           }
        }
            
        //Si se encontró un elemento ya en su limite de contador
        if (!aux.isEmpty())
        {
            //Si la lista tiene las 3 transiciones posibles ya se complió un ciclo
            // de la politica y hay que limpiar el contador
            if (aux.containsAll(politicaActual.keySet()))
            {
                this.vaciarContador();
            }
            //Si no tiene las 3 transiciones, remover de la lista entrante
            //aquellas transiciones que coincidan con la lista de transiciones
            //prohibidas
            else
            {
                obj.removeAll(aux);
            }
            
            //continuar------------
        }
      /*   La lista esta vacia, es decir se puede disparar cualquier transicion
        que la política no tiene drama en disparar cualquiera -->
        OR la lista fue limpiada y se puede ejecutar cualquiera -->
        OR de lista fueron eliminadas transiciones prohibidas y se puede
        ejecutar cualquiera de las que quedan
        

        */
    }
   
    
    
    private int getNumero (String transicion, HashMap<String,Integer> obj)
    {
        return contador.get(transicion);
    }
    
    private void vaciarContador ()
    {
        for (String clave : politicaActual.keySet())
            contador.replace(clave, 0);
    }
}
