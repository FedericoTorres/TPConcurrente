
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
    HashMap <String, Integer> contadorFinal;
    private PrintStream logPiezas;
    
    public Politica (int eleccion)
    {
        politicaActual = new HashMap<>();
        contador = new HashMap<>();
        contadorFinal = new HashMap<>();
        
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        String path = "/src/datos/log_piezas.txt";
        buff.append(filePath);
        buff.append(path);
        path = buff.toString();
        buff.delete(filePath.length(), buff.length());
        
        try 
        {
            logPiezas = new PrintStream(new FileOutputStream(path));
        } catch (FileNotFoundException e) 
        {
			// TODO Auto-generated catch block
            e.printStackTrace();
	}
        
        if  (eleccion == 1)
        {
            politicaActual.put ("T10", 1);
            politicaActual.put ("T21", 2);
            politicaActual.put ("T31", 1);
        }
        else
        {
            politicaActual.put ("T10", 3);
            politicaActual.put ("T21", 2);
            politicaActual.put ("T31", 1);
        }
        contador.put ("T10", 0);
        contador.put ("T21", 0);
        contador.put ("T31", 0);
        contador.put ("TOTAL", 0);
        contadorFinal.put ("T19", 0);
        contadorFinal.put ("T24", 0);
        contadorFinal.put ("T36", 0);
    }
    
    
    public void registrarDisparo (String transicion)
    {
        if (politicaActual.containsKey(transicion)) //testear
        {
            contador.replace (transicion, this.getNumero(transicion, contador) + 1);
            contador.replace ("TOTAL", this.getNumero("TOTAL", contador) + 1);         
            
            logPiezas.println("Linea 1: " + getNumero("T10", contador) + " piezas.");
            logPiezas.println("Linea 2: " + getNumero("T21", contador) + " piezas.");
            logPiezas.println("Linea 3: " + getNumero("T31", contador) + " piezas.");
            logPiezas.println("TOTAL: " + getNumero("TOTAL", contador) + " piezas.");
        }
        if (contadorFinal.containsKey(transicion))
        {
            contadorFinal.replace(transicion, contadorFinal.get(transicion) + 1);
            logPiezas.println("Final Linea 1: " + getNumero("T19", contadorFinal) + " piezas.");
            logPiezas.println("Final Linea 2: " + getNumero("T24", contadorFinal) + " piezas.");
            logPiezas.println("Final Linea 3: " + getNumero("T36", contadorFinal) + " piezas.");
        }
            
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
        }
                
    }
    
    public ArrayList<String> cualDisparar (ArrayList<String> obj)
    {
        ArrayList <String> aux = this.getInvalidas();
        if (!aux.isEmpty())
        {
            obj.removeAll(aux);        
        }
        return obj; 
    }
   
    
    
    public ArrayList<String> getInvalidas ()
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
        return aux;
            
    }
    
    private int getNumero (String transicion, HashMap<String,Integer> obj)
    {
        return obj.get(transicion);
    }
    
    private void vaciarContador ()
    {
        for (String clave : politicaActual.keySet())
            contador.replace(clave, 0);
    }
}
