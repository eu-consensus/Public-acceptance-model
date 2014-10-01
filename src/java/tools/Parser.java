/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.CustomFBStatus;
import Entities.CustomStatus;
import Entities.Edge;
import Entities.TriEdge;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import main_src.Main;
import static main_src.Main.purse;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;
import twitter4j.Status;

/**
 *
 * @author ViP
 */
public class Parser {

    public Parser(ArrayList<CustomStatus> Tweets) {
        this.Tweets = Tweets;
    }
    
    public Parser(ArrayList<CustomStatus> Tweets, ArrayList<CustomFBStatus> Statuses) {
        this.Statuses = Statuses;
        this.Tweets = Tweets;
    }

    public Parser() {
    }

    public ArrayList<CustomStatus> getTweets() {
        return Tweets;
    }
    
    public ArrayList<CustomFBStatus> getStatuses() {
        return Statuses;
    }
    ArrayList<CustomStatus> Tweets;
    ArrayList<CustomFBStatus> Statuses;
    
    public int countCorrect(){
        int count=0;
        int cpo=0;
        int cnu=0;
        int cng=0;
        int fcpo=0;
        int fcnu=0;
        int fcng=0;
        int tcpo=0;
        int tcnu=0;
        int tcng=0;
        for(int i=0;i<this.Tweets.size();i++){
            if(this.Tweets.get(i).getPolarity()!=null && this.Tweets.get(i).getPrePolarity()!=null){
                if(this.Tweets.get(i).getPrePolarity()>0){
                    tcpo++;
                }else if(this.Tweets.get(i).getPrePolarity()<0){
                    tcng++;
                }else if(this.Tweets.get(i).getPrePolarity()==0){
                    tcnu++;
                }
                if(this.Tweets.get(i).getPolarity()>0 && !(this.Tweets.get(i).getPrePolarity()>0)){
                    fcpo++;
                }else if(this.Tweets.get(i).getPolarity()<0 && !(this.Tweets.get(i).getPrePolarity()<0)){
                    fcng++;
                }else if(this.Tweets.get(i).getPolarity()==0 && !(this.Tweets.get(i).getPrePolarity()==0)){
                    fcnu++;
                }
                if(this.Tweets.get(i).getPolarity()>0 && this.Tweets.get(i).getPrePolarity()>0){
                    count++;
                    cpo++;
                }else if(this.Tweets.get(i).getPolarity()<0 && this.Tweets.get(i).getPrePolarity()<0){
                    count++;
                    cng++;
                }else if(this.Tweets.get(i).getPolarity()==0 && this.Tweets.get(i).getPrePolarity()==0){
                    count++;
                    cnu++;
                }
            }
        }
        
        System.out.println("False Positive: "+(double)((int)(((double)fcpo/tcpo)*10000))/100+"%");
        System.out.println("False Neutral: "+(double)((int)(((double)fcnu/tcnu)*10000))/100+"%");
        System.out.println("False Negative: "+(double)((int)(((double)fcng/tcng)*10000))/100+"%");
        System.out.println("True Positive: "+(double)((int)(((double)cpo/tcpo)*10000))/100+"%");
        System.out.println("True Neutral: "+(double)((int)(((double)cnu/tcnu)*10000))/100+"%");
        System.out.println("True Negative: "+(double)((int)(((double)cng/tcng)*10000))/100+"%");
        return count;
    }
    
    public static GraphMerger readMerger(String filename){
         GraphMerger gm=new GraphMerger();
         ArrayList<Edge> eds=null;
         String source=null;
         String target=null;
         Float weight=null;
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
         while ((strLine = br.readLine()) != null) {
                strLine=strLine.toLowerCase();
                if(strLine.contains("<numofgraphs>")){
                    try{
                        gm.GraphsMerged=Integer.parseInt(strLine.substring(strLine.indexOf("<numofgraphs>")+"<numofgraphs>".length(),strLine.indexOf("</numofgraphs>")));
                    }catch(NumberFormatException ex){
                        System.err.println(strLine);
                    }
                }
                if(strLine.toLowerCase().contains("<edges>")){
                    eds=new ArrayList<Edge>();
                }
                if(strLine.toLowerCase().contains("<source>")){
                   source=strLine.substring(strLine.indexOf("<source>")+"<source>".length(),strLine.indexOf("</source>"));
                }
                if(strLine.toLowerCase().contains("<target>")){
                   target=strLine.substring(strLine.indexOf("<target>")+"<target>".length(),strLine.indexOf("</target>"));
                }
                if(strLine.toLowerCase().contains("<weight>")){
                    try{
                        weight=Float.parseFloat(strLine.substring(strLine.indexOf("<weight>")+"<weight>".length(),strLine.indexOf("</weight>")));
                    }catch(NumberFormatException ex){
                        System.err.println(strLine);
                    }
                }
                if(strLine.toLowerCase().contains("</edge>")){
                   eds.add(new Edge(source, target, weight));
                }
                if(strLine.toLowerCase().contains("</edges>")){
                    gm.TotalGraph=eds;
                }
         }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return gm;
    }

