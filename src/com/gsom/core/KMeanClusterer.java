/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core;

import com.gsom.listeners.ClusteringListener;
import com.gsom.objects.GCluster;
import com.gsom.objects.GNode;
import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;
import java.util.*;

/**
 *
 * @author Thush
 */
public class KMeanClusterer {

    private GCluster[] clusters;
    private Map<String,GNode> map;
    
    public void runClustering(Map<String,GNode> map,ClusteringListener listener){
        this.map = map;
        ArrayList<ArrayList<GCluster>> kClusterList = new ArrayList<ArrayList<GCluster>>();
        kClusterList.addAll(getAllClusterLists(map.size()));
        int bestCount = getBestClusterCount(kClusterList);
        ArrayList<GCluster> bestClusters = kClusterList.get(bestCount);
        //clusters = bestClusters;
        System.out.println("Best count: "+(bestCount+2));
        listener.clusteringCompleted(bestClusters);
    }

    //get All posible cluster lists
    private ArrayList<ArrayList<GCluster>> getAllClusterLists(int mapSize) {
        
        ArrayList<ArrayList<GCluster>> clusterList = new ArrayList<ArrayList<GCluster>>();
        
        int kMax = (int) Math.ceil(Math.sqrt(mapSize));        
        for (int i=2;i<=kMax;i++){
            if(getKClusters(i)!= null){
                clusterList.add(getKClusters(i));
            }
        }
        return clusterList;
    }

    //get cluster list with "k" number of clusters
    private ArrayList<GCluster> getKClusters(int k) {
        
        ArrayList<GCluster> clusterList = new ArrayList<GCluster>();
        ArrayList<GNode> clusterCentroids = getHighestHitNeurons(k);
        
        if(clusterCentroids == null){
            return null;
        }
        
        for(GNode node: clusterCentroids){
            GCluster cluster = new GCluster(node.getWeights());
            clusterList.add(cluster);
        }
        assignNeuronsToClusters(clusterList);
        
        return clusterList;
        
    }

    //get the center nodes of the "k" number of cluster
    private ArrayList<GNode> getHighestHitNeurons(int k) {
        ArrayList<GNode> nodeList = new ArrayList<GNode>();
        nodeList.addAll(map.values());
        ArrayList<GNode> highestKNuerons = new ArrayList<GNode>();
        
        Collections.sort(nodeList, new Comparator<GNode>(){

            @Override
            public int compare(GNode o1, GNode o2) {
                if(o1.getHitValue()>o2.getHitValue()){
                    return 1;
                }else if(o2.getHitValue()>o1.getHitValue()){
                    return -1;
                }else{
                    return 0;
                }
                
            }         
        });
        
        for(int i=nodeList.size()-1;i>(nodeList.size()-1)-k;i--){
            if(nodeList.get(i).getHitValue()<=0){
                return null;
            }
            highestKNuerons.add(nodeList.get(i));
        }
        
        return highestKNuerons;
    }

    //find other neurons belonging to this cluster
    private void assignNeuronsToClusters(ArrayList<GCluster> clusters) {
        
        for(GNode node : map.values()){
            double minDist = Double.MAX_VALUE;
            GCluster winnerCluster = null;
            
            for(int i=0;i<clusters.size();i++){
                double dist = Utils.calcEucDist(node.getWeights(), clusters.get(i).getCentroidWeights(), GSOMConstants.DIMENSIONS);
                if(dist<minDist){
                    winnerCluster = clusters.get(i);
                    minDist = dist;
                }
            }
            if(winnerCluster != null){
                winnerCluster.addNeuron(node);
            }
        }
    }

    private int getBestClusterCount(ArrayList<ArrayList<GCluster>> kClusters) {
        double R = Double.MAX_VALUE;
        int bestClusterCount = 2;
        int kMax = (int) Math.ceil(Math.sqrt(map.size()));
        for(int i=2;i<=kMax && i<kClusters.size();i++){
            double temp = getR(kClusters.get(i-2));
            if(temp <= R){
                R= temp;
                bestClusterCount = i-2;
            }
        }
        return bestClusterCount;
    }
    
    //all the inter-cluster distances
    private HashMap<String,Double> getAllMIJs(ArrayList<GCluster> clusters){
        HashMap<String,Double> MIJs = new HashMap<String, Double>();
        for(int i=0;i<clusters.size();i++){
            for(int j=0;j<clusters.size();j++){
                MIJs.put(getMIJIndex(i, j), getMIJ(clusters.get(i), clusters.get(j)));
            }
        }
        return MIJs;
    }
    
    //this is inter cluster distance of i,j clusters
    private double getMIJ(GCluster i,GCluster j){
        return Utils.calcEucDist(i.getCentroidWeights(), j.getCentroidWeights(), GSOMConstants.DIMENSIONS);
    }
    
    //returns a string that identifies 2 clusters together
    private String getMIJIndex(int i,int j){
        return "M"+i+"_"+j;
    }
    
    //add avarage node distance to centroid in i & j clusters,
    //divide this by the distance between two clusters
    //do this from i th cluster to all other clusters
    //get the highest such distance
    private double getRI(ArrayList<GCluster> clusters, int i){
        HashMap<String,Double> allMIJs = new HashMap<String, Double>();
        allMIJs = getAllMIJs(clusters);
        ArrayList<Double> RIJs = new ArrayList<Double>();
        
        for(int j=0;j<clusters.size();j++){
            if(j!=i){
                RIJs.add((clusters.get(i).getSI()+clusters.get(j).getSI())/allMIJs.get(getMIJIndex(i, j)));
            }
        }
        
        Collections.sort(RIJs);
        return RIJs.get(RIJs.size()-1);
    }
    
    //add all RIs for all clusters and get the average
    private double getR(ArrayList<GCluster> clusters){
        double Rn = 0;
        for(int i=0;i<clusters.size();i++){
            Rn += getRI(clusters,i);
        }
        return Rn/clusters.size();
    }
}
