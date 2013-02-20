package com.gsom.core;

import com.gsom.listeners.GSOMSmoothnerListener;
import java.util.ArrayList;
import java.util.Map;

import com.gsom.objects.GNode;
import com.gsom.util.input.parsing.GSOMConstants;
import com.gsom.util.Utils;

public class GSOMSmoothner{

	private int maxIter;
	private Map<String, GNode> map;
	
	public void smoothGSOM(Map<String, GNode> map,ArrayList<double[]> inputs,GSOMSmoothnerListener listener){
		for(int iter=0;iter<GSOMConstants.MAX_ITERATIONS;iter++){
			double learningRate = Utils.getLearningRate(iter, map.size());
			double radius = Utils.getRadius(iter, Utils.getTimeConst());
			for(double[] singleInput : inputs){
				if(singleInput.length==GSOMConstants.DIMENSIONS){
					smoothSingleIterSingleInput(map,iter,singleInput,learningRate,radius);
				}else{
					//error
				}
			}
		}
                listener.smootheningCompleted(map);
	}

	private void smoothSingleIterSingleInput(Map<String, GNode> map, int iter, double[] input,double learningRate,double radius) {
		GNode winner = Utils.selectWinner(map, input);
		double radiusSqr = radius*radius;
		
		String leftNode = Utils.generateIndexString(winner.getX()-1, winner.getY());
		String rightNode = Utils.generateIndexString(winner.getX()+1, winner.getY());
		String topNode = Utils.generateIndexString(winner.getX(), winner.getY()+1);
		String bottomNode = Utils.generateIndexString(winner.getX(), winner.getY()-1);
		
		if(map.containsKey(leftNode)){
			Utils.adjustNeighbourWeight(map.get(leftNode), winner, input, radiusSqr, learningRate);
		}else if(map.containsKey(rightNode)){
			Utils.adjustNeighbourWeight(map.get(rightNode), winner, input, radiusSqr, learningRate);
		}else if(map.containsKey(topNode)){
			Utils.adjustNeighbourWeight(map.get(topNode), winner, input, radiusSqr, learningRate);
		} else if(map.containsKey(bottomNode)){
			Utils.adjustNeighbourWeight(map.get(bottomNode), winner, input, radiusSqr, learningRate);
		}
	}
}
