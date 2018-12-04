package com.example.pickerview.listener;
import com.example.pickerview.entity.PickerData;


/*
地址类相关接口
 */
public interface OnPickerClickListener {
    public void OnPickerClick(PickerData pickerData);
    public void OnPickerConfirmClick(PickerData pickerData);
}
