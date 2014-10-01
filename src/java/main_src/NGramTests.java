/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_src;

import Entities.CustomStatus;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.GlobalVarsStore;
import tools.GraphMerger;
import tools.NGramCreator;
import tools.Parser;
import tools.Reader;
import tools.SimCalculator;
import tools.ThreadedMerger;
import tools.ThreadedVectorCalculator;
import tools.XMLBuilder;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author ViP
 */
public class NGramTests implements Serializable{
    public Classifier theModel;
    public String algor;
    public int nGrammNumber;
    public float threshold;
    public static GraphMerger pos;
    public static GraphMerger neg;
    public static GraphMerger neu;
    public static GraphMerger appl;
    public static ArrayList<GraphMerger> partials;
    public static ArrayList<NGramCreator> ngramsTest;
    public static ArrayList<NGramCreator> ngramsTrain;
    public static ArrayList<NGramCreator> ngramsPos;
    public static ArrayList<NGramCreator> ngramsNeg;
    public static ArrayList<NGramCreator> ngramsNeu;
    public static ArrayList<ArrayList<Float>> trainVectorsPos;
    public static ArrayList<ArrayList<Float>> trainVectorsNeu;
    public static ArrayList<ArrayList<Float>> trainVectorsNeg;
    public static ArrayList<ArrayList<Float>> testVectorsPos;
    public static ArrayList<ArrayList<Float>> testVectorsNeg;
    public static ArrayList<ArrayList<Float>> testVectorsNeu;
    public static ArrayList<ArrayList<Float>> appliedVectors;
    public static ArrayList<ArrayList<String>> trainVectorsPosDisc;
    public static ArrayList<ArrayList<String>> trainVectorsNeuDisc;
    public static ArrayList<ArrayList<String>> trainVectorsNegDisc;
    public static ArrayList<ArrayList<String>> testVectorsPosDisc;
    public static ArrayList<ArrayList<String>> testVectorsNeuDisc;
    public static ArrayList<ArrayList<String>> testVectorsNegDisc;
    public static ArrayList<ArrayList<String>> testVectorsDiscPos;
    public static ArrayList<ArrayList<String>> testVectorsDiscNeg;
    public static Instances TrainingSet;


    public NGramTests(String algor, int nGrammNumber, float threshold) {
        this.algor = algor;
        this.nGrammNumber = nGrammNumber;
        this.threshold = threshold;
    }
    
    public void trainAndTest(String term,String mode){
        System.out.println("Preparing...");
        Parser purse = new Parser();
        purse.parseByTerm(term);
        //purse.parse("tweets-train-data-stanford-csv.xml");
        pos = new GraphMerger();
        neg = new GraphMerger();
        neu = new GraphMerger();

        this.partials = new ArrayList<GraphMerger>();
        algor=algor.toLowerCase().trim();
        if(algor.toLowerCase().trim().equals("bayes")||algor.toLowerCase().trim().equals("c4.5")||algor.toLowerCase().trim().equals("svm")||algor.toLowerCase().trim().equals("logistic")||algor.toLowerCase().trim().equals("simplelogistic")||algor.toLowerCase().trim().equals("test")||algor.toLowerCase().trim().equals("mlp")||algor.toLowerCase().trim().equals("bftree")||algor.toLowerCase().trim().equals("ft"))
            testTrained(purse,this.train(mode));
        else{
            System.out.println("No method specified. Ending analysis...");
        }
    }
    
    private void readBalancedGraphs(){
        Parser purr=new Parser();
        Parser posPur=null;
        Parser neuPur=null;
        Parser negPur=null;
        ArrayList<CustomStatus> total;
        
        System.out.println("Reading Pos Graphs...");
        purr.parse(GlobalVarsStore.trainDataDirectory+"tweets-train-positive-splitted.xml");
        total=purr.getTweets();
        System.out.println("Reading Neu Graphs...");
        purr.parse(GlobalVarsStore.trainDataDirectory+"tweets-train-neutral-splitted.xml");
        total.addAll(purr.getTweets());
        System.out.println("Reading Neg Graphs...");
        purr.parse(GlobalVarsStore.trainDataDirectory+"tweets-train-negative-splitted.xml");
        total.addAll(purr.getTweets());
        purr=new Parser(total);
        purr.autoBalance();
        posPur=new Parser(purr.getPrePositive());
        neuPur=new Parser(purr.getPreNeutral());
        negPur=new Parser(purr.getPreNegative());
        
        ngramsPos=posPur.convertToGraphs();
        ngramsNeu=neuPur.convertToGraphs();
        ngramsNeg=negPur.convertToGraphs();
    }
    
    private void readGraphs(){
        Parser purr=new Parser();
        System.out.println("Reading Pos Graphs...");
        purr.parse(GlobalVarsStore.trainDataDirectory+"tweets-train-positive-splitted.xml");
        ngramsPos=purr.convertToGraphs();
        System.out.println("Reading Neu Graphs...");
        purr.parse(GlobalVarsStore.trainDataDirectory+"tweets-train-neutral-splitted.xml");
        ngramsNeu=purr.convertToGraphs();
        System.out.println("Reading Neg Graphs...");
        purr.parse(GlobalVarsStore.trainDataDirectory+"tweets-train-negative-splitted.xml");
        ngramsNeg=purr.convertToGraphs();
    }
    
