/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui;

import com.gsom.ui.image_ui.listeners.SingleNodeImageViewerListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public class SingleNodeImageController implements SingleNodeImageViewerListener{
    
    private SingleNodeImageViewer view;
    private SingleNodeImageModel model;
    private String saveDirStr;
    
    public SingleNodeImageController(){
        view = new SingleNodeImageViewer();
        view.setSingleNodeViewerListener(this);
        model = new SingleNodeImageModel();
    }
    
    public void setDirPath(String path){
        this.saveDirStr = path;
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

    @Override
    public void saveImageClicked(BufferedImage img) {
        String node = model.getHitNode();
        String tokens[] = node.split(",");
        if(tokens.length>1 && img != null){
            String fName = "X"+tokens[0]+""+"Y"+tokens[1];        
            ImageGridHelper.saveImage(saveDirStr+fName, img);
        }
    }
}
