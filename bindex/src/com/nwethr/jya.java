import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class jya {

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject oj = new JSONObject();
        try {
            oj = (JSONObject) parser.parse(new FileReader("/home/mil/Downloads/SENSEX.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //moving avaerge (sme) is used

        //result of next 3 days
        String da = (String) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(0))).get(0);

        //for 1st day
        double v = 0;
        int co = 0;
        double sv = 0;
        for (int i = 2000; i > 0; i--) {
            if (i == 2000) {
                for (int j = 0; j < 6; j++) {
                    v += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(i))).get(4) * (j + 1);
                    i--;
                    co += j + 1;
                }
                v = v / co;
            } else {
                v = ((double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(i))).get(4) - v) * (2.0 / (6 + 1)) + v;
            }
            if (i < 13) {
                sv += (double) ((JSONArray) (((JSONArray) (((JSONObject) oj.get("dataset")).get("data"))).get(i))).get(4);
            }
        }
        System.out.println("Sensex prediction by EMA for " + da + "+1 day is :: " + (float) v);
        System.out.println("Sensex prediction by SMA for " + da + "+1 day is :: " + (float) sv / 12);

    }
}
