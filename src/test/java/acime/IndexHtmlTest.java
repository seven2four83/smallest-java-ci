import acime.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

public class IndexHtmlTest {
    /**
       Test that given a short list the HTML is what is expected.
     **/
    @Test
    public void correctList() {
	IndexHtml indexHtml = new IndexHtml();
	String x = indexHtml.indexBuild(new ArrayList<String>(Arrays.asList("firstBuild", "secondBuild")), "/build/");
	Assertions.assertEquals(x, "<ul><li><a href=/build/firstBuild>firstBuild</a></li><li><a href=/build/secondBuild>secondBuild</a></li></ul>");
    }
}
