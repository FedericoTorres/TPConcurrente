
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * @author Leonardo Corrado & Federico Torres
 */
public class Tiempo 
{
    private int[][] alfaBeta;
    private long[] timestamps;
    private long[] quienEspera;
    
    public Tiempo (String tiemposFile) throws IOException
    {
        alfaBeta = new int [2][20];
        timestamps = new long [20];
        quienEspera = new long [20];
        Arrays.fill (timestamps, 0);
        Arrays.fill (quienEspera, -1);
        
        BufferedReader br = null;
        try 
        {
            br = new BufferedReader (new FileReader (tiemposFile));
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger (Tiempo.class.getName ()).log (Level.SEVERE, 
                              null, ex);
        }
        for (int i = 0; i < 20; i++)
        {
            String line = br.readLine ();
            String items [] = line.split (",");
            alfaBeta [0][i] = Integer.parseInt (items [1]);
            alfaBeta [1][i] = Integer.parseInt (items [2]);   
        }
    }
    
    /**
     * Método que permite verificar si una transición está dentro de su
     * ventana de tiempo permitida para dispararse
     * 
     * @param transicion
     * @return TRUE si la transición está dentro de su ventana de tiempo. FALSE
     * en caso contrario
     */
    public boolean testVentanaTiempo (int transicion)
    {
        long ahora = System.currentTimeMillis ();
        return ahora - timestamps [transicion] >= alfaBeta [0] [transicion]
                && ahora - timestamps [transicion] < alfaBeta [1] [transicion];
    }
    
    /**
     * Método que permite establecer un nuevo timestamp para una transicion
     * 
     * @param transicion
     * @param condicion. Indica si la transición está o no sensibilizada 
     */
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
    
    /**
     * Método que verifica si una transición determinada se encuentra antes
     * de su ventana de tiempo.
     * 
     * @param transicion
     * @return TRUE si la transición está antes de su ventana. FALSE en caso
     * contrario
     */
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
     * Método que registra un hilo (ID) para esperar por cierta transición
     * pasada como parámetro.
     * 
     * @param transicion 
     */
    public void hiloAEsperar (int transicion) 
    {
        quienEspera [transicion] = Thread.currentThread ().getId ();
    }
    
    /**
     * Método que coloca un -1 en la el array quienEspera en la posición
     * correspondiente a la transición pasada como parámetro (esto representa
     * que nadie está esperando en esa transición).
     * 
     * @param transicion 
     */
    public void hiloSalirEspera (int transicion)
    {
        quienEspera [transicion] = -1;
    }
    
    /**
     * Método que indica si hay algún hilo esperando en una transición
     * determinada, pasada como parámetro.
     * 
     * @param transicion
     * @return TRUE si NADIE espera o HILO ACTUAL espera, false
     * si alguien que no es el HILO ACTUAL espera.
     */
    public boolean hiloAlguienEspera (int transicion)
    {
        return quienEspera [transicion] == -1 ||
                quienEspera [transicion] == Thread.currentThread ().getId ();
    }
    
    /**
     * Método que devuelve el tiempo que le falta a una transición
     * sensibilizada para ingresar en su ventana de tiempo.
     * 
     * @param transicion
     * @return tiempo en ms hasta ingresar en la ventana de tiempo 
     */
    public long cuantoFaltaAVentana(int transicion)
    {
        return alfaBeta [0] [transicion] - (System.currentTimeMillis() - 
                timestamps [transicion]);
    }
    
}
