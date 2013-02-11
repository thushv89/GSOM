/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.listeners;

import com.gsom.objects.GNode;
import java.util.Map;

/**
 *
 * @author Thush
 */
public interface GSOMSmoothnerListener {
    
    public void smootheningCompleted(Map<String,GNode> map);
    
}
