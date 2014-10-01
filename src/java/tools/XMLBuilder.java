/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.CustomFBStatus;
import Entities.CustomStatus;
import Entities.Edge;
import Entities.TriEdge;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_src.Main;

/**
 *
 * @author ViP
 */
public class XMLBuilder {
    
    public static void buildMergedGraph(GraphMerger gm,String filename){
        String result="";
        result +="<Graph>\n";
        result +="<NumOfGraphs>"+gm.GraphsMerged+"</NumOfGraphs>\n";
        result +="<Edges>\n";
        Edge ed=null;
        for(int i=0;i<gm.TotalGraph.size();i++){
            ed=gm.TotalGraph.get(i);
            result +="<Edge>\n";
            result +="<Source>"+ed.source+"</Source>\n";
            result +="<Target>"+ed.target+"</Target>\n";
            result +="<Weight>"+ed.getWeight()+"</Weight>\n";
            result +="</Edge>\n";
        }
        result +="</Edges>\n";
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename);
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } 
    }
    
     public static void buildMergedTriGraph(TriGraphMerger gm,String filename){
        String result="";
        result +="<Graph>\n";
        result +="<NumOfGraphs>"+gm.GraphsMerged+"</NumOfGraphs>\n";
        result +="<Edges>\n";
        TriEdge ed=null;
        for(int i=0;i<gm.TotalGraph.size();i++){
            ed=gm.TotalGraph.get(i);
            result +="<Edge>\n";
            result +="<Left>"+ed.left+"</Left>\n";
            result +="<Source>"+ed.source+"</Source>\n";
            result +="<Right>"+ed.right+"</Right>\n";
            result +="<Weight>"+ed.getWeight()+"</Weight>\n";
            result +="</Edge>\n";
        }
        result +="</Edges>\n";
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename);
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } 
    }

    public static String buildToString(List<CustomStatus> tweetsList) {
        String result = "";
        CustomStatus current;
        result += "<Statuses>";
        for (int i = 0; i < tweetsList.size(); i++) {
            current = tweetsList.get(i);
            result += "<tweet>";
            result += "<id>";
            result += current.getId();
            result += "</id>";
            result += "<date>";
            result += current.getCreatedAt();
            result += "</date>";
            result += "<description>";
            result += current.getText();
            result += "</description>";
            result += "<retweets>";
            result += current.getRetweetCount();
            result += "</retweets>";
            result += "<favorite>";
            result += current.getFavoriteCount();
            result += "</favorite>";
            result += "<polarity>";
            if (current.getPolarity() != null) {
                result += current.getPolarity();
            }
            result += "</polarity>";
            result += "<prePolarity>";
            if (current.getPrePolarity() != null) {
                result += current.getPrePolarity();
            }
            result += "</prePolarity>";
            result += "</tweet>";
        }
        result += "</Statuses>";
        return result;
    }
    
    public static void buildFBToFile(List<CustomFBStatus> tweetsList, String name) {
        String result = "";
        CustomFBStatus current;
        result += "<Statuses>\n";
        for (int i = 0; i < tweetsList.size(); i++) {
            current = tweetsList.get(i);
            result += "<tweet>\n";
            result += "<id>\n";
            result += current.getId();
            result += "\n</id>\n";
            result += "<date>\n";
            result += current.getCreatedAt();
            result += "\n</date>\n";
            result += "<description>\n";
            result += current.getText();
            result += "\n</description>\n";
            result += "<retweets>\n";
            result += current.getRetweetCount();
            result += "\n</retweets>\n";
            result += "<favorite>\n";
            result += current.getFavoriteCount();
            result += "\n</favorite>\n";
            result += "<polarity>\n";
            if (current.getPolarity() != null) {
                result += current.getPolarity();
            }
            result += "\n</polarity>\n";
            result += "<prePolarity>\n";
            if (current.getPrePolarity() != null) {
                result += current.getPrePolarity();
            }
            result += "\n</prePolarity>\n";
            result += "</tweet>\n";
        }
        result += "</Statuses>";
        PrintWriter writer;
        try {
            writer = new PrintWriter("facebook-" + name + ".xml", "UTF-8");
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void buildToFile(List<CustomStatus> tweetsList, String name) {
        String result = "";
        CustomStatus current;
        result += "<Statuses>\n";
        for (int i = 0; i < tweetsList.size(); i++) {
            current = tweetsList.get(i);
            result += "<tweet>\n";
            result += "<id>\n";
            result += current.getId();
            result += "\n</id>\n";
            result += "<date>\n";
            result += current.getCreatedAt();
            result += "\n</date>\n";
            result += "<description>\n";
            result += current.getText();
            result += "\n</description>\n";
            result += "<retweets>\n";
            result += current.getRetweetCount();
            result += "\n</retweets>\n";
            result += "<favorite>\n";
            result += current.getFavoriteCount();
            result += "\n</favorite>\n";
            result += "<polarity>\n";
            if (current.getPolarity() != null) {
                result += current.getPolarity();
            }
            result += "\n</polarity>\n";
            result += "<prePolarity>\n";
            if (current.getPrePolarity() != null) {
                result += current.getPrePolarity();
            }
            result += "\n</prePolarity>\n";
            result += "</tweet>\n";
        }
        result += "</Statuses>";
        PrintWriter writer;
        try {
            writer = new PrintWriter("tweets-" + name + ".xml", "UTF-8");
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void buildToFile(ArrayList<CustomStatus> tweetsList, String name) {
        String result = "";
        CustomStatus current;
        result += "<Statuses>\n";
        for (int i = 0; i < tweetsList.size(); i++) {
            current = tweetsList.get(i);
            result += "<tweet>\n";
            result += "<id>\n";
            result += current.getId();
            result += "\n</id>\n";
            result += "<date>\n";
            result += current.getCreatedAt();
            result += "\n</date>\n";
            result += "<description>\n";
            result += current.getText();
            result += "\n</description>\n";
            result += "<retweets>\n";
            result += current.getRetweetCount();
            result += "\n</retweets>\n";
            result += "<favorite>\n";
            result += current.getFavoriteCount();
            result += "\n</favorite>\n";
            result += "<polarity>\n";
            if (current.getPolarity() != null) {
                result += current.getPolarity();
            }
            result += "\n</polarity>\n";
            result += "<prePolarity>\n";
            if (current.getPrePolarity() != null) {
                result += current.getPrePolarity();
            }
            result += "\n</prePolarity>\n";
            result += "</tweet>\n";
        }
        result += "</Statuses>";
        PrintWriter writer;
        try {
            writer = new PrintWriter("tweets-" + name + ".xml", "UTF-8");
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void buildSingleToFile(CustomStatus tweet, String name) {
        String result = "";
        result += "<tweet>\n";
        result += "<id>\n";
        result += tweet.getId();
        result += "\n</id>\n";
        result += "<date>\n";
        result += tweet.getCreatedAt();
        result += "\n</date>\n";
        result += "<description>\n";
        result += tweet.getText();
        result += "\n</description>\n";
        result += "<retweets>\n";
        result += tweet.getRetweetCount();
        result += "\n</retweets>\n";
        result += "<favorite>\n";
        result += tweet.getFavoriteCount();
        result += "\n</favorite>\n";
        result += "<polarity>\n";
        result += tweet.getPolarity();
        result += "\n</polarity>\n";
        result += "<prePolarity>\n";
        result += tweet.getPrePolarity();
        result += "\n</prePolarity>\n";
        result += "</tweet>\n";
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("tweets-" + name + ".xml", true)));
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void initialiseFile(String name) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("tweets-" + name + ".xml", "UTF-8");
            writer.println("<Statuses>\n");
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void finaliseFile(String name) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("tweets-" + name + ".xml", true)));
            writer.println("</Statuses>\n");
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(XMLBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int clearAndBuildToFile(ArrayList<CustomStatus> css, String term) {
        ArrayList<Long> uniqueIds=new ArrayList<Long>();
        ArrayList<CustomStatus> uniqueStatuses=new ArrayList<CustomStatus>();
        int count=0;
        PercentagePrinter pp = new PercentagePrinter();
        Main.percentage=0;
        System.out.println("Removing Duplicates...");
        for(int i=0;i<css.size();i++){
            if(!uniqueIds.contains(css.get(i).getId())){
                uniqueIds.add(css.get(i).getId());
                uniqueStatuses.add(css.get(i));
            }else{
                count++;
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/css.size())*10000)/100;
            pp.start();
        }
        Main.percentage=0;
        System.out.println("Removing Similar...");
        for(int i=0;i<uniqueStatuses.size();i++){
            for(int j=0;j<uniqueStatuses.size();j++){
                if(i!=j){
                    if(WordDistanceMachine.testSimilar(uniqueStatuses.get(i).getText(),uniqueStatuses.get(j).getText())){
                        count++;
                        uniqueStatuses.remove(j);
                        j--;
                    }
                }
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/uniqueStatuses.size())*10000)/100;
            pp.start();
        }
        System.out.println("Cleared "+count+" tweets from a total of "+css.size()+".");
        System.out.println("Building to file...");
        buildToFile(uniqueStatuses, term);
        return count;
    }
    
    public static int clearAndBuildFBToFile(ArrayList<CustomFBStatus> css, String term) {
        ArrayList<String> uniqueIds=new ArrayList<String>();
        ArrayList<CustomFBStatus> uniqueStatuses=new ArrayList<CustomFBStatus>();
        int count=0;
        PercentagePrinter pp = new PercentagePrinter();
        Main.percentage=0;
        System.out.println("Removing Duplicates...");
        for(int i=0;i<css.size();i++){
            if(!uniqueIds.contains(css.get(i).getId())){
                uniqueIds.add(css.get(i).getId());
                uniqueStatuses.add(css.get(i));
            }else{
                count++;
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/css.size())*10000)/100;
            pp.start();
        }
        Main.percentage=0;
        System.out.println("Removing Similar...");
        for(int i=0;i<uniqueStatuses.size();i++){
            for(int j=0;j<uniqueStatuses.size();j++){
                if(i!=j){
                    if(WordDistanceMachine.testSimilar(uniqueStatuses.get(i).getText(),uniqueStatuses.get(j).getText())){
                        count++;
                        uniqueStatuses.remove(j);
                        j--;
                    }
                }
            }
            if(pp.isAlive()){
               pp.interrupt();
            }
            Main.percentage=(double)Math.round(((double)i/uniqueStatuses.size())*10000)/100;
            pp.start();
        }
        System.out.println("Cleared "+count+" tweets from a total of "+css.size()+".");
        System.out.println("Building to file...");
        buildFBToFile(uniqueStatuses, term);
        return count;
    }
    
    public static void splitAndBuild(ArrayList<CustomStatus> css){
        ArrayList<ArrayList<CustomStatus>> splits=new ArrayList<ArrayList<CustomStatus>>();
        splits.add(new ArrayList<CustomStatus>());
        splits.add(new ArrayList<CustomStatus>());
        splits.add(new ArrayList<CustomStatus>());
        int count=0;
        for(int i=0;i<css.size();i++){
            if(css.get(i).getPrePolarity()!=null){
                if(css.get(i).getPrePolarity()>0)
                    splits.get(0).add(css.get(i));
                else if(css.get(i).getPrePolarity()==0)
                    splits.get(1).add(css.get(i));
                else if(css.get(i).getPrePolarity()<=0)
                    splits.get(2).add(css.get(i));
            }
            else{
                count++;
            }
        }
        buildToFile(splits.get(0),"train-positive-splitted");
        buildToFile(splits.get(1),"train-neutral-splitted");
        buildToFile(splits.get(2),"train-negative-splitted");
        System.out.println(count+"/"+css.size());
    }
}
