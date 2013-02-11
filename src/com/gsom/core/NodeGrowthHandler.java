package com.gsom.core;

import java.util.Map;

import javax.rmi.CORBA.Util;

import com.gsom.listeners.NodeGrowthListener;
import com.gsom.objects.GNode;
import com.gsom.util.ArrayHelper;
import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;

public class NodeGrowthHandler {
    //This class is responsible of growing nodes in the GSOM map.
    //It'll check the free spaces around the winning neuron and grow new nodes accordingly
    //Also, it'll assign optimal weights for the new neurons
	Map<String,GNode> map;
	
	public void growNodes(Map<String,GNode> map, GNode winner, NodeGrowthListener listener){
		this.map = map;
		
		int X = winner.getX();
		int Y = winner.getY();
		
		for(int i=X-1;i<=X+1;i=i+2){
			String nodeStr = Utils.generateIndexString(i, Y);
			if(!map.containsKey(nodeStr)){
				//grow new node
				GNode newNode = new GNode(i, Y, getNewNodeWeights(winner, i, Y));
				//System.out.println("Node "+X+","+Y+" grown from Node "+i+","+Y);
				map.put(Utils.generateIndexString(i, Y), newNode);
			}
		}
		for(int i=Y-1;i<=Y+1;i=i+2){
			String nodeStr = Utils.generateIndexString(X, i);
			if(!map.containsKey(nodeStr)){
				//grow new node
				GNode newNode = new GNode(X, i, getNewNodeWeights(winner, X, i));
				//System.out.println("Node "+X+","+Y+" grown from Node "+X+","+i);
				map.put(Utils.generateIndexString(X, i), newNode);
			}
		}
		
		listener.nodeGrowthComplete(map);
		return;
	}
	
