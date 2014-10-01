/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import tools.GlobalVarsStore;
import tools.GraphMerger;
import tools.NGramCreator;
import tools.Parser;
import tools.Reader;
import tools.SimCalculator;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
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
public class CombinationalNGrammGraphs implements Serializable{

    private static ArrayList<ArrayList<Float>> trainVectorsPos;
    private static ArrayList<ArrayList<Float>> trainVectorsNeu;
    private static ArrayList<ArrayList<Float>> trainVectorsNeg;
    private double averageSuccess;
    private double averageSuccess2;
    private double averageSuccess3;
    private double averageSuccess4;
    private double averageSuccess5;
    private double averageSuccess6;
    private double averageSuccess7;
    private double averageSuccess8;
    private double averageSuccess9;
    private double averageSuccess10;
    Instances train;
    String minMaxMode;
    private double[] averagePositive = new double[3];
    private double[] averageNeutral = new double[3];
    private double[] averageNegative = new double[3];
    ArrayList<Classifier> cModels;

    public CombinationalNGrammGraphs() {
        averagePositive[0] = 1.0;
        averagePositive[1] = 0.0;
        averagePositive[2] = 0.0;
        averageNeutral[0] = 0.0;
        averageNeutral[1] = 1.0;
        averageNeutral[2] = 0.0;
        averageNegative[0] = 0.0;
        averageNegative[1] = 0.0;
        averageNegative[2] = 1.0;
    }

