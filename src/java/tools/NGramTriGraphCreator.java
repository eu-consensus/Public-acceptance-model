/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.TriEdge;
import Entities.NGram;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ViP
 */
public class NGramTriGraphCreator {
    
    String filename;
    String target;
    int n;
    ArrayList<NGram> NGrams;
    ArrayList<TriEdge> Edges;

    public NGramTriGraphCreator() {
        filename = "";
        target = "";
    }

    public ArrayList<NGram> getNGrams() {
        return NGrams;
    }
    

    public NGramTriGraphCreator(String filename) {
        this.filename = filename;
        target = "";
    }

    public void targetString(String target) {
        filename = "";
        this.target = target;
    }

    public void mapEdges(int n) {
        Edges = new ArrayList<TriEdge>();
        this.n = n;
        String left = null;
        String source = null;
        ArrayList<TriEdge> dirtyEdges = new ArrayList<TriEdge>();
        TriEdge ed;
        if (this.filename.length() > 3) {
            try {
                FileInputStream fstream = new FileInputStream(this.filename);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    left = null;
                    source = null;
                    if (strLine.length() <= n) {
                        ed = new TriEdge(null, strLine, null);
                        dirtyEdges.add(ed);
                    } else {
                        for (int i = 0; i + n <= strLine.length(); i++) {
                            String temp=strLine.substring(i, i + n);
                            if(source!=null){
                                ed = new TriEdge(left,source,temp);
                                dirtyEdges.add(ed);
                            }
                            left=source;
                            source=temp;
                        }
                        if(source!=null){
                            ed = new TriEdge(left,source,null);
                            dirtyEdges.add(ed);
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
                 ed = new TriEdge(null, target, null);
                 dirtyEdges.add(ed);
            } else {
                for (int i = 0; i + n <= target.length(); i++) {
                    String temp=target.substring(i, i + n);
                    if(source!=null){
                       ed = new TriEdge(left,source,temp);
                       dirtyEdges.add(ed);
                    }
                    left=source;
                    source=temp;
                }
                if(source!=null){
                   ed = new TriEdge(left,source,null);
                   dirtyEdges.add(ed);
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
      }
    

    public void mapEdgesV2(int n) {
        ArrayList<String> dirtyNGrams = this.disect(n);
        Edges = new ArrayList<TriEdge>();
        this.n = n;
        ArrayList<TriEdge> dirtyEdges = new ArrayList<TriEdge>();
        if (dirtyNGrams.size() > 2) {
            int i;
            dirtyEdges.add(new TriEdge(null, dirtyNGrams.get(0), dirtyNGrams.get(1)));
            for (i = 2; i < dirtyNGrams.size(); i++) {
                dirtyEdges.add(new TriEdge(dirtyNGrams.get(i-2), dirtyNGrams.get(i-1), dirtyNGrams.get(i)));
            }
            dirtyEdges.add(new TriEdge(dirtyNGrams.get(i-2), dirtyNGrams.get(i-1), null));
        }else if(dirtyNGrams.size()>0){
            if(dirtyNGrams.size()==1){
               dirtyEdges.add(new TriEdge(null, dirtyNGrams.get(0), null)); 
            }else{
               dirtyEdges.add(new TriEdge(null, dirtyNGrams.get(0), dirtyNGrams.get(1))); 
            }
        }
        for (int i = 0; i < dirtyEdges.size(); i++) {
            if (dirtyEdges.get(i).source.length() == this.n) {
                if (Edges.indexOf(dirtyEdges.get(i)) < 0) {
                    //d++;
                    Edges.add(dirtyEdges.get(i));
                } else {
                    //o++;
                    Edges.get(Edges.indexOf(dirtyEdges.get(i))).increase();
                }
            }
        }
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