	//calc and get weights for the new node
	private double[] getNewNodeWeights(GNode winner, int X, int Y){
		
		double[] newWeights= new double[GSOMConstants.DIMENSIONS];
		
		if(winner.getY()==Y){
			//two consecutive nodes 
			
			//winnerX,otherX
			if(X == winner.getX()+1){
				String nextNodeStr = Utils.generateIndexString(X+1, Y);
				String othrSideNodeStr = Utils.generateIndexString(X-2, Y);
				String topNodeStr = Utils.generateIndexString(winner.getX(), Y+1);
				String botNodeStr = Utils.generateIndexString(winner.getX(), Y-1);
				
				//new node has one direct neighbor, 
				//but neighbor has a neighbor in the opposing directly
				if(map.containsKey(othrSideNodeStr)){
					newWeights = newWeightsForNewNodeInMiddle(winner, othrSideNodeStr);
				}
				else if(map.containsKey(nextNodeStr)){
					//2 consecutive nodes on right
					newWeights = newWeightsForNewNodeOnOneSide(winner, nextNodeStr);
				}
				else if(map.containsKey(topNodeStr)){
					//right and top nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, topNodeStr);
				}
				else if(map.containsKey(botNodeStr)){
					//right and bottom nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, botNodeStr);
				}
				else {
					newWeights = newWeightsForNewNodeOneOlderNeighbor(winner);
				}
			}
			//otherX,winnerX
			else if(X == winner.getX()-1){
				String nextNodeStr = Utils.generateIndexString(X-1, Y);
				String othrSideNodeStr = Utils.generateIndexString(X+2, Y);
				String topNodeStr = Utils.generateIndexString(winner.getX(), Y+1);
				String botNodeStr = Utils.generateIndexString(winner.getX(), Y-1);
				
				//new node has one direct neighbor, 
				//but neighbor has a neighbor in the opposing directly
				if(map.containsKey(othrSideNodeStr)){
					newWeights = newWeightsForNewNodeInMiddle(winner, othrSideNodeStr);
				}
				else if(map.containsKey(nextNodeStr)){
					//2 consecutive nodes on left
					newWeights = newWeightsForNewNodeOnOneSide(winner, nextNodeStr);
				}
				else if(map.containsKey(topNodeStr)){
					//left and top nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, topNodeStr);
				}
				else if(map.containsKey(botNodeStr)){
					//left and bottom nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, botNodeStr);
				}
				else {
					newWeights = newWeightsForNewNodeOneOlderNeighbor(winner);
				}
			}
			
			
			//new node is in the middle of two older nodes
		}else if(winner.getX()==X){
			
			//otherY
			//winnerY
			if(Y == winner.getY()+1){
				String nextNodeStr = Utils.generateIndexString(X, Y+1);
				String othrSideNodeStr = Utils.generateIndexString(X, Y-2);
				String leftNodeStr = Utils.generateIndexString(X-1, winner.getY());
				String rightNodeStr = Utils.generateIndexString(X+1, winner.getY());
				
				//new node has one direct neighbor, 
				//but neighbor has a neighbor in the opposing directly
				if(map.containsKey(othrSideNodeStr)){
					newWeights = newWeightsForNewNodeInMiddle(winner, othrSideNodeStr);
				}
				else if(map.containsKey(nextNodeStr)){
					//2 consecutive nodes upwards
					newWeights = newWeightsForNewNodeOnOneSide(winner, nextNodeStr);
				}
				else if(map.containsKey(leftNodeStr)){
					//left and top nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, leftNodeStr);
				}
				else if(map.containsKey(rightNodeStr)){
					//right and top nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, rightNodeStr);
				}
				else {
					newWeights = newWeightsForNewNodeOneOlderNeighbor(winner);
				}
			}
			//winnerY
			//otherY
			else if(Y == winner.getY()-1){
				String nextNodeStr = Utils.generateIndexString(X, Y-1);
				String othrSideNodeStr = Utils.generateIndexString(X, Y+2);
				String leftNodeStr = Utils.generateIndexString(X-1, winner.getY());
				String rightNodeStr = Utils.generateIndexString(X+1, winner.getY());
				
				//new node has one direct neighbor, 
				//but neighbor has a neighbor in the opposing directly
				if(map.containsKey(othrSideNodeStr)){
					newWeights = newWeightsForNewNodeInMiddle(winner, othrSideNodeStr);
				}
				else if(map.containsKey(nextNodeStr)){
					//2 consecutive nodes on left
					newWeights = newWeightsForNewNodeOnOneSide(winner, nextNodeStr);
				}
				else if(map.containsKey(leftNodeStr)){
					//left and top nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, leftNodeStr);
				}
				else if(map.containsKey(rightNodeStr)){
					//left and bottom nodes
					newWeights = newWeightsForNewNodeOnOneSide(winner, rightNodeStr);
				}
				else {
					newWeights = newWeightsForNewNodeOneOlderNeighbor(winner);
				}
			}
		}
		
		for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
			if(newWeights[i]<0){
				newWeights[i]=0;
			}
		}
		return newWeights;
	}
	
	//node1,new_node,node2
	private double[] newWeightsForNewNodeInMiddle(GNode winner, String otherNodeIdx){
		double[] newWeights;
		GNode otherNode = map.get(otherNodeIdx);
		newWeights = ArrayHelper.add(winner.getWeights(), otherNode.getWeights(),GSOMConstants.DIMENSIONS);
		newWeights = ArrayHelper.multiplyArrayByConst(newWeights,0.5);
		return newWeights;
	}
	
	//node1,node2,new_node or new_node,node1,node2
	private double[] newWeightsForNewNodeOnOneSide(GNode winner, String otherNodeIdx){
		double[] newWeights;
		GNode otherNode = map.get(otherNodeIdx);
		newWeights = ArrayHelper.multiplyArrayByConst(winner.getWeights(), 2);
		newWeights = ArrayHelper.substract(newWeights, otherNode.getWeights(), GSOMConstants.DIMENSIONS);
		return newWeights;
	}
	
        //winner,new node
	private double[] newWeightsForNewNodeOneOlderNeighbor(GNode winner){
		double[] newWeights = new double[GSOMConstants.DIMENSIONS];
		double min = ArrayHelper.getMin(winner.getWeights());
		double max = ArrayHelper.getMax(winner.getWeights());
		for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
			newWeights[i]=(min+max)/2;
		}
		return newWeights;
	}
}
