package acime;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import java.io.*;
import java.nio.file.*;
/**
   Communicate with gradle.
 **/
public class GradleComm {
    RuntimeFactory rfac;

    public GradleComm(RuntimeFactory runtimeFactory) {
	this.rfac = runtimeFactory;
    }

    /**
       Run process and collect stdout.
       @param dir Directory to run in, has to be absolute.
       @param cmd Command to run in.
    **/
    private String cdAndGo(File dir, String cmd) throws IOException {
	assert(dir.isAbsolute());
	Process p = this.rfac.makeRuntime().exec(cmd, null, dir);
	BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
	return stdout.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
       Run gradle build.
       @param directory Directory to run in.
       @return output from gradle.
     **/
    public String buildAt(File directory) throws IOException {
	return this.cdAndGo(directory, "gradle build");
    }
    /**
       Run gradle compileJava.
       @param directory Directory to run in.
       @return output from gradle.
     **/
    public String compileAt(File directory) throws IOException {
	return this.cdAndGo(directory, "gradle compileJava");
    }
    /**
       Run gradle test.
       @param directory Directory to run in.
       @return output from gradle.
     **/
    public String testAt(File directory) throws IOException {
	return this.cdAndGo(directory, "gradle test");
    }
}
