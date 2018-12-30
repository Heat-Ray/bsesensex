import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class prat {

    public static void main(String[] args) throws IOException {

        JSONParser parser = new JSONParser();
        JSONObject oj = new JSONObject();
        try {
            oj = (JSONObject) parser.parse(new FileReader("/home/mil/Downloads/SENSEX.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double cp1;
        double cp2;
        double cp3;
        int cp11 = 0;
        int cp22 = 0;
        int cp33 = 0;

        for (int l = 0; l < 7000; l++) {


            String date = (String) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(l))).get(0);

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
                    cp1 = (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(l))).get(4) - ra;
                    cp2 = (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(l))).get(4) - (wm / wx);

                    cp3 = (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(l))).get(4) - (rx / 10);
                    if (cp1 < 0) {
                        cp1 = -cp1;
                    }
                    if (cp2 < 0) {
                        cp2 = -cp2;
                    }
                    if (cp3 < 0) {
                        cp3 = -cp3;
                    }

                    if (cp1 < cp2 && cp1 < cp3) {
                        cp11++;
                    }
                    if (cp2 < cp3 && cp2 < cp1) {
                        cp22++;
                    }
                    if (cp3 < cp2 && cp3 < cp1) {
                        cp33++;
                    }
                    break;
                }


            }

        }
        System.out.println("EMA :: " + cp11 + "    SMA :: " + cp33 + "    WMA :: " + cp22);

    }
}