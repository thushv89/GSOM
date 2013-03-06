/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core.cluster;

import com.gsom.objects.GCluster;
import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public abstract class ClusterQualityEvaluator {
    
    public abstract void evaluate(ArrayList<GCluster> clusters);
}
