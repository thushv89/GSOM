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
        else if(type == InputDataType.MACHINES){
            return new MachineDataParser();
        }
        else if(type == InputDataType.LETTERS){
            return new LetterDataParser();
        }
        else if(type == InputDataType.ASHES){
            return new AshesDataParser();
        }
        else if(type == InputDataType.HISTOGRAM){
            return new HistogramDataParser();
        }
        else if(type == InputDataType.CHRIME){
            return new ChrimeDataParser();
        }
        else if(type == InputDataType.EH_POSITION){
            return new EHPositionDataParser();
        }
        else if(type == InputDataType.TEXTURE_PROPORTION){
            return new TextureProportionDataParser();
        }
         else if(type == InputDataType.GENERIC){
            return new GenericDataParser();
        }
        return null;
    }
}
