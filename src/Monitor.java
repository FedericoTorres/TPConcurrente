
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.PrintStream;
import java.util.Date;


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
    private PrintStream archivito;
    
    public Monitor(PetriNet pn)
    {
        this.pn = pn;
        colas = new Queues(this.pn.getListaTransiciones());
        mutex = new Semaphore(1, true);
        k = false;
    }
    
    public Monitor (String matrizFile, Set plazas, Set transiciones, 
                    String marcadoInicialFile, String sensibilizadasFile) throws IOException
    {
        pn = new PetriNet(matrizFile, plazas, transiciones, 
                    marcadoInicialFile, sensibilizadasFile);
        colas = new Queues (pn.getListaTransiciones());
        mutex = new Semaphore(1,true);
        k = false;
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        String path = "/src/datos/log_prueba.txt";
        buff.append(filePath);
        buff.append(path);
        path = buff.toString();
        try 
        {
			archivito= new PrintStream(new FileOutputStream(path));
			archivito.println("Timestamp\t\tThreadID\t\tAccion\t\tTransicion\t\tDetalle");
        } catch (FileNotFoundException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
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
                archivito.println(new Date().getTime() + "\t\t" + Thread.currentThread().getId()+"\t\tDisparando\t\t\t"+ transicion +"\t\t[enventana,nadieesp]");
                ArrayList<String> sensibilizadas = pn.estanSensibilizadas();
                ArrayList<String> esperando = colas.getEsperando();
               // System.out.println(transicion);
              //  System.out.println(sensibilizadas);
                sensibilizadas.retainAll(esperando);
                //System.out.println("En el mutex de la cola esta" + sensibilizadas);
                if(sensibilizadas.isEmpty())
                {
                    k = false;
                }
                else
                {
                    int numAleatorio;
                    if (sensibilizadas.size() > 1)
                    {
                        numAleatorio = (int) (Math.random() * sensibilizadas.size());

                    }
                    else
                    {
                        numAleatorio = 0;
                    }
                    colas.releaseTransition(sensibilizadas.get(numAleatorio));
                    return;
                }
            }
            else
            {
                archivito.println(new Date().getTime() + "\t\t" + Thread.currentThread().getId()+"\t\tBloqueado\t\t\t"+ transicion +"\t\t[nosensibilizada]");
                mutex.release();
                colas.acquireTransition(transicion);
                //System.out.println("Usted intento disparar " + transicion);

            }
        }
        
        mutex.release();
    }
}
