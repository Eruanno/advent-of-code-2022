package com.mycompany.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class FileReader {
    static List<String> readInput(String filename) throws IOException {
        InputStream inputStream = FileReader.class.getResourceAsStream("/" + filename);
        List<String> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                input.add(line);
            }
        }
        return input;
    }
}
