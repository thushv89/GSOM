package com.gsom.util.input.parsing;

import com.gsom.enums.NormalizeType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gsom.listeners.InputParsedListener;
import com.gsom.util.ArrayHelper;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;

public abstract class InputParser {

    ArrayList<String> strForWeights;
    ArrayList<double[]> weights;

    public InputParser() {

        strForWeights = new ArrayList<String>();
        weights = new ArrayList<double[]>();
    }

    abstract public void parseInput(InputParsedListener iListener, String fileName);

    protected void normalizeData(NormalizeType nType, ArrayList<double[]> inputs, int dimensions) {
        if (nType == NormalizeType.COLUMN_MAX_1_MIN_0) {
            normalizeVertical(dimensions, inputs);
        } else if (nType == NormalizeType.VECTOR_TOTAL_ADD_UP_TO_1) {
            normalizeHorizontal(dimensions, inputs);
        }
    }

    private void normalizeHorizontal(int dimensions, ArrayList<double[]> inputs) {
        for (double[] vec : inputs) {
            double total = 0;
            for (double val : vec) {
                total += val;
            }
            for (int i = 0; i < vec.length; i++) {
                vec[i] = vec[i] / total;
            }
        }
    }

    private void normalizeVertical(int dimensions, ArrayList<double[]> inputs) {
        ArrayList<Double> maxDimArr = new ArrayList<Double>();
        ArrayList<Double> minDimArr = new ArrayList<Double>();

        for (int i = 0; i < dimensions; i++) {
            double[] dimArr = new double[inputs.size()];
            for (int j = 0; j < inputs.size(); j++) {
                dimArr[j] = inputs.get(j)[i];
            }
            maxDimArr.add(ArrayHelper.getMax(dimArr));
            minDimArr.add(ArrayHelper.getMin(dimArr));
        }

        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < inputs.size(); j++) {
                double[] inArr = inputs.get(j);
                inArr[i] = (inArr[i] - minDimArr.get(i)) / (maxDimArr.get(i) - minDimArr.get(i));
                inputs.set(j, inArr);
            }
        }
    }

    protected boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    protected Color getColor(String color) {
        Color clr = null;

        if (color.equalsIgnoreCase("red")) {
            clr = Color.red;
        } else if (color.equalsIgnoreCase("green")) {
            clr = Color.green;
        } else if (color.equalsIgnoreCase("blue")) {
            clr = Color.blue;
        } else if (color.equalsIgnoreCase("black")) {
            clr = Color.black;
        } else if (color.equalsIgnoreCase("white")) {
            clr = Color.white;
        } else if (color.equalsIgnoreCase("orange")) {
            clr = Color.orange;
        } else if (color.equalsIgnoreCase("gold")) {
            clr = new Color(255, 215, 0);
        } else if (color.equalsIgnoreCase("brown")) {
            clr = new Color(165, 42, 42);
        }

        return clr;
    }

    public ArrayList<String> getStrForWeights() {
        return strForWeights;
    }

    public ArrayList<double[]> getWeights() {
        return weights;
    }

    public void printInput() {
        for (int i = 0; i < strForWeights.size(); i++) {
            System.out.print(strForWeights.get(i) + " ");
            for (int j = 0; j < GSOMConstants.DIMENSIONS; j++) {
                System.out.print(weights.get(i)[j] + ",");

            }
            System.out.println("");
        }

    }
}
