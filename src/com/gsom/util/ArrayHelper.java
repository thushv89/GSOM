package com.gsom.util;

public class ArrayHelper {

    public static double[] substract(double[] array1, double[] array2, int dimensions) {
        
        double[] arr1_copy = array1.clone();
        double[] arr2_copy = array2.clone();
        double[] array3 = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            array3[i] = arr1_copy[i] - arr2_copy[i];
        }
        return array3;
    }

    public static double[] add(double[] array1, double[] array2, int dimensions) {
        double[] arr1_copy = array1.clone();
        double[] arr2_copy = array2.clone();
        
        double[] array3 = new double[array1.length];
        for (int i = 0; i < dimensions; i++) {
            array3[i] = arr1_copy[i] + arr2_copy[i];
        }
        return array3;
    }

    public static double[] multiplyArrayByConst(double[] array, double constant) {
        double[] arr_copy = array.clone();
        
        for (int i = 0; i < array.length; i++) {
            arr_copy[i] = arr_copy[i] * constant;
        }
        return arr_copy;
    }

    public static double getMin(double[] array) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static double getMax(double[] array) {
        double max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}
