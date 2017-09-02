package com.taitsmith.lifesajoke.paid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.JokeCreator;
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

    JokeCreator creator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        creator = new JokeCreator();

        greetingView.setText(getString(R.string.greeting));

        }

    //button calls asyc task to talk to GCE server and get jokes
    //the onpostexecute calls the joke display activity from the joketeller library
    //and passes the returned joke as an extra.
    @SuppressWarnings("unchecked")
    @OnClick(R.id.jokeButton)
    public void tellJoke() {
        new EndpointsJokeRetriever().execute(new Pair<Context, String>(this, creator.getJoke()));
    }
}
