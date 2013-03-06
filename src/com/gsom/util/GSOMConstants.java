package com.gsom.util;


public class GSOMConstants {

	public static int DIMENSIONS;

	private static double GT;

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
