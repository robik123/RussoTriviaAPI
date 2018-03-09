package ser210.quinnipiac.edu.triviaapi;

/**
 * Created by markrusso on 3/5/18.
 * Assignment 2 Part 2
 * SER210
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private Button answer;
    private int position = 0;
    private String urlInUse = urlMovies;
    final TriviaHandler trivia = new TriviaHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Button nxt = (Button) findViewById(R.id.next_button);
        Button START = (Button) findViewById(R.id.start_button);
        answer = (Button) findViewById(R.id.answer_button);

        START.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute(urlInUse);
                Button START = (Button) findViewById(R.id.start_button);
                START.setVisibility(View.INVISIBLE);
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position < 3) {
                    new JSONTask().execute(urlInUse);
                    TextView option1btn = (TextView) findViewById(R.id.option1);
                    option1btn.setBackgroundColor(Color.WHITE);
                    TextView option2btn = (TextView) findViewById(R.id.option2);
                    option2btn.setBackgroundColor(Color.WHITE);
                    TextView option3btn = (TextView) findViewById(R.id.option3);
                    option3btn.setBackgroundColor(Color.WHITE);
                }else {
                    GameOver();
                }

            }
        });

    }


    //Private class that implements the rest API
    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String triviaQuestion = null;

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

//                String questionFactJsonString = getJsonStringFromBuffer(reader);
//                triviaQuestion = TriviaHandler.getTriva(questionFactJsonString);

                //changes the question when the user hits the next button
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //sets question and button to proper spots
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

        //excecutes task
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

    //game over method to check if game has ended or if one set of qestions has ended
    public void GameOver(){
        if(urlInUse == urlMovies){
            urlInUse = urlFootball;
            position = 0;
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("Movie Trivia Has Ended Lets Try Sports!");
            alert.setTitle("Movie Trivia Over");
            alert.setCancelable(false);
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alert.setNegativeButton("Home", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            alert.show();
            TextView option1btn = (TextView) findViewById(R.id.option1);
            option1btn.setBackgroundColor(Color.WHITE);
            TextView option2btn = (TextView) findViewById(R.id.option2);
            option2btn.setBackgroundColor(Color.WHITE);
            TextView option3btn = (TextView) findViewById(R.id.option3);
            option3btn.setBackgroundColor(Color.WHITE);
            new JSONTask().execute(urlInUse);

        } else if(urlInUse == urlFootball){
            urlInUse = urlMusic;
            position = 0;

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("Sports Trivia Has Ended Lets Try Music!!");
            alert.setTitle("Sports Trivia Over");
            alert.setCancelable(false);
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alert.setNegativeButton("Home", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            alert.show();
            TextView option1btn = (TextView) findViewById(R.id.option1);
            option1btn.setBackgroundColor(Color.WHITE);
            TextView option2btn = (TextView) findViewById(R.id.option2);
            option2btn.setBackgroundColor(Color.WHITE);
            TextView option3btn = (TextView) findViewById(R.id.option3);
            option3btn.setBackgroundColor(Color.WHITE);
            new JSONTask().execute(urlInUse);
        }else if (urlInUse == urlMusic && position == 3){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("Russo's Trivia Has Sadly Ended!!");
            alert.setTitle("Trivia Over");
            alert.setCancelable(false);
            alert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            alert.setNegativeButton("Home", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            alert.show();
        }
    }
}
