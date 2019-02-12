package acime;

import java.io.*;
import java.io.File;
import java.io.FileWriter;


/**
   WriterFactory produces new FileWriters for writing to files.
   Pro-tip:
   Can be used for creating mock FileWriters.
 **/
public interface WriterFactory {
    /**
       Return writer for file.
       @param file File to open
     **/
    public FileWriter makeWriter(File file) throws IOException;
}
