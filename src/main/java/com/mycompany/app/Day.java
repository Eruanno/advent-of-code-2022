package com.mycompany.app;

import java.io.IOException;

interface Day {
    void loadData() throws IOException;

    String calculateFirstStar();

    String calculateSecondStar();
}