package com.example;

import java.util.Random;

public class JokeCreator {
    public String[] jokes = new String[] {
            "haha beep beep",
            "oh no",
            "Open a law firm that represents the animals from Seaworld and call it Habeas Porpoise",
            "Oof owie my bones",
            "I dated a blind woman but we broke up because she didn\'t understand observational humor",
            "There are crab cake but why are there no crab cupcakes?",
            "If I\'m 5\'6\" do you think I can still be a short order cook?",
            "Try again later"
    };

    public String getJoke() {
        Random r = new Random();
        int i = r.nextInt(8);
        return jokes[i];
    }
}
