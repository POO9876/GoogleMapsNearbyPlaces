package com.androidtutorialpoint.googlemapsnearbyplaces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView textView;
    String details=null;
    String addRess=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        textView= (TextView) findViewById(R.id.textView);

         details=getIntent().getExtras().getString("name");
         addRess=getIntent().getExtras().getString("add");
        textView.setText(details+"  ->  "+addRess);


    }
}
