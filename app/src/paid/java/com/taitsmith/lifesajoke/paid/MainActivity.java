package com.taitsmith.lifesajoke.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.JokeCreator;
import com.taitsmith.joketeller.JokeTeller;
import com.taitsmith.lifesajoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.greetingView)
    TextView greetingView;
    @BindView(R.id.jokeButton)
    Button jokeButton;

    JokeCreator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        creator = new JokeCreator();

        greetingView.setText(getString(R.string.greeting));
        }

    @OnClick(R.id.jokeButton)
    public void tellJoke() {
        Intent intent = new Intent(this, JokeTeller.class);
        intent.putExtra("JOKE", creator.getJoke());
        startActivity(intent);
    }
}
