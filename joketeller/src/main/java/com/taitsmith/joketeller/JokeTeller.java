package com.taitsmith.joketeller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class JokeTeller extends AppCompatActivity {
    TextView jokeView;
    String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_teller);
        jokeView = (TextView) findViewById(R.id.jokeView) ;

        if (getIntent().hasExtra("JOKE")) {
            joke = getIntent().getStringExtra("JOKE");
            jokeView.setText(joke);
        } else {
            jokeView.setText(getString(R.string.oops));
        }

    }
}