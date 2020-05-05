package com.example.cs125app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    public static String recentSearch;

    ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DATA", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readData();
        ListView listView = findViewById(R.id.my_list);
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(CoronaData.countyList);
        CoronaData.countyList.clear();
        CoronaData.countyList.addAll(hashSet);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CoronaData.countyList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recentSearch = (String) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent (view.getContext(), Detail.class);
                startActivityForResult(detailIntent, 0);
                Log.d("COUNTY", recentSearch);
                Log.d("COUNTY", CoronaData.stateMaxCase().getCounty());
                Log.d("COUNTY", Integer.toString(CoronaData.daysTill()));
            }
        });
    }
    private void readData() {
        Log.d("DATA", "readData");
        InputStream is = getResources().openRawResource(R.raw.uscounties);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        Log.d("DATA", "readData2");

        try {
            while ((line = reader.readLine()) != null) {
                //Log.d("DATA", "readData3");
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");

                // Read the data and store it
                CoronaData cd = new CoronaData();
                cd.setDate(tokens[0]);
                cd.setCounty(tokens[1]);
                cd.setState(tokens[2]);
                cd.setCases(tokens[4]);
                cd.setDeath(tokens[5]);
                CoronaData.coronaDataList.add(cd);
                CoronaData.countyList.add(tokens[1]);
                CoronaData.countyWholeList.add(tokens[1]);
            }
        } catch (IOException e1) {
            Log.e("DATA", "Error" + line, e1);
            e1.printStackTrace();
        }
    }
}
