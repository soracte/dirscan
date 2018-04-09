package hu.dfulop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final PathCreator PATH_CREATOR = new PathCreator();

    public static void main(String[] args) {
        List<String> readable = Arrays.asList("/var/lib/jenkins", "/var/lib", "/var", "/bin/helper");
        List<String> writable = Arrays.asList("/var/lib/jenkins/log", "/bin/helper/log");

        Stream<Path> readablePaths = readable.stream().map(r -> PATH_CREATOR.createPath(r, Permission.READ));
        Stream<Path> writablePaths = readable.stream().map(r -> PATH_CREATOR.createPath(r, Permission.READ));



    }


}
