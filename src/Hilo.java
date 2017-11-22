
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author Leonardo Corrado & Federico Torres
 */
public class Hilo implements Runnable
{
    private ArrayList<String> transicionesADisparar;
    Monitor monitor;
    
    public Hilo (Monitor monitor, Set transiciones,
                 ArrayList<String> filtro) throws FileNotFoundException, IOException
    {
        transicionesADisparar = new ArrayList<>(transiciones);
        this.monitor = monitor;
        Collections.sort(transicionesADisparar);
        transicionesADisparar.retainAll(filtro);
    }
    

    @Override
    public void run() 
    {   
        while(true)
        {
            for (int i= 0; i < transicionesADisparar.size(); i++)
            {
                monitor.dispararTransicion(transicionesADisparar.get(i));
            }
        }
        
    }
    
 
    
}