    public static TriGraphMerger readTriMerger(String filename){
         TriGraphMerger gm=new TriGraphMerger();
         ArrayList<TriEdge> eds=null;
         String source=null;
         String left=null;
         String right=null;
         Float weight=null;
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
         while ((strLine = br.readLine()) != null) {
                strLine=strLine.toLowerCase();
                if(strLine.contains("<numofgraphs>")){
                    try{
                        gm.GraphsMerged=Integer.parseInt(strLine.substring(strLine.indexOf("<numofgraphs>")+"<numofgraphs>".length(),strLine.indexOf("</numofgraphs>")));
                    }catch(NumberFormatException ex){
                        System.err.println(strLine);
                    }
                }
                if(strLine.toLowerCase().contains("<edges>")){
                    eds=new ArrayList<TriEdge>();
                }
                if(strLine.toLowerCase().contains("<source>")){
                   source=strLine.substring(strLine.indexOf("<source>")+"<source>".length(),strLine.indexOf("</source>"));
                }
                if(strLine.toLowerCase().contains("<left>")){
                   left=strLine.substring(strLine.indexOf("<left>")+"<left>".length(),strLine.indexOf("</left>"));
                }
                if(strLine.toLowerCase().contains("<right>")){
                   right=strLine.substring(strLine.indexOf("<right>")+"<right>".length(),strLine.indexOf("</right>"));
                }
                if(strLine.toLowerCase().contains("<weight>")){
                    try{
                        weight=Float.parseFloat(strLine.substring(strLine.indexOf("<weight>")+"<weight>".length(),strLine.indexOf("</weight>")));
                    }catch(NumberFormatException ex){
                        System.err.println(strLine);
                    }
                }
                if(strLine.toLowerCase().contains("</edge>")){
                   eds.add(new TriEdge(left, source, right, weight));
                }
                if(strLine.toLowerCase().contains("</edges>")){
                    gm.TotalGraph=eds;
                }
         }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return gm;
    }
    
