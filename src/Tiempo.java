
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Corrado, Leonardo y Torres, Federico
 */
public class Tiempo 
{
    private int[][] alfaBeta;
    private long[] timestamps;
    private long[] quienEspera;
    
    public Tiempo (String tiemposFile) throws IOException
    {
        alfaBeta = new int[2][20];
        timestamps = new long[20];
        quienEspera = new long[20];
        Arrays.fill(timestamps, 0);
        Arrays.fill(quienEspera,-1);

        
        
        BufferedReader br = null;
        try 
        {
            br = new BufferedReader(new FileReader(tiemposFile));
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Tiempo.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 20; i++)
        {
            String line = br.readLine();
            String items [] = line.split(",");
            alfaBeta [0] [i] = Integer.parseInt(items [1]);
            alfaBeta [1] [i] = Integer.parseInt(items [2]);   
        }
        for (int i = 0; i < 2; i ++)
        {
            for (int  j = 0; j < 20 ; j++)
            {
                System.out.print("  " + alfaBeta [i] [j]);
            }
            System.out.println("\n");
        }
    }
    
    public boolean testVentanaTiempo (int transicion)
    {
        long ahora = System.currentTimeMillis ();
        return ahora - timestamps [transicion] >= alfaBeta [0] [transicion]
                && ahora - timestamps [transicion] < alfaBeta [1] [transicion];
    }
    
    public void setNuevoTimestamp (int transicion, boolean condicion)
    {
        if (condicion)
        {
            timestamps[transicion] = System.currentTimeMillis();
        }
        else
        {
            timestamps [transicion] = 0;
        }
    }
    
    public boolean antesDeLaVentana (int transicion)
    {
        long ahora = System.currentTimeMillis ();
        return ahora - timestamps [transicion] < alfaBeta [0] [transicion];
    }
    
    public long getTimestamp (int transicion)
    {
        return timestamps [transicion];
    }
    
    
    /**
     * Método que registra un hilo (ID) a esperar por cierta transicion
     * pasada como parametro
     * @param transicion 
     */
    public void hiloAEsperar(int transicion) 
    {
        quienEspera [transicion] = Thread.currentThread().getId();
    }
    
    /**
     * Se coloca un -1 (representa que  nadie espera)
     * en la transicion correspondiente
     * @param transicion 
     */
    public void hiloSalirEspera (int transicion)
    {
        quienEspera [transicion] = -1;
    }
    
    /**
     * 
     * @param transicion
     * @return TRUE si NADIE espera o HILO ACTUAL espero, false
     * si alguien que no soy HILO ACTUAL espera
     */
    public boolean hiloAlguienEspera (int transicion)
    {
        return quienEspera [transicion] == -1 ||
                quienEspera [transicion] == Thread.currentThread().getId();
    }
    
    public long cuantoFaltaAVentana(int transicion)
    {
        return alfaBeta [0] [transicion] - (System.currentTimeMillis() - 
                timestamps [transicion]);
    }
    
}
