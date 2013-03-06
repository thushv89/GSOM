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
public class ELKIEval implements ClusterQualityEvaluator{

    ArrayList<double[]> means;
    ArrayList<double[]> stdDevs;
    
    public ELKIEval(){
        means = new ArrayList<double[]>();
        stdDevs = new ArrayList<double[]>();
    }
    
    @Override
    public void evaluate(ArrayList<GCluster> clusters) {
        for(GCluster cluster : clusters){
            means.add(calcMean(cluster));
            stdDevs.add(calcStdDev(cluster));
        }
    }
    
    private double[] calcMean(GCluster c){
        double[] vec = new double[c.getCentroidWeights().length];
        for(GNode n: c.getcNodes()){
            for(int i=0;i<n.getWeights().length;i++){
                vec[i] += n.getWeights()[i];
            }
        }
        for(double val : vec){
            val = val/c.getcNodes().size();
        }
        
        return vec;
        
    }
    
    private double[] calcStdDev(GCluster c){
        double[] vec = new double[c.getCentroidWeights().length];
        double[] meanVec = calcMean(c);
        for(GNode n: c.getcNodes()){
            for(int i=0;i<n.getWeights().length;i++){
                vec[i] += Math.pow(n.getWeights()[i]-meanVec[i],2);
            }
        }
        for(double val : vec){
            val = Math.sqrt(val/c.getcNodes().size());
        }
        
        return vec;
    }
    
}
