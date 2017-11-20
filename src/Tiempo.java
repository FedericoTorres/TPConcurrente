
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class Tiempo 
{
    private int[][] alfa_beta;
    private long[] timestamps;
    
    public Tiempo (String tiemposFile) throws IOException
    {
        alfa_beta = new int[2][20];
        timestamps = new long[20];
        
        
        BufferedReader br = null;
        try 
        {
            br = new BufferedReader(new FileReader(tiemposFile));
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Tiempo.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 20; i++)
        {
            String line = br.readLine();
            String items [] = line.split(",");
            alfa_beta [0] [i] = Integer.parseInt(items [1]);
            alfa_beta [1] [i] = Integer.parseInt(items [2]);   
        }
        for (int i = 0; i < 2; i ++)
        {
            for (int  j = 0; j < 20 ; j++)
            {
                System.out.print("  " + alfa_beta [i] [j]);
            }
            System.out.println("\n");
        }
    }
}
