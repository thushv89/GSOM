/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.util.input.parsing;

import com.gsom.enums.InputDataType;

/**
 *
 * @author Thush
 */
public class InputParserFactory {
    
    
    public InputParser getInputParser(InputDataType type){
        if(type == InputDataType.NUMERICAL){
            return new NumericalDataParser();
        }
        else if(type == InputDataType.FLAGS){
            return new FlagDataParser();
        }
        else if(type == InputDataType.ASHES){
            return new AshesDataParser();
        }
        return null;
    }
}
