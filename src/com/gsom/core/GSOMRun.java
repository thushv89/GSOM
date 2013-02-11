package com.gsom.core;

import com.gsom.enums.InputDataType;
import com.gsom.listeners.ClusteringListener;
import com.gsom.listeners.GSOMRunListener;
import com.gsom.listeners.GSOMSmoothnerListener;
import java.util.Map;

import com.gsom.listeners.GSOMTrainerListener;
import com.gsom.listeners.InputParsedListener;
import com.gsom.listeners.NodePositionAdjustListener;
import com.gsom.objects.GCluster;
import com.gsom.objects.GNode;
import com.gsom.util.GSOMConstants;
import com.gsom.util.InputParser;
import com.gsom.util.InputParserFactory;
import java.util.ArrayList;

public class GSOMRun implements InputParsedListener,GSOMTrainerListener,NodePositionAdjustListener,GSOMSmoothnerListener,ClusteringListener{

	InputParserFactory parserFactory;
        InputParser parser;
	GSOMTrainer trainer;
	GCoordAdjuster adjuster;
        GSOMSmoothner smoothner;
        GSOMTester tester;
        KMeanClusterer clusterer;
	Map<String,GNode> map;
	Map<String,String> testResults;
        ArrayList<GCluster> clusters;
        GSOMRunListener listener;
        
    public GSOMRun(GSOMRunListener listener) {
        this.listener = listener;
    }
        
        
	public void runTraining(){
		parserFactory = new InputParserFactory();
		trainer = new GSOMTrainer();
		adjuster = new GCoordAdjuster();
                smoothner = new GSOMSmoothner();
		tester = new GSOMTester();
                clusterer = new KMeanClusterer();
                
                String fileName = "flags.txt";
                if(fileName.contains("flags")){
                    parser = parserFactory.getInputParser(InputDataType.FLAGS);
                }else if (fileName.contains("zoo")){
                    parser = parserFactory.getInputParser(InputDataType.ZOO);
                }
                
                parser.parseInput(this, fileName);
	}
	
	@Override
	public void nodePositionAdjustComplete(Map<String, GNode> map) {
		// TODO Auto-generated method stub
		System.out.println("Node position Adjustment Complete");
                listener.stepCompleted("Node position Adjustment Complete");
                this.map = map;
                
                //---------Used for testing
                /*
                for(GNode node: map.values()){
                    if(node.getHitValue()>=1){
                        System.out.println("Node "+node.getX()+","+node.getY()+" hit value is "+node.getHitValue());
                    }
                }        
                for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
                    ArrayList<GNode> maplist = new ArrayList<GNode>(map.values());
                    System.out.println(maplist.get(0).getWeights()[i]+", "
                            +maplist.get(10).getWeights()[i]+", "
                            +maplist.get(20).getWeights()[i]+", "
                            +maplist.get(30).getWeights()[i]+", "
                            +maplist.get(40).getWeights()[i]);
                }*/
                smoothner.smoothGSOM(map, parser.getWeights(), this);                                
               
	}

	@Override
	public void GSOMTrainingCompleted(Map<String, GNode> map) {
		System.out.println("GSOM Training completed!");
                listener.stepCompleted("GSOM Training completed!");
		adjuster.adjustMapCoords(map, this);
		
	}

	@Override
	public void inputParseComplete() {
            listener.stepCompleted("Input parsing,normalization completed");
		trainer.trainNetwork(parser.getStrForWeights(), parser.getWeights(), this);
	}
        
        public void runTesting(Map<String,GNode> map,ArrayList<double[]> iWeights,ArrayList<String> iStrings){
            testResults = tester.testGSOM(map, iWeights, iStrings);
        }
        
        public Map<String,GNode> getGSOMMap(){
            return this.map;
        }
        
        public Map<String,String> getTestResultMap(){
            return this.testResults;
        }
        
        public ArrayList<GCluster> getClusters(){
            return this.clusters;
        }

    @Override
    public void smootheningCompleted(Map<String, GNode> map) {
        listener.stepCompleted("Smoothing phase completed!");
        runTesting(map, parser.getWeights(), parser.getStrForWeights());
        clusterer.runClustering(map,this);
    }

    @Override
    public void clusteringCompleted(ArrayList<GCluster> clusters) {
        listener.stepCompleted("Clustering completed!");
        this.clusters = clusters;
        for(Map.Entry<String,String> entry: testResults.entrySet()){
            System.out.println(entry.getKey() +" - "+ entry.getValue());
        }
    }
}
