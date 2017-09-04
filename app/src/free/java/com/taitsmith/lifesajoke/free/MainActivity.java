package com.taitsmith.lifesajoke.free;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.taitsmith.lifesajoke.EndpointsJokeRetriever;
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
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("SHARED_PREFS", 0);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    //there's no real point to the joke credits thing.
    //it's also a joke.
    @SuppressWarnings("unchecked")
    @OnClick(R.id.jokeButton)
    public void tellJoke() {
        if (getJokeCount() > 0) {
            showLoading(true);
            new EndpointsJokeRetriever().execute(this);
        } else {
            showOutOfJokes(true);
        }
    }

    //pretend that you've a limited number of joke credits.
    int getJokeCount() {
        int jokesRemaining;
        editor = preferences.edit();

        jokesRemaining = preferences.contains("JOKES_REMAINING") ?
                preferences.getInt("JOKES_REMAINING", 0) : 6;

        editor.putInt("JOKES_REMAINING", jokesRemaining - 1);
        editor.apply();

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

    public void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        showLoading(false);
    }
}
