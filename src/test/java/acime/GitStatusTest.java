package acime;

import acime.*;
import acime.Models.GitHubStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GitStatusTest {
    /**
     * Given the correct details, the status must be posted
     */
    @Test
    public void validDetails() throws Exception {
		GitHubStatus status = new GitHubStatus.Builder("pending").build();
//		GitHubStatusHandler ghStatusHandler = new GitHubStatusHandler(status);
//		ghStatusHandler.postStatus("jsjolen","smallest-java-ci","c7cf7d3ae6f7638a6c7c02a8f4630d803c52478b","ciServerTestResults","REgyNjQyY2lzZXJ2ZXI=");
//		new GitHubStatusHandler(status).postStatus("jsjolen","smallest-java-ci","c7cf7d3ae6f7638a6c7c02a8f4630d803c52478b","smallestjavaci", "c21hbGxlc3RqYXZhY2k6ZXZlcnl0aGluZ2lzaGVyZTEyMw==");
        Assertions.assertTrue(new GitHubStatusHandler(status).postStatus("jsjolen","smallest-java-ci","c7cf7d3ae6f7638a6c7c02a8f4630d803c52478b","smallestjavaci", "c21hbGxlc3RqYXZhY2k6ZXZlcnl0aGluZ2lzaGVyZTEyMw=="));
    }


}
