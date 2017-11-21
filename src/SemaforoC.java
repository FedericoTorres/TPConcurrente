
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
