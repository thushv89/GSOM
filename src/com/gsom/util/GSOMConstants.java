package com.gsom.util;

import org.jfree.date.SpreadsheetDate;

public class GSOMConstants {

	public static int DIMENSIONS = 17;
        //zoo=17,letter=16,ashes=13, histogram=5, chrime=10,EH_Position=16,texture_proportion=5
	private static double GT;
//	public static double SPREAD_FACTOR = 0.3;
//	public static double MAX_NEIGHBORHOOD_RADIUS = 4;
//	public static double FD = 0.1;
//	public static double START_LEARNING_RATE = 0.3;
//	public static int MAX_ITERATIONS = 400;

        public static double SPREAD_FACTOR;
	public static double MAX_NEIGHBORHOOD_RADIUS;
	public static double FD;
	public static double START_LEARNING_RATE;
	public static int MAX_ITERATIONS;
        
        public static double getGT(){
            GT = - DIMENSIONS * DIMENSIONS * Math.log(SPREAD_FACTOR);
            return GT;
        }
}
