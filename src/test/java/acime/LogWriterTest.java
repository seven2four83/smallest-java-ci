import acime.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;


class MockFileWriter extends FileWriter {
    File f;
    boolean closed = false;

    public MockFileWriter(File f) throws IOException {
	super("/dev/null");
	f = f;
    }
    public void write(String s) {
	return;
    }
    public void close() {
	this.closed = true;
    }
}

class MockFactory implements WriterFactory {
    File f;
    MockFileWriter lastMFW = null;
    public FileWriter makeWriter(File f) throws IOException {
	this.f = f;
	this.lastMFW = new MockFileWriter(f);
	return this.lastMFW;
    }
}

public class LogWriterTest {
    /**
       Check that filename is the hash + date.
     **/
    @Test
    void correctFilename() {
	String hash = "abc";
	String date = "def";
	MockFactory mf = new MockFactory();
	LogWriter lw = new LogWriter("/", mf);
	lw.log("irrelevant", hash, date);
	Assertions.assertEquals(mf.f.getName(), hash+date);
    }
    /**
       Check that file is written into correct directory.
     **/
    @Test
    void correctDirectory() {
	String hash = "abc";
	String date = "def";
	String dir = "ghi";
	MockFactory mf = new MockFactory();
	LogWriter lw = new LogWriter("/"+dir+"/", mf);
	lw.log("irrelevant", hash, date);
	Assertions.assertEquals(mf.f.getParent(), "/"+dir);
    }
    /**
       The stream is closed
     **/
    @Test
    void doesClose() {
	String hash = "abc";
	String date = "def";
	String dir = "ghi";
	MockFactory mf = new MockFactory();
	LogWriter lw = new LogWriter("/"+dir+"/", mf);
	lw.log("irrelevant", hash, date);
	Assertions.assertTrue(mf.lastMFW.closed);
    }
}
