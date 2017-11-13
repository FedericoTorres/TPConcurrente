/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federico
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Queues 
{
    private HashMap<String, Semaphore> queues;
    private HashMap<String, Integer> waiting;
    
    public Queues(ArrayList<String> transitions)
    {
        queues = new HashMap<String, Semaphore>();
        waiting = new HashMap<String, Integer>();
        
        for(String transition: transitions)
        {
            queues.put(transition, new Semaphore(0));
            waiting.put(transition, 0);
        }
    }
    
    public void acquireTransition(String transition)
    {
        waiting.replace(transition, waiting.get(transition) + 1);
        
        try
        {
            queues.get(transition).acquire();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public void releaseTransition(String transition)
    {
        waiting.replace(transition, waiting.get(transition) - 1);
        
        queues.get(transition).release();
    }
    
    public ArrayList<String> getWaiting()
    {
        ArrayList<String> listWaiting = new ArrayList<String>();
        
        for(String transition : queues.keySet())
        {
            if(waiting.get(transition) > 0)
                listWaiting.add(transition);
        }
        
        return listWaiting;
    }
}
