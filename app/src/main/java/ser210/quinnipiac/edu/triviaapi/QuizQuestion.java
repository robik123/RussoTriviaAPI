package ser210.quinnipiac.edu.triviaapi;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by markrusso on 3/1/18.
 */

public class QuizQuestion {
    private static final int START_YEAR = 1950;
    private static final int END_YEAR= 2010;
    public static final String YEAR_FACT = "YEAR_FACT";
   // final public static String [] years = new String[END_YEAR - START_YEAR +1];;

    public QuizQuestion(){

        //populate the array
        int i = 0;
   //     for (int yr = START_YEAR; yr <= END_YEAR; yr++ )
           // years[i++] = Integer.toString(yr);
    }

    public static String getYearFact(String yearFactJsonStr) throws JSONException {
        JSONObject yearFactJSONObj = new JSONObject(yearFactJsonStr);
        return yearFactJSONObj.getString("text");
    }

}