    private void prepareGraphs(){
        readGraphs();
        //readBalancedGraphs();
        
        System.out.println("Merging Pos Graphs...");
        ThreadedMerger tmp = new ThreadedMerger(ngramsPos, "pos", 1);
        tmp.start();
        System.out.println("Merging Neu Graphs...");
        ThreadedMerger tnu = new ThreadedMerger(ngramsNeu, "neu", 1);
        tnu.start();
        System.out.println("Merging Neg Graphs...");
        ThreadedMerger tng = new ThreadedMerger(ngramsNeg, "neg", 1);
        tng.start();
        if (tng.isAlive()) {
            try {
                tng.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (tnu.isAlive()) {
            try {
                tnu.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (tmp.isAlive()) {
            try {
                tmp.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Thresholding Graphs...");
        pos.setThreshhold(threshold);
        neu.setThreshhold(threshold);
        neg.setThreshhold(threshold);
        
        System.out.println("Saving Graphs To Files...");
        XMLBuilder.buildMergedGraph(pos, GlobalVarsStore.trainDataDirectory+"Merged_Positive_Graph.xml");
        XMLBuilder.buildMergedGraph(neu, GlobalVarsStore.trainDataDirectory+"Merged_Neutral_Graph.xml");
        XMLBuilder.buildMergedGraph(neg, GlobalVarsStore.trainDataDirectory+"Merged_Negative_Graph.xml");
    }
    
    public void produceTrainVectors(String mode){
        if(mode.toLowerCase().equals("prepared")){
            readGraphs();
            pos=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Positive_Graph.xml");
            neu=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Neutral_Graph.xml");
            neg=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Negative_Graph.xml");
        }
        else{
            prepareGraphs();
        }
        System.out.println("Calculating Pos Vectors...");
        ThreadedVectorCalculator vmp = new ThreadedVectorCalculator(ngramsPos, "trainPos");
        vmp.start();
        System.out.println("Calculating Neu Vectors...");
        ThreadedVectorCalculator vmn = new ThreadedVectorCalculator(ngramsNeu, "trainNeu");
        vmn.start();
        System.out.println("Calculating Neg Vectors...");
        ThreadedVectorCalculator vng = new ThreadedVectorCalculator(ngramsNeg, "trainNeg");
        vng.start();

        if (vmp.isAlive()) {
            try {
                vmp.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (vmn.isAlive()) {
            try {
                vmn.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        if (vng.isAlive()) {
            try {
                vng.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Writing vectors.....");
        try {
            Reader.writeVector(trainVectorsPos, GlobalVarsStore.trainDataDirectory+"trainVectorsPos.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        try {
            Reader.writeVector(trainVectorsNeu, GlobalVarsStore.trainDataDirectory+"trainVectorsNeu.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        try {
            Reader.writeVector(trainVectorsNeg, GlobalVarsStore.trainDataDirectory+"trainVectorsNeg.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        
        int countPos = 0;
        int countNeu = 0;
        int countNeg = 0;
        for (int i = 0; i < trainVectorsPos.size(); i++) {
            countPos += trainVectorsPos.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeu.size(); i++) {
            countNeu += trainVectorsNeu.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeg.size(); i++) {
            countNeg += trainVectorsNeg.get(i).size();
        }
        System.out.println("PosVectors = " + countPos + "\nNeuVectors = " + countNeu + "\nNegVectors = " + countNeg);
        System.out.println("PosTweets = " + trainVectorsPos.size() + "\nNeuTweets = " + trainVectorsNeu.size() + "\nNegTweets = " + trainVectorsNeg.size());      
    }
    
    private static String clear(String dirty){
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
        return clear.trim();
    }
    
    public static Instance textToInstance(String text){
        text=org.apache.commons.lang3.StringEscapeUtils.escapeHtml3(text);
        NGramCreator ngc = new NGramCreator();
        ngc.targetString(clear(text));
        ngc.mapEdgesV2(Main.ngramms);
        ArrayList<Float> vectors;
        if(!GlobalVarsStore.fast){
        vectors=SimCalculator.calcVector(ngc.getEdges());
        }else{
         vectors=SimCalculator.calcVectorFast(ngc.getEdges());   
        }
        Attribute Attribute1 = new Attribute("csPos");
        Attribute Attribute2 = new Attribute("csNeu");
        Attribute Attribute3 = new Attribute("csNeg");
        Attribute Attribute4 = new Attribute("vsPos");
        Attribute Attribute5 = new Attribute("vsNeu");
        Attribute Attribute6 = new Attribute("vsNeg");
        Attribute Attribute7 = new Attribute("nvsPos");
        Attribute Attribute8 = new Attribute("nvsNeu");
        Attribute Attribute9 = new Attribute("nvsNeg");


        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);
        
        int attributesSize;
        if(GlobalVarsStore.fast) attributesSize=4;
        else attributesSize=10;

        FastVector fvWekaAttributes = new FastVector(attributesSize);
        
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        if(!GlobalVarsStore.fast){
        fvWekaAttributes.addElement(Attribute4);
        fvWekaAttributes.addElement(Attribute5);
        fvWekaAttributes.addElement(Attribute6);
        fvWekaAttributes.addElement(Attribute7);
        fvWekaAttributes.addElement(Attribute8);
        fvWekaAttributes.addElement(Attribute9);
        }
        fvWekaAttributes.addElement(ClassAttribute);

        
        Instances theSet = new Instances("Rel", fvWekaAttributes, 2);
        theSet.setClassIndex(attributesSize-1);
        Instance iExample = new Instance(attributesSize);
        for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), vectors.get(i));
            }
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
        theSet.add(iExample);
        return theSet.firstInstance();
    }
    
    public double[] testSingle(Instance ins){
        try {
            return theModel.distributionForInstance(ins);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public void crossValidate(){
        pos=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Positive_Graph.xml");
        neu=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Neutral_Graph.xml");
        neg=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Negative_Graph.xml");
        
        System.out.println("Reading pos vectors from file.....");
        trainVectorsPos = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsPos.txt");
        System.out.println("Reading neu vectors from file.....");
        trainVectorsNeu = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeu.txt");
        System.out.println("Reading neg vectors from file.....");
        trainVectorsNeg = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeg.txt");
        int countPos = 0;
        int countNeu = 0;
        int countNeg = 0;
        for (int i = 0; i < trainVectorsPos.size(); i++) {
            countPos += trainVectorsPos.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeu.size(); i++) {
            countNeu += trainVectorsNeu.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeg.size(); i++) {
            countNeg += trainVectorsNeg.get(i).size();
        }
        System.out.println("PosVectors = " + countPos + "\nNeuVectors = " + countNeu + "\nNegVectors = " + countNeg);
        System.out.println("PosTweets = " + trainVectorsPos.size() + "\nNeuTweets = " + trainVectorsNeu.size() + "\nNegTweets = " + trainVectorsNeg.size());
    
        Attribute Attribute1 = new Attribute("csPos");
        Attribute Attribute2 = new Attribute("csNeu");
        Attribute Attribute3 = new Attribute("csNeg");
        Attribute Attribute4 = new Attribute("vsPos");
        Attribute Attribute5 = new Attribute("vsNeu");
        Attribute Attribute6 = new Attribute("vsNeg");
        Attribute Attribute7 = new Attribute("nvsPos");
        Attribute Attribute8 = new Attribute("nvsNeu");
        Attribute Attribute9 = new Attribute("nvsNeg");

        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        int attributesSize;
        if(GlobalVarsStore.fast) attributesSize=4;
        else attributesSize=10;

        FastVector fvWekaAttributes = new FastVector(attributesSize);
        
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        if(!GlobalVarsStore.fast){
        fvWekaAttributes.addElement(Attribute4);
        fvWekaAttributes.addElement(Attribute5);
        fvWekaAttributes.addElement(Attribute6);
        fvWekaAttributes.addElement(Attribute7);
        fvWekaAttributes.addElement(Attribute8);
        fvWekaAttributes.addElement(Attribute9);
        }
        fvWekaAttributes.addElement(ClassAttribute);

        Instances TrainingSet = new Instances("Rel", fvWekaAttributes, trainVectorsPos.size() + trainVectorsNeg.size() + trainVectorsNeu.size() + 1);
        TrainingSet.setClassIndex(attributesSize-1);
        System.out.println("Setting Training Instances...");
        for (int j = 0; j < trainVectorsPos.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsPos.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
            TrainingSet.add(iExample);
        }
        for (int j = 0; j < trainVectorsNeu.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeu.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "neutral");
            TrainingSet.add(iExample);
        }
        for (int j = 0; j < trainVectorsNeg.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeg.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "negative");
            TrainingSet.add(iExample);
        }
        System.out.println("Building Classifier...");

        Classifier cModel = null;
        
        if(algor.toLowerCase().trim().equals("bayes"))
        cModel = (Classifier) new NaiveBayes();
        else if(algor.toLowerCase().trim().equals("c4.5"))
        cModel = (Classifier) new J48();
        else if(algor.toLowerCase().trim().equals("svm"))
        cModel = (Classifier) new LibSVM();
        else if(algor.toLowerCase().trim().equals("logistic"))
        cModel = (Classifier) new weka.classifiers.functions.Logistic();
        else if(algor.toLowerCase().trim().equals("simplelogistic"))
        cModel = (Classifier) new weka.classifiers.functions.SimpleLogistic();
        else if(algor.toLowerCase().trim().equals("mlp"))
        cModel = (Classifier) new weka.classifiers.functions.MultilayerPerceptron();
        else if(algor.toLowerCase().trim().equals("bftree"))
        cModel = (Classifier) new weka.classifiers.trees.BFTree();
        else if(algor.toLowerCase().trim().equals("ft"))
        cModel = (Classifier) new weka.classifiers.trees.FT();
        else if(algor.toLowerCase().trim().equals("test"))
        cModel = (Classifier) new weka.classifiers.trees.SimpleCart();
        cModel.setDebug(true);
        Evaluation eval;
        try {
            double averageCorrect=0;
            Instances randData = new Instances(TrainingSet);   // create copy of original data
            randData.randomize(new Random(1)); 
            for (int i = 0; i < 10; i++) {
                eval = new Evaluation(TrainingSet);
                eval.crossValidateModel(cModel, TrainingSet, 10, new Random(i+1));
                averageCorrect+=eval.correct()/eval.numInstances();
            }
            averageCorrect=averageCorrect/10;
            averageCorrect=(((double)Math.round(averageCorrect*10000))/100);
            if(algor.toLowerCase().trim().equals("c4.5"))
            Main.result+="\n----------Correctly classified C4.5: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("bayes"))
            Main.result+="\n---------Correctly classified Bayes: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("svm"))
            Main.result+="\n-----------Correctly classified SVM: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("logistic"))
            Main.result+="\n------Correctly classified Logistic: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("simplelogistic"))
            Main.result+="\n----Correctly classified S Logistic: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("mlp"))
            Main.result+="\n-----------Correctly classified MLP: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("bftree"))
            Main.result+="\n--------Correctly classified BFtree: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("ft"))
            Main.result+="\n------------Correctly classified FT: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("test"))
            Main.result+="\n-----------Correctly classified test: "+averageCorrect+"%";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Classifier train(String mode){
        if(pos==null || neu==null || neg==null){
            if(mode.toLowerCase().equals("prepared")){
                pos=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Positive_Graph.xml");
                neu=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Neutral_Graph.xml");
                neg=Parser.readMerger(GlobalVarsStore.trainDataDirectory+"Merged_Negative_Graph.xml");
            }
            else{
                prepareGraphs();
            }
        }
        if(trainVectorsPos==null || trainVectorsNeu==null || trainVectorsNeg==null){
            System.out.println("Reading pos vectors from file.....");
            trainVectorsPos = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsPos.txt");
            System.out.println("Reading neu vectors from file.....");
            trainVectorsNeu = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeu.txt");
            System.out.println("Reading neg vectors from file.....");
            trainVectorsNeg = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeg.txt");
        }
        if(TrainingSet==null){
            Attribute Attribute1 = new Attribute("csPos");
            Attribute Attribute2 = new Attribute("csNeu");
            Attribute Attribute3 = new Attribute("csNeg");
            Attribute Attribute4 = new Attribute("vsPos");
            Attribute Attribute5 = new Attribute("vsNeu");
            Attribute Attribute6 = new Attribute("vsNeg");
            Attribute Attribute7 = new Attribute("nvsPos");
            Attribute Attribute8 = new Attribute("nvsNeu");
            Attribute Attribute9 = new Attribute("nvsNeg");

            FastVector fvClassVal = new FastVector(3);
            fvClassVal.addElement("positive");
            fvClassVal.addElement("neutral");
            fvClassVal.addElement("negative");
            Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

            int attributesSize;
            if(GlobalVarsStore.fast) attributesSize=4;
            else attributesSize=10;

            FastVector fvWekaAttributes = new FastVector(attributesSize);

            fvWekaAttributes.addElement(Attribute1);
            fvWekaAttributes.addElement(Attribute2);
            fvWekaAttributes.addElement(Attribute3);
            if(!GlobalVarsStore.fast){
            fvWekaAttributes.addElement(Attribute4);
            fvWekaAttributes.addElement(Attribute5);
            fvWekaAttributes.addElement(Attribute6);
            fvWekaAttributes.addElement(Attribute7);
            fvWekaAttributes.addElement(Attribute8);
            fvWekaAttributes.addElement(Attribute9);
            }
            fvWekaAttributes.addElement(ClassAttribute);

            TrainingSet = new Instances("Rel", fvWekaAttributes, trainVectorsPos.size() + trainVectorsNeg.size() + trainVectorsNeu.size() + 1);
            TrainingSet.setClassIndex(attributesSize-1);
            for (int j = 0; j < trainVectorsPos.size(); j++) {
                Instance iExample = new Instance(attributesSize);
                for (int i = 0; i < attributesSize-1; i++) {
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsPos.get(j).get(i));
                }
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
                TrainingSet.add(iExample);
            }
            for (int j = 0; j < trainVectorsNeu.size(); j++) {
                Instance iExample = new Instance(attributesSize);
                for (int i = 0; i < attributesSize-1; i++) {
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeu.get(j).get(i));
                }
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "neutral");
                TrainingSet.add(iExample);
            }
            for (int j = 0; j < trainVectorsNeg.size(); j++) {
                Instance iExample = new Instance(attributesSize);
                for (int i = 0; i < attributesSize-1; i++) {
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeg.get(j).get(i));
                }
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "negative");
                TrainingSet.add(iExample);
            }
        }
        System.out.println("Building Classifier for Graphs "+algor+" ...");

        Classifier cModel = null;
        
        if(algor.toLowerCase().trim().equals("bayes")){
        cModel = (Classifier) new NaiveBayes();
        }
        else if(algor.toLowerCase().trim().equals("c4.5")){
             cModel = (Classifier) new J48();
        }
        else if(algor.toLowerCase().trim().equals("svm")){
        cModel = (Classifier) new LibSVM();
        }
        else if(algor.toLowerCase().trim().equals("logistic")){
        cModel = (Classifier) new weka.classifiers.functions.Logistic();
        }
        else if(algor.toLowerCase().trim().equals("simplelogistic")){
        cModel = (Classifier) new weka.classifiers.functions.SimpleLogistic();
        }
        else if(algor.toLowerCase().trim().equals("mlp")){
        cModel = (Classifier) new weka.classifiers.functions.MultilayerPerceptron();
        }
        else if(algor.toLowerCase().trim().equals("bftree")){
        cModel = (Classifier) new weka.classifiers.trees.BFTree();
        }
        else if(algor.toLowerCase().trim().equals("ft")){
        cModel = (Classifier) new weka.classifiers.trees.FT();
        }
        else if(algor.toLowerCase().trim().equals("test")){
        cModel = (Classifier) new weka.classifiers.trees.SimpleCart();
        }
        
        try {
            cModel.buildClassifier(TrainingSet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //System.out.println(cModel.toString());
        theModel=cModel;
        return cModel;
    }
    
    /*public void testFromFile(String term){
        System.out.println("Preparing...");
        Parser purse = new Parser();
        purse.parseByTerm(term);
        pos = new GraphMerger();
        neg = new GraphMerger();
        neu = new GraphMerger();
        trainVectorsPos = new ArrayList<ArrayList<Float>>();
        trainVectorsNeu = new ArrayList<ArrayList<Float>>();
        trainVectorsNeg = new ArrayList<ArrayList<Float>>();
        NGramTests.partials = new ArrayList<GraphMerger>();
        
        if("Bayes".equals(algor)){
            testBayes(purse);
        } else if("C4.5".equals(algor)){
            testC45(purse);
        } else{
            System.out.println("No method specified. Ending analysis...");
        }
    }*/

    /*private void testBayes(Parser purse) {
        System.out.println("Reading pos vectors from file.....");
        trainVectorsPos = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsPos.txt");
        trainVectorsNeu = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeu.txt");
        trainVectorsNeg = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeg.txt");
        int countPos = 0;
        int countNeu = 0;
        int countNeg = 0;
        for (int i = 0; i < trainVectorsPos.size(); i++) {
            countPos += trainVectorsPos.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeu.size(); i++) {
            countNeu += trainVectorsNeu.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeg.size(); i++) {
            countNeg += trainVectorsNeg.get(i).size();
        }
        System.out.println("PosVectors = " + countPos + "\nNeuVectors = " + countNeu + "\nNegVectors = " + countNeg);
        System.out.println("PosTweets = " + trainVectorsPos.size() + "\nNeuTweets = " + trainVectorsNeu.size() + "\nNegTweets = " + trainVectorsNeg.size());
    
        Attribute Attribute1 = new Attribute("csPos");
        Attribute Attribute2 = new Attribute("csNeg");
        Attribute Attribute3 = new Attribute("vsPos");
        Attribute Attribute4 = new Attribute("vsNeg");
        Attribute Attribute5 = new Attribute("nvsPos");
        Attribute Attribute6 = new Attribute("nvsNeg");

        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(7);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(Attribute4);
        fvWekaAttributes.addElement(Attribute5);
        fvWekaAttributes.addElement(Attribute6);
        fvWekaAttributes.addElement(ClassAttribute);

        Instances theSet = new Instances("Rel", fvWekaAttributes, trainVectorsPos.size() + trainVectorsNeg.size() + 1);
        theSet.setClassIndex(6);
        System.out.println("Setting Training Instances...");
        for (int j = 0; j < trainVectorsPos.size(); j++) {
            Instance iExample = new Instance(7);
            for (int i = 0; i < 6; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsPos.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), "positive");
            theSet.add(iExample);
        }
        for (int j = 0; j < trainVectorsNeg.size(); j++) {
            Instance iExample = new Instance(7);
            for (int i = 0; i < 6; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeg.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), "negative");
            theSet.add(iExample);
        }
        System.out.println("Building Classifier...");

        Classifier cModel = null;
        cModel = (Classifier) new NaiveBayes();
        try {
            cModel.buildClassifier(theSet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(cModel.toString());
        
        /*System.out.println("Testing Classifier...");
        Evaluation eTest;
        String strSummary = "null";
        try {
            eTest = new Evaluation(theSet);
            eTest.evaluateModel(cModel, theSet);
            strSummary = eTest.toSummaryString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(strSummary);*//*
        
        ngramsTest = purse.convertToGraphs();
        ThreadedVectorCalculator tvc=new ThreadedVectorCalculator(ngramsTest, "ap");
        tvc.start();
        if (tvc.isAlive()) {
            try {
                tvc.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Instances TestSet = new Instances("Rel", fvWekaAttributes, appliedVectors.size() + 1);
        TestSet.setClassIndex(6);
        System.out.println("Applying Algorithm...");
        for (int j = 0; j < appliedVectors.size(); j++) {
            Instance iExample = new Instance(7);
            for (int i = 0; i < 6; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), appliedVectors.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), "positive");
            TestSet.add(iExample);
        }
        ArrayList<double[]> distributs=new ArrayList<double[]>();
        for(int j=0;j<TestSet.numInstances();j++){
            try {
                distributs.add(cModel.distributionForInstance(TestSet.instance(j)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        for(int j=0;j<distributs.size();j++){
            System.out.println("pos: "+distributs.get(j)[0]+" - neg: "+distributs.get(j)[1]);
        }
        System.out.println("Total instances: "+TestSet.numInstances()+"\nTotal tested: "+distributs.size());
    }*/

    /*private void testC45(Parser purse) {
        try {
            prepare();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        Attribute Attribute1 = new Attribute("csPos");
        Attribute Attribute2 = new Attribute("csNeg");
        Attribute Attribute3 = new Attribute("vsPos");
        Attribute Attribute4 = new Attribute("vsNeg");
        Attribute Attribute5 = new Attribute("nvsPos");
        Attribute Attribute6 = new Attribute("nvsNeg");

        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(7);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(Attribute4);
        fvWekaAttributes.addElement(Attribute5);
        fvWekaAttributes.addElement(Attribute6);
        fvWekaAttributes.addElement(ClassAttribute);

        Instances theSet = new Instances("Rel", fvWekaAttributes, trainVectorsPos.size() + trainVectorsNeg.size() + 1);
        theSet.setClassIndex(6);
        System.out.println("Setting Training Instances...");
        for (int j = 0; j < trainVectorsPos.size(); j++) {
            Instance iExample = new Instance(7);
            for (int i = 0; i < 6; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsPos.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), "positive");
            theSet.add(iExample);
        }
        for (int j = 0; j < trainVectorsNeg.size(); j++) {
            Instance iExample = new Instance(7);
            for (int i = 0; i < 6; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeg.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), "negative");
            theSet.add(iExample);
        }
        System.out.println("Building Classifier...");

        Classifier cModel = null;
        cModel = (Classifier) new J48();
        try {
            cModel.buildClassifier(theSet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(cModel.toString());
        
        /*System.out.println("Testing Classifier...");
        Evaluation eTest;
        String strSummary = "null";
        try {
            eTest = new Evaluation(theSet);
            eTest.evaluateModel(cModel, theSet);
            strSummary = eTest.toSummaryString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(strSummary);*//*
        
        ngramsTest = purse.convertToGraphs();
        
        
        ThreadedVectorCalculator tvc=new ThreadedVectorCalculator(ngramsTest, "ap");
        tvc.start();
        if (tvc.isAlive()) {
            try {
                tvc.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Instances TestSet = new Instances("Rel", fvWekaAttributes, appliedVectors.size() + 1);
        TestSet.setClassIndex(6);
        System.out.println("Applying Algorithm...");
        for (int j = 0; j < appliedVectors.size(); j++) {
            Instance iExample = new Instance(7);
            for (int i = 0; i < 6; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), appliedVectors.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), "positive");
            TestSet.add(iExample);
        }
        ArrayList<double[]> distributs=new ArrayList<double[]>();
        for(int j=0;j<TestSet.numInstances();j++){
            try {
                distributs.add(cModel.distributionForInstance(TestSet.instance(j)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        for(int j=0;j<distributs.size();j++){
            System.out.println("pos: "+distributs.get(j)[0]+" - neg: "+distributs.get(j)[1]);
        }
        System.out.println("Total instances: "+TestSet.numInstances()+"\nTotal tested: "+distributs.size());
    }*/
    
     /*private static void prepare() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
        System.out.println("Preparing...");
        pos = new GraphMerger();
        neg = new GraphMerger();
        neu = new GraphMerger();
        trainVectorsPos = new ArrayList<ArrayList<Float>>();
        trainVectorsNeu = new ArrayList<ArrayList<Float>>();
        trainVectorsNeg = new ArrayList<ArrayList<Float>>();
        System.out.println("Reading Pos Graphs...");
        ngramsPos = Reader.readGraphs("C:\\Users\\ViP\\Documents\\tweetsPos.txt", 10000, nGrammNumber);
        System.out.println("Reading Neu Graphs...");
        ngramsNeu = Reader.readGraphs("C:\\Users\\ViP\\Documents\\tweetsNeu.txt", 10000, nGrammNumber);
        System.out.println("Reading Neg Graphs...");
        ngramsNeg = Reader.readGraphs("C:\\Users\\ViP\\Documents\\tweetsNeg.txt", 10000, nGrammNumber);
        System.out.println("Merging Pos Graphs...");
        ThreadedMerger tmp = new ThreadedMerger(ngramsPos, "pos", 1);
        tmp.start();
        System.out.println("Merging Neu Graphs...");
        ThreadedMerger tnu = new ThreadedMerger(ngramsNeu, "neu", 1);
        tnu.start();
        System.out.println("Merging Neg Graphs...");
        ThreadedMerger tng = new ThreadedMerger(ngramsNeg, "neg", 1);
        tng.start();
        if (tng.isAlive()) {
            tng.join();
        }
        if (tnu.isAlive()) {
            tnu.join();
        }
        if (tmp.isAlive()) {
            tmp.join();
        }
        System.out.println("Thresholding Graphs...");
        pos.setThreshhold(threshold);
        neu.setThreshhold(threshold);
        neg.setThreshhold(threshold);

        System.out.println("Reading pos vectors from file.....");
        trainVectorsPos = Reader.readVector("trainVectorsPos.txt");
        trainVectorsNeu = Reader.readVector("trainVectorsNeu.txt");
        trainVectorsNeg = Reader.readVector("trainVectorsNeg.txt");
        int countPos = 0;
        int countNeu = 0;
        int countNeg = 0;
        for (int i = 0; i < trainVectorsPos.size(); i++) {
            countPos += trainVectorsPos.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeu.size(); i++) {
            countNeu += trainVectorsNeu.get(i).size();
        }
        for (int i = 0; i < trainVectorsNeg.size(); i++) {
            countNeg += trainVectorsNeg.get(i).size();
        }
        System.out.println("PosVectors = " + countPos + "\nNeuVectors = " + countNeu + "\nNegVectors = " + countNeg);
        System.out.println("PosTweets = " + trainVectorsPos.size() + "\nNeuTweets = " + trainVectorsNeu.size() + "\nNegTweets = " + trainVectorsNeg.size());
        
    }*/

    private void testTrained(Parser purse, Classifier cModel) {
        Attribute Attribute1 = new Attribute("csPos");
        Attribute Attribute2 = new Attribute("csNeu");
        Attribute Attribute3 = new Attribute("csNeg");
        Attribute Attribute4 = new Attribute("vsPos");
        Attribute Attribute5 = new Attribute("vsNeu");
        Attribute Attribute6 = new Attribute("vsNeg");
        Attribute Attribute7 = new Attribute("nvsPos");
        Attribute Attribute8 = new Attribute("nvsNeu");
        Attribute Attribute9 = new Attribute("nvsNeg");

        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        int attributesSize;
            if(GlobalVarsStore.fast) attributesSize=4;
            else attributesSize=10;

            FastVector fvWekaAttributes = new FastVector(attributesSize);

            fvWekaAttributes.addElement(Attribute1);
            fvWekaAttributes.addElement(Attribute2);
            fvWekaAttributes.addElement(Attribute3);
            if(!GlobalVarsStore.fast){
            fvWekaAttributes.addElement(Attribute4);
            fvWekaAttributes.addElement(Attribute5);
            fvWekaAttributes.addElement(Attribute6);
            fvWekaAttributes.addElement(Attribute7);
            fvWekaAttributes.addElement(Attribute8);
            fvWekaAttributes.addElement(Attribute9);
            }
            fvWekaAttributes.addElement(ClassAttribute);
        
        ngramsTest = purse.convertToGraphs();
        ThreadedVectorCalculator tvc=new ThreadedVectorCalculator(ngramsTest, "ap");
        tvc.start();
        if (tvc.isAlive()) {
            try {
                tvc.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Instances TestSet = new Instances("Rel", fvWekaAttributes, appliedVectors.size() + 1);
        TestSet.setClassIndex(attributesSize-1);
        System.out.println("Applying Algorithm...");
        for (int j = 0; j < appliedVectors.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), appliedVectors.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
            TestSet.add(iExample);
        }
        ArrayList<double[]> distributs=new ArrayList<double[]>();
        for(int j=0;j<TestSet.numInstances();j++){
            try {
                distributs.add(cModel.distributionForInstance(TestSet.instance(j)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        /*for(int i=0;i<purse.getTweets().size();i++){
            if((distributs.get(i)[0]>(distributs.get(i)[1]+(distributs.get(i)[1]*0.5))) &&(distributs.get(i)[0]>(distributs.get(i)[2]+(distributs.get(i)[2]*0.5)))){
                purse.getTweets().get(i).setPolarity(1.0);
            }else if((distributs.get(i)[2]>(distributs.get(i)[1]+(distributs.get(i)[1]*0.5))) &&(distributs.get(i)[2]>(distributs.get(i)[0]+(distributs.get(i)[0]*0.5)))){
                purse.getTweets().get(i).setPolarity(-1.0);
            }else if((distributs.get(i)[1]>(distributs.get(i)[0]+(distributs.get(i)[0]*0.5))) &&(distributs.get(i)[1]>(distributs.get(i)[2]+(distributs.get(i)[2]*0.5)))){
                purse.getTweets().get(i).setPolarity(0.0);
            }else{
                purse.getTweets().get(i).setPolarity(0.0);
            }
        }*/
        for(int i=0;i<purse.getTweets().size();i++){
            String text=purse.getTweets().get(i).getClearText();
            if(distributs.get(i)[0]>distributs.get(i)[1] && distributs.get(i)[0]>distributs.get(i)[2]){
                purse.getTweets().get(i).setPolarity(1.0);
            }else if(distributs.get(i)[2]>distributs.get(i)[1] && distributs.get(i)[2]>distributs.get(i)[0]){
                purse.getTweets().get(i).setPolarity(-1.0);
            }else if(distributs.get(i)[1]>distributs.get(i)[0] && distributs.get(i)[1]>distributs.get(i)[2]){
                purse.getTweets().get(i).setPolarity(0.0);
            }else{
                purse.getTweets().get(i).setPolarity(0.0);
            }
        }
        
        PrintWriter writer;
        PrintWriter writerPOS;
        PrintWriter writerNEU;
        PrintWriter writerNEG;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("NGramTest.txt", true)));
            writerPOS = new PrintWriter(new BufferedWriter(new FileWriter("NGramTestPOS.txt", true)));
            writerNEU = new PrintWriter(new BufferedWriter(new FileWriter("NGramTestNEU.txt", true)));
            writerNEG = new PrintWriter(new BufferedWriter(new FileWriter("NGramTestNEG.txt", true)));
            writer.println("\n-------------------Results-----------------\n");
            ArrayList<CustomStatus> posT=new ArrayList<CustomStatus>();
            ArrayList<CustomStatus> neuT=new ArrayList<CustomStatus>();
            ArrayList<CustomStatus> negT=new ArrayList<CustomStatus>();
            for(int i=0;i<purse.getTweets().size();i++){
                if(purse.getTweets().get(i).getPolarity()>0){
                    posT.add(purse.getTweets().get(i));
                    writerPOS.println(posT.get(posT.size()-1).getText());
                }else if(purse.getTweets().get(i).getPolarity()==0.0){
                    neuT.add(purse.getTweets().get(i));
                    writerNEU.println(neuT.get(neuT.size()-1).getText());
                }else if(purse.getTweets().get(i).getPolarity()<0){
                    negT.add(purse.getTweets().get(i));
                    writerNEG.println(negT.get(negT.size()-1).getText());
                }
            }
            /*double average=0;
            for(int i=0;i<posT.size();i++){
                writer.println(posT.get(i).getText());
                writer.println(posT.get(i).getPolarity());
                writer.println(posT.get(i).getPrePolarity()+"\n");
                if(Math.round(posT.get(i).getPolarity())==posT.get(i).getPrePolarity()){
                    average++;
                }
            }
            for(int i=0;i<neuT.size();i++){
                writer.println(neuT.get(i).getText());
                writer.println(neuT.get(i).getPolarity());
                writer.println(neuT.get(i).getPrePolarity()+"\n");
                if(Math.round(neuT.get(i).getPolarity())==neuT.get(i).getPrePolarity()){
                    average++;
                }
            }
            for(int i=0;i<negT.size();i++){
                writer.println(negT.get(i).getText());
                writer.println(negT.get(i).getPolarity());
                writer.println(negT.get(i).getPrePolarity()+"\n");
                if(Math.round(negT.get(i).getPolarity())==negT.get(i).getPrePolarity()){
                    average++;
                }
            }
            writer.println("--------------------------AVERAGE------------------------\n\n");
            writer.println(((average/(negT.size()+neuT.size()+posT.size()))*100)+"\n\n");*/
            writer.println("----------------------------Totals--------------------------\n\n");
            writer.println("Positive: "+posT.size());
            writer.println("Neutral:  "+neuT.size());
            writer.println("Negative: "+negT.size());
            writer.println("Total:    "+(posT.size()+neuT.size()+negT.size()));
            writer.println("--------------------------Distributs------------------------\n\n");
            for(int j=0;j<distributs.size();j++){
                writer.println(purse.getTweets().get(j).getClearText());
                writer.println("pos: "+distributs.get(j)[0]+" - neu: "+distributs.get(j)[1]+" - neg: "+distributs.get(j)[2]);
            }
            
            writer.close();
            writerPOS.close();
            writerNEU.close();
            writerNEG.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for(int j=0;j<distributs.size();j++){
            System.out.println(purse.getTweets().get(j).getClearText());
            System.out.println("pos: "+distributs.get(j)[0]+" - neu: "+distributs.get(j)[1]+" - neg: "+distributs.get(j)[2]);
        }
        System.out.println("Total instances: "+TestSet.numInstances()+"\nTotal tested: "+distributs.size());
    }
    
}
