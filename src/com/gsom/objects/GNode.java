package com.gsom.objects;

import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;

public class GNode {

	private int X;
	private int Y;
	private double[] weights;
	private double errorValue;
	private int hitValue;
        
	public GNode(int x,int y,double[] weights){
		this.X = x;
		this.Y = y;
		this.weights = weights;
	}	
			
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public void calcAndUpdateErr(double[] iWeight){
		this.errorValue += Utils.calcEucDist(this.weights, iWeight,GSOMConstants.DIMENSIONS);
	}
	
	public double getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(double errorValue) {
		this.errorValue = errorValue;
	}
	
	public void adjustWeights(double[] iWeights,double influence,double learningRate){

		for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
			weights[i] += influence*learningRate*(iWeights[i]-weights[i]);
		}
	}

    /**
     * @return the hitValue
     */
    public int getHitValue() {
        return hitValue;
    }

    /**
     * @param hitValue the hitValue to set
     */
    public void setHitValue(int hitValue) {
        this.hitValue = hitValue;
    }
        
        
}
