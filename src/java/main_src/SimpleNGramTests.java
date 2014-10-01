/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_src;

import Entities.CustomStatus;
import Entities.NGram;
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
import tools.NGramCreator;
import tools.Parser;
import tools.PercentagePrinter;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.FT;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author ViP
 */
public class SimpleNGramTests implements Serializable{

    public static ArrayList<NGramCreator> grammsPos;
    public static ArrayList<NGramCreator> grammsNeu;
    public static ArrayList<NGramCreator> grammsNeg;
    public static ArrayList<NGram> merged;
    String algor;
    public static int n;
    public static int[] margins;
    private Classifier theModel;
    public static Instances TrainingSet;

    
    public SimpleNGramTests(String algor){
        this.algor = algor;
    }
    
    public double[] testSingle(Instance ins){
        try {
            return this.theModel.distributionForInstance(ins);
        } catch (Exception ex) {
            Logger.getLogger(SimpleNGramTests.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Instance textToInstanceSimple(String text){
        text=org.apache.commons.lang3.StringEscapeUtils.escapeHtml3(text);
        NGramCreator ngc = new NGramCreator();
        ngc.targetString(text);
        ngc.disect(n);
        
        Attribute Attribute1 = new Attribute("score");
        FastVector fvClassVal = new FastVector(3);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("neutral");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);
        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(ClassAttribute);
        Instances TrainingSet = new Instances("Rel", fvWekaAttributes, merged.size() + 1);
        TrainingSet.setClassIndex(1);
        
        Instance iExample = new Instance(2);
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), testGramms(ngc.getNGrams(), merged, margins));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "positive");
        TrainingSet.add(iExample);
        
