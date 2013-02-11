/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.listeners;

import com.gsom.objects.GCluster;
import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public interface ClusteringListener {
    public void clusteringCompleted(ArrayList<GCluster> clusters);
}
