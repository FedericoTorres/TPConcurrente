
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class PetriNet 
{
    private int matrizDeIncidencia [][];
    private int marcado [];
    private ArrayList<String> listaPlazas;
    private ArrayList<String> listaTransiciones;
    private boolean sensibilizadas [];
    
    public PetriNet(String matrizFile, Set plazas, Set transiciones, 
                    String marcadoInicialFile, String sensibilizadasFile) throws FileNotFoundException, IOException
    {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(matrizFile));
        String line  = br.readLine();
        String items [] = line.split(",");
        int filas = Integer.parseInt(items[0]);
        int columnas = Integer.parseInt(items[1]);
       // System.out.println(filas);
       //System.out.println(columnas);
        matrizDeIncidencia = new int[filas][columnas];
       // System.out.println("------------");
        for (int i = 0; i < filas; i++)
        {
            line = br.readLine();
            items = line.split(",");
            for (int j = 0; j < columnas; j++)
            {
                matrizDeIncidencia [i] [j] = Integer.parseInt(items[j]);
               //System.out.print(matrizDeIncidencia[i] [j]);
            }
            System.out.println("");
        }
        br.close();
        
        listaPlazas = new ArrayList<>(plazas);
        listaTransiciones = new ArrayList<>(transiciones);
       // System.out.println(listaPlazas);
       // System.out.println(listaTransiciones);
        Collections.sort(listaPlazas);
        Collections.sort(listaTransiciones);
       // System.out.println(listaPlazas);
       // System.out.println(listaTransiciones);
        
        marcado = new int [filas];
        sensibilizadas = new boolean [columnas];
        br = new BufferedReader(new FileReader(marcadoInicialFile));
        line  = br.readLine();
        items = line.split(",");
        br.close();
        for (int i = 0; i < filas; i++)
        {
            marcado [i] = Integer.parseInt(items [i]);
        }
       // System.out.println(Arrays.toString(marcado));
        
        br = new BufferedReader(new FileReader(sensibilizadasFile));
        line = br.readLine();
        items = line.split(",");
        br.close();
        for (int i = 0; i < columnas; i++)
        {
            sensibilizadas [i] = Boolean.parseBoolean(items [i]);
        }
        System.out.println (Arrays.toString(sensibilizadas));
      
    }
        
        
    /**
     * Función que se encarga de disparar una transición de la red de Petri
     * @param transicion  -> la transición a disparar
     */
    public void disparo (String transicion)
    {
         for (int i = 0; i < listaPlazas.size(); i++)
         {
            marcado [i] = marcado [i] + matrizDeIncidencia [i] [listaTransiciones.indexOf(transicion)];
         }
        actualizarSensibilizadas();
    }
    
    /**
     * Método que devuelve todas las transiciones en condiciones de disparo
     * @return ArrayList de Transiciones
     */
    public ArrayList<String> estanSensibilizadas()
    {
        ArrayList<String> listaAuxiliar = new ArrayList<>();
        for (int i = 0; i < listaTransiciones.size(); i++)
        {
            if (sensibilizadas [i])
            {
                listaAuxiliar.add(listaTransiciones.get(i));
            }
        }
        return listaAuxiliar;
    }
    
    /**
     * Función privada que se encarga de actualizar la lista
     * de transiciones disponibles para disparo.
     */
    private void actualizarSensibilizadas ()
    {
        /*
        for (int i = 0; i < listaTransiciones.size() ; i++)
        {
            sensibilizadas [i] = true;
        }
        */
        for (int i = 0; i < listaTransiciones.size(); i++)
        {
            boolean res = true;
            for (int j = 0; j < listaPlazas.size(); j++)
            {
                if (marcado [j] + matrizDeIncidencia [j] [i] >= 0)
                    res = res && true;
                else
                    res = res && false;
            }
            sensibilizadas [i] = res;
                    
        }
    }
    
    
    /**
     * Función que corrobora si una transición puede dispararse
     * comprobando el HashMap de transiciones disparables.
     * @param transicion
     * @return 
     */
    public boolean puedeDispararse(String transicion)
    {
        if (listaTransiciones.contains(transicion))
        {
            return sensibilizadas [listaTransiciones.indexOf(transicion)];
        }
        else
        {
            return false;
        }
    }
    
    
    
    public ArrayList<String> getListaTransiciones()
    {
        return listaTransiciones;
    }
    
    public ArrayList<String> getListaPlazas()
    {
        return listaPlazas;
    }
    
    public int [] getMarcado()
    {
        return this.marcado;
    }
    
        
}

