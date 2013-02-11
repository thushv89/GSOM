package com.gsom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.gsom.listeners.InputParsedListener;
import java.awt.Color;

public abstract class InputParser {

	ArrayList<String> strForWeights;
	ArrayList<double[]> weights;

	public InputParser() {

		strForWeights = new ArrayList<String>();
		weights = new ArrayList<double[]>();
	}

        abstract public void parseInput(InputParsedListener iListener,String fileName);
	
	protected void normalizeData(ArrayList<double[]> inputs, int dimensions){
		ArrayList<Double> maxDimArr = new ArrayList<Double>();

		for(int i=0;i<dimensions;i++){
			double[] dimArr = new double[inputs.size()];
			for(int j=0;j<inputs.size();j++){
				dimArr[j]=inputs.get(j)[i];
			}
			maxDimArr.add(ArrayHelper.getMax(dimArr));
		}
		
		for(int i=0;i<dimensions;i++){
			if(maxDimArr.get(i)>1.0){
				for(int j=0;j<inputs.size();j++){
					double[] inArr = inputs.get(j);
					inArr[i]=inArr[i]/maxDimArr.get(i);
					inputs.set(j, inArr);
				}
			}
			
		}
	}
	
	protected boolean isNumeric(String str)
        {
            for (char c : str.toCharArray())
            {
                if (!Character.isDigit(c)) return false;
            }
            return true;
        }
        
        protected Color getColor(String color){
            Color clr = null;
            
                if (color.equalsIgnoreCase("red")){
                    clr = Color.red;
                }else if(color.equalsIgnoreCase("green")){
                    clr = Color.green;
                }else if(color.equalsIgnoreCase("blue")){
                    clr = Color.blue;
                }else if(color.equalsIgnoreCase("black")){
                    clr = Color.black;
                }else if(color.equalsIgnoreCase("white")){
                    clr = Color.white;
                }else if(color.equalsIgnoreCase("orange")){
                    clr = Color.orange;
                }
                else if(color.equalsIgnoreCase("gold")){
                    clr = new Color(255, 215, 0);
                }else if(color.equalsIgnoreCase("brown")){
                    clr = new Color(165,42,42);
                }
            
            return clr;
        }
        
	public ArrayList<String> getStrForWeights() {
		return strForWeights;
	}

	public ArrayList<double[]> getWeights() {
		return weights;
	}

        public void printInput(){
            for(int i=0;i<strForWeights.size();i++){
                System.out.print(strForWeights.get(i)+" ");
                for(int j=0;j<GSOMConstants.DIMENSIONS;j++){
                    System.out.print(weights.get(i)[j]+",");
                    
                }
                System.out.println("");
            }
        }


}
