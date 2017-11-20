
import java.io.File;
import java.util.Arrays;

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
    
    public Tiempo ()
    {
        alfa_beta = new int[2][20];
        timestamps = new long[20];
        Arrays.fill(alfa_beta[1], 10000);
        for(int i=0; i < 20; i++)
            System.out.println(alfa_beta[1][i]);
        
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        String path = "/src/datos/log_piezas.txt";
        buff.append(filePath);
        buff.append(path);
        path = buff.toString();
        buff.delete(filePath.length(), buff.length());
        
        alfa_beta[0][3] = 30;
        alfa_beta[0][4] = 24;
    }
}
