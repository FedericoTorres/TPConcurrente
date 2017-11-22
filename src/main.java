
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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


        System.out.println("Bienvenido al Trabajo Final de Programación"
                + "  Concurrente de Corrado Leonardo & Torres Federico");
        System.out.println("Usted puede elegir Testear una ejecución previa"
                + " o puede realizar una nueva ejecución de la Red de Petri");
        System.out.println("Por favor, tenga en cuenta que para Testear debe"
                + " haber ejecutado anteriormente este programa.");
        System.out.println("Si desea ejecutar la Red de Petri, por favor"
                + " presione 1, si en cambio desea testear resultados previos"
                   + " presione 2");
        System.out.print("Ingrese su eleccion: ");
        int num;
        while (!teclado.hasNextInt()) 
        {
            System.out.println("Ingrese un número por favor!");
            System.out.print("Ingrese su elección: ");
            teclado.nextLine();
        }
        num = teclado.nextInt();
        System.out.println("Usted elegió la opción n°: " + num);
        if (num == 1)
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
            while (!teclado.hasNextInt()) 
            {
                System.out.println("Ingrese un número por favor!");
                System.out.print("Ingrese su elección: ");
                teclado.nextLine();
            }
            eleccion = teclado.nextInt();
            System.out.println("Usted eligió la política n°: " + eleccion);
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
        else if (num == 2)
        {
            TestPInvariante test = new TestPInvariante ();
            test.testear();
        }
        
          
        
       
    }
    
    
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
    
}
