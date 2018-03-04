package ser210.quinnipiac.edu.triviaapi;

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

    public void onClickPlay(View view){
        Intent intent = new Intent (this, TriviaActivity.class);
            startActivity(intent);
    }

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

    public void onClickExit(View view){
        System.exit(1);

    }
}
