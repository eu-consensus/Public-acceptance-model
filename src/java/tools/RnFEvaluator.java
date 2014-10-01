/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.CustomStatus;
import java.util.ArrayList;
import main_src.Main;

/**
 *
 * @author ViP
 */
public class RnFEvaluator {

    public static int countRetweeted() {
        int counter = 0;
        ArrayList<CustomStatus> twerts = Main.purse.Tweets;
        for (int i = 0; i < twerts.size(); i++) {
            if (twerts.get(i).getRetweetCount() > 0) {
                counter++;
            }
        }
        return counter;
    }

    public static int countFaved() {
        int counter = 0;
        ArrayList<CustomStatus> twerts = Main.purse.Tweets;
        for (int i = 0; i < twerts.size(); i++) {
            if (twerts.get(i).getFavoriteCount() > 0) {
                counter++;
            }
        }
        return counter;
    }

    public static int countDistinctTotal() {
        int counter = 0;
        ArrayList<CustomStatus> twerts = Main.purse.Tweets;
        for (int i = 0; i < twerts.size(); i++) {
            if (twerts.get(i).getRetweetCount() > 0) {
                counter++;
            } else if (twerts.get(i).getFavoriteCount() > 0) {
                counter++;
            }
        }
        return counter;
    }

    public static double RnFScore(CustomStatus tweet) {
        double result=1;
        result+=0.5*tweet.getRetweetCount();
        result+=1*tweet.getFavoriteCount();
        return result;
    }
}
