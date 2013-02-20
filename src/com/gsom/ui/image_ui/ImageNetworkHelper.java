/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Thush and Ruwan
 */
public class ImageNetworkHelper {

    public Map<String, ArrayList<String>> adjustCoords(Map<String, ArrayList<String>> hNodeImgMap) {
        return valueSeparator(UIValues.getMap());
    }

    public Map<String, ArrayList<String>> valueSeparator(Map<String, String> m) {
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        ArrayList<String> list = null;   // holds the files names of the GSOM output
        String[] vals = null;            // holds the files names of the GSOM output as strings

        Iterator it = m.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            list = new ArrayList<String>();
            vals = ((String) pairs.getValue()).split(",");

            for (String string : vals) {
                list.add(string);
            }
            result.put((String) pairs.getKey(), list);
            System.out.println(pairs.getKey() + " => " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return result;
    }

    public static void setUIValues(String fileName) {
        ArrayList<String> x = new ArrayList<String>();
        ArrayList<String> y = new ArrayList<String>();
        BufferedReader br = null;
        File file = null;
        try {

            file = new File(fileName);
            br = new BufferedReader(new FileReader(file));
            String line;
            HashMap<String, String> map = new HashMap<String, String>();

            // To hold key and values
            String[] vals;
            while ((line = br.readLine()) != null && line.length() != 0) {
                // to skip the 'best count' value

                // splitting keys and values
                vals = line.split(":");
                
                // Checks wether node is not empty
                if (!vals[0].trim().isEmpty()) {
                    String[] xyStr = vals[0].trim().split(",");
                    x.add(xyStr[0].trim());
                    y.add(xyStr[1].trim());
                }
                map.put(vals[0].trim(), vals[1].trim());

            }
            UIValues.setMap(map);

            Collections.sort(x);
            Collections.sort(y);

            int xMin = Integer.parseInt(x.get(0).trim());
            int xMax = Integer.parseInt(x.get(x.size() - 1).trim());
            int yMin = Integer.parseInt(y.get(0).trim());
            int yMax = Integer.parseInt(y.get(y.size() - 1).trim());
            UIValues.addMinMaxValues(file.getName(), xMin, xMax, yMin, yMax);

            UIValues.setXGridCount(Math.abs(xMax - xMin) + 1);
            System.out.println("Math.abs(xMax-xMin): " + Math.abs(xMax - xMin));
            UIValues.setYGridCount(Math.abs(yMax - yMin) + 1);

            br.close();

        } catch (IOException ex) {
            Logger.getLogger(ImageNetworkHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageNetworkHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean searchRequiredNode() {
        return false;
    }

}
