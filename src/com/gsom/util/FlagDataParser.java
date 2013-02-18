/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util;

import com.gsom.listeners.InputParsedListener;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Thush
 */
public class FlagDataParser extends InputParser{

    @Override
    public void parseInput(InputParsedListener iListener, String fileName) {
        String tokenizer=",";
		int numOfDimensions = 29;
                GSOMConstants.DIMENSIONS = numOfDimensions;

		try {
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			File iFile = new File(fileName);
			BufferedReader input =  new BufferedReader(new FileReader(iFile));
			try {
				String line = null; //not declared within while loop

				while (( line = input.readLine()) != null){
					String text = line;
					if(text!=null && text.length()>0){
						String[] tokens = text.split(tokenizer);
                                                
                                                
						strForWeights.add(tokens[0]);
						double[] weightArr = new double[numOfDimensions];
                                                int idx = 0;
						for(int j=1;j<tokens.length;j++){
                                                    if(isNumeric(tokens[j])){
                                                        weightArr[idx]=Integer.parseInt(tokens[j]);  
                                                        idx++;
                                                    }else {
                                                        Color color = getColor(tokens[j]);
                                                        weightArr[idx]=color.getRed();
                                                        weightArr[idx+1]=color.getGreen();
                                                        weightArr[idx+2]=color.getBlue();
                                                        idx=idx+3;
                                                    }
                                                    
							
						}
						weights.add(weightArr);
					}					
				}
			}
			finally {
				input.close();
				normalizeData(weights, numOfDimensions);
				iListener.inputParseComplete(); //trigger inputParseComplete event
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
    }
    
}
