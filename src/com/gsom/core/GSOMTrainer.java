package com.gsom.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gsom.listeners.GSOMTrainerListener;
import com.gsom.listeners.NodeGrowthListener;
import com.gsom.objects.GNode;
import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;


public class GSOMTrainer implements NodeGrowthListener {

	private Map<String, GNode> nodeMap;
	private NodeGrowthHandler growthHandler;
	
	public GSOMTrainer(){
		nodeMap = new HashMap<String,GNode>();
		growthHandler = new NodeGrowthHandler();
	}
	
	public void trainNetwork(ArrayList<String> iStrings, ArrayList<double[]> iWeights, GSOMTrainerListener listener){
		initFourNodes();	//init the map with four nodes
		for(int i=0;i<GSOMConstants.MAX_ITERATIONS;i++){
                    int k=0;
                    double learningRate = Utils.getLearningRate(i, nodeMap.size());
                    double radius = Utils.getRadius(i, Utils.getTimeConst());
			for(double[] input : iWeights){
				trainForSingleIterAndSingleInput(i,input,iStrings.get(k),learningRate,radius);
                                k++;
			}
		}
		listener.GSOMTrainingCompleted(nodeMap);
	}
	
	private void trainForSingleIterAndSingleInput(int iter,double[] input, String str, double learningRate, double radius){
		
		GNode winner = Utils.selectWinner(nodeMap,input);
                
                if(iter == GSOMConstants.MAX_ITERATIONS-1){
                    /*for(GNode node:nodeMap.values()){
                        System.out.println("Distance from input to node "+node.getX()+","+node.getY()+" is "
                                +Utils.calcEucDist(node.getWeights(), input, GSOMConstants.DIMENSIONS));
                    }*/
                    //System.out.println("winner "+winner.getX()+","+winner.getY());
                }
                if(str.equalsIgnoreCase("duck")){
                    System.out.println("Winner: "+winner.getX()+","+winner.getY());
                }
		winner.calcAndUpdateErr(input);
		
		for(Map.Entry<String,GNode> entry :nodeMap.entrySet()){            
                    entry.setValue(adjustNeighbourWeight(entry.getValue(), winner, input, radius, learningRate));                  
		}
		
		if(winner.getErrorValue()>GSOMConstants.getGT()){
                    //System.out.println("Winner "+winner.getX()+","+winner.getY()+" GT exceeded");
			adjustWinnerError(winner);
		}
	}
        
        //Adjust winner's neighbor weights accordingly
        public GNode adjustNeighbourWeight(GNode node, GNode winner,double[] input,double radius,double learningRate){
		double nodeDistSqr = Math.pow(winner.getX()-node.getX(),2)+Math.pow(winner.getY()- node.getY(),2);
		double radiusSqr = Math.pow(radius, 2);
		//if node is within the radius
		if(nodeDistSqr<radiusSqr){
			double influence = Math.exp(-nodeDistSqr/(2*radiusSqr));
			node.adjustWeights(input, influence, learningRate);
		}
                return node;
	}
        
        //Initialization of the map. 
        //this will create 4 nodes with random weights
	private void initFourNodes() {
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				GNode initNode = new GNode(i, j, Utils.generateRandomArray(GSOMConstants.DIMENSIONS));
				nodeMap.put(Utils.generateIndexString(i, j), initNode);
			}
		}
	}
		
	//when a neuron wins its error value needs to be adjusted
	private void adjustWinnerError(GNode winner){
		
		//on x-axis
		String nodeLeftStr = Utils.generateIndexString(winner.getX()-1, winner.getY());
		String nodeRightStr = Utils.generateIndexString(winner.getX()+1, winner.getY());
		
		//on y-axis
		String nodeTopStr= Utils.generateIndexString(winner.getX(), winner.getY()+1);
		String nodeBottomStr= Utils.generateIndexString(winner.getX(), winner.getY());
		
		if(nodeMap.containsKey(nodeLeftStr)&&
				nodeMap.containsKey(nodeRightStr)&&
				nodeMap.containsKey(nodeTopStr)&&
				nodeMap.containsKey(nodeBottomStr)){
			distrErrToNeighbors(winner,nodeLeftStr,nodeRightStr,nodeTopStr,nodeBottomStr);
		}else{
			growthHandler.growNodes(nodeMap, winner, this); //NodeGrowthHandler takes over
		}
	}

        //When the node growing is complete this event fill be fired
	@Override
	public void nodeGrowthComplete(Map<String, GNode> map) {
		//System.out.println("Node growth Complete");
		
	}

	//distributing error to the neighbors of thw winning node
	private void distrErrToNeighbors(GNode winner,String leftK,String rightK,String topK,String bottomK) {
		winner.setErrorValue(GSOMConstants.getGT()/2);
		nodeMap.get(leftK).setErrorValue(calcErrForNeighbour(nodeMap.get(leftK)));
		nodeMap.get(rightK).setErrorValue(calcErrForNeighbour(nodeMap.get(rightK)));
		nodeMap.get(topK).setErrorValue(calcErrForNeighbour(nodeMap.get(topK)));
		nodeMap.get(bottomK).setErrorValue(calcErrForNeighbour(nodeMap.get(bottomK)));
	}
	
	//error calculating equation for neighbours of a winner
	private double calcErrForNeighbour(GNode node){
		return node.getErrorValue()+(GSOMConstants.FD*node.getErrorValue());
	}
	
}
