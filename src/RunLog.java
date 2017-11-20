
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federico
 */
public class RunLog 
{
    private static RunLog singleton = null;
    private PrintStream transiciones_log;
    private PrintStream plazas_log;
    private PrintStream completo_log;
    private TransicionAEvento transicionAEvento;
    private PlazaAEstado plazaAEstado;

    protected RunLog(TransicionAEvento transicionAEvento)
    {
        this.transicionAEvento = transicionAEvento;
        StringBuffer buff = new StringBuffer();
        String filePath = new File("").getAbsolutePath();
        buff.append(filePath);
        buff.append("/src/datos/transiciones_log.csv");
        
        try 
        {
            transiciones_log = new PrintStream(new FileOutputStream(buff.toString()));
        } 
        catch(FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        
      /*  try 
        {
            plazas_log = new PrintStream(new FileOutputStream("logs/plazas_log.csv"));
        } 
        catch(FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            completo_log = new PrintStream(new FileOutputStream("logs/completo_log.csv"));
        } 
        catch(FileNotFoundException e) 
        {
            e.printStackTrace();
        }*/
    }
    
    public synchronized static RunLog getInstancia(TransicionAEvento transicionAEvento)
    {
            if(singleton == null)
                singleton = new RunLog(transicionAEvento);
            
            return singleton;
    }

    public synchronized void log(String transicion)
    {
        //completo_log.println(tiempo + "," + TransicionAEvento.getEvento().getEvento(evento) + "," + evento + "," + PlazaToEstado.getPlazaToEstado().getEstado(estado) + "," + estado);
        transiciones_log.println(transicionAEvento.getEvento(transicion) + "," + transicion);
        //plazas_log.println(tiempo + "," + PlazaAEstado.getPlazaToEstado().getEstado(plaza) + "," + plaza);
    }
	
}
