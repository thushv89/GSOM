/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util.input.parsing;

import com.gsom.listeners.InputParsedListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Thush
 */
public class GenericDataParser extends InputParser {

    public void parseInput(InputParsedListener iListener, String fileName) {

        String tokenizer = ",";
        //int numOfDimensions = GSOMConstants.DIMENSIONS;
        int numOfDimensions = 0;
        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            File iFile = new File(fileName);
            BufferedReader input = new BufferedReader(new FileReader(iFile));
            try {
//				String line1 = null; //not declared within while loop
//
//                                if (( line1 = input.readLine()) != null){
//					String text1 = line1;
//					if(text1!=null && text1.length()>0){
//                                            String[] tokens1 = text1.split(tokenizer);
//                                        numOfDimensions=tokens1.length-1;
//                                    }
//
//                                }
//                                GSOMConstants.DIMENSIONS = numOfDimensions;

                String line = null; //not declared within while loop

                while ((line = input.readLine()) != null) {
                    String text = line;
                    if (text != null && text.length() > 0) {
                        String[] tokens = text.split(tokenizer);
                        numOfDimensions = tokens.length - 1;
                        strForWeights.add(tokens[0]);
                        double[] weightArr = new double[numOfDimensions];
                        for (int j = 1; j < tokens.length; j++) {
                            weightArr[j - 1] = Double.parseDouble(tokens[j]);
                        }
                        weights.add(weightArr);
                    }
                }
                GSOMConstants.DIMENSIONS = numOfDimensions;
            } finally {
                input.close();
                super.normalizeData(weights, numOfDimensions);
                iListener.inputParseComplete(); //trigger inputParseComplete event
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
