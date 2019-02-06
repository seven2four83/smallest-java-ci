import acime.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import java.nio.file.*;

public class PresentFolderIndexTest {
    /**
       Tests that the method throws on bad directory
     **/
    @Test
    public void throwOnBadDir() {
	ContinuousIntegrationServer ci = new ContinuousIntegrationServer();
	boolean flag = false;
	try {
	    ci.presentFolderIndex("random");
	}
	catch(Exception e) {
	    if(!(e instanceof IOException)) {
		Assertions.assertTrue(true);
		flag = true;
	    }
	}
	if(!flag) {
	    Assertions.assertTrue(false);
	}
    }
}
