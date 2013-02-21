/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui;

import com.gsom.ui.image_ui.listeners.ImageNetworkControllerListener;
import com.gsom.ui.image_ui.listeners.ImageNetworkViewerListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Thush
 */
public class ImageNetworkController implements ImageNetworkViewerListener{
    
    private ImageNetworkViewer view;
    private ImageNetworkModel model;
    private UIValues uiVal;
    private ImageNetworkControllerListener listener;
    private ImageNetworkHelper helper;
    
    public ImageNetworkController(ImageNetworkControllerListener listener){
        this.listener = listener;
        view = new ImageNetworkViewer();
        helper = new ImageNetworkHelper();
        uiVal = new UIValues();
        view.setImageNetworkViewerListener(this);
    }
    
    
    private void populateModel(Map<String, ArrayList<String>> map){
        //TODO: populate the model
        //TODO: Show the view here
        
        model = new ImageNetworkModel();
        model.setHitAndImageMap(map);
        
    }
    
    public void displayView(){
        view.setVisible(true);
    }

    @Override
    public void readyToCalc(String outputPath, String dirPath) {
        UIValues.setINPUT_FILE_LOCATION(outputPath);
        UIValues.setIMAGE_FOLDER_LOCATION(dirPath);
        createTheMap(outputPath);
        Map<String,ArrayList<String>> map = helper.valueSeparator(UIValues.getMap());
        populateModel(map);
        view.populateAndView(model);

    }
    
    //read the given file and set UIValues values
    private void createTheMap(String fileName){
    
        ImageNetworkHelper.setUIValues(fileName);
    
    }

    @Override
    public void clickedOnImage(String key,ArrayList<String> values) {
        listener.clickedOnImage(key,values);
    }
}
