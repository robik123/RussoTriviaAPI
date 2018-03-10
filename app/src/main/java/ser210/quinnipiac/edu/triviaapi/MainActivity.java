package ser210.quinnipiac.edu.triviaapi;

/**
 * Created by markrusso on 3/5/18.
 * Assignment 2 Part 2
 * SER210
 * this is the main activity with buttons
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //play button sends user to trivia
    public void onClickPlay(View view){
        Intent intent = new Intent (this, TriviaActivity.class);
            startActivity(intent);
    }

    //shows credits
    public void onClickInfo(View view){
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);

        alert.setMessage(R.string.Welcome);
        alert.setTitle("Info");
        alert.setCancelable(true);
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alert.show();
    }

    //how to play with answers
    public void HowtoPlay(View view){
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setMessage(R.string.HowTo);
        alert.setTitle("Welcome!!");
        alert.setCancelable(true);
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alert.show();

    }

    //exits the application
    public void onClickExit(View view){
        System.exit(1);

    }
}
