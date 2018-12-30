package com.nwethr;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        JSONParser parser = new JSONParser();
        JSONObject oj = new JSONObject();
        try {
            oj = (JSONObject) parser.parse(new FileReader("/home/mil/Downloads/SENSEX.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the date you want predicted");
        System.out.print("Enter the day :: ");
        String day = in.nextLine();
        System.out.print("Enter the month :: ");
        String month = in.nextLine();
        System.out.print("Enter the year :: ");
        String year = in.nextLine();

        String date = year + "-" + month + "-" + day;
        //moving avaerge (sme) is used

        System.out.println(date);

        int ch = 0;
        for (int i = 0; i < ((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).size(); i++) {
            double ra = 0;
            double rx = 0;
            double wm = 0;
            int zex = 0;
            int wx = 0;
            String da = (String) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(i))).get(0);
            if (da.compareTo(date) == 0) {
                for (int j = i + 2000; j > i; j--) {
                    zex++;


                    // rolling average of first 5 days , used for calc ema
                    if (zex == 1) {
                        for (int k = 0; k < 5; k++) {
                            ra += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(j))).get(4);
                            j--;
                        }
                        ra = ra / 10;

                    }


                    //sma calc
                    if (j - i <= 10) {
                        rx += ((double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(j))).get(4));
                    }

                    //wma calc
                    if (j - i <= 5) {
                        wm += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(j))).get(4) * (12 - (j - i) - 1);
                        wx += (12 - (j - i) - 1);
                    }


                    //ema calc
                    ra = (((double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(j))).get(4) - ra)
                            * 2.0 / (5 + 1)) + ra;

                }
                double pr = ra;
                System.out.format("Predicted Value (sma) :- %.2f\n", (float) rx / 10);
                System.out.format("Predicted Value (ema) :- %.2f\n", (float) pr);
                System.out.format("Predicted Value (wma) :- %.2f\n", (float) wm / wx);
                System.out.println("Real Value            :- " + ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(i))).get(4));
                ch = 1;
                break;
            }
        }
        if (ch == 0) {
            System.out.println("Invalid entry\nData not available for this date");
        }
    }
}
