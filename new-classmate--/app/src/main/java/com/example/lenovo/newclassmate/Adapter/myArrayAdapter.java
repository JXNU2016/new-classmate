package com.example.lenovo.newclassmate.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

public class myArrayAdapter extends ArrayAdapter {
    public myArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
