package acime;

import java.io.*;
import java.io.File;
import java.io.FileWriter;

/**
   Write build and test logs to files.
 **/
public class LogWriter {
    private String directory;
    private WriterFactory writerFactory;
    /**
       @param directory The directory where all logs will be written
       @param writerFactory The factory that will provide FileWriters
     **/
    public LogWriter(String directory, WriterFactory writerFactory) {
	this.directory = directory;
	this.writerFactory = writerFactory;
    }

    public void log(String body, String hash, String date) {
	try {
	    File f = new File(this.directory, hash+date);
	    FileWriter fw = this.writerFactory.makeWriter(f);
	    fw.write(body);
	    fw.close();
	}
	// Only catches and then keeps on chugging.
	catch(IOException ioe) {
	    System.out.println(ioe);
	}
    }
}
