/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Thush
 */
public class GFileHandler {

    public static void writeFile(String fName, ArrayList<String> data) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fName));
            for (String line : data) {
                out.write(line);
                out.newLine();
            }
            out.close();
            
        } catch (IOException ex) {
            System.out.println("Writing error");
        }
    }
}
