package com.taitsmith.lifesajoke.paid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.JokeCreator;
import com.taitsmith.lifesajoke.EndpointsJokeRetriever;
import com.taitsmith.lifesajoke.R;
import com.taitsmith.lifesajoke.SimpleIdlingResource;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.greetingView)
    TextView greetingView;
    @BindView(R.id.jokeButton)
    Button jokeButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    JokeCreator creator;

    @Nullable public static
    SimpleIdlingResource idlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        creator = new JokeCreator();

        greetingView.setText(getString(R.string.greeting));

        getIdlingResource();
        }

    //button calls asyc task to talk to GCE server and get jokes
    //the onpostexecute calls the joke display activity from the joketeller library
    //and passes the returned joke as an extra.
    @SuppressWarnings("unchecked")
    @OnClick(R.id.jokeButton)
    public void tellJoke() {
        showLoading(true);
        new EndpointsJokeRetriever().execute(new Pair<Context, String>(this, creator.getJoke()));
    }

    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        showLoading(false);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}
