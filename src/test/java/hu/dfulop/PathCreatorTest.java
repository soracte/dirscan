package hu.dfulop;

import com.sun.source.tree.AssertTree;
import hu.dfulop.paths.domain.Path;
import hu.dfulop.paths.control.PathCreator;
import hu.dfulop.permission.Permission;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PathCreatorTest {

    private PathCreator pathCreator = new PathCreator();

    @Test
    public void createPath() {
        // call
        Path path = pathCreator.createPath("/var/lib", Permission.READ);

        // verify
        assertThat(path.getName(), equalTo("/"));
        assertThat(path.getPermission(), equalTo(Permission.NONE));

        Path subfolder = path.getSubfolder();
        assertThat(subfolder.getName(), equalTo("var"));
        assertThat(subfolder.getPermission(), equalTo(Permission.NONE));

        Path subsubfolder = subfolder.getSubfolder();
        assertThat(subsubfolder.getName(), equalTo("lib"));
        assertThat(subsubfolder.getPermission(), equalTo(Permission.READ));
    }
}