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
import com.gsom.util.InputParser;
import com.gsom.util.InputParserFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GSOMRun implements InputParsedListener, GSOMTrainerListener, NodePositionAdjustListener, GSOMSmoothnerListener, ClusteringListener {

    InputParserFactory parserFactory;
    InputParser parser;
    GSOMTrainer trainer;
    GCoordAdjuster adjuster;
    GSOMSmoothner smoothner;
    GSOMTester tester;
    KMeanClusterer clusterer;
    Map<String, GNode> map;
    Map<String, String> testResults;
    ArrayList<GCluster> clusters;
    GSOMRunListener listener;

    public GSOMRun(GSOMRunListener listener) {
        this.listener = listener;
    }

    public void runTraining(String fileName, String type) {
        parserFactory = new InputParserFactory();
        trainer = new GSOMTrainer();
        adjuster = new GCoordAdjuster();
        smoothner = new GSOMSmoothner();
        tester = new GSOMTester();
        clusterer = new KMeanClusterer();

        //String fileName = "texture_proportion.txt";
        if (type.equalsIgnoreCase("FLAGS")) {
            parser = parserFactory.getInputParser(InputDataType.FLAGS);
        } else if (type.equalsIgnoreCase("ZOO")) {
            parser = parserFactory.getInputParser(InputDataType.ZOO);
        } else if (type.equalsIgnoreCase("MACHINES")) {
            parser = parserFactory.getInputParser(InputDataType.MACHINES);
        } else if (type.equalsIgnoreCase("LETTERS")) {
            parser = parserFactory.getInputParser(InputDataType.LETTERS);
        } else if (type.equalsIgnoreCase("ASHES")) {
            parser = parserFactory.getInputParser(InputDataType.ASHES);
        } else if (type.equalsIgnoreCase("HISTOGRAM")) {
            parser = parserFactory.getInputParser(InputDataType.HISTOGRAM);
        } else if (type.equalsIgnoreCase("CHRIME")) {
            parser = parserFactory.getInputParser(InputDataType.CHRIME);
        } else if (type.equalsIgnoreCase("EH_POSITION")) {
            parser = parserFactory.getInputParser(InputDataType.EH_POSITION);
        } else if (type.equalsIgnoreCase("TEXTURE_PROPORTION")) {
            parser = parserFactory.getInputParser(InputDataType.TEXTURE_PROPORTION);
        } else if (type.equalsIgnoreCase("GENERIC")) {
            parser = parserFactory.getInputParser(InputDataType.GENERIC);
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

    public void runTesting(Map<String, GNode> map, ArrayList<double[]> iWeights, ArrayList<String> iStrings) {
        testResults = tester.testGSOM(map, iWeights, iStrings);
    }

    public Map<String, GNode> getGSOMMap() {
        return this.map;
    }

    public Map<String, String> getTestResultMap() {
        return this.testResults;
    }

    public ArrayList<GCluster> getClusters() {
        return this.clusters;
    }

    @Override
    public void smootheningCompleted(Map<String, GNode> map) {
        double totalErrorValue = 0;
        listener.stepCompleted("Smoothing phase completed!");
        //goodness of the map
        for (GNode node : map.values()) {
            totalErrorValue += node.getErrorValue();
        }
        listener.stepCompleted("Goodness of the Map : " + totalErrorValue/map.size());
        runTesting(map, parser.getWeights(), parser.getStrForWeights());
        clusterer.runClustering(map, this);
    }

    @Override
    public void clusteringCompleted(ArrayList<GCluster> clusters) {
        listener.stepCompleted("Clustering completed!");
        listener.stepCompleted("------------------------------------------------");
        this.clusters = clusters;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
            for (Map.Entry<String, String> entry : testResults.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
                bw.write(entry.getKey() + " - " + entry.getValue());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error in file");
            e.printStackTrace();
        }

    }

    public static void readFile(File inputFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;
        HashMap<String, String> map = new HashMap<String, String>();
        int count = 0;

        // To hold key and values
        String[] vals = new String[2];

//        while ((line = br.readLine()) != null) {
//            // to skip the 'best count' value
//            if(count!=0){
//                // splitting keys and values
//                vals = line.split("-");
//                map.put(vals[0].trim(), vals[1].trim());
//
//            } else{
//                Values.bestCount = line;
//            }
//            count++;
//        }
//
//        Values.map = map;
//        System.out.println("> "+Values.map.size()+ " lines were scanned");

        br.close();
    }
}
