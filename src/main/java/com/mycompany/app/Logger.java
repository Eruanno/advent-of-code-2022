package com.mycompany.app;

import java.math.BigInteger;

public class Logger {

    static void log(String message) {
        System.out.println(message);
    }

    static void log(Long message) {
        System.out.println(message);
    }

    static void log(Integer message) {
        System.out.println(message);
    }

    static void log(BigInteger message) {
        System.out.println(message);
    }
}
