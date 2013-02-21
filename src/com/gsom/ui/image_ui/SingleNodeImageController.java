/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui;

import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public class SingleNodeImageController {
    
    private SingleNodeImageViewer view;
    private SingleNodeImageModel model;
    public SingleNodeImageController(){
        view = new SingleNodeImageViewer();
        model = new SingleNodeImageModel();
    }
    
    private void populateModel(String hitNode,ArrayList<String> fileNames){
        model.setHitNode(hitNode);
        model.setImgList(fileNames);
    }
    
    public void showView(String hitNode,ArrayList<String> imgList){
        
        populateModel(hitNode, imgList);
        view.setModelToView(model);
        view.setVisible(true);
    }
}
