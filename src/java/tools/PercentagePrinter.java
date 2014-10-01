/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_src.Main;

/**
 *
 * @author ViP
 */
public class PercentagePrinter extends Thread {

    int oldPer;

    public PercentagePrinter() {
        oldPer = -1;
    }

    @Override
    public void run() {
        DecimalFormat df = new DecimalFormat("#.##");
        if (oldPer != ((int)Main.percentage)%10) {
            oldPer = ((int)Main.percentage)%10;
            if (Main.percentage > 0 && ((int) (Main.percentage * 10)) % 10 == 0) {
                System.out.println("Completion - " + df.format(Main.percentage) + "% " + Main.printTime(new Date()));
            }
        }
    }
}
