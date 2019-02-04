package acime;

import java.io.*;
import java.io.File;
import java.io.FileWriter;

/**
   Basic WriterFactory
 **/
public class FileWriterFactory implements WriterFactory {
    public FileWriter makeWriter(File file) throws IOException {
	return new FileWriter(file);
    }
}
