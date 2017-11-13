
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leonardo
 */
public class PetriNet 
{
    private int matrizDeIncidencia [][];
    private int marcado [];
    private ArrayList<String> listaPlazas;
    private ArrayList<String> listaTransiciones;
    
    public PetriNet(String matrizFile, Set plazas, Set transiciones, String marcadoInicialFile) throws FileNotFoundException, IOException
    {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(matrizFile));
        String line  = br.readLine();
        String items [] = line.split(",");
        int filas = Integer.parseInt(items[0]);
        int columnas = Integer.parseInt(items[1]);
        System.out.println(filas);
        System.out.println(columnas);
        matrizDeIncidencia = new int[filas][columnas];
        System.out.println("------------");
        
        for (int i = 0; i < filas; i++)
        {
            line = br.readLine();
            items = line.split(",");
            for (int j = 0; j < columnas; j++)
            {
                matrizDeIncidencia [i] [j] = Integer.parseInt(items[j]);
                System.out.print(matrizDeIncidencia[i] [j]);
            }
            System.out.println("");
        }
        br.close();
        
        listaPlazas = new ArrayList<>(plazas);
        listaTransiciones = new ArrayList<>(transiciones);
        System.out.println(listaPlazas);
        System.out.println(listaTransiciones);
        Collections.sort(listaPlazas);
        Collections.sort(listaTransiciones);
        System.out.println(listaPlazas);
        System.out.println(listaTransiciones);
        
        marcado = new int [filas];
        br = new BufferedReader(new FileReader(marcadoInicialFile));
        line  = br.readLine();
        items = line.split(",");
        br.close();
        for (int i = 0; i < filas; i++)
        {
            marcado [i] = Integer.parseInt(items [i]);
        }
        System.out.println(Arrays.toString(marcado));



    }
}