        return TrainingSet.firstInstance();
    }

    public static int testGramms(ArrayList<NGram> targetGramms, ArrayList<NGram> gramms, int[] margins) {
        int result = 0;

        try {
            for (int i = 0; i < targetGramms.size(); i++) {
                if (gramms.get(gramms.indexOf(targetGramms.get(i))).getWeight() > margins[1]) {
                    result++;
                } else if (gramms.get(gramms.indexOf(targetGramms.get(i))).getWeight() < margins[0]) {
                    result--;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return result;
    }
    
    public static void prepare(String filename, int[] margins, int gramms) throws Exception {
        if (margins.length != 2) {
            throw new Exception("Wrong number of margins");
        } else {
            SimpleNGramTests.n=gramms;
            SimpleNGramTests.margins=margins;
            Parser purr = new Parser();
            purr.parse(filename);

            SimpleNGramTests.grammsPos = (new Parser(purr.getPrePositive())).disectToGraphs(SimpleNGramTests.n);
            SimpleNGramTests.grammsNeu = (new Parser(purr.getPreNeutral())).disectToGraphs(SimpleNGramTests.n);
            SimpleNGramTests.grammsNeg = (new Parser(purr.getPreNegative())).disectToGraphs(SimpleNGramTests.n);

            SimpleNGramTests.merged = merge();
        }
    }

    public Classifier train(){
            if(TrainingSet==null){
                Attribute Attribute1 = new Attribute("score");

                FastVector fvClassVal = new FastVector(3);
                fvClassVal.addElement("positive");
                fvClassVal.addElement("neutral");
                fvClassVal.addElement("negative");
                Attribute ClassAttribute = new Attribute("TargetClass", fvClassVal);

                FastVector fvWekaAttributes = new FastVector(2);
                fvWekaAttributes.addElement(Attribute1);
                fvWekaAttributes.addElement(ClassAttribute);

                TrainingSet = new Instances("Rel", fvWekaAttributes, merged.size() + 1);
                TrainingSet.setClassIndex(1);

                System.out.println("Setting Training Instances...");
                for (int i = 0; i < grammsPos.size(); i++) {
                    Instance iExample = new Instance(2);
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), testGramms(grammsPos.get(i).getNGrams(), merged, margins));
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "positive");
                    TrainingSet.add(iExample);
                }
                for (int i = 0; i < grammsNeu.size(); i++) {
                    Instance iExample = new Instance(2);
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), testGramms(grammsNeu.get(i).getNGrams(), merged, margins));
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "neutral");
                    TrainingSet.add(iExample);
                }
                for (int i = 0; i < grammsNeg.size(); i++) {
                    Instance iExample = new Instance(2);
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), testGramms(grammsNeg.get(i).getNGrams(), merged, margins));
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), "negative");
                    TrainingSet.add(iExample);
                }
            }
            System.out.println("Building Classifier for Simple "+algor+" ...");

            Classifier cModel = null;

            if(algor.toLowerCase().trim().equals("c4.5"))
                cModel = (Classifier) new J48();
            else if(algor.toLowerCase().trim().equals("bayes"))
                cModel = (Classifier) new NaiveBayes();
            else if(algor.toLowerCase().trim().equals("svm"))
                cModel = (Classifier) new LibSVM();
            else if(algor.toLowerCase().trim().equals("logistic"))
                cModel = (Classifier) new Logistic();
            else if(algor.toLowerCase().trim().equals("simplelogistic"))
                cModel = (Classifier) new SimpleLogistic();
            else if(algor.toLowerCase().trim().equals("mlp"))
                cModel = (Classifier) new MultilayerPerceptron();
            else if(algor.toLowerCase().trim().equals("bftree"))
                cModel = (Classifier) new BFTree();
            else if(algor.toLowerCase().trim().equals("ft"))
                cModel = (Classifier) new FT();
            
            try {
                cModel.buildClassifier(TrainingSet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            theModel=cModel;
            return cModel;
    }

    private static ArrayList<NGram> merge() {
        ArrayList<NGram> posGs = new ArrayList<NGram>();
        ArrayList<NGram> dirtyPosGs = new ArrayList<NGram>();
        for (int i = 0; i < grammsPos.size(); i++) {
            dirtyPosGs.addAll(grammsPos.get(i).getNGrams());
        }
        for (int i = 0; i < dirtyPosGs.size(); i++) {
            if (dirtyPosGs.get(i).getName().length() == SimpleNGramTests.n) {
                NGram ng = new NGram(dirtyPosGs.get(i).getName(), 1);
                if (posGs.indexOf(ng) < 0) {
                    posGs.add(ng);
                } else {
                    posGs.get(posGs.indexOf(ng)).increase();
                }
            }
        }
        ArrayList<NGram> neuGs = new ArrayList<NGram>();
        ArrayList<NGram> dirtyneuGs = new ArrayList<NGram>();
        for (int i = 0; i < grammsNeu.size(); i++) {
            dirtyneuGs.addAll(grammsNeu.get(i).getNGrams());
        }
        for (int i = 0; i < dirtyneuGs.size(); i++) {
            if (dirtyneuGs.get(i).getName().length() == SimpleNGramTests.n) {
                NGram ng = new NGram(dirtyneuGs.get(i).getName(), 1);
                if (neuGs.indexOf(ng) < 0) {
                    neuGs.add(ng);
                } else {
                    neuGs.get(neuGs.indexOf(ng)).increase();
                }
            }
        }
        ArrayList<NGram> negGs = new ArrayList<NGram>();
        ArrayList<NGram> dirtynegGs = new ArrayList<NGram>();
        for (int i = 0; i < grammsNeg.size(); i++) {
            dirtynegGs.addAll(grammsNeg.get(i).getNGrams());
        }
        for (int i = 0; i < dirtynegGs.size(); i++) {
            if (dirtynegGs.get(i).getName().length() == SimpleNGramTests.n) {
                NGram ng = new NGram(dirtynegGs.get(i).getName(), 1);
                if (negGs.indexOf(ng) < 0) {
                    negGs.add(ng);
                } else {
                    negGs.get(negGs.indexOf(ng)).increase();
                }
            }
        }
        ArrayList<NGram> totalGs = new ArrayList<NGram>();
        ArrayList<NGram> dirtyTotalGs = new ArrayList<NGram>();
        dirtyTotalGs.addAll(posGs);
        for (int i = 0; i < neuGs.size(); i++) {
            neuGs.get(i).zeroPolarity();
            dirtyTotalGs.add(neuGs.get(i));
        }
        for (int i = 0; i < negGs.size(); i++) {
            negGs.get(i).reversePolarity();
            dirtyTotalGs.add(negGs.get(i));
        }
        for (int i = 0; i < dirtyTotalGs.size(); i++) {
            NGram ng = dirtyTotalGs.get(i);
            if (totalGs.indexOf(ng) < 0) {
                totalGs.add(ng);
            } else {
                totalGs.get(totalGs.indexOf(ng)).add(ng.getWeight());
            }

        }

        return totalGs;
    }
}
