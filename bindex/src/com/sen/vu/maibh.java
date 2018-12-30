package com.sen.vu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class maibh {

    public static String lao(String json, String x) {


        JSONParser parser = new JSONParser();
        JSONObject oj = new JSONObject();
        try {
            oj = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            return "Error connecting to database";
        }

        for (int i = 0; i < ((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).size(); i++) {
            double ra = 0;
            double rx = 0;
            double ck = 0;
            double wm = 0;
            int zex = 0;
            int wx = 0;
            String da = (String) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(i))).get(0);
            if (da.compareTo(x) == 0) {
                for (int j = i + 200; j > i; j--) {
                    ck = ra;
                    zex++;


                    // rolling average of first 5 days , used for calc ema
                    if (zex == 1) {
                        for (int k = 0; k < 5; k++) {
                            ra += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(j))).get(4);
                            j--;
                        }
                        ra = ra / 10;

                    }


                    //sma calc
                    if (j - i <= 10) {
                        rx += ((double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(j))).get(4));
                    }

                    //wma calc
                    if (j - i <= 5) {
                        wm += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(j))).get(4) * (12 - (j - i) - 1);
                        wx += (12 - (j - i) - 1);
                    }


                    //ema calc
                    ra = (((double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(j))).get(4) - ra)
                            * 2.0 / (5 + 1)) + ra;

                }
                return "<html><pre>SMA prediction was  :: " + (float) rx / 10 + " </pre>" + "<pre>EMA prediction was  :: "
                        + (float) ra + "</pre>" + "<pre>WMA prediction was  :: " + (float) wm / wx + "</pre>" +
                        "<pre>Real closing point  :: " + ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(i))).get(4) + "</pre></html>";
            }
        }
        return "<html>Invalid entry<br>Data not available for this date<html>";
    }
}
