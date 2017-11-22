
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
public class main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        // TODO code application logic here
        String path1 = "/src/datos/Hilos2.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        path1 = buff.toString();
        Scanner teclado = new Scanner(System.in);

        int num = queEjecutar(teclado);
        
        if (num == 1)
        {
            
            int eleccion = quePolitica(teclado);
            teclado.close();
            PlazaAEstado plazas;
            plazas = instanciarPlazaAEstado();
            System.out.println("Ejecutando y Registrando en los logs.....");
            TransicionAEvento transiciones = instanciarTransicionAEvento();
            Monitor monitor = instanciarMonitor(plazas, transiciones, eleccion);
            ArrayList<Thread> hilos = new ArrayList<>();
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(path1));
            for (int i = 0; i < 18; i++) 
            {
                String line = br.readLine();
                String[] items = line.split(",");
                List list = Arrays.asList(items);
                Hilo hilo1 = new Hilo(monitor, transiciones.getKeys(), new ArrayList<>(list));
                hilos.add(new Thread(hilo1));
            }
            br.close();
            for (Thread aux : hilos) 
            {
                aux.start();
            }   
        }
        else
        {
            TestPInvariante test = new TestPInvariante ();
            test.testear();
        }
        
          
        
       
    }
    
    
   /**
    * Función que se encarga de devolver una instancia de la clase monitor
    * pasándole como parámetro los traductores y la política que decidió
    * el usuario
    * @param plazas
    * @param transiciones
    * @param eleccion
    * @return
    * @throws IOException 
    */
    public static Monitor instanciarMonitor(PlazaAEstado plazas, 
                                              TransicionAEvento transiciones,
                                              int eleccion) throws IOException
    {
        String path1 = "/src/datos/";
        String path2 = "MatrizIncidencia.csv";
        String path3 = "MarcadoInicial.csv";
        String path4 = "MatrizSensibilizadas.csv";
        String path8 = "tiempos.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        buff.append(path2);
        path2 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path3);
        path3 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());
        buff.append(path4);
        path4 = buff.toString();
        buff.delete((filePath.length() + path1.length()), buff.length());       
        buff.append(path8);
        path8 = buff.toString();
        
        return new Monitor(path2, plazas.getKeys(),
                transiciones.getKeys(),
                path3, path4, path8, eleccion);
    }
    
    /**
     * Método que se encarga de instanciar el traductor de plazas, de esta clase
     * se obtendrá una lista con todas las plazas de la red.
     * @return
     * @throws IOException 
     */
    public static PlazaAEstado instanciarPlazaAEstado() throws IOException
    {
        String path1 = "/src/datos/DetallesDePlazas.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        path1 = buff.toString();
        return new PlazaAEstado(path1);
    }
    
    /**
     * Método que se encarga de instanciar el traductor de transiciones, de esta
     * clase se obtendrá una lista con todas las transiciones de la red.
     * @return
     * @throws IOException 
     */
    public static TransicionAEvento instanciarTransicionAEvento () throws IOException
    {
        String path1 = "/src/datos/DetallesDeTransiciones.csv";
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append(path1);
        path1 = buff.toString();
        return new TransicionAEvento(path1);
    }
    
    /**
     * Método que da la bienvenida al usuario y le permite ingresar por teclado
     * que acción desea realizar. Ejecutar el test de la RDP o ejecutar la RDP
     * @param teclado, Scanner que analiza las entradas por teclado
     * @return entero que representa la elección realizada por el usuario
     */
    public static int queEjecutar(Scanner teclado)
    {
        System.out.println("Bienvenido al Trabajo Final de Programación"
                + "  Concurrente de Corrado Leonardo & Torres Federico");
        System.out.println("Usted puede elegir Testear una ejecución previa"
                + " o puede realizar una nueva ejecución de la Red de Petri");
        System.out.println("Por favor, tenga en cuenta que para Testear debe"
                + " haber ejecutado anteriormente este programa.");
        System.out.println("Si desea ejecutar la Red de Petri, por favor"
                + " presione 1, si en cambio desea testear resultados previos"
                + " presione cualquier otro número");
        System.out.print("Ingrese su eleccion: ");
        int num;
        while (!teclado.hasNextInt()) {
            System.out.println("Ingrese un número por favor!");
            System.out.print("Ingrese su elección: ");
            teclado.nextLine();
        }
        num = teclado.nextInt();
        System.out.println("Usted elegió la opción n°: " + num);
        return num;
    }
    
    /**
     * Método que le permite al usuario elegir qué política debera manejar 
     * la red de petri.
     * @param teclado Scanner que analiza la entrada por teclado
     * @return entero que representa la política que eligió el usuario.
     */
    public static int quePolitica(Scanner teclado)
    {
        System.out.println("Elija una de las siguientes políticas:");
        System.out.println("Ingrese 1 si desea:"
                + " 2 piezas de B por cada pieza de A y C");
        System.out.println("Ingrese 2 si desea:"
                + " 3 piezas de A por cada pieza de B y C");
        System.out.println("Ingrese 3 si desea:"
                + "una política de disparo equitativa");
        System.out.print("Ingrese su elección: ");
        int eleccion;
        while (!teclado.hasNextInt()) {
            System.out.println("Ingrese un número por favor!");
            System.out.print("Ingrese su elección: ");
            teclado.nextLine();
        }
        eleccion = teclado.nextInt();
        System.out.println("Usted eligió la política n°: " + eleccion);
        return eleccion;
    }
    
}
