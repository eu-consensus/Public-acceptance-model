/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ViP
 */
public class SimpleReader {
    public static void main(String[] args) {
        /*try {
            FileInputStream fstream = new FileInputStream("train-data-stanford.xml");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int limit=1000;
            int i = 0;
            while ((i < limit) && ((strLine = br.readLine()) != null)) {
                System.out.println(strLine);
            }
        }catch(Exception ex){}*/
        System.out.println("Started! "+Main.printTime(new Date()));
        Main.prepare(null,null,"");
        System.out.println("Ready.... "+Main.printTime(new Date()));
        String s="papa";
        while(s!=null && s.length()>0){
             s = (String)JOptionPane.showInputDialog("Text to analyse:");
                    if ((s != null) && (s.length() > 0)) {
                        String test=Main.testSingle(s, "averagetest");
                        System.out.println(test);
                    }
        }
        System.out.println("Done! "+Main.printTime(new Date()));
       
    }
}
