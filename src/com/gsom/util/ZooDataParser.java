/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util;

import com.gsom.listeners.InputParsedListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Thush
 */
public class ZooDataParser extends InputParser{
    
    public void parseInput(InputParsedListener iListener,String fileName){

		String tokenizer=",";
		int numOfDimensions = 17;
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
						for(int j=1;j<tokens.length;j++){
							weightArr[j-1]=Integer.parseInt(tokens[j]);  
						}
						weights.add(weightArr);
					}					
				}
			}
			finally {
				input.close();
				super.normalizeData(weights, numOfDimensions);
				iListener.inputParseComplete(); //trigger inputParseComplete event
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
	}
}
