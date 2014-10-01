/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author Silvestro
 */
public class EmoticonFinder {
    
    public static void locateAndSplit(String filename,int limiterPos,int limiterNeg){
        try{
                int limPos=0;
                int limNeg=0;
                PrintWriter writerPos = new PrintWriter("PosEmoticonTweets.txt", "UTF-8");
                PrintWriter writerNeg = new PrintWriter("NegEmoticonTweets.txt", "UTF-8");
                FileInputStream fstream = new FileInputStream(filename);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                String user="";
                String time="";
                String tweet="";
                while (((limPos<limiterPos)||(limNeg<limiterNeg))&&((strLine = br.readLine()) != null)){
                    if(strLine.startsWith("U\t")) user=strLine;
                    else if(strLine.startsWith("T\t")) time=strLine;
                    else if(strLine.startsWith("W\t")) tweet=strLine;
                    if(user.length()>1 && time.length()>1 && tweet.length()>1){
                       if((limPos<limiterPos)&&(tweet.contains(":-)")||tweet.contains(":-D")||tweet.contains(":)")||tweet.contains(":D")||tweet.contains(";-)")||tweet.contains(";-D")||tweet.contains(";)")||tweet.contains(";D")||tweet.contains(":P")||tweet.contains(":-P")||tweet.contains("=)")||tweet.contains("=D")||tweet.contains("XD")||tweet.contains("X-D")||tweet.contains("(:")||tweet.contains("X)")||tweet.contains("X-)")||tweet.contains("=]")||tweet.contains("[=")||tweet.contains(":]")||tweet.contains("[:")||tweet.contains("[-:")||tweet.contains("(-:"))){
                           writerPos.println(user);
                           writerPos.println(time);
                           writerPos.println(tweet);
                           writerPos.println("");
                           limPos++;
                       }
                       if((limNeg<limiterNeg)&&(tweet.contains(":(")||tweet.contains("):")||tweet.contains(" :/")||tweet.contains(" \\:")||tweet.contains(":-(")||tweet.contains(")-:")||tweet.contains("=/")||tweet.contains("\\=")||tweet.contains("=(")||tweet.contains(")=")||tweet.contains(":-||")||tweet.contains("||-:")||tweet.contains("-.-")||tweet.contains(":@")||tweet.contains("@:")||tweet.contains("@-:")||tweet.contains(":-@")||tweet.contains(":=@")||tweet.contains("@=:"))){
                           writerNeg.println(user);
                           writerNeg.println(time);
                           writerNeg.println(tweet);
                           writerNeg.println("");
                           limNeg++;
                       }
                       user="";
                       time="";
                       tweet=""; 
                    }
                    
                }
                in.close();
                writerPos.close();
                writerNeg.close();
            }catch (Exception e){
                System.err.println("Error: " + e.getMessage());
            }
    }
    
    public static void locateAndSplit(String filename,int limiterPos,int limiterNeg,int Seed){
        try{
                int limPos=0;
                int limNeg=0;
                PrintWriter writerPos = new PrintWriter("PosEmoticonTweetsTest.txt", "UTF-8");
                PrintWriter writerNeg = new PrintWriter("NegEmoticonTweetsTest.txt", "UTF-8");
                FileInputStream fstream = new FileInputStream(filename);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                String user="";
                String time="";
                String tweet="";
                int count=0;
                while ((((strLine = br.readLine()) != null))&&(count<Seed)){
                    if(strLine.startsWith("U\t")) user=strLine;
                    else if(strLine.startsWith("T\t")) time=strLine;
                    else if(strLine.startsWith("W\t")) tweet=strLine;
                    if(user.length()>1 && time.length()>1 && tweet.length()>1){
                       if((tweet.contains(":-)")||tweet.contains(":-D")||tweet.contains(":)")||tweet.contains(":D")||tweet.contains(";-)")||tweet.contains(";-D")||tweet.contains(";)")||tweet.contains(";D")||tweet.contains(":P")||tweet.contains(":-P")||tweet.contains("=)")||tweet.contains("=D")||tweet.contains("XD")||tweet.contains("X-D")||tweet.contains("(:")||tweet.contains("X)")||tweet.contains("X-)")||tweet.contains("=]")||tweet.contains("[=")||tweet.contains(":]")||tweet.contains("[:")||tweet.contains("[-:")||tweet.contains("(-:"))){
                           limPos++;
                       }
                       if((tweet.contains(":(")||tweet.contains("):")||tweet.contains(" :/")||tweet.contains(" \\:")||tweet.contains(":-(")||tweet.contains(")-:")||tweet.contains("=/")||tweet.contains("\\=")||tweet.contains("=(")||tweet.contains(")=")||tweet.contains(":-||")||tweet.contains("||-:")||tweet.contains("-.-")||tweet.contains(":@")||tweet.contains("@:")||tweet.contains("@-:")||tweet.contains(":-@")||tweet.contains(":=@")||tweet.contains("@=:"))){
                           limNeg++;
                       }
                       user="";
                       time="";
                       tweet=""; 
                       if(limNeg>limPos) count=limNeg;
                       else count=limPos;
                    }
                }
                limPos=0;
                limNeg=0;
                while (((limPos<limiterPos)||(limNeg<limiterNeg))&&((strLine = br.readLine()) != null)){
                    if(strLine.startsWith("U\t")) user=strLine;
                    else if(strLine.startsWith("T\t")) time=strLine;
                    else if(strLine.startsWith("W\t")) tweet=strLine;
                    if(user.length()>1 && time.length()>1 && tweet.length()>1){
                       if((limPos<limiterPos)&&(tweet.contains(":-)")||tweet.contains(":-D")||tweet.contains(":)")||tweet.contains(":D")||tweet.contains(";-)")||tweet.contains(";-D")||tweet.contains(";)")||tweet.contains(";D")||tweet.contains(":P")||tweet.contains(":-P")||tweet.contains("=)")||tweet.contains("=D")||tweet.contains("XD")||tweet.contains("X-D")||tweet.contains("(:")||tweet.contains("X)")||tweet.contains("X-)")||tweet.contains("=]")||tweet.contains("[=")||tweet.contains(":]")||tweet.contains("[:")||tweet.contains("[-:")||tweet.contains("(-:"))){
                           writerPos.println(user);
                           writerPos.println(time);
                           writerPos.println(tweet);
                           writerPos.println("");
                           limPos++;
                       }
                       if((limNeg<limiterNeg)&&(tweet.contains(":(")||tweet.contains(" ):")||tweet.contains(" :/")||tweet.contains(" \\:")||tweet.contains(":-(")||tweet.contains(")-:")||tweet.contains("=/")||tweet.contains("\\=")||tweet.contains("=(")||tweet.contains(")=")||tweet.contains(":-||")||tweet.contains("||-:")||tweet.contains("-.-")||tweet.contains(":@")||tweet.contains("@:")||tweet.contains("@-:")||tweet.contains(":-@")||tweet.contains(":=@")||tweet.contains("@=:"))){
                           writerNeg.println(user);
                           writerNeg.println(time);
                           writerNeg.println(tweet);
                           writerNeg.println("");
                           limNeg++;
                       }
                       user="";
                       time="";
                       tweet=""; 
                    }
                    
                }
                in.close();
                writerPos.close();
                writerNeg.close();
            }catch (Exception e){
                System.err.println("Error: " + e.getMessage());
            }
    }
}
