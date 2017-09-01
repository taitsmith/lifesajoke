package com.example.tait.jokeprovider;


/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private String myJokes;

    public String getData() {
        return myJokes;
    }

    public void setData(String jokes) {
        myJokes = jokes;
    }
}