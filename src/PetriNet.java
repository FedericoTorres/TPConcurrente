
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
    private HashMap<String, Boolean> listaSensibilizadas;
    
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
        br = new BufferedReader(new FileReader(marcadoInicialFile));
        line  = br.readLine();
        items = line.split(",");
        br.close();
        for (int i = 0; i < filas; i++)
        {
            marcado [i] = Integer.parseInt(items [i]);
        }
       // System.out.println(Arrays.toString(marcado));
        
        listaSensibilizadas = new HashMap<>();
        br = new BufferedReader(new FileReader(sensibilizadasFile));
        line = br.readLine();
        items = line.split(",");
        br.close();
        for (int i = 0; i < columnas; i++)
        {
            listaSensibilizadas.put(listaTransiciones.get(i),Boolean.parseBoolean(items[i]));
        }
       // System.out.println(listaSensibilizadas + "  " + listaSensibilizadas.size());
    }
        
        
    /**
     * Función que se encarga de disparar una transición de la red de Petri
     * @param transicion  -> la transición a disparar
     * @return retorna true si la transición se disparo, false en caso contrario
     */
    public boolean disparo (String transicion)
    {
        boolean tmp = puedeDispararse(transicion);
        if (tmp)
        {
            for (int i = 0; i < listaPlazas.size(); i++)
            {
                marcado [i] = marcado [i] + matrizDeIncidencia [i] [listaTransiciones.indexOf(transicion)];
            }
        actualizarSensibilizadas();
        }
        return tmp;
    }
    
    /**
     * Método que devuelve todas las transiciones en condiciones de disparo
     * @return ArrayList de Transiciones
     */
    public ArrayList<String> estanSensibilizadas()
    {
        ArrayList<String> sensibilizadas = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>(listaSensibilizadas.keySet());
        for (String tmp : aux)
        {
            if (listaSensibilizadas.get(tmp))
            {
                sensibilizadas.add(tmp);
            }
        }
        
        Collections.sort(sensibilizadas);
        return sensibilizadas;
    }
    
    /**
     * Función privada que se encarga de actualizar la lista
     * de transiciones disponibles para disparo.
     */
    private void actualizarSensibilizadas ()
    {
        for (String clave : listaTransiciones)
        {
            listaSensibilizadas.replace(clave, Boolean.TRUE);
        }
        for (int i = 0; i < listaTransiciones.size(); i++)
        {
            for (int j = 0; j < listaPlazas.size(); j++)
            {
                if (marcado [j] == 0 && matrizDeIncidencia [j] [i] == -1)
                {
                    listaSensibilizadas.put(listaTransiciones.get(i), Boolean.FALSE);
                    break;
                }
            }
        }
    }
    
    /**
     * Función que corrobora si una transición puede dispararse
     * comprobando el HashMap de transiciones disparables.
     * @param transicion
     * @return 
     */
    private boolean puedeDispararse(String transicion)
    {
        return listaSensibilizadas.get(transicion);
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

