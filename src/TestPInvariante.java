
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;


/*
 * Copyright (C) 2017 leo
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
public class TestPInvariante 
{
    private int marcado [];
    
    public TestPInvariante()
    {
        marcado = new int [29];
    }
    
    public void testear() throws IOException
    {
        StringBuffer buffer = new StringBuffer();
        String path1 = "/src/tpfinal_test/log_marcados.txt";
        String path = new File("").getAbsolutePath();
        buffer.append(path);
        buffer.append(path1);
        path1 = buffer.toString();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestPInvariante.class.getName()).log(Level.SEVERE, null, ex);
        }

        String line = null;
        int k = 0;
        while ((line = br.readLine()) != null) 
        {
            k++;
            line = line.replace("[", "");
            line = line.replace("]", "");
            line = line.trim();
            String items[] = line.split(",");
            for (int i = 0; i < items.length; i++) 
            {
                items[i] = items[i].trim();
            }
            System.out.println(Arrays.toString(items));
            for (int i = 0; i < 29; i++)
            {
                marcado[i] = Integer.parseInt(items[i]);
            }
            assertEquals("Ecuacion1", 1, marcado[0] + marcado[11] + marcado[18]);
            assertEquals("Ecuacion2", 1, marcado[1] + marcado[10] + marcado[12] + marcado[14] + marcado[21] + marcado[22] + marcado[23] + marcado[24] + marcado[25]);
            assertEquals("Ecuacion3", 1, marcado[2] + marcado[17] + marcado[18]);
            assertEquals("Ecuacion4", 1, marcado[3] + marcado[9]);
            assertEquals("Ecuacion5", 1, marcado[4] + marcado[13] + marcado[18]);
            assertEquals("Ecuacion6", 1, marcado[5] + marcado[10] + marcado[24]);
            assertEquals("Ecuacion7", 1, marcado[6] + marcado[14] + marcado[22]);
            assertEquals("Ecuacion8", 10, marcado[7] + marcado[8] + marcado[9] + marcado[10] + marcado[11] + marcado[12] + marcado[13] + marcado[14] + marcado[15]);
            assertEquals("Ecuacion9", 10, marcado[16] + marcado[17] + marcado[18] + marcado[19]);
            assertEquals("Ecuacion10", 10, marcado[20] + marcado[21] + marcado[22] + marcado[23] + marcado[24] + marcado[25]);
            assertEquals("Ecuacion11", 1, marcado[8] + marcado[25] + marcado[26]);
            assertEquals("Ecuacion12", 1, marcado[11] + marcado[12] + marcado[17] + marcado[19] + marcado[23] + marcado[27]);
            assertEquals("Ecuacion13", 1, marcado[15] + marcado[21] + marcado[28]);
        }
        System.out.println("Test Passed " + k + " times");
    }
    
}