    public void train() {
        if(NGramTests.trainVectorsPos==null || NGramTests.trainVectorsNeu==null || NGramTests.trainVectorsNeg==null){
            System.out.println("Reading pos vectors from file.....");
            trainVectorsPos = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsPos.txt");
            System.out.println("Reading neu vectors from file.....");
            trainVectorsNeu = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeu.txt");
            System.out.println("Reading neg vectors from file.....");
            trainVectorsNeg = Reader.readVector(GlobalVarsStore.trainDataDirectory+"trainVectorsNeg.txt");
            /*int countPos = 0;
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
            System.out.println("PosTweets = " + trainVectorsPos.size() + "\nNeuTweets = " + trainVectorsNeu.size() + "\nNegTweets = " + trainVectorsNeg.size());*/
        }else{
            CombinationalNGrammGraphs.trainVectorsPos=NGramTests.trainVectorsPos;
            CombinationalNGrammGraphs.trainVectorsNeu=NGramTests.trainVectorsNeu;
            CombinationalNGrammGraphs.trainVectorsNeg=NGramTests.trainVectorsNeg;
        }
        Instances TotalSet;
        if(NGramTests.TrainingSet==null){
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

            TotalSet = new Instances("Rel", fvWekaAttributes, trainVectorsPos.size() + trainVectorsNeg.size() + 1);
            TotalSet.setClassIndex(attributesSize-1);
            System.out.println("Setting Training Instances...");
            for (int j = 0; j < trainVectorsPos.size(); j++) {
                Instance iExample = new Instance(attributesSize);
                for (int i = 0; i < attributesSize-1; i++) {
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsPos.get(j).get(i));
                }
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
                TotalSet.add(iExample);
            }
            for (int j = 0; j < trainVectorsNeu.size(); j++) {
                Instance iExample = new Instance(attributesSize);
                for (int i = 0; i < attributesSize-1; i++) {
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeu.get(j).get(i));
                }
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "neutral");
                TotalSet.add(iExample);
            }
            for (int j = 0; j < trainVectorsNeg.size(); j++) {
                Instance iExample = new Instance(attributesSize);
                for (int i = 0; i < attributesSize-1; i++) {
                    iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeg.get(j).get(i));
                }
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "negative");
                TotalSet.add(iExample);
            }
        }
        else{
            TotalSet=NGramTests.TrainingSet;
        }
        System.out.println("Building Classifier for Graph Combinations...");
        Classifier modelNaive = (Classifier) new NaiveBayes();
        Classifier modelC45 = (Classifier) new J48();
        Classifier modelSVM = (Classifier) new LibSVM();
        Classifier modelLogistic = (Classifier) new Logistic();
        Classifier modelMlp = (Classifier) new MultilayerPerceptron();
        Classifier modelBFTree = (Classifier) new BFTree();
        Classifier modelFt = (Classifier) new FT();

        try {
            modelNaive.buildClassifier(TotalSet);
            modelC45.buildClassifier(TotalSet);
            modelSVM.buildClassifier(TotalSet);
            modelLogistic.buildClassifier(TotalSet);
            modelMlp.buildClassifier(TotalSet);
            modelBFTree.buildClassifier(TotalSet);
            modelFt.buildClassifier(TotalSet);
            cModels = new ArrayList<Classifier>();
            cModels.add(modelNaive);
            cModels.add(modelC45);
            cModels.add(modelSVM);
            cModels.add(modelLogistic);
            cModels.add(modelMlp);
            cModels.add(modelBFTree);
            cModels.add(modelFt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double[] testSingle(Instance ins, String mode) {
        ArrayList<ArrayList<double[]>> distributsTotal=new ArrayList<ArrayList<double[]>>();
        ArrayList<double[]> distributsCombined=new ArrayList<double[]>();
        
        if(mode.toLowerCase().trim().equals("average")){
                for(int i=0;i<cModels.size();i++){
                    ArrayList<double[]> distributs=new ArrayList<double[]>();
                        try {
                            distributs.add(cModels.get(i).distributionForInstance(ins));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    distributsTotal.add(distributs);
                }
                double[] tempDist=new double[distributsTotal.get(0).get(0).length];
                for(int k=0;k<tempDist.length;k++){
                        tempDist[k]=0.0;
                }
                for (int j = 0; j < cModels.size(); j++) {
                    for(int k=0;k<tempDist.length;k++){
                        tempDist[k]+=distributsTotal.get(j).get(0)[k];
                    }
                }
                for(int k=0;k<tempDist.length;k++){
                     tempDist[k]=tempDist[k]/cModels.size();
                }
                distributsCombined.add(tempDist);
        }else if(mode.toLowerCase().trim().equals("majority")){
            for(int i=0;i<cModels.size();i++){
                ArrayList<double[]> distributs=new ArrayList<double[]>();
                try {
                    distributs.add(cModels.get(i).distributionForInstance(ins));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                distributsTotal.add(distributs);
            }
            int[] tempDecisions=new int[distributsTotal.get(0).get(0).length];
            double[] tempDist=new double[tempDecisions.length];
            for(int k=0;k<tempDecisions.length;k++){
                    tempDecisions[k]=0;
                    tempDist[k]=0.0;
            }
            for (int j = 0; j < cModels.size(); j++) {
                tempDecisions[getMaxIndex(distributsTotal.get(j).get(0))]++;
            }
            tempDist[getMaxIndex(tempDecisions)]=1.0;
            distributsCombined.add(tempDist);
        }
        return distributsCombined.get(0);
    }
    
    public double[] testSingle(String text, int grams, String metric, String mode) {
        ArrayList<ArrayList<double[]>> distributsTotal = new ArrayList<ArrayList<double[]>>();

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

        NGramCreator ngc=new NGramCreator();
        ngc.targetString(text);
        ngc.mapEdges(grams);
        ArrayList<Float> vectors;
        if(!GlobalVarsStore.fast){
        vectors=SimCalculator.calcVector(ngc.getEdges());
        }else{
         vectors=SimCalculator.calcVectorFast(ngc.getEdges());   
        }
        Instance iExample = new Instance(attributesSize);
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), vectors.get(0));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), vectors.get(1));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(2), vectors.get(2));
        if(!GlobalVarsStore.fast){
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(3), vectors.get(3));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(4), vectors.get(4));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(5), vectors.get(5));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(6), vectors.get(6));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(7), vectors.get(7));
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(8), vectors.get(8));
        }
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
        for (int i = 0; i < cModels.size(); i++) {
            ArrayList<double[]> distributs = new ArrayList<double[]>();
            try {
                distributs.add(cModels.get(i).distributionForInstance(iExample));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            distributsTotal.add(distributs);
        }
        ArrayList<Double> distancePosAll = new ArrayList<Double>();
        ArrayList<Double> distanceNeuAll = new ArrayList<Double>();
        ArrayList<Double> distanceNegAll = new ArrayList<Double>();
        ArrayList<Double> distancesPos = new ArrayList<Double>();
        ArrayList<Double> distancesNeu = new ArrayList<Double>();
        ArrayList<Double> distancesNeg = new ArrayList<Double>();
        for (int j = 0; j < cModels.size(); j++) {
            distancesPos.add(distance(distributsTotal.get(j).get(0), averagePositive, metric));
            distancesNeu.add(distance(distributsTotal.get(j).get(0), averageNeutral, metric));
            distancesNeg.add(distance(distributsTotal.get(j).get(0), averageNegative, metric));
        }
        double distancePos = 0;
        double distanceNeu = 0;
        double distanceNeg = 0;
        if (mode.toLowerCase().trim().equals("average")) {
            for (int j = 0; j < cModels.size(); j++) {
                distancePos += distancesPos.get(j);
                distanceNeu += distancesNeu.get(j);
                distanceNeg += distancesNeg.get(j);
            }
            distancePos = distancePos / cModels.size();
            distancePosAll.add(distancePos);
            distanceNeu = distanceNeu / cModels.size();
            distanceNeuAll.add(distanceNeu);
            distanceNeg = distanceNeg / cModels.size();
            distanceNegAll.add(distanceNeg);
        } else if (mode.toLowerCase().trim().equals("majority")) {
            for (int j = 0; j < cModels.size(); j++) {
                int index = 0;
                double tempMax = distancesPos.get(j);
                if (tempMax < distancesNeu.get(j)) {
                    tempMax = distancesNeu.get(j);
                    index = 1;
                }
                if (tempMax < distancesNeg.get(j)) {
                    index = 2;
                }
                if (index == 0) {
                    distancePos++;
                } else if (index == 1) {
                    distanceNeu++;
                } else if (index == 2) {
                    distanceNeg++;
                }
            }
        } else if (mode.toLowerCase().trim().equals("majority2")) {
            for (int j = 0; j < cModels.size(); j++) {
                int index = 0;
                double tempMax = distancesPos.get(j);
                if (tempMax < distancesNeu.get(j)) {
                    tempMax = distancesNeu.get(j);
                    index = 1;
                }
                if (tempMax < distancesNeg.get(j)) {
                    index = 2;
                }
                if (index == 0) {
                    distancePos += distancesPos.get(j);
                } else if (index == 1) {
                    distanceNeu += distancesNeu.get(j);
                } else if (index == 2) {
                    distanceNeg += distancesNeg.get(j);
                }
            }
        }
        double[] minDecisions = new double[3];
        double min = distancePos;
        double[] maxDecisions = new double[3];
        double max = distancePos;
        minDecisions = averagePositive;
        maxDecisions = averagePositive;
        if (distanceNeu < min) {
            min = distanceNeu;
            minDecisions = averageNeutral;
        }
        if (distanceNeg < min) {
            min = distanceNeg;
            minDecisions = averageNegative;
        }
        if (distanceNeu > max) {
            max = distanceNeu;
            maxDecisions = averageNeutral;
        }
        if (distanceNeg > max) {
            max = distanceNeg;
            maxDecisions = averageNegative;
        }
        double[] middleDecisions = averagePositive;
        if (middleDecisions == minDecisions || middleDecisions == maxDecisions) {
            middleDecisions = averageNeutral;
        }
        if (middleDecisions == minDecisions || middleDecisions == maxDecisions) {
            middleDecisions = averageNegative;
        }
        if (metric.toLowerCase().trim().equals("ortho_cos") || metric.toLowerCase().trim().equals("euclidean") || metric.toLowerCase().trim().equals("manhattan") || metric.toLowerCase().trim().equals("chebychev") || metric.toLowerCase().trim().equals("cosine")) {
            return minDecisions;
        } else if (metric.toLowerCase().trim().equals("ortho_sin") || metric.toLowerCase().trim().equals("ortho_tan")) {
            return middleDecisions;
        } else{
            return null;
        }
    }
    
    public double[] testSingle(Instance ins, String metric, String mode) {
        ArrayList<ArrayList<double[]>> distributsTotal = new ArrayList<ArrayList<double[]>>();
        
        for (int i = 0; i < cModels.size(); i++) {
            ArrayList<double[]> distributs = new ArrayList<double[]>();
            try {
                distributs.add(cModels.get(i).distributionForInstance(ins));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            distributsTotal.add(distributs);
        }
        ArrayList<Double> distancePosAll = new ArrayList<Double>();
        ArrayList<Double> distanceNeuAll = new ArrayList<Double>();
        ArrayList<Double> distanceNegAll = new ArrayList<Double>();
        ArrayList<Double> distancesPos = new ArrayList<Double>();
        ArrayList<Double> distancesNeu = new ArrayList<Double>();
        ArrayList<Double> distancesNeg = new ArrayList<Double>();
        for (int j = 0; j < cModels.size(); j++) {
            distancesPos.add(distance(distributsTotal.get(j).get(0), averagePositive, metric));
            distancesNeu.add(distance(distributsTotal.get(j).get(0), averageNeutral, metric));
            distancesNeg.add(distance(distributsTotal.get(j).get(0), averageNegative, metric));
        }
        double distancePos = 0;
        double distanceNeu = 0;
        double distanceNeg = 0;
        if (mode.toLowerCase().trim().equals("average")) {
            for (int j = 0; j < cModels.size(); j++) {
                distancePos += distancesPos.get(j);
                distanceNeu += distancesNeu.get(j);
                distanceNeg += distancesNeg.get(j);
            }
            distancePos = distancePos / cModels.size();
            distancePosAll.add(distancePos);
            distanceNeu = distanceNeu / cModels.size();
            distanceNeuAll.add(distanceNeu);
            distanceNeg = distanceNeg / cModels.size();
            distanceNegAll.add(distanceNeg);
        } else if (mode.toLowerCase().trim().equals("majority")) {
            for (int j = 0; j < cModels.size(); j++) {
                int index = 0;
                double tempMax = distancesPos.get(j);
                if (tempMax < distancesNeu.get(j)) {
                    tempMax = distancesNeu.get(j);
                    index = 1;
                }
                if (tempMax < distancesNeg.get(j)) {
                    index = 2;
                }
                if (index == 0) {
                    distancePos++;
                } else if (index == 1) {
                    distanceNeu++;
                } else if (index == 2) {
                    distanceNeg++;
                }
            }
        } else if (mode.toLowerCase().trim().equals("majority2")) {
            for (int j = 0; j < cModels.size(); j++) {
                int index = 0;
                double tempMax = distancesPos.get(j);
                if (tempMax < distancesNeu.get(j)) {
                    tempMax = distancesNeu.get(j);
                    index = 1;
                }
                if (tempMax < distancesNeg.get(j)) {
                    index = 2;
                }
                if (index == 0) {
                    distancePos += distancesPos.get(j);
                } else if (index == 1) {
                    distanceNeu += distancesNeu.get(j);
                } else if (index == 2) {
                    distanceNeg += distancesNeg.get(j);
                }
            }
        }
        double[] minDecisions = new double[3];
        double min = distancePos;
        double[] maxDecisions = new double[3];
        double max = distancePos;
        minDecisions = averagePositive;
        maxDecisions = averagePositive;
        if (distanceNeu < min) {
            min = distanceNeu;
            minDecisions = averageNeutral;
        }
        if (distanceNeg < min) {
            min = distanceNeg;
            minDecisions = averageNegative;
        }
        if (distanceNeu > max) {
            max = distanceNeu;
            maxDecisions = averageNeutral;
        }
        if (distanceNeg > max) {
            max = distanceNeg;
            maxDecisions = averageNegative;
        }
        double[] middleDecisions = averagePositive;
        if (middleDecisions == minDecisions || middleDecisions == maxDecisions) {
            middleDecisions = averageNeutral;
        }
        if (middleDecisions == minDecisions || middleDecisions == maxDecisions) {
            middleDecisions = averageNegative;
        }
        if (metric.toLowerCase().trim().equals("ortho_cos") || metric.toLowerCase().trim().equals("euclidean") || metric.toLowerCase().trim().equals("manhattan") || metric.toLowerCase().trim().equals("chebychev") || metric.toLowerCase().trim().equals("cosine")) {
            return minDecisions;
        } else if (metric.toLowerCase().trim().equals("ortho_sin") || metric.toLowerCase().trim().equals("ortho_tan")) {
            return middleDecisions;
        } else{
            return null;
        }
    }

    public void NGrammGraphs() {

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

        Instances TotalSet = new Instances("Rel", fvWekaAttributes, trainVectorsPos.size() + trainVectorsNeg.size() + 1);
        TotalSet.setClassIndex(attributesSize-1);
        System.out.println("Setting Training Instances...");
        for (int j = 0; j < trainVectorsPos.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsPos.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "positive");
            TotalSet.add(iExample);
        }
        for (int j = 0; j < trainVectorsNeu.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeu.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "neutral");
            TotalSet.add(iExample);
        }
        for (int j = 0; j < trainVectorsNeg.size(); j++) {
            Instance iExample = new Instance(attributesSize);
            for (int i = 0; i < attributesSize-1; i++) {
                iExample.setValue((Attribute) fvWekaAttributes.elementAt(i), trainVectorsNeg.get(j).get(i));
            }
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(attributesSize-1), "negative");
            TotalSet.add(iExample);
        }
        System.out.println(TotalSet.toSummaryString());
        Random rand = new Random((new Date()).getTime());   // create seeded number generator
        Instances randData = new Instances(TotalSet);   // create copy of original data
        randData.randomize(rand);         // randomize data with number generator
        int folds = 10;
        ArrayList<Classifier> cModels = null;
        averageSuccess = 0;
        averageSuccess2 = 0;
        averageSuccess3 = 0;
        averageSuccess4 = 0;
        averageSuccess5 = 0;
        averageSuccess6 = 0;
        averageSuccess7 = 0;
        averageSuccess8 = 0;
        averageSuccess9 = 0;
        averageSuccess10 = 0;
        for (int n = 0; n < folds; n++) {
            train = randData.trainCV(folds, n);
            Instances test = randData.testCV(folds, n);

            Classifier modelNaive = (Classifier) new NaiveBayes();
            Classifier modelC45 = (Classifier) new J48();
            Classifier modelSVM = (Classifier) new LibSVM();
            Classifier modelLogistic = (Classifier) new Logistic();
            Classifier modelMlp = (Classifier) new MultilayerPerceptron();
            Classifier modelBFTree = (Classifier) new BFTree();
            Classifier modelFt = (Classifier) new FT();

            try {
                modelNaive.buildClassifier(train);
                modelC45.buildClassifier(train);
                modelSVM.buildClassifier(train);
                modelLogistic.buildClassifier(train);
                modelMlp.buildClassifier(train);
                modelBFTree.buildClassifier(train);
                modelFt.buildClassifier(train);
                cModels = new ArrayList<Classifier>();
                cModels.add(modelNaive);
                cModels.add(modelC45);
                cModels.add(modelSVM);
                cModels.add(modelLogistic);
                cModels.add(modelMlp);
                cModels.add(modelBFTree);
                cModels.add(modelFt);
                resetCentroids();
                NGrammGraphDecisionAverage(cModels, test);
                NGrammGraphDecisionMajority(cModels, test);
                NGrammGraphDecisionDistance(cModels, test, "euclidean", "average");
                NGrammGraphDecisionDistance(cModels, test, "manhattan", "average");
                NGrammGraphDecisionDistance(cModels, test, "cosine", "average");
                NGrammGraphDecisionDistance(cModels, test, "chebychev", "average");
                NGrammGraphDecisionDistance(cModels, test, "ortho_sin", "average");
                setCentroids(cModels);
                NGrammGraphDecisionDistance(cModels, test, "ortho_cos", "average");
                resetCentroids();
                NGrammGraphDecisionDistance(cModels, test, "ortho_tan", "average");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        averageSuccess = ((double) Math.round((averageSuccess / folds) * 100)) / 100;
        averageSuccess2 = ((double) Math.round((averageSuccess2 / folds) * 100)) / 100;
        averageSuccess3 = ((double) Math.round((averageSuccess3 / folds) * 100)) / 100;
        averageSuccess4 = ((double) Math.round((averageSuccess4 / folds) * 100)) / 100;
        averageSuccess5 = ((double) Math.round((averageSuccess5 / folds) * 100)) / 100;
        averageSuccess6 = ((double) Math.round((averageSuccess6 / folds) * 100)) / 100;
        averageSuccess7 = ((double) Math.round((averageSuccess7 / folds) * 100)) / 100;
        averageSuccess8 = ((double) Math.round((averageSuccess8 / folds) * 100)) / 100;
        averageSuccess9 = ((double) Math.round((averageSuccess9 / folds) * 100)) / 100;
        averageSuccess10 = ((double) Math.round((averageSuccess10 / folds) * 100)) / 100;
        Main.result += "\n-------Correctly classified Average: " + (averageSuccess) + "%";
        Main.result += "\n------Correctly classified Majority: " + (averageSuccess2) + "%";
        Main.result += "\n-----Correctly classified Euclidean: " + (averageSuccess3) + "%";
        Main.result += "\n-----Correctly classified Manhattan: " + (averageSuccess4) + "%";
        Main.result += "\n--------Correctly classified Cosine: " + (averageSuccess5) + "%";
        Main.result += "\n-----Correctly classified Chebychev: " + (averageSuccess6) + "%";
        Main.result += "\n-Correctly classified Orthodromic S: " + (averageSuccess7) + "%";
        Main.result += "\n-Correctly classified Orthodromic C: " + (averageSuccess8) + "%";
        Main.result += "\n-Correctly classified Orthodromic T: " + (averageSuccess9) + "%";
        Main.result += "\n----Correctly classified Experiment: " + (averageSuccess10) + "%";

    }

    private void NGrammGraphDecisionAverage(ArrayList<Classifier> cModels, Instances test) {
        ArrayList<ArrayList<double[]>> distributsTotal = new ArrayList<ArrayList<double[]>>();
        ArrayList<double[]> distributsCombined = new ArrayList<double[]>();

        for (int i = 0; i < cModels.size(); i++) {
            ArrayList<double[]> distributs = new ArrayList<double[]>();
            for (int j = 0; j < test.numInstances(); j++) {
                try {
                    distributs.add(cModels.get(i).distributionForInstance(test.instance(j)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            distributsTotal.add(distributs);
        }
        for (int i = 0; i < test.numInstances(); i++) {
            double[] tempDist = new double[distributsTotal.get(0).get(i).length];
            for (int k = 0; k < tempDist.length; k++) {
                tempDist[k] = 0.0;
            }
            for (int j = 0; j < cModels.size(); j++) {
                for (int k = 0; k < tempDist.length; k++) {
                    tempDist[k] += distributsTotal.get(j).get(i)[k];
                }
            }
            for (int k = 0; k < tempDist.length; k++) {
                tempDist[k] = tempDist[k] / cModels.size();
            }
            distributsCombined.add(tempDist);
        }
        int correct = 0;
        int total = test.numInstances();
        for (int i = 0; i < test.numInstances(); i++) {
            if (getMaxIndex(distributsCombined.get(i)) == ((int) test.instance(i).classValue())) {
                correct++;
            }
        }
        averageSuccess += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        /*for(int i=0;i<test.numInstances();i++){
         System.out.println("Total1: "+distributsTotal.get(0).get(i)[0]+"-"+distributsTotal.get(0).get(i)[1]+"-"+distributsTotal.get(0).get(i)[2]);
         System.out.println("Total2: "+distributsTotal.get(1).get(i)[0]+"-"+distributsTotal.get(1).get(i)[1]+"-"+distributsTotal.get(1).get(i)[2]);
         System.out.println("Total3: "+distributsTotal.get(2).get(i)[0]+"-"+distributsTotal.get(2).get(i)[1]+"-"+distributsTotal.get(2).get(i)[2]);
         System.out.println("Average: "+distributsCombined.get(i)[0]+"-"+distributsCombined.get(i)[1]+"-"+distributsCombined.get(i)[2]);
         System.out.println("TotalTotal1"+(distributsTotal.get(0).get(i)[0]+distributsTotal.get(0).get(i)[1]+distributsTotal.get(0).get(i)[2]));
         System.out.println("TotalTotal2"+(distributsTotal.get(1).get(i)[0]+distributsTotal.get(1).get(i)[1]+distributsTotal.get(1).get(i)[2]));
         System.out.println("TotalTotal3"+(distributsTotal.get(2).get(i)[0]+distributsTotal.get(2).get(i)[1]+distributsTotal.get(2).get(i)[2]));
         System.out.println("TotalAverage: "+(distributsCombined.get(i)[0]+distributsCombined.get(i)[1]+distributsCombined.get(i)[2]));
         }*/
    }

    private void NGrammGraphDecisionMajority(ArrayList<Classifier> cModels, Instances test) {
        ArrayList<ArrayList<double[]>> distributsTotal = new ArrayList<ArrayList<double[]>>();
        ArrayList<double[]> distributsCombined = new ArrayList<double[]>();

        for (int i = 0; i < cModels.size(); i++) {
            ArrayList<double[]> distributs = new ArrayList<double[]>();
            for (int j = 0; j < test.numInstances(); j++) {
                try {
                    distributs.add(cModels.get(i).distributionForInstance(test.instance(j)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            distributsTotal.add(distributs);
        }
        int[] tempDecisions = new int[distributsTotal.get(0).get(0).length];
        for (int i = 0; i < test.numInstances(); i++) {
            double[] tempDist = new double[tempDecisions.length];
            for (int k = 0; k < tempDecisions.length; k++) {
                tempDecisions[k] = 0;
                tempDist[k] = 0.0;
            }
            for (int j = 0; j < cModels.size(); j++) {
                tempDecisions[getMaxIndex(distributsTotal.get(j).get(i))]++;
            }
            tempDist[getMaxIndex(tempDecisions)] = 1.0;
            distributsCombined.add(tempDist);
        }
        int correct = 0;
        int total = test.numInstances();
        for (int i = 0; i < test.numInstances(); i++) {
            if (getMaxIndex(distributsCombined.get(i)) == ((int) test.instance(i).classValue())) {
                correct++;
            }
        }
        averageSuccess2 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
    }

    private void NGrammGraphDecisionDistance(ArrayList<Classifier> cModels, Instances test, String metric, String mode) {
        ArrayList<ArrayList<double[]>> distributsTotal = new ArrayList<ArrayList<double[]>>();
        ArrayList<double[]> distributsCombined = new ArrayList<double[]>();

        for (int i = 0; i < cModels.size(); i++) {
            ArrayList<double[]> distributs = new ArrayList<double[]>();
            for (int j = 0; j < test.numInstances(); j++) {
                try {
                    distributs.add(cModels.get(i).distributionForInstance(test.instance(j)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            distributsTotal.add(distributs);
        }
        ArrayList<Double> distancePosAll = new ArrayList<Double>();
        ArrayList<Double> distanceNeuAll = new ArrayList<Double>();
        ArrayList<Double> distanceNegAll = new ArrayList<Double>();
        for (int i = 0; i < test.numInstances(); i++) {
            ArrayList<Double> distancesPos = new ArrayList<Double>();
            ArrayList<Double> distancesNeu = new ArrayList<Double>();
            ArrayList<Double> distancesNeg = new ArrayList<Double>();
            for (int j = 0; j < cModels.size(); j++) {
                distancesPos.add(distance(distributsTotal.get(j).get(i), averagePositive, metric));
                distancesNeu.add(distance(distributsTotal.get(j).get(i), averageNeutral, metric));
                distancesNeg.add(distance(distributsTotal.get(j).get(i), averageNegative, metric));
            }
            double distancePos = 0;
            double distanceNeu = 0;
            double distanceNeg = 0;
            if (mode.toLowerCase().trim().equals("average")) {
                for (int j = 0; j < cModels.size(); j++) {
                    distancePos += distancesPos.get(j);
                    distanceNeu += distancesNeu.get(j);
                    distanceNeg += distancesNeg.get(j);
                }
                distancePos = distancePos / cModels.size();
                distancePosAll.add(distancePos);
                distanceNeu = distanceNeu / cModels.size();
                distanceNeuAll.add(distanceNeu);
                distanceNeg = distanceNeg / cModels.size();
                distanceNegAll.add(distanceNeg);
            } else if (mode.toLowerCase().trim().equals("majority")) {
                for (int j = 0; j < cModels.size(); j++) {
                    int index = 0;
                    double tempMax = distancesPos.get(j);
                    if (tempMax < distancesNeu.get(j)) {
                        tempMax = distancesNeu.get(j);
                        index = 1;
                    }
                    if (tempMax < distancesNeg.get(j)) {
                        index = 2;
                    }
                    if (index == 0) {
                        distancePos++;
                    } else if (index == 1) {
                        distanceNeu++;
                    } else if (index == 2) {
                        distanceNeg++;
                    }
                }
            } else if (mode.toLowerCase().trim().equals("majority2")) {
                for (int j = 0; j < cModels.size(); j++) {
                    int index = 0;
                    double tempMax = distancesPos.get(j);
                    if (tempMax < distancesNeu.get(j)) {
                        tempMax = distancesNeu.get(j);
                        index = 1;
                    }
                    if (tempMax < distancesNeg.get(j)) {
                        index = 2;
                    }
                    if (index == 0) {
                        distancePos += distancesPos.get(j);
                    } else if (index == 1) {
                        distanceNeu += distancesNeu.get(j);
                    } else if (index == 2) {
                        distanceNeg += distancesNeg.get(j);
                    }
                }
            }
            double[] minDecisions = new double[3];
            double min = distancePos;
            double[] maxDecisions = new double[3];
            double max = distancePos;
            minDecisions = averagePositive;
            maxDecisions = averagePositive;
            if (distanceNeu < min) {
                min = distanceNeu;
                minDecisions = averageNeutral;
            }
            if (distanceNeg < min) {
                min = distanceNeg;
                minDecisions = averageNegative;
            }
            if (distanceNeu > max) {
                max = distanceNeu;
                maxDecisions = averageNeutral;
            }
            if (distanceNeg > max) {
                max = distanceNeg;
                maxDecisions = averageNegative;
            }
            double[] middleDecisions = averagePositive;
            if (middleDecisions == minDecisions || middleDecisions == maxDecisions) {
                middleDecisions = averageNeutral;
            }
            if (middleDecisions == minDecisions || middleDecisions == maxDecisions) {
                middleDecisions = averageNegative;
            }
            /*if(minMaxMode.equals("min"))
             distributsCombined.add(minDecisions);
             else if(minMaxMode.equals("max"))
             distributsCombined.add(maxDecisions);
             else if(minMaxMode.equals("mid"))
             distributsCombined.add(middleDecisions);*/
            if (metric.toLowerCase().trim().equals("ortho_cos") || metric.toLowerCase().trim().equals("euclidean") || metric.toLowerCase().trim().equals("manhattan") || metric.toLowerCase().trim().equals("chebychev") || metric.toLowerCase().trim().equals("cosine")) {
                distributsCombined.add(minDecisions);
            } else if (metric.toLowerCase().trim().equals("ortho_sin") || metric.toLowerCase().trim().equals("ortho_tan")) {
                distributsCombined.add(middleDecisions);
            }
        }
        int correct = 0;
        int total = test.numInstances();
        for (int i = 0; i < test.numInstances(); i++) {
            //System.out.println("Pos:"+distancePosAll.get(i));
            //System.out.println("Neu:"+distanceNeuAll.get(i));
            //System.out.println("Neg:"+distanceNegAll.get(i));
            //System.out.println(test.classAttribute().value((int)test.instance(i).classValue())+" - "+(int)test.instance(i).classValue());
            if (getMaxIndex(distributsCombined.get(i)) == ((int) test.instance(i).classValue())) {
                correct++;
            }
        }
        if (metric.toLowerCase().trim().equals("euclidean") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess3 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (metric.toLowerCase().trim().equals("manhattan") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess4 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (metric.toLowerCase().trim().equals("cosine") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess5 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (metric.toLowerCase().trim().equals("chebychev") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess6 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (metric.toLowerCase().trim().equals("ortho_sin") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess7 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (metric.toLowerCase().trim().equals("ortho_cos") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess8 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (metric.toLowerCase().trim().equals("ortho_tan") && mode.toLowerCase().trim().equals("average")) {
            averageSuccess9 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        } else if (!mode.toLowerCase().trim().equals("average")) {
            averageSuccess10 += (((double) Math.round(((double) correct / total) * 10000)) / 100);
        }
    }

    private void setCentroids(ArrayList<Classifier> cModels) {
        ArrayList<ArrayList<double[]>> distributsTotalTrain = new ArrayList<ArrayList<double[]>>();

        for (int i = 0; i < cModels.size(); i++) {
            ArrayList<double[]> distributsTrain = new ArrayList<double[]>();
            for (int j = 0; j < train.numInstances(); j++) {
                try {
                    distributsTrain.add(cModels.get(i).distributionForInstance(train.instance(j)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            distributsTotalTrain.add(distributsTrain);
        }
        ArrayList<double[]> distTrainPos = new ArrayList<double[]>();
        ArrayList<double[]> distTrainNeu = new ArrayList<double[]>();
        ArrayList<double[]> distTrainNeg = new ArrayList<double[]>();
        for (int i = 0; i < distributsTotalTrain.get(0).size(); i++) {
            double[] temp = new double[distributsTotalTrain.get(0).get(i).length];
            for (int j = 0; j < distributsTotalTrain.get(0).get(i).length; j++) {
                temp[j] = (distributsTotalTrain.get(0).get(i)[j] + distributsTotalTrain.get(1).get(i)[j] + distributsTotalTrain.get(2).get(i)[j]) / 3;
            }
            if (((int) train.instance(i).classValue()) == 0) {
                distTrainPos.add(temp);
            } else if (((int) train.instance(i).classValue()) == 1) {
                distTrainNeu.add(temp);
            } else if (((int) train.instance(i).classValue()) == 2) {
                distTrainNeg.add(temp);
            }
        }
        averagePositive = findCenter(distTrainPos);
        averageNeutral = findCenter(distTrainNeu);
        averageNegative = findCenter(distTrainNeg);
    }

    private int getMaxIndex(double[] nums) {
        double max = nums[0];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
                index = i;
            }
        }
        return index;
    }

    private int getMaxIndex(int[] nums) {
        double max = nums[0];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
                index = i;
            }
        }
        return index;
    }

    private double distance(double[] table1, double[] table2, String metric) {
        if (metric.toLowerCase().trim().equals("euclidean")) {
            return distanceEuclidian(table1, table2);
        } else if (metric.toLowerCase().trim().equals("manhattan")) {
            return distanceManhattan(table1, table2);
        } else if (metric.toLowerCase().trim().equals("cosine")) {
            return distanceCosine(table1, table2);
        } else if (metric.toLowerCase().trim().equals("chebychev")) {
            return distanceChebychev(table1, table2);
        } else if (metric.toLowerCase().trim().equals("ortho_cos")) {
            double res = distanceOrthodromic(table1, table2, "cos");
            return res;
        } else if (metric.toLowerCase().trim().equals("ortho_sin")) {
            double res = distanceOrthodromic(table1, table2, "sin");
            return res;
        } else if (metric.toLowerCase().trim().equals("ortho_tan")) {
            double res = distanceOrthodromic(table1, table2, "tan");
            return res;
        } else {
            return -1;
        }
    }

    private double distanceEuclidian(double[] table1, double[] table2) {

        double distance = 0;
        for (int i = 0; i < table1.length && i < table2.length; i++) {
            distance += Math.pow((table2[i] - table1[i]), 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

    private double distanceChebychev(double[] table1, double[] table2) {
        double distance = -1;
        for (int i = 0; i < table1.length && i < table2.length; i++) {
            double dis = Math.abs(table2[i] - table1[i]);
            if (distance < dis) {
                distance = dis;
            }
        }
        return distance;
    }

    private double distanceManhattan(double[] table1, double[] table2) {

        double distance = 0;
        for (int i = 0; i < table1.length && i < table2.length; i++) {
            distance += Math.abs(table2[i] - table1[i]);
        }
        return distance;
    }

    private double distanceCosine(double[] table1, double[] table2) {
        double distance = 0;
        for (int i = 0; i < table1.length && i < table2.length; i++) {
            double semiDistance1 = 0;
            double semiDistance2 = 0;
            for (int j = 0; j < table1.length; j++) {
                semiDistance1 += Math.pow(table1[j], 2);
            }
            semiDistance1 = Math.sqrt(semiDistance1);
            for (int j = 0; j < table2.length; j++) {
                semiDistance2 += Math.pow(table2[j], 2);
            }
            semiDistance2 = Math.sqrt(semiDistance2);
            semiDistance1 *= semiDistance2;
            distance += (table1[i] * table2[i]) / semiDistance1;
        }
        return distance * (-1);
    }

    private double distanceOrthodromic(double[] table1, double[] table2, String mode) {
        double distance = 0;
        double dotProduct = 0;
        double crossProduct = 0;
        if (mode.toLowerCase().trim().equals("cos")) {
            for (int i = 0; i < table1.length && i < table2.length; i++) {
                dotProduct += (table1[i] * table2[i]) / (table1[i] + table2[i]);
            }
            distance = Math.acos(dotProduct);
        } else if (mode.toLowerCase().trim().equals("sin")) {
            crossProduct = cross(combine(table1, table2));
            distance = Math.asin(crossProduct);
        } else if (mode.toLowerCase().trim().equals("tan")) {
            crossProduct = cross(combine(table1, table2));
            for (int i = 0; i < table1.length && i < table2.length; i++) {
                dotProduct += table1[i] * table2[i];
            }
            distance = Math.atan(crossProduct / dotProduct);
        }
        if (mode.toLowerCase().trim().equals("cos")) {
            int i = 0;
        }
        return distance;
    }

    private double[][] combine(double[] table1, double[] table2) {
        double[][] result = new double[table1.length][2];
        if (table1.length != table2.length) {
            return null;
        } else {
            for (int i = 0; i < table1.length; i++) {
                result[i][0] = table1[i];
                result[i][1] = table2[i];
            }
            return result;
        }
    }

    private double cross(double[][] table) {
        double result = 0;
        double[][] semi = null;
        table = makeSquare(table);
        if (table.length == 1) {
            return table[0][0];
        } else if (table == null) {
            return 0;
        } else {
            for (int k = 0; k < table.length; k++) {
                for (int p = 0; p < table.length; p++) {
                    int semiI = 0;
                    semi = new double[table.length - 1][table.length - 1];
                    for (int i = 0; i < table.length; i++) {
                        int semiJ = 0;
                        if (i != k) {
                            for (int j = 0; j < table.length; j++) {
                                if (j != p) {
                                    semi[semiI][semiJ] = table[i][j];
                                    semiJ++;
                                }
                            }
                            semiI++;
                        }
                    }
                    if (k % 2 == 0) {
                        result += table[k][0] * cross(semi);
                    } else {
                        result -= table[k][0] * cross(semi);
                    }
                }
            }
        }
        return result;
    }

    private double[][] makeSquare(double[][] table) {
        double[][] result = null;

        if (table.length == table[0].length) {
            result = table;
        } else if (table.length > table[0].length) {
            result = new double[table.length][table.length];
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table.length; j++) {
                    if (j < table[i].length) {
                        result[i][j] = table[i][j];
                    } else {
                        result[i][j] = 1;
                    }
                }
            }
        } else if (table.length < table[0].length) {
            result = new double[table[0].length][table[0].length];
            for (int i = 0; i < table[0].length; i++) {
                for (int j = 0; j < table[0].length; j++) {
                    if (i < table.length) {
                        result[i][j] = table[i][j];
                    } else {
                        result[i][j] = 1;
                    }
                }
            }
        }
        return result;
    }

    private double[] findCenter(ArrayList<double[]> collection) {
        double[] result = new double[collection.get(0).length];

        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }
        for (int i = 0; i < collection.size(); i++) {
            for (int j = 0; j < result.length; j++) {
                result[j] += collection.get(i)[j];
            }
        }
        for (int j = 0; j < result.length; j++) {
            result[j] /= collection.size();
        }
        return result;
    }

    private void resetCentroids() {
        averagePositive[0] = 1.0;
        averagePositive[1] = 0.0;
        averagePositive[2] = 0.0;
        averageNeutral[0] = 0.0;
        averageNeutral[1] = 1.0;
        averageNeutral[2] = 0.0;
        averageNegative[0] = 0.0;
        averageNegative[1] = 0.0;
        averageNegative[2] = 1.0;
    }
}
