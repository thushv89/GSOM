package com.gsom.util;

public class ArrayHelper {

	public static double[] substract(double[] array1,double[] array2,int dimensions) {
		double[] array3 = new double[array1.length];
		for (int i=0;i<dimensions;i++){
			array3[i] = array1[i]-array2[i];
		}	
		return array3;
	}
	public static double[] add(double[] array1,double[] array2, int dimensions) {
		double[] array3 = new double[array1.length];
		for (int i=0;i<dimensions;i++){
			array3[i] = array1[i]+array2[i];
		}
		return array3;
	}
	
	public static double[] multiplyArrayByConst(double[] array, double constant) {
		for (int i=0;i<array.length;i++) {
			array[i]=array[i]*constant;
		}
		return array;
	}
	
	public static double getMin(double[] array){
		double min = Double.MAX_VALUE;
		for(int i=0;i<array.length;i++){
			if(array[i]<min){
				min = array[i];
			}
		}
		return min;
	}
	
	public static double getMax(double[] array){
		double max = 0;
		for(int i=0;i<array.length;i++){
			if(array[i]>max){
				max = array[i];
			}
		}
		return max;
	}
}
