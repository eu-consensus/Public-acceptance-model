/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_src;

import Crawlers.FacebookCrawler;
import Crawlers.TwitterCrawler;
import Entities.CustomFBStatus;
import Entities.CustomStatus;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.GlobalVarsStore;
import tools.MainTestingThread;
import tools.Parser;
import tools.PercentagePrinter;
import tools.RnFEvaluator;
import tools.SentiWordNet;
import tools.TweetTransformer;
import tools.WordDistanceMachine;
import tools.Writter;
import tools.XMLBuilder;
import twitter4j.Status;
/**
 *
 * @author ViP
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static double percentage;
    private static String term;
    private static TwitterCrawler crow;
    private static FacebookCrawler fb;
    public static Parser purse;
    private static List<CustomStatus> qrTweets;
    private static List<CustomFBStatus> qrStatuses;
    private static  ArrayList<String> keywords;
    public static String result;
    public static int[] margins;
    public static float threshold;
    public static int ngramms;
    
    public static void warmItUp(){
        GlobalVarsStore.fast=true;
        GlobalVarsStore.trainDataDirectory="/trainData/";
        result="";
        /*keywords=new ArrayList<String>();
        keywords.add("biofuels");
        keywords.add("biofuel");
        keywords.add("ILUC");
        keywords.add("NoFoodForFuel");
        keywords.add("FoodNotFuel");
        initialise("merged");*/
        purse = new Parser(); 
        percentage = 0;
        Main.term = "";
        //parse();
    }
    
    private static void initialise(String term){
        purse = new Parser(); 
        Main.term = term;
        percentage = 0;
    }
    
    private static void crawl(){
        crow = new TwitterCrawler();
        qrTweets = crow.search(term);
        System.out.println(qrTweets.size());
        XMLBuilder.buildToFile(qrTweets, term);
    }
    
    private static void fbCrawlAll(){
        fb = new FacebookCrawler();
        int count=0;
        for(int i=0;i<keywords.size();i++){
            qrStatuses = fb.search(keywords.get(i));
            count+=qrStatuses.size();
            XMLBuilder.buildFBToFile(qrStatuses, keywords.get(i));
        }
        System.out.println("Crawled "+count+" statuses.");
    }
    
    private static void crawlAll(){
        crow = new TwitterCrawler();
        int count=0;
        for(int i=0;i<keywords.size();i++){
            qrTweets = crow.search(keywords.get(i));
            count+=qrTweets.size();
            XMLBuilder.buildToFile(qrTweets, keywords.get(i));
        }
        System.out.println("Crawled "+count+" tweets.");
    }
    
    private static void merge(){
        ArrayList<CustomStatus> tempcs=new ArrayList<CustomStatus>();
        for(int i=0;i<keywords.size();i++){
            Parser tempPurse=new Parser();
            tempPurse.parseByTerm(keywords.get(i));
            tempcs.addAll(tempPurse.getTweets());
        }
        Parser tempPurse=new Parser();
        tempPurse.parseByTerm("merged");
        tempcs.addAll(tempPurse.getTweets());
        XMLBuilder.clearAndBuildToFile(tempcs, "merged");
        System.out.println("Done.");
    }
    
    private static void fbmerge(){
        ArrayList<CustomFBStatus> tempcs=new ArrayList<CustomFBStatus>();
        for(int i=0;i<keywords.size();i++){
            Parser tempPurse=new Parser();
            tempPurse.parseFBByTerm(keywords.get(i));
            tempcs.addAll(tempPurse.getStatuses());
        }
        Parser tempPurse=new Parser();
        tempPurse.parseFBByTerm("merged");
        tempcs.addAll(tempPurse.getStatuses());
        XMLBuilder.clearAndBuildFBToFile(tempcs, "merged");
        System.out.println("Done.");
    }
    
    private static void clear(String condition){
        System.out.println("Cleared "+purse.clearTweets(term)+" tweets and "+purse.countTweets()+" tweets remaining. "+printTime(new java.util.Date()));
    }
    
    private static void parse(){
        System.out.println("Parsing tweets... "+printTime(new java.util.Date()));
        purse.parseByTerm(term);
        //purse.parse("train-data-hcr-csv.xml");
        System.out.println("Parsed "+purse.countTweets()+" tweets. "+printTime(new java.util.Date())); 
    }
    
    public static void prepare(String trainDataDir, String objectDir,String mode){
        if(trainDataDir!=null && trainDataDir.length()>0)
        GlobalVarsStore.trainDataDirectory=trainDataDir;
        else
        GlobalVarsStore.trainDataDirectory="trainData/";
        if(objectDir!=null && objectDir.length()>0)
        GlobalVarsStore.objectDirectory=objectDir;
        else
        GlobalVarsStore.objectDirectory="objectData/";
        ngramms=4;
        threshold=(float)0.001;
        Main.warmItUp();
        MainTestingThread mtt=new MainTestingThread(4,(float)0.001);
        if(mode.trim().toLowerCase().equals("read")){
            mtt.readObjects();
        }else{
            mtt.prepareObjects();
            mtt.trainObjects();
        }
    }
    
    public static void prepare(String trainDataDir,int n, float threshold){
        if(trainDataDir!=null && trainDataDir.length()>0)
        GlobalVarsStore.trainDataDirectory=trainDataDir;
        else
        GlobalVarsStore.trainDataDirectory="trainData/";
        if(n>0)
        ngramms=n;
        else
        ngramms=4;
        if(threshold>0)
        Main.threshold=threshold;
        else
        Main.threshold=(float)0.001;
        Main.warmItUp();
        MainTestingThread mtt=new MainTestingThread(4,(float)0.001);
        mtt.prepareObjects();
        mtt.trainObjects();
    }
    
    public static void produceTrainVectors(String trainDataDir, int n, float threshold){
        Main.warmItUp();
        if(trainDataDir!=null && trainDataDir.length()>0)
        GlobalVarsStore.trainDataDirectory=trainDataDir;
        else
        GlobalVarsStore.trainDataDirectory="trainData/";
        if(n>0)
        ngramms=n;
        else
        ngramms=4;
        if(threshold>0)
        Main.threshold=threshold;
        else
        Main.threshold=(float)0.001;
        NGramTests ngt=new NGramTests("svm", ngramms, threshold);
        ngt.produceTrainVectors(" ");
        NGramTripleGraphTests ntgt=new NGramTripleGraphTests("svm", ngramms, threshold);
        ntgt.produceTrainVectors("");
    }
    
    public static String testAll(String text){
        MainTestingThread mtt=new MainTestingThread(ngramms,threshold);
        return mtt.completeTest(MainTestingThread.clear(text));
    }
    
    /*public static double[] testSingle(String text, String algor){
        MainTestingThread mtt=new MainTestingThread(ngramms,threshold);
        return mtt.simpleTest(MainTestingThread.clear(text),algor);
    }*/
    
    public static String testSingle(String text, String algor){
        MainTestingThread mtt=new MainTestingThread(ngramms,threshold);
        double[] res=mtt.simpleTest(MainTestingThread.clear(text),algor);
        if(res[0] >= res[1] && res[0] >= res[2]) return "positive";
        else if(res[1] >= res[0] && res[1] >= res[2]) return "neutral";
        else if(res[2] >= res[1] && res[2] >= res[0]) return "negative";
        else return null;
    }
    
    public static String testSingle(String text){
        MainTestingThread mtt=new MainTestingThread(ngramms,threshold);
        double[] res=mtt.simpleTest(MainTestingThread.clear(text)," ");
        if(res[0] >= res[1] && res[0] >= res[2]) return "positive";
        else if(res[1] >= res[0] && res[1] >= res[2]) return "neutral";
        else if(res[2] >= res[1] && res[2] >= res[0]) return "negative";
        else return null;
    }
    
    public static String printOptions(){
        String result="<commandlist>";
        result += "<command><method_literal>simplesvm</method_literal><algorithm>Support Vector Machine</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplebayes</method_literal><algorithm>Bayesian Networks</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplec45</method_literal><algorithm>C4.5</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplelogistic</method_literal><algorithm>Logistic Regression</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplesimplelogistic</method_literal><algorithm>Simple Logistic Regression</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplemlp</method_literal><algorithm>Multilayer Perceptrons</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplebftree</method_literal><algorithm>Best-First Trees</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simpleft</method_literal><algorithm>Functional Trees</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>graphsvm</method_literal><algorithm>Support Vector Machine</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphbayes</method_literal><algorithm>Bayesian Networks</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphc45</method_literal><algorithm>C4.5</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphlogistic</method_literal><algorithm>Logistic Regression</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphsimplelogistic</method_literal><algorithm>Simple Logistic Regression</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphmlp</method_literal><algorithm>Multilayer Perceptrons</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphbftree</method_literal><algorithm>Best-First Trees</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphft</method_literal><algorithm>Functional Trees</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphsvm</method_literal><algorithm>Support Vector Machine</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphbayes</method_literal><algorithm>Bayesian Networks</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphc45</method_literal><algorithm>C4.5</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphlogistic</method_literal><algorithm>Logistic Regression</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphsimplelogistic</method_literal><algorithm>Simple Logistic Regression</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphmlp</method_literal><algorithm>Multilayer Perceptrons</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphbftree</method_literal><algorithm>Best-First Trees</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>triplegraphft</method_literal><algorithm>Functional Trees</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>simpleaverage</method_literal><algorithm>Combinational with Average Rule</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplemajority</method_literal><algorithm>Combinational with Majority Rule</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simpleeuclidean</method_literal><algorithm>Combinational with Euclidean Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplemanhattan</method_literal><algorithm>Combinational with Manhattan Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplecosine</method_literal><algorithm>Combinational with Cosine Dissimilarity</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simplechebychev</method_literal><algorithm>Combinational with Chebychev Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simpleorthocos</method_literal><algorithm>Combinational with Cos-based Orthodromic Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simpleorthosin</method_literal><algorithm>Combinational with Sin-based Orthodromic Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>simpleorthotan</method_literal><algorithm>Combinational with Tan-based Orthodromic Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
        result += "<command><method_literal>grapheaverage</method_literal><algorithm>Combinational with Average Rule</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphemajority</method_literal><algorithm>Combinational with Majority Rule</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>grapheuclidean</method_literal><algorithm>Combinational with Euclidean Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphmanhattan</method_literal><algorithm>Combinational with Manhattan Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphcosine</method_literal><algorithm>Combinational with Cosine Dissimilarity</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphchebychev</method_literal><algorithm>Combinational with Chebychev Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphorthocos</method_literal><algorithm>Combinational with Cos-based Orthodromic Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphorthosin</method_literal><algorithm>Combinational with Sin-based Orthodromic Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>graphorthotan</method_literal><algorithm>Combinational with Tan-based Orthodromic Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>fasttest</method_literal><algorithm>A hybrid combination between N-Grams and Graphs</algorithm><nlp_method>N-Grams and N-Gram Graphs</nlp_method></command>";
        result += "<command><method_literal>averagetest</method_literal><algorithm>An Averaged Sum of all N-Grams and Graphs</algorithm><nlp_method>N-Grams and N-Gram Graphs</nlp_method></command>";
        result += "</commandlist>";
        System.out.println(result);
        return result;
    }

    public static String printTime(Date date) {
        return date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    }
}
