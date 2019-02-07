package acime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * Communicates with Git
 */
public class GitRunner {
    RuntimeFactory rfac;

    public GitRunner(RuntimeFactory runtimeFactory) {
        this.rfac = runtimeFactory;
    }

    /**
     * Clones a git repository into the system's temporary directory using the provided clone_url
     * @param clone_url The repo URL to clone
     * @return The temp directory the repo is cloned in
     * @throws IOException
     */
    public File cloneRepo(String clone_url) throws IOException {
        if (!isValidGitRepo(clone_url)) {
            return null;
        }
        Path tempDir = Files.createTempDirectory(null);
        String cmd = "git clone " + clone_url;
        Process p = this.rfac.makeRuntime().exec(cmd, null, tempDir.toFile());
	try {
	    p.waitFor();
	}
	catch(InterruptedException e) {

	}
        tempDir.toFile().deleteOnExit();
        return tempDir.toFile();
    }

    /**
     * Checks out the given commit in the provided repository location
     * @param repository Where the repo is located
     * @param commit_hash The commit hash to checkout
     * @throws IOException
     */
    public void checkoutCommit(File repository, String commit_hash) throws IOException {
        String cmd = "git checkout " + commit_hash;
        Process p = this.rfac.makeRuntime().exec(cmd, null, repository);
	try {
	    p.waitFor();
	}
	catch(InterruptedException e) {

	}
    }

    /**
     * Checks to see if the provided string points to a valid remote repository
     * @param clone_url The repository URL to validate
     * @return Whether or not the URL points to a valid git repository
     * @throws IOException
     */
    public boolean isValidGitRepo(String clone_url) throws IOException {
        String cmd = "git ls-remote  " + clone_url;
        Process p = this.rfac.makeRuntime().exec(cmd, null, new File(System.getProperty("user.dir")));
	try {
	    p.waitFor();
	}
	catch(InterruptedException e) {

	}
        BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String output = stdout.lines().collect(Collectors.joining(System.lineSeparator()));
        return !output.isEmpty();
    }


}