    public void parse(String filename) {
        FileInputStream fstream = null;
        Tweets = new ArrayList<CustomStatus>();
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Boolean root = false;
            Boolean tweet = false;
            Boolean id = false;
            Boolean date = false;
            Boolean description = false;
            Boolean retweets = false;
            Boolean favorite = false;
            Boolean polarity = false;
            Boolean prePolarity = false;
            String t_id = "";
            String t_date = "";
            String t_description = "";
            String t_retweets = "";
            String t_favorite = "";
            String t_polarity = "";
            String t_prePolarity = "";
            CustomStatus stat = null;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("<Statuses>")) {
                    root = true;
                }
                if (strLine.contains("<tweet>") && !tweet) {
                    tweet = true;
                }
                if (strLine.contains("<id>") && tweet) {
                    id = true;
                }
                if (id && !strLine.contains("</id>")) {
                    t_id = strLine.trim();
                }
                if (strLine.contains("</id>") && id) {
                    id = false;
                }
                if (strLine.contains("<date>") && tweet) {
                    date = true;
                }
                if (date && (!strLine.contains("</date>"))) {
                    t_date = strLine.trim();
                }
                if (strLine.contains("</date>") && date) {
                    date = false;
                }
                if (strLine.contains("<description>") && tweet) {
                    description = true;
                }
                if (description && (!strLine.contains("</description>"))) {
                    t_description = strLine.trim();
                }
                if (strLine.contains("</description>") && description) {
                    description = false;
                }
                if (strLine.contains("<retweets>") && tweet) {
                    retweets = true;
                }
                if (retweets && (!strLine.contains("</retweets>"))) {
                    t_retweets = strLine.trim();
                    try {
                        Integer.parseInt(t_retweets);
                    } catch (NumberFormatException exe) {
                        t_retweets = "0";
                    }
                }
                if (strLine.contains("</retweets>") && retweets) {
                    retweets = false;
                }
                if (strLine.contains("<favorite>") && tweet) {
                    favorite = true;
                }
                if (favorite && (!strLine.contains("</favorite>"))) {
                    t_favorite = strLine.trim();
                    try {
                        Integer.parseInt(t_favorite);
                    } catch (NumberFormatException exe) {
                        t_favorite = "0";
                    }
                }
                if (strLine.contains("</favorite>") && favorite) {
                    favorite = false;
                }
                if (strLine.contains("<polarity>") && tweet) {
                    polarity = true;
                }
                if (polarity && (!strLine.contains("</polarity>"))) {
                    t_polarity = strLine.trim();
                    try {
                        Double.parseDouble(t_polarity);
                    } catch (NumberFormatException exe) {
                        t_polarity = "";
                    }
                }
                if (strLine.contains("</polarity>") && polarity) {
                    polarity = false;
                }
                if (strLine.contains("<prePolarity>") && tweet) {
                    prePolarity = true;
                }
                if (prePolarity && (!strLine.contains("</prePolarity>"))) {
                    t_prePolarity = strLine.trim();
                    try {
                        Integer.parseInt(t_prePolarity);
                    } catch (NumberFormatException exe) {
                        t_prePolarity = "";
                    }
                }
                if (strLine.contains("</prePolarity>") && prePolarity) {
                    prePolarity = false;
                }
                if (strLine.contains("</tweet>") && tweet) {
                    tweet = false;
                    stat = new CustomStatus(Long.parseLong(t_id), t_date, t_description, Integer.parseInt(t_retweets), Integer.parseInt(t_favorite));
                    try {
                        stat.setPolarity(Double.parseDouble(t_polarity));
                    } catch (NumberFormatException exe) {
                    }
                    try {
                        stat.setPrePolarity(Integer.parseInt(t_prePolarity));
                    } catch (NumberFormatException exe) {
                    }
                    Tweets.add(stat);
                }
                if (strLine.contains("</Statuses>")) {
                    root = false;
                    br.close();
                    break;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void parseFb(String filename) {
        FileInputStream fstream = null;
        Statuses = new ArrayList<CustomFBStatus>();
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Boolean root = false;
            Boolean tweet = false;
            Boolean id = false;
            Boolean date = false;
            Boolean description = false;
            Boolean retweets = false;
            Boolean favorite = false;
            Boolean polarity = false;
            Boolean prePolarity = false;
            String t_id = "";
            String t_date = "";
            String t_description = "";
            String t_retweets = "";
            String t_favorite = "";
            String t_polarity = "";
            String t_prePolarity = "";
            CustomFBStatus stat = null;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("<Statuses>")) {
                    root = true;
                }
                if (strLine.contains("<tweet>") && !tweet) {
                    tweet = true;
                }
                if (strLine.contains("<id>") && tweet) {
                    id = true;
                }
                if (id && !strLine.contains("</id>")) {
                    t_id = strLine.trim();
                }
                if (strLine.contains("</id>") && id) {
                    id = false;
                }
                if (strLine.contains("<date>") && tweet) {
                    date = true;
                }
                if (date && (!strLine.contains("</date>"))) {
                    t_date = strLine.trim();
                }
                if (strLine.contains("</date>") && date) {
                    date = false;
                }
                if (strLine.contains("<description>") && tweet) {
                    description = true;
                }
                if (description && (!strLine.contains("</description>"))) {
                    t_description = strLine.trim();
                }
                if (strLine.contains("</description>") && description) {
                    description = false;
                }
                if (strLine.contains("<retweets>") && tweet) {
                    retweets = true;
                }
                if (retweets && (!strLine.contains("</retweets>"))) {
                    t_retweets = strLine.trim();
                    try {
                        Integer.parseInt(t_retweets);
                    } catch (NumberFormatException exe) {
                        t_retweets = "0";
                    }
                }
                if (strLine.contains("</retweets>") && retweets) {
                    retweets = false;
                }
                if (strLine.contains("<favorite>") && tweet) {
                    favorite = true;
                }
                if (favorite && (!strLine.contains("</favorite>"))) {
                    t_favorite = strLine.trim();
                    try {
                        Integer.parseInt(t_favorite);
                    } catch (NumberFormatException exe) {
                        t_favorite = "0";
                    }
                }
                if (strLine.contains("</favorite>") && favorite) {
                    favorite = false;
                }
                if (strLine.contains("<polarity>") && tweet) {
                    polarity = true;
                }
                if (polarity && (!strLine.contains("</polarity>"))) {
                    t_polarity = strLine.trim();
                    try {
                        Double.parseDouble(t_polarity);
                    } catch (NumberFormatException exe) {
                        t_polarity = "";
                    }
                }
                if (strLine.contains("</polarity>") && polarity) {
                    polarity = false;
                }
                if (strLine.contains("<prePolarity>") && tweet) {
                    prePolarity = true;
                }
                if (prePolarity && (!strLine.contains("</prePolarity>"))) {
                    t_prePolarity = strLine.trim();
                    try {
                        Integer.parseInt(t_prePolarity);
                    } catch (NumberFormatException exe) {
                        t_prePolarity = "";
                    }
                }
                if (strLine.contains("</prePolarity>") && prePolarity) {
                    prePolarity = false;
                }
                if (strLine.contains("</tweet>") && tweet) {
                    tweet = false;
                    stat = new CustomFBStatus(t_id, t_date, t_description, Integer.parseInt(t_retweets), Integer.parseInt(t_favorite));
                    try {
                        stat.setPolarity(Double.parseDouble(t_polarity));
                    } catch (NumberFormatException exe) {
                    }
                    try {
                        stat.setPrePolarity(Integer.parseInt(t_prePolarity));
                    } catch (NumberFormatException exe) {
                    }
                    Statuses.add(stat);
                }
                if (strLine.contains("</Statuses>")) {
                    root = false;
                    br.close();
                    break;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void parseByTerm(String term) {
        FileInputStream fstream = null;
        Tweets = new ArrayList<CustomStatus>();
        try {
            fstream = new FileInputStream("tweets-" + term + ".xml");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Boolean root = false;
            Boolean tweet = false;
            Boolean id = false;
            Boolean date = false;
            Boolean description = false;
            Boolean retweets = false;
            Boolean favorite = false;
            Boolean polarity = false;
            Boolean prePolarity = false;
            String t_id = "";
            String t_date = "";
            String t_description = "";
            String t_retweets = "";
            String t_favorite = "";
            String t_polarity = "";
            String t_prePolarity = "";
            CustomStatus stat = null;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("<Statuses>")) {
                    root = true;
                }
                if (strLine.contains("<tweet>") && !tweet) {
                    tweet = true;
                }
                if (strLine.contains("<id>") && tweet) {
                    id = true;
                }
                if (id && !strLine.contains("</id>")) {
                    t_id = strLine.trim();
                }
                if (strLine.contains("</id>") && id) {
                    id = false;
                }
                if (strLine.contains("<date>") && tweet) {
                    date = true;
                }
                if (date && (!strLine.contains("</date>"))) {
                    t_date = strLine.trim();
                }
                if (strLine.contains("</date>") && date) {
                    date = false;
                }
                if (strLine.contains("<description>") && tweet) {
                    description = true;
                }
                if (description && (!strLine.contains("</description>"))) {
                    t_description = strLine.trim();
                }
                if (strLine.contains("</description>") && description) {
                    description = false;
                }
                if (strLine.contains("<retweets>") && tweet) {
                    retweets = true;
                }
                if (retweets && (!strLine.contains("</retweets>"))) {
                    t_retweets = strLine.trim();
                    try {
                        Integer.parseInt(t_retweets);
                    } catch (NumberFormatException exe) {
                        t_retweets = "0";
                    }
                }
                if (strLine.contains("</retweets>") && retweets) {
                    retweets = false;
                }
                if (strLine.contains("<favorite>") && tweet) {
                    favorite = true;
                }
                if (favorite && (!strLine.contains("</favorite>"))) {
                    t_favorite = strLine.trim();
                    try {
                        Integer.parseInt(t_favorite);
                    } catch (NumberFormatException exe) {
                        t_favorite = "0";
                    }
                }
                if (strLine.contains("</favorite>") && favorite) {
                    favorite = false;
                }
                if (strLine.contains("<polarity>") && tweet) {
                    polarity = true;
                }
                if (polarity && (!strLine.contains("</polarity>"))) {
                    t_polarity = strLine.trim();
                    try {
                        Double.parseDouble(t_polarity);
                    } catch (NumberFormatException exe) {
                        t_polarity = "";
                    }
                }
                if (strLine.contains("</polarity>") && polarity) {
                    polarity = false;
                }
                if (strLine.contains("<prePolarity>") && tweet) {
                    prePolarity = true;
                }
                if (prePolarity && (!strLine.contains("</prePolarity>"))) {
                    t_prePolarity = strLine.trim();
                    try {
                        Integer.parseInt(t_prePolarity);
                    } catch (NumberFormatException exe) {
                        t_prePolarity = "";
                    }
                }
                if (strLine.contains("</prePolarity>") && prePolarity) {
                    prePolarity = false;
                }
                if (strLine.contains("</tweet>") && tweet) {
                    tweet = false;
                    stat = new CustomStatus(Long.parseLong(t_id), t_date, t_description, Integer.parseInt(t_retweets), Integer.parseInt(t_favorite));
                    try {
                        stat.setPolarity(Double.parseDouble(t_polarity));
                    } catch (NumberFormatException exe) {
                    }
                    try {
                        stat.setPrePolarity(Integer.parseInt(t_prePolarity));
                    } catch (NumberFormatException exe) {
                    }
                    Tweets.add(stat);
                }
                if (strLine.contains("</Statuses>")) {
                    root = false;
                    br.close();
                    break;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void parseFBByTerm(String term) {
        FileInputStream fstream = null;
        Statuses = new ArrayList<CustomFBStatus>();
        try {
            fstream = new FileInputStream("facebook-" + term + ".xml");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Boolean root = false;
            Boolean tweet = false;
            Boolean id = false;
            Boolean date = false;
            Boolean description = false;
            Boolean retweets = false;
            Boolean favorite = false;
            Boolean polarity = false;
            Boolean prePolarity = false;
            String t_id = "";
            String t_date = "";
            String t_description = "";
            String t_retweets = "";
            String t_favorite = "";
            String t_polarity = "";
            String t_prePolarity = "";
            CustomFBStatus stat = null;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("<Statuses>")) {
                    root = true;
                }
                if (strLine.contains("<tweet>") && !tweet) {
                    tweet = true;
                }
                if (strLine.contains("<id>") && tweet) {
                    id = true;
                }
                if (id && !strLine.contains("</id>")) {
                    t_id = strLine.trim();
                }
                if (strLine.contains("</id>") && id) {
                    id = false;
                }
                if (strLine.contains("<date>") && tweet) {
                    date = true;
                }
                if (date && (!strLine.contains("</date>"))) {
                    t_date = strLine.trim();
                }
                if (strLine.contains("</date>") && date) {
                    date = false;
                }
                if (strLine.contains("<description>") && tweet) {
                    description = true;
                }
                if (description && (!strLine.contains("</description>"))) {
                    t_description = strLine.trim();
                }
                if (strLine.contains("</description>") && description) {
                    description = false;
                }
                if (strLine.contains("<retweets>") && tweet) {
                    retweets = true;
                }
                if (retweets && (!strLine.contains("</retweets>"))) {
                    t_retweets = strLine.trim();
                    try {
                        Integer.parseInt(t_retweets);
                    } catch (NumberFormatException exe) {
                        t_retweets = "0";
                    }
                }
                if (strLine.contains("</retweets>") && retweets) {
                    retweets = false;
                }
                if (strLine.contains("<favorite>") && tweet) {
                    favorite = true;
                }
                if (favorite && (!strLine.contains("</favorite>"))) {
                    t_favorite = strLine.trim();
                    try {
                        Integer.parseInt(t_favorite);
                    } catch (NumberFormatException exe) {
                        t_favorite = "0";
                    }
                }
                if (strLine.contains("</favorite>") && favorite) {
                    favorite = false;
                }
                if (strLine.contains("<polarity>") && tweet) {
                    polarity = true;
                }
                if (polarity && (!strLine.contains("</polarity>"))) {
                    t_polarity = strLine.trim();
                    try {
                        Double.parseDouble(t_polarity);
                    } catch (NumberFormatException exe) {
                        t_polarity = "";
                    }
                }
                if (strLine.contains("</polarity>") && polarity) {
                    polarity = false;
                }
                if (strLine.contains("<prePolarity>") && tweet) {
                    prePolarity = true;
                }
                if (prePolarity && (!strLine.contains("</prePolarity>"))) {
                    t_prePolarity = strLine.trim();
                    try {
                        Integer.parseInt(t_prePolarity);
                    } catch (NumberFormatException exe) {
                        t_prePolarity = "";
                    }
                }
                if (strLine.contains("</prePolarity>") && prePolarity) {
                    prePolarity = false;
                }
                if (strLine.contains("</tweet>") && tweet) {
                    tweet = false;
                    if(t_retweets==null) t_retweets="0";
                    if(t_favorite==null) t_retweets="0";
                    stat = new CustomFBStatus(t_id, t_date, t_description, Integer.parseInt(t_retweets), Integer.parseInt(t_favorite));
                    try {
                        stat.setPolarity(Double.parseDouble(t_polarity));
                    } catch (NumberFormatException exe) {
                    }
                    try {
                        stat.setPrePolarity(Integer.parseInt(t_prePolarity));
                    } catch (NumberFormatException exe) {
                    }
                    Statuses.add(stat);
                }
                if (strLine.contains("</Statuses>")) {
                    root = false;
                    br.close();
                    break;
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int countTweets() {
        return Tweets.size();
    }

    public int countStatuses() {
        return Statuses.size();
    }
    
    public int countWithCondition(String condition) {
        int counter = 0;
        for (int i = 0; i < Tweets.size(); i++) {
            if (Tweets.get(i).getClearText().contains(condition)) {
                counter++;
            }
        }
        return counter;
    }
    
    public int countFBWithCondition(String condition) {
        int counter = 0;
        for (int i = 0; i < Statuses.size(); i++) {
            if (Statuses.get(i).getClearText().contains(condition)) {
                counter++;
            }
        }
        return counter;
    }

    public int clearTweets(String term) {
        ArrayList<Long> uniqueIds = new ArrayList<Long>();
        ArrayList<CustomStatus> uniqueStatuses = new ArrayList<CustomStatus>();
        int count = 0;
        for (int i = 0; i < Tweets.size(); i++) {
            if (!uniqueIds.contains(Tweets.get(i).getId())) {
                uniqueIds.add(Tweets.get(i).getId());
                uniqueStatuses.add(Tweets.get(i));
            } else {
                count++;
            }
        }
        for (int i = 0; i < uniqueStatuses.size(); i++) {
            for (int j = 0; j < uniqueStatuses.size(); j++) {
                if (i != j) {
                    if (WordDistanceMachine.testSimilar(uniqueStatuses.get(i).getClearText(), uniqueStatuses.get(j).getClearText())) {
                        count++;
                        uniqueStatuses.remove(j);
                        j--;
                    }
                }
            }
        }
        XMLBuilder.buildToFile(uniqueStatuses, term);
        this.Tweets = uniqueStatuses;
        return count;
    }
    
    public int clearStatuses(String term) {
        ArrayList<String> uniqueIds = new ArrayList<String>();
        ArrayList<CustomFBStatus> uniqueStatuses = new ArrayList<CustomFBStatus>();
        int count = 0;
        for (int i = 0; i < Statuses.size(); i++) {
            if (!uniqueIds.contains(Statuses.get(i).getId())) {
                uniqueIds.add(Statuses.get(i).getId());
                uniqueStatuses.add(Statuses.get(i));
            } else {
                count++;
            }
        }
        for (int i = 0; i < uniqueStatuses.size(); i++) {
            for (int j = 0; j < uniqueStatuses.size(); j++) {
                if (i != j) {
                    if (WordDistanceMachine.testSimilar(uniqueStatuses.get(i).getClearText(), uniqueStatuses.get(j).getClearText())) {
                        count++;
                        uniqueStatuses.remove(j);
                        j--;
                    }
                }
            }
        }
        XMLBuilder.buildFBToFile(uniqueStatuses, term);
        this.Statuses = uniqueStatuses;
        return count;
    }

    public int clearTweets(ArrayList<CustomStatus> tweets, String term) {
        ArrayList<Long> uniqueIds = new ArrayList<Long>();
        ArrayList<CustomStatus> uniqueStatuses = new ArrayList<CustomStatus>();
        int count = 0;
        PercentagePrinter pp = new PercentagePrinter();
        Main.percentage = 0;
        System.out.println("Removing Duplicates...");
        for (int i = 0; i < tweets.size(); i++) {
            if (!uniqueIds.contains(tweets.get(i).getId())) {
                uniqueIds.add(tweets.get(i).getId());
                uniqueStatuses.add(tweets.get(i));
            } else {
                count++;
            }
            if (pp.isAlive()) {
                pp.interrupt();
            }
            Main.percentage = (double) Math.round(((double) i / tweets.size()) * 10000) / 100;
            pp.start();
        }
        Main.percentage = 0;
        System.out.println("Removing Similar...");
        for (int i = 0; i < uniqueStatuses.size(); i++) {
            for (int j = 0; j < uniqueStatuses.size(); j++) {
                if (i != j) {
                    if (WordDistanceMachine.testSimilar(uniqueStatuses.get(i).getClearText(), uniqueStatuses.get(j).getClearText())) {
                        count++;
                        uniqueStatuses.remove(j);
                        j--;
                    }
                }
                if (pp.isAlive()) {
                    pp.interrupt();
                }
                Main.percentage = (double) Math.round(((double) i / tweets.size()) * 10000) / 100;
                pp.start();
            }
        }
        XMLBuilder.buildToFile(uniqueStatuses, term);
        return count;
    }

    public int clearStatuses(ArrayList<CustomFBStatus> statuses, String term) {
        ArrayList<String> uniqueIds = new ArrayList<String>();
        ArrayList<CustomFBStatus> uniqueStatuses = new ArrayList<CustomFBStatus>();
        int count = 0;
        PercentagePrinter pp = new PercentagePrinter();
        Main.percentage = 0;
        System.out.println("Removing Duplicates...");
        for (int i = 0; i < statuses.size(); i++) {
            if (!uniqueIds.contains(statuses.get(i).getId())) {
                uniqueIds.add(statuses.get(i).getId());
                uniqueStatuses.add(statuses.get(i));
            } else {
                count++;
            }
            if (pp.isAlive()) {
                pp.interrupt();
            }
            Main.percentage = (double) Math.round(((double) i / statuses.size()) * 10000) / 100;
            pp.start();
        }
        Main.percentage = 0;
        System.out.println("Removing Similar...");
        for (int i = 0; i < uniqueStatuses.size(); i++) {
            for (int j = 0; j < uniqueStatuses.size(); j++) {
                if (i != j) {
                    if (WordDistanceMachine.testSimilar(uniqueStatuses.get(i).getClearText(), uniqueStatuses.get(j).getClearText())) {
                        count++;
                        uniqueStatuses.remove(j);
                        j--;
                    }
                }
                if (pp.isAlive()) {
                    pp.interrupt();
                }
                Main.percentage = (double) Math.round(((double) i / statuses.size()) * 10000) / 100;
                pp.start();
            }
        }
        XMLBuilder.buildFBToFile(uniqueStatuses, term);
        return count;
    }
    
    public ArrayList<NGramCreator> convertToGraphs() {
        ArrayList<NGramCreator> resultList = new ArrayList<NGramCreator>();
        for (int i = 0; i < Tweets.size(); i++) {
            NGramCreator ngc = new NGramCreator();
            ngc.targetString(Tweets.get(i).getClearText());
            ngc.mapEdgesV2(Main.ngramms);
            resultList.add(ngc);
        }
        return resultList;
    }
    
    public ArrayList<NGramTriGraphCreator> convertToTripleGraphs() {
        ArrayList<NGramTriGraphCreator> resultList = new ArrayList<NGramTriGraphCreator>();
        for (int i = 0; i < Tweets.size(); i++) {
            NGramTriGraphCreator ngc = new NGramTriGraphCreator();
            ngc.targetString(Tweets.get(i).getClearText());
            ngc.mapEdgesV2(Main.ngramms);
            resultList.add(ngc);
        }
        return resultList;
    }
    
    public ArrayList<NGramCreator> convertFBToGraphs() {
        ArrayList<NGramCreator> resultList = new ArrayList<NGramCreator>();
        for (int i = 0; i < Statuses.size(); i++) {
            NGramCreator ngc = new NGramCreator();
            ngc.targetString(Statuses.get(i).getClearText());
            ngc.mapEdgesV2(Main.ngramms);
            resultList.add(ngc);
        }
        return resultList;
    }
    
    public ArrayList<NGramCreator> disectToGraphs(int n) {
        ArrayList<NGramCreator> resultList = new ArrayList<NGramCreator>();
        for (int i = 0; i < Tweets.size(); i++) {
            NGramCreator ngc = new NGramCreator();
            ngc.targetString(Tweets.get(i).getClearText());
            ngc.disect(n);
            resultList.add(ngc);
        }
        return resultList;
    }
    
    public ArrayList<NGramCreator> disectFBToGraphs(int n) {
        ArrayList<NGramCreator> resultList = new ArrayList<NGramCreator>();
        for (int i = 0; i < Statuses.size(); i++) {
            NGramCreator ngc = new NGramCreator();
            ngc.targetString(Statuses.get(i).getClearText());
            ngc.disect(n);
            resultList.add(ngc);
        }
        return resultList;
    }

    public static ArrayList<CustomStatus> readPrerated(String filename, int mode) {
        ArrayList<CustomStatus> readTweets = new ArrayList<CustomStatus>();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int i = 0;
            String date = null;
            String text = null;
            Boolean flag = false;
            while ((strLine = br.readLine()) != null) {
                if (strLine.startsWith("W\t")) {
                    strLine = strLine.substring(2);
                    if (!strLine.equals("No Post Title")) {
                        text = strLine;
                    } else {
                        flag = true;
                    }
                }
                if (strLine.startsWith("T\t")) {
                    strLine = strLine.substring(2);
                    date = strLine;
                }
                if (text != null && date != null) {
                    CustomStatus cs = new CustomStatus(0, date, text, 0, 0);
                    cs.setPrePolarity(mode);
                    readTweets.add(cs);
                    text = null;
                    date = null;
                } else if (date != null) {
                    if (flag) {
                        date = null;
                        flag = false;
                    }
                }
            }
            in.close();
            return readTweets;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<ArrayList<CustomStatus>> readSplitSentiments(){
        ArrayList<ArrayList<CustomStatus>> splits=new ArrayList<ArrayList<CustomStatus>>();
        
        splits.add(new ArrayList<CustomStatus>());
        splits.add(new ArrayList<CustomStatus>());
        splits.add(new ArrayList<CustomStatus>());
        for(int i=0;i<this.Tweets.size();i++){
            if(this.Tweets.get(i).getPrePolarity()!=null){
                if(this.Tweets.get(i).getPrePolarity()>0)
                    splits.get(0).add(this.Tweets.get(i));
                else if(this.Tweets.get(i).getPrePolarity()==0)
                    splits.get(1).add(this.Tweets.get(i));
                else if(this.Tweets.get(i).getPrePolarity()<=0)
                    splits.get(2).add(this.Tweets.get(i));
            }
        }
        return splits;
    }
    
    public ArrayList<ArrayList<CustomFBStatus>> fbSplitSentiments(){
        ArrayList<ArrayList<CustomFBStatus>> splits=new ArrayList<ArrayList<CustomFBStatus>>();
        
        splits.add(new ArrayList<CustomFBStatus>());
        splits.add(new ArrayList<CustomFBStatus>());
        splits.add(new ArrayList<CustomFBStatus>());
        for(int i=0;i<this.Statuses.size();i++){
            if(this.Statuses.get(i).getPrePolarity()!=null){
                if(this.Statuses.get(i).getPrePolarity()>0)
                    splits.get(0).add(this.Statuses.get(i));
                else if(this.Statuses.get(i).getPrePolarity()==0)
                    splits.get(1).add(this.Statuses.get(i));
                else if(this.Statuses.get(i).getPrePolarity()<=0)
                    splits.get(2).add(this.Statuses.get(i));
            }
        }
        return splits;
    }
    
    public void autoBalance(){
        ArrayList<CustomStatus> pos=new ArrayList<CustomStatus>();
        ArrayList<CustomStatus> neu=new ArrayList<CustomStatus>();
        ArrayList<CustomStatus> neg=new ArrayList<CustomStatus>();
        ArrayList<CustomStatus> nul=new ArrayList<CustomStatus>();
        
        for(int i=0;i<this.Tweets.size();i++){
            if(this.Tweets.get(i).getPrePolarity()!=null){
                if(this.Tweets.get(i).getPrePolarity()>0){
                    pos.add(this.Tweets.get(i));
                }else if(this.Tweets.get(i).getPrePolarity()==0){
                    neu.add(this.Tweets.get(i));
                }else if(this.Tweets.get(i).getPrePolarity()<0){
                    neg.add(this.Tweets.get(i));
                }else{
                    nul.add(this.Tweets.get(i));
                }
            }else{
                nul.add(this.Tweets.get(i));
            }
        }
        int min=pos.size();
        if(min>neu.size()) min=neu.size();
        if(min>neg.size()) min=neg.size();
        int random=(int)(Math.random() * (pos.size()));
        while(pos.size()>min){
            pos.remove(random);
            random=(int)(Math.random() * (pos.size()));
        }
        random=(int)(Math.random() * (neu.size()));
        while(neu.size()>min){
            neu.remove(random);
            random=(int)(Math.random() * (neu.size()));
        }
        random=(int)(Math.random() * (neg.size()));
        while(neg.size()>min){
            neg.remove(random);
            random=(int)(Math.random() * (neg.size()));
        }
        this.Tweets=new ArrayList<CustomStatus>();
        this.Tweets.addAll(pos);
        this.Tweets.addAll(neg);
        this.Tweets.addAll(neu);
        this.Tweets.addAll(nul);
        
        
        int cp=0;
        int cnu=0;
        int cng=0;
    }

    public void fbautoBalance(){
        ArrayList<CustomFBStatus> pos=new ArrayList<CustomFBStatus>();
        ArrayList<CustomFBStatus> neu=new ArrayList<CustomFBStatus>();
        ArrayList<CustomFBStatus> neg=new ArrayList<CustomFBStatus>();
        ArrayList<CustomFBStatus> nul=new ArrayList<CustomFBStatus>();
        
        for(int i=0;i<this.Statuses.size();i++){
            if(this.Statuses.get(i).getPrePolarity()!=null){
                if(this.Statuses.get(i).getPrePolarity()>0){
                    pos.add(this.Statuses.get(i));
                }else if(this.Statuses.get(i).getPrePolarity()==0){
                    neu.add(this.Statuses.get(i));
                }else if(this.Statuses.get(i).getPrePolarity()<0){
                    neg.add(this.Statuses.get(i));
                }else{
                    nul.add(this.Statuses.get(i));
                }
            }else{
                nul.add(this.Statuses.get(i));
            }
        }
        int min=pos.size();
        if(min>neu.size()) min=neu.size();
        if(min>neg.size()) min=neg.size();
        int random=(int)(Math.random() * (pos.size()));
        while(pos.size()>min){
            pos.remove(random);
            random=(int)(Math.random() * (pos.size()));
        }
        random=(int)(Math.random() * (neu.size()));
        while(neu.size()>min){
            neu.remove(random);
            random=(int)(Math.random() * (neu.size()));
        }
        random=(int)(Math.random() * (neg.size()));
        while(neg.size()>min){
            neg.remove(random);
            random=(int)(Math.random() * (neg.size()));
        }
        this.Statuses=new ArrayList<CustomFBStatus>();
        this.Statuses.addAll(pos);
        this.Statuses.addAll(neg);
        this.Statuses.addAll(neu);
        this.Statuses.addAll(nul);
    }
    
    public ArrayList<CustomStatus> getPrePositive() {
        ArrayList<CustomStatus> pos=new ArrayList<CustomStatus>();
        for(int i=0;i<this.Tweets.size();i++){
            try{
            if(this.Tweets.get(i).getPrePolarity()>0){
                pos.add(this.Tweets.get(i));
            }
            }catch(NullPointerException ex){}
        }
        return pos;
    }
    
    public ArrayList<CustomFBStatus> getFbPrePositive() {
        ArrayList<CustomFBStatus> pos=new ArrayList<CustomFBStatus>();
        for(int i=0;i<this.Statuses.size();i++){
            try{
            if(this.Statuses.get(i).getPrePolarity()>0){
                pos.add(this.Statuses.get(i));
            }
            }catch(NullPointerException ex){}
        }
        return pos;
    }
    
    public ArrayList<CustomStatus> getPreNegative() {
        ArrayList<CustomStatus> neg=new ArrayList<CustomStatus>();
        for(int i=0;i<this.Tweets.size();i++){
            try{
            if(this.Tweets.get(i).getPrePolarity()<0){
                neg.add(this.Tweets.get(i));
            }
            }catch(NullPointerException ex){}
        }
        return neg;
    }
    
    public ArrayList<CustomFBStatus> getFbPreNegative() {
        ArrayList<CustomFBStatus> neg=new ArrayList<CustomFBStatus>();
        for(int i=0;i<this.Statuses.size();i++){
            try{
            if(this.Statuses.get(i).getPrePolarity()<0){
                neg.add(this.Statuses.get(i));
            }
            }catch(NullPointerException ex){}
        }
        return neg;
    }
    
    public ArrayList<CustomStatus> getPreNeutral() {
        ArrayList<CustomStatus> neu=new ArrayList<CustomStatus>();
        for(int i=0;i<this.Tweets.size();i++){
            try{
            if(this.Tweets.get(i).getPrePolarity()==0){
                neu.add(this.Tweets.get(i));
            }
            }catch(NullPointerException ex){}
        }
        return neu;
    }
    
    public ArrayList<CustomFBStatus> getFbPreNeutral() {
        ArrayList<CustomFBStatus> neu=new ArrayList<CustomFBStatus>();
        for(int i=0;i<this.Statuses.size();i++){
            try{
            if(this.Statuses.get(i).getPrePolarity()==0){
                neu.add(this.Statuses.get(i));
            }
            }catch(NullPointerException ex){}
        }
        return neu;
    }
}
