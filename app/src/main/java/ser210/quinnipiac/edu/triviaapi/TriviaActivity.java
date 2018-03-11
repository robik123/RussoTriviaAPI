package ser210.quinnipiac.edu.triviaapi;

/**
 * Created by markrusso on 3/5/18.
 * Assignment 2 Part 2
 * SER210
 * this class reads JSON data and gets
 * questions and options from Trivia Handler
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
    //Three urls used in the trivia
    final static String urlMovies = "https://qriusity.com/v1/categories/2/questions?page=2&limit=3";
    final static String urlFootball = "https://qriusity.com/v1/categories/9/questions?page=2&limit=3";
    final static String urlMusic = "https://qriusity.com/v1/categories/12/questions?page=2&limit=3";
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private int position = 0;
    private String urlInUse = urlMovies;
    private String triviaQuestion;
    private String trivOption1;
    private String trivOption2;
    private String trivOption3;
    private String trivOption4;
    private int trivAnswer;
    JSONObject jsonObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        Button nxt = (Button) findViewById(R.id.next_button);
        Button START = (Button) findViewById(R.id.start_button);

        //Starts the Trivia
        START.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute(urlInUse);
                Button START = (Button) findViewById(R.id.start_button);
                START.setVisibility(View.INVISIBLE);
            }
        });

        //Switches to next question
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < 3) {
                    new JSONTask().execute(urlInUse);
                    TextView option1btn = (TextView) findViewById(R.id.option1);
                    option1btn.setBackgroundColor(Color.WHITE);
                    TextView option2btn = (TextView) findViewById(R.id.option2);
                    option2btn.setBackgroundColor(Color.WHITE);
                    TextView option3btn = (TextView) findViewById(R.id.option3);
                    option3btn.setBackgroundColor(Color.WHITE);
                    TextView option4btn = (TextView) findViewById(R.id.option4);
                    option4btn.setBackgroundColor(Color.WHITE);
                } else {
                    GameOver();
                }

            }
        });
        //Checks the answers
        Button answer = findViewById(R.id.answer_button);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });
    }

    //Private class that implements the rest API
    private class JSONTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            //tries URL and connects
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String questionJsonString = getJsonStringFromBuffer(reader);

                //retirves data from Trivia Handler
                triviaQuestion = TriviaHandler.getTrivia(questionJsonString);
                trivOption1 = TriviaHandler.getOption1(questionJsonString);
                trivOption2 = TriviaHandler.getOption2(questionJsonString);
                trivOption3 = TriviaHandler.getOption3(questionJsonString);
                trivOption4 = TriviaHandler.getOption4(questionJsonString);
                try {
                    trivAnswer = jsonObj.getInt("answers");
                } catch (JSONException e) {
                    System.out.println("Sorry not and answer");
                }

                //exceptions
            } catch (JSONException e) {
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
            return triviaQuestion;
        }

        //excecutes task and changes text of questions and options
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && position < 3) {
                Log.d(LOG_TAG, result);
                TextView question1 = (TextView) findViewById(R.id.question);
                question1.setText(triviaQuestion);
                RadioButton option1btn = (RadioButton) findViewById(R.id.option1);
                RadioButton option2btn = (RadioButton) findViewById(R.id.option2);
                RadioButton option3btn = (RadioButton) findViewById(R.id.option3);
                RadioButton option4btn = (RadioButton) findViewById(R.id.option4);
                option1btn.setText(trivOption1);
                option2btn.setText(trivOption2);
                option3btn.setText(trivOption3);
                option4btn.setText(trivOption4);
                position++;
            } else if (position == 3) {
                GameOver();
            }
        }
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


    //game over method to check if game has ended or if one set of qestions has ended and changes to new set
    public void GameOver() {
        if (urlInUse == urlMovies) {
            urlInUse = urlFootball;
            position = 0;

            //Prof. Ruby: any repeated code should go in a method that takes the variable part as parameters. (avoid repetetion for high quality code)
            //alerts the user of a new set of questions starting
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
            new JSONTask().execute(urlInUse);

        } else if (urlInUse == urlFootball) {
            urlInUse = urlMusic;
            position = 0;

            //alerts the user of a new set of questions starting
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
            new JSONTask().execute(urlInUse);
        } else if (urlInUse == urlMusic && position == 3) {

            //alerts the user trivia has eneded
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

    //checks for winner and sets the color of the radio button
    public void checkAnswer() {
        if (trivAnswer == 1) {
            TextView option1btn = (TextView) findViewById(R.id.option1);
            option1btn.setBackgroundColor(Color.CYAN);
        }
        if (trivAnswer == 2) {
            TextView option2btn = (TextView) findViewById(R.id.option2);
            option2btn.setBackgroundColor(Color.CYAN);
        }

        if (trivAnswer == 3) {
            TextView option3btn = (TextView) findViewById(R.id.option3);
            option3btn.setBackgroundColor(Color.CYAN);
        }
        if (trivAnswer == 4) {
            TextView option4btn = (TextView) findViewById(R.id.option4);
            option4btn.setBackgroundColor(Color.CYAN);
        }
    }

}
