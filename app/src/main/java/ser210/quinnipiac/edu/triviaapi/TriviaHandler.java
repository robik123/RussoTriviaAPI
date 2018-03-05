package ser210.quinnipiac.edu.triviaapi;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by markrusso on 3/5/18.
 */

public class TriviaHandler {

    public static String getYearFact(String yearFactJsonStr) throws JSONException {
        JSONObject yearFactJSONObj = new JSONObject(yearFactJsonStr);
        return yearFactJSONObj.getString("question");
    }

}
