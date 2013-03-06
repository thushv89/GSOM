/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.linking;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thush
 */
public class MatrixClusterLinker implements ClusterLinker {

    private Map<String, Integer> cLinkStrengthMap;
    private Map<String, String> cLinkNamesMap;

    public MatrixClusterLinker() {
        cLinkStrengthMap = new HashMap<String, Integer>();
        cLinkNamesMap = new HashMap<String, String>();
    }

    @Override
    public void updateLinks(Map<String, String> clusterData1, Map<String, String> clusterData2) {
        //TODO: Do the same for the filenames as done for link strengths
        for (Map.Entry<String, String> entry1 : clusterData1.entrySet()) {

            String[] e1Tokens = entry1.getValue().split(",");
            //do this for each value name in each cluster in clusterData1
            for (String token : e1Tokens) {
                for (Map.Entry<String, String> entry2 : clusterData2.entrySet()) {
                    //current cluster in clusterData2 has the token
                    if (entry2.getValue().contains(token)) {
                        String mapKey = entry1.getKey() + ":" + entry2.getKey();
                        
                        int mapValue = 0;
                        String nameMapValue = "";
                        
                        if (getInterClusterLinkMap().containsKey(mapKey)) {
                            mapValue = getInterClusterLinkMap().get(mapKey);
                            nameMapValue = cLinkNamesMap.get(mapKey);
                        }
                        mapValue++;
                        nameMapValue += token+",";
                        cLinkStrengthMap.put(mapKey, mapValue);
                        cLinkNamesMap.put(mapKey, nameMapValue);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @return the interClusterLinkMap
     */
    public Map<String, Integer> getInterClusterLinkMap() {
        return cLinkStrengthMap;
    }
}
