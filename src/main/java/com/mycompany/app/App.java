package com.mycompany.app;

import java.io.IOException;

import static com.mycompany.app.Logger.log;

public class App {
    public static void main(String[] args) throws IOException {
        Day day = new Day21("day-21");
        log("First star:");
        log(day.calculateFirstStar());
        log("Second star:");
        log(day.calculateSecondStar());
    }
}
