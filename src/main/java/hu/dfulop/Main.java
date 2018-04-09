package hu.dfulop;

import hu.dfulop.application.DirscanApplication;

import java.io.IOException;

public class Main {

    private static DirscanApplication application = new DirscanApplication();


    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new RuntimeException("The application requires two filenames to be run.");
        }

        application.execute(args);
    }




}
