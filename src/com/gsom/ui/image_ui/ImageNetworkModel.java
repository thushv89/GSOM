/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Thush
 */
public class ImageNetworkModel {
    
    private Map<String,ArrayList<String>> hitAndImageMap;

    /**
     * @return the hitAndImageMap
     */
    public Map<String,ArrayList<String>> getHitAndImageMap() {
        return hitAndImageMap;
    }

    /**
     * @param hitAndImageMap the hitAndImageMap to set
     */
    public void setHitAndImageMap(Map<String,ArrayList<String>> hitAndImageMap) {
        this.hitAndImageMap = hitAndImageMap;
    }
    
    
}
