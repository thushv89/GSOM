/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.objects;

import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Thush
 */
public class GCluster {
    
    private double[] centroidWeights;
    private ArrayList<GNode> cNodes;
    
    public GCluster(double[] centroidWeights) {
        cNodes = new ArrayList<GNode>();
        this.centroidWeights = centroidWeights;
    }


    public void addNeuron(GNode node){
        cNodes.add(node);
    }
    
    //similar to avg distance between this cluster centroid and all the nodes in the cluster
    public double getSI(){
        double SI2=0;
        for(GNode node: cNodes){
            SI2 += Math.pow(Utils.calcEucDist(node.getWeights(), centroidWeights, GSOMConstants.DIMENSIONS),2);
        }
        
        return Math.sqrt(SI2/cNodes.size());
    }
    /**
     * @return the centroidWeights
     */
    public double[] getCentroidWeights() {
        return centroidWeights;
    }

    /**
     * @param centroidWeights the centroidWeights to set
     */
    public void setCentroidWeights(double[] centroidWeights) {
        this.centroidWeights = centroidWeights;
    }

    /**
     * @return the cNodes
     */
    public ArrayList<GNode> getcNodes() {
        return cNodes;
    }

    /**
     * @param cNodes the cNodes to set
     */
    public void setcNodes(ArrayList<GNode> cNodes) {
        this.cNodes = cNodes;
    }
    
}
