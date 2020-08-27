package com.me.noban.DengueSolution.pulseRate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.me.noban.DengueSolution.R;

public class pulsrateMain extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        String number = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LAST_MEASURE", "0");
        if (number != "0") {

            TextView tv = (TextView) findViewById(R.id.number);
            tv.setText(number + " bps");

            RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
            tv = (TextView) findViewById(R.id.text);
            if (Double.parseDouble(number) > 95 || Double.parseDouble(number) < 70) {
                //Cosa mala
                rb.setRating(3);
                tv.setText("Your heart rate is Abnormal");
            } else {
                //Cosa buena
                rb.setRating(5);
                tv.setText("Your heart rate is Perfect!!!");
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsrate_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        final Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), pulsMeasure.class);
                startActivity(intent);
            }
        });
    }
}
