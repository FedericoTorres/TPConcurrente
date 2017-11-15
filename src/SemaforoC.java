
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leo
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
     * 
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
