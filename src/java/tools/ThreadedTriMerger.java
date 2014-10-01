/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;

/**
 *
 * @author ViP
 */
public class ThreadedTriMerger extends Thread{
    TriGraphMerger merged;
    ArrayList<TriGraphMerger> parts;
    ArrayList<NGramTriGraphCreator> ngrams;
    String mode;
    int numOfCores;
    ThreadedMerger parent;
    

    public ThreadedTriMerger(ArrayList<NGramTriGraphCreator> ngrams,String mode, int numOfCores, ThreadedMerger creator) {
        this.ngrams = ngrams;
        this.mode = mode;
        this.numOfCores = numOfCores;
        this.parent = creator;
        this.parts = new ArrayList<TriGraphMerger>();
    }
    
    public ThreadedTriMerger(ArrayList<NGramTriGraphCreator> ngrams,String mode, int numOfCores) {
        this.ngrams = ngrams;
        this.mode = mode;
        this.numOfCores = numOfCores;
        this.parent = null;
        this.parts = new ArrayList<TriGraphMerger>();
    }
    
    public ThreadedTriMerger(ArrayList<NGramTriGraphCreator> ngrams,String mode) {
        this.ngrams = ngrams;
        this.mode = mode;
        this.numOfCores = 1;
        this.parent = null;
        this.parts = new ArrayList<TriGraphMerger>();
    }
    
    @Override
    public void run() {
        merged=new TriGraphMerger();
	for (int i = 0; i < ngrams.size(); i++) {
            this.merged.mergeGraph(ngrams.get(i).Edges);
        }
        if(mode.equalsIgnoreCase("pos")) NGramTripleGraphTests.pos=this.merged;
        else if(mode.equalsIgnoreCase("neg")) NGramTripleGraphTests.neg=this.merged;
        else if(mode.equalsIgnoreCase("neu")) NGramTripleGraphTests.neu=this.merged;
        else if(mode.equalsIgnoreCase("appl")) NGramTripleGraphTests.appl=this.merged;
        
    }
    
    /*public void run() {
        merged=new GraphMerger();
	this.merged.mergeV3(ngrams,4,8);
        if(mode.equalsIgnoreCase("pos")) NGramTests.pos=this.merged;
        else if(mode.equalsIgnoreCase("neg")) NGramTests.neg=this.merged;
        else if(mode.equalsIgnoreCase("neu")) NGramTests.neu=this.merged;
        else if(mode.equalsIgnoreCase("appl")) NGramTests.appl=this.merged;
        
    }*/
    
}
