package com.gsom.core;

import com.gsom.enums.InitType;
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

public class GSOMRun implements InputParsedListener, GSOMTrainerListener, NodePositionAdjustListener, GSOMSmoothnerListener, ClusteringListener {

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
    
    public GSOMRun(InitType initType,GSOMRunListener listener) {
        this.listener = listener;
        this.initType = initType;
    }

    public void runTraining(String fileName, InputDataType type) {
        startTime = System.currentTimeMillis();
        parserFactory = new InputParserFactory();
        trainer = new GSOMTrainer(initType);
        adjuster = new GCoordAdjuster();
        smoothner = new GSOMSmoothner();
        tester = new GSOMTester();
        clusterer = new KMeanClusterer();

        //String fileName = "texture_proportion.txt";
        if (type==InputDataType.FLAGS) {
            parser = parserFactory.getInputParser(InputDataType.FLAGS);
        } else if (type==InputDataType.ASHES) {
            parser = parserFactory.getInputParser(InputDataType.ASHES);
        } else if (type==InputDataType.NUMERICAL) {
            parser = parserFactory.getInputParser(InputDataType.NUMERICAL);
        }
        parser.parseInput(this, fileName);
    }

    @Override
    public void nodePositionAdjustComplete(Map<String, GNode> map) {
        // TODO Auto-generated method stub
        System.out.println("Node position Adjustment Complete");
        listener.stepCompleted("Node position Adjustment Complete");
        this.map = map;

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

    public void runTesting(Map<String, GNode> map, ArrayList<double[]> iWeights, ArrayList<String> iStrings) {
        testResults = tester.testGSOM(map, iWeights, iStrings);
        for(Map.Entry<String,String> entry : testResults.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

    public Map<String, GNode> getGSOMMap() {
        return this.map;
    }

    public Map<String, String> getTestResultMap() {
        return this.testResults;
    }

    public ArrayList<ArrayList<GCluster>> getAllClusters() {
        return this.allClusters;
    }

    public int getBestCount(){
        return this.bestCCount;
    }
    
    @Override
    public void smootheningCompleted(Map<String, GNode> map) {
        double totalErrorValue = 0;
        endTimeBeforeClustering = System.currentTimeMillis();
        listener.stepCompleted("Time before Clustering : " + timeFormatter(endTimeBeforeClustering - startTime));
        listener.stepCompleted("Smoothing phase completed!");
        //goodness of the map
        for (GNode node : map.values()) {
            totalErrorValue += node.getErrorValue();
        }
        listener.stepCompleted("Goodness of the Map : " + totalErrorValue / map.size());
        runTesting(map, parser.getWeights(), parser.getStrForWeights());
        clusterer.runClustering(map, this);
    }

    @Override
    public void clusteringCompleted(ArrayList<ArrayList<GCluster>> clusters,int bestCount) {
        endTimeAfterClustering = System.currentTimeMillis();
        listener.stepCompleted("End Time : " + timeFormatter(endTimeAfterClustering - startTime));
        listener.stepCompleted("Clustering completed!");
        listener.stepCompleted("------------------------------------------------");
        this.allClusters = clusters;
        this.bestCCount = bestCount;
        listener.executionCompleted();

    }

    public static void readFile(File inputFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;
        HashMap<String, String> map = new HashMap<String, String>();
        int count = 0;

        // To hold key and values
        String[] vals = new String[2];

        br.close();
    }

    public String timeFormatter(long millis) {

        if (millis > 1000) {
            return String.format("%d s, %d ms",
                    TimeUnit.MILLISECONDS.toSeconds(millis),
                    millis
                    - TimeUnit.MILLISECONDS.toSeconds(millis)*1000);
        } else {
            return (Long.toString(millis)+" ms");
        }

    }
}
