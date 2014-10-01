package main_src;

import Entities.CustomStatus;
import Entities.WordDistance;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main_src.Main.printTime;
import tools.Parser;
import tools.PercentagePrinter;
import tools.RnFEvaluator;
import tools.SentiWordNet;
import tools.XMLBuilder;
import weka.associations.Apriori;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class WordNetTests {

    SentiWordNet swn;
    Double tested1;
    Double tested2;
    Double tested3;
    Double tested4;
    Double tested5;
    Double tested6;
    Double tested7;
    Double tested8;
    Classifier cModel;

    public WordNetTests(String path) {
        System.out.println("Parsing wordNet... "+Main.printTime(new java.util.Date()));
        this.swn = new SentiWordNet();
        this.swn.populate(path);
        System.out.println("WordNet parsed for "+this.swn.getWordCount()+" words. "+printTime(new java.util.Date()));
    }

    /*public void runRnFtest(){
        PercentagePrinter pp = new PercentagePrinter();
        System.out.println("pp running");
        System.out.println("Finding positive tweets... "+printTime(new java.util.Date()));
        int counter=0;
        Main.percentage=0;
        XMLBuilder.initialiseFile("polarised-pos-rnf");
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testWithRnF(Main.purse.getTweets().get(i));
            if(tested!=null && tested>0.0){
                XMLBuilder.buildSinglePolarisedToFile(Main.purse.getTweets().get(i),"polarised-pos-rnf",tested);
                counter++;
            } 
            if(pp.isAlive()){
                pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }
        XMLBuilder.finaliseFile("polarised-pos-rnf");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
        counter=0;
        Main.percentage=0;
        XMLBuilder.initialiseFile("polarised-neg-rnf");
        System.out.println("Finding negative tweets... "+printTime(new java.util.Date()));
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testWithRnF(Main.purse.getTweets().get(i));
            if(tested!=null && tested<0.0){
                XMLBuilder.buildSinglePolarisedToFile(Main.purse.getTweets().get(i),"polarised-neg-rnf",tested);
                counter++;
            }
            if(pp.isAlive()){
                pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }
        XMLBuilder.finaliseFile("polarised-neg-rnf");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
        counter=0;
        Main.percentage=0;
        XMLBuilder.initialiseFile("polarised-neu-rnf");
        System.out.println("Finding neutral tweets... "+printTime(new java.util.Date()));
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testWithRnF(Main.purse.getTweets().get(i));
            if(tested!=null && tested==0.0){
                XMLBuilder.buildSinglePolarisedToFile(Main.purse.getTweets().get(i),"polarised-neu-rnf",tested);
                counter++;
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }  
        XMLBuilder.finaliseFile("polarised-neu-rnf");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
    }*/
    
    private double max(double[] toTest){
        double cm=toTest[0];
        for(int i=1;i<toTest.length;i++){
            if(cm<toTest[i]){
                cm=toTest[i];
            }
        }
        return cm;
    }
    
    public void runTrainedTest(String filename){
        /*Parser temPursePos=new Parser();
        Parser temPurseNeg=new Parser();
        Parser temPurseNeu=new Parser();
        temPursePos.parseByTerm("polarised-pos");
        temPurseNeg.parseByTerm("polarised-neg");
        temPurseNeu.parseByTerm("polarised-neu");
        ArrayList<CustomStatus> total=new ArrayList<CustomStatus>();
        total.addAll(temPursePos.getTweets());
        total.addAll(temPurseNeg.getTweets());
        total.addAll(temPurseNeu.getTweets());*/
        this.train(filename);
        PercentagePrinter pp = new PercentagePrinter();
        DecimalFormat df = new DecimalFormat("#.##");
        Main.percentage=0;
        
        int count=0;
        XMLBuilder.initialiseFile("polarised-pos-trained");
        XMLBuilder.initialiseFile("polarised-neg-trained");
        XMLBuilder.initialiseFile("polarised-neu-trained");
        for(int i=0;i<Main.purse.getTweets().size();i++){
            double[] result=this.testSingle(Main.purse.getTweets().get(i));
            if(result!=null){
                count++;
                if(max(result)==result[0]){
                    Main.purse.getTweets().get(i).setPolarity(1.0);
                    XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-pos-trained");
                }else if(max(result)==result[1]){
                    Main.purse.getTweets().get(i).setPolarity(0.0);
                    XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-neu-trained");
                }else if(max(result)==result[2]){
                    Main.purse.getTweets().get(i).setPolarity(-1.0);
                    XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-neg-trained");
                }
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }
        XMLBuilder.finaliseFile("polarised-pos-trained");
        XMLBuilder.finaliseFile("polarised-neg-trained");
        XMLBuilder.finaliseFile("polarised-neu-trained");
        System.out.println("Classified "+count+"/"+Main.purse.getTweets().size()+" tweets.");
    }
    
    public void runTest(){
        PercentagePrinter pp = new PercentagePrinter();
        System.out.println("Finding positive tweets... "+printTime(new java.util.Date()));
        int counter=0;
        Main.percentage=0;
        XMLBuilder.initialiseFile("polarised-pos");
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testSentence(Main.purse.getTweets().get(i).getClearText());
            Main.purse.getTweets().get(i).setPolarity(tested);
            if(tested!=null && tested>0.0){
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-pos");
                counter++;
            } 
            if(pp.isAlive()){
                pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }
        XMLBuilder.finaliseFile("polarised-pos");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
        counter=0;
        Main.percentage=0;
        XMLBuilder.initialiseFile("polarised-neg");
        System.out.println("Finding negative tweets... "+printTime(new java.util.Date()));
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testSentence(Main.purse.getTweets().get(i).getClearText());
            Main.purse.getTweets().get(i).setPolarity(tested);
            if(tested!=null && tested<0.0){
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-neg");
                counter++;
            }
            if(pp.isAlive()){
                pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }
        XMLBuilder.finaliseFile("polarised-neg");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
        counter=0;
        Main.percentage=0;
        XMLBuilder.initialiseFile("polarised-neu");
        System.out.println("Finding neutral tweets... "+printTime(new java.util.Date()));
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testSentence(Main.purse.getTweets().get(i).getClearText());
             Main.purse.getTweets().get(i).setPolarity(tested);
            if(tested!=null && tested==0.0){
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-neu");
                counter++;
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }  
        XMLBuilder.finaliseFile("polarised-neu");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
    }
    
   /*public void runThreadedTest(){
        System.out.println("Finding positive tweets... "+printTime(new java.util.Date()));
        PercentagePrinter pp = new PercentagePrinter();
        pp.start();
        int counter=0;
        XMLBuilder.initialiseFile("polarised-pos");
        for(int i=0;i<Main.purse.getTweets().size();i++){
            threadedTestSentence tts1= new threadedTestSentence(Main.purse.getTweets().get(i).getText(),"1");
            threadedTestSentence tts3 = null;
            threadedTestSentence tts2 = null;
            threadedTestSentence tts4 = null;
            threadedTestSentence tts5 = null;
            threadedTestSentence tts6 = null;
            threadedTestSentence tts7 = null;
            threadedTestSentence tts8 = null;
            int flag2=-1;
            int flag3=-1;
            int flag4=-1;
            int flag5=-1;
            int flag6=-1;
            int flag7=-1;
            int flag8=-1;
            tts1.start();
            if(i+1<Main.purse.getTweets().size()){
                flag2*=-1;
                tts2=new threadedTestSentence(Main.purse.getTweets().get(i+1).getText(),"2");
                tts2.start();
            }
            if(i+2<Main.purse.getTweets().size()){
                flag3*=-1;
                tts3=new threadedTestSentence(Main.purse.getTweets().get(i+2).getText(),"3");
                tts3.start();
            }
            if(i+3<Main.purse.getTweets().size()){
                flag4*=-1;
                tts4=new threadedTestSentence(Main.purse.getTweets().get(i+3).getText(),"4");
                tts4.start();
            }
            if(i+4<Main.purse.getTweets().size()){
                flag5*=-1;
                tts5=new threadedTestSentence(Main.purse.getTweets().get(i+4).getText(),"5");
                tts5.start();
            }
            if(i+5<Main.purse.getTweets().size()){
                flag6*=-1;
                tts6=new threadedTestSentence(Main.purse.getTweets().get(i+5).getText(),"6");
                tts6.start();
            }
            if(i+6<Main.purse.getTweets().size()){
                flag7*=-1;
                tts7=new threadedTestSentence(Main.purse.getTweets().get(i+6).getText(),"7");
                tts7.start();
            }
            if(i+7<Main.purse.getTweets().size()){
                flag8*=-1;
                tts8=new threadedTestSentence(Main.purse.getTweets().get(i+7).getText(),"8");
                tts8.start();
            }
            if(tts1.isAlive()){
                try {
                    tts1.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts2!=null && tts2.isAlive()){
                try {
                    tts2.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts3!=null && tts3.isAlive()){
                try {
                    tts3.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts4!=null && tts4.isAlive()){
                try {
                    tts4.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts5!=null && tts5.isAlive()){
                try {
                    tts5.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts6!=null && tts6.isAlive()){
                try {
                    tts6.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts7!=null && tts7.isAlive()){
                try {
                    tts7.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tts8!=null && tts8.isAlive()){
                try {
                    tts8.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if(tested1!=null && tested1>0.0){
                Main.purse.getTweets().get(i).setPolarity(tested1);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i),"polarised-pos");
                counter++;
            }
            if(flag2>0 && tested2!=null && tested2>0.0){
                Main.purse.getTweets().get(i+1).setPolarity(tested2);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+1),"polarised-pos");
                counter++;
            }
            if(flag3>0 && tested3!=null && tested3>0.0){
                Main.purse.getTweets().get(i+2).setPolarity(tested3);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+2),"polarised-pos");
                counter++;
            }
            if(flag4>0 && tested4!=null && tested4>0.0){
                Main.purse.getTweets().get(i+3).setPolarity(tested4);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+3),"polarised-pos");
                counter++;
            }
            if(flag5>0 && tested5!=null && tested5>0.0){
                Main.purse.getTweets().get(i+4).setPolarity(tested5);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+4),"polarised-pos");
                counter++;
            }
            if(flag6>0 && tested6!=null && tested6>0.0){
                Main.purse.getTweets().get(i+5).setPolarity(tested6);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+5),"polarised-pos");
                counter++;
            }
            if(flag7>0 && tested7!=null && tested7>0.0){
                Main.purse.getTweets().get(i+6).setPolarity(tested7);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+6),"polarised-pos");
                counter++;
            }
            if(flag8>0 && tested8!=null && tested8>0.0){
                Main.purse.getTweets().get(i+7).setPolarity(tested8);
                XMLBuilder.buildSingleToFile(Main.purse.getTweets().get(i+7),"polarised-pos");
                counter++;
            }
            if(pp.isAlive()){
                pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)counter/Main.purse.getTweets().size())*10000)/100;
            pp.start();
        }
        XMLBuilder.finaliseFile("polarised-pos");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
        counter=0;
        XMLBuilder.initialiseFile("polarised-neg");
        System.out.println("Finding negative tweets... "+printTime(new java.util.Date()));
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testSentence(Main.purse.getTweets().get(i).getText());
            if(tested!=null && tested<0.0){
                XMLBuilder.buildSinglePolarisedToFile(Main.purse.getTweets().get(i),"polarised-neg",tested);
                counter++;
            }
        }
        XMLBuilder.finaliseFile("polarised-neg");
        System.out.println("Found "+counter+" positive tweets. "+printTime(new java.util.Date()));
        counter=0;
        XMLBuilder.initialiseFile("polarised-neu");
        System.out.println("Finding neutral tweets... "+printTime(new java.util.Date()));
        for(int i=0;i<Main.purse.getTweets().size();i++){
            Double tested=this.testSentence(Main.purse.getTweets().get(i).getText());
            if(tested!=null && tested==0.0){
                XMLBuilder.buildSinglePolarisedToFile(Main.purse.getTweets().get(i),"polarised-neu",tested);
                counter++;
            }
        }  
        XMLBuilder.finaliseFile("polarised-neu");
        System.out.println("Found "+counter+" neutral tweets. "+printTime(new java.util.Date()));
    }*/
    
    public Double testSentence(String sentence) {
        ArrayList<String> splitted = SentiWordNet.splitWords(sentence);
        ArrayList<Double> pols = new ArrayList<Double>();
        for (int i = 0; i < splitted.size(); i++) {
            Double cur = swn.testWord(splitted.get(i));
            if (cur != null) {
                pols.add(cur);
            }
        }
        Double result = 0.0;
        if (pols.size() > 0) {
            for (int i = 0; i < pols.size(); i++) {
                result += pols.get(i);
            }
            result=result/pols.size();
        } else {
            result = null;
        }
        if(result!=null)
        return ((double)Math.round(result*100))/100;
        else
        return null;
    }

    public Double testWord(String word) {
        return swn.testWord(word);
    }

    public void buildTrainSet() {
        Parser purr=new Parser();
        System.out.println("Building WordNet train set..."+printTime(new java.util.Date()));
        //ArrayList<CustomStatus> posTweets=Parser.readPrerated("C:\\Users\\ViP\\Documents\\tweetsPos.txt", 1);
        //ArrayList<CustomStatus> negTweets=Parser.readPrerated("C:\\Users\\ViP\\Documents\\tweetsNeg.txt", -1);
        //ArrayList<CustomStatus> neuTweets=Parser.readPrerated("C:\\Users\\ViP\\Documents\\tweetsNeu.txt", 0);
        purr.parseByTerm("train-positive-splitted");
        ArrayList<CustomStatus> posTweets=new ArrayList<CustomStatus>();
        for(int i=0;i<purr.getTweets().size();i++){
            purr.getTweets().get(i).setPrePolarity(1);
            posTweets.add(purr.getTweets().get(i));
        }
        purr.parseByTerm("train-neutral-splitted");
        ArrayList<CustomStatus> neuTweets=new ArrayList<CustomStatus>();
        for(int i=0;i<purr.getTweets().size();i++){
            purr.getTweets().get(i).setPrePolarity(0);
            neuTweets.add(purr.getTweets().get(i));
        }
        purr.parseByTerm("train-negative-splitted");
        ArrayList<CustomStatus> negTweets=new ArrayList<CustomStatus>();
        for(int i=0;i<purr.getTweets().size();i++){
            purr.getTweets().get(i).setPrePolarity(-1);
            negTweets.add(purr.getTweets().get(i));
        }
        ArrayList<CustomStatus> totalTweets=new ArrayList<CustomStatus>();
        totalTweets.addAll(posTweets);
        totalTweets.addAll(negTweets);
        totalTweets.addAll(neuTweets);
        
        PercentagePrinter pp = new PercentagePrinter();
        
        Main.percentage=0;
        int counter=0;
        XMLBuilder.initialiseFile("wordnet-train");
        
        for(int i=0;i<totalTweets.size();i++){
            Double tested=this.testSentence(totalTweets.get(i).getClearText());
            totalTweets.get(i).setPolarity(tested);
            if(tested!=null){
                XMLBuilder.buildSingleToFile(totalTweets.get(i),"wordnet-train");
                counter++;
            } 
            if(pp.isAlive()){
                pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/totalTweets.size())*10000)/100;
            pp.start();
        }
        
        XMLBuilder.finaliseFile("wordnet-train");
        System.out.println("Rated "+counter+" tweets for training. "+printTime(new java.util.Date()));
    }
    
    public double[] testSingle(CustomStatus cs){
        double[] distributs=null;
        
        Attribute Attribute1 = new Attribute("polarity");

        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(ClassAttribute);

        Instances TestSet = new Instances("Rel", fvWekaAttributes, 2);
        TestSet.setClassIndex(1);
        
        Instance iExample = new Instance(2);
        
        Double pol=null;
        try {
            pol=cs.getPolarity();
            if(pol==null){
                pol=this.testSentence(cs.getClearText());
            }
            //if(pol==null){
            //     System.out.println(cs.getClearText());
            //}
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), pol);
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "positive");
            TestSet.add(iExample);
            
            distributs=cModel.distributionForInstance(TestSet.instance(0));
        } catch (Exception ex) {
            distributs=null;
        }
        return distributs;
    }
    
    public void train(String filename){
        Parser tempPurse=new Parser();
        tempPurse.parse(filename);
        tempPurse.autoBalance();
        Attribute Attribute1 = new Attribute("polarity");

        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(ClassAttribute);

        Instances TrainingSet = new Instances("Rel", fvWekaAttributes, tempPurse.countTweets()+1);
        TrainingSet.setClassIndex(1);
        
        for (int j = 0; j < tempPurse.countTweets(); j++) {
            Instance iExample = new Instance(2);
            Double pol=tempPurse.getTweets().get(j).getPolarity();
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), pol);
            if(tempPurse.getTweets().get(j).getPrePolarity()>0)
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "positive");
            else if(tempPurse.getTweets().get(j).getPrePolarity()<0)
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "negative");
            else
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "neutral");  
            TrainingSet.add(iExample);
        }
        
        cModel = new J48();
        //cModel = (Classifier) new NaiveBayes();
        //cModel = (Classifier) new LibSVM();
        try {
            cModel.buildClassifier(TrainingSet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(cModel.toString());
    }
    
    /*public Double testWithRnF(CustomStatus cs){
        double rnf=RnFEvaluator.RnFScore(cs);
        if(this.testSentence(cs.getText())!=null)
        return this.testSentence(cs.getText())*RnFEvaluator.RnFScore(cs);
        else
        return null;
    }*/

    private class threadedTestSentence extends Thread{

        String sentence;
        String mode;
        
        public threadedTestSentence(String sentence, String mode) {
            this.sentence=sentence;
            this.mode=mode;
        }
        
        @Override
        public void run() {
            ArrayList<String> splitted = SentiWordNet.splitWords(sentence);
        ArrayList<Double> pols = new ArrayList<Double>();
        for (int i = 0; i < splitted.size(); i++) {
            Double cur = swn.testWord(splitted.get(i));
            if (cur != null) {
                pols.add(cur);
            }
        }
        Double result = 0.0;
        if (pols.size() > 0) {
            for (int i = 0; i < pols.size(); i++) {
                result += pols.get(i);
            }
            result=result/pols.size();
        } else {
            result = null;
        }
            if("1".equals(mode)){
                tested1=result;
            }else if("2".equals(mode)){
                tested2=result;
            }else if("3".equals(mode)){
                tested3=result;
            }else if("4".equals(mode)){
                tested4=result;
            }else if("5".equals(mode)){
                tested5=result;
            }else if("6".equals(mode)){
                tested6=result;
            }else if("7".equals(mode)){
                tested7=result;
            }else if("8".equals(mode)){
                tested8=result;
            }
        }
    }
    
    public void tenFoldTest(String filename, String algor){
        Parser tempPurse=new Parser();
        tempPurse.parse(filename);
        System.out.println((double)((int)(((double)tempPurse.countCorrect()/tempPurse.countTweets())*10000))/100+"%");
        //tempPurse.autoBalance();
        Attribute Attribute1 = new Attribute("polarity");

        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(ClassAttribute);

        Instances TrainingSet = new Instances("Rel", fvWekaAttributes, tempPurse.countTweets()+1);
        TrainingSet.setClassIndex(1);
        
        for (int j = 0; j < tempPurse.countTweets(); j++) {
            Instance iExample = new Instance(2);
            Double pol=tempPurse.getTweets().get(j).getPolarity();
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), pol);
            if(tempPurse.getTweets().get(j).getPrePolarity()>0)
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "positive");
            else if(tempPurse.getTweets().get(j).getPrePolarity()<0)
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "negative");
            else
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "neutral");  
            TrainingSet.add(iExample);
        }
        
        if(algor.toLowerCase().trim().equals("bayes")){
            cModel = (Classifier) new NaiveBayes();
        } else if(algor.toLowerCase().trim().equals("c4.5")){
            cModel = (Classifier) new J48();
        } else if(algor.toLowerCase().trim().equals("svm")){
            cModel = (Classifier) new LibSVM();
        } 
        
        Evaluation eval;
        try {
            double averageCorrect=0;
            for (int i = 0; i < 10; i++) {
                eval = new Evaluation(TrainingSet);
                eval.crossValidateModel(cModel, TrainingSet, 10, new Random(i+1));
                averageCorrect+=eval.correct()/eval.numInstances();
            }
            averageCorrect=averageCorrect/10;
            averageCorrect=(((double)Math.round(averageCorrect*10000))/100);
            if(algor.toLowerCase().trim().equals("c4.5"))
            Main.result+="\n--Correctly classified WordNet C4.5: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("bayes"))
            Main.result+="\n-Correctly classified WordNet Bayes: "+averageCorrect+"%";
            else if(algor.toLowerCase().trim().equals("svm"))
            Main.result+="\n---Correctly classified WordNet SVM: "+averageCorrect+"%";
        } catch (Exception ex) {
            Logger.getLogger(WordNetTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}