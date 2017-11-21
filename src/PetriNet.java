
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Leonardo Corrado & Federico Torres
 */
public class PetriNet 
{
    private int matrizDeIncidencia [][];
    private int marcado [];
    private ArrayList<String> listaPlazas;
    private ArrayList<String> listaTransiciones;
    private boolean sensibilizadas [];
    private Tiempo tiempo;
    
    public PetriNet (String matrizFile, Set plazas, Set transiciones, 
                       String marcadoInicialFile, String sensibilizadasFile,
                       String tiemposFile) throws FileNotFoundException, IOException
    {
        tiempo = new Tiempo (tiemposFile);
        BufferedReader br = null;
        br = new BufferedReader (new FileReader(matrizFile));
        String line  = br.readLine ();
        String items [] = line.split (",");
        int filas = Integer.parseInt (items [0]);
        int columnas = Integer.parseInt (items [1]);
        matrizDeIncidencia = new int [filas][columnas];
        for (int i = 0; i < filas; i++)
        {
            line = br.readLine ();
            items = line.split (",");
            for (int j = 0; j < columnas; j++)
            {
                matrizDeIncidencia [i][j] = Integer.parseInt (items[j]);
            }
            System.out.println ("");
        }
        br.close ();
        
        listaPlazas = new ArrayList<> (plazas);
        listaTransiciones = new ArrayList<> (transiciones);
        Collections.sort (listaPlazas);
        Collections.sort (listaTransiciones);
        
        marcado = new int [filas];
        sensibilizadas = new boolean [columnas];
        br = new BufferedReader (new FileReader (marcadoInicialFile));
        line  = br.readLine ();
        items = line.split (",");
        br.close ();
        for (int i = 0; i < filas; i++)
        {
            marcado [i] = Integer.parseInt (items [i]);
        }
        
        br = new BufferedReader (new FileReader (sensibilizadasFile));
        line = br.readLine ();
        items = line.split (",");
        br.close ();
        for (int i = 0; i < columnas; i++)
        {
            sensibilizadas [i] = Boolean.parseBoolean (items [i]);
        }
        System.out.println (Arrays.toString (sensibilizadas));
        this.actualizarSensibilizadas ();
    }
        
    /**
     * Función que se encarga de disparar una transición de la red de Petri.
     * @param transicion  -> la transición a disparar
     */
    public void disparo (String transicion)
    {
        for (int i = 0; i < listaPlazas.size (); i++)
        {
           marcado [i] = marcado [i] 
                         + matrizDeIncidencia [i] [listaTransiciones.indexOf (transicion)];
        }
        actualizarSensibilizadas ();
    }
    
    /**
     * Método que se encarga de determinar si una transicion se encuentra
     * dentro de la ventana de tiempo.
     * @param transicion
     * @return TRUE si está en la ventana de tiempo, FALSE si no.
     */
    public boolean testVentanaTiempo (String transicion)
    {
        return tiempo.testVentanaTiempo (this.getIndexOfTransition (transicion));
    }
    
    /**
     * Método que se encarga de determinar si una transición se encuentra
     * antes de la ventana de tiempo.
     * @param transicion
     * @return TRUE si la transición se encuentra antes de la ventana
     * FALSE si no está antes de la ventana
     */
    public boolean antesDeLaVentana (String transicion)
    {
        return tiempo.antesDeLaVentana (this.getIndexOfTransition (transicion));
    }
    
    /**
     * Método que devuelve todas las transiciones en condiciones de disparo.
     * @return ArrayList de transiciones
     */
    public ArrayList<String> estanSensibilizadas ()
    {
        ArrayList<String> listaAuxiliar = new ArrayList<> ();
        for (int i = 0; i < listaTransiciones.size (); i++)
        {
            if (sensibilizadas [i])
            {
                listaAuxiliar.add (listaTransiciones.get (i));
            }
        }
        return listaAuxiliar;
    }
    
    /** CAMBIAR???? EH?????
     * Función privada que se encarga de actualizar la lista
     * de transiciones disponibles para disparo.
     */
    private void actualizarSensibilizadas ()
    {
        for (int i = 0; i < listaTransiciones.size (); i++)
        {
            boolean res = true;
            
            for (int j = 0; j < listaPlazas.size (); j++)
            {
                if (marcado [j] + matrizDeIncidencia [j] [i] >= 0)
                    res = res && true;
                else
                    res = res && false;
            }
            
            sensibilizadas[i] = res;
            
            if (res)
            {
                if (tiempo.getTimestamp (i) == 0)
                {
                    tiempo.setNuevoTimestamp (i, res);
                }
            }
            else
            {
                tiempo.setNuevoTimestamp (i, res);
            }               
        }
    }
        
    /**
     * Función que corrobora si una transición puede dispararse
     * comprobando el HashMap de transiciones disparables.asd
     * @param transicion
     * @return 
     */
    public boolean puedeDispararse (String transicion)
    {
        if (listaTransiciones.contains (transicion))
        {
            return sensibilizadas [listaTransiciones.indexOf (transicion)];
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Método utilizado para traducir el nombre de una transición
     * a un entero, que oficia de índice para los vectores
     * utilizados en la clase Tiempo
     * @param transicion
     * @return entero que es el índice de la transicion
     */
    public int getIndexOfTransition (String transicion)
    {
        return listaTransiciones.indexOf (transicion);
    }
    
    /**
     * Método que se encarga de interfacear con el mismo método que
     * posee la clase Tiempo.
     * @param transicion que se utiliza para obtener un índice entero.
     */
    public void hiloAEsperar (String transicion)
    {
        this.tiempo.hiloAEsperar (getIndexOfTransition (transicion));
    }
    
    /**
     * Método que se encarga de interfacear con el mismo método que
     * posee la clase Tiempo y que saca de la espera a una transición.
     * @param transicion
     */
    public void hiloSalirEspera (String transicion)
    {
        this.tiempo.hiloSalirEspera (getIndexOfTransition (transicion));
    }
    
    public long cuantoFaltaAVentana (String transicion)
    {
        return this.tiempo.cuantoFaltaAVentana (getIndexOfTransition (transicion));
    }
    
    /**
     * Método que se encarga de interfacear con el mismo método que
     * que posee la clase Tiempo.
     * @param transicion
     * @return TRUE si ningún HILO está en espera. FALSE si alguien
     * distinto al HILO ACTUAL espera
     */
    public boolean hiloAlguienEspera (String transicion)
    {
        return this.tiempo.hiloAlguienEspera (getIndexOfTransition (transicion));
    }
    
    /** 
     * Método para obtener una lista de transiciones de la red de Petri.
     * 
     * @return ArrayList con los identificadores de las transiciones 
     */
    public ArrayList<String> getListaTransiciones ()
    {
        return listaTransiciones;
    }
    
    /**
     * Método para obtener una lista de las plazas de la red de Petri.
     * 
     * @return ArrayList con los identificadores de las plazas
     */
    public ArrayList<String> getListaPlazas ()
    {
        return listaPlazas;
    }
    
    /**
     * Método para obtener el marcado actual de la red de Petri.
     * 
     * @return Array que representa el marcado actual de la red
     */
    public int [] getMarcado ()
    {
        return this.marcado;
    }       
}
