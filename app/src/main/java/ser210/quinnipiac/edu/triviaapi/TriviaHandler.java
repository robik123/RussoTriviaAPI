package ser210.quinnipiac.edu.triviaapi;

import android.app.job.JobInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by markrusso on 3/5/18.
 * class retireves data and is used through ASYSC TASK
 */

public class TriviaHandler {
    private static int position = 0;
    static String quiz;
    static String option1;
    static String option2;
    static String option3;
    static String option4;
    static JSONObject answer;


    //gets the trivia question at position 0 1 and 2 called in private class
    public static String getTrivia(String questionJsonStr) throws JSONException {
        if (position < 3) {
            JSONArray questionJSONObj = new JSONArray(questionJsonStr);
            final JSONObject jObj = questionJSONObj.getJSONObject(position);
            quiz = jObj.getString("question");
        } else {
            position = 0;
        }
        return quiz;
    }

    //gets option1 for questions 1 2 and 3 called in private class
    public static String getOption1(String questionJsonStr) throws JSONException {
        JSONArray questionJSONObj = new JSONArray(questionJsonStr);
        JSONObject jObj = questionJSONObj.getJSONObject(position);
        option1 = jObj.getString("option1");
        return option1;
    }

    //gets option2 for questions 1 2 and 3 called in private class
    public static String getOption2(String questionJsonStr) throws JSONException {
        JSONArray questionJSONObj = new JSONArray(questionJsonStr);
        JSONObject jObj = questionJSONObj.getJSONObject(position);
        option2 = jObj.getString("option2");
        return option2;
    }

    //gets option3 for questions 1 2 and 3 called in private class
    public static String getOption3(String questionJsonStr) throws JSONException {
        JSONArray questionJSONObj = new JSONArray(questionJsonStr);
        final JSONObject jObj = questionJSONObj.getJSONObject(position);
        option3 = jObj.getString("option3");
        return option3;
    }

    //gets option4 for questions 1 2 and 3 called in private class
    public static String getOption4(String questionJsonStr) throws JSONException {
        JSONArray questionJSONObj = new JSONArray(questionJsonStr);
        JSONObject jObj = questionJSONObj.getJSONObject(position);
        option4 = jObj.getString("option4");
        position++;
        if (position > 2) {
            position = 0;
        }
        return option4;
    }
}
