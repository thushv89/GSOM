/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core.cluster;

import com.gsom.objects.GCluster;
import com.gsom.objects.GNode;
import java.util.ArrayList;

/**
 *
 * @author Thush
 */
public class SilhouetteCoeffEval extends ClusterQualityEvaluator{

    private double silCoeff = 0.0;
    @Override
    public void evaluate(ArrayList<GCluster> clusters) {
        silCoeff = getSilhoutteCoefficient(clusters);
    }
    
    public double getSilhoutteCoefficient(ArrayList<GCluster> clusters){
        double totalS = 0.0;
        double totalNodes = 0;
        ArrayList<GCluster> kClusters = clusters;
        for(GCluster cl : kClusters){
            for(GNode n : cl.getcNodes()){
                double a = getA(n, kClusters);
                double b = getB(n, kClusters);
                totalS += getS(a, b);
                totalNodes++;
            }
        }
        
        return totalS/totalNodes;
    }
    
    private double getCosineDist(double[] vec1,double[] vec2,int dimension){
        double dotTotal = 0.0;
        for(int i=0;i<dimension;i++){
            dotTotal += vec1[i]*vec2[i];
        }
        return dotTotal/(dimension*dimension);
    }
    
    private double getNodeDistToCluster(GNode n,GCluster c){
        double distTotal =0.0;
        for(GNode temp : c.getcNodes()){
            distTotal += getCosineDist(n.getWeights(), temp.getWeights(), n.getWeights().length);
        }
        return distTotal/c.getcNodes().size();
    }
    
    private double getA(GNode n,ArrayList<GCluster> clusters){
        for(GCluster cl : clusters){
            if(cl.getcNodes().contains(n)){
                return getNodeDistToCluster(n, cl);
            }
        }
        return Double.MAX_VALUE;
    }
    
    private double getB(GNode n, ArrayList<GCluster> clusters){
        double minDist=Double.MAX_VALUE;
        for(GCluster cl : clusters){
            if(cl.getcNodes().contains(n)){
                continue;
            }
            minDist = Math.min(minDist, getNodeDistToCluster(n, cl));
        }
        return minDist;
    }
    
    private double getS(double a,double b){
        return (b-a)/Math.max(a, b);
    }

    /**
     * @return the silCoeff
     */
    public double getSilCoeff() {
        return silCoeff;
    }
}
