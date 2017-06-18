package com.example.alienware.fantasy_image.Bean;

import java.io.Serializable;

/**
 * Created by ALIENWARE on 2017/6/18.
 */

/*选择功能后传递选择的功能的Bundle数据*/
public class PassedData implements Serializable {
    private int FuncId;
    private int valueNum = 1;
    private float value1;
    private float value2;

    public PassedData(int FuncId) {
        this.FuncId = FuncId;
        valueNum = 0;
    }
    public PassedData(int FuncId, float value1) {
        this.FuncId = FuncId;
        valueNum = 1;
        this.value1 = value1;
    }
    public PassedData(int FuncId, float value1, float value2) {
        this.FuncId = FuncId;
        valueNum = 2;
        this.value1 = value1;
        this.value2 = value2;
    }

    public float getValue1() {
        return value1;
    }

    public float getValue2() {
        return value2;
    }

    public int getFuncId() {
        return FuncId;
    }

    public int getValueNum() {
        return valueNum;
    }
}
