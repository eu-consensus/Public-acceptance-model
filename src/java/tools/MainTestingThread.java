/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Crawlers.TwitterCrawler;
import Entities.CustomStatus;
import Entities.SimplePercentageCorrelation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import main_src.CombinationalNGrammGraphs;
import main_src.CombinationalNGramms;
import main_src.FastTest;
import main_src.Main;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;
import main_src.SimpleNGramTests;
import weka.core.Instance;

/**
 *
 * @author ViP
 */
public class MainTestingThread extends Thread{
    
    private String id;
    private long clientId;
    private String text;

    public MainTestingThread(String id, long clientId) {
        this.id=id;
        this.clientId=clientId;
        this.text=null;
        Main.margins=new int[2];
        Main.margins[0]=0;
        Main.margins[1]=0;
        Main.ngramms=4;
        Main.threshold=(float)0.001;
        GlobalVarsStore.completion.add(new SimplePercentageCorrelation((float)0.0,clientId));
    }

    public MainTestingThread(int i, float d) {
        this.id="0";
        this.clientId=0;
        this.text=null;
        Main.ngramms=i;
        Main.threshold=d;
        Main.margins=new int[2];
        Main.margins[0]=0;
        Main.margins[1]=0;
        GlobalVarsStore.completion.add(new SimplePercentageCorrelation((float)0.0,clientId));
    }
    
