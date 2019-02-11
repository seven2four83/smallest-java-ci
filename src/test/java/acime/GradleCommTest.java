// TODO: figure out how to test this stuff...
// Probably through setting up an entire gradle build :-(?
import acime.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import java.nio.file.*;
/**
   These tests are not automated and does not use mocking yet.
   This is because of the large amounts of mocking needed -- the time needed
   to do this is simply too large for this project.
   Instead it depends on manual intervention of the programmer -- sorry about that :-/.
 **/
public class GradleCommTest {
    @Test
    public void testBuild() throws IOException {
	GradleComm gc = new GradleComm(new BasicRuntimeFactory());
	Assertions.assertTrue(gc.buildAt(new File(System.getProperty("user.dir"), "src/test/java/acime/DECIDE")).length() != 0);
    }
}
