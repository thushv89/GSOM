package com.gsom.core;

import com.gsom.core.cluster.ClusterQualityEvaluator;
import com.gsom.core.cluster.SilhouetteCoeffEval;
import com.gsom.enums.InitType;
import com.gsom.enums.InputDataType;
import com.gsom.listeners.GSOMRunListener;

import java.util.Map;
import com.gsom.listeners.InputParsedListener;
import com.gsom.objects.GCluster;
import com.gsom.objects.GNode;
import com.gsom.util.input.parsing.GSOMConstants;
import com.gsom.util.input.parsing.InputParser;
import com.gsom.util.input.parsing.InputParserFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GSOMRun implements InputParsedListener{

    private InputParserFactory parserFactory;
    private InputParser parser;
    private GSOMTrainer trainer;
    private GCoordAdjuster adjuster;
    private GSOMSmoothner smoothner;
    private GSOMTester tester;
    private KMeanClusterer clusterer;
    
    private Map<String, GNode> map;
    private Map<String, String> testResults;
    private ArrayList<ArrayList<GCluster>> allClusters;
    private int bestCCount;
    
    private GSOMRunListener listener;
    
    private long startTime;
    private long endTimeBeforeClustering;
    private long endTimeAfterClustering;
    
    private InitType initType;
    
    private ClusterQualityEvaluator coeffEval;
    
    public GSOMRun(InitType initType,GSOMRunListener listener) {
        this.listener = listener;
        this.initType = initType;
        
        parserFactory = new InputParserFactory();
        trainer = new GSOMTrainer(initType);
        adjuster = new GCoordAdjuster();
        smoothner = new GSOMSmoothner();
        tester = new GSOMTester();
        clusterer = new KMeanClusterer();
        
        coeffEval = new SilhouetteCoeffEval();
    }

    public void runTraining(String fileName, InputDataType type) {

        if (type==InputDataType.FLAGS) {
            parser = parserFactory.getInputParser(InputDataType.FLAGS);
        } else if (type==InputDataType.NUMERICAL) {
            parser = parserFactory.getInputParser(InputDataType.NUMERICAL);
        }
        parser.parseInput(this, fileName);
    }

    private void runAllSteps(){
        
        GSOMConstants.FD = GSOMConstants.SPREAD_FACTOR/GSOMConstants.DIMENSIONS;
        
        map = trainer.trainNetwork(parser.getStrForWeights(), parser.getWeights());
        listener.stepCompleted("GSOM Training completed!");
        
        map = adjuster.adjustMapCoords(map);
        listener.stepCompleted("Node position Adjustment Complete");
        
        map = smoothner.smoothGSOM(map, parser.getWeights());
        listener.stepCompleted("Smoothing phase completed!");
        
        tester.testGSOM(map, parser.getWeights(), parser.getStrForWeights());
        this.testResults = tester.getTestResultMap();
        
        clusterer.runClustering(map);
        this.allClusters = clusterer.getAllClusters();
        this.bestCCount = clusterer.getBestClusterCount();
        
        listener.stepCompleted("Clustering completed!");
        listener.stepCompleted("------------------------------------------------");
        
        listener.executionCompleted();      
        
    }
    

    @Override
    public void inputParseComplete() {
        listener.stepCompleted("Input parsing,normalization completed");
        GSOMConstants.DIMENSIONS = parser.getWeights().get(0).length;
        runAllSteps();
    }

 

    public Map<String, GNode> getGSOMMap() {
        return this.map;
    }

    public Map<String, String> getTestResultMap() {
        return this.testResults;
    }

    public double getSilCoeff(int numCluster){
        //return clusterer.getSilhoutteCoefficient(numCluster);
        coeffEval.evaluate(this.allClusters.get(numCluster-2));
        SilhouetteCoeffEval sCoeffEval = (SilhouetteCoeffEval)coeffEval;
        return sCoeffEval.getSilCoeff();
    }
    
    public ArrayList<ArrayList<GCluster>> getAllClusters() {
        return this.allClusters;
    }

    public int getBestCount(){
        return this.bestCCount;
    }
    
}
