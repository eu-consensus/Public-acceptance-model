/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ViP
 */
public class Writter {
    public static void append(String line){
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("file.txt", true)));
            writer.println(line);
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Writter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
