package ser210.quinnipiac.edu.triviaapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by markrusso on 3/5/18.
 */

public class TriviaHandler {

    public static String getTriva(String questionJsonStr) throws JSONException {
        JSONObject questionJSONObj = new JSONObject(questionJsonStr);
        final String quiz = questionJSONObj.getString("question");

        return quiz;
    }

}
