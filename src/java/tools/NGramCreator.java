/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Edge;
import Entities.NGram;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Silvestro
 */
public class NGramCreator {

    String filename;
    String target;
    int n;
    ArrayList<NGram> NGrams;
    ArrayList<Edge> Edges;

    public NGramCreator() {
        filename = "";
        target = "";
    }

    public ArrayList<NGram> getNGrams() {
        return NGrams;
    }
    

    public NGramCreator(String filename) {
        this.filename = filename;
        target = "";
    }

    public void targetString(String target) {
        filename = "";
        this.target = target;
    }

    public ArrayList<Edge> getEdges(){
        return Edges;
    }
    
    public void mapEdges(int n) {
        Edges = new ArrayList<Edge>();
        this.n = n;
        String prevGram = null;
        ArrayList<Edge> dirtyEdges = new ArrayList<Edge>();
        Edge ed;
        if (this.filename.length() > 3) {
            try {
                FileInputStream fstream = new FileInputStream(this.filename);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    if (strLine.length() <= n) {
                        ed = new Edge(strLine, prevGram);
                        dirtyEdges.add(ed);
                        prevGram = strLine;
                    } else {
                        for (int i = 0; i + n <= strLine.length(); i++) {
                            ed = new Edge(strLine.substring(i, i + n), prevGram);
                            dirtyEdges.add(ed);
                            prevGram = strLine.substring(i, i + n);
                        }
                    }
                }
                for (int i = 0; i < dirtyEdges.size(); i++) {
                    if (dirtyEdges.get(i).source.length() == this.n) {
                        if (Edges.indexOf(dirtyEdges.get(i)) < 0) {
                            Edges.add(dirtyEdges.get(i));
                        } else {
                            Edges.get(Edges.indexOf(dirtyEdges.get(i))).increase();
                        }
                    }
                }
                in.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        if (!this.target.equals("")) {
            if (target.length() <= n) {
                ed = new Edge(target, prevGram);
                dirtyEdges.add(ed);
                prevGram = target;
            } else {
                for (int i = 0; i + n <= target.length(); i++) {
                    ed = new Edge(target.substring(i, i + n), prevGram);
                    dirtyEdges.add(ed);
                    prevGram = target.substring(i, i + n);
                }
            }
            for (int i = 0; i < dirtyEdges.size(); i++) {
                if (dirtyEdges.get(i).source.length() == this.n) {
                    if (Edges.indexOf(dirtyEdges.get(i)) < 0) {
                        Edges.add(dirtyEdges.get(i));
                    } else {
                        Edges.get(Edges.indexOf(dirtyEdges.get(i))).increase();
                    }
                }
            }
        }
    }

    public void mapEdgesV2(int n) {
        ArrayList<String> dirtyNGrams = this.disect(n);
        Edges = new ArrayList<Edge>();
        this.n = n;
        ArrayList<Edge> dirtyEdges = new ArrayList<Edge>();
        if (dirtyNGrams.size() > 0) {
            for (int i = 1; i < dirtyNGrams.size(); i++) {
                for (int k = i - 1; (k > i - n) && (k>=0); k--) {
                    dirtyEdges.add(new Edge(dirtyNGrams.get(i), dirtyNGrams.get(k)));
                }
            }
        }
        int o=0;
        int d=0;
        for (int i = 0; i < dirtyEdges.size(); i++) {
            if (dirtyEdges.get(i).source.length() == this.n) {
                if (Edges.indexOf(dirtyEdges.get(i)) < 0) {
                    d++;
                    Edges.add(dirtyEdges.get(i));
                } else {
                    o++;
                    Edges.get(Edges.indexOf(dirtyEdges.get(i))).increase();
                }
            }
        }
        //System.out.println(d+"/"+dirtyEdges.size()+" - "+o+"/"+dirtyEdges.size());
    }

    public ArrayList<String> disect(int n) {
        NGrams = new ArrayList<NGram>();
        this.n = n;
        ArrayList<String> dirtyNGrams = new ArrayList<String>();
        if (this.filename.length() > 3) {
            try {
                FileInputStream fstream = new FileInputStream(this.filename);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    if (strLine.length() <= n) {
                        dirtyNGrams.add(strLine);
                    } else {
                        for (int i = 0; i + n <= strLine.length(); i++) {
                            dirtyNGrams.add(strLine.substring(i, i + n));
                        }
                    }
                }
                for (int i = 0; i < dirtyNGrams.size(); i++) {
                    if (dirtyNGrams.get(i).length() == this.n) {
                        NGram ng = new NGram(dirtyNGrams.get(i), 1);
                        if (NGrams.indexOf(ng) < 0) {
                            NGrams.add(ng);
                        } else {
                            NGrams.get(NGrams.indexOf(ng)).increase();
                        }
                    }
                }
                in.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        if (!this.target.equals("")) {
            if (this.target.length() <= n) {
                dirtyNGrams.add(this.target);
            } else {
                for (int i = 0; i + n <= this.target.length(); i++) {
                    dirtyNGrams.add(this.target.substring(i, i + n));
                }
            }
            for (int i = 0; i < dirtyNGrams.size(); i++) {
                if (dirtyNGrams.get(i).length() == this.n) {
                    NGram ng = new NGram(dirtyNGrams.get(i), 1);
                    if (NGrams.indexOf(ng) < 0) {
                        NGrams.add(ng);
                    } else {
                        NGrams.get(NGrams.indexOf(ng)).increase();
                    }
                }
            }
        }
        return dirtyNGrams;
    }

    public void printNGrams() {
        for (int i = 0; i < NGrams.size(); i++) {
            System.out.println(NGrams.get(i));
        }
    }

    public void printEdges() {
        for (int i = 0; i < Edges.size(); i++) {
            System.out.println(Edges.get(i));
        }
    }
}
