/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util;

import com.gsom.enums.InputDataType;

/**
 *
 * @author Thush
 */
public class InputParserFactory {
    
    
    public InputParser getInputParser(InputDataType type){
        if(type == InputDataType.ZOO){
            return new ZooDataParser();
        }else if(type == InputDataType.FLAGS){
            return new FlagDataParser();
        }
        return null;
    }
}
