/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.WordDistance;
import java.util.ArrayList;
import main_src.Main;

/**
 *
 * @author ViP
 */
public class WordDistanceMachine {

    public static int Levenshtein(String wordA, String wordB) {
        int result = 0;
        if (wordA.length() < 1) {
            return wordB.length();
        }
        if (wordB.length() < 1) {
            return wordA.length();
        }
        if ((wordA.length() == 1) && (wordB.length() == 1)) {
            if (wordA.charAt(wordA.length() - 1) == wordB.charAt(wordB.length() - 1)) {
                return 0;
            } else {
                return 1;
            }
        }
        //System.out.println(wordA+"\t-\t"+wordB);
        //System.out.println(wordA.charAt(wordA.length()-1)+"\t-\t"+wordB.charAt(wordB.length()-1));
        if (wordA.charAt(wordA.length() - 1) == wordB.charAt(wordB.length() - 1)) {
            result = 0;
        } else {
            result = 1;
        }

        if (wordA.length() > 1 && wordB.length() > 1) {
            result = minimum(WordDistanceMachine.Levenshtein(wordA.substring(0, wordA.length() - 1), wordB.substring(0, wordB.length() - 1)) + result,
                    WordDistanceMachine.Levenshtein(wordA.substring(0, wordA.length() - 1), wordB) + 1,
                    WordDistanceMachine.Levenshtein(wordA, wordB.substring(0, wordB.length() - 1)) + 1);
        } else if (wordA.length() < 1) {
            result = minimum(WordDistanceMachine.Levenshtein("", wordB.substring(0, wordB.length() - 1)) + result,
                    WordDistanceMachine.Levenshtein("", wordB) + 1,
                    WordDistanceMachine.Levenshtein(wordA, wordB.substring(0, wordB.length() - 1)) + 1);
        } else {
            result = minimum(WordDistanceMachine.Levenshtein(wordA.substring(0, wordA.length() - 1), "") + result,
                    WordDistanceMachine.Levenshtein(wordA.substring(0, wordA.length() - 1), wordB) + 1,
                    WordDistanceMachine.Levenshtein(wordA, "") + 1);
        }


        return result;
    }

    public static WordDistance LevenshteinfindClosest(String word, ArrayList<String> list) {
        if (list.contains(word)) {
            return new WordDistance(word, word, 0.0);
        } else if (word.length() > 0) {
            if (list.contains(word.substring(0, word.length() - 1))) {
                return new WordDistance(word, word, 0.0);
            }
        } else if (word.length() > 1) {
            if (list.contains(word.substring(0, word.length() - 2))) {
                 return new WordDistance(word, word, 0.0);
            }
        }/* else {
            int mini = Integer.MAX_VALUE;
            //int count=0;
            String minWord = "";
            System.out.println(word);
            //float oldPer=(float)0.0;
            for (int i = 0; i < list.size(); i++) {
                int dis = WordDistanceMachine.Levenshtein(word, list.get(i));
                if (dis == 0) {
                    mini = dis;
                    minWord = list.get(i);
                    break;
                } else if (dis < mini) {
                    mini = dis;
                    minWord = list.get(i);
                    System.out.println(minWord + " : " + mini);
                }
                /* float newPer=Math.round(((float)i/list.size())*10000)/(float)100;
                 if((oldPer != newPer) && (newPer%10==0)){
                 /* if(count==5){
                 System.out.print("|-|");
                 }else{
                 System.out.print("-");
                 }
                 System.out.print(newPer+" ");
                 oldPer=newPer;
                 count++;
                 }
            }
            WordDistance result = new WordDistance(word, minWord, mini);
            return result;
        }*/
        return null;
    }

    private static int minimum(int i, int i0, int i1) {
        int min = i;
        if (i0 < min) {
            min = i0;
        }
        if (i1 < min) {
            min = i1;
        }
        return min;
    }
    
    public static int customSimilarity(String sentenceA, String sentenceB){
        int lengthA=sentenceA.length();
        int lengthB=sentenceB.length();
        
        for(int i=0;i<lengthA;i++){
            char c=sentenceA.charAt(i);
            int index=sentenceB.indexOf(c);
            if(index>-1){
                
            }
        }
        
        return 0;
    }
    
    public static int sameCharCount(String sentenceA, String sentenceB){
        int lengthA=sentenceA.length();
        int lengthB=sentenceB.length();
        int count=0;
        
        for(int i=0;i<lengthA;i++){
            char c=sentenceA.charAt(i);
            //if(sentenceB.){
                
            //}
        }
        
        return 0;
    }
    
    public static boolean testSimilar(String sentenceA, String sentenceB){
        try{
        if(sentenceA.equals(sentenceB))
        return true;
        else if(sentenceA.length()>=((sentenceB.length()/3)) && sentenceB.contains(sentenceA.substring(sentenceA.length()/5, ((sentenceA.length()/5)*4)+1)))
        return true;
        else if(sentenceB.length()>=((sentenceA.length()/3)) && sentenceA.contains(sentenceB.substring(sentenceB.length()/5, ((sentenceB.length()/5)*4)+1)))
        return true;
        else{
            return false;
        }
        }catch(Exception ex){
            return false;
        }
    }
    
}
