/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Edge;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;

/**
 *
 * @author Silvestro
 */
public class ThreadedVectorCalculator extends Thread{
    
    ArrayList<NGramCreator> ngrams;
    ArrayList<NGramTriGraphCreator> tringrams;
    ArrayList<ArrayList<Float>> vectors;
    boolean flag;
    String mode;
    
    public ThreadedVectorCalculator(ArrayList<NGramCreator> ngrams,String mode) {
        this.ngrams = ngrams;
        this.mode=mode;
        flag=true;
    }
    
    public ThreadedVectorCalculator(ArrayList<NGramTriGraphCreator> tringrams,String mode, boolean flag) {
        this.tringrams = tringrams;
        this.mode=mode;
        flag=false;
    }
    
    @Override
    public void run() {
        if(flag){
            vectors = new ArrayList<ArrayList<Float>>();
            for (int i = 0; i < ngrams.size(); i++) {
                if(i%10==0)
                System.out.println(mode+" vector status: "+((float)i/ngrams.size())*100+"%");
                if(GlobalVarsStore.fast)vectors.add(SimCalculator.calcVectorFast(ngrams.get(i).Edges));
                else vectors.add(SimCalculator.calcVector(ngrams.get(i).Edges));
            }

            if(this.mode.equalsIgnoreCase("testPos")) NGramTests.testVectorsPos=this.vectors;
            else if(this.mode.equalsIgnoreCase("testNeg")) NGramTests.testVectorsNeg=this.vectors;
            else if(this.mode.equalsIgnoreCase("testNeu")) NGramTests.testVectorsNeu=this.vectors;
            else if(this.mode.equalsIgnoreCase("trainNeg")) NGramTests.trainVectorsNeg=this.vectors;
            else if(this.mode.equalsIgnoreCase("trainNeu")) NGramTests.trainVectorsNeu=this.vectors;
            else if(this.mode.equalsIgnoreCase("trainPos")) NGramTests.trainVectorsPos=this.vectors;
            else if(this.mode.equalsIgnoreCase("ap")) NGramTests.appliedVectors=this.vectors;
        }else{
            vectors = new ArrayList<ArrayList<Float>>();
            for (int i = 0; i < tringrams.size(); i++) {
                if(i%10==0)
                System.out.println(mode+" vector status: "+((float)i/tringrams.size())*100+"%");
                if(GlobalVarsStore.fast)vectors.add(SimCalculator.calcTriVectorFast(tringrams.get(i).Edges));
                else vectors.add(SimCalculator.calcTriVector(tringrams.get(i).Edges));
            }

            if(this.mode.equalsIgnoreCase("testPos")) NGramTripleGraphTests.testVectorsPos=this.vectors;
            else if(this.mode.equalsIgnoreCase("testNeg")) NGramTripleGraphTests.testVectorsNeg=this.vectors;
            else if(this.mode.equalsIgnoreCase("testNeu")) NGramTripleGraphTests.testVectorsNeu=this.vectors;
            else if(this.mode.equalsIgnoreCase("trainNeg")) NGramTripleGraphTests.trainVectorsNeg=this.vectors;
            else if(this.mode.equalsIgnoreCase("trainNeu")) NGramTripleGraphTests.trainVectorsNeu=this.vectors;
            else if(this.mode.equalsIgnoreCase("trainPos")) NGramTripleGraphTests.trainVectorsPos=this.vectors;
            else if(this.mode.equalsIgnoreCase("ap")) NGramTripleGraphTests.appliedVectors=this.vectors;
        }
    }
    
    /*private class divider extends Thread{
         ArrayList<Edge> edges;

        public divider(ArrayList<Edge> edges) {
            this.edges = edges;
        }

        @Override
        public void run() {
            vectors.add(SimCalculator.calcVector(edges));
        }
         
         
    }*/
}
