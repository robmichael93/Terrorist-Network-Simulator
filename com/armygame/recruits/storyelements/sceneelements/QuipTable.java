package com.armygame.recruits.storyelements.sceneelements;

import java.util.*;
import java.io.*;
import com.armygame.recruits.globals.ResourceReader;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: MOVES Institute, Army Game Project</p>
 * @author Brian Osborn
 * @version 1.0
 */

public class QuipTable {

   private Hashtable quips;
   private BufferedReader br;
   private String quipFile;

   public QuipTable(String quipFile) {
      this.quipFile = quipFile;
      quips = new Hashtable();
      initializeReader();
      String line = new String();
      String quipName;
      String quip;
      while((line = readNextLine()) != null) {
         StringTokenizer tokens = new StringTokenizer(line, ",");
         quipName = tokens.nextToken();
         quip = tokens.nextToken();
         quips.put(quipName, quip);
      }
//      quips.put("emptyQuip", "No quip was entered");
//      quips.put("invalidQuip", "Invalid quip name provided");
//      quips.put("test", "This is a test quip");

   }



   public String getQuip(String quipName) {
      if ( !quips.containsKey(quipName)) {
         return ((String)quips.get("invalidQuip")) + "(" + quipName + ")";
      }
      else {
         return (String)quips.get(quipName);
      }
   }

   private void initializeReader() {

      try {
         br = new BufferedReader(ResourceReader.getInputReader(quipFile));
      }
      catch (IOException e) {
         System.err.println("Error(QuipTable) - unable to create BufferedReader" +
                            " for file " + quipFile);
      }
   }

   private String readNextLine() {
      try {
         return br.readLine();
      }
      catch (IOException e) {
         System.err.println("Error (QuitTable) - Unable to read from file.");
         return null;
      }
   }


}