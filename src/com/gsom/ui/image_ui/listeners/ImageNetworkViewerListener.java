/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui.listeners;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public interface ImageNetworkViewerListener {
    public void readyToCalc(String outputPath,String dirPath);
    public void clickedOnImage(String key,ArrayList<String> values);
    public void saveImageClicked(String path, BufferedImage img);
    public void saveLocBrowseClicked(String path);
}
