/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util.input.parsing;

import com.gsom.util.input.parsing.InputParser;
import com.gsom.util.input.parsing.GSOMConstants;
import com.gsom.listeners.InputParsedListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Thush
 */
public class NumericalDataParser extends InputParser{
    
    public void parseInput(InputParsedListener iListener,String fileName){

		String tokenizer=",";
                
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
						double[] weightArr = new double[tokens.length-1];
						for(int j=1;j<tokens.length;j++){
							weightArr[j-1]=Integer.parseInt(tokens[j]);  
						}
						weights.add(weightArr);
					}					
				}
			}
			finally {
                            GSOMConstants.DIMENSIONS = weights.get(0).length;
				input.close();
				super.normalizeData(weights, GSOMConstants.DIMENSIONS);
				iListener.inputParseComplete(); //trigger inputParseComplete event
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
	}
}
