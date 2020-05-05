package com.example.cs125app2;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class CoronaData {
    private String dateString;
    private String countyString;
    private String stateString;
    private String casesString;
    int casesInt;
    private String deathString;
    int deathInt;
    public static List<CoronaData> coronaDataList = new LinkedList<>();
    public static List<String> countyList = new LinkedList<>();
    public static List<String> countyWholeList = new LinkedList<>();
    public CoronaData() {
        //construct
    }
    public void setDate(String set) {
        dateString = set;
    }
    public void setCounty(String set) {
        countyString = set;
    }
    public void setState(String set) {
        stateString = set;
    }
    public void setCases(String set) {
        casesString = set;
        try {
            casesInt = Integer.parseInt(set);
        } catch(NumberFormatException nfe) {
            casesInt = 0;
        }
    }
    public void setDeath(String set) {
        deathString = set;
        try {
            deathInt = Integer.parseInt(set);
        } catch(NumberFormatException nfe) {
            deathInt = 0;
        }
    }
    public String getDate() {
        return dateString;
    }
    public String getCounty() {
        return countyString;
    }
    public String getState() {
        return stateString;
    }
    public String getCases() {
        return casesString;
    }
    public String getDeath() {
        return deathString;
    }
    public int getDeathInt() {
        return deathInt;
    }
    public static void printData(int i) {
        Log.d("DATA", coronaDataList.get(i).dateString + "," + coronaDataList.get(i).countyString);
    }
    public static int deathMax(String county) {
        int i = latestIndex(county);
        return coronaDataList.get(i).getDeathInt();
    }
    public static int latestIndex(String county) {
        return countyWholeList.lastIndexOf(county);
    }
    public static CoronaData stateMaxCase(String state) {
        CoronaData toReturn = coronaDataList.get(1);
        for (CoronaData cd : coronaDataList) {
            if (cd.getState().equals(state)) {
                if (toReturn == null) {
                    toReturn = cd;
                }
                if (cd.getDeathInt() > toReturn.getDeathInt()) {
                    toReturn = cd;
                }
            }
        }
        return toReturn;
    }
    public static CoronaData stateMaxCase() {
        return stateMaxCase(getStateSearch());
    }

    public static String getStateSearch() {
        int i = CoronaData.latestIndex(MainActivity.recentSearch);
        return  CoronaData.coronaDataList.get(i).getState();
    }
    public static int daysTill() {
        try {
            int i = latestIndex(MainActivity.recentSearch);
            int low = CoronaData.coronaDataList.get(i).getDeathInt();
            int high = CoronaData.stateMaxCase().getDeathInt();
            Log.d("COUNT", new Integer(low).toString());
            Log.d("COUNT", new Integer(high).toString());
            Log.d("COUNT", "low, then high");

            if (low == high) {
                return 0;
            }
            if (low == 0) {
                return 999;
            }
            double end = high/low;
            double doubleRate = 2.0;
            double toReturn = 4.0*(Math.log(end)/Math.log(doubleRate));
            return ((int) toReturn);
        } catch (Exception e) {
            return 998;
        }

    }
}
