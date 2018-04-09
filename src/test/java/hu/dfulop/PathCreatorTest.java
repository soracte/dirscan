package hu.dfulop;

import hu.dfulop.paths.domain.Path;
import hu.dfulop.paths.control.PathCreator;
import hu.dfulop.permission.Permission;
import org.junit.Test;

public class PathCreatorTest {

    private PathCreator pathCreator = new PathCreator();

    @Test
    public void createPath() {
        Path actual = pathCreator.createPath("/var/lib/jenkins", Permission.READ);
    }
}