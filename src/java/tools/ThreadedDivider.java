/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author ViP
 */
public class ThreadedDivider extends Thread{

    GraphMerger parent;
    
    
    public ThreadedDivider(GraphMerger caller) {
       this.parent=caller;
       
    }
    
    public static Collection splitArrayList(Collection sourceList, int numOfSplits){
        ArrayList<ArrayList> partialLists = new ArrayList<ArrayList>();
        int splitsSize = (int)Math.floor((double)sourceList.size()/numOfSplits);
        int spare=sourceList.size()-(int)Math.floor((double)splitsSize*numOfSplits);
        int spareAdded=0;
        int spareToAdd=0;
        for (int i = 0; i < numOfSplits; i++) {
                if(spare>0){
                    spareToAdd=1;
                }else{
                    spareToAdd=0;
                }
                if(((i+1)*splitsSize)+spareAdded+spareToAdd<=sourceList.size() && (i*splitsSize)+spareAdded<sourceList.size()){
                partialLists.add(new ArrayList(((ArrayList)sourceList).subList((i*splitsSize)+spareAdded, ((i+1)*splitsSize)+spareAdded+spareToAdd)));}
                else if((i*splitsSize)+spareAdded<sourceList.size()){
                partialLists.add(new ArrayList(((ArrayList)sourceList).subList((i*splitsSize)+spareAdded, sourceList.size())));}
                if(spareToAdd>0){
                    spareAdded++;
                    spare--;
                }
       }
        return partialLists;
    }
    
    public static ArrayList<Integer> splitIndexes(int total,int numOfSplits){
        ArrayList<Integer> sizes = new ArrayList<Integer>();
        int splitsSize = (int)Math.floor((double)total/numOfSplits);
        int spare=total-(int)Math.floor((double)splitsSize*numOfSplits);
        int spareAdded=0;
        int spareToAdd=0;
        for (int i = 0; i < numOfSplits; i++) {
                if(spare>0){
                    spareToAdd=1;
                }else{
                    spareToAdd=0;
                }
                if(((i+1)*splitsSize)+spareAdded+spareToAdd<=total && (i*splitsSize)+spareAdded<total){
                sizes.add((((i+1)*splitsSize)+spareAdded+spareToAdd)-((i*splitsSize)+spareAdded));
                }
                else if((i*splitsSize)+spareAdded<total){
                sizes.add(total-((i*splitsSize)+spareAdded));
                if(spareToAdd>0){
                    spareAdded++;
                    spare--;
                }
            }

         }
         return sizes;
    }
}
