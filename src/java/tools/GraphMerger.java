/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Edge;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_src.Main;
/**
 *
 * @author Silvestro
 */
public class GraphMerger {

    int GraphsMerged;
    ArrayList<Edge> TotalGraph;

    public int getGraphsMerged() {
        return GraphsMerged;
    }

    public ArrayList<Edge> getTotalGraph() {
        return TotalGraph;
    }

    
    public GraphMerger() {
        GraphsMerged = 0;
        TotalGraph = new ArrayList<Edge>();
    }
    
    public void setTotalGraph(ArrayList<Edge> graph){
       TotalGraph = graph;
    } 
    
    public void setGraphsMerged(int num){
       GraphsMerged = num;
    }
    
    public void incGraphsMerged(int num){
       GraphsMerged++;
    }
    
    public GraphMerger(ArrayList<GraphMerger> partials) { 
        GraphsMerged = 0;
        TotalGraph = new ArrayList<Edge>();
        System.out.println(Main.printTime(new Date()));
        for (int i = 0; i < partials.size(); i++) {
            GraphsMerged += partials.get(i).getGraphsMerged();
            for (int j = 0; j < partials.get(i).TotalGraph.size(); j++) {
                if (TotalGraph.indexOf(partials.get(i).TotalGraph.get(j)) < 0) {
                    Edge edNew = partials.get(i).TotalGraph.get(j);
                    edNew.setWeight(edNew.getWeight());
                    TotalGraph.add(edNew);
                } else {
                    TotalGraph.get(TotalGraph.indexOf(partials.get(i).TotalGraph.get(j))).setWeight( TotalGraph.get(TotalGraph.indexOf(partials.get(i).TotalGraph.get(j))).getWeight() + partials.get(i).TotalGraph.get(j).getWeight());
                }
            }
            //System.out.println("parent merger status: "+((float)i/partials.size())*100+"%");
            GraphsMerged++;
        }
        for (int i = 0; i < TotalGraph.size(); i++) {
            TotalGraph.get(i).setWeight(TotalGraph.get(i).getWeight()/GraphsMerged);
        }
    }
    
