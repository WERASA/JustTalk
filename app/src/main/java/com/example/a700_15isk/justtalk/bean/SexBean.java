package com.example.a700_15isk.justtalk.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by 700-15isk on 2017/8/20.
 */

public class SexBean implements IPickerViewData {
    private String sex;
public SexBean (String sex){
    this.sex=sex;
}

    @Override
    public String getPickerViewText() {
        return this.sex;
    }
}
