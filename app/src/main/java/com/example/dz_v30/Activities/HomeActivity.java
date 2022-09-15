package com.example.dz_v30.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dz_v30.Adapters.viewpager_adapter;
import com.example.dz_v30.Fragments.InputData;
import com.example.dz_v30.Fragments.Med_Info;
import com.example.dz_v30.Fragments.OutputData;
import com.example.dz_v30.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity{

    FloatingActionButton floatingActionButton;
    ViewPager2 viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        tabLayout = findViewById(R.id.home_tabLayout);
        viewPager = findViewById(R.id.home_vp);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputData inputData = new InputData();
                inputData.show(getSupportFragmentManager(),inputData.getTag());
            }
        });

        viewpager_adapter adapter = new viewpager_adapter(this);
        adapter.addTabs(new Med_Info(),"Medicines");
        adapter.addTabs(new OutputData(),"Time");
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout,viewPager,true,true,((tab, position) -> tab.setText(viewpager_adapter.titles.get(position)))).attach();
    }
}

