package com.mycompany.app;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Integer.parseInt;

public class Day20 implements Day {

    private long[][] encryptedFile;

    private final String filename;
    private List<String> input;

    public Day20(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        encryptedFile = new long[input.size()][2];
    }

    private void resetData(long key) {
        for (int i = 0; i < input.size(); i++) {
            encryptedFile[i][0] = parseInt(input.get(i)) * key;
            encryptedFile[i][1] = i;
        }
    }

    @Override
    public String calculateFirstStar() {
        resetData(1L);
        mixFile();
        return calculateCoordinates();
    }

    @Override
    public String calculateSecondStar() {
        resetData(811589153L);
        IntStream.range(0, 10).forEach(i -> mixFile());
        return calculateCoordinates();
    }

    private void mixFile() {
        //log(Arrays.stream(encryptedFile).map(a -> a[0] + "").collect(joining(", ")));
        for (int i = 0; i < encryptedFile.length; i++) {
            long nextValueIndex = 0;
            long shiftValue = -1;
            while (nextValueIndex < encryptedFile.length) {
                if (encryptedFile[(int) nextValueIndex][1] == i) {
                    shiftValue = encryptedFile[(int) nextValueIndex][0];
                    break;
                }
                nextValueIndex++;
            }
            long destination = clampDestination(nextValueIndex + shiftValue, encryptedFile.length);
            mixArray(nextValueIndex, destination);
/*            log("Value: " + shiftValue + "\t" + "From: " + nextValueIndex + "\t" + "To: " + destination + "\t" + Arrays.stream(encryptedFile)
                                                                                                                       .map(a -> a[0] + "")
                                                                                                                       .collect(joining(", ")));*/
        }
    }

    private String calculateCoordinates() {
        int index = -1;
        for (int i = 0; i < encryptedFile.length; i++) {
            if (encryptedFile[i][0] == 0) {
                index = i;
            }
        }
        long ONE = encryptedFile[((1000 % encryptedFile.length) + index) % encryptedFile.length][0];
        long TWO = encryptedFile[((2000 % encryptedFile.length) + index) % encryptedFile.length][0];
        long THREE = encryptedFile[((3000 % encryptedFile.length) + index) % encryptedFile.length][0];
        return "" + (ONE + TWO + THREE);
    }

    long clampDestination(long destination, long length) {
        long result = destination;
        if (result < 0) {
            while (result < 0) {
                result += length - 1;
            }
        } else if (result >= length) {
            while (result >= length) {
                result += 1 - length;
            }
        }
        return result;
    }

    void mixArray(long from, long to) {
        long tmpValue = encryptedFile[(int) from][0];
        long tmpIndex = encryptedFile[(int) from][1];
        if (from < to) {
            for (long i = from; i < to; i++) {
                encryptedFile[(int) i][0] = encryptedFile[(int) (i + 1)][0];
                encryptedFile[(int) i][1] = encryptedFile[(int) (i + 1)][1];
            }
        } else if (from > to) {
            for (long i = from; i > to; i--) {
                encryptedFile[(int) i][0] = encryptedFile[(int) (i - 1)][0];
                encryptedFile[(int) i][1] = encryptedFile[(int) (i - 1)][1];
            }
        }
        encryptedFile[(int) to][0] = tmpValue;
        encryptedFile[(int) to][1] = tmpIndex;
    }
}
