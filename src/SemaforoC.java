
import java.util.concurrent.Semaphore;

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
public class SemaforoC extends Semaphore
{
    private int contador;
    public SemaforoC() 
    {
        super(0, true);
        contador = 0;
    }
    
    /**
     * 
     * Método que devuelva la cantidad de hilos que realizaron
     * un acquire de el semáforo
     * @return cantidad de hilos en esa cola
     */ 
    public synchronized int getLength()
    {
        return contador;
    }
    
    /**
     * Método que incrementa el contador del semaforo, debe utilizarse
     * antes de realizar un acquire.
     */
    public synchronized void getSemaphore()
    {
        contador++;
    }
    
    /**
     * Método que decrementa el contador de el semáforo, debe utilizarse
     * antes de realizar un release
     */
    public synchronized void releaseSemaphore()
    {
        contador--;
    }
    
}
