package com.gsom.util;

import org.jfree.date.SpreadsheetDate;

public class GSOMConstants {

	public static final int DIMENSIONS = 35;
	private static double GT;
	public static final double SPREAD_FACTOR = 0.3;
	public static final double MAX_NEIGHBORHOOD_RADIUS = 4;
	public static final double FD = 0.1;
	public static final double START_LEARNING_RATE = 0.3;
	public static final int MAX_ITERATIONS = 400;
        
        public static double getGT(){
            GT = - DIMENSIONS* DIMENSIONS * Math.log(SPREAD_FACTOR);
            return GT;
        }
}
