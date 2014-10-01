/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.CustomStatus;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main_src.Main;

/**
 *
 * @author ViP
 */
public class TweetTransformer {

    public static void transformStanfordSet(String filename) {
        FileInputStream fstream = null;
        String strLine = null;
        try {
             PercentagePrinter pp = new PercentagePrinter();
             Main.percentage=0;
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            //int count = 0;
            //ArrayList<CustomStatus> tweets = new ArrayList<CustomStatus>();
            XMLBuilder.initialiseFile("live-train-data-stanford-csv.xml");
            int g=0;
            while ((strLine = br.readLine()) != null) {
                ArrayList cleanSplits = new ArrayList<String>();
                String[] splits = strLine.split(",");
                for (int i = 0; i < splits.length; i++) {
                    if (splits[i].startsWith("\"") && splits[i].endsWith("\"") && splits[i].length()>1) {
                        cleanSplits.add(splits[i].split("\"")[1]);
                    } else {
                        String temp = splits[i];
                        int j = i + 1;
                        while (!splits[j].endsWith("\"") && j < splits.length) {
                            temp += "," + splits[j];
                            j++;
                        }
                        temp += "," + splits[j];
                        i = j;
                        cleanSplits.add(temp.split("\"")[1]);
                    }
                }
                CustomStatus cs = null;
                    try {
                        cs = new CustomStatus(Long.parseLong((String) cleanSplits.get(1)), (String) cleanSplits.get(2), (String) cleanSplits.get(5), 0, 0);
                        cs.setPrePolarity((Integer.parseInt((String) cleanSplits.get(0)) / 2) - 1);
                    } catch (NumberFormatException ex) {
                    } catch (NullPointerException ex) {
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                    if (cs != null && cs.getPrePolarity() != null) {
                        XMLBuilder.buildSingleToFile(cs, "live-train-data-stanford-csv.xml");
                    }
                    if(pp.isAlive()){
                    pp.interrupt();
                    }
                    Main.percentage=(double)Math.round(((double)g/1600000)*10000)/100;
                    pp.start();
                    g++;
            }
            in.close();
            //System.out.println(tweets.size());
            //if (tweets.size() > 0) {
              //  XMLBuilder.clearAndBuildToFile(tweets, "train-data-stanford-csv");
            //}
            XMLBuilder.finaliseFile("live-train-data-stanford-csv.xml");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(TweetTransformer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static void transformHcrCSV(String filename) {
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int count = 0;
            ArrayList<CustomStatus> tweets = new ArrayList<CustomStatus>();
            while ((strLine = br.readLine()) != null) {
                String[] splits = strLine.split(",");
                if (splits != null) {
                    try {
                        boolean flag = true;
                        int i = 4;
                        Integer polarity = null;
                        long id = Long.parseLong(splits[0]);
                        count++;
                        while (flag && i < splits.length) {
                            if (splits[i].toLowerCase().trim().equals("negative")) {
                                polarity = -1;
                                flag = false;
                            } else if (splits[i].toLowerCase().trim().equals("positive")) {
                                polarity = 1;
                                flag = false;
                            } else if (splits[i].toLowerCase().trim().equals("neutral")) {
                                polarity = 0;
                                flag = false;
                            } else if (splits[i].toLowerCase().trim().equals("irrelevant")) {
                                polarity = 0;
                                flag = false;
                            }
                            i++;
                        }
                        i--;
                        if (i > 4 && !flag) {
                            String temp = "";
                            for (int j = 3; j < i; j++) {
                                temp += splits[j];
                            }
                            splits[3] = temp;
                        }
                        if (!flag) {
                            CustomStatus cs = new CustomStatus(id, "", splits[3].replaceAll("\"", "").replaceAll("'", ""), 0, 0);
                            cs.setPrePolarity(polarity);
                            tweets.add(cs);
                        }
                    } catch (NumberFormatException ex) {
                    } catch (NullPointerException ex) {
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                }

            }
            in.close();
            if (tweets.size() > 0) {
                XMLBuilder.clearAndBuildToFile(tweets, "train-data-hcr-csv");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(TweetTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void transformSimpleCSV(String filename) {
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int count = 0;
            ArrayList<CustomStatus> tweets = new ArrayList<CustomStatus>();
            while ((strLine = br.readLine()) != null) {
                String[] splits = strLine.split(",");
                CustomStatus cs = null;
                try {
                    cs = new CustomStatus(Long.parseLong(splits[0]), "", splits[3].replaceAll("\"", "").replaceAll("'", "").replaceAll("-", "").trim(), 0, 0);
                    cs.setPrePolarity((Integer.parseInt(splits[1]) * 2) - 1);
                } catch (NumberFormatException ex) {
                } catch (NullPointerException ex) {
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                if (cs != null && cs.getPrePolarity() != null) {
                    tweets.add(cs);
                }
            }
            in.close();
            System.out.println(tweets.size());
            if (tweets.size() > 0) {
                XMLBuilder.clearAndBuildToFile(tweets, "train-data-simple-csv");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(TweetTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void transformTabbed(String filename) {
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int count = 0;
            ArrayList<CustomStatus> tweets = new ArrayList<CustomStatus>();
            while ((strLine = br.readLine()) != null) {
                String[] splits = strLine.split("\t");
                CustomStatus cs = null;
                try {
                    cs = new CustomStatus(Long.parseLong(splits[0]), splits[1], splits[2].replaceAll("\"", "").replaceAll("'", "").replaceAll("-", "").trim(), 0, 0);
                    ArrayList<Integer> counts = new ArrayList<Integer>();
                    counts.add(0);
                    counts.add(0);
                    counts.add(0);
                    counts.add(0);
                    for (int i = 5; i < splits.length; i++) {
                        counts.set(Integer.parseInt(splits[i]) - 1, counts.get(Integer.parseInt(splits[i]) - 1) + 1);
                    }
                    cs.setPrePolarity(getPolarity(counts));
                } catch (NumberFormatException ex) {
                } catch (NullPointerException ex) {
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                if (cs != null && cs.getPrePolarity() != null) {
                    tweets.add(cs);
                }
            }
            in.close();
            System.out.println(tweets.size());
            if (tweets.size() > 0) {
                XMLBuilder.clearAndBuildToFile(tweets, "train-data-tabbed");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(TweetTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Integer getPolarity(ArrayList<Integer> counts) {
        int max = counts.get(0);
        int maxIndex = 0;
        for (int i = 1; i < counts.size(); i++) {
            if (counts.get(i) > max) {
                max = counts.get(i);
                maxIndex = i;
            }
        }
        if(counts.get(0) != counts.get(1)){
        if (maxIndex == 0) {
            return -1;
        }else if (maxIndex == 1) {
            return 1;
        }else {
            return 0;
        }
        }else {
            return 0;
        }
    }
}
