
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.PrintStream;
import java.util.Arrays;


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
public class Monitor 
{
    private Queues colas;
    private Politica politica;
    private PetriNet pn;
    private Semaphore mutex;
    private boolean k;
    private PrintStream archivito;
    private PrintStream marcados;
    
    public Monitor(PetriNet pn)
    {
        this.pn = pn;
        colas = new Queues(this.pn.getListaTransiciones());
        mutex = new Semaphore(1, true);
        k = false;
    }
    
    public Monitor (String matrizFile, Set plazas, Set transiciones, 
                    String marcadoInicialFile, String sensibilizadasFile,
                    String tiemposFile, int eleccion) throws IOException
    {
        pn = new PetriNet(matrizFile, plazas, transiciones, 
                          marcadoInicialFile, sensibilizadasFile,
                          tiemposFile);
        colas = new Queues (pn.getListaTransiciones());
        mutex = new Semaphore(1,true);
        k = false;
        politica  = new Politica (eleccion);
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        String path = "/src/datos/log_prueba.txt";
        String path2 = "/src/datos/log_marcados.txt";
        buff.append(filePath);
        buff.append(path);
        path = buff.toString();
        buff.delete(filePath.length(), buff.length());
        buff.append(path2);
        path2 = buff.toString();
        try 
        {
            archivito= new PrintStream(new FileOutputStream(path));
            marcados = new PrintStream(new FileOutputStream(path2));
        } catch (FileNotFoundException e) 
        {
			// TODO Auto-generated catch block
            e.printStackTrace();
	}
        archivito.println("\t\tTiempo\t\tID\t\tDescripción\t\tTransicion\t\t"
                            + "EnSemaforos\t\tSensibilizadas");
        archivito.println("");
    }
    
