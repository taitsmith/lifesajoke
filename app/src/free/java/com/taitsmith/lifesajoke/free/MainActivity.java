package com.taitsmith.lifesajoke.free;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    @BindView(R.id.buyJokesButton)
    Button buyJokesButton;

    public JokeCreator creator;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        creator = new JokeCreator();
        preferences = getSharedPreferences("SHARED_PREFS", 0);
    }

    @OnClick(R.id.jokeButton)
    public void tellJoke() {
        if (getJokeCount() > 0) {
            Intent intent = new Intent(this, JokeTeller.class);
            intent.putExtra("JOKE", creator.getJoke());
            startActivity(intent);
        } else {
            showOutOfJokes(true);
        }
    }

    int getJokeCount() {
        int jokesRemaining;
        editor = preferences.edit();

        if (preferences.contains("JOKES_REMAINING")) {
            jokesRemaining = preferences.getInt("JOKES_REMAINING", 0);
        } else {
            jokesRemaining = 6;
        }

        editor.putInt("JOKES_REMAINING", jokesRemaining -1);
        editor.apply();

        Log.d("JOKES REMAINING", Integer.toString(jokesRemaining));
        return jokesRemaining;
    }

    void showOutOfJokes(boolean show) {
        if (show) {
            jokeButton.setEnabled(false);
            greetingView.setText(getString(R.string.out_of_jokes));
            buyJokesButton.setVisibility(View.VISIBLE);
        } else {
            jokeButton.setEnabled(true);
            greetingView.setText(getString(R.string.greeting));
            buyJokesButton.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.buyJokesButton)
    public void buyJokes() {
        showOutOfJokes(false);
        editor = preferences.edit();
        editor.putInt("JOKES_REMAINING", 5);
        editor.apply();
    }
}
