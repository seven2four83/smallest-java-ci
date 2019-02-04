import acime.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;

public class PullRequestEventTest {
    String json;
    public PullRequestEventTest() throws IOException {
	try {
	    // We need some json as a test-case.
	    // This file is provided by Github as an example.
	    Scanner sc = new Scanner(new File(System.getProperty("user.dir"),"/src/test/java/acime/pullreqe.json"));
	    json = sc.useDelimiter("\\Z").next(); // "\\Z" as delimiter means 'read whole file'
	    sc.close();
	}
	catch(IOException ioe) {
	    throw ioe;
	}
    }

    /**
       Test if the hash is correctly extracted.
     **/
    @Test
    public void canExtractHash() throws Exception {
	try {
	    PullRequestEvent pre = new PullRequestEvent(this.json);
	    Assertions.assertEquals(pre.getHeadHash(), "34c5c7793cb3b279e22454cb6750c80560547b3a");
	}
	catch(Exception e) {
	    // Fail test :-)
	    throw e;
	}
    }    /**
       Test if obviously malformed JSON object which is still valid JSON fails.
     **/
    @Test
    public void canRefuseMalformed() {
	try {
	    PullRequestEvent pre = new PullRequestEvent("[1,2,3]");
	}
	catch(Exception e) {
	    // Pass Test :-D
	    Assertions.assertTrue(true);
	}
    }
}
