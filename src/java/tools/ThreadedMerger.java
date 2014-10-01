/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import main_src.NGramTests;

/**
 *
 * @author Silvestro
 */
public class ThreadedMerger extends Thread{
    GraphMerger merged;
    ArrayList<GraphMerger> parts;
    ArrayList<NGramCreator> ngrams;
    String mode;
    int numOfCores;
    ThreadedMerger parent;
    

    public ThreadedMerger(ArrayList<NGramCreator> ngrams,String mode, int numOfCores, ThreadedMerger creator) {
        this.ngrams = ngrams;
        this.mode = mode;
        this.numOfCores = numOfCores;
        this.parent = creator;
        this.parts = new ArrayList<GraphMerger>();
    }
    
    public ThreadedMerger(ArrayList<NGramCreator> ngrams,String mode, int numOfCores) {
        this.ngrams = ngrams;
        this.mode = mode;
        this.numOfCores = numOfCores;
        this.parent = null;
        this.parts = new ArrayList<GraphMerger>();
    }
    
    public ThreadedMerger(ArrayList<NGramCreator> ngrams,String mode) {
        this.ngrams = ngrams;
        this.mode = mode;
        this.numOfCores = 1;
        this.parent = null;
        this.parts = new ArrayList<GraphMerger>();
    }
    
    @Override
    /*public void run() {
        merged=new GraphMerger();
	for (int i = 0; i < ngrams.size(); i++) {
            this.merged.mergeGraph(ngrams.get(i).Edges);
        }
        if(mode.equalsIgnoreCase("pos")) NGramTests.pos=this.merged;
        else if(mode.equalsIgnoreCase("neg")) NGramTests.neg=this.merged;
        else if(mode.equalsIgnoreCase("neu")) NGramTests.neu=this.merged;
        else if(mode.equalsIgnoreCase("appl")) NGramTests.appl=this.merged;
        
    }*/
    
    public void run() {
        merged=new GraphMerger();
	this.merged.mergeV3(ngrams,4,8);
        if(mode.equalsIgnoreCase("pos")) NGramTests.pos=this.merged;
        else if(mode.equalsIgnoreCase("neg")) NGramTests.neg=this.merged;
        else if(mode.equalsIgnoreCase("neu")) NGramTests.neu=this.merged;
        else if(mode.equalsIgnoreCase("appl")) NGramTests.appl=this.merged;
        
    }
    
    /*@Override
    public void run() {
        merged=new GraphMerger();
        ngrams = Reader.readGraphs("C:\\Users\\ViP\\Documents\\tweetsPos.txt", 10000, 3);
        ArrayList<ArrayList<NGramCreator>> partialGramms = new ArrayList<ArrayList<NGramCreator>>();
        if(ngrams.size() > 0){
            partialGramms = new ArrayList<ArrayList<NGramCreator>>(ThreadedDivider.splitArrayList(ngrams, numOfCores));
            if(this.parent==null){
                Date start=new Date();
                ArrayList<ThreadedMerger> tms=new ArrayList<ThreadedMerger>();
                for (int i = 0; i < partialGramms.size(); i++) {
                    tms.add(new ThreadedMerger(partialGramms.get(i), mode, 1, this));
                    tms.get(i).start();
                }
                for (int i = 0; i < tms.size(); i++) {
                    if(tms.get(i).isAlive()){
                        try {
                            tms.get(i).join();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                Date end=new Date();
                Date diff=new Date(end.getTime()-start.getTime());
                System.out.println(diff.getMinutes()+":"+diff.getSeconds());
                merged=new GraphMerger(this.parts);
            }else{
                for (int i = 0; i < ngrams.size(); i++) {
                    merged.mergeGraphThreaded(ngrams.get(i).Edges);
                    if(i%10==0)
                    System.out.println(mode+" merger "+this.getId()+" status: "+((float)i/ngrams.size())*100+"%");
                    this.merged.mergeGraph(ngrams.get(i).Edges);
                    parent.parts.add(merged);
                }   
            }
        }
        if(this.parent==null){
            
            if(mode.equalsIgnoreCase("pos")) NGramTests.pos=this.merged;
            else if(mode.equalsIgnoreCase("neg")) NGramTests.neg=this.merged;
            else if(mode.equalsIgnoreCase("neu")) NGramTests.neu=this.merged;
            else if(mode.equalsIgnoreCase("appl")) NGramTests.appl=this.merged;
        }
    }*/
}
