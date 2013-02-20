package com.gsom.core;

import com.gsom.objects.GNode;
import com.gsom.util.input.parsing.GSOMConstants;
import com.gsom.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GSOMTester {

    Map<String,String> testResultMap;

    public GSOMTester() {
        testResultMap = new HashMap<String, String>();
    }
    
    public Map<String,String> testGSOM(Map<String,GNode> nodeMap,ArrayList<double[]> iWeights,ArrayList<String> iStrings){
        for(int i = 0; i<iWeights.size();i++){
            
            GNode winner = Utils.selectWinner(nodeMap, iWeights.get(i));
            //System.out.println("Winner for "+iStrings.get(i)+" is "+winner.getX()+","+winner.getY());
            
            String winnerStr = Utils.generateIndexString(winner.getX(), winner.getY());
            GNode winnerNode = nodeMap.get(winnerStr);
            winnerNode.setHitValue(winner.getHitValue()+1);
            
            if(!testResultMap.containsKey(winnerStr)){
                testResultMap.put(winnerStr, iStrings.get(i));
            }else{
                String currStr = testResultMap.get(winnerStr);
                String newStr = currStr +","+ iStrings.get(i);
                testResultMap.remove(winnerStr);
                testResultMap.put(winnerStr,newStr);
            }
        }
        return testResultMap;
    }
}
