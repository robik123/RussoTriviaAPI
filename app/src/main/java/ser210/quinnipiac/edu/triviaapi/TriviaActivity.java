package ser210.quinnipiac.edu.triviaapi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TriviaActivity extends Activity {
    final static String urlMovies = "https://qriusity.com/v1/categories/2/questions?page=2&limit=3";
    final static String urlFootball = "https://qriusity.com/v1/categories/9/questions?page=2&limit=3";
    final static String urlMusic = "https://qriusity.com/v1/categories/12/questions?page=2&limit=3";
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean userSelection = false;
    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Button btn = (Button) findViewById(R.id.answer_button);
        question = (TextView) findViewById(R.id.question);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute(urlMovies);
            }
        });

    }


    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();


                InputStream in = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("category");

                JSONObject finalObject = parentArray.getJSONObject(0);

                String question = finalObject.getString("question");
                String option1 = finalObject.getString("option1");
                int answer = finalObject.getInt("answer");


                return question + option1;

            }catch(JSONException e ){
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());

            } finally {
                if (urlConnection == null)
                    urlConnection.disconnect();
                if (urlConnection != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error" + e.getMessage());
                        return null;
                    }


            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            question.setText(result);
        }

        private String getJsonStringFromBuffer(BufferedReader br) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line + '\n');
            }
            if (buffer.length() == 0)
                return null;

            return buffer.toString();

        }
    }
}
