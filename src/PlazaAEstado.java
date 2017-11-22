
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


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
 * @author Corrado, Leonardo y Torres, Federico
 */
public class PlazaAEstado 
{
    private HashMap<String,String> mapaEstados;
    
    
    public PlazaAEstado(String file) throws IOException
    {
        mapaEstados = new HashMap<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));
        String line  = br.readLine();
        while (line != null)
        {
            String[] items = line.split(",");
            mapaEstados.put(items[1], items[0]);
            line = br.readLine();
        }
        br.close();
        for (String clave : mapaEstados.keySet())
        {
            String cl = clave;
            String valor = mapaEstados.get(cl);
            //System.out.println(cl + " " + valor);
        }
      
    }
    
     /**
     *  Método que devuelve la descripción de la plaza 
     *  indicada como parámetro.
     * 
     * @param plaza
     * @return String
     */
    public synchronized String getEstado(String plaza)
    {
        return mapaEstados.get(plaza);
    }
    
    public Set getKeys()
    {
        return mapaEstados.keySet();
    }







}








