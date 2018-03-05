package ser210.quinnipiac.edu.triviaapi;

/**
 * Created by markrusso on 3/5/18.
 * Assignment 2 Part 2
 * SER210
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private Button answer;
    private int position = 0;
    final TriviaHandler trivia = new TriviaHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Button btn = (Button) findViewById(R.id.next_button);
        answer = (Button) findViewById(R.id.answer_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute(urlMovies);
                userSelection = true;
                TextView option1btn = (TextView) findViewById(R.id.option1);
                option1btn.setBackgroundColor(Color.WHITE);
                TextView option2btn = (TextView) findViewById(R.id.option2);
                option2btn.setBackgroundColor(Color.WHITE);
                TextView option3btn = (TextView) findViewById(R.id.option3);
                option3btn.setBackgroundColor(Color.WHITE);


            }
        });

    }


    //Private class that implements the rest API
    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String year = null;

            //tries the URL
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                //connects the url
                InputStream in = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                //retireves specific data from the api
                final JSONArray arr = new JSONArray(finalJson);
                final JSONObject jObj = arr.getJSONObject(position);
                final String quiz = jObj.getString("question");
                final  String option1 = jObj.getString("option1");
                final  String option2 = jObj.getString("option2");
                final  String option3 = jObj.getString("option3");

                //changes the question when the user hits the next button
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            RadioButton option1btn = (RadioButton) findViewById(R.id.option1);
                            TextView question1 = (TextView) findViewById(R.id.question);
                            option1btn.setText(option1);
                            question1.setText(quiz);

                            TextView option2btn = (TextView) findViewById(R.id.option2);
                            option2btn.setText(option2);

                            TextView option3btn = (TextView) findViewById(R.id.option3);
                            option3btn.setText(option3);

                        answer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                position ++;
                                if(position == 1){
                                    TextView option3btn = (TextView) findViewById(R.id.option3);
                                    option3btn.setBackgroundColor(Color.CYAN);
                                }
                                if(position == 2){
                                    TextView option1btn = (TextView) findViewById(R.id.option1);
                                    option1btn.setBackgroundColor(Color.CYAN);
                                }

                                if(position == 3){
                                    TextView option1btn = (TextView) findViewById(R.id.option1);
                                    option1btn.setBackgroundColor(Color.CYAN);
                                }
                            }
                        });

                        }

                });

                return null;

                //exceptions
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
