/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.linking;

import java.util.Map;

/**
 *
 * @author Thush
 */
public interface ClusterLinker {
    public void updateLinks(Map<String,String> clusterData1,Map<String,String> clusterData2);
}
