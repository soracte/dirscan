package hu.dfulop;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathCreatorTest {

    private PathCreator pathCreator = new PathCreator();

    @Test
    public void createPath() {
        Path actual = pathCreator.createPath("/var/lib/jenkins", Permission.READ);
    }
}