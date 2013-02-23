/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.ui.image_ui.listeners;

import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public interface ImageNetworkControllerListener {
    public void clickedOnImage(String key,ArrayList<String> values);
    public void gridImageLocSet(String path);
}
