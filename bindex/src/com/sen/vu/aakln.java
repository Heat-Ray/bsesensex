package com.sen.vu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class aakln {

    public static String calc(int x, String jst) throws IOException, ParseException {

        String rigg = "<html><pre>+ indicates number of days from now</pre><pre>";
        JSONParser parser = new JSONParser();
        JSONObject oj = new JSONObject();
        try {
            oj = (JSONObject) parser.parse(jst);
        } catch (ParseException e) {

        }

        int track_x = 0;
        int dez = 11;
        double sv = 0;

        while (x != 0) {
            for (int i = dez; i >= 0; i--) {
                sv += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset_data")).get("data"))).get(i))).get(4);
                System.out.println(sv);
            }
            if (track_x >= 1) {
                sv = sv / (12 - track_x + 1);
            } else
                sv = sv / 12;

            rigg = rigg + "  +" + (track_x + 1) + " " + (int) sv;
            track_x++;
            dez--;
            x--;
            if (track_x % 3 == 0) {
                rigg = rigg + "</pre><pre>";
            }
        }
        rigg = rigg + "</pre></font></html>";

        return rigg;

    }
}