    /**
     * Método que realiza todo el control del monitor y la red de petri,
     * permitiendo el acceso exclusivo a 1 hilo.El método comprueba que una
     * transición se encuentre sensibilizada así como también las ventanas de
     * tiempo para determinar su ejecución.
     * Cuando su ejecución no es posible se toman diferentes acciones
     * dependiendo de por qué no pudo dispararse.
     * @param transicion 
     */
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
        while(k)
        {
            /**
             * Comprobar si la transición está sensibilizada y
             * si la misma es permitida según la política actual
             * En caso de que una de las condiciones no se cumpla, se considera
             * que la transición NO puede dispararse.
             */
            if(pn.puedeDispararse(transicion) && !politica.getInvalidas().contains(transicion))
            {
                /**
                 * La transición se encuentra sensibilizada y la política 
                 * permite su disparo, es necesario comprobar entonces
                 * si se encuentra dentro de la ventana de tiempo
                 */
                if (pn.testVentanaTiempo(transicion))
                {
                    /**
                     * Se encuentra dentro la ventana por lo que solo falta
                     * comprobar si otro hilo ya se encontraba esperando 
                     * por ejecutar la misma transición, en caso de que ningún 
                     * hilo se encuentre en espera, puede dispararse
                     */
                    if (pn.hiloAlguienEspera(transicion))
                    {
                        pn.disparo(transicion);
                        politica.registrarDisparo(transicion);
                        marcados.println(Arrays.toString(pn.getMarcado()));
                        ArrayList<String> sensibilizadas = pn.estanSensibilizadas();
                        ArrayList<String> esperando = colas.getEsperando();
                        archivito.println(System.currentTimeMillis()+ "\t\t" +Thread.currentThread().getId() +
                                        "\t\tDisparando\t\t\t" + transicion
                                        + "\t\t\t" + esperando + "\t\t\t" + sensibilizadas);
                        sensibilizadas.retainAll(esperando);
                        /**
                         * Si la intersección de las transiciones sensibilizadas
                         * y transiciones bloqueadas es 0, entonces se pone k
                         * en false y para que otro hilo entre al Monitor
                         */
                        if (sensibilizadas.isEmpty()) 
                        {
                            k = false;
                        } 
                        else 
                        {
                            /**
                             * Si la lista no estaba vacía hay que comprobar
                             * que la lista no posea transiciones inválidas
                             * definidas por la política. Si ninguna transición
                             * es disparable, se coloca K en false para que otro
                             * hilo tenga acceso al monitor.
                             */
                            sensibilizadas = politica.cualDisparar(sensibilizadas);
                            if (sensibilizadas.isEmpty()) 
                            {
                                k = false;
                            } 
                            /**
                             * Al menos una transición es disparable según la 
                             * política, luego de disparar se desbloquea un
                             * hilo de las colas de manera aleatoria y se 
                             * sale del monitor.
                             */
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
                    }
                    /** Un hilo se encuentra esperando para disparar dicha
                     * transición
                     * 
                     */
                    else
                    {
                        archivito.println(System.currentTimeMillis() + "\t\t" + 
                                Thread.currentThread().getId()+"\t\tBloqueado\t\t\t" + transicion +
                                            "\t\t\tDentroDeLaVentana Y Otro Hilo Espera");
                        colas.acquireTransition(transicion);
                        mutex.release();
                    }
                }
                /**
                 * El hilo no se encuentra dentro la ventana y es necesario
                 * comprobar si está antes de la ventana (tiempo menor a alfa)
                 */
                else if (pn.antesDeLaVentana(transicion))
                {
                    /**
                     * Se encuentra antes de la ventana y es necesario
                     * comprobar que ningún otro hilo se encuentra
                     * esperando por la misma transición
                     */
                    if (pn.hiloAlguienEspera(transicion))
                    {
                        pn.hiloAEsperar(transicion);
                        long tiempoHasta = pn.cuantoFaltaAVentana(transicion);
                        archivito.println(System.currentTimeMillis() + "\t\t" + 
                                Thread.currentThread().getId() + "\t\tDurmiendo:" + tiempoHasta 
                                            + "ms\t\t" +  transicion + "\t\t\tAntes de Ventana"
                                + " y nadie espera");
                        mutex.release();
                        try 
                        {
                            Thread.sleep(tiempoHasta);
                             pn.hiloSalirEspera(transicion);
                            mutex.acquire();
                        } 
                        catch (InterruptedException ex) 
                        {
                            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       k = true;
                    }
                    /** El hilo se encuentra antes de la ventana y además
                     * otro hilo está esperando por disparar la misma
                     * transición
                     */
                    else
                    {
                        archivito.println(System.currentTimeMillis() + "\t\t" + 
                                Thread.currentThread().getId() + "\t\tBloqueado\t\t\t"
                                            + transicion + "\t\t\tAntes de Ventana y alguien espera");
                        mutex.release();
                        colas.acquireTransition(transicion);     
                    }
                }
                /**
                 * El hilo cayó fuera de la ventana de tiempo, es decir, su 
                 * tiempoes mayor a Beta. El programa fue diseñado para que esta
                 * condición nunca se produzca al definir Betas muy grandes.
                 */
                else
                {
                    archivito.println(System.currentTimeMillis() + "\t\t" + 
                            Thread.currentThread().getId() + "\t\tBloqueado\t\t\t" +
                                        transicion + "\t\tDespues de la ventana");
                    mutex.release();
                    colas.acquireTransition(transicion); 
                }
            }
            /**
             * La transición no se encuentra sensibilizada por lo tanto
             * el hilo debe bloquearse y liberar el monitor.
             */
            else
            {
                archivito.println(System.currentTimeMillis() + "\t\t" + 
                        Thread.currentThread().getId()+"\t\tBloqueado\t\t\t"+ transicion);
                mutex.release();
                colas.acquireTransition(transicion);
            }
        }
        /**
         * K fue false, se salió del ciclo, es necesario devolver el mutex 
         * y salir del monitor.
         */
        mutex.release();
    }
}