    public void mergeV3(ArrayList<NGramCreator> grams, int numOfBuckets, int numOfCores){
        ArrayList<ArrayList<NGramCreator>> splits= new ArrayList<ArrayList<NGramCreator>>(ThreadedDivider.splitArrayList(grams, numOfCores));
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(numOfBuckets);
        //create a list to hold the Future object associated with Callable
        List<Future<GraphMerger>> flist = new ArrayList<Future<GraphMerger>>();
        //Create MyCallable instance
        ArrayList<CallableBucket> buckets = new ArrayList<CallableBucket>();
        int sizeCounter=0;
        for (int i = 0; i < grams.size(); i++) {
            sizeCounter+=grams.get(i).Edges.size();
        }
        ArrayList<Integer> sizes=ThreadedDivider.splitIndexes(sizeCounter, numOfBuckets);
        for(int i=0;i<numOfBuckets;i++){
            boolean last=(i==(numOfBuckets-1));
            buckets.add(new CallableBucket(new ArrayList<Edge>(),i,-1,-1,sizes.get(i),last));
        }
        for(int i=0;i<numOfBuckets-1;i++){
            buckets.get(i).setNextBucket(buckets.get(i+1));
        }
        System.out.println("Filling buckets....");
        ArrayList<ThreadedBucketFiller> tbfs=new ArrayList<ThreadedBucketFiller>();
        for(int i=0;i<numOfCores;i++){
             tbfs.add(new ThreadedBucketFiller(buckets,splits.get(i)));
             tbfs.get(i).start();
        }
        for(int i=0;i<numOfCores;i++){
             if(tbfs.get(i).isAlive()){
                 try {
                     tbfs.get(i).join();
                 } catch (InterruptedException ex) {
                     ex.printStackTrace();
                 }
             }
        }
        System.out.println("Creating partials....");
        for(int i=0; i< numOfBuckets; i++){
            //submit Callable tasks to be executed by thread pool
            Future<GraphMerger> future = executor.submit(buckets.get(i));
            //add Future to the list, we can get return value using Future
            flist.add(future);
        }
        ArrayList<GraphMerger> partials=new ArrayList<GraphMerger>();
        for(Future<GraphMerger> fut : flist){
            try {
                partials.add(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
        System.out.println("Merging partials....");
        for (int i = 0; i < partials.size(); i++) {
            this.GraphsMerged+=partials.get(i).GraphsMerged;
            this.TotalGraph.addAll(partials.get(i).TotalGraph);
        }
        this.GraphsMerged=grams.size();
        System.out.println("Calculating weights....");
        for (int i = 0; i < this.TotalGraph.size(); i++) {
            this.TotalGraph.get(i).setWeight(this.TotalGraph.get(i).getWeight()/this.GraphsMerged);
        }
    }
    
    public void mergeV2(ArrayList<NGramCreator> grams){
        for (int i = 0; i < grams.size(); i++) {
            GraphsMerged++;
            for (int j = 0; j < grams.get(i).Edges.size(); j++) {
                int index=TotalGraph.indexOf(grams.get(i).Edges.get(j));
                if ( index < 0) {
                    Edge edNew = grams.get(i).Edges.get(j);
                    edNew.setWeight(edNew.getWeight());
                    TotalGraph.add(edNew);
                } else {
                    TotalGraph.get(index).setWeight( TotalGraph.get(index).getWeight() + 1);
                }
            }
            if(i%10==0)
            System.out.println("merger status: "+((float)i/grams.size())*100+"%");
        }
        for (int i = 0; i < TotalGraph.size(); i++) {
            TotalGraph.get(i).setWeight(TotalGraph.get(i).getWeight()/GraphsMerged);
        }
    }
    
    public void mergeGraph(ArrayList<Edge> newGraph) {
        for (int i = 0; i < newGraph.size(); i++) {
            if (TotalGraph.indexOf(newGraph.get(i)) < 0) {
                Edge edNew = newGraph.get(i);
                edNew.setWeight(0 + (edNew.getWeight() - 0) * 1 / (GraphsMerged + 1));
                TotalGraph.add(edNew);
            } else {
                float weight = TotalGraph.get(TotalGraph.indexOf(newGraph.get(i))).getWeight();
                TotalGraph.get(TotalGraph.indexOf(newGraph.get(i))).setWeight( TotalGraph.get(TotalGraph.indexOf(newGraph.get(i))).getWeight() + ((newGraph.get(i).getWeight() - weight) * 1 / (GraphsMerged + 1)));
            }
        }
        GraphsMerged++;
    }
    
    public void mergeSimple(ArrayList<Edge> newGraph){
        for (int i = 0; i < newGraph.size(); i++) {
            int index = TotalGraph.indexOf(newGraph.get(i));
            if (index < 0) {
                Edge edNew = newGraph.get(i);
                edNew.setWeight(edNew.getWeight());
                TotalGraph.add(edNew);
            } else {
                float weight = TotalGraph.get(index).getWeight();
                TotalGraph.get(index).setWeight( weight + newGraph.get(i).getWeight());
            }
        }
    }

    public void mergeGraphThreaded(ArrayList<Edge> newGraph) {
        for (int i = 0; i < newGraph.size(); i++) {
            if (TotalGraph.indexOf(newGraph.get(i)) < 0) {
                Edge edNew = newGraph.get(i);
                edNew.setWeight(edNew.getWeight());
                TotalGraph.add(edNew);
            } else {
                float weight = TotalGraph.get(TotalGraph.indexOf(newGraph.get(i))).getWeight();
                TotalGraph.get(TotalGraph.indexOf(newGraph.get(i))).setWeight( TotalGraph.get(TotalGraph.indexOf(newGraph.get(i))).getWeight() + newGraph.get(i).getWeight());
            }
        }
        GraphsMerged++;
    }

    private class ThreadedMergerSeparator extends Thread {

        int load;
        ArrayList<Edge> partial;
        String name;

        public ThreadedMergerSeparator(ArrayList<Edge> partial/*, String name*/) {
            this.load = partial.size();
            this.partial = partial;
            //this.name = name;
        }

        @Override
        public void run() {
            Edge edNew = null;
            for (int i = 0; i < load; i++) {
                if (TotalGraph.indexOf(partial.get(i)) < 0) {
                    edNew = partial.get(i);
                    edNew.setWeight(edNew.getWeight() / (GraphsMerged + 1));
                    TotalGraph.add(edNew);
                } else {
                    float weight = TotalGraph.get(TotalGraph.indexOf(partial.get(i))).getWeight();
                    TotalGraph.get(TotalGraph.indexOf(partial.get(i))).setWeight(((partial.get(i).getWeight() - weight) / (GraphsMerged + 1)));
                }
                //System.out.println(name+" - "+edNew+" - "+i);
            }
            /*for(int i=0;i<TotalGraph.size();i++){
                try{
                  float w=TotalGraph.get(i).weight;
                }catch(Exception ex){
                    System.err.println(ex.getLocalizedMessage()+" - "+this.getId()+" - "+i);
                }
            }*/
        }
    }

    public void printMergedGraphs() {
        for (int i = 0; i < TotalGraph.size(); i++) {
            System.out.println(TotalGraph.get(i));
        }
    }

    public void setThreshhold(float threshold) {
        for (int i = 0; i < TotalGraph.size(); i++) {
            if (TotalGraph.get(i).getWeight() < threshold) {
                TotalGraph.remove(i);
                i--;
            }
        }
    }
}
