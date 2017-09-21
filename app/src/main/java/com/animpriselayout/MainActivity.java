package com.animpriselayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowLayout flowLayout;
    private Button btnAdd;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               flowLayout.setOneUrls("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4082452791,2707706573&fm=27&gp=0.jpg");
            }
        });
        initData();
        flowLayout.setUrls(dataList);
    }

    private void initData() {
        for (int i = 0;i <10 ; i++) {
            dataList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4082452791,2707706573&fm=27&gp=0.jpg");
        }
    }
}
