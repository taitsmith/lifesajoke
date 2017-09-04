package com.taitsmith.lifesajoke.paid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.taitsmith.lifesajoke.EndpointsJokeRetriever;
import com.taitsmith.lifesajoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.jokeButton)
    Button jokeButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

//    JokeCreator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        creator = new JokeCreator();
     }

    //button calls asyc task to talk to GCE server and get jokes
    //the onpostexecute calls the joke display activity from the joketeller library
    //and passes the returned joke as an extra.
    @SuppressWarnings("unchecked")
    @OnClick(R.id.jokeButton)
    public void tellJoke() {
        showLoading(true);
        new EndpointsJokeRetriever().execute(this);
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
