package com.example.cs125app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int i = CoronaData.latestIndex(MainActivity.recentSearch);
        final TextView date = findViewById(R.id.date);
        date.setText(CoronaData.coronaDataList.get(i).getDate());
        final TextView textView = findViewById(R.id.deathMax);
        textView.setText(Integer.toString(CoronaData.deathMax(MainActivity.recentSearch)));
        final TextView county = findViewById(R.id.county);
        county.setText(MainActivity.recentSearch);
        final TextView state = findViewById(R.id.state);
        state.setText(", " + CoronaData.coronaDataList.get(i).getState());
        final TextView cases = findViewById(R.id.caseMax);
        cases.setText(CoronaData.coronaDataList.get(i).getCases());
        final TextView stateCountyCaseMax = findViewById(R.id.stateCountyCaseMax);
        stateCountyCaseMax.setText(CoronaData.stateMaxCase().getCounty());
        final TextView afflictedCountyDeath = findViewById(R.id.afflictedCountyDeath);
        afflictedCountyDeath.setText(CoronaData.stateMaxCase().getDeath());
        final TextView daysTillWorst = findViewById(R.id.tillWorst);
        daysTillWorst.setText(Integer.toString(CoronaData.daysTill()));
    }
}
