package com.example.lenovo.newclassmate.Bean;


import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

import com.example.lenovo.newclassmate.R;


public class School extends TabActivity{

    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school);
        TH();

    }
    private void TH(){
        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        LayoutInflater lif = LayoutInflater.from(this);
        lif.inflate(R.layout.school_jz, tabHost.getTabContentView());
        lif.inflate(R.layout.school_gcfb, tabHost.getTabContentView());
        lif.inflate(R.layout.school_bggz, tabHost.getTabContentView());
        lif.inflate(R.layout.school_xszd, tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("简介")
                .setContent(R.id.jz));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("馆藏分布")
                .setContent(R.id.gcjs));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("本馆规则")
                .setContent(R.id.bggz));
        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator("新生指导")
                .setContent(R.id.xszd));

    }
}
