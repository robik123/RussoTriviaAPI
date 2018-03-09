package ser210.quinnipiac.edu.triviaapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CategoryActivity extends AppCompatActivity {
    final static String urlMovies = "https://qriusity.com/v1/categories/2/questions?page=2&limit=3";
    final static String urlFootball = "https://qriusity.com/v1/categories/9/questions?page=2&limit=3";
    final static String urlMusic = "https://qriusity.com/v1/categories/12/questions?page=2&limit=3";
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean userSelection = false;
    final TriviaActivity trivia = new TriviaActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    public void checkBox(View view){
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){


        }
    }


    public void onClickQuiz(View View){
            Intent intent = new Intent(this, TriviaActivity.class);
            startActivity(intent);

    }
}