    public void writeObjects(){
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)1.0);
        GlobalVarsStore.ft=new FastTest();
         try{
                FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-svm.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(GlobalVarsStore.sng);
		oos.close();
                fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)12.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-bayes.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng2);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)18.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-c45.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng3);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)21.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-logistic.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng4);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)24.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-simplelogistic.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng5);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)28.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-mlp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng6);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)32.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-bftree.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng7);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)38.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-ft.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.sng8);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)42.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-svm.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)47.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-svm.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)49.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-bayes.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt2);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)52.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-c45.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt3);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)56.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-logistic.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt4);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)61.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-simplelogistic.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt5);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)63.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-mlp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt6);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)67.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-bftree.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt7);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)71.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-ft.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ngt8);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)74.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"graph-combo.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.comb);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)78.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"simple-combo.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.comNgramm);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)79.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-svm.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)81.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-bayes.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt2);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)83.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-c45.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt3);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)84.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-logistic.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt4);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)85.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-simplelogistic.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt5);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)89.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-mlp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt6);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)93.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-bftree.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt7);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)96.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileOutputStream fout = new FileOutputStream(GlobalVarsStore.objectDirectory+"triple-ft.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
	    oos.writeObject(GlobalVarsStore.ntgt8);
	    oos.close();
            fout.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)100.0);
         GlobalVarsStore.ft=new FastTest();
    }
    
    public void readObjects(){
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)1.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-svm.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)12.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-bayes.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng2= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)18.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-c45.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng3= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)21.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-logistic.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng4= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)24.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-simplelogistic.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng5= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)28.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-mlp.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng6= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)32.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-bftree.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng7= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)38.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-ft.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.sng8= (SimpleNGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)42.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-svm.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)47.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-svm.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)49.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-bayes.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt2= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)52.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-c45.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt3= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)56.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-logistic.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt4= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)61.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-simplelogistic.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt5= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)63.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-mlp.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt6= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)67.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-bftree.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt7= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)71.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-ft.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ngt8= (NGramTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)74.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"graph-combo.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.comb= (CombinationalNGrammGraphs) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)78.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"simple-combo.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.comNgramm= (CombinationalNGramms) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)79.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-svm.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)81.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-bayes.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt2= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)83.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-c45.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt3= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)84.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-logistic.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt4= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)85.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-simplelogistic.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt5= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)89.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-mlp.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt6= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)93.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-bftree.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt7= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)96.0);
        GlobalVarsStore.ft=new FastTest();
         try{
            FileInputStream fin = new FileInputStream(GlobalVarsStore.objectDirectory+"triple-ft.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
	    GlobalVarsStore.ntgt8= (NGramTripleGraphTests) ois.readObject();
	    ois.close();
            fin.close();
         }catch(Exception ex){ex.printStackTrace();}
         GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)100.0);
         GlobalVarsStore.ft=new FastTest();
    }
    
    public void prepareObjects(){
        GlobalVarsStore.sng=new SimpleNGramTests("svm");
        GlobalVarsStore.sng2=new SimpleNGramTests("bayes");
        GlobalVarsStore.sng3=new SimpleNGramTests("c4.5");
        GlobalVarsStore.sng4=new SimpleNGramTests("logistic");
        GlobalVarsStore.sng5=new SimpleNGramTests("simplelogistic");
        GlobalVarsStore.sng6=new SimpleNGramTests("mlp");
        GlobalVarsStore.sng7=new SimpleNGramTests("bftree");
        GlobalVarsStore.sng8=new SimpleNGramTests("ft");
        GlobalVarsStore.ngt=new NGramTests("svm", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt2=new NGramTests("bayes", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt3=new NGramTests("c4.5", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt4=new NGramTests("logistic", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt5=new NGramTests("simplelogistic", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt6=new NGramTests("mlp", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt7=new NGramTests("bftree", Main.ngramms, Main.threshold);
        GlobalVarsStore.ngt8=new NGramTests("ft", Main.ngramms, Main.threshold);
        GlobalVarsStore.comb=new CombinationalNGrammGraphs();
        GlobalVarsStore.comNgramm = new CombinationalNGramms();
        GlobalVarsStore.ntgt=new NGramTripleGraphTests("svm", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt2=new NGramTripleGraphTests("bayes", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt3=new NGramTripleGraphTests("c4.5", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt4=new NGramTripleGraphTests("logistic", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt5=new NGramTripleGraphTests("simplelogistic", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt6=new NGramTripleGraphTests("mlp", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt7=new NGramTripleGraphTests("bftree", Main.ngramms, Main.threshold);
        GlobalVarsStore.ntgt8=new NGramTripleGraphTests("ft", Main.ngramms, Main.threshold);
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)9.0);
        GlobalVarsStore.ft=new FastTest();
    }
    
    public void trainObjects(){
        try {
            SimpleNGramTests.prepare(GlobalVarsStore.trainDataDirectory+"train-data-mixed.xml", Main.margins, Main.ngramms);
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)12.5);
            GlobalVarsStore.sng.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)16.0);
            GlobalVarsStore.sng2.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)19.5);
            GlobalVarsStore.sng3.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)23.0);
            GlobalVarsStore.sng4.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)27.5);
            GlobalVarsStore.sng5.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)31.0);
            GlobalVarsStore.sng6.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)34.5);
            GlobalVarsStore.sng7.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)38.0);
            GlobalVarsStore.sng8.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)41.5);
            GlobalVarsStore.comNgramm.train(GlobalVarsStore.trainDataDirectory+"train-data-mixed.xml", Main.ngramms, Main.margins);
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)45.0);
            GlobalVarsStore.ngt.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)47.5);
            GlobalVarsStore.ngt2.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)51.0);
            GlobalVarsStore.ngt3.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)54.5);
            GlobalVarsStore.ngt4.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)58.0);
            GlobalVarsStore.ngt5.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)61.5);
            GlobalVarsStore.ngt6.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)65.0);
            GlobalVarsStore.ngt7.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)68.5);
            GlobalVarsStore.ngt8.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)72.0);
            GlobalVarsStore.ntgt.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)75.5);
            GlobalVarsStore.comb.train();
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)79.0);
            GlobalVarsStore.ntgt2.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)82.5);
            GlobalVarsStore.ntgt3.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)86.0);
            GlobalVarsStore.ntgt4.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)89.5);
            GlobalVarsStore.ntgt5.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)93.0);
            GlobalVarsStore.ntgt6.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)96.5);
            GlobalVarsStore.ntgt7.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)98.0);
            GlobalVarsStore.ntgt8.train("prepared");
            GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)100.0);
            this.writeObjects();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public double[] averageTest(Instance ins, Instance insSimple){
        ArrayList<double[]> results=new ArrayList<double[]>();
        double[] sres=GlobalVarsStore.sng.testSingle(insSimple);
        results.add(sres);
        double[] sres2=GlobalVarsStore.sng2.testSingle(insSimple);
        results.add(sres2);
        double[] sres3=GlobalVarsStore.sng3.testSingle(insSimple);
        results.add(sres3);
        double[] sres4=GlobalVarsStore.sng4.testSingle(insSimple);
        results.add(sres4);
        double[] sres5=GlobalVarsStore.sng5.testSingle(insSimple);
        results.add(sres5);
        double[] sres6=GlobalVarsStore.sng6.testSingle(insSimple);
        results.add(sres6);
        double[] sres7=GlobalVarsStore.sng7.testSingle(insSimple);
        results.add(sres7);
        double[] sres8=GlobalVarsStore.sng8.testSingle(insSimple);
        results.add(sres8);
        double[] scres=GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
        results.add(scres);
        double[] scres2=GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
        results.add(scres2);
        double[] scres3=GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
        results.add(scres3);
        double[] scres4=GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
        results.add(scres4);
        double[] scres5=GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
        results.add(scres5);
        double[] scres6=GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
        results.add(scres6);
        double[] scres7=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
        results.add(scres7);
        double[] scres8=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
        results.add(scres8);
        double[] scres9=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
        results.add(scres9);
        double[] res=GlobalVarsStore.ngt.testSingle(ins);
        results.add(res);
        double[] res2=GlobalVarsStore.ngt2.testSingle(ins);
        results.add(res2);
        double[] res3=GlobalVarsStore.ngt3.testSingle(ins);
        results.add(res3);
        double[] res4=GlobalVarsStore.ngt4.testSingle(ins);
        results.add(res4);
        double[] res5=GlobalVarsStore.ngt5.testSingle(ins);
        results.add(res5);
        double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
        results.add(res6);
        double[] res7=GlobalVarsStore.ngt7.testSingle(ins);
        results.add(res7);
        double[] res8=GlobalVarsStore.ngt8.testSingle(ins);              
        results.add(res8);
        double[] rest=GlobalVarsStore.ntgt.testSingle(ins);
        results.add(rest);
        double[] rest2=GlobalVarsStore.ntgt2.testSingle(ins);
        results.add(rest2);
        double[] rest3=GlobalVarsStore.ntgt3.testSingle(ins);
        results.add(rest3);
        double[] rest4=GlobalVarsStore.ntgt4.testSingle(ins);
        results.add(rest4);
        double[] rest5=GlobalVarsStore.ntgt5.testSingle(ins);
        results.add(rest5);
        double[] rest6=GlobalVarsStore.ntgt6.testSingle(ins);
        results.add(rest6);
        double[] rest7=GlobalVarsStore.ntgt7.testSingle(ins);
        results.add(rest7);
        double[] rest8=GlobalVarsStore.ntgt8.testSingle(ins);
        results.add(rest8);
        double[] cres=GlobalVarsStore.comb.testSingle(ins,"average");
        results.add(cres);
        double[] cres2=GlobalVarsStore.comb.testSingle(ins,"majority");
        results.add(cres2);
        double[] cres3=GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
        results.add(cres3);
        double[] cres4=GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
        results.add(cres4);
        double[] cres5=GlobalVarsStore.comb.testSingle(ins,"cosine","average");
        results.add(cres5);
        double[] cres6=GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
        results.add(cres6);
        double[] cres7=GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
        results.add(cres7);
        double[] cres8=GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
        results.add(cres8);
        double[] cres9=GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
        results.add(cres9);
        double[] avres=new double[3];
        avres[0]=0;
        avres[1]=0;
        avres[2]=0;
        for (int i = 0; i < results.size(); i++) {
           avres[0]+=results.get(i)[0];
           avres[1]+=results.get(i)[1];
           avres[2]+=results.get(i)[2];
        }
        avres[0]=avres[0]/results.size();
        avres[1]=avres[1]/results.size();
        avres[2]=avres[2]/results.size();
        return avres;
    }
    
    public String completeTest(String cleartext,String text,int total, int current){
        String result="";
        ArrayList<double[]> results=new ArrayList<double[]>();
        Instance ins=NGramTests.textToInstance(cleartext);
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage(((float)1.0)*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        Instance insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)2.0*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        double[] sres=GlobalVarsStore.sng.testSingle(insSimple);
        results.add(sres);
        double[] sres2=GlobalVarsStore.sng2.testSingle(insSimple);
        results.add(sres2);
        double[] sres3=GlobalVarsStore.sng3.testSingle(insSimple);
        results.add(sres3);
        double[] sres4=GlobalVarsStore.sng4.testSingle(insSimple);
        results.add(sres4);
        double[] sres5=GlobalVarsStore.sng5.testSingle(insSimple);
        results.add(sres5);
        double[] sres6=GlobalVarsStore.sng6.testSingle(insSimple);
        results.add(sres6);
        double[] sres7=GlobalVarsStore.sng7.testSingle(insSimple);
        results.add(sres7);
        double[] sres8=GlobalVarsStore.sng8.testSingle(insSimple);
        results.add(sres8);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams SVM</algor><positiveScore>"+sres[0]+"</positiveScore><neutralScore>"+sres[1]+"</neutralScore><negativeScore>"+sres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Bayes</algor><positiveScore>"+sres2[0]+"</positiveScore><neutralScore>"+sres2[1]+"</neutralScore><negativeScore>"+sres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams C4.5</algor><positiveScore>"+sres3[0]+"</positiveScore><neutralScore>"+sres3[1]+"</neutralScore><negativeScore>"+sres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Logistic</algor><positiveScore>"+sres4[0]+"</positiveScore><neutralScore>"+sres4[1]+"</neutralScore><negativeScore>"+sres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Simple Logistic</algor><positiveScore>"+sres5[0]+"</positiveScore><neutralScore>"+sres5[1]+"</neutralScore><negativeScore>"+sres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams MLP</algor><positiveScore>"+sres6[0]+"</positiveScore><neutralScore>"+sres6[1]+"</neutralScore><negativeScore>"+sres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams BFtree</algor><positiveScore>"+sres7[0]+"</positiveScore><neutralScore>"+sres7[1]+"</neutralScore><negativeScore>"+sres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Ftree</algor><positiveScore>"+sres8[0]+"</positiveScore><neutralScore>"+sres8[1]+"</neutralScore><negativeScore>"+sres8[2]+"</negativeScore></result>";
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)15.0*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        double[] scres=GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
        results.add(scres);
        double[] scres2=GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
        results.add(scres2);
        double[] scres3=GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
        results.add(scres3);
        double[] scres4=GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
        results.add(scres4);
        double[] scres5=GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
        results.add(scres5);
        double[] scres6=GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
        results.add(scres6);
        double[] scres7=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
        results.add(scres7);
        double[] scres8=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
        results.add(scres8);
        double[] scres9=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
        results.add(scres9);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Average</algor><positiveScore>"+scres[0]+"</positiveScore><neutralScore>"+scres[1]+"</neutralScore><negativeScore>"+scres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Majority</algor><positiveScore>"+scres2[0]+"</positiveScore><neutralScore>"+scres2[1]+"</neutralScore><negativeScore>"+scres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Euclidean</algor><positiveScore>"+scres3[0]+"</positiveScore><neutralScore>"+scres3[1]+"</neutralScore><negativeScore>"+scres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Manhattan</algor><positiveScore>"+scres4[0]+"</positiveScore><neutralScore>"+scres4[1]+"</neutralScore><negativeScore>"+scres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Cosine</algor><positiveScore>"+scres5[0]+"</positiveScore><neutralScore>"+scres5[1]+"</neutralScore><negativeScore>"+scres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Chebychev</algor><positiveScore>"+scres6[0]+"</positiveScore><neutralScore>"+scres6[1]+"</neutralScore><negativeScore>"+scres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_cos</algor><positiveScore>"+scres7[0]+"</positiveScore><neutralScore>"+scres7[1]+"</neutralScore><negativeScore>"+scres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_sin</algor><positiveScore>"+scres8[0]+"</positiveScore><neutralScore>"+scres8[1]+"</neutralScore><negativeScore>"+scres8[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_tan</algor><positiveScore>"+scres9[0]+"</positiveScore><neutralScore>"+scres9[1]+"</neutralScore><negativeScore>"+scres9[2]+"</negativeScore></result>";
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)35.0*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        double[] res=GlobalVarsStore.ngt.testSingle(ins);
        results.add(res);
        double[] res2=GlobalVarsStore.ngt2.testSingle(ins);
        results.add(res2);
        double[] res3=GlobalVarsStore.ngt3.testSingle(ins);
        results.add(res3);
        double[] res4=GlobalVarsStore.ngt4.testSingle(ins);
        results.add(res4);
        double[] res5=GlobalVarsStore.ngt5.testSingle(ins);
        results.add(res5);
        double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
        results.add(res6);
        double[] res7=GlobalVarsStore.ngt7.testSingle(ins);
        results.add(res7);
        double[] res8=GlobalVarsStore.ngt8.testSingle(ins);              
        results.add(res8);
        result+="<result><phrase>"+cleartext+"</phrase><algor>SVM</algor><positiveScore>"+res[0]+"</positiveScore><neutralScore>"+res[1]+"</neutralScore><negativeScore>"+res[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Bayes</algor><positiveScore>"+res2[0]+"</positiveScore><neutralScore>"+res2[1]+"</neutralScore><negativeScore>"+res2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>C4.5</algor><positiveScore>"+res3[0]+"</positiveScore><neutralScore>"+res3[1]+"</neutralScore><negativeScore>"+res3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Logistic</algor><positiveScore>"+res4[0]+"</positiveScore><neutralScore>"+res4[1]+"</neutralScore><negativeScore>"+res4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple Logistic</algor><positiveScore>"+res5[0]+"</positiveScore><neutralScore>"+res5[1]+"</neutralScore><negativeScore>"+res5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>MLP</algor><positiveScore>"+res6[0]+"</positiveScore><neutralScore>"+res6[1]+"</neutralScore><negativeScore>"+res6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>BFtree</algor><positiveScore>"+res7[0]+"</positiveScore><neutralScore>"+res7[1]+"</neutralScore><negativeScore>"+res7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Ftree</algor><positiveScore>"+res8[0]+"</positiveScore><neutralScore>"+res8[1]+"</neutralScore><negativeScore>"+res8[2]+"</negativeScore></result>";
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)50.0*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        double[] rest=GlobalVarsStore.ntgt.testSingle(ins);
        results.add(rest);
        double[] rest2=GlobalVarsStore.ntgt2.testSingle(ins);
        results.add(rest2);
        double[] rest3=GlobalVarsStore.ntgt3.testSingle(ins);
        results.add(rest3);
        double[] rest4=GlobalVarsStore.ntgt4.testSingle(ins);
        results.add(rest4);
        double[] rest5=GlobalVarsStore.ntgt5.testSingle(ins);
        results.add(rest5);
        double[] rest6=GlobalVarsStore.ntgt6.testSingle(ins);
        results.add(rest6);
        double[] rest7=GlobalVarsStore.ntgt7.testSingle(ins);
        results.add(rest7);
        double[] rest8=GlobalVarsStore.ntgt8.testSingle(ins);
        results.add(rest8);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph SVM</algor><positiveScore>"+rest[0]+"</positiveScore><neutralScore>"+rest[1]+"</neutralScore><negativeScore>"+rest[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Bayes</algor><positiveScore>"+rest2[0]+"</positiveScore><neutralScore>"+rest2[1]+"</neutralScore><negativeScore>"+rest2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph C4.5</algor><positiveScore>"+rest3[0]+"</positiveScore><neutralScore>"+rest3[1]+"</neutralScore><negativeScore>"+rest3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Logistic</algor><positiveScore>"+rest4[0]+"</positiveScore><neutralScore>"+rest4[1]+"</neutralScore><negativeScore>"+rest4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Simple Logistic</algor><positiveScore>"+rest5[0]+"</positiveScore><neutralScore>"+rest5[1]+"</neutralScore><negativeScore>"+rest5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph MLP</algor><positiveScore>"+rest6[0]+"</positiveScore><neutralScore>"+rest6[1]+"</neutralScore><negativeScore>"+rest6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph BFtree</algor><positiveScore>"+rest7[0]+"</positiveScore><neutralScore>"+rest7[1]+"</neutralScore><negativeScore>"+rest7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Ftree</algor><positiveScore>"+rest8[0]+"</positiveScore><neutralScore>"+rest8[1]+"</neutralScore><negativeScore>"+rest8[2]+"</negativeScore></result>";
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)85.0*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        double[] cres=GlobalVarsStore.comb.testSingle(ins,"average");
        results.add(cres);
        double[] cres2=GlobalVarsStore.comb.testSingle(ins,"majority");
        results.add(cres2);
        double[] cres3=GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
        results.add(cres3);
        double[] cres4=GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
        results.add(cres4);
        double[] cres5=GlobalVarsStore.comb.testSingle(ins,"cosine","average");
        results.add(cres5);
        double[] cres6=GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
        results.add(cres6);
        double[] cres7=GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
        results.add(cres7);
        double[] cres8=GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
        results.add(cres8);
        double[] cres9=GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
        results.add(cres9);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Average</algor><positiveScore>"+cres[0]+"</positiveScore><neutralScore>"+cres[1]+"</neutralScore><negativeScore>"+cres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Majority</algor><positiveScore>"+cres2[0]+"</positiveScore><neutralScore>"+cres2[1]+"</neutralScore><negativeScore>"+cres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Euclidean</algor><positiveScore>"+cres3[0]+"</positiveScore><neutralScore>"+cres3[1]+"</neutralScore><negativeScore>"+cres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Manhattan</algor><positiveScore>"+cres4[0]+"</positiveScore><neutralScore>"+cres4[1]+"</neutralScore><negativeScore>"+cres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Cosine</algor><positiveScore>"+cres5[0]+"</positiveScore><neutralScore>"+cres5[1]+"</neutralScore><negativeScore>"+cres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Chebychev</algor><positiveScore>"+cres6[0]+"</positiveScore><neutralScore>"+cres6[1]+"</neutralScore><negativeScore>"+cres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_cos</algor><positiveScore>"+cres7[0]+"</positiveScore><neutralScore>"+cres7[1]+"</neutralScore><negativeScore>"+cres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_sin</algor><positiveScore>"+cres8[0]+"</positiveScore><neutralScore>"+cres8[1]+"</neutralScore><negativeScore>"+cres8[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_tan</algor><positiveScore>"+cres9[0]+"</positiveScore><neutralScore>"+cres9[1]+"</neutralScore><negativeScore>"+cres9[2]+"</negativeScore></result>";
        //double[] ftres=FastTest.testWithPretrained(insSimple, ins);
        //result+="<result><phrase>"+cleartext+"</phrase><algor>Fast test</algor><positiveScore>"+ftres[0]+"</positiveScore><neutralScore>"+ftres[1]+"</neutralScore><negativeScore>"+ftres[2]+"</negativeScore></result>";
        double[] avres=new double[3];
        avres[0]=0;
        avres[1]=0;
        avres[2]=0;
        for (int i = 0; i < results.size() && i<17; i++) {
           avres[0]+=results.get(i)[0]*0.6;
           avres[1]+=results.get(i)[1]*0.6;
           avres[2]+=results.get(i)[2]*0.6;
        }
        for (int i = 17; i < results.size(); i++) {
           avres[0]+=results.get(i)[0];
           avres[1]+=results.get(i)[1];
           avres[2]+=results.get(i)[2];
        }
        result+="<result><phrase>"+cleartext+"</phrase><algor>The Sum of all results</algor><positiveScore>"+avres[0]+"</positiveScore><neutralScore>"+avres[1]+"</neutralScore><negativeScore>"+avres[2]+"</negativeScore></result>";
        avres[0]=avres[0]/results.size();
        avres[1]=avres[1]/results.size();
        avres[2]=avres[2]/results.size();
        result+="<result><phrase>"+cleartext+"</phrase><algor>The Average of all results</algor><positiveScore>"+avres[0]+"</positiveScore><neutralScore>"+avres[1]+"</neutralScore><negativeScore>"+avres[2]+"</negativeScore></result>";
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)100.0*((float)current/total));
        System.out.println(GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getPercentage());
        return result;
    }
    
    public String completeTest(String cleartext){
        String result="<results>";
        Instance ins=NGramTests.textToInstance(cleartext);
        Instance insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
        double[] sres=GlobalVarsStore.sng.testSingle(insSimple);
        double[] sres2=GlobalVarsStore.sng2.testSingle(insSimple);
        double[] sres3=GlobalVarsStore.sng3.testSingle(insSimple);
        double[] sres4=GlobalVarsStore.sng4.testSingle(insSimple);
        double[] sres5=GlobalVarsStore.sng5.testSingle(insSimple);
        double[] sres6=GlobalVarsStore.sng6.testSingle(insSimple);
        double[] sres7=GlobalVarsStore.sng7.testSingle(insSimple);
        double[] sres8=GlobalVarsStore.sng8.testSingle(insSimple);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams SVM</algor><positiveScore>"+sres[0]+"</positiveScore><neutralScore>"+sres[1]+"</neutralScore><negativeScore>"+sres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Bayes</algor><positiveScore>"+sres2[0]+"</positiveScore><neutralScore>"+sres2[1]+"</neutralScore><negativeScore>"+sres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams C4.5</algor><positiveScore>"+sres3[0]+"</positiveScore><neutralScore>"+sres3[1]+"</neutralScore><negativeScore>"+sres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Logistic</algor><positiveScore>"+sres4[0]+"</positiveScore><neutralScore>"+sres4[1]+"</neutralScore><negativeScore>"+sres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Simple Logistic</algor><positiveScore>"+sres5[0]+"</positiveScore><neutralScore>"+sres5[1]+"</neutralScore><negativeScore>"+sres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams MLP</algor><positiveScore>"+sres6[0]+"</positiveScore><neutralScore>"+sres6[1]+"</neutralScore><negativeScore>"+sres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams BFtree</algor><positiveScore>"+sres7[0]+"</positiveScore><neutralScore>"+sres7[1]+"</neutralScore><negativeScore>"+sres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Ftree</algor><positiveScore>"+sres8[0]+"</positiveScore><neutralScore>"+sres8[1]+"</neutralScore><negativeScore>"+sres8[2]+"</negativeScore></result>";
        double[] scres=GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
        double[] scres2=GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
        double[] scres3=GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
        double[] scres4=GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
        double[] scres5=GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
        double[] scres6=GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
        double[] scres7=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
        double[] scres8=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
        double[] scres9=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Average</algor><positiveScore>"+scres[0]+"</positiveScore><neutralScore>"+scres[1]+"</neutralScore><negativeScore>"+scres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Majority</algor><positiveScore>"+scres2[0]+"</positiveScore><neutralScore>"+scres2[1]+"</neutralScore><negativeScore>"+scres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Euclidean</algor><positiveScore>"+scres3[0]+"</positiveScore><neutralScore>"+scres3[1]+"</neutralScore><negativeScore>"+scres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Manhattan</algor><positiveScore>"+scres4[0]+"</positiveScore><neutralScore>"+scres4[1]+"</neutralScore><negativeScore>"+scres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Cosine</algor><positiveScore>"+scres5[0]+"</positiveScore><neutralScore>"+scres5[1]+"</neutralScore><negativeScore>"+scres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Chebychev</algor><positiveScore>"+scres6[0]+"</positiveScore><neutralScore>"+scres6[1]+"</neutralScore><negativeScore>"+scres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_cos</algor><positiveScore>"+scres7[0]+"</positiveScore><neutralScore>"+scres7[1]+"</neutralScore><negativeScore>"+scres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_sin</algor><positiveScore>"+scres8[0]+"</positiveScore><neutralScore>"+scres8[1]+"</neutralScore><negativeScore>"+scres8[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_tan</algor><positiveScore>"+scres9[0]+"</positiveScore><neutralScore>"+scres9[1]+"</neutralScore><negativeScore>"+scres9[2]+"</negativeScore></result>";
        double[] res=GlobalVarsStore.ngt.testSingle(ins);
        double[] res2=GlobalVarsStore.ngt2.testSingle(ins);
        double[] res3=GlobalVarsStore.ngt3.testSingle(ins);
        double[] res4=GlobalVarsStore.ngt4.testSingle(ins);
        double[] res5=GlobalVarsStore.ngt5.testSingle(ins);
        double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
        double[] res7=GlobalVarsStore.ngt7.testSingle(ins);
        double[] res8=GlobalVarsStore.ngt8.testSingle(ins);              
        result+="<result><phrase>"+cleartext+"</phrase><algor>SVM</algor><positiveScore>"+res[0]+"</positiveScore><neutralScore>"+res[1]+"</neutralScore><negativeScore>"+res[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Bayes</algor><positiveScore>"+res2[0]+"</positiveScore><neutralScore>"+res2[1]+"</neutralScore><negativeScore>"+res2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>C4.5</algor><positiveScore>"+res3[0]+"</positiveScore><neutralScore>"+res3[1]+"</neutralScore><negativeScore>"+res3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Logistic</algor><positiveScore>"+res4[0]+"</positiveScore><neutralScore>"+res4[1]+"</neutralScore><negativeScore>"+res4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple Logistic</algor><positiveScore>"+res5[0]+"</positiveScore><neutralScore>"+res5[1]+"</neutralScore><negativeScore>"+res5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>MLP</algor><positiveScore>"+res6[0]+"</positiveScore><neutralScore>"+res6[1]+"</neutralScore><negativeScore>"+res6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>BFtree</algor><positiveScore>"+res7[0]+"</positiveScore><neutralScore>"+res7[1]+"</neutralScore><negativeScore>"+res7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Ftree</algor><positiveScore>"+res8[0]+"</positiveScore><neutralScore>"+res8[1]+"</neutralScore><negativeScore>"+res8[2]+"</negativeScore></result>";
        double[] rest=GlobalVarsStore.ntgt.testSingle(ins);
        double[] rest2=GlobalVarsStore.ntgt2.testSingle(ins);
        double[] rest3=GlobalVarsStore.ntgt3.testSingle(ins);
        double[] rest4=GlobalVarsStore.ntgt4.testSingle(ins);
        double[] rest5=GlobalVarsStore.ntgt5.testSingle(ins);
        double[] rest6=GlobalVarsStore.ntgt6.testSingle(ins);
        double[] rest7=GlobalVarsStore.ntgt7.testSingle(ins);
        double[] rest8=GlobalVarsStore.ntgt8.testSingle(ins);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph SVM</algor><positiveScore>"+rest[0]+"</positiveScore><neutralScore>"+rest[1]+"</neutralScore><negativeScore>"+rest[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Bayes</algor><positiveScore>"+rest2[0]+"</positiveScore><neutralScore>"+rest2[1]+"</neutralScore><negativeScore>"+rest2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph C4.5</algor><positiveScore>"+rest3[0]+"</positiveScore><neutralScore>"+rest3[1]+"</neutralScore><negativeScore>"+rest3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Logistic</algor><positiveScore>"+rest4[0]+"</positiveScore><neutralScore>"+rest4[1]+"</neutralScore><negativeScore>"+rest4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Simple Logistic</algor><positiveScore>"+rest5[0]+"</positiveScore><neutralScore>"+rest5[1]+"</neutralScore><negativeScore>"+rest5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph MLP</algor><positiveScore>"+rest6[0]+"</positiveScore><neutralScore>"+rest6[1]+"</neutralScore><negativeScore>"+rest6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph BFtree</algor><positiveScore>"+rest7[0]+"</positiveScore><neutralScore>"+rest7[1]+"</neutralScore><negativeScore>"+rest7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Ftree</algor><positiveScore>"+rest8[0]+"</positiveScore><neutralScore>"+rest8[1]+"</neutralScore><negativeScore>"+rest8[2]+"</negativeScore></result>";
        double[] cres=GlobalVarsStore.comb.testSingle(ins,"average");
        double[] cres2=GlobalVarsStore.comb.testSingle(ins,"majority");
        double[] cres3=GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
        double[] cres4=GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
        double[] cres5=GlobalVarsStore.comb.testSingle(ins,"cosine","average");
        double[] cres6=GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
        double[] cres7=GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
        double[] cres8=GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
        double[] cres9=GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Average</algor><positiveScore>"+cres[0]+"</positiveScore><neutralScore>"+cres[1]+"</neutralScore><negativeScore>"+cres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Majority</algor><positiveScore>"+cres2[0]+"</positiveScore><neutralScore>"+cres2[1]+"</neutralScore><negativeScore>"+cres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Euclidean</algor><positiveScore>"+cres3[0]+"</positiveScore><neutralScore>"+cres3[1]+"</neutralScore><negativeScore>"+cres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Manhattan</algor><positiveScore>"+cres4[0]+"</positiveScore><neutralScore>"+cres4[1]+"</neutralScore><negativeScore>"+cres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Cosine</algor><positiveScore>"+cres5[0]+"</positiveScore><neutralScore>"+cres5[1]+"</neutralScore><negativeScore>"+cres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Chebychev</algor><positiveScore>"+cres6[0]+"</positiveScore><neutralScore>"+cres6[1]+"</neutralScore><negativeScore>"+cres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_cos</algor><positiveScore>"+cres7[0]+"</positiveScore><neutralScore>"+cres7[1]+"</neutralScore><negativeScore>"+cres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_sin</algor><positiveScore>"+cres8[0]+"</positiveScore><neutralScore>"+cres8[1]+"</neutralScore><negativeScore>"+cres8[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_tan</algor><positiveScore>"+cres9[0]+"</positiveScore><neutralScore>"+cres9[1]+"</neutralScore><negativeScore>"+cres9[2]+"</negativeScore></result>";
        double[] ftres=FastTest.testWithPretrained(insSimple, ins);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Fast test</algor><positiveScore>"+ftres[0]+"</positiveScore><neutralScore>"+ftres[1]+"</neutralScore><negativeScore>"+ftres[2]+"</negativeScore></result>";
        result+="</results>";
        return result;
    }
    
    public static String clear(String dirty){
        String clear="";
        if(dirty.indexOf("http://")>=0){
            if(dirty.indexOf(" ", dirty.indexOf("http://"))>=0){      
                clear=dirty.toLowerCase().replaceAll("http://[\\S|\\p{Punct}]*", "URL");
            }else{
                clear=dirty.toLowerCase().replaceAll("http://.*", "URL");
            }
        }else{
        clear=dirty.toLowerCase();
        }
        if(clear.indexOf("rt ")>=0){
            clear=clear.replace("rt ", "");
        }
        if(clear.indexOf("#")>=0){
            clear=clear.replaceAll("#","");
        }
        if(clear.indexOf("@")>=0){
            clear=clear.replaceAll("@\\w+", "REF");
            if(clear.indexOf("REF:")>=0){
                clear=clear.replaceAll("REF:", "");
            }
        }
        if(clear.indexOf("\n")>=0){
            clear=clear.replaceAll("\\n", " ");
        }
        if(clear.indexOf("& ")>=0){
            clear=clear.replaceAll("& ", "and ");
        }
        if(clear.indexOf("&")>=0){
            clear=clear.replaceAll("&", "");
        }
        return clear.trim();
    }
    
    @Override
    public void run() {
        String result="";
        if(id.toLowerCase().trim().equals("prepare")){
                result="<results>Prepared!</results>";
                Main.warmItUp();
                //this.readObjects();
                this.prepareObjects();
                this.trainObjects();
                GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setResult(result);
        }else{
                if(GlobalVarsStore.ngt==null || GlobalVarsStore.ngt2==null || GlobalVarsStore.ngt3==null || GlobalVarsStore.ngt4==null || GlobalVarsStore.ngt5==null || GlobalVarsStore.ngt6==null || GlobalVarsStore.ngt7==null || GlobalVarsStore.ngt8==null){
                    result="<results>";
                    result+="Models not prepared!"; 
                    result+="</results>";
                }
                TwitterCrawler tc=new TwitterCrawler();
                ArrayList<CustomStatus> statuses=new ArrayList<CustomStatus>();
                String[] ids=id.split("-");
                for (int i = 0; i < ids.length; i++) {
                    CustomStatus cs=tc.getById(ids[i]);
                    if(cs!=null) statuses.add(cs);
                }
                result="<results>";
                if(statuses.size()>0){
                System.out.println("Starting Tests...");
                    for (int i = 0; i < statuses.size(); i++) {
                        result+=completeTest(statuses.get(i).getClearText(),statuses.get(i).getText(),statuses.size(),i+1);
                    }
                //result+=completeTest("This game is wonderful, gratz to all guys :D");
                System.out.println("Tests Completed.");
                }else{
                   GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setPercentage((float)100.0); 
                   result+="Tweet not found."; 
                }
                result+="</results>";
                try{
                    PrintWriter fw = new PrintWriter("./results/"+id+".res");
                    fw.print(result);
                    fw.close();
                }catch(Exception ex){ex.printStackTrace();}
                GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setResult(result);
        }
    }

    public double[] simpleTest(String cleartext, String algor) {
            Instance insSimple=new Instance(0);
            Instance ins=new Instance(0);
            if(algor.toLowerCase().trim().equals("averagetest")){
                insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
                ins=NGramTests.textToInstance(cleartext);
            }else if(algor.toLowerCase().contains("simple") || algor.toLowerCase().equals("fasttest")){
                insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
            }else{
                ins=NGramTests.textToInstance(cleartext);
            }
            if(algor.toLowerCase().trim().equals("simplesvm")){
                return GlobalVarsStore.sng.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simplebayes")){
                return GlobalVarsStore.sng2.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simplec45")){
                return GlobalVarsStore.sng3.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simplelogistic")){
                return GlobalVarsStore.sng4.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simplesimplelogistic")){
                return GlobalVarsStore.sng5.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simplemlp")){
                return GlobalVarsStore.sng6.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simplebftree")){
                return GlobalVarsStore.sng7.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("simpleft")){
                return GlobalVarsStore.sng8.testSingle(insSimple);
            }else if(algor.toLowerCase().trim().equals("graphsvm")){
                return GlobalVarsStore.ngt.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphbayes")){
                return GlobalVarsStore.ngt2.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphc45")){
                return GlobalVarsStore.ngt3.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphlogistic")){
                return GlobalVarsStore.ngt4.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphsimplelogistic")){
                return GlobalVarsStore.ngt5.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphmlp")){
                return GlobalVarsStore.ngt6.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphbftree")){
                return GlobalVarsStore.ngt7.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("graphft")){
                return GlobalVarsStore.ngt8.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphsvm")){
                return GlobalVarsStore.ntgt.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphbayes")){
                return GlobalVarsStore.ntgt2.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphc45")){
                return GlobalVarsStore.ntgt3.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphlogistic")){
                return GlobalVarsStore.ntgt4.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphsimplelogistic")){
                return GlobalVarsStore.ntgt5.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphmlp")){
                return GlobalVarsStore.ntgt6.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphbftree")){
                return GlobalVarsStore.ntgt7.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("triplegraphft")){
                return GlobalVarsStore.ntgt8.testSingle(ins);
            }else if(algor.toLowerCase().trim().equals("simpleeuclidean")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
            }else if(algor.toLowerCase().trim().equals("simplemanhattan")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
            }else if(algor.toLowerCase().trim().equals("simplecosine")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
            }else if(algor.toLowerCase().trim().equals("simplechebychev")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
            }else if(algor.toLowerCase().trim().equals("simpleorthocos")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
            }else if(algor.toLowerCase().trim().equals("simpleorthosin")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
            }else if(algor.toLowerCase().trim().equals("simpleorthotan")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
            }else if(algor.toLowerCase().trim().equals("simpleaverage")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
            }else if(algor.toLowerCase().trim().equals("simplemajority")){
                return GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
            }else if(algor.toLowerCase().trim().equals("grapheuclidean")){
                return GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
           }else if(algor.toLowerCase().trim().equals("graphmanhattan")){
                return GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
            }else if(algor.toLowerCase().trim().equals("graphcosine")){
                return GlobalVarsStore.comb.testSingle(ins,"cosine","average");
            }else if(algor.toLowerCase().trim().equals("graphchebychev")){
                return GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
            }else if(algor.toLowerCase().trim().equals("graphorthocos")){
                return GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
            }else if(algor.toLowerCase().trim().equals("graphorthosin")){
                return GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
            }else if(algor.toLowerCase().trim().equals("graphorthotan")){
                return GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
            }else if(algor.toLowerCase().trim().equals("graphaverage")){
                return GlobalVarsStore.comb.testSingle(ins,"average");
            }else if(algor.toLowerCase().trim().equals("graphmajority")){
                return GlobalVarsStore.comb.testSingle(ins,"majority");
            }else if(algor.toLowerCase().trim().equals("fasttest")){
                return FastTest.testWithPretrained(cleartext);
            }else if(algor.toLowerCase().trim().equals("averagetest")){
                return averageTest(ins,insSimple);
            }else{
                return GlobalVarsStore.ngt6.testSingle(ins);
            }
    }
}
