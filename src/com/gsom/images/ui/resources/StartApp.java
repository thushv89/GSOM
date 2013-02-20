/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.images.ui.resources;

import com.gsom.images.ui.resources.Values;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ruchira
 */
public class StartApp {

    public static void readFile(File inputFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line = null;
        HashMap<String, String> map = new HashMap<String, String>();
        int count = 0;

        // To hold key and values
        String[] vals = new String[2];

        while ((line = br.readLine()) != null) {

            String text = line;
            // to skip the 'best count' value
            vals = text.split("-");
            map.put(vals[0].trim(), vals[1].trim());
            count++;
        }

        Values.map = map;
        System.out.println("> " + Values.map.size() + " lines were scanned");

        br.close();
    }

    public static boolean searchRequiredNode() {
        return false;
    }

    public static JLabel getThubnailImage(Image img, File file) {
        JLabel label = new JLabel();
        label.setLayout(new GridLayout(2, 1));
        label.setIcon(new ImageIcon(img));
        //  label.setText(file.getName());
        return label;

    }
}
