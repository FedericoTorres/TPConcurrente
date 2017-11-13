
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federico
 */
public class Monitor 
{
    private Queues colas;
    private PetriNet pn;
    private Semaphore mutex;
    private boolean k;
    
    public Monitor(PetriNet pn)
    {
        this.pn = pn;
        colas = new Queues(this.pn.getListaTransiciones());
        mutex = new Semaphore(1, true);
        k = false;
    }
    
    public void dispararTransicion(String transicion)
    {
        try 
        {
            mutex.acquire();
        }
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        k = true;
        boolean seDisparo = false;
        while(k)
        {
            seDisparo = pn.disparo(transicion);
            
            if(seDisparo)
            {
                ArrayList<String> sensibilizadas = pn.estanSensibilizadas();
                ArrayList<String> esperando = colas.getEsperando();
                System.out.println(transicion);
                System.out.println(sensibilizadas);
                sensibilizadas.retainAll(esperando);
                System.out.println("En el mutex de la cola esta" + sensibilizadas);
                if(sensibilizadas.isEmpty())
                {
                    k = false;
                }
                else
                {
                    int numAleatorio = (int) (Math.random() * sensibilizadas.size());
                    colas.releaseTransition(sensibilizadas.get(numAleatorio));
                }
            }
            else
            {
                mutex.release();
                colas.acquireTransition(transicion);
                System.out.println("Usted intento disparar " + transicion);

            }
        }
        
        mutex.release();
    }
}
