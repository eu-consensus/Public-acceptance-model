/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Polarity;
import Entities.WordDistance;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import main_src.Main;

/**
 *
 * @author ViP
 */
public class SentiWordNet {
    ArrayList<Polarity> pol;

    public SentiWordNet() {
        this.pol = new ArrayList<Polarity>();
    }
    
    public void populate(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;
            while ((strLine = br.readLine()) != null) {
                if((!strLine.startsWith("#"))&&(!strLine.startsWith("\t"))){
                    String[] temp = strLine.split("\t");
                    String[] tempSplitFirst = temp[4].split(" ");
                    for(int j=0;j<tempSplitFirst.length;j++){
                        String[] tempSplitSecond=tempSplitFirst[j].split("#");
                        Polarity curr=new Polarity(tempSplitSecond[0],Double.parseDouble(temp[2]),Double.parseDouble(temp[3]));
                    if(!this.pol.contains(curr)){this.pol.add(curr);}}
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String> splitWords(String sentence){
        ArrayList<String> splitted=new ArrayList<String>();
        String[] words=sentence.split(" ");
        splitted.addAll(Arrays.asList(words));
        return splitted;
    }
    
    public void print(){
        for(int i=0;i<this.pol.size();i++){
            System.out.println(this.pol.get(i).toString());
        }
    }
    
    public Double testWord(String word){
        ArrayList<String> words=new ArrayList<String>();
        for(int i=0;i<pol.size();i++){
            words.add(pol.get(i).getWord().toLowerCase().trim());
        }
        WordDistance wd=WordDistanceMachine.LevenshteinfindClosest(word.toLowerCase().trim(), words);
        if(wd != null){
            Polarity pola=getWordPolarity(wd.getWordB());
            if(pola!=null){
            return pola.getPosValue()-pola.getNegValue();
            }
            else{
                return null;
            }
        }else{
        return null;} 
    }
    
    public int getWordCount(){
        return pol.size();
    }
    
    public Polarity getWordPolarity(String word){
        for(int i=0;i<pol.size();i++){
            if(pol.get(i).getWord().toLowerCase().trim().equals(word.trim().toLowerCase())){
                return pol.get(i);
            }
        }
        return null;
    }
}
