package com.taitsmith.lifesajoke;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.example.tait.jokeprovider.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.taitsmith.joketeller.JokeTeller;

import java.io.IOException;

/**
 * Beep beep gettin' jokes from the GCE.
 */

public class EndpointsJokeRetriever extends AsyncTask<Context, Void, String> {
    private MyApi myApiService = null;
    private Context context;
    @VisibleForTesting public static String joke;

    @VisibleForTesting
    @Nullable private static
    SimpleIdlingResource idlingResource;

    @Override
    protected void onPreExecute() {
        getIdlingResource();
    }

    @Override
    protected String doInBackground(Context... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.1.71:8080/_ah/api/") //TODO put in your own IP
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        joke = result;
        Intent intent = new Intent(context, JokeTeller.class);
        intent.putExtra("JOKE", result);
        context.startActivity(intent);
        idlingResource.setIdleState(true);
    }

    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}