/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.nodes;

import com.gsom.objects.GNode;
import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author Thush
 */
public class EucDistOccMat {
    
    Map<String,Double> matrix;
    
    public EucDistOccMat(){
        matrix = new HashMap<String, Double>();
    }
    
    public JTable generateMatrix(Map<String,GNode> nMap,int dimensions){
        String[][] values = new String[nMap.size()][nMap.size()];
        String[] columns = new String[nMap.size()];
        
        int row = 0;
        int col = 0;
        for(Map.Entry<String,GNode> entry1 : nMap.entrySet()){
            columns[row]=entry1.getKey();
            col = 0;
            for(Map.Entry<String,GNode> entry2 : nMap.entrySet()){
                
                double val = Utils.calcEucDist(entry1.getValue().getWeights(), entry2.getValue().getWeights(), dimensions);
                
                val = Double.parseDouble(new DecimalFormat("#.##").format(val));
                values[row][col] = val+"";
                col++;
            }
            row++;
        }
        
        return new JTable( values, columns );
    }
    
    private int[] findMax(Map<String,GNode> nMap){
        int maxX = 0;
        int maxY = 0;
        for(String key : nMap.keySet()){
            String[] tokens = key.split(",");
            maxX = Math.max(maxX, Integer.parseInt(tokens[0]));
            maxY = Math.max(maxY, Integer.parseInt(tokens[1]));
        }
        return new int[]{maxX,maxY};
    }
}
