/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author ViP
 */
public class CallableTestClass implements Callable<String> {
    
    int num1;
    int num2;

    public CallableTestClass(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }
    
    
 
    @Override
    public String call() throws Exception {
        //Thread.sleep(1000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName()+" says: "+num1+" * "+num2+" = "+(num1*num2);
    }
     
    public static void main(String args[]){
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newCachedThreadPool();
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        //Create MyCallable instance
        ArrayList<Callable<String>> callables=new ArrayList<Callable<String>>();
        Random ran=new Random((new Date()).getTime());
        for(int i=0;i<100;i++){
            callables.add(new CallableTestClass(ran.nextInt(50)+1,ran.nextInt(50)+1));
        }
        for(int i=0; i< 100; i++){
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callables.get(i));
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<String> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }
 
}
