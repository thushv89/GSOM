/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui;

import java.util.HashMap;

/**
 *
 * @author Ruchira
 */
public class UIValues {

    private static HashMap<String, String> map;
    private static String INPUT_FILE_LOCATION;
    private static String IMAGE_FOLDER_LOCATION;
    private static String REQUIRED_GSOM_NODE;
    private static int X_MIN,X_MAX,Y_MIN,Y_MAX;
    private static int X_GRID_COUNT,Y_GRID_COUNT;


    /**
     * Set X,Y min max values, this filename can be used to handle multiple types
     * feature extraction files using a HashMap<String,ArrayList<int>>
     * -- this HashMap is not implemented yet
     * @param fileName
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     */
    public static void addMinMaxValues(String fileName,int xMin,int xMax, int yMin,int yMax){
        X_MIN = xMin;
        X_MAX = xMax;
        Y_MIN = yMin;
        Y_MAX = yMax;
    }

    /**
     * @return the INPUT_FILE_LOCATION
     */
    public static String getINPUT_FILE_LOCATION() {
        return INPUT_FILE_LOCATION;
    }

    /**
     * @param aINPUT_FILE_LOCATION the INPUT_FILE_LOCATION to set
     */
    public static void setINPUT_FILE_LOCATION(String aINPUT_FILE_LOCATION) {
        INPUT_FILE_LOCATION = aINPUT_FILE_LOCATION;
    }

    /**
     * @return the map
     */
    public static HashMap<String, String> getMap() {
        return map;
    }

    /**
     * @param aMap the map to set
     */
    public static void setMap(HashMap<String, String> aMap) {
        map = aMap;
    }

    /**
     * @return the IMAGE_FOLDER_LOCATION
     */
    public static String getIMAGE_FOLDER_LOCATION() {
        return IMAGE_FOLDER_LOCATION;
    }

    /**
     * @param aIMAGE_FOLDER_LOCATION the IMAGE_FOLDER_LOCATION to set
     */
    public static void setIMAGE_FOLDER_LOCATION(String aIMAGE_FOLDER_LOCATION) {
        IMAGE_FOLDER_LOCATION = aIMAGE_FOLDER_LOCATION;
    }

    /**
     * @return the REQUIRED_GSOM_NODE
     */
    public static String getREQUIRED_GSOM_NODE() {
        return REQUIRED_GSOM_NODE;
    }

    /**
     * @param aREQUIRED_GSOM_NODE the REQUIRED_GSOM_NODE to set
     */
    public static void setREQUIRED_GSOM_NODE(String aREQUIRED_GSOM_NODE) {
        REQUIRED_GSOM_NODE = aREQUIRED_GSOM_NODE;
    }

    /**
     * @return the X_MIN
     */
    public static int getX_MIN() {
        return X_MIN;
    }

    /**
     * @param aX_MIN the X_MIN to set
     */
    public static void setX_MIN(int aX_MIN) {
        X_MIN = aX_MIN;
    }

    /**
     * @return the X_MAX
     */
    public static int getX_MAX() {
        return X_MAX;
    }

    /**
     * @param aX_MAX the X_MAX to set
     */
    public static void setX_MAX(int aX_MAX) {
        X_MAX = aX_MAX;
    }

    /**
     * @return the YMIN
     */
    public static int getYMIN() {
        return Y_MIN;
    }

    /**
     * @param aYMIN the YMIN to set
     */
    public static void setYMIN(int aYMIN) {
        Y_MIN = aYMIN;
    }

    /**
     * @return the Y_MAX
     */
    public static int getY_MAX() {
        return Y_MAX;
    }

    /**
     * @param aY_MAX the Y_MAX to set
     */
    public static void setY_MAX(int aY_MAX) {
        Y_MAX = aY_MAX;
    }

    /**
     * @return the X_GRID_COUNT
     */
    public static int getXGridCount() {
        return X_GRID_COUNT;
    }

    /**
     * @param aX_GRID_COUNT the X_GRID_COUNT to set
     */
    public static void setXGridCount(int aX_GRID_COUNT) {
        X_GRID_COUNT = aX_GRID_COUNT;
    }

    /**
     * @return the Y_GRID_COUNT
     */
    public static int getYGridCount() {
        return Y_GRID_COUNT;
    }

    /**
     * @param aY_GRID_COUNT the Y_GRID_COUNT to set
     */
    public static void setYGridCount(int aY_GRID_COUNT) {
        Y_GRID_COUNT = aY_GRID_COUNT;
    }
}
