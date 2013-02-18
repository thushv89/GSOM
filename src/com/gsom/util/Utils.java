package com.gsom.util;

import java.util.Map;

import com.gsom.objects.GNode;
import com.gsom.ui.MainWindow;

public class Utils {

    public static double[] generateRandomArray(int dimensions) {
        double[] arr = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            arr[i] = Math.random();
        }
        return arr;
    }

    public static String generateIndexString(int x, int y) {
        return x + "," + y;
    }

    public static double getLearningRate(int iter, int nodeCount) {
        return GSOMConstants.START_LEARNING_RATE * Math.exp(-(double) iter / GSOMConstants.MAX_ITERATIONS) * (1 - (3.8 / nodeCount));
    }

    public static double getTimeConst() {
        return (double) GSOMConstants.MAX_ITERATIONS / Math.log(GSOMConstants.MAX_NEIGHBORHOOD_RADIUS);
    }

    //get the node with the minimal ED to the input (winner)
    public static GNode selectWinner(Map<String, GNode> nodeMap, double[] input) {
        GNode winner = null;
        double currDist = Double.MAX_VALUE;
        double minDist = Double.MAX_VALUE;

        if (MainWindow.distance == 0) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcEucDist(input, entry.getValue().getWeights(), GSOMConstants.DIMENSIONS);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        } else {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcChamDistance(input, entry.getValue().getWeights());

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        }

    }

    public static void adjustNeighbourWeight(GNode node, GNode winner, double[] input, double radius, double learningRate) {
        double nodeDistSqr = Math.pow(Utils.calcEucDist(winner.getWeights(), node.getWeights(), GSOMConstants.DIMENSIONS), 2);
        double radiusSqr = Math.pow(radius, 2);
        //if node is within the radius
        if (nodeDistSqr < radiusSqr) {
            double influence = Math.exp(-nodeDistSqr / (2 * radiusSqr));
            node.adjustWeights(input, influence, learningRate);
        }
    }

    public static double getRadius(int iter, double timeConst) {
        return GSOMConstants.MAX_NEIGHBORHOOD_RADIUS * Math.exp(-(double) iter / timeConst);
    }

    public static double calcEucDist(double[] in1, double[] in2, int dimensions) {
        double dist = 0.0;
        for (int i = 0; i < dimensions; i++) {
            dist += Math.pow(in1[i] - in2[i], 2);
        }

        return Math.sqrt(dist);
    }

    public static double calcChamDistance(double[] vec1, double[] vec2) {

        double minVal;
        double totalMinval = 0.0;
        for (double val1 : vec1) {
            minVal = Double.MAX_VALUE;

            for (double val2 : vec2) {
                if (Math.abs(val1 - val2) < minVal) {
                    minVal = Math.abs(val1 - val2);
                }
            }
            totalMinval += minVal;
        }
        return totalMinval / vec1.length;
    }
}
