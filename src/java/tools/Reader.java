/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Edge;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import main_src.NGramTests;

/**
 *
 * @author Silvestro
 */
public class Reader {
    public static ArrayList<NGramCreator> readGraphs(String fname, int limit, int n) {
        ArrayList<NGramCreator> NGCT = new ArrayList<NGramCreator>();
        try {
            FileInputStream fstream = new FileInputStream(fname);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;
            while ((i < limit) && ((strLine = br.readLine()) != null)) {
                if (strLine.startsWith("W\t")) {
                    strLine = strLine.substring(2);
                    if (!strLine.equals("No Post Title")) {
                        NGramCreator ngc = new NGramCreator();
                        //System.out.println(strLine);
                        ngc.targetString(strLine);
                        ngc.mapEdgesV2(n);
                        NGCT.add(ngc);
                        i++;
                    }
                }
            }
            in.close();
            return NGCT;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public static ArrayList<NGramTriGraphCreator> readTriGraphs(String fname, int limit, int n) {
        ArrayList<NGramTriGraphCreator> NGCT = new ArrayList<NGramTriGraphCreator>();
        try {
            FileInputStream fstream = new FileInputStream(fname);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;
            while ((i < limit) && ((strLine = br.readLine()) != null)) {
                if (strLine.startsWith("W\t")) {
                    strLine = strLine.substring(2);
                    if (!strLine.equals("No Post Title")) {
                        NGramTriGraphCreator ngc = new NGramTriGraphCreator();
                        ngc.targetString(strLine);
                        ngc.mapEdgesV2(n);
                        NGCT.add(ngc);
                        i++;
                    }
                }
            }
            in.close();
            return NGCT;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public static void readStanfordTripleGraphs(String fname, int limit, int n) {
        ArrayList<NGramCreator> NGCT = new ArrayList<NGramCreator>();
        ArrayList<NGramCreator> PGCT = new ArrayList<NGramCreator>();
        ArrayList<NGramCreator> NUGCT = new ArrayList<NGramCreator>();
       try {
            FileInputStream fstream = new FileInputStream(fname);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;
            int j = 0;
            int k = 0;
            while (((i < limit)||(j < limit)) && ((strLine = br.readLine()) != null)) {
                String[] attrs = strLine.split("\",\"");
                attrs[0] = attrs[0].substring(1).trim();
                attrs[1] = attrs[1].trim();
                attrs[2] = attrs[2].trim();
                attrs[3] = attrs[3].trim();
                attrs[4] = attrs[4].trim();
                attrs[5] = attrs[5].substring(0, attrs[5].length() - 1).trim();
                
                NGramCreator ngc = new NGramCreator();
                ngc.targetString(attrs[5]);
                ngc.mapEdgesV2(n);
                if(attrs[0].equals("0") && j<limit) {
                    NGCT.add(ngc);
                    j++;
                }
                else if(attrs[0].equals("4") && i<limit){
                    PGCT.add(ngc);
                    i++;
                }
                else if(attrs[0].equals("2") && k<limit){
                    NUGCT.add(ngc);
                    k++;
                }
                
            }
            NGramTests.ngramsPos=PGCT;
            NGramTests.ngramsNeg=NGCT;
            NGramTests.ngramsNeu=NUGCT;
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void readStanfordDualGraphs(String fname, int limit, int n) {
        ArrayList<NGramCreator> NGCT = new ArrayList<NGramCreator>();
        ArrayList<NGramCreator> PGCT = new ArrayList<NGramCreator>();
        try {
            FileInputStream fstream = new FileInputStream(fname);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;
            int j = 0;
            while (((i < limit)||(j < limit)) && ((strLine = br.readLine()) != null)) {
                String[] attrs = strLine.split("\",\"");
                attrs[0] = attrs[0].substring(1).trim();
                attrs[1] = attrs[1].trim();
                attrs[2] = attrs[2].trim();
                attrs[3] = attrs[3].trim();
                attrs[4] = attrs[4].trim();
                attrs[5] = attrs[5].substring(0, attrs[5].length() - 1).trim();
                
                NGramCreator ngc = new NGramCreator();
                ngc.targetString(attrs[5]);
                ngc.mapEdgesV2(n);
                if(attrs[0].equals("0") && j<limit) {
                    NGCT.add(ngc);
                    j++;
                }
                else if(attrs[0].equals("4") && i<limit){
                    PGCT.add(ngc);
                    i++;
                }
                
            }
            NGramTests.ngramsPos=PGCT;
            NGramTests.ngramsNeg=NGCT;
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static ArrayList<NGramCreator> readGraphs(String fname, int limit, int n, int seed) {
        ArrayList<NGramCreator> NGCT = new ArrayList<NGramCreator>();
        try {
            FileInputStream fstream = new FileInputStream(fname);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int j = 0;
            while ((j < seed) && ((strLine = br.readLine()) != null)) {
                if (strLine.startsWith("W\t")) {
                    strLine = strLine.substring(2);
                    if (!strLine.equals("No Post Title")) {
                        j++;
                    }
                }
            }
            int i = 0;
            while ((i < limit) && ((strLine = br.readLine()) != null)) {
                if (strLine.startsWith("W\t")) {
                    strLine = strLine.substring(2);
                    if (!strLine.equals("No Post Title")) {
                        NGramCreator ngc = new NGramCreator();
                        ngc.targetString(strLine);
                        ngc.mapEdgesV2(n);
                        NGCT.add(ngc);
                        i++;
                    }
                }
            }
            in.close();
            return NGCT;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<ArrayList<Float>> readVector(String filename) {
        ArrayList<ArrayList<Float>> vectors = null;
        ArrayList<Float> vector = null;
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int VectorFlag = 0;
            while ((strLine = br.readLine()) != null) {
                if (strLine.equalsIgnoreCase("<Vectors>")) {
                    vectors = new ArrayList<ArrayList<Float>>();
                }
                if (strLine.equalsIgnoreCase("<Vector>")) {
                    vector = new ArrayList<Float>();
                }
                if (strLine.equalsIgnoreCase("<Argument>")) {
                    VectorFlag = 1;
                }
                if (VectorFlag == 1) {
                    try {
                        vector.add(Float.valueOf(strLine));
                    } catch (Exception ex) {
                    }
                }
                if (strLine.equalsIgnoreCase("</Argument>")) {
                    VectorFlag = 0;
                }
                if (strLine.equalsIgnoreCase("</Vector>")) {
                    vectors.add(vector);
                }
            }
            in.close();
            return vectors;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void writeVector(ArrayList<ArrayList<Float>> vectors, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("<Vectors>");
        for (int i = 0; i < vectors.size(); i++) {
            writer.println("<Vector>");
            for (int j = 0; j < vectors.get(i).size(); j++) {
                writer.println("<Argument>");
                writer.println(vectors.get(i).get(j));
                writer.println("</Argument>");
            }
            writer.println("</Vector>");
        }
        writer.println("</Vectors>");
        writer.close();
    }

    public static void writeMerged(GraphMerger merger, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        for (int i = 0; i < merger.TotalGraph.size(); i++) {
            try{
            writer.println("{" + merger.TotalGraph.get(i).source + ", " + merger.TotalGraph.get(i).target + ", " + merger.TotalGraph.get(i).getWeight() + "}");
            }catch(Exception exe){System.err.println(i+" - "+merger.TotalGraph.get(i));}
        }
        writer.println("graphsMerged="+merger.GraphsMerged);
        writer.close();
    }

    public static GraphMerger readMerged(String filename) throws FileNotFoundException, IOException {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        int merged=0;
        GraphMerger result=new GraphMerger();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        String[] lineParts = new String[3];
        while ((strLine = br.readLine()) != null) {
            try{
            if(!strLine.startsWith("graphsMerged")){
                lineParts = strLine.split(", ");
                lineParts[0] = lineParts[0].substring(1);
                lineParts[2] = lineParts[2].substring(0, lineParts[2].length() - 1);
                edges.add(new Edge(lineParts[0], lineParts[1], Float.valueOf(lineParts[2])));
            }else merged=Integer.valueOf(strLine.substring(strLine.indexOf("=")+1));
            }catch(Exception ex){}
        }
        result.GraphsMerged=merged;
        result.TotalGraph=edges;
        in.close();
        fstream.close();
        br.close();
        return result;
    }
}
