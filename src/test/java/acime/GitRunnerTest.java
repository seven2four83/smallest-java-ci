package acime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import java.nio.file.*;

public class GitRunnerTest {

    /**
     * Tests if valid repositories can be cloned in the system's temp directory
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testClone() throws IOException, InterruptedException {
	    GitRunner gr = new GitRunner(new BasicRuntimeFactory());
	    File repoDir = gr.cloneRepo("https://github.com/jsjolen/smallest-java-ci.git");
        File repoContainer = new File(repoDir.getPath() + "/smallest-java-ci");
        Assertions.assertEquals(true, repoDir.isDirectory());
        Assertions.assertEquals(true, repoContainer.isDirectory());
        repoDir.delete();
    }

    /**
     * Tests if valid commits can be checked out correctly
     * Uses the commit located at: https://github.com/jsjolen/smallest-java-ci/tree/1491e8e189592d6ac7188dde490b2171a9221f29
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testCheckout() throws IOException, InterruptedException {
        GitRunner gr = new GitRunner(new BasicRuntimeFactory());
        File repoDir = gr.cloneRepo("https://github.com/jsjolen/smallest-java-ci.git");
        File repository = new File(repoDir+"/"+repoDir.list()[0]);
        gr.checkoutCommit(repository, "1491e8e189592d6ac7188dde490b2171a9221f29");
        File repoContainer = new File(repository.getPath() + "/src/main/java/acime");
        String[] repoFileNames= {"ContinuousIntegrationServer.java"};
        Assertions.assertEquals(true, repoContainer.isDirectory());
        for (int i = 0; i < repoContainer.list().length; i++) {
            Assertions.assertEquals(repoFileNames[i], repoContainer.list()[i]);
        }
        repoDir.delete();
    }

    /**
     * Tests if a valid git repository URL can be validated
     * @throws IOException
     */
    @Test
    public void testValidRepo() throws IOException {
        GitRunner gr = new GitRunner(new BasicRuntimeFactory());
        Assertions.assertEquals(true, gr.isValidGitRepo("https://github.com/jsjolen/smallest-java-ci.git"));
    }

    /**
     * Tests if an invalid git repository string can be invalidated
     * @throws IOException
     */
    @Test
    public void testInvalidRepo() throws IOException {
        GitRunner gr = new GitRunner(new BasicRuntimeFactory());
        Assertions.assertEquals(false, gr.isValidGitRepo("This is an invalid repository URL."));
    }
}
